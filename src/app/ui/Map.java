package app.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ExpandableListView;
import app.data.SubRoute;
import app.util.RouteManager;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class Map extends MapActivity {

	private MapView mapView;
	private MapController mapController;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_and_map_view);

		mapView = (MapView) findViewById(R.id.mapview);
		mapController = mapView.getController();

		ExpandableListView l = (ExpandableListView) findViewById(R.id.list);
		ArrayList<ArrayList<SubRoute>> s = RouteManager.getRoute();
		ExpandableList adapter = new ExpandableList(this, s, mapView);
		l.setAdapter(adapter.getExpListAdapter());
		adapter.setListeners(l);

		mapView.setBuiltInZoomControls(true);
		GeoPoint point = new GeoPoint(31227462, 29973445); // Alexandria City with Zoom Level 16
		mapController.animateTo(point);
		mapController.setZoom(14);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
