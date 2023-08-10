package com.makinus.unitedsupplies.common.data.service.ServiceCharges;

import com.makinus.unitedsupplies.common.data.dao.ServiceChargeRepository;
import com.makinus.unitedsupplies.common.data.entity.ServiceCharge;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static java.lang.String.format;

@Service
@Transactional
public class ServiceChargesServiceImpl implements ServiceChargesService {

    private final Logger LOG = LogManager.getLogger(ServiceChargesServiceImpl.class);

    private final ServiceChargeRepository serviceChargeRepository;

    public ServiceChargesServiceImpl(@Autowired ServiceChargeRepository serviceChargeRepository) {
        this.serviceChargeRepository = serviceChargeRepository;
    }

    @Override
    public ServiceCharge saveServiceCharge(ServiceCharge serviceCharge) {
        LOG.info("Add New Service Charge in the database");
        ServiceCharge savedServiceCharge = serviceChargeRepository.save(serviceCharge);
        LOG.info("Saved New Product in the database");
        return savedServiceCharge;
    }

    @Override
    public ServiceCharge findServiceCharges(Long id) throws UnitedSuppliesException {
        Optional<ServiceCharge> chargesOptional = serviceChargeRepository.findById(id);
        if (chargesOptional.isPresent()) {
            return chargesOptional.get();
        }
        throw new UnitedSuppliesException(format("Order is not found with the id %d", id));
    }

    @Override
    public List<ServiceCharge> allServiceCharges() {
        LOG.info("List All Service Charges from database");
        return serviceChargeRepository.listAllServiceCharges();
    }

    @Override
    public ServiceCharge updateServiceCharge(Long id, String amount) throws UnitedSuppliesException {
        ServiceCharge serviceCharge = findServiceCharges(id);
        serviceCharge.setAmount(amount);
        serviceCharge.setUpdatedBy(getCurrentUser());
        serviceCharge.setUpdatedDate(getInstant());
        return serviceCharge;
    }

}
