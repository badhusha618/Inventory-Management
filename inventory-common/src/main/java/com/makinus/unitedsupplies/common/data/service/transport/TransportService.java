/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.transport;

import com.makinus.unitedsupplies.common.data.entity.Transport;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface TransportService {

    boolean isQuantityAndDistanceExists(final Integer quantity, final Integer distance, final String transGroup);

    List<Transport> transportList() throws InventoryException;

    List<Transport> transportListByTransGroups(List<String> transGroup);

    List<Transport> transportListByTransGroup(String transGroup);

    List<Transport> addNewTransports(List<Transport> transports);

    Transport addNewTransport(final Transport transport);

    Transport findTransport(Long id) throws InventoryException;

    Transport updateTransport(final Transport transport);

    Transport removeTransport(Long id) throws InventoryException;

    List<Transport> transportGroupList() throws InventoryException;
}
