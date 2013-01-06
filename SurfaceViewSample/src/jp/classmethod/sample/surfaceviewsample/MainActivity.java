package jp.classmethod.sample.surfaceviewsample;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements SurfaceHolder.Callback2 {

	private RenderingThread thread = null;
	private SurfaceView surfaceView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        FrameLayout content = new FrameLayout(this);

        // SurfaceViewを作成して設定
        surfaceView = new SurfaceView(this);
        surfaceView.getHolder().addCallback(this);
        
        // 500x500でViewを作成
        content.addView(surfaceView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        setContentView(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread = new RenderingThread(surfaceView);
		thread.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (thread != null) thread.stopRendering();
	}

	@Override
	public void surfaceRedrawNeeded(SurfaceHolder holder) {
		
	}
	
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

            SurfaceHolder holder = mSurface.getHolder();
            
            while (mRunning && !Thread.interrupted()) {
            	
            	// 描画用Canvasを取得
            	final Canvas canvas = holder.lockCanvas(null);
                try {
                	
                	// Canvasに四角形を描画
                    canvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
                    canvas.drawRect(x, y, x + 20.0f, y + 20.0f, paint);
                } finally {
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
    
}
