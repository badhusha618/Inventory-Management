/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */

package com.makinus.Inventory.admin.utils;

import com.makinus.Inventory.admin.data.forms.CategoryForm;
import com.makinus.Inventory.admin.data.forms.UnitForm;
import com.makinus.Inventory.common.data.entity.*;
import com.makinus.Inventory.common.data.service.Triplet;
import com.makinus.Inventory.common.data.service.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.makinus.Inventory.common.data.reftype.ProdOrderStatus.*;

/**
 * @author Bad_sha
 */
public interface AdminUtils { // TODO refactor

    Logger LOG = LogManager.getLogger(AdminUtils.class);

    static List<CategoryForm> fetchParentCategories(List<Category> categories) {
        return categories.stream()
                .filter(cat -> cat.getParentCategory() == null)
                .map(
                        pCat -> {
                            CategoryForm category = new CategoryForm();
                            category.setCategoryID(String.valueOf(pCat.getId()));
                            category.setCategoryName(pCat.getCategoryName());
                            return category;
                        })
                .collect(Collectors.toList());
    }

    static List<CategoryForm> fetchChildCategories(List<Category> categories) {
        return categories.stream()
                .filter(cat -> cat.getParentCategory() != null)
                .map(
                        pCat -> {
                            CategoryForm category = new CategoryForm();
                            category.setCategoryID(String.valueOf(pCat.getId()));
                            category.setCategoryName(pCat.getCategoryName());
                            return category;
                        })
                .collect(Collectors.toList());
    }

    static List<UnitForm> fetchUnits(List<Unit> units) {
        return units.stream()
                .map(
                        u -> {
                            UnitForm unit = new UnitForm();
                            unit.setUnitID(String.valueOf(u.getId()));
                            unit.setUnitName(u.getUnitName());
                            return unit;
                        })
                .collect(Collectors.toList());
    }

    static Map<String, List<Category>> subCategoryMap(List<Category> categories) {
        Map<String, List<Category>> multimap = new HashMap<>();
        List<Category> subCategoryList =
                categories.stream()
                        .filter(cat -> cat.getParentCategory() != null)
                        .collect(Collectors.toList());
        for (Category category : subCategoryList) {
            multimap
                    .computeIfAbsent(category.getParentCategory().toString(), (k -> new ArrayList<>()))
                    .add(category);
        }
        return multimap;
    }

    static Map<String, List<Product>> productCategoryMap(List<Product> products) {
        Map<String, List<Product>> multimap = new HashMap<>();
        for (Product product : products) {
            multimap
                    .computeIfAbsent(product.getParentCategory().toString(), (k -> new ArrayList<>()))
                    .add(product);
        }
        return multimap;
    }

    static Map<String, List<Triplet>> productCategoryTripletMap(List<Product> products) {
        Map<String, List<Triplet>> multimap = new HashMap<>();
        for (Product product : products) {
            Triplet<Long, String, String> productTriplet = new Triplet<>();
            productTriplet.setA(product.getId());
            productTriplet.setB(product.getProductName());
            productTriplet.setC(product.getProductCode());
            multimap
                    .computeIfAbsent(product.getParentCategory().toString(), (k -> new ArrayList<>()))
                    .add(productTriplet);
        }
        return multimap;
    }

    static Map<Long, Tuple> productTupleMap(List<Product> products) {
        Map<Long, Tuple> multimap = new HashMap<>();
        products.forEach(
                p -> {
                    Tuple<Long, Long> productTuple = new Tuple<>();
                    if ((p.getUnit())!= null) {
                        productTuple.setA(p.getUnit());
                    }
                    productTuple.setB(p.getParentCategory()); // TODO:Please update this
                    multimap.put(p.getId(), productTuple);
                });
        return multimap;
    }

