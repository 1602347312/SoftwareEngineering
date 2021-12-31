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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignupTabFragment extends Fragment {

    //声明变量
    EditText phone_number, password, identity, verification_code, username;
    Button signup,getcode0;

    //用来设置动画透明度的
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup_tab, container, false);

//        //把变量和控件进行绑定
//        phone_number = root.findViewById(R.id.phone_number);
//        password = root.findViewById(R.id.password);
////        confirm = root.findViewById(R.id.confirm);
//        signup = root.findViewById(R.id.signup);
//        getcode0=root.findViewById(R.id.getcode);
//        identity = root.findViewById(R.id.identity);
//        verification_code = root.findViewById(R.id.verification_code);
//        username = root.findViewById(R.id.username);
//
//        //切换为"登录"时的动画设置
//        phone_number.setTranslationY(800);
//        password.setTranslationY(800);
////        confirm.setTranslationY(800);
//        phone_number.setAlpha(v);
//        password.setAlpha(v);
////        confirm.setAlpha(v);
//        phone_number.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
////        confirm.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//
//        //注册按钮的响应
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //捕获按键对应的文本
//
//                String _phone_number = phone_number.getText().toString();
//                String _password = password.getText().toString();
//                String _identity = identity.getText().toString();
//                String _verification_code = verification_code.getText().toString();
//                String _username = username.getText().toString();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            FormBody.Builder params = new FormBody.Builder();
//                            params.add("name", _username);
//                            params.add("passwd", _password);
//                            params.add("phone", _phone_number);
//                            params.add("identifyCode", _verification_code);
//                            params.add("identity", _identity);
//
//                            OkHttpClient client = new OkHttpClient();
//                            Request request = new Request.Builder()
//                                    .url("http://47.103.9.250:9000/api/v1/userservice/registration")
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
//                                        Toast toastCenter = Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_LONG);
//                                        //确定Toast显示位置，并显示
//                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                                        toastCenter.show();
//                                    }
//                                });
//
//
//                            }
//                            else {
//                                getActivity().runOnUiThread( new  Runnable() {
//                                    @Override
//                                    public  void  run() {
//                                        Toast toastCenter = Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_LONG);
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
//                                    Toast toastCenter = Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_LONG);
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
