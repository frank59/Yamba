package com.geewaza.android.yamba;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;

/**
 * Created by wangh on 2016/1/28.
 */
public class RefreshService extends IntentService {
	private static final String TAG = RefreshService.class.getSimpleName();

	public RefreshService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final String username = prefs.getString("username", "");
		final String password = prefs.getString("password", "");

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "Please update your username and password", Toast.LENGTH_LONG).show();
			return;
		}
		Log.d(TAG, "onStarted");
		DbHelper dbHelper = new DbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		YambaClient cloud = new YambaClient(username, password);
		try {
			List<YambaClient.Status> timeline = cloud.getTimeline(20);
			for (YambaClient.Status status: timeline) {
				values.clear();
				values.put(StatusConstract.Column.ID, status.getId());
				values.put(StatusConstract.Column.USER, status.getUser());
				values.put(StatusConstract.Column.MESSAGE, status.getMessage());
				values.put(StatusConstract.Column.CREATED_AT, status.getCreatedAt().getTime());
				db.insertWithOnConflict(StatusConstract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to fetch the timeline", e);
			e.printStackTrace();
		}
		return ;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}
}
