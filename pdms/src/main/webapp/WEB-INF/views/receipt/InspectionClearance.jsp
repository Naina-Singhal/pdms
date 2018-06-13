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
                <c:if test="${(userPermiLi.c5 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            
            .row-ext-7{
                margin-top: 20px;
            }
            .comm-option{
                width: 90%;                
                position: relative;
                top: -29px;                
                border: 1px;
                padding-left: 5px;
                margin-left: 2px;
            }
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table-tab td{
                padding: 0px !important;
                text-align: center;
            }
            .table-tab td:nth-child(6){
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
        <input type="hidden" name="authorUserid" id="authorUserid" value="${userID}">
        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>    
           <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>Inspection Clearance/Gate Pass(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Inspection Clearance/Gate Pass(s)</a>
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
                            <input type="hidden" id="User_ID" name="User_ID" value="${userID}"/>
                            <spring:form  id="InspecCleaForm">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                           
                                            <h1 class="custom-font"><strong>Inspection Clearance/Gate Pass(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c6 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Inspection Clearance/Gate Pass Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body"> 
                                            <input type="hidden" id="inspeClearID" name="inspeClearID" value=""/>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Please Select DB Number: </label>                                                    
                                                    <select name="dbNumber" id="dbNumber" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errordbNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">DB Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dbDate" id="dbDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="section" id="section" class="form-control input-sm">                                                        
                                                    </select>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Plant: </label>
                                                    <input type="text" name="plant" id='plant' value="Manuguru" 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Purchase Unit: </label>
                                                    <input type="text" name="purchaseUnit" id='purchaseUnit' value="RPUM"
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>
                                                    <input type='text' class="form-control input-sm"
                                                               name="poLastNo" id="poLastNo" 
                                                               value="" />                                                    
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>                                                
                                            </div>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="poDate" id="poDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">GR/RR Number: </label>
                                                    <input type="text" name="grOrrNumber" id='grOrrNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">GR/RR Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="grOrrDate" id="grOrrDate" 
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
                                                    <label for="username">Items Covered: </label>                                                    
                                                    <textarea type="text" name="itemsCovered" id='itemsCovered' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Inspected By: </label>                                                    
                                                    <select name="inspectedBy" id="inspectedBy" class="form-control input-sm">
                                                                                                               
                                                    </select>
                                                     <input class="editOptionA comm-option" style="display:none;" placeholder="" />
                                                    
                                                    <label id="errorPOValPaid" style="color: red"></label>
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
                        <%-- ************ START : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <div id="table_form">
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
                                            <table class="table table-bordered table-tab" id="" style="width:120% !important">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th>Sl No</th>
                                                        <th>Code </th>
                                                        <th>Group code</th>
                                                        <th>Store card no</th>
                                                        <th>Item</th>
                                                        <th>Unit</th>
                                                        <th>Order Qty</th>
                                                        <th>Despatch Qty</th>
                                                        <th>Update</th>                                                            
                                                        <th>Accepted Qty</th>
                                                        <th>Rejected Qty</th>

                                                    </tr>
                                                </thead>
                                                <tbody id="stic_items">

                                                </tbody>
                                            </table>
                                        </div>

                                    </div>
                                    <!-- /tile body -->
                                </section>
                                </div>
                                <form id="form_second_part">
                                    <div class="row row-ext-7">
                                        <div class="form-group div-wid-7">
                                            <label for="username">Inspection Clearance Remarks: </label>                                                    
                                            <select name="inspeCleaRemarks" id="inspeCleaRemarks" class="form-control input-sm">
                                                <option value="Select">--Select--</option>
                                                <option value="Inspected">Inspected</option>
                                                <option value="Accepted">Accepted</option>
                                                <option value="Rejected">Rejected</option>
                                                <option value="Commissoning">Commissoning</option>
                                            </select>
                                            <label id="errorIndentDesNoDate" style="color: red"></label>
                                        </div>                                                
                                    </div>
                                </form> 
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)******************** --%>
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
                                        <h1 class="custom-font"><strong>Inspection Clearance/Gate Pass(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c5 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">Inspection Clearance/Gate Pass Form
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
        <script src="<c:url value="/appjs/InspeClearenceJS.js"/>" type="text/javascript"></script>   
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
    <c:if test="${(userPermiLi.c5 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>