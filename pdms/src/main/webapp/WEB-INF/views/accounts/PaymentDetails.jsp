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
                <c:if test="${(userPermiLi.d25 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Payment Status(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
           .textareaPOE{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 100%;
            }
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table>tbody>tr>td{               
                vertical-align: middle !important;
                padding-left: 5px;
            }
            .bill-table>tbody>tr>td{
                padding: 1px !important;               
            }
            #tabSelect{
                padding: 5px;
                border: none;
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
                        <h2>Payment Status(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Payment Status(s)</a>
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
                        <div class="row">
                            <spring:form  id="PaymentDetailsForm">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Payment Status(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openPaymentRe();">Payment record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>                                                    
                                                     <input type="text" name="poNumPayDe" id='poNumPayDe' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorpoNumPayDe" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO SR: </label>
                                                    <input type="text" name="posr" id='posr' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File No: </label>
                                                    <input type="text" name="fileNo" id='fileNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Name: </label>                                                    
                                                    <select name="vendorName" id="vendorName" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Address: </label> 
                                                    <textarea type="text" name="vendorAddress" id='vendorAddress' class="textareaPOE" readonly>                                                               
                                                    </textarea>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="poDate" id="poDate" 
                                                               value="" readonly/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dateOfPmt" id="dateOfPmt" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Value: </label>                                                    
                                                    <input type="text" name="poValue" id='poValue' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Closed On: </label>                                                    
                                                   <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dateOfPmt" id="dateOfPmt" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                            </div>  
                                             <div class="row">                                              
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">Head Of Account: </label>                                                    
                                                    <input type="text" name="headOfAccount" id='headOfAcct' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">Payment Terms: </label>
                                                    <textarea type="text" name="paymentTerms" id='paymentTerms' class="textareaPOE" readonly>                                                               
                                                    </textarea>
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
                        <%-- ************ START : TABLES OF BILL AND RV ************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
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
                                                <table class="table table-bordered bill-table" id="basic-usage" >
                                                    <thead>
                                                        <tr>
                                                            <th>Sl No</th>
                                                            <th>Bill No</th>
                                                            <th>Bill Date</th>
                                                            <th>Amount</th>
                                                            <th>Bill Received Date</th>
                                                            <th>STATUS</th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_bill_items">
                                                       
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
                                                <table class="table table-bordered" id="basic-usage" >
                                                    <thead>
                                                        <tr>
                                                            <th>RV No</th>
                                                            <th>RV Date</th>
                                                            <th>Rv RCD</th>
                                                            <th>STATUS</th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="gradeX">
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                        </tr>
                                                        <tr class="gradeX">
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                        </tr>
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : TABLES OF BILL AND RV ******************* --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : CHEQUE PAYMENT  DETAIL ITEMS(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Cheque Payment </strong>Item(s) </h1>
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
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>V No </th>
                                                            <th>DATE</th>
                                                            <th>BILL NO</th>
                                                            <th>RV.No</th>
                                                            <th>CQE.NO</th>
                                                            <th>CQE.DT.</th>
                                                            <th>Amount</th>
                                                            <th>Desp.DT.</th>
                                                             <th>IBC NO</th>
                                                            <th>TAG</th>
                                                            <th>Account No</th>
                                                            <th>Bank</th>
                                                             <th>IFSC Code</th>
                                                            <th>Recovery</th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                      
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td><select name="" class="" id="tabSelect">
                                                                                <option value="C">Cheque</option>
                                                                                <option value="DD">Demand Draft</option>
                                                                                <option value="RTGS">RTGS</option>
                                                                        </select></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                       <td></td>
                                                                    </tr>
                                                                      <tr class="gradeX">
                                                                          <td></td>
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td><td>
                                                                        <td></td>
                                                                        <td><select name="" class="" id="tabSelect">
                                                                                <option value="C">Cheque</option>
                                                                                <option value="DD">Demand Draft</option>
                                                                                <option value="RTGS">RTGS</option>
                                                                        </select></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                                                                                            
                                                                       
                                                                    </tr>
                                                               
                                                      
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                        <div class="row">
                                            <div class="form-group div-wid-7">
                                                    <label for="username">Amount Paid: </label>                                                    
                                                    <input type="text" name="headOfAccount" id='headOfAcct' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                    <%-- **************************************************************** --%>
                    <%-- ************ END : CHEQUE PAYMENT  DETAIL ITEMS(S)************ --%>
                    <%-- **************************************************************** --%> 
                    
                    <%-- **************************************************************** --%>
                    <%-- ************ START : DEMAND DRAFT DETAIL ITEMS(S)*************** --%>
                    <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Demand Draft</strong>Item(s) </h1>
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
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>V No </th>
                                                            <th>V DATE</th>
                                                            <th>BILL NO</th>
                                                            <th>RV.No</th>
                                                            <th>DD.NO</th>
                                                            <th>DD.DT.</th>
                                                            <th>Amount</th>
                                                            <th>DD.FRDD.</th>
                                                            <th>Recovery</th>
                                                         </tr>
                                                    </thead>
                                                    <tbody>
                                                      
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        
                                                                       
                                                                    </tr>
                                                                      <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td><td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        
                                                                       
                                                                    </tr>
                                                               
                                                      
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                <%-- **************************************************************** --%>
                <%-- ************ END : DEMAND DRAFT DETAIL ITEMS(S)*************** --%>
                <%-- **************************************************************** --%>     
                        
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">                                    
                                    <input type="button" value="Save" class="btn btn-info" id="saveNilVoucher"/> 

                                    <input type="button" value="Cancel" class="btn btn-info" id="cancelNilVoucher" />
                                </span>
                            </div>
                        </div> 
                    </div>
                </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/account/PaymentDeJs.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.d25 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>