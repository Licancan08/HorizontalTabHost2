package licancan.com.horizontaltabhost.app;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import licancan.com.horizontaltabhost.bean.Constants;

/**
 * Created by robot on 2017/8/30.
 */

public class MyApplication extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        x.Ext.init(this);
        //通过代码注册你的AppKey和AppSecret
        MobSDK.init(this, Constants.APPKEY,Constants.APPSECRET);

        //构建者模式
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
