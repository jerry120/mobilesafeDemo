package com.example.mobilesafe2.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtil {
	// 获取版本名

	public static String getVersionName(Context context, String packageName) {

		PackageManager packageManager = context.getPackageManager();
		String versionName = "";
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			
			versionName = packageInfo.versionName;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return versionName;
	}

	// 获取版本号
	
	public static int getVersionCode(Context context, String packageName) {

		PackageManager packageManager = context.getPackageManager();
		int versionCode = 1;
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			
			versionCode = packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return versionCode;
	}

}
