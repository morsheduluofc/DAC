package com.example.dacF;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import ucal.example.dacF.R;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
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


public class Level2 extends Activity implements OnTouchListener, OnClickListener,SensorEventListener {
  /*------------Declare all variable------------------*/
	int practice=0;
	int suggestX=0,suggestY=0,suggestradious=0,centerX,centerY;
  	double timeStart,timeEnd,timeDiff,reactStart,reactEnd,reactDiff;//For time calculation
	private VelocityTracker vTracker = null; //For velocity calculation
	int noofPixel;
	int clockWise;
	double startX,startY;
	int totalRound=0;
	double tengent1,tengent2,angeloffset;
	double finalleftx,finaltopy,finalrightx,	finalbottomy;
	int initial=1;
	double avgXVelocity,XVelocity, YVelocity,totaVelocity;
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    String out="Draw a circle.....";
    int dwawenable=1;
    Paint paint;
    double downx = 0, downy = 0, upx = 0, upy = 0;
    public static int round=0;
    double fdistance[]=new double[2048];
    double velocity[]=new double[2048];
    double orientation[]=new double[2048];
    double PRessure[]=new double[2048];
    double EVentSize[]=new double[2048];
    double alldata[]=new double[2048];
    double combinedata[]=new double[4096];
    double  pressure,eventsize,pres,events;
    TextView text;
    public double points[][]= new double[2048][2];
    public double vpoints[][]= new double[2048][2];
    public double orientationx[]= new double[2048];
    public double orientationy[]= new double[2048];
    public double orientationz[]= new double[2048];
    public double Pressure[]= new double[2048];
    public double EventSize[]= new double[2048];
    public static String store2="", output;
    
    int row=0, intersection=0;
    double intersectx=0, intersecty=0;
    double ldistancepointx=0,ldistancepointy=0;
    double fRadious=0,radiousDiff=0,centerDiff=0,startDiff=0;
    Point2D polygon= new Point2D();
    float orinX,orinY,orinZ,sorinX,sorinY,sorinZ,saveorientationX,saveorientationY,saveorientationZ;
    private SensorManager sensorManager = null;
    float level, density, hpixel, wpixel;
    int ctimes=0,constrain=0,errdist=64,estarting=32,etime=16,eradius=8,ecenter=4,evelocity=2,ereaction=1;
    public int timeConstrain[]= new int[100];
    int radi;
    
    
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private Path    mPath;
    private Path circlePath;
    
  /*---------------End Variable declaration---------------------*/  
    
