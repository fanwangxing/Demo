package com.example.administrator.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/8.
 */

public class Myservice extends Service {
    private LocalBinder localBinder = new LocalBinder();
    static final int CLIENT_TO_SERVICE = 1;
    static final int SERVICE_TO_CLIENT = 2;
    private int age = 28;
    Messenger messenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CLIENT_TO_SERVICE:
                    Log.e("test","myservice getdata:"+msg.arg1);
                    Messenger messenger = msg.replyTo;
                    Message message = Message.obtain();
                    message.what = SERVICE_TO_CLIENT;
                    Bundle bundle = new Bundle();
                    bundle.putString("data","你是个傻逼");
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    });
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String name = intent.getExtras().getString("name");
        Log.e("test","onBind:"+name);
//        return localBinder;
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder{
        Myservice getService(){
            return Myservice.this;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = intent.getExtras().getString("name");
        Log.e("test","onStartCommand:"+name);
        return super.onStartCommand(intent, flags, startId);
    }
    public int getAge(){

        return age;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
