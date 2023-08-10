/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.size;

import com.makinus.unitedsupplies.common.data.entity.Size;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;

/** Created by abuabdul */
public interface SizeService {

  Size saveSize(final Size size);

  List<Size> sizeList();

  List<Size> sizeListByCategory(Long categoryId);

  Size updateSize(final Size size);

  Size findSize(Long id) throws UnitedSuppliesException;

  Size removeSize(Long id) throws UnitedSuppliesException;
}
