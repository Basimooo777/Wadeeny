package app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Search extends Activity {

	private Button searchButton;
	private Button sourceLocatedCheckBox;
	private Button destinationLocatedCheckBox;
	Double geoLat = 0.0;
	Double geoLng = 0.0;
	int selectedLocation = -1;	 	// -1 ==> Nothing
									// 	0 ==> Location = source
									// 	1 ==> Location = destination

	/**
	 * ========================================================================
	 * method onCreate()
	 * ========================================================================
	 */
	@Override
	public void onCreate(Bundle onSavedInstanceState) {

		super.onCreate(onSavedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);

		createGPS();
		findButtons();
		addListener();

	} // end method onCreate

	private void findButtons() {
		searchButton = (Button) findViewById(R.id.searchView_search_button);
		sourceLocatedCheckBox = (Button) findViewById(R.id.searchView_sourceLocated_checkBox);
		destinationLocatedCheckBox = (Button) findViewById(R.id.searchView_destinationLocated_checkBox);
	}

	private void addListener() {
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String srcDistrict = "", destDistrict = "", srcPoint = "", destPoint = "";
				EditText et;
				Intent intent = new Intent(Search.this, SplashScreen.class);
				if (selectedLocation == 0) {		// search with current location as source
					srcPoint = geoLat + "," + geoLng;
					et = (EditText) findViewById(R.id.searchView_destination_editText);
					destDistrict = et.getText().toString();
					destDistrict = destDistrict.replace(' ', '+');
				} else if (selectedLocation == 1) { // search with current location as destination
					
					et = (EditText) findViewById(R.id.searchView_source_editText);
					srcDistrict = et.getText().toString();
					srcDistrict = srcDistrict.replace(' ', '+');
					destPoint = geoLat + "," + geoLng;

				} else {
					et = (EditText) findViewById(R.id.searchView_source_editText);
					srcDistrict = et.getText().toString();
					srcDistrict = srcDistrict.replace(' ', '+');
					et = (EditText) findViewById(R.id.searchView_destination_editText);
					destDistrict = et.getText().toString();
					destDistrict = destDistrict.replace(' ', '+');
				}
				intent.putExtra("src", srcDistrict);
				intent.putExtra("dest", destDistrict);
				intent.putExtra("p_src", srcPoint);
				intent.putExtra("p_dest", destPoint);
				((Activity) Search.this).finish();
				Search.this.startActivity(intent);
			}
		});

		sourceLocatedCheckBox.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBox = (CheckBox) findViewById(R.id.searchView_sourceLocated_checkBox);
				if (checkBox.isChecked()) {
					findViewById(R.id.searchView_source_editText).setEnabled(false);
					findViewById(R.id.searchView_destination_editText).setEnabled(true);
					CheckBox destCheckBox = (CheckBox) findViewById(R.id.searchView_destinationLocated_checkBox);
					destCheckBox.setChecked(false);
					selectedLocation = 0;
				} else {
					findViewById(R.id.searchView_source_editText).setEnabled(true);
					selectedLocation = -1;
				}

			}
		});

		destinationLocatedCheckBox.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBox = (CheckBox) findViewById(R.id.searchView_destinationLocated_checkBox);
				if (checkBox.isChecked()) {
					findViewById(R.id.searchView_destination_editText).setEnabled(false);
					findViewById(R.id.searchView_source_editText).setEnabled(true);
					CheckBox srcCheckBox = (CheckBox) findViewById(R.id.searchView_sourceLocated_checkBox);
					srcCheckBox.setChecked(false);
					selectedLocation = 1;
				} else {
					findViewById(R.id.searchView_destination_editText).setEnabled(true);
					selectedLocation = -1;
				}
			}
		});
	}
	
	private void createGPS() {
		final LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (location != null) {		// Update the map location.
					geoLat = location.getLatitude();
					geoLng = location.getLongitude();
				}
			}

			public void onProviderDisabled(String provider) {
				geoLat = 0.0;
				geoLng = 0.0;
			}

			public void onProviderEnabled(String provider) {

			}

			public void onStatusChanged(String provider, int status, Bundle extras) {

			}
		};
		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(context);
		String provider = LocationManager.GPS_PROVIDER;
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {			// Update the map location.
			geoLat = location.getLatitude();
			geoLng = location.getLongitude();
		}
		locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
		Log.i("locLAST", geoLat + " " + geoLng);
	}

}
