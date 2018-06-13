<%-- 
    Document   : SignEditIndentForm
    Created on : Oct 10, 2016, 2:09:46 PM
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
                <c:if test="${(userPermiLi.e13 == 1)}"> 
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

                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Indent No#: </label><br/>
                                                    <label>${indentSObj.indentNumber} </label>
                                                </div>
                                                <%--
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Received Date: </label><br/>
                                                    <label>${indentSObj.recevidedDate} </label>
                                                </div>
                                                --%>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Date:</label><br/>
                                                    <label>${indentSObj.indentDate}</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Estimated Cost:</label><br/>
                                                    <label> ${indentSObj.estimatedCost} </label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Section Code:</label><br/>
                                                    <label>${indentSObj.sectionObj.sectionCode}-(${indentSObj.sectionObj.sectionName})</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Signing Authority:</label><br/>
                                                    <label>${indentSObj.signingAuthorityObj.designationCode}-
                                                        ${indentSObj.signingAuthorityObj.designationName}</label>
                                                </div>

                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Item Category:</label><br/>
                                                    <label>${indentSObj.categoryObj.categoryCode} - ${indentSObj.categoryObj.categoryName}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Head Of Account:</label><br/>
                                                    <label>${indentSObj.hoaObj.accountType}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>MTA:</label><br/>
                                                    <label>${indentSObj.mta}</label>
                                                </div>


                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>IO Name:</label><br/>
                                                    <label>${indentSObj.empProfileDTo.firstName} ${indentSObj.empProfileDTo.lastName}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Place Of Delivery:</label><br/>
                                                    <label>${indentSObj.podObj.placeOfDelivery}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Type:</label><br/>
                                                    <label>${indentSObj.indentTypeCTQ}</label>,
                                                    <label>${indentSObj.indentTypeProp}</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Desired Delivery Schedule:</label><br/>
                                                    <label>${indentSObj.descriptionDetails}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Brief Description:</label><br/>
                                                    <label>${indentSObj.briefDescription}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Other Information:</label><br/>
                                                    <label>${indentSObj.otherInformation}</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Status:</label><br/>
                                                    <label><b>${indentSObj.indentStatus}</b></label>
                                                </div>
                                                <c:if test="${(indentSObj.indentStatus == constants.INDENT_STATUS_REVERTED)||
                                                              (indentSObj.indentStatus == constants.INDENT_STATUS_REJECTED)}">
                                                      <div class="form-group col-md-6 col-lg-4">
                                                          <label>Comments:</label><br/>
                                                          <label>${indentSObj.comments}</label>
                                                      </div>
                                                </c:if>

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
                                                    <label>Mode Of Purchase:</label><br/>
                                                    <select name="modeOfPurchaseId" id="modeOfPurchaseId">
                                                        <option value="0">Select</option>
                                                        <c:if test="${mopLi != null}">
                                                            <c:forEach items="${mopLi}" var="mopObj">
                                                                <option value="${mopObj.modeOfPurchaseID}">
                                                                    ${mopObj.modeOfPurchase}
                                                                </option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <br/>
                                                    <label id="errormodeOfPurchaseId" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Group</label><br/>
                                                    <select name="groupObj.groupID" id="groupID" class="form-control">
                                                        <option value="0">Select</option>
                                                        <c:if test="${groupLi != null}">
                                                            <c:forEach items="${groupLi}" var="grpObj">
                                                                <option value="${grpObj.groupID}">
                                                                    ${grpObj.groupName}
                                                                </option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <br/>
                                                    <label id="errorgroupID" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Generated File No#</label>
                                                    <input type="text" name="fileNo" value="${fileNo}"
                                                           readonly="true" class="form-control input-sm" id="focusedInput"/>
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
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>Select</th>
                                                            <th>Item Code</th>
                                                            <th>Item Name</th>
                                                            <th>Required Qty.</th>
                                                            <th>Unit(s)</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:if test="${indentSObj.itemDTOLi != null}">
                                                            <c:set var="loopCount" value="1"/>
                                                            <c:forEach var="itemObj" items="${indentSObj.itemDTOLi}">
                                                                <tr class="gradeX">
                                                                    <td>${loopCount}</td>
                                                                    <td>${itemObj.itemCode}</td>
                                                                    <td>${itemObj.itemName}</td>
                                                                    <td>${itemObj.requiredStock}</td>
                                                                    <td>${itemObj.unitDTO.unitName} (${itemObj.unitDTO.unitCode})</td>
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


                            <%-- **************************************************************** --%>
                            <%-- ******** START : UPLOADED INDENT FILE(s) *********************** --%>
                            <%-- **************************************************************** --%>
                            <c:if test="${(indentSObj.indentFormFileLi != null) && 
                                          (fn:length(indentSObj.indentFormFileLi) > 0)}">
                                  <div class="row">
                                      <!-- col -->
                                      <div class="col-md-12">
                                          <!-- tile -->
                                          <section class="tile">
                                              <!-- tile header -->
                                              <div class="tile-header dvd dvd-btm">
                                                  <h1 class="custom-font"><strong>Uploaded</strong> File(s) Details</h1>
                                              </div>
                                              <!-- tile header -->
                                              <!-- tile body -->
                                              <div class="tile-body">
                                                  <div class="table-responsive">
                                                      <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                          <thead>
                                                              <tr>
                                                                  <th>S.No</th>
                                                                  <th>File Name</th>
                                                              </tr>
                                                          </thead>
                                                          <tbody>
                                                              <c:set var="lpCnt" value="1"/>
                                                              <c:forEach var="indFileObj" items="${indentSObj.indentFormFileLi}">
                                                                  <tr>
                                                                      <td>${lpCnt}</td>
                                                                      <td>
                                                                          <a href="#" id="indFileDnd"
                                                                             upid="${indFileObj.encFieldValue}"
                                                                             indid="${indentSObj.encFieldValue}">
                                                                              ${indFileObj.fileName}
                                                                          </a>
                                                                      </td>
                                                                  </tr>
                                                                  <c:set var="lpCnt" value="${lpCnt+1}"/>
                                                              </c:forEach>
                                                          </tbody>
                                                      </table>
                                                  </div>
                                              </div>
                                          </section>
                                      </div>
                                  </div>
                            </c:if>
                            <%-- **************************************************************** --%>
                            <%-- ******** END : UPLOADED INDENT FILE(s) *********************** --%>
                            <%-- **************************************************************** --%>


                            <div class="row">
                                <div class="col-md-12">
                                    <span class="tools pull-right">
                                        <button type='button' 
                                                class="btn btn-info btn-rounded btn-sm"
                                                id="signApproveIndent"
                                                butval="${appStat}">Approve
                                        </button>
                                        <%--
                                        <button type='button' 
                                                class="btn btn-info btn-rounded btn-sm"
                                                data-toggle="modal" 
                                                data-target="#rejModal">Reject
                                        </button>
                                        --%>
                                        <button type='button' 
                                                class="btn btn-info btn-rounded btn-sm"
                                                id="signCancelIndent">Cancel
                                        </button>
                                    </span>
                                </div>
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
    <c:if test="${(userPermiLi.e13 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>