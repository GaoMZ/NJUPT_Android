package com.stitp.android.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

public class ReminderService extends Service {
	public static final String ACTION = "com.sun.shine.reminder.service.ReminderService";

	long timeInterval = 1000 * 60 * 20;
	boolean isStop = true;
	Timer timer;
	TimerTask task;

	Handler handler;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		System.out.println("onCreate");

		super.onCreate();
		handler = new Handler();
		timer = new Timer();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onBind");
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			int time = bundle.getInt("time");
			if (time > 0) {
				Toast.makeText(this, "在" + time + "分钟后会以振动的形式提醒让眼睛休息!",
						Toast.LENGTH_LONG).show();
				timeInterval = time * 1000 * 60;
			}

		} else {
			Toast.makeText(this, "在20分钟后会以振动的形式提醒让眼睛休息!", Toast.LENGTH_LONG)
					.show();
		}
		System.out.println("onStart");
		if (isStop) {

			if (task == null) {
				task = new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						remind();
					}
				};
			}
			timer.schedule(task, 0, timeInterval);
			isStop = false;
		}

	}

	void remind() {
		Vibrator vibratorm = (Vibrator) getApplication().getSystemService(
				Service.VIBRATOR_SERVICE);

		vibratorm.vibrate(new long[] { 100, 50, 100, 50, 100, 50, 100, 100, 50,
				100, 50, 100, 50, 100, 50, 100, 50, 100, 50, 100, 100, 50, 100,
				50, 100, 50, 100, 100, 50, 100, 50, 100, 50, 100 }, -1);

		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(ReminderService.this, "休息一下啊亲!眼睛会受不了的!", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onUnbind");
		return super.onUnbind(intent);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isStop = true;
		task = null;
		timer.cancel();

		System.out.println("onDestory");
		Toast.makeText(ReminderService.this, "提醒被关闭，您要觉得烦可以把时间延长一点", Toast.LENGTH_LONG)
		.show();
	}
}
