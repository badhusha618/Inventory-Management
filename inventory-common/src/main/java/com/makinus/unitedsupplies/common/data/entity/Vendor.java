package com.makinus.unitedsupplies.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "VENDOR")
@JsonIgnoreProperties({"createdBy", "createdDate", "updatedBy", "updatedDate", "deleted"})
public class Vendor {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VENDOR_CODE")
    private String vendorCode;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "ALTERNATE_NO")
    private String alternateNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GST_NO")
    private String gstNo;

    @Column(name = "PAN_NO")
    private String panNo;

    @Column(name = "BANK_ACCOUNT_NO")
    private String bankAcNo;

    @Column(name = "ACC_HOLDER_NAME")
    private String acHolderName;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "ADDRESS")
    private String address;

    @Column (name = "CITY")
    private String city;

    @Column (name = "PIN_CODE")
    private String pinCode;

    @ApiModelProperty(hidden = true)
    @Column(name = "VENDOR_SIGNATURE")
    private String vendorSignature;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "DELETED")
    private String deleted;

    @ApiModelProperty(hidden = true)
    @Transient
    private byte[] image;

    @ApiModelProperty(hidden = true)
    @Transient
    private String originalFileName;

    @ApiModelProperty(hidden = true)
    @Transient
    private String createdDateAsFolderName;

    @Transient private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateNo() {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo) {
        this.alternateNo = alternateNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public void setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
    }

    public String getAcHolderName() {
        return acHolderName;
    }

    public void setAcHolderName(String acHolderName) {
        this.acHolderName = acHolderName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getVendorSignature() {
        return vendorSignature;
    }

    public void setVendorSignature(String vendorSignature) {
        this.vendorSignature = vendorSignature;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getCreatedDateAsFolderName() {
        return createdDateAsFolderName;
    }

    public void setCreatedDateAsFolderName(String createdDateAsFolderName) {
        this.createdDateAsFolderName = createdDateAsFolderName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendor vendor = (Vendor) o;
        return Objects.equals(id, vendor.id) &&
                Objects.equals(vendorCode, vendor.vendorCode) &&
                Objects.equals(vendorName, vendor.vendorName) &&
                Objects.equals(companyName, vendor.companyName) &&
                Objects.equals(mobileNo, vendor.mobileNo) &&
                Objects.equals(alternateNo, vendor.alternateNo) &&
                Objects.equals(email, vendor.email) &&
                Objects.equals(gstNo, vendor.gstNo) &&
                Objects.equals(panNo, vendor.panNo) &&
                Objects.equals(bankAcNo, vendor.bankAcNo) &&
                Objects.equals(acHolderName, vendor.acHolderName) &&
                Objects.equals(ifscCode, vendor.ifscCode) &&
                Objects.equals(telephone, vendor.telephone) &&
                Objects.equals(address, vendor.address) &&
                Objects.equals(city, vendor.city) &&
                Objects.equals(pinCode, vendor.pinCode) &&
                Objects.equals(vendorSignature, vendor.vendorSignature) &&
                Objects.equals(createdBy, vendor.createdBy) &&
                Objects.equals(createdDate, vendor.createdDate) &&
                Objects.equals(updatedBy, vendor.updatedBy) &&
                Objects.equals(updatedDate, vendor.updatedDate) &&
                Objects.equals(active, vendor.active) &&
                Objects.equals(deleted, vendor.deleted) &&
                Arrays.equals(image, vendor.image) &&
                Objects.equals(originalFileName, vendor.originalFileName) &&
                Objects.equals(createdDateAsFolderName, vendor.createdDateAsFolderName) &&
                Objects.equals(imageUrl, vendor.imageUrl);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, vendorCode, vendorName, companyName, mobileNo, alternateNo, email, gstNo, panNo, bankAcNo, acHolderName, ifscCode, telephone, address, city, pinCode, vendorSignature, createdBy, createdDate, updatedBy, updatedDate, active, deleted, originalFileName, createdDateAsFolderName, imageUrl);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
