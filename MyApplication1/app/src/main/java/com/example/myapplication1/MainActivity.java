package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        username=getIntent().getStringExtra("username");
        Log.d("msg",username);

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
                    case R.id.course:
                        //写好的fragment，用这个来看
//                        fragment=new course_stu_fragment();
//                        break;
                        fragment=new sign_list_fragment();
                        break;

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
}