<%-- 
    Document   : UserList
    Created on : Oct 1, 2016, 7:44:53 AM
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
                <c:if test="${(userPermiLi.c9 == 1)}"> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            
            .AuthDropDown{
                padding-top: 0px;                
                padding-left: 20px;
            }
            .AuthDropDown select{
                width: 300px;
                border-radius: 3px;
            }
            .comm-option{
                width: 90%;                
                position: relative;
                top: -29px;                
                border: 1px;
                padding-left: 5px;
                margin-left: 2px;
            }
            .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }
            .table-tab td{
                padding: 0px !important;
                text-align: center;
            }
            .tableA td:nth-child(6), td:nth-child(7){
                width: 25%;
            }
            .tableC td:nth-child(6), td:nth-child(7){
                width: 25%;
            }
            .tableR td:nth-child(6), td:nth-child(7){
                width: 25%;
            }
            .textareaPOE{
                border: 1px solid rgba(128, 128, 128, 0.38);
                border-radius: 2px;
                height: 30px;
                width: 100%;
            }
        </style>
    </head>
    <c:set var="userType" value="User"/>
    <c:set var="userID" value="0"/>
    <c:if test="${sessionScope.USER_SESSION!=null}">
        <c:set var="userType" value="${sessionScope.USER_SESSION.employeeProfileDTO.employeeTypeDTO.employeeTypeName}"/>
        <c:set var="userID" value="${sessionScope.USER_SESSION.employeeID}"/>
    </c:if>
    <input type="hidden" name="authorUserid" id="authorUserid" value="${userID}">
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
                        <h2>RCIV Authorisation(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">RCIV Authorisation(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="AuthDropDown">
                        <select name="rcivSelect" class="form-control input-sm" id="rcivSelect">
                            <option value="select" selected="selected">Select</option>
                            <option value="RCIV_Authorisation">RCIV Authorisation</option>
                            <option value="RCIV_Release">RCIV Release</option>
                            <option value="RCIV_Control">RCIV Control</option>
                        </select>
                        <label id="auth_label" class="label_error_msg"></label>
                    </div>
                    <div class="pagecontent">
                        <div class="display_msg_success_Ma">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error_Ma">
                            <label id="errorDupItem" class="label_error_msg"></label>
                        </div>
                    </div>
                    <%-- **************************************************************** --%>
                    <%-- ******** START : CSRV AUTHORISATION  DETAIL(S)****************** --%>
                    <%-- **************************************************************** --%>
                    <div class="pagecontent" id="first_authorisation">
                        <div class="row">
                            <spring:form id="authorisation_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>RCIV Authorisation(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c17 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">RCIV Authorisation(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="rcivAuthoriID" name="rcivAuthoriID" value="" />
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>                                                    
                                                    <select name="poNumber" id="poNumber" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPoNumber" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Number: </label>                                                    
                                                    <select name="requisitionNo" id="requisitionNo" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorRequiNoAut" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Date: </label>                                                    
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="requisitionDate" id="requisitionDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>                                                                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Please Select: </label>
                                                    <select name="authoriStatus" id="authoriStatus" class="form-control input-sm">
                                                        <option value="null">Select</option>
                                                        <option value="Yet to authorise">Yet to authorise</option>
                                                        <option value="Authorised but material yet to be issued">Authorised but material yet to be issued</option>
                                                        <option value="Not authorised / cancelled">Not authorised / cancelled</option>
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="date" id="date" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue Type:</label>
                                                    <input type="text" name="issueType" id='issueType' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue To </label>
                                                    <input type="text" name="issueTo" id='issueTo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Disposal: </label>
                                                    <input type="text" name="disposal" id='disposal' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue From Other Section Name: </label>
                                                    <input type="text" name="issueFromOSecNa" id='issueFromOSecNa' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Plant:</label>
                                                    <input type="text" name="plant" id='plant' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value="Munuguru" />
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                 <div class="form-group div-wid-7">
                                                    <label for="username">Store Requisition:</label>
                                                    <input type="text" name="storeRequisition" id='storeRequisition' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="controlDate" id="controlDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>                                                
                                            </div>                                            
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indenter Name: </label>                                                    
                                                    <select name="indentorName" id="indentorName" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="section" id="section" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Job Allocation: </label>
                                                    <input type="text" name="jobAllocation" id='jobAllocation' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>                                                                                              
                                            </div>  
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery At: </label>
                                                    <input type="text" name="deliveryAt" id='deliveryAt' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Item Covered: </label>                                                    
                                                    <textarea type="text" name="itemCovered" id='itemCovered' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>                           
                        </div>                        
                            </spring:form>
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div id="tableA_form">
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>RCIV Authorization Items Required</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab tableA" id="" style="width:120% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>Store</th>
                                                            <th>Group</th>
                                                            <th>Card No</th>
                                                            <th>Item Code</th>
                                                            <th>Description</th>
                                                            <th>Unit</th>
                                                            <th>Required Quantity</th>                                                                                                                        
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_itemsA">

                                                    </tbody>
                                                </table>
                                            </div>
                                            </div>
                                        </div>
                                        <div class="tile-body">
                                            <spring:form id="authori_form_sec">
                                            <div class="row row-ext-7">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized Y/N: </label>
                                                    <select name="AuthorisedYesNo" id="AuthorisedYesNo" class="form-control input-sm">
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized By </label>                                                    
                                                    <select name="authorisedBy" id="authorisedBy" class="form-control input-sm">
                                                                                                          
                                                                                                               
                                                    </select>
                                                    <input class="editOptionA comm-option" style="display:none;" placeholder="" />
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="authorisedByDate" id="authorisedByDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="remarks" id='remarks' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                        </spring:form>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <div class="col-md-12">

                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="saveDdNumber">
                                        <span>Save</span>
                                    </button>                                
                                </span>
                                <span class="tools pull-right">
                                    <div id="updateDiv" style="display: none">                                    
                                        <button class="btn btn-info" type="button"
                                                id="updateDdNumber" >
                                            <span>Update</span>
                                        </button>

                                        <button class="btn btn-info" type="button"
                                                id="cancelDdNumber">
                                            <span>Cancel</span>
                                        </button>                                   
                                    </div>
                                </span>
                            </div>
                        </div>   
                    </div>
                        <%-- **************************************************************** --%>
                        <%-- **********START : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="form_record">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>RCIV Authorisation(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c9 == 1)}">                                                
                                                    <button type='button' id="showForm"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">RCIV Authorisation(s) Form
                                                    </button> 
                                                </c:if>
                                            </div>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive">
                                            <div id="show_table">
                                                
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- *********** END : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        
                        
                        
                        

                   <%-- **************************************************************** --%>
<%-- ****************************** END : RCIV AUTHORISATION (S)***************************************************************** --%>
                   <%-- **************************************************************** --%>     
                   
                    <!-- continue second rciv  release -->
                    <!-- == Start :rciv release Page Form ======== -->
                    <div class="pagecontent" id="second_release">                        
                        <div class="row">
                            <spring:form id="release_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>RCIV Release(s)</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c15 == 1)}">                                                
                                                    <button type='button' id="showRecordR"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">RCIV Release(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="rcivReleaseID" name="rcivReleaseID" value="" />
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>                                                    
                                                    <select name="poNumberR" id="poNumberR" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPoNumberR" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Number: </label>                                                    
                                                    <select name="requisitionNumberR" id="requisitionNumberR" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorRequiNoR" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="requisitionNoDateR" id="requisitionNoDateR" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>                                             
                                                
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dateR" id="dateR" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue Type:</label>
                                                    <input type="text" name="issueTypeR" id='issueTypeR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue To </label>
                                                    <input type="text" name="issueToR" id='issueToR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row">   
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Disposal: </label>
                                                    <input type="text" name="disposalR" id='disposalR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue From Other Section Name: </label>
                                                    <input type="text" name="issueFromOthSecNaR" id='issueFromOthSecNaR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Store Requisition</label>
                                                    <input type="text" name="storeRequisitionR" id='storeRequisitionR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                                
                                            </div>                                           
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Plant:</label>
                                                    <input type="text" name="plantR" id='plantR' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value="Munuguru" />
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indentor Name: </label>                                                    
                                                    <select name="indentorNameR" id="indentorNameR" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="sectionR" id="sectionR" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>                                                                                               
                                            </div>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Job Allocation: </label>
                                                    <input type="text" name="jobAllocationR" id='jobAllocationR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery At: </label>
                                                    <input type="text" name="deliveryAtR" id='deliveryAtR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Item Covered: </label>                                                    
                                                    <textarea type="text" name="itemCoveredR" id='itemCoveredR' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                        </div>
                                    </section>
                                </div>
                        </div>
                            </spring:form>                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div id="tableR_form">
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>RCIV Release Items Required</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab tableR" id="" style="width:120% !important">
                                                    <thead>
                                                        <tr><th></th>
                                                            <th>Sl No</th>
                                                            <th>Store</th>
                                                            <th>Group</th>
                                                            <th>Card No</th>
                                                            <th>Item Code</th>
                                                            <th>Description</th>
                                                            <th>Unit</th>
                                                            <th>Required Quantity</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_itemsR">

                                                    </tbody>
                                                </table>
                                            </div>
                                            </div>
                                        </div>
                                          <div class="tile-body">
                                            <spring:form id="release_form_sec">
                                            <div class="row  row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized By: </label>                                                    
                                                    <select name="authorisedByR" id="authorisedByR" class="form-control input-sm">
                                                                                                          
                                                                                                               
                                                    </select>
                                                     <input class="editOption comm-option" style="display:none;" placeholder="" />
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized By Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="authorisedByDateR" id="authorisedByDateR" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="authorisedRemarksR" id='authorisedRemarksR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Released Y/N: </label>
                                                    <select name="releasedYesNoR" id="releasedYesNoR" class="form-control input-sm">
                                                        <option value="yes">Yes</option>
                                                        <option value="no">No</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Released By: </label>                                                    
                                                    <select name="releasedByR" id="releasedByR" class="form-control input-sm">
                                                                                                               
                                                    </select>
                                                     <input class="editOptionRR comm-option" style="display:none;" placeholder="" />
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Released By Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="releasedByDateR" id="releasedByDateR" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="releasedRemarksR" id='releasedRemarksR' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                        </spring:form>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <div class="col-md-12">

                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="saveDdNumberR">
                                        <span>Save</span>
                                    </button>                                
                                </span>
                                <span class="tools pull-right">
                                    <div id="updateDivR" style="display: none">                                    
                                        <button class="btn btn-info" type="button"
                                                id="updateDdNumberR" >
                                            <span>Update</span>
                                        </button>

                                        <button class="btn btn-info" type="button"
                                                id="cancelDdNumberR">
                                            <span>Cancel</span>
                                        </button>                                   
                                    </div>
                                </span>
                            </div>
                        </div>
                        
                        
                    </div>
                     <%-- **************************************************************** --%>
                        <%-- **********START : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="form_recordR">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>RCIV Release(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c9 == 1)}">                                                
                                                    <button type='button' id="showFormR"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">RCIV Release(s) Form
                                                    </button> 
                                                </c:if>
                                            </div>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive">
                                            <div id="show_tableR">
                                                
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- *********** END : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        
                        
                        
   
                   <!-- start (three) rciv control  -->
