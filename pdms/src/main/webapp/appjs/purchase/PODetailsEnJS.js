/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {


    $("#navigation li a").removeClass("active");
    $("#purcmenu").css("display", "block");
    $("#podtenysc").addClass("active");

    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    //******************************************************************
    //START: GENERATE PO ORDER DETAILS
    //******************************************************************   
    
    $("#poNumInPo").keyup(function (){
        $("#append_files").empty();
        var poNum = parseInt(this.value);
        $("#errorPoNum").empty();
        if (poNum <= 0 || isNaN(poNum) === true){
            $("#errorPoNum").append("Please enter valid PO number.");
        }else{
        $.ajax({
            url: "getFileNoFrTenByPo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PoNum: poNum}),
            success: function (details) {                
                $("#errorPoNum").empty();
                var result = $.parseJSON(details);
                if ($.isEmptyObject(result)) {                    
                    $("#errorPoNum").append("This number has no data.");
                } else {
                        $.each(result, function (k, v) {
                            var fiNo = v.tenderFN;                            
                            var slashThreeStr = fiNo;                            
                            $("#fileNo").val(slashThreeStr);
                            $.ajax({
                                url: "getTenderIdByFileNo.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({fileNum: slashThreeStr}),
                                success: function (response) {                                     
                                    var tenderId = parseInt(response);
                                    generate(tenderId);
                                }, error: function (jqXHR, textStatus, errorThrown) {
                                    alert(textStatus + " ::" + errorThrown);
                                }
                            });
                            
                        });
                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
                
            }
        });
    }
    }); //End keyup function
    
    
  
    function generate(poNum){
        $.ajax({
            url: "getPublicTenDetails.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({fileNum: poNum}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                var slno = 1;
                var ckslno = 1;
                var f = 1;
                var ft = 1;
                var cud = 1;
                var pur = 1;
                var fri = 1;
                var pfc = 1;
                var sal = 1;
                var gst = 1;
                var dis = 1;           
                var fa = 1;
                var tot = 1;
                var slnoi = 1;
            var final1;
                $.each(result, function (k, v) {         
                    //alert(v.itemObj.itemID+"---"+v.itemUnits);
                    $.ajax({
                        url: "getItemDeByItemNo.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({itemNo: v.itemUnits}),
                        success: function (det) {
                            //alert("item de :"+det);
                            var res = $.parseJSON(det);
                            $.each(res, function (k, w) { 
                                //alert("unit id: "+z.itemObj.indentSlNo);
                                $.ajax({
                                    url: "getUnitDeByUnitNo.htm",
                                    type: "POST",
                                    cache: false,
                                    async: false,
                                    data: ({UnitNo: w.unitDTO.unitID}),
                                    success: function (deta) {
                                        //alert(deta);
                                        var rest = $.parseJSON(deta);
                                        $.each(rest, function (k, z) {                                           
                                           final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chck' value='"+ (ckslno++) +"' /></td>"+
                                            "<td><input type='text' name='' class='painput' value='"+ (slno++) +"' readonly/></td>"+
                                            "<td><input type='text' name='' class='painput indentslno"+(slnoi++)+"' value='"+v.itemObj.indentSlNo+"' readonly/></td>"+
                                            "<td><textarea name='' id='tabSelect' class='painput itemaa"+(f++)+"'>("+w.itemName+")"+w.description+"</textarea>"+
                                            "<input type='hidden' name='' class='item"+(fa++)+"' value='"+w.itemCode+"' /></td>"+
                                            "<td><input type='text' name='' class='painput itemQty"+(ft++)+"' value='"+v.itemQty+"' readonly/></td>"+
                                            "<td><select name='' id='tabSelect' class='unit"+(cud++)+"'><option value='"+z.unitCode+"'>("+z.unitCode+")"+z.unitName+"</option></select></td>"+
                                            "<td><input type='text' name='' class='painput rate"+(pur++)+"' value='' /></td>"+  
                                            "<td><input type='text' name='' class='painput discount"+(dis++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput pfCharges"+(pfc++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput salestax"+(sal++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput gst"+(gst++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput frieght"+(fri++)+"' value='' /></td>"+ 
                                            "<td><input type='text' name='' class='painput total"+(tot++)+"' value='' /></td>"+
                                            "</tr>";                                                
                                        });
                                    }, error: function (jqXHR, textStatus, errorThrown) {
                                        alert(textStatus + " :" + errorThrown);

                                    }
                                }); 
                            });
                            
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert(textStatus + " :" + errorThrown);

                        }
                    });
                });
                $("#append_files").html(final1);
            
            
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
                
            }
        });
    }
    //******************************************************************
    //END: GENERATE PO ORDER DETAILS
    //****************************************************************** 
    
    $('.inspeTabH').on('keyup', 'tr td input', function () {
        var field = $(this).attr("class");
        var x = document.getElementById("basic-usage").rows.length;
        var rate = this.value;
        if (field.indexOf("rate1") >= 0) {
            var nums = parseInt(field.match(/\d+/g));
            for(var i=1; i<=x-1; i++){                
                $(".rate"+(i+1)).val(rate);
            }
        }  
        if (field.indexOf("frieght1") >= 0) {
            var num = parseInt(field.match(/\d+/g));
            for(var i=1; i<=x-1; i++){                
                $(".frieght"+(i+1)).val(rate);
            }
        } 
        if (field.indexOf("pfCharges1") >= 0) {
            var num = parseInt(field.match(/\d+/g));
            for(var i=1; i<=x-1; i++){                
                $(".pfCharges"+(i+1)).val(rate);
            }
        }
        if (field.indexOf("salestax1") >= 0) {
            var num = parseInt(field.match(/\d+/g));
            for(var i=1; i<=x-1; i++){                
                $(".salestax"+(i+1)).val(rate);
            }
        }
        if (field.indexOf("gst1") >= 0) {
            var num = parseInt(field.match(/\d+/g));
            for(var i=1; i<=x-1; i++){                
                $(".gst"+(i+1)).val(rate);
            }
        }
        if (field.indexOf("discount1") >= 0) {
            var num = parseInt(field.match(/\d+/g));
            for(var i=1; i<=x-1; i++){                
                $(".discount"+(i+1)).val(rate);
            }
        }
    });

    //******************************************************************
    //START: SUM OF TABLE ROWS FOR TOTAL
    //******************************************************************
    $('.inspeTabH').on('keyup', 'tr td input', function () {
        var field = $(this).attr("class");        
        if (field.indexOf('frieght') > -1) {            
            var x = document.getElementById("basic-usage").rows.length;           
            var rate1 = this.value;            
            var nums = parseInt(field.match(/\d+/g));                
            var rate = parseFloat($(".rate"+nums).val());
            var dis = parseFloat($(".discount"+nums).val());
            var pAndF = parseFloat($(".pfCharges"+nums).val());
            var sales = parseFloat($(".salestax"+nums).val());
            var gst = parseFloat($(".gst"+nums).val());
            var fri = parseFloat($(".frieght"+nums).val());            
            $(".total"+nums).val(rate-dis+pAndF+sales+gst+fri);

        }//end if
        if (field.indexOf('gst') > -1) {                       
            var nums = parseInt(field.match(/\d+/g));                
            var rate = parseFloat($(".rate"+nums).val());
            var dis = parseFloat($(".discount"+nums).val());
            var pAndF = parseFloat($(".pfCharges"+nums).val());
            var sales = parseFloat($(".salestax"+nums).val());
            var gst = parseFloat($(".gst"+nums).val());
            var fri = parseFloat($(".frieght"+nums).val());            
            $(".total"+nums).val(rate-dis+pAndF+sales+gst+fri);
        }//end if
        if (field.indexOf('gst') > -1) {                       
            var nums = parseInt(field.match(/\d+/g));                
            var rate = parseFloat($(".rate"+nums).val());
            var dis = parseFloat($(".discount"+nums).val());
            var pAndF = parseFloat($(".pfCharges"+nums).val());
            var sales = parseFloat($(".salestax"+nums).val());
            var gst = parseFloat($(".gst"+nums).val());
            var fri = parseFloat($(".frieght"+nums).val());            
            $(".total"+nums).val(rate-dis+pAndF+sales+gst+fri);
        }//end if
        if (field.indexOf('discount') > -1) {                        
            var nums = parseInt(field.match(/\d+/g));                
            var rate = parseFloat($(".rate"+nums).val());
            var dis = parseFloat($(".discount"+nums).val());
            var pAndF = parseFloat($(".pfCharges"+nums).val());
            var sales = parseFloat($(".salestax"+nums).val());
            var gst = parseFloat($(".gst"+nums).val());
            var fri = parseFloat($(".frieght"+nums).val());            
            $(".total"+nums).val(rate-dis+pAndF+sales+gst+fri);
        }//end if
        if (field.indexOf('pfCharges') > -1) {
            var nums = parseInt(field.match(/\d+/g));                
            var rate = parseFloat($(".rate"+nums).val());
            var dis = parseFloat($(".discount"+nums).val());
            var pAndF = parseFloat($(".pfCharges"+nums).val());
            var sales = parseFloat($(".salestax"+nums).val());
            var gst = parseFloat($(".gst"+nums).val());
            var fri = parseFloat($(".frieght"+nums).val());            
            $(".total"+nums).val(rate-dis+pAndF+sales+gst+fri);
        }//end if
        if (field.indexOf('salestax') > -1) {                       
            var nums = parseInt(field.match(/\d+/g));                
            var rate = parseFloat($(".rate"+nums).val());
            var dis = parseFloat($(".discount"+nums).val());
            var pAndF = parseFloat($(".pfCharges"+nums).val());
            var sales = parseFloat($(".salestax"+nums).val());
            var gst = parseFloat($(".gst"+nums).val());
            var fri = parseFloat($(".frieght"+nums).val());            
            $(".total"+nums).val(rate-dis+pAndF+sales+gst+fri);
        }//end if
    });
    //******************************************************************
    //END: SUM OF TABLE ROWS FOR TOTAL
    //******************************************************************
    
    //******************************************************************
    //START: SAVE PO ORDER DETAILS
    //******************************************************************
    
     $("#savePoOrderDe").click(function (){
        var $items = $('#poNumInPo, #fileNo, #desCode, #noteDes');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

              
        var val = [];
        var myData = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();          
            var obj = { 
                    poNum: $("#poNumInPo").val(),
                    indentslno: $(".indentslno"+val[i]).val(),
                    itemCode: $(".item"+val[i]).val(),
                    quantity: $(".itemQty"+val[i]).val(),
                    unit: $(".unit"+val[i]).val(),
                    rate: $(".rate"+val[i]).val(),
                    frieght: $(".frieght"+val[i]).val(),
                    pfCharges: $(".pfCharges"+val[i]).val(),
                    salesTax: $(".salestax"+val[i]).val(),
                    gst: $(".gst"+val[i]).val(),
                    discount: $(".discount"+val[i]).val(),
                    total: $(".total"+val[i]).val()
            };
            myData.push(obj);
            
        });
        //alert(JSON.stringify(myData));
        
        var jsonOb = JSON.stringify([obj]);        
        var parObj = JSON.parse(jsonOb); 
        
        var big = [];               
        big = JSON.stringify({ poObj : parObj,poItemsObj : myData});            
        
        alert(big);

        $.ajax({
            dataType: "json",
            url: "savePoOrderItemsDe.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data: big,
            type: 'POST',
            success: function (data) {
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#append_files").empty();
                    $("#poNumInPo").val('');
                    $("#form_second_part")[0].reset();
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                }else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to submit ?");
            }
        });
    });
       
       
    //******************************************************************
    //END: SAVE PO ORDER DETAILS
    //******************************************************************
    
    
    //******************************************************************
    //START: NOTE DESCRIPTION TEMPLATE
    //******************************************************************            
                $("#noteDes").dblclick(function(){
                    $(".modal-body-node").empty();                    
                    var text_id;
                    $("#cstModalNoDe").modal("show");                    
                    $.ajax({
                        url: "getDescriptionDetails.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlH){                            
                            var result = $.parseJSON(htmlH);			
                            $.each(result, function(k, v) {
                            //alert(v.description);			
                                $(".modal-body-node").append("<div class='ineer-model' id='"+v.descriptionID+"' >"
                                +" "+v.description+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    $("#desCode").val(text_id);
                                    var tex = $("#"+text_id).text();                                   
                                    $("#noteDes").text('');                                   
                                    document.getElementById("noteDes").value = tex;
                                    $('#cstModalNoDe').modal('toggle');
                                    var WhereToMove = $("#noteDes").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);                                   
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: NOTE DESCRIPTION TEMPLATE
    //******************************************************************              
    

}); //End document