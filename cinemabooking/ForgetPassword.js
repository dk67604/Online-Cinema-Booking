var app = angular.module("forgetModule",[]);
			app.controller("forgetController",function ($scope,$http,$window){
					
					$scope.sendEmail = function()
					{
						var regData = $scope.user;
					
						$http.post("http://localhost:8080/api/forgotPassword",regData).
        					then(function(response) 
						{
            						$scope.type = response.data.messageCode;

			            			if($scope.type==4001)
							{
            				
							$scope.emailNotFound=true;
            						}
							else{
								$window.location.href = 'Homepage.html';
            							$window.location.href;
							}
            			
            					});}
            			

        				});
				
