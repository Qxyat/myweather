package com.example.myweather.db;

import java.util.ArrayList;
import java.util.List;

import com.example.myweather.model.City;
import com.example.myweather.model.County;
import com.example.myweather.model.Province;

import android.R.integer;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

public class CoolWeatherDB {
	public static final String DB_NAME="cool_weather";
	public static final int VERSION=1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbhelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbhelper.getWritableDatabase();
	}
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB ==null)
			coolWeatherDB=new CoolWeatherDB(context);
		return coolWeatherDB;
	}
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	public void saveCity(City city){
		if(city!=null){
			ContentValues contentValues=new ContentValues();
			contentValues.put("city_name", city.getCityName());
			contentValues.put("city_cod", city.getCityCode());
			contentValues.put("province_id", city.getProvinceID());
			db.insert("City", null, contentValues);
		}
	}
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceID(provinceId);
			}while(cursor.moveToNext());
		}
		return list;
	}
	public void saveCounty(County county){
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	public List<County> loadCounties(int cityId){
		List<County> list=new ArrayList<County>();				
		Cursor cursor=db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
