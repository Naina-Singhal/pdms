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
                <c:if test="${(userPermiLi.d1 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .one-left{               
                width: 50%;                
                float: left;
                border-right: 1px solid rgb(229, 229, 229);
            }
            .one_right{                
                width: 50%;
                overflow: hidden;
                
            }
            .side-by-side{
                overflow: hidden;
            }
            
             /* start item scrolling */
            .items_table {
                width: 100%;
                border-collapse: collapse;
                border-spacing: 0;
                
            }

            .items_head, .items_body, .items_table tr, .items_table td, .items_table th { display: block; }

            .items_table tr:after {
                content: ' ';
                display: block;
                visibility: hidden;
                clear: both;
            }

            .items_head th { 
                height: 30px;
                line-height: 30px;
                /*text-align: left;*/
            }

            .items_body {
                width: 100.4%;
                height: 130px;
                overflow-y: overlay;
            }

            .items_head {
                /* fallback */
                width: 100.4%;
                /* minus scroll bar width 
                width: calc(100% - 17px);*/
            }

            

            .items_body td:nth-child(1), .items_head th:nth-child(1) {
                width:7%;
                float: left;
                
            }
            .items_body td:nth-child(2), .items_head th:nth-child(2) {
                width:13%;
                float: left;
                
            }
            .items_body td:nth-child(3), .items_head th:nth-child(3) {
                width:40%;
                float: left;
                
            }
            .items_body td:nth-child(4), .items_head th:nth-child(4) {
                width:40%;
                float: left;
                
            }

            .items_body td:last-child, .items_head th:last-child {
                border-right: none;
            }
            .items_body td:last-child{
                text-align: left !important;
            }
            
            /* end item scrolling */
            
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .third-tab td{
                padding: 0px !important;
                text-align: center;
            }
            #tabSelect{
                padding: 5px;
                border: none;
                width: 100%;
            }
            #table_sele2 {
                width: 100px !important;
            }
            
            .hoaTab tbody td{
                padding:  0px !important;
            }
            .hoaTab tbody td:nth-child(2) {
                width: 10%;
            }
            .cbmt{
                height: 30px;
                font-size: 16px;
                font-weight: 800;
            }
            .cmt{
                font-size: 16px;
                font-weight: 800;
            }
            .amt{
                font-size: 16px;
                font-weight: 800;
            }
            .tott{
                font-size: 16px;
                font-weight: 800;
                text-align: center;
            }
            .tab2CheckBox{
                margin-left: 10px !important;
                margin-top: 10px !important;
            }
            
            .painputB {
                width: 100%;
                height: 22px;
                border: none;
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
                        <h2>Voucher Data Entry(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Voucher Data Entry(s)</a>
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
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form  id="VoucherDaEnForm">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Voucher Data Entry(s)</strong> Details</h1>                                            
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d14 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Voucher Data Entry(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body"> 
                                            <input type="hidden" id="voucherID" name="voucherID" value="" />
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File Number<u>:</u> </label>
                                                    <input type="text" name="fileNumber" id='fileNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorFileNumber" style="color: red"></label>
                                                </div>
                                            </div>    
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>                                                    
                                                    <select name="poNumber" id="poNumber" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorpoNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO SR (File No): </label>
                                                    <input type="text" name="posrFileNo" id='posrFileNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor: </label>                                                    
                                                    <select name="nameOfSupplr" id="nameOfSupplr" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Gcode: </label>
                                                    <input type="text" name="gcode" id='gcode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File Closed: </label>                                                    
                                                    <select name="fileClosed" id="fileClosed" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="yes">yes</option>
                                                        <option value="no">No</option>                                                        
                                                    </select>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Mode Of Payment: </label>
                                                    <select name="modeOfPaymt" id="modeOfPaymt" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="A">Advance</option>
                                                        <option value="B">Balance</option>
                                                        <option value="N">Normal</option>
                                                    </select>
                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">(a)/(b)/c: </label>
                                                    <select name="abc" id="abc" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="A">Direct</option>
                                                        <option value="B">Bank</option>
                                                        <option value="C">Cheque</option>
                                                    </select>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">(b)/(s): </label>                                                    
                                                    <select name="bbys" id="bbys" class="form-control input-sm">
                                                        <option value="null">select</option>                                                        
                                                        <option value="S">SBI</option>                                                        
                                                    </select>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section Code: </label>                                                    
                                                    <select name="scode" id="scode" class="form-control input-sm">
                                                        <option value="null">select</option>                    
                                                    </select>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                            </div> 
                                            <div class="row"> 
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">rpum/mngr: </label>
                                                    <select name="rpumOrmngr" id="rpumOrmngr" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="rpum">RPUM</option>
                                                        <option value="mngr">MNGR</option>                                                        
                                                    </select>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">CQl/DD/R: </label>
                                                    <select name="cqeDdR" id="cqeDdR" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="C">Cheque</option>
                                                        <option value="D">Demand Draft</option>
                                                        <option value="R">RTGS / NEFT</option>
                                                    </select>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PPR No: </label>
                                                    <input type="text" name="pprNo" id='pprNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Bank LR/C: </label>
                                                    <select name="bankLRC" id="bankLRC" class="form-control input-sm">
                                                        <option value="null" selected="selected">select</option>
                                                        <option value="B">Bank</option>
                                                        <option value="C">Cheque Requision</option>
                                                        <option value="L">Against LR</option>
                                                    </select>
                                                    <label id="errorBanklrc" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LR No: </label>
                                                    <input type="text" name="lrNo" id='lrNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>                                                  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LR DT: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="lrDT" id="lrDT" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                            </div> 
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">IBC No: </label>
                                                    <input type="text" name="ibcNo" id='ibcNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Quantity: </label>
                                                    <input type="text" name="quantity" id='quantity' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">BG clause: </label>
                                                    <input type="text" name="bgClause" id='bgClause' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PPR No 2: </label>
                                                    <input type="text" name="pprNo2" id='pprNo2' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Amount Paid: </label>
                                                    <input type="text" name="amountPaid" id='amountPaid' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date Of Payment: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dateOfpmt" id="dateOfpmt" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorRemarks" style="color: red"></label>
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
                        <%-- ************ START : TABLES OF BILL AND RV ************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="billRvDivSub">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile side-by-side">
                                        <div class="one-left">
                                            <div class="tile-header dvd dvd-btm">
                                                <h1 class="custom-font"><strong>Bill</strong> List</h1>
                                            </div>
                                            <div class="tile-body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered items_table" id="" >
                                                    <thead class="items_head">
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>Bill No</th>
                                                            <th>Bill Date</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="appendList" class="items_body">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        </div>
                                        <div class="one_right">
                                            <div class="tile-header dvd dvd-btm">
                                                <h1 class="custom-font"><strong>RV</strong> List</h1>
                                            </div>
                                            <div class="tile-body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered rvtable items_table" id="" >
                                                    <thead class="items_head">
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>                                                            
                                                            <th>RV No</th>
                                                            <th>RV Date</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_rv_list" class="items_body">
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : TABLES OF BILL AND RV ***************** --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : BILL ENTRY ITEMS LIST DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="itemsDivSub">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Items</strong> List</h1>
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
                                                <table class="table table-bordered third-tab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>S No </th>                                                            
                                                            <th>Rate</th>
                                                            <th>IGST</th>
                                                            <th>CGST</th>                                                            
                                                            <th>SGST</th>
                                                            <th>HSN Code</th>
                                                            <th>G/S</th>
                                                            <th>Qty Received</th>                                                            
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
                        <%-- ************ END : BILL ENTRY ITEMS LIST DETAIL(S)************** --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- **** START : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)********** --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Items</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" id="hoaDivSub">
                                                <table class="table table-bordered hoaTab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>H A/C </th>
                                                            <th>Short code</th>                                                            
                                                            <th>CB8670</th>
                                                            <th>Debit-Amount</th>
                                                            <th>Credit-Amount</th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_hoa">
                                                    </tbody>
                                                    <tbody id="stic_hoaTot">
                                                    </tbody>
                                                </table>
                                            </div>
                                            <form id="VoucherDaEnSeForm">                                            
                                            <div class="row row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Recoveries: </label>                                                    
                                                    <input type="text" name="recoveries" id='recoveries' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Difference: </label>
                                                    <input type="text" name="defference" id='defference' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Rs In Words: </label>
                                                    <input type="text" name="rsInWords" id='rsInWords' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                            </div>
                                                <div class="row row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">COMP Code: </label>                                                    
                                                    <input type="text" name="compCode" id='compCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            </form> 
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)**** --%>
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
                                        <h1 class="custom-font"><strong>Voucher Data Entry(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d1 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">Voucher Data Entry(s) Form
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
        <script src="<c:url value="/appjs/vocherDataEnJS.js"/>" type="text/javascript"></script> 
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
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
    <c:if test="${(userPermiLi.d1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>