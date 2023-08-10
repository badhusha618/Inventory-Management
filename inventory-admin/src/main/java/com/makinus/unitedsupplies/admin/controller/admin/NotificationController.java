package com.makinus.Inventory.admin.controller.admin;

import com.makinus.Inventory.admin.data.dao.template.builder.TemplateSelector;
import com.makinus.Inventory.admin.data.forms.NotificationForm;
import com.makinus.Inventory.admin.data.mapping.NotificationMapper;
import com.makinus.Inventory.common.data.entity.Notifications;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Product;
import com.makinus.Inventory.common.data.entity.ProductOrder;
import com.makinus.Inventory.common.data.reftype.NotificationOrderStatus;
import com.makinus.Inventory.common.data.reftype.NotificationPaymentStatus;
import com.makinus.Inventory.common.data.reftype.NotificationType;
import com.makinus.Inventory.common.data.reftype.ProdOrderStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.notifications.NotificationService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.prodorder.ProductOrderService;
import com.makinus.Inventory.common.data.service.product.ProductService;
import com.makinus.Inventory.common.exception.InventoryException;
import com.makinus.Inventory.common.sms.SMSService;
import com.makinus.Inventory.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.Inventory.common.data.reftype.NotificationStatus.MSG_FAILED;
import static com.makinus.Inventory.common.data.reftype.NotificationStatus.MSG_SENT;
import static com.makinus.Inventory.common.utils.AppUtils.*;

/**
 * @author Bad_sha
 */
@Controller
public class NotificationController {

    private final Logger LOG = LogManager.getLogger(NotificationController.class);

    private static final String LIST_NOTIFICATION_PAGE = "dashboard/notification/notification-list";
    private static final String NOTIFICATION_PAGE = "dashboard/notification/notification";

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    @Qualifier("NotificationMapper")
    private NotificationMapper notificationMapper;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/list/notification.mk")
    public String listNotification(ModelMap model) {
        LOG.info("List Notification page - {}", this.getClass().getSimpleName());
        Map<Long, String> productListMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Product::getProductName));
        model.addAttribute("notificationList", notificationService.notificationsList());
        model.addAttribute("productListMap", productListMap);
        return LIST_NOTIFICATION_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/notification.mk")
    public String openSendNotificationPage(ModelMap model) {
        LOG.info("Open Notification Page - {}", this.getClass().getSimpleName());
        NotificationForm notificationForm = new NotificationForm();
        notificationForm.setType(NotificationType.SMS.getStatus());
        List<Order> ordersList = orderService.ordersList();
        model.addAttribute("orderMap", ordersList.stream().collect(Collectors.toMap(Order::getOrderNo, Function.identity())));
        model.addAttribute("notificationForm", notificationForm);
        model.addAttribute("ordersList", ordersList);
        model.addAttribute("notificationTypes", NotificationType.values());
        model.addAttribute("orderStatus", ProdOrderStatus.values());
        model.addAttribute("notificationOrderStatus", NotificationOrderStatus.values());
        model.addAttribute("notificationPaymentStatus", NotificationPaymentStatus.values());
        return NOTIFICATION_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping(value = "/notification.mk")
    public String sendNotification(@ModelAttribute("notificationForm") NotificationForm notificationForm, RedirectAttributes redirectAttrs) throws InventoryException, ExecutionException, InterruptedException, ParseException {
        LOG.info("Send Notification to customer - {}", this.getClass().getSimpleName());
        if (!notificationForm.getStatus().equalsIgnoreCase(NotificationOrderStatus.NEW.getStatus())) {
            Map<Long, Product> productListMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
            Product product = productListMap.get(Long.valueOf(notificationForm.getProductId()));
            notificationForm.setProductCode(product.getProductCode());
            notificationForm.setProductName(product.getProductName());
            notificationForm.setItemFirstName(product.getProductName());
            notificationForm.setItemLastName("");
            if (product.getProductName() != null && product.getProductName().length() > SMS_VARIABLE_LENGTH) {
                List<String> productNames = productNameSplitBySize(product.getProductName(), SMS_VARIABLE_LENGTH);
                notificationForm.setItemFirstName(productNames.get(0));
                notificationForm.setItemLastName(productNames.get(1));
            }
        }
        if (notificationForm.getType().equalsIgnoreCase(NotificationType.SMS.getStatus())) {
            String template = TemplateSelector.getTemplate(notificationForm);
            LOG.info("Notification template is  - {}", template);
            ListenableFuture<Integer> asyncResult = smsService.sendOTP(AppUtils.formattedPhoneNo(notificationForm.getMobileNo(), INDIAN_CODE_WITHOUT_PLUS), template);
            while (true) {
                if (asyncResult.isDone()) {
                    int responseCode = asyncResult.get();
                    if (responseCode == 200) {
                        notificationForm.setMsgStatus(MSG_SENT.getStatus());
                    } else {
                        notificationForm.setMsgStatus(MSG_FAILED.getStatus());
                    }
                    break;
                }
                Thread.sleep(10);
            }
        }
        redirectAttrs.addFlashAttribute("orderNo", notificationForm.getOrderNo());
        Notifications savedNotifications = notificationService.saveNotifications(notificationMapper.map(notificationForm));
        redirectAttrs.addFlashAttribute("notificationName", savedNotifications.getCustomerName());
        return "redirect:/list/notification.mk";
    }

    @GetMapping(value = "/product/name.mk", produces = "application/json")
    @ResponseBody
    public List<Tuple<Long, String>> viewProductName(@RequestParam String id) {
        LOG.info("Fetch product name based on order no from database - {}", this.getClass().getSimpleName());
        List<Tuple<Long, String>> productNames = new ArrayList<>();
        List<ProductOrder> productOrders = productOrderService.getProductListByOrderRef(Long.valueOf(id));
        Map<Long, String> productListMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Product::getProductName));
        productOrders.forEach(productOrder -> productNames.add(new Tuple<>(productOrder.getProductId(), productListMap.get(productOrder.getProductId()))));
        LOG.info("Product Names Details {}", productNames.toString());
        return productNames;
    }

    @PostMapping(value = "/message/preview.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> previewMessage(@ModelAttribute NotificationForm notificationForm, HttpServletResponse response) {
        LOG.info("Preview the message by template - {}", this.getClass().getSimpleName());
        if (!notificationForm.getStatus().equalsIgnoreCase(NotificationOrderStatus.NEW.getStatus())) {
            Map<Long, String> productListMap = productService.productList().stream().collect(Collectors.toMap(Product::getId, Product::getProductName));
            String productName = productListMap.get(Long.valueOf(notificationForm.getProductId()));
            notificationForm.setItemFirstName(productName);
            notificationForm.setItemLastName("");
            if (productName != null && productName.length() > SMS_VARIABLE_LENGTH) {
                List<String> productNames = productNameSplitBySize(productName, SMS_VARIABLE_LENGTH);
                notificationForm.setItemFirstName(productNames.get(0));
                notificationForm.setItemLastName(productNames.get(1));
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();

        if (notificationForm.getOrderRef() != null) {
            String template = TemplateSelector.getTemplate(notificationForm);
            map.put("status", "success");
            map.put("preview", template);
            return map;
        }
        map.put("status", "error");
        return map;
    }
}
