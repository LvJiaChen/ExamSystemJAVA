<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html>
<html>
<head>
<title><decorator:title default="" /> - dmdm</title>
<decorator:head />
<meta charset="utf-8" />
<style type="text/css">
body, html {
	width: 100%;
	height: 600px;
	padding: 0;
	margin: 0;
}

#box_top {
	width: 100%;
	height: 300px;
	background: blue;
}

#box_bottom {
	width: 100%;
	height: 300px;
}

#box_l {
	float: left;
	width: 50%;
	height: 300px;
	background: blueviolet;
}

#box_r {
	float: left;
	width: 50%;
	height: 300px;
	background: skyblue;
}
</style>
</head>
<body>
	<div id="box_top">
		<page:applyDecorator page="/demo/top" name="blank" />
	</div>
	<div id="box_bottom">
		<div id="box_l"></div>
		<div id="box_r">
			<decorator:body />
		</div>
	</div>
</body>
</html>

