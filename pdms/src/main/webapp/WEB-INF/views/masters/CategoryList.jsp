<%-- 
    Document   : CategoryList
    Created on : Sep 23, 2016, 4:57:42 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.m1 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Category List</title>
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
                        <h2>Category(s) <span> Add/ Update Category(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Category(s)</a>
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
                        <%-- ******** START : ADD/EDIT CATEGORY DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addCatForm" commandName="categoryObj" >
                                <!-- col -->
                                <div class="col-md-12">
                                    
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Category</strong> Details</h1>
                                        </div>
                                        <input type="hidden" name="exCategoryName" id="exCategoryName" value=""/>
                                        <input type="hidden" name="exCategoryCode" id="exCategoryCode" value=""/>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupCategory" style="color: red"></label>
                                                </div>
                                            </div>
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Category Name: </label>
                                                    <input type="text" name="categoryName" id='categoryName' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorcategoryName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Category Code: </label>
                                                    <input type="text" name="categoryCode" id='categoryCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorcategoryCode" style="color: red"></label>
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
                                                            id="addCategory">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateCategory" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelCategory">
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
                                        <h1 class="custom-font"><strong>Category(s)</strong> List</h1>
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
                                                        <th>Category Code</th>
                                                        <th>Category Name</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${categoryLi != null}">
                                                        <c:set var="loopCount" value="1"/>
                                                        <c:forEach var="catObj" items="${categoryLi}">
                                                            <tr class="gradeX">
                                                                <td>${loopCount}</td>
                                                                <td>${catObj.categoryCode}</td>
                                                                <td>${catObj.categoryName}</td>
                                                                <td>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnEditCategory('${fn:trim(catObj.encFieldValue)}')">Edit
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
        <script src="<c:url value="/appjs/categoryJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
           
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>