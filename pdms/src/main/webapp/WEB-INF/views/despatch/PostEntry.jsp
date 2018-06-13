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
                <c:if test="${(userPermiLi.p3 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Post Entry</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .firstTab td:nth-child(1){
                width: 5%;
            }
            .firstTab td:nth-child(2){
                width: 10%;
            }
            .firstTab td:nth-child(3){
                width: 30%;
            }
            .firstTab td:nth-child(4){
                width: 30%;
            }
            .firstTab td:nth-child(5){
                width: 25%;
            }
            .secondTab td:nth-child(1){
                width: 5%;
            }
            .secondTab td:nth-child(2), td:nth-child(3), td:nth-child(4){
                width: 10%;
            }            
            .secondTab td:nth-child(5){
                width: 25%;
            }
            .secondTab td:nth-child(6){
                width: 25%;
            }
            .secondTab td:nth-child(7){
                width: 15%;
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
                        <h2>Post Entry <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Post Entry </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <div class="display_msg_success_Ma">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error_Ma">
                            <label id="errorDupItem" class="label_error_msg"></label>
                        </div>
                    </div>
                    <div class="pagecontent">
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form  id="Post_entry_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Post Entry</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Post Entry Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body"> 
                                             <input type="hidden" id="postEntryId" name="postEntryId" value=""/>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Dispatch No : </label>
                                                    <input type="text" name="dispatchNo" id='dispatchNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File No : </label>                                                    
                                                    <select name="fileNo" id="fileNo" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorFileNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Group : </label>
                                                    <input type="text" name="group" id='group' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Nature : </label>
                                                    <select name="nature" id="nature" class="form-control input-sm">
                                                         <option value="null">select</option>
                                                        <option value="UnRegd">UnRegd</option>
                                                        <option value="Regd">Regd</option>
                                                        <option value="SpeedPost">Speed Post</option>
                                                        <option value="Email">Email</option>
                                                    </select>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Amount : </label>                                                    
                                                    <input type="text" name="amount" id='amount' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value="0"/>
                                                    <label id="errorAmount" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Type of Dispatch : </label>                                                   
                                                    <select name="typeOfDispatch" id="typeOfDispatch" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">

                                                    </select>
                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Service Tax : </label>                                                    
                                                    <input type="text" name="serviceTax" id='serviceTax' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value="0"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Total: </label>
                                                    <input type="text" name="total" id='total' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Balance : </label>                                                    
                                                    <input type="text" name="balance" id='balance' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
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
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="table_one">
                                
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Enquiry Items(s)</strong> List</h1>
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
                                                            <th>Amount</th>
                                                             
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
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)*************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="table_two">
                                
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Purchase Items(s)</strong> List</h1>
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
                                                            <th></th>
                                                            <th>Sl No </th>
                                                            <th>PO Number</th>
                                                            <th>POSR</th>                                                             
                                                            <th>Vendor Name</th>
                                                            <th>Vendor City</th> 
                                                            <th>Delivery Date</th>
                                                            
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
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)*********** --%>
                        <%-- **************************************************************** --%>
                        
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
                                        <h1 class="custom-font"><strong>DD Number Entry(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">Post Entry Form
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
        <script src="<c:url value="/appjs/despatch/PostEnJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.p3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>