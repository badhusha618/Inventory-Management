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

import com.makinus.Inventory.common.data.reftype.ProdOrderStatus;
import com.makinus.Inventory.common.data.reftype.PaymentType;
import com.makinus.Inventory.common.data.service.order.OrderService;
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
@Qualifier("OrderExcelWriter")
public class OrderExcelWriter implements GenericWriter<List<OrderExcelDTO>> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final String[] columns = {"Order No","Order Date", "Order Number", "Order Amount", "Shipment Charge","Loading Charge","Service Charge","Delivery Charge","Order Total","Delivery Address1","Delivery Address2","Payment Type", "Paid Amount","Distance","Status"};

    @Autowired
    private OrderService orderService;

    @Override
    public void write(List<OrderExcelDTO> orderExcelDTOS, HttpServletResponse response) throws InventoryException {
        LOG.info("Enter write method - {}", this.getClass().getSimpleName());
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Order Details");
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
            orderExcelDTOS.forEach(order -> {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                row.createCell(0).setCellValue((order.getId()));
                row.createCell(1).setCellValue((order.getOrderDate()));
                row.createCell(2).setCellValue((order.getOrderNo()));
                row.createCell(3).setCellValue(String.valueOf(order.getSubTotal()));
                row.createCell(4).setCellValue(String.valueOf(order.getTransportCharges()));
                row.createCell(5).setCellValue(String.valueOf(order.getLoadingCharges()));
                row.createCell(6).setCellValue(String.valueOf(order.getServiceCharges()));
                row.createCell(7).setCellValue(String.valueOf(order.getDeliveryCharges()));
                row.createCell(8).setCellValue(String.valueOf(order.getOrderTotal()));
                row.createCell(9).setCellValue((order.getDelAddressRef()));
                row.createCell(10).setCellValue((order.getBillAddressRef()));
                row.createCell(11).setCellValue((PaymentType.statusMatch(order.getPaymentType())).getDisplay());
                row.createCell(12).setCellValue(String.valueOf(order.getPaidAmount()));
                row.createCell(13).setCellValue((order.getDistanceBtwLoc()));
                row.createCell(14).setCellValue((ProdOrderStatus.statusMatch(order.getStatus())).getDisplay());
                row.getCell(1).setCellStyle(dateCellStyle);
            });

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(response.getOutputStream());
        } catch (IOException io) {
            throw new InventoryException("Exception occurred while exporting order details as excel", io);
        }
    }

}
