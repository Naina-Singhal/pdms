/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#masterul").css("display", "block");
    $("#hoaccss").addClass("active");
//hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    show_record();
    //******************************************************************
    //SATRT: SAVE HEAD OF ACCOUNT SHORT CODES DETAILS
    //******************************************************************


    $("#saveHoac").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#accountName, #shortCode, #description');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "saveHoaCodes.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            success: function (data) {
                //alert(data);
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#Hoac_form")[0].reset();
                    $("#accountName").selectpicker("refresh");
                    show_record();
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That 'Short Code' already exist, Please try with different code.");
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
    //END: SAVE HEAD OF ACCOUNT SHORT CODES DETAILS
    //**************************************************************************
    
    
    //**************************************************************************
    //START: HEAD OF ACCOUNT SHORT CODES  RECORD DISPLAY
    //**************************************************************************
    function show_record() {        
        $.ajax({
            url: "getHoaCodesDetails.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (sign) {                
                var result = $.parseJSON(sign);
                var final = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style=''>" +                        
                        "<thead><tr></tr></thead>" +
                        "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th></tr></thead>" +
                        "<tbody id='table_body'>";
                var slNo = 0;
                $.each(result, function (k, v) {
                    final = final + "<tr class='gradeX'><td>" + (++slNo) + "</td><td>" + v.shortCode + "</td><td>" + v.accountName + "</td>"+
                            "<td>"+v.description+"</td><td><button type='button' class='btn btn-info btn-rounded btn-sm' " +
                            "id='edit_button' value=" + v.hoaCodeId + ">Edit </button></td> ";

                });
                final = final + "</tbody></table></div>";
                $("#append_record").html(final);

                // Setup - add a text input to each footer cell
                $('#basic-usage thead  #filterrow th').not(":eq(4)").each(function () {
                    var title = $(this).text();
                    $(this).html('<input type="text"  />');
                });

                // DataTable
                var table = $('#basic-usage').DataTable({
                    autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '10%'},
                        {title: "Short Code", width: '20%'},
                        {title: "Account Name", width: '25%'},
                        {title: "Description", width: '25%'},                        
                        {title: "Buttons", width: '20%'},
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
                $('#basic-usage').on('click', '#edit_button', function () {                    
                    $("#Hoac_form")[0].reset();
                    $("#accountName").selectpicker("refresh");
                    close_message();
                    $.ajax({
                        url: "getHoacCodesReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({HoaCode_id: this.value}),
                        success: function (htmlH) {
                            //alert(htmlH);
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#hoaCodeId").val(v.hoaCodeId);                                
                                $('select[name=accountName]').val(v.accountName);
                                $('#accountName').selectpicker('refresh');                               
                                $("#shortCode").val(v.shortCode);
                                $("#description").val(v.description);                                
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveHoac").css("display", "none");
                            $(window).scrollTop(0);

                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });

            }
        });
    }
    //**************************************************************************
    //END: HEAD OF ACCOUNT SHORT CODES RECORD DISPLAY
    //**************************************************************************

    //**************************************************************************
    //START: HEAD OF ACCOUNT SHORT CODES EDIT
    //**************************************************************************

    $("#cancelHoac").click(function () {
        close_message();
        $("#Hoac_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveHoac").css("display", "block");
        $("#accountName").selectpicker("refresh");
    });

    $("#updateHoac").click(function () {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#hoaCodeId, #accountName, #shortCode, #description');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateHoaShortCodes.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) {
                //alert(data);
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#Hoac_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveHoac").css("display", "block");
                    $("#accountName").selectpicker("refresh");
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That 'Short Code' already exist, Please try with different code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while updating data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();                
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while updating Data.");
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
    //END: HEAD OF ACCOUNT SHORT CODES EDIT
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
    
    
    //**************************************************************************
    //***START; GETTING HEAD OF ACCOUNT NAMES**********************
    //**************************************************************************
    $.ajax({
        url: "getHoaNamesDetails.htm",
        type: "POST",
        cache: false,
        async: false,
        success: function (data) {            
            var res = $.parseJSON(data);
            if ($.isEmptyObject(res)) {
                $("#errorAccountName").append("This number has no data. ");               
            } else {
                $.each(res, function (k, v) {
                    $("#accountName").append("<option value=" + v.accountType + ">" + v.accountType + "</option>");
                });
            }

        }, complete: function (data) {
        }
    });
    //**************************************************************************
    //***END; GETTING HEAD OF ACCOUNT NAMES*********************
    //**************************************************************************
});