package jp.classmethod.komuro.uiautomater;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.FrameLayout;

public class FaceDetectActivity extends Activity implements
		SurfaceTextureListener {
	
	private static final String TAG = FaceDetectActivity.class.getSimpleName();

	// TextureView
	private TextureView textureView;
	// Camera
	private Camera camera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TextureViewインスタンスを生成
		textureView = new TextureView(this);

		// Listenerを設定
		textureView.setSurfaceTextureListener(this);
		setContentView(textureView);
	}

	// TextureView生成時にCallされる
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// カメラをオープン
		camera = Camera.open();
		try {
			camera.setPreviewTexture(surface);

			Camera.Size previewSize = camera.getParameters().getPreviewSize();
			textureView.setLayoutParams(new FrameLayout.LayoutParams(
					previewSize.width, previewSize.height, Gravity.CENTER));
			
			textureView.setRotation(90.0f);

			// 顔認識した時の動作を定義
			camera.setFaceDetectionListener(new FaceDetectionListener() {

				@Override
				public void onFaceDetection(Face[] faces, Camera camera) {
					Paint paint = new Paint();
					paint.setColor(0xff00ff00);

					// 顔認識したオブジェクトを評価する
					for (Face face : faces) {
						Rect rect = face.rect;
						Canvas canvas = textureView.lockCanvas();
						try {
							// Canvasに四角形を描画
							canvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
							canvas.drawRect(rect, paint);
							Log.d(TAG, "draw face = (" + rect.top + "," + rect.left +")");
						} finally {
							textureView.unlockCanvasAndPost(canvas);
						}
					}
				}
			});

			camera.startPreview();
			camera.startFaceDetection();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// Camera Resourceを解放
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
