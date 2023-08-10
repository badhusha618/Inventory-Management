package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.VendorForm;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by Kingson
 */
@Component
@Qualifier("VendorMapper")
public class VendorMapper
        implements EntityMapper<VendorForm, Vendor>,
        EntityUpdateMapper<VendorForm, Vendor>,
        EntityRemapper<Vendor, VendorForm> {

    private final Logger LOG = LogManager.getLogger(UnitMapper.class);
    @Autowired
    private ImageWriter imageWriter;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public Vendor map(VendorForm vendorForm) throws UnitedSuppliesException {
        LOG.info("Map Vendor Form to Vendor Entity");
        Vendor vendor = new Vendor();
        try {
            vendor.setVendorCode(vendorForm.getVendorCode());
            vendor.setVendorName(vendorForm.getVendorName());
            vendor.setCompanyName(vendorForm.getCompanyName());
            vendor.setMobileNo(vendorForm.getMobileNo());
            vendor.setAlternateNo(vendorForm.getAlternateNo());
            vendor.setEmail(vendorForm.getEmail());
            vendor.setGstNo(vendorForm.getGstNo());
            vendor.setPanNo(vendorForm.getPanNo());
            vendor.setAcHolderName(vendorForm.getAcHolderName());
            vendor.setBankAcNo(vendorForm.getBankAcNo());
            vendor.setIfscCode(vendorForm.getIfscCode());
            vendor.setTelephone(vendorForm.getTelephone());
            vendor.setAddress(vendorForm.getAddress());
            vendor.setCity(vendorForm.getCity());
            vendor.setPinCode(vendorForm.getPinCode());
            vendor.setOriginalFileName(vendorForm.getVendorSignature().getOriginalFilename());
            vendor.setImage(vendorForm.getVendorSignature().getBytes());
            vendor.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
            vendor.setVendorSignature(getS3UrlFromAttachment(vendorForm.getVendorSignature(),VENDOR_SIGNATURE_PREFIX,amazonS3Client));
            vendor.setActive(vendorForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            vendor.setCreatedBy(getCurrentUser());
            vendor.setCreatedDate(getInstant());
            vendor.setUpdatedBy(getCurrentUser());
            vendor.setUpdatedDate(getInstant());
            vendor.setDeleted(YNStatus.NO.getStatus());
        } catch (IOException e) {
            LOG.warn("CategoryMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return vendor;
    }

    @Override
    public Vendor map(VendorForm vendorForm, Vendor vendor) throws UnitedSuppliesException {
        LOG.info("Map Vendor Form to Updated Vendor Entity");
        try {
            vendor.setVendorCode(vendorForm.getVendorCode());
            vendor.setVendorName(vendorForm.getVendorName());
            vendor.setCompanyName(vendorForm.getCompanyName());
            vendor.setMobileNo(vendorForm.getMobileNo());
            vendor.setAlternateNo(vendorForm.getAlternateNo());
            vendor.setEmail(vendorForm.getEmail());
            vendor.setGstNo(vendorForm.getGstNo());
            vendor.setPanNo(vendorForm.getPanNo());
            vendor.setAcHolderName(vendorForm.getAcHolderName());
            vendor.setBankAcNo(vendorForm.getBankAcNo());
            vendor.setIfscCode(vendorForm.getIfscCode());
            vendor.setTelephone(vendorForm.getTelephone());
            vendor.setAddress(vendorForm.getAddress());
            vendor.setCity(vendorForm.getCity());
            vendor.setPinCode(vendorForm.getPinCode());
            if (vendorForm.getEditSignature() != null
                    && !vendorForm.getEditSignature().isEmpty()) {
                vendor.setOriginalFileName(vendorForm.getEditSignature().getOriginalFilename());
                vendor.setImage(vendorForm.getEditSignature().getBytes());
                vendor.setCreatedDateAsFolderName(
                        localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                vendor.setVendorSignature(getS3UrlFromAttachment(vendorForm.getEditSignature(),VENDOR_SIGNATURE_PREFIX,amazonS3Client));
            }
            vendor.setActive(vendorForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            vendor.setUpdatedBy(getCurrentUser());
            vendor.setUpdatedDate(getInstant());
        } catch (IOException e) {
            LOG.warn("CategoryMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return vendor;
    }

    private String imagePath(Vendor vendor) throws UnitedSuppliesException {
        return imageWriter.writeImage(
                vendor.getImage(),
                vendor.getCreatedDateAsFolderName(),
                String.valueOf(AppUtils.timestamp()));
    }

    @Override
    public VendorForm remap(Vendor vendor) throws UnitedSuppliesException {
        LOG.info("Map Vendor Entity to Vendor Form");
        VendorForm vendorForm = new VendorForm();
        vendorForm.setId(String.valueOf(vendor.getId()));
        vendorForm.setVendorCode(vendor.getVendorCode());
        vendorForm.setVendorName(vendor.getVendorName());
        vendorForm.setCompanyName(vendor.getCompanyName());
        vendorForm.setMobileNo(vendor.getMobileNo());
        vendorForm.setAlternateNo(vendor.getAlternateNo());
        vendorForm.setEmail(vendor.getEmail());
        vendorForm.setGstNo(vendor.getGstNo());
        vendorForm.setPanNo(vendor.getPanNo());
        vendorForm.setAcHolderName(vendor.getAcHolderName());
        vendorForm.setBankAcNo(vendor.getBankAcNo());
        vendorForm.setIfscCode(vendor.getIfscCode());
        vendorForm.setTelephone(vendor.getTelephone());
        vendorForm.setAddress(vendor.getAddress());
        vendorForm.setCity(vendor.getCity());
        vendorForm.setPinCode(vendor.getPinCode());
        vendorForm.setActive(vendor.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        vendorForm.setUpdatedBy(vendor.getUpdatedBy());
        vendorForm.setUpdatedDate(vendor.getUpdatedDate());
        vendorForm.setCreatedBy(vendor.getCreatedBy());
        vendorForm.setCreatedDate(vendor.getCreatedDate());
        return vendorForm;
    }
}
