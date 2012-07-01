package app.data;


public class Node {

	private int id = 0;
	private String name = "";
	private Point [] polyline;
	private boolean stop;

	public Node() {
		
	}

	public Node(int id, String name, Point [] points, boolean stop) {
		super();
		this.id = id;
		this.name = name;
		this.polyline = points;
		this.stop = stop;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point [] getPolyline() {
		return polyline;
	}

	public void setPolyline(Point [] polyline) {
		
		Point [] polylineNew = new Point[polyline.length + 1];

		for (int i = 0; i < polyline.length; i ++) 
			polylineNew[i] = polyline[i];
		
		polylineNew[polylineNew.length - 1] = polylineNew[0];
		
		this.polyline = polylineNew;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
