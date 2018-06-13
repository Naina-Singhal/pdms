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
                <c:if test="${(userPermiLi.c12 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Issue Voucher(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table-tab td{
                padding: 0px !important;
                text-align: center;
            }
            .table-tab td:nth-child(5){
                width: 25%;
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
                        <h2>Issue Voucher(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Issue Voucher(s)</a>
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
                        <%-- ******** START : Issue Voucher DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form id="issue_voucher">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Issue Voucher(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c18 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Issue Voucher(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">  
                                            <input type="hidden" id="id" name="id" value=""/>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">I.V Control Number:</label>                                                    
                                                    <select name="ivControlNo" id="ivControlNo" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errivControlNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addPurchaseDet">
                                                            <span>All Voucher LOV</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addPurchaseDet">
                                                            <span>Previous one year vouchers</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">I.V Control Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="ivControlDate" id="ivControlDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">RCIV Number:</label>
                                                    <input type="text" name="rcivNumber" id='rcivNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">RCIV  Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="rcivDate" id="rcivDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue Type:</label>
                                                    <input type="text" name="issueType" id='issueType' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="section" id="section" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Disposal: </label>
                                                    <input type="text" name="disposal" id='disposal' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery At: </label>
                                                    <input type="text" name="deliveryAt" id='deliveryAt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Item Covered: </label>                                                    
                                                    <textarea type="text" name="itemCovered" id='itemCovered' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Job Allocation: </label>
                                                    <input type="text" name="joAllocation" id='joAllocation' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                            </div>                                                 
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div id="table_formI">
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Items Required</strong> List</h1>
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
                                                <table class="table table-bordered table-tab" id="" style="width:130% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>Group </th>
                                                            <th>Card No </th>
                                                            <th>Item Code</th>
                                                            <th>Unit</th>
                                                            <th>Qty Reqd</th>
                                                            <th>Available</th>
                                                            <th>Qty Issue</th>
                                                            <th>Remarks</th>
                                                            <th>UPD</th>
                                                            <th>Qty bal. after issue </th>
                                                            <th>Qty Short</th>
                                                            <th>W.A Rate</th>
                                                            <th>LIFO Rate</th>
                                                            
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items">

                                                    </tbody>
                                                </table>
                                            </div>
                                            </div>
                                            </div>
                                            <div class="tile-body">
                                            <spring:form id="issue_voucher_sec">
                                            <div class="row  row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Store Group Code: </label>
                                                    <input type="text" name="storeGroupCode" id='storeGroupCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Card Number: </label>
                                                    <input type="text" name="cardNumber" id='cardNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Card Code: </label>
                                                    <input type="text" name="cardCode" id='cardCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Up To Date Balance:</label>
                                                    <input type="text" name="upToDateBal" id='upToDateBal' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Describe:</label>
                                                    <input type="text" name="describe" id='describe' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Part Number: </label>
                                                    <input type="text" name="partNumber" id='partNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Minimum Level: </label>
                                                    <input type="text" name="minLevel" id='minLevel' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">M.L Reached Dat: </label>
                                                    <input type="text" name="mlReachedDat" id='mlReachedDat' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="date" id="date" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                         
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Qty Last Issue: </label>
                                                    <input type="text" name="qtyLaIssue" id='qtyLaIssue' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Basic Rate: </label>
                                                    <input type="text" name="basicRate" id='basicRate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Rate Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="rateDate" id="rateDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">W.A Rate: </label>
                                                    <input type="text" name="waRate" id='waRate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Rate: </label>
                                                    <input type="text" name="rate" id='rate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Currency Code: </label>
                                                    <input type="text" name="currencyCode" id='currencyCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Received By: </label>
                                                    <input type="text" name="receivedBy" id='receivedBy' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                    </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Surplus Qty: </label>
                                                    <input type="text" name="surplusQty" id='surplusQty' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LIFO Rate: </label>
                                                    <input type="text" name="lifoRate" id='lifoRate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indenter Name: </label>                                                    
                                                    <select name="indentorName" id="indentorName" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div> 
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">Issued By Name: </label>
                                                    <input type="text" name="issuedByName" id='issuedByName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized By Name: </label>                                                   
                                                    <select name="authorByName" id="authorByName" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized By Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="authorByDate" id="authorByDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            </div>
                                        </div>
                                        <!-- /tile body -->
                                        </div>
                                    </spring:form>
                            
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : Issue Voucher DETAIL(S)******************** --%>
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
                                        <h1 class="custom-font"><strong>Issue Voucher(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c12 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">Issue Voucher(s) Form
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
                        
                            <!-- == Start :CST Modal Dialog  for payment======== -->
            <div class="modal fade" id="cstModalItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:40%;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Template For Item Covered!</h3>
                            </div>
                            <div class="modal-bodyItem">

                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button> 
                            </div>
                        </div>
                    </div>
               
            </div>
            <!-- == End :CST Modal Dialog ======== --> 
                </div>
          
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/IssueVocherEntryJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.c12 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>