    static Map<Long, Triplet> productTripletMap(List<Product> products) {
        Map<Long, Triplet> multimap = new HashMap<>();
        products.forEach(
                p -> {
                    Triplet<Long, BigDecimal, Long> productTriplet = new Triplet();
                    if ((p.getUnit()!= null)) {
                        productTriplet.setA(p.getUnit());
                    }
                    //productTriplet.setB(p.getSaleRate()); // TODO:Please update this
                    productTriplet.setC(p.getParentCategory());
                    multimap.put(p.getId(), productTriplet);
                });
        return multimap;
    }

    static Map<String, List<Brand>> brandMap(List<Brand> brands) {
        Map<String, List<Brand>> multimap = new HashMap<>();
        for (Brand brand : brands) {
            multimap.computeIfAbsent(brand.getCategory().toString(), (k -> new ArrayList<>())).add(brand);
        }
        return multimap;
    }

    static Map<String, List<Quality>> qualityMap(List<Quality> qualities) {
        Map<String, List<Quality>> multimap = new HashMap<>();
        for (Quality quality : qualities) {
            multimap
                    .computeIfAbsent(quality.getCategory().toString(), (k -> new ArrayList<>()))
                    .add(quality);
        }
        return multimap;
    }

    static Map<String, List<Grade>> gradeMap(List<Grade> grades) {
        Map<String, List<Grade>> multimap = new HashMap<>();
        for (Grade grade : grades) {
            multimap.computeIfAbsent(grade.getCategory().toString(), (k -> new ArrayList<>())).add(grade);
        }
        return multimap;
    }

    static Map<String, List<Type>> typeMap(List<Type> types) {
        Map<String, List<Type>> multimap = new HashMap<>();
        for (Type type : types) {
            multimap.computeIfAbsent(type.getCategory().toString(), (k -> new ArrayList<>())).add(type);
        }
        return multimap;
    }

    static Map<String, List<Size>> sizeMap(List<Size> sizes) {
        Map<String, List<Size>> multimap = new HashMap<>();
        for (Size size : sizes) {
            multimap.computeIfAbsent(size.getCategory().toString(), (k -> new ArrayList<>())).add(size);
        }
        return multimap;
    }

    static Map<String, List<Weight>> weightMap(List<Weight> weights) {
        Map<String, List<Weight>> multimap = new HashMap<>();
        for (Weight weight : weights) {
            multimap.computeIfAbsent(weight.getCategory().toString(), (k -> new ArrayList<>())).add(weight);
        }
        return multimap;
    }

    static Map<String, List<Material>> materialMap(List<Material> materials) {
        Map<String, List<Material>> multimap = new HashMap<>();
        for (Material material : materials) {
            multimap.computeIfAbsent(material.getCategory().toString(), (k -> new ArrayList<>())).add(material);
        }
        return multimap;
    }

    static Map<String, List<Color>> colorMap(List<Color> colors) {
        Map<String, List<Color>> multimap = new HashMap<>();
        for (Color color : colors) {
            multimap.computeIfAbsent(color.getCategory().toString(), (k -> new ArrayList<>())).add(color);
        }
        return multimap;
    }

    static Map<String, List<Specification>> specificationMap(List<Specification> specifications) {
        Map<String, List<Specification>> multimap = new HashMap<>();
        for (Specification specification : specifications) {
            multimap.computeIfAbsent(specification.getCategory().toString(), (k -> new ArrayList<>())).add(specification);
        }
        return multimap;
    }

    static Map<String, List<Crusher>> crusherMap(List<Crusher> crushers) {
        Map<String, List<Crusher>> multimap = new HashMap<>();
        for (Crusher crusher : crushers) {
            multimap
                    .computeIfAbsent(crusher.getCategory().toString(), (k -> new ArrayList<>()))
                    .add(crusher);
        }
        return multimap;
    }

    static Map<String, List<Unit>> unitMap(List<Unit> units, List<UnitMapping> unitMappings) {
        Map<String, List<Unit>> multimap = new HashMap<>();
        Map<String, Unit> mapUnit = new HashMap<>();
        units.forEach(u -> mapUnit.put(String.valueOf(u.getId()), u));
        for (UnitMapping unitMapping : unitMappings) {
            multimap
                    .computeIfAbsent(unitMapping.getCategory().toString(), (k -> new ArrayList<>()))
                    .add(mapUnit.get(String.valueOf(unitMapping.getUnit())));
        }
        return multimap;
    }

