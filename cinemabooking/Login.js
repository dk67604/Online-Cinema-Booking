var app = angular.module("loginModule",[]);



	app.controller("loginController",function ($scope,$http,$location,$window){

				console.log("JS called");

				$scope.loginUser = function () {
				 var regData = $scope.user

				 if(!$scope.user.type){
				 	$scope.user.type=false;
				 }
				 $http.post("http://localhost:8080/api/login",regData).
        			then(function(response) {
									$window.localStorage.clear();
            			$scope.type = response.data.messageCode;

            			if($scope.type==2000){
										$window.localStorage.setItem("userEmailID",$scope.user.userName);
            				$window.location.href = 'Genres.html';
            				$window.location.href;
            			}
            			else if($scope.type==3000){
            				$window.location.href = 'Admin.html';
            				$window.location.href;
            			}
            			else if($scope.type==2001){
            				$scope.userNotExist = true;
            			}
            			else if($scope.type==2003){
            				$scope.passwordIncorrect = true;
            			}


        			});
        		console.log($scope.user)

				}

				$scope.forgetPassword = function() {
					$window.location.href = 'ForgetPassword.html';
            			$window.location.href;
				}


				/*$http.get("http://localhost:8080/api/status").
        			then(function(response) {
            			$scope.greeting = response.data;
        			});
        		*/
			});
