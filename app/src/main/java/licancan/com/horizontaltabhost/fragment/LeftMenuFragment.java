package licancan.com.horizontaltabhost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import licancan.com.horizontaltabhost.MainActivity;
import licancan.com.horizontaltabhost.OtherLoginActivity;
import licancan.com.horizontaltabhost.R;


/**
 * Created by robot on 2017/8/30.
 */
public class LeftMenuFragment extends Fragment implements View.OnClickListener {

    private Button login;
    private ImageView iv_night;
    private TextView tv_night;
    private ImageView iv_qq;

    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private UserInfo mUserInfo;
    private BaseUiListener mIUiListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=View.inflate(getActivity(),R.layout.left_menu,null);
        login = view.findViewById(R.id.login);
        //qq登录
        iv_qq = view.findViewById(R.id.iv_qq);
        iv_qq.setOnClickListener(this);
        //夜间模式
        iv_night = view.findViewById(R.id.iv_night);
        tv_night = view.findViewById(R.id.tv_night);

        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        iv_night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tv_night.getText().equals("夜间"))
                {
                    tv_night.setText("日间");
                    ((MainActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    tv_night.setText("日间");
                }
                else if(tv_night.getText().equals("日间"))
                {
                    tv_night.setText("夜间");
                    ((MainActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), OtherLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * qq登录
     * @param view
     */
    @Override
    public void onClick(View view) {
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(getActivity(),"all", mIUiListener);


    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener{

        @Override
        public void onComplete(Object response) {
            Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
