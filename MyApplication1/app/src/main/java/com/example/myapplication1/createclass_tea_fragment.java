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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class createclass_tea_fragment extends Fragment {
    Button create_back,btn_create;
    EditText edt_name,edt_id;
    Spinner spinner1;
    String slot;
    Data globaldata;

    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        globaldata = (Data) this.getActivity().getApplication();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tea_create, container, false);
        create_back = root.findViewById(R.id.create_back);
        btn_create = root.findViewById(R.id.btn_create);
        edt_name = root.findViewById(R.id.edt_name);
        edt_id = root.findViewById(R.id.edt_id);
        spinner1 = root.findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] slots = getResources().getStringArray(R.array.slots);
                slot = slots[pos];
                Log.d("tea",slot);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String _class_name=edt_name.getText().toString();
                        String _class_slot=spinner1.getSelectedItem().toString();
                        String _teacher_id=edt_id.getText().toString();
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://121.37.172.109:9000/back_end/class/createClass?class_name="+_class_name+"&class_slot="+_class_slot+"&teacher_id="+_teacher_id)
                                    .post(params.build())
                                    .build();
                            Log.d("create","http://121.37.172.109:9000/back_end/class/createClass?class_name="+_class_name+"&class_slot="+_class_slot+"&teacher_id="+_teacher_id);
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.d("msg", jsonObject.getString("msg"));
                            Log.d("code", jsonObject.getString("code"));
                            String code = jsonObject.getString("code");
                            if(code.equals("0")){
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "创建成功", Toast.LENGTH_LONG);
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
                                        Toast toastCenter = Toast.makeText(getActivity(), "创建失败", Toast.LENGTH_LONG);
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
                                    Toast toastCenter = Toast.makeText(getActivity(), "创建失败1", Toast.LENGTH_LONG);
                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                    toastCenter.show();
                                }
                            });
                        }

                    }
                }).start();

            }
        });

        create_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new personal_tea_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new personal_tea_fragment()).commit();
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
