package com.makinus.unitedsupplies.common.data.service.vendor;

import com.makinus.unitedsupplies.common.data.form.VendorFilterForm;
import com.makinus.unitedsupplies.common.data.dao.VendorRepository;
import com.makinus.unitedsupplies.common.data.dao.filter.vendor.VendorFilterDAO;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static java.lang.String.format;
import static java.nio.file.Paths.get;


/**
 * Created by kingson
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private final Logger LOG = LogManager.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;
    private final ImageWriter imageWriter;
    private final VendorFilterDAO vendorFilterDAO;

    public VendorServiceImpl(@Autowired VendorRepository vendorRepository, @Autowired ImageWriter imageWriter, @Autowired VendorFilterDAO vendorFilterDAO) {
        this.vendorRepository = vendorRepository;
        this.imageWriter = imageWriter;
        this.vendorFilterDAO = vendorFilterDAO;
    }

    @Override
    public boolean isVendorAvailable(String vendorCode) {
        LOG.info("Check if UnitedSupplies Vendor is available from database");
        Vendor vendor = vendorRepository.findActiveVendor(vendorCode);
        return vendor != null;
    }

    @Override
    public Vendor saveVendor(Vendor vendor) {
        LOG.info("Saving Vendor in the database");
        Vendor savedVendor = vendorRepository.save(vendor);
        LOG.info("Saved Unit in the database");
        return savedVendor;
    }

    @Override
    public List<Vendor> vendorList() {
        LOG.info("List Vendors from database");
        return vendorRepository.listAllVendors();

    }

    @Override
    public List<Vendor> listActiveVendors() {
        LOG.info("List Active Vendors from database");
        return vendorRepository.listAllActiveVendors();
    }

    @Override
    public List<Vendor> listActiveVendorsByVendorIds(List<Long> ids) {
        LOG.info("List Active Vendors By ids from database");
        return vendorRepository.listAllVendorsByIds(ids);
    }

    @Override
    public Vendor updateVendor(final Vendor vendor) {
        LOG.info("Update existing vendor in the catalog");
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor findVendor(Long id) throws UnitedSuppliesException {
        Optional<Vendor> unitOptional = vendorRepository.findById(id);
        if (unitOptional.isPresent()) {
            return unitOptional.get();
        }
        throw new UnitedSuppliesException(String.format("vendor is not found with the id %d", id));
    }

    @Override
    public Vendor removeVendor(Long id) throws UnitedSuppliesException {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if (vendorOptional.isPresent()) {
            Vendor vendor = vendorOptional.get();
            vendor.setDeleted(YNStatus.YES.getStatus());
            vendor.setUpdatedBy(getCurrentUser());
            vendor.setUpdatedDate(getInstant());
            return vendor;
        }
        throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
    }

    @Override
    public Vendor findVendorWithImages(Long id) throws UnitedSuppliesException {
        Optional<Vendor> vendorOptional = vendorRepository.findById(id);
        if (vendorOptional.isPresent()) {
            Vendor vendor = vendorOptional.get();
            vendor.setImage(imageWriter.readImage(get(vendor.getVendorSignature())));
            return vendor;
        }
        throw new UnitedSuppliesException(format("Vendor is not found with the id %d", id));
    }

    @Override
    public List<Vendor> filterVendor(VendorFilterForm vendorFilterForm) throws UnitedSuppliesException {
        LOG.info("Search vendors by filter from the database");
        if (vendorFilterForm != null &&
                (StringUtils.isNotEmpty(vendorFilterForm.getVendorCode())
                        || StringUtils.isNotEmpty(vendorFilterForm.getVendorName())
                        || StringUtils.isNotEmpty(vendorFilterForm.getCompanyName())
                        || StringUtils.isNotEmpty(vendorFilterForm.getFromDate())
                        || StringUtils.isNotEmpty(vendorFilterForm.getToDate()))) {
            return vendorFilterDAO.filterVendor(vendorFilterForm);
        }
        LOG.info("Get all vendors due to filter form is empty");
        return vendorRepository.listAllActiveVendors();
    }
}
