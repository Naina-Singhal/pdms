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
                <c:if test="${(userPermiLi.d3 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
           
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
                        <h2>Voucher Update(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Voucher Update(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <div class="display_msg_success">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error">
                            <label id="errorDupItem" class="label_error_msg"></label>
                        </div>
                    </div>
                    <div class="pagecontent">
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        <c:if test="${msg != null}">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="alert alert-success alert-dismissable">
                                        <button type="button" class="close" data-dismiss="alert" 
                                                aria-hidden="true">&times;</button>
                                        <strong>Success!</strong>${msg}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${ermsg != null}">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="alert alert-warning alert-dismissable">
                                        <button type="button" class="close" data-dismiss="alert" 
                                                aria-hidden="true">&times;</button>
                                        <strong>Error!</strong>${ermsg}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12" style="margin-left: 20px;width: 96.5%;">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Voucher Update</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Enter CC Number: </label>                                                    
                                                    <input type="text" name="dbNumber" id='dbNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Enter V_NO: </label>
                                                    <input type="text" name="dbNumber" id='dbNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>PO No </th>
                                                            <th>V No</th>
                                                            <th>V.dt</th>
                                                            <th>Sec Code</th>
                                                            <th>HAC</th>
                                                            <th>Debit</th>
                                                            <th>Credit</th>
                                                            <th>C&B</th>
                                                            <th>S Code</th>
                                                            
                                                           
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                      
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
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
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        
                                                                        
                                                                    </tr>
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td>Total:</td>
                                                                        <td  style="padding: 0px;width: 150px;"><input type="text" class="tot_input"/></div></td>
                                                                        <td style="padding: 0px;width: 150px;"><input type="text" class="tot_input"/></td>
                                                                        <td style="padding: 0px;width: 150px;"><input type="text" class="tot_input"/></td>
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
                        <%-- ****************** END : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                         
                        <%-- **************************************************************** --%>
                        <%-- ************ START : Cheque Register DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12" style="margin-left: 20px;width: 96.5%;">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Cheque Register</strong> List</h1>
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
                                                            <th>PO No </th>
                                                            <th>V No</th>
                                                            <th>V.DT</th>
                                                            <th>Cheque No</th>
                                                            <th>New Cheque No</th>
                                                            <th>CQE_DT</th>
                                                            <th>C&B</th>
                                                            <th>Tag</th>
                                                            <th>Tr</th>
                                                            <th>Words</th>
                                                            <th>Recoveries</th>
                                                            
                                                           
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                      
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
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
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        
                                                                    </tr>
                                                                    <tr class="gradeX">
                                                                        <td> </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td>Total:</td>
                                                                        <td  style="padding: 0px;width: 150px;"><input type="text" class="tot_input"/></div></td>
                                                                        <td style="padding: 0px;width: 150px;"><input type="text" class="tot_input"/></td>
                                                                        <td style="padding: 0px;width: 150px;"><input type="text" class="tot_input"/></td>
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
                        <%-- ****************** END : Cheque Register DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        
                        <div class="row">
                            <div class="col-md-12" style="width: 98.2%;">
                                <span class="tools pull-right">
                                    <c:choose>
                                        <c:when test="${indentStat != null}">
                                            <input type="button" value="Update" class="btn btn-info" id="updateIndent"/> 
                                        </c:when>
                                        <c:otherwise>
                                            <input type="button" value="Save" class="btn btn-info" id="saveInspeClearance"/> 
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelEdit" />
                                </span>
                            </div>
                        </div> 
                    </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/VoucherUpdateJS.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js//bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            $(document).ready(function() {
                //$("#categoryID").searchable();
               
            });
        </script>
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.d3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>