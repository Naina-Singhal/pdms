/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#masterul").css("display", "block");
    $("#enquirydjhfhf").addClass("active");
//hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    show_record();    
    //**************************************************************************
    //Start Save ENQUIRY  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#fileNo, #vendorName, #enquireDate, #dueDate, #prepareDate,'+
                       '#openDate, #ifscCode');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveEnquiryForm.htm",
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
                    $("#Enquire_Form")[0].reset();
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
    //END Save ENQUIRY  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display ENQUIRY record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getEnquiryRecord.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
        success: function(enquiry){            
            var result = $.parseJSON(enquiry);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th> <th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.fileNo+"</td><td>"+v.vendorName+"</td>"+
                           "<td>"+v.enquireDate+"</td>"+
                           "<td>"+v.dueDate+"</td><td>"+v.prepareDate+"</td><td>"+v.ifscCode+"</td>"+                           
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.enquiryId + ">Edit </button></td> ";
                                            
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
                        {title: "File No", width: '14%'},
                        {title: "Vendor Name", width: '14%'},
                        {title: "Enquire Date", width: '10%'},
                        {title: "Due Date", width: '14%'},
                        {title: "Prepare Date", width: '14%'},
                        {title: "Ifsc Code", width: '16%'},                        
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
                    $("#Enquire_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getEnquiryReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({enquiry_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#enquiryId").val(v.enquiryId);
                                $("#fileNo").val(v.fileNo);
                                $("#vendorName").val(v.vendorName);
                                $("#enquireDate").val(v.enquireDate);
                                $("#dueDate").val(v.dueDate);
                                $("#prepareDate").val(v.prepareDate);
                                $("#openDate").val(v.openDate);
                                $("#ifscCode").val(v.ifscCode);                                
                            });

                            $("#updateDiv").css("display", "block");
                            $("#saveEnquiry").css("display", "none");
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
    //END: ENQUIRY  record 
    //**************************************************************************
    
    
    $('#saveEnquiry').on('click', function() {        
        sub_mit();
    });
    
    $('#updateEnquiry').on('click', function() {        
        update_Enquiry();
    });
    //**************************************************************************    
    //START: ENQUIRY  UPDATE 
    //**************************************************************************
    
    $("#cancelEnquiry").click(function () {
        close_message();
        $("#Enquire_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveEnquiry").css("display", "block");
    });
    
    
    function update_Enquiry() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#enquiryId, #fileNo, #vendorName, #enquireDate, #dueDate,'+ 
                       '#prepareDate, #openDate, #ifscCode');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateEnquiryMaster.htm",
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
                    $("#Enquire_Form")[0].reset();
                    $("#updateDiv").css("display", "none");
                    $("#saveEnquiry").css("display", "block");
                    //generateIbcNo();
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
    //END: ENQUIRY UPDATE
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