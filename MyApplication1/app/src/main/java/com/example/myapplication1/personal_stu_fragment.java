package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class personal_stu_fragment extends Fragment {
    TextView number;
    TextView realname;
    ImageView picture;
    Button btn_change_info;
    Button btn_exit;
    Button btn_update_icon;
    @Nullable
    @Override
    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_stu_personal, container, false);
        btn_change_info=root.findViewById(R.id.btn_change_info);
        number=root.findViewById(R.id.number);
        realname=root.findViewById(R.id.realname);
        picture=root.findViewById(R.id.picture);
        btn_exit=root.findViewById(R.id.btn_exit);
        btn_update_icon=root.findViewById(R.id.btn_update_icon);
        String username = getActivity().getIntent().getStringExtra("username");
        String token = getActivity().getIntent().getStringExtra("token");
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/user/getUserByToken")
                    .post(RequestBody.create(MediaType.parse("application/json"),token))
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
//            Log.d("msg", jsonObject.getString("msg"));
//            Log.d("code", jsonObject.getString("code"));
            String code=jsonObject.getString("code");
            JSONObject data= new JSONObject(jsonObject.getString("data"));
            if(code.equals("0")){
                number.setText(data.getString("realId"));
                realname.setText(data.getString("realName"));
//                picture.setImageURI(Uri.parse(data.getString("avatar")));
                picture.setImageBitmap(getBitmap(data.getString("avatar")));
            }
            else {
                number.setText("连接成功");
                realname.setText("获取失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            number.setText("连接失败");
            realname.setText("获取失败");
        }
//        Bundle bundle =this.getArguments();//得到从Activity传来的数据
//        String mess = null;
//        if(bundle!=null){
//            mess = bundle.getString("username");
//
//        }
//        else mess="no";
//        Log.d("msg",mess);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
    public static Bitmap getBitmap(String path) throws IOException {  //显示图片函数

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
