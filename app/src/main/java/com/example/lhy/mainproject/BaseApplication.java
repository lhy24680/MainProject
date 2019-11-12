package com.example.lhy.mainproject;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.didi.virtualapk.PluginManager;

import java.io.File;

/**
 * description :
 * author : lihaiyang
 * date : 2019/10/8 15:38
 */

public class BaseApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager pluginManager = PluginManager.getInstance(this);
        //此处是当查看插件apk是否存在,如果存在就去加载(比如修改线上的bug,把插件apk下载到sdcard的根目录下取名为Demo.apk)
        File apk = new File(Environment.getExternalStorageDirectory(), "plugin1.apk");
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
