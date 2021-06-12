package com.example.dacF;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ucal.example.dacF.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.dacF.Confirm;
import com.example.dacF.FirstScreen;




public class Drawing extends Activity implements SensorEventListener{

	 private RelativeLayout relativelayout;
	 // Instance variables
	 private Paint mPaint, mBitmapPaint;
	 private MyView mView;
	 private Bitmap mBitmap;
	 private Canvas mCanvas;
	 private Path mPath;
	 private Button ClearPaint,refreshButton, clearallButton, backButton;
	 TextView text,texts,textc;
	 ImageView imageView;
	 private VelocityTracker vTracker = null; //For velocity calculation
	 private SensorManager sensorManager = null;
	 Point2D polygon= new Point2D();
	 
	 double  pressure,eventsize,pres,events;
	 int row,intersection=0,drawenable=1,radi=0,noofPixel,initial=1,totalRound=0,practice=0;
	 public static int round=0;
	 boolean threeCircle=false,finishDraw1=false,finishDraw2=false,finishDraw3=false,flashedCircle=true;
	 boolean missflashed=false,notmissflashed=false;
	 double avgXVelocity,XVelocity, YVelocity,totaVelocity;
	 double downx = 0, downy = 0, upx = 0, upy = 0;
	 float level,density,hpixel, wpixel;
	 public int timeConstrain[]= new int[100];
	 double tengent1,tengent2,angeloffset;
	 double fRadious=0,radiousDiff=0,centerDiff=0,startDiff=0;
	 double timeStart,timeEnd,timeDiff,reactStart,reactEnd,reactDiff=0,gtimeStart,gtimeEnd,gTime;
	 double finalleftx,finaltopy,finalrightx,finalbottomy;
	 int timeindication=0, stindication=0,ctimes=0,constrain=0,errdist=64,estarting=32,etime=16,eradius=8,ecenter=4,evelocity=2,ereaction=1;
	 float orinX,orinY,orinZ,sorinX,sorinY,sorinZ,saveorientationX,saveorientationY,saveorientationZ;
	 int suggestX1=0,suggestX2=0,suggestX3=0,suggestXf=0,suggestY1=0,suggestY2=0,
				suggestY3=0,suggestYf=0,suggestradious1=0,suggestradious2=0,suggestradious3=0,suggestradiousf=0,
				centerX1,centerX2,centerX3,centerXf,centerY1,centerY2,centerY3,centerYf,ang1,ang2,ang3;
	 String out="Draw all suggested circles (S: Starting point)";
	 String outs="";
	    public double points[][]= new double[2048][2];
	    public double vpoints[][]= new double[2048][2];
	    public double orientationx[]= new double[2048];
	    public double orientationy[]= new double[2048];
	    public double orientationz[]= new double[2048];
	    public double Pressure[]= new double[2048];
	    public double EventSize[]= new double[2048];
	    
	    double fdistance[]=new double[2048];
	    double velocity[]=new double[2048];
	    double orientation[]=new double[2048];
	    double PRessure[]=new double[2048];
	    double EVentSize[]=new double[2048];
	    double alldata[]=new double[2048];
	    double combinedata[]=new double[4096];
	    public static String store1="", output;
	    AlertDialog alertDialog, alertDialog1,alertDialog2;
	    final Context context = this;
	    public static String fStore="";
	    Paint mcPaint=new Paint();
	    Paint SPaint=new Paint();
	    Paint CPaint=new Paint();
	    Paint PSPaint=new Paint();
	    Paint PCPaint=new Paint();
	    
	    
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.testl1);
	  
	  relativelayout = (RelativeLayout) findViewById(R.id.item);
	  relativelayout.setBackgroundColor(Color.WHITE);
	  refreshButton = (Button) findViewById(R.id.refresh_button);
//	  clearallButton = (Button) findViewById(R.id.clearall_button);
	  backButton = (Button) findViewById(R.id.back_button);
	  text = (TextView) findViewById(R.id.textView1);
	  texts = (TextView) findViewById(R.id.textView2);
	  textc = (TextView) findViewById(R.id.myImageView);
	  radi= getIntent().getExtras().getInt("radi");
	  sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	  gtimeStart=System.currentTimeMillis();
	  
	    SPaint.setTextSize(50);
	    SPaint.setColor(Color.BLACK);
	    PSPaint.setColor(Color.BLACK);
	    CPaint.setTextSize(50);
	    CPaint.setColor(Color.RED);
	    PCPaint.setColor(Color.RED);
	    if(radi==0){this.setTitle("DAC (Practice Mode)");}
	    else if (radi==1){this.setTitle("DAC (Registration Phase)");}
	    else {this.setTitle("DAC (Verification Phase)");}
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Done all challenges.");
		// set dialog message
		alertDialogBuilder
			.setMessage("Tap Yes to go home...")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					
				//-------------Game time---------------------//
				  	String gdata=Confirm.name+":"+gTime+":"+radi;
				  	String URL1="http://dac.cpsc.ucalgary.ca/gametime.php?user="+gdata;
		   	        new HttpAsyncTask1().execute(URL1);
		   	        
				//-------------End Game time---------------------//
					
					if(radi!=0){
					  CombineAllFeatures();
	    			  new MyAsyncTask().execute(Confirm.fname);
//	    			  
//	    			  /****** Create Thread that will sleep for 2 seconds *************/        
//	    			    Thread background = new Thread() {
//	    			        public void run() {
//	    			             
//	    			            try {
//	    			                // Thread will sleep for 2 seconds
//	    			                sleep(2*1000);
//	    			                 
//	    			                // After 2 seconds redirect to another intent
//	    			                Intent i=new Intent(getBaseContext(),FirstScreen.class);
//	    			                startActivity(i);
//	    			                 
//	    			                //Remove activity
//	    			                finish();
//	    			                 
//	    			            } catch (Exception e) {
//	    			             
//	    			            }
//	    			        }
//	    			    };
//	    			     
//	    			    // start thread
//	    			    background.start();
//	    			    
//	    			    
	   
					
	    			  
	    			  if(radi==2){
	    				  String URL="http://dac.cpsc.ucalgary.ca/ScoreCheck.php?user="+Confirm.name;
	    				new HttpAsyncTask().execute(URL);
	    			  }
	    			  Confirm.fname="";
					}
					round=0;
					finish();
				}
			  });
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		
		AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);

			// set title
			alertDialogBuilder1.setTitle("Start from begining?");

			// set dialog message
			alertDialogBuilder1
				.setMessage("Tap Yes to start from round 1...")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						ClearAllData();
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});

				// create alert dialog
				alertDialog1 = alertDialogBuilder1.create();
		
				AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(context);

				// set title
				alertDialogBuilder2.setTitle("Stop playing?");

				// set dialog message
				alertDialogBuilder2
					.setMessage("Tap Yes to go home...")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							ClearAllData();
							Confirm.fname="";
							round=0;
							finish();
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});

					// create alert dialog
					alertDialog2 = alertDialogBuilder2.create();
					
	    
	    
	  
