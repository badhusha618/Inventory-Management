package com.makinus.unitedsupplies.common.data.service.ServiceCharges;

import com.makinus.unitedsupplies.common.data.entity.ServiceCharge;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

public interface ServiceChargesService {

    ServiceCharge saveServiceCharge(ServiceCharge serviceCharge) throws InventoryException;

    ServiceCharge findServiceCharges(final Long id) throws InventoryException;

    ServiceCharge updateServiceCharge(Long id, String amount) throws InventoryException;

    List<ServiceCharge> allServiceCharges();
}
