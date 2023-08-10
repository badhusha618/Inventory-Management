/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.FulfillmentForm;
import com.makinus.unitedsupplies.common.data.entity.OrderFulfillment;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.orderfulfillment.OrderFulfillmentService;
import com.makinus.unitedsupplies.common.data.service.prodorder.ProductOrderService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("FulfillmentMapper")
public class FulfillmentMapper implements EntityMapper<FulfillmentForm, OrderFulfillment> {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private VendorService vendorService;

    private final Logger LOG = LogManager.getLogger(FulfillmentMapper.class);

    @Override
    public OrderFulfillment map(FulfillmentForm fulfillmentForm) throws UnitedSuppliesException {
        LOG.info("Map Fulfillment Form to Fulfillment Entity");
        List<ProductOrder> prodOrders = productOrderService.getProductListByOrderRefAndFulfillment(Long.valueOf(fulfillmentForm.getOrderRef()), YNStatus.NO.getStatus());
        Vendor vendor = StringUtils.isNotEmpty(fulfillmentForm.getProdVendorId()) ? vendorService.findVendor(Long.valueOf(fulfillmentForm.getProdVendorId())) : new Vendor();
        OrderFulfillment orderFulfillment = prodOrders.size() > 0 ? orderFulfillmentService.findOrderFulfillmentById(prodOrders.get(0).getFulfillmentId()) : new OrderFulfillment();
        if (fulfillmentForm.getProdOrderIds() != null && prodOrders.size() == fulfillmentForm.getProdOrderIds().size()) {
            return updateOrderFulfillment(orderFulfillment, vendor, fulfillmentForm);
        } else {
            return createNewFulfillment(orderFulfillment, vendor, fulfillmentForm);
        }
    }

    private OrderFulfillment updateOrderFulfillment(OrderFulfillment orderFulfillment, Vendor vendor, FulfillmentForm fulfillmentForm) {
        if(StringUtils.isNotEmpty(fulfillmentForm.getOrderRef())) {
            orderFulfillment.setOrderRef(Long.valueOf(fulfillmentForm.getOrderRef()));
        }
        orderFulfillment.setUpdatedBy(getCurrentUser());
        orderFulfillment.setUpdatedDate(getInstant());
        return orderFulfillmentService.saveOrderFulfillment(updateVendorDetails(orderFulfillment, vendor));
    }

    private OrderFulfillment createNewFulfillment(OrderFulfillment orderFulfillment, Vendor vendor, FulfillmentForm fulfillmentForm) {
        List<ProductOrder> fulfillmentProdOrders = productOrderService.getProductOrderListByIds(fulfillmentForm.getProdOrderIds().stream().map(Long::valueOf).collect(Collectors.toList()));
        BigDecimal fulfilledTransportCharges = fulfillmentProdOrders.stream().map(ProductOrder::getTransportCharges).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal fulfilledLoadingCharges = fulfillmentProdOrders.stream().map(ProductOrder::getLoadingCharges).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal fulfilledSubTotal = fulfillmentProdOrders.stream().map(p -> p.getProdSaleRate().multiply(new BigDecimal(p.getProQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal fulfilledTotal = fulfilledSubTotal.add(fulfilledTransportCharges.add(fulfilledLoadingCharges));
        OrderFulfillment newOrderFulfillment = new OrderFulfillment();
        BeanUtils.copyProperties(orderFulfillment, newOrderFulfillment);
        orderFulfillmentService.saveOrderFulfillment(updateCharges(orderFulfillment, fulfilledTransportCharges,fulfilledLoadingCharges, fulfilledSubTotal, fulfilledTotal));
        updateNewFulfillment(newOrderFulfillment, vendor, fulfillmentForm);
        return updateCharges(newOrderFulfillment, fulfilledTransportCharges,fulfilledLoadingCharges, fulfilledSubTotal, fulfilledTotal);
    }

    private OrderFulfillment updateNewFulfillment(OrderFulfillment newOrderFulfillment, Vendor vendor, FulfillmentForm fulfillmentForm) {
        newOrderFulfillment.setId(null);
        newOrderFulfillment.setProductInvoiceNo(null);
        newOrderFulfillment.setProductInvoiceDate(null);
        newOrderFulfillment.setSellServInvoiceNo(null);
        newOrderFulfillment.setSellServInvoiceDate(null);
        newOrderFulfillment.setSellServInvoiceAmount(null);
        newOrderFulfillment.setFulfillmentRef(generateFulfillmentRef(Long.valueOf(fulfillmentForm.getOrderRef())));
        newOrderFulfillment.setOrderRef(Long.valueOf(fulfillmentForm.getOrderRef()));
        updateVendorDetails(newOrderFulfillment, vendor);
        newOrderFulfillment.setServiceCharges(BigDecimal.ZERO);
        newOrderFulfillment.setCreatedBy(getCurrentUser());
        newOrderFulfillment.setCreatedDate(getInstant());
        newOrderFulfillment.setUpdatedBy(getCurrentUser());
        newOrderFulfillment.setUpdatedDate(getInstant());
        return newOrderFulfillment;
    }

    private OrderFulfillment updateCharges(OrderFulfillment orderFulfillment, BigDecimal fulfilledTransportCharges,
                               BigDecimal fulfilledLoadingCharges, BigDecimal fulfilledSubTotal, BigDecimal fulfilledTotal) {
        orderFulfillment.setTransportCharges(orderFulfillment.getTransportCharges().subtract(fulfilledTransportCharges));
        orderFulfillment.setLoadingCharges(orderFulfillment.getLoadingCharges().subtract(fulfilledLoadingCharges));
        orderFulfillment.setSubTotal(orderFulfillment.getSubTotal().subtract(fulfilledSubTotal));
        orderFulfillment.setOrderTotal(orderFulfillment.getOrderTotal().subtract(fulfilledTotal));
        return orderFulfillment;
    }

    private OrderFulfillment updateVendorDetails(OrderFulfillment orderFulfillment, Vendor vendor) {
        orderFulfillment.setProdVendorId(vendor.getId());
        orderFulfillment.setVendorCode(vendor.getVendorCode());
        orderFulfillment.setVendorName(vendor.getVendorName());
        orderFulfillment.setCompanyName(vendor.getCompanyName());
        orderFulfillment.setAddress(vendor.getAddress());
        orderFulfillment.setCity(vendor.getCity());
        orderFulfillment.setPinCode(vendor.getPinCode());
        orderFulfillment.setGstNo(vendor.getGstNo());
        orderFulfillment.setPanNo(vendor.getPanNo());
        return orderFulfillment;
    }

    private String generateFulfillmentRef(Long orderRef) {
        return orderFulfillmentService.findLastFulfillmentRefByOrderRef(orderRef);
    }

}
