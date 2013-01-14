package jp.classmethod.komuro.uiautomater;

import jp.classmethod.komuro.uiautomater.view.RenderSurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

public class CanvasSurfaceViewAcitivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FrameLayout content = new FrameLayout(this);
		RenderSurfaceView surcaceView = new RenderSurfaceView(this);
		
		// 500x500でViewを作成
        content.addView(surcaceView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        setContentView(content);
	}
}
