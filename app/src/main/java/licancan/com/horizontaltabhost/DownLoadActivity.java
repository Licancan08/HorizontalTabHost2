package licancan.com.horizontaltabhost;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import licancan.com.horizontaltabhost.adapter.RecyclerViewAdapter;
import licancan.com.horizontaltabhost.api.API;
import licancan.com.horizontaltabhost.bean.Catogray;
import licancan.com.horizontaltabhost.mysql.MySqliteOpenHelper;

public class DownLoadActivity extends AppCompatActivity {

    private RecyclerView rlv_list;
    private Button download;
    private List<Catogray> list;
    private Catogray c;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<Catogray>();
        c = new Catogray();
        c.type="top";
        c.name="头条";
        list.add(c);

        c =new Catogray();
        c.type="xinwen";
        c.name="新闻";
        list.add(c);

        c =new Catogray();
        c.type="xinwen";
        c.name="娱乐";
        list.add(c);

        c =new Catogray();
        c.type="xinwen";
        c.name="财经";
        list.add(c);

        c =new Catogray();
        c.type="xinwen";
        c.name="养生";
        list.add(c);

        c =new Catogray();
        c.type="xinwen";
        c.name="政治";
        list.add(c);

        Toast.makeText(this, "数据", Toast.LENGTH_SHORT).show();
        //设置适配器
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,list);
        rlv_list.setLayoutManager(new LinearLayoutManager(this));
        rlv_list.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int pos, View view) {
                checkBox = view.findViewById(R.id.cb);
                Catogray c=list.get(pos);
                if(checkBox.isChecked())
                {
                    checkBox.setChecked(false);
                    c.state=false;
                }
                else{
                    checkBox.setChecked(true);
                    c.state=true;
                }

                //修改原有的list数据
                list.set(pos,c);
            }
        });


    }

    /**
     * 初始化数据
     */
    private void initView() {
        rlv_list = (RecyclerView) findViewById(R.id.rlv_list);
        download = (Button) findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(list != null && list.size()>0)
                {
                    for (Catogray catogray : list) {
                        if(catogray.state)
                        {//判断是否是选中状态  如果选中则执行下载操作
                            loadData(catogray.type);
                        }
                    }
                    //打印下载状态
                    for (Catogray c1:list) {
                        System.out.println("state====="+c1.state);
                    }
                }
                
            }
        });
    }

    /**
     * 只要有wifi  就下载离线数据  下载后完成后保存到数据库
     * @param type
     */
    private void loadData(final String type) {
        RequestParams params=new RequestParams(API.URL_POST);
        params.addParameter("key",API.KEY);
        params.addParameter("type",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //下载成功后保存到数据库
                saveData(type,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 保存数据库的方法
     * @param type
     * @param result
     */
    private void saveData(String type, String result) {
        MySqliteOpenHelper helper=new MySqliteOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",type);
        values.put("content",result);
        db.insert("news",null,values);
    }


}
