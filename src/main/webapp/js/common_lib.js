function getErrorMsg(errCode) {

	let SUCCESS = 0;
	let ERROR   = 1;
	
	let ALREADY_EXISTS_USER = 40001; 
	let NOT_FOUND_USER      = 40002;
	let MISSMATCH_PASSWORD  = 40003;
	let MEMBER_ONLY         = 40004;
	let LOGOUT_FAIL         = 40005;
	let NOT_PERMITTED_USER  = 40006;
	let NOT_FOUND_RECOMMENDER = 40007;
	let ALREADY_EXISTS_EMAIL = 40008; 
	let NOT_CERTIFIED_USER = 40009; 
	let NOT_FOUND_USERS_CHANNEL = 40010;
	
	let NOT_FOUND_PRODUCT    = 50001;
	let NOT_FOUND_REVIEW     = 50002;
	let NOT_FOUND_COMMENT    = 50003;
	let NOT_MY_CONTENT       = 50004;
	let INVALID_SHARE_TOKEN  = 50005;
	let INVALID_PARAM        = 50006;
	let NOT_FOUND_DATA    	 = 50007;
	let NOT_FOUND_EXT_REVIEW = 50008;
	let WRONG_ID_OR_TYPE	 = 50009;
	let NOT_EXIST_CONTENT_TYPE = 50010;
	
	let ALREADY_EXISTS_PROD_RATE    = 50030;
	let ALREADY_EXISTS_REVIEW_RATE  = 50031;
	let ALREADY_EXISTS_PROD_PICK    = 50032;
	let ALREADY_EXISTS_REVIEW_PICK  = 50033;
	
	let IMAGE_UPDATE_TIMEOUT = 60000;
	let NOT_FOUND_TEMPKEY	 = 60001;
	
	let NOT_ENOUGH_RENA = 80001;
	let NOT_ENOUGH_REVU = 80002;
	
	let message = "내부서버 오류입니다. ("+errCode+")";
	
	if(errCode == ALREADY_EXISTS_USER){
		message = "이미 가입되어 있는 회원ID입니다.";
		
	} else if(errCode == NOT_FOUND_USER){
		message = "가입되지 않은 사용자입니다.";
		
	} else if(errCode == MISSMATCH_PASSWORD){
		message = "올바르지 않은 비밀번호 입니다.";
		
	} else if(errCode == MEMBER_ONLY){
		message = "로그인 후 이용 가능한 서비스입니다.";
		
	} else if(errCode == LOGOUT_FAIL){
		message = "로그아웃 실패";
		
	} else if(errCode == NOT_PERMITTED_USER){
		message = "허가되지 않은 접근입니다.";
		
	} else if(errCode == NOT_FOUND_RECOMMENDER){
		message = "등록되지 않은 추천인입니다.";
		
	} else if(errCode == ALREADY_EXISTS_EMAIL){
		message = "사용할 수 없는 이메일입니다.";
		
	} else if(errCode == NOT_CERTIFIED_USER){
		message = "인증되지 않은 사용자입니다.";
		
	} else if(errCode == NOT_FOUND_USERS_CHANNEL){
		message = "해당 채널을 찾을 수 없습니다.";
		
	} else if(errCode == NOT_FOUND_PRODUCT){
		message = "제품을 찾을 수 없습니다.";
		
	} else if(errCode == NOT_FOUND_REVIEW){
		message = "리뷰를 찾을 수 없습니다.";
		
	} else if(errCode == NOT_FOUND_COMMENT){
		message = "댓글을 찾을 수 없습니다.";
		
	} else if(errCode == NOT_MY_CONTENT){
		message = "본인이 작성한 컨텐츠만 접근가능 합니다.";
		
	} else if(errCode == INVALID_SHARE_TOKEN){
		message = "유효하지 않은 접근입니다.";
		
	} else if(errCode == INVALID_PARAM){
		message = "유효하지 않은 접근입니다.";
		
	} else if(errCode == NOT_FOUND_DATA){
		message = "요청 내용을 찾을 수 없습니다.";
		
	} else if(errCode == WRONG_ID_OR_TYPE){
		message = "유효하지 않은 접근입니다.";
		
	} else if(errCode == NOT_EXIST_CONTENT_TYPE){
		message = "유효하지 않은 접근입니다.";
		
	} else if(errCode == ALREADY_EXISTS_PROD_RATE){
		message = "이미 평가한 제품입니다.";
		
	} else if(errCode == ALREADY_EXISTS_REVIEW_RATE){
		message = "이미 평가한 리뷰입니다.";
		
	} else if(errCode == ALREADY_EXISTS_PROD_PICK){
		message = "이미 담은 제품입니다.";
		
	} else if(errCode == ALREADY_EXISTS_REVIEW_PICK){
		message = "이미 담은 리뷰입니다.";
		
	} else if(errCode == IMAGE_UPDATE_TIMEOUT){
		message = "응답 시간이 만료되었습니다.";
		
	} else if(errCode == NOT_FOUND_TEMPKEY){
		message = "응답 시간이 만료되었습니다";
		
	} else if(errCode == NOT_ENOUGH_RENA){
		message = "보유 리나가 충분하지 않습니다.";
		
	} else if(errCode == NOT_ENOUGH_REVU){
		message = "보유 리뷰가 충분하지 않습니다.";
		
	} 
	
	
	return message;
}

function alertNetworkErr() {
	alert("네트워크 오류입니다. 인터넷을 확인해주세요.");
}