<!-- ********************* End :Rciv control form ********************************************************** -->
                    <!-- == Start :Rciv control ======== -->
                    <div class="pagecontent" id="three_control">
                        <%-- **************************************************************** --%>
                        <%-- ******** START : RCIV CONTROL  DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form id="control_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>RCIV Control(s)</strong> Details</h1>                                            
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c16 == 1)}">                                                
                                                    <button type='button' id="showRecordC"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">RCIV Control(s) Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <input type="hidden" id="rcicControlID" name="rcicControlID" value="" />
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">PO Number: </label>                                                    
                                                    <select name="poNumberC" id="poNumberC" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorPoNumberC" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Number: </label>                                                    
                                                    <select name="requisitionNumberC" id="requisitionNumberC" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorRequiNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Please Select: </label>
                                                    <select name="authorisedStatusC" id="authorisedStatusC" class="form-control input-sm">
                                                        <option value="null">Select</option>
                                                        <option value="Yet to authorise">Yet to authorise</option>
                                                        <option value="Authorised but material yet to be issued">Authorised but material yet to be issued</option>
                                                        <option value="Not authorised / cancelled">Not authorised / cancelled</option>
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>                                                                                               
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Store Requisition:</label>
                                                    <input type="text" name="storeRequisitionC" id='storeRequisitionC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="dateC" id="dateC" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue Type:</label>
                                                    <input type="text" name="issueTypeC" id='issueTypeC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue To </label>
                                                    <input type="text" name="issueToC" id='issueToC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Plant:</label>
                                                    <input type="text" name="plantC" id='plantC' 
                                                           class="form-control input-sm"
                                                           maxlength="40" value="Munuguru" />
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Disposal: </label>
                                                    <input type="text" name="disposalC" id='disposalC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>                                                
                                            </div>                                            
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue From Other Section Name: </label>
                                                    <input type="text" name="issueFrOtherSecNaC" id='issueFrOtherSecNaC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="requisitionDateC" id="requisitionDateC" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Control Number: </label>
                                                    <input type="text" name="controlNumberC" id='controlNumberC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Control Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="controlDateC" id="controlDateC" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Indenter Name: </label>                                                    
                                                    <select name="indentorNameC" id="indentorNameC" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>                                                    
                                                    <select name="sectionC" id="sectionC" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>                                                
                                            </div>
                                            <div class="row"> 
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Job Allocation: </label>
                                                    <input type="text" name="jobAllocationC" id='jobAllocationC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery At: </label>
                                                    <input type="text" name="deliveryAtC" id='deliveryAtC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Item Covered: </label>                                                    
                                                    <textarea type="text" name="itemCoveredC" id='itemCoveredC' class="textareaPOE">                                                               
                                                    </textarea>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                        </div>
                                    </section>
                                </div>                            
                        </div>                    
                            </spring:form>
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div id="tableC_form">
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>RCIV Control Items Required</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-x: scroll;">
                                                <table class="table table-bordered table-tab tableC" id="" style="width:120% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>Sl No</th>
                                                            <th>Store</th>
                                                            <th>Group</th>
                                                            <th>Card No</th>
                                                            <th>Item Code</th>
                                                            <th>Description</th>
                                                            <th>Unit</th> 
                                                            <th>Required Quantity</th>                                                                                                                       
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_itemsC">

                                                    </tbody>
                                                </table>
                                            </div>
                                            </div>
                                        </div>
                                         <div class="tile-body">
                                            <spring:form id="control_form_sec">
                                            <div class="row  row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized By: </label>                                                    
                                                     <select name="authorisedByNoC" id="authorisedByNoC" class="form-control input-sm">
                                                                                                               
                                                    </select>
                                                     <input class="editOptionC comm-option" style="display:none;" placeholder="" />
                                                    
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorized Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="authorisedByDateC" id="authorisedByDateC" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="authorisedRemarksC" id='authorisedRemarksC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Released By: </label>                                                   
                                                    <select name="releasedByNoC" id="releasedByNoC" class="form-control input-sm">
                                                                                                               
                                                    </select>
                                                     <input class="editOptionCC comm-option" style="display:none;" placeholder="" />
                                                    
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Released Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="releasedByDateC" id="releasedByDateC" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="releasedRemarksC" id='releasedRemarksC' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                        </spring:form>
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)*********** --%>
                        <%-- **************************************************************** --%>                        
                        <div class="row">
                            <div class="col-md-12">

                                <span class="tools pull-right">                                
                                    <button class="btn btn-info" type="button"
                                            id="saveDdNumberC">
                                        <span>Save</span>
                                    </button>                                
                                </span>
                                <span class="tools pull-right">
                                    <div id="updateDivC" style="display: none">                                    
                                        <button class="btn btn-info" type="button"
                                                id="updateDdNumberC" >
                                            <span>Update</span>
                                        </button>

                                        <button class="btn btn-info" type="button"
                                                id="cancelDdNumberC">
                                            <span>Cancel</span>
                                        </button>                                   
                                    </div>
                                </span>
                            </div>
                        </div>
                        
                        
                        
                    </div>
                        <%-- **************************************************************** --%>
                        <%-- **********START : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row" id="form_recordC">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>RCIV Control(s)</strong> List</h1>
                                        <div class="open_page_button">
                                                <c:if test="${(userPermiLi.c9 == 1)}">                                                
                                                    <button type='button' id="showFormC"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberForm();">RCIV Control(s) Form
                                                    </button> 
                                                </c:if>
                                            </div>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    <div class="tile-body">
                                        <div class="table-responsive">
                                            <div id="show_tableC">
                                                
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /tile body -->
                                </section>
                            </div>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- *********** END : TYPE OF DISPATCH DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>

                        
                        
                        
                </div>
           </section>      
                          <!-- == Start :CST Modal Dialog  for Authorization ======== -->
            <div class="modal fade" id="cstModalItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:40%;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Template For Item Covered!</h3>
                            </div>
                            <div class="modal-bodyItem">

                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button> 
                            </div>
                        </div>
                    </div>
               
            </div>
            <!-- == End :CST Modal Dialog ======== -->  
            
              <!-- == Start :CST Modal Dialog  for Release======== -->
            <div class="modal fade" id="cstModalItemR" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:40%;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Template For Item Covered(R)!</h3>
                            </div>
                            <div class="modal-bodyItemR">

                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button> 
                            </div>
                        </div>
                    </div>
               
            </div>
            <!-- == End :CST Modal Dialog ======== -->  
            
              <!-- == Start :CST Modal Dialog  for Control======== -->
            <div class="modal fade" id="cstModalItemC" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
               
                    <div class="modal-dialog modal-lg" style="width:40%;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Template For Item Covered!(C)</h3>
                            </div>
                            <div class="modal-bodyItemC">

                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button> 
                            </div>
                        </div>
                    </div>
               
            </div>
            <!-- == End :CST Modal Dialog ======== -->  
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/RcivAuthorisationJS.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>

        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.c9 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>