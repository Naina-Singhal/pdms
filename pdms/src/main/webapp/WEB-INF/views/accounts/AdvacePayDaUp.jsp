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
        <c:if test="${(userPermiLi.d5 == 1)}"> 
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta name="description" content="">
                    <title>RPUM : User(s) List</title>
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
                                    <h2>Advance Payment Data Update(s) <span> Add/ Update User(s)</span></h2>
                                    <div class="page-bar">
                                        <ul class="page-breadcrumb">
                                            <li>
                                                <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                            </li>
                                            <li>
                                                <a href="#">Advance Payment Data Update(s)</a>
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
                                    <div id="form_page">
                                        <div class="row">
                                            <spring:form  id="AdvanceForm">
                                                <!-- col -->
                                                <div class="col-md-12">
                                                    <!-- tile -->
                                                    <section class="tile">
                                                        <!-- tile header -->
                                                        <div class="tile-header dvd dvd-btm">
                                                            <input type="hidden" name="exisItemCode" id="eItemCode" value=""/>
                                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                                            <h1 class="custom-font"><strong>Advance Payment Data Update(s)</strong> Details</h1>                                            
                                                            <div class="open_page_button">
                                                                <c:if test="${(userPermiLi.d18 == 1)}">                                                
                                                                    <button type='button' id="showRecord"
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="openDDNumberRec();">Advance Payment Data Update(s) Record
                                                                    </button> 
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <!-- tile header -->
                                                        <!-- tile body -->
                                                        <div class="tile-body"> 
                                                            <input type="hidden" id="avancePayId" name="avancePayId" value="" />
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PO Number: </label>
                                                                    <input type="text" name="poNumber" id='poNumber' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPONUmb" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PPR No: </label>                                                    
                                                                    <select name="pprNo" id="pprNo" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">
                                                                        <option value="null">select</option>
                                                                    </select>
                                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                                </div>                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Vendor Name: </label>                                                    
                                                                    <select name="vname" id="vname" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">
                                                                        <option value="null">select</option>
                                                                    </select>
                                                                    <label id="errorDate" style="color: red"></label>
                                                                </div>

                                                            </div>
                                                            <div class="row">                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Date Of Payment: </label>                                                    
                                                                    <div class='input-group datepicker' data-format="L">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="dop" id="dop" 
                                                                               value="" />
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Amount Paid: </label>                                                    
                                                                    <input type="text" name="amountPaid" id='amountPaid' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorUserUnit" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Advance Adjusted: </label>
                                                                    <input type="text" name="advanceAdj" id='advanceAdj' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Balance Paid: </label>
                                                                    <input type="text" name="balancePaid" id='balancePaid' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Balance Due: </label>                                                    
                                                                    <input type="text" name="balanceDue" id='balanceDue' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Total Due: </label>
                                                                    <input type="text" name="totalDue" id='totalDue' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPoDate" style="color: red"></label>
                                                                </div>
                                                            </div>    
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Closing Date: </label>
                                                                    <div class='input-group datepicker' data-format="L">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="clDate" id="clDate" 
                                                                               value=""/>
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                                </div>                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Quantity Paid: </label>
                                                                    <input type="text" name="qtyPaid" id='qtyPaid' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPoDate" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Recovery: </label>
                                                                    <input type="text" name="recovery" id='recovery' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                                </div>
                                                            </div> 
                                                            <div class="row">                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Remarks: </label>
                                                                    <textarea type="text" name="remarks" id='remarks' class="textareaPOE">                                                               
                                                                    </textarea>
                                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">POSR: </label>
                                                                    <input type="text" name="posr" id='posr' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
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
                                                    <h1 class="custom-font"><strong>DD Number Entry(s)</strong> List</h1>
                                                    <div class="open_page_button">
                                                        <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                            <button type='button' id="showForm"
                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                    onclick="openDDNumberForm();">DD Number Form
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
                    <script src="<c:url value="/appjs/account/AdvancePayUpJS.js"/>" type="text/javascript"></script>
                    <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>
                    <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
                    <!-- ===============================================
                    ============== Page Specific Scripts ===============
                    ================================================ -->
                    <script>

                    </script>
                </body>
            </c:if>
            <c:if test="${(userPermiLi.d5 == 0)}"> 
                <% response.sendRedirect("./login");%>
            </c:if>
        </c:forEach>    
    </c:if>
</html>
