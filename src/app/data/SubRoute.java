package app.data;

public class SubRoute {

 	private int id = 0;
	private Node src = null;
	private Node dest = null;
	private String duration = "";
	private String cost = "";
	private String transportation = "";
	
	public SubRoute() {
		
	}

	public SubRoute(int id, Node src, Node dest, String time, String cost, String transportation) {
		super();
		this.id = id;
		this.src = src;
		this.dest = dest;
		this.duration = time;
		this.cost = cost;
		this.transportation = transportation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node getSrc() {
		return src;
	}

	public void setSrc(Node src) {
		this.src = src;
	}

	public Node getDest() {
		return dest;
	}

	public void setDest(Node dest) {
		this.dest = dest;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

}
