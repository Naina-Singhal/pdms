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
                <c:if test="${(userPermiLi.b3 == 1)}">   
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Purchase Order Details(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .div-wid-7{
                width: 31%;
                margin-left: 15px;
                float: left;
            }
            .div-search-7{
                float: left;
                margin-top: 24px;
            }
            .tableOfPoEntry input{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 120px;
            }
            .tableOfPoEntry td{
                
            }
            .tableOfPoEntry textArea{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 300px;
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
            
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table td{
                padding: 0px !important;
                text-align: center;
            }
            #tabSelect{
                padding: 5px;
                border: none;
            }
            .inspeTabH td:nth-child(2), td:nth-child(2){
                width: 5%;
            }
            .inspeTabH td:nth-child(4){
                width: 20%;
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
                        <h2>Purchase Order Details(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Purchase Order Details(s)</a>
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
                            <spring:form  id="POentryForm">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Purchase Order</strong> Details</h1>
                                            <div class="row">&nbsp;</div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>
                                                    <input type="text" name="poNumInPo" id='poNumInPo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoNum" style="color: red"></label>
                                                </div> 
                                                <input type="hidden" name="fileNo" id="fileNo" class="" value=""/>
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
                        <%-- ***** START : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)********* --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Inspection Clearance ITEMS</strong> List</h1>
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
                                                <table class="table table-bordered inspeTabH" id="basic-usage" style="width:145% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>SL NO </th>
                                                            <th>Indent SlNo</th>
                                                            <th>Detailed Description</th>
                                                            <th>Quantity</th>
                                                            <th>Unit</th>
                                                            <th>Rate</th>
                                                            <th>Discount</th>
                                                            <th>P&F Charges</th>
                                                            <th>Sales Tax</th>
                                                            <th>GST</th>
                                                            <th>Frieght</th>
                                                            <th>Total</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="append_files">
                                                      
                                                    </tbody>
                                                </table>
                                            </div>
                                            <form  id="form_second_part">
                                            <div class="row row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Note Description: </label>
                                                    <textarea type="text" name="noteDes" id='noteDes' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username"></label>
                                                    <input type="hidden" name="desCode" id='desCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40" readonly/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            </form> 
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ********* END : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)******* --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">                                   
                                    <input type="button" value="Save" class="btn btn-info" id="savePoOrderDe"/> 

                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelEdit" />
                                </span>
                            </div>
                        </div>   
                    </div>
                </div>
           </section>                    
        </div>
                        <!-- == Start :CST Modal Dialog ======== -->
            <div class="modal fade" id="cstModalNoDe" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:40%;float: right;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Templates For Note Description!</h3>
                            </div>
                            <div class="modal-body-node">

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
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/purchase/PODetailsEnJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>

        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.b3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>