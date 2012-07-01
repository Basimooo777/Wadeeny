package app.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import app.data.SubRoute;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyOverlays extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private ArrayList<SubRoute> myRoutes = new ArrayList<SubRoute>();
	private int shownGroup = -1;

	public MyOverlays(Drawable defaultMarker, Context context) {

		super(boundCenterBottom(defaultMarker));
		populate();

	}

	public MyOverlays(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	protected boolean onTap(int index) {
		return true;
	}

	public ArrayList<SubRoute> getMyRoutes() {
		return myRoutes;
	}

	public void setMyRoutes(ArrayList<SubRoute> myRoutes) {
		this.myRoutes = myRoutes;
	}

	@Override
	public void draw(Canvas canvas, MapView mapv, boolean shadow) {
		super.draw(canvas, mapv, shadow);
		Projection projection = mapv.getProjection();

		// Drawing Line Style
		Paint mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(2);

		Paint mPaint2 = new Paint();
		mPaint2.setDither(true);
		mPaint2.setColor(Color.BLACK);
		mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint2.setStrokeJoin(Paint.Join.ROUND);
		mPaint2.setStrokeCap(Paint.Cap.ROUND);
		mPaint2.setStrokeWidth(2);

		// My Sub Routes
		for (int i = 0; i < myRoutes.size(); i++) {
			Point p1 = new Point();
			Point p2 = new Point();
			Path path = new Path();
			Path path2 = new Path();
			GeoPoint src = null, src2 = null, dest = null, dest2 = null;
			int tempX = 0, tempY = 0;

			tempX = (int) (myRoutes.get(i).getSrc().getPolyline()[0].getX() * Math.pow(10, 6));
			tempY = (int) (myRoutes.get(i).getSrc().getPolyline()[0].getY() * Math.pow(10, 6));
			src = new GeoPoint(tempX, tempY);
			for (int j = 1; j < myRoutes.get(i).getSrc().getPolyline().length; j++) {
				tempX = (int) (myRoutes.get(i).getSrc().getPolyline()[j].getX() * Math.pow(10, 6));
				tempY = (int) (myRoutes.get(i).getSrc().getPolyline()[j].getY() * Math.pow(10, 6));
				src2 = new GeoPoint(tempX, tempY);
				projection.toPixels(src, p1);
				projection.toPixels(src2, p2);
				path.moveTo(p2.x, p2.y);
				path.lineTo(p1.x, p1.y);
				src = src2;
			}

			tempX = (int) (myRoutes.get(i).getDest().getPolyline()[0].getX() * Math.pow(10, 6));
			tempY = (int) (myRoutes.get(i).getDest().getPolyline()[0].getY() * Math.pow(10, 6));
			dest = new GeoPoint(tempX, tempY);
			for (int j = 1; j < myRoutes.get(i).getDest().getPolyline().length; j ++) {
				tempX = (int) (myRoutes.get(i).getDest().getPolyline()[j].getX() * Math.pow(10, 6));
				tempY = (int) (myRoutes.get(i).getDest().getPolyline()[j].getY() * Math.pow(10, 6));
				dest2 = new GeoPoint(tempX, tempY);
				projection.toPixels(dest, p1);
				projection.toPixels(dest2, p2);
				path.moveTo(p2.x, p2.y);
				path.lineTo(p1.x, p1.y);
				dest = dest2;
			}

			canvas.drawPath(path, mPaint);

			projection.toPixels(src, p1);
			projection.toPixels(dest, p2);
			path2.moveTo(p2.x, p2.y);
			path2.lineTo(p1.x, p1.y);

			canvas.drawPath(path2, mPaint2);
		}
	}

	public int getShownGroup() {
		return shownGroup;
	}

	public void setShownGroup(int shownGroup) {
		this.shownGroup = shownGroup;
	}

}
