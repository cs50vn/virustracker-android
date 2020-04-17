package com.cs50vn.virustracker.app.tracking;

import android.util.Log;

public class PLog {
	public static final String MAIN_TAG = "virustracker";

	public static void WriteLog(String tag, Object content) {
		Log.i(tag, content.toString());
	}
}