<%-- 
    Document   : home.jsp
    Created on : Mar 6, 2015, 4:10:02 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <title>RPUM :Home Page</title>
        <jsp:include page="commons/CommonCSSIncl.jsp"/>
    </head>

    <body id="minovate" class="appWrapper">

        <!-- ====================================================
        ================= Start : Application Content ===========
        ===================================================== -->
        <div id="wrap" class="animsition">
            <jsp:include page="commons/CommonHeader.jsp"/>    
        </div>
        <!-- ====================================================
        ================= End : Application Content =============
        ===================================================== -->
        
        
        <jsp:include page="commons/CommonJSIncl.jsp"/>
    </body>

</html>
