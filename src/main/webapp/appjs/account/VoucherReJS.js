/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {


    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#voudare").addClass("active");


    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
    // Setup - add a text input to each footer cell
    $('#filterrow th').not(":eq(7)").each(function () {
        var title = $(this).text();
        $(this).html('<input type="text"  />');
    });

    var table = $('#basic-usage').dataTable({
        autoWidth: false,
        "columns": [
            {title: "SL No", width: '5%'},
            {title: "POSR No", width: '10%'},
            {title: "Supplier Name", width: '15%'},
            {title: "Payment Mode", width: '11%'},
            {title: "PPR NO", width: '13%'},
            {title: "IBC No", width: '15%'},
            {title: "Rs In Words", width: '15%'},
            {title: "Buttons", width: '15%'}
        ],
        'aoColumnDefs': [{
                'bSortable': false,
                'aTargets': [-1] /* 1st one, start by the right */
            }]
    });

    // Apply the search
    $("#basic-usage thead input").on('keyup change', function () {
        table.column($(this).parent().index() + ':visible')
                .search(this.value)
                .draw();
    });

    //***********************************************
    //End : Initialize basic datatable
    //***********************************************

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
    //**START: BASIC INITIALZATION FOR LESS MORE  PLUGIN************************
    //********Any forther help for this plugin click below link*****************
    //*****www.jqueryscript.net/text/Read-More-Less-Plugin-jQuery-Shorten.html**
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
    //**************************************************************************
    //**START: BASIC INITIALIZATION FOR LESS MORE PLUGIN*************************
    //**************************************************************************


});

function openVouchForm() {

    window.open("./voucherDataEnView.htm", "_parent");
}






