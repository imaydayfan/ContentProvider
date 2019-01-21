package com.example.administrator.contentprovider;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener {

    private ContentResolver contentResolver;
    private ListView IvShowInfo;
    private MyAdapter adapter;
    private Button btnInit;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;
    private Cursor cursor;

    private static final String AUTHORITY = "com.example.studentProvider";
    private static final Uri STUDENT_ALL_URI = Uri.parse("content://" + AUTHORITY + "/student");
    protected static final String TAG = "MainActivty";

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            //可以针对数据改变后做一些操作。
            cursor = contentResolver.query(STUDENT_ALL_URI,null,null,null,null);

            adapter.changeCursor(cursor);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IvShowInfo = (ListView)findViewById(R.id.IvShowInfo);
        initData();
    }

    private void initData(){
        btnInit = (Button)findViewById(R.id.btnInit);
        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnQuery = (Button)findViewById(R.id.btnQuery);

        btnInit.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);

        contentResolver = getContentResolver();
        //注册内容观察者
        contentResolver.registerContentObserver(STUDENT_ALL_URI,true,new PersonObserver(handler));

        adapter = new MyAdapter(MainActivity.this,cursor);
        IvShowInfo.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            //初始化
            case R.id.btnInit:
                ArrayList<Student> students = new ArrayList<Student>();

                Student student1 = new Student("张三",20,"开朗");
                Student student2 = new Student("李四",21,"热情");
                Student student3 = new Student("王五",19,"大方");
                Student student4 = new Student("小明",18,"勤奋");
                Student student5 = new Student("小兰",20,"活泼");

                students.add(student1);
                students.add(student2);
                students.add(student3);
                students.add(student4);
                students.add(student5);

                for(Student Student:students){
                    ContentValues values = new ContentValues();
                    values.put("name",Student.getName());
                    values.put("age",Student.getAge());
                    values.put("introduce",Student.getIntroduce());
                    contentResolver.insert(STUDENT_ALL_URI,values);
                }
                break;

            //增
            case R.id.btnInsert:
                Student student = new Student("小红",22,"安静");

                //实例化一个ContentValues对象
                ContentValues insertContentValues = new ContentValues();
                insertContentValues.put("name",student.getName());
                insertContentValues.put("age",student.getAge());
                insertContentValues.put("introduce",student.getIntroduce());

                //这里的uri和ContentValues对象经过一系列处理之后会传到ContentProvider中的insert方法中
                //在自定义的ContentProvider中进行匹配操作
                contentResolver.insert(STUDENT_ALL_URI,insertContentValues);
                break;

            //删
            case R.id.btnDelete:
                //删除所有条目
                contentResolver.delete(STUDENT_ALL_URI,null,null);
                //删除id为1的记录
                Uri delUri = ContentUris.withAppendedId(STUDENT_ALL_URI,2);
                contentResolver.delete(delUri,null,null);
                break;

            //改
            case R.id.btnUpdate:
                ContentValues contentValues = new ContentValues();
                contentValues.put("introduce","外向");
                //更新数据，将_id=2的条目的introduce更新为“外向”
                //生成的Uri为：content://con.example.studentProvider/student/2
                Uri updateUri = ContentUris.withAppendedId(STUDENT_ALL_URI,2);
                contentResolver.update(updateUri,contentValues,null,null);
                break;

            //查
            case R.id.btnQuery:
                //通过ContentResolver获得一个调用ContentProvider对象
                Cursor cursor = contentResolver.query(STUDENT_ALL_URI,null,null,null,null);
                adapter = new MyAdapter(MainActivity.this,cursor);
                IvShowInfo.setAdapter(adapter);
                cursor = contentResolver.query(STUDENT_ALL_URI,null,null,null,null);
                adapter.changeCursor(cursor);
                break;
        }
    }
}
