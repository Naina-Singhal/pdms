/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    

    $("#navigation li a").removeClass("active");
    $("#despatchUl").css("display", "block");
    $("#despatentr").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //***********************************************
    //Getting file numbers  table
    //***********************************************
    $.ajax({
        url: "getFileNumbers.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#fileNumber").empty();
            $("#fileNumber").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#fileNumber").append("<option value='" + v.fileNoFrMapp +
                        "'>" + v.fileNoFrMapp + "</option>");
            });
            $("#fileNumber").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    //***********************************************
    //Getting Type data from master table
    //***********************************************
    $.ajax({
        url: "getTypeMaRecord.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#type").empty();
            $("#type").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#type").append("<option value='" + v.code +
                        "'>("+v.code+")" + v.type + "</option>");
            });
            $("#type").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    
    //***********************************************
    //Getting Type receiver from master table
    //***********************************************
    $.ajax({
        url: "getReceiverRecord.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#receiver").empty();
            $("#receiver").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#receiver").append("<option value='" + v.receiverCode +
                        "'>("+v.receiverCode+")" + v.receiverName + "</option>");
            });
            $("#receiver").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#fileNumber, #purpose, #reference, #type, #receiver,'+
                        '#remarks, #reasonFrSend, #sendDate');
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveDispatchEn.htm",
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
                    $("#dispatchEn_form")[0].reset();
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
        url: "getFileMovementRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(file){            
            var result = $.parseJSON(file);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.fileNumber+"</td><td>"+v.purpose+"</td>"+  
                           "<td>"+v.reference+"</td><td>"+v.type+"</td>"+
                           "<td>"+v.receiver+"</td><td>"+v.remarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.dispatchEnId + ">Edit </button></td> ";
                                            
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
                        {title: "Purpose", width: '13%'},
                        {title: "Reference", width: '13%'},
                        {title: "Type", width: '13%'},
                        {title: "Receiver", width: '13%'},
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
            
            
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#dispatchEn_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getFileMovementReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({movenment_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#dispatchEnId").val(v.dispatchEnId);
                                $("#fileNumber").val(v.fileNumber);
                                $("#fileNumber").selectpicker("refresh");
                                $("#purpose").val(v.purpose);  
                                $("#reference").val(v.reference); 
                                $("#type").val(v.type); 
                                $("#type").selectpicker("refresh");
                                $("#receiver").val(v.receiver); 
                                $("#receiver").selectpicker("refresh");
                                $("#remarks").val(v.remarks); 
                                $("#reasonFrSend").val(v.reasonFrSend); 
                                $("#sendDate").val(v.sendDate); 
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
        $("#dispatchEn_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#dispatchEnId,#fileNumber, #purpose, #reference, #type, #receiver,'+
                        '#remarks, #reasonFrSend, #sendDate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateFileMovement.htm",
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
                    $("#dispatchEn_form")[0].reset();
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