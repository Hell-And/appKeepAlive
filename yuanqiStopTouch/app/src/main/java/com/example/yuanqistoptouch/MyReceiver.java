package com.example.yuanqistoptouch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    static  final   String  ACTION  = "android.intent.action.BOOT_COMPLETED";   //AndroidManifest.xml  中注册的action
    @Override
    public void onReceive(Context context, Intent intent) {


        //捕获到
        if (intent.getAction().equals(ACTION)){
            Intent mainActivityIntent  = new Intent(context,MainActivity.class);
            //要启用的activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置此标志使activity将成为此历史堆栈上新任务的开始
            context.startActivity(mainActivityIntent);
        }
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent i = new Intent(context, MainActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        }
    }
}
