var app = angular
			.module("logoutModule",[])
			.controller("logOutController",function ($scope,$window){

				$scope.logOut = function(){
						$window.location.href = 'Homepage.html';
            			$window.location.href;
				}
			});	