package jp.classmethod.android.sample.badoperationcollection2;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class SampleAsyncTaskLoader extends AsyncTaskLoader<String> {
	
	public SampleAsyncTaskLoader(Context context) {
		super(context);
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		forceLoad();
	}

	@Override
	public String loadInBackground() {
		try {
			// 10秒停止
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Complete Task";
	}

}
