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

import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.reftype.DeliveryCharge;
import com.makinus.unitedsupplies.common.data.reftype.InvoiceHeader;
import com.makinus.unitedsupplies.common.data.reftype.PaymentType;
import com.makinus.unitedsupplies.common.data.service.ServiceCharges.ServiceChargesService;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.orderfulfillment.OrderFulfillmentService;
import com.makinus.unitedsupplies.common.data.service.prodorder.ProductOrderService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.utils.FTLTemplates;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.data.service.ConvertToWords.convertToINRCurrency;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static com.makinus.unitedsupplies.common.utils.AppUtils.utcDateForDDMMYYYY;

/**
 * @author ibrahim
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "Order API Controller")
public class AttachmentController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ServiceChargesService serviceChargesService;

    @Autowired
    private Configuration freemarkerConfig;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Download Product Receipt")
    @GetMapping(value = "/product/receipt/download")
    public void DownloadProductStatusList(HttpServletResponse response, @ApiParam("Order Ref") @RequestParam String orderRef, @ApiParam("Order Fulfillment Id") @RequestParam String fulfillmentId) throws UnitedSuppliesException {
        LOG.info("Download receipt for product invoice - {}", this.getClass().getSimpleName());
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(orderRef));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            OrderFulfillment orderFulfillment = orderFulfillmentService.findOrderFulfillmentById(Long.valueOf(fulfillmentId));
            LOG.info("OrderFulfillment - Attachment Controller - {}", orderFulfillment.toString());
            orderFulfillment.setOrderTotalInWords(convertToINRCurrency(orderFulfillment.getOrderTotal()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%d.pdf\"", "ProductReceipt", getInstant().getTime()));
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.USER_PRODUCT_RECEIPT_FTL, getProductInvoiceDetails(order, orderFulfillment));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException("IO Exception occurred while downloading product receipt" + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Download Receipt for order")
    @GetMapping("/order/receipt/download")
    public void orderReceiptDownload(HttpServletResponse response, @ApiParam("Order Ref") @RequestParam String orderRef) throws UnitedSuppliesException {
        LOG.info("Download Receipt for order - Order Controller");
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(orderRef));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%d.pdf\"", "OrderReceipt", getInstant().getTime()));
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.USER_ORDER_RECEIPT_FTL, getOrderDetails(order));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException("IO Exception occurred while downloading order receipt" + e.getMessage());
        }
    }

    private Map<String, Object> getOrderDetails(Order order) throws UnitedSuppliesException {
        Map<String, Object> model = new HashMap<>();
        LOG.info("Open Approve Confirmation page - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(order.getOrderRef());
        getProductNames(productOrders);
        model.put("usLogo", getUSLogoAsBase64());
        model.put("orderDate", utcDateForDDMMYYYY(order.getOrderDate()));
        model.put("order", order);
        model.put("productOrders", productOrders);
        model.put("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.put("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        return model;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Download Customer Service Receipt")
    @GetMapping(value = "/customer/receipt/download")
    public void DownloadServiceCustomerList(HttpServletResponse response, @ApiParam("Order Ref") @RequestParam String orderRef) throws UnitedSuppliesException {
        LOG.info("Download Receipt for Customer - Orders Controller - {}", this.getClass().getSimpleName());
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(orderRef));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s_%d.pdf\"", "CustomerReceipt", getInstant().getTime()));
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.USER_SERVICE_CUSTOMER_RECEIPT_FTL, getServiceDetails(order));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException("IO Exception occurred while downloading Service Customer receipt" + e.getMessage());
        }
    }

    private Map<String, Object> getServiceDetails(Order order) throws UnitedSuppliesException, ParseException {
        Map<String, Object> model = new HashMap<>();
        LOG.info("Open Approve Confirmation page - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(order.getId());
        Optional<ServiceCharge> serviceChargeOptional = serviceChargesService.allServiceCharges().stream().findFirst(); //TODO: Ensure service from charge table or order table
        getProductNameAndVendorName(productOrders);
        model.put("usLogo", getUSLogoAsBase64());
        model.put("signLogo", getSignatureAsBase64());
        model.put("orderNo", order.getOrderNo());
        model.put("invoiceHeader", InvoiceHeader.ORIGINAL_FOR_RECIPIENT.getDisplay());
        model.put("orderDate", utcDateForDDMMYYYY(order.getOrderDate()));
        model.put("custServInvoiceDate", utcDateForDDMMYYYY(order.getCustServInvoiceDate()));
        model.put("order", order);
        model.put("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.put("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        String serviceCharge = "0";
        if (order.getServiceCharges() != null) {
            model.put("customerServiceChargeAmount", order.getServiceCharges().intValue());
            model.put("customerAmountInWords", convertToINRCurrency(order.getServiceCharges()));
        }
        if (order.getServiceCharges() != null) {
            model.put("sellerServiceChargeAmount", order.getServiceCharges().intValue());
            model.put("sellerAmountInWords", convertToINRCurrency(new BigDecimal(String.valueOf(order.getServiceCharges()))));
        }
        return model;
    }

    private Map<String, Object> getProductInvoiceDetails(Order order, OrderFulfillment orderFulfillment) throws UnitedSuppliesException, IOException, TemplateModelException {
        LOG.info("getProductInvoiceDetails - {}", this.getClass().getSimpleName());
        Map<String, Object> model = new HashMap<>();
        List<ProductOrder> productOrders = productOrderService.getProductListByFulfillment(orderFulfillment.getId());
        ProductVendor productVendor = productVendorService.findProductVendor(orderFulfillment.getProdVendorId());
        Vendor vendor = vendorService.findVendor(productVendor.getVendorId());
        getProductNameAndVendorName(productOrders);
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel myUtilsWrapper = (TemplateHashModel) staticModels.get("com.makinus.unitedsupplies.common.data.service.ConvertToWords");
        model.put("Utils", myUtilsWrapper);
        model.put("usLogo", getUSLogoAsBase64());
        model.put("orderDate", utcDateForDDMMYYYY(order.getOrderDate()));
        model.put("productInvoiceDate", utcDateForDDMMYYYY(orderFulfillment.getProductInvoiceDate()));
        model.put("order", order);
        model.put("invoiceHeader", InvoiceHeader.ORIGINAL_FOR_RECIPIENT.getDisplay());
        model.put("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.put("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        model.put("orderFulfillment", orderFulfillment);
        model.put("productOrders", productOrders);
        model.put("vendorSign", vendor.getVendorSignature());
        return model;
    }

    private void getProductNameAndVendorName(List<ProductOrder> productOrderSet) {
        Map<Long, Product> productMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        Map<Long, ProductVendor> productVendorMap = productVendorService.productVendorList().stream().collect(Collectors.toMap(ProductVendor::getId, Function.identity()));
        Map<Long, Vendor> vendorMap = vendorService.vendorList().stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        productOrderSet.forEach(p -> {
            p.setProductName(productMap.getOrDefault(p.getProductId(), new Product()).getProductName());
            p.setProductCode(productMap.getOrDefault(p.getProductId(), new Product()).getProductCode());
            p.setVendorName(vendorMap.getOrDefault(productVendorMap.getOrDefault(p.getProdVendorId(), new ProductVendor()).getVendorId(), new Vendor()).getVendorName());
            p.setVendorCode(vendorMap.getOrDefault(productVendorMap.getOrDefault(p.getProdVendorId(), new ProductVendor()).getVendorId(), new Vendor()).getVendorCode());
            p.setPanNo(vendorMap.getOrDefault(productVendorMap.getOrDefault(p.getProdVendorId(), new ProductVendor()).getVendorId(), new Vendor()).getPanNo());
            p.setGstNo(vendorMap.getOrDefault(productVendorMap.getOrDefault(p.getProdVendorId(), new ProductVendor()).getVendorId(), new Vendor()).getGstNo());
        });
    }

    private void getProductNames(List<ProductOrder> productOrderSet) {
        Map<Long, Product> productMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        productOrderSet.forEach(p -> p.setProductName(productMap.get(p.getProductId()).getProductName()));
    }

    private String setDistanceBetweenLoc(Short distance) {
        return (distance != null ? DeliveryCharge.statusMatch(distance.intValue()).getDisplay() : null);
    }

    private String setPaymentType(String payment) {
        return (payment != null ? PaymentType.statusMatch(payment).getDisplay() : null);
    }

    private ITextRenderer renderPdfForAttachment(Configuration freemarkerConfig, String templateName, Map<String, Object> model) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        String htmlString = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        final org.jsoup.nodes.Document document = Jsoup.parse(htmlString);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(document.html());
        renderer.layout();
        return renderer;
    }

    static String getUSLogoAsBase64() throws UnitedSuppliesException {
        try {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(Files.readAllBytes((ResourceUtils.getFile("classpath:static/img/Splash.png").toPath())));
        } catch (Exception e) {
            throw new UnitedSuppliesException(e.getMessage());
        }
    }

    static String getSignatureAsBase64() throws UnitedSuppliesException {
        try {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(Files.readAllBytes((ResourceUtils.getFile("classpath:static/img/US_Signature.jpeg").toPath())));
        } catch (Exception e) {
            throw new UnitedSuppliesException(e.getMessage());
        }
    }
}
