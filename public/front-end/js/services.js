angular.module('fieldSurveyServices', ['ngResource'])
    .factory('Account', function(){
    	var accountObj = {"sfid": "",
                     "tdlinx_outlet_desc__c": "",
                     "name": "",
                     "tdlinx_outlet_state__c": "",
                     "tdlinx_outlet_addr__c": "",
                     "tdlinx_outlet_city__c": "",
      		     "tdlinx_outlet_zip_code__c": "",
                     "distance": 0};
        var cachedAccounts = {
            myAccounts: [],
            proximityAccounts: []
        };
	         return {
            getCache: function(){
                return [cachedAccounts];
            },
            setCache: function(data){
                if(data.myAccounts instanceof Array){
                    cachedAccounts.myAccounts = angular.copy(data.myAccounts);
                } else {
                    cachedAccounts.myAccounts = [];
                }

                if(data.proximityAccounts instanceof Array){
                    cachedAccounts.proximityAccounts = angular.copy(data.proximityAccounts);
                } else {
                    cachedAccounts.proximityAccounts = [];
                }
            },
			setAccount: function(locationObj){
				if(locationObj != undefined && locationObj != null && locationObj && locationObj.name != undefined && locationObj.store_number != undefined){
					locationObj.tdlinx_outlet_desc__c = locationObj.name + " " + locationObj.store_number;
				}
				accountObj = locationObj;
			},
			getAccount: function(){
				return accountObj;
			},
			checkValidation: function(){
				var isValid = true;
				if(accountObj.name == undefined
				|| accountObj.tdlinx_outlet_city__c == undefined
				|| accountObj.tdlinx_outlet_state__c == undefined
				|| accountObj.tdlinx_outlet_addr__c == undefined
				|| accountObj.store_number == undefined){

					isValid = false;
				}
				return isValid;
			},
			invalidFields: function(){
				var neededFields = [];
				if(accountObj.tdlinx_outlet_state__c == undefined){
					neededFields.push("State");
				}
				if(accountObj.tdlinx_outlet_city__c == undefined){
					neededFields.push("City");
				}
				if(accountObj.name == undefined){
					neededFields.push("Name");
				}
				if(accountObj.tdlinx_outlet__addr__c == undefined){
					neededFields.push("Street");
				}
				if(accountObj.store_number == undefined){
					neededFields.push("Store #");
				}

				return neededFields;
			}
		 }
    })
    .factory('Product', function(){
	var productObj = {"diageo_category__c": "",
			"nielsen_brand__c": "",
			"nielsen_brand_variant__c": "",
			"sfid": "",
			"nielsen_size__c": "",
			"upc_code__c": ""
		};
	return {
		setProduct: function(product){
			productObj = product;
		},
		getProduct: function(){
			return productObj;
		}
	}
    })
    .factory('Surveys', function($resource){
		var survey = {};			//the 'working' survey record - the one being created/edited
		//var currentQuestion = null;	//the 'working' question
		//var nextQuestion = null;	//the 'working' question's next one

		var surveyTemplate = util.getLocalStorage(CACHED_SURVEY_TEMPLATE);
		if(surveyTemplate == null || surveyTemplate == 'null'){
			surveyTemplate = {};	//meant to hold the survey to clone from
		}
		//var surveyKey = 'SurveyData';
		var surveyList = util.getLocalStorage(CACHED_SURVEY_DATA);
		if(surveyList == null || surveyList == 'null'){
			surveyList = [];			//list of saved surveys that will be reviewed/submitted
		}
		console.log('Storage: '+JSON.stringify(util.getLocalStorage(CACHED_SURVEY_DATA)));

		return {

			addSurvey: function(selectedSurvey, selectedAccount){
				surveyTemplate = selectedSurvey;

				surveyTemplate.location = selectedAccount;
				surveyTemplate.contact__c = localStorage.contactId;
				console.log(surveyTemplate.contact__c);
				console.log('Account ID: '+selectedAccount.sfid);
				if(selectedAccount.sfid != '' && selectedAccount.sfid != 'undefined' && selectedAccount.sfid != undefined){
					surveyTemplate.account__c = selectedAccount.sfid;
				}else{
					surveyTemplate.manually_entered_store_name__c = selectedAccount.name;
					surveyTemplate.manually_entered_store_number__c = selectedAccount.store_number;
					surveyTemplate.manually_entered_store_address__c = selectedAccount.tdlinx_outlet_addr__c;
					surveyTemplate.manually_entered_city__c = selectedAccount.tdlinx_outlet_city__c;
					surveyTemplate.manually_entered_state__c = selectedAccount.tdlinx_outlet_state__c;
				}
				surveyTemplate.survey_id = surveyList.length;

				util.setLocalStorage(CACHED_SURVEY_TEMPLATE, surveyTemplate);

				survey = angular.copy(surveyTemplate);
				survey.questions = [];  //wipe out questions so we only save what they answered. we will add when saved.
			},

			addProduct: function(selectedProduct){
				survey.nielson_product__c = selectedProduct[0].sfid;
				survey.product = selectedProduct;
				//console.log('Surveys Product: '+survey.nielson_product__c+' | '+survey.product[0].sfid);
			},

			//when they answer a question, save that set to the list
			addAnswer: function(count, answer){

				//TODO: refactor to just add the answers back to the orig question list returned above
				console.log('Question: '+JSON.stringify(survey.questions[count]));
				console.log('Answer: '+answer);
			},
            //Sets all hasBeenAccessedFlags to false
            prepForEdit: function(){
                for(var i = 0; i < surveyList.length; i++){
                    var surv = surveyList[i];
                    for(var j = 0; j < surv.questions.length; j++){
                        surv.questions[j].hasBeenAccessed = false;
                    }
                }
            },

			getQuestion: function(questionId){
				var currentQuestion = null;

				//check if they already answered it and its in the results
				angular.forEach(survey.questions, function(question){
					if(question.sfid == questionId){
						console.log('Question already answered.');
						currentQuestion = question;
					}
				});

				//if they haven't answered it yet, get it from template
				if(currentQuestion == null){
					angular.forEach(surveyTemplate.questions, function(question){
						if(question.sfid == questionId){
							console.log('Question not yet answered.');

                            // MD: Adding the following code to clear answers from previous surveys
                            // submitted by the user
                            question.answer_text__c = null;
                            question.answer_value__c = null;

                            angular.forEach(question.answer_options__c, function(option) {
                              option.checked = false;
                            });
                            // END


							currentQuestion = question;
							survey.questions.push(question);
						}
					});
				}

				return currentQuestion;
			},

			getNextQuestion: function(question, drillDown){
				console.log('NextQ: '+question.next_question__c);
				console.log('ParentQ: '+question.parent_question__c);

				var childQuestions = [];
				//only do this if going down, not back up
				if(drillDown){
					childQuestions = this.getChildQuestions(question);
					this.findOrphanQuestions(question, false);
				}

				if(childQuestions.length > 0){
					console.log('Returning Child');
                    if(question.question_type__c != "Multi-Select"){
                        var childQuestion = childQuestions[0];
    					return childQuestion;
                    } else{
                        console.log("Multi-Select with Children");
                        for(var i = 0; i < childQuestions.length; i++){
                            var childQuestion = childQuestions[i];
                            console.log("ChildQ: ");
                            console.log(childQuestion);
                            var answered = false;
                            for(var j = 0; j < survey.questions.length; j++){
                                var surveyQuestion = survey.questions[j];
                                if(childQuestion.sfid == surveyQuestion.sfid){
                                    console.log("surveyQuestion matches:");
                                    console.log(surveyQuestion);

                                    //answered = (surveyQuestion.answer_text__c != null || surveyQuestion.answer_value__c != null);
                                    answered = surveyQuestion.hasBeenAccessed;
                                    break;
                                }
                            }
                            if(!answered){
                                return childQuestion;
                            }
                        }
                    }
				}
                //Should return before this if not necessary.
                console.log("No child questions need answering. Question:");
                console.log(question);
                if(question.next_question__c != null){
					var nextQ = this.getQuestion(question.next_question__c);
                    return nextQ;
				}else{
                    console.log("No child questions, no next question: ");
                    console.log(question);
					if(question.parent_question__c != null){
						//get the parent and recursively try again!
						var parentQuestion = this.getQuestion(question.parent_question__c);
                        console.log("Parent question:");
                        console.log(parentQuestion);
                        if(parentQuestion.question_type__c != "Multi-Select"){
    						return this.getNextQuestion(parentQuestion, false);
                        } else{
                            return this.getNextQuestion(parentQuestion, true);
                        }
					}else{
						return null;
					}
				}
			},

			//check if there is a child record whose conditional matches what was entered on the parent
			getChildQuestions: function(parentQ){
				var matchingChildren = [];

                console.log(parentQ);

				angular.forEach(surveyTemplate.questions, function(childQ){
					if(childQ.parent_question__c == parentQ.sfid){
                        if(parentQ.question_type__c != "Multi-Select"){
    						console.log('Child Conditional | Parent Answer: '+childQ.conditional_answer__c+" | "+parentQ.answer_text__c);

    						if((childQ.conditional_answer__c == parentQ.answer_text__c || childQ.conditional_answer__c == parentQ.answer_value__c)
    							&& childQ.conditional_answer__c != null){

    							console.log('Matching Child Question: '+JSON.stringify(childQ));
    							matchingChildren.push(childQ);
    						}
                        } else {
                            var multiAnswers = (typeof parentQ.answer_text__c === "string") ? parentQ.answer_text__c.split(";") : [];
                            for (var i = 0; i < multiAnswers.length; i++){
                                if(childQ.conditional_answer__c == multiAnswers[i]){
                                    matchingChildren.push(childQ);
                                    break;
                                }
                            }
                        }
					}
				});
				//console.log('Returning NULL');
				return matchingChildren;
			},

			//find all Orphaned child questions of the one you changed (also recursive)
			findOrphanQuestions: function(parentQ, alreadyMatched){
				console.log('Checking for Orphans of: '+parentQ.question_text__c);
				var childList = [];

				angular.forEach(survey.questions, function(childQ, index){

					if(childQ.parent_question__c == parentQ.sfid){
                        if(parentQ.question_type__c != "Multi-Select"){
    						if(((childQ.conditional_answer__c != parentQ.answer_text__c
    							&& childQ.conditional_answer__c != parentQ.answer_value__c)
    							&& childQ.conditional_answer__c != null) || alreadyMatched){

    							childList.push(childQ.sfid);
    						}
                        } else{
                            var childQMatch = false;
                            var multiAnswers = (typeof parentQ.answer_text__c === "string") ? parentQ.answer_text__c.split(";") : [];
                            for (var i = 0; i < multiAnswers.length; i++){
                                if(childQ.conditional_answer__c == multiAnswers[i]){
                                    childQMatch = true;
                                    break;
                                }
                            }
                            if(!childQMatch || alreadyMatched){
                                childList.push(childQ);
                            }
                        }
					}
				});

				for(var i=0; i<childList.length; i++){
					this.removeQuestion(childList[i]);
				}

				return null;
			},

			//remove the child question found, it's next question and recursively remove their children
			removeQuestion: function(quesId){
				var currentQ = null;
				var nextQ = null;

				angular.forEach(survey.questions, function(q, index){
					if(q.sfid == quesId){
						console.log('Removing: '+q.question_text__c);
						nextQ = q.next_question__c;
						currentQ = q;
						survey.questions.splice(index, 1);
					}
				});

				if(currentQ != null){
					this.findOrphanQuestions(currentQ, true);
				}

				if(nextQ != null){
					this.removeQuestion(nextQ);
				}
			},

			//total questions that could be answered
			getQuestionCount: function(){
                var count = 0;
                for(q in surveyTemplate.questions){
                    if(surveyTemplate.questions[q].parent_question__c !== null){
                        //Prevent counting inactive children!
                        var parent = null;
                        for(p in surveyTemplate.questions){
                            if(surveyTemplate.questions[p].sfid === surveyTemplate.questions[q].parent_question__c){
                                parent = surveyTemplate.questions[p];
                                break;
                            }
                        }
                        if(parent !== null){
                            count++;
                        }
                    } else{
                        count++;
                    }
                }
				return count;
			},

			saveDisplay: function(breadcrumbs){
				var tempSurvey = angular.copy(survey);
				console.log('SurveyId: '+tempSurvey.survey_id);

				//if this is edit, update, don't insert
				if(surveyList[tempSurvey.survey_id] != null){
					surveyList[tempSurvey.survey_id] = tempSurvey;
				}else{
					surveyList.push(tempSurvey);
					console.dir('Storage: '+localStorage.getItem(CACHED_SURVEY_DATA));
				}
				console.log('WOOT: '+JSON.stringify(breadcrumbs));
				this.getOrderedSurveys(breadcrumbs);
				util.setLocalStorage(CACHED_SURVEY_DATA, surveyList);

				//reset survey to be used for another display
				survey = angular.copy(surveyTemplate);
				survey.questions = [];
				console.log('SurveyList: '+JSON.stringify(surveyList));
				survey.survey_id = surveyList.length;
				console.log('SurveyId: '+survey.survey_id);
			},

			getSurvey: function(surveyId){
				console.log('Survey Questions: '+survey.questions);
				console.log('Survey ID: '+survey.survey_id);
				if(surveyId != null && surveyList[surveyId] != null){
					console.log('Survey Found!');
					survey = surveyList[surveyId];
				}else if(survey.survey_id == 'undefined' || survey.survey_id == undefined || survey.survey_id == null){
					console.log('Setting Survey from Template.');
					survey = angular.copy(surveyTemplate);
					survey.questions = [];
					survey.survey_id = surveyList.length;
				}
				return survey;
			},

			getSurveyTemplate: function(){
				return surveyTemplate;
			},

			getSurveys: function(){
				return surveyList;
			},

            getChapters: function(){
                return survey.childSurveys
            },

            getChaptersParentName: function(){
                return survey.name;
            },

            getChaptersParentId: function(){
                return survey.parent_survey__c;
            },

			getOrderedSurveys: function(breadcrumbs){
				var orderedList = [];
				var questions = [];

				if(breadcrumbs.length > 0){
					angular.forEach(breadcrumbs, function(crumb, index){
						console.log('WOOT - crumb: '+JSON.stringify(crumb));
						angular.forEach(surveyList[0].questions, function(q, index){
							console.log('WOOT - q: '+JSON.stringify(q));

							console.log('WOOT - crumb: '+crumb.sfid+' - q: '+q.sfid);
							if(q.sfid == crumb.sfid){
								console.log('WOOT - pushed.');
								questions.push(q);
							}
						});
					});
					surveyList[0].questions = questions;
				}
				console.log('WOOT: '+JSON.stringify(surveyList));
				//return surveyList;
			},

            setNotes: function(notes) {
              angular.forEach(surveyList, function(survey, key) {
                survey.note__c = notes;
              });

            },

			/*includeNone: function(question) {

			},*/

			reset: function(){
				surveyList = [];
				survey = {};
				surveyTemplate = {};
				util.clearLocalStorage(CACHED_SURVEY_DATA);
				console.log('Storage: '+JSON.stringify(util.getLocalStorage(CACHED_SURVEY_DATA)));

			}
		};
	})
    .factory('Goals', function($resource){
        var goal = {};
        var cachedGoals = [];
        var cachedMonth = new Date().getMonth(); //
        return {
            getCache: function(month){
                if(cachedMonth == month){
                    return cachedGoals;
                }
                return null;
            },
            setCache: function(month, data){
                cachedMonth = month;
                cachedGoals = angular.copy(data);
            },
            cachedMonth: function(){
                return cachedMonth;
            },
            setGoal: function(g){
                goal = g;
            },
            getGoal: function(){
                return goal;
            },
            retrieveSubmissions: function(){
                //TODO: Retrieve prior locations that this question has been submitted for.
            },
            reset: function(){
                goal = {};
            }
        };
    })
    .factory('UserService', [function(){
		return { hasAccess: false, email: '', id: null, startPath: '/locations' };
    }])
    .factory('AuthenticatedHttp', function($http){
		var authHttp = {
		post : function(url, data, config){
			//var signature = app.SigGen.getSignature();
			if(typeof(config) == "undefined"){
				config = {};
			}
			if(typeof(config.headers) == "undefined"){
				config.headers = {};
			}
			//config.headers.Salt = signature.uuid;
			//config.headers.Signature = signature.key;
			return $http.post(url, data, config);
		},
		get : function(url, config){
			var signature = app.SigGen.getSignature();
			if(typeof(config) == "undefined"){
				config = {};
			}
			if(typeof(config.headers) == "undefined"){
				config.headers = {};
			}
			config.headers.Salt = signature.uuid;
			config.headers.Signature = signature.key;
			return $http.get(url, config);
		},
		put : function(url, data, config){
			var signature = app.SigGen.getSignature();
			if(typeof(config) == "undefined"){
				config = {};
			}
			if(typeof(config.headers) == "undefined"){
				config.headers = {};
			}
			config.headers.Salt = signature.uuid;
			config.headers.Signature = signature.key;
			return $http.put(url, data, config);
		}};
		return authHttp;
    })
    .factory('AuthenticatedResource', function($rootScope, $resource, $http){
		return function(url, paramDefaults, actions){
			if(typeof(actions) == "undefined"){
				actions = {};
			}
			$http.defaults.headers.common['UID'] = '00337000002JHCK';
			actions._query = {method: 'GET', isArray: true};
			var res = $resource(url, paramDefaults, actions);
			res.query = function(data, success, error) {
				$rootScope.$broadcast("displayLoading");
				console.log("Loading Displayed");
				var realSuccess = function(data){
					if(typeof(success) == "function"){
						success(data);
					}
					$rootScope.$broadcast("closeLoading");
					console.log("loading closed");
                    // Add count valye to data
                    if (typeof data[0] != "undefined" && typeof data[0].isparent__c != "undefined"){
                        for(var i = 0; i < data.length; i++){
                            //Display code pulled
                            if (data[i].isparent__c == 't'){
                                if(data[i].childSurveys instanceof Array){
                                    data[i].count = data[i].childSurveys.length;
                                } else {
                                    data[i].count = 0;
                                }
                            }else{
                                data[i].count = data[i].questions.length;
                            }
                        }
                    }
				};
				//var sig = app.SigGen.getSignature();
				//$http.defaults.headers.common['Salt'] = sig.uuid;
				//$http.defaults.headers.common['Signature'] = sig.key;

                    var temp = res._query(data, realSuccess, error);
                    return temp;
    			};
			return res;
		};
    })
    .factory('BreadcrumbService', function(){
        var questionStack = [];
        //var resultStack = [];
		//var resultList = [];
        return {
			getResults : function(){
				return questionStack;
			},
            pushQuestion : function(question){
                questionStack.push(question);
                return;
            },
            popQuestion : function(){
                var val = questionStack.pop();
                return val;
            },
            size : function(){
                return questionStack.length;
            },
			clear : function(){
				//resultStack = questionStack;
				//resultList.push(resultStack);
				questionStack = [];
				return;
			}
        }
    })
    .factory('FileTransferService', function(){
        var s3URI = encodeURI("https://mobile-survey-images.s3.amazonaws.com/"),
        policyBase64 = "eyJleHBpcmF0aW9uIjoiMjAyMC0xMi0zMVQxMjowMDowMC4wMDBaIiwiY29uZGl0aW9ucyI6W3siYnVja2V0IjoibW9iaWxlLXN1cnZleS1pbWFnZXMifSxbInN0YXJ0cy13aXRoIiwiJGtleSIsIiJdLHsiYWNsIjoicHVibGljLXJlYWQifSxbInN0YXJ0cy13aXRoIiwiJENvbnRlbnQtVHlwZSIsIiJdLFsiY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsNTI0Mjg4MDAwXV19",
        signature = "6jyyjz0qOFDyMJQO/IqoqDP2fjA=",
        awsKey = 'AKIAIWVMFDZMX2MNPJZA',
        acl = "public-read";

        function upload(imageURI, fileName) {

            var deferred = $.Deferred(),
                ft = new FileTransfer(),
                options = new FileUploadOptions();

            options.fileKey = "file";
            options.fileName = fileName;
            options.mimeType = "image/jpeg";
            options.chunkedMode = false;
            options.params = {
                "key": fileName,
                "AWSAccessKeyId": awsKey,
                "acl": acl,
                "policy": policyBase64,
                "signature": signature,
                "Content-Type": "image/jpeg"
            };

            ft.upload(imageURI, s3URI,
                function (e) {
                    deferred.resolve(e, imageURI);
                },
                function (e) {
                    deferred.reject(e);
                }, options);

            return deferred.promise();

        }

        return {
        upload: upload
        }
    })
;
