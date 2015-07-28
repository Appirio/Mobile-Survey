angular.module('fieldSurveyDirectives', [])
	.directive('eatClick', function() {
		return function(scope, element, attrs) {
			element.on('touchstart', function(event) {
				event.preventDefault();
				event.stopPropagation();
			});
		}
	}
).directive('modalWindow', function($rootScope){
		return {
			link: function(scope, elm, attrs){
				scope.$on('displayModal', function(event, modalParams){
					scope.displayMessage = modalParams.message;
					scope.buttonText = modalParams.button;
					scope.type = modalParams.type;
					console.log('modalParams: '+JSON.stringify(modalParams));

					if(scope.type == 'grade'){
						scope.scorePercentage = modalParams.percentage;
						scope.scoreGrade = modalParams.grade;

						console.log('Type: Grade! '+scope.scoreGrade);
					}
					$rootScope.modalDisplayed = true;

					scope.$apply();
					$(elm).modalBox();
				});
				scope.$on('closeModal', function(event){
					$(elm).modalBox('close');
					$rootScope.modalDisplayed = false;
				});
			}
		}
	}
).directive('modalPrompt', function($rootScope){
			return {
				link: function(scope, elm, attrs){
					scope.answered = true;

					scope.$on('displayModalPrompt', function(event, modalParams){
						scope.displayMessage = modalParams.message;
						scope.affirmativeButtonText = modalParams.affirmativeButton;
						scope.negativeButtonText = modalParams.negativeButton;

						scope.answered = false;

						console.log('modalParams: '+JSON.stringify(modalParams));

						$rootScope.modalDisplayed = true;

						scope.$apply();
						$(elm).modalBox();
					});
					scope.$on('closeModal', function(event){
						$(elm).modalBox('close');
						if(scope.answered){
							$rootScope.$broadcast('negativeModalResponse');
						}
					});
					scope.$on('negativeModalResponse', function(event){
						$(elm).modalBox('close');
						scope.answered = true;
						$rootScope.modalDisplayed = false;
					});
					scope.$on('affirmativeModalResponse', function(event){
						$(elm).modalBox('close');
						scope.answered = true;
						$rootScope.modalDisplayed = false;
					});
				}
			}
		}
		).directive('loadingOverlay', function(){
		return {
			link : function(scope, elm, attrs){
				scope.showLoading = false;
				scope.$on('displayLoading', function(event){
					console.log("loading called");
					scope.showLoading = true;
					if(!scope.$$phase){
						scope.$digest();
					}
				});
				scope.$on('closeLoading', function(event){
					console.log("loading ended");
					scope.showLoading = false;
					if(!scope.$$phase){
						scope.$digest();
					}
				});
			}		}
	}
	).directive('limitedLength', function(){
		return {
			restrict: 'C',
			scope: true,
			require: 'ngModel',
			link: function(scope, elm, attrs, ngModelCtrl){
				elm.on('keypress', function(evt){
					var tempstring = elm.val(); //This is the value prior to our keypress.
					if(tempstring.length >= attrs.lengthLimit){
						evt.preventDefault();
						return false;
					} else{
						ngModelCtrl.$setViewValue(elm.val() + String.fromCharCode(evt.keyCode));
						scope.$apply();
					}
				});
				elm.on('keyup', function(evt){
					if(evt.keyCode ==8){
						ngModelCtrl.$setViewValue(elm.val());
						scope.$apply();
					}
				});

			}
		};
	}
	).directive('numeric', function(){
		return {
			restrict: 'C',
			scope: true,
			require: 'ngModel',
			link: function(scope, elm, attrs, ngModelCtrl){
				elm.on('keypress', function(evt){
					if (evt.keyCode > 31 && (evt.keyCode < 48 || evt.keyCode > 57)){
           					 evt.preventDefault();
						 return false;
					} else{
					     ngModelCtrl.$setViewValue(elm.val() + String.fromCharCode(evt.keyCode));
					     scope.$apply();
					}
				});
				elm.on('keyup', function(evt){
					if(evt.keyCode ==8){
						ngModelCtrl.$setViewValue(elm.val());
						scope.$apply();
					}
				});

			}
		};
	}
	).directive('currency', function($compile){
		return {
			restrict: 'C',
			scope: true,
			require: 'ngModel',
			link: function(scope, elm, attrs, ctrl){
				$compile()(scope.$parent);
				elm.on('keypress', function(evt){
					console.log(evt);
					if (evt.keyCode > 31 && (evt.keyCode < 48 || evt.keyCode > 57) && evt.keyCode != 46){
						evt.preventDefault();
						return false;
					}else{
						var temp = elm.val();
						if(temp.indexOf('.') >= 0 && evt.keyCode == 46){
							evt.preventDefault();
							return false;
						}
						var split = temp.split('.');
						var dec = split[1];
						if(typeof(dec) != "undefined" && typeof(dec.length) != "undefined" && dec.length >= 2){
							evt.preventDefault();
							return false;
						}
						ctrl.$setViewValue(elm.val() + String.fromCharCode(evt.keyCode));
						scope.$apply();
					}
				});
				elm.on('keyup', function(evt){
					if(evt.keyCode ==8){
						ctrl.$setViewValue(elm.val());
						scope.$apply();
					}
				});
			}
		};
	}
	).directive('photoUpload', function($compile){
		return {
           		restrict: 'A',
           		templateUrl: 'tmpl/photoUpload.html',
            		controller: con.photoUpload
       		 };
	})
