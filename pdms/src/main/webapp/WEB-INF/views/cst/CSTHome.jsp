<%-- 
    Document   : CSTHome
    Created on : Feb 18, 2017, 1:24:30 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.g4 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Comparative Statement</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.css"/>">
        <style>
            #table_sele1 {
                width: 133px !important;
            }
            #table_sele2 {
                width: 222px !important;
            }
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .inCstVenCat{
                padding-bottom: 10px;
            }
            #venInCstPre{
                padding-right: 50px;
            }
            #cstTable{
                overflow: auto;
                white-space: nowrap;
            }
            #cstTable td, #cstTableForItem td, #cstTableForVenBasd td{
                padding: 0px !important;
                padding-left: 3px !important;
            }
            #cstTable #stic_cstEntryTotal td{
                    padding-bottom: 8px !important;
                    padding-top: 8px !important;
                    font-weight: 600;
                    padding-left: 5px !important;
            }
            #cstTable td:nth-child(1), #cstTable th:nth-child(1){
                    width: 1%;
                    padding-left: 12px !important;
                    padding-top: 9px !important;
            }
            #cstTable td:nth-child(2), #cstTable th:nth-child(2){
                width: 2%;
            }
            #cstTable td:nth-child(3), #cstTable th:nth-child(3){
                width: 4%;
            }
            #cstTable td:nth-child(4), #cstTable th:nth-child(4){
                width: 6%;
            }
            #cstTable td:nth-child(30), #cstTable th:nth-child(30){
                width: 7%;
            }
            #cstTable td, #cstTable th{
               
            }
            #stic_totLandingCost{
                border: none;
            }
            .dropdown-menu{
                width: 300px !important;
                height: auto;
                max-height: 200px;
                overflow-x: hidden;
            }
            #loader {
                position: absolute;
                left: 50%;
                top: 50%;
                z-index: 1;
                width: 150px;
                height: 150px;
                margin: -75px 0 0 -75px;
                border: 16px solid #f3f3f3;
                border-radius: 50%;
                border-top: 16px solid #3498db;
                width: 120px;
                height: 120px;
                -webkit-animation: spin 2s linear infinite;
                animation: spin 2s linear infinite;
            }

            @-webkit-keyframes spin {
                0% { -webkit-transform: rotate(0deg); }
                100% { -webkit-transform: rotate(360deg); }
            }

            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }

            /* Add animation to "page content" */
            .animate-bottom {
                position: relative;
                -webkit-animation-name: animatebottom;
                -webkit-animation-duration: 1s;
                animation-name: animatebottom;
                animation-duration: 1s
            }

            @-webkit-keyframes animatebottom {
                from { bottom:-100px; opacity:0 } 
                to { bottom:0px; opacity:1 }
            }

            @keyframes animatebottom { 
                from{ bottom:-100px; opacity:0 } 
                to{ bottom:0; opacity:1 }
            }

            #myDiv {
                display: none;
                text-align: center;
            } 
            #cstTableForVenBasd td:nth-child(4){
                width: 3%;
            }
        </style>    
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
                        <div class="display_msg_success_Ma">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error_Ma">
                            <label id="errorDupItem" class="label_error_msg"></label>
                        </div>
                    </div>
                    <div class="pagecontent">       
                        <%-- **************************************************************** --%>
                        <%-- ******** START : SELECT INDENT NUMBER ************************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="mapVenH">
                            <spring:form  id="map_Form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Map vendor(s)</strong> Details</h1>
                                            
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">File No: </label>                                                    
                                                    <select name="FileNumber" id="FileNumber" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                    </select>
                                                    <label id="errorFileNo" style="color: red"></label>
                                                </div>  
                                                <div class="form-group col-md-6 col-lg-2 p-20">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="fetchIndent">
                                                        <span>Fetch Indent Details</span>
                                                    </button>
                                                </div>
                                            </div>
                                           
                                           
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>

                        <div class="row" id="indeviewH">
                            <div class="col-md-12">
                                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                    <div class="panel panel-default">
                                        <div class="panel-heading" role="tab" id="headingOne">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
                                                   aria-expanded="false" aria-controls="collapseOne" class="collapsed">
                                                    <h4 class="custom-font">
                                                        Click To View Or Hide<strong>Indent</strong> Details
                                                    </h4>
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                                            <div class="panel-body">
                                                <div class="row" id="stic_indent" style="margin-bottom: 31px;border-bottom: 1px solid rgba(180, 183, 180, 0.46);">

                                                </div>
                                                <div class="row" id="venidshow">
                                                    <div class="form-group col-md-6 col-lg-4" style="width: 150px !important;">
                                                        <label>Item Category:</label><br/>
                                                        <label id="stic_category"></label>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4" style="width: 47% !important;">
                                                        <div>
                                                            <div style="float: left;margin-right: 5px;">
                                                                <label>Vendor Name :</label><br/>                                                        
                                                                <input class="typeaheadven form-control" style="width:300px;" type="text" id="venderName" />
                                                                <label id="errorVenName" style="color: red"></label>
                                                            </div>
                                                            <div style="float: left;">
                                                                <label>&nbsp;</label><br/>  
                                                                <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                        id="VendorNameButton">
                                                                    <span>Click</span>
                                                                </button>
                                                            </div>
                                                            <div style="float: right;">
                                                                <label>&nbsp;</label><br/>  
                                                                <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                        id="VendorBasedCst">
                                                                    <span>Vendor Based</span>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4" style="width: 37% !important;">
                                                        <label>&nbsp;</label><br/>
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                id="ItemBsedData">
                                                            <span>Item Based</span>
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="row" id="itemhidshow">  
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <label>Item Category:</label><br/>
                                                        <label id="stic_categoryItem"></label>
                                                    </div>
                                                    <div class="form-group div-wid-7" style="width: 28%;">

                                                        <label for="username">Item Code: </label>	
                                                        <input class="typeahead form-control" style="width:300px;" type="text" id="itemcode_bs" />
                                                        <label id="AddItemName" style="color: black"></label>
                                                    </div>
                                                    <div class="form-group col-md-6 col-lg-4">
                                                        <div>
                                                            <div style="float: left;">
                                                            <label>&nbsp;</label><br/>
                                                            <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                    id="FetchItemBsData">
                                                                <span>Click</span>
                                                            </button>
                                                            </div>
                                                            <div style="float: left;margin-left: 30px;">
                                                            <label>&nbsp;</label><br/>
                                                            <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                                    id="VendorCSTt">
                                                                <span>Vendor Details</span>
                                                            </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                      
                        <%-- **************************************************************** --%>
                        <%-- ************ START : VENDOR LIST DETAIL(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row" id="vendorListH">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Vendor</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>Vendor ID</th>
                                                            <th>Vendor Code</th>
                                                            <th>Vendor Name</th>
                                                            <th>Address</th>
                                                            <th>Phone</th>
                                                            <th>City</th>  
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody  id="add_vendors">
                                                    </tbody>
                                                    <tbody  id="add_venView">
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : VENDOR LIST DETAIL(S)************** --%>
                        <%-- **************************************************************** --%>
                        
                         <%-- **************************************************************** --%>
                        <%-- ************ START : CST PREPARATION LIST ENTRIES(S)************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row" id="cstEntryH">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>CST Entry</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row inCstVenCat">
                                                <strong>Vendor Name:</strong>&nbsp;&nbsp;&nbsp;<label id="venInCstPre"></label>
                                                <strong>Item Category: </strong>&nbsp;&nbsp;&nbsp;<label id="cateInCstPre"></label>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered" id="cstTable" style="width:300% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl NO</th>
                                                            <th>CST Type</th>
                                                            <th>Item Name</th>
                                                            <th>Qty</th>
                                                            <th>Units</th>
                                                            <th>Basic Rate</th>
                                                            <th>Price</th>
                                                            
                                                            <th>Discount</th>
                                                            <th>Sub Total</th>
                                                            <th>P&F%</th>  
                                                            <th>Excise Duty%</th>
                                                            <th>Sales Tax%</th>
                                                            
                                                            <th>Insurance</th>
                                                            <th>Custom Duty%</th>  
                                                            <th>Cleaning Charging</th>
                                                            <th>Inland Freight</th>
                                                            
                                                            <th>Freight%</th>
                                                            <th>Installation</th>
                                                            <th>Service Tax %</th>
                                                            <th>Service Tax</th>
                                                            <th>Training Charges</th>
                                                            <th>Inspection</th>                                                            
                                                            <th>Testing Amt</th>
                                                            <th>GST on ITIT</th>
                                                            <th>Any Other</th>
                                                            <th>Octroi Entry Tax%</th>
                                                            <th>Sample</th>
                                                            <th>Interest</th>
                                                            <th>Delivery Period</th>
                                                            <th>Payment</th>
                                                            <th>Validity</th>
                                                            <th>Validity Date</th>
                                                            <th>Landing Cost</th>
                                                            <th>Total Amt</th>
                                                            <th>Total Landing Cost</th>                                                            
                                                            <th>HSN Code</th>
                                                            <th>MRP</th>
                                                            <th>Remarks</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody  id="stic_cstEntry">
                                                    </tbody>
                                                    <tbody  id="stic_cstEntryTotal">
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <span class="tools pull-right">                                    
                                            <input type="button" value="Save" class="btn btn-info" id="saveCSTEntry"/> 

                                            <input type="button" value="Cancel" class="btn btn-info " id="cancelCSTEntry" />
                                        </span>
                                    </div>
                                </div>
                            </div>                            
                        <%-- **************************************************************** --%>
                        <%-- ************ END : CST PREPARATION LIST ENTRIES(S)************** --%>
                        <%-- **************************************************************** --%>
                        
                        
        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM BASED CST DATA(S)************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="ItemBasedCstPage">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Item Based CST </strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            
                                            <div class="row">
                                                <div class="row inCstVenCat">
                                                    <strong>Item Id :</strong>&nbsp;&nbsp;&nbsp;<label id="itemID_c"></label>
                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <strong>Item Code :</strong>&nbsp;&nbsp;&nbsp;<label id="dec_c"></label>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <strong>Item Name :</strong>&nbsp;&nbsp;&nbsp;<label id="itemname_c"></label>                                                    
                                                </div>
                                            </div>    
                                            
                                        </div>
                                         <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered" id="cstTableForItem" style="width:400% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl NO</th>
                                                            <th>CST Type</th>
                                                            <th>Vendor Code</th>
                                                            <th>Vendor Name</th>  
                                                            <th>Qty</th>
                                                            <th>Units</th>
                                                            <th>Basic Rate</th>
                                                            <th>Price</th>
                                                            
                                                            <th>Discount</th>
                                                            <th>Sub Total</th>
                                                            <th>P&F</th>  
                                                            <th>Excise Duty</th>
                                                            <th>Sales Tax</th>
                                                            
                                                            <th>Insurance</th>
                                                            <th>Custom Duty</th>  
                                                            <th>Cleaning Charging</th>
                                                            <th>Inland Freight</th>
                                                            
                                                            <th>Freight</th>
                                                            <th>Installation</th>
                                                            <th>Service Tax %</th>
                                                            <th>Service Tax</th>
                                                            <th>Training Charges</th>
                                                            <th>Inspection</th>
                                                            <th>Testing Amt</th>
                                                            <th>GST On ITIT</th>
                                                            <th>Any Other Charges</th>
                                                            <th>Octroi Entry Tax%</th>
                                                            <th>Sample</th>
                                                            <th>Interest</th>
                                                            <th>Delivery Period</th>
                                                            <th>Payment</th>
                                                            <th>Validity</th>
                                                            <th>Validity Date</th>
                                                            <th>Landing Cost</th>
                                                            <th>Total Amt</th>
                                                            <th>Total Landing Cost</th>
                                                            <th>HSN Code</th>
                                                            <th>MRP</th>
                                                            <th>Remarks</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody  id="stic_cstForItem">
                                                    </tbody>
                                                    <tbody  id="stic_cstItemTotal">
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                        <div class="row">
                                        <div class="col-md-12">
                                            <span class="tools pull-right">                                    
                                                <input type="button" value="Save" class="btn btn-info" id="saveCSTItemBased"/> 

                                                <input type="button" value="Cancel" class="btn btn-info " id="cancelCSTItemBased" />
                                            </span>
                                        </div>
                                </div>
                                    </section>
                                </div>
                                
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : ITEM BASED CST DATA(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : VENDOR BASED CST DATA(S)************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="VendorBasedCstPage">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Vendor Based CST </strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            
                                            <div class="row">
                                                <div class="row VenCat">
                                                    <input type="hidden" name="vendorIdV"  id="vendorIdV" value="" />
                                                    <strong>Vendor Id :</strong>&nbsp;&nbsp;&nbsp;<label id="vendorIdd"></label>
                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <strong>Vendor Code :</strong>&nbsp;&nbsp;&nbsp;<label id="vendorCodee"></label>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <strong>Vendor Name :</strong>&nbsp;&nbsp;&nbsp;<label id="vendorNamme"></label>  
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <strong>Vendor City :</strong>&nbsp;&nbsp;&nbsp;<label id="vendorCitty"></label> 
                                                </div>
                                            </div>    
                                            
                                        </div>
                                         <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered" id="cstTableForVenBasd" style="width:400% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl NO</th>
                                                            <th>CST Type</th>
                                                            <th>Item Code</th>
                                                            <th>Item Name</th>
                                                            <th>Qty</th>
                                                            <th>Units</th>
                                                            <th>Basic Rate</th>
                                                            <th>Price</th>
                                                            
                                                            <th>Discount</th>
                                                            <th>Sub Total</th>
                                                            <th>P&F</th>  
                                                            <th>Excise Duty</th>
                                                            <th>Sales Tax</th>
                                                            
                                                            <th>Insurance</th>
                                                            <th>Custom Duty</th>  
                                                            <th>Cleaning Charging</th>
                                                            <th>Inland Freight</th>
                                                            
                                                            <th>Freight</th>
                                                            <th>Installation</th>
                                                            <th>Service Tax %</th>
                                                            <th>Service Tax</th>
                                                            <th>Training Charges</th>
                                                            <th>Inspection</th>
                                                            <th>Testing Amt</th>
                                                            <th>GST On ITIT</th>
                                                            <th>Any Other</th>
                                                            <th>Octroi Entry Tax%</th>
                                                            <th>Sample</th>
                                                            <th>Interest</th>
                                                            <th>Delivery Period</th>
                                                            <th>Payment</th>
                                                            <th>Validity</th>
                                                            <th>Validity Date</th>
                                                            <th>Landing Cost</th>
                                                            <th>Total Amt</th>
                                                            <th>Total Landing Cost</th>
                                                            <th>HSN Code</th>
                                                            <th>MRP</th>
                                                            <th>Remarks</th>                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody  id="stic_cstForVend">
                                                    </tbody>
                                                    <tbody  id="stic_cstVenTotal">
                                                    </tbody>
                                                </table>
                                            </div>
                                            
                                        </div>
                                        <!-- /tile body -->
                                        <div class="row">
                                        <div class="col-md-12">
                                            <span class="tools pull-right">                                    
                                                <input type="button" value="PDF" class="btn btn-info" id="saveCSTVenBasedPdf"/> 

                                                <input type="button" value="Cancel" class="btn btn-info " id="cancelCSTVenBased" />
                                            </span>
                                        </div>
                                </div>
                                    </section>
                                </div>                                
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : ITEM BASED CST DATA(S)******************* --%>
                        <%-- **************************************************************** --%>
                        
                        
<%--    ------------------------------------------------------------------------------------------------------------------------------------------------ --%>                        
                        
                        <!-- == End :Page Form ======== -->

                    </div>
            </section>
            
        </div>
        <!-- ====================================================
        ================= End : Application Content ===========
        ===================================================== -->        
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/assets/js/vendor/sdropdown/jquery-customselect.js"/>"></script>
        <script src="<c:url value="/appjs/cstJS.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>        
        <script src="<c:url value="/assets/js/bootstrap3-typeahead.min.js"/>" type="text/javascript"></script>
        
        <script>
           
        </script>
    </body>
</html>
 </c:if>
    <c:if test="${(userPermiLi.g4 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>