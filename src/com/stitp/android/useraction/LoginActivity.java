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
import com.stitp.android.utils.Code;

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


//��Ϊ��activity��ʵ�ֵ�¼�ġ���android2.3�Ժ�android�涨����activity�����������߳�����һЩ��ʱ�϶��
//��������������Ĳ�������Ҫ�Ǽ���Ӧ�ó���ֹͣ��Ӧ�����⡣
@SuppressLint("ShowToast") public class LoginActivity extends Activity implements OnClickListener{
private Button login;
private	Button register;
private	EditText etusername;
private	EditText etpassword;
private	String username;
private	String password;
private	ProgressDialog p;

private String url;
private static String result=null;//�ӷ������˻�ȡjson����

/*
 * (non-Javadoc)
 * @see android.app.Activity#onCreate(android.os.Bundle)
 * ��֤��
 */
private	ImageView vc_image; // ͼƬ
private	Button vc_shuaixi, vc_ok;
private	String getCode = null;
private	EditText vc_code;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		init();
		register.setOnClickListener((OnClickListener) new RegisterOnclick());
		login.setOnClickListener(new LoginOnclick());
	}
	private void init() 
	{
		etusername=(EditText) findViewById(R.id.etusername);
		etpassword=(EditText) findViewById(R.id.etpassword);
		login=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		p=new ProgressDialog(LoginActivity.this);
		p.setTitle("��¼��");
		p.setMessage("��¼�У����Ͼͺ�");
		
		/*
		 * �����֤��
		 */
		vc_image = (ImageView) findViewById(R.id.vc_image);
		vc_image.setImageBitmap(com.stitp.android.utils.Code.getInstance().getBitmap());
		vc_code = (EditText) findViewById(R.id.vc_code);
		getCode = com.stitp.android.utils.Code.getInstance().getCode(); // ��ȡ��ʾ����֤��
		vc_shuaixi = (Button) findViewById(R.id.vc_shuaixi);
		vc_shuaixi.setOnClickListener(this);
	//	vc_ok = (Button) findViewById(R.id.vc_ok);
		//vc_ok.setOnClickListener(this);
		
	}
	private class RegisterOnclick implements OnClickListener
	{
		public void onClick(View v) {
			Intent intent=new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
		}

	}
	private class LoginOnclick implements OnClickListener
	{
		public void onClick(View arg0) {
			
			int IsRight=0;
			
			String v_code = vc_code.getText().toString().trim();
			if (v_code == null || v_code.equals("")) {
				Toast.makeText(LoginActivity.this, "��֤��Ϊ��", 2).show();
			} else if (!v_code.equals(getCode)) {
				Toast.makeText(LoginActivity.this, "��֤�����", 2).show();
			} else {
				Toast.makeText(LoginActivity.this, "��֤�ɹ�", 2).show();
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
			p.show();
			new Thread(new Runnable() {

				public void run() {
					url="http://10.211.129.191:8080/NJUPT_STITP_Server/user/login?user.username="
							+username/*.getText()*/.toString()+"&user.password="+password/*.getText()*/.toString();
					String str=doHttpClientGet();
					int i=getRegistResult(str);
					Message msg=new Message();
					msg.obj=i;
					handler.sendMessage(msg);
				}
			}).start();
			IsRight=0;
			}

		}
	}	
	@SuppressLint("HandlerLeak") Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			int i=(Integer) msg.obj;
			String string = null;
			if(i==0){
				string="��¼�ɹ�!";
				Toast.makeText(LoginActivity.this, string, Toast.LENGTH_LONG).show();
				Intent intent_login=new Intent(LoginActivity.this,FunctionActivity.class);
				startActivity(intent_login);
				
			}else if(i==1){
				string="��½ʧ�ܣ��û������������";
				Toast.makeText(LoginActivity.this, string, Toast.LENGTH_LONG).show();
			}else if(i==2){
				string="��½ʧ�ܣ��û���������";	
				Toast.makeText(LoginActivity.this, string, Toast.LENGTH_LONG).show();
			}
			p.dismiss();
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
	
	public  int getRegistResult(String jsonString){
		new JSONObject();
		JSONObject resultCode = JSONObject.fromString(jsonString);
		return resultCode.getInt("result_code");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.vc_shuaixi:
			vc_image.setImageBitmap(Code.getInstance().getBitmap());
			getCode = Code.getInstance().getCode();
			break;		
		}
	}
	
}
