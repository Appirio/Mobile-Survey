var con = {

    login: function($scope, $rootScope, $http, $location, User, authHttp){
		$scope.hasAccess = User.hasAccess;
	        //$scope.build = BUILD_NUMBER;
        	$scope.version = VERSION_NUMBER;
        $scope.accessCode = '';
        $scope.email = '';
		$scope.appPath = '/Surveys';
		$scope.pathName = 'Surveys ';

		if(localStorage.getItem(CACHED_SURVEY_DATA) != 'null' && localStorage.getItem(CACHED_SURVEY_DATA) != null){
			$scope.appPath = '/review';
			$scope.pathName = 'Review Unsaved Survey';
		}

		$scope.submitCredentials = function(email, accessCode){
			//convert to lowercase for improved formatting handling
            //alert(email+"   "+accessCode);

			console.log(JSON.stringify({"email": email, "authorizationCode":accessCode}));
			if($rootScope.isOnline()){

				if( typeof(email) != "undefined"  && email != "" &&  email != null){
                    email = email.toLowerCase();
					//Regular HTTP because we are not authenticated yet.
					$rootScope.$broadcast("displayLoading");
                    //alert(AUTHORIZE_PATH);
					$http.post(AUTHORIZE_PATH, {"email": email, "authorizationCode":accessCode},{
                        headers: {'Content-Type':'application/json'}
                    }).success(function(data){
						console.log(data);
						$rootScope.$broadcast("closeLoading");
						if(typeof(data.contact) !== "undefined"){
							User.hasAccess = true;
							User.email = email;
							User.id = data.contact.id;
                            $rootScope.showDashboard = data.contact.showDashboard;
							localStorage.setItem("contactId", data.contact.id);
							localStorage.setItem("contactEmail", email);
                            localStorage.setItem("showDashboard", data.contact.showDashboard);
							$http.defaults.headers.common['UID'] = data.contact.id;
							//app.SigGen.setEmail(email);
						} else{
							User.hasAccess = false;
							User.email = '';
							User.id = '';
						}

						//call service to authorize
                        if(User.hasAccess)
						$location.path('/surveys/');
                        else
                            $rootScope.$broadcast("displayModal", {message: data.description, button: 'Try Again'});

					}).error(function(data, status, headers, config) {
						$rootScope.$broadcast("closeLoading");
						var mess = "The Email Address and Access Code combination was invalid.";

						if(status == 400){
							if(data.description.indexOf("Json body") >= 0){
								mess = "Please fill in your Email Address and Access Code.";
							}
						} else if(status == 500){
							mess = "There was an error proccessing your credentials. Verify that Email Address and Access Code combination is valid."
						}
						$rootScope.$broadcast("displayModal", {message: mess, button: 'Try Again'});
					});
				} else{
                 //   alert("hello");
					$rootScope.$broadcast("displayModal", {message: "Please fill in your Email Address and Access Code", button: 'Try Again'});
				}
			}
		};
		$scope.progress = function(){
            var contactInfo = authHttp.get(CONTACT_INFO_PATH, {}).success(function(success){
                if(typeof success.showDashboard !== "undefined"){
                    $rootScope.showDashboard = success.showDashboard;
                    localStorage.showDashboard = success.showDashboard;
                }
                $location.path($scope.appPath);
            }).error(function(){
                $location.path($scope.appPath);
            });
		}
		$scope.termsAndConditions = function(){
			window.open(T_AND_C_PATH, "_system");
		}
        $scope.$on('backButtonPressed', function(event){
            console.log("Login page. Jump out of App!");
            navigator.app.exitApp();
            return false;
        });
	},



    surveys: function($scope, $rootScope, $http, $location, Surveys, Account, authResource){
		//TODO: get accountId from factory instead of hardcoding
        $http.defaults.headers.common['UID'] = '00337000002JHCK';

        var accountId = Account.getAccount().sfid;	//"001K000000nZFtFIAW";
        $scope.articleClass = ($rootScope.showDashboard == 'true' || (typeof($rootScope.showDashboard) == 'boolean' && $rootScope.showDashboard)) ? 'articleWithFooter' : '';
		console.log('Loading list of Surveys.');

		if($scope.isOnline()){
            console.log('isOnline');
            //========== COMMENT for testing
			if(accountId == null || accountId == ''){
				console.log('Calling Universal Survey Service');
				$scope.displaySurveys = authResource(SURVEY_UNIVERSAL_PATH).query();
			}else{
			//	console.log('Calling Account-based Survey Service: '+accountId);
				$scope.displaySurveys = authResource(SURVEY_ACCOUNT_PATH.replace(':accountId', accountId)).query();
			}
		}

		$scope.saveSurvey = function (selectedSurvey) {
			Surveys.addSurvey(selectedSurvey, Account.getAccount());

            // Save current chapter survey listing
            util.setLocalStorage(CACHED_SURVEY_CHAPTER, selectedSurvey);

            // For NON PRODUCT surevy check if Survey NON PRODUCT and conditionally direct path to /question
            $scope.survey = Surveys.getSurvey(selectedSurvey);

            // Handle Chapter surveys here
            if ($scope.survey.isparent__c == 't'){
                $location.path('/chapters/');
            }else{
                //TODO:All servey are now changed as Non Product survey need to enter product survey in salseforce
                //if($scope.survey.survey_type__c == NON_PRODUCT_SURVEY){
                //    $location.path('/question/');
                //}else{
                //    $location.path('/product/');
                //}
                $location.path('/question/');
            }

        };

        $scope.goBack = function() {
            $location.path('/locations/');
        };

        $scope.openDashboard = function(){
            $location.path('/goals');
        };

        $scope.$on('backButtonPressed', function(event){

            console.log("Survey page. Handle internally.");
           // $scope.goBack();
           // $scope.$apply();
            navigator.app.exitApp();
            return false;
        });



	},

    chapters: function($scope, $rootScope, $http, $location, Surveys, Account){
        console.log('Loading list of Chapters.');
        $http.defaults.headers.common['UID'] = '00337000002JHCK';
        $scope.articleClass = ($rootScope.showDashboard == 'true') ? 'articleWithFooter' : '';
        // Set up Chapter surveys
        var childSurveys = Surveys.getChapters();
        if (childSurveys == null){
            // Manage Cahpter survey after submit or back button activation
            var surveyChapter = util.getLocalStorage(CACHED_SURVEY_CHAPTER);
            childSurveys = surveyChapter.childSurveys;
			$scope.parentName = surveyChapter.name;
		}else{
            // set parent Survey name in scope
            $scope.parentName = Surveys.getChaptersParentName();
		}
        $scope.displaySurveys = childSurveys;

        // Route to Chaoter Questions/Product scan
        $scope.saveSurvey = function (selectedSurvey) {
            Surveys.addSurvey(selectedSurvey, Account.getAccount());

            // For NON PRODUCT surevy check if Survey NON PRODUCT and conditionally direct path to /question
            $scope.survey = Surveys.getSurvey(selectedSurvey);
           //TODO:Add Chapter non product survey
            //Code commented to make all survey non product
            //if($scope.survey.survey_type__c == NON_PRODUCT_SURVEY){
            //   $location.path('/question/');
            //}else{
            //   $location.path('/product/');
            //}
            $location.path('/question/');
        };

        $scope.goBack = function() {
            $location.path('/surveys/');
        };

        $scope.openDashboard = function(){
            $location.path('/goals');
        };

        $scope.$on('backButtonPressed', function(event){
            console.log("Chapters page. Handle internally.");
            $scope.goBack();
            $scope.$apply();
            return false;
        });
    },


    question: function($scope, $routeParams, $rootScope, $location, Surveys, Breadcrumb, FileTransfer, Goals, Account){

        $scope.photos = [null, null, null];
        $scope.allSelectText = "None";
        $scope.accountName = Account.getAccount().name;

		//TODO change this to loop through the questions, but keep on this page until done
		var surveyId = $routeParams.surveyId;
		//var counter = $routeParams.questionCount | 0;
		var mySurvey = Surveys.getSurvey(surveyId); //[0].name;

		console.log('questionId: '+$routeParams.questionId+', firstQuestionId: '+mySurvey.first_question__c);

		var questionId = $routeParams.questionId;
		if(questionId == 'undefined' || questionId == undefined){ questionId = mySurvey.first_question__c; }

		console.log('surveyId: '+surveyId+', questionId: '+questionId);

		//$scope.question = mySurvey.questions[counter]; //[0].name;
		//TODO: below is the new code for getting the question:
		$scope.question = Surveys.getQuestion(questionId);
        if($scope.question.photos instanceof Array){
            $scope.photos = angular.copy($scope.question.photos);
            while($scope.photos.length < 3){
                $scope.photos.push(null);
            }
        }
        $scope.question.hasBeenAccessed = false;
		$scope.wholeSurvey = mySurvey;
		$scope.totalCount = Surveys.getQuestionCount();
        console.log($scope.question);

        //Pre-Parse Select All Text if this is an existing question.
        if($scope.question.question_type__c === TYPE_MULTI_SELECT){
            for(var option in $scope.question.answer_options__c){
                if(!$scope.question.answer_options__c[option].checked && ($scope.question.answer_options__c[option].text !== OPTION_NONE_ABOVE)){
                    $scope.allSelectText = "All";
                    break;
                }
            }
        }

		var counter = Breadcrumb.size() | 0;

        if(($scope.question.question_type__c == TYPE_SELECT || $scope.question.question_type__c == TYPE_MULTI_SELECT)
			&& $scope.question.answer_options__c instanceof Array){

			$scope.noneIncluded = false;

            console.log($scope.question.answer_options__c);
            for(option in $scope.question.answer_options__c){

				console.log('WOOT Option: '+$scope.question.answer_options__c[option].text);
                if(typeof $scope.question.answer_options__c[option] === "string" && $scope.question.answer_options__c[option].text != OPTION_NONE_ABOVE){
                    var tempObj = {};
                    tempObj.text = $scope.question.answer_options__c[option];
                    tempObj.checked = false;
                    $scope.question.answer_options__c[option] = tempObj;

                }else if($scope.question.answer_options__c[option].text == OPTION_NONE_ABOVE){
					$scope.noneIncluded = true;
				}
            }

			//add as option if 'None of the Above' is included
			if($scope.question.include_none_of_the_above__c == 't' && $scope.noneIncluded != true){
				var tempObj = {};
				tempObj.text = OPTION_NONE_ABOVE;
				tempObj.checked = false;
				$scope.question.answer_options__c.push(tempObj);
				//Surveys.includeNone($scope.question);
			}

            console.log($scope.question.answer_options__c);
        }

		$scope.updateSelection = function(optionSelected){

			//if($scope.question.question_type__c == TYPE_SELECT || ($scope.question.question_type__c == TYPE_MULTI_SELECT && optionSelected.text == OPTION_NONE_ABOVE)){
				for(var option in $scope.question.answer_options__c){
					console.log('Option Selected: '+optionSelected.text+'. Option matching: '+JSON.stringify($scope.question.answer_options__c[option]));

					if($scope.question.answer_options__c[option].text != optionSelected.text){

						if((optionSelected.text == OPTION_NONE_ABOVE && optionSelected.checked == true)
						|| $scope.question.question_type__c == TYPE_SELECT
						|| ($scope.question.answer_options__c[option].text == OPTION_NONE_ABOVE && $scope.question.answer_options__c[option].checked == true && $scope.question.question_type__c == TYPE_MULTI_SELECT)){
							$scope.question.answer_options__c[option].checked = false;
						}
						if($scope.question.answer_options__c[option].text == OPTION_NONE_ABOVE && $scope.question.answer_options__c[option].checked == true && $scope.question.question_type__c == TYPE_MULTI_SELECT){
							//alert('Woot!');
						}
					}
				}

                var allSelected = true;
                for(option in $scope.question.answer_options__c){
                    if(!$scope.question.answer_options__c[option].checked && $scope.question.answer_options__c[option].text !== OPTION_NONE_ABOVE){
                        allSelected = false;
                        break;
                    }
                }
                $scope.allSelectText = allSelected ? 'None' : 'All';
			//}
		};

        $scope.selectAll = function(){
            if($scope.question.question_type__c === TYPE_MULTI_SELECT){
                for(var option in $scope.question.answer_options__c){
                    if($scope.allSelectText === "All"){
                        $scope.question.answer_options__c[option].checked = ($scope.question.answer_options__c[option].text !== OPTION_NONE_ABOVE);
                    } else {
                        $scope.question.answer_options__c[option].checked = false;
                    }
                }
                $scope.allSelectText = ($scope.allSelectText === "All") ? "None" : "All";
            }
        };

		$scope.counter = counter;

		$scope.saveAnswer = function(answer){
			console.log($scope.wholeSurvey);

            $scope.question.photos = [];
            //establish a reference for when location changes.
            var weakQuestion = $scope.question;

            //TODO:Remove photo block as per Task
            //for(photo in $scope.photos){
            //    if($scope.photos[photo] != null && $scope.photos[photo].localPath && !$scope.photos[photo].externalPath){
            //
            //        $scope.photos[photo].uploading = true;
            //        weakQuestion.uploadingPhotos = true;
            //        weakQuestion.photos.push($scope.photos[photo]);
            //
            //        FileTransfer.upload($scope.photos[photo].localPath, "" + ACTIVE_ENV +  "_"  + $scope.question.dms_survey__c + $scope.question.sfid + (new Date()).getTime() + "_" + photo + ".jpg").done(function(e, localPath){
            //
            //            console.log("Image uploaded!");
            //            console.log(weakQuestion.photos);
            //            if(e && e.headers && e.headers.Location){
            //                var uploadingPhotos = false;
            //                for(var p in weakQuestion.photos){
            //                    if(weakQuestion.photos[p]){
            //                        if(weakQuestion.photos[p].localPath === localPath){
            //                            weakQuestion.photos[p].externalPath = e.headers.Location;
            //                            weakQuestion.photos[p].uploading = false;
            //                        } else{
            //                            if(weakQuestion.photos[p].uploading){
            //                                uploadingPhotos = true;
            //                            }
            //                        }
            //                    }
            //                }
            //                weakQuestion.uploadingPhotos = uploadingPhotos;
            //            }
            //        });
            //    }
            //}

			//grab selected values from multiselect picklists
            if($scope.question.question_type__c == TYPE_MULTI_SELECT || $scope.question.question_type__c == TYPE_SELECT){
                var selectedString = "";
                console.log($scope.question.answer_options__c);
                for(option in $scope.question.answer_options__c){
                    console.log($scope.question.answer_options__c[option]);
                    if($scope.question.answer_options__c[option].checked){
                        if(selectedString != ""){
                            selectedString += ";";
                        }
                        selectedString += $scope.question.answer_options__c[option].text;
                    }
                }
                console.log("Multi-Select selected options: " + selectedString);
                $scope.question.answer_text__c = selectedString;
            }

			//do field type validation
			if(($scope.question.question_type__c == TYPE_TEXT || $scope.question.question_type__c == '')
			&& $scope.question.answer_text__c == ''){

				$rootScope.$broadcast('displayModal',{message:'You must enter text.', button: 'Ok'})
			}
			else if(($scope.question.question_type__c == TYPE_SELECT || $scope.question.question_type__c == TYPE_MULTI_SELECT)
			&& $scope.question.answer_text__c == ''){

				$rootScope.$broadcast('displayModal',{message:'You must select an option.', button: 'Ok'})
			}
			else if($scope.question.question_type__c == TYPE_COUNT && ($scope.question.answer_value__c == null ||
			$scope.question.answer_value__c == '')){

				$rootScope.$broadcast('displayModal',{message:'You must enter a number.', button: 'Ok'})
			}
			else if($scope.question.question_type__c == TYPE_PRICE && ($scope.question.answer_value__c == null ||
			$scope.question.answer_value__c == '' || isNaN($scope.question.answer_value__c))){

				$rootScope.$broadcast('displayModal',{message:'You must enter a valid price', button: 'Ok'})
			}
			else{
                $scope.question.hasBeenAccessed = true;
				Breadcrumb.pushQuestion($scope.question);

				//THIS IS THE NEW CODE FOR CONDITIONALS:
				var nextQ = Surveys.getNextQuestion($scope.question, true);
				console.log('NextQ: '+JSON.stringify(nextQ));
				if(nextQ != null){
					$location.path('/question/'+mySurvey.survey_id+'/'+nextQ.sfid);
				}else{
					var crumbs = Breadcrumb.getResults();
					console.log('Breadcrumbs: '+JSON.stringify(crumbs));

                    //CHECK FOR UPLOADING PHOTOS.
                    var checkUploading = function(){
                        console.log("checkUploading called");
                        var uploadingPhotos = false;
                        for(var i = 0; i < crumbs.length; i++){
                            if(crumbs[i].uploadingPhotos){
                                uploadingPhotos = true;
                                break;
                            }
                        }
                        if(!uploadingPhotos){
                            console.log("Uploads complete. Saving Crumbs:");
                            console.log(crumbs);
                            //Because this guy right here copies the current stack
                            if(crumbs != null){
                                Surveys.saveDisplay(crumbs);
                                Breadcrumb.clear();
                                crumbs = null;
                            }
                            console.log("Crumbs Saved!")
                            //need to move the save logic here instead of on the display screen!
                            //TODO:
                            //if(mySurvey.survey_type__c == NON_PRODUCT_SURVEY){
                            //    console.log("Navigating to Review!");
                            //    $location.path('/review/');
                            //}else{
                            //    console.log("Navigating to Display!");
                            //    $location.path('/display/');
                            //}
                            $location.path('/review/');
                            console.log("Closing the loading screen!");
                            $rootScope.$broadcast('closeLoading');
                            $scope.$apply();
                        } else{
                            window.setTimeout(checkUploading, 200);
                        }
                    }

                    $rootScope.$broadcast('displayLoading');
                    checkUploading();
				}

				/*
				if(Surveys.getQuestionCount() > counter+1){
					$location.path('/question/'+mySurvey.survey_id+'/'+(counter+1));
				}else{
					$location.path('/display/');
				}
				*/
			}
		};

		$scope.previous = function(){
			if(counter > 0){
				var prevQuestion = Breadcrumb.popQuestion();
				$location.path('/question/'+mySurvey.survey_id+'/'+prevQuestion.sfid);
			}else{
                //TODO:
                // Conditional route for Non Product survey types
               // if(mySurvey.survey_type__c == NON_PRODUCT_SURVEY){
               //     var goal = Goals.getGoal();
               //     if(JSON.stringify(goal) === "{}"){
    			//		if(mySurvey.parent_survey__c != null){
               //             $location.path('/chapters/');
    			//		}else{
               //         	$location.path('/surveys/');
    			//		}
               //     } else{
               //         $location.path('/goals/' + goal.assignedGoal.sfid);
               //     }
                //}else{
                //    $location.path('/product/');
                //}
                if(mySurvey.parent_survey__c != null){
                                $location.path('/chapters/');
                    		}else{
                             	$location.path('/surveys/');
                    		}
            }
		};

        $scope.goBack = function() {
            // Clear all breadcrumbs
            Breadcrumb.clear();

            //TODO:Add Chaptered non product survey
            // Condition change to make all survey work as Non Product
            if(mySurvey.parent_survey__c != null){
                            $location.path('/chapters/');
                        }else{
                            $location.path('/surveys/');
                        }

            //// Conditional route for Non Product survey types
            //if(mySurvey.survey_type__c == NON_PRODUCT_SURVEY){
            //    var goal = Goals.getGoal();
            //    if(JSON.stringify(goal) === "{}"){
            //        if(mySurvey.parent_survey__c != null){
            //            $location.path('/chapters/');
            //        }else{
            //            $location.path('/surveys/');
            //        }
            //    } else{
            //        $location.path('/goals/' + goal.assignedGoal.sfid);
            //    }
            //}else{
            //    $location.path('/product/');
            //}
        };

        $scope.$on('backButtonPressed', function(event){
            console.log("Question page. Handle internally.");
            if($scope.counter > 0){
                $scope.previous();
            }else{
                $scope.goBack();
            }
            $scope.$apply();
            return false;
        });
    },


    review: function($scope, $rootScope, $http, $routeParams, $location, Surveys, Product, Account, authHttp, BreadcrumbService, Goals){

        $http.defaults.headers.common['UID'] = '00337000002JHCK';
        $scope.account = Account.getAccount();
		//var breadcrumbs = BreadcrumbService.getResults();
		$scope.surveys = Surveys.getSurveys();
		$rootScope.isEdit = false;
		$scope.isProductSurvey = true;


		if($scope.surveys[0] && $scope.surveys[0].survey_type__c == NON_PRODUCT_SURVEY){
			$scope.isProductSurvey = false;
			//$scope.surveys = Surveys.getOrderedSurveys(breadcrumbs);
		}


        $scope.continueWorking = function() {
			//Surveys.saveDisplay();
			$rootScope.isEdit = true;
            $location.path('/product/');
        };

        $scope.getQuestion = function(surveyId) {
			//$rootScope.setEdit(true);
			$rootScope.isEdit = true;
            Surveys.prepForEdit();
			console.log('isEdit: '+$rootScope.isEdit);
            $location.path('/question/'+surveyId);
        };

        $scope.finish = function() {
            //TODO:Add Non product survey to salesforce,code comment to make all survey as non-product
			//if($scope.isProductSurvey){
			//	$location.path('/notes/');
			//}else
			if($rootScope.isOnline()){	//for Conditional Surveys

				$rootScope.$broadcast("displayLoading");
				authHttp.post(SURVEY_SUBMIT_PATH, Surveys.getSurveys()).success(function(data){
					//alert('Your Data has been saved.');
					$rootScope.$broadcast("closeLoading");

                    // Save Survey
                    var listSurvey = Surveys.getSurveys();
                    var tmp_survey = listSurvey[0];
                    //if(tmp_survey != null && tmp_survey.parent_survey__c == null){
                        Surveys.reset();
                    //}
					console.log('Returned Data: '+JSON.stringify(data));
					if(typeof(data) != 'undefined' && (typeof(data.percentage) != 'undefined' || typeof(data.grade) != 'undefined' || typeof(data.message) != 'undefined')){

							$rootScope.$broadcast('displayModal', {
								type: 'grade',
								percentage: data.percentage,
								grade: data.grade,
								message: data.message,
								button: 'Okay'
							});
					}
                   // Goals.setCache(Goals.cachedMonth(), null);

                    //TODO:Comment code to remove goal screen and return to survey screen after submitting the survey
                    //var goal = Goals.getGoal();
                    //if(JSON.stringify(goal) === "{}"){
                    //    if(tmp_survey != null && tmp_survey.parent_survey__c != null){
                    //        $location.path('/chapters/');
                    //    }else{
                    //        $location.path('/locations/');
                    //    }
                    //} else{
                    //    $location.path('/goals');
                    //}

                    if(tmp_survey != null && tmp_survey.parent_survey__c != null){
                                $location.path('/chapters/');
                            }else{
                                $location.path('/surveys/');
                    }

				}).error(function(data, status, headers, config){
					//alert('Error! '+data);
					$rootScope.$broadcast("closeLoading");
					$rootScope.$broadcast('displayModal', {message: data, button: 'Okay'});
				});
			}
        }

        $scope.cancel = function() {

            $rootScope.$broadcast('displayModalPrompt', {message: 'This will remove all saved Surveys.', affirmativeButton: 'Okay', negativeButton:'Cancel'});

            $scope.negativeListenerOff = $scope.$on('negativeModalResponse', function(event){
                    //User Canceled. do nothing.
                    $scope.negativeListenerOff();
                    $scope.affirmativeListenerOff();
            });

            $scope.affirmativeListenerOff = $scope.$on('affirmativeModalResponse', function(event){
                //User said okay. Delete the surveys. Go to location select.
                Surveys.reset();
                $scope.negativeListenerOff();
                $scope.affirmativeListenerOff();
                $location.path('/surveys/');

            });

			/*var r = confirm("This will remove all saved Surveys.");
			if (r == true){
				Surveys.reset();
				$location.path('/locations/');
			}*/

        }

        $scope.$on('backButtonPressed', function(event){
            if(!$rootScope.modalDisplayed){
                console.log("Review page. No modal displayed. Exit the app.");
                navigator.app.exitApp();
                return false;
            }
        });
    },

    splash: function($scope, $rootScope, $location, $http, $timeout, User) {
        var that = this;
	console.log("Application Loaded.");
	var appPath = '/login';

	//if already authenticated
	if(localStorage.getItem("contactId") != null){
		User.hasAccess = true;
		User.id = localStorage.getItem("contactId");
		User.email = localStorage.getItem("contactEmail");
		$http.defaults.headers.common['UID'] = localStorage.getItem("contactId");
		app.SigGen.setEmail(User.email);
	}
	if(!IS_DEVICE_READY){
		document.addEventListener('deviceready', function(){navigator.splashscreen.hide()}, false);
	} else{
		navigator.splashscreen.hide();
	}
	$location.path(appPath);
    $scope.$on('backButtonPressed', function(event){
        console.log("Splash page. Exit the app.");
        navigator.app.exitApp();
        return false;
    });
    },


    modal: function($scope, $rootScope){
		$scope.close = function(){
			$rootScope.$broadcast('closeModal');
		};
    },

    modalPrompt: function($scope, $rootScope){
        $scope.negative = function(){
            $rootScope.$broadcast('negativeModalResponse');
        }
        $scope.affirmative = function(){
            $rootScope.$broadcast('affirmativeModalResponse');
        }
    },

    photoUpload: function($scope, $rootScope){
        if($scope.photos){
            console.log("Photos exists in scope!");
            console.log($scope.photos);
        } else{
            console.log("Photos does not exist in scope.");
            $scope.photos = [null, null, null];
        }
        $scope.hasData = function(index){
            console.log("Object at index: " + index);
            return ($scope.photos[index] instanceof Object && typeof($scope.photos[index].localPath) != "undefined") ? "hasData" : "noData";
        };
        var pictureSuccess = function(imageData){
            console.log("Successfully grabbed image");
            //Retrieve the first empty index
            var index = $scope.photos.indexOf(null);

            //Create photo object
            var photo = {};
            //photo.localPath = "data:image/jpeg;base64," + imageData;
            photo.localPath = imageData;

            $scope.photos.splice(index, 1, photo);
            $scope.$digest();
        };

        var pictureError = function(errorMessage){
            console.log("Image Capture Failed: " + errorMessage);
            //$rootScope.$broadcast('displayModal', {message: 'Unable to Capture Photo', button: 'Okay'});
        };
        $scope.addPhoto = function(){
            console.log("Should Add Photo");
            if(navigator.camera){
                navigator.camera.getPicture(pictureSuccess, pictureError, {quality: 50, destinationType: Camera.DestinationType.FILE_URI, targetWidth: 800, targetHeight: 800, correctOrientation: true});
            }
        };
        $scope.deletePhoto = function(index){
            //Remove image
            $scope.photos.splice(index, 1);
            //Push null
            $scope.photos.push(null);
        };
    }

};
con.login.$inject = ['$scope', '$rootScope', '$http', '$location', 'UserService', 'AuthenticatedHttp'];
//con.location.$inject = ['$scope', '$rootScope', '$http', '$location', 'Account', 'AuthenticatedResource', 'Goals', 'Surveys'];
con.surveys.$inject = ['$scope', '$rootScope', '$http', '$location', 'Surveys', 'Account', 'AuthenticatedResource'];
con.chapters.$inject = ['$scope', '$rootScope', '$http', '$location', 'Surveys', 'Account'];
//con.productDetail.$inject = ['$scope', '$rootScope', '$routeParams', '$location', 'Product', 'Surveys', 'AuthenticatedResource'];
con.question.$inject = ['$scope', '$routeParams', '$rootScope', '$location', 'Surveys', 'BreadcrumbService', 'FileTransferService', 'Goals', 'Account'];
con.review.$inject = ['$scope', '$rootScope', '$http', '$routeParams', '$location', 'Surveys', 'Product', 'Account', 'AuthenticatedHttp', 'BreadcrumbService', 'Goals'];
con.splash.$inject = ['$scope', '$rootScope', '$location', '$http', '$timeout', 'UserService'];
//con.notes.$inject = ['$scope', '$rootScope', '$location', 'AuthenticatedHttp', 'Surveys', 'Goals'];
con.modal.$inject = ['$scope', '$rootScope'];
//con.goals.$inject = ['$scope', '$http', '$location', '$rootScope', 'Surveys', 'Account', 'AuthenticatedResource', 'Goals'];
//con.goalDetail.$inject = ['$scope', '$http', '$location', 'Goals', 'Account', 'Surveys', 'AuthenticatedResource', '$rootScope'];
//con.qstack.$inject = ['$scope', '$routeParams', '$rootScope', '$location', 'BreadcrumbService'];
