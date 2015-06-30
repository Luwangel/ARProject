package fr.utt.app;

import java.util.ArrayList;

import fr.utt.ar.CustomARSetup;
import fr.utt.data.ImageResource;
import fr.utt.data.JSONFileManager;
import fr.utt.data.JSONParser;
import fr.utt.data.POI;
import fr.utt.data.Resource;
import fr.utt.data.ResourceType;
import fr.utt.data.TextResource;
import gl.GL1Renderer;
import gl.GLCamera;
import gl.GLFactory;
import system.ArActivity;
import system.CameraView;
import util.IO;
import util.Vec;
import worldData.World;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener, LocationListener {

	public static String TAG = "MainActivity";
	
	public static int FREQUENCY_LOCATION_UPDATE = 10000;
	
	/* Interface */
	private Button showDatasButton;
	private Button arActivityButton;
		
	/* Géolocalisation */
	private LocationManager lm;
    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float azimut;
    private float pitch;
    private float roll; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Récupération des boutons
		this.showDatasButton = (Button) findViewById(R.id.buttonShowDatas);
    	this.arActivityButton = (Button) findViewById(R.id.buttonARActivity);

    	//Ajout des listeners sur les boutons
		this.showDatasButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	String datas = "";
		    	
		    	GLCamera camera = new GLCamera();
		    	
		    	CameraView camerav = new CameraView(v.getContext());
		    	Float posX = camerav.getRotationX();
		    	Float posY = camerav.getRotationY();
		    	String cameraData = camera.getMyNewPosition().toString();
		    	
		    	datas = datas.concat("\nRotation en X : " + posX + "  Rotation y : " + posY + "  ");
		    	
		    	if(null != cameraData && !cameraData.equals("")) {
		    		datas = datas.concat(cameraData);
		    	} else {
		    		datas = datas.concat("no data for camera");
		    	}
		        
		        SensorManager mSensorManager;
		        Sensor mSensor;
		        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		        
		        float[] rotation = new float[4];
		        float[] i = new float[4];
		        mGravity = new float[4];
		        mGeomagnetic = new float[4];
		        
		        boolean success = mSensorManager.getRotationMatrix(rotation, i, mGravity, mGeomagnetic);
		        if(success) {
		        	datas = datas.concat(rotation.toString());
		        } else {
		        	datas = datas.concat("bug sensor rotation");
		        }
		        
		        datas = datas.concat(" Azimut : " + azimut + "  Pitch : "+ pitch + "  Roll : " + roll);
		        

		    	TextView textviewDatas = (TextView) findViewById(R.id.textviewDatas);
		        textviewDatas.setText(datas);
		        
		        
		    }
		});
		
		this.arActivityButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	//Ouverture d'une ARActivity
		    	ArActivity.startWithSetup(MainActivity.this, new CustomARSetup(){

					@Override
					public void addObjectsTo(GL1Renderer renderer, World world, GLFactory objectFactory) {
						Bitmap image = IO.loadBitmapFromURL("http://t3.gstatic.com/images?q=tbn:ANd9GcSXqX5OmNOtEo-Mmhp8H1axjvq-cBq5zlYQnJomegQ76xlnlzM2cv42y4Tw");              
						world.add(objectFactory.newTexturedSquare("image test", image, 10));
						world.add(objectFactory.newArrow());
					}
				});
		    }
		});
		
		this.initDatas();
	}

	
	@Override
	protected void onResume() {
	    super.onResume();
	    
	    initLocationManager();
	}
	 
	@Override
	protected void onPause() {
	    super.onPause();
	    lm.removeUpdates(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/* Implémentation de SensorEventListener */
	
	@Override
	public void onSensorChanged(SensorEvent event) {if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	      mGravity = event.values;
	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
	      mGeomagnetic = event.values;
	    if (mGravity != null && mGeomagnetic != null) {
	      float R[] = new float[9];
	      float I[] = new float[9];
	      boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
	      if (success) {
	        float orientation[] = new float[3];
	        SensorManager.getOrientation(R, orientation);
	        azimut = orientation[0]; // orientation contains: azimut, pitch and roll
	        pitch = orientation[1];
	        roll = orientation[2];
	      }
	    }
//	    mCustomDrawableView.invalidate();
	   }
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	
	/* Implémentation de LocationListener */
	 
    @Override
    public void onLocationChanged(Location location) {
        
    	latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();
 
        //Affichage de la nouvelle position
        
        Toast.makeText(this, "Location changed :" + " latitude" + latitude + " longitude" + longitude, Toast.LENGTH_LONG).show();
    
    	TextView textviewLocation = (TextView) findViewById(R.id.textviewLocation);
        textviewLocation.setText(location.toString());
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    	
        Toast.makeText(this, "Provider disabled : " + provider, Toast.LENGTH_SHORT).show();
    }
 
    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(this, "Provider enabled : " + provider, Toast.LENGTH_SHORT).show();
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    	String newStatus = "";
        
    	switch (status) {
        case LocationProvider.OUT_OF_SERVICE:
            newStatus = "OUT_OF_SERVICE";
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
            newStatus = "TEMPORARILY_UNAVAILABLE";
            break;
        case LocationProvider.AVAILABLE:
            newStatus = "AVAILABLE";
            break;
        }

        Toast.makeText(this, "Status of the location provider changed : " + newStatus, Toast.LENGTH_SHORT).show();
    }
    
    
    /* Méthodes */
    
    private void initLocationManager() {
    

	    //Initialisation du LocationManager à chaque ouverture (ou réouverture) de l'activity
	    lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
	    	    
	    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MainActivity.FREQUENCY_LOCATION_UPDATE, 0, this);
	    }
	    else {
	    	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MainActivity.FREQUENCY_LOCATION_UPDATE, 0, this);
	    }
    }
    
    private void initDatas() {
		
		JSONFileManager jfm = new JSONFileManager(this);
		
		POI poi = new POI();
		poi.setName("Université de Technologie de Troyes");
		poi.setLocation(new fr.utt.data.Location(this.latitude,this.longitude,this.altitude));
		
		ArrayList<Resource> listResources = new ArrayList<Resource>();
		ImageResource r1 = new ImageResource();
		r1.setType(ResourceType.IMAGE);
		r1.setAuthor("Adrien");
		r1.setDescription("Bibliothèque");
		r1.setURLImage("http://t3.gstatic.com/images?q=tbn:ANd9GcSXqX5OmNOtEo-Mmhp8H1axjvq-cBq5zlYQnJomegQ76xlnlzM2cv42y4Tw");
		
		listResources.add(r1);
		
		TextResource r2 = new TextResource();
		r2.setType(ResourceType.TEXT);
		r2.setAuthor("Juju");
		r2.setDescription("Partiel");
		r2.setContent("20/20");
		
		listResources.add(r2);
		
		poi.setResources(listResources);
		
		try {
			jfm.CreateFileOnInternalStorage("POITest",JSONParser.exportPOI(poi));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.d("JSON", jfm.ReadFileFromInternalStorage("POITest"));
    }
}
