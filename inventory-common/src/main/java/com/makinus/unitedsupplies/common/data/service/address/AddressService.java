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
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;
import java.util.Optional;

/** Created by sabique */
public interface AddressService {

  Address saveAddress(final Address address);

  Address findAddressById(Long id) throws UnitedSuppliesException;

  Address findAddressByRef(Long ref)  throws UnitedSuppliesException;

  Optional<Address> findDefaultAddressByUserAndCategory(Long userId, String category)  throws UnitedSuppliesException;

  List<Address> listAllAddressByUserId(Long userId);

  List<Address> listAllAddressByUserIdAndCategory(Long userId, String category);

  List<Address> removeDefaultFlagInAllAddress(Long userId, String category);

  Address updateAddress(final Address address) throws UnitedSuppliesException;

  Address updateDefaultStatus(Long ref) throws UnitedSuppliesException;

  Address removeAddress(Long ref) throws UnitedSuppliesException;
}
