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

import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.reftype.ProdOrderStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.user.MobileUserService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.admin.utils.AdminUtils.ordersMonthlyValueForLastYear;
import static com.makinus.unitedsupplies.admin.utils.AdminUtils.ordersMonthlyValueForThisYear;

// import com.makinus.unitedsupplies.admin.data.service.user.MobileUserService;

/**
 * @author abuabdul
 */
@Controller
public class DashboardController {

    private final Logger LOG = LogManager.getLogger(DashboardController.class);

    private static final String DASHBOARD_PAGE = "dashboard/dashboard";

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = {"/", "/dashboard.mk"})
    public String dashboard(ModelMap model) throws UnitedSuppliesException {
        LOG.info("Open dashboard page - {}", this.getClass().getSimpleName());
        List<Order> orders = orderService.ordersListAll();
        model.addAttribute("newOrders", newOrders(orders));
        model.addAttribute("totalOrderValue", ordersTotalValue(orders));
        int productsCount = productService.productList().size();
        model.addAttribute("productsCount", productsCount);
        model.addAttribute("mobileUsersCount", mobileUserService.mobileUserList().size());
        // Chart data
        prepareChart(model, orders);
        return DASHBOARD_PAGE;
    }

    private void prepareChart(ModelMap model, List<Order> orders) {
        Long newOrderCount = orders.stream().filter(o -> o.getStatus().equalsIgnoreCase(ProdOrderStatus.NEW.getStatus())).count();
        Long inProgressOrderCount = orders.stream().filter(o -> o.getStatus().equalsIgnoreCase(ProdOrderStatus.IN_PROGRESS.getStatus())).count();
        Long inTransitOrderCount = orders.stream().filter(o -> o.getStatus().equalsIgnoreCase(ProdOrderStatus.IN_TRANSIT.getStatus())).count();

        model.addAttribute("pieLabel", pieLabel().toArray());
        model.addAttribute("pieSeries", pieSeries(newOrderCount, inProgressOrderCount, inTransitOrderCount).toArray());
        model.addAttribute("lineSeriesForThisYear", lineSeriesForThisYear(orders).toArray());
        model.addAttribute("lineSeriesForLastYear", lineSeriesForLastYear(orders).toArray());
    }

    private List<String> pieLabel() {
        ArrayList<String> pieLabels = new ArrayList<>();
        pieLabels.add(ProdOrderStatus.NEW.getDisplay());
        pieLabels.add(ProdOrderStatus.IN_PROGRESS.getDisplay());
        pieLabels.add(ProdOrderStatus.IN_TRANSIT.getDisplay());
        return pieLabels;
    }

    private List<Double> pieSeries(Long newOrder, Long progressOrder, Long transitOrder) {
        double overAll = (double) newOrder + (double) progressOrder + (double) transitOrder;
        double newOrderPercent = overAll == 0 ? 0 : (newOrder / overAll) * 100;
        double progressOrderPercent = overAll == 0 ? 0 : (progressOrder / overAll) * 100;
        double transitOrderPercent = overAll == 0 ? 0 : (transitOrder / overAll) * 100;
        ArrayList<Double> pieSeries = new ArrayList<>();
        pieSeries.add(newOrderPercent);
        pieSeries.add(progressOrderPercent);
        pieSeries.add(transitOrderPercent);
        return pieSeries;
    }

    private Collection<Integer> lineSeriesForThisYear(List<Order> orders) {
        return ordersMonthlyValueForThisYear(orders);
    }

    private Collection<Integer> lineSeriesForLastYear(List<Order> orders) {
        return ordersMonthlyValueForLastYear(orders);
    }

    static Long ordersTotalValue(List<Order> orders) {
        return orders.stream().filter(o -> (o.getStatus().equalsIgnoreCase(ProdOrderStatus.DELIVERED.getStatus()))).mapToLong(o -> o.getOrderTotal().longValue()).sum();
    }

    static Long newOrders(List<Order> orders) {
        return orders.stream().filter(o -> (o.getStatus().equalsIgnoreCase(ProdOrderStatus.NEW.getStatus()))).count();
    }
}
