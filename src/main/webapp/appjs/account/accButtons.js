/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {


    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#accopdfs").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#rtgsOfficePdf").on('click', function () {
        var datee = $("#rtgsDate").val();
        
        window.open('rtgsOfficePdf.htm?rtgsdate=' + datee);
    });
    
    $("#postageDetailsPdf").on('click', function () {
        var datee1 = $("#rtgsDate").val();
        
        window.open('postageDetailsPdf.htm?postagedate=' + datee1);
    });
    
    
    $("#postEntryPdf").on('click', function () {
        var frdatee = $("#fromDateP").val();
        var todatee = $("#toDateP").val();
        window.open('postEntryStaPdf.htm?fromdate=' + frdatee+'&todate='+todatee);
    });
    
    $("#SecuriDeFrMn").on('click', function () {
        var headOfAccount = $("#headOfAc").val();
        var dateAc = $("#onlyMonth").val();
        
        window.open('securityDeFrMoPdf.htm?headOfAccount=' + headOfAccount+'&dateAc='+dateAc);
    });
    
    $("#EmdDeForMonth").on('click', function () {
        var headOfAccount = $("#headOfAc").val();
        var dateAc = $("#onlyMonth").val();
        
        window.open('emdDetailsForMoPdf.htm?headOfAccount=' + headOfAccount+'&dateAc='+dateAc);
    });
    
    $("#fileMoveAckonledge").on('click', function () {
        var receivwer = $("#receiver").val();
        var dateAc = $("#fileMoveDate").val();
        
        window.open('fileMoveMentPdf.htm?receiver=' + receivwer+'&dateAc='+dateAc);
    });
    
    
});