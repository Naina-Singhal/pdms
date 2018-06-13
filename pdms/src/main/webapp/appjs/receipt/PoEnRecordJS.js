/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function next_po() {

    window.open("./purOrdLi.htm", "_parent");
}
$(document).ready(function () {

    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#perecord").addClass("active");

    // Setup - add a text input to each footer cell
    $('#filterrow th').not(":eq(8)").each(function () {
        var title = $(this).text();
        $(this).html('<input type="text"  />');
    });

    var table = $('#basic-usage').dataTable({
        autoWidth: false,
        "columns": [
            {title: "SL No", width: '7%'},
            {title: "PO No", width: '10%'},
            {title: "Date", width: '9%'},
            {title: "Indentor", width: '13%'},
            {title: "Section", width: '10%'},
            {title: "PO Value", width: '10%'},
            {title: "Value Paid", width: '10%'},
            {title: "Place Of Delivery", width: '15%'},
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
    //END : Initialize basic datatable
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
    //**START: BASIC INITIALIZATION FOR LESS MORE PLUGIN************************
    //**************************************************************************



});