//	    clearallButton.setOnClickListener(new OnClickListener() {
//		   public void onClick(View v) {
//			   if(radi==1 ||radi==2){
//					 alertDialog1.show();
//				 }else{
//			  	 ClearAllData();
//				 }
//		   }
//		  });
					
	//temporarily remove the refresh button				
	    refreshButton.setOnClickListener(new OnClickListener() {
			   public void onClick(View v) {
				   if(threeCircle){
				   mBitmap.eraseColor(Color.WHITE);
				    mPath.reset();
				    mView.invalidate();
				  
						  if(radi==1){
								outs="Rounds: "+(round/4+1)+"/10";
								}else
								if(radi==2){
								outs="Rounds: "+(round/4+1)+"/5";
								}
								else{
								outs="Rounds:"+(round/4+1)+"/10";	
								}
						  texts.setText(outs);
						  tryaFlasedCirlce(); 
						  
					  }
//				   else{
//						  randomCircle();
//					//tryagain();
//					  }
			   }
			  });
	    backButton.setOnClickListener(new OnClickListener() {
			   public void onClick(View v) {
				   if(radi==1 && round < 40 && practice==0){
				  		//Toast.makeText(Level1.this, "Complete minimum 10 rounds", Toast.LENGTH_SHORT).show();
				  		alertDialog2.show();
						return;
						}
				  	if(radi==2 && round< 20){
				  		alertDialog2.show();
				  		//Toast.makeText(Level1.this, "Complete minimum 5 rounds", Toast.LENGTH_SHORT).show();
						return;
				  		 }
				  	if(radi==0){
				  	Date dt = new Date();
				    int hours = dt.getHours();
				    int minutes = dt.getMinutes();
				    int seconds = dt.getSeconds();
				        //Log.v("","H:"+hours+"M:"+minutes+"S:"+seconds);
				    String dtime="H"+hours+"M"+minutes+"S"+seconds;
				    String uploadFileName=android.os.Build.MODEL;
					uploadFileName=uploadFileName.replace('-', '_');
					String pname=uploadFileName+dtime;
					pname=pname.trim();
		    		pname=pname.replaceAll("\\s+", "");
		    		
				  	String pdata=pname+":"+round;
				  	round=0;
				  	String URL="http://dac.cpsc.ucalgary.ca/practice.php?user="+pdata;
		   	        new HttpAsyncTask1().execute(URL);
		   	        
				  	} 
				  	  finish();
			   }
			  });
	    
	    
	  DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();
	  int w = metrics.widthPixels;
	  int h = metrics.heightPixels;

	  System.out.println(" width " + w);
	  System.out.println(" height " + h);

	  mView = new MyView(this, w, h);
	  mView.setDrawingCacheEnabled(true);

	  mPaint = new Paint();
	  paintSetup();
	  
	  relativelayout.addView(mView);
	  
	  if(radi==2){outs="Rounds: 1/5";}
      else{outs="Rounds: 1/10";}
	  randomCircle();
	  
	  
	  paintSetup();
	  //mCanvas.drawCircle(100, 100, 100, mPaint);
	 }
	 
    void paintSetup(){
	  mPaint.setAntiAlias(true);
	  mPaint.setDither(true);
	  mPaint.setColor(Color.BLUE);
	  mPaint.setStyle(Paint.Style.STROKE);
	  mPaint.setStrokeJoin(Paint.Join.ROUND);
	  mPaint.setStrokeCap(Paint.Cap.ROUND);
	  mPaint.setStrokeWidth(6);
      }
	 
	 /*............... Pinting view ..................................*/

	 public class MyView extends View {
	  public MyView(Context c, int w, int h) {
	   super(c);
	   mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	   mBitmap.eraseColor(Color.WHITE);
	   mCanvas = new Canvas(mBitmap);
	   mPath = new Path();
	   mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	   mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
	  }

	  @Override
	  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	   super.onSizeChanged(w, h, oldw, oldh);
	  }

	  @Override
	  protected void onDraw(Canvas canvas) {
	   canvas.drawColor(Color.WHITE);
	   canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
	   canvas.drawPath(mPath, mPaint);
	  }

	  /*-----------touching events for painting-------------*/
	  private float mX, mY;
	  private static final float TOUCH_TOLERANCE = 5;

	  private void touch_start(float x, float y) {
	   mPath.reset();
	   mPath.moveTo(x, y);
	   mX = x;
	   mY = y;
	  }

	  private void touch_move(float x, float y) {
	   float dx = Math.abs(x - mX);
	   float dy = Math.abs(y - mY);
	   if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
	    mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
	    mX = x;
	    mY = y;
	   }
	  }

	  private void touch_up() {
	   mPath.lineTo(mX, mY);
	   // commit the path to our offscreen
	   mCanvas.drawPath(mPath, mPaint);
	   // kill this so we don't double draw
	   mPath.reset();
	  }

	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
		  paintSetup();
		  int index = event.getActionIndex();
