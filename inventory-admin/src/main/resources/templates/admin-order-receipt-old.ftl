<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Purchase Order</title>
    <Style type ="text/css">
	.clearfix:after {
      content: "";
      display: table;
      clear: both;
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
    }
    #logo {
      text-align: left;
      margin-bottom: 2px;
      border-bottom: 1px solid;
    }
    #logo img {
      padding-left: 5px;
      width: 140px;
      height: 100px;
    }
    h1 {
      color: #5D6975;
      font-size: 1.5em;
      line-height: 1.4em;
      font-weight: normal;
      text-align: left;
      margin: 0 0 20px 0;
      background: url(dimension.png);
    }
    h4 {
      font-size: 15px;
    }
    #address {
      float: right;
      text-align: right;
      font-size: 15px;
      width: 7cm;
      margin-top: 20px;
    }
    .delivery-address {
      float: left;
      text-align: left;
      font-size: 12px;
      width: 8cm;
    }
    #date {
      float: right;
      text-align: right;
      font-size: 15px;
      width: 3cm;
    }
    table {
      width: 18cm;
      border-collapse: collapse;
      border-spacing: 0;
      margin-bottom: 20px;
    }
    #charge-table{
      width: 8cm;
	  float: right;
    }
    table tr:nth-child(2n-1) td {
      background: #F5F5F5;
    }
    table th, table td {
      text-align: center;
      padding: 10px;
    }
    .product {
      text-align: left;
    }
    .quantity {
      width: 10%;
    }
    .rate{
      width: 20%;
      text-align: right;
      padding-right: 50px;
    }
    .total {
        border-top: 1px solid #C1CED9;
    }
    .charge-total {
      text-align: left;
      padding-left: 20px;
      font-size: 15px;
    }
    .price-total {
      text-align: right;
      padding-right: 50px;
      font-size: 15px;
    }
    .charge {
      text-align: left;
      padding-left: 20px;
    }
    .price {
      text-align: right;
      padding-right: 50px;
    }
    table th {
      padding: 10px;
      color: #5D6975;
      border-bottom: 1px solid #C1CED9;
      white-space: nowrap;
      font-weight: normal;
    }
	</style>
</head>
<body>
<header class="clearfix">
    <div id="logo">
        <img src="${usLogo}">
        <div id="address" class="clearfix">
            <div><strong>United Supplies</strong></div>
            <div>25A, Ambai Road, Melapalayam</div>
            <div>Tirunelveli-627032</div>
            <div>Mobile: +91 9042505024</div>
        </div>
    </div>
</header>
<main>
    <div id="date">
        <div>${orderDate}</div>
    </div>
    <h1>ORDER NO #${order.id}</h1>
    <h4>Product Details</h4>
    <table>
        <thead>
        <tr>
            <th class="product">PRODUCT NAME</th>
            <th class="quantity">QUANTITY</th>
            <th>SALE RATE (Rs)</th>
            <th>NET RATE (Rs)</th>
            <th>TRANSPORT (Rs)</th>
        </tr>
        </thead>
        <tbody>
        <#list productOrders as productOrder>
        <tr>
            <td class="product">${productOrder.productName}</td>
            <td class="quantity">${productOrder.proQuantity}</td>
            <td class="rate">${productOrder.prodSaleRate}</td>
            <td class="rate">${productOrder.proQuantity}</td>
            <td class="rate">${productOrder.transportCharges}</td>
        </tr>
        </#list>
        </tbody>
        <tfoot>
        <tr>
            <td class="total"></td>
            <td class="total"></td>
            <td class="total rate">TOTAL</td>
            <td class="total rate">${order.subTotal}</td>
            <td class="total rate">${order.transportCharges}</td>
        </tr>
        </tfoot>
    </table>git
    <div class="row">
        <table id="charge-table">
            <thead>
            <tbody>
            <tr>
                <td class="charge">Product Price (Rs)</td>
                <td class="price">${order.subTotal}</td>
            </tr>
            <tr>
                <td class="charge">Transportation (Rs)</td>
                <td class="price">${order.transportCharges}</td>
            </tr>
            <tr>
                <td class="charge">Service Charges (Rs)</td>
                <td class="price">${order.serviceCharges}</td>
            </tr>
            <tr>
                <td class="charge-total"><b>Order Total (Rs)</b></td>
                <td class="price-total"><b>${order.orderTotal}</b></td>
            </tr>
            <tr>
                <td class="charge-total" style="color:green"><b>Paid Amount (Rs)</b></td>
                <td class="price-total"><b>${order.paidAmount}</b></td>
            </tr>
            <tr>
                <td class="charge-total" style="color:red"><b>Balance Amount (Rs)</b></td>
                <td class="price-total"><b>${order.orderTotal - order.paidAmount}</b></td>
            </tr>
            </tbody>
        </table>
        <div class="delivery-address">
            <h4>Delivery Address</h4>
            <div>
                ${deliveryAddress.name}<br/>
                ${deliveryAddress.address}<br/>
                ${deliveryAddress.cityDisplay}<br/>
                ${deliveryAddress.postalCode}
            </div>
            <h4>Billing Address</h4>
            <div>
                ${billingAddress.name}<br/>
                ${billingAddress.address}<br/>
                ${billingAddress.cityDisplay}<br/>
                ${billingAddress.postalCode}
            </div>
            <br/>
            <br/>
            <h3>Payment Type : ${order.paymentDisplay}</h3>
        </div>
    </div>
</main>
</body>
</html>