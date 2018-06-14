/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    function next_record_page() {

    window.open("./poEntryRecord.htm", "_parent");
    }
$(document).ready(function () {

    $("#hide_show").hide();
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#perecord").addClass("active");

    //***********************************************
    //Getting vendor details
    //***********************************************
    $.ajax({
        url: "getVendorName.htm",
        type: "POST",
        cache: false,
        async: false,
        data: ({uid: "dummy"}),
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#supplier").empty();
            $.each(result, function (k, v) {
                //alert(v.vendorCode);			
                $("#supplier").append("<option value='" + v.vendorCode + "'>(" + v.vendorCode + ")" + v.vendorName + "</option>");
            });

        }, complete: function (data) {
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error while getting vendor details:" + textStatus);
        }
    }); 

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
    //Getting PO number, pO value from po_entry table
    //***********************************************

    $("#getPoDetails").click(function () {
                $("#poNumber").empty();
                $("#poValue").empty();
                $("#fromDateErr").empty();
                $("#toDateErr").empty();
        $.ajax({
            url: "getPoNoFromPOEn.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({FromDate: $("#fromDate").val(), ToDate: $("#toDate").val()}),
            success: function (htmlH) {                
                var result = $.parseJSON(htmlH);
                if ($("#fromDate").val() === "") {
                    $("#hide_show").hide();
                    $("#fromDateErr").append("Please select from date.");
                } else if ($("#toDate").val() === "") {
                    $("#hide_show").hide();
                    $("#toDateErr").append("Please select to date.");
                } else if ($.isEmptyObject(result)) {
                    $("#hide_show").hide();
                    $("#fromDateErr").append("There is no data available.");
                } else {
                    $("#hide_show").show();
                }
                $("#poNumber").append("<option value='0'>Select</option>");
                $.each(result, function (k, v8) {
                    //alert(v8.poNumber+"--"+v8.poValue);                        
                    $("#poNumber").append("<option value='" + v8.poNumber + "'>" + v8.poNumber + "</option>");                    
                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                $("#hide_show").hide();
                alert("Error :" + textStatus);

            }, complete: function (data) {
            }
        });
    });
    //***********************************************
    //Getting purchase order details by file no
    //***********************************************

    $("#poNumber").change('on', function () {
        var po_no_fr = this.value;
        $("#errorpoNumber").empty();
        $("#errpoReleaseType").empty();
        $("#placeOfDel").val("");
        $("#paymentTerms").val("");
        $("#poValue").val("");
        $("#poDate").val("");
        $("#indentRefNo").val("");
        $("#supplier").val(""); 
        
        var po_type = $("#poReleaseType").val();
        if(this.value === "0" || this.value === ""){            
            $("#errorpoNumber").append("Please select option").css({"color":"red"});
            $("#stic_items").empty();
        }else if(po_type === 'null'){
            $("#errpoReleaseType").append("Please select option").css({"color":"red"});
        }else{
        generatePoReleaseTable(po_no_fr);
            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: $("#poNumber").val()}),
                success: function (htmlH) {                    
                    var result = $.parseJSON(htmlH);
                    $.each(result, function (k, v) {
                        $.ajax({
                            url: "getIndentDetailsByFiNo.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({fileNo: v.tenderFN}),
                            success: function (data) {
                                //$("#indentor").empty();
                                var result = $.parseJSON(data);
                                $.each(result, function (k, w) {
                                    $("#indentor").val(w.employeeProfileId);
                                    
                                });
                                $('#indentor').selectpicker('refresh');
                            }, error: function (jqXHR, textStatus, errorThrown) {
                                alert("Error");
                            }

                        });
                        if (po_type === 'RPUM') {
                            document.getElementById('placeOfDel').readOnly = true;
                            document.getElementById('paymentTerms').readOnly = true;
                            document.getElementById('poValue').readOnly = true;
                            document.getElementById('poDate').readOnly = true;
                            document.getElementById('indentRefNo').readOnly = true;
                            document.getElementById('supplier').readOnly = true;
                            $("#placeOfDel").val(v.placeOfDelivery);
                            $("#paymentTerms").val(v.payment);
                            $("#poValue").val(v.poValue);
                            $("#poDate").val(v.preparedDate);
                            $("#indentRefNo").val(v.referenceNo);
                            $("#supplier").val(v.venderName);
                            $('#supplier').selectpicker('refresh');
                        } else {
                            document.getElementById('placeOfDel').readOnly = false;
                            document.getElementById('paymentTerms').readOnly = false;
                            document.getElementById('poValue').readOnly = false;
                            document.getElementById('poDate').readOnly = false;
                            document.getElementById('indentRefNo').readOnly = false;
                            document.getElementById('supplier').readOnly = false;
                            $("#placeOfDel").val('');
                            $("#paymentTerms").val('');
                            $("#poValue").val('');
                            $("#poDate").val('');
                            $("#indentRefNo").val('');
                            $("#supplier").val('null');
                            $('#supplier').selectpicker('refresh');
                        }

                    });

                }, complete: function (data) {
                }
            });
    }
    });
    //***********************************************
    //Getting section from section_master table
    //***********************************************
    $.ajax({
        url: "getSection.htm",
        type: "POST",
        cache: false,
        async: false,
        data: ({uid: "dummy"}),
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $.each(result, function (k, v) {
                //alert(v.sectionCode);			
                $("#section").append("<option value='" + v.sectionCode + "'>(" + v.sectionCode + ")" + v.sectionName + "</option>");
            });

        }, complete: function (data) {
        }
    });
    //***********************************************
    //Getting designation name form designation_master table
    //***********************************************
    $.ajax({
        url: "getDesignation.htm",
        type: "POST",
        cache: false,
        async: false,
        data: ({uid: "dummy"}),
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                //$("#indentor").append("<option value='" + v.designationCode + "'>(" + v.designationCode + ")" + v.designationName + "</option>");
            });

        }, complete: function (data) {
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
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#headOfAccount").append("<option value='" + v.accountCode + "'>(" + v.accountCode + ")" + v.accountType + "</option>");
            });

        }, complete: function (data) {
        }
    });
    
  
    $("#form_record").hide();
    $("#form_page").show();
    
    //**************************************************************************
    //Start Save po release  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poReleaseType, #poNumber, #date,#poDate, #scheDelDate,' +
                '#indentRefNo, #indentor,' +
                '#section, #supplier, #itemsCovered, #remarks, #poValue,' +
                '#poValPaid, #currencyType, #currencyCode,' +
                '#headOfAccount, #placeOfDel, #paymentTerms, #payingAuthority');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
         var jsonOb = JSON.stringify([obj]);
        
        var parObj = JSON.parse(jsonOb); 
        
         var big = [];
         var cur_pono = $("#poNumber").val();
         //alert(cur_pono);
         var itemObj = saveItems(cur_pono); 
         big = JSON.stringify({ poReleaseDTO : parObj,poItemsDTO : itemObj});  
            //var jsonObj = JSON.stringify([obj]);
        //alert(big);
        $.ajax({
            dataType : "json",
            url : "savePorderDetEn.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                //alert(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#poeForm")[0].reset();
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
        url: "poEntryRecord.htm",
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
                           "<td>"+v.poNumber+"</td><td>"+v.poDate+"</td>"+  
                           "<td>"+v.indentor+"</td><td>"+v.section+"</td>"+
                           "<td>"+v.supplier+"</td><td>"+v.remarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.pOrderEntryID + ">Edit </button></td> ";
                                            
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
                        {title: "PO NO", width: '13%'},
                        {title: "PO Date", width: '13%'},
                        {title: "Indenter", width: '13%'},
                        {title: "Section", width: '13%'},
                        {title: "Vendor", width: '13%'},
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
                    $("#poeForm")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getPoReleaseReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({poRelease_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#pOrderEntryID").val(v.pOrderEntryID);
                                $("#poReleaseType").val(v.poReleaseType);
                                $("#poNumber").val(v.poNumber);
                                $('#poNumber').selectpicker('refresh');
                                $("#date").val(v.date);  
                                $("#poDate").val(v.poDate); 
                                $("#scheDelDate").val(v.scheDelDate);                                
                                $("#indentRefNo").val(v.indentRefNo);                                
                                $("#indentor").val(v.indentor);
                                $('#indentor').selectpicker('refresh');
                                $("#section").val(v.section);
                                $('#section').selectpicker('refresh');
                                $("#supplier").val(v.supplier);
                                $('#supplier').selectpicker('refresh');
                                $("#itemsCovered").val(v.itemsCovered);
                                $("#remarks").val(v.remarks);
                                $("#poValue").val(v.poValue);
                                $("#poValPaid").val(v.poValPaid);
                                $("#currencyType").val(v.currencyType);
                                $("#currencyCode").val(v.currencyCode);
                                $("#headOfAccount").val(v.headOfAccount);
                                $('#headOfAccount').selectpicker('refresh');
                                $("#placeOfDel").val(v.placeOfDel);
                                $("#paymentTerms").val(v.paymentTerms);
                                $("#payingAuthority").val(v.payingAuthority);
                            });
                            $("#hide_show").show();
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#puitemstale").hide();
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
        $("#poeForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#pOrderEntryID, #poReleaseType, #poNumber, #date,#poDate, #scheDelDate,' +
                '#indentRefNo, #indentor,' +
                '#section, #supplier, #itemsCovered, #remarks, #poValue,' +
                '#poValPaid, #currencyType, #currencyCode,' +
                '#headOfAccount, #placeOfDel, #paymentTerms, #payingAuthority');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updatePoReleaseDe.htm",
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
                    $("#poeForm")[0].reset();
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
        $("#poeForm")[0].reset();
        $("#hide_show").hide();
        $("#puitemstale").show();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#stic_items").empty();
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
    //START:  GENERATE TABLE FOR PO ENTRY ITEMS
    //******************************************************************
    
    function generatePoReleaseTable(po_no) {        
        $("#stic_items").empty();
        $.ajax({
            url: "getPoDeItemDeByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({poNumb: po_no}),
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
                var excise = 1;
                var cst = 1;
                var surcharge = 1;
                var turnover = 1;
                var service = 1;
                var itemYet = 1;
                var poVal = 1;
                var discount = 1;
                var itemm = 1;
                var itemH = 1;
                var qtyOrdH = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+w.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput code" + (code++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput groupCode" + (group++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput storeCardNo" + (store++) + "' value='' /></td>" + 
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='itemDes" + (item++) + "' value='"+v.itemCode+"' /></td>" +
                        "<td><textarea name='' class='painput itemDesD" + (itemm++) + "'>"+v.itemObj.description+"</textarea></td>" +
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unitQty" + (qtyOrd++) + "' value='"+v.unit+"' /></td>"+
                        "<td><input type='text' name='' class='painput qtyOrder" + (unitQty++) + "' value='"+v.quantity+"' /></td>"+                        
                        "<td><input type='text' name='' class='painput lumpsum" + (lump++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput pAndf" + (pf++) + "' value='"+v.pfCharges+"' /></td>" +
                        "<td><input type='text' name='' class='painput excise" + (excise++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput cst" + (cst++) + "' value='"+v.gst+"' /></td>" +
                        "<td><input type='text' name='' class='painput surcharge" + (surcharge++) + "' value='' /></td>"+
                        "<td><input type='text' name='' class='painput turnOver" + (turnover++) + "' value='' /></td>"+
                         "<td><input type='text' name='' class='painput service" + (service++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput itemYetReq" + (itemYet++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput poValue" + (poVal++) + "' value='' /></td>"+
                        "<td><input type='text' name='' class='painput discount" + (discount++) + "' value='"+v.discount+"' /></td>"+
                        
                        "</tr>";
                });
                $("#stic_items").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveItems(PONUM){
          
          var val = [];
          var myDataObj = [];
        $('#chckItems:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    
                    poNumBer: PONUM,                    
                    code: $(".code"+val[i]).val(),                    
                    groupCode: $(".groupCode"+val[i]).val(),
                    storeCardNo: $(".storeCardNo"+val[i]).val(),
                    itemDes: $(".itemDes"+val[i]).val(),
                    qtyOrder: $(".qtyOrder"+val[i]).val(),
                    unitQty: $(".unitQty"+val[i]).val(),
                    lumpsum: $(".lumpsum"+val[i]).val(),   
                    pAndf: $(".pAndf"+val[i]).val(),
                    
                    excise: $(".excise"+val[i]).val(),                    
                    cst: $(".cst"+val[i]).val(),
                    surcharge: $(".surcharge"+val[i]).val(),
                    turnOver: $(".turnOver"+val[i]).val(),
                    service: $(".service"+val[i]).val(),
                    itemYetReq: $(".itemYetReq"+val[i]).val(),
                    poValue: $(".poValue"+val[i]).val(),   
                    discount: $(".discount"+val[i]).val()
                    
            };
            myDataObj.push(obj);
            
        });
            //alert(JSON.stringify([myDataObj]));
            return  myDataObj;
    }

   
    //******************************************************************
    //END: GENERATE TABLE FOR PO ENTRY ITEMS
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


var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveItem()
{
    //var nameregx = /^[_()-\][+\w\s]*$/;
    var nameregx = /^[)-_(\+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;

    $("#errorPurchaseUnit").html("");
    $("#errorPurchaseUnit1").html("");
    $("#errorRCIVNo").html("");
    $("#errorUserUnit").html("");
    $("#errorPurchaseGroup").html("");
    $("#errorPurGfNo").html("");
    $("#errorPOLastNo").html("");
    $("#errorPoDate").html("");
    $("#errorScheduledDelDate").html("");
    $("#errorExpDelDate").html("");
    $("#errorIndentRefNo").html("");
    $("#errorIndentDesNoDate").html("");
    $("#errorIndentor").html("");
    $("#errorSection").html("");
    $("#errorSupplier").html("");
    $("#errorItemsCovered").html("");
    $("#errorRemarks").html("");
    $("#errorPOoValue").html("");
    $("#errorPOValPaid").html("");
    $("#errorPaymentCurrency").html("");
    $("#errorCurrencyCode").html("");
    $("#errorCancelThisPo").html("");
    $("#errorHeadOfAccount").html("");
    $("#errorPlaceOfDel").html("");
    $("#errorPaymentTerms").html("");
    $("#errorPayingAuth").html("");


    var purchaseUnit = $("#purchaseUnit").val();
    var purchaseUnit1 = $("#purchaseUnit1").val();
    var rcivNo = $("#rcivNo").val();
    var date = $("#date").val();
    var userUnit = $("#userUnit").val();
    var purchaseGroup = $("#purchaseGroup").val();
    var purchaseGfNo = $("#purchaseGfNo").val();
    var poLastNo = $("#poLastNo").val();
    var poDate = $("#poDate").val();
    var scheDelDate = $("#scheDelDate").val();
    var expDelDate = $("#expDelDate").val();
    var indentRefNo = $("#indentRefNo").val();
    var indentDesNoDate = $("#indentDesNoDate").val();
    var indentor = $("#indentor").val();
    var section = $("#section").val();
    var supplier = $("#supplier").val();
    var itemsCovered = $("#itemsCovered").val();
    var remarks = $("#remarks").val();
    var poValue = $("#poValue").val();
    var poValPaid = $("#poValPaid").val();
    var paymentCurrency = $("#paymentCurrency").val();
    var currencyCode = $("#currencyCode").val();
    var cancelThisPo = $("#cancelThisPo").val();
    var headOfAccount = $("#headOfAccount").val();
    var placeOfDel = $("#placeOfDel").val();
    var paymentTerms = $("#paymentTerms").val();
    var payingAuthority = $("#payingAuthority").val();


    //var eItemCode = $("#eItemCode").val();
    





    //******* 1. purchase Unit validation ************//
    if (($.trim(purchaseUnit) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPurchaseUnit").html("Please enter Purchase Unit");
    } else {
        if (nameregx.test($.trim(purchaseUnit)))
        {
            flagValidTotal.push(true);
            $("#errorPurchaseUnit").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPurchaseUnit").html("Invalid Purchase Unit accepted special characters  '_()[]-'");
        }
    }
    //******* 2. RCIV No validation ************//
    if (($.trim(rcivNo) === ''))
    {
        flagValidTotal.push(false);
        $("#errorRCIVNo").html("Please enter RCIV Number");
    } else {
        if (coderegx.test($.trim(rcivNo)))
        {
            flagValidTotal.push(true);
            $("#errorRCIVNo").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorRCIVNo").html("Invalid RCIV Number. RCIV Number must be alphanumeric.");
        }
    }
    //******* 3. Date validation ************//
    if (($.trim(date) === ''))
    {
        flagValidTotal.push(false);
        $("#errorDate").html("Please enter Date or PO Last Number");
    } else {
        if (coderegx.test($.trim(rcivNo)))
        {
            flagValidTotal.push(true);
            $("#errorDate").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorDate").html("Invalid Date/PO Last Number.  Date/PO Last Number must be alphanumeric.");
        }
    }
    //******* 4. Purchase unit2 validation ************//
    if (($.trim(purchaseUnit1) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPurchaseUnit1").html("Please enter Purchase Unit");
    } else {
        if (nameregx.test($.trim(purchaseUnit1)))
        {
            flagValidTotal.push(true);
            $("#errorPurchaseUnit1").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPurchaseUnit1").html("Invalid Purchase Unit, accepted special characters  '_()[]-'");
        }
    }
    //******* 5. User Unit validation ************//
    if (($.trim(userUnit) === ''))
    {
        flagValidTotal.push(false);
        $("#errorUserUnit").html("Please enter User Unit");
    } else {
        if (nameregx.test($.trim(userUnit)))
        {
            flagValidTotal.push(true);
            $("#errorUserUnit").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorUserUnit").html("Invalid User Unit accepted special characters  '_()[]-'");
        }
    }
    //******* 6. Purchase Group validation ************//
    if (($.trim(purchaseGroup) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPurchaseGroup").html("Please enter Purchase Group");
    } else {
        if (nameregx.test($.trim(purchaseGroup)))
        {
            flagValidTotal.push(true);
            $("#errorPurchaseGroup").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPurchaseGroup").html("Invalid Purchase Group accepted special characters  '_()[]-'");
        }
    }
    //******* 7. purchase Gfile No validation ************//
    if (($.trim(purchaseGfNo) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPurGfNo").html("Please enter Purchase Gfile No");
    } else {
        if (nameregx.test($.trim(purchaseGfNo)))
        {
            flagValidTotal.push(true);
            $("#errorPurGfNo").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPurGfNo").html("Invalid Purchase Gfile No accepted special characters  '_()[]-'");
        }
    }
    //******* 8. PO Last No validation ************//
    if (($.trim(poLastNo) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPOLastNo").html("Please enter PO Last Number");
    } else {
        if (nameregx.test($.trim(poLastNo)))
        {
            flagValidTotal.push(true);
            $("#errorPOLastNo").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPOLastNo").html("Invalid PO Last Number accepted special characters  '_()[]-'");
        }
    }
    //******* 9. PO Date: validation ************//
    if (($.trim(poDate) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPoDate").html("Please enter PO Date");
    } else {
        if (nameregx.test($.trim(poDate)))
        {
            flagValidTotal.push(true);
            $("#errorPoDate").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPoDate").html("Invalid PO Date accepted special characters  '_()[]-'");
        }
    }
    //******* 10. Scheduled Delivery Date validation ************//
    if (($.trim(scheDelDate) === ''))
    {
        flagValidTotal.push(false);
        $("#errorScheduledDelDate").html("Please enter Scheduled Delivery Date");
    } else {
        if (nameregx.test($.trim(scheDelDate)))
        {
            flagValidTotal.push(true);
            $("#errorScheduledDelDate").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorScheduledDelDate").html("Invalid Scheduled Delivery Date accepted special characters  '_()[]-'");
        }
    }
    //******* 11. Expected Delivery Date validation ************//
    if (($.trim(expDelDate) === ''))
    {
        flagValidTotal.push(false);
        $("#errorExpDelDate").html("Please enter Expected Delivery Date");
    } else {
        if (nameregx.test($.trim(expDelDate)))
        {
            flagValidTotal.push(true);
            $("#errorExpDelDate").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorExpDelDate").html("Invalid Expected Delivery Date accepted special characters  '_()[]-'");
        }
    }
    //******* 12. Indent Ref Number validation ************//
    if (($.trim(indentRefNo) === ''))
    {
        flagValidTotal.push(false);
        $("#errorIndentRefNo").html("Please enter Indent Ref Number");
    } else {
        if (nameregx.test($.trim(indentRefNo)))
        {
            flagValidTotal.push(true);
            $("#errorIndentRefNo").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorIndentRefNo").html("Invalid Indent Ref Number accepted special characters  '_()[]-'");
        }
    }
    //******* 13. Indent Despatch No Date validation ************//
    if (($.trim(indentDesNoDate) === ''))
    {
        flagValidTotal.push(false);
        $("#errorIndentDesNoDate").html("Please enter Indent Despatch Number");
    } else {
        if (nameregx.test($.trim(indentDesNoDate)))
        {
            flagValidTotal.push(true);
            $("#errorIndentDesNoDate").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorIndentDesNoDate").html("Invalid Indent Despatch Number accepted special characters  '_()[]-'");
        }
    }
    //******* 14. Indentor: validation ************//
    if (($.trim(indentor) === ''))
    {
        flagValidTotal.push(false);
        $("#errorIndentor").html("Please enter Indentor");
    } else {
        if (nameregx.test($.trim(indentor)))
        {
            flagValidTotal.push(true);
            $("#errorIndentor").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorIndentor").html("Invalid Indentor accepted special characters  '_()[]-'");
        }
    }
    //******* 15. Section validation ************//
    if (($.trim(section) === ''))
    {
        flagValidTotal.push(false);
        $("#errorSection").html("Please enter Item Name");
    } else {
        if (nameregx.test($.trim(section)))
        {
            flagValidTotal.push(true);
            $("#errorSection").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorSection").html("Invalid Item Name accepted special characters  '_()[]-'");
        }
    }
    //******* 16. supplier validation ************//
    if (($.trim(supplier) === ''))
    {
        flagValidTotal.push(false);
        $("#errorSupplier").html("Please enter Supplier");
    } else {
        if (nameregx.test($.trim(supplier)))
        {
            flagValidTotal.push(true);
            $("#errorSupplier").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorSupplier").html("Invalid Supplier accepted special characters  '_()[]-'");
        }
    }
    //******* 17. items Covered validation ************//
    if (($.trim(itemsCovered) === ''))
    {
        flagValidTotal.push(false);
        $("#errorItemsCovered").html("Please enter Items Covered");
    } else {
        if (nameregx.test($.trim(itemsCovered)))
        {
            flagValidTotal.push(true);
            $("#errorItemsCovered").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorItemsCovered").html("Invalid Items Covered accepted special characters  '_()[]-'");
        }
    }
    //******* 18. Remarks:  validation ************//
    if (($.trim(remarks) === ''))
    {
        flagValidTotal.push(false);
        $("#errorRemarks").html("Please enter Remarks");
    } else {
        if (nameregx.test($.trim(remarks)))
        {
            flagValidTotal.push(true);
            $("#errorRemarks").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorRemarks").html("Invalid Remarks accepted special characters  '_()[]-'");
        }
    }
    //******* 19. PO Value validation ************//
    if (($.trim(poValue) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPOoValue").html("Please enter PO Value");
    } else {
        if (nameregx.test($.trim(poValue)))
        {
            flagValidTotal.push(true);
            $("#errorPOoValue").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPOoValue").html("Invalid PO Value accepted special characters  '_()[]-'");
        }
    }
    //******* 20. PO Value Paid validation ************//
    if (($.trim(poValPaid) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPOValPaid").html("Please enter PO Value Paid");
    } else {
        if (nameregx.test($.trim(poValPaid)))
        {
            flagValidTotal.push(true);
            $("#errorPOValPaid").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPOValPaid").html("Invalid PO Value Paid accepted special characters  '_()[]-'");
        }
    }
    //******* 21. Payment In Foreign Currency validation ************//
    if (($.trim(paymentCurrency) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPaymentCurrency").html("Please enter Payment In Foreign Currency");
    } else {
        if (nameregx.test($.trim(paymentCurrency)))
        {
            flagValidTotal.push(true);
            $("#errorPaymentCurrency").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPaymentCurrency").html("Invalid Payment In Foreign Currency accepted special characters  '_()[]-'");
        }
    }
    //******* 22. Currency Code validation ************//
    if (($.trim(currencyCode) === ''))
    {
        flagValidTotal.push(false);
        $("#errorCurrencyCode").html("Please enter Currency Code");
    } else {
        if (nameregx.test($.trim(currencyCode)))
        {
            flagValidTotal.push(true);
            $("#errorCurrencyCode").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorCurrencyCode").html("Invalid Currency Code accepted special characters  '_()[]-'");
        }
    }
    //******* 23. Cancel This Po validation ************//
    if (($.trim(cancelThisPo) === ''))
    {
        flagValidTotal.push(false);
        $("#errorCancelThisPo").html("Please enter Cancel This Po");
    } else {
        if (nameregx.test($.trim(cancelThisPo)))
        {
            flagValidTotal.push(true);
            $("#errorCancelThisPo").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorCancelThisPo").html("Invalid Cancel This Po accepted special characters  '_()[]-'");
        }
    }
    //******* 24. Head Of Account validation ************//
    if (($.trim(headOfAccount) === ''))
    {
        flagValidTotal.push(false);
        $("#errorHeadOfAccount").html("Please enter Head Of Account");
    } else {
        if (nameregx.test($.trim(headOfAccount)))
        {
            flagValidTotal.push(true);
            $("#errorHeadOfAccount").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorHeadOfAccount").html("Invalid Head Of Account accepted special characters  '_()[]-'");
        }
    }
    //******* 25. place Of Delivery validation ************//
    if (($.trim(placeOfDel) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPlaceOfDel").html("Please enter Place Of Delivery");
    } else {
        if (nameregx.test($.trim(placeOfDel)))
        {
            flagValidTotal.push(true);
            $("#errorPlaceOfDel").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPlaceOfDel").html("Invalid Place Of Delivery accepted special characters  '_()[]-'");
        }
    }
    //******* 26. Payment Terms validation ************//
    if (($.trim(paymentTerms) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPaymentTerms").html("Please enter Payment Terms");
    } else {
        if (nameregx.test($.trim(paymentTerms)))
        {
            flagValidTotal.push(true);
            $("#errorPaymentTerms").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPaymentTerms").html("Invalid Payment Terms accepted special characters  '_()[]-'");
        }
    }
    //******* 27. paying Authority validation ************//
    if (($.trim(payingAuthority) === ''))
    {
        flagValidTotal.push(false);
        $("#errorPayingAuth").html("Please enter Paying Authority");
    } else {
        if (nameregx.test($.trim(payingAuthority)))
        {
            flagValidTotal.push(true);
            $("#errorPayingAuth").html("");
        } else
        {
            flagValidTotal.push(false);
            $("#errorPayingAuth").html("Invalid Paying Authority accepted special characters  '_()[]-'");
        }
    }




//    if($.trim(itemcode).length > 0)
//    {
//        if($.trim(itemcode) !== $.trim(eItemCode))
//        {
//            fnCheckDuplicateItem(itemcode);
//            if (vChk > 0)
//            {
//                $("#errorDupItem").html("Item details with entered code already exists");
//                flagValidTotal.push(false);
//            } else
//            {
//                $("#errorDupItem").html("");
//                flagValidTotal.push(true);
//            }
//        }
//    }

    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] === false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;
}