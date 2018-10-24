package com.example.ashwin.library;

public class UserDetails {
    private String email, user;
    private int uid;
    private String BKname, doi, dor;

    public UserDetails() {
    }

    public UserDetails(String email, String user, int uid, String BKname, String doi, String dor) {
        this.email = email;
        this.user = user;
        this.uid = uid;
        this.BKname = BKname;
        this.doi = doi;
        this.dor = dor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getBKname() {
        return BKname;
    }

    public void setBKname(String BKname) {
        this.BKname = BKname;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getDor() {
        return dor;
    }

    public void setDor(String dor) {
        this.dor = dor;
    }
}
