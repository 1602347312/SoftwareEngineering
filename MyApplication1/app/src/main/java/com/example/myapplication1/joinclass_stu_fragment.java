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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class joinclass_stu_fragment extends Fragment {

    Button btn_joinclass_back,btn_joinclass_confirm;
    EditText edt_join;
    Data globaldata;
    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_joinclass_stu, container, false);
        btn_joinclass_back = root.findViewById(R.id.btn_joinclass_back);
        btn_joinclass_confirm = root.findViewById(R.id.btn_joinclass_confirm);
        edt_join = root.findViewById(R.id.edt_join);
        globaldata = (Data) this.getActivity().getApplication();


        btn_joinclass_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globaldata.setSign_in_record_code(edt_join.getText().toString());

                Log.d("msg", globaldata.getSign_in_record_id());
                new Thread(new Runnable() {
                    @Override
                    public void run() {



                    }
                }).start();

            }
        });

        btn_joinclass_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new home_stu_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new home_stu_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        return root;
    }
}
