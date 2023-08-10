package com.makinus.unitedsupplies.common.data.service.weight;

import com.makinus.unitedsupplies.common.data.dao.WeightRepository;
import com.makinus.unitedsupplies.common.data.entity.Weight;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class WeightServiceImpl implements WeightService {

    private final Logger LOG = LogManager.getLogger(WeightServiceImpl.class);

    private final WeightRepository weightRepository;

    public WeightServiceImpl(@Autowired WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    @Override
    public boolean isWeightExists(String weight, Long category) {
        LOG.info("Check if UnitedSupplies Weight is available from database");
        Optional<Weight> findWeight = weightRepository.findAvailableCategoryForWeight(weight, category);
        return findWeight.isPresent();
    }

    @Override
    public Weight saveWeight(Weight weight) {
        LOG.info("Saving Weight in the database");
        Weight savedWeight = weightRepository.save(weight);
        LOG.info("Saved Weight in the database");
        return savedWeight;
    }

    @Override
    public List<Weight> weightList() {
        LOG.info("List Weight from database");
        return weightRepository.listOfWeight();
    }

    @Override
    public List<Weight> weightListByCategory(Long categoryId) {
        LOG.info("List Weight By Category from database");
        return weightRepository.listOfWeightByCategory(categoryId);
    }

    @Override
    public Weight updateWeight(final Weight weight) {
        LOG.info("Update existing weight in the catalog");
        return weightRepository.save(weight);
    }

    @Override
    public Weight findWeight(Long id) throws InventoryException {
        Optional<Weight> weightOptional = weightRepository.findById(id);
        if (weightOptional.isPresent()) {
            return weightOptional.get();
        }
        throw new InventoryException(String.format("Weight is not found with the id %d", id));
    }

    @Override
    public Weight removeWeight(Long id) throws InventoryException {
        Optional<Weight> weightOptional = weightRepository.findById(id);
        if (weightOptional.isPresent()) {
            Weight weight = weightOptional.get();
            weight.setDeleted(YNStatus.YES.getStatus());
            weight.setUpdatedBy(getCurrentUser());
            weight.setUpdatedDate(getInstant());
            return weight;
        }
        throw new InventoryException(String.format("Promotion is not found with the id %d", id));
    }

}
