<%-- 
    Document   : EditIndentForm
    Created on : Sep 23, 2016, 2:46:37 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e8 == 1)}"> 

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Indent Form</title>
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
            <spring:form name="addIndentForm" commandName="indentObj">
                <section id="content">
                    <div class="page">
                        <!-- == Start :Page Header & BRead Crumbs ======== -->
                        <div class="pageheader">
                            <h2>Indent(s) <span> Edit Indent Form</span></h2>
                            <div class="page-bar">
                                <ul class="page-breadcrumb">
                                    <li>
                                        <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                    </li>
                                    <li>
                                        <a href="#">Indent Form</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- == End :Page Header & BRead Crumbs ======== -->
                        <!-- == Start :Page Form ======== -->
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT INDENT DETAIL(S)********************** --%>
                        <%-- **************************************************************** --%>
                        <div class="pagecontent">

                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Indent</strong> Details</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="username">Indent No#: </label>
                                                <label>${indentSObj.indentNumber} </label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="username">Item Category: </label>
                                                <label>${indentSObj.categoryObj.categoryName} </label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label for="username">Received Date: </label>
                                                <div class='input-group datepicker' data-format="L">
                                                    <input type='text' class="form-control input-sm"
                                                           name="recevidedDate" id="recevidedDate" 
                                                           value="${indentSObj.recevidedDate}"/>
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-calendar"></span>
                                                    </span>
                                                </div>
                                                <label id="errorrecevidedDate" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Indent Date</label>
                                                <div class='input-group datepicker' data-format="L">
                                                    <input type='text' class="form-control input-sm"
                                                           name="indentDate" id="indentDate" 
                                                           value="${indentSObj.indentDate}"/>
                                                    <span class="input-group-addon">
                                                        <span class="fa fa-calendar"></span>
                                                    </span>
                                                </div>
                                                <label id="errorindentDate" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Estimated Cost</label>
                                                <input type="text" name="estimatedCost" id='estimatedCost' 
                                                       class="form-control input-sm"
                                                       maxlength="40" value="${indentSObj.estimatedCost}"/>
                                                <label id="errorestimatedCost" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Approving Authority</label><br/>
                                                <select name="approveAuthorityId" id="approveAuthorityId">
                                                    <option value="0">Select</option>
                                                    <c:if test="${apprAuthLi != null}">
                                                        <c:forEach items="${apprAuthLi}" var="apprObj">
                                                            <c:choose>
                                                                <c:when test="${indentSObj.approveAuthorityId == apprObj.designationID}">
                                                                    <option value="${apprObj.designationID}" selected="true">
                                                                        ${apprObj.designationCode}-(${apprObj.designationName})
                                                                    </option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${apprObj.designationID}">
                                                                        ${apprObj.designationCode}-(${apprObj.designationName})
                                                                    </option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                                <br/>
                                                <label id="errorapproveAuthorityId" style="color: red"></label> 
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Head Of Account</label><br/>
                                                <select name="headOfAccountId" id="headOfAccountId">
                                                    <option value="0">Select</option>
                                                    <c:if test="${hoaLi != null}">
                                                        <c:forEach items="${hoaLi}" var="hoaObj">
                                                            <c:choose>
                                                                <c:when test="${indentSObj.headOfAccountId == hoaObj.headOfAccountID}">
                                                                    <option value="${hoaObj.headOfAccountID}" selected="true">
                                                                        ${hoaObj.accountType}
                                                                    </option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${hoaObj.headOfAccountID}">
                                                                        ${hoaObj.accountType}
                                                                    </option>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                                <br/>
                                                <label id="errorheadOfAccountId" style="color: red"></label>
                                            </div>

                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Place Of Delivery</label><br/>
                                                <select name="placeOfDeliveryId" id="placeOfDeliveryId">
                                                    <option value="0">Select</option>
                                                    <c:if test="${podLi != null}">
                                                        <c:forEach items="${podLi}" var="podObj">
                                                            <c:choose>
                                                                <c:when test="${indentSObj.placeOfDeliveryId == podObj.placeOfDeliveryID}">
                                                                    <option value="${podObj.placeOfDeliveryID}" selected="true">
                                                                        ${podObj.placeOfDelivery}
                                                                    </option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${podObj.placeOfDeliveryID}">
                                                                        ${podObj.placeOfDelivery}
                                                                    </option>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                                <br/>
                                                <label id="errorplaceOfDeliveryId" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>MTA</label>
                                                <input type="text" name="mta" id='mta' 
                                                       class="form-control input-sm"
                                                       maxlength="40" value="${indentSObj.mta}"/>
                                                <label id="errormta" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label class="checkbox checkbox-custom mr-10">
                                                    <c:choose>
                                                        <c:when test="${indentSObj.indentTypeCTQ == 'CTQ'}">
                                                            <input type="checkbox" name="indentTypeCTQ" id="indentType"
                                                                   value="CTQ" checked="true">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="checkbox" name="indentTypeCTQ" id="indentType"
                                                                   value="CTQ">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <i></i> CTQ
                                                </label>
                                                <label id="errorindentType" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label class="checkbox checkbox-custom mr-10">
                                                    <c:choose>
                                                        <c:when test="${indentSObj.indentTypeProp==PROPRIETARY}">
                                                            <input type="checkbox" name="indentTypeProp" id="indentType"
                                                                   value="PROPRIETARY" checked="true">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="checkbox" name="indentTypeProp" id="indentType"
                                                                   value="PROPRIETARY">
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <i></i> PROPRIETARY
                                                </label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Desired Delivery Schedule</label>
                                                <textarea name="descriptionDetails" id="descriptionDetails"
                                                          class="form-control input-sm" rows="" cols="">${indentSObj.descriptionDetails}</textarea>
                                                <label id="errordescriptionDetails" style="color: red"></label>    
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Brief Description</label>
                                                <textarea name="briefDescription" id="briefDescription"
                                                          class="form-control input-sm" rows="" cols="">${indentSObj.briefDescription}</textarea>
                                                <label id="errorbriefDescription" style="color: red"></label>
                                            </div>
                                            <div class="form-group col-md-6 col-lg-4">
                                                <label>Other Information</label>
                                                <textarea name="otherInformation" id="otherInformation"
                                                          class="form-control input-sm" rows="" cols="">${indentSObj.otherInformation}</textarea>
                                                <label id="errorotherInformation" style="color: red"></label>          
                                            </div>
                                        </div>        
                                    </div>
                            </div>
                            <!-- tile body -->
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
                                                    <th>Select</th>
                                                    <th>Item Code</th>
                                                    <th>Item Name</th>
                                                    <th>Required Qty.</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:if test="${indentSObj.itemDTOLi != null}">
                                                    <c:set var="loopCount" value="0"/>
                                                    <c:forEach var="itemObj" items="${itemLi}">
                                                        <c:set var="chkFlag" value="0" />
                                                        <c:forEach var="selItemObj" items="${indentSObj.itemDTOLi}">
                                                            <c:if test="${itemObj.itemID == selItemObj.itemID}">
                                                                <c:set var="chkFlag" value="1" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <tr class="gradeX">
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${chkFlag == 1}">
                                                                        <input type="checkbox" name="itemDTOLi[${loopCount}].itemID" 
                                                                               id="selItem"
                                                                                value="${itemObj.itemID}"
                                                                                checked="true">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type="checkbox" name="itemDTOLi[${loopCount}].itemID" 
                                                                               id="selItem"
                                                                                value="${itemObj.itemID}">
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                
                                                            </td>
                                                            <td>${itemObj.itemCode}</td>
                                                            <td>${itemObj.itemName}</td>
                                                            <td>
                                                                <input type="text" name="itemDTOLi[${loopCount}].requiredStock" id='reqQty' 
                                                                       class="form-control input-sm"
                                                                       maxlength="6"
                                                                       onKeyPress="return numbersonly(this, event)"/>
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
                    <%-- ****************** END : ITEM LIST DETAIL(S)******************** --%>
                    <%-- **************************************************************** --%>
                    <div class="row">
                        <div class="col-md-12">
                            <span class="tools pull-right">
                                <input type="button" value="Update" class="btn btn-info" id="updateIndent"/> 
                                <input type="button" value="Cancel" class="btn btn-info " id="cancelEdit" />
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

<script src="<c:url value="/appjs/indentFormJS.js"/>" type="text/javascript"></script>
<!-- ===============================================
============== Page Specific Scripts ===============
================================================ -->
</div>
</body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.e8 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>