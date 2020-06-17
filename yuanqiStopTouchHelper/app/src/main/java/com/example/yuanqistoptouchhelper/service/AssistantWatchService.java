package com.example.yuanqistoptouchhelper.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.yuanqistoptouchhelper.ComponentUtil;
import com.example.yuanqistoptouchhelper.StaticConfig;


public class AssistantWatchService extends Service {

    private boolean isRunning = false;
    private Handler handler = new Handler();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            startService(new Intent(AssistantWatchService.this, AssistantGuardService.class));
//            bindService(new Intent(AssistantWatchService.this, AssistantGuardService.class), serviceConnection, Service.BIND_IMPORTANT);
        }
    };

    public AssistantWatchService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启动监听服务
//        startService(new Intent(AssistantWatchService.this, AssistantGuardService.class));
//        bindService(new Intent(AssistantWatchService.this, AssistantGuardService.class), serviceConnection, Service.BIND_IMPORTANT);

        //定时检测
        if (!isRunning) {
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        try {
                            try {

                                //监听终端助手是否运行中，若没有则启动终端助手应用
                                if (ComponentUtil.isInstallTerminalServiceApp(AssistantWatchService.this)) {
                                    if (!ComponentUtil.isServiceRunning(AssistantWatchService.this, StaticConfig.SERVER.WATCHING)) {
                                        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(StaticConfig.SERVER.PACKAGE_NAME);
                                        if (launchIntentForPackage != null) {
                                            launchIntentForPackage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(launchIntentForPackage);
                                        }
                                    }
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AssistantWatchService.this, "终端助手应用未安装!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Thread.sleep(3 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
