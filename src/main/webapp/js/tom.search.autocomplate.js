
$.fn.selectRange = function(start, end) {
	return this.each(function() {
		if(this.setSelectionRange) {
			this.focus();
			this.setSelectionRange(start, end);
		}

		else if(this.createTextRange) {
			var range = this.createTextRange();
			range.collapse(true);
			range.moveEnd('character', end);
			range.moveStart('character', start);
			range.select();
		}
	});
};

function showSearchPopup(showPopup) {
		log("showSearchPopup --- " + showPopup);
		if(showPopup) {
			$("#div_search_popup").css("display",  "block");
			$("#div_search_result").css("display", "none");
			//$("#keyword").focus().setCursorPosition(1);
			
			$("#keyword").val($("#dummy_keyword").val());
			let textLen = $("#keyword").val().length;
			$("#keyword").selectRange(textLen, textLen);
		} else {
			$("#div_search_popup").css("display",  "none");
			$("#div_search_result").css("display", "block");
		}
	}
	
	let nowCategId = "";
	
	function pressKeyword() {
        if (window.event.keyCode == 13) {
        	
 			//location.href= prefix + "/m/search/search.do?categId="+nowCategId+"&keyword=" + $("#keyword").val();
        	let url = prefix + "/m/search/search.do?keyword=" + $("#keyword").val();
        	if(prevKeyword != null) {
        		url += "&prevKeyword=" + prevKeyword;
        	}
 			location.href = url;
 			
	    } else {
	    	let mKeyword = $("#keyword").val();
	    	if(mKeyword.length != 0) {
	    		loadActcomplateKeyword(mKeyword);	
	    		showAutoComplatePanel(true);
	    	} else {
	    		showAutoComplatePanel(false);
	    	}
	    }
	}
	
	function loadActcomplateKeyword(mKeyword) {
		let url = prefix + '/api/search/suggest_autocomplate_keyword.do?keyword=' + encodeURIComponent(mKeyword);
		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	log(data);
	        	if(data['state'] == 0) {
	        		printAutoComplateKeyword(mKeyword, data['list']);
	    		} else{
	    			//alert(getErrorMsg(data['state']));
	    		}
	        },
	        error:function(request, status, error){
	        	alertNetworkErr();
	        }
	    });
	}
	
	
	
	
	function printAutoComplateKeyword(mKeyword, list) {
		$("#ul_word_suggest").empty();
		let alreadyPrintedMap = {};
		
				
		for(let i=0; i<list.length; i++) {
			let sData = list[i];
			let text = sData['disp_text'];
				
			if(sData['text_type'] == 2) {
				//카테고리.
				let appendLi = ''+
				'<li id="ac_cate_'+i+'">' +
	            '	<a class="ser_key"><em class="ct">Category</em><span class="ser_indent">'+text+'</span></a>' +
	            '</li>';
				$("#ul_word_suggest").append(appendLi);
				
				$("#ac_cate_"+i).bind("click", function(){
					let parma = {'categId' : sData['extra_info']};
					location.href = prefix + '/m/search/search.do?categId=' + sData['extra_info'];
				});
				
			} else if(sData['text_type'] == 4) {
				//제품
				let appendLi = ''+
				'<li id="ac_prod_'+i+'">' +
	            '	<a class="ser_key"><em class="ct">제품</em><span class="ser_indent">'+text+'</span></a>' +
	            '</li>';
				$("#ul_word_suggest").append(appendLi);
				
				$("#ac_prod_"+i).bind("click", function(){
					location.href = prefix + '/m/prod/product.do?prod_id=' + sData['extra_info'];
				});
				
				
			} else {
				if(alreadyPrintedMap[text] != null) {
					continue;
				}
				
				let rsDate = sData['recent_search_date'];
				let rsDateStr = '';
				if(rsDate != null) {
					rsDateStr = moment(rsDate).format('YYYY.MM.DD');
				}
				
				//일반...
				let matchedWord = text.replace(mKeyword, '<span class="match">'+mKeyword+'</span>');
				let appendLi = ''+
				'<li id="ac_'+i+'">' +
	            '	<a href="'+prefix+'/m/search/search.do?keyword='+text+'" class="ser_key"><i></i>'+matchedWord+'</a> ' +
	            '   <a class="ser_date">'+rsDateStr+'</a> '+
	            '</li>';
	            
				$("#ul_word_suggest").append(appendLi);    				
				
			}
		}
	}
	
	function showAutoComplatePanel(show) {
		if(show) {
			$("#ul_word_suggest").css("display", "block");
			
			$("#div_recent_search_words").css("display", "none");
			$("#div_pop_search_words").css("display", "none");
		} else {
			$("#ul_word_suggest").css("display", "none");
			
			$("#div_recent_search_words").css("display", "block");
			$("#div_pop_search_words").css("display", "block");
		}
	}
	
	function resetSearchKeyword(){
		$("#keyword").val('');
		$("#dummy_keyword").val('');
		showAutoComplatePanel(false);
	}
	
	
	
	function deleteRecentSearchWord() {
	
		let url = prefix + '/api/search/remove_recent_hist.do?mode=all';
		$.ajax({
	        url: url,
	        type:'post',
	        success:function(data){
	        	//log(data);
	        	if(data['state'] == 0) {
	        		//printAutoComplateKeyword(mKeyword, data['list']);
	        		
	        		$("#ul_recent_keywords").css("display", "none");
	        		$("#div_no_recent_keywords").css("display", "block");
	        	
	        		
	    		} else{
	    			//alert(getErrorMsg(data['state']));
	    		}
	        },
	        error:function(request, status, error){
	        	alertNetworkErr();
	        }
	    });
		
	}