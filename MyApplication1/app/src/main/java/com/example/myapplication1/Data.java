package com.example.myapplication1;
import android.app.Application;

public class Data extends Application{
    private String username;
    private String class_code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }


    @Override
    public void onCreate(){
        username = "-1";
        class_code="-1";

        super.onCreate();
    }
}