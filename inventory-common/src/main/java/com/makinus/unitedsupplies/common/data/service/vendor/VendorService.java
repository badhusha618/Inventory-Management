package com.makinus.unitedsupplies.common.data.service.vendor;

import com.makinus.unitedsupplies.common.data.form.VendorFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * @author kings
 */
public interface VendorService {

    boolean isVendorAvailable(final String vendorName);

    Vendor saveVendor(final Vendor vendor);

    List<Vendor> vendorList();

    List<Vendor> listActiveVendors();

    List<Vendor> listActiveVendorsByVendorIds(List<Long> ids);

    Vendor updateVendor(final Vendor vendor);

    Vendor findVendor(Long Id) throws UnitedSuppliesException;

    Vendor removeVendor(Long Id) throws UnitedSuppliesException;

    Vendor findVendorWithImages(Long id) throws UnitedSuppliesException;

    List<Vendor> filterVendor(VendorFilterForm vendorFilterForm) throws UnitedSuppliesException;

}
