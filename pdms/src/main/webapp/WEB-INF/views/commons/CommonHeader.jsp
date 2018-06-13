<%-- 
    Document   : CommonHeader
    Created on : Sep 21, 2016, 3:00:14 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un"%>
<un:useConstants className="com.pdms.constants.ApplicationConstants" var="constants" />
<script>
    function preventBack() {
        window.history.forward();
    }
    setTimeout("preventBack()", 0);
</script>
<style>
   
</style>
<c:set var="userName" value="User"/>
<c:set var="roleID" value="0"/>
<c:set var="userType" value=""/>
<c:if test="${sessionScope.USER_SESSION!=null}">
    <c:set var="userName" value="${sessionScope.USER_SESSION.employeeProfileDTO.firstName} ${sessionScope.USER_SESSION.employeeProfileDTO.lastName}"/>
    <c:set var="icNumber" value="${sessionScope.USER_SESSION.employeeProfileDTO.icNumber}"/>
    <c:set var="designation" value=""/>
    <c:set var="userType" value="${sessionScope.USER_SESSION.employeeProfileDTO.employeeTypeDTO.employeeTypeName}"/>

    <c:set var="isApprover" value="0"/>
    <c:set var="isSigniner" value="0"/>

    <c:if test="${userType == constants.EMPLOYEE_TYPE_SUPERADMIN}">
        <c:set var="designation" value="Super Administrator"/>
    </c:if>
    <c:if test="${userType == constants.EMPLOYEE_TYPE_SECADMIN}">
        <c:set var="designation" value="Plant Admin"/>
    </c:if>
    <c:if test="${userType == constants.EMPLOYEE_TYPE_PUADMIN}">
        <c:set var="designation" value="Purchase Unit Admin"/>
    </c:if>

    <c:if test="${userType == constants.EMPLOYEE_TYPE_ACCOUNT_ADMIN}">
        <c:set var="designation" value="Accounts Admin"/>
    </c:if>

    <c:if test="${userType == constants.EMPLOYEE_TYPE_STORES_ADMIN}">
        <c:set var="designation" value="Stores Admin"/>
    </c:if>
    <c:if test="${(userType == constants.EMPLOYEE_TYPE_SECUSER) ||
                  (userType == constants.EMPLOYEE_TYPE_ACCOUNT_USER) ||
                  (userType == constants.EMPLOYEE_TYPE_STORES_USER)}">
        <c:set var="designation" value="${sessionScope.USER_SESSION.designationDTO.designationName}"/>
    </c:if>
    <c:if test="${userType == constants.EMPLOYEE_TYPE_PUUSER}">
        <c:set var="designation" value="${sessionScope.USER_SESSION.designationDTO.designationName}"/>
    </c:if>
    <c:if test="${sessionScope.USER_SESSION.designationDTO.isApprovingAuthority >=1}">
        <c:set var="isApprover" value="1"/>
    </c:if>
    <c:if test="${sessionScope.USER_SESSION.designationDTO.isSigningAuthority >=1}">
        <c:set var="isSigniner" value="1"/>
    </c:if>

</c:if>
<!-- =================================================
    ================= START : HEADER Content =========
    ================================================ -->
