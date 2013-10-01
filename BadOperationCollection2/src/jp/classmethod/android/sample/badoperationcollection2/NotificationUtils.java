package jp.classmethod.android.sample.badoperationcollection2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;

/**
 * Notificationを司るUtilityクラス
 * @author komuro.hiraku
 *
 */
public final class NotificationUtils {
	
	public static final int NOTIFICATION_REQUEST_CODE = 999;

	private NotificationUtils() {
	}
	
	/**
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param intent
	 */
	public static void sendNotification(Context context, String title, String message, Intent intent) {
		NotificationCompat.Builder builder = new Builder(context);

		// Iconを設定
		builder.setSmallIcon(R.drawable.ic_launcher);
		
		// Tickerを設定
		builder.setTicker(message);
		
		builder.setContentTitle("Bad Operation Collection");
		builder.setContentText("#2 FragmentManager#popStack");
		
		builder.setAutoCancel(true);
		
		// Large Iconを設定
		Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		builder.setLargeIcon(largeIcon);
		
		if(intent != null) {
			// PendingIntentを設定
			PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.setContentIntent(pendingIntent);
		}
		
		// BigTextStyle
		BigTextStyle style = new BigTextStyle(builder);
		style.bigText("Bad Operation Collection");
		style.setBigContentTitle("#2 FragmentManager#popStack");
		
		// Notificationを発火
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, style.build());
	}
}
