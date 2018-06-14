<%-- 
    Document   : GroupList
    Created on : Sep 30, 2016, 9:05:51 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.m25 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Receiver List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>       
        <style>
            
        </style>
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
                        <h2>Receiver(s) <span> Add/ Update Group(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Receiver(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <div>
                            <div class="display_msg_success_Ma">
                                <label id="successDupItem" class="label_success_msg"></label>
                            </div>
                            <div class="display_msg_error_Ma">
                                <label id="errorDupItem" class="label_error_msg"></label>
                            </div>
                        </div>
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT RECEIVER DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form  id="Receiver_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Receiver</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="receiverId" name="receiverId" value=""/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Receiver Code: </label>
                                                    <input type="text" name="receiverCode" id='receiverCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorgroupCode" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Receiver Name: </label>
                                                    <input type="text" name="receiverName" id='receiverName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorgroupName" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                          
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="saveReceiver">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updateDiv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateReceiver" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelReceiver">
                                                        <span>Cancel</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT RECEIVER DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>

                        <%-- **************************************************************** --%>
                        <%-- **********START : RECEIVER LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Receiver Group(s)</strong> List</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive">
                                            <div id="show_table">
                                                
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- *********** END : RECEIVER LIST DETAIL(S)******************** --%>
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
        <script src="<c:url value="/appjs/master/ReceiverFormJs.js"/>" type="text/javascript"></script>
         <link rel="stylesheet" href="<c:url value="/assets/css/second-dataTables.css"/>">
        <script src="<c:url value="/assets/js/jquery.seconDataTables.min.js"/>" type="text/javascript"></script>        
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
           
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m25 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>
