<%-- 
    Document   : SiteHome
    Created on : Oct 4, 2016, 9:48:05 PM
    Author     : hpasupuleti
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> 
<html class="no-js" lang=""> <!--<![endif]-->
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>RPUM - Site Home Page</title>
        <link rel="icon" type="image/ico" href="assets/images/favicon.ico" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- ============================================
        ================= Stylesheets ===================
        ============================================= -->
        <!-- vendor css files -->
        <link rel="stylesheet" href="<c:url value="/assets/css/vendor/bootstrap.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/assets/css/vendor/animate.css"/>">
        <link rel="stylesheet" href="<c:url value="/assets/css/vendor/font-awesome.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/assets/js/vendor/animsition/css/animsition.min.css"/>">

        <!-- project main css files -->
        <link rel="stylesheet" href="<c:url value="/assets/css/main.css"/>">
        <!--/ stylesheets -->
        <!-- ==========================================
        ================= Modernizr ===================
        =========================================== -->
        <script src="<c:url value="/assets/js/vendor/modernizr/modernizr-2.8.3-respond-1.4.2.min.js"/>"></script>
        <!--/ modernizr -->
    </head>

    <body id="minovate" class="appWrapper">

        <div id="wrap" class="animsition">
            <div class="page page-core page-login">

                <div class="text-center">
                    <h3 class="text-light text-white">Regional Purchase Unit
                        <span class="text-lightred"><strong>Manuguru</strong></span>
                    </h3>
                </div>

                <div class="container w-420 p-20 bg-white mt-40 text-center">
                    <h2 class="text-light text-greensea">
                        Welcome 
                        <a href="" class="myIcon icon-warning icon-ef-4 icon-ef-4a icon-color">
                            <i class="fa fa-user"></i>
                        </a>
                    </h2>
                    <form class="form-signin" name="loginForm" method="post">
                        <div class="row">
                            <div class="col-md-6">
                                <a href="<c:url value="login.htm"/>">
                                    <section class="tile tile-simple p-20 bg-lightred text-center" style="min-height: 130px; overflow: hidden;">
                                        <!-- tile header -->
                                        <div class="tile-header">
                                            <h2 class="custom-font"><strong>Regional Purchase Unit</strong></h2>
                                            <img src="<c:url value="/appimages/OrgMgmt.png"/>"/>
                                        </div>
                                    </section>
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="<c:url value="login.htm"/>">
                                    <section class="tile tile-simple p-20 bg-blue text-center" style="min-height: 130px; overflow: hidden;">
                                        <!-- tile header -->
                                        <div class="tile-header">
                                            <h1 class="custom-font"><strong>Section <br/> Unit </strong></h1>
                                            <img src="<c:url value="/appimages/SectionUnit.png"/>"/>
                                        </div>
                                    </section>
                                </a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <a href="<c:url value="login.htm"/>">
                                    <section class="tile tile-simple p-20 bg-greensea text-center" style="min-height: 130px; overflow: hidden;">
                                        <!-- tile header -->
                                        <div class="tile-header">
                                            <h1 class="custom-font"><strong>Stores</strong></h1>
                                            <img src="<c:url value="/appimages/Stores.png"/>"/>
                                        </div>
                                    </section>
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="<c:url value="login.htm"/>">
                                    <section class="tile tile-simple p-20 bg-orange text-center" style="min-height: 130px; overflow: hidden;">
                                        <!-- tile header -->
                                        <div class="tile-header">
                                            <h1 class="custom-font"><strong>Accounts</strong></h1>
                                            <img src="<c:url value="/appimages/Accounts.png"/>"/>
                                        </div>
                                    </section>
                                </a>
                            </div>
                        </div>
                    </form>
                </div>


            </div>
        </div>


        <!-- ============================================
        ============== Vendor JavaScripts ===============
        ============================================= -->
        <!--        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>-->
        <script src="<c:url value="/assets/js/vendor/jquery/jquery-1.11.2.min.js"/>"></script>
        <script src="<c:url value="/assets/js/vendor/bootstrap/bootstrap.min.js"/>"></script>
        <script src="<c:url value="/assets/js/vendor/jRespond/jRespond.min.js"/>"></script>
        <script src="<c:url value="/assets/js/vendor/sparkline/jquery.sparkline.min.js"/>"></script>
        <script src="<c:url value="/assets/js/vendor/slimscroll/jquery.slimscroll.min.js"/>"></script>
        <script src="<c:url value="/assets/js/vendor/animsition/js/jquery.animsition.min.js"/>"></script>
        <script src="<c:url value="/assets/js/vendor/screenfull/screenfull.min.js"/>"></script>
        <!--/ vendor javascripts -->

        <!-- ============================================
        ============== Custom JavaScripts ===============
        ============================================= -->
        <script src="<c:url value="/assets/js/main.js"/>"></script>
        <!--/ custom javascripts -->


        <!-- ===============================================
        ============== Page Specific Scripts ===============
        ================================================ -->
        <script>
            $(window).load(function () {


            });
        </script>
        <!--/ Page Specific Scripts -->

    </body>
</html>
