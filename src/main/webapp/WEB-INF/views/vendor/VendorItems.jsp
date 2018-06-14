<%-- 
    Document   : VendorItems
    Created on : Oct 19, 2016, 7:14:35 AM
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
                <c:if test="${(userPermiLi.v7 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Vendor Form</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/assets/js/vendor/jquery-ui/jquery.blockUI.js"/>"></script>
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
                        <h2>Vendor(s) <span> Vendor Item(s) Form</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Vendor Item(s)Form</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <div id="rootwizard" class="tab-container tab-wizard">
                            <ul class="nav nav-tabs nav-justified">
                                <li>
                                    <a href="<c:url value="editvendor.htm?vdi=${fn:trim(encVendorID)}"/>">
                                        Vendor Information <span class="badge badge-default pull-right wizard-step">1</span>
                                    </a>
                                </li>
                                <li class="active"><a href="javascript:void(0)">Vendor Items<span class="badge badge-default pull-right wizard-step">2</span></a></li>
                            </ul>
                            <div class="tab-content">
                                <spring:form name="vendorItemsForm" commandName="vendorItemsObj">
                                    <input type="hidden" name="encVendorID" value="${encVendorID}"/>
                                    <div class="tab-pane" id="tab1">
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="categoryID">Category: </label>
                                                <select name="categoryID" id="categoryID">
                                                    <option value="0">Select</option>
                                                    <c:if test="${catLi != null}">
                                                        <c:forEach items="${catLi}" var="catObj">
                                                            <c:choose>
                                                                <c:when test="${sVendorItemsObj.categoryID == catObj.categoryID}">
                                                                    <option value="${catObj.categoryID}" selected="true">
                                                                        ${catObj.categoryCode} (${catObj.categoryName})
                                                                    </option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${catObj.categoryID}">
                                                                        ${catObj.categoryCode} (${catObj.categoryName})
                                                                    </option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                                <br/>
                                                <label id="errorcategoryID" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                        id="fetchItemDtls">
                                                    <span>Fetch Item Details</span>
                                                </button>
                                            </div>
                                        </div>
                                        <c:if test="${itemLi != null}">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <!-- tile -->
                                                    <section class="tile">
                                                        <!-- tile header -->
                                                        <div class="tile-header dvd dvd-btm">
                                                            <h1 class="custom-font"><strong>
                                                                    Item(s)</strong> List
                                                            </h1>
                                                        </div>
                                                        <!-- tile header -->
                                                        <!-- tile body -->
                                                        <div class="tile-body">
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <label id="errorItemID" style="color: red"></label>
                                                                </div>
                                                            </div>
                                                            <c:forEach items="${itemLi}" var="itemObj">
                                                                <c:set var="chkValue" value="0"/>
                                                                <c:forEach items="${vendorItemLi}" var="vendorItemObj">
                                                                    <c:if test="${(itemObj.itemID == vendorItemObj.itemID)
                                                                          &&(vendorItemObj.rowStatusValue == constants.ENTITY_STATUS_ACTIVE_CONSTANT)}">
                                                                        <c:set var="chkValue" value="1"/>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <div class="form-group col-md-6 col-lg-4">
                                                                    <label class="checkbox checkbox-custom mr-10">
                                                                        <c:choose>
                                                                            <c:when test="${chkValue == 1}">
                                                                                <input type="checkbox" name="selItemList"
                                                                                       id="itemID"
                                                                                       checked="true"
                                                                                       value="${itemObj.itemID}">
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input type="checkbox" name="selItemList"
                                                                                       id="itemID"
                                                                                       value="${itemObj.itemID}">
                                                                            </c:otherwise>
                                                                        </c:choose>

                                                                        <i></i> ${itemObj.itemName}[${itemObj.itemCode}]
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                        <!-- tile body -->
                                                    </section>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <span class="tools pull-right">
                                                    <button class="btn btn-info" id="addVendorItem"
                                                            type="button">Add Items</button>
                                                </span>
                                            </div>
                                        </div>

                                        <div class="row"> 
                                            <div class="col-md-12">
                                                <!-- tile -->
                                                <section class="tile">
                                                    <!-- tile header -->
                                                    <div class="tile-header dvd dvd-btm">
                                                        <h1 class="custom-font"><strong>Vendor Item(s)</strong> List</h1>
                                                    </div>
                                                    <!-- tile header -->
                                                    <!-- tile body -->
                                                    <div class="tile-body">
                                                        <div class="table-responsive">
                                                            <table class="table table-custom display" id="basic-usage" cellspacing="0" style="width:100% !important">
                                                                <thead>
                                                                    <tr>
                                                                        <th>All</th>
                                                                        <th></th>
                                                                        <th></th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Category</th>
                                                                        <th>Item</th>
                                                                        <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:if test="${vendorItemLi != null}">
                                                                        <c:forEach var="vendorItemObj" items="${vendorItemLi}">
                                                                            <tr class="gradeX">
                                                                                <td>${vendorItemObj.categoryObj.categoryName}</td>
                                                                                <td>${vendorItemObj.itemObj.itemName}</td>
                                                                                <td>
                                                                                    <c:choose>
                                                                                        <c:when test="${vendorItemObj.rowStatusValue == constants.ENTITY_STATUS_IN_ACTIVE_CONSTANT}">
                                                                                            <button type='button' 
                                                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                                                    onclick="fnEnableVIEntry('${fn:trim(vendorItemObj.encVendorItemID)}',
                                                                                                        '${fn:trim(encVendorID)}')">Map
                                                                                            </button>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <button type='button' 
                                                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                                                    onclick="fnDeleteVIEntry('${fn:trim(vendorItemObj.encVendorItemID)}',
                                                                                                        '${fn:trim(encVendorID)}')">Un Map
                                                                                            </button>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                    
                                                                                </td>
                                                                            </tr>
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
                                        <%--
                                        <div class="row">
                                            <div class="col-md-12">
                                                <span class="tools pull-right">
                                                    <button class="btn btn-info" id="subVendorItem"
                                                            type="button">Submit</button>
                                                </span>
                                            </div>
                                        </div>
                                        --%>
                                    </div>

                                </spring:form>
                            </div>
                        </div>
                        <!-- == End :Page Form ======== -->
                    </div>
            </section>

        </div>
        <!-- ====================================================
    ================= End : Application Content =============
    ===================================================== -->

        <script src="<c:url value="/appjs/vendorFormJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.v7 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>