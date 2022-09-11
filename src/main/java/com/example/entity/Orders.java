package com.example.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by BAD_SHA
 */

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"productOrders"})
@ToString(exclude = {"productOrders"})
@Builder(toBuilder = true)
@TypeDef(name = "json", typeClass = JsonType.class)
public class Orders {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "ORDER_NUMBER")
    private String orderNo;
    @Column(name = "ORDER_DATE")
    private Date orderDate;
    @Column(name = "ORDER_SUBTOTAL")
    private BigDecimal subTotal;
    @Column(name = "DELIVERY_CHARGES")
    private BigDecimal deliveryCharges;
    @Column(name = "DISCOUNT")
    private BigDecimal discount;
    @Column(name = "ORDER_TOTAL")
    private BigDecimal orderTotal;
    @Type(type = "json")
    @Column(name = "DELIVERY_ADDRESS", columnDefinition = "json")
    private String deliveryAddress;
    @Type(type = "json")
    @Column(name = "BILLING_ADDRESS", columnDefinition = "json")
    private String billingAddress;
    @Column(name = "IS_BILL_ADDR_SAME")
    private String billingAddressSame;
    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    @Column(name = "ORDER_STATUS")
    private String orderStatus;
    @Column(name = "ORDER_TYPE")
    private String orderType;
    @Column(name = "ORDER_VIEWED")
    private String orderViewed;
    @Column(name = "GW_ORDER_ID")
    private String gatewayOrderId;
    @Column(name = "PAYMENT_PROVIDER")
    private String paymentProvider;
    @Column(name = "PAID_AMOUNT")
    private BigDecimal paidAmount;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    @Column(name = "DELETED")
    private String deleted;

    @Transient
    private String paymentDisplay;

    @OneToMany(mappedBy = "order")
    private List<ProductOrder> productOrders;
}

