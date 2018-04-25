var app = angular
			.module("regModule",[])
			.controller("regController",function ($scope,$http,$window){


				$scope.emailExist = false;

				function formatDate(date) {
					var monthNames = [
					"January", "February", "March",
					"April", "May", "June", "July",
					"August", "September", "October",
					"November", "December"
					];

				var day = date.getDate();
				var monthIndex = date.getMonth();
				var year = date.getFullYear();

				return (monthIndex+1) + '/' + day + '/' + year;
				}





				$scope.registerUser = function () {
				 var regData = $scope.employee

				 $scope.employee.type="Customer";

					if($scope.expiryDate){
						console.log("going into function")
						 $scope.employee.creditCard = {};
						var d = formatDate($scope.expiryDate)
						 $scope.employee.creditCard.cardNumber = $scope.card;
						 $scope.employee.creditCard.securityCode = $scope.securitycode;
						$scope.employee.creditCard.expiryDate = d


						console.log($scope.card)
					}

				 $http.post("http://localhost:8080/api/register",$scope.employee).
        			then(function(response) {
				console.log(response)
				$scope.status = response.data.messageCode
					if($scope.status == 1000){
						$scope.emailExist = true;}
				else{
            			$scope.greeting = response.data;
            			$window.location.href = 'Homepage.html';
            			$window.location.href;
				}
        			},
        			function(response){
        				$scope.status = response.messageCode
					if($scope.status == 1000){
						$scope.emailExist = true;}
        				//$window.location.href = '/study/uga/se/project/Fail.html';
        				//$window.location.href;
        			});
        		console.log($scope.employee)

				};




		});
