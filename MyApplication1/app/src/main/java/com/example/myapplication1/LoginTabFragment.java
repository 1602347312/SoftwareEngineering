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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginTabFragment extends Fragment {

    EditText username,password;
    Button btn_login;

    //用来设置动画透明度的
    float v=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_login_tab,container,false);

        Data globaldata= (Data) this.getActivity().getApplication();


        username=root.findViewById(R.id.username);
        password=root.findViewById(R.id.password);

        btn_login=root.findViewById(R.id.btn_login);
        //登录按钮的响应
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _name=username.getText().toString();
                String _password=password.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {                      //android studio版本问题    已解决
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("password", _password);
                            params.add("user_id",  _name);
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://121.37.172.109:9000/back_end/user/login")
                                    .post(params.build())
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.d("msg", jsonObject.getString("msg"));
                            Log.d("code", jsonObject.getString("code"));
                            String code = jsonObject.getString("code");
//                            String code=jsonObject.getString("code");
                            JSONObject data= new JSONObject(jsonObject.getString("data"));
                            String token=data.getString("token").toString();

                            if(code.equals("0")){
                                globaldata.setUsername(_name);
                                SharedPreferences spf = getActivity().getSharedPreferences("spf",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("username",_name);
                                editor.putString("password",_password);

                                editor.apply();
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                                if(data.getString("type").equals(true)){//true学生 false老师
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.putExtra("username",_name);
                                    intent.putExtra("token",token);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.putExtra("username",_name);
                                    intent.putExtra("token",token);
                                    startActivity(intent);
                                }

                            }
                            else {
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "请求成功，登陆失败", Toast.LENGTH_LONG);
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
        return root;

    }
}
