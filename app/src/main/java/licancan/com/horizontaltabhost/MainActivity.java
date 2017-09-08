package licancan.com.horizontaltabhost;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.kson.slidingmenu.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import licancan.com.horizontaltabhost.bean.News;
import licancan.com.horizontaltabhost.fragment.Fragment1;
import licancan.com.horizontaltabhost.fragment.Fragment3;
import licancan.com.horizontaltabhost.fragment.Fragment4;
import licancan.com.horizontaltabhost.fragment.Fragment5;
import licancan.com.horizontaltabhost.fragment.Fragment6;
import licancan.com.horizontaltabhost.fragment.Fragment7;
import licancan.com.horizontaltabhost.fragment.LeftMenuFragment;
import licancan.com.horizontaltabhost.fragment.Fragment2;
import licancan.com.horizontaltabhost.fragment.RightMenuFragment;
import licancan.com.horizontaltabhost.mysql.MySqlDao;
import licancan.com.horizontaltabhost.view.HorizontalScollTabHost;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.left)
    ImageView left;
    @ViewInject(R.id.right)
    ImageView right;
    private HorizontalScollTabHost myTabHost;
    private List<ChannelBean> chanlist;
    private List<Fragment> fragmentList;
    private List<News> beans;
    private SlidingMenu menu;
    private ImageView ivjia;
    private List<ChannelBean> cblist;
    private MySqlDao dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        myTabHost = (HorizontalScollTabHost) findViewById(R.id.tabhost);

        initData();
        initMenu();
        initView();
    }

    /**
     * 左右滑动
     */
    private void initView() {
        ivjia= (ImageView) findViewById(R.id.jia);
        ivjia.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);


        dao = new MySqlDao(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragmentList = new ArrayList<>();
        beans = new ArrayList<>();

        News news = new News();
        news.id = "top";
        news.name = "娱乐";
        news.state=true;
        beans.add(news);

        news = new News();
        news.id = "temai";
        news.name = "特卖";
        news.state=true;
        beans.add(news);

        news = new News();
        news.id = "yule";
        news.name = "头条";
        news.state=true;
        beans.add(news);

        news = new News();
        news.id = "caijing";
        news.name = "财经";
        news.state=true;
        beans.add(news);

        news = new News();
        news.id = "shishang";
        news.name = "时尚";
        news.state=true;
        beans.add(news);


        news = new News();
        news.id = "junshi";
        news.name = "军事";
        news.state=true;
        beans.add(news);

        news = new News();
        news.id = "keji";
        news.name = "科技";
        news.state=true;
        beans.add(news);

        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());
        fragmentList.add(new Fragment5());
        fragmentList.add(new Fragment6());
        fragmentList.add(new Fragment7());

        myTabHost.diaplay(beans, fragmentList);
    }

    /**
     * 添加侧拉菜单
     */
    private void initMenu() {

        menu = new SlidingMenu(this);
        //添加左菜单
        menu.setMenu(R.layout.left_content_id);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl, new LeftMenuFragment()).commit();
        //设置属性
        menu.setMode(SlidingMenu.LEFT_RIGHT);//左右侧拉
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//点击边缘侧拉
        menu.setBehindOffsetRes(R.dimen.BehindoffsetRes);

        //设置右菜单
        menu.setSecondaryMenu(R.layout.right_content_id);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_fl, new RightMenuFragment()).commit();

        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                menu.showMenu();
                break;
            case R.id.right:
                menu.showSecondaryMenu();
                break;
            case R.id.jia:
                /*chanlist=new ArrayList<>();
                ChannelBean chanbean=null;
                *//**
                 * 添加横滑菜单的信息
                 *//*
                for (int i = 0; i <beans.size() ; i++) {
                    chanbean = new ChannelBean(beans.get(i).name,true);
                    chanlist.add(chanbean);
                }
                *//**
                 * 添加新的信息
                 *//*
                for (int i = 0; i <pindao.length ; i++) {
                    chanbean = new ChannelBean(pindao[i],false);
                    chanlist.add(chanbean);
                }
                ChannelActivity.startChannelActivity(MainActivity.this,chanlist);*/
                 pindaoUtils();
                break;
        }
    }

    /**
     * 频道管理的方法
     */
    private void pindaoUtils() {
        cblist = new ArrayList<>();
        String result = dao.select("pindao");
        System.out.println("=======数据库中频道"+result);
        if(result==null)
        {//第一次进入频道时
            for (int i = 0; i <beans.size() ; i++) {
                News bean=beans.get(i);
                System.out.println("===beans+cb"+bean.state);
                ChannelBean cb=new ChannelBean(bean.name,bean.state);
                cblist.add(cb);
            }
        }else{
            //第二次进入时
            try {
                JSONArray arr=new JSONArray(result);
                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject obj= (JSONObject) arr.get(i);
                    String name=obj.getString("name");
                    boolean state=obj.getBoolean("isSelect");
                    ChannelBean bean1=new ChannelBean(name,state);
                    cblist.add(bean1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //设置频道管理
        ChannelActivity.startChannelActivity(MainActivity.this,cblist);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==101)
        {
            String result_json_key=data.getStringExtra("json");
            dao.delete("pindao");//清除数据库
            dao.insert("pindao",result_json_key);//添加到数据库
            //清空之前的集合对象
            beans.clear();

            ArrayList<Fragment> flist=new ArrayList<>();
            try {
                JSONArray arr=new JSONArray(result_json_key);
                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject obj= (JSONObject) arr.get(i);
                    String name=obj.getString("name");
                    boolean state=obj.getBoolean("isSelect");
                    if(state)
                    {
                        flist.add(fragmentList.get(i));
                        News news=new News(name,state);
                        beans.add(news);
                    }
                    System.out.println("=======tabhost头部"+name+state);

                }

                System.out.println("========"+flist.size());
                //清空横滑内容
                myTabHost.clear();
                //重新添加内容
                myTabHost.diaplay(beans,flist);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}