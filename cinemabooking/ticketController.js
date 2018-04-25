var app = angular.module("ticketModule",[]).config(function($sceProvider) {
    $sceProvider.enabled(false);
 });


app.controller("ticketController",function ($scope,$http,$window,$location,$sce){


			$window.onload = function () {



			var title = $window.localStorage.getItem("movieTitle");
			//$window.localStorage.removeItem("movieTitle");


			$http.get("http://localhost:8080/api/showtimes",{params: { "title": title }}).
        			then(function(response) {

            			$scope.showtimes = response.data.response.showtimes;
				console.log($scope.showtimes);


        			});
			};

      $scope.selectShowtime = function(showtimeGet){
        $window.localStorage.setItem("showtime",showtimeGet.showtime);
        console.log(showtimeGet.hallnumber);
        $window.localStorage.setItem("hallNumber",showtimeGet.hallnumber);

        //console.log($window.localStorage.getItem("showtime"));
      }


			$scope.seatsPresent = false;
				$scope.selectedSeats = $window.localStorage.getItem("selectedSeats");
				console.log($scope.selectedSeats);
				if($scope.selectedSeats){
					$scope.seatsPresent = true;
				}

				$scope.link = "https://www.youtube.com/embed/tgbNymZ7vqY";
				$scope.seat={
					"normal":0,
					"child":0,
					"senior":0
				};
				$scope.price={
					"cost":0
				};
        if($window.localStorage.getItem("normalSeats")){
          $scope.seat.normal = $window.localStorage.getItem("normalSeats");
        }

        if($window.localStorage.getItem("childSeats")){
          $scope.seat.child = $window.localStorage.getItem("childSeats");
        }

        if($window.localStorage.getItem("seniorSeats")){
          $scope.seat.senior = $window.localStorage.getItem("seniorSeats");
        }
        if($window.localStorage.getItem("ticketPrice")){
          $scope.price.cost = $window.localStorage.getItem("ticketPrice");
        }



				$scope.continue = function(){

					if($scope.price.cost != 0){
						$window.location.href = "Payment.html";
						$window.location.href;
					}
				}

				//$scope.price = $scope.seat.normal*12+$scope.seat.child*10+$scope.seat.senior*8;
				$scope.selectSeats = function(seats){
					if(seats.normal==0 && seats.child==0 && seats.senior==0){
						alert("Please select enter valid number of seats");
					}else{
					$scope.price.cost = seats.normal*12+seats.child*10+seats.senior*8;
					console.log($scope.price.cost);
					$window.localStorage.setItem("normalSeats",seats.normal);
					$window.localStorage.setItem("childSeats",seats.child);
					$window.localStorage.setItem("seniorSeats",seats.senior);
					$window.localStorage.setItem("ticketPrice",$scope.price.cost);
					var size = parseInt(seats.normal)+parseInt(seats.child)+parseInt(seats.senior);
					$window.localStorage.setItem("noOfTickets",size);
					$window.location.href="seatSel.html";
					$window.location.href;
					}

				}

				console.log($scope.price.cost);



});
