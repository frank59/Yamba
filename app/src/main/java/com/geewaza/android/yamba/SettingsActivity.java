package com.geewaza.android.yamba;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by wangh on 2015/12/18.
 */
public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			//创建一个片段
			SettingsFragment fragment = new SettingsFragment();
			getFragmentManager().beginTransaction().add(android.R.id.content, fragment
					, fragment.getClass().getSimpleName()).commit();
		}
	}
}
