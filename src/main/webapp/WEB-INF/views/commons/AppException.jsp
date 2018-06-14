<%-- 
    Document   : AppException
    Created on : Mar 12, 2015, 9:28:18 PM
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
        <title>PDMS :Exception Page</title>
        <jsp:include page="../commons/CommonCSSIncl.jsp"/>
    </head>

    <body id="minovate" class="appWrapper">
        <c:set var="message" value=""/>
        <c:set var="code" value=""/>

        <c:if test="${requestScope.exceptionBean != null}">
            <c:set var="message"  value="${requestScope.exceptionBean.exceptionMessage}"/>
            <c:set var="code"  value="${requestScope.exceptionBean.exceptionCode}"/>
            <c:set var="me"  value="${requestScope.exceptionBean.exception}"/>
        </c:if>
        <div id="wrap" class="animsition">
            <jsp:include page="../commons/CommonHeader.jsp"/>
            <section id="content">
                <div class="page">
                    <!-- == Start :Page Header & BRead Crumbs ======== -->
                    <div class="pageheader">
                        <h2>Exception <span> Page</span></h2>
                    </div>
                    <!-- == End :Page Header & BRead Crumbs ======== -->
                    <!-- == Start :Page Form ======== -->
                    <div class="pagecontent">   
                        <div class="form-group">
                            <label for="exampleInputEmail1">Exception Code -</label>
                            <br/>
                            <label><c:out value="Exception - ${code}"/></label>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Exception Message -</label>
                            <br/>
                            <label><c:out value="Exception - ${message}"/></label>
                        </div>
                        <c:if test="${me!=null}">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Exception Stack Trace -</label>
                                <br/>
                                <label><c:out value="${me.getMessage()}"/></label>
                                <br/><br/><br/><br/>
                                <span id="linkVal">
                                    <a href='#' onclick="javascript:fnShowStackTrace('s')">
                                        View Stack Trace
                                    </a>
                                </span>
                                <span id="linkDVal" style="display: none">
                                    <a href='#' onclick="javascript:fnShowStackTrace('h')">
                                        Hide Stack Trace
                                    </a>
                                </span>
                            </div>
                            <div class="form-group" id="stackTrace">
                                <c:forEach var="trace" items="${me.getStackTrace()}">
                                    <label>${trace}</label><br/>
                                </c:forEach>
                            </div>    
                        </c:if>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="../commons/CommonJSIncl.jsp"/>

        <script>
            $(document).ready(function () {
                $("#stackTrace").hide();
            });

            function fnShowStackTrace(selVal)
            {
                if (selVal == 's')
                {
                    $("#linkDVal").show();
                    $("#stackTrace").show();
                    $("#linkVal").hide();
                }
                if (selVal == "h")
                {
                    $("#linkDVal").hide();
                    $("#stackTrace").hide();
                    $("#linkVal").show();
                }
            }

        </script>
        <script>

         
        </script>    
    </body>

</html>
