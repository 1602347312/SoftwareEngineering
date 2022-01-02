package com.example.myapplication1;
import android.app.Application;

public class Data extends Application{
    private String username;
    private String class_code;
    private String class_name;

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

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }


    @Override
    public void onCreate(){
        username = "-1";
        class_code="-1";
        class_name="-1";
        super.onCreate();
    }
}