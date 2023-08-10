/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.prodorder;

import com.makinus.unitedsupplies.common.data.dao.ProductOrderRepository;
import com.makinus.unitedsupplies.common.data.dao.filter.order.product.ProductOrderFilterDAO;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.form.ProductOrderFilterForm;
import com.makinus.unitedsupplies.common.data.reftype.ProdOrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.orderfulfillment.OrderFulfillmentService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private final Logger LOG = LogManager.getLogger(ProductOrderServiceImpl.class);

    private final ProductOrderRepository productOrderRepository;

    private final ProductOrderFilterDAO productOrderFilterDAO;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    public ProductOrderServiceImpl(@Autowired ProductOrderRepository productOrderRepository, @Autowired ProductOrderFilterDAO productOrderFilterDAO) {
        this.productOrderRepository = productOrderRepository;
        this.productOrderFilterDAO = productOrderFilterDAO;
    }

    @Override
    public ProductOrder findProductOrder(Long id) throws InventoryException {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (productOrderOptional.isPresent()) {
            return productOrderOptional.get();
        }
        throw new InventoryException(format("Product Order is not found with the id %d", id));
    }

    @Override
    public ProductOrder saveProdOrder(ProductOrder productOrder) {
        LOG.info("Saving ProductOrder in the database");
        ProductOrder savedProductOrder = productOrderRepository.save(productOrder);
        LOG.info("Saved ProductOrder in the database");
        return savedProductOrder;
    }

    @Override
    public List<ProductOrder> saveProdOrders(List<ProductOrder> productOrderList) {
        LOG.info("Saving ProductOrders in the database");
        List<ProductOrder> savedProductOrders = productOrderRepository.saveAll(productOrderList);
        LOG.info("Saved ProductOrders in the database");
        return savedProductOrders;
    }

    @Override
    public List<ProductOrder> getProductListByOrderRef(Long orderRef) {
        LOG.info("Get ProductOrders based on order from the database");
        return productOrderRepository.listAllProductsByOrderRef(orderRef);
    }

    @Override
    public List<ProductOrder> getProductListByOrderRefAndFulfillment(Long orderRef, String fulfillmentStatus) {
        LOG.info("Get ProductOrders based on order from the database");
        return productOrderRepository.listProductsByOrderRefAndFulfillmentStatus(orderRef, fulfillmentStatus);
    }

    @Override
    public List<ProductOrder> getProductListByFulfillment(Long fulfillmentId) {
        LOG.info("Get ProductOrders based on fulfillment from the database");
        return productOrderRepository.listProductsByFulfillmentId(fulfillmentId);
    }

    @Override
    public List<ProductOrder> getProductOrderListByIds(List<Long> ids) {
        LOG.info("Get ProductOrders based on order from the database");
        return productOrderRepository.listAllProductOrdersByIds(ids);
    }

    @Override
    public List<ProductOrder> getProductOrderListByOrderRefs(List<Long> orderRefs) {
        LOG.info("Get ProductOrders based on order refs from the database");
        return productOrderRepository.listAllProductOrdersByOrderRefs(orderRefs);
    }

    @Override
    public List<ProductOrder> productOrdersList() {
        LOG.info("List ProductOrders from database");
        return productOrderRepository.listAllProductOrders();
    }

    @Override
    public ProductOrder updateProductOrderVendor(Long id, Long prodVendorId, Vendor vendor, BigDecimal saleRate, BigDecimal transportCharge) throws InventoryException {
        LOG.info("Update ProductOrder vendor in the database");
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (productOrderOptional.isPresent()) {
            ProductOrder productOrder = productOrderOptional.get();
            productOrder.setProdVendorId(prodVendorId);
            productOrder.setVendorCode(vendor.getVendorCode());
            productOrder.setVendorName(vendor.getVendorName());
            productOrder.setGstNo(vendor.getGstNo());
            productOrder.setPanNo(vendor.getPanNo());
            productOrder.setProdSaleRate(saleRate);
            productOrder.setTransportCharges(transportCharge); // TODO: Have to update transport charge here ...
            return productOrder;
        }
        throw new InventoryException(format("ProductOrder is not found with the id %s", id));
    }

    @Override
    public ProductOrder updateProductOrderStatus(Long id, ProdOrderStatus status) throws InventoryException {
        LOG.info("Update ProductOrder status in the database");
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(id);
        if (productOrderOptional.isPresent()) {
            ProductOrder productOrder = productOrderOptional.get();
            productOrder.setStatus(status.getStatus());
            return productOrder;
        }
        throw new InventoryException(format("Product is not found with the id %s", id));
    }

    @Override
    public List<ProductOrder> filterProductOrder(ProductOrderFilterForm productOrderFilterForm) throws InventoryException {
        LOG.info("Search ProductOrders by filter from the database");
        if (productOrderFilterForm != null &&
                (StringUtils.isNotEmpty(productOrderFilterForm.getProductCode())
                        || StringUtils.isNotEmpty(productOrderFilterForm.getProductName())
                        || StringUtils.isNotEmpty(productOrderFilterForm.getVendorCode())
                        || StringUtils.isNotEmpty(productOrderFilterForm.getStatus())
                        || StringUtils.isNotEmpty(productOrderFilterForm.getFromOrderDate())
                        || StringUtils.isNotEmpty(productOrderFilterForm.getToOrderDate()))) {
            return productOrderFilterDAO.filterProductOrder(productOrderFilterForm);
        }
        LOG.info("Get all ProductOrders in the catalog due to form is empty");
        return productOrderRepository.listAllProductOrders();
    }

    @Override
    public List<ProductOrder> updateProductOrderFulfillmentIds(List<Long> ids, Long fulfillmentId) {
        LOG.info("Update ProductOrder fulfillment in the database");
        List<ProductOrder> productOrders = getProductOrderListByIds(ids);
        productOrders.forEach(p -> {
            p.setFulfillmentId(fulfillmentId);
            p.setFulfillmentStatus(YNStatus.YES.getStatus());
        });
        return productOrders;
    }

}
