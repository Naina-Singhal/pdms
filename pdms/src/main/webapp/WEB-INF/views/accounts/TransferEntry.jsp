<%-- 
    Document   : UserList
    Created on : Oct 1, 2016, 7:44:53 AM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="/WEB-INF/tlds/spring-form.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.pdms.constants.ApplicationConstants" var="constants" />
<c:if test="${userPermiLi != null}">
    <c:forEach var="userPermiLi" items="${userPermiLi}">
                <c:if test="${(userPermiLi.u1 == 1)}">
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : Upload Item Excel File</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            fieldset.scheduler-border {
                border: 1px groove rgba(221, 221, 221, 0.45) !important;
                 padding: 0 1.4em 1.4em 1.4em !important;
                margin: 0 0 1.5em 0 !important;
                -webkit-box-shadow:  0px 0px 0px 0px #000;
                box-shadow:  0px 0px 0px 0px #000;
            }

            legend.scheduler-border {
                font-size: 1.2em !important;
                font-weight: bold !important;
                text-align: left !important;
                width:inherit; /* Or auto */
                padding:0 10px; /* To give a bit of padding on the left and right */
                border-bottom:none;
                opacity: 0.7;
            }
            .div-search-7{
                float: left;
                margin-top: 24px;
            }
            #show_file_msg, #show_file_msg1{
                margin-top: 6px;
            }
             #show_file_msg_gr, #show_file_msg_gr1, #show_file_msg_sc, #show_file_msg_sc1{
                margin-top: 6px;
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
                        <h2>Upload Item Excel File(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">Upload Item Excel File(s)</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                
                 <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">
                        <div class="display_msg_success">
                            <label id="successDupItem" class="label_success_msg"></label>
                        </div>
                        <div class="display_msg_error">
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
                        <%-- ******** START : ADD/EDIT USER DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <form:form  method="post" enctype="multipart/form-data" id="form_excel">
                                <!-- col -->
                                <div class="col-md-12" >
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            
                                            <h1 class="custom-font"><strong>Upload Item Excel File</strong> Details</h1>
                                         </div>
                                        <!-- tile header -->
                                        <!-- tile body -->
                                        <div class="tile-body">                                            
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Upload Item Excel File(2003 xls format): </label>
                                                    <input name="excelfile" type="file"  class="form-control input-sm"
                                                           id="uploadF" maxlength="40" required="required"/>
                                                  <div id="show_file_msg_gr"></div>
                                                    <div id="show_file_msg"></div>  
                                                    <div id="show_file_msg_sc"></div>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="submit"
                                                            id="uploadLoading">
                                                            <span>Save</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                        </div>
                                            </form:form>
                                            <form:form  method="post" enctype="multipart/form-data" id="form_xlsx">
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Upload Item Excel File (2007 xlsx format): </label>
                                                    <input name="xlsxfile" type="file"  class="form-control input-sm"
                                                           id="uploadXlsx" maxlength="40" />
                                                    <div id="show_file_msg_gr1"></div>
                                                    <div id="show_file_msg1"></div> 
                                                    <div id="show_file_msg_sc1"></div>
                                                </div>
                                                <div class="form-group div-search-7">
                                                    <div class="col-lg-7">
                                                        <button class="btn btn-rounded btn-success btn-sm" type="submit"
                                                                id="upload_save_xlsx">
                                                            <span>Save</span>
                                                        </button>
                                                    </div>                                                
                                                </div>
                                        </div>
                                             </form:form>
                                        </div>
                                    </section>
                                </div>
                            
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ********** END : ADD/EDIT ITEM DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                         <div class="row">
                            <div class="col-md-12" >
                                <span class="tools pull-right">
                                   
                                </span>
                            </div>
                        </div> 
                        
                    </div>
        </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/account/TransferEnJS.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/appjs/loader/js/jquery.loading.block.js"/>"></script>  
        <script src="<c:url value="//cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.js"/>"></script>       
        <script src="<c:url value="/assets/js/vendor/boostrap-select/js//bootstrap-select.js"/>"></script>
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            $(document).ready(function () {
                    
                    
            
      
            });  
        </script>
       
    </body>
    
</html>
 </c:if>
    <c:if test="${(userPermiLi.u1 == 0)}"> 
        <% response.sendRedirect("./login"); %>
    </c:if>
</c:forEach>    
</c:if>