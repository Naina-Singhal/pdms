/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openBillEnForm() {

    window.open("./billEntryScreen.htm", "_parent");
}
$(document).ready(function () {


    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#billenscrn").addClass("active");
    show_record();
     
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    $("#formOfBill").hide();



    //**************************************************************************
    //**START: BASIC INITIALZATION FOR LESS MORE  PLUGIN************************
    //********Any forther help for this plugin click below link*****************
    //*****www.jqueryscript.net/text/Read-More-Less-Plugin-jQuery-Shorten.html**
    function shorten_fun() {
        $('.less-more').shorten({
            chars: 40,
            more: '&#8680;',
            less: '&#8678;'
        });


        $(".dataTables_paginate").on('click', function () {
            //alert("ok"); 
            $('.less-more').shorten({
                chars: 40,
                more: '&#8680;',
                less: '&#8678;',
                ellipses: '&nbsp...'


            });

        });
    }
    //**************************************************************************
    //**START: BASIC INITIALIZATION FOR LESS MORE PLUGIN*************************
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
    
    //**************************************************************************    
    //START display BILL ENTRY record
    //**************************************************************************
    
    function show_record() {

        $.ajax({
            url: "getBillEnJsonRe.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (IBC) {              
                var result = $.parseJSON(IBC);
                var final = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>" +
                        "<thead>" +
                        "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th> <th>Buttons</th></tr></thead>" +
                        "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>" +
                        "<tbody id='table_body'>";
                var slno = 1;
                $.each(result, function (k, v) {
                    final = final + "<tr class='gradeX'>" +
                            "<td>" + (slno++) + "</td>" +
                            "<td>" + v.poNumber + "</td><td>" + v.supplierName + "</td>" +
                            "<td>" + v.billNo + "</td>" +
                            "<td>" + v.billAmt + "</td><td>" + v.gstinNo + "</td><td class='less-more'>" + v.paymentTerm + "</td>" +
                            "<td><button type='button' class='btn btn-info btn-rounded btn-sm' " +
                            "id='geneLrBillEnPdf' style='margin-right: 5px;' value=" + v.billId + ">PDF </button>"+
                            "<button type='button' class='btn btn-info btn-rounded btn-sm' " +
                            "id='ButBut' value=" + v.billId + ">Edit </button></td> ";

                });
                final = final + "</tbody></table></div>";
                $("#show_table").html(final);

                // Setup - add a text input to each footer cell
                $('#basic-usage thead  #filterrow th').not(":eq(7)").each(function () {
                    var title = $(this).text();
                    $(this).html('<input type="text"  />');
                });

                // DataTable
                var table = $('#basic-usage').DataTable({
                    autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "PO No", width: '14%'},
                        {title: "Vender Name", width: '14%'},
                        {title: "Bill No", width: '10%'},
                        {title: "Bill Amount", width: '14%'},
                        {title: "GSTIN No", width: '14%'},
                        {title: "Payment Terms", width: '16%'},
                        {title: "Buttons", width: '10%'},
                    ],
                    'aoColumnDefs': [{
                            'bSortable': false,
                            'aTargets': [-1] /* 1st one, start by the right */
                        }]
                });

                // Apply the search
                $("#basic-usage thead input").on('keyup change', function () {
                    table
                            .column($(this).parent().index() + ':visible')
                            .search(this.value)
                            .draw();
                });
                
                $("#basic-usage").on('click', '#geneLrBillEnPdf', function () {
                    
                    window.open('lrForwardPdfDown.htm?eindi=' + this.value);
                });

            }, complete: function (data) {
                shorten_fun();
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {
                    close_message();
                    $("#supplierName").empty();
                    $("#gstinNo").empty();
                    $("#Bill_edit_Form")[0].reset(); 
                    $("#formOfBill").show();
                    $("#recordOfBill").hide();
                    var bill_id = parseInt(this.value);
                    $.ajax({
                        url: "getBillEnDeFrEdit.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({Bill_ID: bill_id}),
                        success: function (details) {                            
                            var result = $.parseJSON(details);
                            $.each(result, function (k, v) {
                                //alert(v.gstinNo);
                                //alert("ven---"+v.supplierName);
                                $("#billId").val(bill_id);
                                $("#supplierName").append("<option value='" + v.supplierName + "'>(" + v.supplierName + ")" + v.supplierName + "</option>");
                                $("#poNumber").val(v.poNumber);
                                $("#paymentTerm").val(v.paymentTerm);
                                $("#billNo").val(v.billNo);
                                $("#billDate").val(v.billDate);
                                $("#dcNo").val(v.dcNo);
                                $("#billAmt").val(v.billAmt);                                
                                $.ajax({
                                    url: "getGstinNumber.htm",
                                    type: "POST",
                                    cache: false,
                                    async: false,
                                    success: function (details) {
                                        //alert(details);
                                        var result = $.parseJSON(details);

                                        $.each(result, function (k, w) {
                                            //alert(v.gstinNo+"--"+w.gstinNumber);
                                            if (v.gstinNo === w.gstinNumber) {
                                                $("#gstinNo").append("<option value='" + w.gstinNumber + "'selected>" + w.gstinNumber + "</option>");
                                            } else {
                                                $("#gstinNo").append("<option value='" + w.gstinNumber + "'>" + w.gstinNumber + "</option>");
                                            }
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

                });


            }
        });
    }
     
    //**************************************************************************    
    //END: BILL ENTRY   record 
    //**************************************************************************
    
    
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************

    var table1 = $('#basic-usage').DataTable();

    $('#basic-usage tbody')
            .on('mouseenter', 'td', function () {
                var colIdx = table1.cell(this).index().column;

                $(table1.cells().nodes()).removeClass('highlight');
                $(table1.column(colIdx).nodes()).addClass('highlight');
            });
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 
   
    
     
    //**************************************************************************
    //***START: UPDATE BILL ENTRY DATA ENTRY
    //**************************************************************************

    $("#updateBillEntry").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
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
                if (data === 1) {                     
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong>Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#formOfBill").hide();
                    $("#recordOfBill").show();
                    $("#Bill_edit_Form")[0].reset();  
                    show_record();                    
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
            }, complete: function (data) {
               
            }
        });
    });

    //**************************************************************************
    //***END: UPDATE BILL ENTRY DATA 
    //**************************************************************************

}); // End document



                
                function ShowRecord(){
                    $("#recordOfBill").show();
                    $("#formOfBill").hide();

                }
                
      
          