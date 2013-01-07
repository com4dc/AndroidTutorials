package jp.classmethod.renderscript.sample.gravity;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.renderscript.RSTextureView;
import android.renderscript.RenderScriptGL;
import android.renderscript.RenderScriptGL.SurfaceConfig;
import android.view.MotionEvent;

public class GravityView extends RSTextureView {

	public GravityView(Context context) {
		super(context);
	}
	
	// RenderScriptGL
	private RenderScriptGL rs;
	
	private GravityRS render;

	@Override
	@Deprecated
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		super.onSurfaceTextureAvailable(surface, width, height);
		
		if(rs == null) {
			RenderScriptGL.SurfaceConfig sc = new SurfaceConfig();
			rs = createRenderScriptGL(sc);
			rs.setSurfaceTexture(surface, width, height);
			
			render = new GravityRS();
			render.init(rs, getResources(), width, height);
		}
	}

	@Override
	@Deprecated
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		if(rs != null) {
			rs = null;
			destroyRenderScriptGL();
		}
		return super.onSurfaceTextureDestroyed(surface);
	}

	@Override
	@Deprecated
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		super.onSurfaceTextureSizeChanged(surface, width, height);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		render.newTouchPosition(event.getX(0), event.getY(0),
	            event.getPressure(0), event.getPointerId(0));        
	        return true;
	}
	
	
	
}
