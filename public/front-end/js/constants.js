/* Constants.js for Mobile Survey App*/

//var BUILD_NUMBER = '0.0.1';
var VERSION_NUMBER = '2.0.2';
var SERVICE_BUILD_NUMBER = '/2.0';
var ACTIVE_ENV = "PRODUCTION";


var BASE_PATH =""

var AUTHORIZE_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/contact/authorize';
var ACCOUNT_GEOLOCATION_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/account/:latitudeCoord/:longitudeCoord/:miles';
var SURVEY_UNIVERSAL_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/survey/universal';
var SURVEY_ACCOUNT_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/survey/byaccount/:accountId';
var PRODUCT_ID_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/product/:productId';
var SURVEY_SUBMIT_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/survey';
var GOAL_DASHBOARD_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/goal/achievements/:startdate/:enddate/:includeDetails';
var CONTACT_INFO_PATH = BASE_PATH + SERVICE_BUILD_NUMBER + '/contact';

var T_AND_C_PATH = 'http://appirio.com';

//variables used for localStorage
var CACHED_SURVEY_DATA = 'SurveyData';
var CACHED_SURVEY_TEMPLATE = 'SurveyTemplate';
var CACHED_SURVEY_CHAPTER = 'SurveyList';

//random configurable vars
var ACCOUNT_SEARCH_RADIUS = 3;
var KEY_BUFFER_SIZE = 10;
var HEADER_FOOTER_TOTAL_HEIGHT = 84;

var IS_DEVICE_READY = false;

// NON PRODUCT Survey type
var NON_PRODUCT_SURVEY = 'Non Product';

//Question Select/Multi-Select Constants
var TYPE_SELECT = 'Select';
var TYPE_MULTI_SELECT = 'Multi-Select';
var TYPE_TEXT = 'Text';
var TYPE_COUNT = 'Count';
var TYPE_PRICE = 'Price';
var OPTION_NONE_ABOVE = 'None of the Above';
