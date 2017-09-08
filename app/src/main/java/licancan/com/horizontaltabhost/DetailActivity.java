package licancan.com.horizontaltabhost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class DetailActivity extends PingyiActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        wv.getSettings().setJavaScriptEnabled(true);
        //接收跳转的值
        Intent in=getIntent();
        String url = in.getStringExtra("url");
        wv.loadUrl(url);

        wv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToFinishActivity();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        wv = (WebView) findViewById(R.id.wv);
    }
}
