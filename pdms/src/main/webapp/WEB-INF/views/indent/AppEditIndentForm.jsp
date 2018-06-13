<%-- 
    Document   : AppEditIndentForm
    Created on : Oct 7, 2016, 4:50:11 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e6 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Indent Form</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <link rel="stylesheet" href="<c:url value="assets/js/vendor/file-upload/css/jquery.fileupload.css"/>">
        <link rel="stylesheet" href="<c:url value="assets/js/vendor/file-upload/css/jquery.fileupload-ui.css"/>">
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/assets/js/vendor/jquery-ui/jquery.blockUI.js"/>"></script>
    </head>

    <body id="minovate" class="appWrapper">
        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>    
            <spring:form name="addIndentForm" commandName="indentObj" enctype="multipart/form-data">
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
                                                    <label for="username">Indent No#: </label>
                                                    <label>${indentSObj.indentNumber} </label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Item Category: </label>
                                                    <label>${indentSObj.categoryObj.categoryName} </label>
                                                </div>
                                            </div>
                                            <div class="row">
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
                                                    <label>Signing Authority</label><br/>
                                                    <select name="signingAuthorityId" id="signingAuthorityId">
                                                        <option value="0">Select</option>
                                                        <c:if test="${signAuthLi != null}">
                                                            <c:forEach items="${signAuthLi}" var="apprObj">
                                                                <c:choose>
                                                                    <c:when test="${indentSObj.signingAuthorityId == apprObj.designationID}">
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
                                                    <label id="errorsigningAuthorityId" style="color: red"></label> 
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
                                </div>
                            </div>
                            <!-- tile body -->
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
                                                    <label id="erroritemId" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage">
                                                    <thead>
                                                        <tr>
                                                            <th>Select</th>
                                                            <th>Indent SlNo</th>
                                                            <th>Item Code</th>
                                                            <th>Item Name</th>
                                                            <th>Required Qty.</th>
                                                            <th>Unit(s)</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:if test="${indentSObj.itemDTOLi != null}">
                                                            <% int indslno = 1; %>
                                                            <c:set var="loopCount" value="0"/>
                                                            <c:forEach var="itemObj" items="${itemLi}">
                                                                <c:out value="First Loop"/>
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
                                                                    <td><input type="text" name="itemDTOLi[${loopCount}].indentSlNo" id="selItem"
                                                                        style="border: none;width: 70px;" value="<%=(indslno++)%>"></td>
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
                                                  <span class="tools pull-right" style="padding-right: 40px !important">
                                                      <button type='button' 
                                                              class="btn btn-info btn-rounded btn-sm"
                                                              id="addUpdIndFiles"
                                                              butval="${appStat}"
                                                              data-toggle="modal" 
                                                              data-target="#uploadModal">Add/ Update Files
                                                      </button>
                                                  </span>
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
                                                                  <th>Action</th>
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
                                                                      <td>
                                                                          <button type="button" class="btn btn-primary btn-sm"
                                                                                  id="apprIndDeleteUpFile" 
                                                                                  upid="${indFileObj.encFieldValue}"
                                                                                  indid="${indentSObj.encFieldValue}">
                                                                              <i class="fa fa-minus-circle"></i> Delete
                                                                          </button>
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

                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="tools pull-right">
                                    <button type='button' 
                                            class="btn btn-info btn-rounded btn-sm"
                                            id="appApproveIndent"
                                            butval="${appStat}">Approve
                                    </button>
                                    <button type='button' 
                                            class="btn btn-info btn-rounded btn-sm"
                                            data-toggle="modal" 
                                            data-target="#myModal">Revert
                                    </button>
                                    <button type='button' 
                                            class="btn btn-info btn-rounded btn-sm"
                                            data-toggle="modal" 
                                            data-target="#rejModal">Reject
                                    </button>
                                    <button type='button' 
                                            class="btn btn-info btn-rounded btn-sm"
                                            id="appCancelIndent">Cancel
                                    </button>
                                </span>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- File Upload Model-->

                <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Upload File</h3>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <!-- tile -->
                                        <section class="tile">
                                            <!-- tile body -->
                                            <div class="tile-body">
                                                <div class="panel panel-primary">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">File Upload Notes</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <ul>
                                                            <li>The maximum file size for uploads in this application is <strong>5 MB</strong> (default file size is unlimited).</li>
                                                            <li>Only file types of (<strong>JPG, GIF, PNG, PDF, DOC, DOCX, XLS, XLSX</strong>) are allowed.</li>
                                                            <li>Currently, we are providing an Option for Uploading one file at a time.</li>
                                                            <li>Uploaded files will be deleted when Delete option is selected.</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <input id="uploadFile" placeholder="Choose File" disabled="disabled" />
                                                        <span class="btn btn-success fileinput-button">
                                                            <i class="glyphicon glyphicon-plus"></i>
                                                            <span>Browse file...</span>
                                                            <input type="file" name="indentFile" id="indentFile">
                                                        </span>
                                                        <br/>
                                                        <label id="errorindentFile" style="color: red"></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary start" id="apprIndStartUpload" data-dismiss="modal">
                                    <i class="glyphicon glyphicon-upload"></i>
                                    <span>Start upload</span>
                                </button>
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button>    
                            </div>
                        </div>
                    </div>
                </div>

                <!-- File Upload Model-->

                <!-- Revert/ Reject Comments Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Revert Comments!</h3>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <textarea name="comments" id="comments"
                                                  class="form-control input-sm" rows="" cols=""></textarea>
                                        <label id="errorcomments" style="color: red"></label>    
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success btn-ef btn-ef-3 btn-ef-3c"
                                        id="appRevertIndent" butval="${revStat}">
                                    <i class="fa fa-arrow-right"></i> Submit
                                </button>
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button>    
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="rejModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Reject Comments!</h3>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <textarea name="comments" id="rejcomments"
                                                  class="form-control input-sm" rows="" cols=""></textarea>
                                        <label id="errorrejcomments" style="color: red"></label>    
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success btn-ef btn-ef-3 btn-ef-3c"
                                        id="appRejectIndent" butval="${rejStat}">
                                    <i class="fa fa-arrow-right"></i> Submit
                                </button>
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button> 
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Forgot Password Modal -->                    


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
    <c:if test="${(userPermiLi.e6 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>