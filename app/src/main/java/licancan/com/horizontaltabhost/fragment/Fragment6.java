package licancan.com.horizontaltabhost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import licancan.com.horizontaltabhost.DetailActivity;
import licancan.com.horizontaltabhost.R;
import licancan.com.horizontaltabhost.adapter.MyXListAdapter;
import licancan.com.horizontaltabhost.api.API;
import licancan.com.horizontaltabhost.bean.Bean;
import view.xlistview.XListView;

/**
 * Created by robot on 2017/8/30.
 */

@ContentView(R.layout.fragment6)
public class Fragment6 extends Fragment implements XListView.IXListViewListener {

    @ViewInject(R.id.xlv_list) XListView xlv_list;
    private View myView;
    private MyXListAdapter myAdapter;
    private List<Bean.ResultBean.DataBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(myView==null)
        {
            myView = x.view().inject(this,inflater,container);
        }

        initData();
        xlv_list.setXListViewListener(Fragment6.this);
        //支持刷新和加载
        xlv_list.setPullRefreshEnable(true);
        xlv_list.setPullLoadEnable(true);
        return myView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        RequestParams params=new RequestParams(API.URL_POST);
        params.addBodyParameter("key",API.KEY);
        params.addBodyParameter("type",API.TYPE6);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //请求成功   打印json串
                System.out.println("result============="+result);
                //解析json
                getJson(result);
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
     * 解析json串
     * @param result
     */
    private void getJson(String result) {
        Gson gson=new Gson();
        Bean bean = gson.fromJson(result, Bean.class);
        list = bean.getResult().getData();
        for (Bean.ResultBean.DataBean b: list) {
            System.out.println("bean============="+b.getTitle()+"  "+b.getThumbnail_pic_s()+"  "+b.getCategory()+"  "+b.getDate());
        }


        //添加适配器
        if(myAdapter ==null)
        {
            myAdapter = new MyXListAdapter(getActivity(), list);
            xlv_list.setAdapter(myAdapter);
        }
        else{
            myAdapter.notifyDataSetChanged();
        }

        xlv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("url", list.get(i-1).getUrl());
                startActivity(intent);
            }
        });

    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        //重新请求数据
        initData();

        xlv_list.stopLoadMore();
        xlv_list.stopRefresh();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        list.addAll(list);
        //刷新适配器
        myAdapter.notifyDataSetChanged();
        xlv_list.stopLoadMore();
        xlv_list.stopRefresh();
    }
}
