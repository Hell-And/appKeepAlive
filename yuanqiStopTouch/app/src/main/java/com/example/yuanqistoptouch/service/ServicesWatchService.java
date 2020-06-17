package com.example.yuanqistoptouch.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.example.yuanqistoptouch.ComponentUtil;
import com.example.yuanqistoptouch.StaticConfig;


public class ServicesWatchService extends Service {

    private boolean isRunning = false;
    private Handler handler = new Handler();


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            startService(new Intent(ServicesWatchService.this, ServicesGuardService.class));
//            bindService(new Intent(ServicesWatchService.this, ServicesGuardService.class), serviceConnection, Service.BIND_IMPORTANT);
        }
    };


    public ServicesWatchService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //启动监听服务
//        startService(new Intent(ServicesWatchService.this, ServicesGuardService.class));
//        bindService(new Intent(ServicesWatchService.this, ServicesGuardService.class), serviceConnection, Service.BIND_IMPORTANT);

        //定时检测
        if (!isRunning) {
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        try {
                            try {
                                //Netty长连接保持

                                //监听终端服务是否运行中，若没有则启动终端应用
                                if (ComponentUtil.isInstallTerminalServiceApp(ServicesWatchService.this)) {
                                    if (!ComponentUtil.isServiceRunning(ServicesWatchService.this, StaticConfig.SERVER.WATCHING)) {
                                        Intent startAppIntent = new Intent();
                                        startAppIntent.setComponent(new ComponentName(StaticConfig.SERVER.PACKAGE_NAME, StaticConfig.SERVER.ACTIVITY_LAUNCHER));
                                        startAppIntent.setData(Uri.parse(StaticConfig.SERVER.URI));
                                        startAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(startAppIntent);
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ServicesWatchService.this, "启动helper!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ServicesWatchService.this, "终端服务应用未安装!", Toast.LENGTH_SHORT).show();
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
