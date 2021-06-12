package com.example.dacF;

import ucal.example.dacF.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity{
	/** Called when the activity is first created. */
	TextView text;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	 
 /****** Create Thread that will sleep for 2 seconds *************/        
    Thread background = new Thread() {
        public void run() {
             
            try {
                // Thread will sleep for 2 seconds
                sleep(2*1000);
                 
                // After 2 seconds redirect to another intent
                Intent i=new Intent(getBaseContext(),FirstScreen.class);
                startActivity(i);
                 
                //Remove activity
                finish();
                 
            } catch (Exception e) {
             
            }
        }
    };
     
    // start thread
    background.start();
    
    
	}
	
	 @Override
	    protected void onDestroy() {
	         
	        super.onDestroy();
	         
	    }
	}