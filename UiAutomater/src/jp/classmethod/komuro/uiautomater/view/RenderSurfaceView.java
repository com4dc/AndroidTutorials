package jp.classmethod.komuro.uiautomater.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback2;
import android.view.SurfaceView;

public class RenderSurfaceView extends SurfaceView implements Callback2 {

	private RenderingThread thread = null;
	
	/**
	 * Constructor
	 * @param context
	 */
	public RenderSurfaceView(Context context) {
		super(context);
		
		getHolder().addCallback(this);
	}

	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	public RenderSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		getHolder().addCallback(this);
	}

	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RenderSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		getHolder().addCallback(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		thread = new RenderingThread(this);
		thread.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(thread != null && thread.mRunning) {
			thread.stopRendering();
		}
	}

	@Override
	public void surfaceRedrawNeeded(SurfaceHolder holder) {
		
	}
	
	/**
     * レンダリングスレッド
     */
    private static class RenderingThread extends Thread {
    	
    	// 描画用TextureViewクラス
        private final SurfaceView mSurface;
        // スレッド動作フラグ
        private volatile boolean mRunning = true;

        /**
         * コンストラクタ
         * @param surface
         */
        public RenderingThread(SurfaceView surface) {
            mSurface = surface;
        }

        @Override
        public void run() {
            float x = 0.0f;
            float y = 0.0f;
            float speedX = 5.0f;
            float speedY = 3.0f;
            
            Paint paint = new Paint();
            paint.setColor(0xff00ff00);

            SurfaceHolder holder = mSurface.getHolder();
            
            while (mRunning && !Thread.interrupted()) {
            	
            	// 描画用Canvasを取得
            	final Canvas canvas = holder.lockCanvas(null);
                try {
                	if(canvas != null) {
	                	// Canvasに四角形を描画
//	                    canvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
                		canvas.drawColor(0xff000000);
	                    canvas.drawRect(x, y, x + 20.0f, y + 20.0f, paint);
                	}
                } finally {
                    if(canvas != null) holder.unlockCanvasAndPost(canvas);
                }

                // 0.25秒毎に1度更新
                if (x + 20.0f + speedX >= mSurface.getWidth() || x + speedX <= 0.0f) {
                    speedX = -speedX;
                }
                if (y + 20.0f + speedY >= mSurface.getHeight() || y + speedY <= 0.0f) {
                    speedY = -speedY;
                }

                x += speedX;
                y += speedY;

                try {
                	// 0.15秒Sleep
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    // Interrupted
                }
            }
        }
        
        /**
         * レンダリング終了
         */
        void stopRendering() {
            interrupt();
            mRunning = false;
        }
    }

}
