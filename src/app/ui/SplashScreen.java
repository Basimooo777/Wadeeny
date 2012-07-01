package app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import app.util.RouteManager;
import app.util.ShowAlertDialog;

public class SplashScreen extends Activity {

	/**
	 * ===================================================================
	 * method onCreate
	 * ====================================================================
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);

		boolean isConnectedToInternet = false;
		String src = getIntent().getExtras().getString("src");
		String dest = getIntent().getExtras().getString("dest");
		String srcPoint = getIntent().getExtras().getString("p_src");
		String destPoint = getIntent().getExtras().getString("p_dest");
		isConnectedToInternet = new NetworkConnectivity(this).checkNetworkConnectivity();

		if (!isConnectedToInternet) {
			boolean onOkClickFinishActivity = true;
			new ShowAlertDialog(this, getString(R.string.alertDialogHeadingUnableToConnect), getString(R.string.alertDialogHeadingUnableToConnect), onOkClickFinishActivity).showDialog();
		} else {
			new RouteManager().requestServerForRouteList(this, src, dest,srcPoint, destPoint);
		} // end else
	} // end method onCreate

}// end class SplashScreen