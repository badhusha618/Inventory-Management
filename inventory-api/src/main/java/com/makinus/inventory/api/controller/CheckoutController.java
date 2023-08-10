/* *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 **/
package com.makinus.inventory.api.controller;

import com.makinus.inventory.api.controller.request.*;
import com.makinus.inventory.api.controller.response.CheckoutPriceResponse;
import com.makinus.inventory.api.controller.response.CheckoutResponse;
import com.makinus.inventory.api.controller.response.OrderPayloadResponseModel;
import com.makinus.inventory.api.controller.response.ProductVendorDetail;
import com.makinus.inventory.api.razorpay.RazorpayAPI;
import com.makinus.inventory.api.razorpay.RazorpayPayloadResponseModel;
import com.makinus.unitedsupplies.api.controller.request.*;
import com.makinus.inventory.api.data.mapping.OrderFulfillmentMapper;
import com.makinus.inventory.api.data.mapping.OrderMapper;
import com.makinus.inventory.api.data.mapping.ProductOrderMapper;
import com.makinus.inventory.api.data.mapping.RazorpayPaymentMapper;
import com.makinus.inventory.api.paytm.PaytmPayloadResponseModel;
import com.makinus.inventory.api.paytm.PaytmService;
import com.makinus.inventory.api.paytm.api.init.transaction.InitTxResponse;
import com.makinus.inventory.api.paytm.api.reftype.InitTransactionStatus;
import com.makinus.inventory.api.razorpay.Prefill;
import com.makinus.inventory.api.utils.ApiUtils;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.reftype.AddressCategory;
import com.makinus.unitedsupplies.common.data.service.ServiceCharges.ServiceChargesService;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.loadingCharges.LoadingChargesService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.orderfulfillment.OrderFulfillmentService;
import com.makinus.unitedsupplies.common.data.service.prodorder.ProductOrderService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productsource.ProductSourceService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.razorpayPayment.RazorpayPaymentService;
import com.makinus.unitedsupplies.common.data.service.transport.TransportService;
import com.makinus.unitedsupplies.common.data.service.unit.UnitService;
import com.makinus.unitedsupplies.common.data.service.user.MobileUserService;
import com.makinus.unitedsupplies.common.data.service.usercart.UserCartService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import com.razorpay.RazorpayException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.inventory.api.paytm.api.reftype.InitTransactionStatus.SUCCESS;
import static com.makinus.inventory.api.paytm.hash.PaytmHash.generateChecksum;
import static com.makinus.inventory.api.paytm.hash.PaytmHash.verifyChecksum;
import static com.makinus.inventory.api.paytm.hash.PaytmUtils.getResponseBody;
import static com.makinus.inventory.api.utils.ApiUtils.DEFAULT_DELIVERY_PINCODE;
import static com.makinus.inventory.api.utils.ApiUtils.INDIAN_CURRENCY;
import static com.makinus.unitedsupplies.common.data.reftype.PaymentProvider.RAZORPAY;
import static com.makinus.unitedsupplies.common.data.reftype.PaymentStatus.NOT_PAID;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * @author Bad_sha
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "Checkout API Controller")
public class CheckoutController {

    private final Logger LOG = LogManager.getLogger(this.getClass());
    @Value("${us.app.base.url}")
    private String baseUrl;

    @Value("${us.app.paytm.mid}")
    private String paytmMID;

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

    @Value("${us.app.paytm.payment.url}")
    private String paymentUrl;

    @Value("${payment.razorpay.api.key}")
    private String apiKey;

    @Value("${united.supplies.name}")
    private String companyName;

    @Value("${united.supplies.logo}")
    private String companyLogo;

    @Autowired
    private UnitService unitService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSourceService productSourceService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private ServiceChargesService serviceChargesService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private LoadingChargesService loadingChargesService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private PaytmService paytmService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderFulfillmentMapper orderFulfillmentMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private RazorpayPaymentMapper razorpayPaymentMapper;

    @Autowired
    private RazorpayPaymentService razorpayPaymentService;

