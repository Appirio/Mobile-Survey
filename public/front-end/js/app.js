var app = angular.module('fieldSurvey', ['fieldSurveyServices', 'fieldSurveyDirectives', 'ngMobile']).
  config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider.
      when('/', {templateUrl: 'assets/front-end/tmpl/login.html',   controller: con.login, access:{ isFree: false }}).
      when('/login', {templateUrl: 'assets/front-end/tmpl/login.html',   controller: con.login, access:{ isFree: true }}).
      when('/surveys', {templateUrl: 'assets/front-end/tmpl/surveys.html',   controller: con.surveys, access:{ isFree: false }}).
      when('/chapters', {templateUrl: 'assets/front-end/tmpl/chapters.html',   controller: con.chapters, access:{ isFree: false }}).
      when('/question/:surveyId', {templateUrl: 'assets/front-end/tmpl/question.html', controller: con.question, access:{ isFree: false }}).
      when('/question/:surveyId/:questionId', {templateUrl: 'assets/front-end/tmpl/question.html', controller: con.question, access:{ isFree: false }}).
      when('/review', {templateUrl: 'assets/front-end/tmpl/review.html', controller: con.review, access:{ isFree: false }}).
	  otherwise({redirectTo: '/login'});
  console.log($httpProvider);
  $httpProvider.responseInterceptors.push(['$q', '$rootScope', '$location', 'UserService', function($q, $rootScope, $location, User){
	return function(promise){
		//Prior to request.
		//if(navigator.onLine){
			//$rootScope.$broadcast("displayLoading");
			return promise.then(function(response){
				//Success.
			//	$rootScope.$broadcast("closeLoading");
				console.log("Successful Response");
				console.log(response);
				return response;
			}, function(response){
				//Error.
			//	$rootScope.$broadcast("closeLoading");
				console.log("Error on Response");
				console.log(response);
				console.log(JSON.stringify(response));

				if(response.status >= 400 && response.status < 599){
					var errMsg = 'We have experienced an issue. Please try again later.  If the problem persists, please restart your device and application.';
					if(response.status == 401){
						console.log("WE TOLD THEM THE STATUS WAS 401!! Hahaha");
						delete localStorage.contactId;
						delete localStorage.contactEmail;
						User.hasAccess = false;
						User.id = null;
						User.email = '';
                        $location.path('/splash');
						errMsg = 'Your session has expired. Please login with a new access code.';
						$rootScope.$broadcast('displayModal', {'message' : 'Your session has expired. Please login with a new access code.', 'button' : 'OK'});
					}
				    $rootScope.$broadcast('closeLoading');
				    $rootScope.$broadcast('displayModal', {'message' : errMsg, 'button' : 'OK'});
				    //$location.path('/splash');
				}
				if(typeof(response.data) != "object"){
					response.data = {status: false, description: response.data};
				}
				if(response.status == 0){ //offline
					response.data = "You must be online to perform this action.";
				}
				return $q.reject(response);
			});
		/*} else{
			console.log('The application is offline. '+promise);
			if (typeof(currRoute.$$route.access) == 'undefined' || !currRoute.$$route.access.isOffline) {
				console.log('This is not an offline page. '+currRoute);
				return	$q.reject({data: {status: false, description: "Not connected to Internet."}});
			}else{
				console.log('this is an offline page');
			}
		}*/
	}
    }]);
}]);

app.run(['$location', '$rootScope', 'UserService', function($location, $rootScope, User) {
    $rootScope.modalDisplayed = false;
    $rootScope.showDashboard = localStorage.showDashboard !== undefined ? localStorage.showDashboard : "false";
    $rootScope.onPremise = true;

    document.addEventListener("backbutton", function(){
        console.log("Device Backbutton Pressed.");
        if(!$rootScope.modalDisplayed){
            return $rootScope.$broadcast('backButtonPressed');
        } else{
            return $rootScope.$broadcast('closeModal');
        }
    }, false);

    $rootScope.$on('$routeChangeStart', function(event, currRoute, prevRoute){

		console.log('RouteChangeStart');console.log(event); console.log(currRoute); console.log(prevRoute);

		if (typeof(currRoute.$$route.access) !== 'undefined' && !currRoute.$$route.access.isFree && !User.hasAccess) {
			$location.path('/login');
		}
    });

	$rootScope.isOnline = function(){

		return true;
	};

	$rootScope.isEdit = false;
    $rootScope.myAccounts = true;
}]);

document.addEventListener('deviceready', function(){IS_DEVICE_READY = true}, false);
app.SigGen = {signatureStack : [],
signatureTryCount : 0,
email : "",
	};
app.SigGen.getSignature = function () {

	var signature = app.SigGen.signatureStack.pop();
	console.log(signature);
	this.generateNewSignature();
	//signature = {};
	return signature;

};

