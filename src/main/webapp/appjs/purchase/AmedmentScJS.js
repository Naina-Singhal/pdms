/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#purcmenu").css("display", "block");
    $("#amdnescrneen").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    

    
    //**************************************************************************
    //***START; FETCH PO NUMBERS BY FILE NUMBERS********************************
    //**************************************************************************
        $("#fileNumber").keyup(function () {
        $("#errorFileNumber").empty();
        $("#errorpoNumber").empty();
        $('#vendorName').val('0');    
        $('#bgDate').val('0');
        $("#bgNumber").empty();
        $("#errorBgNumber").empty();
        var fiNo = parseInt(this.value);
        if (fiNo <= 0 || isNaN(fiNo) === true) {
            $("#errorFileNumber").append("Please enter File Number. "); 
            $('#bgDate').val('');
            $("#bgNumber").empty();
        } else {

            $.ajax({
                url: "getPoEntryDeByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({filNumber: fiNo}),
                success: function (response) {
                    var res = $.parseJSON(response);
                    if ($.isEmptyObject(res)) {
                        $("#errorFileNumber").append("This number has no data. ");
                        makeEmptyFunction();                    
                    } else {
                        $("#poNumber").empty();
                        $("#poNumber").append("<option value='null'>select</option>");
                        $.each(res, function (k, v) {
                            $("#poNumber").append("<option value=" + v.poNumber + ">" + v.poNumber + "</option>");
                        });

                        
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }, beforeSend: function () {

                }, complete: function (data) {
                    $('.selectpicker').selectpicker('refresh');
                }
            });
        }
    });
    
    //**************************************************************************
    //***END; FETCH PO NUMBERS BY FILE NUMBERS********************************
    //**************************************************************************

    //**************************************************************************
    //***START; FETCH VENDOR DETAILS BY PO NUMBERS******************************
    //**************************************************************************
    
    $("#poNumber").on('change', function(){
        $("#errorpoNumber").empty();         
        $("#vendorName option").remove();        
        var PoNumber = this.value;        
        if(PoNumber === 'null' || PoNumber === ''){
            $("#errorpoNumber").append("Please select PO number. "); 
            $('#vendorName').val('0'); 
            $('#bgDate').val(''); 
            $("#bgNumber").empty();
        }else{
                      
            $.ajax({
            url: "getPoReleaseDeByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PONumber: PoNumber}),
            success: function (porelese) {
                //alert(porelese);
                var result = $.parseJSON(porelese);
                if($.isEmptyObject(result)){
                    $("#errorpoNumber").append("This number has no data. ");
                    $('#vendorName').val('0'); 
                }else{
                $.each(result, function (k, v) {                     
                    $.ajax({
                        url: "getVendorNameByCode.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({venCode: v.supplier}),
                        success: function (res) {                             
                            var res = $.parseJSON(res);
                            $.each(res, function (k, w) {                                
                                $("#vendorName").append("<option value='" + w.vendorCode + "' selected>"+
                                                    "(" + w.vendorCode + ")" + w.vendorName + "</option>");
                           });
                                                         
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }
                    });
                    // Section name by code                    
                                       
                });
            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {

            }
        });
        
        //  get bank guarantee data
            $.ajax({
                url: "getBankGuaranteeDeByPo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: PoNumber}),
                success: function (guarantee) {
                    //alert(guarantee);
                    var result = $.parseJSON(guarantee);
                    $("#bgNumber").empty();
                    $("#errorBgNumber").empty();
                    if ($.isEmptyObject(result)) {
                        $("#errorBgNumber").append("This number has no data");
                    } else {
                        $("#bgNumber").append("<option value='0'>select</option>");
                        $.each(result, function (k, v) {
                            $("#bgNumber").append("<option value='" + v.bgNumber + "'>" + v.bgNumber + "</option>");
                            $("#bgNumber").selectpicker("refresh");
                        });
                        $("#bgNumber").on('change', function () {
                            var bg_no = parseInt(this.value);
                            $.each(result, function (k, u) {
                                if (bg_no === parseInt(u.bgNumber)) {                                    
                                    $("#bgDate").val(u.bgDate);
                                    $("#amount").val(u.amount);
                                    $("#expireDate").val(u.expireDate);
                                }
                            });
                        });
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }
            });
            
            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: PoNumber}),
                success: function (secur) {                    
                    var res = $.parseJSON(secur);
                    $("#BgClause").val('');
                    $("#errorBgClause").empty();
                    if ($.isEmptyObject(res)) {
                        $("#errorBgClause").append("This number has no data");
                    } else {
                        $.each(res, function (k, w) {
                            $("#BgClause").val(w.securityDeposit);
                        });                        
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }
            });
        
        
        }   //end else of po no on change
    });
    
    //**************************************************************************
    //***END; FETCH VENDOR DETAILS BY PO NUMBERS********************************
    //**************************************************************************
    
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#fileNumber, #poNumber, #vendorName, #vendorAddress,'+
                       '#BreifDescription, #BgClause, #reference, #date, #annexure,'+
                       '#code, #temp, #bgNumber, #bgDate, #amount,'+
                       '#expireDate, #forOne, #deliveryPeriod, #otherAmendments');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveAmendment.htm",
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
                    $("#amendment_Form")[0].reset();
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
        url: "getAmendmentRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(amed){            
            var result = $.parseJSON(amed);
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
                           "<td>"+v.fileNumber+"</td><td>"+v.poNumber+"</td>"+  
                           "<td>"+v.amount+"</td><td>"+v.expireDate+"</td>"+
                           "<td>"+v.deliveryPeriod+"</td><td>"+v.BreifDescription+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.amendmentId + ">Edit </button></td> ";
                                            
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
                        {title: "File NO", width: '13%'},
                        {title: "PO No", width: '13%'},
                        {title: "Amount", width: '13%'},
                        {title: "Expire Date", width: '13%'},
                        {title: "Delivery Period", width: '13%'},
                        {title: "Description", width: '13%'},
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
                    $("#amendment_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getAmendmentReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({Amend_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#amendmentId").val(v.amendmentId);
                                $("#fileNumber").val(v.fileNumber);
                                $("#poNumber").val(v.poNumber);  
                                $("#vendorName").val(v.vendorName); 
                                $("#vendorAddress").val(v.vendorAddress); 
                                $("#BreifDescription").val(v.BreifDescription); 
                                $("#BgClause").val(v.BgClause); 
                                $("#reference").val(v.reference); 
                                
                                $("#date").val(v.date);
                                $("#annexure").val(v.annexure);
                                $("#code").val(v.code);  
                                $("#temp").val(v.temp); 
                                $("#bgNumber").val(v.bgNumber); 
                                $("#bgDate").val(v.bgDate); 
                                $("#amount").val(v.amount); 
                                $("#expireDate").val(v.expireDate);
                                
                                $("#forOne").val(v.forOne);
                                $("#deliveryPeriod").val(v.deliveryPeriod);
                                $("#otherAmendments").val(v.otherAmendments); 
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
        $("#amendment_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#amendmentId, #fileNumber, #poNumber, #vendorName, #vendorAddress,'+
                       '#BreifDescription, #BgClause, #reference, #date, #annexure,'+
                       '#code, #temp, #bgNumber, #bgDate, #amount,'+
                       '#expireDate, #forOne, #deliveryPeriod, #otherAmendments');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateAmendmentDetails.htm",
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
                    $("#amendment_Form")[0].reset();
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
        $("#amendment_Form")[0].reset();
        close_message();
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