//	      int action = event.getActionMasked();
	      int pointerId = event.getPointerId(index);
		  int totalpoint=1; 
	       if(drawenable==0){
	    	   Toast.makeText(Drawing.this, "Wait!!", Toast.LENGTH_SHORT).show();
	    	   return false;
	       }else{	           	
	   
	   switch (event.getAction()) {
	   case MotionEvent.ACTION_DOWN:
		   float x = event.getX();
		   float y = event.getY();
		   
	    if(threeCircle){
   	     reactEnd=System.currentTimeMillis();
   	 }
       	 timeStart=System.currentTimeMillis();
            /*------------Velocity initialization-----*/
            noofPixel=0;
            if(vTracker == null) {
                vTracker = VelocityTracker.obtain();
            }
            else {
                vTracker.clear();
            }
            vTracker.addMovement(event);
           // vTracker.computeCurrentVelocity(1000);
            /*-------End Velocity------------------*/
           XVelocity=Math.abs(vTracker.getXVelocity(pointerId));
           YVelocity=Math.abs(vTracker.getYVelocity(pointerId));
           touch_start(x, y);
   	       invalidate();
           downx=x;
           downy=y;
           sorinX=orinX;
           sorinY=orinY;
           sorinZ=orinZ;
           pres=event.getPressure(pointerId);
           events=event.getSize(pointerId);
           storeData(downx,downy,XVelocity,YVelocity,sorinX,sorinY,sorinZ,pres,events);
	    break;
	    
	   case MotionEvent.ACTION_MOVE: 
		   
	    int historySize = event.getHistorySize();
    	vTracker.addMovement(event);
        vTracker.computeCurrentVelocity(1000);
         	// a pointer was moved
        for (int i = 0; i < historySize; i++) {
            double x1= event.getHistoricalX(i);
            double y1 = event.getHistoricalY(i);
            XVelocity=Math.abs(vTracker.getXVelocity(pointerId));
            YVelocity=Math.abs(vTracker.getYVelocity(pointerId));
            double avgVelocity=(XVelocity+YVelocity)/2;
            //paint.setStrokeWidth(12);
            //canvas.drawLine((float)downx, (float)downy, (float)x, (float)y, paint); //Draw user circle
            touch_move((float)x1,(float)y1);
    	    invalidate();
    	    
            downx=x1;
            downy=y1;
            sorinX=orinX;
            sorinY=orinY;
            sorinZ=orinZ;
            pres=event.getPressure(pointerId);
            events=event.getSize(pointerId);
            //Log.v("",+downx+" "+downy+" "+Math.abs(vTracker.getXVelocity(pointerIndex))+" "+Math.abs(vTracker.getYVelocity(pointerIndex)));
            storeData(downx,downy,XVelocity,YVelocity,sorinX,sorinY,sorinZ,pres,events);  
          }
	     break;
	     
	   case MotionEvent.ACTION_UP:
	    touch_up();
	    invalidate();
	    if(threeCircle){
        	reactDiff=(reactEnd-reactStart)/100;
        	}else{reactDiff=0;}
        	timeEnd=System.currentTimeMillis();
        	timeDiff=(timeEnd-timeStart)/ 100;
        	validation();	
        	drawAPolygon();  /// Next Start2. 
        	//threeCircle=true;flashedCircle=true;
	    break;
	   }
	   return true;
	  } // end of touch events for image
	  }
	 }// end MyView
	 
	 
	 /*----------------------Random Suggestion----------------------------------------*/ 
	    void randomCircle(){
	    	//text.setText("Draw all suggested circles (S: Starting point)");
	    	text.setText("Draw the circle. Wait till it disappear.");
	    	if(radi==2)
	    	  texts.setText("Rounds: "+(round/4+1)+"/5");
	    	else
	    	 texts.setText("Rounds: "+(round/4+1)+"/10");	
	    	//mPaint.setColor(Color.parseColor("#F9F9F9"));
	      	//mPaint.setColor(Color.WHITE);
	    	float left=10 * getResources().getDisplayMetrics().density;
	    	float top=30 * getResources().getDisplayMetrics().density;
	    	float right=360 * getResources().getDisplayMetrics().density;
	    	float bottom=540 * getResources().getDisplayMetrics().density;
	    	//mCanvas.drawRect(left, top, right, bottom, mPaint);
	    	
	    	//mPaint.setStyle(Paint.Style.STROKE);
	    	//mPaint.setColor(Color.parseColor("#CB4335")); //red
	        //mPaint.setColor(Color.RED);
	        row=0;
	        //Random rand=null;

	        try {
	           Random rand = SecureRandom.getInstance("SHA1PRNG");
	        
	        
	        
	        //Random rand = new Random();
	        final float LOW_LEVEL=0.75f;
	      	final float MEDIUM_LEVEL=1.0f;
	      	final float HIGH_LEVEL=1.5f;
	      	final float XHIGH_LEVEL=3.0f;
	      	level=getApplicationContext().getResources().getDisplayMetrics().density;
	      	density=getApplicationContext().getResources().getDisplayMetrics().densityDpi;
	      	hpixel=getApplicationContext().getResources().getDisplayMetrics().heightPixels;
	      	wpixel=getApplicationContext().getResources().getDisplayMetrics().widthPixels;
	      	//int lcenx=0,hcenx=0,lceny=0,hceny=0,lrad=0,hrad=0;
	      	float plcenx=130,plceny=170,phcenx=230,phceny=380,plred=80,phred=110;
	      	plcenx=plcenx* getResources().getDisplayMetrics().density;
	      	plceny=plceny* getResources().getDisplayMetrics().density;
	      	phcenx=phcenx* getResources().getDisplayMetrics().density;
	      	phceny=phceny* getResources().getDisplayMetrics().density;
	      	plred=plred* getResources().getDisplayMetrics().density;
	      	phred=phred* getResources().getDisplayMetrics().density;
	      	
	      	centerX1 = rand.nextInt(((int)phcenx -(int)plcenx) + 1) +(int) plcenx;
	      	centerY1 = rand.nextInt(((int)phceny - (int)plceny) + 1) + (int)plceny;
	      	suggestradious1=rand.nextInt(((int)phred - (int)plred) + 1) + (int)plred;
	      	ang1=rand.nextInt((360 - 0) + 1) + 0;
	      	
	      	centerX2 = rand.nextInt(((int)phcenx -(int)plcenx) + 1) +(int) plcenx;
	      	centerY2 = rand.nextInt(((int)phceny - (int)plceny) + 1) + (int)plceny;
	      	suggestradious2=rand.nextInt(((int)phred - (int)plred) + 1) + (int)plred;
	      	ang2=rand.nextInt((360 - 0) + 1) + 0;
	      	
	      	centerX3 = rand.nextInt(((int)phcenx -(int)plcenx) + 1) +(int) plcenx;
	      	centerY3 = rand.nextInt(((int)phceny - (int)plceny) + 1) + (int)plceny;
	      	suggestradious3=rand.nextInt(((int)phred - (int)plred) + 1) + (int)plred;
	      	ang3=rand.nextInt((360 - 0) + 1) + 0;
	      	
	      	
	      	suggestX1=(int)(centerX1+suggestradious1*Math.sin(Math.PI*ang1/180.0));
	      	suggestY1=(int)(centerY1+suggestradious1*Math.cos(Math.PI*ang1/180.0));
	      	
	      	suggestX2=(int)(centerX2+suggestradious2*Math.sin(Math.PI*ang2/180.0));
	      	suggestY2=(int)(centerY2+suggestradious2*Math.cos(Math.PI*ang2/180.0));
	      	
	      	suggestX3=(int)(centerX3+suggestradious3*Math.sin(Math.PI*ang3/180.0));
	      	suggestY3=(int)(centerY3+suggestradious3*Math.cos(Math.PI*ang3/180.0));
	      	boolean test=checkOverlapinCircle();
	      	if(!test) randomCircle();
	      	else if(!threeCircle){draw3Circle();}
	        } catch(NoSuchAlgorithmException nsae) {
	            // Process the exception in some way or the other
	        }
	    }
	    
	    /*-------------------------End of Random Suggestion----------------------------------------*/    
		 boolean checkOverlapinCircle(){
			 double distc1c2,distc1c3, distc2c3;
			 double dists1s2,dists1s3, dists2s3;
			 boolean test=true;
			 distc1c2=Math.sqrt(Math.pow((centerX1-centerX2), 2)+Math.pow((centerY1-centerY2), 2));
			 distc1c3=Math.sqrt(Math.pow((centerX1-centerX3), 2)+Math.pow((centerY1-centerY3), 2));
			 distc2c3=Math.sqrt(Math.pow((centerX2-centerX3), 2)+Math.pow((centerY2-centerY3), 2));
			 dists1s2=Math.sqrt(Math.pow((suggestX1-suggestX2), 2)+Math.pow((suggestY1-suggestY2), 2));
			 dists1s3=Math.sqrt(Math.pow((suggestX1-suggestX3), 2)+Math.pow((suggestY1-suggestY3), 2));
			 dists2s3=Math.sqrt(Math.pow((suggestX2-suggestX3), 2)+Math.pow((suggestY2-suggestY3), 2));
			 if(distc1c2<200||distc1c3<200||distc2c3<200 ||  dists1s2 <300 || dists1s3<300||dists2s3<300){
				 test= false;
			 //tryagain();
			 }
			return test;
		 }
    /*--------------------------draw3Circle-------------------------*/		 
	  void draw3Circle(){  
		    text.setText("Draw all suggested circles (S: Starting point)");
		    texts.setText(outs);
	    	//mPaint.setStrokeWidth(3);
	    	//mPaint.setTextSize(50);
	    	//mPaint.setColor(Color.parseColor("#CB4335")); //red
	      	//mPaint.setColor(Color.RED);
		   
	      	mCanvas.drawText("S", suggestX1+10, suggestY1+10, SPaint);
	      	
	      	mCanvas.drawCircle(suggestX1, suggestY1, 8, PSPaint);
	      	
	      	mCanvas.drawText("S", suggestX2+10, suggestY2+10, SPaint);
	      	mCanvas.drawCircle(suggestX2, suggestY2, 8, PSPaint);
	      	
	      	mCanvas.drawText("S", suggestX3+10, suggestY3+10, SPaint);
	      	mCanvas.drawCircle(suggestX3, suggestY3, 8, PSPaint);
	      	
	    	mPaint.setColor(Color.parseColor("#2048EC")); //blue
	      	//mPaint.setColor(Color.BLUE);
//	      	mCanvas.drawText("C", centerX1+10, centerY1+10, CPaint);
//	      	mCanvas.drawCircle(centerX1, centerY1, 8, PCPaint);
//	      	
//	      	mCanvas.drawText("C", centerX2+10, centerY2+10, CPaint);
//	      	mCanvas.drawCircle(centerX2, centerY2, 8, PCPaint);
//	      	
//	      	mCanvas.drawText("C", centerX3+10, centerY3+10, CPaint);
//	      	mCanvas.drawCircle(centerX3, centerY3, 8, PCPaint);
	      	
	      	double leftx=centerX1;
	    	leftx=leftx-suggestradious1;
	    	double topy=centerY1;
	    	topy=topy-suggestradious1;
	    	double rightx=leftx+(2*suggestradious1);
	    	double bottomy=topy+(2*suggestradious1);
	      	RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
	      	mPaint.setStyle(Paint.Style.STROKE);
	      	mPaint.setColor(Color.parseColor("#CB4335")); //red
	    	//mPaint.setColor(Color.RED);
	    	mCanvas.drawOval(ovalBounds, mPaint);
	    	
	    	double leftx2=centerX2;
	    	leftx2=leftx2-suggestradious2;
	    	double topy2=centerY2;
	    	topy2=topy2-suggestradious2;
	    	double rightx2=leftx2+(2*suggestradious2);
	    	double bottomy2=topy2+(2*suggestradious2);
	      	RectF ovalBounds2=new RectF((float)leftx2,(float)topy2,(float)rightx2,(float)bottomy2);
	      	mPaint.setStyle(Paint.Style.STROKE);
	      	mPaint.setColor(Color.parseColor("#CB4335")); //red
	    	//mPaint.setColor(Color.RED);
	    	mCanvas.drawOval(ovalBounds2, mPaint);
	    	
	    	double leftx3=centerX3;
	    	leftx3=leftx3-suggestradious3;
	    	double topy3=centerY3;
	    	topy3=topy3-suggestradious3;
	    	double rightx3=leftx3+(2*suggestradious3);
	    	double bottomy3=topy3+(2*suggestradious3);
	      	RectF ovalBounds3=new RectF((float)leftx3,(float)topy3,(float)rightx3,(float)bottomy3);
	      	mPaint.setStyle(Paint.Style.STROKE);
	      	mPaint.setColor(Color.parseColor("#CB4335")); //red
	    	//mPaint.setColor(Color.RED);
	    	mCanvas.drawOval(ovalBounds3, mPaint); 
	    }
	  
	  /*--------------Clear all data form temporary storage---------------*/
	    void ClearAllData(){
		    mBitmap.eraseColor(Color.WHITE);
		    mPath.reset();
		    mView.invalidate();
	  	    row=0;
	  	    intersection=0;
	  	    drawenable=1;
	  	    round=0;
	  	    finishDraw1=false;
			finishDraw2=false;
			finishDraw3=false;
			threeCircle=false;
			if(radi==1){
			outs="Rounds: "+(round/4+1)+"/10";
			}else
			if(radi==2){
			outs="Rounds: "+(round/4+1)+"/5";
			}
			else{
			outs="Rounds: "+(round/4+1)+"/10";	
			}
	  	    randomCircle();
	    }
	    
	  /*...............Store the user drawing data in an array...........*/
	  void storeData(double x,double y,double vx,double vy,double lsorinX,double lsorinY,double lsorinZ,double lp,double les){
	    	 // text.setText("Values:\n X:"+x+"\n Y:"+y+"\n P:"+p);
	     points[row][0]=x;
	     points[row][1]=y;
	     vpoints[row][0]=vx;
	     vpoints[row][1]=vy;
	     orientationx[row]=lsorinX;
	     orientationy[row]=lsorinY;
	     orientationz[row]=lsorinZ;
	     Pressure[row]=lp;
	     EventSize[row]=les;
	     row++; 	 
	    }
	  /*-----------------Validation Check------------------------*/
	  void validation(){
		  for(int j=0;j<row;j++){
			  double xbound=points[j][0]/getResources().getDisplayMetrics().density;
			  double ybound=points[j][1]/getResources().getDisplayMetrics().density;
			  if(xbound<0 ||xbound>360 ||ybound<30 ||ybound>540){
				  Toast.makeText(Drawing.this, "Drawing is out of boundary", Toast.LENGTH_SHORT).show();
				  //tryagain();
				  drawAgain();
			  }
		  }
	  }
	  
