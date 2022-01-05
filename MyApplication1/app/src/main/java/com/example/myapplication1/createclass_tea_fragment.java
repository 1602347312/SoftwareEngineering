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

public class createclass_tea_fragment extends Fragment {
    Button create_back,btn_create;
    EditText edt_name,edt_id;
    Spinner spinner1;
    String slot;
    Data globaldata;

    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        globaldata = (Data) this.getActivity().getApplication();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tea_create, container, false);
        create_back = root.findViewById(R.id.create_back);
        btn_create = root.findViewById(R.id.btn_create);
        edt_name = root.findViewById(R.id.edt_name);
        edt_id = root.findViewById(R.id.edt_id);
        spinner1 = root.findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] slots = getResources().getStringArray(R.array.slots);
                slot = slots[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {



                    }
                }).start();

            }
        });

        create_back.setOnClickListener(new View.OnClickListener() {
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
