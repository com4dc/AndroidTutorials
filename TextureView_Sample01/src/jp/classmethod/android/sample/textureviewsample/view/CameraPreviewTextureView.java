package jp.classmethod.android.sample.textureviewsample.view;

import java.io.IOException;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.LinearLayout;

/**
 * カメラプレビューの顔認識はNG
 * onDraw, drawがfinal宣言で潰されているので、Override不可. どこに使う?
 * @author komuro.hiraku
 *
 */
public class CameraPreviewTextureView extends TextureView implements
		SurfaceTextureListener {
	
	// カメラ
	private Camera camera;
	
	/**
	 * コンストラクタ
	 * @param context
	 */
	public CameraPreviewTextureView(Context context) {
		super(context);
		
		// Listenerを設定
		setSurfaceTextureListener(this);
	}
	
	/**
	 * コンストラクタ
	 * @param context
	 * @param attrs
	 */
	public CameraPreviewTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// Listenerを設定
		setSurfaceTextureListener(this);
	}

	public CameraPreviewTextureView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		// Listenerを設定
		setSurfaceTextureListener(this);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {

		// カメラをインスタンス化
		camera = Camera.open();
		// プレビューサイズを取得
		Camera.Size preViewSize = camera.getParameters().getPreviewSize();
		Log.d("Camera Preview Size", "width : " + preViewSize.width + ", height : " + preViewSize.height );

		// プレビュー画面の大きさをTextureViewに設定
		setLayoutParams(new LinearLayout.LayoutParams(preViewSize.width, preViewSize.height, Gravity.CENTER));
		
		// カメラのプレビューをTextureViewに設定
		try {
			camera.setPreviewTexture(surface);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		camera.startPreview();
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
