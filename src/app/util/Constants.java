package app.util;

public class Constants {
	
	/**============================================
	 * requestType
	 *=============================================*/
	public static final int GET_REQUEST = 1;
	public static final int POST_REQUEST = 2;

	/**=============================================
	 * responseHandleBy
	 *==============================================*/
	public static final int ROUTE_LISTING = 100;
	public static final int ROUTE_ADD_OR_EDIT_DONE = 101;	
	public static final int ROUTE_LISTING_DELETE = 102;
	
	/**=============================================
	 * creatingXMLParser for class
	 *==============================================*/
	public static final int XML_PARSER_FOR_ROUTES = 200;
	static final int XML_PARSER_FOR_ERRORS = 201;
	
	/**=============================================
	 * mode of page
	 *==============================================*/
	public static final String MODE_OF_PAGE = "MODE_OF_PAGE";
	public static final int PAGE_ADD = 300;
	public static final int PAGE_EDIT = 301;	
	
	/**=============================================
	 * others
	 *==============================================*/
	public static final String POSITION_OF_ROUTE_IN_LIST = "POSITION_OF_ROUTE";
}//end class Constants