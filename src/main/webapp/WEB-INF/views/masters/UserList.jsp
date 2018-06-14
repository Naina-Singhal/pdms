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
                <c:if test="${(userPermiLi.m8 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>

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
                        <h2>User(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">User(s)</a>
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
                                        <strong>Error!</strong>${ermsg}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <%-- END : DISPLAY STATUS MESSAGE--%>

                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addUserForm" commandName="userObj" >
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>User</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">User Type </label>
                                                    <select class="form-control input-sm"
                                                            name="employeeProfileDTO.employeeTypeDTO.employeeTypeID" 
                                                            id="employeeTypeID">
                                                        <option value="0">Select</option>
                                                        <c:if test="${empTypeLi != null}">
                                                            <c:forEach var="empTypeObj" items="${empTypeLi}">
                                                                <option value="${empTypeObj.employeeTypeID}">${empTypeObj.employeeTypeName}</option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <label id="erroremployeeTypeID" style="color: red"></label>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-2">
                                                    <label for="username">User Name: </label>
                                                    <input type="text" name="employeeProfileDTO.firstName" id='firstName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"  placeholder="First Name"/>
                                                    <label id="errorfirstName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-2">
                                                    <label for="username">&nbsp; </label>
                                                    <input type="text" name="employeeProfileDTO.middleName" id='middleName' 
                                                           class="form-control input-sm"
                                                           maxlength="40" placeholder="Middle Name"/>
                                                    <label id="errormiddleName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-2">
                                                    <label for="username">&nbsp; </label>
                                                    <input type="text" name="employeeProfileDTO.lastName" id='lastName' 
                                                           class="form-control input-sm"
                                                           maxlength="40" placeholder="Sur Name"/>
                                                    <label id="errorlastName" style="color: red"></label>
                                                </div>

                                                <div class="form-group col-md-6 col-lg-6">
                                                    <label for="username">IC Number</label>
                                                    <input type="text" name="employeeProfileDTO.icNumber" id='icNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="10"
                                                           onKeyPress="return numbersonly(this, event)"/>
                                                    <label id="erroricNumber" style="color: red"></label>
                                                </div>
                                            </div>
                                            <c:if test="${userType != constants.EMPLOYEE_TYPE_SUPERADMIN}">
                                                <div class="row">
                                                    <div class="form-group col-md-6">
                                                        <label for="username">Section: </label>
                                                        <select class="form-control input-sm"
                                                                name="sectionDTO.sectionID" 
                                                                id="sectionID">
                                                            <option value="0">Select</option>
                                                            <c:if test="${sectionLi != null}">
                                                                <c:forEach var="secObj" items="${sectionLi}">
                                                                    <option value="${secObj.sectionID}">${secObj.sectionCode}(${secObj.sectionName})</option>
                                                                </c:forEach>
                                                            </c:if>
                                                        </select><br/>
                                                        <label id="errorsectionID" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="username">Designation: </label>
                                                        <select class="form-control input-sm"
                                                                name="designationDTO.designationID" 
                                                                id="designationID">
                                                            <option value="0">Select</option>
                                                            <c:if test="${desigLi != null}">
                                                                <c:forEach var="desigObj" items="${desigLi}">
                                                                    <option value="${desigObj.designationID}">${desigObj.designationCode}(${desigObj.designationName})</option>
                                                                </c:forEach>
                                                            </c:if>
                                                        </select><br/>
                                                        <label id="errordesignationID" style="color: red"></label>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Email ID: </label>
                                                    <input type="text" name="employeeProfileDTO.employeeEmail" id='employeeEmail' 
                                                           class="form-control input-sm"
                                                           maxlength="150"/>
                                                    <label id="erroremployeeEmail" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-4 col-lg-2">
                                                    <label for="gender">Gender: </label><br/>
                                                    <input type="radio" name="employeeProfileDTO.gender" id="gender"
                                                           value="Male">
                                                    <i></i> Male
                                                </div>
                                                <div class="form-group col-md-4 col-lg-2">
                                                    <label for="gender">&nbsp; </label><br/>
                                                    <input type="radio" name="employeeProfileDTO.gender" id="gender"
                                                           value="Female">
                                                    <i></i> Female
                                                </div><br/>
                                                <label id="errorgender" style="color: red"></label>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addUser">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateUser" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelUser">
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
                        <%-- ********** END : ADD/EDIT CATEGORY DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>

                        <%-- **************************************************************** --%>
                        <%-- ************ START : CATEGORY LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>User(s)</strong> List</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive">
                                            <table class="table table-custom display" id="basic-usage" cellspacing="0" style="width:100% !important">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>S.No#</th>
                                                        <th>IC.No#</th>
                                                        <th>Login Name</th>
                                                        <th>Password</th>
                                                        <th>User Name</th>
                                                        <th>Section</th>
                                                        <th>Designation</th>
                                                        <th>Authority</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${userLi != null}">
                                                        <c:set var="loopCount" value="1"/>
                                                        <c:forEach var="userObj" items="${userLi}">
                                                            <c:if test="${userID != userObj.employeeID}">
                                                                <tr class="gradeX">
                                                                    <td>${loopCount}</td>
                                                                    <td>${userObj.employeeProfileDTO.icNumber}</td>
                                                                    <td>${userObj.username}</td>
                                                                    <td>${userObj.password}</td>
                                                                    <td>${userObj.employeeProfileDTO.firstName}  ${userObj.employeeProfileDTO.lastName}</td>
                                                                    <td>${userObj.sectionDTO.sectionName}</td>
                                                                    <td>${userObj.designationDTO.designationName}</td>
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${userObj.designationDTO.isApprovingAuthority == 1}">
                                                                                Approving Authority
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:choose>
                                                                                    <c:when test="${userObj.designationDTO.isSigningAuthority == 1}">
                                                                                        Signing Authority
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        General
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td>
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnEditUser('${fn:trim(userObj.encFieldValue)}')">Edit
                                                                        </button>
                                                                    </td>
                                                                </tr>
                                                                <c:set var="loopCount" value="${loopCount+1}"/>
                                                            </c:if>
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
                        <%-- ************ END : CATEGORY LIST DETAIL(S)******************** --%>
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
        <script src="<c:url value="/appjs/userJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>

        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m8 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>