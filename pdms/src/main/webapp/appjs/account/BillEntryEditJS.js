/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openBillRe() {

    window.open("./BillEntryRe.htm", "_parent");
}
$(document).ready(function () {
    


    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#billenscrn ").addClass("active");
    
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    //******************************************************************
    //Getting bill entry details for update
    //******************************************************************  
    var bill_id = parseInt($("#billId").val());
    $.ajax({
        url: "getBillEnDeFrEdit.htm",
        type: "POST",
        cache: false,
        async: false,
        data: ({Bill_ID: bill_id}),
        success: function (details) {
            alert(details);
            var result = $.parseJSON(details);
            $.each(result, function (k, v) {
                $("#supplierName").append("<option value='" + v.supplierName + "'>(" + v.supplierName + ")" + v.supplierName + "</option>");
                $("#poNumber").val(v.poNumber);
                $("#paymentTerm").val(v.paymentTerm);
                $("#billNo").val(v.billNo);
                $("#billDate").val(v.billDate);
                $("#dcNo").val(v.dcNo);
                $("#billAmt").val(v.billAmt);
                //$("#gstinNo").append("<option value='" + v.gstinNo + "'>" + v.gstinNo + "</option>");
                $.ajax({
                    url: "getGstinNumber.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    success: function (details) {
                        var result = $.parseJSON(details);
                        
                        $.each(result, function (k, w) {
                            if (v.gstinNo === w.gstinNumber) {
                                $("#gstinNo").append("<option value='" + w.gstinNumber + "'selected>" + w.gstinNumber + "</option>");
                            }
                            $("#gstinNo").append("<option value='" + w.gstinNumber + "'>" + w.gstinNumber + "</option>");
                        });
                    }, error: function (jqXHR, textStatus, errorThrown) {
                        alert(textStatus + " :" + errorThrown);

                    }, complete: function (data) {
                    }
                });

            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert(textStatus + " :" + errorThrown);

        }, complete: function (data) {
        }
    });
    
    
    //**************************************************************************
    //***START: UPDATE BILL ENTRY DATA ENTRY
    //**************************************************************************

    $("#updateBillEntry").click(function () {
        $("#errorDupItem").empty();

        var $items = $('#billId, #poNumber, #supplierName,#paymentTerm,#billNo, #billDate,' +               
                ' #dcNo, #billAmt, #lrEnclosed, #gstinNo');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);       

        $.ajax({
            dataType: "json",
            url: "./updateBillEntry.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            success: function (data) {         
                //alert(data);
                if (data === 1) {                    
                    window.location.href = "./BillEntryReSuc.htm?message=1";
                    
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                }  else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want save data ?");
            }
        });
    });

    //**************************************************************************
    //***END: UPDATE BILL ENTRY DATA ENTRY
    //**************************************************************************
    
     //******************************************************************
    //START: PAYMENT TEMPLATE
    //******************************************************************            
                $("#paymentTerm").dblclick(function(){
                    $(".modal-body").empty();                    
                    var text_id;
                    $("#cstModal").modal("show");                    
                    $.ajax({
                        url: "getPaymentTerms.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlH){				
                            var result = $.parseJSON(htmlH);			
                            $.each(result, function(k, v) {
                            //alert(v.description);			
                                $(".modal-body").append("<div class='ineer-model' id='"+v.paymentCode+"' >"
                                +" "+v.paymentDes+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#paymentTerm").text('');                                   
                                    document.getElementById("paymentTerm").value = tex;
                                    $('#cstModal').modal('toggle');
                                    var WhereToMove = $("#paymentTerm").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);                                   
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: PAYMENT TEMPLATE
    //******************************************************************   

});