<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
<title>Prijava</title>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
</head>
<body>

	<div class="cont_login">
	
		<fieldset>
			<legend>Prijava</legend>


			<form name='loginForm'
				action="<c:url value='/j_spring_security_check' />" method='POST'>

				<table>
					<tr>
						<td>Koriničko ime:</td>
						<td><input type='text' name='username'></td>
					</tr>
					<tr>
						<td>Lozinka:</td>
						<td><input type='password' name='password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit"
							value="Kreni" class="button" /></td>
					</tr>
				</table>

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			
			<c:if test="${not empty error}">
	            <div class="error">${error}</div>
	        </c:if>
		</fieldset>
	</div>

</body>
</html>
