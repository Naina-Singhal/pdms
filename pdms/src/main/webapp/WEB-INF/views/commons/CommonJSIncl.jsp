<%-- 
    Document   : CommonJSIncl
    Created on : Mar 10, 2015, 8:39:15 PM
    Author     : hpasupuleti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
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
       ============== Data Table(s) JavaScripts ===============
       ============================================= -->
<script src="<c:url value="/assets/js/vendor/datatables/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/js/jquery.dataTables.columnFilter.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/ColReorder/js/dataTables.colReorder.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/Responsive/js/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/ColVis/js/dataTables.colVis.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/TableTools/js/dataTables.tableTools.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datatables/extensions/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/daterangepicker/moment.min.js"/>"></script>
<script src="<c:url value="/assets/js/vendor/datetimepicker/js/bootstrap-datetimepicker.min.js"/>"></script>

<!--Select drop down -->
<script src="<c:url value="/assets/js/vendor/boostrap-select/js//bootstrap-select.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/assets/js/vendor/boostrap-select/css/bootstrap-select.css"/>">
<script src="<c:url value="/appjs/plugins/jquery-shorten.js"/>" type="text/javascript"></script>

<!-- ============================================
       ============== Custom JavaScripts ===============
       ============================================= -->
<script src="<c:url value="/assets/js/main.js"/>"></script>
<!--/ custom javascripts -->
<script src="<c:url value="/appjs/CommonUtils.js"/>" type="text/javascript"></script>
<script src="<c:url value="/appjs/CommonFile.js"/>" type="text/javascript"></script>
<jsp:include page="../sessionpopup.jsp"/>