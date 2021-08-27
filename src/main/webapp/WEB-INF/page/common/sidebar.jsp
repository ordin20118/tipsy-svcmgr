<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

 	<div class="col-md-3 left_col">
	  <div class="left_col scroll-view">
	    <div class="navbar nav_title" style="border: 0;">
	      <a href="home.do" class="site_title"><i class="fa fa-paw"></i> <span>RenaScope</span></a>
	    </div>
	
	    <div class="clearfix"></div>
		
	    <!-- menu profile quick info 
	    <div class="profile clearfix">
	      <div class="profile_pic">
	        <img src="images/img.jpg" alt="..." class="img-circle profile_img">
	      </div>
	      <div class="profile_info">
	        <span>Welcome,</span>
	        <h2>John Doe</h2>
	      </div>
	    </div>
	    /menu profile quick info -->
	    
    <br />
    
	<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
        <div class="menu_section">
          <h3>General</h3>
          <ul class="nav side-menu">
            <li><a><i class="fa fa-home"></i> Home <span class="fa fa-chevron-down"></span></a>
              <ul class="nav child_menu">
                <li id="smenu_0101"><a href="home.do">Dashboard</a></li>
			    <li id="smenu_0102"><a href="join_tx_scope.do?actionCode=1001,1002,1003">회원가입</a></li>
			    <li id="smenu_0103"><a href="checkin_tx_scope.do?actionCode=1006">체크인</a></li>
			    <li id="smenu_0104"><a href="review_tx_scope.do?actionCode=2001">리뷰작성</a></li>
			    <li id="smenu_0105"><a href="review_tx_scope.do?actionCode=2002,2003,2004">리뷰조회</a></li>
			    <li id="smenu_0106"><a href="review_tx_scope.do?actionCode=2005">리뷰평가</a></li>
			    <!-- <li id="smenu_0107"><a href="tx_detail.do?actionCode=2005">리뷰한마디</a></li> -->
			    <li id="smenu_0107"><a href="prod_tx_scope.do?actionCode=3001">제품평가</a></li>
			    <!-- <li id="smenu_0109"><a href="prod_tx_scope.do?actionCode=3002">제품한마디</a></li> -->
			    <li id="smenu_0108"><a href="tx_detail.do?actionCode=4001,4002">공유</a></li>
			    <li id="smenu_0109"><a href="tx_detail.do?actionCode=1004,1005">서비스 신고</a></li>
		      </ul>
		    </li>
		    <li><a><i class="fa fa-gear"></i> Manage <span class="fa fa-chevron-down"></span></a>
              <ul class="nav child_menu">
              	<li id="smenu_0201"><a href="abi_manage.do">ABI 관리</a></li>
			    <li id="smenu_0202"><a href="tx_queue_list.do">TX QUEUE</a></li>
			    <li id="smenu_0203"><a href="tx_queue_hist_list.do">TX QUEUE HISTORY</a></li>
              </ul>
            </li>
		  </ul>
		</div>
	</div>	
	
		<!-- 
		<!-- /menu footer buttons 
        <div class="sidebar-footer hidden-small">
          <a data-toggle="tooltip" data-placement="top" title="Settings">
            <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
          </a>
          <a data-toggle="tooltip" data-placement="top" title="FullScreen">
            <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
          </a>
          <a data-toggle="tooltip" data-placement="top" title="Lock">
            <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
          </a>
          <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
            <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
          </a>
        </div>
        <!-- /menu footer buttons -->
      
      </div>
    </div>