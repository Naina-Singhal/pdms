<%-- 
    Document   : UserList
    Created on : Oct 1, 2016, 7:44:53 AM
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
                <c:if test="${(userPermiLi.d24 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Bill Register Update Screen(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
           
        </style>
    </head>
    <c:set var="userType" value="User"/>
    <c:set var="userID" value="0"/>
    <c:if test="${sessionScope.USER_SESSION!=null}">
        <c:set var="userType" value="${sessionScope.USER_SESSION.employeeProfileDTO.employeeTypeDTO.employeeTypeName}"/>
        <c:set var="userID" value="${sessionScope.USER_SESSION.employeeID}"/>
    </c:if>
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
                        <h2>Bill Register Update Screen(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Bill Register Update Screen(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
               
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        <div class="display_msg_success_Ma">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error_Ma">
                            <label id="errorDupItem" class="label_error_msg"></label>
                        </div>
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                    </div>
                    <div class="pagecontent">
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form  id="Cheque payment Detail(s)">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Bill Register Update Screen(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="">Bill Register Update Screen record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Month: </label>                                                    
                                                    <div class='input-group datepicker' data-format="MMMM/YYYY">
                                                        <input type='text' class="form-control input-sm"
                                                               name="billDate" id="billDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                            </div>
                                                                                       
                                           </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT ITEM DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : BILL REGISTER UPDATE SCREEN DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Bill Register(s)</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>S No </th>
                                                            <th>Po Number</th>
                                                            <th>Bill Number</th>
                                                            <th>Bill Date</th>
                                                            <th>Bill Amount</th>
                                                            <th>Bill Received Date</th>
                                                            <th>DOP</th>
                                                            <th>NOD</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody  class="add_rows">
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                        <div class="row">
                                    <div class="form-group div-wid-7">
                                        <label for="username">Number's: </label>
                                        <input type="text" name="billNo" id='billNo' 
                                               class="form-control input-sm"
                                               maxlength="40"/>
                                        <label id="errorPurchaseUnit1" style="color: red"></label>
                                    </div>
                                    <div class="form-group div-wid-7">
                                        <label for="username">Number Of Days: </label>
                                        <input type="text" name="billNo" id='billNo' 
                                               class="form-control input-sm"
                                               maxlength="40"/>
                                        <label id="errorPurchaseUnit1" style="color: red"></label>
                                    </div>
                                    <div class="form-group div-wid-7">
                                        <label for="username">Average: </label>
                                        <input type="text" name="billNo" id='billNo' 
                                               class="form-control input-sm"
                                               maxlength="40"/>
                                        <label id="errorPurchaseUnit1" style="color: red"></label>
                                    </div>
                                </div>
                                    </section>
                                </div>
                                
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END :  BILL REGISTER UPDATE SCREEN DETAIL(S)************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">                                    
                                    <input type="button" value="Save" class="btn btn-info" id="saveNilVoucher"/> 

                                    <input type="button" value="Cancel" class="btn btn-info" id="cancelNilVoucher" />
                                </span>
                            </div>
                        </div> 
                    </div>
                </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/account/PrisReportJs.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js//bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.d24 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>