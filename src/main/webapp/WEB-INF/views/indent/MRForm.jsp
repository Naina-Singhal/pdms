<%-- 
    Document   : MRForm
    Created on : Oct 12, 2016, 4:35:31 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e12 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>PDMS : Material Requisition Form</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
    </head>
    <body id="minovate" class="appWrapper">
        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>    
            <spring:form name="addIndentForm" commandName="indentObj">
                <section id="content">
                    <div class="page">
                        <!-- == Start :Page Header & BRead Crumbs ======== -->
                        <div class="pageheader">
                            <h2> Material Requisition <span>Form</span></h2>
                            <div class="page-bar">
                                <ul class="page-breadcrumb">
                                    <li>
                                        <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                    </li>
                                    <li>
                                        <a href="#">Material Requisition Form</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- == End :Page Header & BRead Crumbs ======== -->
                        <!-- == Start :Page Form ======== -->
                        <div class="pagecontent">
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Indent</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">MR No#: </label>
                                                    <input type="text" name="mrno" id='mrno' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorindentNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Received Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="recevidedDate" id="recevidedDate" 
                                                               value="${indentSObj.recevidedDate}"/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorrecevidedDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Date</label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="indentDate" id="indentDate" 
                                                               value="${indentSObj.indentDate}"/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorindentDate" style="color: red"></label>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                            </div>
                        </div>
                        <!-- == End :Page Form ======== -->
                    </div>
                </section>
            </spring:form>
        </div>
        <!-- ====================================================
        ================= End : Application Content ===========
        ===================================================== -->
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.e12 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>