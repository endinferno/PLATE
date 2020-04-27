package com.buaa.man.Util;

public class CommonRep {
    public String message;
    public String date;

    public CommonRep() {
        message = "success";
    }

    public CommonRep(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
