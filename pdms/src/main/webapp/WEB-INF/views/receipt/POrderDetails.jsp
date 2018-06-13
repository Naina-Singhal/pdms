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
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Purchase Order Entry (s) List</title>
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
                        <h2>Purchase Order Entry (s) <span>  List(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Purchase Order Entry (s)</a>
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
                                        <h1 class="custom-font"><strong>Purchase Order Entry (s)</strong> List</h1>
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
                                                        <th>PO No#</th>
                                                        <th>Payment</th>
                                                        <th>Signatory</th>
                                                        <th>PO Value</th>
                                                        <th>Place Of Delivery</th>
                                                        <th>Prepared Date</th>
                                                        <th>Security Deposit</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${poEntryLi != null}">
                                                        <c:forEach var="poeObj" items="${poEntryLi}">
                                                            <tr class="gradeX">
                                                                <td>${poeObj.poNumber}</td>
                                                                <td>${poeObj.payment}</td>
                                                                <td>${poeObj.signatory}</td>
                                                                <td>${poeObj.poValue}</td>
                                                                <td>${poeObj.placeOfDelivery}</td>
                                                                <td>${poeObj.preparedDate}</td>
                                                                <td>${poeObj.securityDeposit}</td>
                                                                <td>
                                                                    <button type='button' style=""
                                                                                class="btn btn-info btn-rounded btn-sm"
                                                                                onclick="next_po();">Next PO
                                                                    </button>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="">View
                                                                    </button>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="">PDF
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
      $("#poe_active").addClass("active");
      
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
            
            function next_po(){
                
                window.open("./POEntryView.htm","_parent");
            }

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