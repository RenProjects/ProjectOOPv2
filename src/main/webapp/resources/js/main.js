/**
 * 
 */

var iterations = 0;
var images;

var chosenImage;

/**
 * Main
 */
$(document).ready(function() {
	$('#submitAnswer').click(function() {
		submitAnswer();
		return false;
	});
	
	// Skrivanje div-a
	$('#imageCont').hide();
	$('#anwserCont').hide();
	
	// Početni +
	setTimeout(function() {
		$('img.start').hide();
		setUpQuiz();
	}, 2000);

});

function setUpQuiz() {
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/oop/config',
		success : function(data) {
			iterations = data;
			iterate();
		}
	});

}

/**
 * Get images
 */
function getImages() {
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/oop/getImages',
		success : function(data) {
			// Set up images on screen
			var number = Math.floor(Math.random() * 2);
			images = data;

			// Kod za random pozicioniranje slika
			if (number == 0) {
				$('#imageCont .left img').attr("src", data[0].url);
				$('#imageCont .left span.text').text(data[0].character);

				$('#imageCont .right img').attr("src", data[1].url);
				$('#imageCont .right span.text').text(data[1].character);
			} else {
				$('#imageCont .left img').attr("src", data[1].url);
				$('#imageCont .left span.text').text(data[1].character);

				$('#imageCont .right img').attr("src", data[0].url);
				$('#imageCont .right span.text').text(data[0].character);
			}

			// Choose image to answer
			// Odabir random slike
			// number = Math.floor(Math.random() * 2);
			// chosenImage = data[number];

			for (var i = 0; i < data.length; i++) {
				if (data[i].category == "POSITIVE") {
					chosenImage = data[i];
				}
			}

			$("#imageCont").show();

		}
	});
}

function setUpAnswerScreen() {
	// Set up image to answer
	$('#anwserCont img').attr("src", chosenImage.url);
	$('#answer').focus();
	$("input[name='answer']").focus();

	$("#imageCont").hide();
	$('#anwserCont').show();
}

function submitAnswer() {
	var input = document.getElementById("answer");
	var text = input.value;

	var isCorrect = false;

	if (text.toUpperCase() == chosenImage.character.toUpperCase()) {
		isCorrect = true;
	}

	var anwser = {
		'resultsId' : null,
		'imageId' : chosenImage.id,
		'isCorrect' : isCorrect
	};

	$.ajax({
		type : 'POST',
		dataType : 'application/json; charset=utf-8',
		url : '/oop/anwser?imageId=' + chosenImage.id + '&isCorrect='
				+ isCorrect,
		data : text,
		complete : function() {
			iterate();
			$('#anwserCont').hide();
			input.value = '';
		}
	})
}

function iterate() {
	if (iterations > 1) {
		// postavimo slike
		getImages();

		// cekamo 1 sek i pokazujemo ekran sa unosom odgovora
		setTimeout(setUpAnswerScreen, 1000);
		setUpAnswerScreen();

		iterations--;
	} else {
		window.location.href = "/oop/secure/end";
	}
}