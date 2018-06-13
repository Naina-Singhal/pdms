<%-- 
    Document   : SourceViewUpdate
    Created on : Oct 23, 2016, 12:10:10 PM
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
                <c:if test="${(userPermiLi.v3 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Source View & Update</title>
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
                        <h2>Source View & Update<span> </span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Source View & Update</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">   

                        <%-- **************************************************************** --%>
                        <%-- ******** START : SELECT ITEM DETAIL(S)************************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="srcUpdForm" commandName="itemObj" method="post">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Select Item </strong> Details</h1>
                                            <div class="row">&nbsp;</div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="itemID">Item Name </label>
                                                    <br/>
                                                    <select name="itemID" id='itemID' class="form-control custom-select">
                                                        <option value="0">Select</option>
                                                        <c:if test="${itemLi != null}">
                                                            <c:forEach items="${itemLi}" var="itemObj">
                                                                <c:choose>
                                                                    <c:when test="${selItemId == itemObj.itemID}">
                                                                        <option value="${itemObj.itemID}" 
                                                                                selected="true">${itemObj.itemCode} ${itemObj.itemName}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${itemObj.itemID}">${itemObj.itemCode} ${itemObj.itemName}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <br/>
                                                    <label id="erroritemID" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-4 p-25">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="fetchVDtls">
                                                        <span>Fetch Vendor Details</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ******** START : SELECT ITEM DETAIL(S)************************** --%>
                        <%-- **************************************************************** --%>

                        <%-- **************************************************************** --%>
                        <%-- ******** START : VENDOR DETAIL(S)******************************* --%>
                        <%-- **************************************************************** --%>
                        <c:if test="${vendorLi != null}">
                            <spring:form name="updSourceForm" commandName="vItemObj" method="post">
                                <input type="hidden" name="itemID" value="${selItemId}"/>
                                <div class="row">
                                    <div class="col-md-12">
                                        <label id="errorselVendorList" style="color: red"></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <!-- col -->
                                    <div class="col-md-12">
                                        <!-- tile -->
                                        <section class="tile">
                                            <!-- tile header -->
                                            <div class="tile-header dvd dvd-btm">
                                                <h1 class="custom-font"><strong>Vendor(s)</strong> Details</h1>
                                            </div>
                                            <!-- tile header -->
                                            <!-- tile body -->
                                            <div class="tile-body">
                                                <div class="table-responsive" style="overflow-x: scroll">
                                                    <table class="table table-custom" style="width:100% !important" 
                                                           id="vendor-detls">
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
                                                            </tr>
                                                            <tr>
                                                                <th>&nbsp;</th>
                                                                <th>Indent No#</th>
                                                                <th>Vendor Code</th>
                                                                <th>Vendor Name</th>
                                                                <th>City</th>
                                                                <th>Registration</th>
                                                                <th>Sent</th>
                                                                <th>Recorded</th>
                                                                <th>Ordered</th>
                                                                
                                                            </tr>
                                                        <tbody>
                                                            <c:if test="${vendorLi != null}">
                                                                <c:set var="loopCount" value="0"/>
                                                                <c:forEach var="vendObj" items="${vendorLi}">
                                                                    <tr class="gradeX">
                                                                        <td>
                                                                            <c:choose>
                                                                                <c:when test="${vendObj.rowStatusValue == constants.INDENT_STATUS_RECORDED}">
                                                                                   <input type="checkbox" name="selVendorList" 
                                                                                        id="selVendorList"
                                                                                        value="${vendObj.indentID}"
                                                                                        checked="true" disabled="true">
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <input type="checkbox" name="selVendorList" 
                                                                                           id="selVendorList"
                                                                                           value="${vendObj.indentID}">
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </td>
                                                                        <td>${vendObj.indentFormObj.indentNumber}</td>
                                                                        <td>${vendObj.vendorCode}</td>
                                                                        <td>${vendObj.vendorName}</td>
                                                                        <td>${vendObj.vendorCity}</td>
                                                                        <td>${vendObj.registrationType}</td>
                                                                        <td>${vendObj.sentCount}</td>
                                                                        <td>${vendObj.recordedCount}</td>
                                                                        <td>${vendObj.orderedCount}</td>
                                                                        <%--
                                                                        <td>
                                                                            <select name="selOptionArr" id="selOptionArr">
                                                                                <option value="0">Select</option>
                                                                                <option value="1">Record</option>
                                                                                <option value="2">Order</option>
                                                                            </select>
                                                                        </td>
                                                                    <input type="hidden" name="vendorIDArr" value="${vendObj.vendorID}"/>
                                                                    <input type="hidden" name="indentIDArr" value="${vendObj.indentFormObj.indentFormID}"/>
                                                                        --%>
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
                                <div class="row">
                                    <div class="col-md-12">
                                        <span class="tools pull-right">
                                            <button type='button' 
                                                    class="btn btn-info btn-rounded btn-sm"
                                                    id="updateSrcIndent">Record Vendor(s)
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </spring:form>
                            <%-- **************************************************************** --%>
                            <%-- ******************** END : VENDOR DETAILS ********************** --%>
                            <%-- **************************************************************** --%>
                        </c:if>

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
        <script src="<c:url value="/appjs/SrcViewUpdJS.js"/>" type="text/javascript"></script>
        <script>

        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.v3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>