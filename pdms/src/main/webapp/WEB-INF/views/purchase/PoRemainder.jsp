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
        <c:if test="${(userPermiLi.d2 == 1)}">  
            <!DOCTYPE html>
            <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta name="description" content="">
                    <title>RPUM : PO Remainder(s) List</title>
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
                                    <h2>PO Remainder(s) <span> Add/ Update User(s)</span></h2>
                                    <div class="page-bar">
                                        <ul class="page-breadcrumb">
                                            <li>
                                                <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                            </li>
                                            <li>
                                                <a href="#">PO Remainder(s)</a>
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
                                            <spring:form  id="BankGuarantee_Form">
                                                <!-- col -->
                                                <div class="col-md-12">
                                                    <!-- tile -->
                                                    <section class="tile">
                                                        <!-- tile header -->
                                                        <div class="tile-header dvd dvd-btm">                                            
                                                            <h1 class="custom-font"><strong>PO Remainder(s)</strong> Details</h1>

                                                        </div>
                                                        <!-- tile header -->
                                                        <!-- tile body -->
                                                        <div class="tile-body">                                            
                                                            <div class="row">
                                                                <input type="hidden" id="bankGuaranteeId" name="bankGuaranteeId" value="" />
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">Sl No: </label>                                                    
                                                                    <input type="text" name="Slno" id='Slno' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
                                                                    <label id="errorDate" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">BG Details: </label>                                                    
                                                                    <select name="tempone" id="tempone" class="form-control input-sm" >
                                                                        <option value="null">select</option>
                                                                        <option value="Security Deposite">Security Deposite</option>
                                                                        <option value="PBG">PBG</option>
                                                                        <option value="Insurance policy">Insurance policy</option>
                                                                        <option value="Indifinty Form Thing Option">Indifinty Form Thing Option</option>
                                                                    </select>
                                                                    <label id="errorPOoValue" style="color: red"></label>
                                                                </div>
                                                                <div class="form-group div-wid-7">
                                                                    <label for="username">PO Number: </label>
                                                                    <input type="text" name="poNumber" id='poNumber' 
                                                                           class="form-control input-sm"
                                                                           maxlength="40" />
                                                                    <label id="errorPurchaseUnit1" style="color: red"></label>
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
                                    

                                </div>
                            </div>
                        </section>                    
                    </div>
                    <!-- ====================================================
                    ================= End : Application Content =============
                    ===================================================== -->
                    <jsp:include page="../commons/CommonJSIncl.jsp"/>
                    <script src="<c:url value="/appjs/purchase/PoRemainderJS.js"/>" type="text/javascript"></script>
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
        <c:if test="${(userPermiLi.d2 == 0)}"> 
            <% response.sendRedirect("./login");%>
        </c:if>
    </c:forEach>    
</c:if>