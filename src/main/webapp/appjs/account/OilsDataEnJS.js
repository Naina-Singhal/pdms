/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openOilRecord() {

    window.open("./getOilDaRecord.htm", "_parent");
}
$(document).ready(function () {



    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#oilsdaentry ").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    
    //**************************************************************************
    //***START: START AMOUNT CALUCLATE FOR UNIT RATE etc
    //**************************************************************************
    $("#rate").keyup(function(){
        Ed_Amount_Fun();
        unit_rate();
        Total_amt_Fun();
    });
    $("#scDiscount").keyup(function(){
        Ed_Amount_Fun();
        unit_rate();
        Total_amt_Fun();
    });
    $("#ed_rate").keyup(function(){
        Ed_Amount_Fun();     
        Total_amt_Fun();
    });
    $("#edAmount").keyup(function(){  
        Ed_Amount_Fun(); 
        unit_rate();
        Total_amt_Fun();
    });
    $("#edCess").keyup(function(){
        unit_rate();
        Total_amt_Fun();
    });
    $("#addDiscount").keyup(function(){
        unit_rate();
        Total_amt_Fun();
    });
    $("#fcAmount").keyup(function(){
        unit_rate();
        Total_amt_Fun();
    });
    $("#cst").keyup(function(){
        unit_rate();
        Total_amt_Fun();
        calucCstAmount();
    });
    $("#unitRate").keyup(function(){
        unit_rate();
        Total_amt_Fun();
    });
    
    $("#freight").keyup(function(){
        Fc_Amount_fun();
        Total_amt_Fun();
    });
    $("#qtyRecord").keyup(function(){
        Fc_Amount_fun();
        Total_amt_Fun();
    });
    $("#totalAmt").keyup(function(){        
        Total_amt_Fun();
    });
    
    function unit_rate(){
        var final = 0.0; 
        var rate = $("#rate").val();
        var Discount = $("#scDiscount").val();
        var edAmount = $("#edAmount").val();
        var edCess = $("#edCess").val();
        var addDisc = $("#addDiscount").val();
        var fcAmount = $("#fcAmount").val();
        var cst = $("#cst").val(); 
        
        final = parseFloat(return_number(rate)) - parseFloat(return_number(Discount)) + 
                parseFloat(return_number(edAmount)) + parseFloat(return_number(edCess)) 
                - parseFloat(return_number(addDisc)) + parseFloat(return_number(fcAmount));
        
        var finaCst = final * parseFloat(return_number(cst)) / 100;
       
        $("#unitRate").val(finaCst.toFixed(2));
    }
    
    function Ed_Amount_Fun(){
        var raTe = $("#rate").val();
        var ScDIs = $("#scDiscount").val();
        var Ed_rate = $("#ed_rate").val();
        var EDA = 0.0;
        EDA = parseFloat(return_number(raTe)) - parseFloat(return_number(ScDIs));
        var EdAmt = EDA * Ed_rate / 100;
        $("#edAmount").val(EdAmt.toFixed(2));
    }
    
    function Fc_Amount_fun(){
        var Freight = $("#freight").val();
        var qtyReceived = $("#qtyRecord").val();
        var Fc_Amt = 0.0;
        Fc_Amt = parseFloat(return_number(Freight)) * parseFloat(return_number(qtyReceived));
        $("#fcAmount").val(Fc_Amt.toFixed(2));
    }
    
    function Total_amt_Fun(){
        var UnitRate = $("#unitRate").val();
        var QtyReceived = $("#qtyRecord").val();
        var TotAmt = 0.0;
        TotAmt = parseFloat(return_number(UnitRate)) * parseFloat(return_number(QtyReceived));
        $("#totalAmt").val(TotAmt.toFixed(2));
    }
    
    function calucCstAmount(){
        var rateCst = $("#rate").val();
        var qtyRcdCst = $("#qtyRecord").val();
        var gstPerse = $("#cst").val();
        var totCstAmt = 0.0;
        totCstAmt = parseFloat(return_number(rateCst)) * parseFloat(return_number(qtyRcdCst))
                * parseFloat(return_number(gstPerse))/100;
        $("#cstAmt").val(totCstAmt.toFixed(2));
        
        $("#qtyReceivedRate").val(parseFloat(return_number(rateCst)) * parseFloat(return_number(qtyRcdCst)));
    }
    
    
    function return_number(vall){
        var ret_val = 0.0;
        if(isNaN(parseFloat(vall))){           
            ret_val = 0;
        }else{            
            ret_val = vall;
        }
        return ret_val;
    }
    
    
    
    
    //**************************************************************************
    //***END:  AMOUNT CALUCLATE FOR FOR UNIT RATE
    //**************************************************************************
    
    
    
    function validate_fileds() {
        var retval = 0;
        var Rate = $("#rate").val();
        var Ed_Rate = $("#ed_rate").val();
        var ScDis = $("#scDiscount").val();
        var EdAmount = $("#edAmount").val();
        var EdCess = $("#edCess").val();
        var AddDis = $("#addDiscount").val();
        var FcAmount = $("#fcAmount").val();
        var Cst = $("#cst").val();
        var Freigh = $("#freight").val();
        var qtyReceive = $("#qtyRecord").val();
        
        if (Rate === '' || Rate === null) {
            retval = 1;            
        } else if (ScDis === '' || ScDis === null) {
            retval = 1;
        }else if (Ed_Rate === '' || Ed_Rate === null) {
            retval = 1;
        }else if (EdAmount === '' || EdAmount === null) {
            retval = 1;
        }else if (EdCess === '' || EdCess === null) {
            retval = 1;
        }else if (AddDis === '' || AddDis === null) {
            retval = 1;
        }else if (FcAmount === '' || FcAmount === null) {
            retval = 1;
        }else if (Cst === '' || Cst === null) {
            retval = 1;
        }else if (Freigh === '' || Freigh === null) {
            retval = 1;
        }else if (qtyReceive === '' || qtyReceive === null) {
            retval = 1;
        }
        

        return retval;
    }
    
    //******************************************************************
    //START: Getting VOUCHER DATA ENTRY PO DETAILS
    //******************************************************************   
    $("#poNumber12").keyup(function () {
        $("#errorPoNo").empty();
        var pon = parseInt(this.value);
        if (pon > 0) {
            $.ajax({
                url: "getVouDaEnPoDeById.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoVouDaEn_id: pon}),
                success: function (details) {                    
                    var result = $.parseJSON(details);
                    $.each(result, function (k, v) {
                        $("#supplierName").val(v.nameOfSupplr);
                        $("#pprNo").val(v.pprNo);
                    });
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus + " :" + errorThrown);

                }, complete: function (data) {
                }
            });
        } else {
            $("#errorPoNo").append("please enter valid PO number");
        }
    });
    //******************************************************************
    //END: Getting VOUCHER DATA ENTRY PO DETAILS
    //******************************************************************  
    
    
   
    //**************************************************************************
    //***START: FETCHING PO DETAILS FROM VOUCHER DATA ENTRY 
    //**************************************************************************

    $("#poNumber").keyup(function () {
        var PONumber = parseInt(this.value);

        $("#errorPoNo").empty();
        if (PONumber <= 0 || isNaN(PONumber) === true) {
            $("#errorPoNo").append("Please enter valid PO Number. ");
            //$("#pprNo option").remove();
            $("#supplierName option").remove();
            $("#invoiceNo option").remove();
            $("#invoiceDate").val('');
            $("#pprNo").val('');
        } else {
            $.ajax({
                url: "getVouDaEnPoDeById.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoVouDaEn_id: PONumber}),
                success: function (poDe) {                    
                    var poDeRe = $.parseJSON(poDe);                    
                    $("#supplierName").empty();
                    if ($.isEmptyObject(poDeRe)) {
                        $("#errorPoNo").append("This number has no data. ");
                        $("#supplierName option").remove();
                        $("#invoiceNo option").remove();
                        $("#invoiceDate").val('');
                        $("#pprNo").val('');
                    } else {
                        $("#supplierName").append("<option value='null'>select</option>");
                        $.each(poDeRe, function (k, v) {                            
                            $.ajax({
                                url: "getVendorNameByCode.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({venCode: v.nameOfSupplr}),
                                success: function (res) {
                                    var res = $.parseJSON(res);
                                    $.each(res, function (k, w) {
                                        $("#supplierName").append("<option value='" + w.vendorCode + "' selected>(" + w.vendorCode + ")" + w.vendorName + "</option>");
                                        $("#supplierName").selectpicker("refresh");
                                        $("#pprNo").val(v.pprNo);
                                    });

                                }, error: function (jqXHR, textStatus, errorThrown) {
                                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                                }
                            });
                        });
                        
                        $.ajax({
                            url: "getBillEnReByPoNum.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({PoNum: PONumber}),
                            success: function (data) {                                
                                var result = $.parseJSON(data);
                                $("#invoiceNo").empty();
                                $("#errorBillNo").empty();
                                if ($.isEmptyObject(result)) {
                                    $("#errorBillNo").append("This number has no data");                                    
                                    $("#invoiceNo option").remove();
                                    $("#invoiceDate").val('');
                                } else {
                                    $("#invoiceNo").append("<option value='0'>select</option>");
                                    $.each(result, function (k, v) {
                                        $("#invoiceNo").append("<option value='" + v.billNo + "'>" + v.billNo + "</option>");
                                        $("#invoiceNo").selectpicker("refresh");
                                    });
                                    $("#invoiceNo").on('change', function () {
                                        var bill_no = parseInt(this.value);
                                        $.each(result, function (k, u) {
                                            if (bill_no === parseInt(u.billNo)) {
                                                $("#invoiceDate").val(u.billDate);
                                            }
                                        });
                                    });
                                }
                            }, complete: function (data) {
                            }, error: function (jqXHR, textStatus, errorThrown) {
                                alert("Error :" + textStatus);
                            }
                        });
                        
                    }
                }, complete: function (data) {

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + textStatus);
                }
            });
        }
    });
    //**************************************************************************
    //***END: FETCHING PO DETAILS FROM VOUCHER DATA ENTRY
    //**************************************************************************
    
    
    
    //******************************************************************
    //START: DESCRIPTION TEMPLATE
    //******************************************************************            
                $("#description").dblclick(function(){
                    $(".modal-body").empty();                    
                    var text_id;
                    $("#cstModal").modal("show");                    
                    $.ajax({
                        url: "getBreifDesFrTender.htm",
                        type: "POST",
                        cache: false,
                        async: false,                        
                        success: function(htmlD){                            
                            var result = $.parseJSON(htmlD);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-body").append("<div class='ineer-model' id='"+v.pTenderItemID+"' >"
                                +" "+v.breifDesc+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#description").text('');                                   
                                    document.getElementById("description").value = tex;
                                    $('#cstModal').modal('toggle');
                                    var WhereToMove = $("#description").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#invoiceNo").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: DESCRIPTION TEMPLATE
    //******************************************************************    
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#posr, #poNumber,#supplierName,#description, #invoiceNo,' +
                    '#invoiceDate, #invoiceAmt, #invoiceQty, #rate, #state,' +
                    ' #scDiscount, #ed_rate, #edAmount, #edCess,' +
                    '#addDiscount, #freight, #fcAmount, #cst, #cstAmt,' +
                    '#unitRate, #qtyRecord, #totalAmt, #pprNo, #amountPaid, #qtyReceivedRate');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveOilDataEn.htm",
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify([obj]),
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#oil_form")[0].reset();
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
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
        url: "getOilDataRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(iold){            
            var result = $.parseJSON(iold);
            if($.isEmptyObject(result)){
                $("#basic-usage").remove();
                $('#basic-usage').load('ajax.html#basic-usage');
                alert("There is no records");
            }else{
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.posr+"</td><td>"+v.poNumber+"</td>"+  
                           "<td>"+v.rate+"</td><td>"+v.state+"</td>"+
                           "<td>"+v.totalAmt+"</td><td>"+v.description+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.oilDataId + ">Edit </button></td> ";
                                            
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
                        {title: "POSR NO", width: '13%'},
                        {title: "Po NO", width: '13%'},
                        {title: "Rate", width: '13%'},
                        {title: "State", width: '13%'},
                        {title: "Total Amount", width: '13%'},
                        {title: "Description", width: '13%'},
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
            
        }
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#oil_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getOilDataReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({oilData_id: this.value}),
                        success: function (oildata) {                              
                            var result = $.parseJSON(oildata);
                            $.each(result, function (k, v) {                                
                                $("#oilDataId").val(v.oilDataId);
                                $("#posr").val(v.posr);
                                $("#poNumber").val(v.poNumber);  
                                $("#supplierName").val(v.supplierName); 
                                $("#description").val(v.description); 
                                $("#invoiceNo").val(v.invoiceNo); 
                                $("#invoiceDate").val(v.invoiceDate); 
                                $("#invoiceAmt").val(v.invoiceAmt); 
                                $("#invoiceQty").val(v.invoiceQty); 
                                $("#rate").val(v.rate);
                                
                                $("#state").val(v.state);
                                $("#scDiscount").val(v.scDiscount);
                                $("#ed_rate").val(v.ed_rate);  
                                $("#edAmount").val(v.edAmount); 
                                $("#edCess").val(v.edCess); 
                                $("#addDiscount").val(v.addDiscount); 
                                $("#freight").val(v.freight); 
                                $("#fcAmount").val(v.fcAmount); 
                                $("#cst").val(v.cst); 
                                $("#cstAmt").val(v.cstAmt);
                                
                                $("#unitRate").val(v.unitRate);
                                $("#qtyRecord").val(v.qtyRecord);
                                $("#totalAmt").val(v.totalAmt);  
                                $("#pprNo").val(v.pprNo); 
                                $("#amountPaid").val(v.amountPaid); 
                                $("#qtyReceivedRate").val(v.qtyReceivedRate);                                
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#supplierName").selectpicker("refresh");
                            $("#invoiceNo").selectpicker("refresh");
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
        $("#oil_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#oilDataId, #posr, #poNumber,#supplierName,#description, #invoiceNo,' +
                '#invoiceDate, #invoiceAmt, #invoiceQty, #rate, #state,' +
                ' #scDiscount, #ed_rate, #edAmount, #edCess,' +
                '#addDiscount, #freight, #fcAmount, #cst, #cstAmt,' +
                '#unitRate, #qtyRecord, #totalAmt, #pprNo, #amountPaid, #qtyReceivedRate');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateOilDataDetails.htm",
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
                    $("#oil_form")[0].reset();
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
        $("#oil_form")[0].reset();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#supplierName").selectpicker("refresh");
        $("#invoiceNo").selectpicker("refresh");
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
