<%-- 
    Document   : Login
    Created on : Mar 2, 2015, 8:03:23 AM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tlds/fn.tld"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> 
<html class="no-js" lang=""> <!--<![endif]-->
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>RPUM - Login Page</title>
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

                <div class="container w-420 p-15 bg-white mt-40 text-center">
                    <h2 class="text-light text-greensea">Log In</h2>
                    <form class="form-signin" name="loginForm" method="post">
                        <div class="form-group">
                            <input id="username" type="text" name="username" maxlength="40" 
                                   class="form-control underline-input" placeholder="Username">
                        </div>
                        <div class="form-group">
                            <input id="password" type="password" name="password" maxlength="40"
                                   placeholder="Password" class="form-control underline-input">
                        </div>
                        <div class="form-group text-left mt-20">
                            <button href="javascript:void(0)" onclick="fnLogin()" 
                               class="btn btn-greensea b-0 br-2 mr-5">Login</button>

                            <a href="#" data-toggle="modal" data-target="#myModal" 
                               class="pull-right mt-10">Forgot Password?</a>
                        </div>
                        <c:if test="${(message != null)&&(fn:length(message)>0)}">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="alert alert-warning alert-dismissable">
                                        <button type="button" class="close" data-dismiss="alert" 
                                                aria-hidden="true">&times;</button>
                                        <strong></strong>${message}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <script>
                            function fnLogin()
                            {
                                document.loginForm.action = "<c:url value="validatelogin.htm"/>";
                                document.loginForm.submit();
                            }
                        </script>
                    </form>
                </div>
            </div>
        </div>
                                 
        <!-- Forgot Password Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title custom-font">Forgot Password!</h3>
                    </div>
                    <div class="modal-body">
                        Please contact respective Site/ Purchase Unit Administrator for retrieving the Password.
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-lightred btn-ef btn-ef-4 btn-ef-4c" data-dismiss="modal"><i class="fa fa-arrow-left"></i> Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Forgot Password Modal -->                 

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
