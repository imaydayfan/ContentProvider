package com.example.administrator.contentprovider;

import android.database.ContentObserver;
import android.os.Message;
import android.os.Handler;

public class PersonObserver extends ContentObserver {
    private Handler handler;

    public PersonObserver(Handler handler){
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange){
        super.onChange(selfChange);

        //向handler发送消息，更新查询记录
        Message msg = new Message();
        handler.sendMessage(msg);
    }
}
