package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.ServiceCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceChargeRepository extends JpaRepository<ServiceCharge, Long> {

    Optional<ServiceCharge> findById(Long id);

    @Query("select s from ServiceCharge s")
    List<ServiceCharge> listAllServiceCharges();
}