/*--------------------End 5-------------------*/
	  void drawAgain(){
		 
		 /* 
		  text.setText("Draw a circle in the given boundary based on suggestion");
	     paint.setColor(Color.WHITE);
	  	float left=0 * getResources().getDisplayMetrics().density;
	  	float top=30 * getResources().getDisplayMetrics().density;
	  	float right=360 * getResources().getDisplayMetrics().density;
	  	float bottom=540 * getResources().getDisplayMetrics().density;
	  	canvas.drawRect(left, top, right, bottom, paint);
	  	paint.setStyle(Paint.Style.STROKE);
	     paint.setColor(Color.RED);
	      */
		    //Toast.makeText(Level1.this, "Round"+(round+1), Toast.LENGTH_SHORT).show();
		    mBitmap.eraseColor(Color.WHITE);
		    mPath.reset();
		    mView.invalidate();
//		    mBitmap.eraseColor(Color.TRANSPARENT);
//		    paint.reset();
//		    imageView.invalidate();
		    row=0;
		    intersection=0;
		    drawenable=1;
		    //round=0;
		    text.setText(out);
		    texts.setText(outs);
		   // text.setText("Draw a circle in the given boundary based on suggestion");
		   //paint.setColor(Color.parseColor("#F9F9F9"));
		   // paint.setColor(Color.WHITE);
		    float left=0 * getResources().getDisplayMetrics().density;
		  	float top=30 * getResources().getDisplayMetrics().density;
		  	float right=360 * getResources().getDisplayMetrics().density;
		  	float bottom=540 * getResources().getDisplayMetrics().density;
//		  	canvas.drawRect(left, top, right, bottom, paint);
		  	mPaint.setStyle(Paint.Style.STROKE);
 	  	    mPaint.setColor(Color.parseColor("#CB4335")); //red
		   // paint.setColor(Color.RED);
		     
		     
//		    mPaint.setStrokeWidth(3);
//	     	mPaint.setTextSize(50);
//	     	paint.setColor(Color.parseColor("#CB4335")); //red
	    	//paint.setColor(Color.RED);
		  	//Paint textPaint=new Paint();
		   // textPaint.setTextSize(35);
	    	if(!finishDraw1){
	    	mCanvas.drawText("S", suggestX1+10, suggestY1+10, SPaint);
	    	mCanvas.drawCircle(suggestX1, suggestY1, 8, PSPaint);
	    	}
	    	if(!finishDraw2){
	    	mCanvas.drawText("S", suggestX2+10, suggestY2+10, SPaint);
	    	mCanvas.drawCircle(suggestX2, suggestY2, 8, PSPaint);
	    	}
	    	if(!finishDraw3){
	    	mCanvas.drawText("S", suggestX3+10, suggestY3+10, SPaint);
	    	mCanvas.drawCircle(suggestX3, suggestY3, 8, PSPaint);
	    	}
	    	
	    	if(!finishDraw1){
	    	//paint.setColor(Color.parseColor("#2048EC"));
//	    	mCanvas.drawText("C", centerX1+10, centerY1+10, CPaint);
//	    	mCanvas.drawCircle(centerX1, centerY1, 8, PCPaint);
//	    	}
//	    	if(!finishDraw2){
//	    	mCanvas.drawText("C", centerX2+10, centerY2+10, CPaint);
//	    	mCanvas.drawCircle(centerX2, centerY2, 8, PCPaint);
//	    	}
//	    	if(!finishDraw3){
//	    	mCanvas.drawText("C", centerX3+10, centerY3+10, CPaint);
//	    	mCanvas.drawCircle(centerX3, centerY3, 8, PCPaint);
	    	}
	    	
	   if(!finishDraw1){
	     double leftx=centerX1;
	  	 leftx=leftx-suggestradious1;
	  	 double topy=centerY1;
	  	 topy=topy-suggestradious1;
	  	 double rightx=leftx+(2*suggestradious1);
	  	 double bottomy=topy+(2*suggestradious1);
	    	RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
	    	mPaint.setStyle(Paint.Style.STROKE);
	    	mPaint.setColor(Color.parseColor("#CB4335")); //red
	  	    mCanvas.drawOval(ovalBounds, mPaint);
	   }
	   if(!finishDraw2){
	  	double leftx2=centerX2;
	  	leftx2=leftx2-suggestradious2;
	  	double topy2=centerY2;
	  	topy2=topy2-suggestradious2;
	  	double rightx2=leftx2+(2*suggestradious2);
	  	double bottomy2=topy2+(2*suggestradious2);
	    	RectF ovalBounds2=new RectF((float)leftx2,(float)topy2,(float)rightx2,(float)bottomy2);
//	    	paint.setStyle(Paint.Style.STROKE);
//	    	paint.setColor(Color.parseColor("#CB4335")); //red
	  	
	  	mCanvas.drawOval(ovalBounds2, mPaint);
	   }
	   
	   if(!finishDraw3){
	  	double leftx3=centerX3;
	  	leftx3=leftx3-suggestradious3;
	  	double topy3=centerY3;
	  	topy3=topy3-suggestradious3;
	  	double rightx3=leftx3+(2*suggestradious3);
	  	double bottomy3=topy3+(2*suggestradious3);
	    	RectF ovalBounds3=new RectF((float)leftx3,(float)topy3,(float)rightx3,(float)bottomy3);
//	    	paint.setStyle(Paint.Style.STROKE);
//	    	paint.setColor(Color.parseColor("#CB4335")); //red
	  	
	  	mCanvas.drawOval(ovalBounds3, mPaint); 
	   }
		   if(finishDraw1 && finishDraw2 && finishDraw3){
			   threeCircle=true;
			   flashedCircle=true;
			   drawenable=0;
			   tryaFlasedCirlce();
			  
			  //tryagain();
			 }
	// else if (finishDraw1 && finishDraw2 && finishDraw3) {
//				finishDraw1 = false;
//				finishDraw2 = false;
//				finishDraw3 = false;
//				threeCircle = false;
//				flashedCircle = true;
//				// tryaFlasedCirlce();
//				tryagain();
//			}
	   
	  }
	  
	  /*----------------------Start 2----------------------*/
	//Draw a Polygon
	void drawAPolygon(){
		int totalPoint=row,finalPoint=0;
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setColor(Color.parseColor("#2048EC"));
		
		double polygonPoint[][]=new double[5][5];
		polygonPoint[0][0]=points[0][0];
		polygonPoint[0][1]=points[0][1];
		for(int i=1;i<5;i++){
			polygonPoint[i][0]=points[i*(totalPoint/5)][0];
			polygonPoint[i][1]=points[i*(totalPoint/5)][1];
			//canvas.drawLine((float)polygonPoint[i-1][0],(float)polygonPoint[i-1][1], (float)polygonPoint[i][0], (float)polygonPoint[i][1], paint);
			finalPoint=i;
		}
		//canvas.drawLine((float)polygonPoint[finalPoint][0],(float)polygonPoint[finalPoint][1], (float)polygonPoint[0][0], (float)polygonPoint[0][1], paint);
		for(int i=0;i<5;i++){
			polygon.x[i]=polygonPoint[i][0];
			polygon.y[i]=polygonPoint[i][1];
		}
		compute2DPolygonCentroid(); /// Go to the Start 3
		
	}
	
	/*-------------------Start 3-----------------------*/
	 // Polygon center Calculation + Ideal circle drawing
	  void compute2DPolygonCentroid(){
			double signedArea=0;
			int plength=5;
			double x0=0;//Current vertex x
			double y0=0; // Current vertex y
			double x1=0; //Next vertex x
			double y1=0; //Next vertex y
			double a=0; //Partial signed area
			
			//For all vertex except last
			int i=0;
			for(i=0;i<plength-1;i++){
				x0=polygon.x[i];
				y0=polygon.y[i];
				x1=polygon.x[i+1];
				y1=polygon.y[i+1];
				a=x0*y1-x1*y0;
				signedArea +=a;
				polygon.centroid[0] +=(x0+x1)*a;
				polygon.centroid[1] +=(y0+y1)*a;
			}
			//Do last vertex
			x0=polygon.x[i];
			y0=polygon.y[i];
			x1=polygon.x[0];
			y1=polygon.y[0];
			a=x0*y1-x1*y0;
			signedArea +=a;
			polygon.centroid[0] +=(x0+x1)*a;
			polygon.centroid[1] +=(y0+y1)*a;
			Log.v("", "ConsSing:"+signedArea);
			signedArea *=0.5;
			polygon.centroid[0]/=(6.0*signedArea);
			polygon.centroid[1]/=(6.0*signedArea);
	      if(Double.isInfinite(polygon.centroid[0])||Double.isInfinite(polygon.centroid[1])){
	    	  //Log.v("", "Cons???");
	    	  polygon.centroid[0]=0;
	    	  polygon.centroid[1]=0;
	      }
			//Longest radious calculation
			int radiousList=1,flag=0;
			polygon.allRadious[0]=(int)Math.sqrt(Math.pow(polygon.centroid[0]-polygon.x[0],2)+Math.pow(polygon.centroid[1]-polygon.y[0],2));
			fRadious=polygon.allRadious[0];
			polygon.tRadious[0]=1;
			/// Calculate angle between two point
			tengent1=Math.atan2(polygon.y[0]-polygon.centroid[1],polygon.x[0]-polygon.centroid[0]);
			tengent2=Math.atan2(polygon.y[1]-polygon.centroid[1],polygon.x[1]-polygon.centroid[0]);
			angeloffset=Math.atan2(Math.sin(tengent1-tengent2),Math.cos(tengent1-tengent2));
			//Log.v("","Angle:"+angeloffset);
			
			// Use to calculate all the distinct Radius
			for(i=1;i<plength;i++){
				int radious=(int)Math.sqrt(Math.pow(polygon.centroid[0]-polygon.x[i],2)+Math.pow(polygon.centroid[1]-polygon.y[i],2));
				for(int j=0;j<radiousList;j++){
				   if(polygon.allRadious[j]==radious){
					   polygon.tRadious[j]+=1;
					   flag=1;
				   }
				}
				if(flag!=1){
					polygon.allRadious[radiousList]=radious;
					 polygon.tRadious[radiousList]=1;
					flag=0;
					radiousList++;
				}
						}
			//Calculate the maximum time of radius and its position 
			 int maxRadious=1,maxposition=0;
			for(i=0;i<radiousList;i++){
				int localmax=polygon.tRadious[i];
				if(localmax>maxRadious){
					maxRadious=localmax;
					maxposition=i;
				}
			}
			//if all radius are at one times then calculate average radius
					int avgRadious=0;
					if(maxRadious==1){
					for(i=0;i<radiousList;i++){
						avgRadious+=polygon.allRadious[i];
					}
					avgRadious=avgRadious/radiousList;
					fRadious=avgRadious;
					}
					else{
						fRadious=polygon.allRadious[maxposition];
					}
			for(i=0;i<radiousList;i++){
				System.out.print(polygon.allRadious[i]+" "+polygon.tRadious[i]);
				System.out.println(" "+maxRadious+" "+maxposition+" "+fRadious);
			}
			idealCircle(1); //Start 4
	  }
	 /*-----------------------End 3---------------------*/
	
	  /*---------------------Start 4----------------*/
	  //Draw a Ideal Circle drawing based on user drawing
		void idealCircle(int i){
	  if(i==1){
		double leftx=polygon.centroid[0];
		leftx=leftx-fRadious;
		double topy=(float)polygon.centroid[1];
		topy=topy-fRadious;
		double rightx=leftx+(2*fRadious);
		double bottomy=topy+(2*fRadious);
		if(initial==1){
		finalleftx=leftx;
		finaltopy=topy;
		finalrightx=rightx;
		finalbottomy=bottomy;
		initial=0;
		}
		RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
//		paint.setStyle(Paint.Style.STROKE);
//		paint.setColor(Color.GRAY);
		mCanvas.drawOval(ovalBounds, mPaint);
		randomLine((float)(leftx+fRadious), (float)(topy+fRadious));///Start 5
	 }
	 if(i==2){
			double leftx=centerX1;
			leftx=leftx-suggestradious1;
			double topy=centerY1;
			topy=topy-suggestradious1;
			double rightx=leftx+(2*suggestradious1);
			double bottomy=topy+(2*suggestradious1);
			if(initial==1){
			finalleftx=leftx;
			finaltopy=topy;
			finalrightx=rightx;
			finalbottomy=bottomy;
			initial=0;
			}
			RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
//			paint.setStyle(Paint.Style.STROKE);
//			paint.setColor(Color.parseColor("#CB4335")); //red
			mCanvas.drawOval(ovalBounds, mPaint);
		
	 }
			//return fRadious;
	}