<section id="header" class="scheme-greensea">
    <header class="clearfix">
        <!-- Branding -->
        <div class="branding scheme-greensea">
            <a class="brand" href="<c:url value="home.htm"/>">
                <span><strong>RPU</strong>M</span>
            </a>
            <a role="button" tabindex="0" class="offcanvas-toggle visible-xs-inline"><i class="fa fa-bars"></i></a>
        </div>
        <!-- Branding end -->

        <!-- Left-side navigation -->
        <ul class="nav-left pull-left list-unstyled list-inline">
            <li class="sidebar-collapse divided-right">
                <a role="button" tabindex="0" class="collapse-sidebar">
                    <i class="fa fa-outdent"></i>
                </a>
            </li>
        </ul>
        <!-- Left-side navigation end -->

        <!-- Right-side navigation -->
        <ul class="nav-right pull-right list-inline">
            <li class="dropdown nav-profile">
                <a href class="dropdown-toggle" data-toggle="dropdown">
                    <span> ${designation} - (IC#:${icNumber})</span>
                </a>
            </li>
            <li class="dropdown nav-profile">
                <a href class="dropdown-toggle" data-toggle="dropdown">
                    <span>Welcome : ${userName}
                        <i class="fa fa-angle-down"></i>
                    </span>
                </a>
                <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                    <li>
                        <a role="button" tabindex="0" href="<c:url value="/usrprofile.htm"/>">
                            <i class="fa fa-user"></i>Profile
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a role="button" tabindex="0" href="<c:url value="logout.htm"/>">
                            <i class="fa fa-sign-out"></i>Logout
                        </a>
                    </li>

                </ul>
            </li>
        </ul>
        <!-- Right-side navigation end -->
    </header>

</section>
<!-- =================================================
================= END : HEADER Content =========
================================================ -->
<!-- ====================================================
================= START : CONTROLS Content ===============
========================================================== -->
<div id="controls">        
    <!-- ====================================================
        ================= START : SIDEBAR Content ===========
        ================================================= -->
    <aside id="sidebar">
        <div id="sidebar-wrap">
            <div class="panel-group slim-scroll" role="tablist">
                <div class="panel panel-default">
                    <div class="panel-heading panal-han2" role="tab">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" href="#sidebarNav">
                                Navigation <i class="fa fa-angle-up"></i>
                            </a>
                        </h4>
                    </div>
                    <div id="sidebarNav" class="panel-collapse collapse in" role="tabpanel">
                        <div class="panel-body">
                            <!-- ===== START : NAVIGATION Content ===================== -->
                            <%-- **************************************************************** --%>
                            <%-- ******** START : SUPER ADMIN LEFT MENU DETAIL(S)**************** --%>
                            <%-- **************************************************************** --%>

                            
                                
                                       
                             
                            <%-- **************************************************************** --%>
                            <%-- ******** END : SUPER ADMIN LEFT MENU DETAIL(S)****************** --%>
                            <%-- **************************************************************** --%>

                            <%-- **************************************************************** --%>
                            <%-- ******** START : SEC ADMIN LEFT MENU DETAIL(S)****************** --%>
                            <%-- **************************************************************** --%>
                            <c:if test="${userType == constants.EMPLOYEE_TYPE_SECADMIN}">
                                
                            </c:if>
                            <%-- **************************************************************** --%>
                            <%-- ********** END : SEC ADMIN LEFT MENU DETAIL(S)****************** --%>
                            <%-- **************************************************************** --%>


                            <%-- **************************************************************** --%>
                            <%-- ********* START : GROUP LEFT MENU DETAIL(S)****************** --%>
                            <%-- **************************************************************** --%>
                            <ul id="navigation">
                                    <c:if test="${userPermiLi != null}">
                                        <c:forEach var="userPermiLi" items="${userPermiLi}">
                                            
                                                <li>
                                                    <a class="active" href="<c:url value="home.htm"/>">
                                                        <i class="fa fa-dashboard"></i>
                                                        <span>Dashboard</span>
                                                    </a>
                                                </li>
                                            <c:if test="${userType == constants.EMPLOYEE_TYPE_SUPERADMIN}">
                                                <li>
                                                    <a href="javascript:;" id="indlk">
                                                        <i class="fa fa-user"></i>
                                                        <span>Permissions</span>
                                                    </a>
                                                    <ul id="userpermenu">
                                                        <li id="useperli">
                                                            <a href="<c:url value="/userPermissionEn.htm"/>">
                                                                <i class="fa fa-caret-right"></i>User Permissions
                                                            </a>
                                                        </li>

                                                    </ul>
                                                </li>

                                            </c:if>
                                            <c:if test="${(userPermiLi.g1 == 1)}">
                                                <li id="masterli">
                                                    <a role="button" tabindex="0" href="javascript:;" id="master">
                                                        <i class="fa fa-table"></i> <span>Master(s)</span>
                                                    </a>


                                                    <ul id="masterul">
                                                        <c:if test="${(userPermiLi.m1 == 1)}">
                                                            <li id="cat">
                                                                <a href="<c:url value="/categoryli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Category(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m2 == 1)}">
                                                            <li id="unit">
                                                                <a href="<c:url value="/unitli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i> Unit(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m3 == 1)}">
                                                            <li id="item">
                                                                <a href="<c:url value="/itemsli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Item(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m4 == 1)}">
                                                            <li id="grp">
                                                                <a href="<c:url value="/groupli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Group(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m5 == 1)}">
                                                            <li id="hoa">
                                                                <a href="<c:url value="/hoali.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Head Of Accounts(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m6 == 1)}">
                                                            <li id="mop">
                                                                <a href="<c:url value="/mopli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Mode Of Purchase(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m7 == 1)}">
                                                            <li id="pod">
                                                                <a href="<c:url value="/podli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Place Of Delivery(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m8 == 1)}">
                                                            <li id="usr">
                                                                <a href="<c:url value="/userli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> User(s)
                                                                </a>
                                                            </li> 
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m11 == 1)}">
                                                            <li  id="sec">
                                                                <a href="<c:url value="/sectionli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i> Section(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m10 == 1)}">
                                                            <li id="desig">
                                                                <a href="<c:url value="/designationli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i> Designation(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m12 == 1)}">
                                                            <li id="grp">
                                                                <a href="<c:url value="/empgrpli.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i>Map Employee to Group(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m13 == 1)}">
                                                            <li id="paym">
                                                                <a href="<c:url value="/paymentMaster.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Payment(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m14 == 1)}">
                                                            <li id="destion">
                                                                <a href="<c:url value="/descriptionMaster.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Note Description(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m15 == 1)}">
                                                            <li id="usribcno">
                                                                <a href="<c:url value="/ibcNumberMa.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> IBC Number(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m16 == 1)}">
                                                            <li id="lrdetaen">
                                                                <a href="<c:url value="/lrDetailsEn.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> LR Details Entry For Accounts(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m32 == 1)}">
                                                            <li id="lrdeenstors">
                                                                <a href="<c:url value="/lrDeEnForStores.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> LR Details Entry For Stores(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m17 == 1)}">
                                                            <li id="tendocen">
                                                                <a href="<c:url value="/tenderDocEn.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Tender Document Entry(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m18 == 1)}">
                                                            <li id="nametrandggf">
                                                                <a href="<c:url value="/nameOfTransporter.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Name of The Transporter(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m19 == 1)}">
                                                            <li id="crnctype">
                                                                <a href="<c:url value="/currencyType.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Currency(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m20 == 1)}">
                                                            <li id="signtati">
                                                                <a href="<c:url value="/signatory.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Signatory(s)
                                                                </a>
                                                            </li>
                                                        </c:if>  
                                                        <c:if test="${(userPermiLi.m21 == 1)}">
                                                            <li id="rtgsgdre">
                                                                <a href="<c:url value="/rtgsPage.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> RTGS(s)
                                                                </a>
                                                            </li>
                                                        </c:if> 
                                                        <c:if test="${(userPermiLi.m22 == 1)}">
                                                            <li id="hoaccss">
                                                                <a href="<c:url value="/headOfAcCodes.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Head Of Account(short codes)(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m23 == 1)}">
                                                            <li id="bannnkkk">
                                                                <a href="<c:url value="/bankMaster.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Bank(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m24 == 1)}">
                                                            <li id="enquirydjhfhf">
                                                                <a href="<c:url value="/enquiryForm.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Enquiry(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m25 == 1)}">
                                                            <li id="Receiverscrgbd">
                                                                <a href="<c:url value="/receiverScreen.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Receiver(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m26 == 1)}">
                                                            <li id="monthrepot">
                                                                <a href="<c:url value="/monthlyReportSc.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Monthly Report Screen(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m27 == 1)}">
                                                            <li id="taxdetils">
                                                                <a href="<c:url value="/taxDetails.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Tax Details(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m28 == 1)}">
                                                            <li id="speciled">
                                                                <a href="<c:url value="/specialED.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> General Terms Or PO Terms(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m30 == 1)}">
                                                            <li id="typedispafgj">
                                                                <a href="<c:url value="/typeOfDispatch.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Type Of Dispatch(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.m31 == 1)}">
                                                            <li id="typevielks">
                                                                <a href="<c:url value="/typeViewPage.htm"/>" >
                                                                    <i class="fa fa-caret-right"></i> Type(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                    </ul>


                                                </li></c:if>
                                                <c:if test="${(userPermiLi.g2 == 1)}">

                                                    <li>
                                                        <a href="javascript:;" id="indlk">
                                                            <i class="fa fa-book"></i>
                                                            <span>Indent Menu</span>
                                                        </a>
                                                        <ul id="indmenu">
                                                            <li id="vstk">
                                                                <a href="<c:url value="/itemstockli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>View Stock
                                                                </a>
                                                            </li>
                                                            <li id="indli">
                                                                <a href="<c:url value="/admappindli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Approve Indent(s) List
                                                                </a>
                                                            </li>
                                                            <li id="nwind">
                                                                <a href="<c:url value="/indenthome.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Create Indent
                                                                </a>
                                                            </li>
                                                            <li id="indli">
                                                                <a href="<c:url value="/rpuadmapprindli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Approve Indent(s) List
                                                                </a>
                                                            </li>
                                                            <li id="indli">
                                                                <a href="<c:url value="/rpuadmsignindli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Signing Indent(s) List
                                                                </a>
                                                            </li>                                                            
                                                            <li id="indli">
                                                                <a href="<c:url value="/appuserindli.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Indent(s) List
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </li>


                                                </c:if>
                                                <c:if test="${(userPermiLi.g3 == 1)}">
                                                    <li>
                                                        <a href="javascript:;" id="vlnk">
                                                            <i class="fa fa-book"></i>
                                                            <span>Vendor Menu</span>
                                                        </a>
                                                        <ul id="vmenu">
                                                            <c:if test="${(userPermiLi.v1 == 1)}">
                                                                <li id="vlist">
                                                                    <a href="<c:url value="/vendorli.htm"/>">
                                                                        <i class="fa fa-caret-right"></i>Vendor(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.v2 == 1)}">
                                                                <li id="mindtoven">
                                                                    <a href="<c:url value="/mapindentv.htm"/>">
                                                                        <i class="fa fa-caret-right"></i>Sourcing Indent
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.v3 == 1)}">
                                                                <li id="srcvupd">
                                                                    <a href="<c:url value="/srcupdateview.htm"/>">
                                                                        <i class="fa fa-caret-right"></i>Source View/Update
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                            <c:if test="${(userPermiLi.g4 == 1)}">
                                                <li>
                                                    <a href="<c:url value="csthome.htm"/>" id="cstmt">
                                                        <i class="fa fa-book"></i>
                                                        <span>Comparative Statement</span>
                                                    </a>
                                                </li>
                                            </c:if>
                                            <c:if test="${(userPermiLi.g5 == 1)}">
                                                <li>
                                                    <a href="javascript:;" id="ptlnk">
                                                        <i class="fa fa-book"></i>
                                                        <span>Public Tender Menu</span>
                                                    </a>
                                                    <ul id="ptmenu">
                                                        <c:if test="${(userPermiLi.t1 == 1)}">
                                                        <li id="ptilist">
                                                            <a href="<c:url value="/pudtenderindli.htm"/>">
                                                                <i class="fa fa-caret-right"></i>Tender Indent(s)
                                                            </a>
                                                        </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.t2 == 1)}">
                                                        <li id="ptlst">
                                                            <a href="<c:url value="/pudtenderli.htm"/>">
                                                                <i class="fa fa-caret-right"></i>Tender(s) List
                                                            </a>
                                                        </li>
                                                        </c:if>                                                       
                                                    </ul>
                                                </li>  
                                            </c:if>


                                            <c:if test="${(userPermiLi.g6 == 1)}">
                                                <li>
                                                    <a href="javascript:;" id="ptlnk">
                                                        <i class="fa fa-book"></i>
                                                        <span>Purchase Order Menu</span>
                                                    </a>
                                                    <ul id="purcmenu"> 
                                                        <c:if test="${(userPermiLi.b1 == 1)}">
                                                            <li id="poepoe">
                                                                <a href="<c:url value="/POEntryView.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Purchase Order Entry
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.b3 == 1)}">
                                                            <li id="podtenysc">
                                                                <a href="<c:url value="/PODetailsEntry.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Purchase Order Details Entry
                                                                </a>
                                                            </li> 
                                                        </c:if>                                                        
                                                        <c:if test="${(userPermiLi.b4 == 1)}">
                                                            <li id="amdnescrneen">
                                                                <a href="<c:url value="/amendmentScreen.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Amendment Screen(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.b5 == 1)}">
                                                            <li id="bankgrantscr">
                                                                <a href="<c:url value="/bankGarantee.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Bank Guarantee(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.b6 == 1)}">
                                                            <li id="statuscservsn">
                                                                <a href="<c:url value="/statusScreen.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>File Status(s)
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.b7 == 1)}">
                                                            <li id="vendprmds">
                                                                <a href="<c:url value="/vendorPerformance.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Vendor Performance(s)
                                                                </a>
                                                            </li>
                                                        </c:if>   
                                                        <c:if test="${(userPermiLi.b7 == 1)}">
                                                            <li id="poremms">
                                                                <a href="<c:url value="/poRemainder.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>PO Remainder(s)
                                                                </a>
                                                            </li>
                                                        </c:if> 
                                                        <c:if test="${(userPermiLi.b7 == 1)}">
                                                            <li id="bgrmaindfg">
                                                                <a href="<c:url value="/bgRemainder.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>BG Remainder(s)
                                                                </a>
                                                            </li>
                                                        </c:if> 
                                                    </ul>
                                                </li>
                                            </c:if>
                                                <c:if test="${(userPermiLi.g7 == 1)}">       
                                                    <li id="masterli">
                                                        <a role="button" tabindex="0" href="javascript:;" id="master">
                                                            <i class="fa fa-book"></i> <span>Store Menu(s)</span>
                                                        </a>
                                                        <ul id="receiptul">                                                             
                                                            <c:if test="${(userPermiLi.c1 == 1)}"> 
                                                                <li id="perecord">
                                                                    <a href="<c:url value="/purOrdLi.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i> PO Release Details(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>                                                            
                                                            <c:if test="${(userPermiLi.c3 == 1)}"> 
                                                                <li id="mtlrecord">
                                                                    <a href="<c:url value="/materialReceipt.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i> Material Receipt(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>                                                            
                                                            <c:if test="${(userPermiLi.c6 == 1)}"> 
                                                                <li id="insperecord">
                                                                    <a href="<c:url value="/inspectionClearance.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i> Inspection Clearance(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.c7 == 1)}"> 
                                                                <li id="csrvPR">
                                                                    <a href="<c:url value="/csrvPreparation.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i> CSRV Preparation(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.c8 == 1)}"> 
                                                                <li id="mtRequisition">
                                                                    <a href="<c:url value="/materialRequisition.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i> Material Requisition(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.c9 == 1)}"> 
                                                                <li id="rcivAuthori">
                                                                    <a href="<c:url value="/rcivAuthorisation.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i>Rciv Authorisation(s)
                                                                    </a>
                                                                </li>  
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.c10 == 1)}"> 
                                                                <li id="searchMRec">
                                                                    <a href="<c:url value="/searchMasRec.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i>Search Master Records(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.c11 == 1)}"> 
                                                                <li id="brmstdt">
                                                                    <a href="<c:url value="/browseMasterData.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i>Browse Master Data(s)
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${(userPermiLi.c12 == 1)}"> 
                                                                <li id="isvchet">
                                                                    <a href="<c:url value="/issueVocherEntry.htm"/>" >
                                                                        <i class="fa fa-caret-right"></i>Issue Voucher Entry(s)
                                                                    </a>
                                                                </li> 
                                                            </c:if>

                                                        </ul>

                                                    </li>
                                                </c:if>
                                                    <c:if test="${(userPermiLi.g8 == 1)}"> 
                                                        <li id="masterli">
                                                            <a href="javascript:;" id="indlk">
                                                                <i class="fa fa-book"></i>
                                                                <span>Account Menu</span>
                                                            </a>
                                                            <ul id="accountul">
                                                                <c:if test="${(userPermiLi.d13 == 1)}"> 
                                                                    <li id="billenscrn">
                                                                        <a href="<c:url value="/billEntryScreen.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Bill Entry Screen (s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d1 == 1)}">  
                                                                    <li id="voudare">
                                                                        <a href="<c:url value="/voucherDataEnView.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Voucher Data Entry(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d15 == 1)}">  
                                                                    <li id="suvonoen">
                                                                        <a href="<c:url value="/supplierVouNoEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Voucher Number(s)
                                                                        </a>
                                                                    </li>                                                                       
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d3 == 1)}"> 
                                                                    <li id="voucupd">
                                                                        <a href="<c:url value="/voucherUpdate.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Voucher Update(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d4 == 1)}"> 
                                                                    <li id="nilvoucupd">
                                                                        <a href="<c:url value="/nilVoucherEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Nil Voucher Entry(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d5 == 1)}"> 
                                                                    <li id="advvoucupd">
                                                                        <a href="<c:url value="/advacePayDaUp.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Advance Payment Data Update(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d6 == 1)}"> 
                                                                    <li id="chevoucupd">
                                                                        <a href="<c:url value="/chemicalDaEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Chemicals Data Entry (s)
                                                                        </a>
                                                                    </li> 
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d7 == 1)}"> 
                                                                    <li id="chepervoucupd">
                                                                        <a href="<c:url value="/chemicalPerDaEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Chemicals Data Entry (%) (s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d8 == 1)}"> 
                                                                    <li id="preaudreen">
                                                                        <a href="<c:url value="/preAuditFiReEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Pre Audit File Reciept Entry (s)
                                                                        </a>
                                                                    </li>   
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d9 == 1)}"> 
                                                                    <li id="preUpaudreen">
                                                                        <a href="<c:url value="/preAuditSendUpd.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Pre Audit File Send And update (s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d10 == 1)}"> 
                                                                    <li id="oilsdaentry">
                                                                        <a href="<c:url value="/oilsDataEntry.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Oils Data Entry (s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d11 == 1)}"> 
                                                                    <li id="cheDDdaentry">
                                                                        <a href="<c:url value="/chequeAndDdEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Cheque And DD Entry (s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d12 == 1)}"> 
                                                                    <li id="challaenfrcs">
                                                                        <a href="<c:url value="/challanEnForCash.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Challan Entry For Cash(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>                                                                
                                                                <c:if test="${(userPermiLi.d23 == 1)}"> 
                                                                    <li id="ddnmnbenny">
                                                                        <a href="<c:url value="/ddNumberEn.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> DD Number Entry(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>                                                                
                                                                <c:if test="${(userPermiLi.d25 == 1)}"> 
                                                                    <li id="paymmnvtdetla">
                                                                        <a href="<c:url value="/PaymentDetails.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Payment Status(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                    <c:if test="${(userPermiLi.d22 == 1)}"> 
                                                                    <li id="trnsfchjed">
                                                                        <a href="<c:url value="/transferEnScreen.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Transfer Entry Screen(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${(userPermiLi.d24 == 1)}"> 
                                                                    <li id="prisiskfjfgh">
                                                                        <a href="<c:url value="/prisReportScreen.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> PRIS Report Screen(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                 <c:if test="${(userPermiLi.d26 == 1)}"> 
                                                                    <li id="billrudscrn">
                                                                        <a href="<c:url value="/billRegisterUpSc.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Bill Register Update Screen(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                 <c:if test="${(userPermiLi.d27 == 1)}">
                                                                    <li id="challachec">
                                                                        <a href="<c:url value="/challanChequeList.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Challan And Cheque List Screen(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                                 <c:if test="${(userPermiLi.d1 == 1)}">
                                                                    <li id="accopdfs">
                                                                        <a href="<c:url value="/acPdfButtonList.htm"/>" >
                                                                            <i class="fa fa-caret-right"></i> Pdf Button List(s)
                                                                        </a>
                                                                    </li>
                                                                </c:if>
                                                            </ul>
                                                        </li>   
                                                    </c:if>
                                       <c:if test="${(userPermiLi.g9 == 1)}">
                                                <li>
                                                    <a href="javascript:;" id="ptlnk">
                                                        <i class="fa fa-book"></i>
                                                        <span>Upload file Menu</span>
                                                    </a>
                                                    <ul id="uplameexul"> 
                                                        <c:if test="${(userPermiLi.u1 == 1)}">
                                                            <li id="itemexac">
                                                                <a href="<c:url value="/transferEntry.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Item Excel File Upload
                                                                </a>
                                                            </li> 
                                                        </c:if>
                                                    </ul>
                                                </li>
                                        </c:if>
                                        <c:if test="${(userPermiLi.g10 == 1)}">
                                                <li>
                                                    <a href="javascript:;" id="ptlnk">
                                                        <i class="fa fa-book"></i>
                                                        <span>Despatch Menu</span>
                                                    </a>
                                                    <ul id="despatchUl"> 
                                                        <c:if test="${(userPermiLi.p1 == 1)}">
                                                            <li id="despatentr">
                                                                <a href="<c:url value="/dispatchEntry.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>File Movement
                                                                </a>
                                                            </li> 
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.p2 == 1)}">
                                                            <li id="desinwarrd">
                                                                <a href="<c:url value="/dispatchInwardEn.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Despatch Inward Entry
                                                                </a>
                                                            </li> 
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.p3 == 1)}">
                                                            <li id="despapostt">
                                                                <a href="<c:url value="/postEntry.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Post Entry
                                                                </a>
                                                            </li> 
                                                        </c:if>
                                                        <c:if test="${(userPermiLi.p4 == 1)}">
                                                            <li id="postagupdgr">
                                                                <a href="<c:url value="/postageUpdate.htm"/>">
                                                                    <i class="fa fa-caret-right"></i>Postage Update
                                                                </a>
                                                            </li> 
                                                        </c:if>
                                                    </ul>
                                                </li>
                                        </c:if>
                             </c:forEach>
                                    </c:if>
                            </ul>                     
                            <%-- **************************************************************** --%>
                            <%-- *********** END : GROUP MENU DETAIL(S)****************** --%>
                            <%-- **************************************************************** --%>

                            <%-- **************************************************************** --%>
                            <%-- ********* START : STORES ADMIN LEFT MENU DETAIL(S)****** --%>
                            <%-- **************************************************************** --%>

                            <c:if test="${(userType == constants.EMPLOYEE_TYPE_STORES_ADMIN)}">
                                  
                            </c:if>
                            <%-- **************************************************************** --%>
                            <%-- *********** END : STORES ADMIN LEFT MENU DETAIL(S)****** --%>
                            <%-- **************************************************************** --%>


                             <%-- **************************************************************** --%>
                            <%-- ********* START : ACCOUNT ADMIN LEFT MENU DETAIL(S)****** --%>
                            <%-- **************************************************************** --%>

                            <c:if test="${(userType == constants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)}">
                                  
                            </c:if>
                            <%-- **************************************************************** --%>
                            <%-- *********** END : ACCOUNT ADMIN LEFT MENU DETAIL(S)****** --%>
                            <%-- **************************************************************** --%>

                            
   <%-- **************************************************************** ******************************************************************************* --%>
                            

                            <!-- ===== END : NAVIGATION Content ===================== -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </aside>
    <!-- ====================================================
        ================= START : SIDEBAR Content ===========
        ================================================= -->
</div>
<!-- ==================================================
================= END : CONTROLS Content ==============
=================================================== -->

