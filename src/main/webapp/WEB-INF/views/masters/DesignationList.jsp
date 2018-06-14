<%-- 
    Document   : DesignationList
    Created on : Sep 30, 2016, 12:29:42 PM
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
                <c:if test="${(userPermiLi.m10 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Designation List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>

    </head>
    <c:set var="userType" value="User"/>
    <c:if test="${sessionScope.USER_SESSION!=null}">
        <c:set var="userType" value="${sessionScope.USER_SESSION.employeeProfileDTO.employeeTypeDTO.employeeTypeName}"/>
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
                        <h2>Designation(s) <span> Add/ Update Designation(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Designation(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
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
                                        <strong>Error!</strong>&nbsp;${ermsg}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT DESIGNATION DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addDesigForm" commandName="desigObj" >
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Designation</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Designation Name: </label>
                                                    <input type="text" name="designationName" id='designationName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errordesignationName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Designation Code: </label>
                                                    <input type="text" name="designationCode" id='designationCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errordesignationCode" style="color: red"></label>
                                                </div>
                                            </div>
                                            <c:if test="${userType == constants.EMPLOYEE_TYPE_SUPERADMIN}">
                                                <div class="row">
                                                    <div class="form-group col-md-6">
                                                        <label class="checkbox checkbox-custom mr-10">
                                                            <input type="checkbox" name="isSigningAuthority" id="isSigningAuthority"
                                                                   value="1">
                                                            <i></i> Is Signing Authority
                                                        </label>
                                                        <label id="errorisSigningAuthority" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label class="checkbox checkbox-custom mr-10">
                                                            <input type="checkbox" name="isApprovingAuthority" id="isApprovingAuthority"
                                                                   value="1">
                                                            <i></i> Is Approving Authority
                                                        </label>
                                                        <label id="errorisApprovingAuthority" style="color: red"></label>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${(userType == constants.EMPLOYEE_TYPE_SECADMIN)||
                                                  (userType == constants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)||
                                                  (userType == constants.EMPLOYEE_TYPE_STORES_ADMIN)}">
                                                <div class="row">
                                                    <div class="form-group col-md-6">
                                                        <label class="checkbox checkbox-custom mr-10">
                                                            <input type="checkbox" name="isApprovingAuthority" id="isApprovingAuthority"
                                                                   value="1">
                                                            <i></i> Is Approving Authority
                                                        </label>
                                                        <label id="errorisApprovingAuthority" style="color: red"></label>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${(userType == constants.EMPLOYEE_TYPE_PUADMIN)}">
                                                <div class="row">
                                                    <div class="form-group col-md-6">
                                                        <label class="checkbox checkbox-custom mr-10">
                                                            <input type="checkbox" name="isSigningAuthority" id="isSigningAuthority"
                                                                   value="1">
                                                            <i></i> Is Signing Authority
                                                        </label>
                                                        <label id="errorisSigningAuthority" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label class="checkbox checkbox-custom mr-10">
                                                            <input type="checkbox" name="isApprovingAuthority" id="isApprovingAuthority"
                                                                   value="1">
                                                            <i></i> Is Approving Authority
                                                        </label>
                                                        <label id="errorisApprovingAuthority" style="color: red"></label>
                                                    </div>
                                                </div>
                                            </c:if>
                                            
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Description: </label>
                                                    <textarea name="description"
                                                              id="description"
                                                              class="form-control"></textarea>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addDesig">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateDesig" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelDesig">
                                                        <span>Cancel</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT DESIGNATION DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>

                        <%-- **************************************************************** --%>
                        <%-- **********START : DESIGNATION LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Designation(s)</strong> List</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive">
                                            <table class="table table-custom" id="basic-usage" style="width:100% !important">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>S.No#</th>
                                                        <th>Designation Code</th>
                                                        <th>Designation Name</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${desigLi != null}">
                                                        <c:set var="loopCount" value="1"/>
                                                        <c:forEach var="desObj" items="${desigLi}">
                                                            <tr class="gradeX">
                                                                <td>${loopCount}</td>
                                                                <td>${desObj.designationCode}</td>
                                                                <td>${desObj.designationName}</td>
                                                                <td>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnEditDesig('${fn:trim(desObj.encFieldValue)}')">Edit
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                            <c:set var="loopCount" value="${loopCount+1}"/>
                                                        </c:forEach>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- *********** END : DESIGNATION LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                    </div>
                    <!-- == End :Page Form ======== -->
                </div>
            </section>
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/designationJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
           
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m10 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>