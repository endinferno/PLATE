package com.buaa.man.Util;

public class CommonRep {
    public String message;
    public String data;

    public CommonRep() {
        message = "success";
    }

    public CommonRep(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }
}
