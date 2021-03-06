package com.example.myweather.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.myweather.db.CoolWeatherDB;
import com.example.myweather.model.City;
import com.example.myweather.model.County;
import com.example.myweather.model.Province;

public class Utility {
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvince=response.split(",");
			if(allProvince!=null && allProvince.length>0){
				for(String p:allProvince){
					String[] array=p.split("\\|");
					Province province=new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
		if(!TextUtils.isEmpty(response)){
			
			String[] allCities=response.split(",");
			if(allCities!=null && allCities.length>0){
				for(String c: allCities){
					String[] array=c.split("\\|");
					City city=new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceID(provinceId);
					coolWeatherDB.saveCity(city);
					Log.d("handleCities", city.getCityName());
				}
				return true;
			}
		}
		return false;
	}
	public static boolean handleCoutiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties=response.split(",");
			if(allCounties!=null && allCounties.length>0){
				for(String c:allCounties){
					String[] array = c.split("\\|");
					County county=new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	public static void handleWeatherResponse(Context context,String response){
		try {
			JSONObject jsonObject=new JSONObject(response);
			JSONObject weatherinfo=jsonObject.getJSONObject("weatherinfo");
			String cityName=weatherinfo.getString("city");
			String weatherCode=weatherinfo.getString("cityid");
			String temp1=weatherinfo.getString("temp1");
			String temp2=weatherinfo.getString("temp2");
			String weatherDesp=weatherinfo.getString("weather");
			String publishTime=weatherinfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void saveWeatherInfo(Context context,String cityName,String weatherCode,String temp1,String temp2,String weatherDesp,String publishTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy��M��d��",Locale.CHINA);
		SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_data", sdf.format(new Date()));
		editor.commit();
	}
}