function alertErrorMessage(errorMessage, silence){
	if(silence == true){
		return;
	}
	console.log(errorMessage);
	alert(errorMessage);
}

function numFormat01(num) {
	var regexp = /\B(?=(\d{3})+(?!\d))/g;
	return num.toString().replace(regexp, ',');
}

function getPageStatusJson() {
	let url  = decodeURIComponent(location.href);
	if(url.indexOf('#') == -1) {
		return null;
	} else {
	   	let initJsonStr = url.substring(url.indexOf('#')+1, url.length );
	   	return JSON.parse(initJsonStr);
	}
}

function getParams(inKeyName) {
	let targetValueArr = [];
    var param = new Array();
    var url = decodeURIComponent(location.href);
    url = decodeURIComponent(url);
     
    var params;
    params = url.substring( url.indexOf('?')+1, url.length );
    params = params.split("&");
        
    var size = params.length;
    var key, value;
    for(var i=0 ; i < size ; i++) {
        key = params[i].split("=")[0];
        value = params[i].split("=")[1];
        param[key] = value;
        
        if(i == size-1){
        	value = value.split('#')[0];
        }
            
        if(inKeyName == key) {
        	targetValueArr.push(value);
        }
    }
     
    if(inKeyName == null || inKeyName.trim().length==0) {
    	return param;
    } else {
    	return targetValueArr;
    }    
}



let currlogLevel = 0;
function log(message, level) {
	if(level == null) {
		level = 0;
	}
	
	let levelStr = '';
	
	if(level == 0) {
		levelStr = 'DEBUG';
	} else if(level == 1) {
		levelStr = ' INFO';
	} else if(level == 2) {
		levelStr = 'WARN';
	} else if(level >= 3) {
		levelStr = 'ERROR';
	}
	
	if(level >= currlogLevel) {
		if(level == 0) {
			console.log(moment().format('YYYY-MM-DD HH:mm:ss.SSS') + " [%s] : %o", levelStr, message);
		} else if (level == 1) {
			console.info(moment().format('YYYY-MM-DD HH:mm:ss.SSS') + " [%s] : %o", levelStr, message);
		} else if (level == 2) {
			console.warn(moment().format('YYYY-MM-DD HH:mm:ss.SSS') + " [%s] : %o", levelStr, message);
		} else if (level >= 3) {
			console.error(moment().format('YYYY-MM-DD HH:mm:ss.SSS') + " [%s] : %o", levelStr, message);
		}
		
	}
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function goPrePage(){
	var prePageUrl = document.referrer;
	
	if(!prePageUrl==""){
		if(window.location.search==""){
			location.href = prePageUrl;
		} else {
			params = getUrlParams();
			let prePage = prePageUrl+"#"+params['urlParam'];
			location.href= prePage;
			log(prePage);
	        }
	}else{
		location.href = prefix + "/m/home.do";
	}
}

function getUrlParams() {
    var params = {};
    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
    return params;
}


	function queryStringToMap(queryString) {
		let paramMap = {};
		
		let params = queryString.split("&");
		for(let i=0; i<params.length; i++) {
			let kValue = params[i].split("=");
			paramMap[kValue[0]] = kValue[1];
		}

		return paramMap;
	}
	
	function mapToQueryString(paramMap) {
		let qs = '';
		for(let key in paramMap) {
			qs = qs + key + "=" + paramMap[key] + "&";
		}
		if(qs.length > 0) {
			qs = qs.substring(0, qs.length-1);
		}
		return qs;
	}
	
	function getMallMark(name){
		let markString = 'wh etc">ETC';
		if(name == "유투브"){
			markString = 'wh youtube">Youtube';
		} else if (name == "네이버") {
			markString = 'wh naver">NAVER';
		} else if (name == "다음") {
			markString = 'wh daum">DAUM';
		} else {
			markString = 'wh etc">ETC';
		}
		
		return markString;
	}
	
	function simpleCammelObjToSnakeObj(obj){
		for(let i in obj){
			key = i.replace(/\.?([A-Z])/g, function (x,y){return "_" + 			y.toLowerCase()}).replace(/^_/, "");
			obj[key] = obj[i];
			delete obj[i];
		}
	}
	
	function openLayer(layerId){		
		$("#"+layerId).css("display","");
	}
	
	
	
	function bytesToHex(bytes) {
	  return Array.from(
	    bytes,
	    byte => byte.toString(16).padStart(2, "0")
	  ).join("");
	}


	function stringToUTF8Bytes(string) {
	  return new TextEncoder().encode(string);
	}


	function stringToUTF16Bytes(string, littleEndian) {
	  const bytes = new Uint8Array(string.length * 2);
	  // Using DataView is the only way to get a specific
	  // endianness.
	  const view = new DataView(bytes.buffer);
	  for (let i = 0; i != string.length; i++) {
	    view.setUint16(i, string.charCodeAt(i), littleEndian);
	  }
	  return bytes;
	}


	function stringToUTF32Bytes(string, littleEndian) {
	  const codepoints = Array.from(string, c => c.codePointAt(0));
	  const bytes = new Uint8Array(codepoints.length * 4);
	  // Using DataView is the only way to get a specific
	  // endianness.
	  const view = new DataView(bytes.buffer);
	  for (let i = 0; i != codepoints.length; i++) {
	    view.setUint32(i, codepoints[i], littleEndian);
	  }
	  return bytes;
	}