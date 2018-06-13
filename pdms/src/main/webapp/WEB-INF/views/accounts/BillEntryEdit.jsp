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
                <c:if test="${(userPermiLi.d13 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Bill Entry Screen Update(s) List</title>
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
                        <h2>Bill Entry Screen Update(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Bill Entry Screen Update(s)</a>
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
                        <div class="row">
                            <spring:form  id="Bill_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Bill Entry Screen Update(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d16 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openBillRe();">Bill Data Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <input type="hidden" name="billId" id="billId" value="${encBill_ID}"/>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>
                                                    <input type="text" name="poNumber" id='poNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Name: </label>                                                    
                                                    <select name="supplierName" id="supplierName" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Payment Terms: </label>                                                    
                                                    <textarea type="text" name="paymentTerm" id='paymentTerm' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Bill Number: </label>
                                                    <input type="text" name="billNo" id='billNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Bill Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="billDate" id="billDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DC Number: </label>
                                                    <input type="text" name="dcNo" id='dcNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Bill Amount: </label>
                                                   <input type="text" name="billAmt" id='billAmt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">LR Enclosed: </label>                                                    
                                                    <select name="lrEnclosed" id="lrEnclosed" class="form-control input-sm">
                                                        <option value="null">select</option>
                                                        <option value="Yes">Yes</option>
                                                        <option value="No">No</option>                                                        
                                                    </select>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">GSTIN Number: </label>                                                    
                                                    <select name="gstinNo" id="gstinNo" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>                                                                                                             
                                                    </select>
                                                    <label id="errorPoDate" style="color: red"></label>
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
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Bill</strong> List</h1>
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
                                                            <th>S No </th>
                                                            <th>Bill No</th>
                                                            <th>Bill Date</th>
                                                            <th>Bill Amount</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="gradeX">
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
                        <%-- ************ END : BILL  ITEMS LIST DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">                                    
                                    <input type="button" value="Update" class="btn btn-info" id="updateBillEntry"/> 

                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelBillEntry" />
                                </span>
                            </div>
                        </div> 
                    </div>
                </div>
           </section>   
                        <!-- == Start :CST Modal Dialog ======== -->
            <div class="modal fade" id="cstModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:40%;float: right;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Payment Terms!</h3>
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
        <script src="<c:url value="/appjs/account/BillEntryEditJS.js"/>" type="text/javascript"></script>        
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            
        </script>        
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.d13 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>