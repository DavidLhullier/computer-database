<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database - Edit Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<style><%@include file="../../static/css/bootstrap.min.css"%></style>
<style><%@include file="../../static/css/font-awesome.css"%></style>
<style><%@include file="../../static/css/main.css"%></style><link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/computer-database/Dashboard?page=1"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: "${ computer.id }"
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="/computer-database/EditComputerServlet" method="POST">
                        <input type="hidden" value="${ computer.id }" id="id" name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" value="${ computer.name }" class="form-control" id="computerName" name="name" placeholder="Computer name" >
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" value="${ computer.introduced }" class="form-control" id="introduced" name="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" value="${ computer.discontinued }" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0"> ${ company.name }</option>
                                     <c:forEach items="${ listCompany }" var="company">
										<option value="${ company.id }">${ company.name }</option>                                    	
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="/computer-database/Dashboard?page=1" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>