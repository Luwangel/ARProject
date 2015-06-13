package fr.utt.app;

import gl.GL1Renderer;
import gl.GLFactory;
import system.ArActivity;
import system.ConcreteSimpleLocationManager;
import system.DefaultARSetup;
import util.Vec;
import worldData.World;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button locationButton;
	private Button arActivityButton;
	private ConcreteSimpleLocationManager simpleLocationManager;
	
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
		    	TextView textviewLocation = (TextView) findViewById(R.id.textviewLocation);
		        textviewLocation.setText(location);
		    }
		});
		
		this.arActivityButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	//Ouverture d'une ARActivity
		    	ArActivity.startWithSetup(MainActivity.this, new DefaultARSetup(){

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
}
