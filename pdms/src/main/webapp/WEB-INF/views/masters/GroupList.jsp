<%-- 
    Document   : GroupList
    Created on : Sep 30, 2016, 9:05:51 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.m4 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Group List</title>
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
                        <h2>Group(s) <span> Add/ Update Group(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Group(s)</a>
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
                        <%-- ******** START : ADD/EDIT DESIGNATION DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addGroupForm" commandName="groupObj" >
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Group</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Group Name: </label>
                                                    <input type="text" name="groupName" id='groupName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorgroupName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Group Code: </label>
                                                    <input type="text" name="groupCode" id='groupCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorgroupCode" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Description: </label>
                                                    <textarea name="description"
                                                              id="description"
                                                              class="form-control input-sm"></textarea>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addGroup">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateGroup" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelGroup">
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
                                        <h1 class="custom-font"><strong>Group(s)</strong> List</h1>
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
                                                        <th>Group Code</th>
                                                        <th>Group Name</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${groupLi != null}">
                                                        <c:set var="loopCount" value="1"/>
                                                        <c:forEach var="groupObj" items="${groupLi}">
                                                            <tr class="gradeX">
                                                                <td>${loopCount}</td>
                                                                <td>${groupObj.groupCode}</td>
                                                                <td>${groupObj.groupName}</td>
                                                                <td>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnEditGroup('${fn:trim(groupObj.encFieldValue)}')">Edit
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
        <script src="<c:url value="/appjs/groupJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
           
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m4 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>