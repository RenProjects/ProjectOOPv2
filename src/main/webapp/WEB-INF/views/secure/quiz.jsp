<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Quiz</title>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.10.1.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/main.js" />"></script>

<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>
<body>

	<div class="cont">
		<!-- Starting image -->
		<img alt="start" src='<c:url value="/resources/img/start.png" />'
			class="start" />


		<!-- Images display start -->
		<div id="imageCont">
			<div class="left">
				<img alt="start" src='<c:url value="" />' class="" /> 
				<span class="text"></span>
			</div>
			<div class="right">
				<img alt="start" src='<c:url value="" />' class="" /> 
				<span class="text"></span>
			</div>
			<div class="clear"></div>
		</div>
		<!-- Images display end -->

		<!-- Anwsers diplay start -->
		<div id="anwserCont">
			<img alt="start"
				src='<c:url value="/resources/img/adNWpo9_700b.jpg" />' class="" />
			<input type="text" id="answer" name="anwser" /> <br /> 
			<a href="" id="submitAnswer">Potvrdi</a>
		</div>
		<!-- Anwsers diplay end -->
	</div>

</body>
</html>
