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
                <c:if test="${(userPermiLi.d22 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Transfer Entry Screen(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            
            .textareaPOE{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 40px;
                width: 100%;
            }
            fieldset {
                border: 1px groove #ddd !important;                
                margin: 0 0 1.5em 0 !important;
                -webkit-box-shadow:  0px 0px 0px 0px #000;
                box-shadow:  0px 0px 0px 0px #000;
                border-radius: 5px;
                background-color: #8080800a;
            }
            legend {
                width: 200px !important;
                padding: 0;
                margin-left: 10px;
                margin-bottom: -9px;
                border: 0;  
                border-bottom: 0px !important;
                color: #737475f2 !important;
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
                        <h2>Transfer Entry Screen(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Transfer Entry Screen(s)</a>
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
                        <%-- ******** START : ADD/EDIT FILE TRANSFER SCREEN(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form  id="transfer_entry_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Transfer Entry Screen(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Transfer Entry Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">  
                                            <input type="hidden" id="transferEntryId" name="transferEntryId" value="" />
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Options: </label> 
                                                    <input type="text" name="options" id='options' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Voucher/Challan Number: </label>
                                                    <input type="text" name="vouChallanNo" id='vouChallanNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Voucher Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="voucherDate" id="voucherDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Amount </label>                                                    
                                                    <input type="text" name="amount" id='amount' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Year: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="teYear" id="teYear" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">T.E Number: </label>
                                                    <input type="text" name="teNumber" id='teNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorTeNumber" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <fieldset>
                                                <legend>Old Booking Details:</legend>
                                             <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Hcode: </label>                                                    
                                                    <input type="text" name="posr" id='posr' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Head Of Account: </label>
                                                    <input type="text" name="poNumber" id='poNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Description: </label>
                                                    <input type="text" name="nameofthevendor" id='name of the vendor' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Scode: </label>                                                    
                                                    <input type="text" name="posr" id='posr' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Debit: </label>
                                                    <input type="text" name="poNumber" id='poNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Credit: </label>
                                                    <input type="text" name="nameofthevendor" id='name of the vendor' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            </fieldset>
                                            <fieldset>
                                                <legend>New Booking Details:</legend>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">H Code: </label>                                                    
                                                    <input type="text" name="hcodeNew" id='hcodeNew' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Head Of Account: </label>
                                                    <input type="text" name="hoaNew" id='hoaNew' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Description: </label>
                                                    <input type="text" name="desNew" id='desNew' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">S Code: </label>                                                    
                                                    <input type="text" name="scodeNew" id='scodeNew' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Debit: </label>
                                                    <input type="text" name="debitNew" id='debitNew' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Credit: </label>
                                                    <input type="text" name="creditNew" id='creditNew' 
                                                           class="form-control input-sm"
                                                           maxlength="40" />
                                                    <label id="errorDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            </fieldset>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>                                                    
                                                   <textarea type="text" name="remarks" id='remarks' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorRCIVNo" style="color: red"></label>
                                                </div>
                                                                                               
                                            </div>
                                           </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
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
                        <%-- ********** END : ADD/EDIT FILE TRANSFER SCREEN(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- **********START : FILE TRANSFER SCREEN DETAIL(S)******************** --%>
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
                                                            onclick="openDDNumberForm();">Transfer Entry Form
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
                        <%-- *********** END : FILE TRANSFER SCREEN DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                    </div>
                </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/account/TransferEnScJS.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.d22 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>