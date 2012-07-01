package app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import app.ui.R;

class ResponseHandler {

	/**
	 * =======================================================================================
	 *  method handleResponse
	 * =======================================================================================
	 */
	@SuppressWarnings("unchecked")
	void handleResponse(final Context ctx, int responseHandleBy, ServerResponse serverResponseObject) {

		HttpResponse response = serverResponseObject.response;
		Exception exception = serverResponseObject.exception;

		if (response != null) {

			InputStream iStream = null;

			try {
				iStream = response.getEntity().getContent();
			} catch (IllegalStateException e) {
				Log.e("in ResponseHandler -> in handleResponse() -> in if(response !=null) -> in catch ", "IllegalStateException " + e);
			} catch (IOException e) {
				Log.e("in ResponseHandler -> in handleResponse() -> in if(response !=null) -> in catch ", "IOException " + e);
			}

			int statusCode = response.getStatusLine().getStatusCode();
			Log.d("StatusCode", statusCode + "");

			switch (statusCode) {
			case 200:
			case 201: {
				switch (responseHandleBy) {
				
				// Case 1 Constants.USER_LISTING:
				case Constants.ROUTE_LISTING: {
					if (iStream == null) {
						Toast t = Toast.makeText(ctx, ctx.getString(R.string.toastMessageResponseHandlerIStreamIsNull), Toast.LENGTH_LONG);
						t.show();
					} else {
						new RouteManager().saveRouteListInStaticVariable(ctx, iStream);
					}
					break;
				} // end case Constants.USERListing

				// Case 2 Constants.USER_ADD_OR_EDIT_DONE:
				case Constants.ROUTE_ADD_OR_EDIT_DONE: {
					if (iStream == null) {
						Toast t = Toast.makeText(ctx, ctx.getString(R.string.toastMessageResponseHandlerIStreamIsNull), Toast.LENGTH_LONG);
						t.show();
					} else {
						new RouteManager().saveRouteListInStaticVariable(ctx, iStream);
						new RouteManager().gotoShowRoutePage(ctx, 0);
					}
					break;
				} // end case USER_ADD_OR_EDIT_DONE

				// case 2 Constants.USER_LISTING_DELETE:
				case Constants.ROUTE_LISTING_DELETE: {
//					new RouteManager().requestServerForRouteList(ctx);
					break;
				} // end case USER_LISTING_DELETE:
				} // end of switch response handle by

				break;

			} // end of case 200 & 201

			// case Status code == 422
			case 422: {
				Object arrayOfErrorsObject = new CreateXMLParser().creatingXmlParser(iStream, Constants.XML_PARSER_FOR_ERRORS);
				ArrayList<String> arrayOfErrors = (ArrayList<String>) arrayOfErrorsObject;

				String message = "";
				for (String error : arrayOfErrors) {
					message += "\n* " + error + ".";
				}

				String headingOfAlertDialog = ctx.getString(R.string.alertDialogHeadingError);
				new ShowAlertDialog(ctx, headingOfAlertDialog, message).showDialog();
				break;
			} // end case 422:
			}
		} else if (exception != null) {
			
			new ShowAlertDialog(ctx, ctx.getString(R.string.alertDialogHeadingUnableToConnect), ctx.getString(R.string.alertDialogMessageUnableToConnect)).showDialog();

		} else {
			Log.v("in ResponseHandler -> in handleResponse -> in  else ", "response and exception both are null");

		} // end of else
	} // end method handle response
} // end class ResponseHandler