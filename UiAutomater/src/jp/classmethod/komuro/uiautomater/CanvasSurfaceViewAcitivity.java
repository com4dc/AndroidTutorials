package jp.classmethod.komuro.uiautomater;

import jp.classmethod.komuro.uiautomater.view.RenderSurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CanvasSurfaceViewAcitivity extends Activity {
	
	private RenderSurfaceView renderSurfaceView;
	private boolean tilt = false;
	
	private boolean opaque = false;
    private boolean mode = false;
    private ToggleButton toggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FrameLayout content = new FrameLayout(this);
		renderSurfaceView = new RenderSurfaceView(this);
		
		toggle = new ToggleButton(this);
		toggle.setTextOn("Set Alpha");
        toggle.setTextOff("Set Rotate");
        toggle.setChecked(false);
		toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mode = isChecked;
			}
		});
		
		// 500x500でViewを作成
        content.addView(renderSurfaceView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        content.addView(toggle, new FrameLayout.LayoutParams(200,200, Gravity.TOP));
        setContentView(content);
	}

	@Override
	public boolean onTouchEvent( MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if(mode) {
				Toast.makeText(this, "Set Alpha [SurfaceView]", Toast.LENGTH_SHORT).show();
    			if(!opaque) {
    				renderSurfaceView.setAlpha(0.2f);
    			}
    			else {
    				renderSurfaceView.animate().alpha(1.0f).setDuration(1000).start();
    			}
    			opaque = !opaque;
    		} else {
    			Toast.makeText(this, "Set Rotate [SurfaceView]", Toast.LENGTH_SHORT).show();
				if(!tilt) {
					renderSurfaceView.setRotation(45.0f);
				} else {
					renderSurfaceView.animate().rotation(0.f).setDuration(1000).start();
				}
    		}
			// フラグ反転
			tilt = !tilt;
		}
		
		return super.onTouchEvent(event);
	}
	
	
}
