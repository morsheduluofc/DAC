package com.example.dacF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import ucal.example.dacF.R;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import org.apache.http.client.ClientProtocolException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.example.dacF.Level1u;
import com.example.dacF.Level2;


public class Confirm extends Activity implements OnClickListener{
    EditText editTextName;
    public static String name="";
    public static int radi;
    public static String result1=""; 
    String uploadFileName;
    public static String fname="";
    int nameCheck=0,stage=0;
    public static int notify=0;
    private RadioGroup radioGroup;
    Intent i1,i2;
    
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        this.setFinishOnTouchOutside(false);
        View okButton = this.findViewById(R.id.okbutton);
        okButton.setOnClickListener(this); 
        View cancelButton = this.findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(this);
        radi=1;
       // radi= getIntent().getExtras().getInt("radi");
        editTextName = (EditText) findViewById(R.id.editTextName); 
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
  	    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      
  		@Override
  		public void onCheckedChanged(RadioGroup group, int checkedId) {
  			if(checkedId == R.id.radioButton1) {
  				radi=1;
  				Toast.makeText(getApplicationContext(), "Complete 10 rounds challenge-response.", 
  						Toast.LENGTH_SHORT).show();
  				Drawing.fStore="";
  			    Drawing.store1="";
  				Level2.store2="";
  				Drawing.round=0;
  				Level2.round=0;
  				Drawing.fStore="";
  				Drawing.store1="";
  				Level2.store2="";
  				Confirm.fname="";
  				
  			} else if(checkedId == R.id.radioButton2) {
  				radi=2;
  				Toast.makeText(getApplicationContext(), "Complete 5 rounds challenge-response.", 
  						Toast.LENGTH_SHORT).show();
  				Drawing.fStore="";
  			    Drawing.store1="";
  				Level2.store2="";
  				Drawing.round=0;
  				Level2.round=0;
  				Drawing.fStore="";
  				Drawing.store1="";
  				Level2.store2="";
  				Confirm.fname="";
  			} 
  		}
  		
  	});
      } 	   
        

     public void onClick(View v) {
    	 
    	    switch (v.getId()) {
    	    case R.id.cancelbutton:
    	    	finish();
    	    break;
    	    case R.id.okbutton:
    			if(!isConnected()){
    					Toast.makeText(Confirm.this, "Enable your internet connection.", Toast.LENGTH_SHORT).show();
    			}else if(checkUserName()){
    				nameCheck=0;
    			}else{
    			 finish();
    	    	 
    	    	 i1 = new Intent(this, Drawing. class);
    	    	 i2 = new Intent(this, Confirm. class);
    	    	 String URL="http://dac.cpsc.ucalgary.ca/usercheck.php?user="+name;
 				new HttpAsyncTask().execute(URL);
 				 
    	        }
    	    	break;
    	    	
    		
    	    }
    	    
     }
     
     boolean checkUserName(){
    	    name = editTextName.getText().toString();
    	    name=name.trim();
    		name=name.replaceAll("\\s+", "");
    		
			boolean result2;
			if(radi==1){
	    		fname="registration_"+name;
	    	}else if(radi==2){
	    		fname="verification_"+name;
	    	}
			Date dt = new Date();
	    	int hours = dt.getHours();
	        int minutes = dt.getMinutes();
	        int seconds = dt.getSeconds();
	        //Log.v("","H:"+hours+"M:"+minutes+"S:"+seconds);
	        String time="H"+hours+"M"+minutes+"S"+seconds;
	        fname=fname+"_"+time;
	        
			uploadFileName=android.os.Build.MODEL;
			uploadFileName=uploadFileName.replace('-', '_');
			fname=fname+"_"+uploadFileName;
			fname=fname.trim();
    		fname=fname.replaceAll("\\s+", "");
    		
			boolean vcheck=validation(fname);
			
			if(!vcheck){
				Toast.makeText(Confirm.this, "Not a valid Name. Try again.", Toast.LENGTH_SHORT).show();
				editTextName.setText("");
				fname="";
				nameCheck=0;
				//startActivity(i2);
               result2= true;         
              }
               else{
            	 result2=false;
               }
//				String URL="http://dac.cpsc.ucalgary.ca/usercheck.php?user="+name;
//				new HttpAsyncTask().execute(URL);
//         }
              return result2;
     }
     
     private class HttpAsyncTask extends AsyncTask<String, Void, String> {
         @Override
         protected String doInBackground(String... urls) {
  
             return GET(urls[0]);
         }
         
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result) {
        	 //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        	 if(result.compareTo("User not exist")==0 && radi==1){
        	 //result="Valid Name";
        	  nameCheck=1;
        	  notify=1;
        	   i1.putExtra("radi",radi);
				startActivity(i1);
				Log.v("", "radi:"+radi+" "+result);
        	 } else if(result.compareTo("User already exist")==0 && radi==2){
            	// result="Valid Name";
        		 i1.putExtra("radi",radi);
 				 startActivity(i1);
            	 nameCheck=1;
            	 notify=1;
            	 Log.v("", "radi:"+radi+" "+result);
            	 }
        	 else{
        		 result="Invalid name. Try again.";
        		 startActivity(i2);
        		// editTextName.setText("");
        		 Log.v("", "radi:"+radi+" "+result);
        		 //radi=0;
        		 fname="";
        		 nameCheck=0;
        		 notify=1;
        		 Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
        		 
        	 }      
        }
         
     }
     
     
     public static String GET(String url){
         InputStream inputStream = null;
         String result = "";
         try {
  
             // create HttpClient
             HttpClient httpclient = new DefaultHttpClient();
  
             // make GET request to the given URL
             HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
  
             // receive response as inputStream
             inputStream = httpResponse.getEntity().getContent();
  
             // convert inputstream to string
             if(inputStream != null)
                 result = convertInputStreamToString(inputStream);
             else
                 result = "Did not work!";
  
         } catch (Exception e) {
             Log.d("InputStream", e.getLocalizedMessage());
         }
  
         return result;
     }
     
     private static String convertInputStreamToString(InputStream inputStream) throws IOException{
    	 
         String line = "";
         String result = "";
         BufferedReader bufferedReader=null;
         try{
             bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
         while((line = bufferedReader.readLine()) != null)
             result += line;
  
         
    	 }catch(IOException e){}
    	 finally{
    		 inputStream.close();
    	       //validation
    	     bufferedReader.close();
    	     
    	 }
    	 return result;
     }
     
     
     
     private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
    	 
 		@Override
 		protected Double doInBackground(String... params) {
 			// TODO Auto-generated method stub
 			postData(params[0]);
 			return null;
 		}
  
 		protected void onPostExecute(Double result){
 			//pb.setVisibility(View.GONE);
 			Toast.makeText(getApplicationContext(), "Send Data.", Toast.LENGTH_LONG).show();
 		}
 		protected void onProgressUpdate(Integer... progress){
 			//pb.setProgress(progress[0]);
 		}
  
 		public void postData(String valueIWantToSend) {
 			// Create a new HttpClient and Post Header
 			String message="";
 			message=Level1u.fStore;
 			message=message+Level1u.store1;
 			//message=message+Level2.store2;
 			Level1u.fStore="";
 			Level1u.store1="";
 			Level2.store2="";
 			HttpClient httpclient = new DefaultHttpClient();
 			HttpPost httppost = new HttpPost("http://dac.cpsc.ucalgary.ca/string1.php");
  
 			try {
 				// Add your data
 				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 				nameValuePairs.add(new BasicNameValuePair("myHttpData", message));
 				nameValuePairs.add(new BasicNameValuePair("myfile", valueIWantToSend));
 				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  
 				// Execute HTTP Post Request
 				HttpResponse response = httpclient.execute(httppost);
  
 			} catch (ClientProtocolException e) {
 				// TODO Auto-generated catch block
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 			}
 		}
  
 	}
     
     
boolean validation(String nme){
	boolean valid=false;
	   for(int i=0;i<name.length();i++){
			String checkMe=String.valueOf(name.charAt(i));
			Pattern pattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789_]*");
         Matcher matcher = pattern.matcher(checkMe);
         valid = matcher.matches();
                 
}
	   return valid;
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
