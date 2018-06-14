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
                <c:if test="${(userPermiLi.b1 == 1)}">   
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Purchase Order Entry(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .div-wid-7{
                width: 31%;
                margin-left: 15px;
                float: left;
            }            
            
            .textareaPOE{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 100%;
            }
            #details{
                border: 1px solid rgba(128, 128, 128, 0.57);
                border-radius: 3px;
                background-color: rgba(128, 128, 128, 0.08);
                padding: 3px;
                min-height: 30px;
            }
            
            .ineer-model{
                    border: 1px solid rgba(128, 128, 128, 0.4);
                    width: 96%;
                    min-height: 50px;
                    padding: 7px;
            }
            .modal-header {                
                margin-bottom: 10px !important;
            }
            .modal-backdrop.in{
                opacity: -0.1 !important;
            }
            .modal{
                right: 0px !important;
                left: inherit !important;;
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
                        <h2>Purchase Order Entry(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Purchase Order Entry(s)</a>
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
                            <spring:form  id="POentryForm">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                           
                                            <h1 class="custom-font"><strong>Purchase Order</strong> Entry</h1>                                            
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.b2 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">PO Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="poEntryID" name="poEntryID" value=""/>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red;"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Tender File Number: </label>
                                                    <input type="text" name="tenderFN" id='tenderFN'  value=""
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorTenderFN" style="color: red;"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor: </label> 
                                                    <select name="venderName" id="venderName" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">                                                        
                                                    </select>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Details: </label>                                                    
                                                    <div id="details">
                                                        
                                                    </div>
                                                </div>
                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO SR Number: </label>
                                                    <input type="text" name="quotationNo" id='quotationNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Reference Number: </label>
                                                    <input type="text" name="referenceNo" id='referenceNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Description: </label>                                                     
                                                    <textarea type="text" name="venDescription" id='venDescription' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>                                                                                                
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>
                                                    <input type="text" name="poNumber" id='poNumber' value="" 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="poDate" id="poDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Price: </label>
                                                    <input type="text" name="price" id='price' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>                                        
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Payment: </label> 
                                                    <textarea type="text" name="payment" id='payment' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Inspection: </label> 
                                                    <input type="text" name="inspection" id='inspection' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Value: </label>
                                                    <input type="text" name="poValue" id='poValue' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>                                        
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Place Of Delivery: </label>
                                                    <input type="text" name="placeOfDelivery" id='placeOfDelivery' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery Period: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="deliveryPeriod" id="deliveryPeriod" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Signatory: </label>                                                    
                                                    <select name="signatory" id="signatory" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                        
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Prepared Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="preparedDate" id="preparedDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Prepared By: </label>
                                                    <input type="text" name="preparedBy" id='preparedBy' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Security Deposit: </label>                                                    
                                                    <input type="text" name="securityDeposit" id='securityDeposit' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PBG: </label>
                                                    <input type="text" name="pBG" id='pBG' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">Offer: </label>
                                                    <textarea type="text" name="offer" id='offer' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Terms: </label>
                                                    <input type="text" name="specED" id='specED' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">ADD.S.T: </label>
                                                    <input type="text" name="addST" id='addST' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Freight: </label>
                                                    <input type="text" name="freight" id='freight' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Others: </label>
                                                    <input type="text" name="others" id='others' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
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
                                        <h1 class="custom-font"><strong>PO Entry(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.b1 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">PO Entry Form
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
               
                    <div class="modal-dialog modal-lg" style="width:35%;float: right;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Templates For Description!</h3>
                            </div>
                            <div class="modal-body">

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
            
                          <!-- == Start :CST Modal Dialog ======== -->
            <div class="modal fade" id="cstModalTax" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:35%;float: right;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Templates For Tax!</h3>
                            </div>
                            <div class="modal-body-tax">

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
            
            <!-- == Start :CST Modal Dialog  for payment======== -->
            <div class="modal fade" id="cstModalPay" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:60%;float: left;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Templates For Payment!</h3>
                            </div>
                            <div class="modal-body-pay">

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
        <script src="<c:url value="/appjs/POEntryJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.b1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>