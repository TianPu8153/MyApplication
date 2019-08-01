package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String name="schedule";
    static int dbVersion=1;
    private Context mContext;
    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql=" create table event(id integer primary key autoincrement,name varchar(20),date text,state integer,level integer) ";
        db.execSQL(sql);
        Toast.makeText(mContext, "创建数据库成功！", Toast.LENGTH_SHORT).show();
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}