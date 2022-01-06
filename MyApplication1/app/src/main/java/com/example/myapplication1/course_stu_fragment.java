package com.example.myapplication1;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class course_stu_fragment extends Fragment{
    Data globaldata;
    TextView t11,t12,t13,t14,t15,t21,t22,t23,t24,t25,t31,t32,t33,t34,t35,t41,t42,t43,t44,t45,t51,t52,t53,t54,t55;
    List<String> classCode = new ArrayList<>();
    List<String> classTeacherId = new ArrayList<>();
    List<String> className = new ArrayList<>();
    List<String> classSlot = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_stu_course, container, false);
        globaldata = (Data) this.getActivity().getApplication();
        t11=root.findViewById(R.id.cls_11);
        t12=root.findViewById(R.id.cls_12);
        t13=root.findViewById(R.id.cls_13);
        t14=root.findViewById(R.id.cls_14);
        t15=root.findViewById(R.id.cls_15);
        t21=root.findViewById(R.id.cls_21);
        t22=root.findViewById(R.id.cls_22);
        t23=root.findViewById(R.id.cls_23);
        t24=root.findViewById(R.id.cls_24);
        t25=root.findViewById(R.id.cls_25);
        t31=root.findViewById(R.id.cls_31);
        t32=root.findViewById(R.id.cls_32);
        t33=root.findViewById(R.id.cls_33);
        t34=root.findViewById(R.id.cls_34);
        t35=root.findViewById(R.id.cls_35);
        t41=root.findViewById(R.id.cls_41);
        t42=root.findViewById(R.id.cls_42);
        t43=root.findViewById(R.id.cls_43);
        t44=root.findViewById(R.id.cls_44);
        t45=root.findViewById(R.id.cls_45);
        t51=root.findViewById(R.id.cls_51);
        t52=root.findViewById(R.id.cls_52);
        t53=root.findViewById(R.id.cls_53);
        t54=root.findViewById(R.id.cls_54);
        t55=root.findViewById(R.id.cls_55);

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/class/getClassList?real_id=" + globaldata.getRealId() + "&type=true")
                    .get()
                    .build();
            Log.d("tag2","http://121.37.172.109:9000/back_end/class/getClassList?real_id=" + globaldata.getRealId() + "&type=true");
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            String code = jsonObject.getString("code");
            if (!jsonObject.getString("data").equals("null")) {

                JSONArray data = new JSONArray(jsonObject.getString("data"));//data是数组
                JSONObject temp = data.getJSONObject(0);//temp是这个数组的第一个
                JSONArray temp1 = temp.getJSONArray("classes");//temp1是classes这个数组

                if (code.equals("0")) {
                    SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("class_code", "2");
                    editor.apply();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < temp1.length(); i++) {
                                    JSONObject classes = temp1.getJSONObject(i);

                                    if(classes.optString("classSlot").equals("周一 1-2")){
                                        t11.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周一 3-4")){
                                        t12.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周一 5-6")){
                                        t13.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周一 7-8")){
                                        t14.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周一 9-10")){
                                        t15.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周二 1-2")){
                                        t21.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周二 3-4")){
                                        t22.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周二 5-6")){
                                        t23.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周二 7-8")){
                                        t24.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周二 9-10")){
                                        t25.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周三 1-2")){
                                        t31.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周三 3-4")){
                                        t32.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周三 5-6")){
                                        t33.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周三 7-8")){
                                        t34.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周三 9-10")){
                                        t35.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周四 1-2")){
                                        t41.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周四 3-4")){
                                        t42.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周四 5-6")){
                                        t43.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周四 7-8")){
                                        t44.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周四 9-10")){
                                        t45.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周五 1-2")){
                                        t51.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周五 3-4")){
                                        t52.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周五 5-6")){
                                        t53.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周五 7-8")){
                                        t54.setText(classes.optString("className"));
                                    }
                                    if(classes.optString("classSlot").equals("周五 9-10")){
                                        t55.setText(classes.optString("className"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toastCenter = Toast.makeText(getActivity(), "请求成功12登陆失败", Toast.LENGTH_LONG);
                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
                            toastCenter.show();

                        }
                    });
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toastCenter = Toast.makeText(getActivity(), "当前无课程", Toast.LENGTH_LONG);
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
                    Toast toastCenter = Toast.makeText(getActivity(), "请求失败34登陆失败", Toast.LENGTH_LONG);
                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                    toastCenter.show();
                }
            });
        }
        return root;
    }

}
