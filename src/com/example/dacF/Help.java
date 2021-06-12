package com.example.dacF;
import ucal.example.dacF.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
public class Help extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		TextView t2 = (TextView) findViewById(R.id.helptitle);
		t2.setMovementMethod(LinkMovementMethod.getInstance());
		
	}

}
