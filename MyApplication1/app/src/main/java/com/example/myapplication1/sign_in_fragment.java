package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class sign_in_fragment extends Fragment {
    Button btn, btn2;
    EditText cd;
    Data globaldata;
    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);
        btn = root.findViewById(R.id.button);
        btn2 = root.findViewById(R.id.button2);
        cd = root.findViewById(R.id.edittext);
        globaldata = (Data) this.getActivity().getApplication();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globaldata.setSign_in_record_code(cd.getText().toString());
                //赋值应该在登录里
                globaldata.setRealId("22");
                //取值应该在sign_list里
                globaldata.setSign_in_record_id("9");
                Log.d("msg", globaldata.getSign_in_record_code());
                new Thread(new Runnable() {
                    @Override
                    public void run() {                      //android studio版本问题    已解决
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://121.37.172.109:9000/back_end/signIn/checkIn?sign_in_record_code="+globaldata.getSign_in_record_code()+"&sign_in_record_id=" +globaldata.getSign_in_record_id()+"&student_id=+"+globaldata.getRealId())
                                    .get()
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.d("msg", jsonObject.getString("msg"));
                            Log.d("code", jsonObject.getString("code"));
                            String code = jsonObject.getString("code");

                            if (code.equals("0")) {

                                SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spf.edit();
                                editor.putString("class_code", "2");
                                editor.apply();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast toastCenter = null;
                                        try {
                                            toastCenter = Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_LONG);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                        Log.d("tag", "ok");

                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new class_detail_fragment()).commit();

                                    }
                                });
                            }
                            else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = null;
                                        try {
                                            toastCenter = Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_LONG);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new sign_list_fragment()).commit();

                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toastCenter = Toast.makeText(getActivity(), "请求失败，登陆失败", Toast.LENGTH_LONG);
                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                    toastCenter.show();
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
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new sign_list_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new sign_list_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        return root;
    }
}
