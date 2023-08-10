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

import com.makinus.unitedsupplies.common.data.dao.AddressRefRepository;
import com.makinus.unitedsupplies.common.data.dao.AddressRepository;
import com.makinus.unitedsupplies.common.data.entity.Address;
import com.makinus.unitedsupplies.common.data.entity.AddressRef;
import com.makinus.unitedsupplies.common.data.reftype.City;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger LOG = LogManager.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressRefRepository addressRefRepository;

    public AddressServiceImpl(@Autowired AddressRepository addressRepository, @Autowired AddressRefRepository addressRefRepository) {
        this.addressRepository = addressRepository;
        this.addressRefRepository = addressRefRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        LOG.info("Saving Address in the database");
        setAddressRef(address);
        setDefault(address);
        Address savedAddress = addressRepository.save(address);
        LOG.info("Saved Address in the database");
        return savedAddress;
    }

    private void setAddressRef(Address address) {
        List<AddressRef> addressRefs = addressRefRepository.findAll();
        AddressRef addressRef = addressRefs.isEmpty() ? new AddressRef() : addressRefs.get(0);
        addressRef.setRef(addressRef.getRef() + 1L);
        addressRefRepository.save(addressRef);
        address.setRef(addressRef.getRef());
    }

    private void setDefault(Address address) {
        Integer addressCount = addressRepository.addressCountByUserAndCategory(address.getUserId(), address.getCategory());
        address.setIsDefault(addressCount > 0 ? YNStatus.NO.getStatus() : YNStatus.YES.getStatus());
    }

    public Address findAddressById(Long id) throws InventoryException {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setCityDisplay(City.statusMatch(address.getCity()).getDisplay());
            return address;
        }
        throw new InventoryException(String.format("Address is not found with the id %d", id));
    }

    @Override
    public Address findAddressByRef(Long ref) throws InventoryException {
        Optional<Address> addressOptional = addressRepository.findAddressByRef(ref);
        if (addressOptional.isPresent()) {
            return addressOptional.get();
        }
        throw new InventoryException(String.format("Address is not found with the ref %d", ref));
    }

    @Override
    public Optional<Address> findDefaultAddressByUserAndCategory(Long userId, String category) throws InventoryException {
        return addressRepository.findDefaultAddressByUserAndCategory(userId, category);
    }

    public List<Address> listAllAddressByUserId(Long userId) {
        LOG.info("List Address from database");
        return addressRepository.listAllAddressByUserId(userId);
    }

    public List<Address> listAllAddressByUserIdAndCategory(Long userId, String category) {
        LOG.info("List Address by category from database");
        return addressRepository.listAllAddressByUserIdAndCategory(userId, category);
    }

    public List<Address> removeDefaultFlagInAllAddress(Long userId, String category) {
        LOG.info("List Address by category from database");
        List<Address> addressList = addressRepository.listAllAddressByUserIdAndCategory(userId, category);
        addressList.forEach(address -> {
            address.setIsDefault(YNStatus.NO.getStatus());
            address.setUpdatedBy(getCurrentUser());
            address.setUpdatedDate(getInstant());
        });
        return addressList;
    }

    public Address updateAddress(final Address address) throws InventoryException {
        LOG.info("Update existing address in the database");
        return addressRepository.save(address);
    }

    public Address updateDefaultStatus(Long ref) throws InventoryException {
        LOG.info("Update existing address default status in the database");
        Optional<Address> addressOptional = addressRepository.findAddressByRef(ref);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            List<Address> addressList = addressRepository.listAllAddressByUserIdAndCategory(address.getUserId(), address.getCategory());
            addressList.forEach(a -> {
                a.setIsDefault(YNStatus.NO.getStatus());
                a.setUpdatedBy(getCurrentUser());
                a.setUpdatedDate(getInstant());
            });
            address.setIsDefault(YNStatus.YES.getStatus());
            address.setUpdatedBy(getCurrentUser());
            address.setUpdatedDate(getInstant());
            return address;
        }
        throw new InventoryException(String.format("Address is not found with the ref %d", ref));
    }

    public Address removeAddress(Long ref) throws InventoryException {
        Optional<Address> addressOptional = addressRepository.findAddressByRef(ref);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setDeleted(YNStatus.YES.getStatus());
            address.setUpdatedBy(getCurrentUser());
            address.setUpdatedDate(getInstant());
            return address;
        }
        throw new InventoryException(String.format("Address is not found with the id %d", ref));
    }
}
