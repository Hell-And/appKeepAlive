package com.example.yuanqistoptouch;

import android.os.Environment;

public class StaticConfig {
    //终端服务应用
    public static class SERVER {
        public static final String PACKAGE_NAME = "com.example.yuanqistoptouchhelper";
        public static final String WATCHING = "com.example.yuanqistoptouchhelper.service.AssistantWatchService";
        public static final String ACTIVITY_LAUNCHER = "com.example.yuanqistoptouchhelper.MainActivity";

        public static final String URI = "com.example.yuanqistoptouchhelper.app.launcher";
    }

}
