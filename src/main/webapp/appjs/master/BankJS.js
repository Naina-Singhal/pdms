/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#masterul").css("display", "block");
    $("#bannnkkk").addClass("active");
    
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    show_record();
    
    //******************************************************************
    //SATRT: Save RTGS entry details
    //******************************************************************


    $("#saveBank").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#bankCode, #bankName, #branch, #ifscCode, #city');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);        
        $.ajax({
            dataType: "json",
            url: "saveBank.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            success: function (data) {                
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#bank_form")[0].reset();
                    show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to submit ?");
            }
        });

    });

    //**************************************************************************
    //END: RTGS SAVE
    //**************************************************************************
    
    
    //**************************************************************************
    //START: BANK RECORD DISPLAY
    //**************************************************************************
    function show_record() {

        $.ajax({
            url: "getBankMaRecord.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (sign) {                
                var result = $.parseJSON(sign);
                var final = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style=''>" +                        
                        "<thead><tr></tr></thead>" +
                        "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>" +
                        "<tbody id='table_body'>";
                var slno = 1;
                $.each(result, function (k, v) {
                    final = final + "<tr class='gradeX'><td>" + (slno++) + "</td><td>" + v.bankCode + "</td><td>" + v.bankName + "</td><td>" + v.branch + "</td><td>" +v.ifscCode+"</td>"+
                            "<td>"+v.city+"</td><td><button type='button' class='btn btn-info btn-rounded btn-sm' " +
                            "id='ButBut' value=" + v.bankId + ">Edit </button></td> ";

                });
                final = final + "</tbody></table></div>";
                $("#append_record").html(final);

                // Setup - add a text input to each footer cell
                $('#basic-usage thead  #filterrow th').not(":eq(6)").each(function () {
                    var title = $(this).text();
                    $(this).html('<input type="text"  />');
                });

                // DataTable
                var table = $('#basic-usage').DataTable({
                    autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '12%'},
                        {title: "Bank Code", width: '12%'},
                        {title: "Bank NAme", width: '14%'},
                        {title: "Branch", width: '18%'},
                        {title: "IFSC Code", width: '16%'},
                        {title: "City", width: '14%'},
                        {title: "Buttons", width: '14%'},
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




            }, complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#bank_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getBankReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({bank_id: this.value}),
                        success: function (htmlH) {                                                       
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#bankId").val(v.bankId);
                                $("#bankCode").val(v.bankCode);
                                $("#bankName").val(v.bankName);
                                $("#branch").val(v.branch);
                                $("#ifscCode").val(v.ifscCode);
                                $("#city").val(v.city);                               
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveBank").css("display", "none");
                            $(window).scrollTop(0);

                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //**************************************************************************
    //END: BANK RECORD DISPLAY
    //**************************************************************************

    
    //**************************************************************************
    //START: RTGS EDIT
    //**************************************************************************

    $("#cancelBank").click(function () {
        close_message();        
        $("#bank_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveBank").css("display", "block");        
    });

    $("#updateBank").click(function () {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#bankId,#bankCode, #bankName, #branch, #ifscCode, #city');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);        
        $.ajax({
            dataType: "json",
            url: "./updateBankMaster.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) {                
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#bank_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveBank").css("display", "block");                    
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();                
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update Data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to update ?");
                $("#basic-usage").remove();
            }, complete: function (data) {
                show_record();
                $('#basic-usage').load('ajax.html#basic-usage');
            }
        });


    });
    //**************************************************************************
    //END: RTGS EDIT
    //**************************************************************************
    
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************

    var table = $('#basic-usage').DataTable();

        $('#basic-usage tbody')
        .on('mouseenter', 'td', function () {
            var colIdx = table.cell(this).index().column;

            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
        });
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 
    
    
}); //End document