package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupTabFragment extends Fragment {

    //声明变量
    EditText username, password, number,realname,type,gender;
    Button btn_signup;

    //用来设置动画透明度的
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup_tab, container, false);

        //把变量和控件进行绑定
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        number = root.findViewById(R.id.stu_tea_num);
        realname = root.findViewById(R.id.realname);
        type = root.findViewById(R.id.identity);
        gender=root.findViewById(R.id.gender);
        btn_signup=root.findViewById(R.id.signup);
        //注册按钮的响应
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _username = username.getText().toString();
                String _password = password.getText().toString();
                String _realname = realname.getText().toString();
                String _gender = gender.getText().toString();
                int _number = (new Integer(username.getText().toString())).intValue();
                boolean _type;
                if(type.getText().toString()=="学生"){
                    _type=true;
                }
                else{
                    _type=false;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json="{\n" +
                                    "  \"avatar\": \"\",\n" +
                                    "  \"gender\": \""+_gender+"\",\n" +
                                    "  \"password\": \""+_password+"\",\n" +
                                    "  \"realId\": "+_number+",\n" +
                                    "  \"realName\": \""+_realname+"\",\n" +
                                    "  \"type\":    "+_type+" ,\n" +
                                    "  \"userId\": \""+_username+"\"\n" +
                                    "}";
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://121.37.172.109:9000/back_end/user/register")
                                    .post(RequestBody.create(MediaType.parse("application/json"),json))
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.d("msg", jsonObject.getString("msg"));
                            Log.d("code", jsonObject.getString("code"));

                            String code=jsonObject.getString("code");

                            if(code.equals("200")){
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });


                            }
                            else {
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "连接成功，注册失败", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread( new  Runnable() {
                                @Override
                                public  void  run() {
                                    Toast toastCenter = Toast.makeText(getActivity(), "连接失败，注册失败", Toast.LENGTH_LONG);
                                    //确定Toast显示位置，并显示
                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                    toastCenter.show();
                                }
                            });
                        }

                    }
                }).start();

            }
        });
//        getcode0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String _phone_number = phone_number.getText().toString();
//                String _password = password.getText().toString();
//                String _identity = identity.getText().toString();
//                String _verification_code = verification_code.getText().toString();
//                String _username = username.getText().toString();
//
//
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            FormBody.Builder params = new FormBody.Builder();
//
//                            params.add("phone", _phone_number);
//
//
//                            OkHttpClient client = new OkHttpClient();
//                            Request request = new Request.Builder()
//                                    .url("http://47.103.9.250:9000/api/v1/userservice/registercode")
////                                    .url("http://127.0.0.1:9000/api/v1/userservice/registration")
//                                    .post(params.build())
//                                    .build();
//                            Response response = client.newCall(request).execute();
//                            String responseData = response.body().string();
//                            JSONObject jsonObject = new JSONObject(responseData);
//                            Log.d("msg", jsonObject.getString("msg"));
//                            Log.d("object", jsonObject.getString("object"));
//                            String object = jsonObject.getString("object");
//                            String code=jsonObject.getString("code");
//
//                            if(code.equals("200")){
//                                getActivity().runOnUiThread( new  Runnable() {
//                                    @Override
//                                    public  void  run() {
//                                        Toast toastCenter = Toast.makeText(getActivity(), "获取验证码成功", Toast.LENGTH_LONG);
//                                        //确定Toast显示位置，并显示
//                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                                        toastCenter.show();
//                                    }
//                                });
//
//                            }
//                            else {
//                                getActivity().runOnUiThread( new  Runnable() {
//                                    @Override
//                                    public  void  run() {
//                                        Toast toastCenter = Toast.makeText(getActivity(), "获取验证码失败", Toast.LENGTH_LONG);
//                                        //确定Toast显示位置，并显示
//                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                                        toastCenter.show();
//                                    }
//                                });
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            getActivity().runOnUiThread( new  Runnable() {
//                                @Override
//                                public  void  run() {
//                                    Toast toastCenter = Toast.makeText(getActivity(), "获取验证码失败", Toast.LENGTH_LONG);
//                                    //确定Toast显示位置，并显示
//                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                                    toastCenter.show();
//                                }
//                            });
//                        }
//
//                    }
//                }).start();
//
//            }
//        });

        return root;

    }
}
