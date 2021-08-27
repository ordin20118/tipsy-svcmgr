<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="../common/head.jsp" %>
<script>

	$(document).ready(function() {

		console.log("[Join Part Worker Page is loaded]");
		setEvents();

	});


	function setEvents() {

		$("#join_btn").click(function() {
			sendData();			
		});

	}

	function sendData() {

		console.log("[sendData()]");

		
		let userName = $("#user_name").val();
		let userBank = $("#user_bank").val();
		let userBankAccount = $("#user_bank_account").val();

		console.log("[user name]:"+userName);
		console.log("[user bank]:"+userBank);
		console.log("[user bank account]:"+userBankAccount);


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

		var url = prefix +"/api/member_mgmt/insert_part_worker.do";

		$.ajax({
			url:url,
			type:"post",
			contentType:false,
			processData:false,
			data:dataStr,
			success:function(data){
				if(data['state'] == STATE_SUCCESS){
					// 기록 남기지 않고 이전 화면으로 이동
					alert("정상 등록되었습니다.");
					location.replace(prefix + "/page/insert_beer.do");
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




</script>
<body>

  <!-- Navigation -->
  <%@include file="../common/nav.jsp" %>

  <!-- Page Content -->
  <div class="container">

    <!-- Page Heading/Breadcrumbs -->
    <h1 class="mt-4 mb-3">계좌 등록
      <small></small>
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
        <h3>주의 사항</h3>
        <p>
          # 이름과 계좌 정보를 정확히 입력해주세요.<br>
          # 빨간색 항목은 필수 입력 항목입니다.<br>
          # 계좌 정보를 잘못 입력시 보상을 받으실 수 없습니다.<br>
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
        <h3>개인 인증</h3>
        <form name="sentMessage" id="" novalidate>
          <div class="control-group form-group">
            <div class="controls">
              <label style="color: #CC3D3D">이름:</label>
              <input type="text" class="form-control" id="user_name" placeholder="홍길동" required data-validation-required-message="자신의 이름을 입력해주세요.">
              <label style="color: #CC3D3D">은행:</label>
              <input type="text" class="form-control" id="user_bank" placeholder="알콜은행" required data-validation-required-message="은행을 입력해주세요.">
              <label style="color: #CC3D3D">계좌:</label>
              <input type="text" class="form-control" id="user_bank_account" placeholder="00-0000-00000-0000" required data-validation-required-message="계좌 정보를 입력해주세요.">
              <p class="help-block"></p>
            </div>
          </div>
        </form>
        <button id="join_btn">등록하기</button><br>
      </div>
    </div>

  </div>
  <!-- /.container -->

  <!-- Footer -->
  <%@include file="../common/footer.jsp" %>

</body>

</html>
