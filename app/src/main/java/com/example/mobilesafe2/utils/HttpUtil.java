package com.example.mobilesafe2.utils;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
	
	public static Response httpGet(String url) throws IOException{
		OkHttpClient okHttpClient_get = new OkHttpClient();
		Request request  = new Request.Builder()
		.url(url)
		.build();
		
		Call call = okHttpClient_get.newCall(request);
		
		Response response = call.execute();
		
		return response;
	}
		
	
}
