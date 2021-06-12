package com.example.dacF;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import ucal.example.dacF.R;
import android.content.Intent;
import android.widget.RadioGroup;
import android.app.ProgressDialog;

import com.example.dacF.Level1u;
import com.example.dacF.Level2;


public class FirstScreen extends Activity implements OnClickListener {
	  public static TextView tvIsConnected;
	  int radi=0,level=0;
	  public static boolean llevel1=false,llevel2=false;
	  int serverResponseCode = 0;
	  ProgressDialog dialog = null;
	  int exitclick=0; 
	  String upLoadServerUri = null;
	 
	    
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.firstscreen);
	  tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
	  View level1Button = this.findViewById(R.id.level1_button);
	  level1Button.setOnClickListener(this);
	  View sendButton = this.findViewById(R.id.send_button);
	  sendButton.setOnClickListener(this);
	  View exitButton = this.findViewById(R.id.exit_button);
	  exitButton.setOnClickListener(this);
	  View helpButton = this.findViewById(R.id.help_button);
	  helpButton.setOnClickListener(this);
	  View surveyButton = this.findViewById(R.id.survey_button);
	  surveyButton.setOnClickListener(this);
	  Confirm.radi=0;
	  
	  
}
	
    
	public void onClick(View v) {
		boolean check;
	switch (v.getId()) {
	case R.id.send_button:
		Confirm.radi=0;
		Intent i5 = new Intent(this, Drawing. class);
     	i5.putExtra("radi",radi);
		startActivity(i5);
		break;
	case R.id.level1_button:
		//if(Confirm.radi==0){
			Intent i1 = new Intent(this, Confirm. class);
			//i1.putExtra("radi",radi);
			startActivity(i1);
		//}			
//		if(radi==1||radi==2){	
//			if(Confirm.fname.length()<1){
//				Toast.makeText(FirstScreen.this, "Select a valid name.", Toast.LENGTH_SHORT).show();
//			return;
//			}
//		}
//		if(radi==1||radi==2){
//			level=1;
//			llevel1=true;
//		}
//		Intent i1 = new Intent(this, Level1. class);
//		i1.putExtra("radi",radi);
//		startActivity(i1);
		break;
	case R.id.help_button:
		//Intent i6 = new Intent(this, Help. class);
		//startActivity(i6);
		break;
	case R.id.exit_button:  
		System.exit(0); 
	break;
	case R.id.survey_button:
		 //Intent i7 = new Intent(this, Survey. class);
		 //startActivity(i7);
	break;
		}
	}	
	
	class ThreadB extends Thread{
	    int total;
	    @Override
	    public void run(){
	        synchronized(this){
	            while(Confirm.notify==0){}
	            notify();
	        }
	    }
	}
	
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }
    
 
}
