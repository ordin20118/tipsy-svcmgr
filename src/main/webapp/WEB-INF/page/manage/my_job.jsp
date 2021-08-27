<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="../common/head.jsp" %>
<script>

	var perPage = 7;
	var moreJobResult = false;
  	var nowPageTxList = 0;
  	var jobData = null;
  	
	$(document).ready(function() {

		setEvent();
		//loadMyJobList(0);

		$("#job_content").scroll(function() {
			//console.log("job_list scrolled");
  		    if((this.scrollTop+this.clientHeight) >= this.scrollHeight) {
  		    	//console.log("스크롤 특정 상황에 들어옴");
  	  		    if(moreJobResult) {
  	  		    	//console.log("작업 리스트 더 보기");
  		    		loadMyJobList(nowPageTxList+1);	
  		    	}
  		    }
  		});	
	});



	function setEvent() {

		$("#sel_job_btn").click(function() {

			moreJobResult = true;
			jobData = null;

			let userName = $("#user_name").val();
			let userBank = $("#user_bank").val();
			let userBankAccount = $("#user_bank_account").val();

			if(userName != null && userName.length > 0
			   && userBank != null && userBank.length > 0
			   && userBankAccount != null && userBankAccount.length > 0) {

				   loadMyJobList(0);
			} else {
				alert("조회할 정보를 정확히 입력해주세요.");
			}

		});

	}





	function loadMyJobList(page, isRefresh) {

		let userName = $("#user_name").val();
		let userBank = $("#user_bank").val();
		let userBankAccount = $("#user_bank_account").val();
	 		
  		let urlParam = 'paging.nowPage=' + page +
  		'&paging.perPage=' + perPage +
  		'&orderby.field=reg_date' +
  		'&orderby.sorting=desc' + 
  		'&name=' + userName +
  		'&bank=' + userBank +
  		'&bankAccount=' + userBankAccount; 
		//'&startDateStr=' + startDate +'&endDateStr=' + endDate;
					
  		let url = prefix + "/api/my_job_list.do?"+urlParam;
  		
  		//console.log("[Get My Job List URL]:"+url);

		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	//console.log("[My Job LIST]");
	        	//console.log(data);
	        	
	        	var len;
	        	if(jobData != null) {
	        		len = jobData.length;
	        		jobData = jobData.concat(data['list']);
	        	} else {
	        		len = 0;
	        		jobData = data['list'];
	        	}
	        	
	        	let size = data['list'].length;
	        	if(size == 0 || size < perPage) {
	        		moreJobResult = false;
	        	}
	        	
	        	if(size > 0 && isRefresh == null) {
	        		nowPageTxList++;
	        	}
				
				// 보상 금액 정보 출력
				let rewardInfo = data['data'];
				console.log(rewardInfo);
				$("#reward_board").show();
				$("#reward_total").text(": " + rewardInfo['total_reward'] +"원 ");
		  	 	$("#reward_paid").text(": " + rewardInfo['paid_reward'] +"원 ");
		  		$("#reward_reserved").text(": " + rewardInfo['reserve_reward'] +"원 ");
		  		$("#reward_rejected").text(": " + rewardInfo['rejected_reward'] +"원 ");

		  		$("#job_list").empty();
	        	for(let i=0; i<data['list'].length; i++) {
	        		printJobTable(data['list'][i], len);
		        }
	        	
	        },
	        error:function(request, status, error){
	        }
	    });
  	}


   	function printJobTable(data, len) {

  		let job = data['job'];
  		let content = data['content'];
  		let images = data['images'];

  		
  		console.log(images);

  		let imagePath = "";
  		if(images != null && images.length > 0) {
  			imagePath = images[0];
  	  	}

    	let jobType = "";
    	if(job['type'] == 0) {
    		jobType = "데이터 입력";
       	} else if(job['type'] == 1) {
       		jobType = "데이터 수정";
	    } else if(job['type'] == 2) {
	    	jobType = "데이터 건의";
	    }
		

    	let jobState = "";
    	let jobStateItem = "";
    	if(job['state'] == 0) {
    		jobState = "승인 대기중";
    		jobStateItem = ' 			<div class="pull-right"><div class="text-warning" style="font-weight: bold;">'+jobState+'</div></div>';
       	} else if(job['state'] == 1) {
       		jobState = "승인됨";
       		jobStateItem = ' 			<div class="pull-right"><div class="text-success" style="font-weight: bold;">'+jobState+'</div></div>';
	    } else if(job['state'] == 2) {
	    	jobState = "승인 거부";
	    	jobStateItem = ' 			<div class="pull-right"><div class="text-danger" style="font-weight: bold;">'+jobState+'</div></div>';
		}
	    
  		let approvDate = null;
    	if(job['approv_date'] != null) {
    		approvDate = moment(job['approv_date']).format("YYYY-MM-DD HH:mm:ss");
	    }


	    let dataType = "";
	    if(job['data_type'] == 0) {
	    	dataType = "맥주";
       	} else if(job['data_type'] == 1) {
       		dataType = "사케";
	    } else if(job['data_type'] == 2) {
	    	dataType = "전통주";
	    }

	    //0: 병, 1: 캔, 2: 페트병
		let prodType = "";
		if(content['type'] == 0) {
			prodType = "병";
       	} else if(content['type'] == 1) {
       		prodType = "캔";
	    } else if(content['type'] == 2) {
	    	prodType = "페트병";
	    }

    	
		/*		지급 예정 보상액 
		<div class="card mb-4">
			<div class="card-body">
				<div class="row">
			     	<div class="col-lg-6">
				        <a href="#">
				          <img class="img-fluid rounded mb-3 mb-md-0" src="http://placehold.it/700x300" alt="">
				        </a>		        
			      	</div>
				    <div class="col-lg-6">
					     <div style="width:100%;" class="clearfix">
						        <div class="pull-left"><h5>한글명</h5></div> 
						        <div class="pull-right">(English Name) - 4.5%</div> 
						</div>	
						<div class="pull-right clearfix" >보상 금액 : 900₩</div>	
						<br>
						<hr>	
					     <div id="prod_name">제품명</div>
					     <div id="abv">도수</div>
					     <div id="category">Category</div>
					     <div id="country">Country/Region</div>
					     <div id="prod_type">Type</div>
					     <div id="reg_date">등록일 : 2020-01-18 00:00:00</div>
					     <br>
					     <a class="btn btn-primary" href="#">수정하기
					       <span class="glyphicon glyphicon-chevron-right"></span>
					     </a>
				    </div>
			    </div>
			</div>
			<div class="card-footer">
				<span class="pull-right">등록일 : 2020-01-18 00:00:00</span>
			</div>
		</div>
	    
	    */

	    let jobInfo = '<div class="card mb-4">' +
	    			  '		<div class="card-body">' +
		    		  '			<div id="job_"'+job['part_job_id']+' class="row">' +
	    			  '					<div class="col-lg-6">' +
	    			  '						<a href="#">' +
	    			  '							<img class="img-fluid rounded mb-3 mb-md-0" src="'+prefix+'/api/image'+imagePath['path']+'.do" alt="">' +
	    			  '						</a>' +
	    			  '					</div>' +
	    			  '					<div class="col-lg-6">' +
	    			  '						<div style="width:100%;" class="clearfix">' +
	    			  '							<div class="pull-left"><h4>'+dataType+' '+jobType+'</h4></div>' +
	    			  jobStateItem + 
	    			  '						</div>' +
	    			  '						<div class="pull-right clearfix" >예상 보상 금액 : '+job['reward']+'₩</div>' +
	    			  '						<br>' +
	    			  '						<hr>' +
	    			  '						<div>제품명 : '+content['name_kr']+'('+content['name_en']+')</div>' +
	    			  '						<div>알코올 : '+content['abv']+'%</div>' +
	    			  //'						<div>'+content['category']+'</div>' +
	    			  '						<div>생산 지역 : '+content['country']+'/'+content['region']+'</div>' +
	    			  '						<div>제품 타입 : '+prodType+'</div>';
	    			  //'						<p>'+content['description']+'</p>' +
	    			  //'						<p>'+content['history']+'</p>' +

	    			  if(approvDate != null) {
	    				  jobInfo += '<div>승인 날짜 : '+approvDate+'</div>';
		    		  }
	    			  jobInfo += '<br>';

	    			  if(content['update_state'] == 0) {
	    				  jobInfo += '			<a class="btn btn-primary" href="#">수정하기' + 
		    			  '				<span class="glyphicon glyphicon-chevron-right"></span>' + 
		    			  '			</a>';
		    		  }
	    			  
	    			  jobInfo += '		</div>' + 
	    			  '</div>' +
	    			  '</div>' +
	    			  '<div class="card-footer">'+
	    			  '		<span>등록일: '+moment(job['reg_date']).format("YYYY-MM-DD HH:mm:ss")+'</span>' +
	    			  '</div>'+
	    			  '</div>';
    
		$('#job_list').append(jobInfo);


  	} 



