<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Purchase Order</title>
    <style type="text/css">
        .service {
             width: 17cm;
            margin-top: 50px;
        }

        body {
            position: relative;
            width: 18cm;
            margin: 0 auto;
            color: #001028;
            background: #FFFFFF;
            font-family: Arial;
            font-size: 12px;
            line-height: 1.5;

        }

        .no-border {
            border: 0px; !important
        }

        table,
        th,
        td {
            border: 1px solid black;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-left:2px;
            margin-bottom:15px;

        }

        .align-right {
            text-align: right;
            margin-right: 25px;
        }

        .align-left {
            text-align: left;
            margin-left: 25px;
        }

        .tab1 th{
            font-size: 12px;
            text-align: center;

        }

        .tab1 td {
            padding: 5px;
            border-top: none;
            border-bottom: none;
        }

        .tab {
            border-collapse: collapse;
            width: 60%;
            float: right;
            line-height: 1.5;

        }

        .tab3  {
             border-collapse: collapse;
             width: 100%;
             float: left;
             line-height: 1.5;
             font-weight:normal;

        }

        .tab th {
            font-size: 12px;
            text-align: left;
            width: 50%;
        }

        .tab td {
            text-align: right;
            width: 40%;
        }

        .tab2 p{
            margin-bottom: 5px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="service" style="padding-left:20px;padding-right:20px;">
    <div class="border">
        <table class="no-border">
            <tr>
                <td class="no-border">
                    <div>

                        <strong>United Supplies & Ecommerce (OPC) Pvt. Ltd.</strong></br>
                        24C, Ambai Road, Melapalayam<br />
                        Tamilnadu,India-627005<br />
                        support@unitedsupplies.in<br />
                        PAN No.&nbsp;&nbsp; AACCU6937B <br />
                        GST No.&nbsp;&nbsp; 33AACU693781ZJ

                    </div>
                </td>
                <td class="no-border"><img id="logo" height="80px" width="80px" src="${usLogo}"/></td>
            </tr>
        </table>
        <hr style="border: 1px solid black; width: 100%">

        <center>
            <h3> PURCHASE ORDER</h3>
        </center>
        <table>

            <tr>
                <td style="border: none;"><b>Order Number</b></td>
                <td style="border: none;"><b>:</b></td>
                <td style="border: none;">${order.orderNo}</td>
            </tr>

            <tr>
                <td style="border: none;"><b>Order Date</b></td>
                <td style="border: none;"><b>:</b></td>
                <td style="border: none;">${orderDate}</td>
            </tr>

            <tr>
                <td style="border: none;"><b>Customer Name</b></td>
                <td style="border: none;"><b>:</b></td>
                <td style="border: none;">&nbsp;Mr./Mrs.${deliveryAddress.name}
             </tr>

            <tr >
                <td style="border: none; vertical-align: baseline;"><b>Delivery Location</b></td>
                <td style="border: none; vertical-align: baseline;"><b>:</b></td>
                <td style="border: none;">${deliveryAddress.address},
                   </br> ${deliveryAddress.cityDisplay}-${deliveryAddress.postalCode}</td>
            </tr><b>
    </div>
    </table>

    <div class="tab1">

        <table>
            <tr>
                <th style="width:30px;">S.No.</th>
                <th style="width:250px;">PRODUCT NAME & CODE</th>
                <th style="width:20px;">UOM</th>
                <th style="width: 10px;">QTY</th>
                <th>SALE PRICE</th>
                <th>NET RATE</th>
                <th>SHIPMENT</th>
                <th>LOADING</th>
                <th>TOTAL</th>
            </tr>
            <#list productOrders as productOrder>
            <#assign ProductNetRate = productOrder.prodSaleRate * productOrder.proQuantity/>
            <#assign ProductTotal = (productOrder.prodSaleRate * productOrder.proQuantity) + productOrder.transportCharges + productOrder.loadingCharges/>
            <tr>

            <td>${productOrder?index+1}</td>
            <td class="product">
                ${productOrder.productName}</br>
                SKU : ${productOrder.productCode}
            </td>
            <td>${productOrder.unitName}</td>
            <td class="quantity">${productOrder.proQuantity}</td>
            <td class="rate">${productOrder.prodSaleRate?string["##0.00"]}</td>
            <td class="rate">${ProductNetRate?string["##0.00"]}</td>
            <td>${productOrder.transportCharges?string["##0.00"]}</td>
            <td>${productOrder.loadingCharges?string["##0.00"]}</td>
            <td>${ProductTotal?string["##0.00"]}</td>
            </tr>
            </#list>
        </table>
    </div>

    <div class="tab">
        <table cellspacing="1" class="table table-bordered">
            <#setting number_format=",##0.00">
            <tr>
                <th>SUB TOTAL</th>
                <td>${order.subTotal}</td>
            </tr>
            <tr>
                <th>SHIPMENT CHARGE </th>
                <td>${order.transportCharges}</td>
            </tr>
            <tr>
                <th>LOADING CHARGE</th>
                <td>${order.loadingCharges}</td>
            </tr>

            <th>SERVICE CHARGE</th>
            <td>${order.serviceCharges}</td>
            </tr>

            <th>TOTAL ORDER VALUE</th>
            <td>${order.orderTotal}</td>
            </tr>
        </table>

    </div>
    <div class="tab3">
        <footer >
        <p>Note:</br>
        1.The order will be processed under our Terms & Conditions</br>
        2.Invoices will be generated and uploaded after the product is shipped.</br>
        3.UOM - Unit of Measure</p>
        </footer>
    </div>
</div>
</div>
</body>
</html>