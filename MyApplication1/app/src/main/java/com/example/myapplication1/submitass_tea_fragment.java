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

public class submitass_tea_fragment extends Fragment implements AdapterView.OnItemClickListener{
    Button submit_btn_back;
    ListView ass_lv3;
    Data globaldata;
    SimpleAdapter simpleAdapter;


    List<String> studentId = new ArrayList<>();
    List<String> assignmentId = new ArrayList<>();
    List<String> submissionTime = new ArrayList<>();
    List<String> content = new ArrayList<>();
    List<String> score = new ArrayList<>();
    List<String> state = new ArrayList<>();

    int l;


    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tea_submit, container, false);
        submit_btn_back=root.findViewById(R.id.submit_btn_back);
        ass_lv3=root.findViewById(R.id.ass_lv3);
        globaldata = (Data) this.getActivity().getApplication();



        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/submits/getSubmissionList?assignment_id=" + globaldata.getAssignmentId())
                    .get()
                    .build();
            Log.d("ass","http://121.37.172.109:9000/back_end/submits/getSubmissionList?assignment_id=" + globaldata.getAssignmentId());
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
                        String t;
                        JSONObject temp;
                        try {
                            for (int i = 0; i < data.length(); i++) {
                                temp = data.getJSONObject(i);

                                t = temp.optString("studentId", null);
                                studentId.add(t);

                                t = temp.optString("assignmentId", null);
                                assignmentId.add(t);

                                t = temp.optString("submissionTime", null);
                                submissionTime.add(t);

                                t = temp.optString("content", null);
                                content.add(t);

                                t = temp.optString("score", null);
                                score.add(t);

                                t = temp.optString("state", null);
                                state.add(t);
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
                        Toast toastCenter = Toast.makeText(getActivity(), "当前无作业记录", Toast.LENGTH_LONG);
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

        submit_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_tea_fragment()).commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

//                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_stu_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.ass_item, new String[]{"studentId", "submissionTime", "score", "state"}, new int[]{R.id.ass_t1, R.id.ass_t2, R.id.ass_t3,R.id.ass_t4});
        ass_lv3.setAdapter(simpleAdapter);

        ass_lv3.setOnItemClickListener(this);

        return  root;
}
    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Log.d("msg11",""+l);

            Map map = new HashMap();
            map.put("studentId", studentId.get(i));
            map.put("submissionTime", submissionTime.get(i));
            map.put("score", score.get(i));
            map.put("state", state.get(i));
            list.add(map);
        }
        ass_lv3.setAdapter(simpleAdapter);

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = ass_lv3.getAdapter().getItem(position).toString();
        Log.e("msg", "position:" + position + "text" + text);



    }

}