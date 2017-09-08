package licancan.com.horizontaltabhost.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import licancan.com.horizontaltabhost.R;
import licancan.com.horizontaltabhost.bean.News;

/**
 * Created by robot on 2017/8/31.
 */

public class HorizontalScollTabHost extends LinearLayout implements ViewPager.OnPageChangeListener {

    Context context;
    private HorizontalScrollView horizonView;
    private ViewPager viewPager;
    private LinearLayout layout_menu;
    private int color;
    private List<News> list;
    private List<Fragment> fragmentList;
    private int count;
    private List<TextView> topViews;
    private MyAdapter myAdapter;

    public HorizontalScollTabHost(Context context) {
        this(context,null);
    }

    public HorizontalScollTabHost(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public HorizontalScollTabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(context,attrs);
    }

    /**
     * 初始化自定义属性和view
     */
    private void init(Context context,AttributeSet attrs)
    {
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.HorizontalScollTabHost);
        color = typedArray.getColor(R.styleable.HorizontalScollTabHost_top_background,0xffffff);
        typedArray.recycle();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        View view= LayoutInflater.from(context).inflate(R.layout.horizontal_sroll_tabhost,this,true);
        horizonView = view.findViewById(R.id.horizontalScrollView);
        viewPager = view.findViewById(R.id.viewPager);

        viewPager.addOnPageChangeListener(this);
        //菜单LinearLayout控件
        layout_menu = view.findViewById(R.id.layout_menu);
    }

    /**
     * 供调用者调用  保证数据独立
     * @param list
     * @param fragments
     */
    public void diaplay(List<News> list, List<Fragment> fragments)
    {
        this.list=list;
        this.count=list.size();
        this.fragmentList=fragments;
        topViews = new ArrayList<>(count);
        drawUi();

    }

    /**
     * 绘制所有元素
     */
    private void drawUi()
    {
        drawHorizontal();
        drawViewPager();
    }

    /**
     * 绘制ViewPager
     */
    private void drawViewPager() {
        myAdapter = new MyAdapter(((FragmentActivity)context).getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
    }

    /**
     * 绘制横向滑动菜单
     */
    private void drawHorizontal() {
        layout_menu.setBackgroundColor(color);
        for (int i = 0; i <count ; i++) {
            News bean=(News) list.get(i);
            final TextView tv= (TextView) View.inflate(context,R.layout.news_top_tv_item,null);
            tv.setText(bean.name);

            final int finalI=i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击移动到当前fragment
                    viewPager.setCurrentItem(finalI);
                    //点击让文字居中
                    moveItemToCenter(tv);
                }
            });

            layout_menu.addView(tv);

            topViews.add(tv);
        }
        //默认设置第一项为选中
        topViews.get(0).setSelected(true);
    }

    /**
     * 清空横滑的内容
     */
    public void clear()
    {
        layout_menu.removeAllViews();
    }

    /**
     * 把view移到中间
     * @param tv
     */
    private void moveItemToCenter(TextView tv) {

        DisplayMetrics dm=getResources().getDisplayMetrics();
        int screenWith=dm.widthPixels;
        int[] locations=new int[2];
        tv.getLocationInWindow(locations);
        int rbWidth=tv.getWidth();
        horizonView.smoothScrollBy((locations[0] + rbWidth / 2 - screenWith /2),0);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if(layout_menu !=null && layout_menu.getChildCount()>0)
        {
            for (int i = 0; i <layout_menu.getChildCount() ; i++) {
                if(i==position)
                {
                    layout_menu.getChildAt(i).setSelected(true);
                }else{
                    layout_menu.getChildAt(i).setSelected(false);
                }
            }
        }

        //移动view  水平居中
        moveItemToCenter(topViews.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * viewPager适配器
     */
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
