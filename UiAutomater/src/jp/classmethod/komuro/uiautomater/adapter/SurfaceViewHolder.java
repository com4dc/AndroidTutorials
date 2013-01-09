package jp.classmethod.komuro.uiautomater.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback2;
import android.view.SurfaceView;
import android.widget.TextView;

public class SurfaceViewHolder implements Callback2  {
	
	public static final String TAG = SurfaceViewHolder.class.getSimpleName();
	
	TextView labelText;
	SurfaceView surfaceView;
	
	private RenderingThread mThread;
	
	/**
     * レンダリングスレッド
     *
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

            while (mRunning && !Thread.interrupted()) {
            	
            	// 描画用Canvasを取得
            	SurfaceHolder holder = mSurface.getHolder();
                final Canvas canvas = holder.lockCanvas(null);
                try {
                	if(canvas != null) {
                	// Canvasに四角形を描画
//                    canvas.drawColor(0xff000000, PorterDuff.Mode.CLEAR);
	                	canvas.drawColor(0xff000000);
	                    canvas.drawRect(x, y, x + 20.0f, y + 20.0f, paint);
                	}
                } finally {
                	if(canvas != null)
                		holder.unlockCanvasAndPost(canvas);
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

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// レンダリングスレッドの開始
        mThread = new RenderingThread(surfaceView);
        mThread.start();
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d(TAG, "surfaceDestroyed()");
		// レンダリングスレッド委
        if (mThread != null) mThread.stopRendering();
	}
	@Override
	public void surfaceRedrawNeeded(SurfaceHolder holder) {
	}
}