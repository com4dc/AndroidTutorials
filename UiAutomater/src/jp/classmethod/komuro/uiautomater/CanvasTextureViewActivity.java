package jp.classmethod.komuro.uiautomater;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * <ul>
 * <li>https://groups.google.com/forum/?fromgroups=#!topic/android-developers/_Ogjc8sozpA</li>
 * <li>http://pastebin.com/J4uDgrZ8</li>
 * </ul>
 * サンプルコードを実行してみた。TexuterView#lockCanvasの使い方
 */
public class CanvasTextureViewActivity extends Activity
        implements TextureView.SurfaceTextureListener {
	
    private TextureView mTextureView;
    private CanvasTextureViewActivity.RenderingThread mThread;
    private boolean tilt = false;
    
    private boolean opaque = false;
    private boolean mode = false;
    private ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout content = new FrameLayout(this);

        // テクスチャビューを作成して設定
        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        
        toggle = new ToggleButton(this);
        toggle.setTextOn("Set Alpha");
        toggle.setTextOff("Set Rotate");
        toggle.setChecked(false);
        toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mode = isChecked;
			}
		});
        
        // 透過処理をtrue
        mTextureView.setOpaque(true);
        
        // 500x500でViewを作成
        content.addView(mTextureView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        content.addView(toggle, new FrameLayout.LayoutParams(200,200, Gravity.TOP));
        setContentView(content);
    }
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
    	
    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
    		
    		if(mode) {
    			Toast.makeText(this, "Set Alpha [TextureView]", Toast.LENGTH_SHORT).show();
    			if(!opaque) {
    				mTextureView.setAlpha(0.2f);
    			}
    			else {
    				mTextureView.animate().alpha(1.0f).setDuration(1000).start();
    			}
    			opaque = !opaque;
    		} else {
    			Toast.makeText(this, "Set Rotate [TextureView]", Toast.LENGTH_SHORT).show();
    			if(!tilt) {
        			mTextureView.setRotation(45.f);
        		} else {
        			mTextureView.animate().rotation(0.f).setDuration(1000).start();
        		}
        		tilt = !tilt;
    		}
    		
    	}
    	
		return super.onTouchEvent(event);
	}

	@Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
    	
    	// TextureView活性化時にCallされる
    	
    	// レンダリングスレッドの開始
        mThread = new RenderingThread(mTextureView);
        mThread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
    	// レンダリングスレッド委
        if (mThread != null) mThread.stopRendering();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Ignored
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
