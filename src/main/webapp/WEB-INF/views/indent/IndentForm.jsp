<%-- 
    Document   : IndentForm
    Created on : Sep 22, 2016, 7:17:57 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e11 == 1)}"> 

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
                            <h2>Indent(s) <span> Indent Form</span></h2>
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
                            <%-- ******** START : ADD/EDIT INDENT DETAIL(S)********************** --%>
                            <%-- **************************************************************** --%>
                            <div class="row">
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
                                            <input type="hidden" name="encFieldValue" value="${indentSObj.encFieldValue}"/>
                                            <input type="hidden" name="encRowStatusValue" value="${indentSObj.encRowStatusValue}"/>
                                            <c:if test="${indentSObj.comments != null}">
                                                <div class="row">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">Comments: </label>
                                                        <label for="username">${indentSObj.comments} </label>
                                                    </div>
                                                </div>
                                            </c:if>
                                            
                                            <div class="row">
                                                <c:if test="${(sectionLi != null) &&(fn:length(sectionLi)>0)}">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">Section: </label><br/>
                                                        <select name="sectionId" id="sectionId">
                                                            <option value="0">Select</option>
                                                            <c:forEach items="${sectionLi}" var="secObj">
                                                                <c:choose>
                                                                    <c:when test="${indentSObj.sectionId == secObj.sectionID}">
                                                                        <option value="${secObj.sectionID}" selected="true">
                                                                            [${secObj.sectionCode}] ${secObj.sectionName}
                                                                        </option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${secObj.sectionID}">
                                                                            [${secObj.sectionCode}] ${secObj.sectionName}
                                                                        </option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                        <br/>
                                                        <label id="errorsectionId" style="color: red"></label> 
                                                    </div>
                                                </c:if>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Indent No#: </label>
                                                    <input type="text" name="indentNumber" id='indentNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"
                                                           value="${indentSObj.indentNumber}"/>
                                                    <label id="errorindentNumber" style="color: red"></label>
                                                </div>
                                                    <%--
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
                                                    --%>
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
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Estimated Cost</label>
                                                    <input type="text" name="estimatedCost" id='estimatedCost' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value="${indentSObj.estimatedCost}"
                                                           />
                                                    <label id="errorestimatedCost" style="color: red"></label>
                                                </div>
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

                                            </div>

                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Item Category</label><br/>
                                                    <select name="categoryObj.categoryID" id="categoryID">
                                                        <option value="0">Select</option>
                                                        <c:if test="${catLi != null}">
                                                            <c:forEach items="${catLi}" var="catObj">
                                                                <c:choose>
                                                                    <c:when test="${indentSObj.categoryObj.categoryID == catObj.categoryID}">
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
                                                    <label id="erroritemCategoryId" style="color: red"></label>
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
                                                            <c:when test="${indentSObj.indentTypeProp=='PROPRIETARY'}">
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
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>Select</th>
                                                            <th>Indent Sl No</th>
                                                            <th>Item Code</th>
                                                            <th>Item Name</th>
                                                            <th>Required Qty.</th>
                                                            <th>Unit(s)</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:choose>
                                                            <c:when test="${indentSObj.itemDTOLi != null}">
                                                                <% long indentSlNo = 1; %>
                                                                <c:set var="loopCount" value="0"/>
                                                                <c:forEach var="itemObj" items="${itemLi}">
                                                                    <c:set var="chkFlag" value="0" />
                                                                    <c:set var="reqStock" value="" />
                                                                    <c:forEach var="selItemObj" items="${indentSObj.itemDTOLi}">
                                                                        <c:if test="${itemObj.itemID == selItemObj.itemID}">
                                                                            <c:set var="chkFlag" value="1" />
                                                                            <c:set var="reqStock" value="${selItemObj.requiredStock}" />
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
                                                                        <td><input type="text" name="itemDTOLi[${loopCount}].indentSlNo" 
                                                                                           id="indentSlNo"
                                                                                           value="<%=(indentSlNo++)%>"></td>
                                                                        <td>${itemObj.itemCode}</td>
                                                                        <td>${itemObj.itemName}</td>
                                                                        <td>
                                                                            <input type="text" name="itemDTOLi[${loopCount}].requiredStock" id='reqQty' 
                                                                                   class="form-control input-sm col-lg-3"
                                                                                   maxlength="6"
                                                                                   onKeyPress="return numbersonly(this, event)"
                                                                                   value="${reqStock}"/>
                                                                        </td>
                                                                        <td>${itemObj.unitDTO.unitName} (${itemObj.unitDTO.unitCode})</td>
                                                                    </tr>
                                                                    <c:set var="loopCount" value="${loopCount+1}"/>
                                                                </c:forEach>

                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:if test="${itemLi != null}">
                                                                    <c:set var="loopCount" value="0"/>
                                                                    <c:forEach var="itemObj" items="${itemLi}">
                                                                        <tr class="gradeX">
                                                                            <td><input type="checkbox" name="itemDTOLi[${loopCount}].itemID" id="selItem"
                                                                                       value="${itemObj.itemID}"></td>
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
                                                            </c:otherwise>
                                                        </c:choose>

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
                                    <c:choose>
                                        <c:when test="${indentStat != null}">
                                            <input type="button" value="Update" class="btn btn-info" id="updateIndent"/> 
                                        </c:when>
                                        <c:otherwise>
                                            <input type="button" value="Save" class="btn btn-info" id="saveIndent"/> 
                                        </c:otherwise>
                                    </c:choose>
                                    
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
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.e11 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>