/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openChequeDDRe() {

    window.open("./chequeDdRecord.htm", "_parent");
}
$(document).ready(function () {

    receipt_incre();
    challan_incre();
    
    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#cheDDdaentry").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    
    //******************************************************************
    //Getting RECEIPT NUMBER, SERIAL NUMBER for increment by one.
    //******************************************************************
    function receipt_incre() {
        $.ajax({
            url: "getReceiptNoIncr.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (rece) {                
                $("#receiptNo").val(parseInt(rece));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });
    }  
    
    function serial_incre() {
        $.ajax({
            url: "getSerialNoIncr.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (rec) {                
                $("#slNo").val(parseInt(rec));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //Getting Challan NUMBER for increment by one.
    //******************************************************************
    function challan_incre() {
        $.ajax({
            url: "getChallanNoIncr.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (cha) {                
                $("#challanNo").val(parseInt(cha));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });
    }
    
    
    //******************************************************************
    //START: CHALLAN YEAR TEMPLATE
    //******************************************************************            
    $("#challanYear").click(function () {
        $(".modal-body").empty();        
        var text_id;
        $("#cstModal").modal("show");

        var no = 1;
        var year = 2010;
        var yearTo;
        show_year(year);

        function show_year(yearStart) {
            for (var i = 1; i <= 25; i++) {
                yearTo = yearStart++;
                $(".modal-body").append("<a class='ineer-model' id='" + (no++) + "'>" + (yearTo) + "-" + (yearTo + 1) + "</a>");

                if (i % 5 === 0) {
                    $(".modal-body").append("<br/><br/>");
                }

            }
        }

        var div = $(".ineer-model");
        div.click(function ()
        {
            text_id = this.id;
            var tex = $("#" + text_id).text();
            $("#challanYear").text('');
            document.getElementById("challanYear").value = tex;
            $('#cstModal').modal('toggle');
            var WhereToMove = $("#challanYear").position().top;
            $("html,body").animate({scrollTop: WhereToMove}, 1000);
        });
    });
    //******************************************************************
    //END: CHALLAN YEAR TEMPLATE
    //******************************************************************              
    
    //******************************************************************
    //START: TAG, SUBHEAD FIELDS WORK
    //******************************************************************    
    $("#ShHiSlNo").hide();
    $("#tag").on('change', function(){
        var tag = this.value;
        $('.editOption').hide();
        $("#subHead").val('null');
        if(tag === 'M'){
            $('.editOption').hide();
            $("#subHead").val('minusDebit');            
            $("#ShHiSlNo").hide();
            $("#slNo").val('0');
        }else if(tag === 'EMD'){
            $('.editOption').hide();
            $("#ShHiSlNo").show();
            serial_incre();
        }else if(tag === 'SD'){
            $('.editOption').hide();
            $("#ShHiSlNo").show();
            serial_incre();
        }else{
            $('.editOption').hide();
            $("#subHead").val('null');
            //document.getElementById("slNo").disabled = false;
            $("#ShHiSlNo").hide();
            $("#slNo").val('0');
        }        
    });
    
    
    var initialText = $('.editable').val();
    $('.editOption').val(initialText);

    $('#subHead').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editable').text();
        $('.editOption').val('');
        if (selected == "editable") {
            $('.editOption').show();

            $('.editOption').keyup(function () {
                var editText = $('.editOption').val();
                $('.editable').val(editText);
                $('.editable').html(editText);
            });

        } else {
            $('.editOption').hide();
        }
    });
    
    //******************************************************************
    //END: TAG, SUBHEAD FIELDS WORK
    //******************************************************************   
    
    //******************************************************************
    //START: HEAD OF ACCOUNT, SHORT CODE, HEAD DISPLY
    //****************************************************************** 
    $.ajax({
        url: "getHoaNamesDetails.htm",
        type: "POST",
        cache: false,
        success: function (hoade) {
            //alert(hoade);
            var hoaDe = $.parseJSON(hoade);
            $("#headOfAc").empty();
            $("#headOfAc").append("<option value='null'>select</option>");
            $.each(hoaDe, function (k, v) {

                $("#headOfAc").append("<option value=" + v.accountType + ">" + v.accountCode + "</option>");
                $("#headOfAc").selectpicker("refresh");
            });

            $("#headOfAc").on('change', function () {
                $("#errorShortCo").empty();
                var hoaVar = this.value;                
                if (hoaVar === 'null' || hoaVar === '') {
                    $("#scode option").remove();
                    $("#errorShortCo").append("Select Head Of Account. ");
                    $("#head").val('');
                } else {
                    $.ajax({
                        url: "getHoaSsortByAcName.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({acName: hoaVar}),
                        success: function (hoashort) {
                            //alert(hoashort);
                            $("#head").val(hoaVar);
                            var shortCod = $.parseJSON(hoashort);
                            if ($.isEmptyObject(shortCod)) {
                                $("#errorShortCo").append("This number has no data. ");
                                $("#scode option").remove();
                                $("#head").val('');
                            } else {
                                $("#scode").empty();
                                $("#scode").append("<option value='null'>select</option>");
                                $.each(shortCod, function (k, w) {
                                    $("#scode").append("<option value=" + w.shortCode + ">" + w.shortCode + "</option>");
                                    $("#scode").selectpicker("refresh");
                                });
                            }
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert(textStatus + " :" + errorThrown);
                        }
                    });
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error :" + jqXHR.status + ", " + errorThrown);
        }, complete: function (data) {

        }
    });
    //******************************************************************
    //END: HEAD OF ACCOUNT, SHORT CODE DISPLY
    //****************************************************************** 
    
      $.ajax({
        url: "getVendorName.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){	
                //alert(htmlH);
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v) {
                    //alert(v.vendorCode);			
			$("#supplierName").append("<option value='"+v.vendorCode+"'>("+v.vendorCode+")"+v.vendorName+"</option>");
                    });	
										
		},complete: function (data) {
		}
	});
        
    //******************************************************************
    //START: REMAINING BAL.T.DOCUMENT
    //******************************************************************
        $("#fileNo").keyup(function () {
        $("#errorfileNo").empty();
        var fileNuM = parseInt(this.value);        
        if(fileNuM <= 0 || isNaN(fileNuM) === true){
            $("#errorfileNo").append("Please enter file number. ");
            $("#balDocument").val('');
        }else{
        $.ajax({
            url: "getTenderDocuDeByFile.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({FileNuM: fileNuM}),
            success: function (htmlH) {                
                $("#errorfileNo").empty();
                var result = $.parseJSON(htmlH);
                if ($.isEmptyObject(result)) {
                    $("#errorfileNo").append("This Number has no data. ");
                    $("#balDocument").val('');
                } else {
                    $.each(result, function (k, v) {  
                        if(parseInt(v.noOfCopies) <= 0){
                            $("#errorfileNo").append("This Number has zero documents. ");
                            $("#balDocument").val('');
                        }
                        $("#balDocument").val(v.noOfCopies);
                    });
                }
            }, complete: function (data) {
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    });
    //******************************************************************
    //END: REMAINING BAL.T.DOCUMENT
    //******************************************************************   
    
    $("#form_record").hide();
    $("#form_page").show();
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#receiptNo, #date,#supplierName,#challanNo, #challanDate,' +
                '#month, #challanYear, #headOfAc,' +
                ' #scode,#ddDate,' +
                '#head, #tag, #subHead, #fileNo, #balDocument,' +
                '#balance, #ddOrChequeNo, #amount, #letterDt,' +
                '#details, #slNo');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveChequeDdEntry.htm",
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify([obj]),
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong>Your data has been Successfully saved.");
                    $(window).scrollTop(0);                    
                    $("#Cheque_Dd_Form")[0].reset();                    
                    $("#ShHiSlNo").hide();                    
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();           
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> This file number has no data, Please try with different file number.");
                    $(window).scrollTop(0);
                }  else {
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
        url: "getChequeAndDdRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(cheque){            
            var result = $.parseJSON(cheque);
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
                           "<td>"+v.receiptNo+"</td><td>"+v.challanNo+"</td>"+  
                           "<td>"+v.headOfAc+"</td>"+
                           "<td>"+v.amount+"</td><td>"+v.details+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='chequeForwPdf' style='margin-right: 5px;' value=" + v.chequeDdId + ">Cheque Forward</button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='generateEmdPdf' style='margin-right: 5px;' value=" + v.chequeDdId + ">PDF </button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.chequeDdId + ">Edit </button></td> ";
                                            
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
                        {title: "Receipt NO", width: '13%'},
                        {title: "Challan No", width: '13%'},
                        {title: "Head OF Account", width: '13%'},                        
                        {title: "Amount", width: '13%'},
                        {title: "Details", width: '13%'},
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
                    $("#Cheque_Dd_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getChequeAndDdrReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({chedd_id: this.value}),
                        success: function (cheddd) {                              
                            var result = $.parseJSON(cheddd);
                            $.each(result, function (k, v) {                                
                                $("#chequeDdId").val(v.chequeDdId);
                                $("#receiptNo").val(v.receiptNo);
                                $("#date").val(v.date);  
                                $("#supplierName").val(v.supplierName); 
                                $("#challanNo").val(v.challanNo); 
                                $("#challanDate").val(v.challanDate); 
                                $("#month").val(v.month); 
                                $("#challanYear").val(v.challanYear); 
                                
                                $("#headOfAc").val(v.headOfAc);
                                $("#scode").val(v.scode);
                                $("#head").val(v.head);  
                                $("#tag").val(v.tag); 
                                $("#ddDate").val(v.ddDate); 
                                $("#subHead").val(v.subHead); 
                                $("#fileNo").val(v.fileNo); 
                                $("#balDocument").val(v.balDocument);
                                
                                $("#balance").val(v.balance);
                                $("#ddOrChequeNo").val(v.ddOrChequeNo);
                                $("#amount").val(v.amount);  
                                $("#letterDt").val(v.letterDt); 
                                $("#details").val(v.details); 
                                $("#slNo").val(v.slNo);                                
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#scode").selectpicker("refresh");
                            $("#headOfAc").selectpicker("refresh");
                            $("#supplierName").selectpicker("refresh");
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
   
                $("#basic-usage").on('click', '#generateEmdPdf', function (){
                    //alert(this.value);
                    window.open('challanEmdPdfDown.htm?eindi='+this.value);
                });
                
                $("#basic-usage").on('click', '#chequeForwPdf', function (){
                    //alert(this.value);
                    window.open('chequeForwdPdfDown.htm?eindi='+this.value);
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
        $("#Cheque_Dd_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#chequeDdId, #receiptNo, #date,#supplierName,#challanNo, #challanDate,' +
                '#month, #challanYear, #headOfAc,' +
                ' #scode,#ddDate,' +
                '#head, #tag, #subHead, #fileNo, #balDocument,' +
                '#balance, #ddOrChequeNo, #amount, #letterDt,' +
                '#details, #slNo');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateChequeDdDetails.htm",
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
                    $("#Cheque_Dd_Form")[0].reset();
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
        $("#Cheque_Dd_Form")[0].reset();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#scode").selectpicker("refresh");
        $("#headOfAc").selectpicker("refresh");
        $("#supplierName").selectpicker("refresh");
        receipt_incre();
        challan_incre();
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

    
}); //End document