   @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    radi= getIntent().getExtras().getInt("radi");
    RandomCircle();
    new Handler().postDelayed(new Runnable(){
		 public void run(){
			 activateListener();	
		 }
	 },1000);
    reactStart=System.currentTimeMillis();
   }
   
   void activateListener(){
	   setContentView(R.layout.level2);
	   dwawenable=1;
	   imageView = (ImageView) this.findViewById(R.id.imageView1);
	   Display currentDisplay = getWindowManager().getDefaultDisplay();
	    double dw = currentDisplay.getWidth();
	    double dh = currentDisplay.getHeight();
	    bitmap = Bitmap.createBitmap((int) dw, (int) dh,
	    Bitmap.Config.ARGB_8888);
	    canvas = new Canvas(bitmap);
	    paint = new Paint();
	    paint.setStrokeWidth(4);
	    text = (TextView) findViewById(R.id.textView1);
	    text.setText("Draw a circle in the boundary based on suggestion");
    	//text.setText("Draw an ideal circle in the given boundary.");
      	paint.setColor(Color.WHITE);
    	float left=0 * getResources().getDisplayMetrics().density;
    	float top=30 * getResources().getDisplayMetrics().density;
    	float right=360 * getResources().getDisplayMetrics().density;
    	float bottom=540 * getResources().getDisplayMetrics().density;
    	canvas.drawRect(left, top, right, bottom, paint);
	    paint.setColor(Color.GREEN);
	   imageView.setImageBitmap(bitmap);
	   imageView.setOnTouchListener(this);
	   View l4nextButton = this.findViewById(R.id.l4next_button);
	   l4nextButton.setOnClickListener(this);
	   View l4clearallButton = this.findViewById(R.id.l4clearall_button);
		l4clearallButton.setOnClickListener(this);
		View l4backButton = this.findViewById(R.id.l4back_button);
	    l4backButton.setOnClickListener(this);
	    
	    mPath = new Path();
	    // circlePaint = new Paint();
	    circlePath = new Path();
	   
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

  /*----------------Action Button on click-------------------*/
  public void onClick(View v) {
	  switch (v.getId()) {
	  case R.id.l4next_button:
		  tryagain();
		  if(radi==1 && round==10 && practice==0){
				Toast.makeText(Level2.this, "Minimum 40 rounds left", Toast.LENGTH_SHORT).show();
			}else if(radi==1 && round==25 && practice==0){
				Toast.makeText(Level2.this, "Minimum 25 rounds left", Toast.LENGTH_SHORT).show();
			}else if(radi==1 && round==45 && practice==0){
				Toast.makeText(Level2.this, "Minimum 5 rounds left", Toast.LENGTH_SHORT).show();
			}else if(radi==1 && round==50 && practice==0){
				Toast.makeText(Level2.this, "You can exit from level 1", Toast.LENGTH_SHORT).show();
			}
			
			if(radi==2 && round==10){
				Toast.makeText(Level2.this, "Minimum 10 rounds left", Toast.LENGTH_SHORT).show();
			}else if(radi==2 && round==15){
				Toast.makeText(Level2.this, "Minimum 5 rounds left", Toast.LENGTH_SHORT).show();
			}else if(radi==2 && round==20){
				Toast.makeText(Level2.this, "You can exit from level 1", Toast.LENGTH_SHORT).show();
			}
  	   break;
	  	  case R.id.l4clearall_button:
	  		ClearAllData();
		  break;
	  case R.id.l4back_button:
	  		
	  		if(radi==1 && round<50 && practice==0){
	  			Toast.makeText(Level2.this, "Complete minimum 50 rounds", Toast.LENGTH_LONG).show();
	  			return;
	  		 }
	  		if(radi==2 && round<20){
	  			Toast.makeText(Level2.this, "Complete minimum 20 rounds", Toast.LENGTH_LONG).show();
	  			return;
	  		 }
	  		 
	  		if(radi==1 || radi==2){
	  		Toast.makeText(Level2.this, "Your have completed all levels.\n Now you can send data", Toast.LENGTH_LONG).show();
			  CombineAllFeatures();
	  		}
	  	 finish();
			  break;
	  	  }
	  }
    /*----------------End Action Button on click-------------------*/
  
  
  /*-------------Point Declaration-------------------------*/
    class Point2D{
		public double[] x=new double[50];
		public double[] y=new double[50];
		public double[] centroid=new double[]{0,0};
		public int[] allRadious=new int[100];
		public int[] tRadious=new int[100];
	}
  /*-------------End Point Declaration-------------------------*/ 
    
  
  /*--------------Clear screen to draw again-------------------*/ 
    void tryagain(){
    	bitmap.eraseColor(Color.TRANSPARENT);
  	    paint.reset();
  	    imageView.invalidate();
  	    row=0;
  	    intersection=0;
  	   dwawenable=1;
    	RandomCircle();
	    new Handler().postDelayed(new Runnable(){
			 public void run(){
				 activateListener();	
			 }
		 },1000);
	    reactStart=System.currentTimeMillis();     
    }
   /*--------------End clear screen to draw again-------------------*/
    
    /*--------------Clear all data form temp storage---------------*/
    void ClearAllData(){
    	bitmap.eraseColor(Color.TRANSPARENT);
  	    paint.reset();
  	    imageView.invalidate();
  	    row=0;
  	    intersection=0;
  	    dwawenable=1;
  	    round=0;
    	RandomCircle();
	    new Handler().postDelayed(new Runnable(){
			 public void run(){
				 activateListener();	
			 }
		 },1000);
	    reactStart=System.currentTimeMillis();
    }  
   
    
   /*-------------Suggestion----------------------------------------*/ 
    void RandomCircle() {
    	setContentView(R.layout.level2);
        imageView = (ImageView) this.findViewById(R.id.imageView1);
        Display currentDisplay = getWindowManager().getDefaultDisplay();
        double dw = currentDisplay.getWidth();
        double dh = currentDisplay.getHeight();
        bitmap = Bitmap.createBitmap((int) dw, (int) dh,
        Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        imageView.setImageBitmap(bitmap);
        
        View l4nextButton = this.findViewById(R.id.l4next_button);
    	
    	View l4clearallButton = this.findViewById(R.id.l4clearall_button);
    	
        View l4backButton = this.findViewById(R.id.l4back_button);
    	
        text = (TextView) findViewById(R.id.textView1);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
       
    	dwawenable=0;
    	text.setText("Draw a circle in the given boundary based on suggestion");
    	//text.setText("Draw an ideal circle in the given boundary.");
      	paint.setColor(Color.WHITE);
    	float left=0 * getResources().getDisplayMetrics().density;
    	float top=30 * getResources().getDisplayMetrics().density;
    	float right=360 * getResources().getDisplayMetrics().density;
    	float bottom=540 * getResources().getDisplayMetrics().density;
    	canvas.drawRect(left, top, right, bottom, paint);
        paint.setStyle(Paint.Style.STROKE);
      	paint.setColor(Color.RED);
      	
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
      	float plcenx=130,plceny=170,phcenx=230,phceny=380,plred=80,phred=130;
      	plcenx=plcenx* getResources().getDisplayMetrics().density;
      	plceny=plceny* getResources().getDisplayMetrics().density;
      	phcenx=phcenx* getResources().getDisplayMetrics().density;
      	phceny=phceny* getResources().getDisplayMetrics().density;
      	plred=plred* getResources().getDisplayMetrics().density;
      	phred=phred* getResources().getDisplayMetrics().density;
      	centerX = rand.nextInt(((int)phcenx -(int)plcenx) + 1) +(int) plcenx;
      	centerY = rand.nextInt(((int)phceny - (int)plceny) + 1) + (int)plceny;
      	suggestradious=rand.nextInt(((int)phred - (int)plred) + 1) + (int)plred;
      	int ang=rand.nextInt((360 - 0) + 1) + 0;;
      	suggestX=(int)(centerX+suggestradious*Math.sin(Math.PI*ang/180.0));
      	suggestY=(int)(centerY+suggestradious*Math.cos(Math.PI*ang/180.0));
      	paint.setStrokeWidth(3);
      	paint.setTextSize(50);
      	paint.setColor(Color.RED);
      	canvas.drawText("S", suggestX+10, suggestY+10, paint);
      	canvas.drawCircle(suggestX, suggestY, 6, paint);
      	paint.setColor(Color.BLUE);
      	canvas.drawText("C", centerX+10, centerY+10, paint);
      	canvas.drawCircle(centerX, centerY, 6, paint);
      	paint.setColor(Color.GRAY);
      	canvas.drawLine(centerX, centerY, suggestX, suggestY, paint);
      	double leftx=centerX;
    	leftx=leftx-suggestradious;
    	double topy=centerY;
    	topy=topy-suggestradious;
    	double rightx=leftx+(2*suggestradious);
    	double bottomy=topy+(2*suggestradious);
      	RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
      	paint.setStyle(Paint.Style.STROKE);
    	paint.setColor(Color.RED);
    	canvas.drawOval(ovalBounds, paint);
        } catch(NoSuchAlgorithmException nsae) {
            // Process the exception in some way or the other
        }
    }
   /*-------------End Suggestion----------------------------------------*/
    
 void waittime(){
	 setContentView(R.layout.level2);
	 new Handler().postDelayed(new Runnable(){
		 public void run(){
			 //setContentView(R.layout.level4);
			 RandomCircle();
		 }
	 },3000);
 }
  
  
  /*-------Calculate radius of user drawing circle----------*/
  double Radious(){
	  double maxdistance=0,d=0;
	  for(int i=0;i<row;i++){
		 	 d=Math.sqrt(Math.pow((intersectx-points[i][0]), 2)+Math.pow((intersecty-points[i][1]), 2));
		 if(d>maxdistance){
			 maxdistance=d;
			 ldistancepointx=points[i][0];
			 ldistancepointy=points[i][1];
		 }
	  }
	  return (maxdistance/2);
	  
  }
  /*-------End calculate radius of user drawing circle----------*/
  
  
  /*-----------------Validation Check------------------------*/
  void validation(){
	  for(int j=0;j<row;j++){
		  double xbound=points[j][0]/getResources().getDisplayMetrics().density;
		  double ybound=points[j][1]/getResources().getDisplayMetrics().density;
		  if(xbound<0 ||xbound>360 ||ybound<30 ||ybound>540){
			  Toast.makeText(Level2.this, "Your drawing is out of boundary", Toast.LENGTH_SHORT).show();
			  tryagain();
		  }
	  }
  }
  
  /*------------------------Start 5---------------------------------*/
  //Random point selection to calculate error
  void randomLine(float cx,float cy){
	  double ang=0.0,x1,y1,ax1,ay1,errordist,tempdist=0,ux=0,uy=0;
	  double x,y,m,c,A,B,C,D,check,insecx,insecy,plusinsecx,minusinsecx,minuinsecx,plusinsecy,minusinsecy,distplus,distminus,dist;
	  radiousDiff=Math.abs(suggestradious-fRadious);
	  //Log.v("","Radius Diff:"+radiousDiff);
	  if(radiousDiff<0 || radiousDiff>125){
	    	  constrain=(constrain | eradius);
	      }
      centerDiff=Math.sqrt(Math.pow(polygon.centroid[0]-centerX, 2)+Math.pow(polygon.centroid[1]-centerY, 2));
      //Log.v("","Center Diff:"+centerDiff);
      if(centerDiff<0 || centerDiff>75){
    	  constrain=(constrain | ecenter);
    	  //return;
      }
      startDiff=Math.sqrt(Math.pow(points[0][0]-suggestX, 2)+Math.pow(points[0][1]-suggestY, 2));
      //Log.v("","Start Difference:"+startDiff);
      if(startDiff<0 || startDiff>125){
          constrain=(constrain | estarting);
      	  //return;
        }
      for(int i=0;i<10;i++,ang+=36){
		  x1=cx+(fRadious*Math.sin(Math.PI*ang/180.0));
		  y1=cy+(fRadious*Math.cos(Math.PI*ang/180.0));
		  
		  ax1=centerX+(suggestradious*Math.sin(Math.PI*ang/180.0));
		  ay1=centerY+(suggestradious*Math.cos(Math.PI*ang/180.0));
		   idealCircle(2);
		  //tempdist=Math.sqrt(Math.pow(Math.abs(x1-ax1),2)+Math.pow(Math.abs(y1-ay1),2));
		  //Log.v("", "Angel:"+x1+"  "+y1+"  "+cx+"  "+cy);
		 errordist=99999;
		  int k=0;
		  for(int j=0;j<row;j++){
			 tempdist=Math.sqrt(Math.pow(Math.abs(ax1-points[j][0]),2)+Math.pow(Math.abs(ay1-points[j][1]),2));
			  if(tempdist<errordist){
				  errordist=tempdist;
				    ux=points[j][0];
				    uy=points[j][1];
				   k=j;
			  }
		  }
		  if(errordist<0 || errordist>125){
			  	 constrain=(constrain | errdist);
			  	  //return;
			    }
			  Log.v("","Errror:" +i+" "+errordist);
		   int tround=round*10+i;
		   int tori=round*30+3*i;
		   int tvel=round*20+2*i;
		   fdistance[tround]=errordist*(160/density);
		   ///Store velocity data 
		   velocity[tvel]=vpoints[k][0];
		   velocity[tvel+1]=vpoints[k][1];
		   //Log.v("","Velocity:"+totaVelocity);
		   PRessure[tround]=Pressure[k];
		   Log.v("","Pressure:"+Pressure[k]);
		   EVentSize[tround]=EventSize[k];
		   Log.v("","Event:"+EventSize[k]);
		   orientation[tori]= orientationx[k];
		   orientation[tori+1]= orientationy[k];
		   orientation[tori+2]= orientationz[k];
		   Log.v("","Orientation:"+orientationx[k]+" "+orientationy[k]+" "+orientationz[k]);
	   	  paint.setColor(Color.RED);
	   	  canvas.drawLine((float)ax1,(float) ay1, (float)ux, (float)uy, paint);
	   	if((radi==1 && round>=49)||(radi==2 && round>=19)||(radi==0 && round>=29)){
			  out="Stop!!";
		  } else if(radi==1 && practice==0){
	   	  out="Round:"+(round+1)+". Remaining:"+(49-round);
		  } 
		  else if(radi==2){
		  out="Round:"+(round+1)+". Remaining:"+(19-round);  
		  }else if(radi==0){
			  out="Round:"+(round+1)+".Maximum Try:30";
		  }
 	 }
	  totalRound=round;
      alldata[totalRound*6+0]=startDiff*(160/density);//Start of x
      alldata[totalRound*6+1]=centerDiff*(160/density);
      
      if(angeloffset>=0){
			alldata[totalRound*6+2]=-1;
			//Log.v("","Rotation:-1");
			}
		else {
			alldata[totalRound*6+2]=1.0;
			//Log.v("","Rotation:1");	
		}
      alldata[totalRound*6+3]=7*timeDiff/(44*fRadious);
       alldata[totalRound*6+4]=radiousDiff*(160/density);
  	   alldata[totalRound*6+5]=reactDiff;
  	if(constrain>0){
        Log.v("","Constrain:"+constrain);
 	   Toast.makeText(Level2.this, "Violation the constrain,try again", Toast.LENGTH_SHORT).show();
 	   timeConstrain[ctimes]=constrain;
 	   ctimes++;
 	   constrain=0;
 	   tryagain();
 	   }else{
     round++;
     text.setText(out);
 	   }
      
  }
  /*--------------------End 5-------------------*/
  
  
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
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.GRAY);
		canvas.drawOval(ovalBounds, paint);
		randomLine((float)(leftx+fRadious), (float)(topy+fRadious));///Start 5
		}
		if(i==2){
			double leftx=centerX;
			leftx=leftx-suggestradious;
			double topy=centerY;
			topy=topy-suggestradious;
			double rightx=leftx+(2*suggestradious);
			double bottomy=topy+(2*suggestradious);
			if(initial==1){
				finalleftx=leftx;
				finaltopy=topy;
				finalrightx=rightx;
				finalbottomy=bottomy;
				initial=0;
			}
		RectF ovalBounds=new RectF((float)leftx,(float)topy,(float)rightx,(float)bottomy);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.RED);
		canvas.drawOval(ovalBounds, paint);
		//randomLine(leftx+fRadious, topy+fRadious);///Start 5
	
		}
		
  }
