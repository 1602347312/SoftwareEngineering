package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sign_list_fragment extends Fragment implements AdapterView.OnItemClickListener{
    Button sigh_list_btn_back;
    SimpleAdapter simpleAdapter;
    ListView lv_sign_list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sign_list, container, false);
        sigh_list_btn_back=root.findViewById(R.id.sigh_list_btn_back);
        lv_sign_list=root.findViewById(R.id.lv_sign_list);
        sigh_list_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new class_detail_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new class_detail_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });


        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.namelist_item, new String[]{"name", "divider", "num"}, new int[]{R.id.txt1, R.id.txt2, R.id.txt3});
        lv_sign_list.setAdapter(simpleAdapter);

        lv_sign_list.setOnItemClickListener(this);
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
    }
}
