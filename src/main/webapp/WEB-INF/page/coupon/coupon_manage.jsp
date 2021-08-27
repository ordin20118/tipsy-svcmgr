<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">

<%@include file="../common/head.jsp" %>
<link href="${path}/css/json-viewer.css"></link>
<%-- <script type="text/javascript" src="${path}/js/json-viewer.js"></script> --%>
   
<script>

	const perPage = 15;
	var moreJobResult = true;
  	var nowPageTxList = 0;
  	var couponData = null;
  
  	$(document).ready(function() {   		
  		setEvent();  	
		loadCouponList(1);
  		
  	  	$("#div_list").scroll(function() {
  		    if((this.scrollTop+this.clientHeight) >= this.scrollHeight) {
  		    	if(moreJobResult) {
  		    		loadCouponList(nowPageTxList+1);	
  		    	}
  		    }
  		});	   	
  	});
  	
  	function setEvent() {
  		
  	}


	function createCoupon() {

		let desc = null;
		if($("#input-coupoon-desc").val() != null && $("#input-coupoon-desc").val() != "") {
			desc = $("#input-coupoon-desc").val();
		}
  		let param = {
  				description : desc
 		}
 		
  		let url = prefix + "/api/new_coupon.do";
  		
		$.ajax({
	        url: url,
	        type:'post',
	        data: param,
	        success:function(data){
	        	// check result 
	        	if(data['state'] == 0) {
		        	alert("정상적으로 쿠폰을 생성했습니다.");
		        	printCouponInfo(data);
		       	} else {
					alert("쿠폰 생성 오류...");
				}
	        },
	        error:function(request, status, error){
	        }
	    });
  	}

	function checkCoupon() {
  		
  		let urlParam = "?code=" + $("#input-coupoon-code").val();		
  		let url = prefix + "/api/coupon.do" + urlParam;
  		
		$.ajax({
	        url: url,
	        type:'GET',
	        success:function(data){

	        	// check result 
	        	if(data['state'] == 0 && data['data'] != null) {		        	
		        	// print 
		        	printCouponInfo(data);
		       	} else {
			       	alert("조회된 쿠폰이 없습니다.");
		       		$('#coupon-info').hide();
				}
	        },
	        error:function(request, status, error){
	        }
	    });
  	}

  	function printCouponInfo(data) {

    	let coupon = data['data'];
    	let state = "";
    	if(coupon['state'] == 0) {
        	state = "사용 가능";
       	} else if(coupon['state'] == 1) {
        	state = "사용됨";
	    } else if(coupon['state'] == 2) {
        	state = "만료됨";
		}
	    
  		let useDate = null;
    	if(coupon['use_date'] != null) {
			useDate = moment(coupon['use_date']).format("YYYY-MM-DD HH:mm:ss");
	    }

    	let couponInfo = "쿠폰 코드: " + coupon['code'] + "<br>" +
						 "내용: " + coupon['description'] + "<br>" +
						 "상태: " + state + "<br>" +
						 "등록 날짜: " + moment(coupon['reg_date']).format("YYYY-MM-DD HH:mm:ss") + "<br>";
		if(coupon['use_date'] != null) {
			couponInfo += "사용 날짜: " + moment(coupon['use_date']).format("YYYY-MM-DD HH:mm:ss") + "<br>"
		}
		couponInfo += "만료 날짜: " + moment(coupon['exp_date']).format("YYYY-MM-DD HH:mm:ss") + "<br>";
		
		$('#coupon-info').html(couponInfo);
		$('#coupon-info').show();

  	}


	function useCoupon() {
  		
  		let param = {
			code : $("#input-coupoon-code").val()
  		} 
		
  		let url = prefix + "/api/use_coupon.do";

		$.ajax({
	        url: url,
	        type:'POST',
	        data: param,
	        success:function(data){
	        	// check result 
	        	if(data['state'] == 0) {
		        	alert('정상적으로 사용처리 되었습니다.');
		       	} else {
		       		let coupon = data['data'];
		        	if(coupon['state'] == 1) {
			        	alert('이미 사용된 쿠폰입니다.');
				    } else if(coupon['state'] == 2) {
			        	alert('만료된 쿠폰입니다.');
					}
			    }
	        },
	        error:function(request, status, error){
	        }
	    });
  	}
    
  
  	function loadCouponList(page, isRefresh) {
  		 		
  		let urlParam = 'paging.nowPage=' + page +
  		'&paging.perPage=' + perPage +
  		'&orderby.field=reg_date' +
  		'&orderby.sorting=desc'; 
		//'&startDateStr=' + startDate +'&endDateStr=' + endDate;
					
  		let url = prefix + "/api/coupon_list.do?"+urlParam;
  		
  		console.log("[Coupon List URL]:"+url);

		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	console.log("[TX QUEUE LIST]");
	        	console.log(data);
	        	
	        	var len;
	        	if(couponData != null) {
	        		len = couponData.length;
	        		couponData = couponData.concat(data['list']);
	        	} else {
	        		len = 0;
	        		couponData = data['list'];
	        	}
	        	
	        	let size = data['list'].length;
	        	if(size == 0 || size < perPage) {
	        		moreJobResult = false;
	        	}
	        	
	        	if(size > 0 && isRefresh == null) {
	        		nowPageTxList++;
	        	}
	        	
	        	printCouponTable(data, len);
	        },
	        error:function(request, status, error){
	        }
	    });
  	}
  	
	function printCouponTable(data, len) {
  		let list = data['list'];
  		let size = list.length;
  		
  		for(var i=0; i<size; i++) {
  	  		let appendItem = null;
  	  		let idx = len + i;
  	  		
  	  		if(i%2 == 0) {
  	  			appendItem = makeCouponHtmlEven(list[i], idx);
  	  		} else if(i%2 == 1) {
  	  			appendItem = makeCouponHtmlEven(list[i], idx);
  	  		}  	  		
  			
  	  		$('#list-table').append(appendItem);
  	  		  	  		
  	  	
  	  		$('#q_check_'+idx).bind('click', function(){
  	  			console.log("[테이블 아이템 클릭]]");
  	  			console.log(list[i]);
  	  			if($('#q_check_'+idx).is(":checked")) {
  	  				//$('#q_txdata_'+idx).show();
  	  				let qId = queueData[idx]['tx_queue_id'];
  	  				checkedQList.push(qId);
  	  				console.log(checkedQList);
  	  			} else {
  	  				//$('#q_txdata_'+idx).hide();
  	  				
  	  				let qItem = queueData[idx]['tx_queue_id'];
  	  				const qIndex = checkedQList.indexOf(qItem);
  	  				
  	  				if (qIndex > -1) {
  	  					checkedQList.splice(qIndex, 1)
  	  				} 
  	  			}
  	  		});
  		}
  	}
	
	function reloadCouponList() {
		$("#queue-list-table").empty();
		checkedQList = new Array();
		queueData = null;
		nowPageTxList = 0;
		loadCouponList(1);
	}
	
	function refresh() {
		$("#queue-list-table").empty();
		checkedQList = new Array();
		queueData = null;
		loadCouponList(nowPageTxList, true);
	}
	
	function showQueueContent(idx) {
		
		if($('#q_txdata_'+idx).is(":visible")) {
			$('#q_txdata_'+idx).hide();
		} else {
			$('#q_txdata_'+idx).show();			
		}
	}
	
	function makeCouponHtmlEven(info, idx) {

		console.log("info");
		console.log(info);
				
		let state = "";
    	if(info['state'] == 0) {
        	state = "사용 가능";
       	} else if(info['state'] == 1) {
        	state = "사용됨";
	    } else if(info['state'] == 2) {
        	state = "만료됨";
		}
		
		let regDate = "";
		if(info['reg_date'] == null) {
			regDate = "-";
		} else {
			regDate = moment(info['reg_date']).format("YYYY-MM-DD HH:mm:ss");
		}	
		
		let useDate = "";
		if(info['use_date'] == null) {
			useDate = "-";
		} else {
			useDate = moment(info['use_date']).format("YYYY-MM-DD HH:mm:ss");
		}
		
		let expDate = "";
		if(info['exp_date'] == null) {
			expDate = "-";
		} else {
			expDate = moment(info['exp_date']).format("YYYY-MM-DD HH:mm:ss");
		}

		
  		let appendItem = '<tr class="even pointer" onclick="showQueueContent('+idx+')">' + 
			 '	<td class="a-center " onclick="event.cancelBubble = true;">' + 
			 '		<input type="checkbox" id="q_check_'+idx+'" class="icheckbox_flat-green" name="table_records">' +
			 '	</td>' +
			 '	<td class=" ">'+info['code']+'</td>' +
			 '	<td class=" ">'+info['description']+'</td>' +
			 '	<td class=" ">'+state+'</td>' +
			 '	<td class=" ">'+regDate+'</td>' +
			 '	<td class=" ">'+useDate+'</td>' +
			 '	<td class=" ">'+expDate+'</td>' +
			 '</tr>';
			 
		return appendItem;
		
	}
	
	function makeTxQueueHtmlOdd(info, idx) {
		
		let method = "";
		var txData = JSON.parse(info['tx_data']);
		method = txData['method'];
		
		let jobResult = "";
		if(info['job_result'] == "S") {
			jobResult = "SUCCESS";
		} else if(info['job_result'] == "E"){
			jobResult = "ERROR";
		} else {
			jobResult = "-";
		}
		
		let jobLog = "";
		if(info['job_log'] == null || info['job_log'] == "") {
			jobLog = "-";
		} else {
			jobLog = info['job_log'];
		}
		
		let jobBegin = "";
		if(info['job_begin'] == null) {
			jobBegin = "-";
		} else {
			jobBegin = moment(info['job_begin']).format("YYYY-MM-DD HH:mm:ss");
		}
		
		let jobEnd = "";
		if(info['job_end'] == null) {
			jobEnd = "-";
		} else {
			jobEnd = moment(info['job_end']).format("YYYY-MM-DD HH:mm:ss");
		}
		
		let regDate = "";
		if(info['reg_date'] == null) {
			regDate = "-";
		} else {
			regDate = moment(info['reg_date']).format("YYYY-MM-DD HH:mm:ss");
		}
		
		let appendItem = '<tr class="odd pointer" onclick="showQueueContent('+idx+')">' + 
		 '	<td class="a-center " onclick="event.cancelBubble = true;">' + 
		 '		<input type="checkbox" id="q_check_'+idx+'" class="icheckbox_flat-green" name="table_records">' +
		 '	</td>' +
		 '	<td class=" ">'+info['tx_queue_id']+'</td>' +
		 '	<td class=" ">'+info['assigned_sys']+'</td>' +
		 '	<td class=" ">'+method+'</td>' +
		 '	<td class=" ">'+info['queue_state']+'</td>' +
		 '	<td class=" ">'+info['req_cnt']+'</td>' +
		 '	<td class=" ">'+jobResult+'</td>' +
		 '	<td class=" ">'+jobLog+'</td>' +
		 '	<td class=" ">'+jobBegin+'</td>' +
		 '	<td class=" ">'+jobEnd+'</td>' +
		 '	<td class=" ">'+regDate+'</td>';

		 if(jobResult == "ERROR" || info['queue_state'] != "STANDBY") {
			 appendItem += '<td class=" " onclick="event.cancelBubble = true;"><button onclick="retryOneQueue('+info['tx_queue_id']+')" type="button" class="btn btn-success pull-rigth">RETRY</button></td>';
		 } else {
			 appendItem += '<td class=" "> - </td>';
		 }
		 
		 appendItem += '</tr>'+
		 '<tr id="q_txdata_'+idx+'" class="odd pointer" style="display: none;">' +
		 '	<td id="q_content_td_'+idx+'" colspan="10">' +
		 '	  <table class="detail-table table-striped" style="width:100%;">' +
		 '		<colgroup>' +
		 '			<col width="10%"/>' +
		 '			<col width="80%"/>' +
		 '		</colgroup>' +
		 '		<tr>' +
		 '			<td class="col-detail-title">TX DATA</td><td id="q_txdata_td_'+idx+'"></td>' + 
		 '		</tr>' +
		 '	</td>' +
		 '</tr>';
		 return appendItem;
	}
	
	
	// JSON 데이터를 보기좋게 만들기
  	function makeTxDataPretty(elId, txData) {

  	    //console.log("[makeTxDataPretty] txData : ["+txData+"]");  	    

  	    if(txData != null && txData != "") {

  	    	var txDataJson = JSON.parse(txData);
  	        var jsonViewer = new JSONViewer();
  	        document.querySelector(elId).appendChild(jsonViewer.getContainer());
  	      	jsonViewer.showJSON(txDataJson);
  	        
  	    }
  	   
  	} 
    	
  	function setDatePicker() {
  		
  		var	end = moment();
  		var endDate = end.format('MM/DD/YYYY');
		var start = moment().subtract(30, 'days');
		var startDate = start.format('MM/DD/YYYY');
		

		var cb = function(start, end, label) {
		  //console.log(start.toISOString(), end.toISOString(), label);
		  $('#timerange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		};
		
		var optionSet1 = {
		  startDate: startDate,
		  endDate: endDate,
		  minDate: '01/01/2012',
		  maxDate: endDate,
		  dateLimit: {
			days: 365
		  },
		  showDropdowns: true,
		  showWeekNumbers: true,
		  timePicker: false,
		  timePickerIncrement: 1,
		  timePicker12Hour: true,
		  ranges: {
			'Today': [moment(), moment()],
			'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'Last 7 Days': [moment().subtract(6, 'days'), moment()],
			'Last 30 Days': [moment().subtract(29, 'days'), moment()],
			'This Month': [moment().startOf('month'), moment().endOf('month')],
			'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		  },
		  opens: 'left',
		  buttonClasses: ['btn btn-default'],
		  applyClass: 'btn-small btn-primary',
		  cancelClass: 'btn-small',
		  format: 'MM/DD/YYYY',
		  separator: ' to ',
		  locale: {
			applyLabel: 'Submit',
			cancelLabel: 'Clear',
			fromLabel: 'From',
			toLabel: 'To',
			customRangeLabel: 'Custom',
			daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
			monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
			firstDay: 1
		  }
		};

		$('#timerange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
		$('#timerange').daterangepicker(optionSet1, cb);
		$('#timerange').on('show.daterangepicker', function() {
		  //console.log("show event fired");
		});
		$('#timerange').on('hide.daterangepicker', function() {
		  //console.log("hide event fired");
		});
		$('#timerange').on('apply.daterangepicker', function(ev, picker) {
		  //console.log("날짜 설정 변경 이벤트 " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
		  txData = null;
		  moreJobResult = true;
		  nowPageTxList = 1;
		  $('#queue-list-table').empty();
		  //loadTxQueueHistList(nowPageTxList);	
		});
		$('#timerange').on('cancel.daterangepicker', function(ev, picker) {
		  //console.log("cancel event fired");
		});
  		
  	}
  	
  	function dateConverter(year, month, day, hour) {
		var label = month + "-" + day + " " + hour;
		return label;
	}
  	  	
  	function retryOneQueue(id) {
  		checkedQList = new Array();
  		checkedQList.push(id);
  		retryQueue();
  	}
  	
	function retryQueue() {
		
		console.log("[retryQueue]");
		
		if(checkedQList.length == 0)
			return;

		let url = prefix + "/api/retry_tx_queue.do";
		
		let param = JSON.stringify(checkedQList);
		
		//console.log("[param]"+param);

		$.ajax({
	        url: url,
	        type:'POST',
	        contentType: "application/json",
	        data:param,
	        success:function(data){
	        	//console.log("[RETRY TX QUEUE RESULT]");
	        	//console.log(data);
	        	
	        	if(data['state'] == 0) {
	        		// 수정 완료 메시지
	        		refresh();
	        		new PNotify({
	                    title: '변경 완료',
	                    text: '',
	                    type: 'info',
	                    hide: true,
	                    styling: 'bootstrap3'
	                });
	        	} else {
	        		new PNotify({
	                    title: '변경 에러',
	                    text: '요청 사항을 적용하지 못했습니다. 다시 시도해주세요.',
	                    type: 'error',
	                    hide: true,
	                    styling: 'bootstrap3'
	                });
	        	}	        	
	        	
	        },
	        error:function(request, status, error){
	        }
	    });
		
  	}
	
	
	// URL 파라미터 파싱
	function getUrlParams() {
	    var params = {};
	    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
	    return params;
	} 
	
	function activeMenu(menuId) {
		$("#" + menuId).addClass("current-page");
		$($("#" + menuId).parent()).css("display", "block");
		$($($("#" + menuId).parent()).parent()).addClass("active");
		$($($($($("#" + menuId).parent()).parent()).parent()).parent()).addClass("active");
	}
  
  </script>

  <body class="nav-md">
  	<div class="container body">
  		<div class="main_container">
  			<div class="right_col" role="main">
  				<div class="row">
  					<div class="col-md-12 col-sm-12 col-xs-12">
  						<div class="dashboard_graph">
							<!-- 코드 생성 -->
						  	종류 : <input type="text" id="input-coupoon-desc" name="input-coupoon-code" placeholder="10% 할인 쿠폰">
						  	<button class="favorite styled" type="button" onclick="createCoupon()"> 쿠폰 생성 </button>  	
						  	  	
						  	<br>
						  	<br>
						  	
						  	<!-- 코드 조회 및 사용 -->
						  	<input type="text" id="input-coupoon-code" name="input-coupoon-code" placeholder="쿠폰 번호를 입력해주세요.">
						  	<button type="button" onclick="checkCoupon()"> 쿠폰 정보 조회 </button>  	
						  	<button type="button" onclick="useCoupon()"> 쿠폰 사용 </button>  
						  	
						  	<br><br>
						  	
						  	<!-- 쿠폰 정보 -->  	
						  	<div id="coupon-info" style="border: 2px solid blue; display: none;">
						  	
						  	</div>
  						</div>
  						<br>
  						<div class="col-md-12 col-sm-12 col-xs-12">
  							<div class="x_panel">
  								<div class="x_title">
  									<h2>상세내역 </h2>
  									<ul class="nav navbar-right panel_toolbox">
  									</ul>
  									<div class="clearfix"></div>
  								</div>
  								<div class="x_content">  									
								  	<!-- 코드 리스트 -->
								  	<div id="div_list" class="table-responsive" style="width:100%; height:700px; overflow:auto">
									  <table class="detail-table table-striped bulk_action">
										<thead>
										  <tr class="headings">
											<th>
											  <input type="checkbox" id="check-all" class="flat">
											</th>
											<th class="column-title">코드 </th>
											<th class="column-title">설명 </th>
											<th class="column-title">상태 </th>
											<th class="column-title">등록 날짜 </th>
											<th class="column-title">사용 날짜 </th>
											<th class="column-title">만료 날짜 </th>
											<!-- <th class="column-title no-link last"><span class="nobr">Action</span> 
											</th>
											-->
											<th class="bulk-actions" colspan="6">
											  <a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
											</th>
										  </tr>
										</thead>
								
										<tbody id="list-table">
								
										</tbody>
									  </table>
									</div>
  								</div>
	  						</div>
	  					</div>
	  				</div>
	  			</div>
	  		</div>
	  	</div> 
	  </div> 	
  </body>
</html>
