package com.example.myapplication1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class home_tea_fragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    SimpleAdapter simpleAdapter;
    Data globaldata;
    List<String> classCode = new ArrayList<>();
    List<String> classTeacherId = new ArrayList<>();
    List<String> className = new ArrayList<>();
    List<String> classSlot = new ArrayList<>();
    int l;

    @Nullable
    @Override
    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tea_home, container, false);
        listView = root.findViewById(R.id.lv2);
        globaldata = (Data) this.getActivity().getApplication();


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/class/getClassList?real_id=" + globaldata.getRealId() + "&type=true")
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            String code = jsonObject.getString("code");
            if (!jsonObject.getString("data").equals("null")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));

                if (code.equals("0")) {

                    SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("class_code", "2");
                    editor.apply();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject temp = data.getJSONObject(i);
                                    JSONArray a = temp.getJSONArray("classes");
                                    JSONObject classes = a.getJSONObject(i);
                                    String t = classes.optString("classCode", null);
                                    classCode.add(t);

                                    t = classes.optString("classTeacherId", null);
                                    classTeacherId.add(t);

                                    t = classes.optString("className", null);
                                    className.add(t);

                                    t = classes.optString("classSlot", null);
                                    classSlot.add(t);


                                }
                                l = data.length();
                                Log.d("tag2", "" + l);
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


        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.class_item, new String[]{"className", "classCode", "classSlot"}, new int[]{R.id.tv1, R.id.tv2, R.id.tv3});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
        return root;
    }

    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Map map = new HashMap();
            map.put("className", className.get(i));
            map.put("classCode", classCode.get(i));
            map.put("classSlot", classSlot.get(i));
            list.add(map);
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = listView.getAdapter().getItem(position).toString();
        Log.e("___", "position:" + position + "text" + text);
        globaldata.setClass_code(classCode.get(position));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new class_detail_fragment()).commit();

    }
}
