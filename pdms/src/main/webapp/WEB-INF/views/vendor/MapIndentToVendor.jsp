<%-- 
    Document   : MapIndentToVendor
    Created on : Oct 21, 2016, 5:29:13 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.v2 == 1)}">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Map Indent To Vendor(s)</title>
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
                        <h2>Map Vendor(s) <span> Map Vendor(s) to Indent</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Map Vendor(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">       
                        <%-- **************************************************************** --%>
                        <%-- ******** START : SELECT INDENT NUMBER ************************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font">Select <strong>Indent</strong> Number</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <spring:form name="fIndentForm" commandName="indentObj" method="post">
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">File Number: </label>
                                                    <select name=indentNumber" id='indentNumber' class="form-control custom-select">
                                                        <option value="0">Select</option>
                                                        
                                                            <c:forEach items="${fileLi}" var="indentLiObja">
                                                                
                                                                    
                                                                   
                                                               <option value="${indentLiObja.fileNoFrMapp}">${indentLiObja.fileNoFrMapp}</option>
                                                                   
                                                               
                                                            </c:forEach>
                                                       
                                                    </select>
                                                    <br/>
                                                    <label id="errorindentNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-2 p-20">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="fetchIndDtls">
                                                        <span>Fetch Indent Details</span>
                                                    </button>
                                                </div>
                                            </div>
                                        </spring:form>
                                        <%-- **************************************************************** --%>
                                        <%-- ******** START : INDENT DETAIL(S)******************************* --%>
                                        <%-- **************************************************************** --%>
                                        <c:if test="${indentSObj != null}">
                                            <spring:form name="vIndentForm" commandName="vIndentObj" method="post">
                                                <c:forEach items="${indentSObj}" var="indentForm">
                                                    <input type="hidden" name="encFieldValue" id="encFieldValue" value="${indentForm.encFieldValue}"/>
                                                </c:forEach>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading" role="tab" id="headingOne">
                                                                    <h4 class="panel-title">
                                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
                                                                           aria-expanded="false" aria-controls="collapseOne" class="collapsed">
                                                                            <h4 class="custom-font">
                                                                                Click To View <strong>Indent</strong> Details
                                                                            </h4>
                                                                        </a>
                                                                    </h4>
                                                                </div>
                                                                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                                                                    <div class="panel-body">
                                                                        <c:forEach items="${indentSObj}" var="indentForm">
                                                                        <div class="row">
                                                                            <div class="form-group col-md-6 col-lg-3">
                                                                                <label for="username">Indent No#: </label><br/>
                                                                                <label>${indentForm.indentNumber} </label>
                                                                                <input type="hidden" name="indentIDD" id="indentIDD" value="${indentForm.indentFormID}" />
                                                                            </div>
                                                                            <div class="form-group col-md-6 col-lg-3">
                                                                                <label>Indent Date:</label><br/>
                                                                                <label>${indentForm.indentDate}</label>
                                                                            </div>
                                                                            <div class="form-group col-md-6 col-lg-3">
                                                                                <label>Estimated Cost:</label><br/>
                                                                                <label> ${indentForm.estimatedCost} </label>
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="form-group col-md-6 col-lg-3">
                                                                                <label>Group Name:</label><br/>
                                                                                <label>${indentForm.mappedGroupName}</label>
                                                                            </div>
                                                                            <div class="form-group col-md-6 col-lg-3">
                                                                                <label>File No:</label><br/>
                                                                                <label>${indentForm.fileNo}</label>
                                                                                <input type="hidden" name="fileNoHid" id="fileNoHid" value="${indentForm.fileNo}" />
                                                                            </div>
                                                                        </div>
                                                                            </c:forEach>
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
                                                                <h1 class="custom-font"><strong>Indent</strong> Item Details</h1>
                                                            </div>
                                                            <!-- tile header -->
                                                            <!-- tile body -->
                                                            <div class="tile-body">
                                                                <div class="row">
                                                                    <div class="form-group col-md-6 col-lg-4">
                                                                        <label>Item Category:</label><br/>
                                                                        <c:forEach items="${indentSObj}" var="indentForm">
                                                                        <label>${indentForm.categoryObj.categoryCode} - ${indentForm.categoryObj.categoryName}</label>
                                                                        </c:forEach>
                                                                    </div>
                                                                    <div class="form-group col-md-6 col-lg-4">
                                                                        <label>Item :</label><br/>
                                                                        <select name=itemId" id='itemId' class="form-control custom-select">
                                                                            <option value="0">Select</option>
                                                                            
                                                                                <c:forEach items="${itemSObj}" var="ItmObj">
                                                                                  
                                                                                   <option value="${ItmObj.itemID}">${ItmObj.itemName}</option>
                                                                                       

                                                                                </c:forEach>
                                                                            
                                                                        </select>
                                                                        <br/>
                                                                        <label id="erroritemId" style="color: red"></label>
                                                                    </div>
                                                                    <div class="form-group col-md-6 col-lg-2 p-20">
                                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                                id="getvendors">
                                                                            <span>Fetch Vendor Details</span>
                                                                        </button>
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
                                        <%-- **************************************************************** --%>
                                        <%-- ******** START : VENDOR DETAIL(S)******************************* --%>
                                        <%-- **************************************************************** --%>
                                        <c:if test="${vendorLi != null}">
                                            <spring:form name="vSourceForm" commandName="vItemObj" method="post">
                                                <c:forEach items="${indentSObj}" var="indentForm">
                                                    <input type="hidden" name="encIndentID" id="encIndentID" value="${indentForm.indentFormID}"/>
                                                    <input type="hidden" name="fileNo" value="${indentForm.fileNo}"/>
                                                     
                                                </c:forEach>
                                                                                             
                                                    <input type="hidden" name="itemID" value="${selItemID}" />
                                                    
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
                                                                            <th></th>
                                                                        </tr>
                                                                        <tr>
                                                                            <th>&nbsp;</th>
                                                                            <th>Vendor Code</th>
                                                                            <th>Vendor Name</th>
                                                                            <th>City</th>
                                                                            <th>Registration</th>
                                                                            <th>Sent</th>
                                                                            <th>Received</th>
                                                                            <th>Ordered</th>
                                                                        </tr>
                                                                    <tbody>
                                                                        <c:if test="${vendorLi != null}">
                                                                            <c:set var="loopCount" value="1"/>
                                                                            <c:forEach var="vendObj" items="${vendorLi}">
                                                                                <tr class="gradeX">
                                                                                    <td>
                                                                                        <c:forEach items="${indentSObj}" var="indentForm">                                                                                                
                                                                                                
                                                                                        <c:choose>
                                                                                            
                                                                                            <c:when test="${(selItemID == vendObj.itemID) && (vendObj.fileNo == indentForm.fileNo)}">
                                                                                                <%--<input type="checkbox" name="selVendorList" 
                                                                                                    id="selVendorList"
                                                                                                    value="${vendObj.encVendorID}"
                                                                                                    checked="true" disabled="true">
                                                                                                --%>
                                                                                                <button type='button' 
                                                                                                        class="btn btn-info btn-rounded btn-sm"
                                                                                                        onclick="fnUnMapVendorIndent('${fn:trim(vendObj.encVendorID)}')">Un Map
                                                                                                </button>
                                                                                            </c:when>
                                                                                            <c:otherwise>
                                                                                                <input type="checkbox" name="selVendorList" 
                                                                                                    id="selVendorList"
                                                                                                    value="${vendObj.encVendorID}">
                                                                                            </c:otherwise>
                                                                                        </c:choose>
                                                                                               
                                                                                        </c:forEach>
                                                                                    </td>
                                                                                    <td>${vendObj.vendorCode}</td>
                                                                                    <td>${vendObj.vendorName}</td>
                                                                                    <td>${vendObj.vendorCity}</td>
                                                                                    <td>${vendObj.registrationType}</td>
                                                                                    <td>${vendObj.sentCount}</td>
                                                                                    <td>${vendObj.recordedCount}</td>
                                                                                    <td>${vendObj.orderedCount}</td>
                                                                                   
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
                                                                id="sourceIndent">Source Indent
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
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ******************** END : SELECT INDENT NUMBER***************** --%>
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
        <script src="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.js"/>"></script>
        <script>
            $(document).ready(function () {
                //$("#categoryID").searchable();
                $("#indentNumber").customselect();

                $("#fetchIndDtls").click(function () {

                    $("#errorindentNumber").html("");
                    var selInd = $("#indentNumber").val();
                    if ($.trim(selInd) === '0')
                    {
                        $("#errorindentNumber").html("Please select Indent Number.");
                        return false;
                    }

                    document.fIndentForm.action = "getvindentdtl.htm?endi=" + $.trim(selInd);
                    document.fIndentForm.submit();
                });
                
                $("#getvendors").click(function () {
                    
                    $("#erroritemId").html("");
                    var selFileNo = $("#fileNoHid").val();
                    var selItem = $("#itemId").val();
                    var indenti = $("#indentIDD").val();
                    //alert(selFileNo+"--"+indenti+"--"+selItem);
                    
                    if($.trim(selItem) === '0')
                    {
                        $("#erroritemId").html("Please select Item to proceed.");
                        return false;
                    }

                    document.vIndentForm.action = "getvendtlselitem.htm?endi=" + $.trim(selFileNo) + "&enit=" + selItem;
                    document.vIndentForm.submit();

                });
                
                $("#sourceIndent").click(function(){
                    
                    if ($('[id="selVendorList"]:checked').length >0) {
                        $("#errorselVendorList").html("");
                        
                        document.vSourceForm.action = "saveselvd.htm";
                        document.vSourceForm.submit();
                    } else {
                        $("#errorselVendorList").html("Please select Vendor details to be mapped.");
                        return false;
                    }
                    
                    
                });
                
                
            });
            
            function fnUnMapVendorIndent(encvid)
            {
                var conf = window.confirm("Are you sure to Unmap the selected vendor from Indent");
                if(conf)
                {
                    document.vSourceForm.action = "unmapvendorind.htm?vid="+encvid;
                    document.vSourceForm.submit();
                }
            }
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.v2 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>