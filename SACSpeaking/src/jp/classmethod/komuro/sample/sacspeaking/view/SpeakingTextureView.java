package jp.classmethod.komuro.sample.sacspeaking.view;

import jp.classmethod.komuro.sample.sacspeaking.R;
import jp.classmethod.komuro.sample.sacspeaking.util.ImageUtil;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class SpeakingTextureView extends TextureView implements SurfaceTextureListener {
	
	// Rendering Threadオブジェクト
	private RenderingThread thread;

	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SpeakingTextureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setSurfaceTextureListener(this);
	}
	
	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	public SpeakingTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setSurfaceTextureListener(this);
	}
	
	/**
	 * Constructor
	 * @param context
	 */
	public SpeakingTextureView(Context context) {
		super(context);
		
		setSurfaceTextureListener(this);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width,
			int height) {
		
		// アルファ値で透明度を設定
		setAlpha(0.85f);
		
		Log.d("SpeakingTextureView", "start Rendering Thread");
		thread = new RenderingThread(this, getResources());
		thread.start();
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
		thread.stopRendering();
		return true;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width,
			int height) {

	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

	}
	
	/**
	 * レンダリング用スレッドクラス
	 * @author komuro.hiraku
	 *
	 */
	private static class RenderingThread extends Thread {
		
		// スレッド動作状態フラグ
		public volatile boolean running = true;
		
		// TextureViewオブジェクト
		private TextureView textureView;
		
		private Resources resource;
		
		/**
		 * public constructor
		 * @param textureView
		 */
		public RenderingThread(TextureView textureView, Resources resource) {
			this.textureView = textureView;
			this.resource = resource;
		}

		@Override
		public void run() {
			
			Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            
            // 笑い男アイコンを読み込む
            Bitmap laughingmanSrc = BitmapFactory.decodeResource(resource, R.drawable.laughingman);
            int resizeWidth = textureView.getWidth();
            int resizeHeight = textureView.getHeight();
            Bitmap resizedBmp = ImageUtil.resizeImage(laughingmanSrc, resizeWidth, resizeHeight);
            
            while(running && !isInterrupted()) {
            	Canvas canvas = textureView.lockCanvas();
            	try{
	            	if(canvas != null) {
	            		// 描画
	            		canvas.drawColor(0xff000000, PorterDuff.Mode.CLEAR);
	            		canvas.drawBitmap(resizedBmp, 0, 0, paint);
	            	}
            	} finally {
            		if(canvas != null) {
            			textureView.unlockCanvasAndPost(canvas);
            		}
            	}
            }
		}
		
		/**
		 * 描画停止
		 */
		public void stopRendering() {
			interrupt();
			running = false;
		}
	}

}
