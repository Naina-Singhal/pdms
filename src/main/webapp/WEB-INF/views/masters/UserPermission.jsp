<%-- 
    Document   : GroupList
    Created on : Sep 30, 2016, 9:05:51 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Permissions</title>
        <jsp:include page="../commons/CommonCSSInclSecond.jsp"/>
        <style>
            .dataTables_paginate .disabled{
                cursor: not-allowed;

            }
            .dataTables_paginate  .previous{
                border: 1px solid gray !important;
                height: 40px !important;
                padding-top: 4px !important;
                padding-right: 5px !important;
            }
            .dataTables_paginate  .next{
                border: 1px solid gray !important;
                height: 40px !important;
                padding-top: 4px !important;
                padding-right: 5px !important;
            }
            .current{
                border: 1px solid gray !important;
                height: 40px !important;
                padding-top: 4px !important;
                padding-right: 5px !important;
                background-color: #3795b1 !important;
                width: 40px !important;
                color:white;
            }
            .paginate_button{
                border: 1px solid gray !important;
                height: 40px !important;
                padding-top: 4px !important;
                padding-right: 5px !important;
            }
            .dataTables_paginate {
                margin-top: -30px !important;
            }
            .table.table-custom > tbody > tr td{
                width: 15%;
            }
            .row {
                margin-right:0px !important;
                margin-left: 0px !important; 
            }
            .col-md-12{
                padding-left: 0px !important; 
            }
            .pageheader .page-bar {
                
                margin-right: 15px;
            }
            #display_check label{
                padding-left: 15px;
            }
            
            .panel-default .panel-heading {
                color: rgba(51, 51, 51, 0.72) !important;;
               
                border-color: #ddd !important;;
                font-size: 12.5px;
                font-weight: 600;
            }
            .panel{
                margin-bottom: 0px !important;
                border: 0px solid gray !important;
            }
            .firstHandle{
                border-top: 1px solid gray;
            }
            .groupHandle{
                border-top: 1px solid gray !important;
                border-bottom: 0px solid gray !important;
            }
            .titleHed2{
                background-color:  rgb(255, 255, 255);
            }
            .div-wid-7 label{
                padding-left: 6px;
                font-size: 12.5px;
                color: rgba(45, 43, 43, 0.63);
            }
            .div-wid-7 input{
                width: 15px;
                height: 15px;
            }
            .form-group1 {
                margin-bottom: 0px !important;
            }
            .bs-searchbox .form-control {
                height: 36px;
            }
            .col-lg-7{
                float: right !important;
                width: 150px !important;
                padding-top: 10px !important;
                padding-bottom: 10px !important;
            }
            .col-lg-7 button{
                width: 100px;
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
                        <h2> User Permission(s) <span> Add/ Update Group(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#"> User Permission(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <div>
                            <div class="display_msg_success_Ma">
                                <label id="successDupItem" class="label_success_msg"></label>
                            </div>
                            <div class="display_msg_error_Ma">
                                <label id="errorDupItem" class="label_error_msg"></label>
                            </div>
                        </div>
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
                        <%-- ******** START : ADD/EDIT DESIGNATION DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            
                                <!-- col -->
                                <div class="col-md-12">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm titleHed2">
                                            <h1 class="custom-font"><strong>User Permission</strong> Details</h1>
                                        </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group1 div-wid-7">
                                                    <label for="username">Select Employee: </label>
                                                    <select name="empSelect" id="empSelect" class="selectpicker form-control" 
                                                            data-live-search="true" data-dropup-auto="false">
                                                        <option value="null">select</option>
                                                        
                                                    </select>
                                                    <label id="errorEmployeeIdt" style="color: red"></label>
                                                </div>                                               
                                            </div>    
                                            </div>
                                        <spring:form name="addGroupForm" commandName="groupObj" id="permission_form">
                                            
                                                <div class="permis_collapse">
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading groupHandle" data-acc-link="group1">Group Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="group1">

                                                            <div class="row" id="display_group">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading firstHandle" data-acc-link="master1">Masters Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="master1">

                                                            <div class="row" id="display_master">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="account1">Account Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="account1">
                                                            <div class="row" id="display_account">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="receipt1">Receipt Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="receipt1">
                                                            <div class="row" id="display_receipt">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="purchase1">Purchase Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="purchase1">
                                                            <div class="row" id="display_purchase">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="indent1">Indent Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="indent1">
                                                            <div class="row" id="display_indent">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="tender1">Tender Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="tender1">
                                                            <div class="row" id="display_tender">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="vendor1">Vendor Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body" id="hi_sh_master"  data-acc-content="vendor1">
                                                            <div class="row" id="display_vendor">

                                                            </div>
                                                        </div>
                                                    </div>  
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="vendor3">Upload file Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body"   data-acc-content="vendor3">
                                                            <div class="row" id="display_upload">

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="panel panel-default">
                                                        <!-- tile header -->
                                                        <div class="panel-heading secondHandle" data-acc-link="despa">Despatch Permissions</div>
                                                        <div class="tile-header dvd dvd-btm panel-body"   data-acc-content="despa">
                                                            <div class="row" id="display_dispatch">

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                             
                                            <div class="row">
                                                <div class="col-lg-7">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="savePermissions">
                                                        <span>Save</span>
                                                    </button>
                                                </div>
                                                <div class="col-lg-7" id="updatediv" style="display: none">
                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="updateGroup" >
                                                        <span>Update</span>
                                                    </button>

                                                    <button class="btn btn-rounded btn-success btn-sm" type="button"
                                                            id="cancelGroup">
                                                        <span>Cancel</span>
                                                    </button>
                                                    
                                                </div>
                                            </div>
                                        </section>
                                        </div></div></div></div>
                                    </section>
                                </div>
                            </spring:form>
                        
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT DESIGNATION DETAIL(S)***************** --%>
                        <%-- **************************************************************** --%>
                      <!-- Revert/ Reject Comments Modal -->
                      
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title custom-font">Revert Comments!</h3>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <textarea name="comments" id="comments"
                                                  class="form-control input-sm" rows="" cols=""></textarea>
                                        <label id="errorcomments" style="color: red"></label>    
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success btn-ef btn-ef-3 btn-ef-3c"
                                        id="appRevertIndent" butval="${revStat}">
                                    <i class="fa fa-arrow-right"></i> Submit
                                </button>
                                <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal">
                                    <i class="fa fa-arrow-left"></i> Cancel
                                </button>    
                            </div>
                        </div>
                    </div>
                </div>  
                        
                     
                    
                    <!-- == End :Page Form ======== -->
             
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/master/UserPermiJS.js"/>" type="text/javascript"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js/bootstrap-select.js"/>"></script>  
        <script src="<c:url value="/appjs/master/jquery.accordion.js"/>"></script>  
        <script src="<c:url value="/appjs/loader/js/jquery.loading.block.js"/>"></script>  
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ --><script src="<c:url value="/appjs/master/jquery.accordion.js"/>"></script>  
        <script>
               $(function() {
			
			$('.permis_collapse').accordion({ multiOpen: false });

			
		});
        </script>
    </body>
</html>
