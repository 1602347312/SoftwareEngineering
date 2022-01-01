package com.example.myapplication1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sign_list_fragment extends Fragment implements AdapterView.OnItemClickListener {
    Button sigh_list_btn_back;
    SimpleAdapter simpleAdapter;
    ListView lv_sign_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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


        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.signl_ist, new String[]{"record", "date", "state1", "state2"}, new int[]{R.id.txt4, R.id.txt5, R.id.txt6});
        lv_sign_list.setAdapter(simpleAdapter);

        lv_sign_list.setOnItemClickListener(this);
        return root;
    }

    private List<Map<String, Object>> getData() {

        String[] record = {"签到1", "签到2", "签到3", "签到4", "签到1", "签到2", "签到3", "签到4", "签到1", "签到2", "签到3", "签到4", "签到9", "签到10", "签到11", "签到12"};
        String[] date = {"2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31",
                "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31", "2021.12.31"};
        String[] state1 = {"已截止", "已截止", "已截止", "已截止",
                "已截止", "已截止", "已截止", "已截止",
                "已截止", "已截止", "已截止", "已截止",
                "已截止", "已截止", "已截止", "已截止",};
        String[] state2 = {"已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到", "已签到"};

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Map map = new HashMap();
            map.put("record", record[i]);
            map.put("state1", state1[i]);
            map.put("state2", state2[i]);
            map.put("date", date[i]);
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new sign_in_fragment()).commit();

    }
    private class MyListener implements View.OnClickListener {
        int mPosition;
        public MyListener(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.e("msg", "ojbk" );
        }

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Button viewBtn;
        MyListener myListener=null;
        if (convertView == null) {

            //可以理解为从vlist获取view  之后把view返回给ListView
            myListener=new MyListener(position);

            convertView = mInflater.inflate(R.layout.vlist, null);

            viewBtn = (Button)convertView.findViewById(R.id.view_btn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.title.setText((String)mData.get(position).get("title"));
        holder.info.setText((String)mData.get(position).get("info"));
        holder.viewBtn.setTag(position);
        //给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
        holder.viewBtn.setOnClickListener( myListener);

        //holder.viewBtn.setOnClickListener(MyListener(position));

        return convertView;
    }
}
}