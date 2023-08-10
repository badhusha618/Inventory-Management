/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.service.excel;

import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ammar
 */
@Component
@Qualifier("VendorExcelWriter")
public class VendorExcelWriter implements GenericWriter<List<VendorExcelDTO>> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final String[] columns = {"Vendor ID", "Vendor Name", "Proprietor Name", "Mobile No",  "E-mail", "IFS Code", "PAN No", "GST No",
            "Bank A/c No", "Address", "City", "Pin Code"};

    @Autowired
    private VendorService vendorService;

    @Override
    public void write(List<VendorExcelDTO> vendorExcelDTOS, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Enter write method - {}", this.getClass().getSimpleName());
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vendor Details");
            Row headerRow = sheet.createRow(0);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
            vendorExcelDTOS.forEach(vendor -> {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue((vendor.getId()));
                row.createCell(1).setCellValue((vendor.getVendorName()));
                row.createCell(2).setCellValue((vendor.getCompanyName()));
                row.createCell(3).setCellValue((vendor.getMobileNo()));
                row.createCell(4).setCellValue((vendor.getEmail()));
                row.createCell(5).setCellValue((vendor.getGstNo()));
                row.createCell(6).setCellValue((vendor.getPanNo()));
                row.createCell(7).setCellValue((vendor.getIfscCode()));
                row.createCell(8).setCellValue((vendor.getBankAcNo()));
                row.createCell(9).setCellValue((vendor.getAddress()));
                row.createCell(10).setCellValue((vendor.getCity()));
                row.createCell(11).setCellValue((vendor.getPinCode()));
                row.getCell(1).setCellStyle(dateCellStyle);
            });

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(response.getOutputStream());
        } catch (IOException io) {
            throw new UnitedSuppliesException("Exception occurred while exporting vendor details as excel", io);
        }
    }

}
