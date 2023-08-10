/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by abuabdul
 */
@Entity
@Table(name = "ORDER_REF")
public class OrderRef {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REF")
    private Long ref = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRef address = (OrderRef) o;
        return Objects.equals(id, address.id) && Objects.equals(ref, address.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref);
    }

}
