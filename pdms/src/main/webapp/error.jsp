<%-- 
    Document   : error
    Created on : Apr 28, 2016, 7:25:06 PM
    Author     : mrejeti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">

        <title>RPUM - Error Page</title>

        <!-- Bootstrap core CSS -->
        <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
        <link href="<c:url value="/css/bootstrap-reset.css"/>" rel="stylesheet">
        <!--external css-->
        <link href="<c:url value="/assets/font-awesome/css/font-awesome.css"/>" rel="stylesheet" />
        <!-- Custom styles for this template -->
        <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
        <link href="<c:url value="/css/style-responsive.css"/>" rel="stylesheet" />

        <!--script for this page-->
        <%--
        <script src="<c:url value="/js/form-validation-script.js"/>"></script>
        --%>

        <script src="<c:url value="/js/jquery.js"/>"></script>
        <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
    </head>
    <body class="login-body">
        <div class="container">
            <!-- Begin: Content -->
            <div class="ux-content">
                <table class="ux-height-13t" width="100%">
                    <tr>
                        <td align="center"><h2>An error occurred on the Application server. Please try to access the application after some time.
                                <br><br>
                                We apologize for any inconvenience caused.
                            </h2></td>
                    </tr>
                    <tr>
                        <td align="center">
                            <a href="<c:url value="/login.htm"/>" title="Click here">Click here</a> to navigate to the login page
                        </td>
                    </tr>

                </table>
            </div>
        </div>
    </body>
</html>
