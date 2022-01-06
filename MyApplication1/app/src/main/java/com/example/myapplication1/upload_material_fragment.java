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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class upload_material_fragment extends Fragment {
    Button upload_back,btn_upload;
    EditText upload_name;
    Data globaldata;

    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        globaldata = (Data) this.getActivity().getApplication();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_upload_material, container, false);

        upload_back=root.findViewById(R.id.upload_back);
        btn_upload=root.findViewById(R.id.btn_upload);
        upload_name=root.findViewById(R.id.upload_name);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globaldata.setMaterial_title(upload_name.getText().toString());
                globaldata.setorder(2);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                getActivity().startActivityForResult(intent, 10);

            }
        });

        upload_back.setOnClickListener(new View.OnClickListener() {
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
        return root;
    }
}
