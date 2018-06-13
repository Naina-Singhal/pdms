/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    

    $("#navigation li a").removeClass("active");
    $("#despatchUl").css("display", "block");
    $("#despapostt").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    DisNmberIncre();
    
     //***********************************************
    //Getting file numbers  table
    //***********************************************
    $.ajax({
        url: "getFileNumbers.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#fileNo").empty();
            $("#fileNo").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#fileNo").append("<option value='" + v.fileNoFrMapp +
                        "'>" + v.fileNoFrMapp + "</option>");
            });
            $("#fileNo").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    
    //******************************************************************
    //START: DISPATCH NUMBER INCREAMENT
    //******************************************************************
    function DisNmberIncre() {
        $("#dispatchNo").val('');
        $.ajax({
            url: "getDispatchNoIncreament.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (dbincre) {                
                document.getElementById('dispatchNo').readOnly = true;
                $("#dispatchNo").val(parseInt(dbincre));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at Dispatch Number:" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //******************************************************************
    //END:  DISPATCH NUMBER INCREAMENT
    //******************************************************************
    
    //***********************************************
    //Getting Type Of Dispatch from master table
    //***********************************************
    $.ajax({
        url: "getDispatchMaRecord.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#typeOfDispatch").empty();
            $("#typeOfDispatch").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#typeOfDispatch").append("<option value='" + v.code +
                        "'>("+v.code+")" + v.name + "</option>");
            });
            $("#typeOfDispatch").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    
    $("#amount").keyup(function (){
        var amt = this.value;
        var taxx =  $("#serviceTax").val();   
        if(amt === '' || amt <= 0){
        
        }else{
            $("#errorAmount").empty();
            $("#total").val((parseFloat(amt) + parseFloat(taxx)).toFixed(2));
        }
    });
    
    $("#serviceTax").keyup(function (){
        var amount = $("#amount").val();       
        $("#errorAmount").empty();
        
        var tax = this.value;
        
        if(amount === '' || amount <= 0){
            $("#errorAmount").append("Please Enter amount");
        }else{
            $("#total").val((parseFloat(amount) + parseFloat(tax)).toFixed(2));
        }
        
    });
    
    
    //**************************************************************************
    //START: DATA FETCHING FROM PO ENTRY DEPENDS ON File NUMBER
    //**************************************************************************
    $("#fileNo").on('change', function () {
        $("#errorFileNumber").empty();
        $("#stic_items").empty();
        var fiNo = parseInt(this.value);
        if (fiNo <= 0 || isNaN(fiNo) === true || fiNo === 'null') {
            $("#errorFileNumber").append("Please select File Number. ");
            $("#stic_items").empty();
            $("#stic_items_two").empty();
        } else {
            vendor_table(fiNo);
            $("#stic_items").empty();
            $.ajax({
                url: "getPoEntryDeByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({filNumber: fiNo}),
                success: function (details) {
                    //alert(details);
                    var result = $.parseJSON(details);
                    if ($.isEmptyObject(result)) {
                        $("#stic_items").empty();
                        $("#stic_items").append("This number has no data. ").css({"color": "#0000009c"});
                    } else {
                        var slno = 1;
                        var ckslno = 1;
                        var group = 1;
                        var store = 1;
                        var qtyOrd = 1;
                        var itemH = 1;
                        var qtyOrdH = 1;
                        var stor = 1;
                        var final1;
                        $.each(result, function (k, v) {
                            //alert("item : " + v.venderName);
                            final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                                    "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                                    "<td><input type='text' name='' class='painput ponumber" + (itemH++) + "' value='" + v.poNumber + "' /></td>" +
                                    "<td><input type='text' name='' class='painput posrno" + (group++) + "' value='" + v.referenceNo + "' /></td>" +
                                    "<td><input type='text' name='' class='painput cardNo" + (store++) + "' value='" + v.vendorObj.vendorName + "' /></td>" +
                                    "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='" + v.vendorObj.vendorCity + "' />" +
                                    "<input type='hidden' name='' class='vendorcode" + (qtyOrd++) + "' value='" + v.venderName + "' /></td>" +
                                    "<td><input type='date' name='' class='painput deliverydate" + (stor++) + "' value='' /></td>" +
                                    "</tr>";
                        });
                        $("#stic_items").html(final1).css({"color": "#0000009c"});

                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error for PO Entry items table  ;" + textStatus + " :" + errorThrown);

                }, complete: function (data) {

                }, beforeSend: function () {
                    //return confirm("Are you sure you want to submit ?");

                }
            });


        }
    });
    
    //**************************************************************************
    //END: DATA FETCHING FROM PO ENTRY DEPENDS ON File NUMBER
    //**************************************************************************
    
    function vendor_table(fileNo) {
        $("#stic_items_two").empty();
        $.ajax({
            url: "getVenIndMappDeByFile.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({file_no: fileNo}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if ($.isEmptyObject(result)) {
                    $("#stic_items_two").empty();
                    $("#stic_items_two").append("This number has no data. ").css({"color": "#0000009c"});
                } else {
                    var slno = 1;
                    var ckslno = 1;
                    var group = 1;
                    var store = 1;
                    var qtyOrd = 1;
                    var itemH = 1;
                    var qtyOrdH = 1;
                    var stor = 1;
                    var final1;
                    $.each(result, function (k, v) {
                        //alert("item : " + v.venderName);
                        final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItemsTwo' value='" + (ckslno++) + "' /></td>" +
                                "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                                "<td><textarea type='text' name='' class='painput ponumberr" + (itemH++) + "'>" + v.itemObj.itemName + "</textarea>" +
                                "<input type='hidden' name='' class='itemid" + (qtyOrd++) + "' value='" + v.itemObj.itemID + "' /></td>" +
                                "<td><textarea type='text' name='' class='painput posrnor" + (group++) + "'>" + v.vendorObj.vendorName + "</textarea></td>" +
                                
                                "<td><input type='text' name='' class='painput unitHr" + (qtyOrdH++) + "' value='" + v.vendorObj.vendorCity + "' />" +
                                "<input type='hidden' name='' class='vendorid" + (store++) + "' value='" + v.vendorObj.vendorID + "' /></td>" +
                                "<td><input type='text' name='' class='painput amount" + (stor++) + "' value='' /></td>" +
                                "</tr>";
                    });
                    $("#stic_items_two").html(final1).css({"color": "#0000009c"});

                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at vendor Table ;" + textStatus + " :" + errorThrown);

            }, complete: function (data) {

            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });

    }

    
    
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#dispatchNo, #fileNo, #group, #nature, #amount,#typeOfDispatch, #serviceTax, #total, #balance');
               
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var val = [];
        var myDataObj = [];
        $('#chckItems:checked').each(function (i) {
            val[i] = $(this).val();
            var obj = {
                dispatchno: $("#dispatchNo").val(),
                filenumber: $("#fileNo").val(),
                ponumber: $(".ponumber" + val[i]).val(),
                posrno: $(".posrno" + val[i]).val(),
                vendorcode: $(".vendorcode" + val[i]).val(),
                deliverydate: $(".deliverydate" + val[i]).val()
            };
            myDataObj.push(obj);

        });
        
        var valT = [];
        var myDataObjT = [];
        $('#chckItemsTwo:checked').each(function (i) {
            valT[i] = $(this).val();
            var objT = {
                dispatchno: $("#dispatchNo").val(),
                filenumber: $("#fileNo").val(),
                itemid: $(".itemid" + valT[i]).val(),
                vendorid: $(".vendorid" + valT[i]).val(),
                amount: $(".amount" + valT[i]).val()
            };
            myDataObjT.push(objT);

        });
            
        var jsonOb = JSON.stringify([obj]);        
        var parObj = JSON.parse(jsonOb);
        var inwardJson = [];        
        
        inwardJson = JSON.stringify({ postDTO : parObj, postPoDTO : myDataObj, postVenDTO: myDataObjT});            
        
        alert(inwardJson);
        $.ajax({
            dataType : "json",
            url : "savePostEntry.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : inwardJson,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#Post_entry_Form")[0].reset();
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
        url: "getPostEntryRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(postEntry){            
            var result = $.parseJSON(postEntry);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.dispatchNo+"</td><td>"+v.fileNo+"</td>"+  
                           "<td>"+v.group+"</td><td>"+v.amount+"</td>"+
                           "<td>"+v.serviceTax+"</td><td>"+v.total+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.postEntryId + ">Edit </button></td> ";
                                            
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
                        {title: "Dispatch NO", width: '13%'},
                        {title: "File No", width: '13%'},
                        {title: "Group", width: '13%'},
                        {title: "Amount", width: '13%'},
                        {title: "Service Tax", width: '13%'},
                        {title: "Total", width: '13%'},
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
                    $("#Post_entry_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getPostEnReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({postEn_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#postEntryId").val(v.postEntryId);
                                $("#dispatchNo").val(v.dispatchNo);
                                $("#fileNo").val(v.fileNo); 
                                $("#fileNo").selectpicker("refresh");
                                $("#group").val(v.group); 
                                $("#nature").val(v.nature); 
                                $("#amount").val(v.amount); 
                                $("#typeOfDispatch").val(v.typeOfDispatch); 
                                $("#typeOfDispatch").selectpicker("refresh");
                                $("#serviceTax").val(v.serviceTax); 
                                $("#total").val(v.total); 
                                $("#balance").val(v.balance); 
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_items").empty();
                            $("#stic_items_two").empty();
                            $("#table_one").hide();
                            $("#table_two").hide();
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
        $("#Post_entry_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#postEntryId, #dispatchNo, #fileNo, #group, #nature, #amount,'+
                '#typeOfDispatch, #serviceTax, #total, #balance');
        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updatePostEntry.htm",
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
                    $("#Post_entry_Form")[0].reset();
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
        $("#stic_items_two").empty();
        $("#table_one").hide();
        $("#table_two").hide();
        $(".selectpicker").selectpicker("refresh");
    });
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#stic_items").empty();
        $("#stic_items_two").empty();
        $("#table_one").show();
        $("#table_two").show();
        $("#fileNo").val('null');
        $("#fileNo").selectpicker("refresh");
        $("#typeOfDispatch").val('null');
        $("#typeOfDispatch").selectpicker("refresh");
        $("#Post_entry_Form")[0].reset();
        DisNmberIncre();
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

