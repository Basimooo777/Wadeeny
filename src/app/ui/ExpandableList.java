package app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import app.data.SubRoute;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ExpandableList extends ExpandableListActivity {
	
	private Context context;
	SimpleExpandableListAdapter expListAdapter;
	private ArrayList<ArrayList<SubRoute>> data;
	MapView mapview;

	public ExpandableList(Context l, ArrayList<ArrayList<SubRoute>> data, MapView mapView) {
		this.context = l;
		this.data = data;
		this.mapview = mapView;
		expListAdapter = new SimpleExpandableListAdapter (
				l,
				createGroupList(), 							// Creating group List.
				R.layout.group_row, 						// Group item layout XML.
				new String[] { "main" }, 					// the key of group item.
				new int[] { R.id.row_name }, 				// ID of each group item.-Data under the key goes into this TextView.
				createChildList(), 							// childData describes second-level entries.
				R.layout.child_row, 						// Layout for sub-level entries(second level).
				new String[] { "src", "dest", "trans" }, 	// Keys in childData maps to display.
				new int[] { 								// Data under the keys above go into these TextViews.
					R.id.grp_child, 
					R.id.grp_child2, 
					R.id.grp_child3 } 
		);

	}

	public SimpleExpandableListAdapter getExpListAdapter() {
		return expListAdapter;
	}

	public void setExpListAdapter(SimpleExpandableListAdapter expListAdapter) {
		this.expListAdapter = expListAdapter;

	}

	private String sum(int i, int type) {
		double sum = 0.0;
		switch (type) {
		case 0: // Cost
			for (int j = 0; j < data.get(i).size(); j ++) 
				if (data.get(i).get(j).getDest().isStop())
					sum += Double.parseDouble(data.get(i).get(j).getCost());
			break;
		case 1: // Duration
			for (int j = 0; j < data.get(i).size(); j ++) 
				sum += Double.parseDouble(data.get(i).get(j).getDuration());
			break;
		default:
			break;
		}
	
		return sum + "";
	}

	private ArrayList<HashMap<String, String>> createGroupList() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < data.size(); ++ i) { // 15 groups........
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("main", "Route " + i + " Cost = " + sum(i, 0) + " Time = " + sum(i, 1)); // the key and it's value.
			result.add(m);
		}
		return result;
	}

	private ArrayList<ArrayList<HashMap<String, String>>> createChildList() {
		ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
		for (int i = 0; i < data.size(); ++i) { // This is the number of groups
			ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> child = new HashMap<String, String>();

			for (int n = 0; n < data.get(i).size() - 1; n++) {
				if (data.get(i).get(n).getSrc().isStop()) {
					child = new HashMap<String, String>();
					child.put("src", "From " + data.get(i).get(n).getSrc().getName());
				}
				if (data.get(i).get(n).getDest().isStop()) {
					child.put("dest", "To " + data.get(i).get(n).getDest().getName());
					child.put("trans", "By " + data.get(i).get(n).getTransportation());
					secList.add(child);
					child = new HashMap<String, String>();
					child.put("src", "From " + data.get(i).get(n).getDest().getName());
				}
			}
			child.put("dest", "To " + data.get(i).get(data.get(i).size() - 1).getDest().getName());
			child.put("trans", "By " + data.get(i).get(data.get(i).size() - 1).getTransportation());
			secList.add(child);
			result.add(secList);
		}
		return result;
	}

	private void clearData(MapView mapV) {
		List<Overlay> mapOverlays = mapV.getOverlays();
		mapOverlays.clear();
	}

	private void drawData(MapView mapV, int index, int child) {
			List<Overlay> mapOverlays = mapV.getOverlays();
			mapOverlays.clear();
			Drawable drawable = context.getResources().getDrawable(R.drawable.greenmarker);
			MyOverlays overlays = new MyOverlays(drawable, context);
			overlays.setMyRoutes(data.get(index));
	//		overlays.setSelectedChild(child);
			overlays.setShownGroup(index);
			mapOverlays.add(overlays);
		}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		if (((MyOverlays) mapview.getOverlays().get(0)).getShownGroup() == groupPosition) {
			drawData(mapview, groupPosition, childPosition);
			mapview.postInvalidate();
		}
		return true;
	}

	@Override
	public void onGroupExpand(int groupPosition) {
		try {
			drawData(mapview, groupPosition, -1);
			mapview.postInvalidate();
		} catch (Exception e) {
			Log.v("ExpandableList", "" + e);
		}
	}

	@Override
	public void onGroupCollapse(int groupPosition) {
		try {
			clearData(mapview);
			mapview.postInvalidate();
		} catch (Exception e) {
			Log.v("ExpandableList", "" + e);
		}
	}

	public void setListeners(ExpandableListView l) {
		l.setOnChildClickListener(this);
		l.setOnGroupExpandListener(this);
		l.setOnGroupCollapseListener(this);
	}
}