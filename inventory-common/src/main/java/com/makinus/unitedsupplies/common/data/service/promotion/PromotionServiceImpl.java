/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.promotion;

import com.makinus.unitedsupplies.common.data.dao.PromotionRepository;
import com.makinus.unitedsupplies.common.data.entity.Promotion;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
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
import static java.lang.String.format;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    private final Logger LOG = LogManager.getLogger(PromotionServiceImpl.class);

    private final PromotionRepository promotionRepository;

    private final ImageWriter imageWriter;

    public PromotionServiceImpl(
            @Autowired PromotionRepository promotionRepository, ImageWriter imageWriter) {
        this.promotionRepository = promotionRepository;
        this.imageWriter = imageWriter;
    }

    @Override
    public Promotion savePromotion(Promotion promotion) {
        LOG.info("Saving Promotion in the database");
        Promotion savedPromotion = promotionRepository.save(promotion);
        LOG.info("Saved Sale Promotion in the database");
        return savedPromotion;
    }

    @Override
    public Promotion updateSalePromotion(Promotion promotion) throws InventoryException {
        LOG.info("Update existing Sale Promotion in the database");
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> promotionsList() {
        LOG.info("List Sale Promotions from database");
        return promotionRepository.listAllPromotions();
    }

    @Override
    public List<Promotion> activePromotionsList() {
        LOG.info("List Active Sale Promotions from database");
        return promotionRepository.listAllActivePromotions();
    }

    @Override
    public Promotion removePromotion(Long id) throws InventoryException {
        Optional<Promotion> promotionOptional = promotionRepository.findById(Long.valueOf(id));
        if (promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
            promotion.setDeleted(YNStatus.YES.getStatus());
            promotion.setUpdatedBy(getCurrentUser());
            promotion.setUpdatedDate(getInstant());
            return promotion;
        }
        throw new InventoryException(format("Sale Promotion is not found with the id %s", id));
    }

    @Override
    public Promotion findSalePromotion(Long id) throws InventoryException {
        Optional<Promotion> promotionOptional = promotionRepository.findById(id);
        if (promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
//      promotion.setImage(imageWriter.readImage(get(promotion.getImagePath())));
            return promotion;
        }
        throw new InventoryException(format("Sale Promotion is not found with the id %d", id));
    }
}
