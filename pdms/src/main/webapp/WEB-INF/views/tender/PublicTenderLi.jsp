<%-- 
    Document   : PublicTenderLi
    Created on : Oct 26, 2016, 12:54:10 PM
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
                <c:if test="${(userPermiLi.t2 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Public Tender (s) List</title>
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
                        <h2>Tender(s) <span> Public Tender List(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Public Tender(s)</a>
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
                        <%-- ************** START : TENDER LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="indentLi" method="post" commandName="vIndentObj">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Tender(s)</strong> List</h1>
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
                                                    </tr>
                                                    <tr>
                                                        <th>Indent No#</th>
                                                        <th>Tender File No#</th>
                                                        <th>Tender Type</th>
                                                        <th>Due Date</th>
                                                        <th>Opening Date</th>
                                                        <th>Tender Cost</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${tenderLi != null}">
                                                        <c:forEach var="tendObj" items="${tenderLi}">
                                                            <tr class="gradeX">
                                                                <td>${tendObj.indentObj.indentNumber}</td>
                                                                <td>${tendObj.fileNo}</td>
                                                                <td>${tendObj.tenderType}</td>
                                                                <td>${tendObj.dueDate}</td>
                                                                <td>${tendObj.openingDate}</td>
                                                                <td>${tendObj.tenderCost}</td>
                                                                <td>${tendObj.rowStatusValue}</td>
                                                                <td>
                                                                    
                                                                    <c:if test="${constants.TENDER_STATUS_RAISED == tendObj.rowStatusValue}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnEditTender('${fn:trim(tendObj.encFieldValue)}')">Edit
                                                                        </button>
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnMapVendorTender('${fn:trim(tendObj.encFieldValue)}')">Map Vendor
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${constants.TENDER_STATUS_VENDOR_MAPPED == tendObj.rowStatusValue}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnGeneratePO('${fn:trim(tendObj.encFieldValue)}')">Generate PO
                                                                        </button>
                                                                       
                                                                    </c:if>
                                                                    <c:if test="${constants.TENDER_STATUS_PO_GENERATED == tendObj.rowStatusValue}">
                                                                        <button type='button' 
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="fnViewPOPdf('${fn:trim(tendObj.encFieldValue)}')">PDF PO
                                                                        </button>
                                                                    </c:if>
                                                                    
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnViewTender('${fn:trim(tendObj.encFieldValue)}')">View
                                                                    </button>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="fnDownloadTender('${fn:trim(tendObj.encFieldValue)}')">Download
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
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- **************** END : TENDER LIST DETAIL(S)******************** --%>
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
      $("#ptlst").addClass("active");
      
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
                        null
                    ]
                });
                //***********************************************
                //End : Initialize basic datatable
                //***********************************************


            });

            function fnViewTender(selIndID)
            {
                document.indentLi.action="<c:url value="viewpudtender.htm?etid="/>"+selIndID;
                document.indentLi.submit();
            }
            
            function fnDownloadTender(selIndID)
            {
                document.indentLi.action="<c:url value="vdwdpudtender.htm?etid="/>"+selIndID;
                document.indentLi.submit();
            }
            
            function fnEditTender(selIndID)
            {
                document.indentLi.action="<c:url value="edtpudtender.htm?etid="/>"+selIndID;
                document.indentLi.submit();
            }
            
            function fnMapVendorTender(selIndID)
            {
                document.indentLi.action="<c:url value="maptendertov.htm?etid="/>"+selIndID;
                document.indentLi.submit();
            }
            
            function fnGeneratePO(selIndID)
            {
                document.indentLi.action="<c:url value="gpoview.htm?etid="/>"+selIndID;
                document.indentLi.submit();
            }
            
            function fnViewPOPdf(selIndID)
            {
                window.open("<c:url value="popdf.htm?etid="/>"+selIndID);
            }
            
            
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->


    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.t2 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>