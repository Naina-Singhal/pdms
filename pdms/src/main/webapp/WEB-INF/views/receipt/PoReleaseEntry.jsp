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
                <c:if test="${(userPermiLi.c1 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
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
            .table-tab td:nth-child(6), td:nth-child(7){
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
                        <h2>PO Release Details(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">PO Release Details(s)</a>
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
                                            <h1 class="custom-font"><strong>PO Release</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c1 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">PO Release Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body" style="padding-top: 0px;">
                                            <spring:form  id="poeForm">    
                                                <div class="row po-type">
                                                    <select name="poReleaseType" id="poReleaseType" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="DPS">DPS</option>
                                                        <option value="HWP">HWP</option>
                                                        <option value="RPUM">RPUM</option>
                                                        <option value="Other">Other</option>
                                                    </select>
                                                    <label id="errpoReleaseType" style="color: red"></label>
                                                </div> 
                                            <div class="row">
                                                <input type="hidden" id="pOrderEntryID" name="pOrderEntryID" value=""/>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">From Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="fromDate" id="fromDate" 
                                                               value=""/>
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
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="toDateErr" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="getPoDetails">
                                                            <span>Search</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                            </div>
                                            <div id="hide_show">
                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>                                                    
                                                    <select name="poNumber" id="poNumber" class="form-control input-sm">                                                        
                                                    </select>
                                                    <label id="errorpoNumber" style="color: red"></label>
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
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="poDate" id="poDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Scheduled Delivery Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="scheDelDate" id="scheDelDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorScheduledDelDate" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indent Ref Number: </label>
                                                    <input type="text" name="indentRefNo" id='indentRefNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indenter: </label>                                                    
                                                    <select name="indentor" id="indentor" class="form-control input-sm">
                                                        
                                                    </select>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="section" id="section" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor: </label>                                                    
                                                    <select name="supplier" id="supplier" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Items Covered: </label>                                                    
                                                    <textarea type="text" name="itemsCovered" id='itemsCovered' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="remarks" id='remarks' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Value: </label> 
                                                    <input type="text" name="poValue" id='poValue' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Value Paid: </label>
                                                    <input type="text" name="poValPaid" id='poValPaid' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Currency Type: </label>                                                    
                                                    <select name="currencyType" id="currencyType" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="indian">Indian Currency</option>
                                                        <option value="us">US Currency</option>
                                                    </select>
                                                    <label id="errorCurrencyCode" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Currency Code: </label>                                                    
                                                    <input type="text" name="currencyCode" id='currencyCode' value="" 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorCurrencyCode" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Head Of Account : </label>                
                                                    <select name="headOfAccount" id="headOfAccount" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorHeadOfAccount" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Place Of Delivery: </label> 
                                                    <input type="text" name="placeOfDel" id='placeOfDel' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPlaceOfDel" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Payment Terms : </label>
                                                    <input type="text" name="paymentTerms" id='paymentTerms' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPaymentTerms" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Paying Authority: </label>
                                                    <input type="text" name="payingAuthority" id='payingAuthority' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPayingAuth" style="color: red"></label>
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
                        <div class="row" id="puitemstale">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Purchase Order Items Entry</strong> List</h1>
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
                                                <table class="table table-bordered table-tab" id="" style="width:150% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th></th>
                                                            <th>Code </th>
                                                            <th>Group Code</th>
                                                            <th>Store Card No</th>
                                                            <th>Item</th>
                                                            <th>Item Description </th>
                                                            <th>unit</th>
                                                            <th>Qty Ordered</th>                                                            
                                                            <th>%Lumpsum</th>
                                                            <th>P&F%</th>
                                                            <th>Excise Duty</th>
                                                            <th>cst/st/vat%</th>
                                                            <th>Surcharge On cst/st/vat</th>
                                                            <th>Turnover Tax%</th>
                                                            <th>Service Tax%</th>
                                                            <th>Item Yet Required</th>
                                                            <th>PO Value</th>
                                                            <th>Discount</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items" class="items_body">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : P.O. Items Entry LIST DETAIL(S)******************** --%>
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
                                        <h1 class="custom-font"><strong>PO Release Entry(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c2 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">PO Release Form
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
        <script src="<c:url value="/appjs/receipt/PODDetEnJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.c1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>