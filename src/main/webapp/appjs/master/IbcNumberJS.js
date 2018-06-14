/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    show_record();
    //generateIbcNo();
    $("#navigation li a").removeClass("active");
      $("#masterul").css("display","block");
      $("#usribcno ").addClass("active");
      
      
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
    //START: data fetching
    //****************************************************************** 
    $("#poNumber").on('change', function () {
        var ponumber = this.value;        
        $("#errorpoNumber").empty();
        if (ponumber === 'null' || ponumber === '') {
            $("#errorpoNumber").append("Please select PO number. ");

        } else {
            $.ajax({
                url: "getBillEnReByPoNum.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoNum: ponumber}),
                success: function (htmlH) {
                    //alert(htmlH);
                    var result = $.parseJSON(htmlH);
                    if ($.isEmptyObject(result)) {
                        $("#errorpoNumber").append("This number has no data. ");
                    } else {

                        $.each(result, function (k, v) {
                           $("#nameOfSupplr").val(v.supplierName); 
                           $("#billNumber").val(v.billNo); 
                           $("#billDate").val(v.billDate); 
                           $("#billAmount").val(v.billAmt); 
                        });
                        $("#nameOfSupplr").selectpicker("refresh"); 
                    }

                }, complete: function (data) {
                }
            });
        }

    });
    //******************************************************************
    //END: data fetching 
    //****************************************************************** 
    
    
    //**************************************************************************
    //Start Save IBC Number details in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poNumber, #nameOfSupplr, #billNumber, #billDate, #billAmount,'+
                       '#ibcNumber, #ibcDate, #ibcAmount, #ibcBank');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "./saveIbcNumber.htm",
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
                    $("#ibc_number_form")[0].reset();
                    show_record();
                    //generateIbcNo();
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
    //END Save IBC Number details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START display IBC NUMBER record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getIBCnumberRe.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
        success: function(IBC){           
            var result = $.parseJSON(IBC);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th> <th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.ibcNumber+"</td><td>"+v.ibcAmount+"</td>"+
                           "<td>"+v.poNumber+"</td>"+
                           "<td>"+v.nameOfSupplr+"</td><td>"+v.billNumber+"</td><td>"+v.billAmount+"</td>"+                           
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.ibcID + ">Edit </button></td> ";
                                            
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
                        {title: "Ibc No", width: '14%'},
                        {title: "Amount", width: '14%'},
                        {title: "Po No", width: '10%'},
                        {title: "Suppier", width: '14%'},
                        {title: "Bill No", width: '14%'},
                        {title: "Amount", width: '16%'},                        
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
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#ibc_number_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getIbcNumberReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({ibcNo_id: this.value}),
                        success: function (htmlH) {                            
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#ibcID").val(v.ibcID);
                                $("#poNumber").val(v.poNumber);
                                $("#poNumber").selectpicker("refresh"); 
                                $("#nameOfSupplr").val(v.nameOfSupplr);
                                $("#nameOfSupplr").selectpicker("refresh"); 
                                $("#billNumber").val(v.billNumber);
                                $("#billDate").val(v.billDate);
                                $("#billAmount").val(v.billAmount);
                                $("#ibcNumber").val(v.ibcNumber);
                                $("#ibcDate").val(v.ibcDate);
                                $("#ibcAmount").val(v.ibcAmount);
                                $("#ibcBank").val(v.ibcBank);
                                $("#ibcBank").selectpicker("refresh"); 
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveIBCnumber").css("display", "none");
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
    //END: IBE NUMBER  record 
    //**************************************************************************
    
    
    
    //**************************************************************************
    //START: IBC NUMBER EDIT
    //**************************************************************************

    $("#cancelIbc").click(function () {
        close_message();
        $("#ibc_number_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveIBCnumber").css("display", "block");
    });

    function updateIbc_Number() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#ibcID, #poNumber, #nameOfSupplr, #billNumber, #billDate,'+ 
                       '#billAmount, #ibcNumber, #ibcDate, #ibcAmount, #ibcBank');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "./updateIbcNumber.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) {                
                var data = parseInt(data);
                //alert(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#ibc_number_form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveIBCnumber").css("display", "block");
                    //generateIbcNo();
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                }else if (data === -2) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> IBC number saved with voucher data entry.");
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


    }   // end function
    //**************************************************************************
    //END: IBC NUMBER EDIT
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
    //***START; AUTO INCREEMENT IBC NUMBER**********************************
    //************************************************************************** 
    function generateIbcNo() {         
        $.ajax({
            url: "getIbcNumInc.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (data) {
                var ibcNum = parseInt(data);                
                $("#ibcNumber").val(ibcNum);

            }, complete: function (data) {
            }
        });
    }
     //**************************************************************************
    //***END; AUTO INCREEMENT  FOR IBC NUMBER**********************************
    //************************************************************************** 
    
    
    
    //**************************************************************************
    //***START; FORM VALIDATION FOR IBC NUMBER**********************************
    //************************************************************************** 
    
    $('#ibc_number_form').validate({ // initialize plugin
        // rules & options, 
        
        rules: {
            poNumber: {
                required: true,
                minlength: 1,
                maxlength: 10,
                numaricals: true
                
            },
            nameOfSupplr: {
                select_vendor: true
                
            },
            billNumber: {
                required: true,  
                minlength: 1,
                maxlength: 10,
                numaricals: true                
            },
            billDate:{
                required: true  
            },
            billAmount:{
                required: true,                
                maxlength: 13,
                mynumber: true
            },
            ibcNumber:{
                required: true,
                minlength: 1,
                maxlength: 10,
                numaricals: true 
            },
            ibcDate:{
                required: true
            },
            ibcAmount:{
                required: true,                
                maxlength: 13,
                mynumber: true
            },
            ibcBank:{
                select_option: true
            }
        },
        messages: {
            poNumber:{ 
                required: "Please enter PO number",
                minlength: "Po Number must be at least more than 1",
                maxlength: "PO Number should be below 10 numbers"
            },
            nameOfSupplr:{
               
            },
            billNumber: {
                required: "Please enter Bill number",
                minlength: "Bill Number must be at least more than 1",
                maxlength: "Bill Number should be below 10 numbers"
                
            },
            billDate:{
                required: "Please select Bill Date"
            },
            billAmount:{
                required: "Please enter Bill amount",
                maxlength: "Bill Amount length should be below 13"
            },
            ibcNumber:{
                required: "Please enter IBC number",
                minlength: "IBC Number must be at least more than 1",
                maxlength: "IBC Number should be below 10 numbers"
            },
            ibcDate:{
                required: "Please select IBC Date"
            },
            ibcAmount:{
                required: "Please enter IBC Amount",
                maxlength: "Bill Amount length should be below 13"
            },
            ibcBank:{
                
            }
        },
        submitHandler: function(form) {            
            var hidden_input_val = 0;
            hidden_input_val = parseInt($("#ibcID").val());            
            if(hidden_input_val === '' || hidden_input_val === 0){
                sub_mit();
            }else{
                updateIbc_Number();
            }
            $("#ibcID").val('0');            
            return false;  // blocks regular submit since you have ajax
        }
    });
    
    $('#saveIBCnumber').on('click', function() {        
        $('#ibc_number_form').submit();        
    });
    
    $('#updateIbc').on('click', function() {
        $('#ibc_number_form').submit();        
    });
    
    jQuery.validator.addMethod("mynumber", function (value, element) {
        if (/^(?!0\.00)[1-9]\d{0,10}(,\d{3})*(\.\d\d)?$/.test(value)) {
            return true; 
        } else {
            return false; 
        }
    }, "Please specify the correct Decimal number format");
    
    jQuery.validator.addMethod("vendorName", function (value, element) {
        if (/^([a-zA-Z0-9 _-]+)$/.test(value)) {
            return true; 
        } else {
            return false; 
        }
    }, "Special characters not allowed (accept _-space)");
    
    jQuery.validator.addMethod("numaricals", function (value, element) {
        if (/^([0-9]+)$/.test(value)) {
            return true;
        } else {
            return false; 
        }
    }, "Accept only numbers");
    
    jQuery.validator.addMethod("select_option", function (value, element) {
        if (value === 'null') {
            return false;  
        } else {
            return true; 
        }
    }, "Please select IBC bank");
    
        jQuery.validator.addMethod("select_vendor", function (value, element) {
        if (value === 'null') {
            return false;  
        } else {
            return true; 
        }
    }, "Please select vendor name");
    
    //**************************************************************************
    //***END; FORM VALIDATION FOR IBC NUMBER************************************
    //**************************************************************************
    
    
    
 });    //End Document
