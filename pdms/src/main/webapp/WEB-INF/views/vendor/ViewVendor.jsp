<%-- 
    Document   : ViewVendor
    Created on : Oct 19, 2016, 2:54:10 PM
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
                <c:if test="${(userPermiLi.v8 == 1)}"> 
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
            <spring:form name="vIndentForm" commandName="vendorObj">
                <section id="content">
                    <div class="page">
                        <!-- == Start :Page Header & BRead Crumbs ======== -->
                        <div class="pageheader">
                            <h2>Vendor(s) <span> View Vendor Form</span></h2>
                            <div class="page-bar">
                                <ul class="page-breadcrumb">
                                    <li>
                                        <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                    </li>
                                    <li>
                                        <a href="#">Vendor Form</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- == End :Page Header & BRead Crumbs ======== -->
                        <!-- == Start :Page Form ======== -->
                        <div class="pagecontent">
                            <%-- **************************************************************** --%>
                            <%-- ******** START : ADD/EDIT INDENT DETAIL(S)********************** --%>
                            <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Vendor</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorCode">Vendor Code: </label><br/>
                                                    <label>${sVendorObj.vendorCode}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorName">Vendor Name: </label><br/>
                                                    <label>${sVendorObj.vendorName}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorPhone">Rating: </label><br/>
                                                    <label>${sVendorObj.vendorRating}</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorAddress">Address: </label><br/>
                                                    <label>${sVendorObj.vendorAddress}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorCity">City: </label><br/>
                                                    <label>${sVendorObj.vendorCity}</label>
                                                </div>

                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorPin">Pin code: </label><br/>
                                                    <label>${sVendorObj.vendorPin}</label><br/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorEmail">Email: </label><br/>
                                                    <label>${sVendorObj.vendorEmail}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorPhone">Phone: </label><br/>
                                                    <label>${sVendorObj.vendorPhone}</label>
                                                </div>

                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorFax">Fax: </label><br/>
                                                    <label>${sVendorObj.vendorFax}</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="vendorPan">PAN No#: </label><br/>
                                                    <label>${sVendorObj.vendorPan}</label>
                                                </div>
                                                
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="source">Source: </label><br/>
                                                    <label>${sVendorObj.source}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="source">Comments: </label><br/>
                                                    <label>${sVendorObj.comments}</label>
                                                </div>
                                            </div>
                                            
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="registrationType">Registration Type: </label><br/>
                                                    <label>${sVendorObj.registrationType}</label>,
                                                </div>
                                                <c:if test="${sVendorObj.registrationType == 'Registered'}">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="expiraryDate">Expiry Date: </label><br/>
                                                        <label>${sVendorObj.expiraryDate}</label>,
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                            <%-- **************************************************************** --%>
                            <%-- ******** END : ADD/EDIT INDENT DETAIL(S)************************ --%>
                            <%-- **************************************************************** --%>

                            <%-- **************************************************************** --%>
                            <%-- ************ START : ITEM LIST DETAIL(S)************************ --%>
                            <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Items(s)</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                        <tr>
                                                            <th>SNo</th>
                                                            <th>Category</th>
                                                            <th>Item</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:if test="${sVendorObj.vendorItemsLi != null}">
                                                            <c:set var="loopCount" value="1"/>
                                                            <c:forEach var="vItemObj" items="${sVendorObj.vendorItemsLi}">
                                                                <tr class="gradeX">
                                                                    <td>${loopCount}</td>
                                                                    <td>${vItemObj.categoryObj.categoryName}</td>
                                                                    <td>${vItemObj.itemObj.itemName}</td>
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
                            <%-- ****************** END : ITEM LIST DETAIL(S)******************** --%>
                            <%-- **************************************************************** --%>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">
                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelView" />
                                </span>
                            </div>
                        </div>
                    </div>
                </section>
            </spring:form>
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
    <c:if test="${(userPermiLi.v8 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>
