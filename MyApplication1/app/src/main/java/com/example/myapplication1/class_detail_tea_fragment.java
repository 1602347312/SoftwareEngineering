package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class class_detail_tea_fragment extends Fragment {

    Data globaldata;
    Button class_detail_btn_back, class_detail_btn_stu_list, class_detail_rand, class_detail_sign, class_detail_source;
    TextView class_detail_txt_name, class_detail_txt_course, class_detail_txt_slot, class_detail_txt_code,class_detail_rand_result;
    ImageView class_detail_icon;
    Button ass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_class_detail_tea, container, false);
        class_detail_btn_back = root.findViewById(R.id.class_detail_btn_back);
        class_detail_btn_stu_list = root.findViewById(R.id.class_detail_btn_stu_list);
        class_detail_rand = root.findViewById(R.id.class_detail_rand);
        class_detail_sign = root.findViewById(R.id.class_detail_sign);
        class_detail_source = root.findViewById(R.id.class_detail_source);
        class_detail_icon = root.findViewById(R.id.class_detail_icon);

        class_detail_txt_name = root.findViewById(R.id.class_detail_txt_name);
        class_detail_txt_course = root.findViewById(R.id.class_detail_txt_course);
        class_detail_txt_slot = root.findViewById(R.id.class_detail_txt_slot);
        class_detail_txt_code = root.findViewById(R.id.class_detail_txt_code);
        getClassDetail();//为上面4个textview赋值
        globaldata= (Data) this.getActivity().getApplication();

        ass=root.findViewById(R.id.tea_ass_list);

        ass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new asslist_tea_fragment()).commit();


                        } catch (Exception e) {
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

        class_detail_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new home_tea_fragment()).commit();


                        } catch (Exception e) {
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

        class_detail_btn_stu_list.setOnClickListener(new View.OnClickListener() {  //学生列表要加监听事件
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new stu_list_fragment_tea()).commit();

                        } catch (Exception e) {
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
        //作业界面还没写
        class_detail_rand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    String _class_code=globaldata.getClass_code();
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://121.37.172.109:9000/back_end/class/randomRoll?class_code="+_class_code)
                                    .get()
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            String code = jsonObject.getString("code");
                            String _rand_result=jsonObject.getString("data");
                            if (code.equals("0")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "摇人成功:"+_rand_result, Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "摇人失败", Toast.LENGTH_LONG);
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();

                                    }
                                });
                            }

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toastCenter = Toast.makeText(getActivity(), "摇人失败1", Toast.LENGTH_LONG);
                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                    toastCenter.show();

                                }
                            });
                        }

                    }
                }).start();
            }
        });

        class_detail_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new launch_sign_fragment()).commit();

                        } catch (Exception e) {
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

        class_detail_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //跳转到上传文件界面
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new upload_material_fragment()).commit();

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

    public void getClassDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {                      //android studio版本问题    已解决
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://121.37.172.109:9000/back_end/class/getClassDetail?class_code=" + globaldata.getClass_code())
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    Log.d("msg", jsonObject.getString("msg"));
                    Log.d("code", jsonObject.getString("code"));
                    String code = jsonObject.getString("code");
//                            String code=jsonObject.getString("code");
                    JSONObject data = new JSONObject(jsonObject.getString("data"));

                    if (code.equals("0")) {

                        SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putString("class_code", "2");
                        editor.apply();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {


                                    class_detail_txt_code.setText(data.getString("classCode"));
                                    class_detail_txt_name.setText(data.getString("classTeacherId"));

                                    class_detail_txt_course .setText(data.getString("className"));
                                    class_detail_txt_slot.setText(data.getString("classSlot"));
                                    globaldata.setClass_name(data.getString("className"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toastCenter = Toast.makeText(getActivity(), "请求sdsd成功，登陆失败", Toast.LENGTH_LONG);
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
                            Toast toastCenter = Toast.makeText(getActivity(), "请求wwww失败，登陆失败", Toast.LENGTH_LONG);
                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
                            toastCenter.show();
                        }
                    });
                }

            }
        }).start();

    }
}
