/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#mtRequisition").addClass("active");

    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    
    $("#form_record").hide();
    $("#form_page").show();
    RequisitionNumIncre();
    var userValue = $("#userHidVal").val();
    generateIndenter();
    //alert(userValue);
    appendCategory();
    $("#indentor").val(userValue);
    $("#indentor").selectpicker("refresh"); 
    document.getElementById('releasedBy').type = 'hidden';
    $("#releasedByLabel").hide();
    $("#releasedBy").val('0');
    
    //***********************************************
    //Getting section from section_master table
    //***********************************************  
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
        
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poNumber, #pleaseSelIssType, #whetherDispIss,#releaseSecReq,#otherSecName, #secOrDivision,' +
                '#jobAllocation, #itemCovered, #toBeDeliAt, #issueType, #reqNumber,' +
                '#date, #vrDate, #disposalIssue, #fromTo,' +
                '#woNumber, #woDate, #releasrReqd, #fromSection, #deliveredAt,' +
                '#itemSelected, #indentor, #section, #recdBy,' + 
                '#authorisedBy, #releasedBy, #issuedBy');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_requi = $("#reqNumber").val();
            var cur_category = $("#category").val();
            //alert(cur_requi+"--"+cur_category);
            var itemObj = saveItems(cur_requi, cur_category); 
            big = JSON.stringify({ requiDTO : parObj, requiItemsDTO : itemObj});            
        
            //alert(big);
         
        $.ajax({
            dataType : "json",
            url : "saveMaterialRequiSi.htm",
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
                    $("#mate_requ_form")[0].reset();
                    $("#mate_requ_Secform")[0].reset();                    
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
        url: "getMaterialRequiRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(rqui){            
            var result = $.parseJSON(rqui);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.reqNumber+"</td><td>"+v.date+"</td>"+  
                           "<td>"+v.woNumber+"</td><td>"+v.vrDate+"</td>"+
                           "<td>"+v.indentor+"</td><td>"+v.section+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.materialReqId + ">Edit </button></td> ";
                                            
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
                        {title: "Requisition NO", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "WO No", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "Indenter", width: '13%'},
                        {title: "Section", width: '13%'},
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
                    $("#mate_requ_form")[0].reset();
                    $("#mate_requ_Secform")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getMaterialRquiReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({Requisition_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#materialReqId").val(v.materialReqId);
                                $("#poNumber").val(v.poNumber);
                                $("#pleaseSelIssType").val(v.pleaseSelIssType);
                                $("#whetherDispIss").val(v.whetherDispIss);  
                                $("#releaseSecReq").val(v.releaseSecReq); 
                                $("#otherSecName").val(v.otherSecName); 
                                $("#secOrDivision").val(v.secOrDivision); 
                                $("#jobAllocation").val(v.jobAllocation); 
                                $("#itemCovered").val(v.itemCovered); 
                                $("#toBeDeliAt").val(v.toBeDeliAt);
                                $("#issueType").val(v.issueType);
                                
                                $("#reqNumber").val(v.reqNumber);
                                $("#date").val(v.date);                                 
                                $("#vrDate").val(v.vrDate); 
                                $("#disposalIssue").val(v.disposalIssue); 
                                $("#fromTo").val(v.fromTo); 
                                $("#woNumber").val(v.woNumber); 
                                $("#woDate").val(v.woDate); 
                                $("#releasrReqd").val(v.releasrReqd);
                                $("#fromSection").val(v.fromSection);
                                
                                $("#deliveredAt").val(v.deliveredAt);
                                $("#itemSelected").val(v.itemSelected);                                
                                generateIndenter();
                                $("#indentor").val(v.indentor);
                                $("#indentor").selectpicker("refresh");
                                $("#section").val(v.section);
                                $("#section").selectpicker("refresh");
                                $("#recdBy").val(v.recdBy); 
                                $("#authorisedBy").val(v.authorisedBy);                                 
                                $("#releasedBy").val(v.releasedBy);
                                $("#issuedBy").val(v.issuedBy);
                                
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_items").empty();
                            $("#table_form").hide();
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
        $("#mate_requ_form")[0].reset();
        $("#mate_requ_Secform")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#materialReqId, #poNumber, #pleaseSelIssType, #whetherDispIss,#releaseSecReq,#otherSecName, #secOrDivision,' +
                '#jobAllocation, #itemCovered, #toBeDeliAt, #issueType, #reqNumber,' +
                '#date, #vrDate, #disposalIssue, #fromTo,' +
                '#woNumber, #woDate, #releasrReqd, #fromSection, #deliveredAt,' +
                '#itemSelected, #indentor, #section, #recdBy,' + 
                '#authorisedBy, #releasedBy, #issuedBy');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateMaterialRquisition.htm",
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
                    $("#mate_requ_form")[0].reset();
                    $("#mate_requ_Secform")[0].reset();
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
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        close_message();
        RequisitionNumIncre();
        $("#stic_items").empty();
        $("#table_form").show();
        $("#category").selectpicker("refresh");
        $("#mate_requ_form")[0].reset();
        $("#mate_requ_Secform")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
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
    //***START;  RELEASED FROM OTHER SECTION************************
    //**************************************************************************
    $("#releaseSecReq").on('change', function (){
        var relesedby = this.value;        
        $("#errreleaseSecReq").empty();
        if(relesedby === 'null'){
            $("#errreleaseSecReq").append("Please select option");
            document.getElementById('releasedBy').type = 'hidden';
            $("#releasedByLabel").hide();
            $("#releasedBy").val('0');
        }else if(relesedby === 'yes'){            
            document.getElementById('releasedBy').type = 'text';
             $("#releasedByLabel").show();
             $("#releasedBy").val('');
        }else if(relesedby === 'no'){
            document.getElementById('releasedBy').type = 'hidden';
             $("#releasedByLabel").hide();
             $("#releasedBy").val('0');
        }
        
    });
    
    //**************************************************************************
    //***END;  RELEASED FROM OTHER SECTION************************
    //**************************************************************************
    
    //******************************************************************
    //START: DB NUMBER INCREAMENT
    //******************************************************************
    function RequisitionNumIncre() {
        $("#reqNumber").val('');
        $.ajax({
            url: "getRequiSitionNoIncrea.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (dbincre) {                
                document.getElementById('reqNumber').readOnly = true;
                $("#reqNumber").val(parseInt(dbincre));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //******************************************************************
    //END:  DB NUMBER INCREAMENT
    //******************************************************************
    
    
    //******************************************************************
    //START:  APPEND PO NUMBER AND FETCHING DATA 
    //******************************************************************
//    $.ajax({
//        url: "poEntryRecord.htm",
//        type: "POST",
//        cache: false,
//        async: false,
//        success: function (podata) {
//            alert(podata);
//            $("#poNumber").empty();
//            var poresult = $.parseJSON(podata);
//            $("#poNumber").append("<option value='null'>select</option>");
//            $.each(poresult, function (k, p) {
//                $("#poNumber").append("<option value='" + p.poNumber + "'>" + p.poNumber + "</option>");
//                $('#poNumber').selectpicker('refresh');
//            });
//        }, error: function (jqXHR, textStatus, errorThrown) {
//            alert("Error : " + textStatus + " " + jqXHR);
//        }
//    });
    
    $("#poNumber1").change('on', function () {
        $("#errorPoNumber").empty();
        var po_no = this.value;
        if(po_no === "0" || po_no === ""){            
            $("#errorPoNumber").append("Please select option").css({"color":"red"});
        }else if(po_no === 'null'){
            $("#errorPoNumber").append("Please select option").css({"color":"red"});
        }else{
            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: po_no}),
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
                                //alert(data);
                                $("#indentor").empty();
                                var result = $.parseJSON(data);
                                $.each(result, function (k, w) {
                                    $("#indentor").append("<option value='" + w.employeeProfileId + "'>(" + w.empProfileDTo.icNumber + ")" + w.empProfileDTo.firstName + "  " + w.empProfileDTo.lastName + "</option>");
                                });
                            }, error: function (jqXHR, textStatus, errorThrown) {
                                alert("Error");
                            }

                        });
                        

                    });

                }, complete: function (data) {
                }
            });
        }
    });
    //******************************************************************
    //END:  APPEND PO NUMBER AND FETCHING DATA
    //******************************************************************
    function generateIndenter() {
        
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

    }
    
    
    //******************************************************************
    //START:  APPEND CATEGORY LIST AND TABLE ITEMS
    //******************************************************************
    function appendCategory() {
        $.ajax({
            url: "getCategoryAllDetails.htm",
            type: "POST",
            cache: false,
            //async: false,            
            success: function (vategory) {
                //alert(vategory)
                var result = $.parseJSON(vategory);
                $("#category").empty();
                $("#category").append("<option value='null'>Select</option>");
                $.each(result, function (k, v) {
                    //alert(v.sectionCode);			
                    $("#category").append("<option value='" + v.categoryID + "'>(" 
                            + v.categoryCode + ")" + v.categoryName + "</option>");
                    $("#category").selectpicker("refresh");
                });

            }, complete: function (data) {
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("Error while getting category details. "+textStatus);
            }
        });
    }
    
    $("#category").change('on', function () {
        $("#errorCategory").empty();
        var category_id = this.value;
        if(category_id === "0" || category_id === ""){            
            $("#errorCategory").append("Please select option").css({"color":"red"});
        }else if(category_id === 'null'){
            $("#errorCategory").append("Please select option").css({"color":"red"});
        }else{
            $("#stic_items").empty();
            $.ajax({
            url: "getItemsDeByCategoryId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({Category_Id: category_id}),
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
                    //alert("item : "+v.unitDTO.unitName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput store" + (code++) + "' value='' /></td>" +
                        "<td><input type='text' name='' class='painput groupCode" + (group++) + "' value='' /></td>" + 
                        "<td><input type='text' name='' class='painput cardNo" + (store++) + "' value='' /></td>" +
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='item" + (item++) + "' value='"+v.itemCode+"' /></td>" +
                        "<td><textarea name='' class='painput itemDes" + (unitQty++) + "'>"+v.description+"</textarea></td>"+
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitDTO.unitName+"' />"+
                            "<input type='hidden' name='' class='unit" + (qtyOrd++) + "' value='"+v.unitDTO.unitCode+"' /></td>"+
                        "<td><input type='text' name='' class='painput qtyRged" + (ace++) + "' value='' /></td>"+
                        "<td><input type='text' name='' class='painput qtyIssued" + (rege++) + "' value='' /></td>"+
                        
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
        }
    });
    
    function saveItems(requiNo, categoryID) {

        var val = [];
        var myDataObj = [];
        $('#chckItems:checked').each(function (i) {
            val[i] = $(this).val();
            var obj = {
                requisitionNo: requiNo,
                categoryId: categoryID,
                store: $(".store" + val[i]).val(),
                groupCode: $(".groupCode" + val[i]).val(),
                cardNo: $(".cardNo" + val[i]).val(),
                item: $(".item" + val[i]).val(),
                itemDes: $(".itemDes" + val[i]).val(),
                unit: $(".unit" + val[i]).val(),
                qtyRged: $(".qtyRged" + val[i]).val(),
                qtyIssued: $(".qtyIssued" + val[i]).val()
            };
            myDataObj.push(obj);

        });

        return  myDataObj;
    }

    //******************************************************************
    //END:  APPEND CATEGORY LIST AND TSBLE ITEMS
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
    
    
    }); // END DOCUMENT
