package jp.clasamethod.sample.backpressexit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	// BackボタンPress時の有効タイマー
	private CountDownTimer keyEventTimer;
	
	// 一度目のBackボタンが押されたかどうかを判定するフラグ
	private boolean pressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		keyEventTimer = new CountDownTimer(1000, 100) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				Log.d("onTick", "call onTick method");
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
			
			keyEventTimer.cancel();
			keyEventTimer.start();
			
			if(!pressed) {
				Toast.makeText(this, "終了する場合は、もう一度バックボタンを押してください", Toast.LENGTH_SHORT).show();
				pressed = true;
				return false;
			}
			
			return super.dispatchKeyEvent(event);
		}
		
		return super.dispatchKeyEvent(event);
	}
	
	

}
