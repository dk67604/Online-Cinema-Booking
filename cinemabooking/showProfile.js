var app = angular.module("profileModule",[]);
app.controller("profileController",function ($scope,$http,$window){
				console.log($window.localStorage.getItem("userEmailID"));
				$scope.user = {
					"Name" : "Maikhar Parikh",
					"EmailID" : "maikhar@hotmail.com",
					"Address" : {
						"Street" : "Lakeside Dr",
						"City" : "Athens",
						"State" : "Georgia",
						"Zipcode" : "30605"
					}
				}

				$scope.updateUser = function(userValues){
					console.log(userValues);
				}
});
