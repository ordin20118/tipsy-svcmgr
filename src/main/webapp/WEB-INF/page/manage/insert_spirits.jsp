<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="../common/head.jsp" %>
<script>

	var isCheckIn = false;
	var isEssentialData = false;
	var isDupliCheck = false;
	var jobType = 0;			// 0: new, 1:update, 2:request
	var dataType = 0;
	var selCountry = 0;
	var selCategory;
	
	var countryList = null;
	var categoryList = null;

	var numberRegex = /^[0-9|'.']*$/;
	var koreanRegex = /[^ㄱ-ㅎ가-힣\s0-9]/g;
	var englishRegex = /[^a-zA-Z\s0-9]/g;
		

	var nameKrReward = 0;
	var nameEnReward = 0;
	var imageReward = 0;
	var categoryReward = 0;
	var typeReward = 0;
	var countryReward = 0;
	var regionReward = 0;
	var abvReward = 0;
	var descReward = 0;
	var historyReward = 0;
	var priceReward = 0;
	var totalReward = 0;


	function setTotalReward() {
		let reward = nameKrReward + nameEnReward + imageReward
					+ countryReward + regionReward + abvReward
					+ descReward + historyReward + priceReward
					+ categoryReward + typeReward;

		let textHtml = "<p style='color: blue'>예상 보상 금액 : "+reward+"원</p>";
		$("#total_reward").html(textHtml);
		totalReward = reward;
		return reward;
	}

	

	$(document).ready(function() {

		console.log("[Insert Spirits Page is loaded]");
		setEvents();
		previewFile();
		loadCountry();
		loadCateg();
		setTotalReward();

	});


	function setEvents() {

		$("#sendMessageButton").click(function() {
			sendData();			
		});

		$("#reg_account_btn").click(function() {
			location.replace(prefix + "/page/join_part_worker.do");	
		});

		$("#certification_btn").click(function() {
			checkIn();
		});

		$("#chck_duplicate_btn").click(function() {
			checkDuplication();
		});
		
		


		// - 필수 입력 확인
		$("#name_kr").change(function() {

			console.log("한국어 이름 입력됨");

			// 한국어만
			if( koreanRegex.test($("#name_kr").val()) ) {
				alert("한글만 입력해주세요");
				nameKrReward = 0;
				$("#name_kr").val('')
				return;
			}

			if($("#name_kr").val() != null && $("#name_kr").val().length > 0) {
				nameKrReward = 100;
			} else {
				nameKrReward = 0;
			}			
			setTotalReward();

		});
		$("#name_en").change(function() {

			console.log("영어 이름 입력됨");

			// 영어만
			if( englishRegex.test($("#name_en").val()) ) {
				alert("영어만 입력해주세요");
				nameEnReward = 0;
				$("#name_en").val('')
				return;
			}
			
			if($("#name_en").val() != null && $("#name_en").val().length > 0) {
				nameEnReward = 100;
				$("#chck_duplicate_btn").attr("disabled",false); 
				isDupliCheck = false;
			} else {
				nameEnReward = 0;
			}			
			setTotalReward();

		});
		$("#region").change(function() {

			console.log("지역 이름 입력됨");

			// 영어만
			if( englishRegex.test($("#region").val()) ) {
				alert("영어만 입력해주세요");
				regionReward = 0;
				$("#region").val('')
				return;
			}
			
			if($("#region").val() != null && $("#region").val().length > 0) {
				regionReward = 100;					
			} else {
				regionReward = 0;
			}			
			setTotalReward();

		});
		$("#abv").change(function() {

			console.log("도수 입력됨");

			// 숫자만
			if( !numberRegex.test($("#abv").val()) ) {
				alert("숫자만 입력해주세요");
				abvReward = 0;
				$("#abv").val('')
				return;
			}
			
			if($("#abv").val() != null && $("#abv").val().length > 0) {
				abvReward = 100;
			} else {
				abvReward = 0;
			}			
			setTotalReward();

		});

		$("#type").change(function() {

			console.log("형태 입력됨"+$("#type").val());

			if($("#type").val() != "") {
				typeReward = 100;
			} else {
				typeReward = 0;
			}
			
			setTotalReward();

		});

		$("#description").change(function() {

			console.log("한국어 이름 입력됨");

			if($("#description").val() != null && $("#description").val().length > 20) {
				descReward = 100;
			} else {
				alert('20자 이상 입력해주세요');
				descReward = 0;
			}			
			setTotalReward();

		});
		
		$("#history").change(function() {

			console.log("한국어 이름 입력됨");

			if($("#history").val() != null && $("#history").val().length > 20) {
				historyReward = 100;
			} else {
				alert('20자 이상 입력해주세요');
				historyReward = 0;
			}			
			setTotalReward();

		});
		
		$("#price").change(function() {

			console.log("가격 입력됨");

			// 숫자만
			if( !numberRegex.test($("#price").val()) ) {
				alert("숫자만 입력해주세요");
				priceReward = 0;
				$("#price").val('')
				return;
			}

			if($("#price").val() != null && $("#price").val().length > 0) {
				priceReward = 100;
			} else {
				alert('20자 이상 입력해주세요');
				priceReward = 0;
			}			
			setTotalReward();

		});

	}

	function checkDuplication() {

		var keyword = $("#name_en").val();

		if(keyword == null || keyword.length == 0) {
			alert('영문 이름을 입력 후 중복 확인을 해주세요.');
			return;
		}
		
		var url = prefix +"/api/search_beer.do?keyword=" + keyword;

		$.ajax({
			url:url,
			type:"get",
			contentType:false,
			success:function(data){
				console.log("[search beer result]");
				console.log(data);
				if(data['state'] == STATE_SUCCESS){

					if(data['list'].length > 0) {
						alert('이미 등록된 정보입니다.');
					} else {
						alert('등록 가능한 정보입니다.');
						$("#chck_duplicate_btn").attr("disabled",true);
						isDupliCheck = true; 						
					}
					
				} else {
					alert("중복 조회 실패. 새로고침 해주세요.");
				}
			},
			error:function(request, status, error){
				alertNetworkErr();
			}
		});
	}

	function loadCateg() {

		var url = prefix +"/api/category_child.do?categId=0101";

		$.ajax({
			url:url,
			type:"get",
			contentType:false,
			success:function(data){
				console.log("[category data]");
				console.log(data);
				if(data['state'] == STATE_SUCCESS){
					printCategory(data);					
				} else {
					alert("카테고리 정보 조회 실패. 새로고침 해주세요.");
				}
			},
			error:function(request, status, error){
				alertNetworkErr();
			}
		});

	}

	function printCategory(data) {

		let list = data['list'];
		categoryList = list;

		$('#category').empty();
		var defaultOption = '<a class="dropdown-item" href="javascript:selectCategory(\'00\')">선택</a>';
		$('#category').append(defaultOption);
		 
		for(var i=0; i<list.length; i++) {   
			var category = list[i];             
	        var option = '<a class="dropdown-item" href="javascript:selectCategory(\''+category['categ_id']+'\')">'+category['categ_name']+'</a>';
	        
	        $('#category').append(option);
		}
	}


	function selectCategory(id) {

		if(id == "00") {
			$("#dropdownCategoryButton").html('선택');
			selCategory = id;
			categoryReward = 0;
			setTotalReward();
			return;
		}

		for(var i=0; i<categoryList.length; i++) {
			if(categoryList[i]['categ_id'] == id) {
				// 텍스트 설정
				$("#dropdownCategoryButton").html(categoryList[i]['categ_name']);
				// 선택된 아이디 설정
				selCategory = id;
				categoryReward = 100;		
				setTotalReward();		
			}
		}
		
	}

	function loadCountry() {

		var url = prefix +"/api/country_list.do";

		$.ajax({
			url:url,
			type:"get",
			contentType:false,
			success:function(data){
				console.log("[country data]");
				console.log(data);
				if(data['state'] == STATE_SUCCESS){
					printCountry(data);					
				} else {
					alert("국가 정보 조회 실패. 새로고침 해주세요.");
				}
			},
			error:function(request, status, error){
				alertNetworkErr();
			}
		});
		
	}

	function printCountry(data) {

		let list = data['list'];
		countryList = list;

		$('#country').empty();
		var defaultOption = '<a class="dropdown-item" href="javascript:selectCountry(0)">국가 선택</a>';
		$('#country').append(defaultOption);
		 
		for(var i=0; i<list.length; i++) {   
			var country = list[i];             
	        var option = '<a class="dropdown-item" href="javascript:selectCountry('+country['country_id']+')"><img src="'+country['image']+'" style="margin: 0px 9px 0px 0px;">'+country['name']+'</a>';
	        
	        $('#country').append(option);
		}
	}

	function selectCountry(id) {

		if(id == 0) {
			$("#dropdownCountryButton").html('국가 선택');
			// 선택된 아이디 설정
			selCountry = id;
			countryReward = 0;
			setTotalReward();
			return;
		}

		for(var i=0; i<countryList.length; i++) {
			if(countryList[i]['country_id'] == id) {
				// 텍스트 설정
				$("#dropdownCountryButton").html('<img src="'+countryList[i]['image']+'" style="margin: 0px 9px 0px 0px;">' + countryList[i]['name']);
				// 선택된 아이디 설정
				selCountry = id;
				countryReward = 100;
				setTotalReward();
			}
		}
		
	}	


	function checkIn() {

	 	console.log("[checkIn()]");

		let userName = $("#user_name").val();
		let userBank = $("#user_bank").val();
		let userBankAccount = $("#user_bank_account").val();

		if(userName == null || userName.length == 0) {
			alert("이름을 입력해주세요.");
			return;
		}

		if(userBank == null || userBank.length == 0) {
			alert("은행을 입력해주세요.");
			return;
		}

		if(userBankAccount == null || userBankAccount.length == 0) {
			alert("은행 계좌를 입력해주세요.");
			return;
		}
		
		let formData = {
			"name": userName,
			"bank": userBank,
			"bank_account": userBankAccount
		}

		let dataStr = JSON.stringify(formData);

		var url = prefix +"/api/checkin_part_worker.do";

		$.ajax({
			url:url,
			type:"post",
			contentType:false,
			processData:false,
			data:dataStr,
			success:function(data){
				if(data['state'] == STATE_SUCCESS){
					// 기록 남기지 않고 이전 화면으로 이동
					alert("정상 로그인되었습니다.");
					isCheckIn = true;

					// 입력창 disabled
					$("#user_name").attr("readonly",true).attr("disabled",false); 
					$("#user_bank").attr("readonly",true).attr("disabled",false); 
					$("#user_bank_account").attr("readonly",true).attr("disabled",false); 

				} else {
					alert("등록된 정보가 없습니다.");
				}
			},
			error:function(request, status, error){
				alertNetworkErr();
			}
		})

	}

	function sendData() {

		console.log("[sendData()]");
		
		if(!isCheckIn) {
			alert('제출전 계좌를 인증해주세요.');
			return;
		}
		
		// - 필수 입력 확인
		let nameKr = $("#name_kr").val();
		let nameEn = $("#name_en").val();
		let region = $("#region").val();
		let abv = $("#abv").val();
		let categ = $("#categ").val();
		let imageData = $("#image")[0].files[0];
		let type = $('#type').val();

		// ** 이미지 데이터 확인, 카테고리는 select에서 선택하도록


		if(nameKr != null && nameKr.length > 0
		  && nameEn != null && nameEn.length > 0
		  && selCountry != 0
		  && region != null && region.length > 0
		  && abv != null && abv.length > 0
		  && selCategory != null && selCategory.length > 0
		  && imageData != null && type != null && type != "") {

			isEssentialData = true;

		} else {
			alert('필수 데이터를 모두 입력해주세요.');
			return;
		}

		let description = $("#description").val();
		let history = $("#history").val();
		let price = $("#price").val();

		console.log("[Total Reward]:"+totalReward);

		if(!isEssentialData){
			alert('필수 데이터를 모두 입력해주세요.');
			return;
		}


		let formData = {
			"name":  $("#user_name").val(),
			"bank":  $("#user_bank").val(),
			"bank_account":  $("#user_bank_account").val(),
			"name_kr": nameKr,
			"name_en": nameEn,
			"abv": abv,
			"country_id": selCountry,
			"region": region,
			"category_id": selCategory,
			"type": type,
			"price": price,
			"description": description,
			"history": history,
			"reward": totalReward,
			"type": jobType
		}

		console.log("[Send Beer Data]");
		console.log(formData);

		let dataStr = JSON.stringify(formData);

		var url = prefix +"/api/insert_beer.do";

		$.ajax({
			url:url,
			type:"post",
			contentType:false,
			processData:false,
			data:dataStr,
			success:function(data){
				console.log("insert beer result")
				console.log(data)
				if(data['state'] == STATE_SUCCESS){
					// 이미지 업로드
					uploadImage(data['data']['beer_id']);
				} else if(data['state'] == STATE_DUPLICATION){
					alert("이미 등록된 정보입니다.");
				} else {
					alertErrorMessage(data['error_message']);
				}
			},

			error:function(request, status, error){
				alertNetworkErr();
			}
		})
		

	}



	function previewFile() {
		var preview = document.querySelector('img');
		var file	= document.querySelector('input[type=file]').files[0];
		var reader	= new FileReader();

		reader.addEventListener("load", function() {
			preview.src = reader.result;
		}, false);

		if(file) {
			reader.readAsDataURL(file);
			imageReward = 100;
			setTotalReward();
		} else {
			$("#thumb").attr("src", prefix+"/image/default_image.jpg");
		}			
	}

	function uploadImage(contentId){

		// image data 
		// 여러개 받을 방안
		var imageData = $("#image")[0].files[0];

		
		var formData = new FormData;
		formData.append("image",imageData)
		formData.append("contentId", contentId)
		formData.append("imageType", 0)
		formData.append("contentType", 0)

		var url = prefix +"/api/upload_image.do";

		$.ajax({
			url:url,
			type:"post",
			contentType:false,
			processData:false,
			data:formData,
			beforeSend : function(xhr){
	        },
			success:function(data){
				if(data['state'] == STATE_SUCCESS){
					// 기록 남기지 않고 이전 화면으로 이동
					alert("정상 등록되었습니다.");
					// 페이지 이동 말고 데이터 모드 empty 
					//location.replace(prefix + "/page/insert_beer.do");
					clearInputData();
				} else{
					alertErrorMessage(data['error_message']);
				}
			},

			error:function(request, status, error){
				alertNetworkErr();
			}
		})
	}


	function clearInputData() {

		$("#name_kr").val('');
		$("#name_en").val('');

		loadCountry();
		loadCateg();
		
		$("#type option:eq(0)").prop("selected", true);
		$("#category").html('선택');
		
		$("#region").val('');
		$("#abv").val('');
		$("#description").val('');
		$("#history").val('');
		$("#price").val('');
		$("#image").val('');
		$("#chck_duplicate_btn").attr("disabled",false); 
		previewFile();
		selCountry = 0;
		selCategory = 0;

		$('html').scrollTop(0);		
		
	}


