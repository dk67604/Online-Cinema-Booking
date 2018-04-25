var app = angular.module("payModule",[]);
app.controller("payController",function ($scope,$http,$window){



				$scope.ticket={};
				$scope.user={
					"Name":"",
					"CardNo":"",
					"ExpM":"",
					"ExpY":"",
					"CardType":"",
					"securCode":""
				}
				$scope.ticket.hallnumber = $window.localStorage.getItem("hallNumber");
				$scope.ticket.movieTitle = $window.localStorage.getItem("movieTitle");
				$scope.ticket.userEmailID = $window.localStorage.getItem("userEmailID");
				$scope.ticket.normalSeats = $window.localStorage.getItem("normalSeats");
				$scope.ticket.showTime = $window.localStorage.getItem("showtime");
				$scope.ticket.childSeats = $window.localStorage.getItem("childSeats");

				$scope.ticket.seniorSeats = $window.localStorage.getItem("seniorSeats");

				$scope.ticket.selectedSeats = $window.localStorage.getItem("selectedSeats");

				$scope.ticket.ticketPrice = $window.localStorage.getItem("ticketPrice");


				$scope.submitPayment = function(){

						$http.post("http://localhost:8080/api/bookticket",$scope.ticket).
		        			then(function(response) {
								
		            			$window.location.href="Genres.html";
											$window.location.href;

		        			});



}
});
