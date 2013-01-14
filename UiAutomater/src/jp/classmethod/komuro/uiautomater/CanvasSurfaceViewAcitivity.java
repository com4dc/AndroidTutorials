package jp.classmethod.komuro.uiautomater;

import jp.classmethod.komuro.uiautomater.view.RenderSurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class CanvasSurfaceViewAcitivity extends Activity {
	
	private RenderSurfaceView renderSurfaceView;
	private boolean tilt = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FrameLayout content = new FrameLayout(this);
		renderSurfaceView = new RenderSurfaceView(this);
		
		// 500x500でViewを作成
        content.addView(renderSurfaceView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        setContentView(content);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if(tilt) {
				renderSurfaceView.setRotation(45.0f);
			} else {
				renderSurfaceView.animate().rotation(0.f).setDuration(1000).start();
			}
			// フラグ反転
			tilt = !tilt;
		}
		
		return super.onTouchEvent(event);
	}
	
	
}
