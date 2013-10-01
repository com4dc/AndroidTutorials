package jp.classmethod.android.sample.badoperationcollection2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements LoaderCallbacks<String> {
	
	private MainActivity self = MainActivity.this;
	
	private Button addFragment;
	
	private Button clearBackStack;
	
	private Button sendNotification;
	
	private int fragmentCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Fragmentを追加する
		addFragment = (Button) findViewById(R.id.add_fragment);
		addFragment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle argument = new Bundle();
				argument.putString(SampleFragment.FRAGMENT_ID_KEY, "Fragment#" + fragmentCount++);

				SampleFragment fragment = new SampleFragment();
				fragment.setArguments(argument);
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.fragment_container, fragment);
				ft.addToBackStack(null);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});
		
		// Backstack解放
		clearBackStack = (Button) findViewById(R.id.clear_backstack);
		clearBackStack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				
				// 第一引数がヒットするまでFragmentをPopし続けるので、、結果BackStackのAll Clearと同じ結果になる
				fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

				fragmentCount = 0;
			}
		});
		
		// Notificationを送付
		sendNotification = (Button) findViewById(R.id.send_notification);
		sendNotification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(self, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				NotificationUtils.sendNotification(self, "Bad Operation", "Bad Operation Sample", intent);
				
				// Async Task Start
				getLoaderManager().initLoader(0, new Bundle(), self);
			}
		});
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<String> onCreateLoader(int id, Bundle args) {
		Loader<String> loader = new SampleAsyncTaskLoader(self);
		Toast.makeText(self, "Start Loading", Toast.LENGTH_SHORT).show();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<String> loader, String result) {
		Toast.makeText(self, "Loader Task Finished", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoaderReset(Loader<String> loader) {
	}

}
