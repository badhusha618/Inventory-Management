package com.makinus.unitedsupplies.common.data.service.ServiceCharges;

import com.makinus.unitedsupplies.common.data.entity.ServiceCharge;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

public interface ServiceChargesService {

    ServiceCharge saveServiceCharge(ServiceCharge serviceCharge) throws UnitedSuppliesException;

    ServiceCharge findServiceCharges(final Long id) throws UnitedSuppliesException;

    ServiceCharge updateServiceCharge(Long id, String amount) throws UnitedSuppliesException;

    List<ServiceCharge> allServiceCharges();
}
