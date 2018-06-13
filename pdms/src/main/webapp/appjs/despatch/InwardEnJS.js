/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    

    $("#navigation li a").removeClass("active");
    $("#despatchUl").css("display", "block");
    $("#desinwarrd").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    SlNmberIncre();
    
    //******************************************************************
    //START: SL NUMBER INCREAMENT
    //******************************************************************
    function SlNmberIncre() {
        $("#slno").val('');
        $.ajax({
            url: "getSlNoInwaIncreament.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (dbincre) {                
                document.getElementById('slno').readOnly = true;
                $("#slno").val(parseInt(dbincre));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at Sl Number:" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //******************************************************************
    //END:  SL NUMBER INCREAMENT
    //******************************************************************
    
    //***********************************************
    //Getting file numbers 
    //***********************************************
    $.ajax({
        url: "getFileNumbers.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#fileNumber").empty();
            $("#fileNumber").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#fileNumber").append("<option value='" + v.fileNoFrMapp +
                        "'>" + v.fileNoFrMapp + "</option>");
            });
            $("#fileNumber").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    
    //***********************************************
    //Getting Type data from master table
    //***********************************************
    $.ajax({
        url: "getTypeMaRecord.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            var result = $.parseJSON(htmlH);
            $("#type").empty();
            $("#type").append("<option value='null'>Select</option>");
            $.each(result, function (k, v) {
                //alert(v.designationCode);			
                $("#type").append("<option value='" + v.code +
                        "'>("+v.code+")" + v.type + "</option>");
            });
            $("#type").selectpicker("refresh");
        }, complete: function (data) {
        }
    });
    
    
    //**************************************************************************
    //START: DATA FETCHING FROM PO ENTRY DEPENDS ON File NUMBER
    //**************************************************************************
    $("#fileNumber").on('change', function () {
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
        var $items = $('#slno, #fileNumber, #type, #receiveDate');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var val = [];
        var myDataObj = [];
        $('#chckItems:checked').each(function (i) {
            val[i] = $(this).val();
            var obj = {
                filenumber: $("#fileNumber").val(),
                ponumber: $(".ponumber" + val[i]).val(),
                posrno: $(".posrno" + val[i]).val(),
                vendorcode: $(".vendorcode" + val[i]).val()                
            };
            myDataObj.push(obj);

        });
        
        var valT = [];
        var myDataObjT = [];
        $('#chckItemsTwo:checked').each(function (i) {
            valT[i] = $(this).val();
            var objT = {
                filenumber: $("#fileNumber").val(),
                itemid: $(".itemid" + valT[i]).val(),
                vendorid: $(".vendorid" + valT[i]).val()                               
            };
            myDataObjT.push(objT);

        });

        var jsonOb = JSON.stringify([obj]);        
        var parObj = JSON.parse(jsonOb);
        var inwardJson = [];        
        
        inwardJson = JSON.stringify({ inwardDTO : parObj, inwardPoDTO : myDataObj, inwardVenDTO: myDataObjT});            
        
        alert(inwardJson);
        $.ajax({
            dataType : "json",
            url : "savedInward.htm",
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
                    $("#inward_form")[0].reset();
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
        url: "getInwardRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(inward){            
            var result = $.parseJSON(inward);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slnos = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slnos++) +"</td>"+
                           "<td>"+v.slno+"</td><td>"+v.fileNumber+"</td>"+  
                           "<td>"+v.type+"</td><td>"+v.receiveDate+"</td>"+                           
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.inwardId + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(5)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "Serial NO", width: '20%'},
                        {title: "File NO", width: '20%'},
                        {title: "Type", width: '20%'},
                        {title: "Received Date", width: '20%'},                        
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
                    $("#inward_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getInwardReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({inward_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#inwardId").val(v.inwardId);
                                $("#slno").val(v.slno);
                                $("#fileNumber").val(v.fileNumber); 
                                $("#fileNumber").selectpicker("refresh");
                                $("#type").val(v.type); 
                                $("#type").selectpicker("refresh");
                                $("#receiveDate").val(v.receiveDate);                                 
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
        $("#inward_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#inwardId, #slno, #fileNumber, #type, #receiveDate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateInwardDetails.htm",
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
                    $("#inward_form")[0].reset();
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
    $("#showRecord").on('click', function () {
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
        $("#fileNumber").val('null');
        $("#fileNumber").selectpicker("refresh");
        $("#type").val('null');
        $("#type").selectpicker("refresh");
        $("#inward_form")[0].reset(); 
        SlNmberIncre();
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
