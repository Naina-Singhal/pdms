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
                <c:if test="${(userPermiLi.b2 == 1)}">    
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
                        <%-- **************************************************************** --%>
                        <%-- ************** START : TENDER LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form >
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Purchase Order Entry (s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.b1 == 1)}">                                                
                                                    <button type='button' 
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="next_po();">PO Entry
                                                    </button> 
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body" style="overflow-x: scroll">
                                        <div class="table-responsive">
                                            <table class="table table-custom row-border hover order-column" id="basic-usage" cellspacing="0" style="width:100% !important;">
                                                <thead>
                                                    <tr>                                                        
                                                    </tr>
                                                </thead>
                                                <thead id="filterrow">
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
                                                </thead>
                                                <tbody>
                                                    <% int slno = 0;    %>
                                                    <c:if test="${poEntryLi != null}">
                                                        <c:forEach var="poeObj" items="${poEntryLi}">
                                                            <tr class="gradeX">
                                                                <td><%= ++slno %></td> 
                                                                <td>${poeObj.poNumber}</td>
                                                                <td>${poeObj.payment}</td>
                                                                <td>${poeObj.signatory}</td>
                                                                <td>${poeObj.poValue}</td>
                                                                <td class="less-more">${poeObj.placeOfDelivery}</td>
                                                                <td>${poeObj.preparedDate}</td>                                                                
                                                                <td>
                                                                    <button type='button' 
                                                                            class="btn btn-info btn-rounded btn-sm"
                                                                            onclick="">Edit
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
        <link rel="stylesheet" href="<c:url value="/assets/css/second-dataTables.css"/>">
        <script src="<c:url value="/assets/js/jquery.seconDataTables.min.js"/>" type="text/javascript"></script>
        <script>

   $(document).ready(function () {
    
      $("#navigation li a").removeClass("active");  
      $("#purcmenu").css("display", "block");
      $("#poe_active").addClass("active");
      
    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
    // Setup - add a text input to each footer cell
    $('#filterrow th').not(":eq(7)").each(function () {
        var title = $(this).text();
        $(this).html('<input type="text"  />');
    });

    var table = $('#basic-usage').dataTable({
        autoWidth: false,
        "columns": [
            {title: "SL No", width: '5%'},
            {title: "PO No", width: '10%'},
            {title: "Payment", width: '15%'},
            {title: "Signatory", width: '11%'},
            {title: "Po Value", width: '13%'},
            {title: "Place OF Delivery", width: '15%'},
            {title: "Prepared Date", width: '15%'},
            {title: "Buttons", width: '15%'}
        ],
        'aoColumnDefs': [{
                'bSortable': false,
                'aTargets': [-1] /* 1st one, start by the right */
            }]
    });

    // Apply the search
    $("#basic-usage thead input").on('keyup change', function () {
        table.column($(this).parent().index() + ':visible')
                .search(this.value)
                .draw();
    });

    //***********************************************
    //End : Initialize basic datatable
    //***********************************************

    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************

    var table = $('#basic-usage').DataTable();

    $('#basic-usage tbody')
            .on('mouseenter', 'td', function () {
                var colIdx = table.cell(this).index().column;

                $(table.cells().nodes()).removeClass('highlight');
                $(table.column(colIdx).nodes()).addClass('highlight');
            });
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 

    //**************************************************************************
    //**START: BASIC INITIALZATION FOR LESS MORE  PLUGIN************************
    //********Any forther help for this plugin click below link*****************
    //*****www.jqueryscript.net/text/Read-More-Less-Plugin-jQuery-Shorten.html**
    $('.less-more').shorten({
        chars: 40,
        more: '&#8680;',
        less: '&#8678;'
    });


    $(".dataTables_paginate").on('click', function () {
        //alert("ok"); 
        $('.less-more').shorten({
            chars: 40,
            more: '&#8680;',
            less: '&#8678;',
            ellipses: '&nbsp...'


        });

    });
    //**************************************************************************
    //**START: BASIC INITIALIZATION FOR LESS MORE PLUGIN*************************
    //**************************************************************************
    
      
    }); // End document    
            function next_po(){
                
                window.open("./POEntryView.htm","_parent");
            }

            
            
        </script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->


    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.b2 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>