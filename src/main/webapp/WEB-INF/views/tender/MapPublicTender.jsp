<%-- 
    Document   : MapPublicTender
    Created on : Oct 31, 2016, 4:11:26 PM
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
                <c:if test="${(userPermiLi.t4 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Tender Form</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/assets/js/vendor/jquery-ui/jquery.blockUI.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.css"/>">
        <script src="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.js"/>"></script>
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
                            <h2>Tender Form <span> Map Tender to Vendor</span></h2>
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
                                                    <label for="username">Tender File No#: </label><br/>
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
                                                    <label>Indent File No:</label><br/>
                                                    <label>${tenderObj.indentObj.fileNo}</label>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                                <!-- == End : Indent Details Form ======== -->
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
                                                            <th>Item Name</th>
                                                            <th>Description</th>
                                                            <th>Quantity</th>
                                                            <th>Units</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:if test="${tenderObj.tenderItemsLi != null}">
                                                            <c:forEach var="vendorItemObj" items="${tenderObj.tenderItemsLi}">
                                                                <tr class="gradeX">
                                                                    <td>${vendorItemObj.itemObj.itemName}</td>
                                                                    <td>${vendorItemObj.breifDesc}</td>
                                                                    <td>${vendorItemObj.itemQty}</td>
                                                                    <td>${vendorItemObj.itemObj.unitDTO.unitName}(${vendorItemObj.itemObj.unitDTO.unitCode})</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:if>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                                <!-- == End : Tender Items Details Form ======== -->
                                <!-- == Start : Tender Vendor Details Form ======== -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Map Vendor</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label>Vendor:</label>
                                                    <select name="vendorID" id='vendorID' class="form-control custom-select">
                                                        <option value="0">Select</option>
                                                        <c:if test="${vendorLi != null}">
                                                            <c:forEach items="${vendorLi}" var="vendorObj">
                                                                <option value="${fn:trim(vendorObj.vendorID)}">${vendorObj.vendorName}</option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <br/>
                                                    <label id="errorvendorID" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">Date For Closing: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="closingDate" id="closingDate" />
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorclosingDate" style="color: red"></label>
                                                </div>
                                                 <div class="form-group col-md-6 col-lg-4">
                                                    <label for="username">File No: </label>
                                                    <input type='text' class="form-control input-sm"
                                                           name="fileNo" id="fileNo" 
                                                           maxlength="40"/>
                                                    <label id="errorfileNo" style="color: red"></label>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- tile body -->
                                    </section>
                                </div>
                                <!-- == End : Tender Vendor Details Form ======== -->
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <span class="tools pull-right">
                                        <input type="button" value="Map Vendor" 
                                               class="btn btn-info " id="mapView" 
                                               onclick="fnMapVendor()"/>
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
                        
                        $("#vendorID").customselect();
                    });
                    function fnCancelView()
                    {
                        document.vTenderForm.action="<c:url value="pudtenderli.htm"/>";
                        document.vTenderForm.submit();
                    }
                    
                    function fnMapVendor()
                    {
                        //maptendertova
                        if(fnValidateForm())
                        {
                            document.vTenderForm.action="<c:url value="maptendertova.htm"/>";
                            document.vTenderForm.submit();
                        }
                    }
                    
                    function fnValidateForm()
                    {
                        var flagValidTotal = new Array();
                        var returnstring = false;
                        
                        $("#errorvendorID").html("");
                        $("#errorclosingDate").html("");
                        $("#errorfileNo").html("");
                        
                        var vID = $("#vendorID").val();
                        var cDate = $("#closingDate").val();
                        var fNo = $("#fileNo").val();
                        
                        if ($.trim(vID) === '0')
                        {
                            $("#errorvendorID").html("Please select Vendor");
                            flagValidTotal.push(false);
                        } else
                        {
                            $("#errorvendorID").html("");
                            flagValidTotal.push(true);
                        }
                        if ($.trim(cDate) === '')
                        {
                            $("#errorclosingDate").html("Please select Closing Date");
                            flagValidTotal.push(false);
                        } else
                        {
                            $("#errorclosingDate").html("");
                            flagValidTotal.push(true);
                        }
                        if ($.trim(fNo) === '')
                        {
                            $("#errorfileNo").html("Please enter File No#");
                            flagValidTotal.push(false);
                        } else
                        {
                            $("#errorfileNo").html("");
                            flagValidTotal.push(true);
                        }
                        
                        for (var i = 0; i < flagValidTotal.length; i++) {
                            if (flagValidTotal[i] == false) {
                                returnstring = flagValidTotal[i];
                                flagValidTotal = new Array();
                                break;
                            } else {
                                returnstring = true;
                            }
                        }
                        return returnstring;
                    }
                </script>
            </spring:form>
        </div>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.t4 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>