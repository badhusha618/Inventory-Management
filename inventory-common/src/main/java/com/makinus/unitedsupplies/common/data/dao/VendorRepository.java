package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/** @author Kingson */
public interface VendorRepository extends JpaRepository<Vendor,Long> {

    @Query("select v from Vendor v where v.deleted = 'F' order by updatedDate desc")
    List<Vendor> listAllVendors();

    @Query("select v from Vendor v where v.vendorCode = :vendorCode and v.deleted = 'F'")
    Vendor findActiveVendor(@Param("vendorCode") String vendorCode);

    @Query("select v from Vendor v where v.deleted = 'F' order by updatedDate desc")
    List<Vendor> listAllActiveVendors();

    @Query("select v from Vendor v where v.id in :ids and v.deleted = 'F'")
    List<Vendor> listAllVendorsByIds(@Param("ids") List<Long> ids);

    Optional<Vendor> findById(Long id);

}
