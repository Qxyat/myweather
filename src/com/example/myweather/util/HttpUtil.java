package com.example.myweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in=connection.getInputStream();
					BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
					StringBuilder stringBuilder=new StringBuilder();
					String line;
					while((line=bufferedReader.readLine())!=null){
						stringBuilder.append(line);
					}
					Log.d("httputil",stringBuilder.toString());
					if(listener!=null)
						listener.onFinish(stringBuilder.toString());
				} catch (Exception e) {
					// TODO: handle exception
					if(listener!=null){
						listener.onError(e);
					}
				}finally{
					if(connection!=null){
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}
}
