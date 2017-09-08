package licancan.com.horizontaltabhost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class PingyiActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingyi);

        SwipeBackLayout layout=getSwipeBackLayout();
        layout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }
}
