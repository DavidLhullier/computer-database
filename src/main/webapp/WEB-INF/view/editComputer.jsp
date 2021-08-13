<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <a class="navbar-brand" href="/computer-database/Dashboard?page=1">
            <fmt:message key="label.home" /> </a>
        </div>
    </header>
    <section id="main">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: "${ computer.id }"
                    </div>
                    <h1><fmt:message key="label.editComputer" /></h1>

                    <form action="/computer-database/EditComputerServlet" method="POST">
                        <input type="hidden" value="${ computer.id }" id="id" name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><fmt:message key="label.computerName" /></label>
                                <input type="text" value="${ computer.name }" class="form-control"
                                 id="computerName" name="name" placeholder=<fmt:message key="label.computerName" /> >
                            </div>
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.introducedDate" /></label>
                                <input type="date" value="${ computer.introduced }" class="form-control" 
                                id="introduced" name="introduced" placeholder=<fmt:message key="label.introducedDate" />>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.discontinuedDate" /></label>
                                <input type="date" value="${ computer.discontinued }" class="form-control" 
                                id="discontinued" name="discontinued" placeholder="<fmt:message key="label.discontinuedDate" />">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="label.companyName" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0"> ${ company.name }</option>
                                     <c:forEach items="${ listCompany }" var="company">
										<option value="${ company.id }">${ company.name }</option>                                    	
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<fmt:message key="label.edit" /> class="btn btn-primary">
                            <fmt:message key="label.or" />
                            <a href="/computer-database/Dashboard?page=1" class="btn btn-default"><fmt:message key="label.cancel" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>