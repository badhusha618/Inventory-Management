/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.service.excel;

import com.makinus.Inventory.common.data.entity.Specification;
import com.makinus.Inventory.common.data.service.product.ProductService;
import com.makinus.Inventory.common.exception.InventoryException;
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
 * @author Bad_sha
 */
@Component
@Qualifier("ProductExcelWriter")
public class ProductExcelWriter implements GenericWriter<List<ProductExcelDTO>> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final String[] columns = {"Product ID", "SKU", "Product Name", "Parent Category", "Sub Category", "HSN Code", "Rate of CGST",
            "Rate of SGST", "Rate of IGST", "Minimum Order Quantity", "Brand", "Quality", "Grade", "Type", "Crusher", "Unit", "Weight",
            "Material", "Color","Size","Specification","Vendor", "Mrp Rate", "Sale Rate", "Pin Code"};

    @Autowired
    private ProductService productService;

    @Override
    public void write(List<ProductExcelDTO> productExcelDTOS, HttpServletResponse response) throws InventoryException {
        LOG.info("Enter write method - {}", this.getClass().getSimpleName());
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Product Details");
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
            productExcelDTOS.forEach(product -> {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getProductCode());
                row.createCell(2).setCellValue(product.getProductName());
                row.createCell(3).setCellValue(product.getParentCategory());
                row.createCell(4).setCellValue(product.getSubCategory());
                row.createCell(5).setCellValue(product.getHsnCode());
                row.createCell(6).setCellValue((product.getRateOfCgst() == null) ? "" : product.getRateOfCgst());
                row.createCell(7).setCellValue((product.getRateOfSgst() == null) ? "" : product.getRateOfSgst());
                row.createCell(8).setCellValue((product.getRateOfIgst() == null) ? "" : product.getRateOfIgst());
                row.createCell(9).setCellValue(product.getMinOrderQty());
                row.createCell(10).setCellValue(product.getBrand());
                row.createCell(11).setCellValue(product.getQuality());
                row.createCell(12).setCellValue(product.getGrade());
                row.createCell(13).setCellValue(product.getType());
                row.createCell(14).setCellValue(product.getCrusher());
                row.createCell(15).setCellValue(product.getUnit());
                row.createCell(16).setCellValue(product.getWeight());
                row.createCell(17).setCellValue(product.getMaterial());
                row.createCell(18).setCellValue(product.getColor());
                row.createCell(19).setCellValue(product.getSize());
                row.createCell(20).setCellValue(product.getSpecification());

                List<ProdVendorExcelDTO> prodVendorExcelDTOs = product.getProdVendorExcelDTOs();
                prodVendorExcelDTOs.forEach(pv -> {
                    int vendorRowIndex = prodVendorExcelDTOs.indexOf(pv);
                    if (vendorRowIndex > 0) {
                        Row vendorRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        vendorRow.createCell(21).setCellValue(pv.getVendor());
                        vendorRow.createCell(22).setCellValue(pv.getMrpRate());
                        vendorRow.createCell(23).setCellValue(pv.getSaleRate());
                        vendorRow.createCell(24).setCellValue(pv.getPinCode());
                    } else {
                        row.createCell(21).setCellValue(pv.getVendor());
                        row.createCell(22).setCellValue(pv.getMrpRate());
                        row.createCell(23).setCellValue(pv.getSaleRate());
                        row.createCell(24).setCellValue(pv.getPinCode());
                    }
                });
            });

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(response.getOutputStream());
        } catch (IOException io) {
            throw new InventoryException("Exception occurred while exporting product details as excel", io);
        }
    }

}
