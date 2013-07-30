package jp.classmethod.sample.locationclientsample.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public final class PreferenceUtils {

	/** LocationServiceが起動しているかどうかを記録するKey */
	public static final String PREF_LOCATION_SERVICE_KEY = "location_service_key";
	
	private PreferenceUtils() {
	}
	
	/**
	 * ApplicationのSharedPreferenceを取得
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreference(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/**
	 * LocagtionServiceの起動状態を永続保存
	 * @param context
	 * @param enable
	 */
	public static void setEnableLocationService(Context context, boolean enable) {
		SharedPreferences pref = getSharedPreference(context);
		Editor edit = pref.edit();
		edit.putBoolean(PREF_LOCATION_SERVICE_KEY, enable);
		edit.commit();
	}
	
	/**
	 * LocationServiceの起動状態を取得
	 * @param context
	 * @return
	 */
	public static boolean isEnableLocationService(Context context) {
		SharedPreferences pref = getSharedPreference(context);
		return pref.getBoolean(PREF_LOCATION_SERVICE_KEY, false);
	}
	
}
