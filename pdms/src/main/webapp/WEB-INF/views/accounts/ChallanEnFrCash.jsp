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
        <c:if test="${(userPermiLi.d12 == 1)}">  
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta name="description" content="">
                    <title>RPUM : Challan Entry For Cash</title>
                    <jsp:include page="../commons/CommonCSSIncl.jsp"/>
                    <style>
                        .editOption{
                            width: 90%;                
                            position: relative;
                            top: -29px;                
                            border: 1px;
                            padding-left: 5px;
                            margin-left: 2px;
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
                                    <h2>Challan Entry For Cash(s) <span> Add/ Update User(s)</span></h2>
                                    <div class="page-bar">
                                        <ul class="page-breadcrumb">
                                            <li>
                                                <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                            </li>
                                            <li>
                                                <a href="#">Challan Entry For Cash(s)</a>
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
                                            <spring:form id="Challan_Form">
                                                <!-- col -->
                                                <div class="col-md-12">
                                                    <!-- tile -->
                                                    <section class="tile">
                                                        <!-- tile header -->
                                                        <div class="tile-header dvd dvd-btm">                                            
                                                            <h1 class="custom-font"><strong>Challan Entry For Cash(s)</strong> Details</h1>                                            
                                                            <div class="open_page_button">
                                                                <c:if test="${(userPermiLi.d21 == 1)}">                                                
                                                                    <button type='button' id="showRecord"
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="openDDNumberRec();">Challan Entry For Cash(s) Record
                                                                    </button> 
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <!-- tile header -->
                                                        <!-- tile body -->
                                                        <div class="tile-body">    
                                                            <input type="hidden" id="challanId" name="challanId" value="" />
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Challan Number: </label>
                                                                    <input type="text" name="challanNo" id='challanNo' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" readonly/>
                                                                    <label id="errorPurchaseUnit" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Challan Date: </label>
                                                                    <div class='input-group datepicker' data-format="L">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="challanDate" id="challanDate" 
                                                                               value=""/>
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Month: </label>                                                    
                                                                    <div class='input-group datepicker' data-format="MMMM/YYYY">
                                                                        <input type='text' class="form-control input-sm"
                                                                               name="month" id="month" 
                                                                               value="" />
                                                                        <span class="input-group-addon">
                                                                            <span class="fa fa-calendar"></span>
                                                                        </span>
                                                                    </div>
                                                                    <label id="errorDate" style="color: red"></label>
                                                                </div>

                                                            </div>
                                                            <div class="row">                                                
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Challan Year: </label> 
                                                                    <input type="text" name="challanYear" id='challanYear' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
                                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Challan Amount: </label>                                                    
                                                                    <input type="text" name="challanAmt" id='challanAmt' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorUserUnit" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Head Of Account: </label>
                                                                    <select name="headOfAc" id="headOfAc" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">

                                                                    </select>
                                                                    <label id="errorPurchaseGroup" style="color: red"></label>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Short code: </label>
                                                                    <select name="scode" id="scode" class="selectpicker form-control" 
                                                                            data-live-search="true" data-dropup-auto="false">

                                                                    </select>
                                                                    <label id="errorShortCo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Detailed Head: </label>                                                    
                                                                    <input type="text" name="detailedHead" id='detailedHead' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Sub head: </label>                                                    
                                                                    <select name="subHead" id="subHead" class="form-control input-sm">
                                                                        <option value="null">Select</option>
                                                                        <option value="minusDebit">(-) debit</option>
                                                                        <option class="editable" value="other">other</option> 
                                                                    </select>
                                                                    <input class="editOption" style="display:none;" placeholder="" />
                                                                    <label id="errorPoDate" style="color: red"></label>
                                                                </div>
                                                            </div>                                            
                                                            <div class="row">
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Description Of Head: </label>
                                                                    <input type="text" name="desOfHead" id='desOfHead' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Details: </label>
                                                                    <input type="text" name="details" id='details' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Total: </label>
                                                                    <input type="text" name="total" id='total' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40"/>
                                                                    <label id="errorIndentor" style="color: red"></label>
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
                                                    <h1 class="custom-font"><strong>Challan Entry For Cash(s)</strong> List</h1>
                                                    <div class="open_page_button">
                                                        <c:if test="${(userPermiLi.d12 == 1)}">                                                
                                                            <button type='button' id="showForm"
                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                    onclick="openDDNumberForm();">Challan Entry For Cash(s) Form
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
                        <!-- == Start :CST Modal Dialog ======== -->
                        <div class="modal fade" id="cstModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >

                            <div class="modal-dialog modal-lg" style="width:510px;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title custom-font">Template For Year!</h3>
                                    </div>
                                    <div class="modal-body"></div>

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
                    <script src="<c:url value="/appjs/account/ChallanEnFrCashJS.js"/>" type="text/javascript"></script>
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
        <c:if test="${(userPermiLi.d12 == 0)}"> 
            <% response.sendRedirect("./login");%>
        </c:if>
    </c:forEach>    
</c:if>