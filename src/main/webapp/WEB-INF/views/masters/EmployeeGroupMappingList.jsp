<%-- 
    Document   : EmployeeGroupMappingList
    Created on : Oct 9, 2016, 7:54:05 AM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.m12 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Employee Group Mapping List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>

    </head>
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
                        <h2>Group Mapping(s) <span> Add/ Update Employee Group Mapping</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Employee Group Mapping</a>
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
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT CATEGORY DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="empMapForm" commandName="grpObj" >
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Employee Group Mapping</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Group Name: </label><br/>
                                                    <select name="groupID" id="groupID">
                                                        <option value="0">Select</option>
                                                        <c:if test="${groupLi != null}">
                                                            <c:forEach items="${groupLi}" var="grpObj">
                                                                <option value="${grpObj.groupID}">
                                                                    ${grpObj.groupName}
                                                                </option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <br/>
                                                    <label id="errorgroupID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-12">
                                                    <label for="username">Employees Name: </label><br/>
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                <select name="SelectList" id="SelectList" SIZE="10" multiple="multiple" style="width: 200px">
                                                                    <c:if test="${empGrpLi != null}">
                                                                        <c:forEach items="${empGrpLi}" var="empObj">
                                                                            <c:if test="${empObj.groupDTO.groupID != null && empObj.groupDTO.groupID <= 0}">
                                                                                <option value="${empObj.employeeID}">
                                                                                    ${empObj.employeeProfileDTO.firstName} ${empObj.employeeProfileDTO.lastName}
                                                                                </option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                </select>
                                                            </td>
                                                            <td class="ux-padding-2t">
                                                                <input TYPE="BUTTON" class="btn btn-rounded btn-blue btn-sm" VALUE=">>>" ONCLICK="addIt();">
                                                                <br />
                                                                <br />
                                                                <input TYPE="BUTTON" class="btn btn-rounded btn-blue btn-sm" VALUE="<<<" ONCLICK="delIt();">
                                                            </td>
                                                            <td>
                                                                <select NAME="empList" ID="empList" SIZE="10" multiple="multiple" style="width: 200px">

                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <br/>
                                                    <label id="errorempList" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addEmpGrpMap">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateEmpGrpMap" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelEmpGrpMap">
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
                                        <h1 class="custom-font"><strong>Employee(s)</strong> List</h1>
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
                                                        <th>Group Name</th>
                                                        <th>Employee Name</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${empGrpLi != null}">
                                                        <c:set var="loopCount" value="1"/>
                                                        <c:forEach var="empObj" items="${empGrpLi}">
                                                            <c:if test="${empObj.groupDTO.groupID != null && empObj.groupDTO.groupID > 0}">
                                                                <tr class="gradeX">
                                                                    <td>${loopCount}</td>
                                                                    <td>${empObj.groupDTO.groupName}</td>
                                                                    <td>${empObj.employeeProfileDTO.firstName}  ${empObj.employeeProfileDTO.lastName}</td>
                                                                    <td>
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnEditEmpGroup('${fn:trim(empObj.groupDTO.encFieldValue)}')">Edit
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
        <script src="<c:url value="/appjs/listBoxMove.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/appjs/empGroupMapJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
           
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m12 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>