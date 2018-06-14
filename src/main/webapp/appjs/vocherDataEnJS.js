/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#voudare").addClass("active");
    generateCompCode();
    generateHoa();
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    //**************************************************************************
    //***START: APPEND PO DETAILS           ************************************
    //**************************************************************************
    
       
    
    
    //**************************************************************************
    //***END: APPEND PO DETAILS ************************************************
    //**************************************************************************
    
    //***********************************************
    //Getting venders from section_master table
    //***********************************************
        $.ajax({
        url: "getVendorName.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v) {
                    //alert(v.vendorCode);			
			$("#nameOfSupplr").append("<option value='"+v.vendorCode+"'>("
                                +v.vendorCode+")"+v.vendorName+"</option>");
                    });	
		$('#nameOfSupplr').selectpicker('refresh');  								
		},complete: function (data) {
		}
	});

    //**************************************************************************
    //***START: APPEND SECTION CODES
    //**************************************************************************

    $.ajax({
        url: "getSection.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);
                $("#scode").empty();
                    $("#scode").append("<option value='null'>select</option>");
                    $.each(result, function(k, v) {
                    //alert(v.sectionCode);			
			$("#scode").append("<option value='"+v.sectionCode+
                                "'>("+v.sectionCode+")"+v.sectionName+"</option>");
                    });	
		$('#scode').selectpicker('refresh');  								
		},complete: function (data) {
		},error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at generate section code");
        }
	});

    //**************************************************************************
    //***END: APPEND SECTION
    //**************************************************************************
    
    //***********************************************
    //START:
    //***********************************************
    var modeOfPmt;
    $("#bankLRC").on('change', function (){
        $("#errorBanklrc").empty();
        //alert(this.value+"---"+$("#modeOfPaymt").val());
        if($("#modeOfPaymt").val() === 'null' || $("#modeOfPaymt").val() === ''){
            $("#errorBanklrc").append("Please select 'Mode Of Payment'. ");
        }
    });
    
    //disa_enable();
    $("#ibcNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
    $("#pprNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
    //$("#bankLRC").prop('disabled', true);

    $("#pprNo2").css({"background-color": "#f7e6e6"}).prop('disabled', true);
    $("#amountPaid").css({"background-color": "#f7e6e6"}).prop('disabled', true);
    $("#dateOfpmt").css({"background-color": "#f7e6e6"}).prop('disabled', true);

    $("#lrNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
    $("#lrDT").css({"background-color": "#f7e6e6"}).prop('disabled', true);
    
    $("#modeOfPaymt").on('change', function () {
        $("#errorBanklrc").empty();
        modeOfPmt = this.value;
        var bank_lrc;
        //alert("----" + this.value);
        $("#bankLRC").val('null');
         $("#abc").val('null');
        var mode_of_pmt = this.value;
         
             
        if(mode_of_pmt === 'null' || mode_of_pmt === ''){
            $("#pprNo2").val('');
            $("#amountPaid").val('');
            $("#dateOfpmt").val('');
            $("#ibcNo").val("");
            $("#pprNo").val('');
            disa_enable();
        }else if (mode_of_pmt === 'A') {
            $("#ibcNo").css({"background-color": "#dedcdc"}).prop('disabled', false);
            $("#pprNo").css({"background-color": "#dedcdc"}).prop('disabled', false);
            
            $("#bankLRC").css({"background-color": "white"}).prop('disabled', false);
            $("#ibcNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            $("#pprNo2").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            $("#amountPaid").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            $("#dateOfpmt").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            
            $("#pprNo2").val('');
            $("#amountPaid").val('');
            $("#dateOfpmt").val('');
            $.ajax({
                url: "getPprNoFrInc.htm",
                type: "POST",
                cache: false,
                async: false,
                success: function (data) {
                    
                    var PprNo = parseInt(data);
                    $("#pprNo").val(PprNo);

                }, complete: function (data) {
                }
            });
        }else if(mode_of_pmt === 'B'){
            $("#pprNo2").css({"background-color": "white"}).prop('disabled', false);
            $("#amountPaid").css({"background-color": "white"}).prop('disabled', false);
            $("#dateOfpmt").css({"background-color": "white"}).prop('disabled', false);
            
            $("#ibcNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            $("#pprNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            
            $("#bankLRC").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            $("#lrNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            $("#lrDT").css({"background-color": "#f7e6e6"}).prop('disabled', true);
            
            $("#ibcNo").val('');
            $("#pprNo").val('');
            $("#lrNo").val('');
            $("#lrDT").val('');
        }else{
            $("#ibcNo").val("");
            $("#pprNo").val('');
        }
   
            $("#bankLRC").on('change', function (){
        
                bank_lrc = this.value;
                //alert(bank_lrc);
                if(bank_lrc === 'B' && mode_of_pmt === 'A'){
                    var poNM = parseInt($("#poNumber").val());                    
                    if(poNM <= 0){
                        alert("Please select PO number");
                    }else{
                        $.ajax({
                            url: "getIbcNoFrIbcMaByPo.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({PoNumb: poNM}),
                            success: function (dataIb) {                                                            
                                var res = $.parseJSON(dataIb);			
                                    $.each(res, function(k, x) {                        		
                                        $("#ibcNo").val(x.ibcNumber);
                                    });       
                            },error : function(jqXHR,textStatus,errorThrown ){
                                alert(textStatus+" :"+errorThrown);
                            }
                        });
                    }
                        
                    //$("#ibcNo").val('5');
                                       
                    $("#lrNo").val('');
                    $("#lrDT").val('');
                    $("#ibcNo").css({"background-color": "#dedcdc"}).prop('disabled', false);
                    $("#lrNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
                    $("#lrDT").css({"background-color": "#f7e6e6"}).prop('disabled', true);
                }else if(bank_lrc === 'L'){
                    $("#ibcNo").val('');
                    $("#ibcNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
                    $("#lrNo").css({"background-color": "white"}).prop('disabled', false);
                    $("#lrDT").css({"background-color": "white"}).prop('disabled', false);
                }else{
                    $("#ibcNo").val("");
                    $("#lrNo").val('');
                    $("#lrDT").val('');
                    $("#ibcNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
                    $("#lrNo").css({"background-color": "#f7e6e6"}).prop('disabled', true);
                    $("#lrDT").css({"background-color": "#f7e6e6"}).prop('disabled', true);
                }
            });
            
            
           
     });
     
     function disa_enable(){
         $("#ibcNo").prop('disabled', true);
            $("#pprNo").prop('disabled', true);
            $("#bankLRC").prop('disabled', true);
            
            $("#pprNo2").prop('disabled', true);
            $("#amountPaid").prop('disabled', true);
            $("#dateOfpmt").prop('disabled', true);
            
             $("#lrNo").prop('disabled', true);
             $("#lrDT").prop('disabled', true);
     }
     
      $("#abc").on('change', function () {
            var abc = this.value;            
            if (abc === 'A' && modeOfPmt === 'B') {
                $.ajax({
                    url: "getPprNoFrInc.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    success: function (data) {
                        var PprNo = parseInt(data);
                        $("#pprNo2").val(PprNo);
                        $("#pprNo2").css({"background-color": "#dedcdc"}).prop('disabled', true);
                    }, complete: function (data) {
                    }
                });
            } else {                
                //$("#pprNo2").css({"background-color": "white"}).prop('disabled', false);
                $("#pprNo2").val('');
            }
            });
     //*************************************************************************
     //***END:
     //*************************************************************************
     
     //*************************************************************************
     //***START:    COMP CODE INCREAMENT    ************************************
     //*************************************************************************
     function generateCompCode() {         
        $.ajax({
            url: "getCompCodeInc.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (data) {
                var comp = parseInt(data);
                $("#compCode").val(comp);

            }, complete: function (data) {
            }
        });
    }
    
    
    //**************************************************************************
    //START: DATA FETCHING FROM PO ENTRY DEPENDS ON PO NUMBER
    //**************************************************************************
    $("#fileNumber").keyup(function () {
        $("#errorFileNumber").empty();
        $("#errorpoNumber").empty();
        $("#appendList").empty();
        $("#stic_items").empty();
        $("#ibcNo").val(0); 
        $("#poNumber").empty();
        var fiNo = parseInt(this.value);
        if (fiNo <= 0 || isNaN(fiNo) === true) {
            $("#errorFileNumber").append("Please enter File Number. ");
            makeEmptyFunction();
        } else {
            
            $.ajax({
                url: "getIndentDeByFiNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({fileNo: fiNo}),
                success: function (indentForm) {
                    //alert(indentForm);
                    var res = $.parseJSON(indentForm);
                    $.each(res, function (k, i) {                        
                        $("#scode").val(i.sectionObj.sectionCode);                        
                        $('#scode').selectpicker('refresh'); 
                    });
                },error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at: Section code ");
                }
            });
            $.ajax({
                url: "getPoEntryDeByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({filNumber: fiNo}),
                success: function (response) {
                    var res = $.parseJSON(response);
                    if ($.isEmptyObject(res)) {
                        $("#errorFileNumber").append("This number has no data. ");
                        makeEmptyFunction();                    
                    } else {
                        $("#poNumber").empty();
                        $("#poNumber").append("<option value='null'>select</option>");
                        $.each(res, function (k, v) {
                            $("#poNumber").append("<option value=" + v.poNumber + ">" + v.poNumber + "</option>");
                        });

                        
                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }, beforeSend: function () {

                }, complete: function (data) {
                    $('#poNumber').selectpicker('refresh');
                }
            });
        }
    });
    
    $("#poNumber").on('change', function(){
        $("#errorpoNumber").empty(); 
        $("#stic_items").empty();
        var PoNumber = this.value;        
        if(PoNumber === 'null' || PoNumber === ''){
            $("#errorpoNumber").append("Please select PO number. ");
             $("#appendList").empty();
             $("#stic_items").empty();
        }else{
            generateItemsList(PoNumber);
            generate(PoNumber);   
            generateRVTableList(PoNumber);
            $.ajax({
            url: "getPoDetailsByFileNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PONumber: PoNumber}),
            success: function (porelese) {
                //alert(porelese);
                var result = $.parseJSON(porelese);
                if($.isEmptyObject(result)){
                    $("#errorpoNumber").append("This number has no vendor data. ");                    
                }else{
                $.each(result, function (k, v) {                     
                    $('#nameOfSupplr').val(v.venderName);
                    $('#nameOfSupplr').selectpicker('refresh');   
                                       
                });
            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {

            }
        });
        }
    });
    
   
    
    function makeEmptyFunction(){
        
        
    }
    //**************************************************************************
    //END: DATA FETCHING FROM PO ENTRY DEPENDS ON PO NUMBER
    //**************************************************************************
    
    
    //******************************************************************
    //START: BILL ENTRY DETAILS FOR APPEND TO BILL ITEMS LIST
    //****************************************************************** 
    function generateItemsList(poNoFrItemsList) {     
        $.ajax({
            url: "getBillEnReByPoNum.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PoNum: poNoFrItemsList}),
            success: function (billDa) {                 
                var res = $.parseJSON(billDa);                
                if($.isEmptyObject(res)){
                    $("#appendList").empty();
                    $("#appendList").append("This number has no data. ");
                }else{
                    var bislno = 1;
                    $("#appendList").empty();
                var slNo = 1;
                var biLNo = 1;
                var biLDate = 1;
                $.each(res, function (k, m) {
                    $("#appendList").append("<tr>" + 
                            "<td><input type='checkbox' name='selector[]' class='' id='chckBills' value='" + (bislno++) + "' /></td>"+
                            "<td><input type='text' name='' class='painputB' value='" + (slNo++) + "' readonly/></td>"+
                            "<td><input type='text' name='' class='painputB billNUM" + (biLNo++) + "' value='" + m.billNo + "' readonly/></td>" +
                            "<td><input type='text' name='' class='painputB billDATE" + (biLDate++) + "' value='" + m.billDate + "' readonly/></td></tr>").css({"color":"#0000009c"});
                });
            }
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
    //START: RV TABLE FORM CSRV 
    //****************************************************************** 
    function generateRVTableList(poNumber) {     
        $.ajax({
            url: "getCsrvDeByPoNomber.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PoNo_no: poNumber}),
            success: function (rvdata) { 
                //alert(rvdata);
                var res = $.parseJSON(rvdata);                
                if($.isEmptyObject(res)){
                    $("#stic_rv_list").empty();
                    $("#stic_rv_list").append("This number has no data. ");
                }else{
                    var rvslno = 1;
                    $("#stic_rv_list").empty();
                var rslNo = 1;
                var rvno = 1;
                var rvDate = 1;
                $.each(res, function (k, m) {
                    $("#stic_rv_list").append("<tr>" + 
                            "<td><input type='checkbox' name='selector[]' class='' id='chckRvnos' value='" + (rvslno++) + "' /></td>"+
                            "<td><input type='text' name='' class='painputB' value='" + (rslNo++) + "' readonly/></td>"+
                            "<td><input type='text' name='' class='painputB rvnumber" + (rvno++) + "' value='" + m.rvControlNo + "' readonly/></td>" +
                            "<td><input type='text' name='' class='painputB rvdate" + (rvDate++) + "' value='" + m.date + "' readonly/></td></tr>").css({"color":"#0000009c"});
                });
            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " Error occured at RV table:" + errorThrown);

            }, complete: function (data) {
            }
        });
    }
    //******************************************************************
    //END:  RV TABLE FORM CSRV 
    //******************************************************************
    
    
    //******************************************************************
    //START:  GENERATE TABLE FOR BILL ENTRY ITEM FROM BILL ENTRY SCREEN ITEMS
    //******************************************************************
   
    function generate(poNum) {        
        $("#stic_items").empty();
        $.ajax({
            url: "getBillEnItemsByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({PONumBer: poNum}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if($.isEmptyObject(result)){
                    $("#stic_items").empty();
                    $("#stic_items").append("This number has no data. ").css({"color":"#0000009c"});
                }else{
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
                var gbyss = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+w.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput rate" + (rate++) + "' value='" + v.rate + "' /></td>" +
                        "<td><input type='text' name='' class='painput igst" + (igst++) + "' value='"+v.igst+"' /></td>" +
                        "<td><input type='text' name='' class='painput cgst" + (cgst++) + "' value='"+v.cgst+"' /></td>" +
                        "<td><input type='text' name='' class='painput sgst" + (sgst++) + "' value='"+v.sgst+"' /></td>" +
                        "<td><input type='text' name='' class='painput hsnCode" + (hsn++) + "' value='"+v.hsnCode+"' /></td>"+
                        "<td><input type='text' name='' class='painput gbys" + (gbyss++) + "' value='"+v.gbys+"' /></td>"+
                        "<td><input type='text' name='' id='qtyrcd" + (qtyE++) + "' class='painput qtyrcd" + (qty++) + "' value='"+v.rcdQty+"' /></td>" +
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



   
    //******************************************************************
    //END: GENERATE TABLE FOR BILL ENTRY ITEM FROM BILL ENTRY SCREEN ITEMS
    //******************************************************************
    
    //******************************************************************
    //START: GENERATE TABLE FOR HEAD OF ACCOUNT
    //******************************************************************
        function generateHoa() {
        $("#stic_hoa").empty();
        $("#stic_hoaTot").empty();
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
        for (var i = 0; i <= 4; i++) {

            a = "<tr><td><input type='checkbox' name='selector[]' class='tab2CheckBox' id='chckHOA' value='" + (hoaslno++) + "' /></td>" +
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
                       d=  "<option value = "+v.accountType+">" +v.accountType+"</option>";
                       b = b+d;
                       
                    });
                    

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus + " :" + errorThrown);

                }, complete: function (jqXHR, textStatus) {
                   
                }

            });
            c = a+b+"</select></td>" +
                    "<td><select name='' id='tabSelect' class='seSecod selectSecod"+(aascd++)+"'>" +
                    "</select></td>" +
                    "<td><input type='text' name='cbcb' class='painput cbcbcb" + (cb++) + "' value='0' /></td>" +
                    "<td><input type='text' name='damount' class='painput debamount" + (damt++) + "' value='0' /></td>" +
                    "<td><input type='text' name='camount' class='painput creamount" + (camt++) + "' value='0' /></td>" +
                    "</tr>";
            b ="";
            $("#stic_hoa").append(c).css({"color": "#0000009c"});
            
        } //End for loop
       final = "<tr><td></td><td></td><td></td><td class='tott'>Total:</td><td class='cbmt'></td><td class='amt'></td><td class='cmt'></td></tr>"
        $("#stic_hoaTot").append(final);

    }   //End function
    
    $('.hoaTab').on('change', 'tr td select', function () {
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
        
        $("#recoveries").blur(function (){
           $("#defference").val('');
           $(".cbmt").empty();
           $(".cmt").empty();
           $(".amt").empty();
            //alert(findTotOfDamt()+"--"+findTotOfCamt()+"--"+findTotOfCb());
            $(".cbmt").append((findTotOfCb()).toFixed(2));
            $(".cmt").append((findTotOfCamt()).toFixed(2));
            $(".amt").append((findTotOfDamt()).toFixed(2));
            var diff = (findTotOfDamt() - findTotOfCamt() + findTotOfCb()).toFixed(2);
            $("#defference").val(diff);
            convertAmount(findTotOfDamt());
        });

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
    
    
    function saveItems(fileno, PONUM){
          
          var val = [];
          var myDataObj = [];
        $('#chckItems:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    fileNumber: fileno,
                    poNumBer: PONUM,                    
                    rate: $(".rate"+val[i]).val(),                    
                    igst: $(".igst"+val[i]).val(),
                    cgst: $(".cgst"+val[i]).val(),
                    sgst: $(".sgst"+val[i]).val(),
                    hsnCode: $(".hsnCode"+val[i]).val(),
                    gbys: $(".gbys"+val[i]).val(),
                    qtyReceived: $(".qtyrcd"+val[i]).val()                                      
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }
    
    function jsonHeadOfAc(fileno, PONUM){
          
          var val = [];
          var myData = [];
        $('#chckHOA:checked').each(function(i){
          val[i] = $(this).val(); 
            var obj = { 
                    fileNumber: fileno,
                    poNumBer: PONUM,                    
                    hoaName: $(".hoaname"+val[i]).val(),                    
                    shortCode: $(".selectSecod"+val[i]).val(),
                    cbcbcb: $(".cbcbcb"+val[i]).val(),
                    debitamt: $(".debamount"+val[i]).val(),
                    creditAmt: $(".creamount"+val[i]).val()                                                         
            };
            myData.push(obj);
            
        });
            
           // alert(JSON.stringify(myData));  
            return myData;
    }
    
    function makeJsonObjBillNos(fileno, PONUM){          
          var val = [];
          var myDataB = [];
        $('#chckBills:checked').each(function(i){
          val[i] = $(this).val(); 
            var objB = { 
                    fileNumber: fileno,
                    poNumBer: PONUM,                    
                    billNUM: $(".billNUM"+val[i]).val(),                    
                    billDATE: $(".billDATE"+val[i]).val()
                                                                            
            };
            myDataB.push(objB);
            
        });            
          //alert(JSON.stringify(myDataB));
            return myDataB;
    }
    
    function makeJsonObjRVNos(fileno, PONUM){          
          var valR = [];
          var myDataR = [];
        $('#chckRvnos:checked').each(function(i){
          valR[i] = $(this).val(); 
            var objR = { 
                    fileNumber: fileno,
                    poNumBer: PONUM,                    
                    rvnumber: $(".rvnumber"+valR[i]).val(),                    
                    rvdate: $(".rvdate"+valR[i]).val()
                                                                            
            };
            myDataR.push(objR);
            
        });            
          //alert(JSON.stringify(myDataR));
            return myDataR;
    }
    
    
    
    
    
    //******************************************************************
    //START: CONVERT AMOUNT IN WORDS
    //******************************************************************
    var iWords = ['zero', ' one', ' two', ' three', ' four', ' five', ' six', ' seven', ' eight', ' nine'];
    var ePlace = ['ten', ' eleven', ' twelve', ' thirteen', ' fourteen', ' fifteen', ' sixteen', ' seventeen', ' eighteen', ' nineteen'];
    var tensPlace = ['', ' ten', ' twenty', ' thirty', ' forty', ' fifty', ' sixty', ' seventy', ' eighty', ' ninety'];
    var inWords = [];

    var numReversed, inWords, actnumber, i, j;

    function tensComplication() {
        if (actnumber[i] === 0) {
            inWords[j] = '';
        } else if (actnumber[i] === 1) {
            inWords[j] = ePlace[actnumber[i - 1]];
        } else {
            inWords[j] = tensPlace[actnumber[i]];
        }
    }

    function convertAmount(numericValue) {
        //var numericValue = 14322589.23;
        numericValue = parseFloat(numericValue).toFixed(2);

        var amount = numericValue.toString().split('.');
        var taka = amount[0];
        var paisa = amount[1];
        //document.getElementById('container').innerHTML = convert(taka) +" rupees and "+ convert(paisa)+" paisa only";
        $("#rsInWords").val(convert(taka) + " rupees and " + convert(paisa) + " paisa only");
    }
    function convert(numericValue) {
        inWords = []
        if (numericValue === "00" || numericValue === "0") {
            return 'zero';
        }
        var obStr = numericValue.toString();
        numReversed = obStr.split('');
        actnumber = numReversed.reverse();


        if (Number(numericValue) === 0) {
            document.getElementById('container').innerHTML = 'BDT Zero';
            return false;
        }

        var iWordsLength = numReversed.length;
        var finalWord = '';
        j = 0;
        for (i = 0; i < iWordsLength; i++) {
            switch (i) {
                case 0:
                    if (actnumber[i] === '0' || actnumber[i + 1] === '1') {
                        inWords[j] = '';
                    } else {
                        inWords[j] = iWords[actnumber[i]];
                    }
                    inWords[j] = inWords[j] + '';
                    break;
                case 1:
                    tensComplication();
                    break;
                case 2:
                    if (actnumber[i] === '0') {
                        inWords[j] = '';
                    } else if (actnumber[i - 1] !== '0' && actnumber[i - 2] !== '0') {
                        inWords[j] = iWords[actnumber[i]] + ' hundred';
                    } else {
                        inWords[j] = iWords[actnumber[i]] + ' hundred';
                    }
                    break;
                case 3:
                    if (actnumber[i] === '0' || actnumber[i + 1] === '1') {
                        inWords[j] = '';
                    } else {
                        inWords[j] = iWords[actnumber[i]];
                    }
                    if (actnumber[i + 1] !== '0' || actnumber[i] > '0') {
                        inWords[j] = inWords[j] + ' thousand';
                    }
                    break;
                case 4:
                    tensComplication();
                    break;
                case 5:
                    if (actnumber[i] === '0' || actnumber[i + 1] === '1') {
                        inWords[j] = '';
                    } else {
                        inWords[j] = iWords[actnumber[i]];
                    }
                    if (actnumber[i + 1] !== '0' || actnumber[i] > '0') {
                        inWords[j] = inWords[j] + ' lakh';
                    }
                    break;
                case 6:
                    tensComplication();
                    break;
                case 7:
                    if (actnumber[i] === '0' || actnumber[i + 1] === '1') {
                        inWords[j] = '';
                    } else {
                        inWords[j] = iWords[actnumber[i]];
                    }
                    inWords[j] = inWords[j] + ' crore';
                    break;
                case 8:
                    tensComplication();
                    break;
                default:
                    break;
            }
            j++;
        }


        inWords.reverse();
        for (i = 0; i < inWords.length; i++) {
            finalWord += inWords[i];
        }
        return finalWord;
    }
    //******************************************************************
    //START: CONVERT AMOUNT IN WORDS
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
        var File_No = $("#fileNumber").val();
        var PO_Number = $("#fileNumber").val();
        var $items = $('#fileNumber, #poNumber, #posrFileNo, #posrFileNo, #nameOfSupplr,#gcode, #fileClosed,' +
                '#modeOfPaymt, #abc, #bbys, #scode, #rpumOrmngr,' +
                '#cqeDdR, #pprNo, #lrNo, #bankLRC,' +
                '#lrDT, #quantity, #bgClause, #ibcNo, #pprNo2, #amountPaid,' +
                '#dateOfpmt, #recoveries,' +
                '#defference, #rsInWords, #compCode');
        
         

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });    

        var jsonObj = JSON.stringify([obj]);
        
        var parseObj = JSON.parse(jsonObj);                
        
        for (var i = 0; i < parseObj.length; i++) {
            if (parseObj[i].pprNo === '') {
                parseObj[i].pprNo = 0;                
            }
            if (parseObj[i].lrNo === '') {
                parseObj[i].lrNo = 0;                 
            }
            if (parseObj[i].ibcNo === '') {
                parseObj[i].ibcNo = 0;                
            }
            if (parseObj[i].pprNo2 === '') {
                parseObj[i].pprNo2 = 0;                
            }
            if (parseObj[i].amountPaid === '') {
                parseObj[i].amountPaid = 0; 
                break;
            }            
        }
        
        var big = [];
                var obj1 = saveItems(File_No, PO_Number);
                var obj2 = jsonHeadOfAc(File_No, PO_Number);
                var obj3 = makeJsonObjBillNos(File_No, PO_Number);
                var obj4 = makeJsonObjRVNos(File_No, PO_Number);
                big = JSON.stringify({ voucherDTO : parseObj,itemsDTO : obj1,hoaDTO : obj2, billsDTO: obj3, rvDTO: obj4});   
                //alert(big);                 
        $.ajax({
            dataType: "json",
            url: "saveVoucherDataEn.htm",            
            contentType: 'application/json',
            mimeType: 'application/json',
            data: big,
            type: 'POST',
            success: function (data) { 
                 //var aaa = $.parseJSON(data);	
                //alert(JSON.stringify(data));
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong>Your data has been Successfully saved.");
                    $(window).scrollTop(0);                    
                    $("#VoucherDaEnSeForm")[0].reset();
                    $("#VoucherDaEnForm")[0].reset();  
                    $("#nameOfSupplr").selectpicker("refresh"); 
                    $("#poNumber").selectpicker("refresh");
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();                       
                }else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {                
                show_error_mes();
                $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while updating data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {               
                return confirm("Are you sure you want save data ?");
            }, complete: function (data) {
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
        url: "getVoucherDaEnRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(vouche){            
            var result = $.parseJSON(vouche);
            if($.isEmptyObject(result)){
                $("#basic-usage").remove();
                $('#basic-usage').load('ajax.html#basic-usage');
                alert("There is no records");
            }else{
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.fileNumber+"</td><td>"+v.poNumber+"</td>"+  
                           "<td>"+v.quantity+"</td>"+
                           "<td>"+v.amountPaid+"</td><td>"+v.rsInWords+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='geneHrauRpuPdf' style='margin-right: 3px;' value=" + v.voucherID + ">pdf1</button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='geneVouDataPdf' style='margin-right: 3px;' value=" + v.voucherID + ">pdf</button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.voucherID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(6)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "File NO", width: '13%'},
                        {title: "Po NO", width: '13%'},                        
                        {title: "Quantity", width: '13%'},
                        {title: "Amount Paid", width: '13%'},
                        {title: "Rs In Words", width: '13%'},
                        {title: "Buttons", width: '25%'},
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
                    $("#VoucherDaEnForm")[0].reset();
                    $("#VoucherDaEnSeForm")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getVoucherDaEnReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({VouCher_id: this.value}),
                        success: function (VouCher) {                              
                            var result = $.parseJSON(VouCher);
                            $.each(result, function (k, v) {                                
                                $("#voucherID").val(v.voucherID);
                                $("#fileNumber").val(v.fileNumber);
                                $("#poNumber").val(v.poNumber);  
                                $("#posrFileNo").val(v.posrFileNo); 
                                $("#bgClause").val(v.bgClause); 
                                $("#nameOfSupplr").val(v.nameOfSupplr); 
                                $('#nameOfSupplr').selectpicker('refresh');  
                                $("#gcode").val(v.gcode); 
                                $("#fileClosed").val(v.fileClosed); 
                                $("#modeOfPaymt").val(v.modeOfPaymt);
                                
                                $("#abc").val(v.abc);
                                $("#bbys").val(v.bbys);
                                $("#scode").val(v.scode); 
                                $('#scode').selectpicker('refresh');  
                                $("#rpumOrmngr").val(v.rpumOrmngr); 
                                $("#cqeDdR").val(v.cqeDdR); 
                                $("#pprNo").val(v.pprNo); 
                                $("#lrNo").val(v.lrNo); 
                                $("#bankLRC").val(v.bankLRC); 
                                $("#lrDT").val(v.lrDT);
                                
                                $("#quantity").val(v.quantity);
                                $("#ibcNo").val(v.ibcNo);
                                $("#pprNo2").val(v.pprNo2);  
                                $("#amountPaid").val(v.amountPaid); 
                                $("#dateOfpmt").val(v.dateOfpmt); 
                                $("#recoveries").val(v.recoveries); 
                                $("#defference").val(v.defference); 
                                $("#rsInWords").val(v.rsInWords); 
                                $("#compCode").val(v.compCode);
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#nameOfSupplr").selectpicker("refresh"); 
                            $("#poNumber").selectpicker("refresh");
                            $("#hoaDivSub").hide();
                            $("#itemsDivSub").hide();
                            $("#billRvDivSub").hide();
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
                
                tableRowHighlighting();
                
                $("#basic-usage").on('click', '#geneVouDataPdf', function (){
                    
                    window.open('voucherDataPdf.htm?eindi='+this.value);
                }); 
                
                $("#basic-usage").on('click', '#geneHrauRpuPdf', function (){
                    
                    window.open('voucherDaHrauRpuPdf.htm?eindi='+this.value);
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
        $("#VoucherDaEnForm")[0].reset();
        $("#VoucherDaEnSeForm")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#voucherID, #fileNumber, #poNumber, #posrFileNo, #posrFileNo, #nameOfSupplr,#gcode, #fileClosed,' +
                '#modeOfPaymt, #abc, #bbys, #scode, #rpumOrmngr,' +
                '#cqeDdR, #pprNo, #lrNo, #bankLRC,' +
                '#lrDT, #quantity, #bgClause, #ibcNo, #pprNo2, #amountPaid,' +
                '#dateOfpmt, #recoveries,' +
                '#defference, #rsInWords, #compCode');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateVoucherDataEnDe.htm",
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
                    $("#VoucherDaEnForm")[0].reset();
                    $("#VoucherDaEnSeForm")[0].reset();
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
        $("#VoucherDaEnForm")[0].reset();
        $("#VoucherDaEnSeForm")[0].reset();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        $("#nameOfSupplr").selectpicker("refresh"); 
        $("#poNumber").selectpicker("refresh");
        $("#scode").selectpicker("refresh"); 
        $("#hoaDivSub").show();
        $("#itemsDivSub").show();
        $("#billRvDivSub").show();
        $("#appendList").empty();
        $("#stic_items").empty();
        generateCompCode();
        generateHoa();
    });
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************
    function tableRowHighlighting() {
        var table = $('#basic-usage').DataTable();

        $('#basic-usage tbody')
                .on('mouseenter', 'td', function () {
                    var colIdx = table.cell(this).index().column;

                    $(table.cells().nodes()).removeClass('highlight');
                    $(table.column(colIdx).nodes()).addClass('highlight');
                });
    }
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 

    
    
}); //End document

    function openVoucherRe(){
                
        window.open("./voucherDaRe.htm","_parent");
    }