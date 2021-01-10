package com.ridoy.xmnotice;

public class UniversityCategoryNamesModel {

    private String imageurl;
    private String universityname;

    public UniversityCategoryNamesModel() {
    }

    public UniversityCategoryNamesModel(String imageurl, String universityname) {
        this.imageurl = imageurl;
        this.universityname = universityname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }
}
