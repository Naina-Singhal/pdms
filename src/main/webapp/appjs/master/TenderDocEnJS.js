/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    
    show_record();
    
    $("#navigation li a").removeClass("active");
      $("#masterul").css("display","block");
      $("#tendocen").addClass("active");
      
      
    //hide the error, success messages when load the page
        $(".display_msg_error_Ma").hide();
        $(".display_msg_success_Ma").hide(); 
        
    //**************************************************************************
    //START Save Tender Document details in data base
    //**************************************************************************
    
    $("#saveTenderDoc").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#tenderNo, #tenderDate, #tenderAmt, #noOfCopies, #dateOfIssue,'+
                       '#issueClose, #dateOfOpen, #fileNo, #tenderreceFr, #bDescription');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "./saveTenderDocEn.htm",
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify([obj]),
            type : 'POST',
            success : function(data) {
                //alert(data);
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#ten_number_form")[0].reset();
                    show_record();
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That Tender No already exist, Please try with different Number.");
                    $(window).scrollTop(0);
                } else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                    $(window).scrollTop(0);
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                    $(window).scrollTop(0);
            },beforeSend:function(){
                return confirm("Are you sure?");
            }
        });
        
        }); 
    //**************************************************************************    
    //END Save Save Tender Document details in data base
    //**************************************************************************
   
    //**************************************************************************    
    //START Tender Document details record from data base
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getTenderDocRe.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
        success: function(IBC){           
            var result = $.parseJSON(IBC);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th> <th></th><th></th><th></th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td><td>"+v.tenderNo+"</td>"+
                           "<td>"+v.tenderDate+"</td><td>"+v.tenderAmt+"</td>"+
                           "<td>"+v.dateOfIssue+"</td><td>"+v.dateOfOpen+"</td><td>"+v.fileNo+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='edit_button' value=" + v.tenderDocID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(7)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text" />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '10%'},
                        {title: "Tender No", width: '14%'},
                        {title: "Date", width: '10%'},
                        {title: "Amount", width: '18%'},
                        {title: "Date Of Issue", width: '11%'},
                        {title: "Date Of Opening", width: '13%'},
                        {title: "File NO", width: '14%'},
                        {title: "Buttons", width: '10%'},
                    ],
                    'aoColumnDefs': [{
                            'bSortable': false,
                            'aTargets': [-1] /* 1st one, start by the right */
                    }]
            });
 
            // Apply the search
            $("#basic-usage thead input").on( 'keyup change', function () {
            table
                .column( $(this).parent().index()+':visible' )
                .search( this.value )
                .draw();
            });
            
            
        },complete: function (data) {
                 //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#edit_button', function () {                    
                    $("#ten_number_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getTenderDocReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({tenderDoc_id: this.value}),
                        success: function (htmlH) {
                            //alert(htmlH);
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {
                                //alert(v.sectionCode);
                                $("#tenderDocID").val(v.tenderDocID);
                                $("#tenderNo").val(v.tenderNo);
                                $("#tenderDate").val(v.tenderDate);
                                $("#tenderAmt").val(v.tenderAmt);
                                $("#noOfCopies").val(v.noOfCopies);
                                $("#dateOfIssue").val(v.dateOfIssue);
                                $("#issueClose").val(v.issueClose);
                                $("#dateOfOpen").val(v.dateOfOpen);
                                $("#fileNo").val(v.fileNo);
                                $("#tenderreceFr").val(v.tenderreceFr);
                                $("#bDescription").val(v.bDescription);
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveTenderDoc").css("display", "none");
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
    //END Tender Document details record from data base
    //**************************************************************************
    
    //**************************************************************************
    //START: Tender Document details EDIT***************************************
    //**************************************************************************

    $("#cancelTenderDoc").click(function () {
        close_message();
        $("#ten_number_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveTenderDoc").css("display", "block");
    });

    $("#updateTenderDoc").click(function () {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#tenderDocID, #tenderNo, #tenderDate, #tenderAmt, #noOfCopies,'+
                     '#dateOfIssue, #issueClose, #dateOfOpen, #fileNo, #tenderreceFr, #bDescription');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "./updateTenderDocEn.htm",
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
                    $("#ten_number_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveTenderDoc").css("display", "block");
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That Tender No already exist, Please try with different Number.");
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
    //END: Tender Document details EDIT
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
