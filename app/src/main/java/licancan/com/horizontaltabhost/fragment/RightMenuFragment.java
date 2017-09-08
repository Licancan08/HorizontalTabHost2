package licancan.com.horizontaltabhost.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.city_picker.CityListActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import licancan.com.horizontaltabhost.DownLoadActivity;
import licancan.com.horizontaltabhost.R;


/**
 * Created by robot on 2017/8/30.
 */

public class RightMenuFragment extends Fragment implements View.OnClickListener {

    private ImageView back;
    private View view;
    private RelativeLayout layout_wifiload;
    private RelativeLayout layout_download;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.right_menu,null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        back = view.findViewById(R.id.iv_back);
        layout_wifiload=view.findViewById(R.id.layout_wifiload);
        layout_download=view.findViewById(R.id.layout_download);
        //离线下载和非wifi网络流量
        layout_wifiload.setOnClickListener(this);
        layout_download.setOnClickListener(this);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"城市列表",Toast.LENGTH_SHORT).show();
                CityListActivity.startCityActivityForResult(getActivity());
            }
        });
    }

    /**
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //离线下载
            case R.id.layout_download:
                startActivity(new Intent(getActivity(), DownLoadActivity.class));
                break;
            //非Wifi网络流量
            case R.id.layout_wifiload:
                AlertDialog.Builder b=new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"大图", "无图"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });
                b.create().show();
                break;
        }
    }
}