/*-------------------End 4--------------------------*/
  
/*-------------------Start 3-----------------------*/
 // Polygon center Calculation + Ideal circle drawing
  void compute2DPolygonCentroid(){
		int signedArea=0;
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
		//Calculate the maximum time of radious and its position 
		 int maxRadious=1,maxposition=0;
		for(i=0;i<radiousList;i++){
			int localmax=polygon.tRadious[i];
			if(localmax>maxRadious){
				maxRadious=localmax;
				maxposition=i;
			}
		}
		//if all radious are at one times then calculate average radious
				int avgRadious=0;
				if(maxRadious==1){
				for(i=0;i<radiousList;i++){
					avgRadious+=polygon.allRadious[i];
				}
				avgRadious=avgRadious/radiousList;
				fRadious=avgRadious;
				}
				else
					fRadious=polygon.allRadious[maxposition];
		for(i=0;i<radiousList;i++){
			System.out.print(polygon.allRadious[i]+" "+polygon.tRadious[i]);
			System.out.println(" "+maxRadious+" "+maxposition+" "+fRadious);
		}
		
		idealCircle(1); //Start 4
  }
 /*-----------------------End 3---------------------*/
  
  
 /*----------------------Start 2----------------------*/
//Draw a Polygon
void drawAPolygon(){
	int totalPoint=row,finalPoint=0;
	paint.setStyle(Paint.Style.STROKE);
	paint.setColor(Color.BLUE);
	double polygonPoint[][]=new double[5][5];
	polygonPoint[0][0]=points[0][0];
	polygonPoint[0][1]=points[0][1];
	/// five random points and then draw polygon
	for(int i=1;i<5;i++){
		polygonPoint[i][0]=points[i*(totalPoint/5)][0];
		polygonPoint[i][1]=points[i*(totalPoint/5)][1];
		finalPoint=i;
	}
   	for(int i=0;i<5;i++){
		polygon.x[i]=polygonPoint[i][0];
		polygon.y[i]=polygonPoint[i][1];
	}
	compute2DPolygonCentroid(); /// Go to the Start 3
	
}
/*--------------------- End Start 2-------------------------*/


