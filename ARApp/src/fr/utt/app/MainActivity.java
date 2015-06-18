package fr.utt.app;

import fr.utt.ar.CustomARSetup;
import gl.GL1Renderer;
import gl.GLCamera;
import gl.GLFactory;
import system.ArActivity;
import system.CameraView;
import system.ConcreteSimpleLocationManager;
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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

	private Button locationButton;
	private Button arActivityButton;
	private ConcreteSimpleLocationManager simpleLocationManager;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float azimut;
    private float pitch;
    private float roll; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		//Initialisation du LocationManager
		this.simpleLocationManager = new ConcreteSimpleLocationManager(this);

		//Récupération des boutons
		this.locationButton = (Button) findViewById(R.id.buttonLocation);
    	this.arActivityButton = (Button) findViewById(R.id.buttonARActivity);

    	//Ajout des listeners sur les boutons
		this.locationButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	String location = simpleLocationManager.getCurrentLocation().toString();
		    	GLCamera camera = new GLCamera();
		    	
		    	CameraView camerav = new CameraView(v.getContext());
		    	Float posX = camerav.getRotationX();
		    	Float posY = camerav.getRotationY();
		    	String cameraData = camera.getMyNewPosition().toString();
		    	
		    	location = location.concat("Rotation en X : " + posX + "  Rotation y : " + posY + "  ");
		    	
		    	if(null != cameraData && !cameraData.equals("")) {
		    		location = location.concat(cameraData);
		    	} else {
		    		location = location.concat("no data for camera");
		    	}
		        
		        SensorManager mSensorManager;
		        Sensor mSensor;
		        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		        float[] rotation = new float[4];
		        float[] i = new float[4];
		        
		        boolean success = mSensorManager.getRotationMatrix(rotation, i, mGravity, mGeomagnetic);
		        if(success) {
		        	location = location.concat(rotation.toString());
		        } else {
		        	location = location.concat("bug sensor rotation");
		        }
		        
		        location = location.concat(" Azimut : " + azimut + "  Pitch : "+ pitch + "  Roll : " + roll);
		        

		    	TextView textviewLocation = (TextView) findViewById(R.id.textviewLocation);
		        textviewLocation.setText(location);
		    }
		});
		
		this.arActivityButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	//Ouverture d'une ARActivity
		    	ArActivity.startWithSetup(MainActivity.this, new CustomARSetup(){

					@Override
					public void addObjectsTo(GL1Renderer renderer, World world, GLFactory objectFactory) {
						world.add(objectFactory.newSolarSystem(new Vec(10, 10, 0)));					
					}

				});
		    }
		});
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
}
