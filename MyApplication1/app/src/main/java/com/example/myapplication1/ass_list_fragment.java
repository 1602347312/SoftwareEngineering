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

public class ass_list_fragment extends Fragment implements AdapterView.OnItemClickListener{
    Button ass_btn_back;
    ListView ass_lv1;
    Data globaldata;
    SimpleAdapter simpleAdapter;

    List<String> assignmentId = new ArrayList<>();
    List<String> assignmentTitle = new ArrayList<>();
    List<String> assignmentRequirement = new ArrayList<>();
    List<String> assignmentDeadline = new ArrayList<>();
    List<String> assignmentTotalScore = new ArrayList<>();
    List<String> assignmentClassId = new ArrayList<>();
    List<String> assignmentState = new ArrayList<>();


    int l;

    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ass_list, container, false);
        ass_btn_back=root.findViewById(R.id.ass_btn_back);
        ass_lv1=root.findViewById(R.id.ass_lv1);
        globaldata = (Data) this.getActivity().getApplication();
        ass_btn_back.setOnClickListener(new View.OnClickListener() {
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

        //请求签到列表
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/assignment/getAssignmentList?class_code=" + globaldata.getClass_code())
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

                        try {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject temp = data.getJSONObject(i);

                                String t = temp.optString("assignmentId", null);
                                assignmentId.add(t);

                                t = temp.optString("assignmentTitle", null);
                                assignmentTitle.add(t);

                                t = temp.optString("assignmentRequirement", null);
                                assignmentRequirement.add(t);

                                t = temp.optString("assignmentDeadline", null);
                                assignmentDeadline.add(t);

                                t = temp.optString("assignmentTotalScore", null);
                                assignmentTotalScore.add(t);

                                t = temp.optString("assignmentClassId", null);
                                assignmentClassId.add(t);

                                t = temp.optString("assignmentState", null);
                                assignmentState.add(t);
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



        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.ass_item, new String[]{"assignmentTitle", "assignmentDeadline", "assignmentState", "assignmentTotalScore"}, new int[]{R.id.ass_t1, R.id.ass_t2, R.id.ass_t3,R.id.ass_t4});
        ass_lv1.setAdapter(simpleAdapter);

        ass_lv1.setOnItemClickListener(this);

    return root;
    }
    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Log.d("msg11",""+l);

            Map map = new HashMap();
            map.put("assignmentTitle", assignmentTitle.get(i));
            map.put("assignmentDeadline", assignmentDeadline.get(i));
            map.put("assignmentState", assignmentState.get(i));
            map.put("assignmentTotalScore", assignmentTotalScore.get(i));
            list.add(map);
        }
        ass_lv1.setAdapter(simpleAdapter);

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = ass_lv1.getAdapter().getItem(position).toString();
        Log.e("msg", "position:" + position + "text" + text);
        Log.e("msg", assignmentId.get(position));

        globaldata.setass_id(Integer.parseInt(assignmentId.get(position)));
        globaldata.setorder(1);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        getActivity().startActivityForResult(intent, 10);



    }
}
