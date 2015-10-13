package com.stitp.android.useraction;

import com.stitp.android.R;
import com.stitp.android.service.ReminderService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReminderActivity extends Activity implements OnClickListener{
	private Button start;
	private Button stop;

	private EditText time;
	private Button submit;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.protect_eyes);
		start = (Button) findViewById(R.id.start_service);
		stop = (Button) findViewById(R.id.stop_service);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		time = (EditText) findViewById(R.id.time);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager manage = getWindowManager();
		Display display = manage.getDefaultDisplay();
		display.getMetrics(dm);
		int screenWidth = dm.widthPixels;
		System.out.println(screenWidth);
		System.out.println(dm.heightPixels);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(ReminderService.ACTION);
		if (v == start) {

			startService(intent);
		} else if (v == stop) {
			stopService(intent);
			
		} else if (v == submit) {
			try {
				int number = Integer.parseInt(time.getText().toString());
				stopService(intent);
				intent.putExtra("time", number);
				startService(intent);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(ReminderActivity.this, "输入不能为空",
						Toast.LENGTH_LONG).show();
			}

		}
	}
}
