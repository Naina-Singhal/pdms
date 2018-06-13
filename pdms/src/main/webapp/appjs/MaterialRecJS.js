/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    function openMeteReceRe(){
                
        window.open("./materialReceiptRecord.htm","_parent");
    }
$(document).ready(function () {
         
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#mtlrecord").addClass("active");

    //hide the error, success messages when load the page   
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide(); 
    DbNmberIncre();
    appendSection();
    //***********************************************
    //Getting PO number, pO value from po_entry table
    //***********************************************
    $.ajax({
        url: "poEntryRecord.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v8) {                    			
			$("#poLastNo").append("<option value='"+v8.poNumber+"'>"+v8.poNumber+"</option>");                        
                    });	
										
        },error : function(jqXHR,textStatus,errorThrown ){                    
                    alert("Error :"+jqXHR.status+", "+errorThrown);
        },complete: function (data) {
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
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v) {
                    //alert(v.designationCode);			
			$("#indentor").append("<option value='"+v.designationCode+
                                "'>("+v.designationCode+")"+v.designationName+"</option>");
                    });	
										
		},complete: function (data) {
		}
	});
        //***********************************************
        //Getting section from section_master table
        //***********************************************
        function appendSection(){
        $.ajax({
        url: "getSection.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);
                $("#section").empty();
                    $("#section").append("<option value='null'>select</option>");
                    $.each(result, function(k, v) {
                    //alert(v.sectionCode);			
			$("#section").append("<option value='"+v.sectionCode+
                                "'>("+v.sectionCode+")"+v.sectionName+"</option>");
                    });	
										
		},complete: function (data) {
		}
	});
    }
    //***********************************************
    //Getting purchase order details by file no
    //***********************************************
    $("#poLastNo").change('on', function () {
        $("#errorpoLastNo").empty();
        $("#poLNoDate").val("");
        $("#lrORrrNo").val('');
        $("#lrDate").val('');
        $("#itemsCovered").val('');
        $("#remarks").val('');
        $("#section").empty();
        var Po_numbER = this.value;
        var LrType = $("#lrType").val();        
        if (Po_numbER <= 0 || isNaN(Po_numbER) === true || Po_numbER === 'null')  {
            $("#errorpoLastNo").append("Please select option").css({"color": "red"});
            $("#stic_items").empty();
        }else if(LrType === 'null'){
            $("#errorpoLastNo").append("Please select LR Option").css({"color": "red"});
        } else {
            generatePoReleaseTable(Po_numbER);
            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: $("#poLastNo").val()}),
                success: function (htmlH) {
                    //alert(htmlH);
                    var result = $.parseJSON(htmlH);
                    if ($.isEmptyObject(result)) {
                        $("#errorpoLastNo").append("This PO number has no data.").css({"color": "red"});
                    } else {
                        $.each(result, function (k, v) {
                            $("#poLNoDate").val(v.preparedDate);
                            document.getElementById('poLNoDate').readOnly = true;
                        });

                        if (LrType === 'LR') {
                            lrClearanceButton();
                        }
                    }

                }, complete: function (data) {
                }
            });
            // FETCHING DATA FROM LR STORES MASTER FOR LR/RR NO, DATE
            $.ajax({
                url: "getLrStoresDeByPoNum.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({LrStores_po: Po_numbER}),
                success: function (podat) { 
                    //alert(podat);
                    var oresult = $.parseJSON(podat);                    
                    $.each(oresult, function (k, w) {                        
                        $("#lrORrrNo").val(w.rrAndLrNumber);                        
                        $("#lrDate").val(w.rrAndLrDate);                        
                        document.getElementById('lrORrrNo').readOnly = true;
                        document.getElementById('lrDate').readOnly = true;
                    });
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at LR/RR NO, date : " + textStatus + " " + jqXHR);
                }
            });
            // FETCHING DATA FROM PO RELEASE BY PO NO
            $.ajax({
                url: "getPoReleaseDeByPoNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: Po_numbER}),
                success: function (podata) {                    
                    appendSection();
                    var poresult = $.parseJSON(podata);                    
                    $.each(poresult, function (k, p) {
                        
                        $("#section").val(p.section);
                        $('#section').selectpicker('refresh');
                        $("#itemsCovered").val(p.itemsCovered);
                        $("#remarks").val(p.remarks);                        
                        document.getElementById('remarks').readOnly = true;
                    });
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at section, item covered, remarks: " + textStatus + " " + jqXHR);
                }
            });
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
        var $items = $('#mateReceiptType, #excessQuaAllow,#dbNumber,#date, #purchaseUnit,' +
                '#section, #plant, #poLastNo, #poLNoDate, #delayRchaNo,' +
                '#delayDate, #lrORrrNo, #lrDate, #lrClearance,' +
                '#transportCharges, #tranChaType, #packingCharges,' +
                '#otherDemCha, #itemsCovered, #remarks,' +
                '#locInRecSec, #userDbId');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var jsonOb = JSON.stringify([obj]);        
        var parObj = JSON.parse(jsonOb); 
        
        var big = [];
        var cur_dbno = $("#dbNumber").val();
         //alert(cur_pono);
        var itemObj = saveItems(cur_dbno); 
        big = JSON.stringify({ mateReceiptDTO : parObj,meteReceItemsDTO : itemObj});            
        
        //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveMaterialRecipet.htm",
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
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();
                        
                    $("#materialReForm")[0].reset();                    
                    $("#section").selectpicker("refresh");
                    $("#poLastNo").selectpicker("refresh");
                    $("#indentor").selectpicker("refresh");                    
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
        url: "materialReceiptRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(materiasl){            
            var result = $.parseJSON(materiasl);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.dbNumber+"</td><td>"+v.date+"</td>"+  
                           "<td>"+v.purchaseUnit+"</td><td>"+v.section+"</td>"+
                           "<td>"+v.plant+"</td><td>"+v.remarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.materialRecID + ">Edit </button></td> ";
                                            
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
                        {title: "DB NO", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "Purchase Unit", width: '13%'},
                        {title: "Section", width: '13%'},
                        {title: "Plant", width: '13%'},
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
                    $("#materialReForm")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getMaterialRecptReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({recpt_id: this.value}),
                        success: function (html) {                              
                            var result = $.parseJSON(html);
                            $.each(result, function (k, v) {                                
                                $("#materialRecID").val(v.materialRecID); 
                                $("#mateReceiptType").val(v.mateReceiptType);
                                $("#excessQuaAllow").val(v.excessQuaAllow);  
                                $("#dbNumber").val(v.dbNumber); 
                                $("#date").val(v.date); 
                                $("#purchaseUnit").val(v.purchaseUnit); 
                                $("#section").val(v.section); 
                                $('#section').selectpicker('refresh');
                                $("#plant").val(v.plant); 
                                
                                $("#poLastNo").val(v.poLastNo);
                                $('#poLastNo').selectpicker('refresh');
                                $("#poLNoDate").val(v.poLNoDate);
                                document.getElementById('poLNoDate').readOnly = false;
                                $("#delayRchaNo").val(v.delayRchaNo);  
                                $("#delayDate").val(v.delayDate);                               
                                $("#lrORrrNo").val(v.lrORrrNo); 
                                $("#lrDate").val(v.lrDate); 
                                $("#lrClearance").val(v.lrClearance); 
                                
                                $("#transportCharges").val(v.transportCharges);
                                $("#tranChaType").val(v.tranChaType);  
                                $("#packingCharges").val(v.packingCharges);                                
                                $("#otherDemCha").val(v.otherDemCha); 
                                $("#itemsCovered").val(v.itemsCovered);
                                document.getElementById('itemsCovered').readOnly = false;
                                $("#remarks").val(v.remarks);
                                document.getElementById('remarks').readOnly = false;
                                $("#locInRecSec").val(v.locInRecSec);
                                $("#userDbId").val(v.userDbId);
                                document.getElementById('lrORrrNo').readOnly = false;
                                document.getElementById('lrDate').readOnly = false;
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_items").empty();
                            $("#form_table").hide();   
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
        $("#materialReForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#materialRecID, #mateReceiptType, #excessQuaAllow,#dbNumber,#date, #purchaseUnit,' +
                '#section, #plant, #poLastNo, #poLNoDate, #delayRchaNo,' +
                '#delayDate, #lrORrrNo, #lrDate, #lrClearance,' +
                '#transportCharges, #tranChaType, #packingCharges,' +
                '#otherDemCha, #itemsCovered, #remarks,' +
                '#locInRecSec, #userDbId');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateMaterialReceiptDe.htm",
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
                    $("#materialReForm")[0].reset();
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
        $("#stic_items").empty();
        $("#form_table").hide(); 
        show_record();
        close_message();
    });
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        close_message();
        $("#materialReForm")[0].reset();                    
        $("#section").selectpicker("refresh");
        $("#poLastNo").selectpicker("refresh");
        $("#indentor").selectpicker("refresh");  
        $("#errorpoLastNo").empty();
        DbNmberIncre();
        $("#stic_items").empty();
        $("#form_table").show(); 
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
    //START: LR CLEARANCE POPUP
    //******************************************************************            
    function lrClearanceButton() {
        //$(".modal-body").empty(); 
        var PONOOmberr = $("#poLastNo").val();        
        $("#modal-bodyM").empty();
        $.ajax({
            url: "getLrDetailsByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PONOOmber: PONOOmberr}),
            success: function (podet) {
                //alert(podet);
                var result = $.parseJSON(podet);
                if ($.isEmptyObject(result)) {
                    $("#errorpoLastNo").append("This PO number has no LR record. ").css({"color": "red"});
                } else {
                    $("#cstModal").modal("show");
                    var slno = 1;
                    var lrno = 1;
                    var lrDa = 1;
                    var lrCleDa = 1;
                    var ckslno = 1;
                    var final;
                    $.each(result, function (k, v) {
                        final = final + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckCle' value='" + (ckslno++) + "' /></td>" +
                                "<td><input type='text' name='' class='painputM' value='" + (slno++) + "' readonly/></td>" +
                                "<td><input type='text' name='' class='painputM lrNumberr" + (lrno++) + "' value='" + v.lrNumber + "' readonly/></td>" +
                                "<td><input type='text' name='' class='painputM lrDatee" + (lrDa++) + "' value='" + v.lrDate + "' readonly/></td>" +
                                "<td><input type='date' name='' class='painputM lrClearanceDate" + (lrCleDa++) + "' /></td>" +
                                "</tr>";
                    });
                    $("#modal-bodyM").html("<table class='popup-table'>" + final + "</table>");
                }

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //END: LR CLEARANCE POPUP
    //******************************************************************   
    
    //******************************************************************
    //START: LR CLEARANCE save
    //******************************************************************

    $("#saveLrClearance").click(function () {
        var PONOOmbe = $("#poLastNo").val();

        var vall = [];
        var myData = [];
        $('#chckCle:checked').each(function (i) {
            vall[i] = $(this).val();

            var obj = {
                poNumBer: PONOOmbe,
                lrNumberr: $(".lrNumberr" + vall[i]).val(),
                lrDatee: $(".lrDatee" + vall[i]).val(),
                lrClearanceDate: $(".lrClearanceDate" + vall[i]).val()
            };
            myData.push(obj);

        });

        if (vall.length > 1) {
            alert("Please select only one row. ");
        } else if (vall.length < 1) {
            alert("Please select one row. ");
        } else {
           
            var result = $.parseJSON(JSON.stringify(myData));
            $.each(result, function (k, x) {
                $("#lrORrrNo").val(x.lrNumberr);
                $("#lrDate").val(x.lrDatee);
            });
            
            $.ajax({
                
                dataType: "json",
                url: "saveMaterialReceCleDa.htm",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify(myData),
                type: 'POST',
                async: false,
                success: function (data) {
                    $("#errorpoLastNo").empty();
                    var data = parseInt(data);
                if (data === 1) {
                    $('#cstModal').modal('toggle');
                    $("#errorpoLastNo").append("LR Clearance success ").css({"color": "green"});                  
                }else if(data === -1){
                    $("#errorpoLastNo").append("LR Clearance fail. ").css({"color": "red"});  
                } else {
                   $("#errorpoLastNo").append("LR Clearance fail. ").css({"color": "red"});  
                }
                },error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error");
                }
            });
        }


    });

    //******************************************************************
    //END: LR CLEARANCE save
    //******************************************************************
    
    //******************************************************************
    //START: DB NUMBER INCREAMENT
    //******************************************************************
    function DbNmberIncre() {
        $("#dbNumber").val('');
        $.ajax({
            url: "getDbNumIncreament.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (dbincre) {                
                document.getElementById('dbNumber').readOnly = true;
                $("#dbNumber").val(parseInt(dbincre));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //******************************************************************
    //END:  DB NUMBER INCREAMENT
    //******************************************************************
    
    
    //******************************************************************
    //START:  GENERATE TABLE FOR PO ENTRY ITEMS
    //******************************************************************
    
    function generatePoReleaseTable(po_no) {        
        $("#stic_items").empty();
        $.ajax({
            url: "getPoReleaseItemsDeByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PONumber: po_no}),
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
                var itemH = 1;
                var pf = 1;   
                var qtyOrdH = 1;
                var Ord = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+w.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                       
                        
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='itemCode" + (code++) + "' value='"+v.itemDes+"' /></td>" +
                        
                        "<td><input type='text' name='' class='painput group" + (group++) + "' value='"+v.groupCode+"' /></td>" +
                        "<td><input type='text' name='' class='painput cardNo" + (store++) + "' value='"+v.storeCardNo+"' /></td>" +
                        
                        
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unit" + (qtyOrd++) + "' value='"+v.unitQty+"' /></td>"+
                        
                        "<td><input type='text' name='' class='painput order" + (Ord++) + "' value='"+v.qtyOrder+"' /></td>"+
                        "<td><input type='text' name='' class='painput dispatchQty" + (unitQty++) + "' value='' /></td>"+
                        "<td><input type='text' name='' class='painput accepted" + (lump++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput updataed" + (pf++) + "' value='' /></td>" +
                        
                        "</tr>";
                });
                $("#stic_items").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for PO release items table  ;"+textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveItems(dbNUM){
          
          var val = [];
          var myDataObj = [];
        $('#chckItems:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    
                    dbNumBer: dbNUM,                    
                    itemCode: $(".itemCode"+val[i]).val(),                    
                    group: $(".group"+val[i]).val(),
                    cardNo: $(".cardNo"+val[i]).val(),
                    unit: $(".unit"+val[i]).val(),
                    order: $(".order"+val[i]).val(),
                    dispatchQty: $(".dispatchQty"+val[i]).val(),
                    accepted: $(".accepted"+val[i]).val(),   
                    updataed: $(".updataed"+val[i]).val()
            };
            myDataObj.push(obj);
            
        });
            
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
    
    
});//End document