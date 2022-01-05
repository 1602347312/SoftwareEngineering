package com.example.myapplication1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class src_list_fragment extends Fragment implements AdapterView.OnItemClickListener{
    Button src_btn_back;
    ListView src_lv;
    Data globaldata;
    SimpleAdapter simpleAdapter;

    List<String> materialId = new ArrayList<>();
    List<String> materialTitle = new ArrayList<>();
    List<String> materialUploadTime = new ArrayList<>();
    List<String> materialSize = new ArrayList<>();
    List<String> materialUploader = new ArrayList<>();
    List<String> materialUrl = new ArrayList<>();
    List<String> materialClassId = new ArrayList<>();



    int l;

    @SuppressLint("NewApi")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_src_list, container, false);


        src_btn_back=root.findViewById(R.id.src_btn_back);
        src_lv=root.findViewById(R.id.src_lv);
        globaldata = (Data) this.getActivity().getApplication();
        src_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(globaldata.getIsStu().equals("学生"))
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_stu_fragment()).commit();
                            else if(globaldata.getIsStu().equals("老师"))
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_tea_fragment()).commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(globaldata.getIsStu().equals("学生"))
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_stu_fragment()).commit();
                                    else if(globaldata.getIsStu().equals("老师"))
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new personal_tea_fragment()).commit();
                                }
                            });
                        }

                    }
                }).start();
            }
        });

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://121.37.172.109:9000/back_end/material/getMaterialList?class_code=" + globaldata.getClass_code())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            Log.d("msg", jsonObject.getString("msg"));
            Log.d("code", jsonObject.getString("code"));
            String code = jsonObject.getString("code");


            if (code.equals("0")) {
                JSONArray data = new JSONArray(jsonObject.getString("data"));
                SharedPreferences spf = getActivity().getSharedPreferences("spf", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("class_code", "2");
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String t;
                        try {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject temp = data.getJSONObject(i);

                                t = temp.optString("materialId", null);
                                materialId.add(t);

                                t = temp.optString("materialTitle", null);
                                materialTitle.add(t);

                                t = temp.optString("materialUploadTime", null);
                                materialUploadTime.add(t);

                                t = temp.optString("materialSize", null);
                                materialSize.add(t);

                                t = temp.optString("materialUploader", null);
                                materialUploader.add(t);

                                t = temp.optString("materialUrl", null);
                                materialUrl.add(t);

                                t = temp.optString("materialClassId", null);
                                materialClassId.add(t);
                            }
                            l = data.length();
                            Log.d("tag2", "" + l);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toastCenter = Toast.makeText(getActivity(), "当前无课程资源", Toast.LENGTH_LONG);
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
                    Toast toastCenter = Toast.makeText(getActivity(), "请求失败，登陆失败", Toast.LENGTH_LONG);
                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                    toastCenter.show();
                }
            });
        }




        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.src_item, new String[]{"materialTitle", "materialUploadTime", "materialUploader", "materialSize"}, new int[]{R.id.src_t1, R.id.src_t2, R.id.src_t3,R.id.src_t4});
        src_lv.setAdapter(simpleAdapter);

        src_lv.setOnItemClickListener(this);
        return root;
    }
    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Log.d("msg11",""+l);

            Map map = new HashMap();
            map.put("materialId", materialId.get(i));
            map.put("materialTitle", materialTitle.get(i));
            map.put("materialUploadTime", materialUploadTime.get(i));
            map.put("materialSize", materialSize.get(i));
            map.put("materialUploader", materialUploader.get(i));
            map.put("materialUrl", materialUrl.get(i));
            map.put("materialClassId", materialClassId.get(i));


            list.add(map);
        }
        src_lv.setAdapter(simpleAdapter);

        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = src_lv.getAdapter().getItem(position).toString();
        Log.e("msg", "position:" + position + "text" + text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url="http://121.37.172.109:9000/back_end/material/download?material_id="+materialId.get(position);
                    Log.d("download:",url);
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .method("GET", null)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    String code = jsonObject.getString("code");
                    String file_url = jsonObject.getString("data");
                    Log.d("file_url:",file_url);
                    if(code.equals("0")){
                        //申请写入权限
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                            Log.d("perm","1");
                        }
                        Log.d("dowmload", download(getContext(),file_url));
//                        DownloadManager.Request download_request = new DownloadManager.Request(Uri.parse(file_url));
//                        download_request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "3");
////                        download_request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, GetFileName(file_url));
//                        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//                        downloadManager.enqueue(download_request);
//                        //下载操作
//                        MediaScannerConnection.scanFile(getContext(), new String[] { Environment.DIRECTORY_DOWNLOADS }, null, null);
////                        //扫描文件夹


                        getActivity().runOnUiThread( new  Runnable() {
                            @Override
                            public  void  run() {
                                Toast toastCenter = Toast.makeText(getActivity(), "下载文件成功", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });


                    }
                    else {
                        getActivity().runOnUiThread( new  Runnable() {
                            @Override
                            public  void  run() {
                                Toast toastCenter = Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread( new  Runnable() {
                        @Override
                        public  void  run() {
                            Toast toastCenter = Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_LONG);
                            //确定Toast显示位置，并显示
                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
                            toastCenter.show();
                        }
                    });
                }

            }
        }).start();



    }
    public String GetFileName(String URL){
        int start = URL.lastIndexOf("/");
        int end = URL.length();
        if (start != -1 && end != -1) {
            return URL.substring(start+1,end);
        } else {
            return null;
        }
    }
    @SuppressLint("NewApi")
    public static String download(Context context,String docUrl)throws Exception, MalformedURLException {                           /***加载正文***/
        //获取存储卡路径、构成保存文件的目标路径
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String dirName = "";
        //SD卡具有读写权限、指定附件存储路径为SD卡上指定的文件夹
        dirName = Environment.getExternalStorageDirectory()+"/Signature/";
        File f = new File(dirName);
        if(!f.exists()){      //判断文件夹是否存在
            f.mkdir();        //如果不存在、则创建一个新的文件夹
        }
        //准备拼接新的文件名
        String[] list = docUrl.split("/");
        String fileName = list[list.length-1];
        fileName = dirName + fileName;
        File file = new File(fileName);
        if(file.exists()){    //如果目标文件已经存在
            file.delete();    //则删除旧文件
        }
        //1M的数据缓冲
        byte[] bs = new byte[1024*1024];
        //读取到的数据长度
        int len;
        try{

            //通过文件地址构建url对象
            URL url = new URL(docUrl);
            Log.d("url", String.valueOf(url));
            //获取链接
            URLConnection conn = url.openConnection();
            Log.d("conn", String.valueOf(conn));
            //创建输入流
            InputStream is = url.openStream();
            Log.d("is", String.valueOf(is));
            //获取文件的长度
            int contextLength = conn.getContentLength();
            Log.d("length", String.valueOf(contextLength));
            //输出的文件流
            OutputStream os = new FileOutputStream(file);
            Log.d("os", String.valueOf(os));
            //开始读取
            while((len = is.read(bs)) != -1){
                os.write(bs,0,len);
            }
            //完毕关闭所有连接
            os.close();
            is.close();
        } catch(FileNotFoundException e){
            fileName = null;
            System.out.println("无法加载文件");
            throw e;
        }catch(IOException e){
            fileName = null;
            System.out.println("获取连接失败");
            throw e;
        }
        return fileName;
    }
}
