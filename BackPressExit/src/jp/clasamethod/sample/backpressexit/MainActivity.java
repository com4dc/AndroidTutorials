package jp.clasamethod.sample.backpressexit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	// Log用のTAG
	private static final String TAG = MainActivity.class.getSimpleName();
	
	// BackボタンPress時の有効タイマー
	private CountDownTimer keyEventTimer;
	
	// 一度目のBackボタンが押されたかどうかを判定するフラグ
	private boolean pressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// onFinish(), onTick()
		keyEventTimer = new CountDownTimer(1000, 100) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				Log.d(TAG, "call onTick method");
			}
			
			@Override
			public void onFinish() {
				pressed = false;
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		// Backボタン検知
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if(!pressed) {
				// Timerを開始
				keyEventTimer.cancel();
				keyEventTimer.start();
			
				// 終了する場合, もう一度タップするようにメッセージを出力する
				Toast.makeText(this, "終了する場合は、もう一度バックボタンを押してください", Toast.LENGTH_SHORT).show();
				pressed = true;
				return false;
			}
			
			// pressed=trueの時、通常のBackボタンで終了処理.
			return super.dispatchKeyEvent(event);
		}
		// Backボタンに関わらないボタンが押された場合は、通常処理.
		return super.dispatchKeyEvent(event);
	}

}
