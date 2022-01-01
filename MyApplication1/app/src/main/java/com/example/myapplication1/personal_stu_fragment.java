package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class personal_stu_fragment extends Fragment {
    TextView number;
    TextView realname;
    Button btn_change_info;
    Button btn_exit;
    Button btn_update_icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_stu_personal, container, false);
        btn_change_info=root.findViewById(R.id.btn_change_info);
        number=root.findViewById(R.id.number);
        realname=root.findViewById(R.id.realname);
        btn_exit=root.findViewById(R.id.btn_exit);
        btn_update_icon=root.findViewById(R.id.btn_update_icon);
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        String mess = null;
        if(bundle!=null){
            mess = bundle.getString("username");

        }
        else mess="no";
        Log.d("msg",mess);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
