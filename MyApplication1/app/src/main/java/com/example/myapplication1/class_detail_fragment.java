package com.example.myapplication1;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class class_detail_fragment extends Fragment{
    Button class_detail_btn_back,class_detail_btn_stu_list,class_detail_homework,class_detail_sign,class_detail_source;
    TextView class_detail_txt_name,class_detail_txt_course,class_detail_txt_slot,class_detail_txt_code;
    ImageView class_detail_icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_class_detail, container, false);
        class_detail_btn_back=root.findViewById(R.id.class_detail_btn_back);
        class_detail_btn_stu_list=root.findViewById(R.id.class_detail_btn_stu_list);
        class_detail_homework=root.findViewById(R.id.class_detail_homework);
        class_detail_sign=root.findViewById(R.id.class_detail_sign);
        class_detail_source=root.findViewById(R.id.class_detail_source);

        class_detail_icon=root.findViewById(R.id.class_detail_icon);

        class_detail_txt_name=root.findViewById(R.id.class_detail_txt_name);
        class_detail_txt_course=root.findViewById(R.id.class_detail_txt_course);
        class_detail_txt_slot=root.findViewById(R.id.class_detail_txt_slot);
        class_detail_txt_code=root.findViewById(R.id.class_detail_txt_code);

        class_detail_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new course_stu_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new course_stu_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        class_detail_btn_stu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new stu_list_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new stu_list_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });
        //作业界面还没写
        class_detail_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new stu_list_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new stu_list_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });
        //signlist还没写,sign写了
        class_detail_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new sign_list_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new sign_list_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });
        //课程资源还没写
        class_detail_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new stu_list_fragment()).commit();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new stu_list_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });
//        取数据后赋值
//        class_detail_txt_name.setText("a");
//        class_detail_txt_course.setText("b");
//        class_detail_txt_slot.setText("c");
//        class_detail_txt_code.setText("d");

        return root;
    }

}
