package com.makinus.unitedsupplies.common.data.service.vendor;

import com.makinus.unitedsupplies.common.data.form.VendorFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface VendorService {

    boolean isVendorAvailable(final String vendorName);

    Vendor saveVendor(final Vendor vendor);

    List<Vendor> vendorList();

    List<Vendor> listActiveVendors();

    List<Vendor> listActiveVendorsByVendorIds(List<Long> ids);

    Vendor updateVendor(final Vendor vendor);

    Vendor findVendor(Long Id) throws InventoryException;

    Vendor removeVendor(Long Id) throws InventoryException;

    Vendor findVendorWithImages(Long id) throws InventoryException;

    List<Vendor> filterVendor(VendorFilterForm vendorFilterForm) throws InventoryException;

}
