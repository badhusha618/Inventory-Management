<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Product Invoice</title>
    <Style type="text/css">

    body {
       position: relative;
       width: 18cm;
       margin: 0 auto;
       color: #001028;
       background: #FFFFFF;
       font-family: Arial, sans-serif;
       font-size: 12px;
       font-family: Arial;
       line-height: 1.5;
    }

    div.border {
       border: 1px solid #000;
    }

    table, th {
       border: 1px solid black;
    }

    table, td.border {
       border: 1px solid black;
    }

    table, td.border-x {
       border-right: 1px solid black;
       border-left: 1px solid black;
    }

    table {
       border-collapse: collapse;
       width: 95%;
       height: 150px;
       margin-left:17px;
       margin-bottom:17px;
    }

    td {
       vertical-align: top;
    }

    .red {
       color: red;
    }

    .align-center {
       text-align: center;
    }

    .align-right {
       text-align: right;
       margin-right:25px;
    }

    .align-left {
       text-align: left;
       margin-left:25px;
    }

    .row-border {
       border-top: none;
       border-bottom: none;
    }

    #logo {
       width: 120px;
       height: 120px;
       float: right;
       margin-right: 10px;
       margin-top: 10px;
       margin-bottom: 10px;
    }

    #sign {
       width: 120px;
       height: 70px;
       float: right;
       margin-right: 20px;
    }

    </Style>
