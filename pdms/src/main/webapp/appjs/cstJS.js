/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    $("#indentNumber").customselect();
    

        $("#ItemBasedCstPage").hide();
        $("#VendorBasedCstPage").hide();
        $("#indeviewH").hide();
        $("#vendorListH").hide();        
        $("#cstEntryH").hide();
        $("#itemhidshow").hide();
    var vendorNameBlobal;    
    var vendorIdGlobal;
    var itemIdGlobal;
    var categortIdGlobal;
    $.ajax({
        url: "getFileNumbers.htm",
        type: "POST",
        cache: false,
        success: function (indet) {
            //alert(indet);
            var result = $.parseJSON(indet);
            $.each(result, function (k, v) {
                //alert(v.fileNoFrMapp);
                $("#FileNumber").append("<option value='"+v.fileNoFrMapp+"'>"+v.fileNoFrMapp+"</option>");
                $("#FileNumber").selectpicker("refresh");
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert(textStatus+": Please Contac Adminstrative Department");
        }, complete: function (jqXHR, textStatus) {

        }, beforeSend: function (xhr) {

        }
    });
    var fileNo;
    $("#fetchIndent").click(function () {
        $("#ItemBasedCstPage").hide();
        $("#VendorBasedCstPage").hide();
        $("#errorFileNo").html("");
        fileNo = $("#FileNumber").val();
        //alert(fileNo);
        if ($.trim(fileNo) === '0' || $.trim(fileNo) === 'null')
        {
            $("#errorFileNo").html("Please select File Number.");
            $("#indeviewH").hide();
            return false;
        } else {
           
            addIndentorDetails(fileNo);
            $.ajax({
                url: "getTenderIdByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({fileNum: fileNo}),
                success: function (vendor) {
                    $("#stic_category").empty();
                    if(parseInt(vendor) === 0 || parseInt(vendor) === ''){
                        $("#stic_category").append("This number has no data. ").css({"color":"red"});
                        $("#venderName").empty();
                        $("#add_vendors").empty();
                    }else{
                    //alert(vendor);
                        $.ajax({
                            url: "getPubTenDeByTenId.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({FkPubTenderId: parseInt(vendor)}),
                            success: function (itemCatego) {
                                //alert("cate--" + itemCatego);
                                $("#stic_category").empty();
                                var res = $.parseJSON(itemCatego);
                                if ($.isEmptyObject(res)) {
                                    $("#stic_category").append("This number has no data. ").css({"color": "red"});
                                    $("#venderName").empty();
                                    $("#add_vendors").empty();
                                } else {
                                    var tempVal;
                                    $.each(res, function (k, z) {
                                        //alert(z.itemObj.itemName + "---+" + z.itemObj.categoryDTO.categoryName);
                                        tempVal = z.itemObj.categoryDTO.categoryName;

                                    });
                                    $("#stic_category").append(tempVal).css({"color": "black"});
                                    
                                }
                            }
                        });

                }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus);
                },beforeSend: function (xhr) {
                   
                },complete: function (jqXHR, textStatus) {
                   
                }
            });  
        }


    });
    
    function addIndentorDetails(filNNo) {
        $.ajax({
            url: "getIndentDeByFiNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({fileNo: filNNo}),
            success: function (indet) {
                //alert(indet);
                $("#indeviewH").show();
                $("#errorFileNo").empty();
                var result = $.parseJSON(indet);
                if ($.isEmptyObject(result)) {
                    $("#errorFileNo").append("This number has no data. ");
                } else {
                    $("#stic_indent").empty();
                    $.each(result, function (k, v) {
                        //alert(v.itemObj.categoryDTO.categoryID);
                        $("#stic_indent").append("" +
                                "<div class='form-group col-md-6 col-lg-3'>" +
                                "<label for='username'>Indent No#: </label><br/>" +
                                "<label>" + v.indentNumber + "</label>" +
                                "</div>" +
                                "<div class='form-group col-md-6 col-lg-2'>" +
                                "<label>Indent Date:</label><br/>" +
                                "<label>" + v.indentDate + "</label>" +
                                "</div>" +
                                "<div class='form-group col-md-6 col-lg-2'>" +
                                "<label>Estimated Cost:</label><br/>" +
                                "<label>" + v.estimatedCost + "</label>" +
                                "</div>" +
                                "<div class='form-group col-md-6 col-lg-2'>" +
                                "<label>Group Name:</label><br/>" +
                                "<label>G2</label>" +
                                "</div>" +
                                "<div class='form-group col-md-6 col-lg-2'>" +
                                "<label>File No:</label><br/>" +
                                "<label>" + fileNo + "</label>" +
                                "</div>");

                    });


                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + ": Please Contac Adminstrative Department");
            }, complete: function (jqXHR, textStatus) {
                cstPreparedVendors(fileNo);
            }, beforeSend: function (xhr) {

            }
        });
    }
    
     var afterVenGlogal = [];
    function cstPreparedVendors(fileNumBer) {        
        $("#vendorListH").show();
        $("#add_venView").empty();
        $("#add_vendors").empty();
        $.ajax({
            url: "getCstDeByFileNoVenNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({fileNo: fileNumBer, vid: 3}),
            success: function (cstDe) {
                //alert(cstDe+"cstde");
                var result = $.parseJSON(cstDe);
                if ($.isEmptyObject(result)) {
                    alert("This file number has no CST preparation data for vendor");                    
                } else {
                    var ven = [];
                    $.each(result, function (k, z) {
                        //alert("ven id: "+z.vendorID);
                        
                        ven.push(z.vendorID);

                    });
                    var unique = ven.filter(function (elem, index, self) {
                        return index === self.indexOf(elem);
                    })
                    afterVenGlogal = unique;
                    //alert(unique);
                    for (var i = 0; i < unique.length; i++) {
                        //alert(unique[i]);
                        $.ajax({
                            url: "getVendorDeByVenId.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({Vendor_ID: unique[i]}),
                            success: function (venDE) {
                                //alert(venDE);
                                var VenResult = $.parseJSON(venDE);
                                if ($.isEmptyObject(VenResult)) {
                                    alert("There is no data");
                                } else {
                                    
                                    $.each(VenResult, function (k, w) {
                                       
                                        $("#add_venView").append("<tr>" +
                                                "<td>" + w.vendorID + "</td>" +
                                                "<td>" + w.vendorCode + "</td>"+
                                                "<td>" + w.vendorName + "</td><td>" + w.vendorAddress + "</td>" +
                                                "<td>" + w.vendorPhone + "</td><td>" + w.vendorCity + "</td>" +
                                                "<td><button type='button' class='btn btn-info btn-rounded btn-sm' " +
                                                "id='ViewVendorCst' value="+ w.vendorID + ">View CST </button></td>" +
                                                "</tr>");
                                    });
                                    window.scrollTo(0,document.body.scrollHeight);
                                }
                                
                                

                            }
                        });
                    }
                }

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error : " + textStatus);
            }, complete: function (jqXHR, textStatus) {
                
            }
        });        
       
    }
    
    $("#basic-usage").on('click', '#ViewVendorCst', function () {   
        var venIdViewCst = this.value;
        $("#ItemBasedCstPage").hide();
        $("#VendorBasedCstPage").show();
        $("#vendorListH").hide();
        $("#cstEntryH").hide();
        $("#venidshow").show();
        $("#itemhidshow").hide();        
        $.ajax({
            url: "getVendorDeByVenId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({Vendor_ID: venIdViewCst}),
            success: function (itemname) {                
                    $("#vendorIdd").empty();
                    $("#vendorCodee").empty();
                    $("#vendorNamme").empty();
                    $("#vendorCitty").empty();
                var pubresult = $.parseJSON(itemname);
                $.each(pubresult, function (k, x) {
                    $("#vendorIdd").append(x.vendorID);                    
                    $("#vendorCodee").append(x.vendorCode);
                    $("#vendorNamme").append(x.vendorName);
                    $("#vendorCitty").append(x.vendorCity);                    
                });
                
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at: getVendorDetails"+textStatus);
            }
        });
        generateItemBasedCST(fileNo, venIdViewCst);
    });
    
    $("#VendorNameButton").on('click', function () {
        var venName = $("#venderName").val();     
        var vendor_code;                    
        if (/[a-zA-Z]/.test(venName)) {            
            $.ajax({
                url: "getVendorCodeByName.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({vendor_name: venName}),
                success: function (vendor) {
                    vendor_code = $.parseJSON(vendor);
                    
                }
            });
        } else {            
            vendor_code = venName;
        }        
        $.ajax({
            url: "getVendorNameByCode.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({venCode:vendor_code}),
            success: function (vendor) {
                //alert(vendor);
                $("#ItemBasedCstPage").hide();
                $("#VendorBasedCstPage").hide();
                $("#vendorListH").show();
                var result = $.parseJSON(vendor);                
                $("#add_vendors").empty();
                $("#errorVenName").empty();                
                $.each(result, function (k, z) {                   
                    if(afterVenGlogal.indexOf(z.vendorID) > -1){
                        $("#add_vendors").empty();
                        $("#errorVenName").empty();  
                        $("#errorVenName").append("Vendor cst preparation already done. ");
                    }else{
                        $("#add_vendors").append("<tr>" +                            
                            "<td>" +z.vendorID+ "</td><td>" +z.vendorCode+ "</td>" +
                            "<td>" +z.vendorName+ "</td><td>" + z.vendorAddress + "</td>" +
                            "<td>" +z.vendorPhone+ "</td><td>" +z.vendorCity+ "</td>"+
                            "<td><button type='button' class='btn btn-info btn-rounded btn-sm' " +
                            "id='cstPreparation' value=" + z.vendorCode + ">Prepare CST </button></td>" +
                            "</tr>");
                    }
                    vendorNameBlobal = z.vendorName;
                    vendorIdGlobal = z.vendorID;
                });
                window.scrollTo(0,document.body.scrollHeight);
                
                $("#cstPreparation").click(function (){
                    //alert(fileNo+"-----"+this.value);
                    $("#vendorListH").hide();
                    
                    $("#indeviewH").hide();
                    $("#mapVenH").hide();
                    $("#cstEntryH").show();
                    $.ajax({
                        url: "getTenderIdByFileNo.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({fileNum: fileNo}),
                        success: function (vendor) {
                            //alert(vendor);
                            $.ajax({
                                url: "getPubTenDeByTenId.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({FkPubTenderId: parseInt(vendor)}),
                                success: function (itemCatego) {
                                    //alert(itemCatego);
                                    var res = $.parseJSON(itemCatego);
                                    var slno = 1;
                                    var ckslno = 1;
                                    var cstTyp = 1;
                                    var itemId = 1;
                                    var basRate = 1;
                                    var price = 1;
                                    var disco = 1;
                                    var pAndF = 1;
                                    var exicDu = 1;
                                    var saleTax = 1;
                                    var frig = 1;
                                    var install = 1;
                                    var serTaxPer = 1;
                                    var serTax = 1;
                                    var inspec = 1;
                                    var testAmt = 1;
                                    var sampl = 1;
                                    var intrst = 1;
                                    var delPerio = 1;
                                    var payme = 1;
                                    var vslida = 1;
                                    var valDate = 1;
                                    var landCost = 1;
                                    var totAmt = 1;
                                    var totLandCost = 1;
                                    var remark = 1;
                                    
                                    var insur = 1;
                                    var cusDuty = 1;
                                    var cleCha = 1;
                                    var inlaFrig = 1;
                                    
                                    var insurTD = 1;
                                    var subtt = 1;
                                    var qtty = 1;
                                    var uunt = 1;
                                    var trainn = 1;
                                    var gsitit = 1;
                                    var anyoot = 1;
                                    var octroi = 1;
                                    var graad = 1;
                                    var hssnc = 1;
                                    var mrp = 1;
                                    var unnori = 1;
                                    $("#stic_cstEntry").empty();
                                    $("#stic_cstEntryTotal").empty();
                                    var tempCate;
                                    var DueDate;
                                    $.ajax({
                                        url: "getPublicTenderDeByFileNo.htm",
                                        type: "POST",
                                        cache: false,
                                        async: false,
                                        data: ({file_no: fileNo}),
                                        success: function (pubtende) {
                                            //alert(pubtende);
                                            var pubresult = $.parseJSON(pubtende);                                            
                                            $.each(pubresult, function (k, x) {
                                                DueDate = x.dueDate;
                                            });
                                        }, error: function (jqXHR, textStatus, errorThrown) {
                                            alert(textStatus);
                                        }
                                    });
                                    
                                    $.each(res, function (k, z) {
                                        //alert(z.itemDto.indentSlNo+ "---+");
                                        $("#stic_cstEntry").append("<tr>" +
                                                "<td><input type='checkbox' name='selector[]' class='' id='chck' value='"+ (ckslno++) +"' /></td>" +
                                                "<td><input type='text' name='' class='painput' value='"+ (slno++) +"' readonly/></td>" +
                                                "<td><select name='' class='painput cstType"+(cstTyp++)+"' id='table_sele1'>"+
                                                    "<option value='null' selected>select</option>"+
                                                    "<option value='Local'>Local Exachange</option>"+
                                                    "<option value='Forgin'>Foreign Exchange</option>"+                                                  
                                                "</select></td>"+                                                
                                                "<td><select name='' class='painput itemID"+(itemId++)+"' id='table_sele2'>"+
                                                    "<option value='"+z.itemObj.itemID+"' selected>"+z.itemObj.itemName+"</option>"+
                                                "</select></td>"+
                                                "<td><input type='text' name='' class='painput itemqty"+(qtty++)+"' value='"+z.itemQty+"' /></td>"+
                                                "<td><input type='text' name='' class='painput unitsss"+(uunt++)+"' value='"+z.itemObj.unitDTO.unitName+"' />"+
                                                "<input type='hidden' name='' class='units"+(unnori++)+"' value='"+z.itemUnits+"' /></td>"+
                                                "<td><input type='text' name='' class='painput basicRate"+(basRate++)+"' value='' /></td>"+
                                                "<td><select name='' class='painput price"+(price++)+"' id='table_sele1'>"+
                                                    "<option value='null' selected>Select</option>"+
                                                    "<option value='Works'>Works</option>"+
                                                    "<option value='Price'>Price</option>"+
                                                    "<option value='CityPrice'>City Price</option>"+
                                                    "<option value='Destination'>Destination Price</option>"+
                                                "</select></td>"+
                                                
                                                "<td><input type='text' name='' class='painput discount"+(disco++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput subtotal"+(subtt++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput packetForwardCharges"+(pAndF++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput exciseDuty"+(exicDu++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput salesTax"+(saleTax++)+"' value='' /></td>" +
                                                
                                                "<td><input type='text' name='' class='painput insurance"+(insur++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput customDuty"+(cusDuty++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput cleaningCharges"+(cleCha++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput inlandFreight"+(inlaFrig++)+"' value='' /></td>" +
                                                
                                                "<td><input type='text' name='' class='painput freight"+(frig++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput installation"+(install++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput serviceTaxPercent"+(serTaxPer++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput serviceTax"+(serTax++)+"' value='' /></td>" +  
                                                "<td><input type='text' name='' class='painput trainingcha"+(trainn++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput inspection"+(inspec++)+"' value='' /></td>" +                                                
                                                "<td><input type='text' name='' class='painput testingAmount"+(testAmt++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput gstonitit"+(gsitit++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput anyother"+(anyoot++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput octroitax"+(octroi++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput sample"+(sampl++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput intrest"+(intrst++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput deliveryPeriod"+(delPerio++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput payment"+(payme++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput validityDays"+(vslida++)+"' value='"+validatyDays(DueDate)+"' /></td>" +
                                                "<td><input type='text' name='' class='painput validityDate"+(valDate++)+"' value='"+DueDate+"' /></td>" +
                                                "<td><input type='text' name='' class='painput landingCost"+(landCost++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput totalAmount"+(totAmt++)+"' value='' /></td>" +
                                                "<td><input type='text' name='totLandingCost' class='painput totalLandingCost"+(totLandCost++)+"' value='' /></td>" +                                                
                                                "<td><input type='text' name='' class='painput hsncode"+(hssnc++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput mrprice"+(mrp++)+"' value='' /></td>" +
                                                "<td><input type='text' name='' class='painput remarks"+(remark++)+"' value='' /></td>" +
                                                "</tr>");
                                        tempCate = z.itemObj.categoryDTO.categoryName;
                                        categortIdGlobal = z.itemObj.categoryDTO.categoryID;
                                    });
                                    $("#stic_cstEntryTotal").append("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>"+
                                            "<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>"+
                                            "<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td id='totalClick'>Total</td><td></td><td></td>"+
                                            "<td><input type='text' name=''  id='stic_totLandingCost' /></td><td></td><td></td><td></td></tr>"); 
                                    
                                    $("#cateInCstPre").empty();
                                    $("#cateInCstPre").append(tempCate).css({"color":"#171414cc"});
                                    $("#venInCstPre").empty();
                                    $("#venInCstPre").append(vendorNameBlobal).css({"color":"#171414cc"});
                                    //alert(vendorIdGlobal);
                                    
                                    $("#totalClick").click(function () {
                                        $("#stic_totLandingCost").val('');
                                        $("#stic_totLandingCost").val((findTotOfLandingAmt()).toFixed(2));
                                    });
                                }
                            });
                            
                            
                        },error: function (jqXHR, textStatus, errorThrown) {
                            alert(textStatus);
                        }
                    });    
                });

            }, complete: function (data) {
                
            }
        });

    });
    
   //*************START:  COUNT VALIDITY DAYS***********************************
    $('.table').on('keyup', 'tr td input', function () {
        var field = $(this).attr("class"); 
        var duedate = this.value;
        if(field.indexOf("validityDate") >= 0) {
            var num = parseInt(field.match(/\d+/g));
            $(".validityDays"+num).val(validatyDays(duedate));
           
        }
    });
    //*************END:  COUNT VALIDITY DAYS************************************
    
    //*************START: BASIC RATE CALUCLATION FOR SUB TOTAL******************
    $('.table').on('keyup', 'tr td input', function () {
        var field = $(this).attr("class"); 
        var basicRateC = this.value;
        if(field.indexOf("basicRate") >= 0) {
            var numr = parseInt(field.match(/\d+/g)); 
            var discountC = $(".discount"+numr).val();                               
            $(".subtotal"+numr).val((parseFloat(return_number(basicRateC)) - parseFloat(return_number(discountC))).toFixed(2));           
        }
    });   
    //*************END:  BASIC RATE CALUCLATION FOR SUB TOTAL*******************
    
    //*************START: discount CALUCLATION FOR SUB TOTAL******************
    $('.table').on('keyup', 'tr td input', function () {
        var field = $(this).attr("class"); 
        var discountC = this.value;
        if(field.indexOf("discount") >= 0) {
            var numr = parseInt(field.match(/\d+/g)); 
            var basicraC = $(".basicRate"+numr).val();                                  
            $(".subtotal"+numr).val((parseFloat(return_number(basicraC)) - parseFloat(return_number(discountC))).toFixed(2));           
        }
    });   
    //*************END: discount CALUCLATION FOR SUB TOTAL*******************
  
    //*************START: LANDING COST CALUCLATION *****************************
    $('.table').on('keyup', 'tr td input', function () {
        var field = $(this).attr("class"); 
        var TestingAmt = this.value;
        if(field.indexOf("payment") >= 0) {
            var num = parseInt(field.match(/\d+/g)); 
            
            var qtyC = $(".itemqty"+num).val();
            var subTotalC = $(".subtotal"+num).val();
            var pAndFc = $(".packetForwardCharges"+num).val();
            var exciseDutyC = $(".exciseDuty"+num).val();
            var salesTaxC = $(".salesTax"+num).val();
            var customDutyC = $(".customDuty"+num).val();
            var freightC = $(".freight"+num).val();
            var installationC = $(".installation"+num).val();
            var inspectionC = $(".inspection"+num).val();  
            var testingAmountC = $(".testingAmount"+num).val();
            
            var trainingchaC = $(".trainingcha"+num).val();
            var anyotherC = $(".anyother"+num).val();
            var octroitaxC = $(".octroitax"+num).val();
            var gstonititC = $(".gstonitit"+num).val();
            
            var pAndFcA = parseFloat(return_number(subTotalC)) * (parseFloat(return_number(pAndFc))/100);
            var exciseDutyCA = parseFloat(return_number(subTotalC)) * (parseFloat(return_number(exciseDutyC))/100);
            var salesTaxCA = parseFloat(return_number(subTotalC)) * (parseFloat(return_number(salesTaxC))/100);
            var customDutyCA = parseFloat(return_number(subTotalC)) * (parseFloat(return_number(customDutyC))/100);
            var freightCA = parseFloat(return_number(subTotalC)) * (parseFloat(return_number(freightC))/100);
            var installationCA = parseFloat(return_number(installationC));
            var inspectionCA = parseFloat(return_number(inspectionC));
            var testingAmountCA = parseFloat(return_number(testingAmountC));
            
            var trainingchaCA = parseFloat(return_number(trainingchaC));
            var anyotherCA = parseFloat(return_number(anyotherC));
            var octroitaxCA = parseFloat(return_number(subTotalC)) * (parseFloat(return_number(octroitaxC))/100);
            
            var gstOnititTotC = parseFloat(return_number(installationC))+parseFloat(return_number(trainingchaC))+
                                parseFloat(return_number(inspectionC))+parseFloat(return_number(testingAmountC));
                        
            var gstOnititTotCA = parseFloat(return_number(gstOnititTotC)) * (parseFloat(return_number(gstonititC))/100);            
            
            var total = pAndFcA+exciseDutyCA+salesTaxCA+customDutyCA+freightCA+installationCA
                    +inspectionCA+testingAmountCA+trainingchaCA+anyotherCA+octroitaxCA+gstOnititTotCA+
                    parseFloat(return_number(subTotalC));
                                           
            $(".landingCost"+num).val((total).toFixed(2));   
            var totalByQty = ((parseFloat(return_number(total))) * (parseFloat(return_number(qtyC))));
            $(".totalAmount"+num).val((totalByQty).toFixed(2)); 
            $(".totalLandingCost"+num).val((totalByQty).toFixed(2)); 
            //alert(total+"--"+totalByQty);  
        }
    });   
    //*************END: LANDING COST CALUCLATION********************************
    

  
    //*************START:  SERVICE TAX CALUCLATION******************************
 
    //*************END:  SERVICE TAX CALUCLATION******************************
    
    function findTotOfLandingAmt() {
        var arr = document.getElementsByName('totLandingCost');
        var tot = 0;
        for (var i = 0; i < arr.length; i++) {
            if (parseFloat(arr[i].value))
                tot += parseFloat(arr[i].value);
        }
        return tot;
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
    
     $('#cstTable').on('change', 'tr td select', function () {

        var field = $(this).attr("class");
        //alert(field);
        if (field.indexOf("cstType") >= 0) {
            var nums = parseInt(field.match(/\d+/g));            
            //var rcdQty = $("#" + field).val();
            //var rate = $(".rate" + num).val();
            //var IgstSel = $(".igstD" + nums).val();
            var cst_type = $(".cstType"+nums).val();
            //alert(nums+"---"+cst_type);
            if(cst_type === 'Local'){                
                $(".insurance"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);                
                $(".customDuty"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);               
                $(".cleaningCharges"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);                
                $(".inlandFreight"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);
                
                $(".discount"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".packetForwardCharges"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);               
                $(".exciseDuty"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".salesTax"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);
            }else if(cst_type === 'Forgin'){
                $(".discount"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);                
                $(".packetForwardCharges"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);               
                $(".exciseDuty"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);                
                $(".salesTax"+nums).val('0').css({"background-color": "#9999991f"}).prop('disabled', true);
                
                $(".insurance"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".customDuty"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);               
                $(".cleaningCharges"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".inlandFreight"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);
            }else{
                $(".insurance"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".customDuty"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);               
                $(".cleaningCharges"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".inlandFreight"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);
                
                $(".discount"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".packetForwardCharges"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);               
                $(".exciseDuty"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);                
                $(".salesTax"+nums).val('0').css({"background-color": "white"}).prop('disabled', false);
            }
        }
    });
    
    $("#cancelCSTEntry").click(function () {
        close_message();
        afterVenGlogal = [];
        $("#mapVenH").show();
        $("#indeviewH").show();
        $("#FileNumber").selectpicker("refresh");
        
        $("#vendorListH").hide();
        $("#indeviewH").hide();
        $("#cstEntryH").hide();
        $("#ItemBasedCstPage").hide();
    });
    
    $("#saveCSTEntry").click(function () {
        //alert(vendorIdGlobal);
        saveCstEnItems(vendorIdGlobal);
    });
    
    //******************************************************************
    //START: SAVE CST PREPARATION ENTRY  ITEMS
    //******************************************************************
    
    function saveCstEnItems(vendor_ID){
         //alert("fileno ___"+fileNo);
          var val = [];
          var myData = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val(); 
            var obj = { 
                    vendorID: vendor_ID,
                    fileNumber: fileNo,
                    categoryId: categortIdGlobal,
                    cstType: $(".cstType"+val[i]).val(),
                    itemID: $(".itemID"+val[i]).val(),
                    itemqty: $(".itemqty"+val[i]).val(),
                    units: $(".units"+val[i]).val(),
                    basicRate: $(".basicRate"+val[i]).val(),                    
                    price: $(".price"+val[i]).val(),
                    
                    discount: $(".discount"+val[i]).val(),
                    subtotal: $(".subtotal"+val[i]).val(),
                    packetForwardCharges: $(".packetForwardCharges"+val[i]).val(),
                    exciseDuty: $(".exciseDuty"+val[i]).val(),
                    salesTax: $(".salesTax"+val[i]).val(),
                    
                    insurance: $(".insurance"+val[i]).val(),
                    customDuty: $(".customDuty"+val[i]).val(),
                    cleaningCharges: $(".cleaningCharges"+val[i]).val(),
                    inlandFreight: $(".inlandFreight"+val[i]).val(),
                    
                    freight: $(".freight"+val[i]).val(),
                    installation: $(".installation"+val[i]).val(),
                    serviceTaxPercent: $(".serviceTaxPercent"+val[i]).val(),
                    serviceTax: $(".serviceTax"+val[i]).val(),
                    trainingcha: $(".trainingcha"+val[i]).val(),
                    inspection: $(".inspection"+val[i]).val(),
                    testingAmount: $(".testingAmount"+val[i]).val(),
                    gstonitit: $(".gstonitit"+val[i]).val(),
                    anyother: $(".anyother"+val[i]).val(),
                    octroitax: $(".octroitax"+val[i]).val(),
                    sample: $(".sample"+val[i]).val(),
                    intrest: $(".intrest"+val[i]).val(),
                    deliveryPeriod: $(".deliveryPeriod"+val[i]).val(),
                    payment: $(".payment"+val[i]).val(),
                    validityDays: $(".validityDays"+val[i]).val(),
                    validityDate: $(".validityDate"+val[i]).val(),
                    landingCost: $(".landingCost"+val[i]).val(),
                    totalAmount: $(".totalAmount"+val[i]).val(),
                    totalLandingCost: $(".totalLandingCost"+val[i]).val(),
                    hsncode: $(".hsncode"+val[i]).val(),
                    mrprice: $(".mrprice"+val[i]).val(),
                    remarks: $(".remarks"+val[i]).val()
            };
            myData.push(obj);
            
        });
                       
            $.ajax({
            dataType: "json",
            url: "savecstdtls.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(myData),
            type: 'POST',
            success: function (data) {  
                //alert(data);
                $("#successDupItem").empty();
                $("#errorDupItem").empty();
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success !</strong> Your data items has been Successfully saved. ");
                    $(window).scrollTop(0);                    
                }else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus);               
            }, beforeSend: function () {
                return confirm("Are you sure you want to submit ?");
            }
        });
       }
       
       
    //******************************************************************
    //END: SAVE CST PREPARATION ENTRY ITEMS
    //****************************************************************** 
   
    //******************************************************************
    //START: ITEM BASED CST DATA
    //****************************************************************** 
    $("#ItemBsedData").on('click', function(){
        
        $("#ItemBasedCstPage").show();
        $("#VendorBasedCstPage").hide();
        $("#vendorListH").hide();
        
        $("#cstEntryH").hide();
        
        $("#venidshow").hide();
        $("#itemhidshow").show();
    });
    
    $("#VendorCSTt").on('click', function(){
        
        $("#ItemBasedCstPage").hide();
        $("#vendorListH").show();
        
        $("#cstEntryH").hide();
        
        $("#venidshow").show();
        $("#itemhidshow").hide();
    });
    
     $('input.typeahead').typeahead({
	    source:  function (query, process) {
        return $.get('getItemOnlyCode.htm', { query: query }, function (data) {
        		//alert(data);
        		data = $.parseJSON(data);
	            return process(data);
	        });
	    }
	}); 
        
     $('input.typeaheadven').typeahead({
	    source:  function (query, process) {
        return $.get('getVendorOnlyCodeSer.htm', { query: query }, function (data) {
        		//alert(data);
        		data = $.parseJSON(data);
	            return process(data);
	        });
	    }
	}); 
        
    $("#FetchItemBsData").on('click', function () {
        var itemcode_c = $("#itemcode_bs").val(); 
        $("#ItemBasedCstPage").show();
        $("#VendorBasedCstPage").hide();
        $("#vendorListH").hide();
        if (/[a-zA-Z]/.test(itemcode_c)) {            
            $.ajax({
                url: "getItemDeByItemName.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({item_name: itemcode_c}),
                success: function (itemname) {
                    $("#itemname_c").empty();
                    $("#dec_c").empty();
                    $("#itemID_c").empty();
                    var pubresult = $.parseJSON(itemname);
                    $.each(pubresult, function (k, x) {
                        $("#itemname_c").append(x.itemName);
                        $("#dec_c").append(x.itemCode);
                        $("#itemID_c").append(x.itemID);
                        genrateItemBasedCST($("#FileNumber").val(), x.itemID);
                    });

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at item details by item name: "+textStatus);
                }
            });
        } else {
            $.ajax({
            url: "getItemDeByItemCode.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({itemCode: itemcode_c}),
            success: function (itemname) {                
                $("#itemname_c").empty();
                $("#dec_c").empty();
                $("#itemID_c").empty();
                var pubresult = $.parseJSON(itemname);
                $.each(pubresult, function (k, x) {
                    $("#itemname_c").append(x.itemName);
                    $("#dec_c").append(x.itemCode);
                    $("#itemID_c").append(x.itemID);
                    genrateItemBasedCST($("#FileNumber").val(), x.itemID);
                });
                
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at:  getItemDeByItemCode"+textStatus);
            }
        });
        }
        
    });    
    
    function genrateItemBasedCST(file_no, item_id) {        
        $.ajax({
            url: "getCstReByItemFile.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({fileNo: file_no,item_id :item_id}),
            success: function (itemCatego) {
                //alert(itemCatego);
                var res = $.parseJSON(itemCatego);
                if($.isEmptyObject(res)){
                    $("#stic_cstForItem").empty();
                    $("#stic_cstForItem").append("This number has no item's data. ");                    
                }else{
                
                var slno = 1;
                var ckslno = 1;
                var cstTyp = 1;
                var itemId = 1;
                var basRate = 1;
                var price = 1;
                var disco = 1;
                var pAndF = 1;
                var exicDu = 1;
                var saleTax = 1;
                var frig = 1;
                var install = 1;
                var serTaxPer = 1;
                var serTax = 1;
                var inspec = 1;
                var testAmt = 1;
                var sampl = 1;
                var intrst = 1;
                var delPerio = 1;
                var payme = 1;
                var vslida = 1;
                var valDate = 1;
                var landCost = 1;
                var totAmt = 1;
                var totLandCost = 1;
                var remark = 1;

                var insur = 1;
                var cusDuty = 1;
                var cleCha = 1;
                var inlaFrig = 1;
                var vendorr = 1;
                var insurTD = 1;
                var pri = 1;
                var cstt = 1;
                var iteqtt = 1;
                var uninni = 1;
                var subtii = 1;
                var traichai = 1;
                var gstii = 1;
                var anyoti = 1;
                var octri = 1;
                var hsnci = 1;
                var mrppti = 1;
                var uninoi = 1;
                $("#stic_cstForItem").empty();
                var tempCate;
                var DueDate;
                $.ajax({
                    url: "getPublicTenderDeByFileNo.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    data: ({file_no: fileNo}),
                    success: function (pubtende) {
                        //alert(pubtende);
                        var pubresult = $.parseJSON(pubtende);
                        $.each(pubresult, function (k, x) {
                            DueDate = x.dueDate;
                        });
                    }, error: function (jqXHR, textStatus, errorThrown) {
                        alert(textStatus);
                    }
                });

                $.each(res, function (k, z) {                    
                    $("#stic_cstForItem").append("<tr>" +
                            "<td><input type='checkbox' name='selector[]' class='' id='chck' value='" + (ckslno++) + "' /></td>" +
                            "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                            "<td><select name='' class='painput cstType" + (cstTyp++) + "' id='table_sele1'>" +
                            "<option value='null' selected>select</option>" +
                            "<option value='Local'>Local Exachange</option>" +
                            "<option value='Forgin'>Foreign Exchange</option>" +
                            "</select></td>" +
                            "<td><input type='text' name='' class='painput vendorcodei" + (vendorr++) + "' value='"+z.venObj.vendorCode+"' /></td>" +                            
                            "<td><textarea name='' class='painput vendornamei" + (itemId++) + "' id='table_sele2'>"+z.venObj.vendorName+"</textarea></td>" +                            
                            
                            "<td><input type='text' name='' class='painput itemqtyi" + (iteqtt++) + "' value='"+z.itemqty+"' /></td>" +
                            "<td><input type='text' name='' class='painput unitsiii" + (uninni++) + "' value='"+z.itemObj.unitDTO.unitName+"' />" +
                            "<input type='hidden' name='' class='painput unitsi" + (uninoi++) + "' value='"+z.units+"' /></td>"+
                            "<td><input type='text' name='' class='painput basicRate" + (basRate++) + "' value='"+z.basicRate+"' /></td>" +
                            "<td><select name='' class='painput price" + (price++) + "' id='table_sele1'>" +
                            "<option value='null' selected>Select</option>" +
                            "<option value='Works'>Works</option>" +
                            "<option value='Price'>Price</option>" +
                            "<option value='CityPrice'>City Price</option>" +
                            "<option value='Destination'>Destination Price</option>" +
                            "</select></td>" +
                            "<td><input type='text' name='' class='painput discount" + (disco++) + "' value='"+z.discount+"' /></td>" +
                            "<td><input type='text' name='' class='painput subtotali" + (subtii++) + "' value='"+z.subtotal+"' /></td>" +
                            "<td><input type='text' name='' class='painput packetForwardCharges" + (pAndF++) + "' value='"+z.packetForwardCharges+"' /></td>" +
                            "<td><input type='text' name='' class='painput exciseDuty" + (exicDu++) + "' value='"+z.exciseDuty+"' /></td>" +
                            "<td><input type='text' name='' class='painput salesTax" + (saleTax++) + "' value='"+z.salesTax+"' /></td>" +
                            "<td><input type='text' name='' class='painput insurance" + (insur++) + "' value='"+z.insurance+"' /></td>" +
                            "<td><input type='text' name='' class='painput customDuty" + (cusDuty++) + "' value='"+z.customDuty+"' /></td>" +
                            "<td><input type='text' name='' class='painput cleaningCharges" + (cleCha++) + "' value='"+z.cleaningCharges+"' /></td>" +
                            "<td><input type='text' name='' class='painput inlandFreight" + (inlaFrig++) + "' value='"+z.inlandFreight+"' /></td>" +
                            "<td><input type='text' name='' class='painput freight" + (frig++) + "' value='"+z.freight+"' /></td>" +
                            "<td><input type='text' name='' class='painput installation" + (install++) + "' value='"+z.installation+"' /></td>" +
                            "<td><input type='text' name='' class='painput serviceTaxPercent" + (serTaxPer++) + "' value='"+z.serviceTaxPercent+"' /></td>" +
                            "<td><input type='text' name='' class='painput serviceTax" + (serTax++) + "' value='"+z.serviceTax+"' /></td>" +
                            "<td><input type='text' name='' class='painput trainingchai" + (traichai++) + "' value='"+z.trainingcha+"' /></td>" +
                            "<td><input type='text' name='' class='painput inspection" + (inspec++) + "' value='"+z.inspection+"' /></td>" +
                            "<td><input type='text' name='' class='painput testingAmount" + (testAmt++) + "' value='"+z.testingAmount+"' /></td>" +
                            "<td><input type='text' name='' class='painput sample" + (sampl++) + "' value='"+z.sample+"' /></td>" +
                            
                            "<td><input type='text' name='' class='painput gstonititi" + (gstii++) + "' value='"+z.gstonitit+"' /></td>" +
                            "<td><input type='text' name='' class='painput anyotheri" + (anyoti++) + "' value='"+z.anyother+"' /></td>" +
                            "<td><input type='text' name='' class='painput octroitaxi" + (octri++) + "' value='"+z.octroitax+"' /></td>" +
                            
                            "<td><input type='text' name='' class='painput intrest" + (intrst++) + "' value='"+z.intrest+"' /></td>" +
                            "<td><input type='text' name='' class='painput deliveryPeriod" + (delPerio++) + "' value='"+z.deliveryPeriod+"' /></td>" +
                            "<td><input type='text' name='' class='painput payment" + (payme++) + "' value='"+z.payment+"' /></td>" +
                            "<td><input type='text' name='' class='painput validityDays" + (vslida++) + "' value='" + z.validityDays + "' /></td>" +
                            "<td><input type='text' name='' class='painput validityDate" + (valDate++) + "' value='" + z.validityDate + "' /></td>" +
                            "<td><input type='text' name='' class='painput landingCost" + (landCost++) + "' value='"+z.landingCost+"' /></td>" +
                            "<td><input type='text' name='' class='painput totalAmount" + (totAmt++) + "' value='"+z.totalAmount+"' /></td>" +
                            "<td><input type='text' name='totLandingCost' class='painput totalLandingCost" + (totLandCost++) + "' value='"+z.totalLandingCost+"' /></td>" +
                            
                            "<td><input type='text' name='' class='painput hsncodei" + (hsnci++) + "' value='"+z.hsncode+"' /></td>" +
                            "<td><input type='text' name='' class='painput mrpricei" + (mrppti++) + "' value='"+z.mrprice+"' /></td>" +                            
                            "<td><textarea type='text' name='' class='painput remarks" + (remark++) + "'>"+z.remarks+"</textarea></td>" +
                            "</tr>");
                    $(".price"+(pri++)).val(z.price);   
                    $(".cstType"+(cstt++)).val(z.cstType);
                    $("#stic_categoryItem").empty();
                    $("#stic_categoryItem").append(z.catObj.categoryName);
                });
                $("#stic_cstItemTotalV").append("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>" +
                        "<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>" +
                        "<td></td><td></td><td></td><td></td><td></td><td id='totalClick1'>Total</td><td></td><td></td>" +
                        "<td><input type='text' name=''  id='stic_totLandingCost' /></td><td></td></tr>");

            }   //End if
            }, error: function (jqXHR, textStatus, errorThrown) {
                        alert("Error occured at getCstReByItemFile:"+textStatus);
            }  //End success
        });


    } // End finction item based cst
   
    //******************************************************************
    //END: ITEM BASED CST DATA
    //****************************************************************** 
  
    //******************************************************************
    //START: VENDOR BASED CST DATA
    //****************************************************************** 
    $("#VendorBasedCst").on('click', function () {

        $("#ItemBasedCstPage").hide();
        $("#VendorBasedCstPage").show();
        $("#vendorListH").hide();
        $("#cstEntryH").hide();
        $("#venidshow").show();
        $("#itemhidshow").hide();

        $("#stic_cstForVend").empty();
        $("#stic_cstVenTotal").empty();
        var ven_name = $("#venderName").val();        
        var venCode_venBas;
        if (/[a-zA-Z]/.test(ven_name)) {
            $.ajax({
                url: "getVendorCodeByName.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({vendor_name: ven_name}),
                success: function (vendor) {
                    venCode_venBas = $.parseJSON(vendor);

                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error occured at getVendorCodeByName:");
                }
            });
        } else {

            venCode_venBas = ven_name;

        }
        $.ajax({
            url: "getVendorDetails.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({vendorName: venCode_venBas}),
            success: function (itemname) {
                $("#vendorIdd").empty();
                $("#vendorCodee").empty();
                $("#vendorNamme").empty();
                $("#vendorCitty").empty();
                var pubresult = $.parseJSON(itemname);
                $.each(pubresult, function (k, x) {
                    $("#vendorIdd").append(x.vendorID);
                    $("#vendorIdV").val(x.vendorID);
                    $("#vendorCodee").append(x.vendorCode);
                    $("#vendorNamme").append(x.vendorName);
                    $("#vendorCitty").append(x.vendorCity);
                    generateItemBasedCST($("#FileNumber").val(), x.vendorID);
                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus);
            }
        });

    });
    function generateItemBasedCST(file_no, vendor_id) {        
        $("#stic_cstForVend").empty();
        $("#stic_cstVenTotal").empty();
        $.ajax({
            url: "getCstReByVendorBased.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({fileNo: file_no,vendor_id :vendor_id}),
            success: function (vedorBase) {
                //alert(vedorBase);
                var res = $.parseJSON(vedorBase);
                if($.isEmptyObject(res)){
                    $("#stic_cstForVend").empty();
                    $("#stic_cstForVend").append("This number has no vendor's data. ");                    
                }else{
                var slno = 1;
                var ckslno = 1;
                var cstTyp = 1;
                var itemId = 1;
                var basRate = 1;
                var price = 1;
                var disco = 1;
                var pAndF = 1;
                var exicDu = 1;
                var saleTax = 1;
                var frig = 1;
                var install = 1;
                var serTaxPer = 1;
                var serTax = 1;
                var inspec = 1;
                var testAmt = 1;
                var sampl = 1;
                var intrst = 1;
                var delPerio = 1;
                var payme = 1;
                var vslida = 1;
                var valDate = 1;
                var landCost = 1;
                var totAmt = 1;
                var totLandCost = 1;
                var remark = 1;

                var insur = 1;
                var cusDuty = 1;
                var cleCha = 1;
                var inlaFrig = 1;

                var insurTD = 1;
                var pri = 1;
                var cstt = 1;
                var iteeem = 1;
                var hssn = 1;
                var itemqyy = 1;
                var unnnd = 1;
                var suttv = 1;
                var trinvv = 1;
                var ititi = 1;
                var anyoo = 1;
                var mrpp = 1;
                var cotrr = 1;
                var unitss = 1;
                var itemiddd = 1;
                var DueDate;
                $.ajax({
                    url: "getPublicTenderDeByFileNo.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    data: ({file_no: fileNo}),
                    success: function (pubtende) {
                        //alert(pubtende);
                        var pubresult = $.parseJSON(pubtende);
                        $.each(pubresult, function (k, x) {
                            DueDate = x.dueDate;
                        });
                    }, error: function (jqXHR, textStatus, errorThrown) {
                        alert(textStatus);
                    }
                });

                $.each(res, function (k, z) {  
                    //alert(z.venObj.vendorName);
                    $("#stic_cstForVend").append("<tr>" +
                            "<td><input type='checkbox' name='selector[]' class='' id='chckVendorBased' value='" + (ckslno++) + "' /></td>" +
                            "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                            "<td><select name='' class='painput cstTypev" + (cstTyp++) + "' id='table_sele1'>" +
                            "<option value='null' selected>select</option>" +
                            "<option value='Local'>Local Exachange</option>" +
                            "<option value='Forgin'>Foreign Exchange</option>" +
                            "</select></td>" +
                            "<td><input type='text' name='' class='painput itemcodev" + (iteeem++) + "' value='"+z.itemObj.itemCode+"' />" + 
                            "<input type='hidden' name='' class='painput itemidv" + (itemiddd++) + "' value='"+z.itemID+"' /></td>"+
                            "<td><textarea name='' class='painput itemID" + (itemId++) + "' id='table_sele2'>"+z.itemObj.itemName+"</textarea></td>" +
                            "<td><input type='text' name='' class='painput itemqtyv" + (itemqyy++) + "' value='"+z.itemqty+"' /></td>" +
                            "<td><input type='text' name='' class='painput unitsvhide" + (unnnd++) + "' value='"+z.itemObj.unitDTO.unitName+"' />" +
                            "<input type='hidden' name='' class='painput unitsv" + (unitss++) + "' value='"+z.units+"' /></td>"+
                            "<td><input type='text' name='' class='painput basicRatev" + (basRate++) + "' value='"+z.basicRate+"' /></td>" +
                            "<td><select name='' class='painput pricev" + (price++) + "' id='table_sele1'>" +
                            "<option value='null' selected>Select</option>" +
                            "<option value='Works'>Works</option>" +
                            "<option value='Price'>Price</option>" +
                            "<option value='CityPrice'>City Price</option>" +
                            "<option value='Destination'>Destination Price</option>" +
                            "</select></td>" +
                            "<td><input type='text' name='' class='painput discountv" + (disco++) + "' value='"+z.discount+"' /></td>" +
                            "<td><input type='text' name='' class='painput subtotalv" + (suttv++) + "' value='"+z.subtotal+"' /></td>" +
                            "<td><input type='text' name='' class='painput packetForwardChargesv" + (pAndF++) + "' value='"+z.packetForwardCharges+"' /></td>" +
                            "<td><input type='text' name='' class='painput exciseDutyv" + (exicDu++) + "' value='"+z.exciseDuty+"' /></td>" +
                            "<td><input type='text' name='' class='painput salesTaxv" + (saleTax++) + "' value='"+z.salesTax+"' /></td>" +
                            "<td><input type='text' name='' class='painput insurancev" + (insur++) + "' value='"+z.insurance+"' /></td>" +
                            "<td><input type='text' name='' class='painput customDutyv" + (cusDuty++) + "' value='"+z.customDuty+"' /></td>" +
                            "<td><input type='text' name='' class='painput cleaningChargesv" + (cleCha++) + "' value='"+z.cleaningCharges+"' /></td>" +
                            "<td><input type='text' name='' class='painput inlandFreightv" + (inlaFrig++) + "' value='"+z.inlandFreight+"' /></td>" +
                            "<td><input type='text' name='' class='painput freightv" + (frig++) + "' value='"+z.freight+"' /></td>" +
                            "<td><input type='text' name='' class='painput installationv" + (install++) + "' value='"+z.installation+"' /></td>" +
                            "<td><input type='text' name='' class='painput serviceTaxPercentv" + (serTaxPer++) + "' value='"+z.serviceTaxPercent+"' /></td>" +
                            "<td><input type='text' name='' class='painput serviceTaxv" + (serTax++) + "' value='"+z.serviceTax+"' /></td>" +
                            "<td><input type='text' name='' class='painput trainingchav" + (trinvv++) + "' value='"+z.trainingcha+"' /></td>" +
                            "<td><input type='text' name='' class='painput inspectionv" + (inspec++) + "' value='"+z.inspection+"' /></td>" +
                            "<td><input type='text' name='' class='painput testingAmountv" + (testAmt++) + "' value='"+z.testingAmount+"' /></td>" +
                            "<td><input type='text' name='' class='painput gstititvv" + (ititi++) + "' value='"+z.gstonitit+"' /></td>" +
                            "<td><input type='text' name='' class='painput anyotherv" + (anyoo++) + "' value='"+z.anyother+"' /></td>" +
                            "<td><input type='text' name='' class='painput octroiv" + (cotrr++) + "' value='"+z.octroitax+"' /></td>" +
                            "<td><input type='text' name='' class='painput samplev" + (sampl++) + "' value='"+z.sample+"' /></td>" +
                            "<td><input type='text' name='' class='painput intresvt" + (intrst++) + "' value='"+z.intrest+"' /></td>" +
                            "<td><input type='text' name='' class='painput deliveryPeriodv" + (delPerio++) + "' value='"+z.deliveryPeriod+"' /></td>" +
                            "<td><input type='text' name='' class='painput paymentv" + (payme++) + "' value='"+z.payment+"' /></td>" +
                            "<td><input type='text' name='' class='painput validityDaysv" + (vslida++) + "' value='" + z.validityDays + "' /></td>" +
                            "<td><input type='text' name='' class='painput validityDatev" + (valDate++) + "' value='" + z.validityDate + "' /></td>" +
                            "<td><input type='text' name='' class='painput landingCostv" + (landCost++) + "' value='"+z.landingCost+"' /></td>" +
                            "<td><input type='text' name='' class='painput totalAmountv" + (totAmt++) + "' value='"+z.totalAmount+"' /></td>" +
                            "<td><input type='text' name='totLandingCost' class='painput totalLandingCostv" + (totLandCost++) + "' value='"+z.totalLandingCost+"' /></td>" +
                            "<td><input type='text' name='' class='painput hsncodev" + (hssn++) + "' value='"+z.hsncode+"' /></td>" +
                            "<td><input type='text' name='' class='painput mrpricev" + (mrpp++) + "' value='"+z.mrprice+"' /></td>" +
                            "<td><textarea type='text' name='' class='painput remarksv" + (remark++) + "'>"+z.remarks+"</textarea></td>" +
                            "</tr>");
                    $(".pricev"+(pri++)).val(z.price);   
                    $(".cstTypev"+(cstt++)).val(z.cstType);
                    
                    
                });
                $("#stic_cstVenTotal").append("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>" +
                        "<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>" +
                        "<td></td><td></td><td></td><td></td><td></td><td id='totalClick1'>Total</td><td></td><td></td>" +
                        "<td><input type='text' name=''  id='stic_totLandingCost' /></td><td></td></tr>");

                }

              
            }
        });


    } // End finction item based cst

    //******************************************************************
    //END: VENDOR BASED CST DATA
    //****************************************************************** 
    
    $("#cancelCSTVenBased").click(function () {
        close_message();        
        $("#mapVenH").show();
        $("#indeviewH").show();        
        $("#vendorListH").hide();        
        $("#cstEntryH").hide();
        $("#ItemBasedCstPage").hide();
        $("#VendorBasedCstPage").hide();
    });
    
    $("#cancelCSTItemBased").click(function () {
        close_message();        
        $("#mapVenH").show();
        $("#indeviewH").show();        
        $("#vendorListH").hide();        
        $("#cstEntryH").hide();
        $("#ItemBasedCstPage").hide();
        $("#VendorBasedCstPage").hide();
    });
    
    $("#saveCSTVenBasedPdf").on('click', function (){
                    //alert(this.value);
                    window.open('venBasedCstPdf.htm?eindi='+2);
                });  
    
});  //End document
    
  
    


function treatAsUTC(date) {
    var result = new Date(date);
    result.setMinutes(result.getMinutes() - result.getTimezoneOffset());
    return result;
}

function daysBetween(startDate, endDate) {
    var millisecondsPerDay = 24 * 60 * 60 * 1000;
    return (treatAsUTC(endDate) - treatAsUTC(startDate)) / millisecondsPerDay;
}

function currentDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    var today = mm + '/' + dd + '/' + yyyy;
    return today;
}

function validatyDays(dueDate){
    return daysBetween(dueDate, currentDate());
}