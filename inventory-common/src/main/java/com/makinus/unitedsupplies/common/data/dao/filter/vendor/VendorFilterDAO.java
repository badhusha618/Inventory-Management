package com.makinus.unitedsupplies.common.data.dao.filter.vendor;

import com.makinus.unitedsupplies.common.data.form.VendorFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * @author kings
 */
public interface VendorFilterDAO {

    List<Vendor> filterVendor(VendorFilterForm vendorFilterForm) throws UnitedSuppliesException;

}
