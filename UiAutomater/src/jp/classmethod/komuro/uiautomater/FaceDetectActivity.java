package jp.classmethod.komuro.uiautomater;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class FaceDetectActivity extends Activity implements SurfaceTextureListener{

	// TextureView
	private TextureView textureView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		textureView = new TextureView(this);
		
		// Listenerを設定
		textureView.setSurfaceTextureListener(this);
	}

	// TextureView生成時にCallされる
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return false;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
	}
}
