<%-- 
    Document   : sessionpopup
    Created on : May 31, 2016, 3:39:57 PM
    Author     : rpanuganti
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS : Include -->
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
<!-- CSS : Include -->
<script type="text/javascript" src="<c:url value="/resources/assets/js/vendor/jquery/jquery-1.11.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/appjs/sessionExtends.js"/>"></script>


<!-- Start: popup for session notification -->
<input type="hidden" name="defaulttime" id="defaulttime" />

<div class="modal fade" id="popupbox10" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title custom-font">Session Warning!</h3>
            </div>
            <div class="modal-body">
                Your session is about to end in 5 minutes.
            </div>
            <div class="modal-footer">
                <button class="btn btn-success btn-ef btn-ef-3 btn-ef-3c" onclick="sessionpopup(-10);">
                    <i class="fa fa-arrow-right"></i> Ok
                </button>
            </div>
        </div>
    </div>
</div>

<!--End: popup for session notification -->

<!-- Start: popup for session expired -->

<div class="modal fade" id="popupbox20" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title custom-font">Session Error!</h3>
            </div>
            <div class="modal-body">
                Your session has ended due to inactivity..
            </div>
            <div class="modal-footer">
                <button class="btn btn-success btn-ef btn-ef-3 btn-ef-3c"
                        onclick='location.href = "<c:url value="/sessionInactive.htm"/>"'>
                    <i class="fa fa-arrow-right"></i> Ok
                </button>
            </div>
        </div>
    </div>
</div>

<!-- End: popup for session expired -->