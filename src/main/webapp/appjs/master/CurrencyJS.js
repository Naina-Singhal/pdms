/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#masterul").css("display", "block");
    $("#crnctype").addClass("active");
//hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    show_record();
//******************************************************************
//Save currency entry details
//******************************************************************


    $("#saveCurrency").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#currencyName, #currencyCode,#currencyDes');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "./saveCurrency.htm",
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
                    $("#currency_form")[0].reset();
                    show_record();
                }else if(data === -1){                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while update data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to submit ?");
            }
        });

    });

    //**************************************************************************
    //END: currency SAVE
    //**************************************************************************

    //**************************************************************************
    //START: CURRENCY RECORD DISPLAY
    //**************************************************************************
    function show_record() {

        $.ajax({
            url: "getCurrencyDetails.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({uid: "dummy"}),
            success: function (payment) {
                //alert(payment);
                //$("#poNumber").val(poNo);
                var result = $.parseJSON(payment);
                var final = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>" +
                        "<thead>" +
                        "<tr><th>S.No#</th><th>Code</th><th>Name</th><th>Description</th> <th>Buttons</th></tr></thead>" +
                        "<thead><tr id='filterrow'><th>S.No#</th><th>Code</th><th>Name</th><th>Description</th><th></th></tr></thead>" +
                        "<tbody id='table_body'>";
                var slno = 1;
                $.each(result, function (k, v) {
                    final = final + "<tr class='gradeX'><td>" + (slno++) + "</td><td>" + v.currencyCode + "</td><td>" + v.currencyName + "</td><td>" + v.currencyDes + "</td>" +
                            "<td><button type='button' class='btn btn-info btn-rounded btn-sm' " +
                            "id='btnBtn' value=" + v.currencyId + ">Edit </button></td> ";

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
                $('#basic-usage').on('click', '#btnBtn', function () {                    
                    $("#currency_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getCurrencyReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({cur_id: this.value}),
                        success: function (htmlH) {
                            //alert(htmlH);
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {
                                //alert(v.sectionCode);
                                $("#currencyId").val(v.currencyId);
                                $("#currencyName").val(v.currencyName);
                                $("#currencyCode").val(v.currencyCode);
                                $("#currencyDes").val(v.currencyDes);
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveCurrency").css("display", "none");
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
    //END: CURRENCY RECORD DISPLAY
    //**************************************************************************

    //**************************************************************************
    //START: CURRENCY EDIT
    //**************************************************************************

    $("#cancelCurrency").click(function () {
        close_message();
        $("#currency_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveCurrency").css("display", "block");
    });

    $("#updateCurrency").click(function () {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#currencyId, #currencyName, #currencyCode, #currencyDes');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "./updateCurrency.htm",
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
                    $("#currency_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveCurrency").css("display", "block");
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
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update data.");
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
    //END: CURRENCY EDIT
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
    
});
