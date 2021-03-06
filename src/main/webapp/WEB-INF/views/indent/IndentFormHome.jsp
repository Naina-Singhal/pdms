<%-- 
    Document   : IndentFormHome
    Created on : Dec 5, 2016, 2:11:35 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e3 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Indent Form</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/assets/js/vendor/jquery-ui/jquery.blockUI.js"/>"></script>
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
                            <h2>Indent(s) <span> Indent Form</span></h2>
                            <div class="page-bar">
                                <ul class="page-breadcrumb">
                                    <li>
                                        <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                    </li>
                                    <li>
                                        <a href="#">Indent Form</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- == End :Page Header & BRead Crumbs ======== -->
                        <!-- == Start :Page Form ======== -->
                        <div class="pagecontent">
                            <%-- START : DISPLAY STATUS MESSAGE--%>
                            <c:if test="${msg != null}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="alert alert-success alert-dismissable">
                                            <button type="button" class="close" data-dismiss="alert" 
                                                    aria-hidden="true">&times;</button>
                                            <strong>Success!</strong>${msg}
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${ermsg != null}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="alert alert-warning alert-dismissable">
                                            <button type="button" class="close" data-dismiss="alert" 
                                                    aria-hidden="true">&times;</button>
                                            <strong>Error!</strong>${ermsg}
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <%-- END : DISPLAY STATUS MESSAGE--%>
                            
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Indent</strong> Form</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Indent Type</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label class="checkbox checkbox-custom mr-10">
                                                        <input type="radio" name="indType" value="self"
                                                           id="indType"
                                                           class="form-control"
                                                           checked="true"/>
                                                        <i></i>Self:
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label class="checkbox checkbox-custom mr-10">
                                                        <input type="radio" name="indType" value="behalf"
                                                           id="indType"
                                                           class="form-control"/>
                                                        <i></i>On Behalf:
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <span class="tools pull-right">
                                                        <input type="button" value="Proceed" class="btn btn-info "
                                                               id="redirectIndentForm" />
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        </div>
                </section>
            </spring:form>
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->

        <script src="<c:url value="/appjs/indentFormJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.e3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>