package com.makinus.unitedsupplies.common.data.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Transient;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Base implements Serializable {

  private static final long serialVersionUID = 23429873L;

  @Transient private String status;

  @ApiModelProperty(hidden = true)
  @Transient
  private String otp; // TODO remove this

  public Base() {}

  public Base(String status) {
    this.status = status;
  }

  public Base(String status, String otp) {
    this.status = status;
    this.otp = otp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Base base = (Base) o;
    return Objects.equals(status, base.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    return "Base{" + "status='" + status + '\'' + '}';
  }
}
