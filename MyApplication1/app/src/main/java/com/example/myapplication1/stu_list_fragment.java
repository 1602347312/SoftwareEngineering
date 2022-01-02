package com.example.myapplication1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class stu_list_fragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    Button btn;
    SimpleAdapter simpleAdapter;
    Data globaldata;
    TextView txt1;

    String[] num;
    String[] name;
    int l;
    String[] divider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_stu_list, container, false);
        listView = root.findViewById(R.id.lv2);
        btn = root.findViewById(R.id.btn_back);
        txt1=root.findViewById(R.id.txt1);
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.namelist_item, new String[]{"name", "divider", "num"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(this);
        globaldata= (Data) this.getActivity().getApplication();
        txt1.setText(globaldata.getClass_name());
        btn.setOnClickListener(new View.OnClickListener() {
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
        getStuList();
        return root;
    }

    private List<Map<String, Object>> getData() {

        String[] num = {"1951111", "1951111", "1951111", "1951111", "1951111", "1951111", "1951111", "1951111","1951111", "1951111", "1951111", "1951111", "1951111", "1951111", "1951111", "1951111"};
        String[] name = {"张三", "张三四", "张三", "李三四", "张三", "张三四", "张三", "李三四","张三", "张三四", "张三", "李三四", "张三", "张三四", "张三", "李三四"};
        String[] divider = {"学号:", "学号:", "学号:", "学号:", "学号:", "学号:", "学号:", "学号:","学号:", "学号:", "学号:", "学号:", "学号:", "学号:", "学号:", "学号:"};
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Map map = new HashMap();
            map.put("num", num[i]);
            map.put("divider", divider[i]);
            map.put("name", name[i]);
            list.add(map);
        }
        listView.setAdapter(simpleAdapter);

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = listView.getAdapter().getItem(position).toString();
        Log.e("msg", "position:" + position + "text" + text);
    }

    public void getStuList() {
        new Thread(new Runnable() {
            @Override
            public void run() {                      //android studio版本问题    已解决
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://121.37.172.109:9000/back_end/class/getStudentList?class_code=" + globaldata.getClass_code())
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    Log.d("msg", jsonObject.getString("msg"));
                    Log.d("code", jsonObject.getString("code"));
                    String code = jsonObject.getString("code");
//                            String code=jsonObject.getString("code");
                    JSONArray data = new JSONArray(jsonObject.getString("data"));

                    if (code.equals("0")) {

                        SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putString("class_code", "2");
                        editor.apply();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("data", "stulist" + data.toString());
                                Log.d("data", "stulist" + data);
                                Log.d("data", "stulist" + data.length());

                                try {
                                    for(int i=0;i<data.length();i++)
                                    {  JSONObject temp = data.getJSONObject(i);
                                    String student_id = temp.optString("student_id", null);
                                    String nm = temp.optString("name", null);
                                    num[i]=student_id;
                                    name[i]=nm;
                                    }
                                    l=data.length();
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

            }
        }).start();

    }
}
