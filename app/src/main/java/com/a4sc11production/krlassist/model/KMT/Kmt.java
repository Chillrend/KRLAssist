
package com.a4sc11production.krlassist.model.KMT;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kmt {

    @SerializedName("kmt_no")
    @Expose
    private String kmtNo;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getKmtNo() {
        return kmtNo;
    }

    public void setKmtNo(String kmtNo) {
        this.kmtNo = kmtNo;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
