/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openVouNoRe(){
                
        window.open("./voucherNoDaRe.htm","_parent");
}
$(document).ready(function () {
    
     getVoucherEnDa();
    
    $("#navigation li a").removeClass("active");
      $("#accountul").css("display","block");
      $("#suvonoen ").addClass("active");
      
      
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    generateOpenBalance();
    $("#firstSave").show();
    $("#Lc_form_page").hide();    
    //**************************************************************************
    //***START: GET VOUCHER DATA ENTRY
    //**************************************************************************
    function getVoucherEnDa() {          
        $.ajax({
            url: "getVoucherEnDa.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (data) {                
                var arr = [];
                var arr2 = [];
                var result = $.parseJSON(data);
                $.each(result, function (k, v) {                    
                    $("#pprNumber").append("<option value='" + v.pprNo + "'>" + v.pprNo + "</option>");
                    arr2.push(v.compCode);
                    $.ajax({
                        url: "getVoucherNosOnly.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        success: function (vouNos) {
                            var res = $.parseJSON(vouNos);
                            $.each(res, function (k, w) {
                                if(v.compCode === w.compCode){
                                   arr.push(v.compCode);
                                }
                            });
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + textStatus);
                        }
                    });
                });         
                var output = arr2.filter(function (i) {
                    return arr.indexOf(i) === -1;
                });
                for(var z=0; z<output.length; z++){                    
                    $("#compCode").append("<option value='" + output[z] + "'>" + output[z] + "</option>"); 
                }  
            }, complete: function (data) {
            },error: function (jqXHR, textStatus, errorThrown) {                
               alert("Error :"+textStatus);
            }
        });
    }      
    //**************************************************************************
    //***END: GET VOUCHER DATA ENTRY
    //**************************************************************************
    
    
    //******************************************************************
    //START: DISPLAY REQUISITION NUMBER AND GS NUMBER
    //******************************************************************  
        $("#supplierName").on('change', function (){
            $("#cqe_dd_rtgs").val('null');
            $("#rtgsNumber").val('0');
            $("#req_number").val('0');
        });
        
        $("#cqe_dd_rtgs").on('change', function () {
        var cdr = this.value;
        if (cdr === "D") {
            $.ajax({
                url: "getRequisitionNoInce.htm",
                type: "POST",
                cache: false,
                async: false,
                success: function (detai) {
                    var resu = parseInt(detai);
                    $("#req_number").val(resu);
                    $("#rtgsNumber").val('0');
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus + " :" + errorThrown);
                }
            });
        } else if (cdr === 'R') {
            var vendorName = $("#supplierName").val();
            if (vendorName === 'null' || vendorName === '') {
                $("#req_number").val('0');
                alert("please select vendor name");
            } else if (vendorName !== '' || vendorName !== 'null') {
                
                $.ajax({
                    url: "getRtgsNoFrRtgsMaByVen.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    data: ({vendorName: vendorName}),
                    success: function (deta) {
                       var resul = $.parseJSON(deta);			
                        $.each(resul, function(k, w) {                        		
                            $("#rtgsNumber").val(w.rtgsNo);
                        });
                    }, error: function (jqXHR, textStatus, errorThrown) {
                        alert(textStatus + " :" + errorThrown);
                    }
                });
                $("#req_number").val('0');
            } else {
                $("#rtgsNumber").val('0');
            }


        } else {
            $("#req_number").val('0');
            $("#rtgsNumber").val('0');
        }
    });
    //******************************************************************
    //END: DISPLAY REQUISITION NUMBER
    //******************************************************************  
    
    //******************************************************************
    //START: APPEND COMP DATA FROM VOUCHER DATA ENTRY
    //******************************************************************  
    $("#compCode").on('change', function () {        
        $.ajax({
            url: "getCompDaFrVouDaEn.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({CompCode: this.value}),
            success: function (deta) {                
                var resul = $.parseJSON(deta);
                $.each(resul, function (k, w) {                    
                    $("#supplierName").empty();
                    $("#poNumber").val(w.poNumber);
                    generateHoa(w.poNumber);
                    $("#ibcNumber").val(w.ibcNo);
                    $("#pprNumber").val(w.pprNo);
                    $.ajax({
                            url: "getVendorName.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            success: function (dataa) {                                
                                var res = $.parseJSON(dataa);
                                $.each(res, function (k, z) {                                    
                                    if (z.vendorCode === w.nameOfSupplr) {
                                        $("#supplierName").append("<option value='" + w.nameOfSupplr + "' selected>(" + w.nameOfSupplr + ")" + z.vendorName + "</option>");
                                    }
                                });                                
                            }, error: function (jqXHR, textStatus, errorThrown) {
                                alert(textStatus + " :" + errorThrown);

                            }, complete: function (data) {
                            }
                    });
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);
            }
        });
    });
    
    //******************************************************************
    //END: APPEND COMP DATA FROM VOUCHER DATA ENTRY
    //****************************************************************** 
    
    
    //******************************************************************
    //START: GENERATE TABLE FOR HEAD OF ACCOUNT
    //******************************************************************
    
    function generateHoa(poNum) {
        $("#stic_hoa").empty();
        $("#stic_hoaTot").empty();
        $.ajax({
            url: "getVoucDaEnHoaReByPo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({poNo_id: poNum}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if ($.isEmptyObject(result)) {
                    $("#stic_hoa").empty();
                    $("#stic_hoa").append("This number has no data. ").css({"color": "#0000009c"});
                } else {


                    var final;
                    var hoaslno = 1;
                    var hoano = 1;
                    var hoac = 1;
                    var hoasco = 1;
                    var cb = 1;
                    var damt = 1;
                    var camt = 1;
                    var aascd = 1;
                    var a;
                    var b;
                    var c;
                    var d;
                    $.each(result, function (k, v) {

                        a = "<tr><td><input type='checkbox' name='selector[]' class='tab2CheckBox' id='chckHoa' value='" + (hoaslno++) + "' /></td>" +
                                "<td><input type='text' name='' class='painput' value='" + (hoano++) + "' readonly/></td>" +
                                "<td><select name='' id='tabSelect' class='painput hoaname" + (hoac++) + "'><option value='null'>select</option>";
                        $.ajax({
                            url: "getHoaNamesDetails.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            success: function (hoa) {
                                //alert(hoa);
                                var hoaDe = $.parseJSON(hoa);

                                $.each(hoaDe, function (k, v) {
                                    //alert(v.accountType);
                                    d = "<option value = " + v.accountType + ">" + v.accountType + "</option>";
                                    b = b + d;

                                });


                            }, error: function (jqXHR, textStatus, errorThrown) {
                                alert(textStatus + " :" + errorThrown);

                            }, complete: function (jqXHR, textStatus) {

                            }

                        });
                        c = a + b + "</select></td>" +
                                "<td><input type='text' name='' class='painput selectSecod shortcode" + (aascd++) + "' value='" + v.shortCode + "' /></td>" +
                                "<td><input type='text' name='cbcb' class='painput cbamt" + (cb++) + "' value='" + v.cbcbcb + "' /></td>" +
                                "<td><input type='text' name='damount' class='painput creamt" + (damt++) + "' value='" + v.debitamt + "' /></td>" +
                                "<td><input type='text' name='camount' class='painput debamt" + (camt++) + "' value='" + v.creditAmt + "' /></td>" +
                                "</tr>";
                        b = "";
                        $("#stic_hoa").append(c).css({"color": "#0000009c"});
                        $(".hoaname" + (hoasco++)).val(v.hoaName);
                    }); //End for loop
                    final = "<tr><td></td><td></td><td></td><td class='tott'>Total:</td><td class='cbmt'></td><td class='amt'></td><td class='cmt'></td></tr>";
                    $("#stic_hoaTot").append(final);


                }
                
                  $(".tott").click(function () {                    
                    //$("#recoveries").blur(function (){
                    //$("#defference").val('');
                    $(".cbmt").empty();
                    $(".cmt").empty();
                    $(".amt").empty();                    
                    $(".cbmt").append((findTotOfCb()).toFixed(2));
                    $(".cmt").append((findTotOfCamt()).toFixed(2));
                    $(".amt").append((findTotOfDamt()).toFixed(2));                    
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
              
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });



    }   //End function
    

    
    $('.hoaTabNot').on('change', 'tr td select', function () {
        var field = $(this).attr("class");        
        var HoaName = this.value;
        if (field.indexOf("hoaname") >= 0) {
            var nums = parseInt(field.match(/\d+/g));            
            $.ajax({
                url: "getHoaSsortByAcName.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({acName: HoaName}),
                success: function (hoashort) {                    
                    var shortCod = $.parseJSON(hoashort);
                    $(".selectSecod"+nums).empty();
                    $(".selectSecod"+nums).append("<option value='null'>select</option>");
                    $.each(shortCod, function (k, w) {
                        //alert(w.shortCode);
                        $(".selectSecod"+nums).append("<option value=" + w.shortCode + ">" + w.shortCode + "</option>");
                    });

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus + " :" + errorThrown);
                }
            });
            //var instalatio = $(".installation"+nums).val();            
            //$(".serviceTax"+nums).val(instalatio * TAX/100);           
        }
    });
   
        
    //***********START : HOA CALUCLATIONS**************************************  
        

    function findTotOfDamt() {
        var arr = document.getElementsByName('damount');
        var tot = 0;
        for (var i = 0; i < arr.length; i++) {
            if (parseFloat(arr[i].value))
                tot += parseFloat(arr[i].value);
        }
        return tot;
    }
    
    function findTotOfCamt() {
        var arr = document.getElementsByName('camount');
        var tot = 0;
        for (var i = 0; i < arr.length; i++) {
            if (parseFloat(arr[i].value))
                tot += parseFloat(arr[i].value);
        }
        return tot;
    }
    
    function findTotOfCb() {
        var arr = document.getElementsByName('cbcb');
        var tot = 0;
        for (var i = 0; i < arr.length; i++) {
            if (parseFloat(arr[i].value))
                tot += parseFloat(arr[i].value);
        }
        return tot;
    }
        //***********END :  HOA CALUCLATIONS************************************** 
    
    //******************************************************************
    //END: GENERATE TABLE FOR HEAD OF ACCOUNT
    //******************************************************************
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save voucher Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#compCode, #supplierName,#posr,#poNumber, #pprNumber,' +                
                '#voucherNo, #voucherDate, #chequeNo,' +
                '#chequeDate, #chequeAmount, #cqe_dd_rtgs, #bankCode, #bank,' +
                '#req_number, #lcBalanceAmt, #rtgsNumber, #ibcNumber'); 
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var val = [];
        var myDataB = [];
        $('#chckHoa:checked').each(function (i) {
            val[i] = $(this).val();
            var objB = {
                compCode: $("#compCode").val(),
                poNumBer: $("#poNumber").val(),
                hoaname: $(".hoaname" + val[i]).val(),
                shortcode: $(".shortcode" + val[i]).val(),
                cbamt: $(".cbamt" + val[i]).val(),
                creamt: $(".creamt" + val[i]).val(),
                debamt: $(".debamt" + val[i]).val()

            };
            myDataB.push(objB);

        });  
        
        var valb = [];
        var myData = [];
        $('#chckBalance:checked').each(function (i) {
            valb[i] = $(this).val();
            var objc = {
                compCode: $("#compCode").val(),
                poNumBer: $("#poNumber").val(),
                month: $(".month" + valb[i]).val(),
                openbalance: $(".openbalance" + valb[i]).val(),
                closebalance: $(".closebalance" + valb[i]).val(),
                dateofentry: $(".dateofentry" + valb[i]).val()                
            };
            myData.push(objc);
        }); 
            
        var jsonObj = JSON.stringify([obj]);        
        var parseObj = JSON.parse(jsonObj);
        
        var withHoa = [];               
        withHoa = JSON.stringify({ voucherNoDTO : parseObj,hoaDTO : myDataB, ocbDTO: myData});   
        //alert(withHoa);                 
        
        $.ajax({
            dataType : "json",
            url : "saveVoucherNoEn.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : withHoa,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#VoucherNo_Form")[0].reset(); 
                    $("#supplierName").selectpicker("refresh");
                    $("#compCode").selectpicker("refresh");                                       
                    $("#form_record").hide();
                    $("#form_page").show();
         
                    $("#stic_hoa").empty();
                    $("#stic_hoaTot").empty();
                    $("#table_one").show();
                    $("#table_two").show();
                    $("#firstSave").show();
                    $("#Lc_form_page").hide();
                    $("#updateDiv").css("display", "none");
                    $("#saveDdNumber").css("display", "block");
                    $('input[type=checkbox]').each(function ()
                    {
                        this.checked = false;
                    });
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
    //END Save voucher Number  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display voucher Number record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getVoucherNumberRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(voucher){            
            var result = $.parseJSON(voucher);
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
                           "<td>"+v.compCode+"</td><td>"+v.posr+"</td>"+  
                           "<td>"+v.poNumber+"</td><td>"+v.bank+"</td>"+
                           "<td>"+v.lcBalanceAmt+"</td><td>"+v.rtgsNumber+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='geneVouNoIbcPdf' style='margin-right: 3px;' value=" + v.voucherNoId + ">PDF </button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='geneRtgsLetterPdf' style='margin-right: 3px;' value=" + v.voucherNoId + ">PDFr </button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.voucherNoId + ">Edit </button></td> ";
                                            
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
                        {title: "Comp Code", width: '13%'},
                        {title: "POSR NO", width: '13%'},
                        {title: "Po NO", width: '10%'},
                        {title: "Bank", width: '13%'},
                        {title: "LC Balance", width: '13%'},
                        {title: "RTGS NO", width: '10%'},
                        {title: "Buttons", width: '18%'},
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
                    $("#VoucherNo_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getVoucherNoReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({VoucherNo_id: this.value}),
                        success: function (data) {                              
                            var result = $.parseJSON(data);
                            $.each(result, function (k, v) {                                
                                $("#voucherNoId").val(v.voucherNoId);
                                $("#compCode").val(v.compCode);
                                $("#supplierName").val(v.supplierName);  
                                $("#posr").val(v.posr); 
                                $("#poNumber").val(v.poNumber); 
                                $("#pprNumber").val(v.pprNumber); 
                                $("#voucherNo").val(v.voucherNo); 
                                $("#voucherDate").val(v.voucherDate);
                                $("#chequeNo").val(v.chequeNo);
                                
                                $("#chequeDate").val(v.chequeDate);
                                $("#chequeAmount").val(v.chequeAmount);
                                $("#cqe_dd_rtgs").val(v.cqe_dd_rtgs);  
                                $("#bankCode").val(v.bankCode); 
                                $("#bank").val(v.bank); 
                                $("#req_number").val(v.req_number); 
                                $("#lcBalanceAmt").val(v.lcBalanceAmt); 
                                $("#rtgsNumber").val(v.rtgsNumber);
                                $("#ibcNumber").val(v.ibcNumber);
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);                            
                            $("#compCode").selectpicker("refresh");
                            $("#supplierName").selectpicker("refresh");
                            $("#stic_hoa").empty();
                            $("#stic_hoaTot").empty();
                            $("#table_one").hide();
                            $("#table_two").hide();
                            $("#firstSave").show();
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
   
                $("#basic-usage").on('click', '#geneVouNoIbcPdf', function (){
                    //alert(this.value);
                    window.open('voucherNoIbcPdf.htm?eindi='+this.value);
                });    
                
                $("#basic-usage").on('click', '#geneRtgsLetterPdf', function (){
                    
                    window.open('rtgsLetterPdf.htm?eindi='+this.value);
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
        $("#VoucherNo_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $itemsU = $('#voucherNoId, #compCode, #supplierName,#posr,#poNumber, #pprNumber,' +
                '#voucherNo, #voucherDate, #chequeNo,' +
                '#chequeDate, #chequeAmount, #cqe_dd_rtgs, #bankCode, #bank,' +
                '#req_number, #lcBalanceAmt, #rtgsNumber, #ibcNumber'); 

        var obj = {};
        $itemsU.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateVoucherNumberDe.htm",
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
                    $("#stic_hoa").empty();
                    $("#stic_hoaTot").empty();
                    $("#table_one").hide();
                    $("#table_two").hide();
                    $("#firstSave").hide();
                    show_record();
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
        $("#stic_hoa").empty();
        $("#stic_hoaTot").empty();
        $("#table_one").hide();
        $("#table_two").hide();
        $("#firstSave").hide();
    });
  
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        $("#VoucherNo_Form")[0].reset();        
        $("#compCode").selectpicker("refresh");
        $("#supplierName").selectpicker("refresh");
        close_message();
        $("#stic_hoa").empty();
        $("#stic_hoaTot").empty();
        $("#table_one").show();
        $("#table_two").show();
        $("#firstSave").show();
        $("#Lc_form_page").hide();
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

   $("#show_lc_form").click(function (){
        $("#Lc_form_page").show();
        $("#form_record").hide();
        $("#form_page").hide();
        $("#table_one").hide();
        $("#table_two").hide();
        $("#firstSave").hide();
    });
    
    $("#showFormOnLc").on('click', function (){        
        $("#form_record").hide();
        $("#form_page").show();
        $("#VoucherNo_Form")[0].reset();        
        $("#compCode").selectpicker("refresh");
        $("#supplierName").selectpicker("refresh");
        close_message();
        $("#stic_hoa").empty();
        $("#stic_hoaTot").empty();
        $("#table_one").show();
        $("#table_two").show();
        $("#firstSave").show();
        $("#Lc_form_page").hide();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        
        $('input[type=checkbox]').each(function ()
        {
            this.checked = false;
        });
        
    });
    
    
    $("#save_lc_form").on('click', function (){
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $itemsU = $('#monthlc, #opbalance, #aamount,#total,#cmamount, #cbamount,' +
                '#dateoe'); 

        var obj = {};
        $itemsU.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "saveLcTable.htm",
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
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your LC Table data has been Successfully saved.");
                    $(window).scrollTop(0);    
                    $("#LC_Form")[0].reset(); 
                    
                    
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
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


    });  // end save lc form
    
    
    //******************************************************************
    //START: TABLE GENERATE FOR OPEN CLOSE BALANCE
    //****************************************************************** 
    function generateOpenBalance() {     
        $.ajax({
            url: "getOpenBalanceRe.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (balance) { 
                //alert(balance);
                var res = $.parseJSON(balance);                
                if($.isEmptyObject(res)){
                    $("#stic_balance").empty();
                    $("#stic_balance").append("This number has no data. ");
                }else{
                    var bislno = 1;
                    $("#stic_balance").empty();
                var slNo = 1;
                var biLNo = 1;
                var biLDate = 1;
                var open =1;
                var clo = 1;
                $.each(res, function (k, m) {
                    $("#stic_balance").append("<tr>" + 
                            "<td><input type='checkbox' name='selector[]' class='' id='chckBalance' value='" + (bislno++) + "' /></td>"+
                            "<td><input type='text' name='' class='painput' value='" + (slNo++) + "' readonly/></td>"+
                            "<td><input type='text' name='' class='painput month" + (biLNo++) + "' value='" + m.monthlc + "' readonly/></td>" +
                            "<td><input type='text' name='' class='painput openbalance" + (open++) + "' value='" + m.opbalance + "' readonly/></td>" +
                            "<td><input type='text' name='' class='painput closebalance" + (clo++) + "' value='" + m.aamount + "' readonly/></td>" +
                            "<td><input type='date' name='' class='painput dateofentry" + (biLDate++) + "' value='' /></td></tr>").css({"color":"#0000009c"});
                });
            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //END: TABLE GENERATE FOR OPEN CLOSE BALANCE
    //******************************************************************
    
    
    
 });  