/*------------------------Start 5---------------------------------*/
		  //Random point selection to calculate error
		  void randomLine(float cx,float cy){
			  double ang=0.0,x1,y1,ax1,ay1,errordist,tempdist=0,ux=0,uy=0;
			  double x,y,m,c,A,B,C,D,check,insecx,insecy,plusinsecx,minusinsecx,minuinsecx,plusinsecy,minusinsecy,distplus,distminus,dist;
			  double Ssuggestradious=0,ScenterX=0,ScenterY=0,SsuggestX=0,SsuggestY=0;
			  int selectedCircleNo=checkCircleNo();
			  if(!threeCircle){
			  if(selectedCircleNo==1){
				  Ssuggestradious=suggestradious1;
				  ScenterX=centerX1;
				  ScenterY=centerY1;
				  SsuggestX=suggestX1;
				  SsuggestY=suggestY1;
			  }else if(selectedCircleNo==2){
				  Ssuggestradious=suggestradious2;
				  ScenterX=centerX2;
				  ScenterY=centerY2;
				  SsuggestX=suggestX2;
				  SsuggestY=suggestY2;
			  }else if(selectedCircleNo==3){
				  Ssuggestradious=suggestradious3;
				  ScenterX=centerX3;
				  ScenterY=centerY3;
				  SsuggestX=suggestX3;
				  SsuggestY=suggestY3;
			  }
			  }else{
				  Ssuggestradious=suggestradiousf;
				  ScenterX=centerXf;
				  ScenterY=centerYf;
				  SsuggestX=suggestXf;
				  SsuggestY=suggestYf;  
			  }
			  
			  radiousDiff=Math.abs(fRadious-Ssuggestradious);
			  //Log.v("","Radius Diff:"+radiousDiff);
		      if(radiousDiff<0 || radiousDiff>100){
		    	 // Log.v("","Conssugg:"+suggestradious+"FRadi:"+fRadious );
		    	  constrain=(constrain | eradius);
		      }
		      centerDiff=Math.sqrt(Math.pow(polygon.centroid[0]-ScenterX, 2)+Math.pow(polygon.centroid[1]-ScenterY, 2));
		      //Log.v("","Center Diff:"+centerDiff);
		      if(centerDiff<0 || centerDiff>75){
		    	  //Log.v("","ConsPolyC:"+polygon.centroid[0]+" "+polygon.centroid[1]+"Cen:"+centerX+" "+centerY );
		    	  constrain=(constrain | ecenter);
		    	  //return;
		      }
		      startDiff=Math.sqrt(Math.pow(points[0][0]-SsuggestX, 2)+Math.pow(points[0][1]-SsuggestY, 2));
		      //Log.v("","Start Difference:"+startDiff);
		      if(startDiff<0 || startDiff>100){
		      constrain=(constrain | estarting);
		      stindication=1;
		  	  //return;
		    }
		      
		      for(int i=0;i<10;i++,ang+=36){
				  x1=cx+(fRadious*Math.sin(Math.PI*ang/180.0));
				  y1=cy+(fRadious*Math.cos(Math.PI*ang/180.0));
				  
				  ax1=ScenterX+(Ssuggestradious*Math.sin(Math.PI*ang/180.0));
				  ay1=ScenterY+(Ssuggestradious*Math.cos(Math.PI*ang/180.0));
				 
				  //idealCircle(2);
				 
				  errordist=99999;
				  int k=0;
				  for(int j=0;j<row;j++){
					 tempdist=Math.sqrt(Math.pow(Math.abs(ax1-points[j][0]),2)+Math.pow(Math.abs(ay1-points[j][1]),2));
					  if(tempdist<errordist){
						  errordist=tempdist;
						    ux=points[j][0];
						    uy=points[j][1];
						   k=j;
						   //totaVelocity=(vpoints[j][0]+vpoints[j][1])/2;
					  }
				  }
				 if(errordist<0 || errordist>100){
				  	 constrain=(constrain | errdist);
				  	  //return;
				    }
				  Log.v("","Error:" +i+" "+errordist);
			       int tround=round*10+i;
				   int tori=round*30+3*i;
				   int tvel=round*20+2*i;
				   fdistance[tround]=errordist*(160/density);
				   ///Store velocity data 
				   velocity[tvel]=vpoints[k][0];
				   velocity[tvel+1]=vpoints[k][1];
				   //Log.v("","Velocity:"+totaVelocity);
				   PRessure[tround]=Pressure[k];
				   Log.v("","Pressure2:"+Pressure[k]);
				   EVentSize[tround]=EventSize[k];
				   Log.v("","Event:"+EventSize[k]);
				   orientation[tori]= orientationx[k];
				   orientation[tori+1]= orientationy[k];
				   orientation[tori+2]= orientationz[k];
				   Log.v("","Orientation:"+orientationx[k]+" "+orientationy[k]+" "+orientationz[k]);
			   	 }
			   
		       totalRound=round;
		       alldata[totalRound*7+0]=startDiff*(160/density);//Start of x
		       Log.v("","Start:"+startDiff*(160/density));
		       alldata[totalRound*7+1]=centerDiff*(160/density);
		       if(angeloffset>=0){
					alldata[totalRound*7+2]=-1;
					//Log.v("","Rotation:-1");
					}
				else {
					alldata[totalRound*7+2]=1.0;
					//Log.v("","Rotation:1");	
				}
		       Log.v("","Direction:"+alldata[totalRound*7+1]);
		       alldata[totalRound*7+3]=7*timeDiff/(44*fRadious);
		       Log.v("","Time:"+timeDiff);
		       if(timeDiff<0 || timeDiff>50){
				   constrain=(constrain | etime);
				   timeindication=1;
			    }
		      
		       alldata[totalRound*7+4]=radiousDiff*(160/density);
		       alldata[totalRound*7+5]=reactDiff;
		       alldata[totalRound*7+6]=timeDiff;
//		       if(constrain>0 && missflashed==true){
//		    	   constrain=0;
//		    	  drawAgain();
//		       }else 
		    	   if(constrain>0){
		       Log.v("","Constrain:"+constrain);
		       if(stindication==1){
		       Toast.makeText(Drawing.this, "Error in starting point. Try again", Toast.LENGTH_SHORT).show();
		       stindication=0;
		       }else if(timeindication==1){
		    	Toast.makeText(Drawing.this, "Too long time. Try again", Toast.LENGTH_SHORT).show();
		    	timeindication=0;
		       }else{
		 	   Toast.makeText(Drawing.this, "Not close to the circle. Try again", Toast.LENGTH_SHORT).show();
		       }
		 	   timeConstrain[ctimes]=constrain;
		 	   ctimes++;
		 	   constrain=0;
		 	  drawAgain();
		        }else if(!threeCircle){
		      if((radi==1 && round <41)||(radi==2 && round <21)||(radi==0))
		     round++;
		     //Toast.makeText(Level1.this, "Round:"+round, Toast.LENGTH_SHORT).show();
		     //Log.v("", "Roundd:"+round);
		     if(selectedCircleNo==1){finishDraw1=true;}
		     if(selectedCircleNo==2){finishDraw2=true;}
		     if(selectedCircleNo==3){finishDraw3=true;}
		     drawAgain();    
		  }else{
			  if((radi==1 && round <41)||(radi==2 && round <21)||(radi==0))
			  round++;
			 // Toast.makeText(Level1.this, "Round:"+round, Toast.LENGTH_SHORT).show();
			  threeCircle=false;
			  finishDraw1=false;
			  finishDraw2=false;
			  finishDraw3=false;
			  mPath.reset();
 		      mView.invalidate();
			  intersection=0;
//			   paint.reset();
//			    imageView.invalidate();
			    intersection=0;
			    if((radi==1 && round>=40)||(radi==2 && round>=20)||(radi==0 && round>=40)){
					  outs="Completed.";
				  } else if(radi==1){
			   	  outs="Rounds: "+(round/4+1)+"/10";
				  } 
				  else if(radi==2){
				  outs="Rounds: "+(round/4+1)+"/5";  
				  }else if(radi==0){
					  outs="Rounds: "+(round/4+1)+"/10";
				  }
			    
		    	if(radi==1 && round==40){
		    	gtimeEnd=System.currentTimeMillis();
		    	gTime=(gtimeEnd-gtimeStart)/ 1000;
				alertDialog.show();
		 		//Toast.makeText(Level1.this, "Finish!! You can exit.", Toast.LENGTH_SHORT).show();
				}
		    	else if(radi==2 && round==20){
		  
		    	gtimeEnd=System.currentTimeMillis();
			    gTime=(gtimeEnd-gtimeStart)/ 1000;
		    			  
					alertDialog.show();
					//Toast.makeText(Level1.this, "Finish!! You can exit.", Toast.LENGTH_SHORT).show();
				}
		    	else if(radi==0 && round==40){
					alertDialog.show();
					}
			  //randomCircle();
		      tryagain();
		      //draw3Circle();
		  }
		  }
	
	/*----------Check Circle No---------------------*/	  
		  int checkCircleNo(){
				 int circleNo=0; 
				 double tempradiousDiff1,tempradiousDiff2,tempradiousDiff3;
				 tempradiousDiff1=Math.abs(fRadious-suggestradious1); 
				 tempradiousDiff2=Math.abs(fRadious-suggestradious2);
				 tempradiousDiff3=Math.abs(fRadious-suggestradious3);
				 
				 double tempcenterDiff1,tempcenterDiff2,tempcenterDiff3;
				 tempcenterDiff1=Math.sqrt(Math.pow(polygon.centroid[0]-centerX1, 2)+Math.pow(polygon.centroid[1]-centerY1, 2));
				 tempcenterDiff2=Math.sqrt(Math.pow(polygon.centroid[0]-centerX2, 2)+Math.pow(polygon.centroid[1]-centerY2, 2));
				 tempcenterDiff3=Math.sqrt(Math.pow(polygon.centroid[0]-centerX3, 2)+Math.pow(polygon.centroid[1]-centerY3, 2));
				 
				 double tempstartDiff1,tempstartDiff2,tempstartDiff3;
				 tempstartDiff1=Math.sqrt(Math.pow(points[0][0]-suggestX1, 2)+Math.pow(points[0][1]-suggestY1, 2));
				 tempstartDiff2=Math.sqrt(Math.pow(points[0][0]-suggestX2, 2)+Math.pow(points[0][1]-suggestY2, 2));
				 tempstartDiff3=Math.sqrt(Math.pow(points[0][0]-suggestX3, 2)+Math.pow(points[0][1]-suggestY3, 2));
				 
//				 if(tempradiousDiff1< tempradiousDiff2 && tempradiousDiff1 <tempradiousDiff3 &&
//				    tempcenterDiff1 < tempcenterDiff2 && tempcenterDiff1<tempcenterDiff3 &&
//				    tempstartDiff1 < tempstartDiff2 && tempstartDiff1 <tempstartDiff3){
//					 circleNo=1; 
//					 
//				 }else if(tempradiousDiff2< tempradiousDiff1 && tempradiousDiff2 <tempradiousDiff3 &&
//						    tempcenterDiff2 < tempcenterDiff1 && tempcenterDiff2<tempcenterDiff3 &&
//						    tempstartDiff2 < tempstartDiff1 && tempstartDiff2 <tempstartDiff3){
//					 circleNo=2; 
//					 
//				 }else if(tempradiousDiff3< tempradiousDiff1 && tempradiousDiff3 <tempradiousDiff2 &&
//						    tempcenterDiff3 < tempcenterDiff1 && tempcenterDiff3<tempcenterDiff2 &&
//						    tempstartDiff3 < tempstartDiff1 && tempstartDiff3 <tempstartDiff2){
//					 circleNo=3; 
//					 
//				 }
				 if(tempstartDiff1 < tempstartDiff2 && tempstartDiff1 <tempstartDiff3){
							 circleNo=1; 
							 Log.v("", "Circlce1");
							 
						 }else if(tempstartDiff2 < tempstartDiff1 && tempstartDiff2 <tempstartDiff3){
							 circleNo=2; 
							 Log.v("", "Circlce2");
							 
						 }else if(tempstartDiff3 < tempstartDiff1 && tempstartDiff3 <tempstartDiff2){
							 circleNo=3; 
							 Log.v("", "Circlce3");
							 
						 }
				 
				 //Toast.makeText(Level1.this, "Select circel no:"+circleNo, Toast.LENGTH_SHORT).show();
				 return circleNo;
			  }
		  
		  /*--------------Clear screen to draw again-------------------*/ 
		    void tryagain(){
				
				finishDraw1=false;
				finishDraw2=false;
				finishDraw3=false;
				threeCircle=false;
			    mBitmap.eraseColor(Color.WHITE);
			    mPath.reset();
			    mView.invalidate();
		  	    intersection=0;
		  	    drawenable=1;
		  	    mCanvas.drawLine(0, 0, 0, 0, mPaint);
//		  	    paint.setStrokeWidth(3);
//		    	paint.setTextSize(50);
		  	    draw3Circle();      
		    }

		    /*--------------Try flashed circle-------------------*/ 
		    void tryaFlasedCirlce(){
		    	//bitmap.eraseColor(Color.TRANSPARENT);
//		  	    paint.reset();
//		  	    imageView.invalidate();
//		  	    row=0;
//		  	    intersection=0;
//		    	 missflashed=false;
		  	  randomFlasedCircle(1);
//		  	textc.setText("seconds remaining: " );
//		  	
//		  	new CountDownTimer(3000, 1000) {
//
//		  	     public void onTick(long millisUntilFinished) {
//		  	    	//Toast.makeText(getApplicationContext(), "Wait till disappear.", Toast.LENGTH_SHORT).show();
//		  	         textc.setText("Wait!");
//		  	    	 
//		  	    	//mCanvas.drawText("Wait till circle disappear.", 50, 150, mPaint);
//		  	         
//		  	         
//		  	     }
//
//		  	     public void onFinish() {
//		  	         //textc.setText("done!");
//		  	     }
//		  	  }.start();
		  	  
		  	  
		  	  randomCircle();
		  	  final Handler handler = new Handler();
		  	  handler.postDelayed(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	        // Do something after 5s = 5000ms
		  	   // bitmap.eraseColor(Color.TRANSPARENT);
		  	    mPath.reset();
		 		mView.invalidate();
				intersection=0;
		  	    
			    intersection=0;
			    randomFlasedCircleRemove();
			   // randomFlasedCircle(0);
			    drawenable=1;
			    reactStart=System.currentTimeMillis();
			    notmissflashed=false;
			   // MissCirelce(false);
		   	    //MissCirelce(true);	
		   	     out="Miss the suggestion? Press Refresh button.";
		         text.setText(out);  
		   	    //ftime=1;
//			    paint.reset();
//			    imageView.invalidate();
		  	    }
		  	}, 1500);
		  	
		  	
