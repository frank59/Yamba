package com.geewaza.android.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.IDN;

/**
 * Created by wangh on 2016/1/27.
 */
public class DbHelper extends SQLiteOpenHelper {
	private static final String TAG = DbHelper.class.getSimpleName();
	public DbHelper(Context context) {
		super(context, StatusConstract.DB_NAME, null, StatusConstract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s (" +
				"%s int primary key," +
				"%s text," +
				"%s text," +
				"%s int" +
				")",
				StatusConstract.TABLE,
				StatusConstract.Column.ID,
				StatusConstract.Column.USER,
				StatusConstract.Column.MESSAGE,
				StatusConstract.Column.CREATED_AT);
		Log.d(TAG, "onCreate with SQL:" + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + StatusConstract.TABLE);
		onCreate(db);
	}
}
