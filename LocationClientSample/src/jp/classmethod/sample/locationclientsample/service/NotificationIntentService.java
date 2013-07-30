package jp.classmethod.sample.locationclientsample.service;

import jp.classmethod.sample.locationclientsample.R;
import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.google.android.gms.location.LocationClient;

/**
 * Notification通知を行うIntentService
 * @author komuro
 *
 */
public class NotificationIntentService extends IntentService {

	public NotificationIntentService(String name) {
		super(name);
	}
	
	public NotificationIntentService() {
		super("NotificationIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// GooglePlayServiceからSendされたPendingIntentを検知する
		// 現在のLocation情報が格納されている
		Location location = intent.getParcelableExtra(LocationClient.KEY_LOCATION_CHANGED);
		
		// LocationデータをNotificationで表示
		sendNotification(location);
	}

	/**
	 * Notificationを表示する
	 * @param location
	 */
	private void sendNotification(Location location) {
		NotificationCompat.Builder builder = new Builder(getApplicationContext());
		
		builder.setTicker("Location Changed");
		builder.setContentTitle("Location Detail");
		builder.setSmallIcon(R.drawable.ic_launcher);
		
		StringBuffer sb = new StringBuffer();
		sb.append("location ; (");
		sb.append(location.getLatitude()); 	// 緯度
		sb.append(location.getLongitude());	// 経度
		sb.append(")");
		builder.setContentText(sb.toString());
	}
}
