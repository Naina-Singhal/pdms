/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    
    display_ICNo();
    
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#insperecord").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
   
    
    var user_id_login = $("#authorUserid").val();
    //***********************************************
    //Getting section from section_master table
    //***********************************************
        
    //***********************************************
    //Getting PO number, pO value from po_entry table
    //***********************************************
    $.ajax({
        url: "getPoNoFrPOEntry.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v8) {
                    //alert(v8.poNumber+"--"+v8.poValue);			
			$("#poLastNo").append("<option value='"+v8.poNumber+"'>"+v8.poNumber+"</option>");
                        //$("#poValue").append("<option value='"+v8.poValue+"'>"+v8.poValue+"</option>");
                    });	
										
		},error : function(jqXHR,textStatus,errorThrown ){                    
                    alert("Error :"+jqXHR.status+", "+errorThrown);
                },complete: function (data) {
		}
	});
    //******************************************************************
    //Getting GR/RR no for increment by one.
    //******************************************************************
    function display_ICNo(){
      $.ajax({
        url: "getICNoIncrement.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(poNo){
                //alert(poNo);
		$("#grOrrNumber").val(poNo);	
                document.getElementById('grOrrNumber').readOnly = true;
		},error : function(jqXHR,textStatus,errorThrown ){                    
                    alert("Error :"+jqXHR.status+", "+errorThrown);
                },complete: function (data) {
		}
	}); 
    }  
    
    
    //***********************************************
    //Getting purchase order details by file no
    //***********************************************
    $("#poLastNo").change('on', function () {
        $("#errorPOLastNo").empty();
        $("#poDate").val("");
        if (this.value === "0" || this.value === "") {
            $("#errorPOLastNo").append("Please select option").css({"color": "red"});
        } else {

            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: $("#poLastNo").val()}),
                success: function (htmlH) {
                    var result = $.parseJSON(htmlH);
                    //alert(result);
                    $.each(result, function (k, v) {
                        //alert(v.placeOfDelivery);	                   
                        $("#poDate").val(v.preparedDate);
                    });

                }, complete: function (data) {
                }
            });
        }
    });
    
    
    
    //******************************************************************
    //Getting DB Number record from material receipt data
    //******************************************************************

    var User_Id = $("#User_ID").val();    
    $.ajax({
        url: "getDbNoFrMeteReceDeByUser.htm",
        type: "POST",
        cache: false,
        async: false,
        data: ({User_Id: User_Id}),
        success: function (merere) {            
            $("#errordbNumber").empty(); 
            var res = $.parseJSON(merere);
            if ($.isEmptyObject(res)) {
                $("#errordbNumber").append("This user has no DB numbers data. ").css({"color":"red"});
            } else {
                $.each(res, function (k, w) {
                    $("#dbNumber").append("<option value='" + w.dbNumber + "'>" + w.dbNumber + "</option>");
                    $("#dbNumber").selectpicker("refresh");
                });
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error :" + jqXHR.status + ", " + errorThrown);
        }
    });
    
    $("#dbNumber").on('change', function () {
        $("#errordbNumber").empty();
        $("#dbDate").val("");
        $("#section").empty();
        $("#dbDate").val('');
        $("#poLastNo").val('');
        $("#poDate").val('');
        var Db_NuMber = this.value;
        if (Db_NuMber === "" || Db_NuMber === 'null') {
            $("#stic_items").empty();
            $("#errordbNumber").append("Please select DB number").css({"color": "red"});
        } else {

            $.ajax({
                url: "getDbNoReFromMeteRec.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({DbNumber: Db_NuMber}),
                success: function (dbno) {
                    //alert(dbno);
                    var resultd = $.parseJSON(dbno);
                    if ($.isEmptyObject(resultd)) {
                        $("#errordbNumber").append("There is no data.").css({"color": "red"});
                    } else {
                        generatePoReleaseTable(Db_NuMber);
                        $.each(resultd, function (k, d) {                                                   
                            $("#dbDate").val(d.date);
                            $("#poLastNo").val(d.poLastNo);
                            $("#poDate").val(d.poLNoDate);
                            $("#itemsCovered").val(d.itemsCovered);
                            document.getElementById('dbDate').readOnly = true;
                            document.getElementById('poLastNo').readOnly = true;
                            document.getElementById('poDate').readOnly = true;                            
                            $.ajax({
                                url: "getSection.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({uid: "dummy"}),
                                success: function (htmlH) {
                                    var result = $.parseJSON(htmlH);
                                    $.each(result, function (k, v) {                                        
                                        if(d.section === v.sectionCode){
                                            $("#section").append("<option value='"+v.sectionCode+"'>("+v.sectionCode+")"+v.sectionName+"</option>");
                                        }
                                    });

                                }, error: function (jqXHR, textStatus, errorThrown) {
                                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                                }, complete: function (data) {
                                }
                            });
                            
                        });
                    }
                    appendInspectedBy(user_id_login);
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }, complete: function (data) {
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
        var $items = $('#dbNumber, #dbDate,' +
                '#section, #plant, #purchaseUnit, #poLastNo, #poDate, #grOrrNumber,' +
                '#grOrrDate, #itemsCovered, #inspectedBy, #inspeCleaRemarks'); 
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_dbno = $("#dbNumber").val();
            //alert(cur_dbno);
            var itemObj = saveItems(cur_dbno); 
            big = JSON.stringify({ inspecDTO : parObj, inspecItemsDTO : itemObj});            
        
            //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveInspeClearance.htm",
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
                    $("#InspecCleaForm")[0].reset();
                    $("#form_second_part")[0].reset();
                    $("#section").selectpicker("refresh");                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                    display_ICNo();
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                }else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                    display_ICNo();
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
                    $(window).scrollTop(0);
                    display_ICNo();
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
        url: "inspectionCleaRecord.htm",
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
                           "<td>"+v.dbNumber+"</td><td>"+v.dbDate+"</td>"+  
                           "<td>"+v.section+"</td><td>"+v.plant+"</td>"+
                           "<td>"+v.purchaseUnit+"</td><td>"+v.inspeCleaRemarks+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.inspeClearID + ">Edit </button></td> ";
                                            
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
                        {title: "DB Date", width: '13%'},
                        {title: "section", width: '13%'},
                        {title: "Plant", width: '13%'},
                        {title: "Purchase Unit", width: '13%'},
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
                    $("#InspecCleaForm")[0].reset();
                    $("#section").empty(); 
                    close_message();
                    $.ajax({
                        url: "getInspeClearReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({inspeClear_id: this.value}),
                        success: function (html) {                              
                            var result = $.parseJSON(html);
                            $.each(result, function (k, v) {
                                $("#inspeClearID").val(v.inspeClearID);
                                $("#dbNumber").val(v.dbNumber);
                                $("#dbDate").val(v.dbDate);
                                $("#section").val(v.section);
                                $("#plant").val(v.plant);
                                $("#purchaseUnit").val(v.purchaseUnit);
                                $("#poLastNo").val(v.poLastNo);
                                $("#poDate").val(v.poDate);
                                $("#grOrrNumber").val(v.grOrrNumber);
                                $("#grOrrDate").val(v.grOrrDate);
                                $("#itemsCovered").val(v.itemsCovered);                                
                                appendInspectedBy(user_id_login);                                                             
                                $("#inspectedBy").append("<option value='" + v.inspectedBy + "' selected>" + v.inspectedBy + "</option>"); 
                                $("#inspeCleaRemarks").val(v.inspeCleaRemarks);
                                document.getElementById('dbDate').readOnly = false;
                                document.getElementById('poLastNo').readOnly = false;
                                document.getElementById('poDate').readOnly = false;                                
                                document.getElementById('grOrrNumber').readOnly = false;
                                $("#stic_items").empty();
                                $("#table_form").hide();
                                $.ajax({
                                    url: "getSection.htm",
                                    type: "POST",
                                    cache: false,
                                    async: false,
                                    data: ({uid: "dummy"}),
                                    success: function (htmlH) {
                                        var result = $.parseJSON(htmlH);
                                        $.each(result, function (k, w) {
                                            if (v.section === w.sectionCode) {
                                                $("#section").append("<option value='" + w.sectionCode + "' selected>(" 
                                                        + w.sectionCode + ")" + w.sectionName + "</option>");
                                            }else{
                                                $("#section").append("<option value='" + w.sectionCode + "'>(" 
                                                        + w.sectionCode + ")" + w.sectionName + "</option>");
                                            }
                                        });

                                    }, error: function (jqXHR, textStatus, errorThrown) {
                                        alert("Error :" + jqXHR.status + ", " + errorThrown);
                                    }, complete: function (data) {
                                    }
                                });
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#dbNumber").selectpicker("refresh");                                                      
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
        $("#InspecCleaForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#inspeClearID, #dbNumber, #dbDate,' +
                '#section, #plant, #purchaseUnit, #poLastNo, #poDate, #grOrrNumber,' +
                '#grOrrDate, #itemsCovered, #inspectedBy, #inspeCleaRemarks');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateInspecClearRe.htm",
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
                    $("#InspecCleaForm")[0].reset();
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
        $("#InspecCleaForm")[0].reset();
        $("#form_second_part")[0].reset();  
        $("#dbNumber").selectpicker("refresh");
        $("#section").empty();
        display_ICNo();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#stic_items").empty();
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
    //***START; APPEND INSPECTED BY NAME************************
    //************************************************************************** 

        function appendInspectedBy(authUserid) {
        $.ajax({
            url: "getEmpDeByEmpId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({employee_id: authUserid}),
            success: function (rele) {
                var resul = $.parseJSON(rele);
                if ($.isEmptyObject(resul)) {
                    alert("There is no employee data");
                } else {
                    $("#inspectedBy").empty();
                    $("#inspectedBy").append("<option value='null'>select</option>");
                    $.each(resul, function (k, w) {
                        $("#inspectedBy").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")"
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#inspectedBy").append("<option class='editableA' value='other'>other</option> ");

                    });
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }

    var initialText = $('.editableA').val();
    $('.editOptionA').val(initialText);
    $('#inspectedBy').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editableA').text();
        $('.editOptionA').val('');
        if (selected == "editableA") {
            $('.editOptionA').show();
            $('.editOptionA').keyup(function () {
                var editText = $('.editOptionA').val();
                $('.editableA').val(editText);
                $('.editableA').html(editText);
            });
        } else {
            $('.editOptionA').hide();
        }
    });
    //**************************************************************************
    //***END; APPEND INSPECTED BY NAME************************
    //************************************************************************** 
    
    
    //******************************************************************
    //START:  GENERATE TABLE FOR INSPECTION CLEARANCE
    //******************************************************************
    
    function generatePoReleaseTable(db_no) {        
        $("#stic_items").empty();
        $.ajax({
            url: "getMateReceiptItemsByDbNo.htm",
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
                        "<td><input type='text' name='' class='painput code" + (code++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput groupCode" + (group++) + "' value='"+v.group+"' /></td>" +
                        "<td><input type='text' name='' class='painput storeCardNo" + (store++) + "' value='"+v.cardNo+"' /></td>" +
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='item" + (item++) + "' value='"+v.itemCode+"' /></td>" +
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unit" + (qtyOrd++) + "' value='"+v.unit+"' /></td>"+
                        "<td><input type='text' name='' class='painput orderQty" + (unitQty++) + "' value='"+v.order+"' /></td>"+
                        "<td><input type='text' name='' class='painput dispatchQty" + (lump++) + "' value='"+v.dispatchQty+"' /></td>" +
                        "<td><input type='text' name='' class='painput update" + (pf++) + "' value='"+v.updataed+"' /></td>" +
                        "<td><input type='text' name='' class='painput acceptedQty" + (ace++) + "' value='"+v.accepted+"' /></td>" +
                        "<td><input type='text' name='' class='painput rejectedQty" + (rege++) + "' value='' /></td>" +
                        "</tr>";
                });
                $("#stic_items").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for material receipt items table  ;"+textStatus + " :" + errorThrown);

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
                    code: $(".code"+val[i]).val(),                    
                    groupCode: $(".groupCode"+val[i]).val(),
                    storeCardNo: $(".storeCardNo"+val[i]).val(),
                    item: $(".item"+val[i]).val(),
                    unit: $(".unit"+val[i]).val(),
                    orderQty: $(".orderQty"+val[i]).val(),
                    dispatchQty: $(".dispatchQty"+val[i]).val(),
                    update: $(".update"+val[i]).val(),
                    acceptedQty: $(".acceptedQty"+val[i]).val(),
                    rejectedQty: $(".rejectedQty"+val[i]).val()                    
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }

   
    //******************************************************************
    //END: GENERATE TABLE FOR INSPECTION CLEARANCE
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
                                    $("#inspectedBy").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: Item Covered TEMPLATE
    //******************************************************************   
   
    
    
    });//End Document
    
 