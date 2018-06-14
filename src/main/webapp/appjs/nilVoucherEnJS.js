/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openNilVourRe() {

    window.open("./nilVoucherEnRe.htm", "_parent");
}
$(document).ready(function () {
    
    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#nilvoucupd ").addClass("active");

    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

  
    //**************************************************************************
    //***START: CALCULATION FOR BALANCE DUE
    //**************************************************************************
    
        $("#balanceDue").keyup(function (){
            var amtAdmitted = parseFloat($("#amtAdmitted").val());
            var amountPaid = parseFloat($("#amountPaid").val());
            var fina = (amtAdmitted - amountPaid).toFixed(2);
            $("#balanceDue").val(fina);
        });
        $("#amountPaid").keyup(function (){
            var amtAdmitted = parseFloat($("#amtAdmitted").val());
            var amountPaid = parseFloat($("#amountPaid").val());
            $("#balanceDue").val((amtAdmitted - amountPaid).toFixed(2));
        });
        $("#amtAdmitted").keyup(function (){
            var amtAdmitted = parseFloat($("#amtAdmitted").val());
            var amountPaid = parseFloat($("#amountPaid").val());
            $("#balanceDue").val((amtAdmitted - amountPaid).toFixed(2));
        });
    //**************************************************************************
    //***END: CALCULATION FOR BALANCE DUE
    //**************************************************************************
    
    //**************************************************************************
    //***START: FETCHING PO DETAILS FROM VOUCHER DATA ENTRY AND BILL DATA
    //**************************************************************************

    $("#poNumber").keyup(function () {
        var PONumber = parseInt(this.value);

        $("#errorPoNum").empty();
        if (PONumber <= 0 || isNaN(PONumber) === true) {
            $("#errorPoNum").append("Please enter valid PO Number. ");
            $("#pprNumber option").remove();
        } else {
            $.ajax({
                url: "getVouDaEnPoDeById.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoVouDaEn_id: PONumber}),
                success: function (poDe) {

                    var poDeRe = $.parseJSON(poDe);
                    $("#pprNumber").empty();
                    if ($.isEmptyObject(poDeRe)) {
                        $("#errorPoNum").append("This number has no data. ");
                        $("#pprNumber option").empty();
                    } else {
                        $("#pprNumber").append("<option value='0'>select</option>");
                        $.each(poDeRe, function (k, v) {
                            $("#pprNumber").append("<option value='" + v.pprNo + "'>" + v.pprNo + "</option>");
                            $("#pprNumber").selectpicker("refresh");
                        });
                        $.ajax({
                            url: "getBillEnReByPoNum.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({PoNum: PONumber}),
                            success: function (data) {
                                var result = $.parseJSON(data);
                                $("#billNumber").empty();
                                $("#errorBillNo").empty();
                                if ($.isEmptyObject(result)) {
                                    $("#errorBillNo").append("This number has no data");
                                } else {
                                    $("#billNumber").append("<option value='0'>select</option>");
                                    $.each(result, function (k, v) {
                                        $("#billNumber").append("<option value='" + v.billNo + "'>" + v.billNo + "</option>");
                                        $("#billNumber").selectpicker("refresh");
                                    });
                                    $("#billNumber").on('change', function () {
                                        var bill_no = parseInt(this.value);
                                        $.each(result, function (k, u) {
                                            if (bill_no === parseInt(u.billNo)) {
                                                $("#billDate").val(u.billDate);
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
                }, complete: function (data) {

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + textStatus);
                }
            });
        }
    });
    //**************************************************************************
    //***END: FETCHING PO DETAILS FROM VOUCHER DATA ENTRY AND BILL DATA
    //**************************************************************************
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save nil voucher  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#pprNumber, #posr,#poNumber,#amountPaid, #dateOfPmt,' +                
                ' #billNumber, #billDate, #rvNumber, #rvDate, #amtAdmitted, #balanceDue');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveNilVoucherDaEn.htm",
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
                    $("#NilVoucherForm")[0].reset();    
                    $("#pprNumber").selectpicker("refresh");
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
    //END Save DD Number  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display DD Number record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getNilVoucherRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(nil){            
            var result = $.parseJSON(nil);
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
                           "<td>"+v.pprNumber+"</td><td>"+v.posr+"</td>"+  
                           "<td>"+v.poNumber+"</td><td>"+v.amountPaid+"</td>"+
                           "<td>"+v.billNumber+"</td><td>"+v.balanceDue+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.nilVouId + ">Edit </button></td> ";
                                            
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
                        {title: "POSR", width: '13%'},
                        {title: "PO NO", width: '13%'},
                        {title: "Amount Paid", width: '13%'},
                        {title: "Bill No", width: '13%'},
                        {title: "Balance Due", width: '13%'},
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
                    $("#NilVoucherForm")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getNilVoucherReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({NilVou_id: this.value}),
                        success: function (data) {                              
                            var result = $.parseJSON(data);
                            $.each(result, function (k, v) {                                
                                $("#nilVouId").val(v.nilVouId);
                                $("#pprNumber").val(v.pprNumber);
                                $("#posr").val(v.posr);  
                                $("#poNumber").val(v.poNumber); 
                                $("#amountPaid").val(v.amountPaid); 
                                $("#dateOfPmt").val(v.dateOfPmt); 
                                $("#billNumber").val(v.billNumber); 
                                $("#billDate").val(v.billDate); 
                                
                                $("#rvNumber").val(v.rvNumber); 
                                $("#rvDate").val(v.rvDate); 
                                $("#amtAdmitted").val(v.amtAdmitted); 
                                $("#balanceDue").val(v.balanceDue); 
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#pprNumber").selectpicker("refresh");
                            $("#billNumber").selectpicker("refresh");
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
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
        $("#NilVoucherForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $itemsU = $('#nilVouId, #pprNumber, #posr,#poNumber,#amountPaid, #dateOfPmt,' +                
                ' #billNumber, #billDate, #rvNumber, #rvDate, #amtAdmitted, #balanceDue');

        var obj = {};
        $itemsU.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateNilVoucherDe.htm",
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
                    $("#NilVoucherForm")[0].reset();
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
        $("#NilVoucherForm")[0].reset();
        $("#pprNumber").selectpicker("refresh");
        $("#billNumber").selectpicker("refresh");
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

    
}); 
