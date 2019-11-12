package com.example.lhy.mainproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.example.lhy.commonmodule.Bean;
import com.example.lhy.commonmodule.BeanManager;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button toPlugin1Btn;
    private TextView showText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toPlugin1Btn = findViewById(R.id.main_btn1);
        showText = findViewById(R.id.main_showText);
        toPlugin1Btn.setOnClickListener(this);
        // 权限检查
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return;
        }else {
            File apk = new File(Environment.getExternalStorageDirectory(), "plugin1.apk");
            PluginManager pluginManager = PluginManager.getInstance(this);
            if (apk.exists()) {
                try {
                    pluginManager.loadPlugin(apk);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("value")) {
            String value = intent.getStringExtra("value");
            showText.setText(value);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn1:
                if (PluginManager.getInstance(MainActivity.this).getLoadedPlugin("com.example.lhy.pluginproject1") == null) {
                    Toast.makeText(MainActivity.this, "插件未加载", Toast.LENGTH_SHORT).show();
                } else {
                    Bean bean = new Bean();
                    bean.setName("宿主通过中间aar传给插件的值");
                    BeanManager.init(bean);
                    Intent intent = new Intent();
                    intent.setClassName("com.example.lhy.pluginproject1", "com.example.lhy.pluginproject1.Plugin1MainActivity");
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length >0 && grantResults[0]  == PackageManager.PERMISSION_GRANTED) {
                    File apk = new File(Environment.getExternalStorageDirectory(), "plugin1.apk");
                    PluginManager pluginManager = PluginManager.getInstance(this);
                    if (apk.exists()) {
                        try {
                            pluginManager.loadPlugin(apk);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "插件不存在", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
