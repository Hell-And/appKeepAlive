package com.example.yuanqistoptouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.yuanqistoptouch.service.ServicesWatchService;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

//        Intent intent = new Intent("com.example.yuanqistoptouch.MyReceiver");
//        ComponentName componentName  = new ComponentName(this,"com.example.yuanqistoptouch.MyReceiver");
//        intent.setComponent(componentName);
//        sendBroadcast(intent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,  WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Window window = this.getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = 0;
        p.width = 0;
        p.gravity = Gravity.CENTER;
        window.setAttributes(p);

        Toast.makeText(this, "主程序启动", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(MainActivity.this, ServicesWatchService.class));
    }

    public void verifyStoragePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            } else {
//                initView();
            }
        } else {
//            initView();
        }
    }

    private void initView() {
        findViewById(R.id.bt_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //写文件
                String result = SdCardFileUtil.save("0");
                if ("成功".equals(result)) {
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                }
//                Log.d(TAG, "onClick: " + SdCardFileUtil.save("1"));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
//            initView();
//            for (int i = 0; i < permissions.length; i++) {
//            }
        }
    }
}