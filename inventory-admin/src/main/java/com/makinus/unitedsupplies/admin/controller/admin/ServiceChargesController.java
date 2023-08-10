package com.makinus.Inventory.admin.controller.admin;

import com.makinus.Inventory.common.data.entity.ServiceCharge;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.ServiceCharges.ServiceChargesService;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.makinus.Inventory.common.utils.AppUtils.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author Bad_sha
 */
@Controller
public class ServiceChargesController {

    private final Logger LOG = LogManager.getLogger(ServiceChargesController.class);

    private static final String ADD_SERVICE_CHARGE_PAGE = "dashboard/service-charges/service-charges";

    @Autowired
    private ServiceChargesService serviceChargesService;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/new/charges.mk")
    public String newCharges(ModelMap model, HttpServletRequest request) throws InventoryException {
        LOG.info("Open Service Charge add form page - {}", this.getClass().getSimpleName());
        List<ServiceCharge> charges = serviceChargesService.allServiceCharges();
        model.addAttribute("serviceCharge", charges.size() > 0 ? charges.get(0) : new ServiceCharge());
        return ADD_SERVICE_CHARGE_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/service/status/change.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> changeServiceChargesStatus(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws InventoryException {
        LOG.info("Action on Service Charges Status from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("msg", "cannot be empty");
            return map;
        }
        ServiceCharge serviceCharge = Long.valueOf(pk) <= 0 ? serviceChargesService.saveServiceCharge(mapNewServiceCharge(value)) : serviceChargesService.updateServiceCharge(Long.valueOf(pk), value);
        map.put("status", "success");
        map.put("updatedBy", serviceCharge.getUpdatedBy());
        map.put("updatedDate", utcDateForMMMMDDYYYYHHMM(serviceCharge.getUpdatedDate()));
        return map;
    }

    private ServiceCharge mapNewServiceCharge(String amount) {
        ServiceCharge serviceCharge = new ServiceCharge();
        serviceCharge.setAmount(amount);
        serviceCharge.setCreatedBy(getCurrentUser());
        serviceCharge.setCreatedDate(getInstant());
        serviceCharge.setUpdatedBy(getCurrentUser());
        serviceCharge.setUpdatedDate(getInstant());
        serviceCharge.setActive(YNStatus.YES.getStatus());
        serviceCharge.setDeleted(YNStatus.NO.getStatus());
        return serviceCharge;
    }

}
