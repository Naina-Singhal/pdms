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
                <c:if test="${(userPermiLi.d27 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Challan And Cheque List Screen(s)</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .po-type{
                margin-left: 12px !important;
                padding-bottom: 20px;
                padding-top: 10px;
                width: 337px;
            }
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table-tab td{
                padding: 0px !important;
                text-align: center;
            }
           
             .textareaPOE{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 100%;
            }
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
                        <h2>Challan And Cheque List Screen(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Challan And Cheque List Screen(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <%-- START : DISPLAY STATUS MESSAGE--%>
                    <div class="pagecontent">
                        <div class="display_msg_success_Ma">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error_Ma">
                            <label id="errorDupItem" class="label_error_msg"></label>
                        </div>
                    </div>
                    <%-- END : DISPLAY STATUS MESSAGE--%>
                    <div class="pagecontent">
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <input type="hidden" name="exisItemCode" id="eItemCode" value=""/>
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <h1 class="custom-font"><strong>Challan And Cheque List</strong> Details</h1>                                            
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body" style="padding-top: 0px;">
                                            <spring:form  id="challanCheque">    
                                                <div class="row po-type">
                                                    <select name="chooseOption" id="chooseOption" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="challan">Challan</option>
                                                        <option value="cheque">Cheque</option>                                                        
                                                    </select>
                                                    <label id="errChooseOpt" style="color: red"></label>
                                                </div> 
                                            <div class="row">
                                                <input type="hidden" id="ch" name="ch" value=""/>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">From Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="fromDate" id="fromDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="fromDateErr" style="color: red"></label>
                                                </div>
                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">To Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="toDate" id="toDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="toDateErr" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="getRelatedData">
                                                            <span>Search</span>
                                                        </button>
                                                    </div>                                                
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
                        <%-- ************ START : P.O. Items Entry LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="challanShow">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Challan </strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th></th>
                                                            <th>Challan Number </th>
                                                            <th>Date</th>
                                                            <th>Amount</th>
                                                            <th>Received Date</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                                
                                <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="saveChallanList">
                                        <span>Save Challan List</span>
                                    </button>                                
                                </span>                                
                            </div>
                        </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : P.O. Items Entry LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : P.O. Items Entry LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="chequeShow">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Cheque </strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th></th>
                                                            <th>Cheque Number </th>
                                                            <th>Date</th>
                                                            <th>Amount</th>
                                                            <th>Received Date</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="chequeList">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                                <div class="row">
                            <div class="col-md-12">

                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="saveChequeList">
                                        <span>Save Cheque List</span>
                                    </button>                                
                                </span>                                
                            </div>
                        </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : P.O. Items Entry LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        
                        </div>
                        
                        
                    </div>
                </div>
           </section>    
            
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/account/ChallnChequeListJs.js"/>" type="text/javascript"></script>
         <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>

        </script>
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.d27 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>