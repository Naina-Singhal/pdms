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
                <c:if test="${(userPermiLi.p4 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Postage Update</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .firstTab td:nth-child(1){
                width: 5%;
            }
            .firstTab td:nth-child(2){
                width: 5%;
            }
            .firstTab td:nth-child(3){
                width: 10%;
            }
            .firstTab td:nth-child(4){
                width: 10%;
            }
            .firstTab td:nth-child(5), td:nth-child(6){
                width: 20%;
            }
            .firstTab td:nth-child(7){
                width: 15%;
            }
            .firstTab td:nth-child(8){
                width: 15%;
            }
           .painput{
                width: 100%;
                height: 35px;
                border: none;
                
            }            
            .firstTab td{
                padding: 0px !important;
                text-align: center;
            }
            
        </style>
    </head>
    <c:set var="userType" value="User"/>
    <c:set var="userID" value="0"/>
    <c:if test="${sessionScope.USER_SESSION!=null}">
        <c:set var="userType" value="${sessionScope.USER_SESSION.employeeProfileDTO.employeeTypeDTO.employeeTypeName}"/>
        <c:set var="userID" value="${sessionScope.USER_SESSION.employeeID}"/>
    </c:if>
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
                        <h2>Postage Update<span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Postage Update </a>
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
                        <%-- START : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div id="form_page">
                        <div class="row">
                            <spring:form  id="first_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">                                            
                                            <h1 class="custom-font"><strong>Postage Update</strong> Details</h1>
                                            <div class="open_page_button">
                                                <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                    <button type='button' id="showRecord"
                                                            class="btn btn-info btn-rounded btn-sm"
                                                            onclick="openDDNumberRec();">Postage Update Record
                                                    </button> 
                                                </c:if>
                                            </div>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">  
                                            <input type="hidden" id="postageId" name="postageId" value=""/>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Enter Today's Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="toDayDate" id="toDayDate" 
                                                               value="" readonly/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorCloseButton" style="color: red"></label>
                                                </div> 
                                            </div>    
                                            <input type="hidden" id="status" name="status" value="1"/>
                                            <div class="row">
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="closeButton">
                                                            <span>Close</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                            </div>    
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT ITEM DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <div id="table_one">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <h1 class="custom-font"><strong>Items Required</strong> List</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorItemID" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="table-responsive" style="overflow-y: scroll;max-height:300px;">
                                                <table class="table table-bordered firstTab" id="" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th></th> 
                                                            <th>S.NO</th>
                                                            <th>Dispatch No</th>
                                                            <th>Nature</th>
                                                            <th>Party Name/Vendor Name</th>
                                                            <th>Vendor City</th> 
                                                            <th>Type Of Dispatch</th> 
                                                            <th>Amount</th>                                                             
                                                        </tr>
                                                    </thead>
                                                    <tbody id="stic_items">
                                                       
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        </div>
                                        <!-- /tile body -->
                                        <div class="tile-body">
                                        <spring:form  id="second_form">
                                        <div class="row">
                                            <div class="form-group div-wid-7">
                                                <label for="username">Total Amount: </label>
                                                <input type="text" name="totalAmt" id='totalAmt' 
                                                       class="form-control input-sm"
                                                       maxlength="40" />
                                                <label id="errorDate" style="color: red"></label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group div-wid-7">
                                                <label for="username">OT Balance: </label>
                                                <input type="text" name="otBalance" id='otBalance' 
                                                       class="form-control input-sm"
                                                       maxlength="40" />
                                                <label id="errorDate" style="color: red"></label>
                                            </div>
                                            <div class="form-group div-wid-7">
                                                <label for="username">CT Balance : </label>
                                                <input type="text" name="ctBalance" id='ctBalance' 
                                                       class="form-control input-sm"
                                                       maxlength="40"/>
                                                <label id="errorPurchaseUnit1" style="color: red"></label>
                                            </div>
                                            <div class="form-group div-wid-7">
                                                <label for="username">Postage: </label>
                                                <input type="text" name="postage" id='postage' 
                                                       class="form-control input-sm"
                                                       maxlength="40"/>
                                                <label id="errorPurGfNo" style="color: red"></label>
                                            </div>
                                        </div>
                                        </div>
                                        </spring:form>
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
                                        <h1 class="custom-font"><strong>Postage Update(s)</strong> List</h1>
                                        <div class="open_page_button">
                                            <c:if test="${(userPermiLi.d17 == 1)}">                                                
                                                <button type='button' id="showForm"
                                                        class="btn btn-info btn-rounded btn-sm"
                                                        onclick="openDDNumberForm();">Postage Update Form
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
                    </div>
                </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/despatch/PostageUpJs.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js//bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            
        </script>
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.p4 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>