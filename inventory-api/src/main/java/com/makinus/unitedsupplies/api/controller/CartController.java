/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller;

import com.makinus.unitedsupplies.api.controller.request.CartRequest;
import com.makinus.unitedsupplies.api.controller.response.CartProductResponse;
import com.makinus.unitedsupplies.api.controller.response.CartResponse;
import com.makinus.unitedsupplies.api.controller.response.ProductVendorDetail;
import com.makinus.unitedsupplies.api.data.mapping.UserCartMapper;
import com.makinus.unitedsupplies.api.utils.ApiUtils;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.reftype.AddressCategory;
import com.makinus.unitedsupplies.common.data.reftype.ApiResponseStatus;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.transport.TransportService;
import com.makinus.unitedsupplies.common.data.service.unit.UnitService;
import com.makinus.unitedsupplies.common.data.service.usercart.UserCartService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.api.utils.ApiUtils.imagePaths;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by sabique
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "Cart API Controller")
public class CartController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Value("${us.app.base.url}")
    private String baseUrl;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private UserCartMapper userCartMapper;

    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Get all products from cart by user")
    @GetMapping("/{userId}/cart")
    public ResponseEntity<CartResponse> getAllCartItems(@ApiParam("User Id") @PathVariable("userId") Long userId) throws UnitedSuppliesException {
        LOG.info("Get all cart items from the database- {} ", this.getClass().getSimpleName());
        CartResponse cartResponse = new CartResponse();
        List<UserCart> userCarts = userCartService.getAllUserCart(userId);
        if (!userCarts.isEmpty()) {
            cartResponse.setCartProducts(mapCartProducts(userCarts));
            cartResponse.setTotalAmount(String.format("%,.2f", (cartResponse.getCartProducts().stream().mapToDouble(this::totalAmountInCart).sum())));
        }
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    private Double totalAmountInCart(CartProductResponse cart) {
        return new BigDecimal(cart.getProductPrice()).add(new BigDecimal(cart.getTransCharge()).add(new BigDecimal(cart.getLoadingCharge()))).doubleValue();
    }

    private List<CartProductResponse> mapCartProducts(List<UserCart> userCarts) {
        List<Long> productIds = userCarts.stream().map(UserCart::getProductId).collect(Collectors.toList());
        Map<Long, Product> productMap = productService.productListByIds(productIds).stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        List<ProductVendor> productVendorList = productVendorService.productVendorListByProductIds(productIds);
        Map<Long, List<ProductVendor>> productVendorsMap = productVendorList.stream().collect(Collectors.groupingBy(ProductVendor::getProductId));
        Map<Long, Unit> unitMap = unitService.unitList().stream().collect(Collectors.toMap(Unit::getId, Function.identity()));
        Map<Long, Vendor> vendorMap = vendorService.listActiveVendorsByVendorIds(productVendorList.stream().map(ProductVendor::getVendorId).collect(Collectors.toList())).stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        return userCarts.stream().map(cart -> {
            Product product = productMap.get(cart.getProductId());
            List<ProductVendor> productVendors = productVendorsMap.getOrDefault(product.getId(), new ArrayList<>());
            Map<Long, ProductVendor> productVendorMap = productVendors.stream().collect(Collectors.toMap(ProductVendor::getId, Function.identity()));
            CartProductResponse cartProduct = new CartProductResponse();
            mapCartProduct(cartProduct, cart, product, unitMap.getOrDefault(product.getUnit(), new Unit()), productVendorMap.getOrDefault(cart.getProdVendorId(), new ProductVendor()));
            cartProduct.setProductVendorDetails(mapProductVendorList(productVendors, vendorMap));
            return cartProduct;
        }).collect(Collectors.toList());
    }

    private void mapCartProduct(CartProductResponse cartProduct, UserCart cart, Product product, Unit unit, ProductVendor productVendor) {
        cartProduct.setId(longToString(cart.getId()));
        cartProduct.setProductId(longToString(cart.getProductId()));
        cartProduct.setProdVendorId(longToString(cart.getProdVendorId()));
        cartProduct.setProductName(product.getProductName());
        cartProduct.setUnitId(longToString(unit.getId()));
        cartProduct.setUnitCode(unit.getUnitCode());
        cartProduct.setUnitName(unit.getUnitName());
        cartProduct.setSaleRate(decimalToString(productVendor.getSaleRate()));
        cartProduct.setQuantity(cart.getQuantity().toString());
        cartProduct.setDeliveryIn(byteToString(product.getDeliveryBy()));
        cartProduct.setTransCharge(formatIntoTwoPrecision(cart.getTransCharges()));
        cartProduct.setLoadingCharge(formatIntoTwoPrecision(cart.getLoadingCharges()));
        cartProduct.setProductPrice(formatIntoTwoPrecision(productVendor.getSaleRate().multiply(new BigDecimal(cart.getQuantity()))));
        cartProduct.setMinOrderQty(String.valueOf(product.getMinOrderQty()));
        cartProduct.setMaxOrderQuantity(String.valueOf(product.getMaxOrderQty()));
        cartProduct.setTaxInclusive(product.getTaxInclusive());
        cartProduct.setTransGroup(product.getTransGroup());
        cartProduct.setImagePath(imagePaths(product));
        cartProduct.setQuantityList(ApiUtils.mapQuantityList(Integer.valueOf(product.getMinOrderQty())));
    }

    private List<ProductVendorDetail> mapProductVendorList(List<ProductVendor> productVendors, Map<Long, Vendor> vendorMap) {
        return productVendors.stream().map(productVendor -> {
            ProductVendorDetail productVendorDetail = new ProductVendorDetail();
            mapProductVendor(productVendorDetail, productVendor, vendorMap.getOrDefault(productVendor.getVendorId(), new Vendor()));
            return productVendorDetail;
        }).collect(Collectors.toList());
    }

    private void mapProductVendor(ProductVendorDetail productVendorDetail, ProductVendor productVendor, Vendor vendor) {
        productVendorDetail.setId(longToString(productVendor.getId()));
        productVendorDetail.setProductId(longToString(productVendor.getProductId()));
        productVendorDetail.setVendorId(longToString(productVendor.getVendorId()));
        productVendorDetail.setVendorCode(vendor.getVendorCode());
        productVendorDetail.setVendorName(vendor.getVendorName());
        productVendorDetail.setMrpRate(decimalToString(productVendor.getMrpRate()));
        productVendorDetail.setSaleRate(decimalToString(productVendor.getSaleRate()));
        productVendorDetail.setPinCode(productVendor.getPinCode());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Add product to cart by user")
    @PostMapping("/{userId}/cart/add")
    public ResponseEntity<CartProductResponse> addCartByUser(@ApiParam("User Id") @PathVariable("userId") Long userId, @ApiParam("Cart") @RequestBody CartRequest cartRequest) throws UnitedSuppliesException {
        LOG.info("Add all cart items in the database - {}", this.getClass().getSimpleName());
        Optional<UserCart> userCartOptional = userCartService.getCartByProductIdAndUserId(userId, Long.valueOf(cartRequest.getProductId()));
        if (userCartOptional.isPresent()) {
            LOG.info("Product has already been added to cart {}", cartRequest.getProductId());
            return new ResponseEntity<>(new CartProductResponse(ApiResponseStatus.EXISTS.getStatus()), HttpStatus.NOT_FOUND);
        }
        Product product = productService.findProduct(Long.valueOf(cartRequest.getProductId()));
        Unit unit = new Unit();
        if ((product.getUnit())!= null) {
            unit = unitService.findUnit(Long.valueOf(product.getUnit()));
        }
        cartRequest.setProductVendorId(String.valueOf(productVendorService.findDefaultProductVendor(Long.valueOf(cartRequest.getProductId())).getId()));
        UserCart userCart = userCartService.saveUserCart(userCartMapper.mapExtra(cartRequest, userId));
        clubbingTransportCharges(userId);
        CartProductResponse cartProduct = new CartProductResponse();
        ProductVendor productVendor = productVendorService.findProductVendor(userCart.getProdVendorId());
        List<ProductVendor> productVendors = productVendorService.productVendorListByProduct(product.getId());
        Map<Long, Vendor> vendorMap = vendorService.listActiveVendorsByVendorIds(productVendors.stream().map(ProductVendor::getVendorId).collect(Collectors.toList())).stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        mapCartProduct(cartProduct, userCart, product, unit, productVendor);
        cartProduct.setProductVendorDetails(mapProductVendorList(productVendors, vendorMap));
        return new ResponseEntity<>(cartProduct, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Update product to cart by user")
    @PutMapping("/{userId}/cart/update")
    public ResponseEntity<CartProductResponse> updateCartByUser(@ApiParam("User Id") @PathVariable("userId") Long userId, @ApiParam("Cart") @RequestBody CartRequest cartRequest) throws UnitedSuppliesException {
        LOG.info("Update cart item in the database - {}", this.getClass().getSimpleName());
        UserCart cart = userCartService.findCartByProductIdAndUserId(userId, Long.valueOf(cartRequest.getProductId()));
        UserCart userCart = userCartService.saveUserCart(userCartMapper.map(cartRequest, cart));
        clubbingTransportCharges(userId);
        Product product = productService.findProduct(Long.valueOf(cartRequest.getProductId()));
        Unit unit = new Unit();
        if ((product.getUnit()!= null)) {
            unit = unitService.findUnit(product.getUnit());
        }
        CartProductResponse cartProduct = new CartProductResponse();
        ProductVendor productVendor = productVendorService.findProductVendor(userCart.getProdVendorId());
        List<ProductVendor> productVendors = productVendorService.productVendorListByProduct(product.getId());
        Map<Long, Vendor> vendorMap = vendorService.listActiveVendorsByVendorIds(productVendors.stream().map(ProductVendor::getVendorId).collect(Collectors.toList())).stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        mapCartProduct(cartProduct, userCart, product, unit, productVendor);
        cartProduct.setProductVendorDetails(mapProductVendorList(productVendors, vendorMap));

        return new ResponseEntity<>(cartProduct, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Remove all products from cart by user")
    @DeleteMapping("/{userId}/cart/remove")
    public ResponseEntity<Base> removeAllProductsFromCartByUser(@ApiParam("User Id") @PathVariable("userId") Long userId) throws UnitedSuppliesException {
        LOG.info("Remove all cart items by user id in the database - {}", this.getClass().getSimpleName());
        userCartService.removeAllUserCart(userId);
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Remove a product from cart by user")
    @DeleteMapping("/{userId}/cart/product/{productId}")
    public ResponseEntity<Base> removeProductFromCartByUser(@ApiParam("User Id") @PathVariable("userId") Long userId, @ApiParam("Product Id") @PathVariable("productId") Long productId) throws UnitedSuppliesException {
        LOG.info("Remove a cart item by product id in the database - {}", this.getClass().getSimpleName());
        userCartService.removeCartByProductIdAndUserId(userId, productId);
        clubbingTransportCharges(userId);
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }

//    private void clubbingTransportCharges (Long userId) throws UnitedSuppliesException {
//        List<UserCart> userCartList = userCartService.getAllUserCart(userId);
//        List<String> transGroupList = productService.getTransGroupListByProductList(userCartList.stream().map(UserCart::getProductId).collect(Collectors.toList()));
//        userCartList.stream().map(u-> {
//            List<Long> = new arrayList();
//
//
//
//        });
//    }

    private void clubbingTransportCharges(Long userId) throws UnitedSuppliesException {
        List<UserCart> userCartList = userCartService.getAllUserCart(userId);
        if (userCartList.size() > 0) {
            String destinationPincode = addressService.findDefaultAddressByUserAndCategory(userId, AddressCategory.DELIVERY_ADDRESS.getStatus()).get().getPostalCode();
            productVendorService.productVendorList();
            Map<Long, Long> vendorMapByProductVendor = productVendorService.productVendorList().stream().collect(Collectors.toMap(ProductVendor::getId, ProductVendor::getVendorId));
            Map<Long, String> pinCodeByProductVendor = productVendorService.productVendorList().stream().collect(Collectors.toMap(ProductVendor::getId, ProductVendor::getPinCode));
            List<Long> productIds = userCartList.stream().map(UserCart::getProductId).collect(Collectors.toList());
            userCartList.forEach(uc -> {
                uc.setVendorId(vendorMapByProductVendor.get(uc.getProdVendorId()));
            });
            List<String> transGroups = productService.getTransGroupListByProductList(productIds);
            Map<Long, String> productMap = productService.productListByIds(productIds).stream().collect(Collectors.toMap(Product::getId, Product::getTransGroup));
            Map<Long, List<UserCart>> userCartMap = userCartList.stream().collect(Collectors.groupingBy(UserCart::getVendorId));
            Map<String, List<Transport>> transportChargesMapByTransGroup = transportService.transportListByTransGroups(transGroups).stream().collect(Collectors.groupingBy(Transport::getTransGroup));
            userCartList.forEach(u -> {
                Integer toAddressDistance = getDistanceByPincodeRange(pinCodeByProductVendor.get(u.getProdVendorId()), destinationPincode);
                Integer qty = userCartMap.get(u.getVendorId()).stream().filter(p -> productMap.get(p.getProductId())
                        .equalsIgnoreCase(productMap.get(u.getProductId()))).mapToInt(UserCart::getQuantity).sum();
                BigDecimal clubbedTransportCharge = transportChargeCalculationMethod(transportChargesMapByTransGroup.getOrDefault(productMap.get(u.getProductId()), new ArrayList<>()), qty, toAddressDistance);
                u.setTransCharges(clubbedTransportCharge.divide(new BigDecimal(qty), 4, RoundingMode.CEILING).multiply(new BigDecimal(u.getQuantity())));
            });
            userCartService.saveUserCartList(userCartList);
        }
    }
}
