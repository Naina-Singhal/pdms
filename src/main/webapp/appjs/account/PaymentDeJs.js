/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#paymmnvtdetla").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    //**************************************************************************
    //START: DATA FETCHING FROM PO ENTRY DEPENDS ON PO NUMBER
    //**************************************************************************
    $("#poNumPayDe").keyup(function () {        
        $("#errorpoNumPayDe").empty();        
        var poNoum = parseInt(this.value);        
        if(poNoum <= 0 || isNaN(poNoum) === true){
             $("#errorpoNumPayDe").append("Please enter valid number. ");
             makeEmptyFunction();
        }else{
        $.ajax({
            url: "getPoDetailsByFileNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PONumber: poNoum}),
            success: function (resp) {                 
                var result = $.parseJSON(resp);
                if($.isEmptyObject(result)){
                    $("#errorpoNumPayDe").append("There no data. ");
                        makeEmptyFunction();
                }else{
                $.each(result, function (k, v) {                    
                    $.ajax({
                        url: "getVendorNameByCode.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({venCode: v.venderName}),
                        success: function (res) {                             
                            var res = $.parseJSON(res);
                            $.each(res, function (k, w) {                                
                                $("#vendorName").append("<option value='" + w.vendorCode + "' selected>(" + w.vendorCode + ")" + w.vendorName + "</option>");
                                
                                $("#vendorAddress").val("Address :"+w.vendorAddress+", city: "+w.vendorCity+", phone: "+w.vendorPhone);
                            });
                            
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }
                    });
                    $("#fileNo").val(v.tenderFN);
                    $("#poDate").val(v.poDate);
                    $("#poValue").val(v.poValue);
                    $("#paymentTerms").val(v.payment);
                    generateItemsList(poNoum);
                });
            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {

            }
        });
    }
    });
    
    function makeEmptyFunction(){
        $("#fileNo").val('');
        $("#vendorName").val('');
        $("#poDate").val('');
        $("#poValue").val('');
        $("#vendorAddress").val("null");
        $("#paymentTerms").val('');
    }
    //**************************************************************************
    //END: DATA FETCHING FROM PO ENTRY DEPENDS ON PO NUMBER
    //**************************************************************************

    //******************************************************************
    //START: BILL ENTRY DETAILS FOR APPEND TO BILL ITEMS LIST
    //****************************************************************** 
    function generateItemsList(poNoFrItemsList) {          
        $("#stic_bill_items").empty();
        $.ajax({
            url: "getBillEnReByPoNum.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PoNum: poNoFrItemsList}),
            success: function (billDa) {                
                var res = $.parseJSON(billDa);
                var slNo = 1;
                $.each(res, function (k, m) {
                    $("#stic_bill_items").append("<tr>" +                            
                            "<td>" + (slNo++) + "</td><td>" + m.billNo + "</td>" +
                            "<td>" + m.billDate + "</td><td>" + m.billAmt + "</td>"+
                            "<td><input type='date' name='' id='' class='painput' /></td>"+
                            "<td><input type='text' name='' id='' class='painput' /></td></tr>");
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //END:  BILL ENTRY DETAILS FOR APPEND TO BILL ITEMS LIST
    //******************************************************************
    
});

