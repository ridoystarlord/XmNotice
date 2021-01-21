package com.ridoy.xmnotice;

public class UserInformation {

    private String uId,name,profileImageUrl,sscpoint,sscyear,hscpoint,hscyear,currentscore,totalscore,totalearn;

    public UserInformation() {
    }

    public UserInformation(String uId, String name, String profileImageUrl, String sscpoint, String sscyear, String hscpoint, String hscyear, String currentscore, String totalscore, String totalearn) {
        this.uId = uId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.sscpoint = sscpoint;
        this.sscyear = sscyear;
        this.hscpoint = hscpoint;
        this.hscyear = hscyear;
        this.currentscore = currentscore;
        this.totalscore = totalscore;
        this.totalearn = totalearn;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getSscpoint() {
        return sscpoint;
    }

    public void setSscpoint(String sscpoint) {
        this.sscpoint = sscpoint;
    }

    public String getSscyear() {
        return sscyear;
    }

    public void setSscyear(String sscyear) {
        this.sscyear = sscyear;
    }

    public String getHscpoint() {
        return hscpoint;
    }

    public void setHscpoint(String hscpoint) {
        this.hscpoint = hscpoint;
    }

    public String getHscyear() {
        return hscyear;
    }

    public void setHscyear(String hscyear) {
        this.hscyear = hscyear;
    }

    public String getCurrentscore() {
        return currentscore;
    }

    public void setCurrentscore(String currentscore) {
        this.currentscore = currentscore;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    public String getTotalearn() {
        return totalearn;
    }

    public void setTotalearn(String totalearn) {
        this.totalearn = totalearn;
    }
}
