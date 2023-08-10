package com.makinus.unitedsupplies.common.data.dao.filter.vendor;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author kings
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorFilterRequest {

    @ApiModelProperty(notes = "Vendor Code", example = "1", required = true, position = 1)
    private String vendorCode;

    @ApiModelProperty(notes = "Vendor Name", example = "kingson", position = 2)
    private String vendorName;

    @ApiModelProperty(notes = "Company Name", example = "kingson*co", position = 3)
    private String companyName;

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

}
