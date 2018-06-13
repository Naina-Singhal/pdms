/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openAdvancePayRe() {

    window.open("./advancePaymentRe.htm", "_parent");
}
$(document).ready(function () {
    
    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#advvoucupd ").addClass("active");
    

    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    
    //******************************************************************
    //Getting vendor details from vendor_details table
    //******************************************************************        
           
    //**************************************************************************
    //***START: GET VOUCHER DATA ENTRY
    //**************************************************************************
    function getVoucherEnDa() {        
        $.ajax({
            url: "getVoucherEnDa.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (data) {                  
                var result = $.parseJSON(data);                
                $.each(result, function (k, v) {
                   // $("#pprNo").append("<option value='" + v.pprNo + "'>" + v.pprNo + "</option>"); 
                });
            }, complete: function (data) {
            },error: function (jqXHR, textStatus, errorThrown) {                
               alert("Error :"+textStatus);
            }
        });
    }
    //**************************************************************************
    //***END: GET VOUCHER DATA ENTRY
    //**************************************************************************
    
    
    //**************************************************************************
    //***START: FETCHING PO DETAILS FROM VOUCHER DATA ENTRY 
    //**************************************************************************

    $("#poNumber").keyup(function () {
        var PONumber = parseInt(this.value);

        $("#errorPONUmb").empty();
        if (PONumber <= 0 || isNaN(PONumber) === true) {
            $("#errorPONUmb").append("Please enter valid PO Number. ");
            $("#pprNo option").remove();
            $("#vname option").remove();
        } else {
            $.ajax({
                url: "getVouDaEnPoDeById.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoVouDaEn_id: PONumber}),
                success: function (poDe) {                    
                    var poDeRe = $.parseJSON(poDe);
                    $("#pprNo").empty();
                    $("#vname").empty();
                    if ($.isEmptyObject(poDeRe)) {
                        $("#errorPONUmb").append("This number has no data. ");
                        $("#pprNo option").empty();
                    } else {
                        $("#pprNo").append("<option value='0'>select</option>");
                        $.each(poDeRe, function (k, v) {
                            $("#pprNo").append("<option value='" + v.pprNo + "'>" + v.pprNo + "</option>");
                            $("#pprNo").selectpicker("refresh");
                            $.ajax({
                                url: "getVendorNameByCode.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({venCode: v.nameOfSupplr}),
                                success: function (res) {
                                    var res = $.parseJSON(res);
                                    $.each(res, function (k, w) {
                                        $("#vname").append("<option value='" + w.vendorCode + "' selected>(" + w.vendorCode + ")" + w.vendorName + "</option>");
                                        $("#vname").selectpicker("refresh");
                                    });

                                }, error: function (jqXHR, textStatus, errorThrown) {
                                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                                }
                            });
                            
                            
                        });
                        
                    }
                }, complete: function (data) {

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + textStatus);
                }
            });
        }
    });
    //**************************************************************************
    //***END: FETCHING PO DETAILS FROM VOUCHER DATA ENTRY
    //**************************************************************************
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save advance pay  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#pprNo, #poNumber,#vname,#dop, #amountPaid,' +
                       '#advanceAdj, #balancePaid, #balanceDue, #totalDue, #clDate,' +
                       '#qtyPaid, #recovery, #remarks, #posr');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveAdvancePayment.htm",
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify([obj]),
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#AdvanceForm")[0].reset();  
                    $("#pprNo").selectpicker("refresh");
                    $("#vname").selectpicker("refresh");
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                }else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
                    $(window).scrollTop(0);
            },beforeSend:function(){
                return confirm("Are you sure you want to save ?");
            }
        });
        
        }
    //**************************************************************************    
    //END Save advance  pay  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display advance pay record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getAdvancePayRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(advance){            
            var result = $.parseJSON(advance);
            if($.isEmptyObject(result)){
                $("#basic-usage").remove();
                $('#basic-usage').load('ajax.html#basic-usage');
                alert("There is no records");
            }else{
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.pprNo+"</td><td>"+v.poNumber+"</td>"+  
                           "<td>"+v.amountPaid+"</td><td>"+v.balancePaid+"</td>"+
                           "<td>"+v.balanceDue+"</td><td>"+v.remarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.avancePayId + ">Edit </button></td> ";
                                            
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
                        {title: "SL No", width: '8%'},
                        {title: "PPR NO", width: '13%'},
                        {title: "PO NO", width: '13%'},
                        {title: "Amount Paid", width: '13%'},
                        {title: "Balance Paid", width: '13%'},
                        {title: "Balance Due", width: '13%'},
                        {title: "Remarks", width: '13%'},
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
            
        }
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#AdvanceForm")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getAdvancePayReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({advance_id: this.value}),
                        success: function (adda) {                              
                            var result = $.parseJSON(adda);
                            $.each(result, function (k, v) {                                
                                $("#avancePayId").val(v.avancePayId);
                                $("#pprNo").val(v.pprNo);
                                $("#poNumber").val(v.poNumber);  
                                $("#vname").val(v.vname); 
                                $("#dop").val(v.dop); 
                                $("#amountPaid").val(v.amountPaid); 
                                $("#advanceAdj").val(v.advanceAdj); 
                                $("#balancePaid").val(v.balancePaid); 
                                
                                $("#balanceDue").val(v.balanceDue);
                                $("#totalDue").val(v.totalDue);
                                $("#clDate").val(v.clDate);  
                                $("#qtyPaid").val(v.qtyPaid); 
                                $("#recovery").val(v.recovery); 
                                $("#remarks").val(v.remarks); 
                                $("#posr").val(v.posr);                                
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $("#pprNo").selectpicker("refresh");
                            $("#vname").selectpicker("refresh");
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
    //END: DD Number  record 
    //**************************************************************************
    
    
    $('#saveDdNumber').on('click', function() {        
        sub_mit();
    });
    
    $('#updateDdNumber').on('click', function() {        
        update_dispatch();
    });
    //**************************************************************************    
    //START: DD Number  UPDATE 
    //**************************************************************************
    
    $("#cancelDdNumber").click(function () {
        close_message();
            $("#form_record").show();
            $("#form_page").hide();
        $("#AdvanceForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $itemsU = $('#avancePayId, #pprNo, #poNumber,#vname,#dop, #amountPaid,' +
                       '#advanceAdj, #balancePaid, #balanceDue, #totalDue, #clDate,' +
                       '#qtyPaid, #recovery, #remarks, #posr');

        var obj = {};
        $itemsU.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateAdvancePay.htm",
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
                    $("#AdvanceForm")[0].reset();
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();
                    $("#updateDiv").css("display", "none");
                    $("#saveDdNumber").css("display", "block");                    
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
                //$("#basic-usage").remove();
            }, complete: function (data) {
                show_record();
                //$('#basic-usage').load('ajax.html#basic-usage');
            }
        });


    }   // end function
    //**************************************************************************
    //END: DD Number UPDATE
    //**************************************************************************
    $("#showRecord").on('click', function (){       
        $("#form_record").show();
        $("#form_page").hide();
        show_record();
        close_message();
    });
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        $("#AdvanceForm")[0].reset();
        $("#pprNo").selectpicker("refresh");
        $("#vname").selectpicker("refresh");
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
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

}); //End Document
