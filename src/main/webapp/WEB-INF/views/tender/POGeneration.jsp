<%-- 
    Document   : POGeneration
    Created on : Oct 31, 2016, 6:24:38 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.t5 == 1)}"> 
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
            <spring:form name="vTenderForm" commandName="ptVendorObj">
                <input type="hidden" name="tenderID" value="${tenderObj.pTenderID}"/>
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
                            <div class="row">
                                <!-- col -->
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
                                                    <label for="username">File No#: </label><br/>
                                                    <label>${tenderObj.fileNo} </label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Tender Cost:</label><br/>
                                                    <label>${tenderObj.tenderCost}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>No Of Sets:</label><br/>
                                                    <label> ${tenderObj.setsNo} </label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Last Date of Sale: </label><br/>
                                                    <label>${tenderObj.saleLastDate} </label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Due Date:</label><br/>
                                                    <label>${tenderObj.dueDate}</label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Opening Date:</label><br/>
                                                    <label> ${tenderObj.openingDate} </label>
                                                </div>
                                            </div>   
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">EMD: </label><br/>
                                                    <label>${tenderObj.ewd} </label>
                                                </div>
                                            </div>   
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                                <!-- == End : Tender Details Form ======== -->

                                <!-- == Start : PO Form ======== -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>PO</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">PO File No#: </label>
                                                    <input type="text" name="poNumber" id='poNumber' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorpoNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">PO Offer: </label>
                                                    <textarea class="form-control input-sm"
                                                              name="poOffer" id="poOffer"></textarea>
                                                    <label id="errorpoOffer" style="color: red"></label>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                                <!-- == End : PO Form ======== -->
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <span class="tools pull-right">
                                        <input type="button" value="Update" 
                                               class="btn btn-info " id="updPoView" 
                                               onclick="fnUpdateTenderPO()"/>
                                        <input type="button" value="Cancel" 
                                               class="btn btn-info " id="cancelView" 
                                               onclick="fnCancelView()"/>
                                    </span>
                                </div>
                            </div>

                        </div>
                        <!-- == End :Page Form ======== -->
                    </div>
                </section>
            </spring:form>
        </div>
        <!-- ====================================================
        ================= End : Application Content ===========
        ===================================================== -->       
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script>
            $("#navigation li a").removeClass("active");
            $("#ptmenu").css("display","block");
            $("#ptlst").addClass("active");
            function fnCancelView()
            {
                document.vTenderForm.action = "<c:url value="pudtenderli.htm"/>";
                document.vTenderForm.submit();
            }
            function fnUpdateTenderPO()
            {
                document.vTenderForm.action = "<c:url value="savepoact.htm"/>";
                document.vTenderForm.submit();
            }
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.t5 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>