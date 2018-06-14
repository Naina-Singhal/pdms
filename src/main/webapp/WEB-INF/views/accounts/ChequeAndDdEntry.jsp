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
                <c:if test="${(userPermiLi.d11 == 1)}">                   
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>            
            .editOption{
                width: 90%;                
                position: relative;
                top: -29px;                
                border: 1px;
                padding-left: 5px;
                margin-left: 2px;
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
                        <h2>Cheque Or DD Entry For Challan(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Cheque Or DD  Entry(s)</a>
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
                            <spring:form  id="Cheque_Dd_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <input type="hidden" name="exisItemCode" id="eItemCode" value=""/>
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <h1 class="custom-font"><strong>Cheque Or DD Entry For Challan(s)</strong> Details</h1>                                            
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d20 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Cheque Or DD Entry(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">    
                                            <input type="hidden" id="chequeDdId" name="chequeDdId" value="" />
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Receipt Number: </label>
                                                    <input type="text" name="receiptNo" id='receiptNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
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
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Challan Number: </label>
                                                    <input type="text" name="challanNo" id='challanNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Challan Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="challanDate" id="challanDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Tag: </label>
                                                    <select name="tag" id="tag" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="T">Tender Document</option>  
                                                        <option value="R">Registration For MS</option>  
                                                        <option value="M">Minus Expenditure</option>  
                                                        <option value="EMD">EMD</option> 
                                                        <option value="SD">SD</option> 
                                                        <option value="other">Other</option> 
                                                    </select>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File Number: </label>
                                                    <input type="text" name="fileNo" id='fileNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorfileNo" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DD Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="ddDate" id="ddDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Name: </label>                                                    
                                                    <select name="supplierName" id="supplierName" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>                                                        
                                                    </select>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">month: </label>                                                    
                                                    <div class='input-group datepicker' data-format="MMMM/YYYY">
                                                        <input type='text' class="form-control input-sm"
                                                               name="month" id="month" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                            </div>                                                                                       
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Challan Year: </label> 
                                                     <input type="text" name="challanYear" id='challanYear' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Head Of the Account: </label> 
                                                    <select name="headOfAc" id="headOfAc" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Short Code: </label>                                                    
                                                    <select name="scode" id="scode" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorShortCo" style="color: red"></label>
                                                </div>                                                                                           
                                            </div>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Head: </label>
                                                    <input type="text" name="head" id='head' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>                                                 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Sub Head: </label>                                                    
                                                    <select name="subHead" id="subHead" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="minusDebit">(-)Debit</option>  
                                                        <option class="editable" value="other">other</option>                                                        
                                                    </select>
                                                     <input class="editOption" style="display:none;" placeholder="" />
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">bal. t. documents: </label>
                                                    <input type="text" name="balDocument" id='balDocument' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                            </div>                                             
                                            <div class="row">                                                 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Balance: </label>
                                                    <input type="text" name="balance" id='balance' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DD/Cheque Number: </label>
                                                    <input type="text" name="ddOrChequeNo" id='ddOrChequeNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Amount: </label>
                                                    <input type="text" name="amount" id='amount' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">letter DT: </label>
                                                    <input type="text" name="letterDt" id='letterDt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Details: </label>                                                    
                                                    <select name="details" id="details" class="form-control input-sm">
                                                        <option value="null">select</option>                                                         
                                                        <option value="EMD">EMD</option> 
                                                        <option value="SD">SD</option> 
                                                    </select>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7" id="ShHiSlNo">
                                                    <label for="username">Serial Number: </label>
                                                    <input type="text" name="slNo" id='slNo' value="" 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorSection" style="color: red"></label>
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
                                        <h1 class="custom-font"><strong>Cheque And DD Entry(s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.d11 == 1)}">                                                
                                                <button type='button' id="showForm"
                                                        class="btn btn-info btn-rounded btn-sm"
                                                        onclick="openDDNumberForm();">Cheque And DD(s) Form
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
               
                    <div class="modal-dialog modal-lg" style="width:34.5%;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Templates For Year!</h3>
                            </div>                            
                            <div class="modal-body"></div>
                            
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
        <script src="<c:url value="/appjs/account/CheckAndDdJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.d11 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>