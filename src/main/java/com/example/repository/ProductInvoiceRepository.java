package com.example.repository;

import com.example.entity.ProductInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Created by Bad_sha 24/07/22
 */

@Repository
public interface ProductInvoiceRepository extends JpaRepository<ProductInvoice,Integer>{
}
