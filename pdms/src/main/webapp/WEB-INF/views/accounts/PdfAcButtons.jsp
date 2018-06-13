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
                <c:if test="${(userPermiLi.d1 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Pdf report(s) List</title>
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
                        <h2>Pdf report(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Pdf report(s)</a>
                                </li>
                            </ul>
                        </div>
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
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                       
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
                                            <h1 class="custom-font"><strong>Pdf reports</strong> List</h1>
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
                                                    <label for="username">RTGS Office Copy on Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="rtgsDate" id="rtgsDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="rtgsOfficePdf">
                                                            <span>Pdf</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="postageDetailsPdf">
                                                            <span>Postage Details Pdf</span>
                                                        </button>
                                                    </div>                                                
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">From Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="fromDateP" id="fromDateP" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">To Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="toDateP" id="toDateP" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="postEntryPdf">
                                                            <span>Post Entry Pdf</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="username"><b>Security Deposit For The Month: </b></label> 
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Head Of Account: </label>                                                    
                                                    <input type="text" name="headOfAc" id='headOfAc' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="onlyMonth" id="onlyMonth" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-search-7">                                                    
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                id="SecuriDeFrMn">
                                                            <span>Pdf(Security)</span>
                                                        </button>                                        
                                                    </div> 
                                                </div>
                                                <div class="form-group div-search-7">                                                    
                                                    <div class="col-lg-7">                                                                                                              
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                id="EmdDeForMonth">
                                                            <span>Pdf(E.M.D)</span>
                                                        </button>                                                   
                                                    </div>  

                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="username"><b>Acknowledgement For RPUM</b></label> 
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Receiver: </label>                                                    
                                                    <input type="text" name="receiver" id='receiver' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorUserUnit" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="fileMoveDate" id="fileMoveDate" 
                                                               value="" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-search-7">                                                    
                                                    <div class="col-lg-7">                                                                                                              
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                id="fileMoveAckonledge">
                                                            <span>Filemovement</span>
                                                        </button>                                                   
                                                    </div>  

                                                </div>
                                            </div>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : INSPECTION CLEARANCE ITEMS LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                       
                    </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/account/accButtons.js"/>" type="text/javascript"></script>
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
    <c:if test="${(userPermiLi.d1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>