<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Service Invoice to the Seller</title>
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
       color: red;
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
        <center><p>${serviceInvoiceForm.invoiceHeaderDisplay}</p></center>
        <table class="no-border">
                <td class="no-border">
                    <div>
                        <p><h3>United Supplies & Ecommerce (OPC) Private Limited</h3>
                            24C, Ambai Road, Melapalayam,<br/>
                             Tirunelveli,Tamilnadu,<br/>
                             India-627005<br/>
                             support@Inventory.in<br/>
                            PAN No.&nbsp;&nbsp; AACCU6937B <br/>
                            GST No.&nbsp;&nbsp; 33AACU693781ZJ </p>
                    </div>
                </td>
                <td class="no-border"><img id="logo" height="80px" width="80px" src="${usLogo}"/></td>
            </tr>
        </table>
        <table class="border">
            <tr>
               <td style="border: none; width:35%;"><B>Order No</B></td>
               <td style="border: none;"><b>:</b></td>
               <td style="border: none;">${orderNo}</td>
            </tr>
            <tr>
             <td style="border: none;"><B>Order Fulfillment</B></td>
              <td style="border: none;"><b>:</b></td>
             <td style="border: none;">${orderFulfillment.fulfillmentRef}</td>
            </tr>
            <tr>
             <td style="border: none;"><B>Order Date</B></td>
              <td style="border: none;"><b>:</b></td>
             <td style="border: none;">${orderDate}</td>
            </tr>
            <tr>
             <td style="border: none;"><B>Invoice No</B></td>
              <td style="border: none;"><b>:</b></td>
             <td style="border: none;">${serviceInvoiceForm.invoiceNo}</td>
            </tr>
            <tr>
             <td style="border: none;"><B>Invoice Date</B></td>
              <td style="border: none;"><b>:</b></td>
             <td style="border: none;">${sellerServiceInvoiceDate}</td>
            </tr>
            <tr>
             <td style="border: none;"><B>State</B></td>
              <td style="border: none;"><b>:</b></td>
             <td style="border: none;">Tamilnadu</td>
            </tr>
            <tr>
             <td style="border: none;"><B>State Code</B></td>
              <td style="border: none;"><b>:</b></td>
             <td style="border: none;">33</td>
            </tr>
            </table>
            <table class="border">
            <tr>
                <td style="vertical-align:top;width:34%;">
                    Service To:
                    <div style="padding-left:20px">
                         &nbsp;&nbsp;Mr./Mrs. ${orderFulfillment.vendorName}<br/>
                         &nbsp;&nbsp;${orderFulfillment.address}<br/>
                         &nbsp;&nbsp;${orderFulfillment.cityDisplay}&nbsp;-&nbsp;${orderFulfillment.pinCode}
                        <#if orderFulfillment.panNo?has_content>
                             &nbsp;&nbsp;Pan No.: ${orderFulfillment.panNo}<br/>
                        </#if>
                        <#if orderFulfillment.gstNo?has_content>
                             &nbsp;GST No.: ${orderFulfillment.gstNo}
                        </#if>
                    </div>
                </td>
                <td style="vertical-align:top;width:34%;">
                    Billing Address:
                    <div style="padding-left:20px">
                        &nbsp;&nbsp;Mr./Mrs. ${orderFulfillment.vendorName}<br/>
                        &nbsp;&nbsp;${orderFulfillment.address}<br/>
                        &nbsp;&nbsp;${orderFulfillment.cityDisplay}&nbsp;-&nbsp;${orderFulfillment.pinCode}
                        <#if orderFulfillment.panNo?has_content>
                        &nbsp;Pan No.${orderFulfillment.panNo}<br/>
                        </#if>
                        <#if orderFulfillment.gstNo?has_content>
                        &nbsp;GST No.${orderFulfillment.gstNo}
                        </#if>

                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <th style="width:40px"><center>S.No.</center></th>
                <th style="width:35%; text-align: center ;">Particular</th>
                <th>Qty</th>
                <th>Rate</th>
                <th style="text-align: center ;">Net Rate</th>
                <th>CGST</th>
                <th>SGST</th>
                <th style="text-align: center ;">Total Tax Amount</th>
                <th style="text-align: center ;">Total Amount</th>
            </tr>
            <tr>
                <td><center>1</center></td>
                <td>
                    Service charge for order fulfillment to Seller:<br/>
                    SAC:&nbsp;996111 &nbsp;(9%CGST+9%SGST)
                </td>
                <td class="align-right">1</td>
                <td class="align-right"> ${(sellerServiceChargeAmount / (1+(18 / 100)))?string["##0.00"]}</td>
                <td class="align-right"> ${(sellerServiceChargeAmount / (1+(18 / 100)))?string["##0.00"]}</td>
                <td class="align-right"> ${((sellerServiceChargeAmount / (1+(18 / 100)))*(9/100))?string["##0.00"]}</td>
                <td class="align-right"> ${((sellerServiceChargeAmount / (1+(18 / 100)))*(9/100))?string["##0.00"]}</td>
                <td class="align-right"> ${(sellerServiceChargeAmount - (sellerServiceChargeAmount / (1+(18/100))))?string["##0.00"]}</td>
                <td class="align-right"> ${sellerServiceChargeAmount?string["##0.00"]}</td>
            </tr>
            <tr>
                <td colspan="2"><center>Total</center></td>
                <td colspan="6">Amount in words:&nbsp; ${sellerAmountInWords}</td>
                <td class="align-right"> ${sellerServiceChargeAmount?string["##0.00"]}</td>
            </tr>
        </table>

        <div class="align-right">
            <p><strong>For&nbsp;&nbsp;&nbsp;&nbsp; United Supplies & Ecommerce</strong></p><br/>
            <img id="sign" height="40px" width="120px" src="${signLogo}"/>
            <p><strong>Authorized Signatory</strong> </p>
        </div>
        <div><span class="align-left">E. & O.E.</span></div>
        <br/>
    </div>
</div>
</body>
</html>