/*----------------Combine All Features----------------------*/
void CombineAllFeatures(){
	store2="";
	int rounds=0;
	if(radi==1){
	    rounds=50;
		}else if(radi==2){
			rounds=20;
		}
	
	for(int j=0;j<rounds;j++){
    	store2+=("L-2:"+String.valueOf(rounds));
		for(int i=0;i<10;i++){
			store2+=","+fdistance[j*10+i];
				}
		for(int i=0;i<20;i++){
			store2+=","+velocity[j*20+i];
				}
		for(int i=0;i<10;i++){
			store2+=","+PRessure[j*10+i];
		       }
		for(int i=0;i<10;i++){
			store2+=","+EVentSize[j*10+i];
			   }
		for(int i=0;i<30;i++){
			store2+=","+orientation[j*30+i];
				}
		for(int i=0;i<6;i++){
			store2+=","+alldata[j*6+i];
				}
		store2+=","+timeConstrain[j];
		store2+="\n";
	}
	//Level1.fStore=Level1.fStore.concat(store2);
	
	Log.v("","All Data1:"+Level1u.fStore);
	Log.v("","All Data2:"+Level1u.store1);
	Log.v("","All Data3:"+store2);
		
 }

//Store the user drawing data in an array
void Storedata(double x,double y,double vx,double vy,double lsorinX,double lsorinY,double lsorinZ,double lp,double les){
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

/*-----------Improvement of Drawing------------------*/
void touch_start(float x, float y) {
    mPath.reset();
    mPath.moveTo(x, y);
    mX = x;
    mY = y;
}

void touch_move(float x, float y) {
    float dx = Math.abs(x - mX);
    float dy = Math.abs(y - mY);
    
    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
    	
        mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
        //mPath.reset();
        //mPath.moveTo(x, y);
        mX = x;
        mY = y;

        circlePath.reset();
       // canvas.drawPath(circlePath, paint);
        circlePath.addCircle(mX, mY, 5, Path.Direction.CW);
        canvas.drawPath(circlePath, paint);
        //canvas.drawPath(mPath, paint);
    }
}
void touch_up() {
    mPath.lineTo(mX, mY);
    circlePath.reset();
    // commit the path to our offscreen
    canvas.drawPath(mPath,  paint);
    // kill this so we don't double draw
    mPath.reset();
}





