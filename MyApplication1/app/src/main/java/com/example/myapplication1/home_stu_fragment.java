package com.example.myapplication1;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home_stu_fragment extends Fragment implements AdapterView.OnItemClickListener{
    ListView listView;
    SimpleAdapter simpleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_stu_home, container, false);
        listView=(ListView) root.findViewById(R.id.lv1);
        simpleAdapter = new SimpleAdapter(getActivity(),getData(),R.layout.class_item,new String[]{"num","divider","name"},new int[]{R.id.tv1,R.id.tv2,R.id.tv3});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);

        return root;
    }
    private List<Map<String,Object>> getData() {
        String [] num={"stu1","stu2","stu3","stu4","stu5","stu6","stu7","stu8","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9"};
        String [] name={"stu11","stu22","stu33","stu44","stu55","stu66","stu77","stu8","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9"};
        String [] test={"stu111","stu222","stu332","stu443","stu554","stu665","stu776","stu8","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9","stu9"};
        List<Map<String,Object>> list= new ArrayList<>();
        for(int i=0;i<20;i++){
            Map  map = new HashMap();
            map.put("num",num[i]);
            map.put("divider",test[i]);
            map.put("name",name[i]);
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
        Log.e("___","position:"+position+"text"+text );
    }
}
