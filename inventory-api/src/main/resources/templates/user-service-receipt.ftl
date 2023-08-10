<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Service Invoice to the Customer</title>
    <style type="text/css">

    .service {
      width: 17cm;
    }

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

    .no-border {
       border: 0px; !important
    }

    table, th, td {
       border: 1px solid black;
    }

    table {
       border-collapse: collapse;
       width: 95%;
       margin-left:17px;
       margin-bottom:17px;
    }

    .red {
       color: black;
    }

    .align-right {
       text-align: right;
       margin-right:25px;
    }

    .align-left {
       text-align: left;
       margin-left:25px;
    }

    </style>
</head>
<body>

<div class="service" style="padding-left:20px;padding-right:20px;">
    <div class="border">
        <h4><center>Tax Invoice</center></h4>
        <center><p class="red">${invoiceHeader}</p></center>
        <table class="no-border">
            <tr>
                <td class="no-border">
                    <div>

                        <p><h3>United Supplies & Ecommerce (OPC) Private Limited</h3>
                            24C, Ambai Road, Melapalayam<br/>
                            Tirunelveli, Tamilnadu,<br/>
                            India-627005<br/>
                            support@unitedsupplies.com<br/>
                            PAN No.&nbsp;&nbsp; AACCU6937B<br/>
                            GST No.&nbsp;&nbsp; 33AACU693781ZJ </p>
                    </div>
                </td>
                <td class="no-border"><img id="logo" height="80px" width="80px" src="${usLogo}"/></td>
            </tr>
        </table>
        <table class="border">
            <tr>
                <td style="vertical-align:top;width:34%;">
                    Order No:&nbsp;&nbsp;${order.orderNo}<br/>
                    Order Date:&nbsp;&nbsp;${orderDate}<br/>
                    Invoice No: &nbsp;&nbsp;${order.custServInvoiceNo}<br/>
                    Invoice Date:&nbsp;&nbsp;${custServInvoiceDate}<br/>
                    State: &nbsp;&nbsp;Tamilnadu<br/>
                    State Code: &nbsp;&nbsp;33
                </td>
                <td style="vertical-align:top;width:34%;">
                    Service To:
                    <div style="padding-left:20px">
                        &nbsp;&nbsp;Mr./Mrs. ${deliveryAddress.name}<br/>
                        &nbsp;&nbsp;${deliveryAddress.address}<br/>
                        &nbsp;&nbsp;${deliveryAddress.cityDisplay}&nbsp;-&nbsp;${deliveryAddress.postalCode}

                    </div>
                </td>
                <td style="vertical-align:top;width:34%;">
                    Billing Address:
                    <div style="padding-left:20px">
                         &nbsp;&nbsp;Mr./Mrs.${billingAddress.name}<br/>
                         &nbsp;&nbsp;${billingAddress.address}<br/>
                         &nbsp;&nbsp;${billingAddress.city}&nbsp;-&nbsp;${billingAddress.postalCode}
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <th style="width:50px"><center>S.No.</center></th>
                <th style="width:50px"><center>Particular</center></th>
                <th style="width:50px"><center>Qty</center></th>
                <th style="width:50px"><center>Rate</center></th>
                <th style="width:50px"><center>NetRate</center></th>
                <th style="width:50px"><center>CGST</center></th>
                <th style="width:50px"><center>SGST</center></th>
                <th style="width:50px"><center>Total Tax Amount</center></th>
                <th style="width:50px"><center>Total Amount</center></th>
            </tr>
            <tr>
                <td><center>1</center></td>
                <td>
                    Service charge for order fulfillment to Customer:<br/>
                    SAC:&nbsp;996111 &nbsp;(9%CGST+9%SGST)
                </td>
                <td><center>1</center></td>
                <td class="align-right">${(customerServiceChargeAmount /(1+(18 / 100)))?string["##0.00"]}</td>
                <td class="align-right">${(customerServiceChargeAmount /(1+(18 / 100)))?string["##0.00"]}</td>
                <td class="align-right"> ${((customerServiceChargeAmount / (1+(18 / 100)))*(9/100))?string["##0.00"]}</td>
                <td class="align-right"> ${((customerServiceChargeAmount / (1+(18 / 100)))*(9/100))?string["##0.00"]}</td>
                <td class="align-right"> ${(customerServiceChargeAmount - (customerServiceChargeAmount / (1+(18/100))))?string["##0.00"]}</td>
                <td class="align-right">${customerServiceChargeAmount?string["##0.00"]}</td>
            </tr>
            <tr>
                <td colspan="2"><center>Total</center></td>
                <td colspan="6">Amount in words:&nbsp; ${customerAmountInWords}</td>
                <td class="align-right">${customerServiceChargeAmount?string["##0.00"]}</td>
            </tr>
        </table>

        <div class="align-right">
            <p><strong>For&nbsp;&nbsp; United Supplies & Ecommerce</strong></p><br/>
            <img id="sign" height="40px" width="120px" src="${signLogo}"/>
            <p><strong>Authorized Signatory</strong> </p>
        </div>
        <div><span class="align-left">E. & O.E.</span></div>
        <br/>
    </div>
</div>
</body>
</html>
