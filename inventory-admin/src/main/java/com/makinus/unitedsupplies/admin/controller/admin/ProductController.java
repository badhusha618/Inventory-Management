/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.admin;

import com.makinus.Inventory.admin.data.forms.ProductForm;
import com.makinus.Inventory.admin.data.forms.ProductVendorForm;
import com.makinus.Inventory.admin.data.mapping.ProductMapper;
import com.makinus.Inventory.admin.data.mapping.ProductVendorMapper;
import com.makinus.Inventory.admin.data.service.excel.GenericWriter;
import com.makinus.Inventory.admin.data.service.excel.ProdVendorExcelDTO;
import com.makinus.Inventory.admin.data.service.excel.ProductExcelDTO;
import com.makinus.Inventory.common.data.entity.*;
import com.makinus.Inventory.common.data.form.ProductFilterForm;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.brand.BrandService;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.color.ColorService;
import com.makinus.Inventory.common.data.service.crusher.CrusherService;
import com.makinus.Inventory.common.data.service.grade.GradeService;
import com.makinus.Inventory.common.data.service.material.MaterialService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.product.ProductService;
import com.makinus.Inventory.common.data.service.productchargehistory.ProductChargeHistoryService;
import com.makinus.Inventory.common.data.service.productvendor.ProductVendorService;
import com.makinus.Inventory.common.data.service.quality.QualityService;
import com.makinus.Inventory.common.data.service.size.SizeService;
import com.makinus.Inventory.common.data.service.specification.SpecificationService;
import com.makinus.Inventory.common.data.service.transport.TransportService;
import com.makinus.Inventory.common.data.service.type.TypeService;
import com.makinus.Inventory.common.data.service.unit.UnitService;
import com.makinus.Inventory.common.data.service.unitmapping.UnitMappingService;
import com.makinus.Inventory.common.data.service.vendor.VendorService;
import com.makinus.Inventory.common.data.service.weight.WeightService;
import com.makinus.Inventory.common.exception.InventoryException;
import com.makinus.Inventory.common.s3.AmazonS3Client;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.Inventory.admin.utils.AdminUtils.*;
import static com.makinus.Inventory.common.utils.AppUtils.getCurrentUser;
import static com.makinus.Inventory.common.utils.AppUtils.getInstant;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author Bad_sha
 */
@Controller
public class ProductController {

    private final Logger LOG = LogManager.getLogger(ProductController.class);
    private static final String ADD_PRODUCT_PAGE = "dashboard/product/product-add";
    private static final String EDIT_PRODUCT_PAGE = "dashboard/product/product-edit";
    private static final String LIST_PRODUCT_PAGE = "dashboard/product/product-list";
    private static final String VENDOR_CHARGE_HISTORY_PAGE = "dashboard/product/vendor-charge-history";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private ProductChargeHistoryService productChargeHistoryService;


    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private QualityService qualityService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private CrusherService crusherService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitMappingService unitMappingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeightService weightService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private TransportService transportService;

    @Autowired
    @Qualifier("ProductMapper")
    private ProductMapper productMapper;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    @Qualifier("ProductVendorMapper")
    private ProductVendorMapper productVendorMapper;

    @Autowired
    @Qualifier("ProductExcelWriter")
    private GenericWriter<List<ProductExcelDTO>> productExcelWriter;

    @ModelAttribute("zone")
    public TimeZone injectTimeZone(TimeZone serverZone, @CookieValue(value = "us_user_timezone", required = false) String zoneID) {
        LOG.info("Setting Zone in model attribute - {}", this.getClass().getSimpleName());
        return isNotEmpty(zoneID) ? TimeZone.getTimeZone(zoneID) : serverZone;
    }

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/list/products.mk")
    public String listProduct(ModelMap model, @ModelAttribute("productList") ArrayList<Product> products, @ModelAttribute("productFilterForm") ProductFilterForm productFilterForm, @ModelAttribute("fromSearch") String fromSearch) throws InventoryException {
        LOG.info("List Products page - {}", this.getClass().getSimpleName());
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        model.addAttribute("categoryValues", fetchParentCategories(categories));
        model.addAttribute("subCategoryValues", fetchChildCategories(categories));
        model.addAttribute("productList", new ArrayList<>());
        if (StringUtils.isNotEmpty(fromSearch)) {
            model.addAttribute("productList", products);
            model.addAttribute("productFilterForm", productFilterForm);
        } else {
            List<Product> productList = productService.productList().stream().sorted(Comparator.comparing(Product::getCreatedDate).reversed()).collect(Collectors.toList());
            model.addAttribute("productList", productList);
            model.addAttribute("productFilterForm", new ProductFilterForm());
        }
        return LIST_PRODUCT_PAGE;
    }

