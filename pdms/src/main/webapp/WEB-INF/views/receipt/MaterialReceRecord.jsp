<%-- 
    Document   : PublicTenderLi
    Created on : Oct 26, 2016, 12:54:10 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.pdms.constants.ApplicationConstants" var="constants" />
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.c4 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Material Receipt Record(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>

    </head>
    <body id="minovate" class="appWrapper">
        
        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>    
            <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>Material Receipt Record(s) <span>  List(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Material Receipt Record(s)</a>
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
                        <%-- END : DISPLAY STATUS MESSAGE--%>

                        <%-- **************************************************************** --%>
                        <%-- ************** START : TENDER LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="indentLi" method="post" commandName="vIndentObj">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Material Receipt Record(s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.c3 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openMeteReceForm();">Material Receipt Entry
                                                    </button> 
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body" style="overflow-x: scroll">
                                        <div class="table-responsive">
                                            <table class="table table-custom row-border hover order-column" id="basic-usage" cellspacing="0" style="width:100% !important;">
                                                <thead>
                                                    <tr>
                                                    </tr>
                                                </thead>    
                                                <thead id="filterrow">    
                                                    <tr>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>                                                        
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% int slno = 0;    %>
                                                    <c:if test="${MRrecord != null}">
                                                        <c:forEach var="mrObj" items="${MRrecord}">
                                                            <tr class="gradeX">
                                                                <td><%= ++slno %></td> 
                                                                <td>${mrObj.dbNumber}</td>
                                                                <td>${mrObj.section}</td>
                                                                <td>${mrObj.poLastNo}</td>
                                                                <td>${mrObj.poLNoDate}</td>
                                                                <td>${mrObj.indentor}</td>                                                                                                                               
                                                                <td class="less-more">${mrObj.remarks}</td>
                                                                <td> 
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="">Edit
                                                                    </button>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="">View
                                                                    </button>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="">PDF
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- **************** END : TENDER LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                    </div>
                    <!-- == End :Page Form ======== -->
                </div>
            </section>
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/receipt/MateReceReJS.js"/>" type="text/javascript"></script>
        <link rel="stylesheet" href="<c:url value="/assets/css/second-dataTables.css"/>">
        <script src="<c:url value="/assets/js/jquery.seconDataTables.min.js"/>" type="text/javascript"></script>
        <script>
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->


    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.c4 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>