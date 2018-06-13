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
                <c:if test="${(userPermiLi.d2 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Voucher Number(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
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
            .balanceTab td{
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
                        <h2>Voucher Number(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Voucher Number(s)</a>
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
                        <div >
                        <div class="row" id="form_page">
                            <spring:form  id="VoucherNo_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Voucher Number(s)</strong> Details</h1>                                            
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d15 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Voucher Number(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d15 == 1)}">                                                
                                                    <button type='button' id="show_lc_form"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="">LC Table(s) Entry
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body"> 
                                            <input type="hidden" id="voucherNoId" name="voucherNoId" value=""/>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">COMP Code: </label>                                                    
                                                    <select name="compCode" id="compCode" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Name: </label>                                                    
                                                    <select name="supplierName" id="supplierName" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorVendorName" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO SR: </label>
                                                    <input type="text" name="posr" id='posr' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>
                                                    <input type="text" name="poNumber" id='poNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PPR NO: </label>                                                     
                                                    <input type="text" name="pprNumber" id='pprNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Voucher Number: </label>
                                                    <input type="text" name="voucherNo" id='voucherNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div> 
                                            </div>                                             
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Voucher Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="voucherDate" id="voucherDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>      
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Cheque Number: </label>
                                                    <input type="text" name="chequeNo" id='chequeNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Cheque Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="chequeDate" id="chequeDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Cheque Amount: </label>
                                                    <input type="text" name="chequeAmount" id='chequeAmount' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">cqe/dd/rtgs(c/d/r): </label>
                                                    <select name="cqe_dd_rtgs" id="cqe_dd_rtgs" class="form-control input-sm" >
                                                        <option value="null">select</option>
                                                        <option value="C">Cheque</option>
                                                        <option value="D">DD</option>
                                                        <option value="R">RTGS</option>
                                                    </select>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Bank Code: </label>
                                                    <input type="text" name="bankCode" id='bankCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div> 
                                            </div> 
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Bank: </label>
                                                    <select name="bank" id="bank" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="sbi">SBI</option>
                                                        <option value="icici">ICICI</option>                                                        
                                                    </select>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Number: </label>
                                                    <input type="text" name="req_number" id='req_number' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>      
                                                <div class="form-group div-wid-7">
                                                    <label for="username">L.C Balance Amount: </label>
                                                    <input type="text" name="lcBalanceAmt" id='lcBalanceAmt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">RTGS Number: </label>
                                                    <input type="text" name="rtgsNumber" id='rtgsNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">IBC Number: </label>
                                                    <input type="text" name="ibcNumber" id='ibcNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
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
                        <%-- ************ START : BILL  ITEMS LIST DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="table_one">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Head Of Account(s)</strong> List</h1>
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
                                            
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : BILL  ITEMS LIST DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : BILL  ITEMS LIST DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="table_two">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Item(s)</strong> List</h1>
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
                                                <table class="table table-bordered balanceTab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>S No </th>
                                                            <th>Month</th>
                                                            <th>Opening Balance</th>
                                                            <th>Closing Balance</th>   
                                                            <th>Date Of Entry</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_balance">
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : BILL  ITEMS LIST DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : LC TABLE FORM(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        <div class="row" id="Lc_form_page">
                            <spring:form  id="LC_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>LC Table(s)</strong> Entry</h1>                                           
                                            <div class="open_page_button">
                                            <c:if test="${(userPermiLi.d2 == 1)}">                                                
                                                <button type='button' id="showFormOnLc"
                                                        class="btn btn-info btn-rounded btn-sm"
                                                        onclick="openDDNumberForm();">Voucher Number(s) Form
                                                </button> 
                                            </c:if>
                                        </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body"> 
                                            <input type="hidden" id="lcnumber" name="lcnumber" value=""/>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Month: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="monthlc" id="monthlc" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">OP-Balance: </label>                                                    
                                                    <input type="text" name="opbalance" id='opbalance' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorVendorName" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">a_amount: </label>
                                                    <input type="text" name="aamount" id='aamount' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Total: </label>
                                                    <input type="text" name="total" id='total' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">cm-Amount: </label>                                                     
                                                    <input type="text" name="cmamount" id='cmamount' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">cb-Amount: </label>
                                                    <input type="text" name="cbamount" id='cbamount' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div> 
                                            </div>                                             
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DOE: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dateoe" id="dateoe" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>      
                                                
                                            </div>
                                          
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                                <div class="row">
                            <div class="col-md-12">

                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="save_lc_form">
                                        <span>Save LC </span>
                                    </button>                                
                                </span>
                               
                            </div>
                        </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END :LC TABLE FORM(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <div class="row" id="firstSave">
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
                        <%-- **********START : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="form_record">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Voucher Number Entry(s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.d2 == 1)}">                                                
                                                <button type='button' id="showForm"
                                                        class="btn btn-info btn-rounded btn-sm"
                                                        onclick="openDDNumberForm();">Voucher Number(s) Form
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
        <script src="<c:url value="/appjs/account/VoucherNoEnJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.d2 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>