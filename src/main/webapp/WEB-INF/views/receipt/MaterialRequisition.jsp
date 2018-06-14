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
                <c:if test="${(userPermiLi.c8 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
           .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table-tab td{
                padding: 0px !important;
                text-align: center;
            }
            .table-tab td:nth-child(6), td:nth-child(7){
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
        <input type="hidden" name="userHidVal" id="userHidVal" value="${userID}" />
        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>    
           <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>Material Requisition(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Material Requisition(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">

                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        <div class="pagecontent">
                            <div class="display_msg_success_Ma">
                                <label id="successDupItem" class="label_success_msg"></label>
                            </div>
                            <div class="display_msg_error_Ma">
                                <label id="errorDupItem" class="label_error_msg"></label>
                            </div>
                        </div>
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : MATERIAL REQUISATION  DETAIL(S)**************** --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form id="mate_requ_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Material Requisition(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c14 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Material Requisition(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="materialReqId" name="materialReqId" value=""/>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>  
                                                    <input type="text" name="poNumber" id='poNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPoNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Choose One </label>
                                                    <select name="mateReceType" id="mateReceType" class="form-control input-sm">
                                                        <option value="Fresh">Fresh  material receipt entry</option>
                                                        <option value="Existing">Existing material receipt</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Choose One </label>
                                                    <select name="chooseOne" id="chooseOne" class="form-control input-sm">
                                                        <option value="null">--Select--</option>
                                                        <option value="Contine">Continue</option>
                                                        <option value="Cancel">Cancel</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>                                              
                                            </div>
                                            
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Please Select Issue Type </label>
                                                    <select name="pleaseSelIssType" id="pleaseSelIssType" class="form-control input-sm">
                                                        <option value="">--Select--</option>
                                                        <option value="normal">Normal</option>
                                                        <option value="chargeable">Chargeable</option>
                                                        <option value="returnable">Returnable</option>
                                                        <option value="free_issue">Free issue</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Whether This Is Disposal Issue </label>
                                                    <select name="whetherDispIss" id="whetherDispIss" class="form-control input-sm">
                                                        <option value="null">--Select--</option>
                                                        <option value="yes">Yes</option>
                                                        <option value="no">No</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Release From Other Section Is Required </label>
                                                    <select name="releaseSecReq" id="releaseSecReq" class="form-control input-sm">
                                                        <option value="null">--Select--</option>
                                                        <option value="yes">Yes</option>
                                                        <option value="no">No</option>
                                                        
                                                    </select>
                                                    <label id="errreleaseSecReq" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Other Section Name:</label>
                                                    <input type="text" name="otherSecName" id='otherSecName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section / Division </label>
                                                    <input type="text" name="secOrDivision" id='secOrDivision' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Job Allocation: </label>
                                                    <input type="text" name="jobAllocation" id='jobAllocation' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Items Covered: </label>                                                    
                                                    <textarea type="text" name="itemCovered" id='itemCovered' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">To Be Delivered At: </label>
                                                    <input type="text" name="toBeDeliAt" id='toBeDeliAt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue Type: </label>
                                                    <input type="text" name="issueType" id='issueType' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Number: </label>
                                                    <input type="text" name="reqNumber" id='reqNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorScheduledDelDate" style="color: red"></label>
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
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">VR Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="vrDate" id="vrDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Disposal Issue : </label>
                                                    <input type="text" name="disposalIssue" id='disposalIssue' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">From/to: </label>
                                                    <input type="text" name="fromTo" id='fromTo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">WO Number: </label>
                                                    <input type="text" name="woNumber" id='woNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">WO Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="woDate" id="woDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Release Reqd.: </label>
                                                    <input type="text" name="releasrReqd" id='releasrReqd' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">From Section: </label>
                                                    <input type="text" name="fromSection" id='fromSection' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery At: </label>
                                                    <input type="text" name="deliveredAt" id='deliveredAt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div> 
                                            </div>                                                                                        
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ******** END :MATERIAL REQUISATION   DETAIL(S)****************** --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- *** START : MATERIAL REQUISATION  ITEM REQUIRED LIST DETAIL(S)** --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div id="table_form">
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Items Required</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Category: </label>                                                    
                                                    <select name="category" id="category" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorCategory" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab" id="" style="width:120% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>Store </th>
                                                            <th>Group</th>
                                                            <th>Card No</th>
                                                            <th>Item Code</th>
                                                            <th>Item Description</th>
                                                            <th>Unit</th>
                                                            <th>Qty. Regd</th>                                                            
                                                            <th>Qty Issued</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items">

                                                </tbody>
                                                </table>
                                            </div>
                                            </div>
                                        </div>
                                            <div class="tile-body">
                                            <spring:form id="mate_requ_Secform">
                                            <div class="row row-ext-7">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Item Selected: </label>
                                                    <input type="text" name="itemSelected" id='itemSelected' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indenter: </label>                                                    
                                                    <select name="indentor" id="indentor" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section   </label>                                                    
                                                    <select name="section" id="section" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Recd. By: </label>
                                                    <input type="text" name="recdBy" id='recdBy' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorised by : </label>
                                                    <input type="text" name="authorisedBy" id='authorisedBy' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username" id="releasedByLabel">Released By: </label>
                                                    <input type="text" name="releasedBy" id='releasedBy' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issued By: </label>
                                                    <input type="text" name="issuedBy" id='issuedBy' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                            </div> 
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </spring:form>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- *** END : MATERIAL REQUISATION  ITEM REQUIRED LIST DETAIL(S)**** --%>
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
                                        <h1 class="custom-font"><strong>Material Requisition(s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.c8 == 1)}">                                                
                                                <button type='button' id="showForm"
                                                        class="btn btn-info btn-rounded btn-sm"
                                                        onclick="openDDNumberForm();">Material Requisition(s) Form
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
        <script src="<c:url value="/appjs/MaterialRequiJS.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>

        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.c8 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>