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
        <c:if test="${(userPermiLi.b4 == 1)}"> 
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta name="description" content="">
                    <title>RPUM : Amendment Screen(s) List</title>
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
                                    <h2>Amendment Screen(s) <span> Add/ Update User(s)</span></h2>
                                    <div class="page-bar">
                                        <ul class="page-breadcrumb">
                                            <li>
                                                <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                            </li>
                                            <li>
                                                <a href="#">Amendment Screen(s)</a>
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
                                            <spring:form  id="amendment_Form">
                                                <!-- col -->
                                                <div class="col-md-12">
                                                    <!-- tile -->
                                                    <section class="tile">
                                                        <!-- tile header -->
                                                        <div class="tile-header dvd dvd-btm">                                            
                                                            <h1 class="custom-font"><strong>Amendment Screen(s)</strong> Details</h1>                                                        
                                                            <div class="open_page_button">
                                                                <c:if test="${(userPermiLi.d16 == 1)}">                                                
                                                                    <button type='button' id="showRecord"
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="openDDNumberRec();">Amendment Record(s)
                                                                    </button> 
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <!-- tile header -->
                                                        <!-- tile body -->
                                                        <div class="tile-body">    
                                                            <input type="hidden" id="amendmentId" name="amendmentId" value="" />
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">File Number: </label>                                                    
                                                                    <input type="text" name="fileNumber" id='fileNumber' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorFileNumber" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PO Number: </label> 
                                                                    <select name="poNumber" id="poNumber" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">

                                                                    </select>
                                                                    <label id="errorpoNumber" style="color: red"></label>
                                                                </div>                                                            
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Vendor Name: </label>                                                                
                                                                    <select name="vendorName" id="vendorName" class="form-control input-sm">

                                                                    </select>
                                                                    <label id="errorDate" style="color: red"></label>
                                                                </div>                                                
                                                            </div>
                                                            <div class="row">                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Vendor Address: </label>
                                                                    <input type="text" name="vendorAddress" id='vendorAddress' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
                                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Brief Description: </label>                                                     
                                                                    <input type="text" name="BreifDescription" id='BreifDescription' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorIndentor" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">BG Clause: </label>
                                                                    <input type="text" name="BgClause" id='BgClause' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorBgClause" style="color: red"></label>
                                                                </div> 
                                                            </div>                                             
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Reference: </label>
                                                                    <input type="text" name="reference" id='reference' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
                                                                    <label id="errorDate" style="color: red"></label>
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
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Annexure: </label>
                                                                    <input type="text" name="annexure" id='annexure' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>
                                                            </div> 
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Head: </label>
                                                                    <input type="text" name="code" id='code' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">temp: </label>
                                                                    <input type="text" name="temp" id='temp' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">BG Number: </label>                                                                
                                                                    <select name="bgNumber" id="bgNumber" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">

                                                                    </select>
                                                                    <label id="errorBgNumber" style="color: red"></label>
                                                                </div>

                                                            </div> 
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">BG Date: </label>
                                                                    <div class='input-group datepicker' data-format="L">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="bgDate" id="bgDate" 
                                                                               value="" readonly/>
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div> 
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Amount: </label>
                                                                    <input type="text" name="amount" id='amount' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" readonly/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div> 
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Expiry Date: </label>
                                                                    <div class='input-group datepicker' data-format="L">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="expireDate" id="expireDate" 
                                                                               value="" readonly/>
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div>      

                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">For: </label>
                                                                    <input type="text" name="forOne" id='forOne' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorItemsCovered" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Delivery Period: </label>
                                                                    <input type="text" name="deliveryPeriod" id='deliveryPeriod' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
                                                                    <label id="errorRemarks" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Other Amendments: </label>
                                                                    <input type="text" name="otherAmendments" id='otherAmendments' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
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

                                        <%-- **************************************************************** --%>
                                        <%-- ************ START : BILL ENTRY ITEMS LIST DETAIL(S)************ --%>
                                        <%-- **************************************************************** --%>


                                        <%-- **************************************************************** --%>
                                        <%-- ************ END : BILL ENTRY ITEMS LIST DETAIL(S)************** --%>
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
                                                    <h1 class="custom-font"><strong>Amendment Record(s)</strong> List</h1>
                                                    <div class="open_page_button">
                                                        <c:if test="${(userPermiLi.b4 == 1)}">                                                
                                                            <button type='button' id="showForm"
                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                    onclick="openDDNumberForm();">Amendment Form(s)
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

                    </div>
                    <!-- ====================================================
                    ================= End : Application Content =============
                    ===================================================== -->
                    <jsp:include page="../commons/CommonJSIncl.jsp"/>
                    <script src="<c:url value="/appjs/purchase/AmedmentScJS.js"/>" type="text/javascript"></script>  
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
        <c:if test="${(userPermiLi.b4 == 0)}"> 
            <% response.sendRedirect("./login");%>
        </c:if>
    </c:forEach>    
</c:if>
