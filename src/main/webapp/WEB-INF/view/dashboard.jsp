<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<style><%@include file="../../static/css/bootstrap.min.css"%></style>
<style><%@include file="../../static/css/font-awesome.css"%></style>
<style><%@include file="../../static/css/main.css"%></style>
	
	
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/computer-database/Dashboard?search=">
			<fmt:message key="label.home" /> </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${page.nbElementDB }"/> <fmt:message key="label.computersFound" /></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
				<!--  ici on search -->
				
				<input type="search" id="searchbox" name="search"
							class="form-control" 
							placeholder="<fmt:message key="label.searchName" />" />
				<input
							
							type="submit" id="searchsubmit" value="<fmt:message key="label.filter" />"
							class="btn btn-primary" />
					</form>
					
				</div>
				
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" 
						href="/computer-database/AddComputerServlet"><fmt:message key="label.add" /></a> 
						
						<a class="btn btn-default" id="editComputer" 
						href="#"
						onclick="$.fn.toggleEditMode();"><fmt:message key="label.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" />
							 <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
							
							<!-- ici on order by  -->
						<th><a href="?orderBy=cp.name,ASC"> <i
								class="fa fa-fw  fa-angle-up  fa-clickable"></i>
						</a><fmt:message key="label.computerName" /><a href="?orderBy=cp.name,DESC"> <i
								class="fa fa-fw  fa-angle-down  fa-clickable"></i>
						</a></th>

						<th><a href="?orderBy=cp.introduced,ASC"> <i
								class="fa fa-fw  fa-angle-up  fa-clickable"></i>
						</a><fmt:message key="label.introducedDate" /><a href="?orderBy=cp.introduced,DESC"> <i
								class="fa fa-fw  fa-angle-down  fa-clickable"></i>
						</a></th>
						
						<th><a href="?orderBy=cp.discontinued,ASC"> 
						<i class="fa fa-fw  fa-angle-ud  fa-clickable"></i>
						</a><fmt:message key="label.discontinuedDate" /><a href="?orderBy=cp.discontinued,DESC"> <i
								class="fa fa-fw  fa-angle-down  fa-clickable"></i>
						</a></th>

						<th><a href="?orderBy=cny.name,ASC"> <i
								class="fa fa-fw  fa-angle-up  fa-clickable"></i>
						</a><fmt:message key="label.companyName" /><a href="?orderBy=cny.name,DESC"> <i
								class="fa fa-fw  fa-angle-down  fa-clickable"></i>
						</a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->

				<tbody id="results">
					<c:forEach var="computer" items="${listComputer}">
						<tr>
							<td class="editMode"><input type="checkbox" id="id" name="cb"
								class="cb" value=${computer.id }>${computer.id }</td>
							<td><a
							href="
							<c:url value="/EditComputerServlet"><c:param 
								name="id" value="${ computer.id }"/></c:url>
							"
							onclick=""> ${ computer.name }</a>
							</td>
							<td>${ computer.introduced }</td>
							<td>${ computer.discontinued }</td>
							<td>${ computer.company.name }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<div class="btn-group btn-sm pull left" role="group">
			
			<a href="?lang=en"><fmt:message key="label.lang.en" /></a>
			<a href="?lang=fr"><fmt:message key="label.lang.fr" /></a>
			</div>
				<ul class="pagination">
				<li><a href="?page=1" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a></li>
				
								
				<c:if test="${page.numeroPage > 2}">
					<li><a href="
                      		<c:url value="/Dashboard"><c:param 
                      			name="page" value="${page.numeroPage - 2}"/></c:url>
                      	"> ${page.numeroPage - 2}
                 	</a>
                 </li>
                 </c:if>
                 <c:if test="${page.numeroPage > 1}">
					<li><a href="
                      		<c:url value="/Dashboard"><c:param 
                      			name="page" value="${page.numeroPage - 1}"/></c:url>
                      	"> ${page.numeroPage - 1}
                 	</a>
                 </li>
                 </c:if>
				<li><a href="?page=${page.numeroPage }"   >${page.numeroPage}</a></li>
				<c:if test="${page.numeroPage < page.totalPage}">
					<li><a href="
                   		<c:url value="/Dashboard"><c:param 
                   			name="page" value="${page.numeroPage + 1}"/></c:url>
                      	">${page.numeroPage + 1}
                 	</a>
                 	</li>
                 </c:if>
                 <c:if test="${page.numeroPage < page.totalPage - 1}">
					<li><a href="
                   		<c:url value="/Dashboard"><c:param 
                   			name="page" value="${page.numeroPage + 2}"/></c:url>
                      	">${page.numeroPage + 2}
                 	</a>
                 	</li>
                 </c:if>
                 <li><a href="?page=${page.totalPage}" aria-label="Next"> 
                 <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		<div class="btn-group btn-group-sm pull-right" role="group" >
	         	<form id="nbElementByPage" action="#" method="GET">
		        	<button type="submit" class="btn btn-default"
						name="nbElementByPage" value="10">10</button>
		            <button type="submit" class="btn btn-default"
		            	name="nbElementByPage" value="50">50</button>
		            <button type="submit" class="btn btn-default" 
		            	name="nbElementByPage" value="100">100</button>
	            </form>
	        </div>
	</div>
	</footer>
	

<script><%@include file="../../static/js/jquery.min.js"%></script>
<script><%@include file="../../static/js/bootstrap.min.js"%></script>
<script><%@include file="../../static/js/dashboard.js"%></script>

</body>
</html>