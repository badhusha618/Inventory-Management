/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller;

import com.makinus.unitedsupplies.api.controller.request.OrderStatusRequest;
import com.makinus.unitedsupplies.api.controller.response.*;
import com.makinus.unitedsupplies.api.data.mapping.OrderMapper;
import com.makinus.unitedsupplies.api.data.mapping.PaymentOrderMapper;
import com.makinus.unitedsupplies.api.data.mapping.RazorpayPaymentMapper;
import com.makinus.unitedsupplies.api.paytm.PaytmService;
import com.makinus.unitedsupplies.api.paytm.api.reftype.TransactionStatus;
import com.makinus.unitedsupplies.api.paytm.api.transaction.status.TxStatusResponse;
import com.makinus.unitedsupplies.api.razorpay.RazorpayAPI;
import com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayPaymentStatus;
import com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayReason;
import com.makinus.unitedsupplies.api.razorpay.request.RazorpayPaymentRequest;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.reftype.OrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.PaymentProvider;
import com.makinus.unitedsupplies.common.data.reftype.PaymentStatus;
import com.makinus.unitedsupplies.common.data.reftype.ProdOrderStatus;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.orderfulfillment.OrderFulfillmentService;
import com.makinus.unitedsupplies.common.data.service.paymentorder.PaymentOrderService;
import com.makinus.unitedsupplies.common.data.service.prodorder.ProductOrderService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.razorpayPayment.RazorpayPaymentService;
import com.makinus.unitedsupplies.common.data.service.user.MobileUserService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.sms.SMSService;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import com.razorpay.RazorpayException;
import freemarker.template.Configuration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.api.paytm.api.reftype.PaymentOrderStatus.ORDER_FAILED;
import static com.makinus.unitedsupplies.api.paytm.api.reftype.PaymentOrderStatus.ORDER_SUCCESS;
import static com.makinus.unitedsupplies.api.paytm.api.reftype.TransactionStatus.*;
import static com.makinus.unitedsupplies.api.paytm.hash.PaytmHash.generateChecksum;
import static com.makinus.unitedsupplies.api.paytm.hash.PaytmHash.verifyChecksum;
import static com.makinus.unitedsupplies.api.paytm.hash.PaytmUtils.getResponseBody;
import static com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayPaymentStatus.PAYMENT_COMPLETED;
import static com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayPaymentStatus.PAYMENT_FAILURE;
import static com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayReason.SIGNATURE_VERIFICATION_FAILED;
import static com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayReason.SIGNATURE_VERIFIED;
import static com.makinus.unitedsupplies.api.razorpay.reftype.RazorPayStep.SIGNATURE_VERIFICATION;
import static com.makinus.unitedsupplies.api.utils.ApiUtils.SIGNATURE_VERIFICATION_DESCRIPTION;
import static com.makinus.unitedsupplies.common.data.reftype.PaymentStatus.PAYMENT_SUCCESS;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * @author sabique
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "Order API Controller")
public class OrderController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final String splashImage = "classpath:static/img/Splash.png";

    @Value("${us.app.paytm.mid}")
    private String paytmMID;

    @Value("${us.app.sms.admin.mobile.number}")
    private String adminMobileNumber;

    @Value("${us.app.paytm.mkey}")
    private String paytmMKey;

    @Value("${us.app.paytm.website}")
    private String paytmWebSite;

    @Value("${us.app.paytm.industry}")
    private String paytmIndustry;

    @Value("${us.app.paytm.channel}")
    private String paytmChannel;

    @Value("${us.app.paytm.callback}")
    private String callbackUrl;

    @Value("${us.app.product.invoice.url}")
    private String productInvoiceDownloadUrl;

    @Value("${us.app.service.invoice.url}")
    private String serviceInvoiceDownloadUrl;

    @Value("${us.app.purchase.order.url}")
    private String purchaseOrderDownloadUrl;

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private PaytmService paytmService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private SMSService smsService;

    @Autowired
    private RazorpayPaymentMapper razorpayPaymentMapper;

    @Autowired
    private RazorpayPaymentService razorpayPaymentService;

    @Autowired
    private RazorpayAPI razorpayAPI;


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Order list API")
    @GetMapping("/order/list")
    public ResponseEntity<List<OrderResponse>> getMyOrders() throws UnitedSuppliesException {
        LOG.info("Order list by user - Order Controller");
        try {
            User user = mobileUserService.findByMobile(getCurrentUser());
            List<Order> orders = orderService.ordersListByUser(user.getId());
            List<OrderResponse> orderResponses = new ArrayList<>();
            orders.forEach(order -> {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setOrderRef(longToString(order.getOrderRef()));
                orderResponse.setOrderNo(order.getOrderNo());
                orderResponse.setOrderDate(utcDateForDDMMYYYY(order.getOrderDate()));
                orderResponse.setTotalAmount(order.getOrderTotal().toString());
                orderResponse.setOrderStatus(OrderStatus.statusMatch(order.getStatus()).getDisplay());
                orderResponses.add(orderResponse);
            });
            return new ResponseEntity<>(orderResponses, HttpStatus.OK);
        } catch (Exception ex) {
            LOG.error("Error occurs while fetch orders by user {}", ex.getMessage());
            throw new UnitedSuppliesException(ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Transaction Status for Order")
    @PostMapping("/order/transaction/status")
    public ResponseEntity<OrderStatusResponse> getTransactionStatus(@ApiParam("Order Request") @RequestBody OrderStatusRequest statusRequest) {
        LOG.info("Transaction Status Api for order - Order Controller - {}", this.getClass().getSimpleName());
        try {
            LOG.info("OrderStatusRequest  {}", statusRequest.toString());
            User user = mobileUserService.findByMobile(getCurrentUser());
            Order order = orderService.findOrderByOrderRef(Long.valueOf(statusRequest.getOrderId()));
            TxStatusResponse txnStatus = paytmService.txnStatus(generateRequestBody(statusRequest.getOrderNo()));
            LOG.info("Transaction status response from paytm {}", txnStatus.toString());
            PaymentOrder paymentOrder = paymentOrderMapper.mapExtra(txnStatus, order);
            paymentOrder.setCurrency(statusRequest.getCurrency());
            boolean verifyChecksum = verifyChecksum(getResponseBody(txnStatus.getResponseString()), paytmMKey, txnStatus.getHead().getSignature());
            if (verifyChecksum) {
                boolean verified = txnVerification(statusRequest, txnStatus);
                if (verified) {
                    paymentOrder.setOrderStatus(ORDER_SUCCESS.getStatus());
                    String template = MessageFormat.format(NEW_ORDER_SMS_BODY, user.getFullName(), order.getOrderNo());
                    String adminTemplate = MessageFormat.format(ADMIN_NEW_ORDER_SMS_BODY, order.getOrderNo(), user.getFullName(), PAYMENT_STATUS_SUCCESSFUL, user.getMobile());
                    smsService.sendOTP(AppUtils.formattedPhoneNo(user.getMobile(), INDIAN_CODE_WITHOUT_PLUS), template);
                    smsService.sendOTP(AppUtils.formattedPhoneNo(adminMobileNumber, INDIAN_CODE_WITHOUT_PLUS), adminTemplate);
                } else {
                    paymentOrder.setOrderStatus(ORDER_FAILED.getStatus());
                    String template = MessageFormat.format(PAYMENT_FAILED_SMS_BODY, user.getFullName(), order.getOrderNo());
                    String adminTemplate = MessageFormat.format(ADMIN_NEW_ORDER_SMS_BODY, order.getOrderNo(), user.getFullName(), PAYMENT_STATUS_FAILURE, user.getMobile());
                    smsService.sendOTP(AppUtils.formattedPhoneNo(user.getMobile(), INDIAN_CODE_WITHOUT_PLUS), template);
                    smsService.sendOTP(AppUtils.formattedPhoneNo(adminMobileNumber, INDIAN_CODE_WITHOUT_PLUS), adminTemplate);
                }
                PaymentOrder savedPaymentOrder = paymentOrderService.saveOrUpdatePaymentOrder(paymentOrder);
                order.setPaymentStatus(PaymentStatus.valueMatch(txnStatus.getBody().getResultInfo().getResultStatus()).getStatus());
                orderService.updateOrder(order);
                return new ResponseEntity<>(orderStatusResponse(savedPaymentOrder), HttpStatus.OK);
            } else {
                LOG.info("Checksum verification failed - {}", this.getClass().getSimpleName());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (UnitedSuppliesException | IOException ex) {
            LOG.info("Order is not found with the order ref {}", statusRequest.getOrderId());
            return new ResponseEntity<>(orderStatusResponse(statusRequest), HttpStatus.OK);
        }
    }

    private OrderStatusResponse orderStatusResponse(PaymentOrder savedPaymentOrder) throws UnitedSuppliesException {
        OrderStatusResponse response = new OrderStatusResponse();
        response.setTxnId(savedPaymentOrder.getTxnId());
        response.setOrderId(String.valueOf(savedPaymentOrder.getOrderRef()));
        response.setOrderNo(orderService.findOrderByOrderRef(savedPaymentOrder.getOrderRef()).getOrderNo());
        response.setResponseCode(savedPaymentOrder.getResultCode());
        response.setResponseStatus(savedPaymentOrder.getResultStatus());
        response.setResponseMsg(codeMatch(savedPaymentOrder.getResultCode()).getDisplay());
        if (savedPaymentOrder.getOrderStatus().equals(ORDER_FAILED.getStatus())) {
            response.setResponseMsg(TXN_FAILED.getDisplay());
            if (TXN_SUCCESS.getCode().equals(response.getResponseCode())) {
                response.setResponseCode(TXN_FAILED.getCode());
            }
        }
        LOG.info("Transaction status api response - {}", response.toString());
        return response;
    }

    private OrderStatusResponse orderStatusResponse(OrderStatusRequest orderStatusRequest) {
        OrderStatusResponse response = new OrderStatusResponse();
        response.setOrderId(orderStatusRequest.getOrderId());
        response.setOrderNo(orderStatusRequest.getOrderNo());
        response.setResponseCode(TransactionStatus.TXN_FAILED.getCode());
        response.setResponseStatus(TransactionStatus.TXN_FAILED.getStatus());
        response.setResponseMsg(TransactionStatus.TXN_FAILED.getDisplay());
        LOG.info("Transaction status api response after failure - {}", response.toString());
        return response;
    }

    private boolean txnVerification(OrderStatusRequest statusRequest, TxStatusResponse status) {
        return TXN_SUCCESS == TransactionStatus.statusMatch(status.getBody().getResultInfo().getResultStatus()) &&
                statusRequest.getOrderNo().equals(status.getBody().getOrderId())
                && Double.parseDouble(statusRequest.getTxnAmount()) == Double.parseDouble(status.getBody().getTxnAmount());
    }

    private String generateRequestBody(String orderNo) throws UnitedSuppliesException {
        JSONObject body = getBody(orderNo);
        JSONObject paytmParams = new JSONObject();
        paytmParams.put("head", getHead(body));
        paytmParams.put("body", body);
        return paytmParams.toString();
    }

    private JSONObject getHead(JSONObject body) throws UnitedSuppliesException {
        JSONObject head = new JSONObject();
        head.put("channelId", paytmChannel);
        head.put("requestTimestamp", String.valueOf(Instant.now().toEpochMilli()));
        head.put("signature", generateChecksum(body.toString(), paytmMKey));
        return head;
    }

    private JSONObject getBody(String orderNo) {
        JSONObject body = new JSONObject();
        body.put("mid", paytmMID);
        body.put("orderId", orderNo);
        return body;
    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Order Details by order ref")
    @GetMapping("/order/details")
    public ResponseEntity<OrderDetailsResponse> getOrderDetailsResponse(@ApiParam("Order Ref") @RequestParam("orderRef") Long orderRef) throws UnitedSuppliesException {
        LOG.info("Get order details by order ref - {}", this.getClass().getSimpleName());
        Order order = orderService.findOrderByOrderRef(orderRef);
        List<ProductOrder> productOrderList = productOrderService.getProductListByOrderRef(orderRef);
        Address address = addressService.findAddressById(order.getDelAddressId());
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setOrderRef(String.valueOf(order.getOrderRef()));
        orderDetailsResponse.setOrderNo(order.getOrderNo());
        orderDetailsResponse.setOrderDate(utcDateForDDMMYYYY(order.getOrderDate()));
        orderDetailsResponse.setTotalAmount(String.valueOf(order.getOrderTotal()));
        orderDetailsResponse.setPaymentStatus(PaymentStatus.statusMatch(order.getPaymentStatus()).getDisplay());
        orderDetailsResponse.setOrderStatus(OrderStatus.statusMatch(order.getStatus()).getDisplay());
        orderDetailsResponse.setPurchaseOrderDownloadLink(MessageFormat.format(purchaseOrderDownloadUrl, orderRef));
        orderDetailsResponse.setServiceInvoiceDownloadLink(StringUtils.isEmpty(order.getCustServInvoiceNo()) ? "" : MessageFormat.format(serviceInvoiceDownloadUrl, orderRef));
        orderDetailsResponse.setDeliveryAddress(address.toDisplayableString());
        orderDetailsResponse.setProductStatus(productOrderStatus(productOrderList));
        orderDetailsResponse.setProductInvoice(productInvoiceResponses(orderFulfillmentService.getGeneratedInvoicesByOrderRef(orderRef), order.getOrderNo()));
        if (order.getPaymentProvider().equalsIgnoreCase(PaymentProvider.PAYTM.getStatus())) {
            Optional<PaymentOrder> optionalPaymentOrder = paymentOrderService.paymentOrderByOrderRef(order.getOrderRef());
            if (optionalPaymentOrder.isPresent()) {
                orderDetailsResponse.setDescription(optionalPaymentOrder.get().getResultMsg());
            } else {
                orderDetailsResponse.setDescription(PaymentStatus.NOT_PAID.getDisplay());
            }
        } else if (order.getPaymentProvider().equalsIgnoreCase(PaymentProvider.RAZORPAY.getStatus())) {
            Optional<RazorpayPayment> optionalRazorpayPayment = razorpayPaymentService.getRazorpayByOrderRef(order.getOrderRef());
            if (optionalRazorpayPayment.isPresent()) {
                orderDetailsResponse.setDescription(optionalRazorpayPayment.get().getDescription());
            } else {
                orderDetailsResponse.setDescription(PaymentStatus.NOT_PAID.getDisplay());
            }
        }
        return new ResponseEntity<>(orderDetailsResponse, HttpStatus.OK);
    }

    private List<ProductInvoice> productInvoiceResponses(List<OrderFulfillment> orderFulfillments, String orderNo) {
        return orderFulfillments.stream().map(o -> productInvoiceResponse(o, orderNo)).collect(Collectors.toList());
    }

    private ProductInvoice productInvoiceResponse(OrderFulfillment orderFulfillment, String orderNo) {
        ProductInvoice productInvoice = new ProductInvoice();
        productInvoice.setOrderRef(String.valueOf(orderFulfillment.getOrderRef()));
        productInvoice.setOrderNo(orderNo);
        productInvoice.setFulfillmentId(String.valueOf(orderFulfillment.getId()));
        productInvoice.setFulfillmentRef(String.valueOf(orderFulfillment.getFulfillmentRef()));
        productInvoice.setInvoiceNo(orderFulfillment.getProductInvoiceNo());
        productInvoice.setInvoiceDate(utcDateForDDMMYYYY(orderFulfillment.getProductInvoiceDate()));
        productInvoice.setSeller(orderFulfillment.getVendorName());
        productInvoice.setProductInvoiceDownloadLink(MessageFormat.format(productInvoiceDownloadUrl, orderFulfillment.getOrderRef(), orderFulfillment.getId()));
        return productInvoice;
    }

    private List<ProductOrderResponse> productOrderStatus(List<ProductOrder> productOrders) {
        List<ProductOrderResponse> productOrderResponses = new ArrayList<>();
        productOrders.forEach(productOrder -> {
            ProductOrderResponse productOrderResponse = new ProductOrderResponse();
            productOrderResponse.setProductId(longToString(productOrder.getProductId()));
            productOrderResponse.setProductCode(productOrder.getProductCode());
            productOrderResponse.setProductName(productOrder.getProductName());
            productOrderResponse.setQuantity(productOrder.getProQuantity());
            productOrderResponse.setUnit(productOrder.getUnitCode());
            productOrderResponse.setProductStatus(ProdOrderStatus.statusMatch(productOrder.getStatus()).getDisplay());
            productOrderResponses.add(productOrderResponse);
        });
        return productOrderResponses;
    }

    @PostMapping(value = "/verify/signature.mk")
    public ResponseEntity<OrderStatusResponse> verifySignature(@ApiParam("Razorpay Payment Request") @RequestBody RazorpayPaymentRequest razorpayPaymentRequest) throws UnitedSuppliesException, RazorpayException {
        LOG.info("Save payment Id and verify signature - {}", this.getClass().getSimpleName());
        RazorpayPayment razorpayPayment = razorpayPaymentMapper.map(razorpayPaymentRequest);
        Order order = orderService.findOrderByOrderRef(Long.valueOf(razorpayPaymentRequest.getOrderID()));
        updateOrderStatusAfterSignatureVerification(razorpayAPI.verifySignature(createVerifyPaymentJSONObject(razorpayPaymentRequest)), order);
        razorpayPayment.setPaymentStatus(PAYMENT_COMPLETED.getStatus());
        razorpayPayment.setReason(order.getPaymentStatus().equals(PAYMENT_SUCCESS.getStatus()) ? SIGNATURE_VERIFIED.getStatus() : SIGNATURE_VERIFICATION_FAILED.getStatus());
        return new ResponseEntity<>(orderStatusResponse(razorpayPaymentService.saveRazorpayPayment(razorpayPayment), order), HttpStatus.OK);
    }

    private OrderStatusResponse orderStatusResponse(RazorpayPayment razorpayPayment, Order order) throws UnitedSuppliesException {
        OrderStatusResponse response = new OrderStatusResponse();
        response.setTxnId(razorpayPayment.getRazorPaymentID());
        response.setOrderId(String.valueOf(order.getOrderRef()));
        response.setOrderNo(razorpayPayment.getOrderNo());
        response.setResponseCode(RazorPayPaymentStatus.statusMatch(razorpayPayment.getPaymentStatus()).getStatus());
        response.setResponseStatus(RazorPayPaymentStatus.statusMatch(razorpayPayment.getPaymentStatus()).getDisplay());
        response.setResponseMsg(RazorPayReason.statusMatch(razorpayPayment.getReason()).getDisplay());
        LOG.info("order status response for razorpay is - {}", response.toString());
        return response;
    }

    @PostMapping(value = "/payment/failure.mk")
    public ResponseEntity<OrderStatusResponse> paymentFailure(@ApiParam("Razorpay Payment Request") @RequestBody RazorpayPaymentRequest razorpayPaymentRequest) throws UnitedSuppliesException {
        LOG.info("Update razorpay payment table when payment failure - {}", this.getClass().getSimpleName());
        RazorpayPayment razorpayPayment = razorpayPaymentMapper.map(razorpayPaymentRequest);
        Order order = orderService.findOrderByOrderRef(Long.valueOf(razorpayPaymentRequest.getOrderID()));
        updateOrderStatusAfterSignatureVerification(Boolean.FALSE, order);
        razorpayPayment.setPaymentStatus(PAYMENT_FAILURE.getStatus());
        return new ResponseEntity<>(orderStatusResponse(razorpayPaymentService.saveRazorpayPayment(razorpayPayment), order), HttpStatus.OK);

    }

    private JSONObject createVerifyPaymentJSONObject(RazorpayPaymentRequest razorpayPaymentRequest) {
        JSONObject verifyPaymentRequest = new JSONObject();
        verifyPaymentRequest.put("razorpay_signature", razorpayPaymentRequest.getRazorSignature());
        verifyPaymentRequest.put("razorpay_order_id", razorpayPaymentRequest.getRazorOrderID());
        verifyPaymentRequest.put("razorpay_payment_id", razorpayPaymentRequest.getRazorPaymentID());
        return verifyPaymentRequest;
    }

    private void updateOrderStatusAfterSignatureVerification(boolean signatureValid, Order order) throws UnitedSuppliesException {
        User user = mobileUserService.findByMobile(getCurrentUser());
        if (signatureValid) {
            order.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS.getStatus());
            String template = MessageFormat.format(NEW_ORDER_SMS_BODY, user.getFullName(), order.getOrderNo());
            String adminTemplate = MessageFormat.format(ADMIN_NEW_ORDER_SMS_BODY, order.getOrderNo(), user.getFullName(), PAYMENT_STATUS_SUCCESSFUL, user.getMobile());
            smsService.sendOTP(AppUtils.formattedPhoneNo(user.getMobile(), INDIAN_CODE_WITHOUT_PLUS), template);
            smsService.sendOTP(AppUtils.formattedPhoneNo(adminMobileNumber, INDIAN_CODE_WITHOUT_PLUS), adminTemplate);
        } else {
            order.setPaymentStatus(PAYMENT_FAILURE.getStatus());
            String template = MessageFormat.format(PAYMENT_FAILED_SMS_BODY, user.getFullName(), order.getOrderNo());
            String adminTemplate = MessageFormat.format(ADMIN_NEW_ORDER_SMS_BODY, order.getOrderNo(), user.getFullName(), PAYMENT_STATUS_FAILURE, user.getMobile());
            smsService.sendOTP(AppUtils.formattedPhoneNo(user.getMobile(), INDIAN_CODE_WITHOUT_PLUS), template);
            smsService.sendOTP(AppUtils.formattedPhoneNo(adminMobileNumber, INDIAN_CODE_WITHOUT_PLUS), adminTemplate);
        }
        order.setUpdatedBy(getCurrentUser());
        order.setUpdatedDate(getInstant());
        orderService.updateOrder(order);
    }
}
