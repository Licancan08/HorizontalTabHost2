package licancan.com.horizontaltabhost;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import licancan.com.horizontaltabhost.bean.User;

public class OtherLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_x;
    private EditText et_phone;
    private TextView tv_sendCode;
    private EditText et_code;
    private Button but_login;
    private int TIME=45;
    private final int SECOND=1000;

    Handler timeHandler=new Handler();
    Runnable timeRunnable=new Runnable() {
        @Override
        public void run() {
            TIME--;
            if(TIME==0)
            {
                timeHandler.removeCallbacks(this);
                //再次获取
                TIME=45;
                tv_sendCode.setEnabled(true);
                tv_sendCode.setText("  再次获取");
            }
            else{
                tv_sendCode.setEnabled(false);
                tv_sendCode.setTextColor(Color.GRAY);
                tv_sendCode.setText("  "+TIME+" s");
                timeHandler.postDelayed(this,SECOND);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_login);
        initView();
        initData();
        registerSMS();
    }

    /**
     * 注册SMS的方法
     */
    private void registerSMS() {
        EventHandler eventHandler=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OtherLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    if(result==SMSSDK.RESULT_COMPLETE)
                    {//只有服务器验证成功  才能允许用户登录
                        if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(OtherLoginActivity.this,"服务器验证成功",Toast.LENGTH_SHORT).show();
                                    User user=new User();
                                    user.uid=et_phone.getText().toString();
                                    user.phone=et_phone.getText().toString();

                                }
                            });
                        }
                    }
                    else if(event==SMSSDK.EVENT_GET_VERIFICATION_CODE)
                    {//获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OtherLoginActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(event==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES)
                    {
                        //返回支持发送验证码的国家列表
                    }
                }

            }
        };

        //注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        iv_x = (ImageView) findViewById(R.id.iv_x);
        et_phone = (EditText) findViewById(R.id.et_phone);//输入的手机号码
        tv_sendCode = (TextView) findViewById(R.id.tv_sendCode);//获取验证码
        et_code = (EditText) findViewById(R.id.et_code);     //输入验证码
        but_login = (Button) findViewById(R.id.but_login);  //进入头条的按钮

        //获取验证码的点击事件
        tv_sendCode.setOnClickListener(this);
        //进入头条的按钮 的点击事件
        but_login.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //关闭页面的点击事件
        iv_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 登录按钮的点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.but_login:
                verify();
                SMSSDK.submitVerificationCode("86",et_phone.getText().toString(),et_code.getText().toString());
                break;
            case R.id.tv_sendCode:
                if(TextUtils.isEmpty(et_phone.getText().toString()))
                {
                    Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                timeHandler.postDelayed(timeRunnable,SECOND);
                SMSSDK.getVerificationCode("86",et_phone.getText().toString());
                break;
        }
    }

    /**
     * 验证输入
     */
    private void verify() {
        if(TextUtils.isEmpty(et_phone.getText().toString()))
        {
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(et_code.getText().toString()))
        {
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
