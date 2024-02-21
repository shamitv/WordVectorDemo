/**
 * 
 */
$(document).ready(function() {
	$( "#wordlookupBtn" ).click(function() {
		  var word = $("#wordlookupInput").val();
		  console.log("Fething neighbours for "+word);
		  var url="./services/rest/nearWords?word="+word;
		  $.ajax({
			  dataType: "json",
			  url: url,
			  success: handleWordLookupResponse
			  });
		});

	function addResponseToPlot(data){
		var labelValues=$('#inlabels').val();
		var vectorValues=$('#incsv').val();
		data.forEach(
				function(element) {
				    if(labelValues===""){
				    	labelValues=labelValues+element.word.word;	
				    }else{
				    	labelValues=labelValues+"\r\n"+element.word.word;
				    }
				    if(vectorValues===""){
				    	vectorValues=vectorValues+element.word.vector;	
				    }else{
				    	vectorValues=vectorValues+"\r\n"+element.word.vector;
				    }
				}
		);
		 $('#inlabels').val(labelValues);
		 $('#incsv').val(vectorValues);
	}
	
	function handleWordLookupResponse(data){
		//console.log(data);
		var innerHtml="<table><tr><th>Distance</th><th>Word</th></tr>"
		data.forEach(
				function(element) {
				    //console.log(element.distance);
				    //console.log(element.word.word);
				    innerHtml=innerHtml+"\r\n"+"<tr><td>"+element.distance+"</td><td>"+element.word.word+"</td></tr>";
				}
		);
		innerHtml=innerHtml+"\r\n"+"</table>";
		$("#lookupRespTable").html(innerHtml);
		addResponseToPlot(data);
	}
	
	  $('#puzzleSolvBtn').click(checkAndSolvePuzzle);

	  function checkAndSolvePuzzle(){
		  console.log("Solving puzzle");
		  var w1,w2,w3;
		  w1=$("#puzzleInput1").val();
		  w2=$("#puzzleInput2").val();
		  w3=$("#puzzleInput3").val();
		  if(w1.trim()!=="" && w2.trim()!=="" && w3.trim()!==""){
			  var url="./services/rest/solvePuzzle?word1="+w1.trim()+"&word2="+w2.trim()+"&word3="+w3.trim();
			  $.ajax({
				  dataType: "json",
				  url: url,
				  success: handlePuzzleResponse
				  });
		  }
	  }

	  function handlePuzzleResponse(data){
			var val=""
			var respCount=0;	
				data.forEach(
						function(element) {
							respCount++;
							if(respCount<=3){
								if(val===""){
							    	val=val+element.word.word;	
							    }else{
							    	val=val+"\r\n"+element.word.word;
							    }
							}
						}
				);
				$("#puzzleAnswer").val(val);
				addResponseToPlot(data);
	  }
	
});