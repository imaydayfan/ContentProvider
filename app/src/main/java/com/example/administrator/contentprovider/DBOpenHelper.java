package com.example.administrator.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String DB_NAME = "person.db";
    //数据库版本
    private static final int version = 1;

    public DBOpenHelper(Context context){
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table personData(" +
                "_id integer primary key," +
                "name varchar(20) ," +
                "age Integer," +
                "introduce varchar(100))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("");
    }
}
