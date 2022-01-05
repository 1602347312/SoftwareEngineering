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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class sign_list_fragment extends Fragment implements AdapterView.OnItemClickListener {
    Button sigh_list_btn_back;
    SimpleAdapter simpleAdapter;
    ListView lv_sign_list;
    Data globaldata;

    int l;
    List<String> signInRecordId = new ArrayList<>();
    List<String> signInRecordTitle = new ArrayList<>();
    List<String> signInRecordLaunchTime = new ArrayList<>();
    List<String> signInRecordDuration = new ArrayList<>();
    List<String> signInRecordState = new ArrayList<>();
    List<String> signInRecordClassId = new ArrayList<>();
    List<String> signInRecordCode = new ArrayList<>();
    List<String> stu_sign_state = new ArrayList<>();




    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_list, container, false);
        sigh_list_btn_back = root.findViewById(R.id.sigh_list_btn_back);
        lv_sign_list = root.findViewById(R.id.lv_sign_list);
        sigh_list_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(globaldata.getIsStu().equals("学生"))
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_stu_fragment()).commit();
                            else if(globaldata.getIsStu().equals("老师"))
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_tea_fragment()).commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(globaldata.getIsStu().equals("学生"))
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_stu_fragment()).commit();
                                    else if(globaldata.getIsStu().equals("老师"))
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_tea_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });
        globaldata = (Data) this.getActivity().getApplication();


        //请求签到列表
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/signInRecord/getSignInRecordList?class_code=" + globaldata.getClass_code())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            Log.d("msg", jsonObject.getString("msg"));
            Log.d("code", jsonObject.getString("code"));
            String code = jsonObject.getString("code");


            if (code.equals("0")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));

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

                                String t = temp.optString("signInRecordId", null);
                                signInRecordId.add(t);

                                t = temp.optString("signInRecordTitle", null);
                                signInRecordTitle.add(t);

                                t = temp.optString("signInRecordLaunchTime", null);
                                signInRecordLaunchTime.add(t);

                                t = temp.optString("signInRecordDuration", null);
                                signInRecordDuration.add(t);

                                t = temp.optString("signInRecordState", null);
                                signInRecordState.add(t);

                                t = temp.optString("signInRecordClassId", null);
                                signInRecordClassId.add(t);

                                t = temp.optString("signInRecordCode", null);
                                signInRecordCode.add(t);
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
                        Toast toastCenter = Toast.makeText(getActivity(), "当前无签到记录", Toast.LENGTH_LONG);
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
        //请求某一学生的某次签到是否签了
        for(int i=0;i<l;i++)
        {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://121.37.172.109:9000/back_end/signInRecord/getSignInState?sign_in_record_id="+signInRecordId.get(i)+"&student_id="+globaldata.getRealId())
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                JSONObject jsonObject = new JSONObject(responseData);
                Log.d("msg", jsonObject.getString("msg"));
                Log.d("code", jsonObject.getString("code"));
                String code = jsonObject.getString("code");
                String tof = jsonObject.getString("data");

                if (code.equals("0")) {

                    SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("class_code", "2");
                    editor.apply();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("msg1",tof);
                            if(tof.equals("true"))
                            {
                                stu_sign_state.add("已签到");
                            }
                            else
                                stu_sign_state.add("未签到");
                            Log.d("msg1",stu_sign_state.toString());
                        }
                    });
                }
                else if (code.equals("-1")) {

                    SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("class_code", "2");
                    editor.apply();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("msg1",tof);
                                stu_sign_state.add("无此记录");
                            Log.d("msg1",stu_sign_state.toString());

                        }
                    });
                }
                else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toastCenter = Toast.makeText(getActivity(), "请求成功，登陆失败", Toast.LENGTH_SHORT);
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
                        Toast toastCenter = Toast.makeText(getActivity(), "请求失败，登陆失败", Toast.LENGTH_SHORT);
                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                        toastCenter.show();
                    }
                });
            }

        }


        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.signl_ist, new String[]{"record", "date", "state1", "state2"}, new int[]{R.id.txt4, R.id.txt5, R.id.txt6,R.id.txt7});
        lv_sign_list.setAdapter(simpleAdapter);

        lv_sign_list.setOnItemClickListener(this);
        return root;
    }

    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Log.d("msg11",stu_sign_state.toString());
            Log.d("msg11",""+l);

            Map map = new HashMap();
            map.put("record", signInRecordTitle.get(i));
            map.put("state1", signInRecordState.get(i));
            map.put("state2", stu_sign_state.get(i));
            map.put("date", signInRecordLaunchTime.get(i));
            list.add(map);
        }
        lv_sign_list.setAdapter(simpleAdapter);

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = lv_sign_list.getAdapter().getItem(position).toString();
        Log.e("msg", "position:" + position + "text" + text);
        Log.e("msg", signInRecordState.get(position));
        if(!signInRecordState.get(position).equals("已截止")&&stu_sign_state.get(position).equals("未签到")){
            //取值应该在sign_list里
            globaldata.setSign_in_record_id(signInRecordId.get(position));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new sign_in_fragment()).commit();}
        else
        {
            Toast toastCenter = null;
            toastCenter = Toast.makeText(getActivity(), "无法签到", Toast.LENGTH_LONG);
            toastCenter.setGravity(Gravity.CENTER, 0, 0);
            toastCenter.show();
        }
    }
}