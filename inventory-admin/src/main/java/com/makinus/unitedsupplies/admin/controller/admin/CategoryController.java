/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.admin;

import com.makinus.unitedsupplies.admin.data.forms.CategoryForm;
import com.makinus.unitedsupplies.admin.data.mapping.CategoryMapper;
import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.admin.utils.AdminUtils.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author abuabdul
 */
@Controller
public class CategoryController {

    private final Logger LOG = LogManager.getLogger(CategoryController.class);

    private static final String CATEGORY_PAGE = "dashboard/category/category-list";
    private static final String ADD_CATEGORY_PAGE = "dashboard/category/category-add";
    private static final String EDIT_CATEGORY_PAGE = "dashboard/category/category-edit";

    @Autowired
    @Qualifier("CategoryMapper")
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private OrderService orderService;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream()
                .collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/category.mk")
    public String category(ModelMap model, HttpServletRequest request)
            throws UnitedSuppliesException {
        LOG.info("Open Category page - {}", this.getClass().getSimpleName());
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryList", categories);
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        return CATEGORY_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/new/category.mk")
    public String newCategory(ModelMap model, HttpServletRequest request) throws UnitedSuppliesException {
        LOG.info("Open Category add form page - {}", this.getClass().getSimpleName());
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setActiveCategory(Boolean.TRUE);
        model.addAttribute("categoryForm", categoryForm);
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryValues", fetchParentCategories(categories)); // parent category for Add form
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        return ADD_CATEGORY_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/category.mk")
    public String addCategory(@ModelAttribute("categoryForm") CategoryForm categoryForm, ModelMap model, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Create New Category on admin panel dashboard - {}", this.getClass().getSimpleName());
        Category savedCategory = categoryService.saveCategory(categoryMapper.map(categoryForm));
        redirectAttrs.addFlashAttribute("categoryName", savedCategory.getCategoryName());
        LOG.debug("Category Details {}", savedCategory.toString());
        return "redirect:/category.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping(value = "/category/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isCategoryAvailable(HttpServletRequest request) throws UnitedSuppliesException {
        LOG.info("Checking if the category exists - {}", this.getClass().getSimpleName());
        boolean isCategoryAvailable = categoryService.isCategoryAvailable(request.getParameter("categoryName"));
        LOG.info("category is available? {}", isCategoryAvailable);
        return !isCategoryAvailable;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/category.mk")
    public String editCategory(ModelMap model, @PathVariable("id") String categoryId) throws UnitedSuppliesException {
        LOG.info("Open Edit Category page - {}", this.getClass().getSimpleName());
        CategoryForm savedCategory = categoryMapper.remap(categoryService.findCategory(Long.valueOf(categoryId)));
        model.addAttribute("editCategoryForm", savedCategory);
        model.addAttribute("categoryValues", fetchParentCategoriesSelfExcluded(categoryService.categoryList(), categoryId));
        model.addAttribute("categoryNameFromDB", savedCategory.getCategoryName());
        return EDIT_CATEGORY_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/category.mk")
    public String updateCategory(@ModelAttribute("editCategoryForm") CategoryForm categoryForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Update Category page - {}", this.getClass().getSimpleName());
        Category updateCategory = categoryService.findCategory(Long.valueOf(categoryForm.getCategoryID()));
        if (updateCategory.getImagePath() != null && categoryForm.getEditCategoryImage() != null && !categoryForm.getEditCategoryImage().isEmpty()) {
            String[] uriPaths = updateCategory.getImagePath().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        Category savedCategory = categoryService.updateCategory(categoryMapper.map(categoryForm, updateCategory));
        redirectAttrs.addFlashAttribute("categoryName", savedCategory.getCategoryName());
        redirectAttrs.addFlashAttribute("editCategoryFlag", Boolean.TRUE);
        LOG.debug("Updated Category Details {}", savedCategory.toString());
        return "redirect:/category.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/category.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeCategory(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Category from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Category removedCategory = categoryService.removeCategory(Long.valueOf(id));
            LOG.info("Category is removed? {}", (removedCategory != null && removedCategory.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            if (removedCategory.getImagePath() != null) {
                String[] uriPaths = removedCategory.getImagePath().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            map.put("valid", Boolean.TRUE);
        } catch (UnitedSuppliesException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @Deprecated
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/category/{id}/view.mk")
    public void viewCategoryImage(HttpServletResponse response, @PathVariable String id) throws UnitedSuppliesException {
        LOG.info("View Category from dashboard - {}", this.getClass().getSimpleName());
        try {
            Category category = categoryService.findCategory(Long.valueOf(id));
            if (category != null && isNotEmpty(category.getImagePath())) {
                Path path = Paths.get(category.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(category.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(category.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new UnitedSuppliesException("IO Exception occurred while viewing category image " + e.getMessage());
        }
    }
}
