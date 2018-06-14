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
                <c:if test="${(userPermiLi.b7 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Vendor Performance(s) List</title>
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
                        <h2>Vendor Performance(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Vendor Performance(s)</a>
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
                        <%-- ************ START : VENDOR LIST DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong></strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Name: </label>
                                                    <input type="text" name="VendorName" id='vendorname' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Vendor Address: </label>
                                                    <input type="text" name="VendorAddress" id='vendoraddress' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-bordered items_table" id="basic-usage" style="width:100% !important">
                                                    <thead class="items_head">
                                                        <tr>                                                            
                                                            <th>S No </th>
                                                            <th>Vendor Name</th>
                                                            <th>Vendor Address</th>
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
                        <%-- ************ END : VENDOR LIST DETAIL(S)******************* --%>
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
                                            <h1 class="custom-font"><strong>ENQUIRES</strong> List</h1>
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
                                                            <th>Brief Description</th>
                                                            <th>ENQUIRY DATE</th>
                                                            <th>Due Date</th>                                                   
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
                                                                 </tr>
                                                                <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td> </td>
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
                        <%-- ************ START : FILE MOVEMENT DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>RESPONSES</strong> List</h1>
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
                                                            <th>Brief Description</th>
                                                            <th>Due Date</th>                                                        
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr class="gradeX">
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
                        
                        
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">                                    
                                    <input type="button" value="Save" class="btn btn-info" id="saveBillEntry"/> 

                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelBillEntry" />
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
        <script src="<c:url value="/appjs/purchase/VendorPerformanceJs.js"/>" type="text/javascript"></script>        
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            
        </script>        
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.b7 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>