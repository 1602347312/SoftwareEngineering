package com.example.myapplication1;

import static java.util.logging.Logger.getLogger;
import static java.util.logging.Logger.global;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String username;
    Data globaldata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globaldata=(Data) getApplication();
        bottomNavigationView = findViewById(R.id.bottomNav);
        username=getIntent().getStringExtra("username");
        Log.d("msg",username);
        Data data =(Data)this.getApplication();

        //创建默认fragment
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new home_stu_fragment()).commit();
        }

        //设置fragment的切换监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                //根据选择不同显示不同界面
                switch(item.getItemId()){
                    case R.id.home:
                        fragment=new home_stu_fragment();
                        break;
                    case R.id.personal:
                        fragment=new personal_stu_fragment();
                        break;
                    case R.id.course: {

                        if (!globaldata.getClass_code().equals("-1"))
                        {

                            fragment = new course_stu_fragment();
                            break;
                        }
                        else {
                            fragment = new home_stu_fragment();
                            Toast toastCenter = null;
                            toastCenter = Toast.makeText(getApplicationContext(), "请选择班级", Toast.LENGTH_LONG);
                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
                            toastCenter.show();
                            break;
                        }
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });
    }
    //文件选择器部分看不懂代码
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    @Nullable
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mas","我是mainac");
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return;
        }
        Log.d("mas","我是mainac9");
        Uri uri = data.getData(); // 获取用户选择文件的URI
        Log.d("mas","我是mainac8");
        String path = uriToFileApiQ(this, uri);
        Log.d("mas","我是mainac7");
        File file = new File(path);
        Log.d("mas","我是mainac6");
        uploadFile(file);
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)      //司马选择器 搞你妈一中午
    public static String uriToFileApiQ(Context context, Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    // 使用   OkHttp上传图片文件
    public void uploadFile(File file) {
        Log.d("mas", String.valueOf(file));
        Log.d("mas","我是mainac2");
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"),file);
        requestBody.addFormDataPart("file",file.getName(),fileBody);
        Request request = new Request.Builder()
                .url("http://121.37.172.109:9000/back_end/user/uploadAvatar")
                .post(requestBody.build())
                .build();
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("mas","222");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        Log.d("mas",jsonObject.getString("code"));
                        Log.d("massssssssssss",jsonObject.getString("data"));
                        update_icon(jsonObject.getString("data"));
                        Log.d("masdddddddddddd",jsonObject.getString("data"));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    Log.d("mas","G");
                }
            }
        });
    }
    public void update_icon(String URL){     //调接口修改头像
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String encoderUrl = URLEncoder.encode(URL, "UTF-8");
                    String url="http://121.37.172.109:9000/back_end/user/modifyUserInfo?avatar="+encoderUrl+"&old_password="+globaldata.getPassword()+"&user_id="+globaldata.getUsername();

                    Log.d("mas",url);

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
                    if(code.equals("0")){
                        runOnUiThread( new  Runnable() {
                            @Override
                            public  void  run() {
                                Toast toastCenter = Toast.makeText(getApplicationContext(), "头像修改成功", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });

                    }
                    else {
                        runOnUiThread( new  Runnable() {
                            @Override
                            public  void  run() {
                                Toast toastCenter = Toast.makeText(getApplicationContext(), "连接成功，修改失败", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread( new  Runnable() {
                        @Override
                        public  void  run() {
                            Toast toastCenter = Toast.makeText(getApplicationContext(), "连接失败，修改失败", Toast.LENGTH_LONG);
                            //确定Toast显示位置，并显示
                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
                            toastCenter.show();
                        }
                    });
                }

            }
        }).start();
    }
}