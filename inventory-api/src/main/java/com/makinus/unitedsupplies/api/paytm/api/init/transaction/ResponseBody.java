package com.makinus.unitedsupplies.api.paytm.api.init.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBody {

    private ResultInfo resultInfo;
    private String txnToken;
    @JsonProperty(value = "isPromoCodeValid")
    private boolean promoCodeValid;
    @JsonProperty(value = "authenticated")
    private boolean authenticated;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getTxnToken() {
        return txnToken;
    }

    public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
    }

    public boolean isPromoCodeValid() {
        return promoCodeValid;
    }

    public void setPromoCodeValid(boolean promoCodeValid) {
        this.promoCodeValid = promoCodeValid;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
