/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.productsource;

import com.makinus.unitedsupplies.common.data.entity.ProductSource;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;
import java.util.Optional;

/** Created by abuabdul */
public interface ProductSourceService {

  ProductSource saveProductSource(final ProductSource productSource);

  List<ProductSource> saveProductSourceList(List<ProductSource> productSources);

  List<ProductSource> updateProductSourceList(List<ProductSource> productSources);

  List<ProductSource> productSourceList();

  List<ProductSource> activeProductSourceList();

  Optional<ProductSource> findDefaultProductSource(Long prodId);

  List<ProductSource> findDefaultProductSources();

  List<ProductSource> productSourceListByProduct(Long prodId);

  ProductSource updateProductSource(final ProductSource productSource);

  ProductSource findProductSource(Long id) throws UnitedSuppliesException;

  ProductSource removeProductSource(Long id) throws UnitedSuppliesException;
}
