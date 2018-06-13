<%-- 
    Document   : EditPublicTender
    Created on : Oct 28, 2016, 3:09:42 PM
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
                <c:if test="${(userPermiLi.t3 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Edit Tender Form</title>
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
            <spring:form name="vTenderForm" commandName="tenderObj" method="post">
                <input type="hidden" name="encIndentID" value="${tenderObj.encIndentID}"/>
                <input type="hidden" name="encTenderID" value="${tenderObj.encTenderID}"/>
                <section id="content">
                    <div class="page">
                        <!-- == Start :Page Header & BRead Crumbs ======== -->
                        <div class="pageheader">
                            <h2>Tender Form <span> Edit Tender Information</span></h2>
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
                            <div class="row">
                                <!-- col -->
                                <!-- == Start : Indent Details Form ======== -->
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
                                                    <label for="username">Indent No#: </label><br/>
                                                    <label>${tenderObj.indentObj.indentNumber} </label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Date:</label><br/>
                                                    <label>${tenderObj.indentObj.indentDate}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Estimated Cost:</label><br/>
                                                    <label> ${tenderObj.indentObj.estimatedCost} </label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Desired Delivery Schedule:</label><br/>
                                                    <label>${tenderObj.indentObj.descriptionDetails}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Type:</label><br/>
                                                    <label>${tenderObj.indentObj.indentTypeCTQ}</label>,
                                                    <label>${tenderObj.indentObj.indentTypeProp}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Indent Status:</label><br/>
                                                    <label>${tenderObj.indentObj.indentStatus}</label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Group Name:</label><br/>
                                                    <label>${tenderObj.indentObj.mappedGroupName}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>File No:</label><br/>
                                                    <label>${tenderObj.indentObj.fileNo}</label>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                                <!-- == Start : Indent Details Form ======== -->
                                <!-- == Start : Tender Details Form ======== -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Tender</strong> Details</h1>
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
                                                            <c:choose>
                                                                <c:when test="${lookupObj.lookUpID == tenderObj.tenderTypeID}">
                                                                    <option value="${lookupObj.lookUpID}" selected="true">
                                                                        ${lookupObj.lookUpName}
                                                                    </option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${lookupObj.lookUpID}">
                                                                        ${lookupObj.lookUpName}
                                                                    </option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            
                                                        </c:forEach>
                                                    </select>
                                                    <br/>
                                                    <label id="errortenderTypeID" style="color: red"></label> 
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">File No#: </label>
                                                    <input type="text" name="fileNo" id='fileNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"
                                                           value="${tenderObj.fileNo}"/>
                                                    <label id="errorfileNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Tender Cost:</label>
                                                    <input type="text" name="tenderCost" id='tenderCost' 
                                                           class="form-control input-sm"
                                                           maxlength="10"
                                                           value="${tenderObj.tenderCost}"/>
                                                    <label id="errortenderCost" style="color: red"></label>
                                                </div>

                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Last Date of Sale: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="saleLastDate" id="saleLastDate" 
                                                               value="${tenderObj.saleLastDate}"/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorsaleLastDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Due Date:</label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dueDate" id="dueDate"
                                                               value="${tenderObj.dueDate}"/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errordueDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Opening Date:</label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="openingDate" id="openingDate" 
                                                               value="${tenderObj.openingDate}"/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="erroropeningDate" style="color: red"></label>
                                                </div>
                                            </div>   
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>No Of Sets:</label>
                                                    <input type="text" name="setsNo" id='setsNo' 
                                                           class="form-control input-sm"
                                                           maxlength="3"
                                                           onKeyPress="return numbersonly(this, event)"
                                                           value="${tenderObj.setsNo}"/>
                                                    <label id="errorsetsNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">EMD </label>
                                                    <input type="text" name="ewd" id='ewd' 
                                                           class="form-control input-sm"
                                                           maxlength="10"
                                                           value="${tenderObj.ewd}"
                                                           onKeyPress="return numbersonly(this, event)"/>
                                                    <label id="errorewd" style="color: red"></label>
                                                </div>
                                            </div>   
                                            
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                                <!-- == End : Tender Details Form ======== -->
                                <!-- == Start : Tender Items Details Form ======== -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Item</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="table-responsive">
                                                <table class="table table-custom display" id="basic-usage" cellspacing="0" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>SNo</th>
                                                            <th>Item Code</th>
                                                            <th>Item Name</th>
                                                            <th>Description</th>
                                                            <th>Quantity</th>
                                                            <th>Units</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:set value="0" var="lstCnt"/>
                                                        <c:if test="${tenderObj.tenderItemsLi != null}">
                                                            <c:forEach var="vendorItemObj" items="${tenderObj.tenderItemsLi}">
                                                                <tr class="gradeX">
                                                                    <td>${vendorItemObj.itemObj.itemCode}</td>
                                                                    <td>${vendorItemObj.itemObj.itemName}</td>
                                                                    <td>
                                                                        <input type="hidden" name="tenderItemsLi[${lstCnt}].itemObj.itemID" 
                                                                               value="${vendorItemObj.itemObj.itemID}" />
                                                                        <input type="hidden" name="tenderItemsLi[${lstCnt}].itemObj.categoryDTO.categoryID" 
                                                                               value="${vendorItemObj.itemObj.categoryDTO.categoryID}" />
                                                                        <textarea name="tenderItemsLi[${lstCnt}].breifDesc" 
                                                                                  id="breifDesc${lstCnt}" class="form-control">${vendorItemObj.breifDesc}</textarea>
                                                                    </td>
                                                                    <td>${vendorItemObj.itemQty}</td>
                                                                    <td>${vendorItemObj.itemObj.unitDTO.unitName}(${vendorItemObj.itemObj.unitDTO.unitCode})</td>
                                                                </tr>
                                                                <c:set value="${lstCnt+1}" var="lstCnt"/>
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
                                        <input type="button" value="Update" 
                                               class="btn btn-info " id="updateTender" 
                                               onclick="fnUpdateTender()"/>
                                        <input type="button" value="Cancel" 
                                               class="btn btn-info " id="cancelUpdate" 
                                               onclick="fnCancelUpdate()"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <!-- == End :Page Form ======== -->
                    </div>
                </section>
                <script>
                    $(document).ready(function () {
                        //***********************************************
                        //Start : Initialize basic datatable
                        //***********************************************
                        $("#navigation li a").removeClass("active");

                        $("#ptlnk").addClass("active");
                        $("#ptmenu").css("display", "block");
                        $("#ptlst").addClass("active");

                        //***********************************************
                        //End : Initialize basic datatable
                        //***********************************************
                    });
                    function fnCancelUpdate()
                    {
                        document.vTenderForm.action = "<c:url value="pudtenderli.htm"/>";
                        document.vTenderForm.submit();
                    }

                    function fnUpdateTender()
                    {
                        document.vTenderForm.action = "<c:url value="updpubtender.htm"/>";
                        document.vTenderForm.submit();
                    }
                </script>
            </spring:form>
        </div>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.t3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>