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
                <c:if test="${(userPermiLi.p2 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Inward(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .firstTab td:nth-child(1){
                width: 6%;
            }
            .firstTab td:nth-child(2){
                width: 6%;
            }
            .firstTab td:nth-child(3){
                width: 28%;
            }
            .firstTab td:nth-child(4){
                width: 40%;
            }
            .firstTab td:nth-child(5){
                width: 20%;
            }
            .secondTab td:nth-child(1){
                width: 6%;
            }
            .secondTab td:nth-child(2){
                width: 10%;
            }
            .secondTab td:nth-child(3){
                width: 10%;
            }
            .secondTab td:nth-child(3){
                width: 14%;
            }
            .secondTab td:nth-child(5){
                width: 30%;
            }
            .secondTab td:nth-child(6){
                width: 30%;
            }
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .secondTab td{
                padding: 0px !important;
                text-align: center;
            }
            .firstTab td{
                padding: 0px !important;
                text-align: center;
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
                        <h2>Inward(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Inward(s)</a>
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
                        <%-- ******** START : csrv Preparation  DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form  id="inward_form" >
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Inward(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c13 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Inward Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="inwardId" name="inwardId" value=""/>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">SL No : </label>
                                                    <input type="text" name="slno" id='slno' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File No : </label>                                                    
                                                    <select name="fileNumber" id="fileNumber" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorFileNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Type : </label>                                                    
                                                    <select name="type" id="type" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Receive Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="receiveDate" id="receiveDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ******** END : csrv Preparation  DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)*************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="table_one">
                                
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Enquiry Items</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-y: scroll;max-height:300px;">
                                                <table class="table table-bordered firstTab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th> 
                                                            <th>Sl No</th>
                                                            <th>Item</th>
                                                            <th>Vendor Name</th>
                                                            <th>Vendor City</th>                                                                                                                                                                                  
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items_two">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                               
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)*********** --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="table_two">
                                
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Purchase Items</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-y: scroll;max-height:300px;">
                                                <table class="table table-bordered secondTab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th> </th>
                                                            <th>Sl No</th>
                                                            <th>PO Number</th>
                                                            <th>POSR</th> 
                                                            <th>Vendor Name</th>
                                                            <th>Vendor City</th> 
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
                               
                            </div>
                        
                       
                        
                        <div class="row">
                            <div class="col-md-12">

                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="saveDdNumber">
                                        <span>Save</span>
                                    </button>                                
                                </span>
                                <span class="tools pull-right">
                                    <div id="updateDiv" style="display: none">                                    
                                        <button class="btn btn-info" type="button"
                                                id="updateDdNumber" >
                                            <span>Update</span>
                                        </button>

                                        <button class="btn btn-info" type="button"
                                                id="cancelDdNumber">
                                            <span>Cancel</span>
                                        </button>                                   
                                    </div>
                                </span>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)*********** --%>
                        <%-- **************************************************************** --%>
                        
                        </div>
                        
                        <%-- **************************************************************** --%>
                        <%-- **********START : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="form_record">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Inward(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">Inward Form
                                                    </button> 
                                                </c:if>
                                            </div>
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
                        <%-- *********** END : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                    </div>
                </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/despatch/InwardEnJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.p2 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>

   