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
                <c:if test="${(userPermiLi.d20 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Cheque Or DD Entry For Challan Record(s) List</title>
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
                        <h2>Cheque Or DD Entry For Challan Record(s) <span>  List(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Cheque Or DD Entry For Challan Record(s)</a>
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
                            <spring:form >
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Voucher Data Record(s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.d11 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openChequeDDForm();">Cheque Or DD Challan Entry
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
                                                    <c:if test="${DdRecord != null}">
                                                        <c:forEach var="mrObj" items="${DdRecord}">
                                                            <tr class="gradeX">
                                                                <td><%= ++slno %></td> 
                                                                <td>${mrObj.receiptNo}</td>
                                                                <td class="less-more">${mrObj.supplierName}</td>
                                                                <td>${mrObj.challanNo}</td>
                                                                <td>${mrObj.balance}</td>
                                                                <td>${mrObj.amount}</td>                                                                                                                               
                                                                <td class="less-more">${mrObj.details}</td>
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
        <script src="<c:url value="/appjs/account/ChequeDDReJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.d20 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>