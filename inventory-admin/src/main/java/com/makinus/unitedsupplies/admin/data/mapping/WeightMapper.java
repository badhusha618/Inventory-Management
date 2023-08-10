package com.makinus.Inventory.admin.data.mapping;

import com.makinus.Inventory.admin.data.forms.WeightForm;
import com.makinus.Inventory.common.data.entity.Weight;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityRemapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.Inventory.common.utils.AppUtils.getCurrentUser;
import static com.makinus.Inventory.common.utils.AppUtils.getInstant;

@Component
@Qualifier("WeightMapper")
public class WeightMapper
        implements EntityMapper<WeightForm, Weight>,
        EntityUpdateMapper<WeightForm, Weight>,
        EntityRemapper<Weight, WeightForm> {

    private final Logger LOG = LogManager.getLogger(WeightMapper.class);

    @Override
    public Weight map(WeightForm weightForm) throws InventoryException {
        LOG.info("Map Weight Form to Weight Entity");
        Weight weight = new Weight();
        weight.setWeight(weightForm.getWeight());
        weight.setCategory(Long.valueOf(weightForm.getCategory()));
        weight.setActive(weightForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
        weight.setCreatedBy(getCurrentUser());
        weight.setCreatedDate(getInstant());
        weight.setUpdatedBy(getCurrentUser());
        weight.setUpdatedDate(getInstant());
        weight.setDeleted(YNStatus.NO.getStatus());
        return weight;
    }

    @Override
    public Weight map(WeightForm weightForm, Weight weight) throws InventoryException {

        LOG.info("Map Weight Form to Updated Weight weight");
        weight.setWeight(weightForm.getWeight());
        // quality.setCategory(Long.valueOf(qualityForm.getCategory()));
        weight.setActive(weightForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
        weight.setUpdatedBy(getCurrentUser());
        weight.setUpdatedDate(getInstant());
        weight.setDeleted(YNStatus.NO.getStatus());
        return weight;
    }

    @Override
    public WeightForm remap(Weight weight) throws InventoryException {
        LOG.info("Map Weight Entity to Quality Form");
        WeightForm weightForm = new WeightForm();
        weightForm.setWeightID(String.valueOf(weight.getId()));
        weightForm.setWeight(weight.getWeight());
        weightForm.setCategory(String.valueOf(weight.getCategory()));
        weightForm.setActive(weight.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        return weightForm;
    }


}