/*--------------------- Start 1----------------------------*/
   public boolean onTouch(View v, MotionEvent event) {
	   int index = event.getActionIndex();
       int action = event.getActionMasked();
       int pointerId = event.getPointerId(index);
	   int totalpoint=1;
       if(dwawenable==1){
    	 paint.setStrokeWidth(4);
    	 paint.setColor(Color.GREEN);
    	switch (action) {
         case MotionEvent.ACTION_DOWN:{
        	 timeStart=System.currentTimeMillis();
        	 //timeEnd=timeStart; 
             /*------------Velocity initialization-----*/
             noofPixel=0;
             if(vTracker == null) {
                 vTracker = VelocityTracker.obtain();
             }
             else {
                 vTracker.clear();
             }
             vTracker.addMovement(event);
             double x = event.getX();
             double y = event.getY();
             XVelocity=Math.abs(vTracker.getXVelocity(pointerId));
             YVelocity=Math.abs(vTracker.getYVelocity(pointerId));
             touch_start((float)x,(float)y);
             canvas.drawPath(mPath, paint);
             imageView.invalidate();
             downx=x;
             downy=y;
             sorinX=orinX;
             sorinY=orinY;
             sorinZ=orinZ;
             pres=event.getPressure(pointerId);
             events=event.getSize(pointerId);
             Storedata(downx,downy,XVelocity,YVelocity,sorinX,sorinY,sorinZ,pres,events);
                  break;
            }
         
        case MotionEvent.ACTION_MOVE: {
        	int historySize = event.getHistorySize();
        	vTracker.addMovement(event);
            vTracker.computeCurrentVelocity(1000);
             	// a pointer was moved
            for (int i = 0; i < historySize; i++) {
                double x= event.getHistoricalX(i);
                double y = event.getHistoricalY(i);
                XVelocity=Math.abs(vTracker.getXVelocity(pointerId));
                YVelocity=Math.abs(vTracker.getYVelocity(pointerId));
                double avgVelocity=(XVelocity+YVelocity)/2;
                //canvas.drawLine((float)downx, (float)downy, (float)x, (float)y, paint); //Draw user circle
                touch_move((float)x,(float)y);
                
                //imageView.invalidate();
                imageView.invalidate();
                downx=x;
                downy=y;
                sorinX=orinX;
                sorinY=orinY;
                sorinZ=orinZ;
                pres=event.getPressure(pointerId);
                events=event.getSize(pointerId);
                //Log.v("",+downx+" "+downy+" "+Math.abs(vTracker.getXVelocity(pointerIndex))+" "+Math.abs(vTracker.getYVelocity(pointerIndex)));
                Storedata(downx,downy,XVelocity,YVelocity,sorinX,sorinY,sorinZ,pres,events);
            }
                break;
            }
        
        case MotionEvent.ACTION_UP:{
        	// touch_up();
        	//imageView.invalidate();
        	timeEnd=System.currentTimeMillis();
        	timeDiff=(timeEnd-timeStart)/ 1000.0;
        	reactDiff=(timeStart-reactStart)/ 1000.0;
        	if(timeDiff<0 || timeDiff>5){
   			   constrain=(constrain | etime);
   			  
   		    }
        	
        	if(reactDiff<0 || reactDiff>10){
     		   constrain=(constrain | ereaction);
     	    }
        	    validation();
        	    dwawenable=0;
        		drawAPolygon();  /// Next Start2. 
        	
        }
        case MotionEvent.ACTION_CANCEL:{
        }
    }
    return true;
    }

   return true;
  }
}
