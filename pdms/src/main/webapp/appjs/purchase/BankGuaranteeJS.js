/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#purcmenu").css("display", "block");
    $("#bankgrantscr").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    
    generateSlno();
    function generateSlno() {
        $("#Slno").val('');
        $.ajax({
            url: "generateSlNoIncre.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (data) {
                var slno = parseInt(data);
                $("#Slno").val(slno);
                $("#Slno").css({"background-color": "#dedcdc"}).prop('disabled', true);
            }, complete: function (data) {
                
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at : generate Sl No" +errorThrown);
            }
        });
    }
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#Slno, #tempone, #poNumber, #posr, #modeBgDd,'+
                       '#vendorName, #bank, #tempTwo, #bgNumber,#bgDate,'+ 
                       '#amount, #expireDate, #gracePeriod, #retiredDate,'+ 
                       '#entryDate, #remarks, #status');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveBankGuarantee.htm",
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
                    $("#BankGuarantee_Form")[0].reset();
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
        url: "getBankGuaranteeRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(guaran){            
            var result = $.parseJSON(guaran);
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
                           "<td>"+v.poNumber+"</td><td>"+v.posr+"</td>"+  
                           "<td>"+v.bank+"</td><td>"+v.bgNumber+"</td>"+
                           "<td>"+v.amount+"</td><td>"+v.remarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='geneBankGauaPdf' style='margin-right: 3px;' value=" + v.bankGuaranteeId + ">Pdf</button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.bankGuaranteeId + ">Edit </button></td> ";
                                            
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
                        {title: "PO Number", width: '13%'},
                        {title: "POSR", width: '13%'},
                        {title: "Bank", width: '13%'},
                        {title: "Bg Number", width: '13%'},
                        {title: "Amount", width: '13%'},
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
                    $("#BankGuarantee_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getBankGuaranteeReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({Guarantee_id: this.value}),
                        success: function (html) {                              
                            var result = $.parseJSON(html);
                            $.each(result, function (k, v) {                                
                                $("#bankGuaranteeId").val(v.bankGuaranteeId);
                                $("#Slno").val(v.Slno);
                                $("#tempone").val(v.tempone);  
                                $("#poNumber").val(v.poNumber); 
                                $("#posr").val(v.posr); 
                                $("#modeBgDd").val(v.modeBgDd); 
                                $("#vendorName").val(v.vendorName); 
                                $("#bank").val(v.bank); 
                                
                                $("#tempTwo").val(v.tempTwo);
                                $("#bgNumber").val(v.bgNumber);
                                $("#bgDate").val(v.bgDate);  
                                $("#amount").val(v.amount); 
                                $("#expireDate").val(v.expireDate); 
                                $("#gracePeriod").val(v.gracePeriod); 
                                $("#retiredDate").val(v.retiredDate); 
                                $("#entryDate").val(v.entryDate);
                                
                                $("#remarks").val(v.remarks); 
                                $("#status").val(v.status);
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
   
                $("#basic-usage").on('click', '#geneBankGauaPdf', function (){
                    
                    window.open('bankGuaranteePdf.htm?eindi='+this.value);
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
        $("#BankGuarantee_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#bankGuaranteeId, #Slno, #tempone, #poNumber, #posr, #modeBgDd,'+
                       '#vendorName, #bank, #tempTwo, #bgNumber,#bgDate,'+ 
                       '#amount, #expireDate, #gracePeriod, #retiredDate,'+ 
                       '#entryDate, #remarks, #status');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateBankGuarantee.htm",
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
                    $("#BankGuarantee_Form")[0].reset();
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
        $("#BankGuarantee_Form")[0].reset();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        generateSlno();
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

