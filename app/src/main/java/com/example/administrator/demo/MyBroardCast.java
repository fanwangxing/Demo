package com.example.administrator.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/9.
 */

public class MyBroardCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("test","get broard:"+intent.getExtras().getString("board"));
    }
}
