package com.example.dacF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.widget.RadioGroup.OnCheckedChangeListener;
import ucal.example.dacF.R;
import android.app.Activity;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
 
public class Survey extends Activity implements OnClickListener{
	public static String result1="";
	RadioGroup rg[]=new RadioGroup[7];
	int selection[]=new int[7];
	String data;
	char indexalp[]={'a','b','c','d','e'};
 String question[]={"Familiarity with Smartphone-","Interested in new technology?", "Age-","This authentication system is not hard to understand-",
		 "It is easy to use-",	" All functionality of the system are ok-","Time required for verification is acceptable (compatr to others second factor authentication)-"};
 String pques[][] = { {"Basic", "Average", "Advanced User"}, {"Yes  ","No"},
		 {"<10  ","10-29  ","30-49  ", "50 <="},{"Yes  ","No"},
		 {"Yes  ","No"}, {"Yes  ","No"},{"Yes  ","No"}};
 
 //String name= getIntent().getExtras().getString("nm");
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.survey);
  View SubmitButton = this.findViewById(R.id.submitbutton);
  SubmitButton.setOnClickListener(this);
//  View SkipButton = this.findViewById(R.id.skip);
//  SkipButton.setOnClickListener(this);
 
  LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.linear1);
  int id=0;
  for (int k = 1; k <= 3; k++) {
   //create text button
   TextView title = new TextView(this);
   title.setText("Q" + k+": "+question[k-1]);
   title.setTextColor(Color.GREEN);
   mLinearLayout.addView(title);
   // create radio button
   final RadioButton[] rb = new RadioButton[8];
   rg[k-1] = new RadioGroup(this);
   if(k==2){
   rg[k-1].setOrientation(RadioGroup.HORIZONTAL);
   }else{
	   rg[k-1].setOrientation(RadioGroup.VERTICAL);   
   }
   int l=pques[k-1].length;
   for (int i = 0; i < l; i++) {
    rb[i] = new RadioButton(this);
    rg[k-1].addView(rb[i]);
    rb[i].setText(pques[k-1][i]);
    //rb[i].setId(id);
    //id++;
 
   }
   mLinearLayout.addView(rg[k-1]);
   
  }
  TextView title1 = new TextView(this);
  title1.setText("Q" + 4+": "+"Usability?");
  title1.setTextColor(Color.GREEN);
  mLinearLayout.addView(title1);
  //TextView title2 = new TextView(this);
  //title2.setText("(5=Strongly Agree, 4=Agree, 3= No Opinion, 2=Disagree, 1=Strongly Disagree)");
  //title2.setTextColor(Color.YELLOW);
  //mLinearLayout.addView(title2);
  
  for (int k1 = 4; k1 <= 7; k1++) {
	   //create text button
	  TextView tt = new TextView(this);
	  tt.setText("");
	  tt.setTextColor(Color.GREEN);
	  mLinearLayout.addView(tt);
	  
	   TextView title = new TextView(this);
	   title.setText("  "+indexalp[k1-4]+"): "+question[k1-1]);
	   title.setTextColor(Color.CYAN);
	   mLinearLayout.addView(title);
	   // create radio button
	   final RadioButton[] rb = new RadioButton[8];
	   rg[k1-1] = new RadioGroup(this);
	   rg[k1-1].setOrientation(RadioGroup.HORIZONTAL);
	   int l=pques[k1-1].length;
	   for (int i = 0; i < l; i++) {
	    rb[i] = new RadioButton(this);
	    rg[k1-1].addView(rb[i]);
	    rb[i].setText(pques[k1-1][i]);
	    //rb[i].setId(id);
	    //id++;
	 
	   }
	   mLinearLayout.addView(rg[k1-1]);
	   
	  }
  for(int i=0;i<=3;i++){
  TextView tt = new TextView(this);
  tt.setText("");
  tt.setTextColor(Color.GREEN);
  mLinearLayout.addView(tt);
  }
  rg[0].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[0].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[0]=1; break;
	  case 1 : selection[0]=2; break;
	  case 2 : selection[0]=3; break;
	  //case 3 : selection[0]=4; break;
	  }}});
  //Log.v("","Hi all"+selection[0]);
  rg[1].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[1].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[1]=1; break;
	  case 1 : selection[1]=2; break;
	  //case 2 : selection[1]=2; break;
	  }}});
  
  rg[2].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[2].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[2]=1; break;
	  case 1 : selection[2]=2; break;
	  case 2 : selection[2]=3; break;
	  case 3 : selection[2]=4; break;
	  }}});
  rg[3].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[3].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[3]=1; break;
	  case 1 : selection[3]=2; break;
	  //case 2 : selection[4]=3; break;
	  //case 3 : selection[4]=4; break;
	  }}});
  rg[4].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[4].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[4]=1; break;
	  case 1 : selection[4]=2; break;
	  }}});
  rg[5].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[5].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[5]=1; break;
	  case 1 : selection[5]=2; break;
	  }}});
  rg[6].setOnCheckedChangeListener(new OnCheckedChangeListener() {
	  @Override
	  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  int pos = rg[6].indexOfChild(findViewById(checkedId));
	  switch (pos) {
	  case 0 : selection[6]=1; break;
	  case 1 : selection[6]=2; break;
	  }}});
  

 }
 public void onClick(View v) {
	// ((RadioGroup)v.getParent()).check(v.getId());
	 //Log.v("","Select:"+v.getId());
	 
	 switch (v.getId()) {
//		case R.id.skip:
//			//for(int i=0;i<5;i++)
//			//Log.v("","All:"+selection);
//			finish();
//			break;
		case R.id.submitbutton:
			
			if(selection[0]==0||selection[1]==0||selection[2]==0||selection[3]==0||selection[4]==0||selection[5]==0||selection[6]==0){
				Toast.makeText(getBaseContext(), "One or more selection is empty.", Toast.LENGTH_LONG).show();
				//data=""+selection[0]+selection[1]+selection[2]+selection[3]+selection[4]+selection[5]+selection[6]+selection[7]+selection[8]+selection[9];
				//Toast.makeText(getBaseContext(), "Data:"+data, Toast.LENGTH_LONG).show();
				return;
			}
			data=""+selection[0]+selection[1]+selection[2]+selection[3]+selection[4]+selection[5]+selection[6];
			if(isConnected()){
			  String URL="http://dac.cpsc.ucalgary.ca/survey.php?user="+data;
   	          new HttpAsyncTask().execute(URL);
			}
			finish();
			
			break;	
			
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
     BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
     String line = "";
    String result = "";
     while((line = bufferedReader.readLine()) != null)
         result += line;

     inputStream.close();
     return result;

 }

 public boolean isConnected(){
     ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
         if (networkInfo != null && networkInfo.isConnected()) 
             return true;
         else
             return false;   
 }
 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
     @Override
     protected String doInBackground(String... urls) {

         return GET(urls[0]);
     }
     // onPostExecute displays the results of the AsyncTask.
     @Override
     protected void onPostExecute(String result) {
    	  result1=result;
         Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
         //etResponse.setText(result);
    }
 }
}
///Source  http://www.androidhub4you.com/2013/04/dynamic-radio-button-demo-in-android.html