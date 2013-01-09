package jp.classmethod.komuro.sample.sacspeaking;

import java.io.IOException;
import java.util.List;

import jp.classmethod.komuro.sample.sacspeaking.gravity.GravityView;
import jp.classmethod.komuro.sample.sacspeaking.view.SpeakingTextureView;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class CameraScreenActivity extends Activity implements SurfaceTextureListener {
	
	/** Log用Tag */
	public static final String TAG = CameraScreenActivity.class.getSimpleName();
	
	/** カメラプレビュー表示用TextureView */
	private TextureView cameraTextureView;
	
	/** Speaking View */
	private TextureView laughingmanView;
	private int laughingmanTop;
	private int laughingmanLeft;
	
	private GravityView gravityView;
	
	private boolean showLaughingman = false;
	
	/** カメラインスタンス */
	private Camera camera;
	
	/** 画面の傾き */
	private static final int DIRECTION = 90;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Layout XMLからinflate
		setContentView(R.layout.camera_activity);
		
		// TextureViewを取得
		cameraTextureView = (TextureView) findViewById(R.id.camera_textureview);
		
		laughingmanView = new SpeakingTextureView(this);
		
		FrameLayout layout = (FrameLayout) findViewById(R.id.screen_layout);
		
		LayoutParams params = new LayoutParams(300, 300, Gravity.CENTER_VERTICAL);
		layout.addView(laughingmanView, params);
		
		// GravityViewの生成
		gravityView = new GravityView(this);
		LayoutParams gViewParams = new LayoutParams(300, 300, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		layout.addView(gravityView, gViewParams);
		
		// SurfaceTextureListenerを設定
		cameraTextureView.setSurfaceTextureListener(this);
		
		// 初めは見えない
		laughingmanView.setRotationY(90.0f);
		
		laughingmanTop = laughingmanView.getTop();
		laughingmanLeft = laughingmanView.getLeft();
		
		// くるくる回転させる
//		laughingmanView.animate().rotation(360f).setDuration(30000).start();
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width,
			int height) {
		
		// カメラインスタンスをオープン
		camera = Camera.open();

		Camera.Parameters parameters = camera.getParameters();
		List<Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
		Size size = supportedPictureSizes.get(0);	// 一番先頭の画像サイズを採用
		parameters.setPictureSize(size.width, size.height);	// 画像サイズを設定しなおす
		camera.setParameters(parameters);	// パラメータを設定しなおし
		
		camera.setDisplayOrientation(DIRECTION);
		
		try {
			camera.setPreviewTexture(surfaceTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Preview開始
		camera.startPreview();
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
		
		// Previewを停止
		camera.stopPreview();
		camera.release();
		
		Log.d(TAG, "onSurfaceTextureDestroyed()#release Resources");
		return true;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width,
			int height) {
		Log.d(TAG, "onSurfaceTextureSizeChanged()");
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
		// 結構すごい量のLogが出る. カメラのプレビューだからかも.
//		Log.d(TAG, "onSurfaceTextureUpdated()");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			
			// 回転軸を0に設定
			laughingmanView.setPivotX(0.0f);
			if(!showLaughingman) {
				laughingmanView.animate().rotationY(30.0f).setDuration(500).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animator animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animator animation) {
						// 回転軸を画像の中心に設定
//						laughingmanView.setPivotX(laughingmanTop + 150);
//						laughingmanView.setPivotY(laughingmanLeft + 150);
//						
//						// Property Animationの実行
//						laughingmanView.animate().rotation(360.0f).setInterpolator(new LinearInterpolator()).setDuration(10000).start();
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {
						
					}
				}).start();
				showLaughingman = true;
			}
			else { 
				laughingmanView.animate().rotationY(90.0f).setDuration(500).setInterpolator(new AccelerateInterpolator()).start();
				showLaughingman = false;
			}
		}
		return super.onTouchEvent(event);
	}
	
	

}
