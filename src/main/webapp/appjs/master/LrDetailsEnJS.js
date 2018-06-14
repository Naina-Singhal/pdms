/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    
    show_record();
    
    $("#navigation li a").removeClass("active");
      $("#masterul").css("display","block");
      $("#lrdetaen").addClass("active");
      
      
    //hide the error, success messages when load the page
        $(".display_msg_error_Ma").hide();
        $(".display_msg_success_Ma").hide(); 
        
    $.ajax({
        url: "getVendorName.htm",
        type: "POST",
        cache: false,
        async: false,
        success: function (details) {
            var result = $.parseJSON(details);
            $("#nameOfSupplr").empty();
            $("#nameOfSupplr").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                $("#nameOfSupplr").append("<option value='" + v.vendorCode + "'>(" + v.vendorCode + ")" + v.vendorName + "</option>");
            });
            $("#nameOfSupplr").selectpicker("refresh");
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert(textStatus + " :" + errorThrown);

        }, complete: function (data) {
        }
    });
    
        //******************************************************************
    //START: PO LIST
    //****************************************************************** 
    $.ajax({
        url: "getPoEntryRecord.htm",
        type: "POST",
        cache: false,
        async: false,
        success: function (POnos) {            
            var result = $.parseJSON(POnos);
            $("#poNumber").empty();
            $("#poNumber").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                $("#poNumber").append("<option value='" + v.poNumber + "'>" + v.poNumber + "</option>");
            });
            $("#poNumber").selectpicker("refresh");
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert(textStatus + " :" + errorThrown);

        }, complete: function (data) {
        }
    });
    
    //******************************************************************
    //END: PO LIST
    //******************************************************************
 
    //******************************************************************
    //START: fetching data
    //****************************************************************** 
    $("#poNumber").on('change', function () {
        var ponumber = this.value;        
        $("#errorpoNumber").empty();
        if (ponumber === 'null' || ponumber === '') {
            $("#errorpoNumber").append("Please select PO number. ");

        } else {
            $.ajax({
                url: "getIbcNoFrIbcMaByPo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoNumb: ponumber}),
                success: function (htmlH) {
                    //alert(htmlH);
                    var result = $.parseJSON(htmlH);
                    if ($.isEmptyObject(result)) {
                        $("#errorpoNumber").append("This number has no data. ");
                    } else {

                        $.each(result, function (k, v) {                           
                           $("#nameOfSupplr").val(v.nameOfSupplr); 
                           $("#billNumber").val(v.billNumber); 
                           $("#billDate").val(v.billDate); 
                           $("#billAmount").val(v.billAmount); 
                           $("#ibcNumber").val(v.ibcNumber);
                        });
                        $("#nameOfSupplr").selectpicker("refresh"); 
                    }

                }, complete: function (data) {
                }
            });
        }

    });

    
 
    
    //******************************************************************
    //END: fetching data
    //****************************************************************** 
    
 
    //**************************************************************************
    //Start Save LR Number details in data base
    //**************************************************************************
    
    $("#saveLRnumber").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poNumber, #nameOfSupplr, #billNumber, #billDate, #billAmount,'+
                       '#ibcNumber, #lrNumber, #lrDate, #nameTransporter');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "./saveLRDataNO.htm",
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
                    $("#LR_number_form")[0].reset();
                    show_record();
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while update data.");
                    $(window).scrollTop(0);
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("Data saved unsuccessfull.");
                    $(window).scrollTop(0);
            },beforeSend:function(){
                return confirm("Are you sure you want to submit ?");
            }
        });
        
        }); 
    //**************************************************************************    
    //END Save LR Number details in data base
    //**************************************************************************
   
    
    //**************************************************************************    
    //START: display LR DETAILS drecord
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getLrDetailsRe.htm",
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
                           "<td>"+ (slno++) +"</td><td>"+v.lrNumber+"</td><td>"+v.poNumber+"</td>"+
                           "<td>"+v.nameOfSupplr+"</td><td>"+v.billNumber+"</td>"+
                           "<td>"+v.billAmount+"</td><td>"+v.ibcNumber+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='lcForwarding' style='margin-right: 3px;' value=" + v.lrEntryID + ">Pdf </button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='edit_button' value=" + v.lrEntryID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(7)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '10%'},
                        {title: "Lr No", width: '14%'},
                        {title: "PO No", width: '12%'},
                        {title: "Supplier Name", width: '16%'},
                        {title: "Bill No", width: '12%'},
                        {title: "Bill Amount", width: '16%'},
                        {title: "Ibc No", width: '10%'},
                        {title: "Buttons", width: '12%'},
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
                    $("#LR_number_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getLrDetailsReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({LrEntry_id: this.value}),
                        success: function (htmlH) {
                            //alert(htmlH);
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {
                                //alert(v.sectionCode);
                                $("#lrEntryID").val(v.lrEntryID);
                                $("#poNumber").val(v.poNumber);
                                $("#poNumber").selectpicker("refresh");
                                $("#nameOfSupplr").val(v.nameOfSupplr);
                                $("#nameOfSupplr").selectpicker("refresh");
                                $("#billNumber").val(v.billNumber);
                                $("#billDate").val(v.billDate);
                                $("#billAmount").val(v.billAmount);
                                $("#ibcNumber").val(v.ibcNumber);
                                $("#lrNumber").val(v.lrNumber);
                                $("#lrDate").val(v.lrDate);
                                $("#nameTransporter").val(v.nameTransporter);
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveLRnumber").css("display", "none");
                            $(window).scrollTop(0);

                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
                
                $("#basic-usage").on('click', '#lcForwarding', function () {
                    var lcid = this.value;
                   
                    window.open('LcForwardingMasPdf.htm?lcforid=' + lcid);
                });

            }
              
	}); 
     }
     
    //**************************************************************************    
    //END display LR DETAILS  record
    //**************************************************************************
    
    //**************************************************************************
    //START: LR DETAILS EDIT
    //**************************************************************************

    $("#cancelLRnumber").click(function () {
        close_message();
        $("#LR_number_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveLRnumber").css("display", "block");
    });

    $("#updateLRnumber").click(function () {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#lrEntryID, #poNumber, #nameOfSupplr, #billNumber, #billDate, #billAmount, #ibcNumber, #lrNumber, #lrDate, #nameTransporter');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "./updateLrDetails.htm",
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
                    $("#LR_number_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveLRnumber").css("display", "block");
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That 'Lr Number' or 'Name Of The Supplier' already exist, Please try with different Name or Number.");
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
    //END: LR DETAILS EDIT
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