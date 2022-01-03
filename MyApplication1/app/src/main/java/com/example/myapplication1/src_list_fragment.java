package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class src_list_fragment extends Fragment implements AdapterView.OnItemClickListener{
    Button src_btn_back;
    ListView src_lv;
    Data globaldata;
    SimpleAdapter simpleAdapter;

    List<String> materialId = new ArrayList<>();
    List<String> materialTitle = new ArrayList<>();
    List<String> materialUploadTime = new ArrayList<>();
    List<String> materialSize = new ArrayList<>();
    List<String> materialUploader = new ArrayList<>();
    List<String> materialUrl = new ArrayList<>();
    List<String> materialClassId = new ArrayList<>();



    int l;

    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_src_list, container, false);


        src_btn_back=root.findViewById(R.id.src_btn_back);
        src_lv=root.findViewById(R.id.src_lv);
        globaldata = (Data) this.getActivity().getApplication();
        src_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new class_detail_fragment()).commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new class_detail_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/material/getMaterialList?class_code=" + globaldata.getClass_code())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            Log.d("msg", jsonObject.getString("msg"));
            Log.d("code", jsonObject.getString("code"));
            String code = jsonObject.getString("code");
            JSONArray data = new JSONArray(jsonObject.getString("data"));

            if (code.equals("0")) {

                SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("class_code", "2");
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String t;
                        try {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject temp = data.getJSONObject(i);

                                t = temp.optString("materialId", null);
                                materialId.add(t);

                                t = temp.optString("materialTitle", null);
                                materialTitle.add(t);

                                t = temp.optString("materialUploadTime", null);
                                materialUploadTime.add(t);

                                t = temp.optString("materialSize", null);
                                materialSize.add(t);

                                t = temp.optString("materialUploader", null);
                                materialUploader.add(t);

                                t = temp.optString("materialUrl", null);
                                materialUrl.add(t);

                                t = temp.optString("materialClassId", null);
                                materialClassId.add(t);
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
                        Toast toastCenter = Toast.makeText(getActivity(), "请求成功，登陆失败", Toast.LENGTH_LONG);
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
                    Toast toastCenter = Toast.makeText(getActivity(), "请求失败，登陆失败", Toast.LENGTH_LONG);
                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                    toastCenter.show();
                }
            });
        }




        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.signl_ist, new String[]{"materialTitle", "materialUploadTime", "materialUploader", "materialSize"}, new int[]{R.id.txt4, R.id.txt5, R.id.txt6,R.id.txt7});
        src_lv.setAdapter(simpleAdapter);

        src_lv.setOnItemClickListener(this);
        return root;
    }
    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Log.d("msg11",""+l);

            Map map = new HashMap();
            map.put("materialId", materialId.get(i));
            map.put("materialTitle", materialTitle.get(i));
            map.put("materialUploadTime", materialUploadTime.get(i));
            map.put("materialSize", materialSize.get(i));
            map.put("materialUploader", materialUploader.get(i));
            map.put("materialUrl", materialUrl.get(i));
            map.put("materialClassId", materialClassId.get(i));


            list.add(map);
        }
        src_lv.setAdapter(simpleAdapter);

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = src_lv.getAdapter().getItem(position).toString();
        Log.e("msg", "position:" + position + "text" + text);




    }
}
