package com.example.myapplication1;
import android.app.Application;

public class Data extends Application{
    private String username;
    private String class_code;
    private String class_name;
    private String sign_in_record_code;
    private String sign_in_record_id;
    private String realId;


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

    public String getSign_in_record_code() {
        return sign_in_record_code;
    }

    public void setSign_in_record_code(String sign_in_record_code) {
        this.sign_in_record_code = sign_in_record_code;
    }

    public String getSign_in_record_id() {
        return sign_in_record_id;
    }

    public void setSign_in_record_id(String sign_in_record_id) {
        this.sign_in_record_id = sign_in_record_id;
    }

    public String getRealId() {
        return realId;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    @Override
    public void onCreate(){
        username = "-1";
        class_code="-1";
        class_name="-1";
        sign_in_record_id="-1";
        sign_in_record_code="-1";
        realId="-1";
        super.onCreate();
    }
}