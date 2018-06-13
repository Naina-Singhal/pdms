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
        <c:if test="${(userPermiLi.d10 == 1)}">  
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta name="description" content="">
                    <title>RPUM : User(s) List</title>
                    <jsp:include page="../commons/CommonCSSIncl.jsp"/>
                    <style>
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
                                    <h2>Oils Data Entry(s) <span> Add/ Update User(s)</span></h2>
                                    <div class="page-bar">
                                        <ul class="page-breadcrumb">
                                            <li>
                                                <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                            </li>
                                            <li>
                                                <a href="#">Oils Data Entry(s)</a>
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
                                    <div id="form_page">
                                        <div class="row">
                                            <spring:form  id="oil_form">
                                                <!-- col -->
                                                <div class="col-md-12">
                                                    <!-- tile -->
                                                    <section class="tile">
                                                        <!-- tile header -->
                                                        <div class="tile-header dvd dvd-btm">                                            
                                                            <h1 class="custom-font"><strong>Oils Data Entry(s)</strong> Details</h1>                                            
                                                            <div class="open_page_button">
                                                                <c:if test="${(userPermiLi.d19 == 1)}">                                                
                                                                    <button type='button' id="showRecord"
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="openDDNumberRec();">Oils Data Entry(s) Record
                                                                    </button> 
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <!-- tile header -->
                                                        <!-- tile body -->
                                                        <div class="tile-body">     
                                                            <input type="hidden" id="oilDataId" name="oilDataId" value="" />
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PO Number: </label>
                                                                    <input type="text" name="poNumber" id='poNumber' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPoNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PO SR: </label>
                                                                    <input type="text" name="posr" id='posr' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                                </div>                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Vendor Name: </label>                                                    
                                                                    <select name="supplierName" id="supplierName" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">                                                        
                                                                    </select>
                                                                    <label id="errorDate" style="color: red"></label>
                                                                </div>

                                                            </div>
                                                            <div class="row">                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">B.Description: </label>                                                    
                                                                    <textarea type="text" name="description" id='description' class="textareaPOE">                                                               
                                                                    </textarea>
                                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Bill Number: </label>  
                                                                    <select name="invoiceNo" id="invoiceNo" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">                                                        
                                                                    </select>
                                                                    <label id="errorBillNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Bill Date: </label>
                                                                    <div class='input-group datepicker' data-format="L">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="invoiceDate" id="invoiceDate" 
                                                                               value="" readonly/>
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Invoice Amount: </label>
                                                                    <input type="text" name="invoiceAmt" id='invoiceAmt' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Invoice Qty: </label>                                                    
                                                                    <input type="text" name="invoiceQty" id='invoiceQty' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Rate: </label>
                                                                    <input type="text" name="rate" id='rate' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorPoDate" style="color: red"></label>
                                                                </div>
                                                            </div>                                            
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">State: </label>
                                                                    <input type="text" name="state" id='state' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">SC Discount: </label>
                                                                    <input type="text" name="scDiscount" id='scDiscount' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">ED: </label>
                                                                    <input type="text" name="ed_rate" id='ed_rate' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorIndentor" style="color: red"></label>
                                                                </div>                                                
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">ED Amount: </label>
                                                                    <input type="text" name="edAmount" id='edAmount' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" readonly/>
                                                                    <label id="errorIndentor" style="color: red"></label>  
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">ED cess: </label>
                                                                    <input type="text" name="edCess" id='edCess' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Additional Discount: </label>
                                                                    <input type="text" name="addDiscount" id='addDiscount' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Freight: </label>
                                                                    <input type="text" name="freight" id='freight' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Qty Received </label>
                                                                    <input type="text" name="qtyRecord" id='qtyRecord' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">FC Amount: </label>
                                                                    <input type="text" name="fcAmount" id='fcAmount' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" readonly/>
                                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                                </div>                                                 
                                                            </div> 
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">CST / GST %: </label>
                                                                    <input type="text" name="cst" id='cst' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                                </div> 
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">CST Amount: </label>
                                                                    <input type="text" name="cstAmt" id='cstAmt' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorSection" style="color: red"></label>
                                                                </div>      
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Unit Rate: </label>
                                                                    <input type="text" name="unitRate" id='unitRate' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" readonly/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>                                                
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Total Amount: </label>
                                                                    <input type="text" name="totalAmt" id='totalAmt' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorSection" style="color: red"></label>
                                                                </div>      
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PPR Number: </label>
                                                                    <input type="text" name="pprNo" id='pprNo' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" readonly/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Amount Paid: </label>
                                                                    <input type="text" name="amountPaid" id='amountPaid' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Quantity Received * Rate: </label>
                                                                    <input type="text" name="qtyReceivedRate" id='qtyReceivedRate' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" value="0"/>
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
                                                    <h1 class="custom-font"><strong>Oil Data Entry(s)</strong> List</h1>
                                                    <div class="open_page_button">
                                                        <c:if test="${(userPermiLi.d10 == 1)}">                                                
                                                            <button type='button' id="showForm"
                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                    onclick="openDDNumberForm();">Oil Data(s) Form
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

                            <div class="modal-dialog modal-lg" style="width:40%;float: right;">
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
                    </div>
                    <!-- ====================================================
                    ================= End : Application Content =============
                    ===================================================== -->
                    <jsp:include page="../commons/CommonJSIncl.jsp"/>
                    <script src="<c:url value="/appjs/account/OilsDataEnJS.js"/>" type="text/javascript"></script>
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
        <c:if test="${(userPermiLi.d10 == 0)}"> 
            <% response.sendRedirect("./login");%>
        </c:if>
    </c:forEach>    
</c:if>