</head>
<body>
<div class="border">
    <center><b>Tax Invoice</b></center>
    <center><p>${productInvoiceForm.invoiceHeaderDisplay}</p></center>
    <div>
        <table>
            <tr>
                <td class="border" colspan="2">
                    Sold By: <br/>
                        &nbsp;&nbsp;${orderFulfillment.vendorName}<br/>
                        &nbsp;&nbsp;${orderFulfillment.address}<br/>
                        &nbsp;&nbsp;${orderFulfillment.cityDisplay}<br/>
                        &nbsp;&nbsp;${orderFulfillment.pinCode}<br/>
			        <#if orderFulfillment.panNo?has_content>
                        &nbsp;&nbsp;Pan No.: ${orderFulfillment.panNo}<br/>
                    </#if>
			        <#if orderFulfillment.gstNo?has_content>
                        &nbsp;&nbsp;GST No.: ${orderFulfillment.gstNo}
                    </#if>
                </td>
                <td colspan="5">
                    Ordered Through:<br/>
                    &nbsp;&nbsp;United Supplies & Ecommerce (OPC) Pvt. Ltd.<br/>
                    &nbsp;&nbsp;24 C , Ambai Road, Melapalayam<br/>
                    &nbsp;&nbsp;Tirunelveli-627005<br/>
                    &nbsp;&nbsp;support@unitedsupplies.in
                </td>
                <td colspan="3" class="align-right" style="padding-right:40px;padding-top:20px">
                    <img height="80px" width="80px" id="usLogo" src="${usLogo}"/>
                </td>
            </tr>
            <tr>
                <td class="border" colspan="2">
                    Order No: ${order.orderNo}<br/>
                    Order Div: ${orderFulfillment.fulfillmentRef}<br/>
                    Order Date: ${orderDate}<br/>
                    Invoice No: ${productInvoiceForm.invoiceNo}<br/>
                    Invoice Date: ${productInvoiceForm.invoiceDate}<br/>
                    State: TAMILNADU<br/>
                    State Code: 33<br/>
                </td>
                <td class="border" colspan="4">
                    Delivery Addess:<br/>
                    &nbsp;&nbsp;Mr./Mrs. ${deliveryAddress.name}<br/>
                    &nbsp;&nbsp;${deliveryAddress.address}<br/>
                    &nbsp;&nbsp;${deliveryAddress.cityDisplay}&nbsp;-&nbsp;${deliveryAddress.postalCode}
                </td>
                <td class="border" colspan="4">
                    Billing Addess:<br/>
                    &nbsp;&nbsp;Mr./Mrs. ${billingAddress.name}<br/>
                    &nbsp;&nbsp;${billingAddress.address}<br/>
                    &nbsp;&nbsp;${billingAddress.city}&nbsp;-&nbsp;${billingAddress.postalCode}
                </td>
            </tr>
            <tr>
                <td class="border-x" colspan="10">
                    <br/>
                </td>
            </tr>
            <tr>
                <th style="width:35px;">S.No.</th>
                <th style="width:35%; text-align: center ;">
                    Product Details
                </th>
                <th>
                    Qty
                </th>
                <th>
                    Price
                </th>
                <th style=" text-align: center ;">
                    Net Amount
                </th>
                <th>
                    Discount
                </th>
                <th style=" text-align: center ;">
                    Total Taxable Value
                </th>
                <th>
                    CGST
                </th>
                <th>
                    SGST
                </th>
                <th style="text-align: center ;">
                    Total Amount
                </th>
            </tr>
            <#assign Total = 0/>
            <#list productOrders as productOrder>
            <#assign prodSaleRateWOGst = productOrder.prodSaleRate / ((100 + productOrder.rateOfCgst + productOrder.rateOfSgst) / 100)/>
            <#assign prodNetRateWOGst = productOrder.proQuantity *  prodSaleRateWOGst/>
            <#assign prodNetCgst =  prodNetRateWOGst*(productOrder.rateOfCgst / 100)/>
            <#assign prodNetSgst = prodNetRateWOGst*(productOrder.rateOfSgst / 100)/>
            <#assign prodLineTotal = productOrder.proQuantity * productOrder.prodSaleRate/>

            <#assign ShipmentWOGst = productOrder.transportCharges / ((100 + productOrder.rateOfCgst + productOrder.rateOfSgst) / 100)/>
            <#assign ShipmentNetWOGst = ShipmentWOGst/>
            <#assign ShipmentNetCgst = ShipmentNetWOGst*(productOrder.rateOfCgst / 100)/>
            <#assign ShipmentNetSgst = ShipmentNetWOGst*(productOrder.rateOfSgst / 100)/>
            <#assign ShipmentLineTotal = productOrder.transportCharges/>

            <#if productOrder.loadingCharges?has_content>
            <#assign LoadingWOGst = productOrder.loadingCharges / ((100 + productOrder.rateOfCgst + productOrder.rateOfSgst) / 100)/>
            <#assign LoadingNetWOGst = LoadingWOGst/>
            <#assign LoadingNetCgst = LoadingNetWOGst*(productOrder.rateOfCgst / 100)/>
            <#assign LoadingNetSgst = LoadingNetWOGst*(productOrder.rateOfSgst / 100)/>
            <#assign LoadingLineTotal = productOrder.loadingCharges/>
            </#if>



            <tr>
                <td class="border-x">${productOrder?index + 1}</td>
                <td class="border-x" style="width:25%;">
                    ${productOrder.productName};<br/>
                    SKUcode: ${productOrder.productCode}<br/>
                    <#if productOrder.taxInclusive == "T">
                    HSN: ${productOrder.hsnCode}(${productOrder.rateOfCgst}%CGST+${productOrder.rateOfSgst}%SGST)<br/>
                    </#if>
                    UOM: ${productOrder.unitCode};<br/>
                </td>

                <td class="border-x" style="text-align:center">${productOrder.proQuantity}</td>
                <td class="border-x" style="text-align:right">${prodSaleRateWOGst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${prodNetRateWOGst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">0.00</td>
                <td class="border-x" style="text-align:right">${prodNetRateWOGst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${prodNetCgst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${prodNetSgst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${prodLineTotal?string["##0.00"]}</td>
            </tr>
            <tr>
                <td class="border-x"></td>
                <td class="border-x" style="width:25%;">
                    <b>Shipment</b>
                </td>
                <td class="border-x" style="text-align:center">1</td>
                <td class="border-x" style="text-align:right">${ShipmentWOGst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${ShipmentNetWOGst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">0.00</td>
                <td class="border-x" style="text-align:right">${ShipmentNetWOGst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${ShipmentNetCgst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${ShipmentNetSgst?string["##0.00"]}</td>
                <td class="border-x" style="text-align:right">${ShipmentLineTotal?string["##0.00"]}</td>
            </tr>
            <#if (productOrder.loadingCharges > 0)>
                <tr>
                    <td class="border-x"></td>
                    <td class="border-x" style="width:25%;">
                        <b>Loading</b>
                    </td>
                    <td class="border-x" style="text-align:center">1</td>
                    <td class="border-x" style="text-align:right">${LoadingWOGst?string["##0.00"]}</td>
                    <td class="border-x" style="text-align:right">${LoadingNetWOGst?string["##0.00"]}</td>
                    <td class="border-x" style="text-align:right">0.00</td>
                    <td class="border-x" style="text-align:right">${LoadingNetWOGst?string["##0.00"]}</td>
                    <td class="border-x" style="text-align:right">${LoadingNetCgst?string["##0.00"]}</td>
                    <td class="border-x" style="text-align:right">${LoadingNetSgst?string["##0.00"]}</td>
                    <td class="border-x" style="text-align:right">${LoadingLineTotal?string["##0.00"]}</td>
                </tr>
                </#if>
                <tr>
                    <td class="border-x">&nbsp;</td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                    <td class="border-x"></td>
                </tr>



            <#assign Total += (productOrder.proQuantity * productOrder.prodSaleRate)+productOrder.transportCharges+productOrder.loadingCharges/>



            </#list>

            <tr>
                <td class="border" colspan="10">
                    <br/>
                </td>
            </tr>
            <tr>
                <td class="border" colspan="9">
                    <br/>
                </td>
                <td class="border" style="text-align:right">${Total?string["##0.00"]}</td>
            </tr>
            <tr>
                <td class="border" colspan="10">
                    <center>
                        Amount In Words: ${Utils.convertToINRCurrency(Total)}
                    </center>
                </td>
            </tr>
        </table>
        <div class="align-left" style="margin-left:20px;margin-top:0px">Whether tax is payable under reverse charge - No</div>
        <div class="align-right" style="margin-right:40px;margin-top:0px"><b>For ${orderFulfillment.vendorName}</b></div>
    </div><br/>
    <img id="sign" height="40px" width="120px" alt={vendorSign} src="${vendorSign}"/>
    <h5 class="align-right" style="margin-top:60px;">Authorized Signatory</h5>
    <div><span class="align-left">E. & O.E.</span><span style="margin-left:60px;">The goods sold are intended for end user consumption not for resale</span></div>
    <br/>
</div>
</body>
</html>