package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class launch_sign_fragment extends Fragment {

        Button back,launch;
        EditText title;
        Spinner spinner2;
        String duration;
        Data globaldata;
        String class_code;
    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tea_lauchsign, container, false);

        back = root.findViewById(R.id.back);
        launch = root.findViewById(R.id.launch);
        title = root.findViewById(R.id.edt_name);

        spinner2 = root.findViewById(R.id.spinner2);
        globaldata = (Data) this.getActivity().getApplication();
        class_code=globaldata.getClass_code();

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] slots = getResources().getStringArray(R.array.time);
                if(slots[pos].equals("1min"))
                    duration="1";
                else if(slots[pos].equals("2min"))
                    duration="2";
                else if(slots[pos].equals("5min"))
                    duration="5";
                else if(slots[pos].equals("10min"))
                    duration="10";
                Log.d("tea",""+slots[pos]);
                Log.d("tea",""+duration);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new class_detail_tea_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }

                    }
                }).start();
            }
        });


        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           //code here

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //code here
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        return  root;
    }
}
