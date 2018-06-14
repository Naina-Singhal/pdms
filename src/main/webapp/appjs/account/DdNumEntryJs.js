/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#ddnmnbenny").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    //**************************************************************************
    //START: DATA FETCHING FROM VOUCHER NUMBER DEPEND ON REQ NO
    //**************************************************************************
    $("#reqNumber").keyup(function () {        
        $("#errorReqNo").empty();
        $("#vendorname").empty();
        var requiNo = this.value;
        if(requiNo <= 0){
             $("#errorReqNo").append("Please enter valid number. ");
             makeEmptyFunction();
        }else{
        $.ajax({
            url: "getVouNoDeByReqNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({requiNo: requiNo}),
            success: function (resp) {                               
                var result = $.parseJSON(resp);
                if($.isEmptyObject(result)){
                    $("#errorReqNo").append("There no data. ");
                        makeEmptyFunction();
                }else{
                $.each(result, function (k, v) {                    
                    $.ajax({
                        url: "getVendorNameByCode.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({venCode: v.supplierName}),
                        success: function (res) {                           
                            var res = $.parseJSON(res);
                            $.each(res, function (k, w) {                                
                                $("#vendorname").append("<option value='" + w.vendorCode + "' selected>"+
                                        "(" + w.vendorCode + ")" + w.vendorName + "</option>");
                                $("#vendorname").selectpicker("refresh");
                            });
                            
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }
                    });
                    $("#vouNumber").val(v.voucherNo);
                    $("#voucherDate").val(v.voucherDate);
                });
            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {

            }
        });
    }
    });
    
    function makeEmptyFunction(){
        $("#vouNumber").val('');
        $("#voucherDate").val('');
        $("#vendorname").val("null");
    }
    
    //**************************************************************************
    //END: DATA FETCHING FROM VOUCHER NUMBER DEPEND ON REQ NO
    //**************************************************************************
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#reqNumber, #vendorname, #vouNumber, #voucherDate, #amount, #ddNumber, #ddDate');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "savedDdNumberAc.htm",
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
                    $("#DdNumber_form")[0].reset();
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
        url: "getDdNumberRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(DdNumber){            
            var result = $.parseJSON(DdNumber);
            if($.isEmptyObject(result)){
                $("#basic-usage").remove();
                $('#basic-usage').load('ajax.html#basic-usage');
                alert("There is no records");
            }else{
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.reqNumber+"</td>"+  
                           "<td>"+v.vouNumber+"</td><td>"+v.amount+"</td>"+
                           "<td>"+v.ddNumber+"</td><td>"+v.ddDate+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='geneDdForwardingPDf' style='margin-right: 3px;' value=" + v.ddNumberId + ">D.D Forwarding</button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.ddNumberId + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(6)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "Requisition NO", width: '13%'},                       
                        {title: "Voucher NO", width: '13%'},
                        {title: "Amount", width: '13%'},
                        {title: "DD No", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "Buttons", width: '25%'},
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
                    $("#DdNumber_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getDdNumberReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({ddNo_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#ddNumberId").val(v.ddNumberId);
                                $("#reqNumber").val(v.reqNumber);
                                $("#vendorname").val(v.vendorname);  
                                $("#vouNumber").val(v.vouNumber); 
                                $("#voucherDate").val(v.voucherDate); 
                                $("#amount").val(v.amount); 
                                $("#ddNumber").val(v.ddNumber); 
                                $("#ddDate").val(v.ddDate); 
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);

                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
                
                tableRowHighlighting();
                $("#basic-usage").on('click', '#geneDdForwardingPDf', function (){
                    
                    window.open('ddForwardingPdf.htm?eindi='+this.value);
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
        $("#DdNumber_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#ddNumberId, #reqNumber, #vendorname, #vouNumber, #voucherDate, #amount, #ddNumber, #ddDate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateDdNumberAc.htm",
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
                    $("#DdNumber_form")[0].reset();
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
        $("#DdNumber_form")[0].reset();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************
    function tableRowHighlighting() {
        var table = $('#basic-usage').DataTable();

        $('#basic-usage tbody')
                .on('mouseenter', 'td', function () {
                    var colIdx = table.cell(this).index().column;

                    $(table.cells().nodes()).removeClass('highlight');
                    $(table.column(colIdx).nodes()).addClass('highlight');
                });
    }
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 


    

});