    @PostMapping(value = {"/product/search.mk"})
    public String productSearch(@ModelAttribute("productFilterForm") ProductFilterForm productFilterForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Search ordered product - {}", this.getClass().getSimpleName());
        List<Product> productList = productService.filterProduct(productFilterForm).stream().sorted(Comparator.comparing(Product::getCreatedDate).reversed()).collect(Collectors.toList());
        redirectAttrs.addFlashAttribute("productList", productList);
        redirectAttrs.addFlashAttribute("productFilterForm", productFilterForm);
        redirectAttrs.addFlashAttribute("fromSearch", Boolean.TRUE.toString());
        return "redirect:/list/products.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/add/product.mk")
    public String addProductPage(ModelMap model) throws InventoryException {
        LOG.info("Open Add Product page - {}", this.getClass().getSimpleName());
        ProductForm productForm = new ProductForm();
        productForm.setInStock(Boolean.TRUE);
        productForm.setActiveProduct(Boolean.TRUE);
        productForm.setTaxInclusive(Boolean.TRUE);
        model.addAttribute("productForm", productForm);

        List<Category> categories = categoryService.categoryList();
        List<Vendor> vendors = vendorService.vendorList();
        List<Brand> brands = brandService.brandList();
        List<Quality> qualities = qualityService.qualityList();
        List<Grade> grades = gradeService.gradeList();
        List<Type> types = typeService.typeList();
        List<Crusher> crushers = crusherService.crusherList();
        List<Unit> units = unitService.unitList();
        List<UnitMapping> unitMappings = unitMappingService.unitMappingList();
        List<Weight> weights = weightService.weightList();
        List<Material> materials = materialService.materialList();
        List<Color> colors = colorService.colorList();
        Map<Long, List<Transport>> transGroups = transportService.transportGroupList().stream().collect(Collectors.groupingBy(Transport::getUnitId));
        model.addAttribute("vendors", vendors);
        model.addAttribute("vendorPincodeMap", vendors.stream().collect(Collectors.toMap(Vendor::getId, Vendor::getPinCode)));
        model.addAttribute("brandValues", brandMap(brands));
        model.addAttribute("qualityValues", qualityMap(qualities));
        model.addAttribute("gradeValues", gradeMap(grades));
        model.addAttribute("typeValues", typeMap(types));
        model.addAttribute("crusherValues", crusherMap(crushers));
        model.addAttribute("unitValues", unitMap(units, unitMappings));
        model.addAttribute("categoryValues", fetchParentCategories(categories));
        model.addAttribute("weightValues", weightMap(weights));
        model.addAttribute("materialValues", materialMap(materials));
        model.addAttribute("colorValues", colorMap(colors));
        model.addAttribute("transMap", transGroups);
        return ADD_PRODUCT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/new/product.mk")
    public String addNewProduct(@ModelAttribute("productForm") ProductForm productForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Add New Product page - {}", this.getClass().getSimpleName());
        Product savedProduct = productService.addNewProduct(productMapper.map(productForm));
        productVendorService.saveProductVendorList(productVendorMapper.mapExtra(productForm.getProductVendorForms(), savedProduct));
        updateProductHistory(savedProduct.getId(), productForm.getProductVendorForms());
        redirectAttrs.addFlashAttribute("productName", productForm.getProductName());
        LOG.debug("Product Details {}", savedProduct);
        return "redirect:/list/products.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/product/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingproduct(HttpServletRequest request) throws InventoryException {
        LOG.info("Checking if the product exists - {}", this.getClass().getSimpleName());
        boolean isProductAvailable = productService.isProductAvailable(request.getParameter("productCode").trim());
        LOG.info("Product is available? {}", isProductAvailable);
        return !isProductAvailable;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/product.mk")
    public String editProductPage(ModelMap model, @PathVariable("id") String productId) throws InventoryException {
        LOG.info("Open Edit Product page - {}", this.getClass().getSimpleName());
        Product product = productService.findProduct(Long.valueOf(productId));
        ProductForm productForm = productMapper.remap(product);
        List<Transport> transportList = transportService.transportGroupList();
        Map<Long, List<Transport>> transGroups = transportService.transportGroupList().stream().collect(Collectors.groupingBy(Transport::getUnitId));
        List<ProductVendor> productVendorList = productVendorService.productVendorListByProduct(Long.valueOf(productId));
        if (!productVendorList.isEmpty()) {
            productForm.setProductVendorForms(productVendorMapper.remap(productVendorList));
        }
        model.addAttribute("editProductForm", productForm);
        List<Category> categories = categoryService.categoryList();
        List<Category> subCategories = categoryService.categoryListByParent(product.getParentCategory());
        List<Vendor> vendors = vendorService.vendorList();
        List<Brand> brands = brandService.brandList();
        List<Quality> qualities = qualityService.qualityList();
        List<Grade> grades = gradeService.gradeList();
        List<Type> types = typeService.typeList();
        List<Size> sizes = sizeService.sizeList();
        List<Unit> units = unitService.unitList();
        List<UnitMapping> unitMappings = unitMappingService.unitMappingList();
        List<Weight> weights = weightService.weightList();
        List<Material> materials = materialService.materialList();
        List<Color> colors = colorService.colorList();
        List<Specification> specifications = specificationService.specificationList();
        model.addAttribute("vendors", vendors);
        model.addAttribute("vendorPincodeMap", vendors.stream().collect(Collectors.toMap(Vendor::getId, Vendor::getPinCode)));
        model.addAttribute("categoryId", product.getParentCategory());
        model.addAttribute("brandValues", brandMap(brands));
        model.addAttribute("qualityValues", qualityMap(qualities));
        model.addAttribute("gradeValues", gradeMap(grades));
        model.addAttribute("typeValues", typeMap(types));
        model.addAttribute("sizeValues", sizeMap(sizes));
        model.addAttribute("crusherValues", crusherMap(crusherService.crusherList()));
        model.addAttribute("unitValues", unitMap(units, unitMappings));
        model.addAttribute("categoryValues", fetchParentCategories(categories));
        model.addAttribute("subCategoryValues", fetchChildCategories(subCategories));
        model.addAttribute("productVendorList", productVendorList.isEmpty());
        model.addAttribute("weightValues", weightMap(weights));
        model.addAttribute("materialValues", materialMap(materials));
        model.addAttribute("colorValues", colorMap(colors));
        model.addAttribute("specificationValues", specificationMap(specifications));
        model.addAttribute("transMap", transGroups);
        model.addAttribute("editTransGroups", product.getTransGroup());
        return EDIT_PRODUCT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/product.mk")
    public String updateProduct(@ModelAttribute("editProductForm") ProductForm productForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Product page - {}", this.getClass().getSimpleName());
        Product updateProduct = productService.findProduct(Long.valueOf(productForm.getProductID()));
        if (updateProduct.getImagePath() != null && productForm.getEditProductImage() != null && !productForm.getEditProductImage().isEmpty()) {
            String[] uriPaths = updateProduct.getImagePath().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        if (updateProduct.getOptImagePath1() != null && productForm.getEditProductImageOpt1() != null && !productForm.getEditProductImageOpt1().isEmpty()) {
            String[] uriPaths = updateProduct.getOptImagePath1().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        if (updateProduct.getOptImagePath2() != null && productForm.getEditProductImageOpt2() != null && !productForm.getEditProductImageOpt2().isEmpty()) {
            String[] uriPaths = updateProduct.getOptImagePath2().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        if (updateProduct.getOptImagePath3() != null && productForm.getEditProductImageOpt3() != null && !productForm.getEditProductImageOpt3().isEmpty()) {
            String[] uriPaths = updateProduct.getOptImagePath3().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        Product savedProduct = productService.updateProduct(productMapper.map(productForm, updateProduct));
        List<ProductVendor> productVendorList = productVendorService.productVendorListByProduct(updateProduct.getId());
        productVendorService.updateProductVendorList(productVendorMapper.mapListUpdate(productForm, updateProduct, productVendorList));
        updateProductHistory((Long.valueOf(productForm.getProductID())), productForm.getProductVendorForms());
        redirectAttrs.addFlashAttribute("productName", productForm.getProductName());
        redirectAttrs.addFlashAttribute("editProductFlag", Boolean.TRUE);
        LOG.debug("Updated Product Details {}", savedProduct.toString());
        return "redirect:/list/products.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/product.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeProduct(@PathVariable String id) {
        LOG.info("Remove Product from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Product removedProduct = productService.removeProduct(Long.valueOf(id));
            LOG.info("Product is removed? {}", (removedProduct != null && removedProduct.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            if (removedProduct.getImagePath() != null) {
                String[] uriPaths = removedProduct.getImagePath().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            if (removedProduct.getOptImagePath1() != null) {
                String[] uriPaths = removedProduct.getOptImagePath1().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            if (removedProduct.getOptImagePath2() != null) {
                String[] uriPaths = removedProduct.getOptImagePath2().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            if (removedProduct.getOptImagePath3() != null) {
                String[] uriPaths = removedProduct.getOptImagePath3().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/view/{id}/product.mk")
    public void viewProduct(HttpServletResponse response, @PathVariable String id)
            throws InventoryException {
        LOG.info("View Product from dashboard - {}", this.getClass().getSimpleName());
        try {
            Product product = productService.findProduct(Long.valueOf(id));
            if (product != null && isNotEmpty(product.getImagePath())) {
                Path path = Paths.get(product.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(product.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(product.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new InventoryException("IO Exception occurred while viewing product image " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/subcategory.mk", produces = "application/json")
    @ResponseBody
    public List<Category> viewSubCategories(@RequestParam String id) throws InventoryException {
        LOG.info("Fetch Subcategories for parent category from dashboard - {}", this.getClass().getSimpleName());
        return categoryService.categoryListByParent(Long.valueOf(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/view/{prodId}/price.mk")
    public String viewAllVendorPriceHistory(ModelMap model, @PathVariable String prodId) throws InventoryException {
        LOG.info("Fetch Vendor price history - {}", this.getClass().getSimpleName());
        List<Vendor> vendors = vendorService.vendorList();
        List<ProductChargeHistory> productChargeHistories = productChargeHistoryService.getProductChargeHistory(Long.valueOf(prodId));
        model.addAttribute("vendorMap", vendors.stream().collect(Collectors.toMap(Vendor::getId, Function.identity())));
        model.addAttribute("vendorPriceList", productChargeHistories);
        return VENDOR_CHARGE_HISTORY_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/view/{prodId}/vendor/{vendorId}/price.mk")
    public String viewVendorPriceHistory(ModelMap model, @PathVariable String prodId, @PathVariable String vendorId) throws InventoryException {
        LOG.info("Fetch Vendor price history - {}", this.getClass().getSimpleName());
        List<ProductChargeHistory> productChargeHistories = productChargeHistoryService.productChargeHistoryList(Long.valueOf(prodId), Long.valueOf(vendorId));
        model.addAttribute("vendorPriceList", productChargeHistories);
        model.addAttribute("vendorMap", "");
        return VENDOR_CHARGE_HISTORY_PAGE;
    }

    @PostMapping(value = "/export/products.mk")
    public void exportProductListToExcel(@ModelAttribute("productFilterForm") ProductFilterForm productFilterForm, HttpServletResponse response) throws InventoryException {
        LOG.info("Export Product details in Excel - {}", this.getClass().getSimpleName());
        List<Product> productList = productService.filterProduct(productFilterForm).stream().sorted(Comparator.comparing(Product::getCreatedDate).reversed()).collect(Collectors.toList());

        List<Category> categories = categoryService.categoryList();
        List<Brand> brands = brandService.brandList();
        List<Quality> qualities = qualityService.qualityList();
        List<Grade> grades = gradeService.gradeList();
        List<Type> types = typeService.typeList();
        List<Crusher> crushers = crusherService.crusherList();
        List<Unit> units = unitService.unitList();
        List<Weight> weights = weightService.weightList();
        List<Material> materials = materialService.materialList();
        List<Color> colors = colorService.colorList();
        List<Vendor> vendorList = vendorService.vendorList();
        List<ProductVendor> productVendors = productVendorService.productVendorList();


        Map<Long, String> categoryMap = categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName));
        Map<Long, String> brandMap = brands.stream().collect(Collectors.toMap(Brand::getId, Brand::getBrandName));
        Map<Long, String> qualityMap = qualities.stream().collect(Collectors.toMap(Quality::getId, Quality::getQuality));
        Map<Long, String> gradeMap = grades.stream().collect(Collectors.toMap(Grade::getId, Grade::getGrade));
        Map<Long, String> typeMap = types.stream().collect(Collectors.toMap(Type::getId, Type::getType));
        Map<Long, String> crusherMap = crushers.stream().collect(Collectors.toMap(Crusher::getId, Crusher::getCrusher));
        Map<String, String> unitMap = units.stream().collect(Collectors.toMap(u -> u.getId().toString(), Unit::getUnitName));
        Map<Long, String> weightMap = weights.stream().collect(Collectors.toMap(Weight::getId, Weight::getWeight));
        Map<Long, String> materialMap = materials.stream().collect(Collectors.toMap(Material::getId, Material::getMaterial));
        Map<Long, String> colorMap = colors.stream().collect(Collectors.toMap(Color::getId, Color::getColor));
        Map<Long, Vendor> vendorMap = vendorList.stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        Map<Long, List<ProductVendor>> prodVendorMap = productVendors.stream().collect(Collectors.groupingBy(ProductVendor::getProductId));


        List<ProductExcelDTO> productExcelDTOList = new ArrayList<>();
        productList.forEach(product -> {
            ProductExcelDTO productExcelDTO = new ProductExcelDTO();
            productExcelDTO.setId(String.valueOf(product.getId()));
            productExcelDTO.setProductCode(product.getProductCode());
            productExcelDTO.setProductName(product.getProductName());
            productExcelDTO.setHsnCode(product.getHsnCode());
            productExcelDTO.setRateOfCgst(product.getRateOfCgst() == null ? StringUtils.EMPTY : String.valueOf(product.getRateOfCgst()));
            productExcelDTO.setRateOfSgst(product.getRateOfSgst() == null ? StringUtils.EMPTY : String.valueOf(product.getRateOfSgst()));
            productExcelDTO.setRateOfIgst(product.getRateOfIgst() == null ? StringUtils.EMPTY : String.valueOf(product.getRateOfIgst()));
            productExcelDTO.setMinOrderQty(product.getMinOrderQty().toString());
            productExcelDTO.setParentCategory(categoryMap.get(product.getParentCategory()));
            productExcelDTO.setSubCategory(product.getSubCategory() == null ? StringUtils.EMPTY : categoryMap.get(product.getSubCategory()));
            productExcelDTO.setSize(product.getSize());
            productExcelDTO.setSpecification(product.getSpecification());
            productExcelDTO.setBrand(product.getBrand() == null ? StringUtils.EMPTY : brandMap.get(product.getBrand()));
            productExcelDTO.setQuality(product.getQuality() == null ? StringUtils.EMPTY : qualityMap.get(product.getQuality()));
            productExcelDTO.setGrade(product.getGrade() == null ? StringUtils.EMPTY : gradeMap.get(product.getGrade()));
            productExcelDTO.setType(product.getType() == null ? StringUtils.EMPTY : typeMap.get(product.getType()));
            productExcelDTO.setCrusher(product.getCrusher() == null ? StringUtils.EMPTY : crusherMap.get(product.getCrusher()));
            productExcelDTO.setUnit(product.getUnit() == null ? StringUtils.EMPTY : unitMap.get(product.getUnit()));
            productExcelDTO.setWeight(product.getWeight() == null ? StringUtils.EMPTY : weightMap.get(product.getWeight()));
            productExcelDTO.setMaterial(product.getMaterial() == null ? StringUtils.EMPTY : materialMap.get(product.getMaterial()));
            productExcelDTO.setColor(product.getColor() == null ? StringUtils.EMPTY : colorMap.get(product.getColor()));
            List<ProdVendorExcelDTO> prodVendorExcelDTOs = new ArrayList<>();
            prodVendorMap.getOrDefault(product.getId(), new ArrayList<>()).forEach(pv -> {
                ProdVendorExcelDTO prodVendorExcelDTO = new ProdVendorExcelDTO();
                prodVendorExcelDTO.setVendor(vendorMap.getOrDefault(pv.getVendorId(), new Vendor()).getVendorCode() + " - " + vendorMap.getOrDefault(pv.getVendorId(), new Vendor()).getVendorName());
                prodVendorExcelDTO.setMrpRate(pv.getMrpRate() == null ? StringUtils.EMPTY : String.valueOf(pv.getMrpRate()));
                prodVendorExcelDTO.setSaleRate(pv.getSaleRate() == null ? StringUtils.EMPTY : String.valueOf(pv.getSaleRate()));
                prodVendorExcelDTO.setPinCode(pv.getPinCode());
                prodVendorExcelDTOs.add(prodVendorExcelDTO);
            });
            productExcelDTO.setProdVendorExcelDTOs(prodVendorExcelDTOs);
            productExcelDTOList.add(productExcelDTO);
        });

        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%d.xls\"", "ProductDetails", getInstant().getTime()));
        response.setCharacterEncoding("UTF-8");
        productExcelWriter.write(productExcelDTOList, response);
    }

    private void updateProductHistory(Long productId, List<ProductVendorForm> productVendorForms) throws InventoryException {
        Map<Long, List<ProductChargeHistory>> vendorMap = productChargeHistoryService.getProductChargeHistory(productId).stream().collect(Collectors.groupingBy(ProductChargeHistory::getVendorId));
        List<ProductChargeHistory> productChargeHistoryList = new ArrayList<>();
        productVendorForms.forEach(pvf -> {
            if (StringUtils.isNotEmpty(pvf.getVendorId())) {
                if (!vendorMap.containsKey(Long.valueOf(pvf.getVendorId()))) {
                    productChargeHistoryList.add(addNewProductPriceHistory(pvf, productId));
                } else {
                    ProductChargeHistory lastPriceDetail = vendorMap.get(Long.valueOf(pvf.getVendorId())).stream().sorted(Comparator.comparing(ProductChargeHistory::getCreatedDate).reversed()).collect(Collectors.toList()).get(0);
                    if (!(String.format("%.2f", new BigDecimal(pvf.getMrpRate())).equals(String.valueOf(lastPriceDetail.getMrpRate()))) ||
                            !(String.format("%.2f", new BigDecimal(pvf.getSaleRate())).equals(String.valueOf(lastPriceDetail.getSaleRate()))) ||
                            !(String.format("%.2f", new BigDecimal(pvf.getActualRate())).equals(String.valueOf(lastPriceDetail.getActualRate())))) {
                        productChargeHistoryList.add(addNewProductPriceHistory(pvf, productId));
                    }
                }
            }
        });
        productChargeHistoryService.saveProductChargeHistoryList(productChargeHistoryList);
    }

    private ProductChargeHistory addNewProductPriceHistory(ProductVendorForm productVendorForm, Long productId) {
        ProductChargeHistory productChargeHistory = new ProductChargeHistory();
        productChargeHistory.setVendorId(Long.valueOf(productVendorForm.getVendorId()));
        productChargeHistory.setMrpRate(new BigDecimal(productVendorForm.getMrpRate()));
        productChargeHistory.setSaleRate(new BigDecimal(productVendorForm.getSaleRate()));
        productChargeHistory.setActualRate(new BigDecimal(productVendorForm.getActualRate()));
        productChargeHistory.setProductId(productId);
        productChargeHistory.setPinCode(productVendorForm.getPinCode());
        productChargeHistory.setCreatedBy(getCurrentUser());
        productChargeHistory.setCreatedDate(getInstant());
        productChargeHistory.setUpdatedBy(getCurrentUser());
        productChargeHistory.setUpdatedDate(getInstant());
        return productChargeHistory;
    }
}
