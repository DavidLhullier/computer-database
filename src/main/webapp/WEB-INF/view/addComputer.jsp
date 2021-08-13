<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<style><%@include file="../../static/css/bootstrap.min.css"%></style>
<style><%@include file="../../static/css/font-awesome.css"%></style>
<style><%@include file="../../static/css/main.css"%></style><link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/computer-database/Dashboard?page=1"><fmt:message key="label.home" /> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="/computer-database/AddComputerServlet" 
                    	method="POST" >
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><fmt:message key="label.computerName" /></label>
                                <input type="text" class="form-control" name="name" 
                                placeholder=<fmt:message key="label.computerName" /> value=${ computer.name }>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.introducedDate" /></label>
                                <input type="date" class="form-control" name="introduced" 
                                placeholder=<fmt:message key="label.introducedDate" />  value=${ computer.introduced }>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.discontinuedDate" /></label>
                                <input type="date" class="form-control" name="discontinued" 
                                placeholder=<fmt:message key="label.discontinedDate" />  value=${ computer.discontinued }>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="label.companyName" /></label>
                                <select class="form-control" name="companyId" >
                                    <option value="0">--</option>
                                    <c:forEach items="${ listCompany }" var="company">
										<option value="${ company.id }">${ company.name }</option>                                    	
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<fmt:message key="label.add" />" 
                            class="btn btn-primary">
                           
                            <fmt:message key="label.or" />
                            <a href="/computer-database/Dashboard?page=1" class="btn btn-default">
                            <fmt:message key="label.cancel" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>