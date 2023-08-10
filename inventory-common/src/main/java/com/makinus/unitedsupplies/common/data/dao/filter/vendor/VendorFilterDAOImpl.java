package com.makinus.unitedsupplies.common.data.dao.filter.vendor;

import com.makinus.unitedsupplies.common.data.form.VendorFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.makinus.unitedsupplies.common.data.dao.filter.vendor.VendorFilterCriteriaBuilder.aVendorFilterCriteria;

/**
 * @author Bad_sha
 */
@Repository
public class VendorFilterDAOImpl implements VendorFilterDAO {
    private final Logger LOG = LogManager.getLogger(VendorFilterDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vendor> filterVendor(VendorFilterForm vendorFilterForm) throws InventoryException {
        try {
            LOG.info("List filtered Vendor page");
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Vendor> query = builder.createQuery(Vendor.class);
            VendorFilterCriteriaBuilder vendorFilterCriteriaBuilder = aVendorFilterCriteria(builder, query)
                    .vendorCode(vendorFilterForm.getVendorCode())
                    .vendorName(vendorFilterForm.getVendorName())
                    .companyName(vendorFilterForm.getCompanyName())
                    .dateRange(vendorFilterForm.getFromDate(), vendorFilterForm.getToDate());

            if (vendorFilterCriteriaBuilder.isParamSet()) {
                return entityManager.createQuery(query.where(vendorFilterCriteriaBuilder.predicate())).getResultList();
            }
        } catch (ParseException ex) {
            throw new InventoryException(ex.getMessage());
        }
        return new ArrayList<>();
    }

}
