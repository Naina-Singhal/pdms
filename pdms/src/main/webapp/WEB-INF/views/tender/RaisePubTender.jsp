<%-- 
    Document   : RaisePubTender
    Created on : Oct 25, 2016, 3:24:31 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.t6 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Raise Public Tender</title>
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
                        <h2>Tender Form <span> Raise Public Tender</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Tender Form</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">       
                        <%-- **************************************************************** --%>
                        <%-- ******** START : INDENT DETAIL(S)******************************* --%>
                        <%-- **************************************************************** --%>
                        <c:if test="${indentSObj != null}">
                            <spring:form name="iTenderForm" commandName="tenderObj" method="post">
                                <input type="hidden" name="encFieldValue" id="encFieldValue" value="${indentSObj.encFieldValue}"/>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="headingOne">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
                                                           aria-expanded="false" aria-controls="collapseOne" class="collapsed">
                                                            <h4 class="custom-font">
                                                                <strong>Indent</strong> Details
                                                            </h4>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                                                    <div class="panel-body">
                                                        <div class="row">
                                                            <div class="form-group col-md-6 col-lg-3">
                                                                <label for="username">Indent No#: </label><br/>
                                                                <label>${indentSObj.indentNumber} </label>
                                                            </div>
                                                            <div class="form-group col-md-6 col-lg-3">
                                                                <label>Indent Date:</label><br/>
                                                                <label>${indentSObj.indentDate}</label>
                                                            </div>
                                                            <div class="form-group col-md-6 col-lg-3">
                                                                <label>Estimated Cost:</label><br/>
                                                                <label> ${indentSObj.estimatedCost} </label>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="form-group col-md-6 col-lg-3">
                                                                <label>Group Name:</label><br/>
                                                                <label>${indentSObj.mappedGroupName}</label>
                                                            </div>
                                                            <div class="form-group col-md-6 col-lg-3">
                                                                <label>File No:</label><br/>
                                                                <label>${indentSObj.fileNo}</label>
                                                            </div>
                                                            <div class="form-group col-md-6 col-lg-3">
                                                                <label>Item Category:</label><br/>
                                                                <label>${indentSObj.categoryObj.categoryCode} - 
                                                                    ${indentSObj.categoryObj.categoryName}</label>
                                                            </div>
                                                        </div>
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
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <!-- col -->
                                    <div class="col-md-12">
                                        <!-- tile -->
                                        <section class="tile">
                                            <!-- tile header -->
                                            <div class="tile-header dvd dvd-btm">
                                                <h1 class="custom-font"><strong>Indent</strong> Tender Details</h1>
                                            </div>
                                            <!-- tile header -->
                                            <!-- tile body -->
                                            <div class="tile-body">
                                                <div class="row">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="tenderTypeID">Tender Type: </label><br/>
                                                        <select name="tenderTypeID" id="tenderTypeID">
                                                            <option value="0">Select</option>
                                                            <c:forEach items="${tenderTypes}" var="lookupObj">
                                                                <option value="${lookupObj.lookUpID}">
                                                                    ${lookupObj.lookUpName}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                        <br/>
                                                        <label id="errorsectionId" style="color: red"></label> 
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">File No#: </label>
                                                        <input type="text" name="fileNo" id='fileNo' 
                                                               class="form-control input-sm"
                                                               maxlength="40"/>
                                                        <label id="errorfileNo" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">Cost Of Tender: </label>
                                                        <input type="text" name="tenderCost" id='tenderCost' 
                                                               class="form-control input-sm"
                                                               maxlength="10"/>
                                                        <label id="errortenderCost" style="color: red"></label>
                                                    </div>

                                                </div>
                                                <div class="row">

                                                    <div class="form-group col-md-6 col-lg-4" id="lastDateSale" style="display: none">
                                                        <label for="username">Last Date For Sale: </label>
                                                        <div class='input-group datepicker' data-format="L">
                                                            <input type='text' class="form-control input-sm"
                                                                   name="saleLastDate" id="saleLastDate" />
                                                            <span class="input-group-addon">
                                                                <span class="fa fa-calendar"></span>
                                                            </span>
                                                        </div>
                                                        <label id="errorsaleLastDate" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">Due Date: </label>
                                                        <div class='input-group datepicker' data-format="L">
                                                            <input type='text' class="form-control input-sm"
                                                                   name="dueDate" id="dueDate" />
                                                            <span class="input-group-addon">
                                                                <span class="fa fa-calendar"></span>
                                                            </span>
                                                        </div>
                                                        <label id="errordueDate" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">Date Of Opening: </label>
                                                        <div class='input-group datepicker' data-format="L">
                                                            <input type='text' class="form-control input-sm"
                                                                   name="openingDate" id="openingDate" />
                                                            <span class="input-group-addon">
                                                                <span class="fa fa-calendar"></span>
                                                            </span>
                                                        </div>
                                                        <label id="erroropeningDate" style="color: red"></label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">No Of Sets: </label>
                                                        <input type="text" name="setsNo" id='setsNo' 
                                                               class="form-control input-sm"
                                                               maxlength="3"
                                                               onKeyPress="return numbersonly(this, event)"/>
                                                        <label id="errorsetsNo" style="color: red"></label>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label for="username">EMD: </label>
                                                        <input type="text" name="ewd" id='ewd' 
                                                               class="form-control input-sm"
                                                               maxlength="10"
                                                               onKeyPress="return numbersonly(this, event)"/>
                                                        <label id="errorewd" style="color: red"></label>
                                                    </div>
                                                </div>
                                               
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <label id="errortendItems" style="color: red"></label>
                                                    </div>
                                                    <!-- col -->
                                                    <div class="col-md-12">
                                                        <!-- tile -->
                                                        <section class="tile">
                                                            <!-- tile header -->
                                                            <div class="tile-header dvd dvd-btm">
                                                                <h1 class="custom-font"><strong>Item(s)</strong> Details</h1>
                                                            </div>
                                                            <div class="tile-body">
                                                                <div class="table-responsive">
                                                                    <table class="table table-custom" style="width:100% !important" id="vendor-detls">
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
                                                                                <th>SNo</th>
                                                                                <th>Indent SlNo</th>
                                                                                <th>Item Code</th>
                                                                                <th>Item Name</th>
                                                                                <th>Brief Description</th>
                                                                                <th>Quantity</th>
                                                                                <th>Units</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:set value="0" var="lstCnt"/>
                                                                            <c:if test="${indentSObj.itemDTOLi != null}">
                                                                                <c:set value="1" var="loopCnt"/>
                                                                                <c:forEach items="${indentSObj.itemDTOLi}" var="indentItmObj">
                                                                                    <tr>
                                                                                        <td>${loopCnt}</td>                                                                                        
                                                                                        <td><input type="text" name="tenderItemsLi[${lstCnt}].itemObj.indentSlNo" 
                                                                                                   value="${indentItmObj.indentSlNo}" /></td>
                                                                                        <td>${indentItmObj.itemCode}</td>
                                                                                        <td style="width: 270px;">${indentItmObj.itemName}</td>
                                                                                        <td>
                                                                                            <input type="hidden" name="tenderItemsLi[${lstCnt}].itemObj.itemID" 
                                                                                                   value="${indentItmObj.itemID}" />
                                                                                            <input type="hidden" name="tenderItemsLi[${lstCnt}].itemObj.categoryDTO.categoryID" 
                                                                                                   value="${indentSObj.categoryObj.categoryID}" />
                                                                                            <textarea name="tenderItemsLi[${lstCnt}].breifDesc" id="breifDesc${lstCnt}" class="form-control">${indentSObj.briefDescription}</textarea>
                                                                                        </td>
                                                                                        <td>
                                                                                            ${indentItmObj.requiredStock}
                                                                                            <input type="hidden" name="tenderItemsLi[${lstCnt}].itemQty" id='itemQty${lstCnt}' 
                                                                                                   class="form-control input-sm"
                                                                                                   maxlength="5" value="${indentItmObj.requiredStock}"/>
                                                                                        </td>
                                                                                        <td>
                                                                                            ${indentItmObj.unitDTO.unitName}(${indentItmObj.unitDTO.unitCode})
                                                                                            <input type="hidden" name="tenderItemsLi[${lstCnt}].itemUnits" id='itemUnits${lstCnt}' 
                                                                                                   class="form-control input-sm"
                                                                                                   value="${indentItmObj.unitDTO.unitID}"/>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <c:set value="${loopCnt+1}" var="loopCnt"/>
                                                                                    <c:set value="${lstCnt+1}" var="lstCnt"/>
                                                                                </c:forEach>
                                                                            </c:if>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </section>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <span class="tools pull-right">
                                                            <button type='button' 
                                                                    class="btn btn-info btn-rounded btn-sm"
                                                                    id="sourceIndent"
                                                                    listCount="${lstCnt}">Submit Tender
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                    </div>
                                    <!-- tile body -->
                                    </section>
                                </div>
                            </div>
                        </spring:form>
                    </c:if>
                    <%-- **************************************************************** --%>
                    <%-- ************ END : INDENT DETAIL(S)***************************** --%>
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
<script src="<c:url value="/appjs/raisePubTenderJS.js"/>" type="text/javascript"></script>
</body>
</html>

</c:if>
    <c:if test="${(userPermiLi.t6 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>