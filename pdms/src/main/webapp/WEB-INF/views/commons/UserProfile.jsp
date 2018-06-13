<%-- 
    Document   : UserProfile
    Created on : Oct 11, 2016, 7:51:01 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM :Profile Page</title>
        <jsp:include page="CommonCSSIncl.jsp"/>
    </head>

    <body id="minovate" class="appWrapper">

        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="CommonHeader.jsp"/>  
            <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>User Profile<span> Update User Profile</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Profile</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <div class="pagecontent">
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        <c:if test="${msg != null}">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="alert alert-success alert-dismissable">
                                        <button type="button" class="close" data-dismiss="alert" 
                                                aria-hidden="true">&times;</button>
                                        <strong></strong>${msg}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        <!-- ==== Start : User Profile Content =========== -->
                        <div class="row">
                            <spring:form name="userProfileForm" commandName="userProfileObj" >
                                <div class="col-md-6">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>User</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">Login Name: </label>
                                                    <label>${userSessObj.username} </label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">IC Number: </label>
                                                    <label>${userSessObj.employeeProfileDTO.icNumber} </label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">User Name: </label>
                                                    <input type="text" name="employeeProfileDTO.firstName" id='firstName' 
                                                           class="form-control input-sm"
                                                           maxlength="40" 
                                                           value="${userSessObj.employeeProfileDTO.firstName}"/>
                                                    <label id="errorfirstName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">&nbsp; </label>
                                                    <input type="text" name="employeeProfileDTO.lastName" id='lastName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"
                                                           value="${userSessObj.employeeProfileDTO.lastName}"/>
                                                    <label id="errorlastName" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">Email: </label>
                                                    <input type="text" name="employeeProfileDTO.employeeEmail" id='employeeEmail' 
                                                           class="form-control input-sm"
                                                           maxlength="150" 
                                                           value="${userSessObj.employeeProfileDTO.employeeEmail}"/>
                                                    <label id="erroremployeeEmail" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">Phone: </label>
                                                    <input type="text" name="employeeProfileDTO.phone" id='phone' 
                                                           class="form-control input-sm"
                                                           maxlength="12" 
                                                           value="${userSessObj.employeeProfileDTO.phone}"/>
                                                    <label id="errorphone" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div>
                                                <div class="form-group col-md-6 col-lg-3">
                                                    <label for="gender">Gender: </label><br/>
                                                    <c:choose>
                                                        <c:when test="${userSessObj.employeeProfileDTO.gender == 'Male'}">
                                                            <input type="radio" name="employeeProfileDTO.gender" 
                                                                   id="gender"
                                                                   value="Male" checked="true">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="radio" name="employeeProfileDTO.gender" 
                                                                   id="gender"
                                                                   value="Male">
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <i></i> Male
                                                </div>
                                                <div class="form-group col-md-6 col-lg-3">
                                                    <label for="gender">&nbsp; </label><br/>
                                                    <c:choose>
                                                        <c:when test="${userSessObj.employeeProfileDTO.gender == 'Female'}">
                                                            <input type="radio" name="employeeProfileDTO.gender" 
                                                                   id="gender"
                                                                   value="Female" checked="true">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="radio" name="employeeProfileDTO.gender" 
                                                                   id="gender"
                                                                   value="Female">
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <i></i> Female
                                                </div><br/>
                                                <label id="errorgender" style="color: red"></label>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updProfile">
                                                        <span>Update</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                            </spring:form>
                            <spring:form name="changePassForm" commandName="userObj" >
                                <div class="col-md-6">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font">Change <strong>Password</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">Current Password: </label>
                                                    <input type="password" name="currentPassword" id='currentPassword' 
                                                           class="form-control input-sm"
                                                           maxlength="40" 
                                                           value=""/>
                                                    <label id="errorcurrentPassword" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">New Password: </label>
                                                    <input type="password" name="newPassword" id='newPassword' 
                                                           class="form-control input-sm"
                                                           maxlength="40" 
                                                           value=""/>
                                                    <label id="errornewPassword" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">Confirm Password: </label>
                                                    <input type="password" name="confPassword" id='confPassword' 
                                                           class="form-control input-sm"
                                                           maxlength="40" 
                                                           value=""/>
                                                    <label id="errorconfPassword" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updPassword">
                                                        <span>Update</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <!-- ==== End : User Profile Content =========== -->
                    </div>
                </div>
            </section>

        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->


        <jsp:include page="CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/profileJS.js"/>" type="text/javascript"></script>
    </body>

</html>
