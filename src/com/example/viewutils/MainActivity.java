package com.example.viewutils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Deprecated
	@ViewInject(R.id.tv1)
	private TextView xxxxxxHelloxxxx;
	
	private boolean isTrue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		Log.d("tag", "textView="+xxxxxxHelloxxxx.getText());
	}
	@Deprecated
	@OnClick(R.id.btn1)
	private void clickeMe(View view){
		Toast.makeText(this, "我被点了", Toast.LENGTH_SHORT).show();
	}
	

}
