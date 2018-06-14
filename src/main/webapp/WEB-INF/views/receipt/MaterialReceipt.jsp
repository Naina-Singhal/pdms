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
                <c:if test="${(userPermiLi.c3 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        
        <style>
           
            .div-wid-8{
                width: 23%;
                margin-left: 15px;
                float: left;
            }
            .div-wid-9{
                width: 31%;
                margin-left: 15px;
                float: left;
            }
            .innerRadio{
                margin-top: 30px;
            }
            .innerRadio strong{
                font-weight: 300;
                margin-left: 10px;
            }
            .innerRadio input{
                width: 15px;
                height: 15px;
            }
            .SElect{
                
            }
            .item_table td{
                
            }
            .item_table input{
                width: 110px;
                border: 1px solid #DAD4D4;
                border-radius: 3px;
            }
            .rows_incrementer{
                margin-top: 10px;
                margin-left: 15px;
            }
            .rows_incrementer input{
                width: 210px;
                border: 1px solid #DAD4D4;
                height: 30px;
                border-radius: 2px;
            }
            
            .popup-table{
                border: 1px solid rgba(97, 111, 119, 0.38);
                margin: 20px;
            }
            .popup-table td{
                padding: 5px;
                border: 1px solid rgba(97, 111, 119, 0.38);
                width: 100px;
            }
            .popup-table td:nth-child(1){
                width: 30px;
            }
            .popup-table td:nth-child(5), td:nth-child(5) input{
                width: 170px;
            }
            .painputM{
                border: none;
                width: 100px;
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
            .table-tab td:nth-child(3){
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
                        <h2>Material Receipt <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Material Receipt</a>
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
                            <spring:form  id="materialReForm">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <input type="hidden" name="exisItemCode" id="eItemCode" value=""/>
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <h1 class="custom-font"><strong>Material Receipt</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c4 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Material Receipt Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body" style="padding-top: 0px;"> 
                                            <input type="hidden" id="materialRecID" name="materialRecID" value=""/>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Material Receipt Type:</label>                                                    
                                                    <select name="mateReceiptType" id="mateReceiptType" class="form-control input-sm">
                                                        <option value="null">Select</option>
                                                        <option value="Fresh">Fresh Material Receipt Entry</option>
                                                        <option value="Exist">Exist Material Receipt</option>                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-9">
                                                    <label for="username">Excess Quantity Allowed(%): </label>
                                                    <input type="text" name="excessQuaAllow" id='excessQuaAllow' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Choose One: </label>                                                    
                                                    <select name="lrType" id="lrType" class="form-control input-sm">
                                                        <option value="null">Select</option>
                                                        <option value="LR">LR</option>
                                                        <option value="OTHER">Other</option>                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number : </label>                                                    
                                                    <select name="poLastNo" id="poLastNo" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="0">Select</option>
                                                    </select>
                                                    <label id="errorpoLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DB Number: </label>
                                                    <input type="text" name="dbNumber" id='dbNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
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
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Purchase Unit: </label>
                                                    <input type="text" name="purchaseUnit" id='purchaseUnit' value="RPUM"
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="section" id="section" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Plant: </label>
                                                    <input type="text" name="plant" id='plant' value="MANUGURU" 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="poLNoDate" id="poLNoDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delay Note/Challan Number: </label>
                                                    <input type="text" name="delayRchaNo" id='delayRchaNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="error" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="delayDate" id="delayDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LR / RR Number : </label>
                                                    <input type="text" name="lrORrrNo" id='lrORrrNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LR/RR Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="lrDate" id="lrDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LR Clearance: </label>
                                                    <input type="text" name="lrClearance" id='lrClearance' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Transport Charges: </label>
                                                    <input type="text" name="transportCharges" id='transportCharges' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value=""/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>                                               
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Choose One: </label>                                                    
                                                    <select name="tranChaType" id="tranChaType" class="form-control input-sm">
                                                        <option value="null">Select</option>
                                                        <option value="PS">Paid by supplier(to pay)</option>
                                                        <option value="P">Prepaid(by purchase)</option>
                                                        <option value="T">To be build</option>
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Freight Charges: </label>
                                                    <input type="text" name="packingCharges" id='packingCharges' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Other(dem/whar)Charges: </label>
                                                    <input type="text" name="otherDemCha" id='otherDemCha' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Items Covered: </label>                                                    
                                                    <textarea type="text" name="itemsCovered" id='itemsCovered' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Location In Receipt Section: </label>
                                                    <input type="text" name="locInRecSec" id='locInRecSec' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
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
                                            </div>
                                             <input type="hidden" id="userDbId" name="userDbId" value="${userID}"/>       
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT ITEM DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : despatched items entry LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row" id="form_table">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Despatched Items Entry</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->                                        
                                        <form id="my_form">
                                        <div class="tile-body">
                                           
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab" id="" style="width:120% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>Item code </th>
                                                            <th>Group</th>
                                                            <th>Card no</th>
                                                            <th>Unit</th>
                                                            <th>Order</th>
                                                            <th>Dispatched qty</th>
                                                            <th>Accepted</th>
                                                            <th>Updated qty already regularized</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>                                            
                                        </div>
                                        </form>
                                        
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : despatched items entry LIST DETAIL(S)******************** --%>
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
                                        <h1 class="custom-font"><strong>Material Receipt (s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c3 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">Material Receipt Form
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
                        
                                    <!-- == Start :CST Modal Dialog ======== -->
            <div class="modal fade" id="cstModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:580px;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Templates For LR Clearance!</h3>
                            </div>
                            <div id="modal-bodyM">
                                   
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" id="saveLrClearance" 
                                        style="background-color: #2DA22D;">
                                    <i class="fa fa-arrow-left"></i> Save
                                </button> 
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button> 
                            </div>
                        </div>
                    </div>
               
            </div>
            <!-- == End :CST Modal Dialog ======== --> 
            
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
        <script src="<c:url value="/appjs/MaterialRecJS.js"/>" type="text/javascript"></script>  
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
    <c:if test="${(userPermiLi.c3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>