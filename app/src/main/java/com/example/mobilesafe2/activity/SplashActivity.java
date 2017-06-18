package com.example.mobilesafe2.activity;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.mobilesafe2.R;
import com.example.mobilesafe2.R.layout;
import com.example.mobilesafe2.bean.JsonBean;
import com.example.mobilesafe2.utils.Constant;
import com.example.mobilesafe2.utils.HttpUtil;
import com.example.mobilesafe2.utils.PackageUtil;
import com.example.mobilesafe2.utils.SpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private static final int REQUEST_INSTALL = 1;
	private TextView tv_version;
	private JsonBean jsonBean;

	private ProgressDialog pd;
	private Handler mhHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_splash);

		initView();
		initData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	private void initView() {
		tv_version = (TextView) findViewById(R.id.tv_version);
	}
	private void initData() {

		String versionName = PackageUtil.getVersionName(SplashActivity.this,
				getPackageName());
		tv_version.setText(versionName);

		// 访问服务器数据


		boolean flag = SpUtil.getBoolean(SplashActivity.this, Constant.KEY_AUTO_UPDATE, true);
		if(flag){
			autoUpdate();
		}else{
			enterHome();
		}


	}

	private void autoUpdate() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Response response = HttpUtil
							.httpGet("http://10.0.2.2:8080/update21.txt");
					String result = response.body().string();

					try {
						JSONObject jsonObject = new JSONObject(result);
						jsonBean = new JsonBean();
						jsonBean.remoteVersion = jsonObject.getInt("remoteVersion");
						jsonBean.desc = jsonObject.getString("desc");
						jsonBean.apkUrl = jsonObject.getString("apkUrl");

						int versionCode = PackageUtil.getVersionCode(SplashActivity.this, getPackageName());

						if(versionCode<jsonBean.remoteVersion){
							updateDialog();
						}else {
							enterHome();
						}

					} catch (JSONException e) {
						// 进入主界面
						enterHome();
						e.printStackTrace();
					}


				} catch (IOException e) {
					// 进入主界面
					enterHome();
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void enterHome() {
		mhHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						HomeActivity.class);
				startActivity(intent);
				finish();
			}
		}, 2000);
	};

	protected void updateDialog() {
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
				builder.setTitle("版本更新提示");
				builder.setMessage(jsonBean.desc);
				builder.setPositiveButton("现在升级", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// 开始下载任务
						startDownloadTask();

						pd = new ProgressDialog(SplashActivity.this);
						pd.setTitle("下载进度");
						pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						pd.show();

					}
				});

				builder.setNegativeButton("稍后再说", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 进入主界面
						enterHome();
					}
				});

				AlertDialog alertDialog = builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();
				
				
			}
		});

		
		
		
		
	}

	protected void installApk(File apkFile) {

		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(apkFile),
				"application/vnd.android.package-archive");

		startActivityForResult(intent, REQUEST_INSTALL);
	}



	protected void startDownloadTask() {

		new Thread(new Runnable() {

			private InputStream is = null;
			private FileOutputStream fos = null;

			@Override
			public void run() {
				try {
					Response response = HttpUtil.httpGet(jsonBean.apkUrl);

					long max = response.body().contentLength();
					pd.setMax((int) max);

					is  = response.body().byteStream();

					File file = new File(
							Environment.getExternalStorageDirectory(),
							"MobileSafe21.apk");

					fos  = new FileOutputStream(file);

					byte[] buffer = new byte[1024];
					int len;
					int progress = 0;
					while ((len = is .read(buffer)) != -1) {
						fos.write(buffer, 0, len);

						progress += len;

						pd.setProgress(progress);

					}

					fos .flush();

					pd.dismiss();

					installApk(file);

				} catch (IOException e) {
					enterHome();
					e.printStackTrace();
				}finally{
					closeIos(is,fos);
				}
			}
		}).start();

	}

	protected void closeIos(Closeable...io) {
		
		if(io!=null){
			for (int i = 0; i < io.length; i++) {
				Closeable closeable = io[i];
				if(closeable!=null){
					try {
						closeable.close();
					} catch (IOException e) {
						
						enterHome();
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_INSTALL) {
			switch (resultCode) {
				case RESULT_OK:
					System.out.println("RESULT_OK");
					break;
				case RESULT_CANCELED:
					System.out.println("RESULT_CANCELED");
					// 进入主界面
					enterHome();
					break;
				default:
					break;
			}
		}
	}

}
