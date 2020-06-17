package com.example.yuanqistoptouchhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.yuanqistoptouchhelper.service.AssistantWatchService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,  WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Window window = this.getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = 0;
        p.width = 0;
        p.gravity = Gravity.CENTER;
        window.setAttributes(p);
        Toast.makeText(this, "help启动", Toast.LENGTH_SHORT).show();
        startService(new Intent(this, AssistantWatchService.class));
    }
}