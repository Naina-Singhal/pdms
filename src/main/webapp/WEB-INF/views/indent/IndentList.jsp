<%-- 
    Document   : IndentList
    Created on : Sep 23, 2016, 7:54:01 AM
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
                <c:if test="${(userPermiLi.e2 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Indent(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>

    </head>
    <body id="minovate" class="appWrapper">
        <c:set var="userType" value="User"/>
        <c:set var="appAuthority" value="0"/>
        <c:set var="signAuthority" value="0"/>
        <c:set var="userID" value="0"/>
        <c:if test="${sessionScope.USER_SESSION!=null}">
            <c:set var="userType" value="${sessionScope.USER_SESSION.employeeProfileDTO.employeeTypeDTO.employeeTypeName}"/>
            <c:set var="appAuthority" value="${sessionScope.USER_SESSION.designationDTO.isApprovingAuthority}"/>
            <c:set var="signAuthority" value="${sessionScope.USER_SESSION.designationDTO.isSigningAuthority}"/>
            <c:set var="userID" value="${sessionScope.USER_SESSION.employeeID}"/>
        </c:if>
        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>    
            <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>Indents(s) <span> Indent List(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Indent(s)</a>
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
                        <%-- END : DISPLAY STATUS MESSAGE--%>

                        <%-- **************************************************************** --%>
                        <%-- ************ START : INDENT LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <form name="indentLi" method="post"/>
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Indent(s)</strong> List</h1>
                                        <c:if test="${userType == constants.EMPLOYEE_TYPE_SECUSER}">
                                            <div class="pull-right" style="padding-right: 30px;">
                                                <span class="">
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            id="addIndent">
                                                        Create Indent
                                                    </button>
                                                </span>
                                            </div>
                                        </c:if>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body" style="overflow-x: scroll">
                                        <div class="table-responsive">
                                            <table class="table table-custom display" id="basic-usage" cellspacing="0" style="width:100% !important;">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th></th>
<!--                                                        <th></th>-->
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Indent No#</th>
<!--                                                        <th>Received Date</th>-->
                                                        <th>Indent Date</th>
                                                        <th>Estimated Cost</th>
                                                        <th>Section</th>
                                                        <th>Item Category</th>
                                                        <th>Signing Authority</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${indentLi != null}">
                                                        <c:forEach var="indObj" items="${indentLi}">
                                                            <tr class="gradeX">
                                                                <td>${indObj.indentNumber}</td>
                                                                <%--<td>${indObj.recevidedDate}</td>--%>
                                                                <td>${indObj.indentDate}</td>
                                                                <td>${indObj.estimatedCost}</td>
                                                                <td>${indObj.sectionObj.sectionName}</td>
                                                                <td>${indObj.categoryObj.categoryName}</td>
                                                                <td>${indObj.signingAuthorityObj.designationName}</td>
                                                                <td>${indObj.indentStatus}</td>
                                                                <%--
                                                                ${indObj.signingAuthorityObj.designationCode}-
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${fn:length(indObj.fileNo)>0}">
                                                                            ${indObj.mappedGroupName}:(File-${indObj.fileNo})
                                                                        </c:when>
                                                                        <c:otherwise>

                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                --%>
                                                                <td>
                                                                    <%-- onclick="fnEditIndent('${fn:trim(indObj.encFieldValue)}')"--%>
                                                                    <c:if test="${((appAuthority==1) && 
                                                                                  ((indObj.indentStatus == constants.INDENT_STATUS_CREATED) ||
                                                                                  (indObj.indentStatus == constants.INDENT_STATUS_MODIFIED)))
                                                                          || (userType == constants.EMPLOYEE_TYPE_SECADMIN)}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnEditAppIndent('${fn:trim(indObj.encFieldValue)}')">Edit
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${(signAuthority==1 && 
                                                                                  (indObj.indentStatus == constants.INDENT_STATUS_APPROVED))
                                                                          || ((userType == constants.EMPLOYEE_TYPE_PUADMIN) &&
                                                                               (indObj.indentStatus == constants.INDENT_STATUS_APPROVED))}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnEditSignIndent('${fn:trim(indObj.encFieldValue)}')">Sign
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${!(indObj.indentStatus == constants.INDENT_STATUS_CREATED)&&
                                                                                  !(indObj.indentStatus == constants.INDENT_STATUS_MODIFIED)&&
                                                                                  !(indObj.indentStatus == constants.INDENT_STATUS_REJECTED)&&
                                                                                  !(indObj.indentStatus == constants.INDENT_STATUS_REVERTED)}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnIndentFormPDF('${fn:trim(indObj.encFieldValue)}')">PDF
                                                                        </button>
                                                                    </c:if>
                                                                    
                                                                    <c:if test="${(appAuthority==0)&&(signAuthority==0)&& 
                                                                                  (userType != constants.EMPLOYEE_TYPE_SECADMIN) &&
                                                                                  ((indObj.indentStatus == constants.INDENT_STATUS_CREATED)||
                                                                                  (indObj.indentStatus == constants.INDENT_STATUS_REVERTED))}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnEditIndent('${fn:trim(indObj.encFieldValue)}')">Edit
                                                                        </button>
                                                                    </c:if>
                                                                    
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnViewIndent('${fn:trim(indObj.encFieldValue)}')">View
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                            </form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : INDENT LIST DETAIL(S)******************** --%>
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
        <script>

            $(document).ready(function () {

    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
      $("#navigation li a").removeClass("active");
      
      $("#indlk").addClass("active");
      $("#indmenu").css("display","block");
      $("#indli").addClass("active");
      
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
                //***********************************************
                //Start : Initialize basic datatable
                //***********************************************
                $('#basic-usage').dataTable({
                    "aoColumns": [
                        {"bSortable": true},
                        {"bSortable": true},
                        //{"bSortable": true},
                        {"bSortable": true},
                        {"bSortable": true},
                        {"bSortable": true},
                        {"bSortable": true},
                        {"bSortable": true},
                        {"bSortable": false}
                    ]
                })
                .columnFilter({
                    sPlaceHolder: "head:after",
                    aoColumns: [
                        {type: "text"},
                        {type: "text"},
                        //{type: "text"},
                        {type: "text"},
                        {type: "text"},
                        {type: "text"},
                        {type: "text"},
                        {type: "text"},
                        null
                    ]
                });
                //***********************************************
                //End : Initialize basic datatable
                //***********************************************

                $("#addIndent").click(function () {
                    document.indentLi.action = "<c:url value="indenthome.htm"/>"
                    document.indentLi.submit();
                });

            });

            function fnViewIndent(selIndID)
            {
                document.indentLi.action = "<c:url value="viewindent.htm?eindi="/>"+selIndID;
                document.indentLi.submit();
            }
            function fnEditAppIndent(selIndID)
            {
                document.indentLi.action = "<c:url value="appeditindent.htm?eindi="/>"+selIndID;
                document.indentLi.submit();
            }
            function fnEditSignIndent(selIndID)
            {
                document.indentLi.action = "<c:url value="signeditindent.htm?eindi="/>"+selIndID;
                document.indentLi.submit();
            }
            function fnEditIndent(selIndID)
            {
                document.indentLi.action = "<c:url value="editindent.htm?eindi="/>"+selIndID;
                document.indentLi.submit();
            }
            function fnIndentFormPDF(selIndID)
            {
                window.open('<c:url value="ifpdfdownload.htm?eindi="/>'+selIndID);
            }
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->


    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.e2 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>