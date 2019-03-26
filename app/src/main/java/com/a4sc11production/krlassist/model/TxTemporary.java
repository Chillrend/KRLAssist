package com.a4sc11production.krlassist.model;

public class TxTemporary {
private String tx_type;
private String stasiun;
private int amount;

    public TxTemporary(String tx_type, String stasiun, int amount) {
        this.tx_type = tx_type;
        this.stasiun = stasiun;
        this.amount = amount;
    }

    public String getTx_type() {
        return tx_type;
    }

    public void setTx_type(String tx_type) {
        this.tx_type = tx_type;
    }

    public String getStasiun() {
        return stasiun;
    }

    public void setStasiun(String stasiun) {
        this.stasiun = stasiun;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
