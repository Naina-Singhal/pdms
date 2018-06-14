/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () { 
    show_record();
    appenVendor();
    $("#navigation li a").removeClass("active");
    $("#masterul").css("display", "block");
    $("#lrdeenstors").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide(); 
    
    //******************************************************************
    //Getting vendor details from vendor_details table
    //******************************************************************   
    function appenVendor() {
        $.ajax({
            url: "getVendorName.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (details) {               
                var result = $.parseJSON(details);
                $("#vendorName").empty();
                $("#vendorName").append("<option value='null'>Select</option>");
                $.each(result, function (k, v) {
                    $("#vendorName").append("<option value='" + v.vendorCode + "'>(" + v.vendorCode + ")" + v.vendorName + "</option>");
                });
                $("#vendorName").selectpicker("refresh"); 
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //ENS: Getting vendor details from vendor_details table
    //******************************************************************  
    
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
    //START: SELECT  VENDOR 
    //****************************************************************** 
    $("#poNumber").on('change', function () {
        var ponumber = this.value;        
        $("#errorpoNumber").empty();
        if (ponumber === 'null' || ponumber === '') {
            $("#errorpoNumber").append("Please select PO number. ");

        } else {
            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: ponumber}),
                success: function (htmlH) {
                    //alert(htmlH);
                    var result = $.parseJSON(htmlH);
                    if ($.isEmptyObject(result)) {
                        $("#errorpoNumber").append("This number has no vendor data. ");
                    } else {

                        $.each(result, function (k, v) {
                           $("#vendorName").val(v.venderName); 
                        });
                        $("#vendorName").selectpicker("refresh"); 
                    }

                }, complete: function (data) {
                }
            });
        }

    });

    
 
    
    //******************************************************************
    //END: SELECT  VENDOR 
    //****************************************************************** 
    
    //**************************************************************************
    //Start Save LR Number details in data base
    //**************************************************************************
    
    $("#saveLRnumber").click(function () {
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poNumber, #vendorName, #challanNo, #rrAndLrNumber, #rrAndLrDate, #numOfPackage,'+
                       '#freight, #fromPlace, #toPlace, #nameTransporter');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveLRStoresData.htm",
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
                    $("#LR_stores_form")[0].reset();
                    show_record();
                    $("#poNumber").selectpicker("refresh");
                    $("#vendorName").selectpicker("refresh");
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
        url: "getLrStoresDetailsRe.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(stores){           
            var result = $.parseJSON(stores);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th> <th></th><th></th><th></th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td><td>"+v.poNumber+"</td><td>"+v.challanNo+"</td>"+
                           "<td>"+v.rrAndLrNumber+"</td><td>"+v.nameTransporter+"</td>"+
                           "<td>"+v.fromPlace+"</td><td>"+v.toPlace+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='edit_button' value=" + v.lrEnDeStoresId + ">Edit </button></td> ";
                                            
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
                        {title: "PO No", width: '14%'},
                        {title: "Challan No", width: '12%'},
                        {title: "RR / LR NO", width: '16%'},
                        {title: "name Transporter", width: '12%'},
                        {title: "From Place", width: '16%'},
                        {title: "To Place", width: '10%'},
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
                    $("#LR_stores_form")[0].reset();
                    
                    close_message();
                    $.ajax({
                        url: "getLrStoresDetailsReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({LrStores_id: this.value}),
                        success: function (store) {
                            //alert(htmlH);
                            var result = $.parseJSON(store);
                            $.each(result, function (k, v) {
                                //alert(v.sectionCode);
                                $("#lrEnDeStoresId").val(v.lrEnDeStoresId);
                                $("#poNumber").val(v.poNumber);
                                $("#vendorName").val(v.vendorName);
                                $("#vendorName").selectpicker("refresh"); 
                                $("#challanNo").val(v.challanNo);
                                $("#rrAndLrNumber").val(v.rrAndLrNumber);
                                $("#rrAndLrDate").val(v.rrAndLrDate);
                                $("#numOfPackage").val(v.numOfPackage);
                                $("#freight").val(v.freight);
                                $("#fromPlace").val(v.fromPlace);
                                $("#toPlace").val(v.toPlace);
                                $("#nameTransporter").val(v.nameTransporter);
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveLRnumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#vendorName").selectpicker("refresh"); 
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
    //END display LR DETAILS  record
    //**************************************************************************
    
    //**************************************************************************
    //START: LR DETAILS EDIT
    //**************************************************************************

    $("#cancelLRnumber").click(function () {
        close_message();
        $("#LR_stores_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveLRnumber").css("display", "block");
        $("#vendorName").selectpicker("refresh");
    });

    $("#updateLRnumber").click(function () {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#lrEnDeStoresId, #poNumber, #vendorName, #challanNo,'+
                '#rrAndLrNumber, #rrAndLrDate, #numOfPackage, #freight, #fromPlace, #toPlace, #nameTransporter');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateLrstoresDetails.htm",
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
                    $("#LR_stores_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveLRnumber").css("display", "block");
                    $("#vendorName").selectpicker("refresh");
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

        
}); //End document