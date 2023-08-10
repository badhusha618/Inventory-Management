package com.makinus.unitedsupplies.common.data.service.weight;

import com.makinus.unitedsupplies.common.data.entity.Weight;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

public interface WeightService {

    boolean isWeightExists(final String weight, final Long category);

    Weight saveWeight(final Weight weight);

    List<Weight> weightList();

    List<Weight> weightListByCategory(Long categoryId);

    Weight updateWeight(final Weight weight);

    Weight findWeight(Long id) throws UnitedSuppliesException;

    Weight removeWeight(Long id) throws UnitedSuppliesException;
}
