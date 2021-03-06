<%-- 
    Document   : EditVendor
    Created on : Oct 19, 2016, 10:57:32 AM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.v4 == 1)}">   
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
                        <h2>Vendor(s) <span> Vendor Form</span></h2>
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
                        <div id="rootwizard" class="tab-container tab-wizard">
                            <ul class="nav nav-tabs nav-justified">
                                <li class="active">
                                    <a href="javascript:void(0)">
                                        Vendor Information <span class="badge badge-default pull-right wizard-step">1</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="<c:url value="vendoritems.htm?vdi=${fn:trim(sVendorObj.encVendorID)}"/>">
                                        Vendor Items<span class="badge badge-default pull-right wizard-step">2</span>
                                    </a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <spring:form name="addVendorForm" commandName="vendorObj">
                                    <input type="hidden" name="encVendorID" value="${sVendorObj.encVendorID}"/>
                                    <input type="hidden" name="exisVendorName" id="exisVendorName"
                                           value="${sVendorObj.vendorName}"/>
                                    <div class="tab-pane" id="tab1">
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorCode">Vendor Code: </label>
                                                <input type="text" name="vendorCode" id='vendorCode' 
                                                       class="form-control input-sm"
                                                       maxlength="10"
                                                       value="${sVendorObj.vendorCode}"
                                                       readonly="true"/>
                                                <label id="errorvendorCode" style="color: red"></label>
                                            </div>
                                            
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorName">Vendor Name: </label>
                                                <input type="text" name="vendorName" id='vendorName' 
                                                       class="form-control input-sm"
                                                       maxlength="120"
                                                       value="${sVendorObj.vendorName}"/>
                                                <label id="errorvendorName" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorName">Contact Person: </label>
                                                <input type="text" name="contactPerson" id='contactPerson' 
                                                       class="form-control input-sm"
                                                       maxlength="120"
                                                       value="${sVendorObj.contactPerson}"/>
                                                <label id="errorContactPerson" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorAddress">Address: </label>
                                                <textarea class="form-control" name="vendorAddress" 
                                                          id="vendorAddress">${sVendorObj.vendorAddress}</textarea>
                                                <label id="errorvendorAddress" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorCity">City: </label>
                                                <input type="text" name="vendorCity" id='vendorCity' 
                                                       class="form-control input-sm"
                                                       maxlength="20"
                                                       value="${sVendorObj.vendorCity}"/>
                                                <label id="errorvendorCity" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorPin">Pin code: </label>
                                                <input type="text" name="vendorPin" id='vendorPin' 
                                                       class="form-control input-sm"
                                                       maxlength="10"
                                                       onKeyPress="return numbersonly(this, event)"
                                                       value="${sVendorObj.vendorPin}"/>
                                                <label id="errorvendorPin" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorEmail">Email: </label>
                                                <input type="text" name="vendorEmail" id='vendorEmail' 
                                                       class="form-control input-sm"
                                                       maxlength="120"
                                                       value="${sVendorObj.vendorEmail}"/>
                                                <label id="errorvendorEmail" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorPhone">Phone: </label>
                                                <input type="text" name="vendorPhone" id='vendorPhone' 
                                                       class="form-control input-sm"
                                                       maxlength="12"
                                                       onKeyPress="return numbersonly(this, event)"
                                                       value="${sVendorObj.vendorPhone}"/>
                                                <label id="errorvendorPhone" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="vendorFax">Fax: </label>
                                                <input type="text" name="vendorFax" id='vendorFax' 
                                                       class="form-control input-sm"
                                                       maxlength="12"
                                                       onKeyPress="return numbersonly(this, event)"
                                                       value="${sVendorObj.vendorFax}"/>
                                                <label id="errorvendorFax" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-3">
                                                <label for="vendorPan">PAN No#: </label>
                                                <input type="text" name="vendorPan" id='vendorPan' 
                                                       class="form-control input-sm"
                                                       maxlength="10"
                                                       value="${sVendorObj.vendorPan}"/>
                                                <label id="errorvendorPan" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-1">
                                                <label for="vendorPhone">Rating: </label>
                                                <input type="text" name="vendorRating" id='vendorRating' 
                                                       class="form-control input-sm"
                                                       maxlength="1"
                                                       onKeyPress="return numbersonly(this, event)"
                                                       value="${sVendorObj.vendorRating}"/>
                                                <label id="errorvendorRating" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="source">Source: </label>
                                                <textarea class="form-control" name="source" 
                                                          id="source">${sVendorObj.source}</textarea>
                                                <label id="errorsource" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="source">Comments: </label>
                                                <textarea class="form-control" name="comments" 
                                                          id="comments">${sVendorObj.comments}</textarea>
                                                <label id="errorcomments" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="registrationType">Registration Type: </label>
                                                <select name="registrationType" id="registrationType" 
                                                        class="form-control">
                                                    <option value="0">Select</option>
                                                    <c:forEach var="regObj" items="${vendroRegType}">\
                                                        <c:choose>
                                                            <c:when test="${sVendorObj.registrationType == regObj.lookUpName}">
                                                                <option value="${regObj.lookUpName}" selected="true">${regObj.lookUpName}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${regObj.lookUpName}">${regObj.lookUpName}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        
                                                    </c:forEach>
                                                        <%--
                                                    <c:choose>
                                                        <c:when test="${sVendorObj.registrationType == 'Registered'}">
                                                            <option value="Registered" selected="true">Registered</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="Registered">Registered</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${sVendorObj.registrationType == 'Un-Registered'}">
                                                            <option value="Un-Registered" selected="true">Un-Registered</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="Un-Registered">Un-Registered</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                        --%>
                                                </select>
                                                <label id="errorregistrationType" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4" id="expDate">
                                                <label for="expiraryDate">Expiry Date: </label>
                                                <div class='input-group datepicker' data-format="L">
                                                    <input type='text' class="form-control input-sm"
                                                           name="expiraryDate" id="expiraryDate"
                                                           value="${sVendorObj.expiraryDate}"/>
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-calendar"></span>
                                                    </span>
                                                </div>
                                                <label id="errorexpiraryDate" style="color: red"></label>
                                            </div>
                                        </div>
                                    </div>
                                    <ul class="pager wizard">
                                        <li class="next">
                                            <button class="btn btn-info" id="updateVendor"
                                                    type="button">Update & Continue</button>
                                        </li>
                                    </ul>
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
    <c:if test="${(userPermiLi.v4 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>