//		  	  
//			    new Handler().postDelayed(new Runnable(){
//					 public void run(){
//						 randomFlasedCircle(0);	 
//						 drawenable=1;
//						 reactStart=System.currentTimeMillis();
//					 }
//				 },1000);
			   
			        
		    }  
		    
	    /*-----------remove Flashed Circle----------------------------------------*/
		    void randomFlasedCircleRemove(){
		    	 mBitmap.eraseColor(Color.WHITE);
				 mPath.reset();
				 mView.invalidate();
		    	
		    }
		    /*-------------Random Suggestion----------------------------------------*/ 
		    void randomFlasedCircle(int color){
		    	 
		    	
		    	mPaint.setColor(Color.parseColor("#F9F9F9")); //white
		    	//paint.setColor(Color.WHITE);
		    	float left=0 * getResources().getDisplayMetrics().density;
		    	float top=30 * getResources().getDisplayMetrics().density;
		    	float right=360 * getResources().getDisplayMetrics().density;
		    	float bottom=540 * getResources().getDisplayMetrics().density;
		    	//mCanvas.drawRect(left, top, right, bottom, mPaint);
		    	//paint.setStyle(Paint.Style.STROKE);
		    	if(color==1){
		    		    //SPaint.setTextSize(50);
		    		    SPaint.setColor(Color.BLACK);
		    		    PSPaint.setColor(Color.BLACK);
		    		    //CPaint.setTextSize(50);
		    		    CPaint.setColor(Color.RED);
		    		    PCPaint.setColor(Color.RED);
		    		//mPaint.setColor(Color.parseColor("#CB4335")); //red
		       // paint.setColor(Color.RED);
		    	}else{
		    		SPaint.setColor(Color.WHITE);
	    		    PSPaint.setColor(Color.WHITE);
	    		    //CPaint.setTextSize(50);
	    		    CPaint.setColor(Color.WHITE);
	    		    PCPaint.setColor(Color.WHITE);
		    		//mPaint.setColor(Color.WHITE);
		    		//paint.setColor(Color.WHITE);	
		    	}
		    
		        try {
		           Random rand = SecureRandom.getInstance("SHA1PRNG");
		        //Random rand = new Random();
		        final float LOW_LEVEL=0.75f;
		      	final float MEDIUM_LEVEL=1.0f;
		      	final float HIGH_LEVEL=1.5f;
		      	final float XHIGH_LEVEL=3.0f;
		      	level=getApplicationContext().getResources().getDisplayMetrics().density;
		      	density=getApplicationContext().getResources().getDisplayMetrics().densityDpi;
		      	hpixel=getApplicationContext().getResources().getDisplayMetrics().heightPixels;
		      	wpixel=getApplicationContext().getResources().getDisplayMetrics().widthPixels;
		      	//int lcenx=0,hcenx=0,lceny=0,hceny=0,lrad=0,hrad=0;
		      	float plcenx=130,plceny=170,phcenx=230,phceny=380,plred=80,phred=130;
		      	plcenx=plcenx* getResources().getDisplayMetrics().density;
		      	plceny=plceny* getResources().getDisplayMetrics().density;
		      	phcenx=phcenx* getResources().getDisplayMetrics().density;
		      	phceny=phceny* getResources().getDisplayMetrics().density;
		      	plred=plred* getResources().getDisplayMetrics().density;
		      	phred=phred* getResources().getDisplayMetrics().density;
		      	
		      	if(color==1){
		      	centerXf = rand.nextInt(((int)phcenx -(int)plcenx) + 1) +(int) plcenx;
		      	centerYf = rand.nextInt(((int)phceny - (int)plceny) + 1) + (int)plceny;
		      	suggestradiousf=rand.nextInt(((int)phred - (int)plred) + 1) + (int)plred;
		      	ang1=rand.nextInt((360 - 0) + 1) + 0;
		      	}
		      	suggestXf=(int)(centerXf+suggestradiousf*Math.sin(Math.PI*ang1/180.0));
		      	suggestYf=(int)(centerYf+suggestradiousf*Math.cos(Math.PI*ang1/180.0));
		      	
		    	mPaint.setStrokeWidth(3);
		       	mPaint.setTextSize(50);
		       	
//		       	if(color==1){mPaint.setColor(Color.parseColor("#CB4335")); //red
//		       	mPaint.setColor(Color.RED);
//		       	}
		      	mCanvas.drawText("S", suggestXf+10, suggestYf+10, SPaint);
		      	mcPaint.setTextSize(70);
		 	    mcPaint.setColor(Color.BLUE);
		      	//mCanvas.drawText("Wait till circle disappear.", 200, 200, mcPaint);
		      	//Log.v("", "Centeree:"+suggestXf+" "+suggestYf);
		      	mCanvas.drawCircle(suggestXf, suggestYf, 8, PSPaint);
		      	
		      	
//		      	if(color==1){
//		      		mPaint.setColor(Color.parseColor("#2048EC"));
//		      		//paint.setColor(Color.BLUE);
//		      		}
//		      	mCanvas.drawText("C", centerXf+10, centerYf+10, CPaint);
//		      	mCanvas.drawCircle(centerXf, centerYf, 8, PCPaint);
		      	      	
		      	double leftx=centerXf;
		    	leftx=leftx-suggestradiousf;
		    	double topy=centerYf;
		    	topy=topy-suggestradiousf;
		    	double rightx=leftx+(2*suggestradiousf);
		    	double bottomy=topy+(2*suggestradiousf);
		      	RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
		      	mPaint.setStyle(Paint.Style.STROKE);
		      	paintSetup();
		      	if(color==1){
	    		mPaint.setColor(Color.parseColor("#CB4335")); //red
//		    	//paint.setColor(Color.RED);
	    	}
		    	mCanvas.drawOval(ovalBounds, mPaint); 
		    		
//		    	  handler.postDelayed(new Runnable() {
//		    	    @Override
//		    	    public void run() {
//		    	    	if(threeCircle==true){
//		    	    	out="Miss the suggestion? Tap on screen.";
//		    	        text.setText(out);
//		    	        missflashed=true;
//		    	    	}
//		    	    }
//		    	}, 5000);
//		    	 
		        } catch(NoSuchAlgorithmException nsae) {
		            // Process the exception in some way or the other
		        }
		    }
		    
		    
		    
		    /*----------------Combine All Features----------------------*/
		    void CombineAllFeatures(){
		    	store1="";
		    	int rounds=0;
		    	if(radi==1){
		    	    rounds=40;
		    		}else if(radi==2){
		    			rounds=20;
		    		}
		    	String heading="Lev/Rou"+","+"error1"+","+"error2"+","+"error3"+","+"error"+","+"error5"+","+"error6"+","+"error7"+","+"error8"+","+"error9"+","+"error10"
		      		  +","+"speedx1"+","+"speedy1"+","+"speedx3"+","+"speedy2"+","+"speedx3"+","+"speedy3"+","+"speedx4"+","+"speedy4"+","+"speedx5"+","+"speedy5"
		      		  +","+"speedx6"+","+"speedy6"+","+"speedx7"+","+"speedy7"+","+"speedx8"+","+"speedy8"+","+"speedx9"+","+"speedy9"+","+"speedx10"+","+"speedy10"
		      		  +","+"pres1"+","+"pres2"+","+"pres3"+","+"pres4"+","+"pres5"+","+"pres6"+","+"pres7"+","+"pres8"+","+"pres9"+","+"pres10"
		      		  +","+"events1"+","+"events2"+","+"events3"+","+"events4"+","+"events5"+","+"events6"+","+"events7"+","+"events8"+","+"events9"+","+"events10"
		      		  +","+"orix1"+","+"oriy1"+","+"oriz1"+","+"orix2"+","+"oriy2"+","+"oriz2"+","+"orix3"+","+"oriy3"+","+"oriz3"+","+"orix4"
		      		  +","+"oriy4"+","+"oriz4"+","+"orix5"+","+"oriy5"+","+"oriz5"+","+"orix6"+","+"oriy6"+","+"oriz6"+","+"orix7"+","+"oriy7"
		      		  +","+"oriz7"+","+"orix8"+","+"oriy8"+","+"oriz8"+","+"orix9"+","+"oriy9"+","+"oriz9"+","+"orix10"+","+"oriy10"+","+"oriz10"
		     		  +","+"eStart"+","+"eCenter"+","+"direction"+","+"timediff"+","+"eRadious"+","+"reactDiff"+","+"tTime"+","+"Constrain"+"\n";
		     // store=heading;
		      
		      for(int j=0;j<rounds;j++){
		      	store1+=("L-1:"+String.valueOf(j+1));
		    		for(int i=0;i<10;i++){
		    			store1+=","+fdistance[j*10+i];
		    				}
		    		for(int i=0;i<20;i++){
		    			store1+=","+velocity[j*20+i];
		    				}
		    		for(int i=0;i<10;i++){
		    			store1+=","+PRessure[j*10+i];
		    		       }
		    		for(int i=0;i<10;i++){
		    			store1+=","+EVentSize[j*10+i];
		    			   }
		    		for(int i=0;i<30;i++){
		    			store1+=","+orientation[j*30+i];
		    				}
		    		for(int i=0;i<7;i++){
		    			store1+=","+alldata[j*7+i];
		    				}
		    		//store1+=","+0;
		    		store1+=","+timeConstrain[j];
		    		store1+="\n";
		    		}
		      fStore=fStore.concat(heading);
		      //fStore=fStore+store;
		      FirstScreen.llevel1=true;
		      Log.v("","All Data1:"+store1);
		      
		    }
		    
		    /*--------------------Send data-----------------------------------*/  
		    private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		   	 
		  		@Override
		  		protected Double doInBackground(String... params) {
		  			// TODO Auto-generated method stub
		  			postData(params[0]);
		  			return null;
		  		}

		  		protected void onPostExecute(Double result){
		  			//pb.setVisibility(View.GONE);
		  			if(radi==1)
		  			Toast.makeText(getApplicationContext(), "Finished Registration", Toast.LENGTH_LONG).show();
		  			//FirstScreen.tvIsConnected.setText("Finished Registration");
		  		}
		  		protected void onProgressUpdate(Integer... progress){
		  			//pb.setProgress(progress[0]);
		  		}

		  		public void postData(String valueIWantToSend) {
		  			// Create a new HttpClient and Post Header
		  			String message="";
		  			message=Drawing.fStore;
		  			message=message+Drawing.store1;
		  			//message=message+Level2.store2;
		  			Drawing.fStore="";
		  			Drawing.store1="";
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
		    /*--------------------End Send data-----------------------------------*/
		    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
		        @Override
		        protected String doInBackground(String... urls) {

		            return GET(urls[0]);
		        }
		        // onPostExecute displays the results of the AsyncTask.
		        @Override
		        protected void onPostExecute(String result) {
		       	  //result1=result;
		            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
		            //etResponse.setText(result);
		       }
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
        	 if(radi==2){
        		    String[] resultmsg=result.split(";");
		  			Toast.makeText(getApplicationContext(), ""+resultmsg[0], Toast.LENGTH_LONG).show();
        	        FirstScreen.tvIsConnected.setText(resultmsg[1]);
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
    
	/*-------------Point Declaration-------------------------*/
   class Point2D{
	public double[] x=new double[50];
	public double[] y=new double[50];
	public double[] centroid=new double[]{0,0};
	public int[] allRadious=new int[100];
	public int[] tRadious=new int[100];
    }
   
   /*--------------Start Sensor Data----------------------------*/
   public void onSensorChanged(SensorEvent sensorEvent) {
	     synchronized (this) {
	      if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	      }
	      
	      if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
	       orinX=sensorEvent.values[0];
	       orinY=sensorEvent.values[1];
	       orinZ=sensorEvent.values[2];       
	      }
	     }
	    }
 public void onAccuracyChanged(Sensor arg0, int arg1) {
	   // TODO Auto-generated method stub
	   
	  }
 
 @Override
 protected void onResume() {
	 super.onResume();
	 // Register this class as a listener for the accelerometer sensor
	 sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	 // ...and the orientation sensor
	 sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
 	}

 @Override
 protected void onStop() {
	 // Unregister the listener
	 sensorManager.unregisterListener(this);
	 super.onStop();
 	} 
 /*--------------End Sensor Data----------------------------*/
 
	}


