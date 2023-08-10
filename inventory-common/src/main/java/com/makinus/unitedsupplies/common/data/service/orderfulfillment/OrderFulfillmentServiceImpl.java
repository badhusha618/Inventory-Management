/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.orderfulfillment;

import com.makinus.unitedsupplies.common.data.dao.OrderFulfillmentRepository;
import com.makinus.unitedsupplies.common.data.entity.OrderFulfillment;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.reftype.City;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.FULFILLMENT_REF;

/**
 * Created by sabique
 */
@Service
@Transactional
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    private final Logger LOG = LogManager.getLogger(OrderFulfillmentServiceImpl.class);

    private final OrderFulfillmentRepository orderFulfillmentRepository;

    public OrderFulfillmentServiceImpl(@Autowired OrderFulfillmentRepository orderFulfillmentRepository) {
        this.orderFulfillmentRepository = orderFulfillmentRepository;
    }

    @Override
    public OrderFulfillment saveOrderFulfillment(OrderFulfillment orderFulfillment) {
        LOG.info("Saving OrderFulfillmentStatus in the database");
        OrderFulfillment savedOrderFulfillment = orderFulfillmentRepository.save(orderFulfillment);
        LOG.info("Saved OrderFulfillmentStatus in the database");
        return savedOrderFulfillment;
    }

    public OrderFulfillment findOrderFulfillmentById(Long id) throws UnitedSuppliesException {
        Optional<OrderFulfillment> orderFulfillmentOptional = orderFulfillmentRepository.findById(id);
        if (orderFulfillmentOptional.isPresent()) {
            OrderFulfillment orderFulfillment = orderFulfillmentOptional.get();
            orderFulfillment.setCityDisplay(City.statusMatch(orderFulfillment.getCity()).getDisplay());
            return orderFulfillment;
        }
        throw new UnitedSuppliesException(String.format("OrderFulfillmentStatus is not found with the id %d", id));
    }

    public OrderFulfillment findOrderFulfillmentByFulfillmentRef(String fulfillmentRef) throws UnitedSuppliesException {
        Optional<OrderFulfillment> orderFulfillmentOptional = orderFulfillmentRepository.findFulfillmentByFulfillmentRef(fulfillmentRef);
        if (orderFulfillmentOptional.isPresent()) {
            return orderFulfillmentOptional.get();
        }
        throw new UnitedSuppliesException(String.format("OrderFulfillment is not found with the ref %s", fulfillmentRef));
    }

    public String findLastFulfillmentRefByOrderRef(Long orderRef) {
        Optional<String> fulfillmentRefOptional = orderFulfillmentRepository.findLastFulfillmentRefByOrderRef(orderRef);
        if (fulfillmentRefOptional.isPresent() && StringUtils.isNumeric(fulfillmentRefOptional.get())) {
            String newFulfillmentRef = String.valueOf(Integer.parseInt(fulfillmentRefOptional.get()) + 1);
            //return formattedFulfillmentRef(newFulfillmentRef); TODO remove later
            return String.format("%03d", Long.valueOf(newFulfillmentRef));
        }
        return FULFILLMENT_REF;
    }

    public OrderFulfillment updateOrderFulfillment(final OrderFulfillment orderFulfillment) {
        LOG.info("Update existing orderFulfillment in the database");
        return orderFulfillmentRepository.save(orderFulfillment);
    }

    @Override
    public List<Tuple<Long, String>> getOrderFulfillmentRefListByOrderRef(Long orderRef) {
        LOG.info("Get Fulfillments based on order from the database");
        return orderFulfillmentRepository.listFulfillmentRefByOrderRef(orderRef);
    }

    @Override
    public List<OrderFulfillment> getOrderFulfillmentListByOrderRef(Long orderRef) {
        LOG.info("Get Fulfillments based on order from the database");
        return orderFulfillmentRepository.listFulfillmentByOrderRef(orderRef);
    }

    @Override
    public List<OrderFulfillment> getOrderFulfillmentListByFulfillmentIds(List<Long> fulfillmentIds) {
        LOG.info("Get Fulfillments based from fulfillment Ids from the database");
        return orderFulfillmentRepository.listFulfillmentByFulfillmentIds(fulfillmentIds);
    }

    @Override
    public List<OrderFulfillment> getAllFulfillments() {
        LOG.info("Get all Fulfillments from the database");
        return orderFulfillmentRepository.listAllFulfillment();
    }

    @Override
    public List<OrderFulfillment> getOrderFulfillmentListByOrder(Long orderRef) {
        LOG.info("Get Fulfillments based on order from the database");
        return orderFulfillmentRepository.listAllFulfillmentByOrderRef(orderRef);
    }

    @Override
    public List<OrderFulfillment> getGeneratedInvoicesByOrderRef(Long orderRef) {
        LOG.info("Get Fulfillments based on order from the database");
        return orderFulfillmentRepository.listGeneratedInvoicesByOrderRef(orderRef);
    }

    @Override
    public void updateOrderFulfillmentBasedVendorAllocation(ProductOrder productOrder, BigDecimal saleRate, BigDecimal transportCharge) throws UnitedSuppliesException {
        LOG.info("Update Order fulfillment based on vendor allocation in the database");
        BigDecimal saleRateDiff = saleRate.subtract(productOrder.getProdSaleRate()).multiply(new BigDecimal(productOrder.getProQuantity()));
        BigDecimal tranChargeDiff = transportCharge.subtract(productOrder.getTransportCharges());
        BigDecimal totalRateDiff = saleRateDiff.add(tranChargeDiff);
        OrderFulfillment orderFulfillment = findOrderFulfillmentById(productOrder.getFulfillmentId());
        orderFulfillment.setTransportCharges(orderFulfillment.getTransportCharges().add(tranChargeDiff));
        orderFulfillment.setSubTotal(orderFulfillment.getSubTotal().add(saleRateDiff));
        orderFulfillment.setOrderTotal(orderFulfillment.getOrderTotal().add(totalRateDiff));
        orderFulfillmentRepository.save(orderFulfillment);
    }

    @Override
    public boolean isProductInvoiceAvailable(String invoiceNo, Long fulfillmentId) {
        LOG.info("Check if product invoice is available from database");
        OrderFulfillment orderFulfillment = orderFulfillmentRepository.findProductInvoice(invoiceNo, fulfillmentId);
        return orderFulfillment != null;
    }

    @Override
    public boolean isServiceInvoiceAvailable(String sellServInvoiceNo, Long fulfillmentId) {
        LOG.info("Check if service invoice is available from database");
        OrderFulfillment orderFulfillment = orderFulfillmentRepository.findServiceInvoice(sellServInvoiceNo, fulfillmentId);
        return orderFulfillment != null;
    }

}
