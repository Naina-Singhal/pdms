/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openIssueVouRe() {

    window.open("./issueVoucherRe.htm", "_parent");
}

$(document).ready(function () {
    
    
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#isvchet").addClass("active");
    
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    appendRcivControlDe();
    appendSection();
    generateIndenter();
    //**************************************************************************
    //***START: SAVE ISSUE VOUCHER DETAILS
    //**************************************************************************
    $("#saveIssueVoucher").click(function () {
        
    var $items = $('#ivControlNo, #ivControlDate,#rcivNumber,#rcivDate, #issueType,'+
                    '#section, #disposal, #deliveryAt, #itemCovered, #joAllocation,'+
                    '#storeGroupCode, #cardNumber, #cardCode, #upToDateBal, #describe,'+
                    '#partNumber, #minLevel, #mlReachedDat, #date, #qtyLaIssue,'+
                    '#basicRate, #rateDate, #waRate, #rate, #currencyCode,'+
                    '#receivedBy, #surplusQty, #lifoRate, #indentorName,'+
                    '#issuedByName, #authorByName, #authorByDate');
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
        var jsonObj = JSON.stringify([obj]);
         
            
        $.ajax({
            dataType : "json",
            url : "./saveIssueVoucher.htm",
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify([obj]),
            type : 'POST',
            success : function(data) {                
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong>Your data has been Successfully saved.");
                    $(window).scrollTop(0);                   
                    $("#issue_voucher")[0].reset();
                    $("#issue_voucher_sec")[0].reset();
                    challan_incre();
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                }  else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                    $(window).scrollTop(0);
                }
            }, error: function (jqXHR, textStatus, errorThrown) {                
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want save data ?");
            }
        });
    
    });
    
    
    //**************************************************************************
    //***START: SAVE ISSUE VOUCHER DETAILS
    //**************************************************************************
    
    var ivControlNo = $("#ivControlNo").val();
    var ivControlDate = $("#ivControlDate").val();
    var rcivNumber = $("#rcivNumber").val();
    var rcivDate = $("#rcivDate").val();
    var issueType = $("#issueType").val();
    var section = $("#section").val();
    var disposal = $("#disposal").val();
    var deliveryAt = $("#deliveryAt").val();
    var itemCovered = $("#itemCovered").val();
    var joAllocation = $("#joAllocation").val();
    var storeGroupCode = $("#storeGroupCode").val();
    var cardNumber = $("#cardNumber").val();
    var cardCode  = $("#cardCode").val();
    var upToDateBal = $("#upToDateBal").val();
    var describe = $("#describe").val();
    var partNumber = $("#partNumber").val();
    var minLevel = $("#minLevel").val();
    var mlReachedDat = $("#mlReachedDat").val();
    var date = $("#date").val();
    var qtyLaIssue = $("#qtyLaIssue").val();
    var basicRate = $("#basicRate").val();
    var rateDate = $("#rateDate").val();
    var waRate = $("#waRate").val();
    var rate = $("#rate").val();
    var currencyCode = $("#currencyCode").val();
    var receivedBy = $("#receivedBy").val();
    var surplusQty = $("#surplusQty").val();
    var lifoRate = $("#lifoRate").val();
    var indentorNo = $("#indentorNo").val();
    var indentorName = $("#indentorName").val();
    var issuedByNo = $("#issuedByNo").val();
    var issuedByName = $("#issuedByName").val();
    var authorByNo = $("#authorByNo").val();
    var authorByName = $("#authorByName").val();
    var authorByDate = $("#authorByDate").val();
    
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#ivControlNo, #ivControlDate,#rcivNumber,#rcivDate, #issueType,'+
                    '#section, #disposal, #deliveryAt, #itemCovered, #joAllocation,'+
                    '#storeGroupCode, #cardNumber, #cardCode, #upToDateBal, #describe,'+
                    '#partNumber, #minLevel, #mlReachedDat, #date, #qtyLaIssue,'+
                    '#basicRate, #rateDate, #waRate, #rate, #currencyCode,'+
                    '#receivedBy, #surplusQty, #lifoRate, #indentorName,'+
                    '#issuedByName, #authorByName, #authorByDate');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
         var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_conno = $("#ivControlNo").val();
            //alert(cur_conno);
            var itemObj = saveIssueItems(cur_conno);
            big = JSON.stringify({ issueDTO : parObj, issueItemsDTO : itemObj});            
        
            //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveIssueVoucher.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#issue_voucher")[0].reset();
                    $("#issue_voucher_sec")[0].reset();                    
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                }else if (data === -5) {
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
        url: "getIssueVoucherRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(DdNumber){            
            var result = $.parseJSON(DdNumber);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.ivControlNo+"</td><td>"+v.rcivNumber+"</td>"+  
                           "<td>"+v.issueType+"</td><td>"+v.section+"</td>"+
                           "<td>"+v.indentorName+"</td><td>"+v.describe+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.id + ">Edit </button></td> ";
                                            
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
                        {title: "IV Control NO", width: '13%'},
                        {title: "RCIV NO", width: '13%'},
                        {title: "Issue Type", width: '13%'},
                        {title: "Section", width: '13%'},
                        {title: "Indenter", width: '13%'},
                        {title: "Describe", width: '13%'},
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
                    $("#issue_voucher")[0].reset();
                    $("#issue_voucher_sec")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getIssueVoucherReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({IssueVou_id: this.value}),
                        success: function (data) {                              
                            var result = $.parseJSON(data);
                            $.each(result, function (k, v) {                                
                                $("#id").val(v.id);
                                $("#ivControlNo").val(v.ivControlNo);
                                $('#ivControlNo').selectpicker('refresh');
                                $("#ivControlDate").val(v.ivControlDate);  
                                $("#rcivNumber").val(v.rcivNumber); 
                                $("#rcivDate").val(v.rcivDate); 
                                $("#issueType").val(v.issueType); 
                                $("#section").val(v.section); 
                                $('#section').selectpicker('refresh');
                                $("#disposal").val(v.disposal); 
                                
                                $("#deliveryAt").val(v.deliveryAt);
                                $("#itemCovered").val(v.itemCovered);
                                $("#joAllocation").val(v.joAllocation);  
                                $("#storeGroupCode").val(v.storeGroupCode); 
                                $("#cardNumber").val(v.cardNumber); 
                                $("#cardCode").val(v.cardCode); 
                                $("#upToDateBal").val(v.upToDateBal); 
                                $("#describe").val(v.describe);
                                
                                $("#partNumber").val(v.partNumber);
                                $("#minLevel").val(v.minLevel);
                                $("#mlReachedDat").val(v.mlReachedDat);  
                                $("#date").val(v.date); 
                                $("#qtyLaIssue").val(v.qtyLaIssue); 
                                $("#basicRate").val(v.basicRate); 
                                $("#rateDate").val(v.rateDate); 
                                $("#waRate").val(v.waRate);
                                
                                $("#rate").val(v.rate);
                                $("#currencyCode").val(v.currencyCode);
                                $("#receivedBy").val(v.receivedBy);  
                                $("#surplusQty").val(v.surplusQty); 
                                $("#lifoRate").val(v.lifoRate);                                
                                $("#indentorName").val(v.indentorName); 
                                $('#indentorName').selectpicker('refresh');                                
                                
                                $("#issuedByName").val(v.issuedByName);                                
                                $("#authorByName").val(v.authorByName); 
                                $('#authorByName').selectpicker('refresh');
                                $("#authorByDate").val(v.authorByDate);
                                document.getElementById('ivControlDate').readOnly = false;
                                document.getElementById('deliveryAt').readOnly = false;
                                document.getElementById('itemCovered').readOnly = false;   
                                document.getElementById('issueType').readOnly = false;
                                document.getElementById('disposal').readOnly = false;
                                document.getElementById('joAllocation').readOnly = false;
                                document.getElementById('authorByDate').readOnly = false;
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_items").empty();
                            $("#table_formI").hide();
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
        $("#issue_voucher")[0].reset();
        $("#issue_voucher_sec")[0].reset();;
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#id, #ivControlNo, #ivControlDate,#rcivNumber,#rcivDate, #issueType,'+
                    '#section, #disposal, #deliveryAt, #itemCovered, #joAllocation,'+
                    '#storeGroupCode, #cardNumber, #cardCode, #upToDateBal, #describe,'+
                    '#partNumber, #minLevel, #mlReachedDat, #date, #qtyLaIssue,'+
                    '#basicRate, #rateDate, #waRate, #rate, #currencyCode,'+
                    '#receivedBy, #surplusQty, #lifoRate, #indentorName,'+
                    '#issuedByName, #authorByName, #authorByDate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateIssueVoucherDe.htm",
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
        $("#table_formI").hide();
    });
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        close_message();
        $("#issue_voucher")[0].reset();
        $("#issue_voucher_sec")[0].reset();
        $('.selectpicker').selectpicker('refresh');
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");  
        $("#stic_items").empty();
        $("#table_formI").show();
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
    //***START; FETCHING DATA FROM CONTROL*************************************
    //************************************************************************** 
       function appendRcivControlDe() {
        $.ajax({
            url: "getRcivControlRecord.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (htmlH) {
                //alert(htmlH);
                var result = $.parseJSON(htmlH);
                $("#ivControlNo").empty();
                $("#ivControlNo").append("<option value='null'>select</option>");
                $.each(result, function (k, v8) {
                    $("#ivControlNo").append("<option value='" + v8.controlNumberC + "'>" + v8.controlNumberC + "</option>");
                    $('#ivControlNo').selectpicker('refresh');
                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for control number:" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });

    }
    
    
        
    $("#ivControlNo").on('change', function () {
        $("#errivControlNo").empty();
        var po_NoC = this.value
        if (po_NoC <= 0 || isNaN(po_NoC) === true) {
            $("#errivControlNo").append("Please select PO number.");
        } else {
            generateIssueVouTable(po_NoC);
            $.ajax({
                url: "getRcivControlReByConNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({Control_no: po_NoC}),
                success: function (poonooo) {
                    //alert(poonooo);
                    var rest = $.parseJSON(poonooo);
                    if ($.isEmptyObject(rest)) {
                        $("#errivControlNo").append("This number has no data.");
                    } else {
                        $.each(rest, function (k, w) {                            
                            $("#ivControlDate").val(w.controlDateC);                            
                            $("#indentorName").val(w.indentorNameC);
                            $('#indentorName').selectpicker('refresh');
                            $("#section").val(w.sectionC);    
                            $('#section').selectpicker('refresh');
                            $("#deliveryAt").val(w.deliveryAtC);
                            $("#itemCovered").val(w.itemCoveredC);    
                            $("#issueType").val(w.issueTypeC);
                            $("#disposal").val(w.disposalC); 
                            $("#joAllocation").val(w.jobAllocationC);
                            $("#authorByName").val(w.authorisedByNoC);
                            $('#authorByName').selectpicker('refresh');
                            $("#authorByDate").val(w.authorisedByDateC);                            
                            document.getElementById('ivControlDate').readOnly = true;
                            document.getElementById('deliveryAt').readOnly = true;
                            document.getElementById('itemCovered').readOnly = true;   
                            document.getElementById('issueType').readOnly = true;
                            document.getElementById('disposal').readOnly = true;
                            document.getElementById('joAllocation').readOnly = true;
                            document.getElementById('authorByDate').readOnly = true;
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error at PO number:" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    
    
    
    //**************************************************************************
    //***END; FETCHING DATA FROM CONTROL****************************************
    //************************************************************************** 
    
    
    //******************************************************************
    //START:  APPEND INDENTER FOR RELEASE
    //******************************************************************
    function generateIndenter() {
        
        $.ajax({
            url: "getUserProfileDa.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (data) {
                //alert(data);
                $("#indentorName").empty();
                $("#authorByName").empty();
                var result = $.parseJSON(data);
                $("#indentorName").append("<option value='null'>select</option>");
                $("#authorByName").append("<option value='null'>select</option>");
                $.each(result, function (k, w) {                    
                  $("#indentorName").append("<option value='" + w.employeeProfileID + "'>("+ w.icNumber + ")" + w.firstName + "  " + w.lastName + "</option>");
                  $("#indentorName").selectpicker("refresh");  
                  
                  $("#authorByName").append("<option value='" + w.employeeProfileID + "'>("+ w.icNumber + ")" + w.firstName + "  " + w.lastName + "</option>");
                  $("#authorByName").selectpicker("refresh");
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error while getting employee profile for indenter or authorized. "+textStatus);
            }

        });

    }
    
    //******************************************************************
    //END:  APPEND INDENTER 
    //******************************************************************
    
    //***********************************************
    //START: Getting section from section_master table
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
                $("#section").append("<option value='null'>select</option>");
                $.each(result, function (k, v) {
                    //alert(v.sectionCode);			
                    $("#section").append("<option value='" + v.sectionCode +
                            "'>(" + v.sectionCode + ")" + v.sectionName + "</option>");
                });

            }, complete: function (data) {
            }
        });
    }
    //***********************************************
    //END: Getting section from section_master table
    //***********************************************
    
    //******************************************************************
    //START:  GENERATE TABLE FOR ISSUE VOUCHER
    //******************************************************************
    
    function generateIssueVouTable(ConNo) {        
        $("#stic_items").empty();
        $.ajax({
            url: "getRcivControlItemReByPo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({ConNumber_Id: ConNo}),
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
                var qtys = 1;
                var war = 1;
                var lifo = 1;
                $.each(result, function (k, v) { 
                    //alert("item : "+v.itemObj.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput group" + (code++) + "' value='"+v.groupCodeC+"' /></td>" +
                        "<td><input type='text' name='' class='painput cardNo" + (group++) + "' value='"+v.storeCardNoC+"' /></td>" +                        
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='item" + (item++) + "' value='"+v.itemC+"' /></td>" +
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unit" + (qtyOrd++) + "' value='"+v.unitC+"' /></td>"+
                        "<td><input type='text' name='' class='painput qtyRqud" + (unitQty++) + "' value='"+v.requiredQtyC+"' /></td>"+
                        "<td><input type='text' name='' class='painput available" + (lump++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput qtyIssue" + (pf++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput remarks" + (ace++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput upd" + (rege++) + "' value='' /></td>" +
                        
                        "<td><input type='text' name='' class='painput qtyBalance" + (store++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput qtyShort" + (qtys++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput waRate" + (war++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput lifoRate" + (lifo++) + "' value='' /></td>" +
                        "</tr>";
                });
                $("#stic_items").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for Issue Voucher items table  ;"+textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveIssueItems(ConNUM){
          
          var val = [];
          var myDataObj = [];
        $('#chckItems:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    
                    ConNumber: ConNUM,                    
                    group: $(".group"+val[i]).val(),                    
                    cardNo: $(".cardNo"+val[i]).val(),
                    item: $(".item"+val[i]).val(),
                    unit: $(".unit"+val[i]).val(),
                    qtyRqud: $(".qtyRqud"+val[i]).val(),
                    available: $(".available"+val[i]).val(),
                    qtyIssue: $(".qtyIssue"+val[i]).val(),
                    remarks: $(".remarks"+val[i]).val(),
                    upd: $(".upd"+val[i]).val(),
                    qtyBalance: $(".qtyBalance"+val[i]).val(),
                    qtyShort: $(".qtyShort"+val[i]).val(), 
                    waRate: $(".waRate"+val[i]).val(),
                    lifoRate: $(".lifoRate"+val[i]).val() 
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }

   
    //******************************************************************
    //END: GENERATE TABLE FOR ISSUE VOUCHER
    //******************************************************************
    
    
    //******************************************************************
    //START: Item Covered TEMPLATE
    //******************************************************************            
                $("#itemCovered").dblclick(function(){
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
                                    $("#itemCovered").text('');                                   
                                    document.getElementById("itemCovered").value = tex;
                                    $('#cstModalItem').modal('toggle');
                                    var WhereToMove = $("#itemCovered").position().top;
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

