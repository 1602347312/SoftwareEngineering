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
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class modify_userinfo_fragment extends Fragment {
    EditText m_username, m_password, m_number,m_realname,m_type,m_gender;
    Button btn_m_modify;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_modifyuserinfo, container, false);
        m_username = root.findViewById(R.id.m_username);
        m_password = root.findViewById(R.id.m_password);
        m_number = root.findViewById(R.id.m_stu_tea_num);
        m_realname = root.findViewById(R.id.m_realname);
        m_type = root.findViewById(R.id.m_identity);
        m_gender=root.findViewById(R.id.m_gender);
        btn_m_modify=root.findViewById(R.id.m_modify);
        btn_m_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _username = m_username.getText().toString();
                String _password = m_password.getText().toString();
                String _realname = m_realname.getText().toString();
                String _gender = m_gender.getText().toString();
                String y = m_number.getText().toString();
                Integer x=new Integer(y);
                int _number = x.intValue();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String url="http://121.37.172.109:9000/back_end/user/modifyUserInfo?user_id="+_username+"&real_name="+_realname+"&real_id="+_number+"&password="+_password+"&gender="+_gender;
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .method("GET", null)
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            String code = jsonObject.getString("code");
                            if(code.equals("0")){
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "信息修改成功，请重新登陆", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });


                            }
                            else {
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "连接成功，修改失败", Toast.LENGTH_LONG);
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
                                    Toast toastCenter = Toast.makeText(getActivity(), "连接失败，修改失败", Toast.LENGTH_LONG);
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
        return root;
    }

}