/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openBillRe() {

    window.open("./BillEntryRe.htm", "_parent");
}
$(document).ready(function () {
    


    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#billenscrn ").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    //**************************************************************************
    //***START: SAVE BILL ENTRY DATA
    //**************************************************************************

    $("#saveBillEntry").click(function () {
        $("#errorDupItem").empty();

        var $items = $('#poNumber, #supplierName,#paymentTerm,#billNo, #billDate,' +               
                ' #dcNo, #billAmt, #lrEnclosed, #gstinNo, #billReceDate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var val = [];
        var myData = [];
        $(':checkbox:checked').each(function (i) {
            val[i] = $(this).val();
            var obj = {
                poNumBer: $("#poNumber").val(),
                items: $(".item" + val[i]).val(),
                rate: $(".rate" + val[i]).val(),
                rcdQty: $("#qtyrcd" + val[i]).val(),
                gbys: $(".gbys" + val[i]).val(),
                gstPer: $(".gst" + val[i]).val(),
                igst: $(".igst" + val[i]).val(),
                sgst: $(".sgst" + val[i]).val(),
                cgst: $(".cgst" + val[i]).val(),
                gst: $(".gstAmo" + val[i]).val(),
                hsnCode: $(".hsnCode" + val[i]).val()
            };
            myData.push(obj);

        });

        var jsonObj = JSON.stringify([obj]);        
        var parseObj = JSON.parse(jsonObj);  
        var formItems = [];
        
        formItems = JSON.stringify({ billEnDTO : parseObj,billItemsDTO : myData});   
        //alert(formItems); 

        $.ajax({
            dataType: "json",
            url: "saveBillEntry.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data: formItems,
            type: 'POST',
            success: function (data) {                
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong>Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#Bill_Form")[0].reset();   
                    $("#supplierName").empty();
                    $("#gstinNo").selectpicker("refresh");
                    $("#poNumber").val("");
                    $(".add_rows").empty();
                    $("#append_items").empty();
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {                
                return confirm("Are you sure you want save data ?");
            }
        });
    });

    //**************************************************************************
    //***END: SAVE BILL ENTRY DATA
    //**************************************************************************
    
    //******************************************************************
    //Getting vendor details from po_entry table
    //****************************************************************** 
    
    $("#poNumber").keyup(function () {       
        var lastStr = parseInt(this.value);
        close_message();   
        $("#errorPoNumber").empty();
        if (lastStr <= 0 || isNaN(lastStr) === true) {             
            $("#supplierName").empty();
            $("#paymentTerm").val('');
            $("#append_items").empty();
            $("#gstinNo").val('');
            $("#errorPoNumber").append("This number has no data. ");
            $(".add_rows").empty();
            $("#append_items").empty();
        }else{
            generate(parseInt(this.value));
            $.ajax({
                url: "getPoDetailsByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PONumber: lastStr}),
                success: function (details) {                    
                    var result = $.parseJSON(details);
                    if($.isEmptyObject(result)){
                        $("#supplierName").empty();
                        $("#paymentTerm").val('');
                        $("#gstinNo").val('');
                        $("#errorPoNumber").append("This number has no data.");
                        $(".add_rows").empty();
                        $("#append_items").empty();
                    }else{
                        $("#errorPoNumber").empty();
                    $.each(result, function (k, v) {
                        $("#supplierName").empty();
                        $("#paymentTerm").val(v.payment);
                        $.ajax({
                            url: "getVendorName.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            success: function (deta) {
                                var resul = $.parseJSON(deta);
                                $.each(resul, function (k, w) {
                                    if (v.venderName === w.vendorCode) {
                                        $("#supplierName").append("<option value='" + w.vendorCode + "'>(" + w.vendorCode + ")" + w.vendorName + "</option>");
                                    }                                    
                                });
                                generateItemsList(lastStr);
                            }, error: function (jqXHR, textStatus, errorThrown) {
                                alert(textStatus + " :" + errorThrown);

                            }, complete: function (data) {
                            }
                        });                        
                        if(v.venderName !== null || v.venderName !== ''){
                            appendGstin(v.venderName);                            
                        }                        
                    });
                }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus + " :" + errorThrown);

                }, complete: function (data) {
                    
                }, beforeSend: function () {
               
                
                }
            });
        }
    });
    //******************************************************************
    //START :   Build PO number
    //****************************************************************** 
    
    
    //******************************************************************
    //END :   Build PO number
    //****************************************************************** 
    
    //******************************************************************
    //START: PAYMENT TEMPLATE
    //******************************************************************            
                $("#paymentTerm").dblclick(function(){
                    $(".modal-body").empty();                    
                    var text_id;
                    $("#cstModal").modal("show");                    
                    $.ajax({
                        url: "getPaymentTerms.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlH){				
                            var result = $.parseJSON(htmlH);			
                            $.each(result, function(k, v) {
                            //alert(v.description);			
                                $(".modal-body").append("<div class='ineer-model' id='"+v.paymentCode+"' >"
                                +" "+v.paymentDes+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#paymentTerm").text('');                                   
                                    document.getElementById("paymentTerm").value = tex;
                                    $('#cstModal').modal('toggle');
                                    var WhereToMove = $("#paymentTerm").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);                                   
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: PAYMENT TEMPLATE
    //******************************************************************              
    
    //******************************************************************
    //START: Getting GSTIN NUMBER  from vendor_details table
    //******************************************************************      
    function appendGstin(code) {        
        $.ajax({
            url: "getGstinNoByVendorNa.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({ven_code: code}),
            success: function (details) {                
                var result = $.parseJSON(details);
                $.each(result, function (k, v) {
                    $("#gstinNo").val(v.gstinNumber);
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //END: GSTIN NUMBER  from vendor_details table
    //******************************************************************
    
    //******************************************************************
    //START: BILL ENTRY DETAILS FOR APPEND TO BILL ITEMS LIST
    //****************************************************************** 
    function generateItemsList(poNoFrItemsList) {        
        $("#append_items").empty();
        $.ajax({
            url: "getBillEnReByPoNum.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PoNum: poNoFrItemsList}),
            success: function (billDa) {                                  
                var res = $.parseJSON(billDa);
                var slNo = 1;
                $.each(res, function (k, m) {
                    $("#append_items").append("<tr>" +                            
                            "<td>" + (slNo++) + "</td><td>" + m.billNo + "</td>" +
                            "<td>" + m.billDate + "</td><td>" + m.billAmt + "</td></tr>");
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //END:  BILL ENTRY DETAILS FOR APPEND TO BILL ITEMS LIST
    //******************************************************************
    
    //******************************************************************
    //START:  GENERATE TABLE FOR BILL ENTRY ITEM DETAILS
    //******************************************************************
        function generate(poNum){
           // alert("po num: "+poNum);
           $(".ddd").empty();
        $.ajax({
            url: "getPoDeItemDeByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({poNumb: poNum}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                var slno = 1;
                var ckslno = 1;
                var f = 1;
                var rate = 1;
                var qty = 1;
                var gbys = 1;
                var igst = 1;
                var igstD = 1;
                var sgst = 1;
                var cgst = 1;
                var gst = 1;
                var gstAmo = 1;           
                var hsn = 1;
                var qtyE = 1;
            var final1;
                $.each(result, function (k, v) {         
                    //alert(v.itemCode+"---"+v.unit);
                    $.ajax({
                        url: "getItemDeByItemCode.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({itemCode: v.itemCode}),
                        success: function (det) {
                           // alert("item de :"+det);
                            var res = $.parseJSON(det);
                            $.each(res, function (k, w) { 
                                //alert("item : "+w.itemName);
                                final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chck' value='"+ (ckslno++) +"' /></td>"+
                                            "<td><input type='text' name='' class='painput' value='"+ (slno++) +"' readonly/></td>"+
                                            "<td><select name='' id='tabSelect' class='item"+(f++)+"'><option value='"+w.itemCode+"'>("+w.itemName+")"+w.description+"</option></select></td>"+
                                            "<td><input type='text' name='' class='painput rate"+(rate++)+"' value='"+v.rate+"' /></td>"+  
                                            "<td><input type='text' name='' id='qtyrcd"+(qtyE++)+"' class='painput qtyrcd"+(qty++)+"' value='' /></td>"+                                            
                                            "<td><select name='gbys' class='painput gbys"+(gbys++)+"' id='table_sele2'>"+
                                                    "<option value='null'>select</option>"+
                                                    "<option value='Goods'>Goods</option>"+
                                                    "<option value='Services'>Services</option>"+                                                    
                                                "</select></td>"+ 
                                            "<td><input type='text' name='' class='painput gst"+(gst++)+"' value='"+v.gst+"' /></td>"+                                            
                                            "<td><select name='igst' class='igstD"+(igstD++)+"' id='table_sele2'>"+
                                                    "<option value='null'>select</option>"+
                                                    "<option value='IGST'>IGST</option>"+
                                                    "<option value='GST'>GST</option>"+                                                    
                                                "</select></td>"+ 
                                            "<td><input type='text' name='' class='painput igst"+(igst++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput sgst"+(sgst++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput cgst"+(cgst++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput gstAmo"+(gstAmo++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput hsnCode"+(hsn++)+"' value='' /></td>"+ 
                                            "</tr>";                                              
                                       
                            });
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert(textStatus + " :" + errorThrown);

                        }
                    });
                });
                $(".add_rows").html(final1);
            
            
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
                
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");
                
            }
        });
    }   //end function
    
    $('.table').on('keyup', 'tr td input', function () {

        var field = $(this).attr("id");        
        if (field.indexOf("qtyrcd") >= 0) {
            var num = parseInt(field.match(/\d+/g));            
            var rcdQty = $("#" + field).val();
            var rate = $(".rate" + num).val();
            var gstPer = $(".gst" + num).val();            
            $(".gstAmo" + num).val((rate * rcdQty * gstPer / 100).toFixed(2));
            $(".igstD" + num).val('null');  // reset IGST dropdown & corresponding data
                $(".igst" + num).val(0);
                $(".sgst" + num).val(0);
                $(".cgst" + num).val(0);
        }
    });
    
    $('.table').on('change', 'tr td select', function () {

        var field = $(this).attr("class");          
        if (field.indexOf("igstD") >= 0) {
            var nums = parseInt(field.match(/\d+/g));            
            //var rcdQty = $("#" + field).val();
            //var rate = $(".rate" + num).val();
            var IgstSel = $(".igstD" + nums).val();
            var rcdQty = $("#qtyrcd" + nums).val();
            var rate = $(".rate" + nums).val();
            var gstPer = $(".gst" + nums).val();            
            if(IgstSel === 'IGST'){
                $(".igst" + nums).val((rate * rcdQty * gstPer / 100).toFixed(2));
                $(".sgst" + nums).val(0);
                $(".cgst" + nums).val(0);
                $(".gstAmo" + nums).val(0);
            }else if(IgstSel === 'GST'){                 
                gstPer = gstPer / 2 ; 
                //alert(rcdQty+"--"+rate+"--"+gstPer);
                $(".sgst" + nums).val((rate * rcdQty * gstPer / 100).toFixed(2));
                $(".cgst" + nums).val((rate * rcdQty * gstPer / 100).toFixed(2));
                $(".igst" + nums).val(0);
            }else{
                $(".igst" + nums).val(0);
                $(".sgst" + nums).val(0);
                $(".cgst" + nums).val(0);
            }
            //$(".gstAmo" + num).val(rate * rcdQty * gstPer / 100);
        }
    });
    //******************************************************************
    //END:  GENERATE TABLE FOR BILL ENTRY ITEM DETAILS
    //******************************************************************
    
     //******************************************************************
    //START: SAVE BILL ENTRY ITEMS
    //******************************************************************
    
    function saveBillEnItems(PONUM){
         
          var val = [];
          var myData = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val(); 
            var obj = { 
                    poNumBer: PONUM,
                    items: $(".item"+val[i]).val(),
                    rate: $(".rate"+val[i]).val(),                    
                    rcdQty: $("#qtyrcd"+val[i]).val(),
                    gbys: $(".gbys"+val[i]).val(),
                    gstPer: $(".gst"+val[i]).val(),
                    igst: $(".igst"+val[i]).val(),
                    sgst: $(".sgst"+val[i]).val(),
                    cgst: $(".cgst"+val[i]).val(),
                    gst: $(".gstAmo"+val[i]).val(),
                    hsnCode: $(".hsnCode"+val[i]).val()                    
            };
            myData.push(obj);
            
        });
            alert(JSON.stringify(myData));            
            $.ajax({
            dataType: "json",
            url: "saveBillEnItemList.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(myData),
            type: 'POST',
            success: function (data) {                 
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("(<strong></strong> Including items).");
                    $(window).scrollTop(0);  
                    $(".add_rows").empty();
                    $("#append_items").empty();
                }else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus);               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");
            }
        });
       }
       
       
    //******************************************************************
    //END: SAVE SAVE BILL ENTRY ITEMS
    //******************************************************************
    
 });    //End document

