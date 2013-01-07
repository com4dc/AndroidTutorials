package jp.classmethod.sample.surfaceviewsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.FrameLayout;

public class SurfaceViewTestActivity extends Activity {

	private RenderSurfaceView surfaceView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        FrameLayout content = new FrameLayout(this);

        // SurfaceViewを作成して設定
        surfaceView = new RenderSurfaceView(this);
        
        surfaceView.setRotation(45.0f);
        
        // 500x500でViewを作成
        content.addView(surfaceView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        setContentView(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
