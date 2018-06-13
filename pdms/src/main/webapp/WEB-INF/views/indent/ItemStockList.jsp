<%-- 
    Document   : ItemStockList
    Created on : Oct 3, 2016, 2:02:58 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e1 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Item Stock</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.css"/>">
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
                        <h2>Item Stock <span> Category wise Item(s) Stock</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Item Stock</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">       
                        <%-- **************************************************************** --%>
                        <%-- ******** START : SELECT ITEM CATEGORY DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addItemForm" commandName="itemObj" >
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Item Stock</strong> Details</h1>
                                            <div class="row">&nbsp;</div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Category: </label>
                                                    <br/>
                                                    <select name="categoryDTO.categoryID" id='categoryID' class="form-control custom-select">
                                                        <option value="0">Select</option>
                                                        <c:if test="${categoryLi != null}">
                                                            <c:forEach items="${categoryLi}" var="catObj">
                                                                <c:choose>
                                                                    <c:when test="${selCatID == catObj.categoryID}">
                                                                        <option value="${catObj.categoryID}" selected="true">${catObj.categoryName}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${catObj.categoryID}">${catObj.categoryName}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <br/>
                                                    <label id="errorcategoryID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="fetchDtls">
                                                        <span>Fetch Item Details</span>
                                                    </button>
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="raiseIndent">
                                                        <span>Raise Indent</span>
                                                    </button>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : SELECT ITEM CATEGORY DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>


                        <%-- **************************************************************** --%>
                        <%-- ******** START : ITEM(s) LIST DETAIL(S)************************* --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Item</strong> Details</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive" style="overflow-x: scroll">
                                            <table class="table table-custom" style="width:100% !important" id="basic-usage">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th></th>
                                                        <th>All</th>
                                                        <th>All</th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>S.No#</th>
                                                        <th>Item Code</th>
                                                        <th>Item Name</th>
                                                        <th>Category</th>
                                                        <th>In Stock</th>
                                                        <th>Action</th>
                                                    </tr>
                                                <tbody>
                                                    <c:if test="${itemLi != null}">
                                                        <c:set var="loopCount" value="1"/>
                                                        <c:forEach var="itemObj" items="${itemLi}">
                                                            <tr class="gradeX">
                                                                <td>${loopCount}</td>
                                                                <td>${itemObj.itemCode}</td>
                                                                <td>${itemObj.itemName}</td>
                                                                <td>${itemObj.categoryDTO.categoryName}</td>
                                                                <td>${itemObj.currentStock}</td>
                                                                <td>
                                                                    <button type='button' 
                                                                            class="btn btn-rounded btn-info btn-sm">
                                                                            View
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
                                    <!-- tile body -->
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : ITEM(s) LIST DETAIL(S)*********************** --%>
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
        <script src="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.js"/>"></script>
        <script src="<c:url value="/appjs/itemStockJS.js"/>" type="text/javascript"></script>
        <script>
            $(document).ready(function() {
                //$("#categoryID").searchable();
                $("#categoryID").customselect();
            });
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.e1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>