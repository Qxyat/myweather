package com.example.myweather.util;

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
}
