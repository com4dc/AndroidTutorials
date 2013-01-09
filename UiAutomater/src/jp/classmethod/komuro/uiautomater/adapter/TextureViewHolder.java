package jp.classmethod.komuro.uiautomater.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.widget.TextView;

public class TextureViewHolder implements TextureView.SurfaceTextureListener  {
	TextView labelText;
	TextureView textureView;
	
	private RenderingThread mThread;
	
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface,
			int width, int height) {
		// レンダリングスレッドの開始
        mThread = new RenderingThread(textureView);
        mThread.start();
	}
	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// レンダリングスレッド委
        if (mThread != null) mThread.stopRendering();
        return true;
	}
	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
			int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * レンダリングスレッド
     *
     */
    private static class RenderingThread extends Thread {
    	
    	// 描画用TextureViewクラス
        private final TextureView mSurface;
        // スレッド動作フラグ
        private volatile boolean mRunning = true;

        /**
         * コンストラクタ
         * @param surface
         */
        public RenderingThread(TextureView surface) {
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
                final Canvas canvas = mSurface.lockCanvas(null);
                try {
                	
                	// Canvasに四角形を描画
//                    canvas.drawColor(0xff000000, PorterDuff.Mode.CLEAR);
                	canvas.drawColor(0xff000000);
                    canvas.drawRect(x, y, x + 20.0f, y + 20.0f, paint);
                } finally {
                    mSurface.unlockCanvasAndPost(canvas);
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