    @Autowired
    private RazorpayAPI razorpayAPI;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Cart Checkout or Direct Checkout")
    @PostMapping("/cart/checkout")
    public ResponseEntity<CheckoutResponse> checkoutProducts(@ApiParam("Checkout") @RequestBody CheckoutRequest checkoutRequest) throws InventoryException {
        CheckoutResponse checkoutResponse = getCheckoutResponseValues(checkoutRequest);
        return new ResponseEntity<>(checkoutResponse, HttpStatus.OK);
    }

    private CheckoutResponse getCheckoutResponseValues(CheckoutRequest checkoutRequest) throws InventoryException {
        CheckoutResponse checkoutResponse = new CheckoutResponse();

        User user = mobileUserService.findByMobile(getCurrentUser());
        String destinationPincode = addressService.findDefaultAddressByUserAndCategory(user.getId(), AddressCategory.DELIVERY_ADDRESS.getStatus()).get().getPostalCode();

        List<ProductCharges> productChargesList = new ArrayList<>();

        List<Long> productIds = checkoutRequest.getProducts().stream().map(p -> Long.valueOf(p.getProductId())).collect(Collectors.toList());
        List<Product> productList = productService.productListByIds(productIds);
        Map<Long, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        List<String> transGroups = productList.stream().map(Product::getTransGroup).collect(Collectors.toList());
        List<ProductVendor> productVendorList = productVendorService.productVendorListByProductIds(productIds);
        Map<Long, List<ProductVendor>> productVendorsMap = productVendorList.stream().collect(Collectors.groupingBy(ProductVendor::getProductId));
        Map<Long, Vendor> vendorMap = vendorService.listActiveVendorsByVendorIds(productVendorList.stream().map(ProductVendor::getVendorId).collect(Collectors.toList())).stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        Map<Long, List<LoadingCharges>> loadingChargesMap = loadingChargesService.loadingChargesListByProduct(productIds).stream().collect(Collectors.groupingBy(LoadingCharges::getProductId));
        Map<String, List<Transport>> transportChargesMap = transportService.transportListByTransGroups(transGroups).stream().collect(Collectors.groupingBy(Transport::getTransGroup));
        Map<Long, Unit> unitMap = unitService.unitList().stream().collect(Collectors.toMap(Unit::getId, Function.identity()));
        checkoutRequest.getProducts().forEach(p -> {
            ProductCharges productCharges = new ProductCharges();
            Product product = productMap.getOrDefault(Long.valueOf(p.getProductId()), new Product());
            List<ProductVendor> productVendors = productVendorsMap.getOrDefault(product.getId(), new ArrayList<>());
            Map<Long, ProductVendor> productVendorMap = productVendors.stream().collect(Collectors.toMap(ProductVendor::getId, Function.identity()));
            ProductVendor productVendor = productVendorMap.getOrDefault(Long.valueOf(p.getProductVendorId()), new ProductVendor());
            Vendor vendor = vendorMap.getOrDefault(productVendor.getVendorId(), new Vendor());
            Unit unit = unitMap.getOrDefault(product.getUnit(), new Unit());
            productCharges.setProductId(product.getId());
            productCharges.setVendorId(vendor.getId());
            productCharges.setVendorCode(vendor.getVendorCode());
            productCharges.setVendorName(vendor.getVendorName());
            productCharges.setPinCode(productVendor.getPinCode());
            productCharges.setUnitId(longToString(unit.getId()));
            productCharges.setUnitCode(unit.getUnitCode());
            productCharges.setUnitName(unit.getUnitName());
            productCharges.setQuantity(Integer.parseInt(p.getQuantity()));
            productCharges.setSaleRate(decimalToString(productVendor.getSaleRate()));
            productCharges.setSubTotal(decimalToString(productVendor.getSaleRate().multiply(new BigDecimal(p.getQuantity()))));
            Integer loadingCharge = loadingChargeCalculation(loadingChargesMap.getOrDefault(product.getId(), new ArrayList<>()), stringToInt(p.getQuantity()));
            productCharges.setLoadingCharge(formatIntoTwoPrecision(new BigDecimal(loadingCharge)));
            productCharges.setMinOrderQty(String.valueOf(product.getMinOrderQty()));
            productCharges.setMaxOrderQty(String.valueOf(product.getMaxOrderQty()));
            productCharges.setTaxInclusive(String.valueOf(product.getTaxInclusive()));
            productCharges.setTransGroup(product.getTransGroup());
            productCharges.setProductVendorDetail(mapProductVendorList(productVendors, vendorMap));
            productChargesList.add(productCharges);
            productCharges.setQuantityList(ApiUtils.mapQuantityList(Integer.valueOf(product.getMinOrderQty())));
        });
        mapTransportCharges(productChargesList, productVendorList, transportChargesMap, destinationPincode);
        checkoutResponse.setProductCharges(productChargesList);
        List<ServiceCharge> serviceChargeList = serviceChargesService.allServiceCharges();
        checkoutResponse.setServiceCharge(formatIntoTwoPrecision(serviceChargeList.size() > 0 ? new BigDecimal(serviceChargeList.get(0).getAmount()) : BigDecimal.ZERO));
        checkoutResponse.setProductPrice(formatIntoTwoPrecision(new BigDecimal(productChargesList.stream().map(p -> stringToDouble(p.getSubTotal())).mapToDouble(Double::intValue).sum())));
        checkoutResponse.setTransportCharge(formatIntoTwoPrecision(new BigDecimal(productChargesList.stream().map(p -> stringToDouble(p.getTransportCharge())).mapToDouble(Double::doubleValue).sum())));
        checkoutResponse.setLoadingCharge(formatIntoTwoPrecision(new BigDecimal(productChargesList.stream().map(p -> stringToDouble(p.getLoadingCharge())).mapToDouble(Double::intValue).sum())));
        checkoutResponse.setTotalCharge(formatIntoTwoPrecision(new BigDecimal(sumOfStrings(checkoutResponse.getServiceCharge(), checkoutResponse.getProductPrice(), checkoutResponse.getTransportCharge(), checkoutResponse.getLoadingCharge()))));
        return checkoutResponse;
    }

