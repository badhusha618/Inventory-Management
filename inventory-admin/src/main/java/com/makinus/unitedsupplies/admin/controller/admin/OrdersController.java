/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.admin;

import com.makinus.unitedsupplies.admin.data.forms.FulfillmentForm;
import com.makinus.unitedsupplies.admin.data.forms.ProductInvoiceForm;
import com.makinus.unitedsupplies.admin.data.forms.ProductVendorForm;
import com.makinus.unitedsupplies.admin.data.forms.ServiceInvoiceForm;
import com.makinus.unitedsupplies.admin.data.mapping.FulfillmentMapper;
import com.makinus.unitedsupplies.admin.data.service.excel.GenericWriter;
import com.makinus.unitedsupplies.admin.data.service.excel.OrderExcelDTO;
import com.makinus.unitedsupplies.admin.data.service.excel.ProductOrderExcelDTO;
import com.makinus.unitedsupplies.common.data.dao.InvoiceProdSeqRepository;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.form.OrderFilterForm;
import com.makinus.unitedsupplies.common.data.form.ProductOrderFilterForm;
import com.makinus.unitedsupplies.common.data.reftype.*;
import com.makinus.unitedsupplies.common.data.service.ServiceCharges.ServiceChargesService;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.invoice.settings.InvoiceSettingsService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.orderfulfillment.OrderFulfillmentService;
import com.makinus.unitedsupplies.common.data.service.prodorder.ProductOrderService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.transport.TransportService;
import com.makinus.unitedsupplies.common.data.service.unit.UnitService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import com.makinus.unitedsupplies.common.utils.FTLTemplates;
import freemarker.ext.beans.BeansWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import freemarker.template.*;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.data.service.ConvertToWords.convertToINRCurrency;
import static com.makinus.unitedsupplies.common.utils.ApiUtils.FORWARD_SLASH;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author ammar
 */
@Controller
public class OrdersController {

    private final Logger LOG = LogManager.getLogger(OrdersController.class);

    private static final String LIST_ORDERS_PAGE = "dashboard/order/order-list";
    private static final String ORDER_FULFILLMENT_PAGE = "dashboard/order/fulfillment";
    private static final String LIST_ORDER_PRODUCTS_PAGE = "dashboard/order/order-product-list";
    private static final String LIST_ORDERS_PRODUCTWISE_PAGE = "dashboard/order/products";
    private static final String ADD_SELLER_INPUT_PAGE = "dashboard/order/seller-invoice";
    private static final String ADD_CUSTOMER_INPUT_PAGE = "dashboard/order/customer-invoice";
    private static final String ADD_PRODUCT_INPUT_PAGE = "dashboard/order/product-invoice";

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ServiceChargesService serviceChargesService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    @Qualifier("FulfillmentMapper")
    private FulfillmentMapper fulfillmentMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private InvoiceSettingsService invoiceSettingsService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private InvoiceProdSeqRepository invoiceProdSeqRepository;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    @Qualifier("OrderExcelWriter")
    private GenericWriter<List<OrderExcelDTO>> orderExcelWriter;

    @Autowired
    @Qualifier("ProductOrderExcelWriter")
    private GenericWriter<List<ProductOrderExcelDTO>> productOrderExcelWriter;


