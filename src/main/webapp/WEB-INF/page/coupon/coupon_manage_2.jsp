<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html lang="en">

  <%@include file="../common/head.jsp" %>
  <link href="${path}/css/json-viewer.css"></link>
  <script type="text/javascript" src="${path}/js/json-viewer.js"></script>
     
  <script>
  
  	const perPage = 30;
  	var actionName = "";
  	var actionCode = 0;
  	var moreJobResult = true;
  	var nowPageTxList = 0;
  	var checkedQList = new Array();
  	
  	let queueData;
  
  	$(document).ready(function() {  
  		
  		setEvent();
  		//setDatePicker();
  		loadTxQueueList(1);
  		
  	  	$("#div_queue_list").scroll(function() {
  		    if((this.scrollTop+this.clientHeight) >= this.scrollHeight) {
  		    	if(moreJobResult) {
  		    		loadTxQueueList(nowPageTxList+1);	
  		    	}
  		    }
  		});	 
  	  	
  	});
  	
  	function setEvent() {
  		
  		$('#sel_assigned_sys').change(function() {
  			reloadTxQueueList();
  		});
  		
  		$('#sel_queue_state').change(function() {
  			reloadTxQueueList();
  		});
  		
  		$('#sel_job_result').change(function() {
  			reloadTxQueueList();
  		});
  		
  	}
    
  
  	function loadTxQueueList(page, isRefresh) {
  		
  		console.log("[loadTxQueueList]");
  		
  		//let picker = $('#timerange').data('daterangepicker');  		
  		//let startDate = picker.startDate.format('YYYYMMDDHHmmss');
  		//let endDate = picker.endDate.format('YYYYMMDDHHmmss');
  		
  		//console.log("[날짜 확인]:"+picker.startDate+"/"+endDate);
  		 		
  		let urlParam = 'paging.nowPage=' + page +
  		'&paging.perPage=' + perPage +
  		'&orderby.field=tx_queue_id' +
  		'&orderby.sorting=desc'; 
		//'&startDateStr=' + startDate +'&endDateStr=' + endDate;
		
		if($("#sel_assigned_sys").val() != "") {
			urlParam += '&assignedSys=' + $("#sel_assigned_sys").val();
		}
		
		if($("#sel_queue_state").val() != "") {
			urlParam += '&queueState=' + $("#sel_queue_state").val();
		}
		
		if($("#sel_job_result").val() != "") {
			urlParam += '&jobResult=' + $("#sel_job_result").val();
		}
			
  		let url = prefix + "/api/tx_queue_list.do?"+urlParam;
  		
  		console.log("[Tx Queue Hist List URL]:"+url);

		$.ajax({
	        url: url,
	        type:'get',
	        success:function(data){
	        	//console.log("[TX QUEUE LIST]");
	        	//console.log(data);
	        	
	        	var len;
	        	if(queueData != null) {
	        		len = queueData.length;
	        		queueData = queueData.concat(data['list']);
	        	} else {
	        		len = 0;
	        		queueData = data['list'];
	        	}
	        	
	        	let size = data['list'].length;
	        	if(size == 0 || size < perPage) {
	        		moreJobResult = false;
	        	}
	        	
	        	if(size > 0 && isRefresh == null) {
	        		nowPageTxList++;
	        	}
	        	
	        	printTxQueueTable(data, len);
	        },
	        error:function(request, status, error){
	        }
	    });
  	}
  	
	function printTxQueueTable(data, len) {
  		let list = data['list'];
  		let size = list.length;
  		
  		for(var i=0; i<size; i++) {
  	  		let appendItem = null;
  	  		let idx = len + i;
  	  		
  	  		if(i%2 == 0) {
  	  			appendItem = makeTxQueueHtmlEven(list[i], idx);
  	  		} else if(i%2 == 1) {
  	  			appendItem = makeTxQueueHtmlOdd(list[i], idx);
  	  		}  	  		
  			
  	  		$('#queue-list-table').append(appendItem);
  	  		  	  		
  	  		// TX DATA를 JSONViewer로 출력 
  	  		makeTxDataPretty("#q_txdata_td_"+idx, list[i]['tx_data']);
  	  	
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
	
	function reloadTxQueueList() {
		$("#queue-list-table").empty();
		checkedQList = new Array();
		queueData = null;
		nowPageTxList = 0;
		loadTxQueueList(1);
	}
	
	function refresh() {
		$("#queue-list-table").empty();
		checkedQList = new Array();
		queueData = null;
		loadTxQueueList(nowPageTxList, true);
	}
	
	function showQueueContent(idx) {
		
		if($('#q_txdata_'+idx).is(":visible")) {
			$('#q_txdata_'+idx).hide();
		} else {
			$('#q_txdata_'+idx).show();			
		}
	}
	
	function makeTxQueueHtmlEven(info, idx) {
		
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
		
  		let appendItem = '<tr class="even pointer" onclick="showQueueContent('+idx+')">' + 
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
				 appendItem += '<td class=" "onclick="event.cancelBubble = true;"><button onclick="retryOneQueue('+info['tx_queue_id']+')" type="button" class="btn btn-success pull-rigth">RETRY</button></td>';
			 } else {
				 appendItem += '<td class=" "> - </td>';
			 }
			 
			 appendItem += '</tr>'+
			 '<tr id="q_txdata_'+idx+'" class="even pointer" style="display: none;">' +
			 '	<td colspan="10">' +
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
      
        <!-- sidebar menu -->
		<%@include file="../common/sidebar.jsp"%>
        <!-- /sidebar menu -->
          

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <!-- top tiles -->

          <!-- /top tiles -->

          <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
              <div class="dashboard_graph">

                <div class="row x_title">
                  <div class="col-md-6">
                    <h3 id="title">쿠폰 관리</h3>
                  </div>
                  <div class="col-md-6">
                    <div id="timerange" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
                      <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                      <span>December 30, 2014 - January 28, 2015</span> <b class="caret"></b>
                    </div>
                  </div>
                </div>
                <div class="clearfix"></div>
              </div>

			  <br>

			  <div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
				  <div class="x_title">
					<h2>상세내역 </h2>
					<ul class="nav navbar-right panel_toolbox">
						 <li style="margin-right: 10px;">
						 	<p>Assigned System : 
								<select id="sel_assigned_sys">
									<option value="" selected>시스템 선택</option>
									<option value=batch_s01>batch_s01</option>
									<option value="batch_c111">batch_c111</option>
									<option value="batch_c112">batch_c112</option>
								</select>
					        </p> 	
						  </li>
						 <!--  <li style="margin-right: 10px;">
						 	<p>Method : 
								<select id="text_contract_type">
									<option value="" selected>TX 함수 선택</option>
									<option value="">...</option>
									<option value="">...</option>
								</select>
					        </p> 	
						  </li> -->
						  <li style="margin-right: 10px;">
						 	<p>Queue State : 
								<select id="sel_queue_state">
									<option value="" selected>상태 선택</option>
									<option value="STANDBY">STANDBY</option>
									<option value="ING">ING</option>
									<option value="DONE">DONE</option>
								</select>
					        </p> 	
						  </li>
						  <li style="margin-right: 10px;">
						 	<p>Job Result : 
								<select id="sel_job_result">
									<option value="" selected>작업 결과 선택</option>
									<option value="Success">SUCCESS</option>
									<option value="Error">ERROR</option>
								</select>
					        </p> 	
						  </li>
						  <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						  </li>
						  <li><a class="close-link"><i class="fa fa-close"></i></a>
						  </li>					  
						</ul>
					<div class="clearfix"></div>
				  </div>

				  <div class="x_content">

					<!-- <p>설정 기간 동안의 <code id="sub_title">트랜잭션 큐 히스토리 </code>입니다.</p> -->
					<button onclick="retryQueue()" type="button" class="btn btn-success pull-rigth">선택 큐 재시도</button>
					
					<div id="div_queue_list" class="table-responsive" style="width:100%; height:700px; overflow:auto">
					  <table class="detail-table table-striped bulk_action">
						<thead>
						  <tr class="headings">
							<th>
							  <input type="checkbox" id="check-all" class="flat">
							</th>
							<th class="column-title">QUEUE ID </th>
							<th class="column-title">ASSIGNED SYSTEM </th>
							<th class="column-title">METHOD </th>
							<th class="column-title">STATE </th>
							<th class="column-title">REQUEST CNT </th>
							<th class="column-title">JOB RESULT </th>
							<th class="column-title">JOB LOG </th>
							<th class="column-title">JOB BEGIN </th>
							<th class="column-title">JOB END </th>
							<th class="column-title">REG DATE </th>
							<th class="column-title">RETRY </th>
							<!-- <th class="column-title no-link last"><span class="nobr">Action</span> 
							</th>
							-->
							<th class="bulk-actions" colspan="10">
							  <a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
							</th>
						  </tr>
						</thead>

						<tbody id="queue-list-table">
		
						</tbody>
					  </table>
					</div>

				  </div>
				</div>
			  </div>
            </div>

          </div>
          <br/>

        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <%@include file="../common/footer.jsp"%>
        <!-- /footer content -->
      </div>
    </div>
    
  </body>
</html>
