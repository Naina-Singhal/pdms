<%-- 
    Document   : ptIndentList
    Created on : Oct 25, 2016, 3:07:45 PM
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
                <c:if test="${(userPermiLi.t1 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Public Tender - Indent(s) List</title>
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
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                        <th></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Indent No#</th>
                                                        <th>Received Date</th>
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
                                                                <td>${indObj.recevidedDate}</td>
                                                                <td>${indObj.indentDate}</td>
                                                                <td>${indObj.estimatedCost}</td>
                                                                <td>${indObj.sectionObj.sectionName}</td>
                                                                <td>${indObj.categoryObj.categoryName}</td>
                                                                <td>${indObj.signingAuthorityObj.designationCode}-${indObj.signingAuthorityObj.designationName}</td>
                                                                <td>${indObj.indentStatus}</td>
                                                                <td>
                                                                    <c:if test="${(indObj.indentStatus == constants.INDENT_STATUS_MAPPED)}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnRaiseTender('${fn:trim(indObj.encFieldValue)}')">Raise Tender
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${(indObj.indentStatus == constants.INDENT_STATUS_RAISED_TENDER)||
                                                                                (indObj.indentStatus == constants.INDENT_STATUS_TENDER_ACCEPTED)||
                                                                          (indObj.indentStatus == constants.INDENT_STATUS_RAISED_PO)}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnViewTender('${fn:trim(indObj.encFieldValue)}')">View Tender
                                                                        </button>
                                                                    </c:if>
                                                                   
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
      
      $("#ptlnk").addClass("active");
      $("#ptmenu").css("display", "block");
      $("#ptilist").addClass("active");
      
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
                        {"bSortable": true},
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
                        {type: "text"},
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


            });

            function fnRaiseTender(selIndID)
            {
                document.indentLi.action = "<c:url value="raisepudtender.htm?eindi="/>"+selIndID;
                document.indentLi.submit();
            }
            
            function fnViewTender(selIndID)
            {
                //viewpudtenderfi
                document.indentLi.action = "<c:url value="viewpudtenderfi.htm?etid="/>"+selIndID;
                document.indentLi.submit();
            }
            
            
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->


    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.t1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>