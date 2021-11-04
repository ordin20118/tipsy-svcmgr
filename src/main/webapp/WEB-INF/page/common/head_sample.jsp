<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="tipsy.common.configuration.Configuration"
%><%
	response.setHeader("Cache-Control","no-store");   
    response.setHeader("Pragma","no-cache");
%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="path" value="<%=request.getContextPath()%>"/>
<script>
	var prefix = '${path}';
</script>
<head>
	<!-- meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 	<meta name="description" content="">
  	<meta name="author" content=""> -->

	<title>Lux Una</title>
	<!-- link -->
	<link href="${path}/css/tom.css" rel="stylesheet">
	<link href="${path}/css/modern-business.css" rel="stylesheet">
	
	<!-- Bootstrap core JavaScript -->
  	<script src="${path}/vendors/jquery/jquery.min.js"></script>
  	<script src="${path}/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<!-- js -->
	<script type="text/javascript" src="${path}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${path}/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="${path}/js/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="${path}/js/moment-timezone-with-data.min.js"></script>
    <!-- Bootstrap -->
    <link href="${path}/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${path}/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${path}/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="${path}/vendors/iCheck/skins/flat/green.css" rel="stylesheet">

    <!-- bootstrap-progressbar -->
    <link href="${path}/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="${path}/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="${path}/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${path}/build/css/custom.min.css" rel="stylesheet">  

	<!-- PNotify -->
    <link href="${path}/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="${path}/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="${path}/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">
 
</head>
