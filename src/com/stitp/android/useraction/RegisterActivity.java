package com.stitp.android.useraction;

import java.io.IOException;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.stitp.android.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.stitp.android.utils.*;

public class RegisterActivity extends Activity implements OnClickListener {
	private String url;
	private static String result=null;//�ӷ������˻�ȡjson����

	private	EditText etusername;
	private	EditText etpassword;
	private	ProgressDialog dialog;
	private	String username=null;
	private	String password=null;

	private Button registButton;
	private Button return_login;
	
	private	ImageView vc_image; // ͼƬ
	private	Button vc_shuaixi, vc_ok;
	private	String getCode = null;
	private	EditText vc_code;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		registButton.setOnClickListener((OnClickListener)new RegisterOnclick());
		return_login.setOnClickListener((OnClickListener)new Return_Login_Onclick());		
	}
	private void init()
	{
		etusername=(EditText) findViewById(R.id.name);
		etpassword=(EditText) findViewById(R.id.password);
		registButton = (Button) findViewById(R.id.regist);
		return_login = (Button) findViewById(R.id.return_login);	
		dialog=new ProgressDialog(RegisterActivity.this);
		dialog.setTitle("�ϴ�������");
		dialog.setMessage("���Ե�...");
		
		/*
		 * �����֤��
		 */
		vc_image = (ImageView) findViewById(R.id.vc_image);
		vc_image.setImageBitmap(com.stitp.android.utils.Code.getInstance().getBitmap());
		vc_code = (EditText) findViewById(R.id.vc_code);
		getCode = com.stitp.android.utils.Code.getInstance().getCode(); // ��ȡ��ʾ����֤��
		vc_shuaixi = (Button) findViewById(R.id.vc_shuaixi);
		vc_shuaixi.setOnClickListener(this);
	}

	private class Return_Login_Onclick implements OnClickListener
	{
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.setClass(RegisterActivity.this, LoginActivity.class);
			startActivity(intent);
		}

	}

	private class RegisterOnclick implements OnClickListener
	{
	
		public void onClick(View view) {
			int IsRight=0;
			
			String v_code = vc_code.getText().toString().trim();
			if (v_code == null || v_code.equals("")) {
				Toast.makeText(RegisterActivity.this, "��֤��Ϊ��", 2).show();
			} else if (!v_code.equals(getCode)) {
				Toast.makeText(RegisterActivity.this, "��֤�����", 2).show();
			} else {
				Toast.makeText(RegisterActivity.this, "��֤�ɹ�", 2).show();
				IsRight=1;
			}	
			
			if(IsRight==1){
				
		
			username=etusername.getText().toString().trim();
			if (username==null||username.length()<=0) 
			{		
				etusername.requestFocus();
				etusername.setError("�Բ����û�������Ϊ��");
				return;
			}
			else 
			{
				username=etusername.getText().toString().trim();
			}
			password=etpassword.getText().toString().trim();
			if (password==null||password.length()<=0) 
			{		
				etpassword.requestFocus();
				etpassword.setError("�Բ������벻��Ϊ��");
				return;
			}
			else 
			{
				password=etpassword.getText().toString().trim();
			}
			dialog.show();
			new Thread(new Runnable() {

				public void run() {
					url="http://10.211.129.191:8080/NJUPT_STITP_Server/user/register?user.username="
							+username/*.toString()*/+"&user.password="+password/*.toString()*/;
					String str=doHttpClientGet();
					System.out.println("***content----->"+str);
					int i=getRegistResult(str);
					Message msg_register=new Message();
					msg_register.obj=i;
					handler_register.sendMessage(msg_register);
				}
			}).start();
			IsRight=0;
			}
		}
	}	
	
	Handler handler_register=new Handler()
	{
		public void handleMessage(Message msg) {
			int i=(Integer) msg.obj;
			String string = null;
			if(i==0){
				string="ע��ɹ�,��ʼ��¼....!";
				Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_LONG).show();
				Intent intent=new Intent(RegisterActivity.this,FunctionActivity.class);
				startActivity(intent);
			}else if(i==1){
				string="ע��ʧ�ܣ��û����Ѵ���";	
				Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_LONG).show();
			}
			dialog.dismiss();
			super.handleMessage(msg);
		}	
	};
	
	private  String doHttpClientGet() {
		// TODO Auto-generated method stub
		
		HttpGet httpGet=new HttpGet(url);
		HttpClient client=new DefaultHttpClient();
		try {
			HttpResponse response=client.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				result=EntityUtils.toString(response.getEntity());	
				}
				
				System.out.println("content----->"+result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	
	
	public  int getRegistResult(String jsonString){
		
		JSONObject resultCode = new JSONObject().fromString(jsonString);
		return resultCode.getInt("result_code");
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * ��֤��
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.vc_shuaixi:
			vc_image.setImageBitmap(Code.getInstance().getBitmap());
			getCode = Code.getInstance().getCode();
			break;		
		}
	}

}