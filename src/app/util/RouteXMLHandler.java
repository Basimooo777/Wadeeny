package app.util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import app.data.Node;
import app.data.Point;
import app.data.SubRoute;

public class RouteXMLHandler extends DefaultHandler {

	boolean inRoute = false;
	boolean inSubRoute = false;
	boolean inSrc = false;
	boolean inDest = false;
	boolean inName = false;
	boolean inId = false;
	boolean inPolylineLength = false;
	boolean inPolyline = false;
	boolean inPoint = false;
	boolean inLatitude = false;
	boolean inLongitude = false;
	boolean inIsStop = false;
	boolean inCost = false;
	boolean inDuration = false;
	boolean inTransportation = false;
	
	static int counter = 0;
	ArrayList<SubRoute> currentRoute = null;
	SubRoute currentSubRoute = null;
	Node currentNode = null;
	Point [] currentPolyline = null;
	Point currentPoint = null;
	ArrayList<ArrayList<SubRoute>> arrayOfRoutes = null;

	/**
	 * ===================================================================
	 * method getArrayOfUsers
	 * ====================================================================
	 */
	public ArrayList<ArrayList<SubRoute>> getArrayOfRoutes() {
		return arrayOfRoutes;
	}

	/**
	 * ===================================================================
	 * method startDocument
	 * ====================================================================
	 */
	@Override
	public void startDocument() throws SAXException {
		arrayOfRoutes = new ArrayList<ArrayList<SubRoute>>();
	}

	/**
	 * ===================================================================
	 * method endDocument
	 * ====================================================================
	 */
	@Override
	public void endDocument() throws SAXException {

	}

	/**
	 * ===================================================================
	 * method startElement
	 * ====================================================================
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("route")) {
			currentRoute = new ArrayList<SubRoute>();
			this.inRoute = true;
		} else if (localName.equals("sub_route")) {
			currentSubRoute = new SubRoute();
			this.inSubRoute = true;
		} else if (localName.equals("src")) {
			currentNode = new Node();
			this.inSrc = true;
		} else if (localName.equals("name")) {
			this.inName = true;
		} else if (localName.equals("polyline_length")) {
			this.inPolylineLength = true;
		} else if (localName.equals("polyline")) {
			this.inPolyline = true;
		} else if (localName.equals("point")) {
			currentPoint = new Point();
			this.inPoint = true;
		} else if (localName.equals("latitude")) {
			this.inLatitude = true;
		} else if (localName.equals("longitude")) {
			this.inLongitude = true;
		} else if (localName.equals("isStop")) {
			this.inIsStop = true;
		} else if (localName.equals("dest")) {
			currentNode = new Node();
			this.inDest = true;
		} else if (localName.equals("cost")) {
			this.inCost = true;
		} else if (localName.equals("duration")) {
			this.inDuration = true;
		} else if (localName.equals("transportation")) {
			this.inTransportation = true;
		}
	}

	/**
	 * ===================================================================
	 * method endElement
	 * ====================================================================
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("route")) {
			arrayOfRoutes.add(currentRoute);
			this.inRoute = false;
		} else if (localName.equals("sub_route")) {
			currentRoute.add(currentSubRoute);
			this.inSubRoute = false;
		} else if (localName.equals("src")) {
			currentSubRoute.setSrc(currentNode);
			this.inSrc = false;
		} else if (localName.equals("name")) {
			this.inName = false;
		} else if (localName.equals("polyline_length")) {
			this.inPolylineLength = false;
		} else if (localName.equals("polyline")) {
			currentNode.setPolyline(currentPolyline);
			counter = 0;
			this.inPolyline = false;
		} else if (localName.equals("point")) {
			currentPolyline[counter] = currentPoint;
			counter ++;
			this.inPoint = false;
		} else if (localName.equals("latitude")) {
			this.inLatitude = false;
		} else if (localName.equals("longitude")) {
			this.inLongitude = false;
		} else if (localName.equals("isStop")) {
			this.inIsStop = false;
		} else if (localName.equals("dest")) {
			currentSubRoute.setDest(currentNode);
			this.inDest = false;
		} else if (localName.equals("cost")) {
			this.inCost = false;
		} else if (localName.equals("duration")) {
			this.inDuration = false;
		} else if (localName.equals("transportation")) {
			this.inTransportation = false;
		}
	}

	/**
	 * ===================================================================
	 * method characters
	 * ====================================================================
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		if (this.inName) {
			String name = new String(ch, start, length);
			currentNode.setName(name);
		} else if (this.inPolylineLength) {
			int polylineLength = Integer.parseInt(new String(ch, start, length));
			currentPolyline = new Point[polylineLength];
		} else if (this.inLatitude) {
			double latitude = Double.parseDouble(new String(ch, start, length));
			currentPoint.setX(latitude);
		} else if (this.inLongitude) {
			double longitude = Double.parseDouble(new String(ch, start, length));
			currentPoint.setY(longitude);
		}  else if (this.inIsStop) {
			String isStop = new String(ch, start, length);
			if(isStop.equals("true"))
				currentNode.setStop(true);
			else
				currentNode.setStop(false);
		}  else if (this.inCost) {
			String cost = new String(ch, start, length);
			currentSubRoute.setCost(cost);
		}  else if (this.inDuration) {
			String duration = new String(ch, start, length);
			currentSubRoute.setDuration(duration);
		}  else if (this.inTransportation) {
			String transportation = new String(ch, start, length);
			currentSubRoute.setTransportation(transportation);
		}
		
	}
}