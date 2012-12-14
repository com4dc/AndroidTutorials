package jp.classmethod.komuro.uiautomater;

import java.io.IOException;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements SurfaceTextureListener {
	
	/** カメラオブジェクト. */
	private Camera camera;
	/** カメラのプレビューを表示するTextureViewオブジェクト. */ 
	private TextureView textureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// TextureViewをインスタンス化
		textureView = new TextureView(this);
		// Listenerを設定
		textureView.setSurfaceTextureListener(this);
		
		// TextureViewを配置
		setContentView(textureView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// TextureViewの初期時に呼ばれる(?)
		
		// カメラをインスタンス化
		camera = Camera.open();
		// プレビューサイズを取得
		Camera.Size preViewSize = camera.getParameters().getPreviewSize();
		// TextureViewの設定
		textureView.setLayoutParams(new FrameLayout.LayoutParams(preViewSize.width, preViewSize.height, Gravity.CENTER));
		
		// カメラのプレビューをTextureViewに設定
		try {
			camera.setPreviewTexture(surface);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		camera.startPreview();
		
		// α値を設定
		textureView.setAlpha(0.5f);
		// 45度傾ける
		textureView.setRotation(45.0f);
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TextureView破棄時にカメラリソースをRelease
		camera.stopPreview();
		camera.release();
		return true;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		
	}

}
