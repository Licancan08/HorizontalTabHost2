package licancan.com.horizontaltabhost.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by robot on 2017/9/5.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper{
    public MySqliteOpenHelper(Context context) {
        super(context, "download", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //离线下载的表
        /*String sql="create table news(type varchar,content text)";
        db.execSQL(sql);*/
        db.execSQL("create table news(type varchar,content text)");

        //频道管理的表
        /*String sql1="create table pin(type varchar,json text)";
        db.execSQL(sql1);*/
        db.execSQL("create table pin(type varchar,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
