/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function po_record() {

    window.open("./poEntryDetails.htm", "_parent");
}
$(document).ready(function () {
    
    $("#navigation li a").removeClass("active");
    $("#purcmenu").css("display","block");
    $("#poepoe").addClass("active");
    
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();   
    
    $("#form_record").hide();
    $("#form_page").show();
    
   show_file_no();
   generate_po_number();
    
    
    
        //***********************************************
        //Getting section from section_master table
        //***********************************************
        $.ajax({
        url: "getVendorName.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);
                    $("#venderName").append("<option value='null'>Select</option>");
                    $.each(result, function(k, v) {
                    //alert(v.vendorCode);			
			$("#venderName").append("<option value='"+v.vendorCode+"'>("
                                +v.vendorCode+")"+v.vendorName+"</option>");
                    });	
		$("#venderName").selectpicker("refresh");								
		},complete: function (data) {
		},error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at getVendorName :"+textStatus)
        }
	}); 
        //***********************************************
        //Getting Signatory  details
        //***********************************************
        $.ajax({
        url: "getSignatoryDetails.htm",
        type: "POST",
	cache: false,
	async: false,        
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v) {
                    //alert(v.signatoryName);			
			$("#signatory").append("<option value='"+v.signatoryCode+"'>("+v.signatoryCode+")"+v.signatoryName+"</option>");
                    });								
		},complete: function (data) {
		},error : function(jqXHR,textStatus,errorThrown ){
                    alert("Error :"+textStatus);
                }
	});    
        //***********************************************
        //Getting file no from  table
        //***********************************************
        function show_file_no(){
        $.ajax({
        url: "getFileNumber.htm",
        type: "POST",
	cache: false,
	async: false,
            success: function(fileNo){
                //alert(fileNo);
		//$("#tenderFN").val('');							
            },error : function(jqXHR,textStatus,errorThrown ){
                alert(textStatus);
            },complete: function (data) {
            }
	}); 
        }
    //******************************************************************
    //Getting PO number from  po_entry table for increment by one.
    //******************************************************************
    function generate_po_number() {
        $.ajax({
            url: "getPONoIncrement.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({uid: "dummy"}),
            success: function (poNo) {
                //alert(poNo);
                $("#poNumber").val(poNo);
            }, complete: function (data) {
            }
        });
    }
    //***********************************************
    //START : TEMPLATE FOR PAYMENT
    //***********************************************
        $("#payment").dblclick(function(){
                    $(".modal-body-pay").empty();                    
                    var textId;
                    $("#cstModalPay").modal("show");                    
                    $.ajax({
                        url: "getPaymentDetails.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlP){                           
                            var resul = $.parseJSON(htmlP);			
                            $.each(resul, function(k, u) {
                            //alert(u.paymentDes);			
                                $(".modal-body-pay").append("<div class='ineer-model' id='p"+u.paymentID+"' >"
                                +" "+u.paymentDes+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    textId = this.id;
                                    var textt = $("#"+textId).text();                                    
                                    $("#payment").append(textt+"\n\n");                                   
                                    //document.getElementById("payment").value = textt;
                                    $('#cstModalPay').modal('toggle');
                                    var WhereToMove = $("#payment").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000); 
                                    $("#inspection").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
        //***********************************************
        //END : TEMPLATE FOR PAYMENT
        //***********************************************        
                
        //******************************************************************
        //Getting vendor details from vendor_details table
        //******************************************************************
        $('#venderName').on('change', function() {
                //alert( this.value );
            if(this.value === "null" || this.value === ""){                
                $("#details").empty();
            }else{   
            $.ajax({
                url: "getVendorDetails.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({vendorName: this.value}),
                success: function(details){
                    //alert(details);
                    var result = $.parseJSON(details);			
                    $.each(result, function(k, v) {
                    //alert(v.vendorName);			
			$("#details").html("Name : "+v.vendorName+", &nbsp&nbsp&nbsp"+
                                "City : "+v.vendorCity+", &nbsp&nbsp&nbsp"+
                                "Phone : "+v.vendorPhone+", &nbsp&nbsp&nbsp"+
                                "Email : "+v.vendorEmail+", &nbsp&nbsp&nbsp"+
                                "Date : "+v.expiraryDate+", &nbsp&nbsp&nbsp"+
                                "PAN : "+v.vendorPan+", &nbsp&nbsp&nbsp"+
                                "Address : "+v.vendorAddress);
                    });								
		},error : function(jqXHR,textStatus,errorThrown ){
                    alert("Error occured at getVendorDetails "+textStatus);
                },complete: function (data) {
		}
            });
        }
        });
        

    
    //******************************************************************
    //START: getting indent form details
    //******************************************************************
    $('#tenderFN').keyup(function () {
        var fileNUMBER = parseInt(this.value);
        $("#errorTenderFN").empty();  
        if (fileNUMBER <= 0 || isNaN(fileNUMBER) === true) {
            $("#errorTenderFN").append("Please ente file number. ");  
        }else{
        
        $("#placeOfDelivery").val("");
        $.ajax({
            url: "getIndentFormData.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({IndentFormId: fileNUMBER}),
            success: function (htmlH) {
                var result = $.parseJSON(htmlH); 
                if($.isEmptyObject(result)){
                    $("#errorTenderFN").append("This numbe has no data. ");
                }else{
                $.each(result, function (k, v) {                    
                    $.ajax({
                        url: "getPlaceOfDeli.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({uid: "dummy"}),
                        success: function (htmlH) {                            
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, p) {                                
                                if (p.placeOfDeliveryID === v.placeOfDeliveryId) {
                                    $("#placeOfDelivery").val(p.placeOfDelivery);
                                }
                            });

                        }, complete: function (data) {
                        }
                    });
                   
                });
            }
            }, complete: function (data) {
            }
        });
        }
    });
    
    //******************************************************************
    //END: getting indent form details
    //******************************************************************
    
    //******************************************************************
    //START: DESCRIPTION TEMPLATE
    //******************************************************************            
                $("#venDescription").dblclick(function(){
                    $(".modal-body").empty();                    
                    var text_id;
                    $("#cstModal").modal("show");                    
                    $.ajax({
                        url: "getBreifDescriFrIndentForm.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlD){                            
                            var result = $.parseJSON(htmlD);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-body").append("<div class='ineer-model' id='"+v.indentFormID+"' >"
                                +" "+v.briefDescription+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#venDescription").text('');                                   
                                    document.getElementById("venDescription").value = tex;
                                    $('#cstModal').modal('toggle');
                                    var WhereToMove = $("#venDescription").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#poDate").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: DESCRIPTION TEMPLATE
    //******************************************************************   
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
       var $items = $('#tenderFN, #venderName,#quotationNo,#referenceNo,' +
                '#venDescription, #poNumber, #poDate, #price,' +
                '#payment, #inspection, #poValue,' +
                '#placeOfDelivery, #deliveryPeriod, #signatory, #preparedDate,' +
                '#preparedBy, #securityDeposit, #pBG, #offer,' +
                '#specED, #addST, #freight, #others');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "savePOEntryDetails.htm",
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
                    $("#POentryForm")[0].reset();
                    $("#payment").selectpicker("refresh");
                    $("#signatory").selectpicker("refresh");
                    $("#venDescription").val("");
                    $("#details").empty();
                    show_file_no();
                    generate_po_number();
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
        url: "getPoEntryRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(poentry){            
            var result = $.parseJSON(poentry);
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
                           "<td>"+v.tenderFN+"</td><td>"+v.quotationNo+"</td>"+  
                           "<td>"+v.poNumber+"</td><td>"+v.poValue+"</td>"+
                           "<td>"+v.placeOfDelivery+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='generatePoPdf' style='margin-right: 5px;' value=" + v.poEntryID + ">Po Pdf </button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='generPoItemsPdf' style='margin-right: 5px;' value=" + v.poNumber + ">Items Pdf</button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.poEntryID + ">Edit </button></td> ";
                                            
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
                        {title: "File NO", width: '13%'},
                        {title: "POSR No", width: '13%'},
                        {title: "PO NO", width: '13%'},
                        {title: "Po Value", width: '13%'},
                        {title: "Place Of Delivery", width: '13%'},                        
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
                    $("#POentryForm")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getPoEntryReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({poEntry_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#poEntryID").val(v.poEntryID);
                                $("#tenderFN").val(v.tenderFN);
                                $("#venderName").val(v.venderName); 
                                $("#venderName").selectpicker("refresh");
                                $("#quotationNo").val(v.quotationNo); 
                                $("#referenceNo").val(v.referenceNo); 
                                $("#venDescription").val(v.venDescription); 
                                $("#poNumber").val(v.poNumber); 
                                $("#poDate").val(v.poDate); 
                                
                                $("#price").val(v.price);
                                $("#payment").val(v.payment);
                                $("#inspection").val(v.inspection);  
                                $("#poValue").val(v.poValue); 
                                $("#placeOfDelivery").val(v.placeOfDelivery); 
                                $("#deliveryPeriod").val(v.deliveryPeriod); 
                                $("#signatory").val(v.signatory); 
                                $("#preparedDate").val(v.preparedDate); 
                                
                                $("#preparedBy").val(v.preparedBy);
                                $("#securityDeposit").val(v.securityDeposit);
                                $("#pBG").val(v.pBG);  
                                $("#offer").val(v.offer); 
                                $("#specED").val(v.specED); 
                                $("#addST").val(v.addST); 
                                $("#freight").val(v.freight); 
                                $("#others").val(v.others); 
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
                
                $("#basic-usage").on('click', '#generatePoPdf', function (){
                    //alert(this.value);
                    window.open('poPdfDownload.htm?eindi='+this.value);
                });
                
                $("#basic-usage").on('click', '#generPoItemsPdf', function (){
                    //alert(this.value);
                    window.open('poItemsPdfDownload.htm?ponum='+this.value);
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
        $("#POentryForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#poEntryID, #tenderFN, #venderName,#quotationNo,#referenceNo,' +
                '#venDescription, #poNumber, #poDate, #price,' +
                '#payment, #inspection, #poValue,' +
                '#placeOfDelivery, #deliveryPeriod, #signatory, #preparedDate,' +
                '#preparedBy, #securityDeposit, #pBG, #offer,' +
                '#specED, #addST, #freight, #others');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updatePoEntryDetails.htm",
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
                    $("#POentryForm")[0].reset();
                    $("#payment").selectpicker("refresh");
                    $("#signatory").selectpicker("refresh");
                    $("#venDescription").val("");
                    $("#details").empty();
                    show_file_no();                    
                        $("#form_record").show();
                        $("#form_page").hide();                          
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
    $("#showForm").on('click', function () {
        $("#form_record").hide();
        $("#form_page").show();
        
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        
        $("#POentryForm")[0].reset();
        $("#payment").selectpicker("refresh");
        $("#signatory").selectpicker("refresh");
        $("#venDescription").val("");
        $("#details").empty();
        close_message();
        generate_po_number();
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

    //******************************************************************
    //START: DESCRIPTION TEMPLATE
    //******************************************************************            
                $("#offer").dblclick(function(){
                    $(".modal-body-tax").empty();                    
                    var text_ida;
                    $("#cstModalTax").modal("show");                    
                    $.ajax({
                        url: "getTaxRecord.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlD){                            
                            var result = $.parseJSON(htmlD);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-body-tax").append("<div class='ineer-model' id='o"+v.taxId+"' >"
                                +" "+v.description+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_ida = this.id;                                    
                                    var texa = $("#"+text_ida).text();                                    
                                    $("#offer").append(texa+"\n\n");                                                                 
                                    //document.getElementById("offer").value = texa;
                                    $('#cstModalTax').modal('toggle');
                                    var WhereToMove = $("#offer").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#specED").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: DESCRIPTION TEMPLATE
    //******************************************************************  
    
    
    
});
