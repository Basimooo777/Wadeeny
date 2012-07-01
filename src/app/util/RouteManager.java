package app.util;


import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import app.data.SubRoute;
import app.ui.Map;
import app.ui.R;


public class RouteManager {
	  
	private static ArrayList<ArrayList<SubRoute>> route;
	
	/**===============================================================   
	  * getter setter methods for userArrayList     
	  * ==============================================================*/   
	public static void setRoute(ArrayList<ArrayList<SubRoute>> route) {
		RouteManager.route = route;
	}
	
	public static ArrayList<ArrayList<SubRoute>> getRoute() {
		return route;
	}


	/**===============================================================   
    * method requestServerForUserList
    * ==============================================================*/   
	public void requestServerForRouteList(Context ctx, String src, String dest, String srcPoint, String destPoint) {
		String shownOnProgressDialog = ctx.getString(R.string.progressDialogMessageSplashScreenRetrievingUserListing);
		String urlString = ctx.getString(R.string.urlString) + ".xml?src=" + src + "&dest=" + dest + "&p_src=" + srcPoint + "&p_dest=" + destPoint + "&key=1234";
		System.out.println(urlString);
		new RequestSentToServerAsyncTask(ctx, Constants.GET_REQUEST, Constants.ROUTE_ADD_OR_EDIT_DONE, shownOnProgressDialog).execute(urlString);
		// when the execute method will be called it will send a get request to rails server and
		// when the response will come it will be handled by ResponseHandler Class, and there in
		// case Constants.USER_LISTING  
    
	} // end method requestServerForUserList
   
 
	/**===============================================================   
	  * method saveUserListInStaticVariable   
	  * ==============================================================*/   
	@SuppressWarnings("unchecked")
	public void saveRouteListInStaticVariable(Context ctx, InputStream iStream) {
		try {
			Object routesObject = new CreateXMLParser().creatingXmlParser(iStream, Constants.XML_PARSER_FOR_ROUTES);
			setRoute((ArrayList<ArrayList<SubRoute>>) routesObject); 
		} catch (IllegalStateException e) {
			Log.v("in RouteManager -> saveUserListInStaticVariable(Context ctx, InputStream iStream) -> in catch", "exception has occured exception is " + e);
		}
	} //end method saveUserListInStaticVariable
	
	/**===============================================================   
	  * method gotoShowRoutePage   
	  * ==============================================================*/   
	public void gotoShowRoutePage(Context ctx, int positionOfRouteInList){
		Intent intent = new Intent(ctx, Map.class);
		System.out.println("test1");
		ctx.startActivity(intent);
		System.out.println("test2");
		((Activity)ctx).finish();
	} //end method gotoShowUserPage
	
} //end class UserManager