</script>
<body>

  <!-- Navigation -->
  <%@include file="../common/nav.jsp" %>

  <!-- Page Content -->
  <div id="job_content" class="container none_scroll" style="width:100%; height:800px; overflow:auto">

    <!-- Page Heading/Breadcrumbs -->
    <h1 class="mt-4 mb-3">내가 등록한 
      <small>데이터</small>
    </h1>

	<!-- 
    <ol class="breadcrumb">
      <li class="breadcrumb-item">
        <a href="index.html">Home</a>
      </li>
      <li class="breadcrumb-item active">Portfolio 1</li>
    </ol>
     -->
	<hr>  
   <div class="row">
    	<div class="col-lg-8 mb-4">
	      <h4>조회할 사용자 정보</h4>
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
	      <button id="sel_job_btn" class="btn btn-primary">조회</button><br>
    	</div>
    	
    	<hr>
    </div>
    
    
    <div id="reward_board" style="display: none;">
   		<div class="clearfix"><p class="pull-left font-weight-bold text-dark">총 보상 </p><span id="reward_total" class="pull-left">: 10000원</span></div>
   		<div class="clearfix"><p class="pull-left font-weight-bold text-sucess">지급된 보상액</p><span id="reward_paid" class="pull-left">: 10000원</span></div>
   		<div class="clearfix"><p class="pull-left font-weight-bold text-warning">승인 대기 보상액 </p><span id="reward_reserved" class="pull-left">: 10000원</span></div>
   		<div class="clearfix"><p class="pull-left font-weight-bold text-danger">거부된 보상액 </p><span id="reward_rejected" class="pull-left">: 10000원</span></div>
 	</div>
  	<hr>

	<div id="job_list">
		<!-- 
		<div class="card mb-4">
			<div class="card-body">
				<div class="row">
			     	<div class="col-lg-6">
				        <a href="#">
				          <img class="img-fluid rounded mb-3 mb-md-0" src="http://placehold.it/700x300" alt="">
				          <img class="img-fluid rounded mb-3 mb-md-0" src="http://localhost:8080/allalcohol/api/image/product/0_28_300.jpg.do" alt="">
				        </a>		        
			      	</div>
				    <div class="col-lg-6">
					     <div style="width:100%;" class="clearfix">
						        <div class="pull-left"><h5>한글명</h5></div> 
						        <div class="pull-right">(English Name) - 4.5%</div> 
						 </div>	
						 <div class="pull-right clearfix" >보상 금액 : 900₩</div>	
						 <br>
						 <hr>
					     <div class="clearfix"><div id="prod_name" class="pull-left">제품명</div></div>
					     <div id="abv">도수</div>
					     <div id="category">Category</div>
					     <div id="country">Country/Region</div>
					     <div id="prod_type">Type</div>
					     <br>
					     <a class="btn btn-primary" href="#">수정하기
					       <span class="glyphicon glyphicon-chevron-right"></span>
					     </a>
				    </div>
			    </div>
			</div>
			<div class="card-footer">
				<span class="pull-right">등록일 : 2020-01-18 00:00:00</span>
			</div>
		</div>	
		-->	
	</div>
  </div>
  <!-- /.container -->

  <!-- Footer -->
  <%@include file="../common/footer.jsp" %>


</body>

</html>
