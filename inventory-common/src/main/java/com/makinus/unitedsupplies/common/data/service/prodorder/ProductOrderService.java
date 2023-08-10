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

import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.form.ProductOrderFilterForm;
import com.makinus.unitedsupplies.common.data.reftype.ProdOrderStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ammar
 */
public interface ProductOrderService {

    ProductOrder findProductOrder(final Long id) throws UnitedSuppliesException;

    List<ProductOrder> productOrdersList();

    ProductOrder saveProdOrder(final ProductOrder productOrder);

    List<ProductOrder> saveProdOrders(final List<ProductOrder> productOrderList);

    List<ProductOrder> getProductListByOrderRef(Long orderRef);

    List<ProductOrder> getProductListByOrderRefAndFulfillment(Long orderRef, String fulfillmentStatus);

    List<ProductOrder> getProductListByFulfillment(Long fulfillmentId);

    List<ProductOrder> getProductOrderListByIds(List<Long> ids);

    List<ProductOrder> getProductOrderListByOrderRefs(List<Long> orderRefs);

    ProductOrder updateProductOrderVendor(Long id, Long prodVendorId, Vendor vendor, BigDecimal saleRate, BigDecimal pincode) throws UnitedSuppliesException;

    ProductOrder updateProductOrderStatus(Long id, ProdOrderStatus prodOrderStatus) throws UnitedSuppliesException;

    List<ProductOrder> filterProductOrder(ProductOrderFilterForm productOrderFilterForm) throws UnitedSuppliesException;

    List<ProductOrder> updateProductOrderFulfillmentIds(List<Long> ids, Long fulfillmentId);
}
