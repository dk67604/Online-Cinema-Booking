var app = angular
			.module("myModule",[])
			.controller("myController",function ($scope,$http){

				var Movie = [
					{name: "Titanic", genre: "Romantic", date: "03/08/18", time: "09:00"},
					{name: "Avengers", genre: "Sci-Fi", date: "03/09/18", time: "10:00"},
					{name: "Shawshank Redemption", genre: "Drama", date: "03/10/18", time: "11:00"},
					{name: "James Bond", genre: "Thriller", date: "03/11/18", time: "12:00"},
				];

				$http.get("http://localhost:8080/api/status").
        			then(function(response) {
            			$scope.greeting = response.data;
        			});
        		console.log($scope.greeting)

				$scope.Movie = Movie;
			});