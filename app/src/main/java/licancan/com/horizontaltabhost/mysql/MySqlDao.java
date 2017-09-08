package licancan.com.horizontaltabhost.mysql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by robot on 2017/9/7.
 */

public class MySqlDao {
    Context context;
    private final MySqliteOpenHelper helper;

    public MySqlDao(Context context) {
        helper = new MySqliteOpenHelper(context);
    }

    /**
     * 添加数据
     */
    public void insert(String type,String json)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",type);
        values.put("json",json);
        db.insert("pin",null,values);
    }

    /**
     * 删除的方法
     */
    public void delete(String type)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("pin","type=?",new String[]{type});
        String select = select(type);
        if(select==null)
        {
            System.out.println("-======查询数据为空");
        }else{
            System.out.println("=========查询未成功");
        }
    }

    /**
     * 查询的方法
     */
    public String select(String type)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("pin", null, " type=?", new String[]{type}, null, null, null);
        while (cursor.moveToNext())
        {
            String json = cursor.getString(cursor.getColumnIndex("json"));
            return json;
        }
        return null;
    }

}
