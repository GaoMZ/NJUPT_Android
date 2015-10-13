package com.stitp.android.useraction;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.stitp.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Location extends Activity implements OnClickListener {

	private ImageView backButton;
	private ImageView navigaButton;

	private MapView mMapView = null;
	 private BaiduMap mBaiduMap;
	
	//定位相关
	private LocationClient mLocationClient;
	//定位监听器
	private MyLocationListener mLocationListener;
	private boolean isFirstIn=true;
	
	//private Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_location);
		//context=this;
		initView();
		//初始化定位
		initLocation();
		
	}

	private void initLocation() {
		// TODO Auto-generated method stub
		
		//mLocationMode = LocationMode.NORMAL;
		mLocationClient=new LocationClient(this);
		mLocationListener=new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		
		LocationClientOption option=new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap=mMapView.getMap();		

        //设置地图的显示功能,可以改变地图的放大倍数
        MapStatusUpdate msu=MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        
		backButton=(ImageView) findViewById(R.id.back);
		navigaButton=(ImageView) findViewById(R.id.navigation);
		setOnclick_Button();
	}
	
	private void setOnclick_Button() {
		// TODO Auto-generated method stub
		backButton.findViewById(R.id.back).setOnClickListener(this);
		navigaButton.findViewById(R.id.navigation).setOnClickListener(this);
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//开启定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			Intent intent_back = new Intent(Location.this, FunctionActivity.class);
			startActivity(intent_back);
			break;
		case R.id.navigation:
			Intent intent_navigation = new Intent(Location.this, FunctionActivity.class);
			startActivity(intent_navigation);
			break;

		}

	}
	
	private class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			//定位成功之后的回调
			MyLocationData data=new MyLocationData.Builder()//
			.accuracy(location.getRadius())//
			.latitude(location.getLatitude())//
			.longitude(location.getLongitude()).build();
		
			mBaiduMap.setMyLocationData(data);
			
			//MyLocationConfiguration configuration=new MyLocationConfiguration(LocationMode.NORMAL, arg1, arg2)	
			
			
			//拿到用户的位置，判断是否是第一次进入地图，将该位置作为地图显示的中心点
			if (isFirstIn) {
				LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
				
				MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn=false;
				Toast.makeText(Location.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
				
			}
		}	
		
	}

}
