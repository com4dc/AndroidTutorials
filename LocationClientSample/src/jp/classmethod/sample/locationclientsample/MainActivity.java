package jp.classmethod.sample.locationclientsample;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
	
	/** LocationClientオブジェクト */
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 引数は、Context, ConnectionCallbacks, OnConnectionFailedListener
		mLocationClient = new LocationClient(this, this, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		
	}

	@Override
	public void onDisconnected() {
		
	}

}
