/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.order;

import com.makinus.unitedsupplies.common.data.dao.OrderRefRepository;
import com.makinus.unitedsupplies.common.data.dao.OrderRepository;
import com.makinus.unitedsupplies.common.data.dao.filter.order.OrderFilterDAO;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.form.OrderFilterForm;
import com.makinus.unitedsupplies.common.data.reftype.OrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.PaymentType;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static java.lang.String.format;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger LOG = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderRefRepository orderRefRepository;

    private final OrderFilterDAO orderFilterDAO;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private VendorService vendorService;

    public OrderServiceImpl(@Autowired OrderRepository orderRepository, @Autowired OrderRefRepository orderRefRepository, @Autowired OrderFilterDAO orderFilterDAO) {
        this.orderRepository = orderRepository;
        this.orderRefRepository = orderRefRepository;
        this.orderFilterDAO = orderFilterDAO;
    }

    @Override
    public Order saveOrder(Order order) {
        LOG.info("Add New Order in the database");
        setOrderRef(order);
        Order savedOrder = orderRepository.save(order);
        LOG.info("Saved New Product in the database");
        return savedOrder;
    }

    private void setOrderRef(Order order) {
        List<OrderRef> orderRefs = orderRefRepository.findAll();
        OrderRef orderRef = orderRefs.isEmpty() ? new OrderRef() : orderRefs.get(0);
        orderRef.setRef(orderRef.getRef() + 1L);
        orderRefRepository.save(orderRef);
        order.setOrderRef(orderRef.getRef());
    }

    @Override
    public List<Order> ordersList() {
        LOG.info("List Orders from database");
        return orderRepository.listAllActiveOrders();
    }

    @Override
    public List<Order> ordersListAll() {
        LOG.info("List All Orders from database");
        return orderRepository.listAllOrders();
    }

    @Override
    public List<Order> newOrderList() {
        LOG.info("List All New Orders from database");
        return orderRepository.listNewOrders();
    }

    @Override
    public List<Tuple<Long, Integer>> orderProdOrderCount() {
        LOG.info("List Product Orders Count for Orders from database");
        return orderRepository.getOrderProdOrderCount();
    }

    @Override
    public List<Order> ordersListByUser(Long userId) {
        LOG.info("List Orders By User ID from database");
        return orderRepository.listAllActiveOrdersByUserId(userId);
    }

    @Override
    public Order findOrderByOrderRef(Long orderRef) throws InventoryException {
        LOG.info("Find order by order ref from database");
        Optional<Order> orderOptional = orderRepository.findOrderByOrderRef(orderRef);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }
        throw new InventoryException(format("Order is not found with the order ref %d", orderRef));
    }

    @Override
    public Order updateOrder(Order order) {
        LOG.info("Update Order in the database");
        return orderRepository.save(order);
    }

    @Override
    public void updateOrdersBasedVendorAllocation(ProductOrder oldProductOrder, BigDecimal saleRate, BigDecimal transportCharge, String vendorId) throws InventoryException {
        LOG.info("Update Orders based on vendor allocation in the database");
        Order order = findOrderByOrderRef(oldProductOrder.getOrderRef());
        BigDecimal saleRateDiff = saleRate.subtract(oldProductOrder.getProdSaleRate()).multiply(new BigDecimal(oldProductOrder.getProQuantity()));
        BigDecimal tranChargeDiff = transportCharge.subtract(oldProductOrder.getTransportCharges());
        BigDecimal totalRateDiff = saleRateDiff.add(tranChargeDiff);
        order.setChangeLog(formattedChangeLog(oldProductOrder, vendorId, transportCharge, order.getSubTotal().add(tranChargeDiff), saleRate, order.getSubTotal().add(saleRateDiff), totalRateDiff, order.getOrderTotal().add(totalRateDiff))); // TODO: Update here change log string format
        order.setUpdatedBy(getCurrentUser());
        order.setUpdatedDate(getInstant());
        order.setDeleted(YNStatus.YES.getStatus());
        Order newOrder = new Order();
        BeanUtils.copyProperties(order, newOrder);
        newOrder.setId(null);
        newOrder.setTransportCharges(order.getTransportCharges().add(tranChargeDiff));
        newOrder.setSubTotal(order.getSubTotal().add(saleRateDiff));
        newOrder.setOrderTotal(order.getOrderTotal().add(totalRateDiff));
        newOrder.setUpdatedBy(getCurrentUser());
        newOrder.setUpdatedDate(getInstant());
        newOrder.setChangeLog(null);
        newOrder.setDeleted(YNStatus.NO.getStatus());
        updateOrder(order);
        updateOrder(newOrder);
    }

    private String formattedChangeLog(ProductOrder oldProductOrder, String vendorId, BigDecimal oldTranCharge, BigDecimal tranCharge, BigDecimal oldSubtotal, BigDecimal subTotal, BigDecimal oldTotal, BigDecimal total) throws InventoryException {
        ProductVendor oldProdVendor = productVendorService.findProductVendor(oldProductOrder.getProdVendorId());
        Vendor oldVendor = vendorService.findVendor(oldProdVendor.getVendorId());
        ProductVendor productVendor = productVendorService.findProductVendor(Long.valueOf(vendorId));
        Vendor vendor = vendorService.findVendor(productVendor.getVendorId());
        return MessageFormat.format(ORDER_CHANGE_LOG,
                oldProductOrder.getProductCode() + " - " + oldProductOrder.getProductName(),
                oldVendor.getVendorCode() + " - " + oldVendor.getVendorName(),
                vendor.getVendorCode() + " - " + vendor.getVendorName(),
                oldTranCharge, tranCharge,
                oldSubtotal, subTotal,
                oldTotal, total
        );
    }

    @Override
    public void updateOrderList(List<Order> orders) {
        LOG.info("Update New Orders as Read in the database");
        orderRepository.saveAll(orders);
    }

    @Override
    public Order findOrder(Long id) throws InventoryException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }
        throw new InventoryException(format("Order is not found with the id %d", id));
    }

    @Override
    public Order removeOrder(Long id) throws InventoryException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setDeleted(YNStatus.YES.getStatus());
            order.setUpdatedBy(getCurrentUser());
            order.setUpdatedDate(getInstant());
            return order;
        }
        throw new InventoryException(format("Order is not found with the id %s", id));
    }

    @Override
    public Order orderDetailsUpdated(Long ref) throws InventoryException {
        Order order = findOrderByOrderRef(ref);
        order.setUpdatedBy(getCurrentUser());
        order.setUpdatedDate(getInstant());
        return order;
    }

    @Override
    public Order updateOrderStatus(Long ref, OrderStatus orderStatus) throws InventoryException {
        Order order = findOrderByOrderRef(ref);
        order.setStatus(orderStatus.getStatus());
        order.setUpdatedBy(getCurrentUser());
        order.setUpdatedDate(getInstant());
        return order;
    }

    @Override
    public Order updatePaymentType(Long id, PaymentType paymentType) throws InventoryException {
        Order order = findOrder(id);
        order.setPaymentType(paymentType.getStatus());
        order.setUpdatedBy(getCurrentUser());
        order.setUpdatedDate(getInstant());
        return order;
    }

    @Override
    public List<Order> filterOrder(OrderFilterForm orderFilterForm) throws InventoryException {
        LOG.info("Search Orders by filter from the database");
        if (orderFilterForm != null &&
                (StringUtils.isNotEmpty(orderFilterForm.getName())
                        || StringUtils.isNotEmpty(orderFilterForm.getMobile())
                        || StringUtils.isNotEmpty(orderFilterForm.getStatus())
                        || StringUtils.isNotEmpty(orderFilterForm.getOrderDate())
                        || StringUtils.isNotEmpty(orderFilterForm.getFromDate())
                        || StringUtils.isNotEmpty(orderFilterForm.getToDate()))) {
            return orderFilterDAO.filterOrder(orderFilterForm);
        }
        LOG.info("Get all Orders in the catalog due to form is empty");
        return orderRepository.listAllActiveOrders();
    }

    @Override
    public boolean isCustomerInvoiceAvailable(String custServInvoiceNo, Long orderRef) {
        LOG.info("Check if customer invoice is available from database");
        Order order = orderRepository.findCustomerInvoice(custServInvoiceNo, orderRef);
        return order != null;
    }
}
