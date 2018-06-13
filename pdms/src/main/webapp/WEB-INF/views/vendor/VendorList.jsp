<%-- 
    Document   : VendorList
    Created on : Oct 18, 2016, 9:04:54 PM
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
                <c:if test="${(userPermiLi.v1 == 1)}">    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Vendor(s) List</title>
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
                        <h2>Vendor(s) <span> Vendor List(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Vendor(s)</a>
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
                        <%-- ************ START : VENDOR LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>

                        <div class="row">
                            <spring:form name="vendorLi" method="post" commandName="vendorObj">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Vendor(s)</strong> List</h1>
                                        <div class="pull-right" style="padding-right: 30px;">
                                            <span class="">
                                                <button type='button' 
                                                        class="btn btn-info btn-rounded btn-sm"
                                                        id="addVendor">
                                                    Add Vendor
                                                </button>
                                            </span>
                                        </div>
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
                                                    </tr>
                                                    <tr>
                                                        <th>Code</th>
                                                        <th>Name</th>
                                                        <th>City</th>
                                                        <th>Phone</th>
                                                        <th>Fax</th>
                                                        <th>Rating</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${vendorLi != null}">
                                                        <c:forEach var="vendorObj" items="${vendorLi}">
                                                            <tr class="gradeX">
                                                                <td>${vendorObj.vendorCode}</td>
                                                                <td>${vendorObj.vendorName}</td>
                                                                <td>${vendorObj.vendorCity}</td>
                                                                <td>${vendorObj.vendorPhone}</td>
                                                                <td>${vendorObj.vendorFax}</td>
                                                                <td>${vendorObj.vendorRating}</td>
                                                                <td>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnEditVendor('${fn:trim(vendorObj.encVendorID)}')">Edit
                                                                    </button>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnViewVendor('${fn:trim(vendorObj.encVendorID)}')">View
                                                                    </button>
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
                        </spring:form>
                    </div>

                    <%-- **************************************************************** --%>
                    <%-- ************ END : VENDOR LIST DETAIL(S)******************** --%>
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
    <script src="<c:url value="/appjs/vendorLiJS.js"/>" type="text/javascript"></script>
</body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.v1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>