    static List<CategoryForm> fetchParentCategoriesSelfExcluded(
            List<Category> categories, final String categoryId) {
        return categories.stream()
                .filter(cat -> cat.getParentCategory() == null && cat.getId() != Long.parseLong(categoryId))
                .map(
                        pCat -> {
                            CategoryForm category = new CategoryForm();
                            category.setCategoryID(String.valueOf(pCat.getId()));
                            category.setCategoryName(pCat.getCategoryName());
                            return category;
                        })
                .collect(Collectors.toList());
    }

    static Long ordersCount(List<Order> orders) {
        return orders.stream().filter(o -> (!o.getStatus().equalsIgnoreCase(CANCELLED.getStatus()))).count();
    }

    static Long ordersTotalValue(List<Order> orders) {
        return orders.stream()
                .filter(o -> (o.getStatus().equalsIgnoreCase(DELIVERED.getStatus())))
                .mapToLong(o -> o.getOrderTotal().longValue())
                .sum();
    }

    static Collection<Integer> ordersMonthlyValueForThisYear(List<Order> orders) {
        Map<Integer, Integer> monthlyMap = new HashMap<>();
        monthlyMap.put(1, 0);
        monthlyMap.put(2, 0);
        monthlyMap.put(3, 0);
        monthlyMap.put(4, 0);
        monthlyMap.put(5, 0);
        monthlyMap.put(6, 0);
        monthlyMap.put(7, 0);
        monthlyMap.put(8, 0);
        monthlyMap.put(9, 0);
        monthlyMap.put(10, 0);
        monthlyMap.put(11, 0);
        monthlyMap.put(12, 0);
        Map<Integer, Integer> monthToOrderValue =
                orders.stream()
                        .filter(
                                o ->
                                        (getLocalDate(o.getOrderDate()).getYear() == Year.now().getValue())
                                                && o.getStatus().equalsIgnoreCase(DELIVERED.getStatus()))
                        .collect(
                                Collectors.groupingBy(
                                        order -> getLocalDate(order.getOrderDate()).getMonth().getValue()))
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        HashMap.Entry::getKey,
                                        o -> o.getValue().stream().mapToInt(s -> s.getOrderTotal().intValue()).sum()));
        monthlyMap.putAll(monthToOrderValue);
        return monthlyMap.values();
    }

    static Collection<Integer> ordersMonthlyValueForLastYear(List<Order> orders) {
        Map<Integer, Integer> monthlyMap = new HashMap<>();
        monthlyMap.put(1, 0);
        monthlyMap.put(2, 0);
        monthlyMap.put(3, 0);
        monthlyMap.put(4, 0);
        monthlyMap.put(5, 0);
        monthlyMap.put(6, 0);
        monthlyMap.put(7, 0);
        monthlyMap.put(8, 0);
        monthlyMap.put(9, 0);
        monthlyMap.put(10, 0);
        monthlyMap.put(11, 0);
        monthlyMap.put(12, 0);
        Map<Integer, Integer> monthToOrderValue =
                orders.stream()
                        .filter(
                                o ->
                                        (getLocalDate(o.getOrderDate()).getYear() == Year.now().getValue() - 1)
                                                && o.getStatus().equalsIgnoreCase(DELIVERED.getStatus()))
                        .collect(
                                Collectors.groupingBy(
                                        order -> getLocalDate(order.getOrderDate()).getMonth().getValue()))
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        HashMap.Entry::getKey,
                                        o -> o.getValue().stream().mapToInt(s -> s.getOrderTotal().intValue()).sum()));
        monthlyMap.putAll(monthToOrderValue);
        return monthlyMap.values();
    }

    static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
