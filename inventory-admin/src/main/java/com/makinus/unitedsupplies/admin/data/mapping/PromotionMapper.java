/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.PromotionForm;
import com.makinus.unitedsupplies.common.data.entity.Promotion;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("PromotionMapper")
public class PromotionMapper
        implements EntityMapper<PromotionForm, Promotion>,
        EntityUpdateMapper<PromotionForm, Promotion>,
        EntityRemapper<Promotion, PromotionForm> {

    private final Logger LOG = LogManager.getLogger(PromotionMapper.class);

    @Autowired
    private ImageWriter imageWriter;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public Promotion map(PromotionForm promotionForm) throws UnitedSuppliesException {
        LOG.info("Map Promotion Form to Promotion Entity");
        Promotion promotion = new Promotion();
        try {
            promotion.setPromotionName(promotionForm.getPromotionName());
            promotion.setDescription(promotionForm.getDescription());
            promotion.setOriginalFileName(promotionForm.getPromotionImage().getOriginalFilename());
            promotion.setImage(promotionForm.getPromotionImage().getBytes());
            promotion.setCreatedDateAsFolderName(
                    localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
            promotion.setImagePath(getS3UrlFromAttachment(promotionForm.getPromotionImage(), PROMOTION_PREFIX, amazonS3Client));
            promotion.setCreatedBy(getCurrentUser());
            promotion.setCreatedDate(getInstant());
            promotion.setUpdatedBy(getCurrentUser());
            promotion.setUpdatedDate(getInstant());
            promotion.setActive(
                    promotionForm.isActivePromotion() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            promotion.setDeleted(YNStatus.NO.getStatus());
        } catch (IOException e) {
            LOG.warn("PromotionMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return promotion;
    }

    @Override
    public PromotionForm remap(Promotion promotion) throws UnitedSuppliesException {
        LOG.info("Map Promotion Entity to promotion Form");
        PromotionForm promotionForm = new PromotionForm();
        promotionForm.setPromotionID(String.valueOf(promotion.getId()));
        promotionForm.setPromotionName(promotion.getPromotionName());
        promotionForm.setDescription(promotion.getDescription());
        promotionForm.setActivePromotion(
                promotion.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        return promotionForm;
    }

    @Override
    public Promotion map(PromotionForm promotionForm, Promotion promotion)
            throws UnitedSuppliesException {
        LOG.info("Map Promotion Form to Updated Promotion Entity");
        try {
            promotion.setPromotionName(promotionForm.getPromotionName());
            promotion.setDescription(promotionForm.getDescription());
            if (promotionForm.getEditPromotionImage() != null
                    && !promotionForm.getEditPromotionImage().isEmpty()) {
                // Just to ensure old image is preserved while editing
                promotion.setOriginalFileName(promotionForm.getEditPromotionImage().getOriginalFilename());
                promotion.setImage(promotionForm.getEditPromotionImage().getBytes());
                promotion.setCreatedDateAsFolderName(
                        localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                promotion.setImagePath(getS3UrlFromAttachment(promotionForm.getEditPromotionImage(), PROMOTION_PREFIX, amazonS3Client));
            }
            promotion.setUpdatedBy(getCurrentUser());
            promotion.setUpdatedDate(getInstant());
            promotion.setActive(
                    promotionForm.isActivePromotion() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
        } catch (IOException e) {
            LOG.warn("Promotion Mapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return promotion;
    }

    private String imagePath(Promotion promotion) throws UnitedSuppliesException {
        return imageWriter.writeImage(
                promotion.getImage(),
                promotion.getCreatedDateAsFolderName(),
                String.valueOf(AppUtils.timestamp()));
    }
}
