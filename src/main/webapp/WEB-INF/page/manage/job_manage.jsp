<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="../common/head.jsp" %>
<script>

	var perPage = 7;
	var pageCount = 5;
	var moreJobResult = false;
  	var nowPageTxList = 0;
  	var jobData = null;
  	
	$(document).ready(function() {

		setEvent();
		//loadJobList(1);

		/*
		$("#job_table").scroll(function() {
			console.log("job_list scrolled");
  		    if((this.scrollTop+this.clientHeight) >= this.scrollHeight) {
  		    	console.log("스크롤 특정 상황에 들어옴");
  	  		    if(moreJobResult) {
  	  		    	//console.log("작업 리스트 더 보기");
  		    		loadJobList(nowPageTxList+1);	
  		    	}
  		    }
  		});
  		*/
  			
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

				   loadJobList(1);
				   
			} else {
				alert("조회할 정보를 정확히 입력해주세요.");
			}

		});

	}

	function loadJobList(page, isRefresh) {
	 		
  		let urlParam = 'paging.nowPage=' + page +
  		'&paging.perPage=' + perPage +
  		'&orderby.field=reg_date' +
  		'&orderby.sorting=desc'; 
		//'&startDateStr=' + startDate +'&endDateStr=' + endDate;
					
  		let url = prefix + "/api/jobL.do?"+urlParam;  		

		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	//console.log("[Job LIST]");
	        	console.log(data);
	        	
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
				
		  		$("#job_list").empty();
	        	for(let i=0; i<data['list'].length; i++) {
	        		printJobTable(data['list'][i], len);
		        }
		        paging(data['sparam']);
	        	
	        },
	        error:function(request, status, error){
	        }
	    });
  	}


   	function printJobTable(data, len) {

   		//console.log("[printJobTable]");
   		//console.log(data);
   		
  		let job = data['job'];
  		let content = data['content'];
  		let worker = data['worker'];
  		let images = data['images'];

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
	    
    	let regDate = null;
    	if(job['reg_date'] != null) {
    		regDate = moment(job['reg_date']).format("YYYY-MM-DD HH:mm:ss");
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


	    /*
	    <tr>
	      <th scope="col">작업 유형</th>
	      <th scope="col">작업자</th>
	      <th scope="col">내용</th>
	      <th scope="col">보상액</th>
	      <th scope="col">작업 상태</th>
	      <th scope="col">등록 날짜</th>
	    </tr>
	    */

	    let jobInfo = '<tr onclick="javascript:printApproveModal(2)" data-toggle="modal" data-target="#approveModal">' +
	    			  '	<td scope"col">'+jobType+'</td>' +
	    			  '	<td scope"col">'+worker['name']+'</td>' +
	    			  '	<td scope"col">'+content['name_kr']+'/'+content['name_en']+'</td>' +
	    			  '	<td scope"col">'+job['reward']+'원</td>' +
	    			  '	<td scope"col">'+jobState+'</td>' +
	    			  '	<td scope"col">'+regDate+'</td>' +
	    			  '</tr>';

	    /*
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
	    */
    
		$('#job_list').append(jobInfo);


  	} 


  	function paging(data){  		

		let pageData = data['paging'];
  		
  		let totalCount = pageData['total_count'];
  		 
		
  		let totalPage = Math.ceil(totalCount/perPage);
  		let pageGroup = Math.ceil(nowPageTxList/pageCount);

  		console.log("totalPage:"+totalPage+"/pageGroup:"+pageGroup);

  		let lastPage = pageGroup * pageCount;

  		if(lastPage > totalPage) {
			lastPage = totalPage;
  	  	}

		let firstPage = lastPage - (pageCount - 1);
		let nextPage = lastPage - 1;
		let prevPage = firstPage - 1;

		console.log("firstPage:"+firstPage);
		console.log("nextGroup:"+nextPage);
		console.log("prevGroup:"+prevPage);

		if(totalPage < 1) {
			firstPage = lastPage;
		}

		let paging = $("#paging");
		paging.empty();

		if(firstPage > pageCount) {
			paging.append('<li class="page-item"><a class="page-link" href="javascript:loadJobList('+prevPage+')">Previous</a></li>');
		}

		 

		for(let i=0; i<=lastPage; i++) {
			if(nowPageTxList === i) {
				paging.append('<li class="page-item active"><a class="page-link" href="javascript:loadJobList('+i+')">'+i+' <span class="sr-only">(current)</span></a></li>');
			} else if(i > 0) {
				paging.append('<li class="page-item"><a class="page-link" href="javascript:loadJobList('+i+')">'+i+'</a></li>');
			}			
		}

		if(nextPage > 5 && nextPage < totalPage) {
			paging.append('<li class="page-item"><a class="page-link" href="javascript:loadJobList('+nextPage+')">Next</a></li>');
		}
  	  	
  	}


  	function printApproveModal(jobIdx) {

  		console.log("[printApproveModal]");

  		if(jobData['job']['data_type'] == 0){
  			beerModal(jobData['content']);
  	  	}
  		/*
  	 	$('#approveModal').on('show.bs.modal', function (event) {
  		  var button = $(event.relatedTarget) // Button that triggered the modal
  		  //var recipient = button.data('whatever') // Extract info from data-* attributes

  		  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
  		  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
  		  var modal = $(this)
  		  modal.find('.modal-title').text('New message to ' + jobIdx)
  		  modal.find('.modal-body input').val(jobIdx)
  		});

  		*/

  	}


  	function beerModal(data) {

  		

  	  	/*
  	  	
  	  		<div>
				<label for="content_type" class="col-form-label" style="font-weight: bold;">컨텐츠 유형 : </label><text id="content_type">1234</text>
			</div>
			<div>
				<label for=content_id class="col-form-label" style="font-weight: bold;">컨텐츠ID : </label><text id="content_id">894636</text>
			</div>
			<div>
				<label for="content_en_name" class="col-form-label" style="font-weight: bold;">영문 이름 : </label><text id="content_en_name">Edel Vise</text>
			</div>
			<div>
				<label for="content_kr_name" class="col-form-label" style="font-weight: bold;">한글 이름 : </label><text id="content_kr_name">에델 바이스</text>
			</div>
			<div>
				<label for="content_abv" class="col-form-label" style="font-weight: bold;">abv : </label><text id="content_abv">5.0%</text>
			</div>
			<div>
				<label for="content_country" class="col-form-label" style="font-weight: bold;">국가/지역 : </label><text id="content_country">네덜란드/머시기</text>
			</div>
			<div>
				<label for="content_category" class="col-form-label" style="font-weight: bold;">카테고리 : </label><text id="content_category">에일 맥주</text>
			</div>	
			<div>
				<label for="content_price" class="col-form-label" style="font-weight: bold;">가격 : </label><text id="content_price">4,000원</text>
			</div>	
			<div>
				<label for="content_desc" class="col-form-label" style="font-weight: bold;">설명 </label><br>
				<text id="content_desc">부드럽고 향긋한 맥주이다.</text>
			</div>	
			<div>
				<label for="content_hist" class="col-form-label" style="font-weight: bold;">역사 </label><br>
				<text id="content_hist">네덜란드의 머시기 지역에서 처음 생산된 에델바이스는 머시기머시기 어쩌고 저쩌고 하다.</text>
			</div>	
			<div>
				<label for="content_reg_date" class="col-form-label" style="font-weight: bold;">등록 날짜 : </label><text id="content_reg_date">2020-09-27 13:00:00</text>
			</div>	
			<div>
				<label for="update_date" class="col-form-label" style="font-weight: bold;">수정 날짜 : </label><text id="update_date">2020-09-27 13:00:00</text>
			</div>	
  	  	
  	  	*/


  		let appendItem = '' +
  						 '' +
  						 '' +
  						 '' +
  						 '' +
  						 '' +
  						 '' +  						
  	  					 '';

  	}



</script>
<body>

  <div id="approveModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="approveModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	    
	      <div class="modal-header">
	        <h5 class="modal-title" id="approveModalLabel">Job Info</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      
	      <div class="modal-body">
			
			<div id="content_info_div">
					
			</div>	  
	      
			<!-- 작업에 대한 정보 및 액션 -->      
	        <form>	         
	          <div class="form-group">
	          	<label for="job_id" class="col-form-label">작업 번호:</label><text id="job_id">1234</text>
	          </div>
	          <div class="form-group">
	          	<label for="job_type" class="col-form-label">작업 유형:</label><text id="job_type">새로운 데이터</text>
	          </div>
	          <div class="form-group">	          	
	          	<div class="controls">
	              <label for="job_state" class="col-form-label">작업 상태:</label>
	              <select id="job_state">
	               	<option value="">작업 상태</option>
				    <option value="0">승인 대기중</option>
				    <option value="1">승인 완료</option>
				    <option value="2">승인 거부</option>
				    <option value="3">부분 승인</option>
	              </select>
	              <p class="help-block"></p>
	            </div>
	          </div>
	          <div class="form-group">
	          	<label for="job_reg_date" class="col-form-label">등록 날짜:</label><text id="job_reg_date">2020-01-01</text>
	          </div>
	          <div class="form-group">
	          </div>
	          <div class="form-group">
	            <label for="reward_desc" class="col-form-label">반려 사유:</label>
	            <textarea class="form-control" id="reward_desc"></textarea>
	          </div>
	        </form>
	      </div>
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
	        <button type="button" class="btn btn-primary">적용</button>
	      </div>	      
	    </div>
	  </div>  
  </div>

  <!-- Navigation -->
  <%@include file="../common/nav.jsp" %>

  <!-- Page Content -->
  <div id="job_content" class="container">

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
	      <h4>로그인</h4>
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


	<table id="job_table" class="table table none_scroll" style="width:100%; height:300px; overflow:auto">
	  <thead>
	    <tr>
	      <th scope="col">작업 유형</th>
	      <th scope="col">작업자</th>
	      <th scope="col">내용</th>
	      <th scope="col">보상액</th>
	      <th scope="col">작업 상태</th>
	      <th scope="col">등록 날짜</th>
	    </tr>
	  </thead>
	  <tbody id="job_list">
	  </tbody>
	</table>
	
	<div>
		<nav aria-label="Page navigation example">
  			<ul id="paging" class="pagination justify-content-center">
  			</ul>  			
  		</nav>	
	</div>
	
  </div>
  <!-- /.container -->

  <!-- Footer -->
  <%@include file="../common/footer.jsp" %>


</body>

</html>
