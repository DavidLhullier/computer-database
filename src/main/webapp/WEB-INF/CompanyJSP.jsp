<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>title</title>
</head>
<body>

	<%@ include file="menu.jsp"%>
	<p>okokokokook</p>

	<p>
		<%
        String heure = (String) request.getAttribute("heure");
        if (heure.equals("jour")) {
            out.println("Bonjour"); 
        }
        else {
            out.println("Bonsoir");
        }
    %>
	</p>
	<p>
		<%
        for (int i = 0 ; i < 20 ; i++) {
            out.println("Une nouvelle ligne !<br />");
        }
    %>
	</p>


	<c:out value="test" />
	
	<c:forEach var="item" items="${tirage}" >
	<c:out value="${item}" />
</c:forEach>
	
	
</body>
</html>