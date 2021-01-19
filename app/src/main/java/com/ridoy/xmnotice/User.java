package com.ridoy.xmnotice;

public class User {
    private String fname, fcoins;
    private long coins = 0;

    public User() {
    }

    public User(String fname, String fcoins) {
        this.fname = fname;
        this.fcoins = fcoins;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFcoins() {
        return fcoins;
    }

    public void setFcoins(String fcoins) {
        this.fcoins = fcoins;
    }
}
