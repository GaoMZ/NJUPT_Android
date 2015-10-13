package com.stitp.android.useraction;

import com.stitp.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FunctionActivity extends Activity implements OnClickListener{
	private ImageButton position;
	private ImageButton find;
	private ImageButton lock;
	private ImageButton protect;
	private ImageButton message;
	private ImageButton history;
	
	private Context mContext;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function);
		position=(ImageButton) findViewById(R.id.position);
		find=(ImageButton) findViewById(R.id.find);
		lock=(ImageButton) findViewById(R.id.lock);
		protect=(ImageButton) findViewById(R.id.protect);
		message=(ImageButton) findViewById(R.id.message);
		history=(ImageButton) findViewById(R.id.history);
		
		initButton();
	}
	private void initButton() {
		// TODO Auto-generated method stub
		 findViewById(R.id.position).setOnClickListener(this);
		 findViewById(R.id.find).setOnClickListener(this);		
		 findViewById(R.id.lock).setOnClickListener(this);
		 findViewById(R.id.protect).setOnClickListener(this);
		 findViewById(R.id.message).setOnClickListener(this);
		 findViewById(R.id.history).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.position:
			Intent intent=new Intent(this, Location.class);
			startActivity(intent);		
			break;

		case R.id.find:
			
			
			break;
			
			
		case R.id.lock:
			
			
			break;		
		case R.id.protect:
			Intent intent_protect =new Intent(this, ReminderActivity.class);
			startActivity(intent_protect);
					
			break;		
		case R.id.message:
			
			
			break;		
		case R.id.history:
			
			
			break;		
			
		}
		
	}
	
}