    private String sumOfStrings(String serviceCharge, String saleRate, String tranCharge, String loadCharge) {
        return doubleToString(stringToDouble(serviceCharge) + stringToDouble(saleRate) + stringToDouble(tranCharge) + stringToDouble(loadCharge));
    }

    private List<ProductVendorDetail> mapProductVendorList(List<ProductVendor> productVendors, Map<Long, Vendor> vendorMap) {
        return productVendors.stream().map(productVendor -> {
            ProductVendorDetail productVendorDetail = new ProductVendorDetail();
            mapProductVendor(productVendorDetail, productVendor, vendorMap.getOrDefault(productVendor.getVendorId(), new Vendor()));
            return productVendorDetail;
        }).collect(Collectors.toList());
    }

    private void mapProductVendor(ProductVendorDetail productVendorDetail, ProductVendor productVendor, Vendor vendor) {
        productVendorDetail.setId(longToString(productVendor.getId()));
        productVendorDetail.setProductId(longToString(productVendor.getProductId()));
        productVendorDetail.setVendorId(longToString(productVendor.getVendorId()));
        productVendorDetail.setVendorCode(vendor.getVendorCode());
        productVendorDetail.setVendorName(vendor.getVendorName());
        productVendorDetail.setMrpRate(decimalToString(productVendor.getMrpRate()));
        productVendorDetail.setSaleRate(decimalToString(productVendor.getSaleRate()));
        productVendorDetail.setPinCode(productVendor.getPinCode());
    }

    private List<SourceLocation> productSourceLocations(Map<Long, List<ProductSource>> productSourceListMap, ProductRequest p) {
        List<SourceLocation> locations = new ArrayList<>();
        List<ProductSource> productSourceList = productSourceListMap.get(Long.valueOf(p.getProductId()));
        productSourceList.forEach(
                ps -> {
                    SourceLocation sourceLocation = new SourceLocation();
                    sourceLocation.setPinCode(String.valueOf(ps.getId()));
                    sourceLocation.setSource(ps.getSourceName());
                    locations.add(sourceLocation);
                });
        return locations;
    }

