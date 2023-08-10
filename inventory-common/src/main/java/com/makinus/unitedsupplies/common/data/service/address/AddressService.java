/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.address;

import com.makinus.unitedsupplies.common.data.entity.Address;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;
import java.util.Optional;

/** Created by sabique */
public interface AddressService {

  Address saveAddress(final Address address);

  Address findAddressById(Long id) throws InventoryException;

  Address findAddressByRef(Long ref)  throws InventoryException;

  Optional<Address> findDefaultAddressByUserAndCategory(Long userId, String category)  throws InventoryException;

  List<Address> listAllAddressByUserId(Long userId);

  List<Address> listAllAddressByUserIdAndCategory(Long userId, String category);

  List<Address> removeDefaultFlagInAllAddress(Long userId, String category);

  Address updateAddress(final Address address) throws InventoryException;

  Address updateDefaultStatus(Long ref) throws InventoryException;

  Address removeAddress(Long ref) throws InventoryException;
}
