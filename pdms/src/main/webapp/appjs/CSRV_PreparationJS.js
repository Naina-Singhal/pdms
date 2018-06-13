/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#csrvPR").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    RvNmberIncre();

      
        
    //***********************************************
    //Getting PO number, pO value from po_entry table
    //***********************************************
    $.ajax({
        url: "materialReceiptRecord.htm",
        type: "POST",
        cache: false,
        async: false,       
        success: function (htmlH) {
            //alert(htmlH);
            var result = $.parseJSON(htmlH);
            $.each(result, function (k, v8) {
                //alert(v8.poNumber+"--"+v8.poValue);			
                $("#dbNumber").append("<option value='" + v8.dbNumber + "'>" + v8.dbNumber + "</option>");
                $("#dbNumber").selectpicker("refresh");
            });

        }, complete: function (data) {
        }
    });
    //***********************************************
    //Getting section from section_master table
    //***********************************************
    function appendSection() {
        $.ajax({
            url: "getSection.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({uid: "dummy"}),
            success: function (htmlH) {
                var result = $.parseJSON(htmlH);
                $("#section").empty();
                $("#section").append("<option value='null'>Select</option>");
                $.each(result, function (k, v) {
                    //alert(v.sectionCode);			
                    $("#section").append("<option value='" + v.sectionCode + "'>(" 
                            + v.sectionCode + ")" + v.sectionName + "</option>");
                });

            }, complete: function (data) {
            }
        });
    }
    
    
    //***********************************************
    //Getting Users details
    //***********************************************
       $.ajax({
            url: "getUserProfileDa.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (data) {
                //alert(data);
                $("#indentor").empty();
                var result = $.parseJSON(data);
                $("#indentor").append("<option value='null'>select</option>");
                $.each(result, function (k, w) {                    
                  $("#indentor").append("<option value='" + w.employeeProfileID + "'>("+ w.icNumber + ")" + w.firstName + "  " + w.lastName + "</option>");
                  $("#indentor").selectpicker("refresh");  
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error while getting employee profile for indenter. "+textStatus);
            }

        });
        
    //***********************************************
    //Getting Head of account form head of account_master table
    //***********************************************
        $.ajax({
        url: "getHeadOfAc.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v) {
                    //alert(v.designationCode);			
			$("#headOfAccount").append("<option value='"+v.accountCode+"'>("+v.accountCode+")"+v.accountType+"</option>");
                    });	
										
		},complete: function (data) {
		}
	});
        
    $("#form_record").hide();
    $("#form_page").show();  
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#rvControlNo, #date,#poNumber,#poNodate, #dbNumber, #dbDate, #indentNumber, #indentDate, #updateDate, #updatedBy,'+
                    ' #deliveryChallanNo, #deliveryChallenDate, #inspectedBy, #section, #itemsCovered, #deliveryAt, #pfCharges, #tptCharges, #otherCharges, #remarks,'+
                ' #amountPaid, #paymentTerms, #indentor, #payingAuthority, #headOfAccount, #inspectRemarks, #group, #cardNumber,'+
                ' #code, #itemDescription, #partNumber, #unit, #SuppLastReceived, #balance, #basicRate, #lifoRate, #waRate, #rateDate,'+
                ' #mrDate, #qtyAccepted, #mrUnit, #mrPoNumber, #rvNumber, #mrRemarks, #mrLocation, #minLevelDate');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_dbno = $("#dbNumber").val();
            var cur_pono = $("#poNumber").val();
            //alert(cur_dbno);
            var itemObj = saveItems(cur_dbno, cur_pono); 
            big = JSON.stringify({ csrvDTO : parObj, csrvItemsDTO : itemObj});            
        
            //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveCsrvPreparation.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {    
                //alert(data);
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
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
        url: "getCsrvPrepaRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(csrv){            
            var result = $.parseJSON(csrv);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.rvControlNo+"</td><td>"+v.poNumber+"</td>"+  
                           "<td>"+v.indentNumber+"</td><td>"+v.section+"</td>"+
                           "<td>"+v.balance+"</td><td>"+v.remarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.csrvPreparationID + ">Edit </button></td> ";
                                            
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
                        {title: "RV Control NO", width: '13%'},
                        {title: "PO No", width: '13%'},
                        {title: "Indent NO", width: '13%'},
                        {title: "Section", width: '13%'},
                        {title: "Balance", width: '13%'},
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
                    $("#csrv_first_form")[0].reset();
                    $("#csrv_second_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getCsrvPrepaReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({csrv_id: this.value}),
                        success: function (html) { 
                            //alert(html);
                            var result = $.parseJSON(html);
                            $.each(result, function (k, v) {                                
                                $("#csrvPreparationID").val(v.csrvPreparationID);
                                $("#rvControlNo").val(v.rvControlNo);
                                $("#date").val(v.date);  
                                $("#poNumber").val(v.poNumber);                                 
                                $("#poNodate").val(v.poNodate); 
                                $("#dbNumber").val(v.dbNumber); 
                                $("#dbNumber").selectpicker("refresh");
                                $("#dbDate").val(v.dbDate); 
                                $("#indentNumber").val(v.indentNumber); 
                                $("#indentDate").val(v.indentDate); 
                                $("#updateDate").val(v.updateDate); 
                                
                                $("#updatedBy").val(v.updatedBy);
                                $("#deliveryChallanNo").val(v.deliveryChallanNo);
                                $("#deliveryChallenDate").val(v.deliveryChallenDate);  
                                $("#inspectedBy").val(v.inspectedBy); 
                                appendSection();
                                $("#section").val(v.section); 
                                $("#section").selectpicker("refresh");
                                $("#itemsCovered").val(v.itemsCovered); 
                                $("#deliveryAt").val(v.deliveryAt); 
                                $("#pfCharges").val(v.pfCharges); 
                                $("#tptCharges").val(v.tptCharges); 
                                $("#otherCharges").val(v.otherCharges);
                                
                                $("#remarks").val(v.remarks);
                                $("#amountPaid").val(v.amountPaid);
                                $("#paymentTerms").val(v.paymentTerms);                                
                                $("#indentor").val(v.indentor);
                                $("#indentor").selectpicker("refresh");
                                $("#payingAuthority").val(v.payingAuthority); 
                                $("#headOfAccount").val(v.headOfAccount);    
                                $("#headOfAccount").selectpicker("refresh");
                                $("#inspectRemarks").val(v.inspectRemarks); 
                                 
                                $("#group").val(v.group);                                
                                $("#cardNumber").val(v.cardNumber);
                                $("#code").val(v.code);
                                $("#itemDescription").val(v.itemDescription);  
                                $("#partNumber").val(v.partNumber); 
                                $("#unit").val(v.unit); 
                                $("#SuppLastReceived").val(v.SuppLastReceived); 
                                $("#balance").val(v.balance); 
                                $("#basicRate").val(v.basicRate); 
                                $("#lifoRate").val(v.lifoRate); 
                                $("#waRate").val(v.waRate);
                                
                                $("#rateDate").val(v.rateDate);
                                $("#mrDate").val(v.mrDate);
                                $("#qtyAccepted").val(v.qtyAccepted);  
                                $("#mrUnit").val(v.mrUnit); 
                                $("#mrPoNumber").val(v.mrPoNumber); 
                                $("#rvNumber").val(v.rvNumber); 
                                $("#mrRemarks").val(v.mrRemarks); 
                                $("#mrLocation").val(v.mrLocation); 
                                $("#minLevelDate").val(v.minLevelDate);       
                                document.getElementById('poNodate').readOnly = false;
                                document.getElementById('poNumber').readOnly = false;
                                document.getElementById('dbDate').readOnly = false;
                                document.getElementById('deliveryChallanNo').readOnly = false;
                                document.getElementById('deliveryChallenDate').readOnly = false;
                                document.getElementById('tptCharges').readOnly = false;
                                document.getElementById('remarks').readOnly = false;
                                document.getElementById('indentNumber').readOnly = false;
                                document.getElementById('indentDate').readOnly = false;
                                //appendIndenter(v.indentor);
                                $("#stic_items").empty();
                                $("#table_form").hide();
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
        $("#csrv_first_form")[0].reset();
        $("#csrv_second_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#csrvPreparationID, #rvControlNo, #date,#poNumber,#poNodate, #dbNumber, #dbDate, #indentNumber, #indentDate, #updateDate, #updatedBy,'+
                    ' #deliveryChallanNo, #deliveryChallenDate, #inspectedBy, #section, #itemsCovered, #deliveryAt, #pfCharges, #tptCharges, #otherCharges, #remarks,'+
                ' #amountPaid, #paymentTerms, #indentor, #payingAuthority, #headOfAccount, #inspectRemarks, #group, #cardNumber,'+
                ' #code, #itemDescription, #partNumber, #unit, #SuppLastReceived, #balance, #basicRate, #lifoRate, #waRate, #rateDate,'+
                ' #mrDate, #qtyAccepted, #mrUnit, #mrPoNumber, #rvNumber, #mrRemarks, #mrLocation, #minLevelDate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateCsrvPreparation.htm",
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
                    $("#csrv_first_form")[0].reset();
                    $("#csrv_second_form")[0].reset();
                    $("#section").selectpicker("refresh");
                    $("#dbNumber").selectpicker("refresh");
                    $("#headOfAccount").selectpicker("refresh");
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
        $("#stic_items").empty();
    });
    $("#showForm").on('click', function () {
        $("#form_record").hide();
        $("#form_page").show();
        close_message();
        RvNmberIncre();
        $("#csrv_first_form")[0].reset();
        $("#csrv_second_form")[0].reset();
        $("#section").selectpicker("refresh");
        $("#dbNumber").selectpicker("refresh");
        $("#headOfAccount").selectpicker("refresh");
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#table_form").show();
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

    //**************************************************************************
    //Start: rv control increament
    //**************************************************************************
    function RvNmberIncre() {
        $("#rvControlNo").val('');
        $.ajax({
            url: "getRvNumIncreament.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (dbincre) {                
                document.getElementById('rvControlNo').readOnly = true;
                $("#rvControlNo").val(parseInt(dbincre)).re;
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //**************************************************************************
    //end: rv control increament
    //**************************************************************************   
    
    //**************************************************************************
    //START: FETCHING DATA BY ON NUMBER
    //************************************************************************** 
    
    $("#dbNumber").on('change', function () {
        $("#errorDbNumber").empty();
        $("#dbDate").val("");
        $("#section").empty();
        $("#dbDate").val('');
        $("#poLastNo").val('');
        $("#poDate").val('');
        var Db_NuMber = this.value;
        if (Db_NuMber === "" || Db_NuMber === 'null') {
            $("#errorDbNumber").append("Please select PO number").css({"color": "red"});
            $("#stic_items").empty();
        } else {
            $.ajax({
                url: "getDbNoReFromMeteRec.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({DbNumber: Db_NuMber}),
                success: function (recede) {
                    //alert(recede);
                    var resultd = $.parseJSON(recede);
                    if ($.isEmptyObject(resultd)) {
                        $("#errorDbNumber").append("This number has no data. ").css({"color": "red"});
                    } else {
                        generateCsrvPrepaTable(Db_NuMber);
                        $.each(resultd, function (k, d) {
                            $("#poNodate").val(d.date);
                            $("#poNumber").val(d.poLastNo);
                            $("#dbDate").val(d.date);
                            $("#deliveryChallanNo").val(d.delayRchaNo);
                            $("#deliveryChallenDate").val(d.delayDate);
                            appendSection();
                            $("#section").val(d.section);
                            $("#section").selectpicker("refresh");
                            $("#itemsCovered").val(d.itemsCovered);
                            $("#tptCharges").val(d.packingCharges);
                            $("#remarks").val(d.remarks);                            
                            document.getElementById('poNodate').readOnly = true;
                            document.getElementById('poNumber').readOnly = true;
                            document.getElementById('dbDate').readOnly = true;
                            document.getElementById('deliveryChallanNo').readOnly = true;
                            document.getElementById('deliveryChallenDate').readOnly = true;                            
                            document.getElementById('tptCharges').readOnly = true;
                            document.getElementById('remarks').readOnly = true;
                            //appendIndenter(d.userDbId);
                            $.ajax({
                                url: "getFileNoFrTenByPo.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({PoNum: d.poLastNo}),
                                success: function (filenoDe) {
                                    //alert(filenoDe);
                                    var fileNoDe = $.parseJSON(filenoDe);
                                    $.each(fileNoDe, function (k, f) {
                                        //alert(f.tenderFN);
                                        $.ajax({
                                        url: "getIndentDetailsByFiNo.htm",
                                        type: "POST",
                                        cache: false,
                                        async: false,
                                        data: ({fileNo: f.tenderFN}),
                                            success: function (indentform) {
                                                //alert(indentform);
                                                var IndentForm = $.parseJSON(indentform);
                                                $.each(IndentForm, function (k, i) {
                                                    $("#indentNumber").val(i.indentNumber);
                                                    $("#indentDate").val(i.indentDate);
                                                    document.getElementById('indentNumber').readOnly = true;
                                                    document.getElementById('indentDate').readOnly = true;
                                                    //alert(i.employeeProfileId);
                                                    $("#indentor").val(i.employeeProfileId);
                                                    $("#indentor").selectpicker("refresh");
                                                });
                                            }, error: function (jqXHR, textStatus, errorThrown) {
                                                alert("Error for Indenter:" + jqXHR.status + ", " + errorThrown);
                                            }
                                    });
                                    });
                                    
                                }, error: function (jqXHR, textStatus, errorThrown) {
                                    alert("Error for getting File Number by PO number :" + jqXHR.status + ", " + errorThrown);
                                }
                            });
                        });
                    }
                    
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }, complete: function (data) {
                }
            });
        }
    });
    //**************************************************************************
    //END: FETCHING DATA
    //**************************************************************************
    
    //**************************************************************************
    //START: FETCHING DATA FOR INDENTER
    //**************************************************************************
    function appendIndenter(authUserid){    
    $.ajax({
            url: "getEmpDeByEmpId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({employee_id: authUserid}),
            success: function (rele) {                 
                var resul = $.parseJSON(rele);
                $("#indentor").empty(); 
                if ($.isEmptyObject(resul)) {
                    alert("This DB number has no Indenter(employee) data");
                } else {
                    
                    $("#indentor").append("<option value='null'>select</option>");                    
                    $.each(resul, function (k, w) {                        
                        $("#indentor").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")" 
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#indentor").append("<option class='editable' value='other'>other</option> ");
                    });                    
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for Indenter:" + jqXHR.status + ", " + errorThrown);
            }
        });
        }
    
    var initialText = $('.editable').val();
    $('.editOption').val(initialText);    
    $('#indentor').change(function () {
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
    
    //**************************************************************************
    //END: FETCHING DATA FOR INDENTER
    //**************************************************************************
        
    //******************************************************************
    //START:  GENERATE TABLE FOR CSRV PREPARATION ITEMS
    //******************************************************************
    
    function generateCsrvPrepaTable(db_no) {        
        $("#stic_items").empty();
        $.ajax({
            url: "getInspecCleaItemsReByDbNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({dbnumber_Id: db_no}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if($.isEmptyObject(result)){
                    $("#stic_items").empty();
                    $("#stic_items").append("This number has no data. ").css({"color":"#0000009c"});
                }else{
                var slno = 1;
                var ckslno = 1;
                
                var code = 1;
                var group = 1;               
                var store = 1;
                var item = 1;
                var qtyOrd = 1;
                var unitQty = 1;
                var lump = 1;
                var pf = 1;                
                var ace = 1;
                var rege = 1;
                var qtyOrdH = 1;
                var itemH = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+v.itemObj.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput groupCode" + (code++) + "' value='"+v.groupCode+"' /></td>" +
                        "<td><input type='text' name='' class='painput storeCode" + (group++) + "' value='"+v.storeCardNo+"' /></td>" +                        
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='item" + (item++) + "' value='"+v.item+"' /></td>" +
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unit" + (qtyOrd++) + "' value='"+v.unit+"' /></td>"+
                        "<td><input type='text' name='' class='painput orderQty" + (unitQty++) + "' value='"+v.orderQty+"' /></td>"+
                        "<td><input type='text' name='' class='painput acceptedQty" + (lump++) + "' value='"+v.acceptedQty+"' /></td>" +
                        "<td><input type='text' name='' class='painput balanceQty" + (store++) + "' value='' /></td>" +
                        "</tr>";
                });
                $("#stic_items").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for inspection clearance items table  ;"+textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveItems(dbNUM, poNum){
          
          var val = [];
          var myDataObj = [];
        $('#chckItems:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = {                     
                    dbNumBer: dbNUM, 
                    poNumberI: poNum,
                    groupCode: $(".groupCode"+val[i]).val(),                    
                    storeCode: $(".storeCode"+val[i]).val(),
                    item: $(".item"+val[i]).val(),
                    unit: $(".unit"+val[i]).val(),                    
                    orderQty: $(".orderQty"+val[i]).val(),
                    acceptedQty: $(".acceptedQty"+val[i]).val(),
                    balanceQty: $(".balanceQty"+val[i]).val()                   
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }

   
    //******************************************************************
    //END: GENERATE TABLE FOR CSRV PREPARATION ITEMS
    //******************************************************************
        
     
    //******************************************************************
    //START: Item Covered TEMPLATE
    //******************************************************************            
                $("#itemsCovered").dblclick(function(){
                    $(".modal-bodyItem").empty();                    
                    var text_id;
                    $("#cstModalItem").modal("show");                    
                    $.ajax({
                        url: "getBreifDescriFrIndentForm.htm",
                        type: "POST",
                        cache: false,                                             
                        success: function(breif){  
                            //alert(breif);
                            var result = $.parseJSON(breif);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-bodyItem").append("<div class='ineer-model' id='"+v.indentFormID+"' >"
                                +" "+v.briefDescription+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#itemsCovered").text('');                                   
                                    document.getElementById("itemsCovered").value = tex;
                                    $('#cstModalItem').modal('toggle');
                                    var WhereToMove = $("#itemsCovered").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#locInRecSec").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: Item Covered TEMPLATE
    //******************************************************************   
    
    
    
    }); //End document