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
                <c:if test="${(userPermiLi.b6 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : File Status(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
           .textareaPOE{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 100%;
            }
            .tab-two td:nth-child(6){
                font-size: 10px;
                width: 27%;

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
                        <h2>File Status(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">File Status(s)</a>
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
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form  id="Purchase Status_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>File Status(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d15 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openPurcStaRe();">Purchase Status Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File Number <u>:</u> </label>                                                    
                                                    <input type="text" name="fileNumber" id='fileNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorFileNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Group: </label>                                                    
                                                    <input type="text" name="group" id='group' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indent No: </label>
                                                    <input type="text" name="indentNumber" id='indentNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indent Date: </label>
                                                    <input type="text" name="indentDate" id='indentDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Mode of Purchase: </label>                                                     
                                                    <input type="text" name="modeOfPurchase" id='modeOfPurchase' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Received On: </label>
                                                    <input type="text" name="receivedDate" id='receivedDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div> 
                                            </div>                                             
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Category Name: </label>
                                                    <select name="itemCode" id="itemCode" class="form-control input-sm">                                                        
                                                    </select>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div> 
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">Estimated Cost: </label>
                                                    <input type="text" name="estimatedCost " id='estimatedCost' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                                    
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section Code: </label>
                                                    <input type="text" name="sectionCode" id='sectionCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div> 
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DGM Group: </label>                                                   
                                                   <select name="dgmGroup" id="dgmGroup" class="form-control input-sm">                                                        
                                                    </select>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indenter: </label>
                                                    <select name="indenter" id="indenter" class="form-control input-sm">                                                        
                                                    </select>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Description: </label>
                                                    <input type="text" name="description" id='description' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly="true"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                           </div> 
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <textarea type="text" name="remarks" id='remarks' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Status: </label>
                                                   <input type="text" name="Status" id='Status' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
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
                        <%-- ************ START : LIST DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Enquiry Table</strong> List</h1>
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
                                                <table class="table table-bordered items_table" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>
                                                            <th>Enquiry Date</th>
                                                            <th>Due Date</th>                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                </tr>
                                                         </tbody>
                                                 </table>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END :  LIST DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                      
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : PURCHASE ORDER DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>CST</strong> List</h1>
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
                                                <table class="table table-bordered tab-two" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>                                                            
                                                            <th>PO NO</th>
                                                            <th>Date</th>
                                                            <th>Prepare Date</th>
                                                            <th>Vendor Name</th>
                                                            <th>Vendor Address</th>
                                                            <th>Value</th>
                                                            <th>Delivery Period</th>
                                                            
                                                          </tr>
                                                    </thead>
                                                    <tbody id="stic_Po_data">
                                                    
                                                   </tbody>
                                                 </table>
                                            </div>
                                          </div>                                                                                                                                                
                                    </section>
                                </div>
                            </div>
                                    
                        <%-- **************************************************************** --%>
                        <%-- ************ END : PURCHASE ORDER DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : FILE MOVEMENT DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>FILE MOVEMENT</strong> List</h1>
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
                                                <table class="table table-bordered items_table" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>
                                                            <th>Sent Date</th>
                                                            <th>Sent To/Purpose</th>
                                                            <th>Received Date</th>
                                                          </tr>
                                                    </thead>
                                                    <tbody>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
                                                                </tr>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>                                                                       
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
                        <%-- ************ END : FILE MOVEMENT DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : DAK DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>DAK DETAILS</strong> List</h1>
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
                                                <table class="table table-bordered items_table" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>
                                                            <th>Recived On</th>
                                                            <th>PO No</th>
                                                            <th>Vendor Name</th>
                                                            <th>Vendor Address</th>                                                           
                                                          </tr>
                                                    </thead>
                                                    <tbody>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
                                                                        <td></td>
                                                                       
                                                                    </tr>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
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
                        <%-- ************ END : DAK DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : FILE MOVEMENT DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>PO Details</strong> List</h1>
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
                                                <table class="table table-bordered items_table" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>
                                                            <th>GROUP</th>
                                                            <th>FILE NO</th>
                                                            <th>PO SR</th>
                                                            <th>PO</th>
                                                            <th>PO DATE</th>
                                                            <th>Brief Description</th>
                                                            <th>AMOUNT</th>                                                     
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                         <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
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
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : FILE MOVEMENT DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                         <%-- **************************************************************** --%>
                        <%-- ************ START : DESPATCH DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>DESPATCH DETAILS</strong> List</h1>
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
                                                <table class="table table-bordered items_table" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>
                                                            <th>Despatch no</th>
                                                            <th>PO No</th>
                                                            <th>Type</th>
                                                            <th>Vendor Name</th>
                                                            <th>Vendor Address</th>
                                                            <th>Despatch Date</th>
                                                            
                                                          </tr>
                                                    </thead>
                                                    <tbody>
                                                                <tr class="gradeX">
                                                                        <td> </td>
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
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>  
                                                                        <td></td>
                                                         </tr>
                                                   </tbody>
                                                 </table>
                                            </div>
                                          </div>
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : DESPATCH DETAIL(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                                         
                         <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right"> 

                                    <input type="button" value="Save" class="btn btn-info" id="save"/> 

                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelEdit" />
                                </span>
                            </div>
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
        <script src="<c:url value="/appjs/purchase/FileStatusJs.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.b6 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>