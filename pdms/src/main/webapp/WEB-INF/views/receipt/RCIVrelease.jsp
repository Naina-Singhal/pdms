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
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM : User(s) List</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
        <style>
            .div-wid-7{
                width: 31%;
                margin-left: 15px;
                float: left;
            }
            .row-ext-7{
                margin-top: 20px;
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
                        <h2>RCIV Release(s) <span> Add/ Update User(s)</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <a href="<c:url value="home.htm"/>"><i class="fa fa-home"></i> Home</a>
                                </li>
                                <li>
                                    <a href="#">RCIV Release(s)</a>
                                </li>
                            </ul>
                        </div>
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
                        <%-- ******** START : csrv Preparation  DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        <div class="row">
                            <spring:form name="addItemForm" commandName="itemObj" >
                                <!-- col -->
                                <div class="col-md-12" style="width: 96.5%;margin-left: 20px;">
                                    <!-- tile -->
                                    <section class="tile">
                                        <!-- tile header -->
                                        <div class="tile-header dvd dvd-btm">
                                            <input type="hidden" name="exisItemCode" id="eItemCode" value=""/>
                                            <input type="hidden" id="encFieldValue" name="encFieldValue" value=""/>
                                            <h1 class="custom-font"><strong>RCIV Release(s)</strong> Details</h1>
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
                                                <div class="form-group div-wid-7">
                                                    <label for="username">indentor:</label>
                                                    <input type="text" name="purchaseGfNo" id='purchaseGfNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">store requisition</label>
                                                    <input type="text" name="purchaseGfNo" id='purchaseGfNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>                                         
                                                
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Plant:</label>
                                                    <input type="text" name="purchaseGfNo" id='purchaseGfNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">:</label>
                                                    <input type="text" name="purchaseGfNo" id='purchaseGfNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="expDelDate" id="expDelDate" 
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
                                                    <label for="username">Issue Type:</label>
                                                    <input type="text" name="purchaseGfNo" id='purchaseGfNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue To </label>
                                                    <input type="text" name="poDate" id='poDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPurGfNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Disposal: </label>
                                                    <input type="text" name="poDate" id='poDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOLastNo" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Issue From Other Section Name: </label>
                                                    <input type="text" name="poDate" id='poDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">: </label>
                                                    <input type="text" name="poDate" id='poDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Requisition Number: </label>
                                                    <input type="text" name="poDate" id='poDate' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPoDate" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">                                                
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="expDelDate" id="expDelDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Control Number: </label>
                                                    <input type="text" name="indentRefNo" id='indentRefNo' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentRefNo" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="expDelDate" id="expDelDate" 
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
                                                    <label for="username">Indenter : </label>
                                                    <input type="text" name="indentor" id='indentor' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">: </label>
                                                    <input type="text" name="indentor" id='indentor' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorIndentor" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Section: </label>
                                                    <input type="text" name="section" id='section' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSection" style="color: red"></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Job Allocation: </label>
                                                    <input type="text" name="supplier" id='supplier' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Delivery At: </label>
                                                    <input type="text" name="supplier" id='supplier' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Item Covered: </label>
                                                    <input type="text" name="remarks" id='remarks' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorRemarks" style="color: red"></label>
                                                </div>
                                            </div>
                                            
                                                                            
                                            
                                        </div>
                                    </section>
                                </div>
                            </spring:form>
                        </div>
                        <%-- **************************************************************** --%>
                        <%-- ******** END : csrv Preparation  DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                        
                        <%-- **************************************************************** --%>
                        <%-- ************ START : ITEM REQUIRED LIST DETAIL(S)************************ --%>
                        <%-- **************************************************************** --%>
                            <div class="row">
                                <!-- col -->
                                <div class="col-md-12" style="margin-left: 20px;width: 96.5%;">
                                    <!-- tile -->
                                    <section class="tile">
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
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="basic-usage" style="width:100% !important">
                                                    <thead>
                                                        <tr>
                                                            <th>Store</th>
                                                            <th>Group</th>
                                                            <th>Card No</th>
                                                            <th>Code</th>
                                                            <th>Description</th>
                                                            <th>Required Quantity</th>
                                                            <th>Unit</th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                      
                                                                    <tr class="gradeX">
                                                                        <td>
                                                                            

                                                                        </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>
                                                                <tr class="gradeX">
                                                                        <td>
                                                                            

                                                                        </td>
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                       
                                                                        <td></td>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>
                                                      
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="row  row-ext-7">
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Authorised By: </label>
                                                    <input type="text" name="supplier" id='supplier' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">: </label>
                                                    <input type="text" name="supplier" id='supplier' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorSupplier" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="expDelDate" id="expDelDate" 
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
                                                    <input type="text" name="poValue" id='poValue' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Released Y/N: </label>
                                                    <select name="" id="" class="form-control input-sm">
                                                        <option value="">Yes</option>
                                                        <option value="">No</option>
                                                        
                                                    </select>
                                                    <label id="errorIndentDesNoDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">By </label>
                                                    <input type="text" name="poValue" id='poValue' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                                
                                            </div>
                                            <div class="row">  
                                                <div class="form-group div-wid-7">
                                                    <label for="username">: </label>
                                                    <input type="text" name="poValPaid" id='poValPaid' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOValPaid" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Date: </label>
                                                    <div class='input-group datepicker' data-format="L">
                                                        <input type='text' class="form-control input-sm"
                                                               name="expDelDate" id="expDelDate" 
                                                               value=""/>
                                                        <span class="input-group-addon">
                                                            <span class="fa fa-calendar"></span>
                                                        </span>
                                                    </div>
                                                    <label id="errorExpDelDate" style="color: red"></label>
                                                </div>
                                                <div class="form-group div-wid-7">
                                                    <label for="username">Remarks: </label>
                                                    <input type="text" name="poValue" id='poValue' 
                                                           class="form-control input-sm"
                                                           maxlength="40"/>
                                                    <label id="errorPOoValue" style="color: red"></label>
                                                </div>
                                            </div>
                                           
                                        </div>
                                        <!-- /tile body -->
                                    </section>
                                </div>
                            </div>
                        <%-- **************************************************************** --%>
                        <%-- ****************** END : ITEM REQUIRED LIST DETAIL(S)******************** --%>
                        <%-- **************************************************************** --%>
                        
                        
                        <div class="row">
                            <div class="col-md-12" style="width: 98.2%;">
                                <span class="tools pull-right">
                                    <c:choose>
                                        <c:when test="${indentStat != null}">
                                            <input type="button" value="Update" class="btn btn-info" id="updateIndent"/> 
                                        </c:when>
                                        <c:otherwise>
                                            <input type="button" value="Save" class="btn btn-info" id="saveIndent"/> 
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <input type="button" value="Cancel" class="btn btn-info " id="cancelEdit" />
                                </span>
                            </div>
                        </div>    
                    </div>
           </section>                    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        <jsp:include page="../commons/CommonJSIncl.jsp"/>
        <script src="<c:url value="/appjs/PODDetEnJS.js"/>" type="text/javascript"></script>
        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>

        </script>
    </body>
</html>
