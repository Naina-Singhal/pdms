<%-- 
    Document   : ApproveIndentFileUpload
    Created on : Jan 10, 2017, 8:48:37 AM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>     
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.e7 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Indent Upload File(s)</title>
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
            <%--<jsp:include page="../commons/CommonHeader.jsp"/>    --%>

            <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>Indent Form <span> Upload Indent File(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Indent Upload File(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">     

                        <c:if test="${indentSObj != null}">
                            <spring:form name="indFileUpldForm" commandName="indentObj" method="post" enctype="multipart/form-data">
                                <%-- **************************************************************** --%>
                                <%-- ******** START : INDENT DETAIL(S)******************************* --%>
                                <%-- **************************************************************** --%>
                                <div class="row">
                                    <input type="hidden" name="encFieldValue" id="encFieldValue" value="${indentSObj.encFieldValue}"/>
                                    <input type="hidden" name="encRowStatusValue" value="${indentSObj.encRowStatusValue}"/>
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
                                                <%-- START : DISPLAY STATUS MESSAGE--%>
                                                <c:if test="${smsg != null}">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="alert alert-success alert-dismissable">
                                                                <button type="button" class="close" data-dismiss="alert" 
                                                                        aria-hidden="true">&times;</button>
                                                                <strong>Success!</strong>${smsg}
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
                                                                <label>Item Category:</label><br/>
                                                                <label>${indentSObj.categoryObj.categoryCode} - 
                                                                    ${indentSObj.categoryObj.categoryName}</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%-- **************************************************************** --%>
                                <%-- ******** END : INDENT DETAIL(S)******************************* --%>
                                <%-- **************************************************************** --%>
                                <%-- **************************************************************** --%>
                                <%-- ******** START : UPLOADED INDENT FILE(s) *********************** --%>
                                <%-- **************************************************************** --%>
                                <c:if test="${(indentFileLi != null) && (fn:length(indentFileLi) > 0)}">
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
                                                                    <th>Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:set var="lpCnt" value="1"/>
                                                                <c:forEach var="indFileObj" items="${indentFileLi}">
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
                                                                                    id="deleteUpFile" 
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
                                <%-- **************************************************************** --%>
                                <%-- ******** START : INDENT FILE UPLOAD DIV ************************ --%>
                                <%-- **************************************************************** --%>
                                <div class="row">
                                    <!-- col -->
                                    <div class="col-md-12">
                                        <!-- tile -->
                                        <section class="tile">
                                            <!-- tile header -->
                                            <div class="tile-header dvd dvd-btm">
                                                <h1 class="custom-font"><strong>Indent</strong> File(s) Details</h1>
                                            </div>
                                            <!-- tile header -->
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
                                                <div class="row">
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <button type="button" class="btn btn-primary start" id="startUpload">
                                                            <i class="glyphicon glyphicon-upload"></i>
                                                            <span>Start upload</span>
                                                        </button>
                                                    </div>
                                                </div>

                                            </div>
                                        </section>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <span class="tools pull-right">
                                                <input type="button" value="Close" class="btn btn-info " id="closeAppUpload" />
                                                <input type="button" value="Submit" class="btn btn-info" id="submitAppUpload"/> 
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <%-- **************************************************************** --%>
                                <%-- ******** END : INDENT FILE UPLOAD DIV ************************ --%>
                                <%-- **************************************************************** --%>
                            </spring:form>
                        </c:if>

                    </div>
                </div>
            </section>

        </div>
        <script src="<c:url value="/appjs/indentFormJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.e7 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>