package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class sign_in_fragment extends Fragment {
    Button btn, btn2;
    EditText cd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);
        btn = root.findViewById(R.id.button);
        btn2 = root.findViewById(R.id.button2);
        cd = root.findViewById(R.id.edittext);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _code = cd.getText().toString();
                Log.d("msg", _code);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("signcode", _code);


                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://47.103.9.250:9000/api/v1/userservice/login")
                                    .post(params.build())
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.d("msg", jsonObject.getString("msg"));
                            Log.d("object", jsonObject.getString("object"));
                            String code = jsonObject.getString("code");

                            if (code.equals("200")) {

                                SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spf.edit();
                                editor.putString("signcode", _code);
                                editor.apply();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
//                                if (object.equals("1")) {//
//                                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                                    intent.putExtra("coed", _code);
//                                    startActivity(intent);
//                                } //教师和学生的判断
//                                else {
//                                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                                    intent.putExtra("code", _code);
//                                    startActivity(intent);
//                                }

                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "签到失败", Toast.LENGTH_LONG);
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();

                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("msg", "跳转");
//                                    Toast toastCenter = Toast.makeText(getActivity(), "请求失败，登陆失败", Toast.LENGTH_LONG);
//                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                                    toastCenter.show();
//                                    Intent intent = new Intent(getActivity(), sign_in_fragment.class);
//                                    intent.putExtra("signcode", _code);
//                                    startActivity(intent);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new course_stu_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent intent = new Intent(getActivity(), sign_in_fragment.class);

                            startActivity(intent);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(getActivity(), sign_in_fragment.class);

                                    startActivity(intent);
                                }
                            });
                        }

                    }
                }).

                        start();
            }
        });

        return root;
    }
}
