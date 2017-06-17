/**
 * 
 */
$(document).ready(function() {
	  var wordSuggestions = new Bloodhound({
		    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		    queryTokenizer: Bloodhound.tokenizers.whitespace,
		    prefetch: './services/rest/defaultWords',
		    remote: {
		      url: './services/rest/lookup?sead=%QUERY',
		      wildcard: '%QUERY'
		    }
		  });
	  
	  $('#wordlookup .typeahead').typeahead(null, {
		    /*name: 'best-pictures',
		    display: 'value',*/
		    source: wordSuggestions,
		    limit: 200
		    
		  }).on('typeahead:autocompleted', function(object, datum) {
			    console.log('typeahead:autocompleted');
			    $(this).trigger('typeahead:_done', [object, datum]);
			}).on('change', function() {
			    $(this).trigger('typeahead:_changed');
			}).on('typeahead:_changed', function() {
			    console.log('typeahead:_changed');
			    // This event is always triggered before 'typeahead:_done', so you have a
			    // chance to set a variable/property to indicate the value is dirty.
			}).on('typeahead:_done', function(evt, object, datum) {
			    console.log('typeahead:_done');
			    // This event is triggered ONLY when the user selects or autocompletes
			    // with an entry from the suggestions.
			});
	  $('#wordPuzzle1 .typeahead').typeahead(null, {
		    /*name: 'best-pictures',
		    display: 'value',*/
		    source: wordSuggestions,
		    limit: 200
		    
		  }).on('typeahead:autocompleted', function(object, datum) {
			    console.log('typeahead:autocompleted');
			    $(this).trigger('typeahead:_done', [object, datum]);
			}).on('change', function() {
			    $(this).trigger('typeahead:_changed');
			}).on('typeahead:_changed', function() {
			    console.log('typeahead:_changed');
			    // This event is always triggered before 'typeahead:_done', so you have a
			    // chance to set a variable/property to indicate the value is dirty.
			}).on('typeahead:_done', function(evt, object, datum) {
			    console.log('typeahead:_done');
			    // This event is triggered ONLY when the user selects or autocompletes
			    // with an entry from the suggestions.
			});
	  $('#wordPuzzle2 .typeahead').typeahead(null, {
		    /*name: 'best-pictures',
		    display: 'value',*/
		    source: wordSuggestions,
		    limit: 200
		    
		  }).on('typeahead:autocompleted', function(object, datum) {
			    console.log('typeahead:autocompleted');
			    $(this).trigger('typeahead:_done', [object, datum]);
			}).on('change', function() {
			    $(this).trigger('typeahead:_changed');
			}).on('typeahead:_changed', function() {
			    console.log('typeahead:_changed');
			    // This event is always triggered before 'typeahead:_done', so you have a
			    // chance to set a variable/property to indicate the value is dirty.
			}).on('typeahead:_done', function(evt, object, datum) {
			    console.log('typeahead:_done');
			    // This event is triggered ONLY when the user selects or autocompletes
			    // with an entry from the suggestions.
			});

	  $('#wordPuzzle3 .typeahead').typeahead(null, {
		    /*name: 'best-pictures',
		    display: 'value',*/
		    source: wordSuggestions,
		    limit: 200
		    
		  }).on('typeahead:autocompleted', function(object, datum) {
			    console.log('typeahead:autocompleted');
			    $(this).trigger('typeahead:_done', [object, datum]);
			}).on('change', function() {
			    $(this).trigger('typeahead:_changed');
			}).on('typeahead:_changed', function() {
			    console.log('typeahead:_changed');
			    // This event is always triggered before 'typeahead:_done', so you have a
			    // chance to set a variable/property to indicate the value is dirty.
			}).on('typeahead:_done', function(evt, object, datum) {
			    console.log('typeahead:_done');
			    // This event is triggered ONLY when the user selects or autocompletes
			    // with an entry from the suggestions.
			});
	  

	  
});