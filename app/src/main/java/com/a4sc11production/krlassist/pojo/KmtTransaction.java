package com.a4sc11production.krlassist.pojo;

import com.google.firebase.Timestamp;

public class KmtTransaction {
    private int amount;
    private Timestamp date;
    private String kmt_no;
    private String stasiun_at;
    private String transaction_type;

    public KmtTransaction(){

    }

    public KmtTransaction(int amount, Timestamp date, String kmt_no, String stasiun_at, String transaction_type) {
        this.amount = amount;
        this.date = date;
        this.kmt_no = kmt_no;
        this.stasiun_at = stasiun_at;
        this.transaction_type = transaction_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getKmt_no() {
        return kmt_no;
    }

    public void setKmt_no(String kmt_no) {
        this.kmt_no = kmt_no;
    }

    public String getStasiun_at() {
        return stasiun_at;
    }

    public void setStasiun_at(String stasiun_at) {
        this.stasiun_at = stasiun_at;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
