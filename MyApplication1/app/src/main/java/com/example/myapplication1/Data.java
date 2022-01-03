package com.example.myapplication1;
import android.app.Application;
import android.util.Log;

public class Data extends Application{
    private String username;
    private String class_code;
    private String class_name;
    private String sign_in_record_code;
    private String sign_in_record_id;
    private String realId;
    private String password;
    private String token;
    private int order;     //order为1 选ass      order为2 选src      order为3 传头像
    private int ass_id;
    public int getass_id() {
        return ass_id;
    }

    public void setass_id(int ass_id) {
        this.ass_id = ass_id;
    }

    public int getorder() {
        return order;
    }

    public void setorder(int order) {
        this.order = order;
    }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void show(){
        Log.d("data_username",username);
        Log.d("data_class_code",class_code);
        Log.d("data_class_name",class_name);
        Log.d("data_sign_in_record_id",sign_in_record_id);
        Log.d("data_sign_in_recordcode",sign_in_record_code);
        Log.d("data_realId",realId);
        Log.d("data_password",password);
        Log.d("data_token",token);
        Log.d("data_order", String.valueOf(order));
        Log.d("data_ass_id", String.valueOf(ass_id));

    }

    @Override
    public void onCreate(){
        username = "-1";
        class_code="-1";
        class_name="-1";
        sign_in_record_id="-1";
        sign_in_record_code="-1";
        realId="-1";
        password="-1";
        token="-1";
        order=-1;
        ass_id=-1;
        super.onCreate();
    }
}