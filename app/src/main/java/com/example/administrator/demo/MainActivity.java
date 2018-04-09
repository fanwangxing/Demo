package com.example.administrator.demo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Intent intent;
    private Button start;
    private Button stop;
    private Button bind;
    private Button unbind;
    private Button getdata;
    private Button senddata;
    private Button sendboard;
    private TextView show;
    private ServiceConnection serviceConnection;
    private Myservice myservice;
    private Messenger messenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setLisener();
        initData();


    }



    private void initView() {
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        bind = (Button)findViewById(R.id.bind);
        unbind = (Button)findViewById(R.id.unbind);
        getdata = (Button)findViewById(R.id.getdata);
        senddata = (Button)findViewById(R.id.senddata);
        sendboard = (Button)findViewById(R.id.sendboard);
        show = (TextView)findViewById(R.id.show);
    }

    private void setLisener() {

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        getdata.setOnClickListener(this);
        senddata.setOnClickListener(this);
        sendboard.setOnClickListener(this);
    }
    private void initData() {
        createServiceConnect();
    }
    private void createServiceConnect() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

//                Myservice.LocalBinder localBinder = (Myservice.LocalBinder)iBinder;
//                myservice = localBinder.getService();
                messenger = new Messenger(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
    }



    private void bindService() {
        intent = new Intent();
        intent.setClass(this,Myservice.class);
        intent.putExtra("name","fan");
        bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
        Log.e("test","启动绑定服务");
    }

    private void startService() {
         intent = new Intent();
        intent.setClass(this,Myservice.class);
        intent.putExtra("name","fan");
        startService(intent);
        Log.e("test","启动服务");
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.start:
                startService();
                break;
            case R.id.stop:
                stopService(intent);
                break;
            case R.id.bind:
                bindService();
                break;
            case R.id.unbind:
                unbindService(serviceConnection);
                break;
            case R.id.getdata:
                show.setText(String.valueOf(myservice.getAge()));
                break;
            case R.id.senddata:
                Message msg = Message.obtain();
                msg.what = Myservice.CLIENT_TO_SERVICE;
                msg.replyTo = clientMessenger;
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sendboard:
                Intent boardIntent = new Intent();
                boardIntent.setAction("TESTBOARD");
                boardIntent.putExtra("board","广播发送的数据");
                sendBroadcast(boardIntent);

                break;
        }
    }
    Messenger clientMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Myservice.SERVICE_TO_CLIENT:
                    Log.i("test", "receiver message from service:"+msg.getData().getString("data"));
                    break;
            }
        }
    });

}
