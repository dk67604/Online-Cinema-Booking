var app = angular.module("movieModule",[]).config(function($sceProvider) {
    $sceProvider.enabled(false);
 });


app.controller("movieController",function ($scope,$http,$window,$location,$sce){

      $scope.goToProfile = function(){
        $window.location.href="ProfilePage.html";
        $window.location.href;
      }

			$window.onload = function () {
				$http.get("http://localhost:8080/api/movies").
        			then(function(response) {

            			$scope.movies = response.data.response.movies;

        			});
			};
			$scope.bookTicket = function(movie){

				$window.localStorage.setItem("movieTitle",movie.title);

				$window.location.href="TicketCustomization.html";
				$window.location.href;
			};





});
