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

import com.makinus.Inventory.common.data.service.prodorder.ProductOrderService;
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
@Qualifier("ProductOrderExcelWriter")
public class ProductOrderExcelWriter implements GenericWriter<List<ProductOrderExcelDTO>> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final String[] columns = {"Order No", "Order Date", "Order Status", "Product Name", "Product Code", "Quantity", "Vendor", "Product Order Status", "Sale Rate", "Transport Charges", "Loading Charges", "Total Amount"};

    @Autowired
    private ProductOrderService productOrderService;

    @Override
    public void write(List<ProductOrderExcelDTO> productOrderExcelDTOS, HttpServletResponse response) throws InventoryException {
        LOG.info("Enter write method - {}", this.getClass().getSimpleName());
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Product Order Details");
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
            productOrderExcelDTOS.forEach(productOrder -> {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue(productOrder.getOrderRef());
                row.createCell(1).setCellValue(productOrder.getOrderDate());
                row.createCell(2).setCellValue(productOrder.getOrderStatus());
                row.createCell(3).setCellValue(productOrder.getProductName());
                row.createCell(4).setCellValue(productOrder.getProductCode());
                row.createCell(5).setCellValue(productOrder.getProQuantity());
                row.createCell(6).setCellValue(productOrder.getVendorNameWithCode());
                row.createCell(7).setCellValue(productOrder.getProductOrderStatus());
                row.createCell(8).setCellValue(productOrder.getProdSaleRate());
                row.createCell(9).setCellValue(productOrder.getTransportCharges());
                row.createCell(10).setCellValue(productOrder.getLoadingCharges());
                row.createCell(11).setCellValue(productOrder.getTotalCharges());
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
