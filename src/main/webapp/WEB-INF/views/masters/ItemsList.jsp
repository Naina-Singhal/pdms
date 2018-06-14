<%-- 
    Document   : ItemsList
    Created on : Sep 23, 2016, 5:43:09 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.m3 == 1)}">  
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Item(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .table td:nth-child(1){
                width: 50px !important;
            }
            .table td:nth-child(3), td:nth-child(4){
                width: 230px !important;
            }
            .table td:nth-child(2), td:nth-child(6){
                width: 120px !important;
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
                        <h2>Item(s) <span> Add/ Update Item(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Item(s)</a>
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
                        <c:if test="${ermsg != null}">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="alert alert-warning alert-dismissable">
                                        <button type="button" class="close" data-dismiss="alert" 
                                                aria-hidden="true">&times;</button>
                                        <strong>Error!</strong>${ermsg}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <%-- END : DISPLAY STATUS MESSAGE--%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ******** START : ADD/EDIT ITEM DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addItemForm" commandName="itemObj" id="item_form">
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <input type="hidden" name="exisItemCode" id="eItemCode" value=""/>
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <h1 class="custom-font"><strong>Item</strong> Details</h1>
                                            <div class="row">&nbsp;</div>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label id="errorDupItem" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Item Name: </label>
                                                    <input type="text" name="itemName" id='itemName' 
                                                           class="form-control input-sm"
                                                           maxlength="100"/>
                                                    <label id="erroritemName" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Item Code: </label>
                                                    <input type="text" name="itemCode" id='itemCode' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="erroritemCode" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Category: </label>
                                                    <br/>                                                    
                                                    <select name="categoryDTO.categoryID" id="categoryID" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <br/>
                                                    <label id="errorcategoryID" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Current Stock: </label>
                                                    <input type="text" name="currentStock" id='currentStock' 
                                                           class="form-control input-sm"
                                                           maxlength="8" 
                                                           onKeyPress="return numbersonly(this, event)"/>
                                                    <label id="errorcurrentStock" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="username">Unit: </label>
                                                    <br/>                                                    
                                                    <select name="unitDTO.unitID" id="unitID" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        
                                                    </select>
                                                    <br/>
                                                    <label id="errorunitID" style="color: red"></label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="username">Description: </label>
                                                    <textarea name="description"
                                                              id="description"
                                                              class="form-control input-sm"></textarea>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="addItem">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" >
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateItemData" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelItem">
                                                        <span>Cancel</span>
                                                    </button>
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
                        <%-- ******** START : ITEM(s) LIST DETAIL(S)************************* --%>
                        <%-- **************************************************************** --%>
                    

                        <div class="row">
                            <!-- col -->
                            <div class="col-md-12">
                                <!-- tile -->
                                <section class="tile">
                                    <!-- tile header -->
                                    <div class="tile-header dvd dvd-btm">
                                        <h1 class="custom-font"><strong>Item(s)</strong> List</h1>
                                    </div>
                                    <!-- tile header -->
                                    <!-- tile body -->
                                    
                                        <div class='table-responsive'>
                                            <table class='table table-custom row-border hover order-column' id="basic-usage" style='width:100% !important'>
                                               
                                                <thead>
                                                    <tr>
                                                        <th>Item Id</th>
                                                        <th>Item Code</th>
                                                        <th>Item Name</th>
                                                        <th>Description</th>
                                                        <th>Category</th>
                                                        <th>Unit</th>                                                        
                                                        <th>Buttons</th>
                                                    </tr>
                                                </thead>       
                                            </table>
                                        </div>
                                    
                                    <!-- /tile body -->
                                </section>
                            </div>



                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ************ END : ITEM(s) LIST DETAIL(S)*********************** --%>
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
        <script src="<c:url value="/appjs/itemJS.js"/>" type="text/javascript"></script>
     
    

        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
    </body>
</html>
</c:if>
    <c:if test="${(userPermiLi.m3 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>