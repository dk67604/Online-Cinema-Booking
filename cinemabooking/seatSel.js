var app = angular.module("seatModule",[]);
app.controller("seatController",function ($scope,$http,$window,$compile){
	var size = parseInt($window.localStorage.getItem("noOfTickets"));
	console.log(size);
	var seats=[];


				function positionSeat(seatNo, x, y){

	var seat = seatNo.toString();
	console.log("position "+seat);

}// end of positionSeat()



$scope.addSeat = function(seatNo){
	console.log("Calling addSeat");
	console.log(seatNo);
	if(seats.indexOf(seatNo) != -1){
		seats.splice(seats.indexOf(seatNo),1);
		document.getElementById(seatNo).style.backgroundColor = "green";
	}
	else{
		seats.push(seatNo);
		var no = "style_"+seatNo;
		//console.log(no);

		document.getElementById(seatNo).style.backgroundColor = "orange";


	}
	$scope.selectSeats = seats;
	//console.log(seats);
}



$scope.bookSeats = function(selectedSeats){
	if(!selectedSeats){
		alert("Please select some seats");
	}
	else if(selectedSeats.length < size){
		alert("Please select "+ size +" seats.");
	}
	else if(selectedSeats.length > size){
		alert("Please select "+ size +" seats.");
	}
	else{
		if($window.localStorage.getItem("selectedSeats")){
			$window.localStorage.removeItem("selectedSeats");
		}
		$window.localStorage.setItem("selectedSeats",selectedSeats);
		$window.location.href = "TicketCustomization.html";
		$window.location.href;
		//console.log("Calling API");
	}
}



function makeSeat( seatNo, x, y){
	//console.log(seatNo);
	var $htmlButton = $("<button style=\"background-color: green; text-color= white; width:4%; height:4%; margin-left: 55px; margin-top: 25px; position=\"relative\" \"  data-ng-click = addSeat(\""+ y+x +"\") id=\"" +y+ x + "\"" + " name=\"" + seatNo + "\" " + " value=\""+ y  + x +"\">" + y+x + "</button>").appendTo('#container');
	//document.getElementById("container").innerHTML += $htmlButton; // the button has been added to the div called container.
	$compile($htmlButton)($scope);


	//the position of the button on the page is set.

	$scope.create = function(){
		positionSeat( seatNo, x, y);	//do nothing
	}
}// end of makeSeat()




// This function creates so many seats in
// several rows.
function generateSeats( number ){

	var n = 1; // keeps track of the number of seats made.
	var A = ['A','B','C','D','E','F','G','H','I'];
	// While the number of seats made is less than the parameter called number.
	while (n < number){

		for(  y = 0 ; y < 9 ; y++ ){// controls the number of rows that will exist

			for( var x = 0 ; x < 9 ; x++ ){// [(number-to-make)/2] columns per row.

				makeSeat(n,x+1,A[y]);// seat number

				n++; // increases the number of seats made by 1.

				if( n > number ) break;
			}// end of inner for-loop
				if( n > number ) break;
		}// end of outter for-loop

	}// end of while( n> number )


}
			$window.onload = function(){

				console.log("JS Called");
				generateSeats(81);
				//alert("DAMN IT");
			};


			});