    @ModelAttribute("zone")
    public TimeZone injectTimeZone(TimeZone serverZone, @CookieValue(value = "us_user_timezone", required = false) String zoneID) {
        LOG.info("Setting Zone in model attribute - {}", this.getClass().getSimpleName());
        return isNotEmpty(zoneID) ? TimeZone.getTimeZone(zoneID) : serverZone;
    }

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream()
                .collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = {"/order/products.mk"})
    public String listProductOrders(ModelMap model, @ModelAttribute("productOrderList") ArrayList<ProductOrder> productOrders, @ModelAttribute("productOrderFilterForm") ProductOrderFilterForm productOrderFilterForm, @ModelAttribute("fromSearch") String fromSearch) {
        LOG.info("Open User Product Orders page - {}", this.getClass().getSimpleName());
        if (StringUtils.isNotEmpty(fromSearch)) {
            model.addAttribute("productOrderList", productOrders);
            model.addAttribute("productOrderFilterForm", productOrderFilterForm);
        } else {
            List<ProductOrder> productOrderList = productOrderService.productOrdersList();
            model.addAttribute("productOrderList", productOrderList);
            model.addAttribute("productOrderFilterForm", new ProductOrderFilterForm());
        }
        model.addAttribute("orderListMap", orderService.ordersList().stream().collect(Collectors.toMap(Order::getOrderRef, Function.identity())));
        model.addAttribute("fulfillmentMap", orderFulfillmentService.getAllFulfillments().stream().collect(Collectors.toMap(OrderFulfillment::getId, Function.identity())));
        model.addAttribute("productMap", productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity())));
        model.addAttribute("vendorMap", vendorService.vendorList().stream().collect(Collectors.toMap(Vendor::getId, Function.identity())));
        return LIST_ORDERS_PRODUCTWISE_PAGE;
    }

    @PostMapping(value = {"/order/products/search.mk"})
    public String orderedProductSearch(@ModelAttribute("productOrderFilterForm") ProductOrderFilterForm productOrderFilterForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Search Product Orders - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrders = productOrderService.filterProductOrder(productOrderFilterForm).stream().sorted(Comparator.comparing(ProductOrder::getOrderRef).reversed()).collect(Collectors.toList());
        redirectAttrs.addFlashAttribute("productOrderList", productOrders);
        redirectAttrs.addFlashAttribute("productOrderFilterForm", productOrderFilterForm);
        redirectAttrs.addFlashAttribute("fromSearch", Boolean.TRUE.toString());
        return "redirect:/order/products.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = {"/orders.mk"})
    public String listOrders(ModelMap model, @ModelAttribute("orderList") ArrayList<Order> orders, @ModelAttribute("orderFilterForm") OrderFilterForm orderFilterForm, @ModelAttribute("fromSearch") String fromSearch) {
        LOG.info("Open User Orders page -{}", this.getClass().getSimpleName());
        model.addAttribute("prodOrderStatus", Arrays.stream(ProdOrderStatus.values()).collect(Collectors.toMap(ProdOrderStatus::getStatus, ProdOrderStatus::getDisplay)));
        model.addAttribute("paymentType", Arrays.stream(PaymentType.values()).collect(Collectors.toMap(PaymentType::getStatus, PaymentType::getDisplay)));
        model.addAttribute("orderList", new ArrayList<>());
        if (StringUtils.isNotEmpty(fromSearch)) {
            model.addAttribute("orderList", orders);
            model.addAttribute("orderFilterForm", orderFilterForm);
        } else {
            List<Order> orderList = orderService.ordersList().stream().sorted(Comparator.comparing(Order::getCreatedDate).reversed()).collect(Collectors.toList());
            model.addAttribute("orderList", orderList);
            model.addAttribute("orderFilterForm", new OrderFilterForm());
        }
        return LIST_ORDERS_PAGE;
    }

    @PostMapping(value = {"/orders/search.mk"})
    public String orderSearch(@ModelAttribute("orderFilterForm") OrderFilterForm orderFilterForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Search Orders - {}", this.getClass().getSimpleName());
        List<Order> orderList = orderService.filterOrder(orderFilterForm).stream().sorted(Comparator.comparing(Order::getCreatedDate).reversed()).collect(Collectors.toList());
        redirectAttrs.addFlashAttribute("orderList", orderList);
        redirectAttrs.addFlashAttribute("orderFilterForm", orderFilterForm);
        redirectAttrs.addFlashAttribute("fromSearch", Boolean.TRUE.toString());
        return "redirect:/orders.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/orders/{id}/product.mk")
    public String listOrdersByProduct(ModelMap model, @PathVariable("id") Long productId) {
        LOG.info("View product orders - {}", this.getClass().getSimpleName());
        List<Order> ordersList = orderService.ordersList(); // TODO: Update this query to be list orders based on products
        model.addAttribute("ordersList", ordersList);
        model.addAttribute("prodOrderStatus", Arrays.stream(ProdOrderStatus.values()).collect(Collectors.toMap(ProdOrderStatus::getStatus, ProdOrderStatus::getDisplay)));
        model.addAttribute("paymentType", Arrays.stream(PaymentType.values()).collect(Collectors.toMap(PaymentType::getStatus, PaymentType::getDisplay)));
        return LIST_ORDERS_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/orders/{orderRef}/product-details.mk")
    public String listProductOrders(ModelMap model, @PathVariable Long orderRef, @ModelAttribute("fromPreview") String fromPreview, @ModelAttribute("previewFulfillmentForm") FulfillmentForm previewFulfillmentForm) throws UnitedSuppliesException {
        Map<Long, Product> productMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        Map<Long, Vendor> vendorMap = vendorService.vendorList().stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        Map<Long, OrderFulfillment> orderFulfillmentMap = orderFulfillmentService.getOrderFulfillmentListByOrder(orderRef).stream().collect(Collectors.toMap(OrderFulfillment::getId, Function.identity()));
        List<ProductVendor> prodVendorList = productVendorService.productVendorList();
        Map<Long, ProductVendor> prodVendorMap = prodVendorList.stream().collect(Collectors.toMap(ProductVendor::getId, Function.identity()));
        model.addAttribute("productMap", productMap);
        model.addAttribute("vendorMap", vendorMap);
        model.addAttribute("prodVendorMap", prodVendorMap);
        model.addAttribute("orderFulfillmentMap", orderFulfillmentMap);
        if (StringUtils.isNotEmpty(fromPreview)) {
            model.addAttribute("productOrderList", productOrderService.getProductOrderListByIds(previewFulfillmentForm.getProdOrderIds().stream().map(Long::valueOf).collect(Collectors.toList())));
            model.addAttribute("previewFulfillmentForm", previewFulfillmentForm);
            return ORDER_FULFILLMENT_PAGE;
        }
        LOG.info("View Order Products from Dashboard - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(orderRef);
        Map<Long, List<ProductVendor>> prodToProdVendorMap = prodVendorList.stream().collect(Collectors.groupingBy(ProductVendor::getProductId));
        Order order = orderService.findOrderByOrderRef(productOrders.get(0).getOrderRef());
        model.addAttribute("productOrders", productOrders);
        model.addAttribute("order", order);
        model.addAttribute("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.addAttribute("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        model.addAttribute("productMap", productMap);
        model.addAttribute("vendorMap", vendorMap);
        model.addAttribute("prodToProdVendorMap", prodToProdVendorMap);
        model.addAttribute("productVendorForm", new ProductVendorForm());
        FulfillmentForm fulfillmentForm = new FulfillmentForm();
        fulfillmentForm.setOrderRef(String.valueOf(orderRef));
        model.addAttribute("fulfillmentForm", fulfillmentForm);
        return LIST_ORDER_PRODUCTS_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/order/fulfillment/{stage}.mk")
    public String orderFulfillment(@PathVariable("stage") String stage, @ModelAttribute("fulfillmentForm") FulfillmentForm fulfillmentForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Order fulfillment Page - {}", this.getClass().getSimpleName());
        if (stage != null && stage.equals(OrderFulfillmentStatus.PREVIEW.getStatus())) {
            String fromPreview = YNStatus.YES.getStatus();
            redirectAttrs.addFlashAttribute("previewFulfillmentForm", fulfillmentForm);
            redirectAttrs.addFlashAttribute("fromPreview", fromPreview);
            return "redirect:/orders/" + fulfillmentForm.getOrderRef() + "/product-details.mk";
        }
        OrderFulfillment orderFulfillment = orderFulfillmentService.saveOrderFulfillment(fulfillmentMapper.map(fulfillmentForm));
        productOrderService.updateProductOrderFulfillmentIds(fulfillmentForm.getProdOrderIds().stream().map(Long::valueOf).collect(Collectors.toList()), orderFulfillment.getId());
        orderService.orderDetailsUpdated(orderFulfillment.getOrderRef());
        return "redirect:/orders/" + fulfillmentForm.getOrderRef() + "/product-details.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/order.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeOrder(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Order from Dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Order removedOrder = orderService.removeOrder(Long.valueOf(id));
            LOG.info("Order is removed? {}", (removedOrder != null && removedOrder.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (UnitedSuppliesException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/order/prod/{id}/{deliveryPinCode}/vendor/change.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> updateProductVendor(HttpServletRequest request, @PathVariable String id, @PathVariable String deliveryPinCode, @ModelAttribute ProductVendorForm productVendorForm) {
        LOG.info("Update Product Order vendor from Dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            if (StringUtils.isNotEmpty(id)) {

                ProductVendor productVendor = productVendorService.findProductVendor(Long.valueOf(productVendorForm.getVendorId()));
                Vendor vendor = vendorService.findVendor(productVendor.getVendorId());
                ProductOrder oldProductOrder = productOrderService.findProductOrder(Long.valueOf(id));
                BigDecimal transportCharge = findTransportCharge(oldProductOrder.getTransGroup(), oldProductOrder.getProQuantity(), AppUtils.getDistanceByPincodeRange(productVendor.getPinCode(), deliveryPinCode)); // TODO: Calculate transport charge based on pincode
                orderService.updateOrdersBasedVendorAllocation(oldProductOrder, productVendor.getSaleRate(), transportCharge, productVendorForm.getVendorId());
                orderFulfillmentService.updateOrderFulfillmentBasedVendorAllocation(oldProductOrder, productVendor.getSaleRate(), transportCharge);
                ProductOrder updatedProductOrder = productOrderService.updateProductOrderVendor(Long.valueOf(id), Long.valueOf(productVendorForm.getVendorId()), vendor, productVendor.getSaleRate(), transportCharge);

                List<ProductOrder> productOrderList = productOrderService.getProductListByOrderRef(oldProductOrder.getOrderRef()).stream()
                        .filter(po -> po.getFulfillmentStatus().equalsIgnoreCase(YNStatus.NO.getStatus()))
                        .collect(Collectors.toList());
                Map<String, List<ProductOrder>> productOrderMapByTransGroup = productOrderList.stream().collect(Collectors.groupingBy(ProductOrder::getTransGroup));
                List<ProductVendor> productVendors =  productVendorService.productVendorList();
                Map<Long, Long> VendorIdMap = productVendors.stream().collect(Collectors.toMap(ProductVendor::getId, ProductVendor::getVendorId));
                productOrderList.forEach( pol -> {
                    Integer qty = productOrderMapByTransGroup.getOrDefault(pol.getTransGroup(),new ArrayList<>()).stream()
                            .filter(pl -> VendorIdMap.get(pl.getProdVendorId()).equals(VendorIdMap.get(pol.getProdVendorId()))).mapToInt(ProductOrder::getProQuantity).sum();

                    BigDecimal clubbedTransportCharge = findTransportCharge(pol.getTransGroup(), qty, AppUtils.getDistanceByPincodeRange(pol.getPinCode(), deliveryPinCode));
                    pol.setTransportCharges(clubbedTransportCharge.divide(new BigDecimal(qty), 2, RoundingMode.CEILING).multiply(new BigDecimal(pol.getProQuantity())));

                });
                productOrderService.saveProdOrders(productOrderList);
                LOG.info("Vendor is updated {}", updatedProductOrder != null);
                map.put("response", Boolean.TRUE);
            }
        } catch (UnitedSuppliesException usm) {
            map.put("response", Boolean.FALSE);
        }
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/order/status/change.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> changeOrderStatus(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Action on Order Status from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("msg", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("msg", "cannot be empty");
            return map;
        }
        OrderStatus orderStatus = OrderStatus.statusMatch(value);
        Order order = orderService.updateOrderStatus(Long.valueOf(pk), orderStatus);
        map.put("status", "success");
        map.put("orderRef", String.valueOf(order.getOrderRef()));
        map.put("updatedBy", order.getUpdatedBy());
        map.put("updatedDate", utcDateForMMMMDDYYYYHHMM(order.getUpdatedDate()));
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/order/status/list.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> loadOrderStatusList(HttpServletResponse response) {
        LOG.info("Load Order Status List from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        return Arrays.stream(OrderStatus.values()).collect(Collectors.toMap(OrderStatus::getStatus, OrderStatus::getDisplay));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/payment/type/change.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> changePaymentType(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Action on Payment Type from dashboard -{}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("msg", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("msg", "cannot be empty");
            return map;
        }
        PaymentType paymentType = PaymentType.statusMatch(value);
        Order order = orderService.updatePaymentType(Long.valueOf(pk), paymentType);
        map.put("orderRef", String.valueOf(order.getOrderRef()));
        map.put("status", "success");
        map.put("paidAmount", order.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getStatus()) ? String.valueOf(order.getPaidAmount()) : String.valueOf(order.getOrderTotal()));
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/payment/type/list.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> loadPaymentTypeList(HttpServletResponse response) {
        LOG.info("Load Payment Type List from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        return Arrays.stream(PaymentType.values()).collect(Collectors.toMap(PaymentType::getStatus, PaymentType::getDisplay));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "order/{orderRef}/product/invoice.mk")
    public String productInvoicePage(ModelMap model, @PathVariable("orderRef") String orderRef) throws UnitedSuppliesException {
        LOG.info("Open product invoice page - {}", this.getClass().getSimpleName());
        try {
            ProductInvoiceForm productInvoiceForm = new ProductInvoiceForm();
            productInvoiceForm.setOrderRef(orderRef);
            productInvoiceForm.setInvoiceHeader(InvoiceHeader.ORIGINAL_FOR_RECIPIENT.getStatus());
            List<Long> fulfillmentIds = productOrderService.getProductListByOrderRefAndFulfillment(Long.valueOf(orderRef), YNStatus.YES.getStatus()).stream().map(ProductOrder::getFulfillmentId).collect(Collectors.toList());
            List<OrderFulfillment> orderFulfillmentList = fulfillmentIds.size() > 0 ? orderFulfillmentService.getOrderFulfillmentListByFulfillmentIds(fulfillmentIds) : new ArrayList<>();
            model.addAttribute("orderFulfillmentList", orderFulfillmentList);
            model.addAttribute("orderFulfillmentMap", orderFulfillmentList.stream().collect(Collectors.toMap(OrderFulfillment::getId, Function.identity())));
            model.addAttribute("productInvoiceForm", productInvoiceForm);
            return ADD_PRODUCT_INPUT_PAGE;
        } catch (Exception e) {
            throw new UnitedSuppliesException("Exception occurred while enter product invoice form" + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/order/receipt/{orderRef}/download.mk")
    public void loadOrderStatusList(HttpServletResponse response, @PathVariable String orderRef) throws UnitedSuppliesException {
        LOG.info("Download Receipt for order - Orders Controller - {}", this.getClass().getSimpleName());
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(orderRef));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s_%d.pdf\"", "OrderReceipt", getInstant().getTime()));
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.ADMIN_ORDER_RECEIPT_FTL, getOrderDetails(order));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException("IO Exception occurred while downloading order receipt" + e.getMessage());
        }
    }

    private Map<String, Object> getOrderDetails(Order order) throws UnitedSuppliesException {
        Map<String, Object> model = new HashMap<>();
        LOG.info("Open Approve Confirmation page - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(order.getId());
        getProductNames(productOrders);
        model.put("usLogo", getUSLogoAsBase64());
        model.put("orderDate", utcDateForDDMMYYYY(order.getOrderDate()));
        model.put("order", order);
        model.put("productOrders", productOrders);
        model.put("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.put("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        return model;
    }

    private void getProductNames(List<ProductOrder> productOrderSet) {
        Map<Long, Product> productMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        productOrderSet.forEach(p -> p.setProductName(productMap.get(p.getProductId()).getProductName()));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/product/receipt/download.mk")
    public void loadProductStatusList(HttpServletResponse response, @ModelAttribute("productInvoiceForm") ProductInvoiceForm productInvoiceForm) throws UnitedSuppliesException {
        LOG.info("Download receipt for product invoice - Orders Controller - {}", this.getClass().getSimpleName());
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(productInvoiceForm.getOrderRef()));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            OrderFulfillment orderFulfillment = orderFulfillmentService.findOrderFulfillmentById(Long.valueOf(productInvoiceForm.getOrderFulfillment()));
            orderFulfillment.setOrderTotalInWords(convertToINRCurrency(orderFulfillment.getOrderTotal()));
            productInvoiceForm.setInvoiceNo(orderFulfillment.getProductInvoiceNo());
            productInvoiceForm.setInvoiceDate(utcDateForDDMMYYYY(orderFulfillment.getProductInvoiceDate()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s_%d.pdf\"", "ProductReceipt", getInstant().getTime()));
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.ADMIN_PRODUCT_RECEIPT_FTL, getProductInvoiceDetails(order, orderFulfillment, productInvoiceForm));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException("IO Exception occurred while downloading product receipt" + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/seller/invoice/{ref}.mk")
    public String sellerInvoicePage(ModelMap model, @PathVariable("ref") String ref) {
        LOG.info("Open Seller Invoice page - {}", this.getClass().getSimpleName());
        List<OrderFulfillment> orderFulfillmentList = orderFulfillmentService.getOrderFulfillmentListByOrderRef(Long.valueOf(ref));
        model.addAttribute("orderFulfillmentList", orderFulfillmentList);
        model.addAttribute("orderFulfillmentMap", orderFulfillmentList.stream().collect(Collectors.toMap(OrderFulfillment::getId, Function.identity())));
        model.addAttribute("serviceInvoiceForm", new ServiceInvoiceForm(ref, InvoiceHeader.ORIGINAL_FOR_RECIPIENT.getStatus()));
        return ADD_SELLER_INPUT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/service/seller/download.mk")
    public void loadServiceSellerList(HttpServletResponse response, @ModelAttribute("serviceInvoiceForm") ServiceInvoiceForm serviceInvoiceForm) throws UnitedSuppliesException {
        LOG.info("Download Receipt for seller - Orders Controller - {}", this.getClass().getSimpleName());
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(serviceInvoiceForm.getOrderRef()));
            OrderFulfillment orderByOrderRef = orderFulfillmentService.findOrderFulfillmentById(Long.valueOf(serviceInvoiceForm.getOrderDiv()));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s_%d.pdf\"", "SellerReceipt", getInstant().getTime()));
            serviceInvoiceForm.setInvoiceNo(orderByOrderRef.getSellServInvoiceNo());
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.ADMIN_SERVICE_SELLER_RECEIPT_FTL, getServiceDetails(order, serviceInvoiceForm));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException(
                    "IO Exception occurred while downloading Service Seller receipt" + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/customer/invoice/{ref}.mk")
    public String customerInvoicePage(ModelMap model, @PathVariable("ref") String ref) throws UnitedSuppliesException {
        LOG.info("Open Customer Invoice page - {}", this.getClass().getSimpleName());
        Order order = orderService.findOrderByOrderRef(Long.valueOf(ref));
        ServiceInvoiceForm serviceInvoiceForm = new ServiceInvoiceForm(ref, InvoiceHeader.ORIGINAL_FOR_RECIPIENT.getStatus());
        serviceInvoiceForm.setInvoiceNo(order.getCustServInvoiceNo());
        if (order.getCustServInvoiceDate() != null) {
            serviceInvoiceForm.setInvoiceDate(utcDateForDDMMYYYY(order.getCustServInvoiceDate()));
        } else {
            serviceInvoiceForm.setInvoiceDate(utcDateForDDMMYYYY(getInstant()));
        }
        model.addAttribute("serviceInvoiceForm", serviceInvoiceForm);
        return ADD_CUSTOMER_INPUT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/customer/receipt/download.mk")
    public void loadServiceCustomerList(HttpServletResponse response, @ModelAttribute("serviceInvoiceForm") ServiceInvoiceForm serviceInvoiceForm) throws UnitedSuppliesException {
        LOG.info("Download Receipt for Customer - Orders Controller - {}", this.getClass().getSimpleName());
        try {
            Order order = orderService.findOrderByOrderRef(Long.valueOf(serviceInvoiceForm.getOrderRef()));
            order.setPaymentDisplay(setPaymentType(order.getPaymentType()));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s_%d.pdf\"", "CustomerReceipt", getInstant().getTime()));
            serviceInvoiceForm.setInvoiceNo(order.getCustServInvoiceNo());
            serviceInvoiceForm.setInvoiceDate(utcDateForDDMMYYYY(order.getCustServInvoiceDate()));
            ITextRenderer renderer = renderPdfForAttachment(freemarkerConfig, FTLTemplates.ADMIN_SERVICE_CUSTOMER_RECEIPT_FTL, getServiceDetails(order, serviceInvoiceForm));
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            throw new UnitedSuppliesException("IO Exception occurred while downloading Service Customer receipt" + e.getMessage());
        }
    }

    private Map<String, Object> getServiceDetails(Order order, ServiceInvoiceForm serviceInvoiceForm) throws UnitedSuppliesException, ParseException {
        Map<String, Object> model = new HashMap<>();
        LOG.info("Open Approve Confirmation page - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(order.getId());
        Optional<ServiceCharge> serviceChargeOptional = serviceChargesService.allServiceCharges().stream().findFirst();
        getProductNameAndVendorName(productOrders);
        serviceInvoiceForm.setInvoiceHeaderDisplay(ServiceInvoiceHeader.statusMatch(serviceInvoiceForm.getInvoiceHeader()).getDisplay());
        model.put("usLogo", getUSLogoAsBase64());
        model.put("signLogo", getSignatureAsBase64());
        model.put("orderNo", order.getOrderNo());
        model.put("orderDate", utcDateForDDMMYYYY(order.getOrderDate()));
        model.put("order", order);
        model.put("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.put("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        model.put("serviceInvoiceForm", serviceInvoiceForm);
        if (order.getServiceCharges() != null) {
            model.put("customerServiceChargeAmount", order.getServiceCharges().intValue());
            model.put("customerAmountInWords", convertToINRCurrency(order.getServiceCharges()));
        }
        if (StringUtils.isNotEmpty(serviceInvoiceForm.getOrderDiv())) {
            OrderFulfillment orderFulfillment = orderFulfillmentService.findOrderFulfillmentById(Long.valueOf(serviceInvoiceForm.getOrderDiv()));
            model.put("sellerServiceChargeAmount", Integer.parseInt(orderFulfillment.getSellServInvoiceAmount()));
            model.put("sellerAmountInWords", convertToINRCurrency(new BigDecimal(orderFulfillment.getSellServInvoiceAmount())));
            model.put("sellerServiceInvoiceDate", (utcDateForDDMMYYYY(orderFulfillment.getSellServInvoiceDate())));
            model.put("orderFulfillment", orderFulfillment);
        }
        return model;
    }

    private Map<String, Object> getProductInvoiceDetails(Order order, OrderFulfillment orderFulfillment, ProductInvoiceForm productInvoiceForm) throws UnitedSuppliesException, IOException, TemplateModelException {
        LOG.info("Open Approve Confirmation page - {}", this.getClass().getSimpleName());
        Map<String, Object> model = new HashMap<>();
        List<ProductOrder> productOrders = productOrderService.getProductListByFulfillment(Long.valueOf(productInvoiceForm.getOrderFulfillment()));
        ProductVendor productVendor = productVendorService.findProductVendor(orderFulfillment.getProdVendorId());
        Vendor vendor = vendorService.findVendor(productVendor.getVendorId());
        getProductNameAndVendorName(productOrders);
        productInvoiceForm.setInvoiceHeaderDisplay(InvoiceHeader.statusMatch(productInvoiceForm.getInvoiceHeader()).getDisplay());
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel myUtilsWrapper = (TemplateHashModel) staticModels.get("com.makinus.unitedsupplies.common.data.service.ConvertToWords");
        model.put("Utils", myUtilsWrapper);
        model.put("usLogo", getUSLogoAsBase64());
        model.put("orderDate", utcDateForDDMMYYYY(order.getOrderDate()));
        model.put("order", order);
        model.put("deliveryAddress", addressService.findAddressById(order.getDelAddressId()));
        model.put("billingAddress", addressService.findAddressById(order.getBillAddressId()));
        model.put("orderFulfillment", orderFulfillment);
        model.put("productOrders", productOrders);
        model.put("productInvoiceForm", productInvoiceForm);
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

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/export/orders.mk")
    public void exportOrderListToExcel(@ModelAttribute("orderFilterForm") OrderFilterForm orderFilterForm, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Export Order details in Excel - {}", this.getClass().getSimpleName());
        List<Order> orderList = orderService.filterOrder(orderFilterForm).stream().sorted(Comparator.comparing(Order::getCreatedDate).reversed()).collect(Collectors.toList());

        List<OrderExcelDTO> orderExcelDTOList = new ArrayList<>();
        orderList.forEach(order -> {
            OrderExcelDTO orderExcelDTO = new OrderExcelDTO();
            orderExcelDTO.setId(String.valueOf(order.getId()));
            orderExcelDTO.setOrderDate(order.getOrderDate());
            orderExcelDTO.setOrderNo(order.getOrderNo());
            orderExcelDTO.setSubTotal(order.getSubTotal());
            orderExcelDTO.setTransportCharges(order.getTransportCharges());
            orderExcelDTO.setServiceCharges(order.getServiceCharges());
            orderExcelDTO.setLoadingCharges(order.getLoadingCharges());
            orderExcelDTO.setOrderTotal(order.getOrderTotal());
            orderExcelDTO.setBillAddressRef(longToString(order.getBillAddressId()));
            orderExcelDTO.setDelAddressRef(longToString(order.getDelAddressId()));
            orderExcelDTO.setPaymentType(order.getPaymentType());
            orderExcelDTO.setPaidAmount(order.getPaidAmount());
            orderExcelDTO.setStatus(order.getStatus());
            orderExcelDTOList.add(orderExcelDTO);
        });

        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%d.xls\"", "OrderDetails", getInstant().getTime()));
        response.setCharacterEncoding("UTF-8");
        orderExcelWriter.write(orderExcelDTOList, response);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/prod/order/status/change.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> changeProductOrderStatus(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Action on Product Order Status from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("msg", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("msg", "cannot be empty");
            return map;
        }
        ProdOrderStatus prodOrderStatus = ProdOrderStatus.statusMatch(value);
        ProductOrder productOrder = productOrderService.updateProductOrderStatus(Long.valueOf(pk), prodOrderStatus);
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(productOrder.getOrderRef());
        orderService.updateOrderStatus(productOrder.getOrderRef(), predictOrderStatus(productOrders));
        map.put("status", "success");
        return map;
    }

    private OrderStatus predictOrderStatus(List<ProductOrder> productOrders) {
        List<String> prodOrderStatuses = productOrders.stream().map(ProductOrder::getStatus).collect(Collectors.toList());
        if (isOrderInProgress(prodOrderStatuses)) {
            return OrderStatus.IN_PROGRESS;
        } else if (isNewOrder(prodOrderStatuses) && isOrderCompleted(prodOrderStatuses)) {
            return OrderStatus.IN_PROGRESS;
        } else if (isNewOrder(prodOrderStatuses)) {
            return OrderStatus.NEW;
        } else {
            return OrderStatus.COMPLETED;
        }
    }

    private Boolean isOrderInProgress(List<String> prodOrderStatuses) {
        List<String> inProgressStatuses = new ArrayList<>();
        inProgressStatuses.add(ProdOrderStatus.IN_PROGRESS.getStatus());
        inProgressStatuses.add(ProdOrderStatus.LOADING.getStatus());
        inProgressStatuses.add(ProdOrderStatus.IN_TRANSIT.getStatus());
        return prodOrderStatuses.stream().anyMatch(inProgressStatuses::contains);
    }

    private Boolean isNewOrder(List<String> prodOrderStatuses) {
        return prodOrderStatuses.stream().anyMatch(p -> p.equals(ProdOrderStatus.NEW.getStatus()));

    }

    private Boolean isOrderCompleted(List<String> prodOrderStatuses) {
        List<String> completedStatuses = new ArrayList<>();
        completedStatuses.add(ProdOrderStatus.DELIVERED.getStatus());
        completedStatuses.add(ProdOrderStatus.CANCELLED.getStatus());
        return prodOrderStatuses.stream().anyMatch(completedStatuses::contains);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/prod/order/status/list.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> loadProductOrderStatusList(HttpServletResponse response) {
        LOG.info("Load Order Status List from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        return Arrays.stream(ProdOrderStatus.values()).collect(Collectors.toMap(ProdOrderStatus::getStatus, ProdOrderStatus::getDisplay));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/export/productorders.mk")
    public void exportProductOrderListToExcel(HttpServletResponse response, @ModelAttribute("zone") TimeZone zone) throws UnitedSuppliesException {
        LOG.info("Export Product Order details in Excel - {}", this.getClass().getSimpleName());
        List<ProductOrder> productOrderList = productOrderService.productOrdersList();
        Map<Long, Product> productMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        Map<Long, Vendor> vendorMap = vendorService.vendorList().stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        Map<Long, Order> orderRefMap = orderService.ordersList().stream().collect(Collectors.toMap(Order::getOrderRef, Function.identity()));

        final Product defaultProduct = new Product();
        final Vendor defaultVendor = new Vendor();
        List<ProductOrderExcelDTO> productOrderExcelDTOList = new ArrayList<>();
        productOrderList.forEach(productOrder -> {
            ProductOrderExcelDTO productOrderExcelDTO = new ProductOrderExcelDTO();
            Order order = orderRefMap.getOrDefault(productOrder.getOrderRef(), new Order());
            if (order.getId() != null) {
                // Order fields
                productOrderExcelDTO.setOrderRef(String.valueOf(order.getOrderRef()));
                productOrderExcelDTO.setOrderStatus(ProdOrderStatus.statusMatch(order.getStatus()).getDisplay());
                productOrderExcelDTO.setOrderDate(getLocalTimezoneDate(order.getOrderDate(), zone));

                // Product Order fields
                productOrderExcelDTO.setProQuantity(String.valueOf(productOrder.getProQuantity()));
                productOrderExcelDTO.setProductOrderStatus(ProdOrderStatus.statusMatch(productOrder.getStatus()).getDisplay());
                productOrderExcelDTO.setProductCode(productMap.getOrDefault(productOrder.getProductId(), defaultProduct).getProductCode());
                productOrderExcelDTO.setProductName(productMap.getOrDefault(productOrder.getProductId(), defaultProduct).getProductName());
                productOrderExcelDTO.setProdSaleRate(productOrder.getProdSaleRate().toString());
                productOrderExcelDTO.setTransportCharges(productOrder.getTransportCharges().toString());
                productOrderExcelDTO.setLoadingCharges(productOrder.getLoadingCharges().toString());
                productOrderExcelDTO.setTotalCharges(String.valueOf((productOrder.getProQuantity() * productOrder.getProdSaleRate().intValue()) + productOrder.getLoadingCharges().intValue() + productOrder.getTransportCharges().intValue()));

                // Vendor fields
                productOrderExcelDTO.setVendorNameWithCode(vendorMap.getOrDefault(productOrder.getProductId(), defaultVendor).getVendorCode() + "-" + vendorMap.getOrDefault(productOrder.getProductId(), defaultVendor).getVendorName());
                productOrderExcelDTOList.add(productOrderExcelDTO);
            }
        });

        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%d.xls\"", "ProductDetails", getInstant().getTime()));
        response.setCharacterEncoding("UTF-8");
        productOrderExcelWriter.write(productOrderExcelDTOList, response);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/product/invoice/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingProductInvoiceNumber(HttpServletRequest request) throws UnitedSuppliesException {
        LOG.info("Checking if the invoice number exists - {}", this.getClass().getSimpleName());
        String fulfillmentId = request.getParameter("fulfillmentId").trim();
        if (StringUtils.isNotEmpty(fulfillmentId)) {
            boolean isInvoiceNumberAvailable = orderFulfillmentService.isProductInvoiceAvailable(request.getParameter("invoiceNo").trim(), Long.valueOf(fulfillmentId));
            LOG.info("Invoice number is available? {}", isInvoiceNumberAvailable);
            return !isInvoiceNumberAvailable;
        }
        return true;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/service/invoice/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingServiceInvoiceNumber(HttpServletRequest request) throws UnitedSuppliesException {
        LOG.info("Checking if the invoice number exists - {}", this.getClass().getSimpleName());
        String fulfillmentId = request.getParameter("fulfillmentId").trim();
        if (StringUtils.isNotEmpty(fulfillmentId)) {
            boolean isInvoiceNumberAvailable = orderFulfillmentService.isServiceInvoiceAvailable(request.getParameter("invoiceNo").trim(), Long.valueOf(fulfillmentId));
            LOG.info("Invoice number is available? {}", isInvoiceNumberAvailable);
            return !isInvoiceNumberAvailable;
        }
        return true;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/customer/invoice/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingCustomerInvoiceNumber(HttpServletRequest request) throws UnitedSuppliesException {
        LOG.info("Checking if the invoice number exists - {}", this.getClass().getSimpleName());
        boolean isInvoiceNumberAvailable = orderService.isCustomerInvoiceAvailable(request.getParameter("invoiceNo").trim(), Long.valueOf(request.getParameter("orderRef")));
        LOG.info("Invoice number is available? {}", isInvoiceNumberAvailable);
        return !isInvoiceNumberAvailable;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/product/receipt/{id}/invoice/confirmation.mk", produces = "application/json")
    @ResponseBody
    public void updateProductReceiptInvoiceNo(HttpServletRequest request, @PathVariable Long id) throws UnitedSuppliesException {
        LOG.info("Update Product Receipt Invoice No from Database");
        OrderFulfillment orderFulfillment = orderFulfillmentService.findOrderFulfillmentById(id);
        if (StringUtils.isNotEmpty(orderFulfillment.getProductInvoiceNo())) {
            throw new UnitedSuppliesException("Invoice number already generated for product invoice");
        }
        orderFulfillment.setProductInvoiceNo(generateReceiptNo(SequenceGenerationType.PRODUCT_INVOICE.getStatus(), generateAndGetProductInvoiceNo(orderFulfillment.getProdVendorId()), orderFulfillment.getVendorCode()));
        orderFulfillment.setProductInvoiceDate(getInstant());
        orderFulfillmentService.saveOrderFulfillment(orderFulfillment);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/seller/receipt/{id}/{serviceCharge}/invoice/confirmation.mk", produces = "application/json")
    @ResponseBody
    public void updateSellerReceiptInvoiceNo(HttpServletRequest request, @PathVariable Long id, @PathVariable String serviceCharge) throws UnitedSuppliesException {
        LOG.info("Update Seller Receipt Invoice No from Database");
        OrderFulfillment orderFulfillment = orderFulfillmentService.findOrderFulfillmentById(id);
        if (StringUtils.isNotEmpty(orderFulfillment.getSellServInvoiceNo())) {
            throw new UnitedSuppliesException("Invoice number already generated for service invoice");
        }
        orderFulfillment.setSellServInvoiceNo(formatInvoiceNo(updateReceiptNoInInvoiceSettings()));
        orderFulfillment.setSellServInvoiceDate(getInstant());
        orderFulfillment.setSellServInvoiceAmount(serviceCharge);
        orderFulfillmentService.saveOrderFulfillment(orderFulfillment);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/customer/receipt/{id}/invoice/confirmation.mk", produces = "application/json")
    @ResponseBody
    public void updateServiceReceiptInvoiceNo(HttpServletRequest request, @PathVariable Long id) throws UnitedSuppliesException {
        Order order = orderService.findOrderByOrderRef(id);
        if (StringUtils.isNotEmpty(order.getCustServInvoiceNo())) {
            throw new UnitedSuppliesException("Invoice number already generated for service invoice");
        }
        order.setCustServInvoiceNo(formatInvoiceNo(updateReceiptNoInInvoiceSettings()));
        order.setCustServInvoiceDate(getInstant());
        orderService.updateOrder(order);
    }

    private String GetProductInvoiceNo(Long vendorId) throws UnitedSuppliesException {
        InvoiceProdSeq invoiceProdSeq = new InvoiceProdSeq();
        List<InvoiceProdSeq> ref = invoiceProdSeqRepository.findAllByVendorId(vendorId);
        if (ref.isEmpty()) {
            ref.add(invoiceProdSeq);
            invoiceProdSeq.setVendorId(vendorId);
        }
        invoiceProdSeq = ref.get(0);
        return String.valueOf(invoiceProdSeq.nextInvoiceProdRef() + 1);
    }

    private String generateAndGetProductInvoiceNo(Long vendorId) throws UnitedSuppliesException {
        InvoiceProdSeq invoiceProdSeq = new InvoiceProdSeq();
        List<InvoiceProdSeq> ref = invoiceProdSeqRepository.findAllByVendorId(vendorId);
        if (ref.isEmpty()) {
            ref.add(invoiceProdSeq);
            invoiceProdSeq.setVendorId(vendorId);
        }
        invoiceProdSeq = ref.get(0);
        invoiceProdSeq.setInvoiceProdRef(String.valueOf(invoiceProdSeq.nextInvoiceProdRef() + 1));
        invoiceProdSeqRepository.save(invoiceProdSeq);
        return invoiceProdSeq.getInvoiceProdRef();
    }

    private String generateReceiptNo(String sequenceType, String invoiceSeqNo, String vendorCode) throws UnitedSuppliesException {
        List<InvoiceSettings> invoiceSettingsList = invoiceSettingsService.invoiceSettingsListByType(sequenceType);
        if (invoiceSettingsList.isEmpty() || invoiceSettingsList.get(0).getSequenceNo().equals(SequenceGenerationType.SERVICE_INVOICE.getStatus()) && StringUtils.isEmpty(invoiceSettingsList.get(0).getSequenceNo())) {
            throw new UnitedSuppliesException("Invoice Settings not updated for receipt no");
        }
        InvoiceSettings invoiceSettings = invoiceSettingsList.get(0);
        String invoiceNo = sequenceType.equals(SequenceGenerationType.PRODUCT_INVOICE.getStatus()) ? invoiceSeqNo : String.valueOf(invoiceSettings.nextReceiptRef() + 1);
        String prefix = sequenceType.equals(SequenceGenerationType.PRODUCT_INVOICE.getStatus()) && StringUtils.isNotEmpty(vendorCode) ? invoiceSettings.getPrefix() + vendorCode : invoiceSettings.getPrefix();
        return prefix + FORWARD_SLASH + invoiceSettings.getFinancialYear() + FORWARD_SLASH + invoiceNo;
    }

    private InvoiceSettings updateReceiptNoInInvoiceSettings() {
        List<InvoiceSettings> invoiceSettingsList = invoiceSettingsService.protectedInvoiceSettingsListByType(SequenceGenerationType.SERVICE_INVOICE.getStatus());
        InvoiceSettings invoiceSettings = new InvoiceSettings();
        if (invoiceSettingsList.size() > 0) {
            invoiceSettings = invoiceSettingsList.get(0);
        }
        invoiceSettings.setSequenceNo(String.valueOf(invoiceSettings.nextReceiptRef() + 1));
        return invoiceSettingsService.addNewInvoiceSettings(invoiceSettings);
    }

    private String formatInvoiceNo(InvoiceSettings invoiceSettings) {
        return invoiceSettings.getPrefix() + FORWARD_SLASH + invoiceSettings.getFinancialYear() + FORWARD_SLASH + invoiceSettings.getSequenceNo();
    }

    private BigDecimal findTransportCharge(String transGroup, Integer productQuantity, Integer distance) {
        Map<String, List<Transport>> transportChargesMap = transportService.transportListByTransGroup(transGroup).stream().collect(Collectors.groupingBy(Transport::getTransGroup));
        return transportChargeCalculationMethod(transportChargesMap.getOrDefault(transGroup, new ArrayList<>()), productQuantity, distance);
    }
}