    private Integer loadingChargeCalculation(List<LoadingCharges> loadingChargesList, Integer productQuantity) {
        Map<Integer, LoadingCharges> loadingChargesMap = loadingChargesList.stream().collect(Collectors.toMap(LoadingCharges::getQuantity, Function.identity()));
        List<Integer> quantityList = getPossibleQuantities(loadingChargesList, productQuantity);
        Integer availableQuantity = 0;
        if (!loadingChargesList.isEmpty()) {
            availableQuantity = (quantityList.isEmpty() ? 0 : quantityList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()).get(0));
        }
        return decimalToInt(loadingChargesMap.getOrDefault(availableQuantity, new LoadingCharges()).getCharges());
    }

    private List<Integer> getPossibleQuantities(List<LoadingCharges> loadingChargesList, Integer productQuantity) {
        List<Integer> quantityList = new ArrayList<>();
        loadingChargesList.forEach(lc -> {
            if (lc.getQuantity() >= productQuantity) {
                quantityList.add(lc.getQuantity());
            }
        });
        return quantityList;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Checkout Buy")
    @PostMapping("/checkout/buy")
    public ResponseEntity<OrderPayloadResponseModel> orderProducts(@ApiParam("Order Request") @RequestBody OrderRequest orderRequest) throws InventoryException, IOException, RazorpayException {
        OrderPayloadResponseModel orderPayloadResponseModel = new OrderPayloadResponseModel();

        User user = mobileUserService.findById(orderRequest.getUserId());
        //setLoadingAndTranCharge(orderRequest);
        LOG.info("Order request detail - {}", orderRequest.toString());
        Order order = orderService.saveOrder(orderMapper.mapExtra(orderRequest, user));
        OrderFulfillment orderFulfillment = orderFulfillmentService.saveOrderFulfillment(orderFulfillmentMapper.map(order));
        productOrderService.saveProdOrders(productOrderMapper.map(orderRequest.getProducts(), orderFulfillment));
        if (orderRequest.isBuyFromCart()) {
            userCartService.removeAllUserCart(orderRequest.getUserId());
        }
        if (orderRequest.getPaymentProvider().equals(RAZORPAY.getStatus())) {
            String razorPayOrderId = razorpayAPI.createOrder(order);
            order.setGatewayOrderId(razorPayOrderId);
            order.setPaymentStatus(NOT_PAID.getStatus());
            orderService.updateOrder(order);
            RazorpayPayloadResponseModel razorpayPayloadResponseModel = generateRazorpayPayloadResponse(orderRequest, order);
            return new ResponseEntity<>(generatePaymentPayloadResponse(order, new PaytmPayloadResponseModel(), razorpayPayloadResponseModel), HttpStatus.OK);
        }
        String requestBody = generateRequestBody(orderRequest, order.getOrderNo());
        InitTxResponse initTxResponse = paytmService.initiateTxn(requestBody, paytmMID, order.getOrderNo());
        if (SUCCESS == InitTransactionStatus.statusMatch(initTxResponse.getBody().getResultInfo().getResultStatus())) {
            boolean verifyChecksum = verifyChecksum(getResponseBody(initTxResponse.getResponseString()), paytmMKey, initTxResponse.getHead().getSignature());
            if (verifyChecksum) {
                PaytmPayloadResponseModel payload = generatePayloadResponse(orderRequest, order.getOrderRef(), order.getOrderNo(), initTxResponse.getBody().getTxnToken());
                return new ResponseEntity<>(generatePaymentPayloadResponse(order, payload, new RazorpayPayloadResponseModel()), HttpStatus.OK);
            }
            LOG.info("Checksum verification failed - {}", this.getClass().getSimpleName());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private OrderPayloadResponseModel generatePaymentPayloadResponse(Order order, PaytmPayloadResponseModel paytmPayloadResponseModel, RazorpayPayloadResponseModel razorpayPayloadResponseModel) {
        OrderPayloadResponseModel orderPayloadResponseModel = new OrderPayloadResponseModel();
        orderPayloadResponseModel.setPaymentProvider(order.getPaymentProvider());
        orderPayloadResponseModel.setPaytmPayloadResponseModel(paytmPayloadResponseModel);
        orderPayloadResponseModel.setRazorpayPayloadResponseModel(razorpayPayloadResponseModel);
        LOG.info("payload response for razor pay - {}", orderPayloadResponseModel.toString());

        return orderPayloadResponseModel;
    }

    private PaytmPayloadResponseModel generatePayloadResponse(OrderRequest orderRequest, Long orderId, String orderNo, String txnToken) {
        PaytmPayloadResponseModel payload = new PaytmPayloadResponseModel();
        payload.setMid(paytmMID);
        payload.setOrderId(String.valueOf(orderId));
        payload.setOrderNo(orderNo);
        payload.setUserId(String.valueOf(orderRequest.getUserId()));
        payload.setMobile(orderRequest.getMobile());
        payload.setEmail(orderRequest.getEmail());
        payload.setWebsite(paytmWebSite);
        payload.setChannelId(paytmChannel);
        payload.setIndustry(paytmIndustry);
        payload.setAmount(String.valueOf(orderRequest.getTotalCharges()));
        payload.setCallBackUrl(String.format(callbackUrl, orderNo));
        payload.setTxnToken(txnToken);
        payload.setPaymentUrl(paymentUrl);
        return payload;
    }

    private RazorpayPayloadResponseModel generateRazorpayPayloadResponse(OrderRequest orderRequest, Order order) throws InventoryException {
        RazorpayPayloadResponseModel payload = new RazorpayPayloadResponseModel();
        payload.setOrderId(String.valueOf(order.getOrderRef()));
        payload.setOrderNo(order.getOrderNo());
        payload.setKey(apiKey);
        payload.setAmount(String.valueOf(order.getPaidAmount()));
        payload.setCurrency(INDIAN_CURRENCY);
        payload.setCompanyName(companyName);
        payload.setCompanyLogo(companyLogo);
        payload.setRazorOrderId(order.getGatewayOrderId());

        User user = mobileUserService.findById(orderRequest.getUserId());

        Prefill prefill = new Prefill();
        prefill.setName(user.getFullName());
        prefill.setEmail(user.getEmail());
        prefill.setContact(user.getMobile());
        payload.setPrefill(prefill);
        return payload;
    }

    private String generateRequestBody(OrderRequest orderRequest, String orderNo) throws InventoryException {
        JSONObject body = getBody(orderRequest, orderNo);
        JSONObject paytmParams = new JSONObject();
        paytmParams.put("head", getHead(body));
        paytmParams.put("body", body);
        return paytmParams.toString();
    }

    private JSONObject getHead(JSONObject body) throws InventoryException {
        JSONObject head = new JSONObject();
        head.put("channelId", paytmChannel);
        head.put("requestTimestamp", String.valueOf(Instant.now().toEpochMilli()));
        head.put("signature", generateChecksum(body.toString(), paytmMKey));
        return head;
    }

    private JSONObject getBody(OrderRequest orderRequest, String orderNo) {
        JSONObject body = new JSONObject();
        body.put("requestType", "Payment");
        body.put("mid", paytmMID);
        body.put("websiteName", paytmWebSite);
        body.put("orderId", orderNo);
        body.put("callbackUrl", String.format(callbackUrl, orderNo));

        JSONObject txnAmount = new JSONObject();
        txnAmount.put("value", String.valueOf(orderRequest.getTotalCharges()));
        txnAmount.put("currency", "INR");
        body.put("txnAmount", txnAmount);

        JSONObject userInfo = new JSONObject();
        userInfo.put("custId", orderRequest.getUserId());
        userInfo.put("mobile", orderRequest.getMobile());
        userInfo.put("email", orderRequest.getEmail());
        body.put("userInfo", userInfo);
        return body;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Process product price to checkout by user")
    @PostMapping("/checkout/process/price")
    public ResponseEntity<CheckoutPriceResponse> processPriceByQuantityAndVendor(@ApiParam("Order Request") @RequestBody CheckoutPriceRequest priceRequest) throws InventoryException {
        LOG.info("Update price by quantity and vendor - {}", this.getClass().getSimpleName());
        Long userId = mobileUserService.findByMobile(getCurrentUser()).getId();
        Optional<Address> optionalAddress = addressService.findDefaultAddressByUserAndCategory(userId, AddressCategory.DELIVERY_ADDRESS.getStatus());
        String deliveryPincode = optionalAddress.isPresent() ? optionalAddress.get().getPostalCode() : DEFAULT_DELIVERY_PINCODE;
        CheckoutPriceResponse priceResponse = new CheckoutPriceResponse();
        ProductVendor productVendor = productVendorService.findProductVendor(priceRequest.getProdVendorId());
        Integer toAddressDistance = getDistanceByPincodeRange(productVendor.getPinCode(), deliveryPincode);
        priceResponse.setProductPrice(formatIntoTwoPrecision(productVendor.getSaleRate().multiply(new BigDecimal(priceRequest.getQuantity()))));
        priceResponse.setLoadingCharge(formatIntoTwoPrecision(processLoadingCharge(priceRequest.getProductId(), priceRequest.getQuantity())));
        priceResponse.setTransCharge(formatIntoTwoPrecision(findTransportCharge(priceRequest.getTransGroup(), priceRequest.getQuantity(), toAddressDistance)));
        priceResponse.setProductId(String.valueOf(priceRequest.getProductId()));
        priceResponse.setProductVendorId(String.valueOf(priceRequest.getProdVendorId()));
        priceResponse.setQuantity(String.valueOf(priceRequest.getQuantity()));
        priceResponse.setTransGroup(priceRequest.getTransGroup());
        return new ResponseEntity<>(priceResponse, HttpStatus.OK);
    }

    private BigDecimal processLoadingCharge(Long productId, Integer quantity) {
        Optional<Tuple<BigDecimal, Integer>> optionalCharge = loadingChargesService.availableQuantityListByProduct(quantity, productId);
        return (optionalCharge.get().getA() != null ? optionalCharge.get().getA() : new BigDecimal(0));
    }

    private BigDecimal findTransportCharge(String transGroup, Integer productQuantity, Integer distance) {
        // TODO: Find the transport charge with the pincode...
        List<Transport> transportList = transportService.transportListByTransGroup(transGroup);
        return transportChargeCalculationMethod(transportList, productQuantity, distance);
    }

    private void mapTransportCharges(List<ProductCharges> productChargesList, List<ProductVendor> productVendorList, Map<String, List<Transport>> transportChargesMap, String destinationPincode) {
        Map<Long, List<ProductCharges>> productChargeVendorMap = productChargesList.stream().collect(Collectors.groupingBy(ProductCharges::getVendorId));
        Map<Long, List<ProductVendor>> productVendorMap = productVendorList.stream().collect(Collectors.groupingBy(ProductVendor::getProductId));
        productChargesList.forEach(p -> {
            Map<Long, String> vendorSourcePinCode = productVendorMap.get(p.getProductId()).stream().collect(Collectors.toMap(ProductVendor::getVendorId, ProductVendor::getPinCode));
            Integer toAddressDistance = getDistanceByPincodeRange(vendorSourcePinCode.get(p.getVendorId()), destinationPincode);
            Integer qty = productChargeVendorMap.get(p.getVendorId()).stream().filter(pc -> pc.getTransGroup()
                    .equalsIgnoreCase(p.getTransGroup())).mapToInt(ProductCharges::getQuantity).sum();
            BigDecimal clubbedTransportCharge = transportChargeCalculationMethod(transportChargesMap.getOrDefault(p.getTransGroup(), new ArrayList<>()), qty, toAddressDistance);
            p.setTransportCharge(formatIntoTwoPrecision(clubbedTransportCharge.divide(new BigDecimal(qty), 4, RoundingMode.CEILING).multiply(new BigDecimal(p.getQuantity()))));
        });
    }
}