</script>
<body>

  <!-- Navigation -->
  <%@include file="../common/nav.jsp" %>

  <!-- Page Content -->
  <div class="container">

    <!-- Page Heading/Breadcrumbs -->
    <h1 class="mt-4 mb-3">맥주
      <small>데이터 입력</small>
    </h1>

    <!-- Content Row -->
    <div class="row">
      <!-- Map Column -->
      <!-- 
      <div class="col-lg-8 mb-4">
        <!-- Embedded Google Map 
        <iframe style="width: 100%; height: 400px; border: 0;" src="http://maps.google.com/maps?hl=en&amp;ie=UTF8&amp;ll=37.0625,-95.677068&amp;spn=56.506174,79.013672&amp;t=m&amp;z=4&amp;output=embed"></iframe>
      </div>
      -->
      <!-- Contact Details Column -->
      <div class="col-lg-4 mb-4">
        <h3>진행 방법</h3>
        <p>
          1. 보상금 수신 계좌 등록을 하지 않았다면 계좌 등록부터 <button id="reg_account_btn" class="btn btn-primary btn-sm">계좌 등록</button><br>
          2. 등록한 이름과 계좌번호를 입력하여 인증<br>
          3. 맥주 정보를 기입<br>
          4. 기입을 완료 했다면 <b>'제출'</b> 버튼을 클릭<br>
          
        </p>
        <h3>주의 사항</h3>
        <p>
          # 이름과 계좌 정보를 정확히 입력해주세요.<br>
          # 빨간색 항목은 필수로 입력해야 할 정보입니다.<br>
          # 부적절한 정보 입력시 보상을 받을 수 없습니다.<br>
          -  ex1) 존재하지 않는 맥주 이름<br>
          -  ex2) 맥주와 국가 정보가 매치되지 않는 경우<br>
          # 부실한 데이터에 대한 보상금액은 차감됩니다.<br>
          - 내용이 부실한 경우 (설명, 유래)<br>
          
        </p>
        <p>
          <abbr title="Email">*문의 메일</abbr>:
          <a href="mailto:ordin20118@gmail.com">ordin20118@gmail.com
          </a>
        </p>
      </div>
    </div>
    <!-- /.row -->
    
    <!-- 개인 인증 -->
    <div class="row">
      <div class="col-lg-8 mb-4">
        <h3>로그인</h3>
        <form name="sentMessage" id="" novalidate>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">이름:</label>
              <input type="text" class="form-control" id="user_name" placeholder="홍길동" required data-validation-required-message="자신의 이름을 입력해주세요.">
              <label style="color: #CC3D3D">은행:</label>
              <input type="text" class="form-control" id="user_bank" placeholder="우리은행" required data-validation-required-message="은행을 입력해주세요.">
              <label style="color: #CC3D3D">계좌:</label>
              <input type="text" class="form-control" id="user_bank_account" placeholder="00-0000-00000-0000" required data-validation-required-message="계좌 정보를 입력해주세요.">
              <p class="help-block"></p>
            </div>
          </div>
        </form>
        <button id="certification_btn" class="btn btn-primary">로그인</button><br>
      </div>
    </div>

    <!-- Contact Form -->
    <!-- In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
    <div class="row">
      <div class="col-lg-8 mb-4">
        <h3>데이터 입력</h3>
        <form name="sentMessage" id="insertBeerForm" novalidate>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">이미지</br><span style="font-size: 12px";>( 흰 배경에 술의 이미지만 있는 이미지, 특정 회사의 로고가 없는 이미지 ):</span></label></br>
              <input type="file" id="image" accept="image/*" onchange="previewFile()" required data-validation-required-message="이미지를 입력해주세요.">
              <p class="help-block"></p>
              <img id="thumb" class="photo" height="50%" width="50%" alt="이미지 미리보기...">
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">맥주 이름(한국어):</label>
              <input type="text" class="form-control" id="name_kr" required data-validation-required-message="맥주의 한글 이름을 입력해주세요.">
              <p class="help-block"></p>
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">맥주 이름(영어):</label>
              <input type="text" class="form-control" id="name_en" required data-validation-required-message="맥주의 영어 이름을 입력해주세요.">
              <p class="help-block"></p>
              <button id="chck_duplicate_btn" type="button" class="btn btn-info">중복 확인</button>
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">국가:</label>
			  <div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownCountryButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	국가 선택
				  </button>
				  <div id="country" class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="overflow:scroll;height:200px;">
				    <a class="dropdown-item" href="#">Action</a>
				  </div>
			 </div>
             
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">지역(영어):</label>
              <input type="text" class="form-control" id="region" placeholder="ex) Napa Valley" required data-validation-required-message="지역을 입력해주세요.">
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">도수:</label>
              <input type="text" class="form-control" id="abv" placeholder="ex) 4.5" required data-validation-required-message="4.5"/>
          	</div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">카테고리:</label>
               <div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownCategoryButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	선택
				  </button>
				  <div id="category" class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="overflow:scroll;height:200px;">
				    <a class="dropdown-item" href="#">Action</a>
				  </div>
			 </div>
              <!-- <input type="text" class="form-control" id="categ" required data-validation-required-message="select로 변경"/> -->
          	</div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">형태:</label>
              <select id="type">
               	<option value="">선택</option>
			    <option value="0">병</option>
			    <option value="1">캔</option>
			    <option value="2">페트병</option>
              </select>
              <p class="help-block"></p>
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label>(선택) 설명 :</label>
              <textarea rows="10" cols="100" class="form-control" id="description" placeholder="ex) 부드럽고 향이 강한 맥주..." required data-validation-required-message="부드럽고 향이 강한 맥주" maxlength="999" style="resize:none"></textarea>
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label>(선택) 유래 :</label>
              <textarea rows="10" cols="100" class="form-control" id="history" placeholder="ex) 미국에서 처음 생산된 ..." required data-validation-required-message="미국에서 처음 생산된 ..." maxlength="999" style="resize:none"></textarea>
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label>가격($):</label>
              <input type="text" class="form-control" id="price" required data-validation-required-message="25.5"/>
          	</div>
          </div>
          <div id="success"></div>
          <div id="total_reward"></div>
          <!-- For success/fail messages -->
          <button type="button" class="btn btn-primary" id="sendMessageButton">제출</button>
        </form>
      </div>
    </div>
    <!-- /.row -->

  </div>
  <!-- /.container -->

  <!-- Footer -->
  <%@include file="../common/footer.jsp" %>

</body>

</html>
