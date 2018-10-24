package com.example.ashwin.library;

public class addreqlistdata {

    public String bname;
    public String stid;
    boolean accepted = false;
    String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStid() {
        return stid;
    }


    public void setStid(String stid) {
        this.stid = stid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
