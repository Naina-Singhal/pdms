/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function () {
    
    
    var $fo = function loader() {
        
        $.loadingBlockShow({
            imgPath: 'appjs/loader/img/default.svg',
            text: ' Loading ...',
            style: {
                position: 'fixed',
                width: '100%',
                height: '100%',
                background: 'rgba(0, 0, 0, .8)',
                left: 0,
                top: 0,
                zIndex: 10000
            }
        });

    };
    //$(document).ajaxStart(function () {
                        //ajax request went so show the loading image
                        
                         //$fo();
                     //})
                   ///.ajaxStop(function () {
                       //got response so hide the loading image
                       // setTimeout($.loadingBlockHide, 5000);
                    //});
    
    $("#navigation li a").removeClass("active");
      $("#userpermenu").css("display","block");
      $("#useperli").addClass("active");
      
      
    //hide the error, success messages when load the page
        $(".display_msg_error_Ma").hide();
        $(".display_msg_success_Ma").hide(); 
      
    //$("#myModal").modal("show");
       
    //***********************************************
    //Getting User profile details
    //***********************************************
    $.ajax({
        url: "getUserProfileDa.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);			
                    $.each(result, function(k, v8) {
                    //alert(v8.employeeProfileID+"--"+v8.icNumber);			
			$("#empSelect").append("<option value='"+v8.icNumber+"'>("+v8.icNumber+")"+v8.firstName+"</option>");
                        
                    });	
										
        }, complete: function (data) {
        }, beforeSend: function () {
           
        }
	});
    //***********************************************
    //Getting User Permission Pages details
    //***********************************************
    
 
    
    var ICNO = "null";
    $('#empSelect').on('change', function () {
        //alert(this.value);     
        $(".display_msg_error_Ma").hide();
        $(".display_msg_success_Ma").hide(); 
        ICNO = this.value;
        $("#display_account").empty();
        $("#display_master").empty();
        $("#display_receipt").empty();
        $("#display_purchase").empty();
        $("#display_indent").empty();
        $("#display_tender").empty();
        $("#display_vendor").empty();
        $("#display_group").empty();
        $("#display_upload").empty();
        $("#display_dispatch").empty();
        $("#errorEmployeeIdt").empty();
        if (this.value === "null" || this.value === "") {
            
            $("#errorEmployeeIdt").append("Please  select  employee  id.");
        } else {
            
            loadAllAjax(ICNO);
            
        }
    });//End select icno    
    
    
    function loadAllAjax(ICNO){
        $("#display_account").empty();
        $("#display_master").empty();
        $("#display_receipt").empty();
        $("#display_purchase").empty();
        $("#display_indent").empty();
        $("#display_tender").empty();
        $("#display_vendor").empty();
        $("#display_group").empty();
        $("#display_upload").empty();
        $("#display_dispatch").empty();
    $.ajax({
        url: "getPagePermiData.htm",
        type: "POST",
	cache: false,	
        data: ({uid: "dummy"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);
                var countAc = 1;
                var count = 1;
                var countAcname = 1;
                var countAcID = 1;
                var countMaNa = 1;
                var countMaId = 1;
                var countRe = 1;
                var countReNa = 1;
                var countReId = 1;
                var countPu = 1;
                var countPuNa = 1;
                var countPuId = 1;  
                var countIn = 1;
                var countInNa = 1;
                var countInId = 1;  
                var countTe = 1;
                var countTeNa = 1;
                var countTeId = 1; 
                var countVe = 1;
                var countVeNa = 1;
                var countVeId = 1; 
                var countGu = 1;
                var countGuNa = 1;
                var countGuId = 1;
                var countUp = 1;
                var countUpNa = 1;
                var countUpId = 1;
                var countDe = 1;
                var countDeNa = 1;
                var countDeId = 1;
                    $.each(result, function(k, v8) {
                    //alert(v8.page_id+"--"+v8.page_group);
                    if(v8.page_group === "group"){
                        $.ajax({
                            url: "getUserPermiActive.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({icno: ICNO, page_ID: v8.page_id}),
                            success: function (act) { // start of inner success                                
                                var active = parseInt(act);
                                        if(active === 1){                                            
                                        $("#display_group").append("<div class='form-group div-wid-7'>" +
                                            "<input type='checkbox' name='g"+countGuNa++ +"' id='g"+countGuId++ +"' value='1'  checked/>" +
                                            "<label for='username'>" + v8.page_name + " </label></div>");
                                        }else{                                           
                                            $("#display_group").append("<div class='form-group div-wid-7'>" +
                                            "<input type='checkbox' name='g"+countGuNa++ +"' id='g"+countGuId++ +"' value='0' />" +
                                            "<label for='username'>" + v8.page_name + " </label></div>");
                                        } 
                            }, error: function (jqXHR, textStatus, errorThrown) {

                            }, beforeSend: function () {

                            }, complete: function (data) {

                            }// end of inner success 
                        });
                            if (countGu % 3 === 0) {
                                $("#display_group").append("</br>");
                            }
                          countGu++;
                        }
                        if (v8.page_group === "master") {
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (actM) { 
                                    var active1 = parseInt(actM);
                                    if (active1 === 1) {
                                        $("#display_master").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='m"+countMaNa++ +"' id='m"+countMaId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_master").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='m"+countMaNa++ +"' id='m"+countMaId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countAc % 3 === 0) {
                                $("#display_master").append("</br>");
                            }
                            countAc++;
                        }
                      if(v8.page_group === "account"){
                        $.ajax({
                            url: "getUserPermiActive.htm",
                            type: "POST",
                            cache: false,
                            async: false,
                            data: ({icno: ICNO, page_ID: v8.page_id}),
                            success: function (act) { // start of inner success                                
                                var active = parseInt(act);
                                        if(active === 1){                                            
                                        $("#display_account").append("<div class='form-group div-wid-7'>" +
                                            "<input type='checkbox' name='d"+countAcname++ +"' id='d"+countAcID++ +"' value='1'  checked/>" +
                                            "<label for='username'>" + v8.page_name + " </label></div>");
                                        }else{                                           
                                            $("#display_account").append("<div class='form-group div-wid-7'>" +
                                            "<input type='checkbox' name='d"+countAcname++ +"' id='d"+countAcID++ +"' value='0' />" +
                                            "<label for='username'>" + v8.page_name + " </label></div>");
                                        } 
                            }, error: function (jqXHR, textStatus, errorThrown) {

                            }, beforeSend: function () {

                            }, complete: function (data) {

                            }// end of inner success 
                        });
                            if (count % 3 === 0) {
                                $("#display_account").append("</br>");
                            }
                          count++;
                        }                        
                        if (v8.page_group === "receipt") {
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (rece) { 
                                    var rece1 = parseInt(rece);

                                    //alert(v8.page_id+"--" + active);
                                    if (rece1 === 1) {
                                        $("#display_receipt").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='c"+countReNa++ +"' id='c"+countReId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_receipt").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='c"+countReNa++ +"' id='c"+countReId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }

                                },complete: function (data) {
                                    
                                }
                            });

                            if (countRe % 3 === 0) {
                                $("#display_receipt").append("</br>");
                            }

                            countRe++;
                        }
                        if (v8.page_group === "purchase") {
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (rece) { 
                                    var rece1 = parseInt(rece);
                                    if (rece1 === 1) {
                                        $("#display_purchase").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='b"+countPuNa++ +"' id='b"+countPuId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_purchase").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='b"+countPuNa++ +"' id='b"+countPuId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countPu % 3 === 0) {
                                $("#display_purchase").append("</br>");
                            }
                            countPu++;
                        }
                        if (v8.page_group === "indent") {
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (rece) { 
                                    var rece1 = parseInt(rece);
                                    if (rece1 === 1) {
                                        $("#display_indent").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='e"+countInNa++ +"' id='e"+countInId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_indent").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='e"+countInNa++ +"' id='e"+countInId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countIn % 3 === 0) {
                                $("#display_indent").append("</br>");
                            }
                            countIn++;
                        }
                        if (v8.page_group === "tender") {
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (rece) { 
                                    var rece1 = parseInt(rece);
                                    if (rece1 === 1) {
                                        $("#display_tender").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='t"+countTeNa++ +"' id='t"+countTeId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_tender").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='t"+countTeNa++ +"' id='t"+countTeId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countTe % 3 === 0) {
                                $("#display_tender").append("</br>");
                            }
                            countTe++;
                        }
                        if (v8.page_group === "vendor") {
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (rece) { 
                                    var rece1 = parseInt(rece);
                                    if (rece1 === 1) {
                                        $("#display_vendor").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='v"+countVeNa++ +"' id='v"+countVeId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_vendor").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='v"+countVeNa++ +"' id='v"+countVeId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countVe % 3 === 0) {
                                $("#display_vendor").append("</br>");
                            }
                            countVe++;
                        }
                        if (v8.page_group === "upload") {
                            //alert(v8.page_group+"--"+v8.page_id);
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (rece) { 
                                    var rece1 = parseInt(rece);
                                    if (rece1 === 1) {
                                        $("#display_upload").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='u"+countUpNa++ +"' id='u"+countUpId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_upload").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='u"+countUpNa++ +"' id='u"+countUpId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countUp % 3 === 0) {
                                $("#display_upload").append("</br>");
                            }
                            countUp++;
                        }
                        if (v8.page_group === "dispatch") {
                            //alert(v8.page_group+"--"+v8.page_id);
                            $.ajax({
                                url: "getUserPermiActive.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({icno: ICNO, page_ID: v8.page_id}),
                                success: function (des) { 
                                    var despat = parseInt(des);
                                    if (despat === 1) {
                                        $("#display_dispatch").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='p"+countDeNa++ +"' id='p"+countDeId++ +"' value='1'  checked/>" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    } else {
                                        $("#display_dispatch").append("<div class='form-group div-wid-7'>" +
                                                "<input type='checkbox' name='p"+countDeNa++ +"' id='p"+countDeId++ +"' value='0' />" +
                                                "<label for='username'>" + v8.page_name + " </label></div>");
                                    }
                                },complete: function (data) {
                                    
                                }
                            });
                            if (countDe % 3 === 0) {
                                $("#display_dispatch").append("</br>");
                            }
                            countDe++;
                        }
                    });	
                    
                    var g11 = $("#g1").is(':checked') ? 1 : 0;
                    en_dis_master(g11);
                    $('#g1').on('change', function () {
                        var g = $("#g1").is(':checked') ? 1 : 0;
                        en_dis_master(g);
                    });
                    var g88 = $("#g8").is(':checked') ? 1 : 0;
                    en_dis_account(g88);
                    $('#g8').on('change', function () {
                        var g8 = $("#g8").is(':checked') ? 1 : 0;
                        en_dis_account(g8);
                    });
                    var g77 = $("#g7").is(':checked') ? 1 : 0;
                    en_dis_receipt(g77);
                    $('#g7').on('change', function () {
                        var g7 = $("#g7").is(':checked') ? 1 : 0;
                        en_dis_receipt(g7);
                    });
                    var g22 = $("#g2").is(':checked') ? 1 : 0;
                    en_dis_indent(g22);
                    $('#g2').on('change', function () {
                        var g2 = $("#g2").is(':checked') ? 1 : 0;
                        en_dis_indent(g2);
                    });
                    var g33 = $("#g3").is(':checked') ? 1 : 0;
                    en_dis_vendor(g33);
                    $('#g3').on('change', function () {
                        var g3 = $("#g3").is(':checked') ? 1 : 0;
                        en_dis_vendor(g3);
                    });
                    var g55 = $("#g5").is(':checked') ? 1 : 0;
                    en_dis_tender(g55);
                    $('#g5').on('change', function () {
                        var g5 = $("#g5").is(':checked') ? 1 : 0;
                        en_dis_tender(g5);
                    });
                    var g66 = $("#g6").is(':checked') ? 1 : 0;
                    en_dis_purchase(g66);
                    $('#g6').on('change', function () {
                        var g6 = $("#g6").is(':checked') ? 1 : 0;
                        en_dis_purchase(g6);
                    });
                    var g99 = $("#g9").is(':checked') ? 1 : 0;                    
                    en_dis_upload(g99);
                    $('#g9').on('change', function () {                        
                        var g9 = $("#g9").is(':checked') ? 1 : 0;
                        en_dis_upload(g9);
                    });
                    
                    var des = $("#g10").is(':checked') ? 1 : 0;                    
                    en_dis_despatch(des);
                    $('#g10').on('change', function () {                        
                        var g10 = $("#g10").is(':checked') ? 1 : 0;
                        en_dis_despatch(g10);
                    });
										
		}, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + textStatus);
                }, beforeSend: function () {
                   $fo();
                 
                }, complete: function (data) {
                    //alert("loading"+ICNO);
                    setTimeout($.loadingBlockHide, 3000);                    
                }
	}); 
        
    }    
   
    
    
    $("#savePermissions").click(function(){
        $("#errorEmployeeIdt").empty();
        if(ICNO === "null" || ICNO === ""){            
            $("#errorEmployeeIdt").append("Please  select  employee  id.");
        }else{
        var d1 = $("#d1").is(':checked') ? 1 : 0;
        var d2 = $("#d2").is(':checked') ? 1 : 0;
        var d3 = $("#d3").is(':checked') ? 1 : 0;
        var d4 = $("#d4").is(':checked') ? 1 : 0;
        var d5 = $("#d5").is(':checked') ? 1 : 0;
        var d6 = $("#d6").is(':checked') ? 1 : 0;
        var d7 = $("#d7").is(':checked') ? 1 : 0;
        var d8 = $("#d8").is(':checked') ? 1 : 0;
        var d9 = $("#d9").is(':checked') ? 1 : 0;
        var d10 = $("#d10").is(':checked') ? 1 : 0;
        var d11 = $("#d11").is(':checked') ? 1 : 0;
        var d12 = $("#d12").is(':checked') ? 1 : 0;
        var d13 = $("#d13").is(':checked') ? 1 : 0; 
        var d14 = $("#d14").is(':checked') ? 1 : 0;
        var d15 = $("#d15").is(':checked') ? 1 : 0;
        var d16 = $("#d16").is(':checked') ? 1 : 0;
        var d17 = $("#d17").is(':checked') ? 1 : 0;
        var d18 = $("#d18").is(':checked') ? 1 : 0;
        var d19 = $("#d19").is(':checked') ? 1 : 0;
        var d20 = $("#d20").is(':checked') ? 1 : 0;
        var d21 = $("#d21").is(':checked') ? 1 : 0;
        var d22 = $("#d22").is(':checked') ? 1 : 0;
        var d23 = $("#d23").is(':checked') ? 1 : 0;
        var d24 = $("#d24").is(':checked') ? 1 : 0;
        var d25 = $("#d25").is(':checked') ? 1 : 0;
        var d26 = $("#d26").is(':checked') ? 1 : 0;
        var d27 = $("#d27").is(':checked') ? 1 : 0;
        
        var m1 = $("#m1").is(':checked') ? 1 : 0;
        var m2 = $("#m2").is(':checked') ? 1 : 0;
        var m3 = $("#m3").is(':checked') ? 1 : 0;
        var m4 = $("#m4").is(':checked') ? 1 : 0;
        var m5 = $("#m5").is(':checked') ? 1 : 0;
        var m6 = $("#m6").is(':checked') ? 1 : 0;
        var m7 = $("#m7").is(':checked') ? 1 : 0;
        var m8 = $("#m8").is(':checked') ? 1 : 0;
        var m9 = $("#m9").is(':checked') ? 1 : 0;
        var m10 = $("#m10").is(':checked') ? 1 : 0;
        var m11 = $("#m11").is(':checked') ? 1 : 0;
        var m12 = $("#m12").is(':checked') ? 1 : 0;
        var m13 = $("#m13").is(':checked') ? 1 : 0;
        var m14 = $("#m14").is(':checked') ? 1 : 0;
        var m15 = $("#m15").is(':checked') ? 1 : 0;
        var m16 = $("#m16").is(':checked') ? 1 : 0;
        var m17 = $("#m17").is(':checked') ? 1 : 0;
        var m18 = $("#m18").is(':checked') ? 1 : 0;
        var m19 = $("#m19").is(':checked') ? 1 : 0;
        var m20 = $("#m20").is(':checked') ? 1 : 0;
        var m21 = $("#m21").is(':checked') ? 1 : 0;
        var m22 = $("#m22").is(':checked') ? 1 : 0;
        var m23 = $("#m23").is(':checked') ? 1 : 0;
        var m24 = $("#m24").is(':checked') ? 1 : 0;
        var m25 = $("#m25").is(':checked') ? 1 : 0;
        var m26 = $("#m26").is(':checked') ? 1 : 0;
        var m27 = $("#m27").is(':checked') ? 1 : 0;
        var m28 = $("#m28").is(':checked') ? 1 : 0;
        var m29 = $("#m29").is(':checked') ? 1 : 0;
        var m30 = $("#m30").is(':checked') ? 1 : 0;
        var m31 = $("#m31").is(':checked') ? 1 : 0;
        var m32 = $("#m32").is(':checked') ? 1 : 0;
        //alert(m8);
        var c1 = $("#c1").is(':checked') ? 1 : 0;
        var c2 = $("#c2").is(':checked') ? 1 : 0;
        var c3 = $("#c3").is(':checked') ? 1 : 0;
        var c4 = $("#c4").is(':checked') ? 1 : 0;
        var c5 = $("#c5").is(':checked') ? 1 : 0;
        var c6 = $("#c6").is(':checked') ? 1 : 0;
        var c7 = $("#c7").is(':checked') ? 1 : 0;
        var c8 = $("#c8").is(':checked') ? 1 : 0;
        var c9 = $("#c9").is(':checked') ? 1 : 0;
        var c10 = $("#c10").is(':checked') ? 1 : 0;
        var c11 = $("#c11").is(':checked') ? 1 : 0;
        var c12 = $("#c12").is(':checked') ? 1 : 0; 
        var c13 = $("#c13").is(':checked') ? 1 : 0;  
        var c14 = $("#c14").is(':checked') ? 1 : 0;
        var c15 = $("#c15").is(':checked') ? 1 : 0;
        var c16 = $("#c16").is(':checked') ? 1 : 0;
        var c17 = $("#c17").is(':checked') ? 1 : 0;
        var c18 = $("#c18").is(':checked') ? 1 : 0;
        
        var b1 = $("#b1").is(':checked') ? 1 : 0;
        var b2 = $("#b2").is(':checked') ? 1 : 0;
        var b3 = $("#b3").is(':checked') ? 1 : 0;
        var b4 = $("#b4").is(':checked') ? 1 : 0;
        var b5 = $("#b5").is(':checked') ? 1 : 0;
        var b6 = $("#b6").is(':checked') ? 1 : 0;
        var b7 = $("#b7").is(':checked') ? 1 : 0;
        
        var e1 = $("#e1").is(':checked') ? 1 : 0;
        var e2 = $("#e2").is(':checked') ? 1 : 0;
        var e3 = $("#e3").is(':checked') ? 1 : 0;
        var e4 = $("#e4").is(':checked') ? 1 : 0;
        var e5 = $("#e5").is(':checked') ? 1 : 0;
        var e6 = $("#e6").is(':checked') ? 1 : 0;
        var e7 = $("#e7").is(':checked') ? 1 : 0;
        var e8 = $("#e8").is(':checked') ? 1 : 0;
        var e9 = $("#e9").is(':checked') ? 1 : 0;
        var e10 = $("#e10").is(':checked') ? 1 : 0;
        var e11 = $("#e11").is(':checked') ? 1 : 0;
        var e12 = $("#e12").is(':checked') ? 1 : 0;
        var e13 = $("#e13").is(':checked') ? 1 : 0;
        var e14 = $("#e14").is(':checked') ? 1 : 0;
        
        var t1 = $("#t1").is(':checked') ? 1 : 0;
        var t2 = $("#t2").is(':checked') ? 1 : 0;
        var t3 = $("#t3").is(':checked') ? 1 : 0;
        var t4 = $("#t4").is(':checked') ? 1 : 0;
        var t5 = $("#t5").is(':checked') ? 1 : 0;
        var t6 = $("#t6").is(':checked') ? 1 : 0;
        var t7 = $("#t7").is(':checked') ? 1 : 0;
        var t8 = $("#t8").is(':checked') ? 1 : 0;
        var t9 = $("#t9").is(':checked') ? 1 : 0;
        
        var v1 = $("#v1").is(':checked') ? 1 : 0;
        var v2 = $("#v2").is(':checked') ? 1 : 0;
        var v3 = $("#v3").is(':checked') ? 1 : 0;
        var v4 = $("#v4").is(':checked') ? 1 : 0;
        var v5 = $("#v5").is(':checked') ? 1 : 0;
        var v6 = $("#v6").is(':checked') ? 1 : 0;
        var v7 = $("#v7").is(':checked') ? 1 : 0;
        var v8 = $("#v8").is(':checked') ? 1 : 0;
        
        var g1 = $("#g1").is(':checked') ? 1 : 0;
        var g2 = $("#g2").is(':checked') ? 1 : 0;
        var g3 = $("#g3").is(':checked') ? 1 : 0;
        var g4 = $("#g4").is(':checked') ? 1 : 0;
        var g5 = $("#g5").is(':checked') ? 1 : 0;
        var g6 = $("#g6").is(':checked') ? 1 : 0;
        var g7 = $("#g7").is(':checked') ? 1 : 0;
        var g8 = $("#g8").is(':checked') ? 1 : 0;
        var g9 = $("#g9").is(':checked') ? 1 : 0;
        var g10 = $("#g10").is(':checked') ? 1 : 0;
        
        var u1 = $("#u1").is(':checked') ? 1 : 0;
        var p1 = $("#p1").is(':checked') ? 1 : 0;
        var p2 = $("#p2").is(':checked') ? 1 : 0;
        var p3 = $("#p3").is(':checked') ? 1 : 0;
        var p4 = $("#p4").is(':checked') ? 1 : 0;
       
        var persons = JSON.stringify([{
            "IC_No": ICNO,"d1": d1,"d2": d2,"d3": d3,"d4": d4,"d5": d5,"d6": d6,"d7": d7,"d8": d8,
            "d9": d9,"d10": d10,"d11": d11,"d12": d12,"d13": d13,"d14": d14,"d15": d15,
            "d16": d16,"d17": d17,"d18": d18,"d19": d19,"d20": d20,"d21": d21,"d22": d22,"d23": d23,
            "d24": d24,"d25": d25,"d26": d26,"d27": d27,
            "m1": m1,"m2": m2,"m3": m3,"m4": m4,"m5": m5,"m6": m6,"m7": m7,"m8": m8,
            "m9": m9,"m10": m10,"m11": m11,"m12": m12,"m13": m13,"m14": m14,
            "m15": m15,"m16": m16,"m17": m17,"m18": m18,"m19": m19,"m20": m20,"m21": m21,"m22": m22,
            "m23": m23,"m24": m24,"m25": m25,"m26": m26,"m27": m27,"m28": m28,"m29": m29,"m30": m30,"m31": m31,
            "m32": m32,"c1": c1,"c2": c2,"c3": c3,"c4": c4,"c5": c5,"c6": c6,"c7": c7,"c8": c8,
            "c9": c9,"c10": c10,"c11": c11,"c12": c12,"c13": c13,"c14": c14,
            "c15": c15,"c16": c16,"c17": c17,"c18": c18,
            "b1": b1,"b2": b2,"b3": b3,"b4": b4,"b5": b5,"b6": b6,"b7": b7,
            "e1": e1,"e2": e2,"e3": e3,"e4": e4,"e5": e5,"e6": e6,"e7": e7,
            "e8": e8,"e9": e9,"e10": e10,"e11": e11,"e12": e12,"e13": e13,"e14": e14,
            "t1": t1,"t2": t2,"t3": t3,"t4": t4,"t5": t5,"t6": t6,"t7": t7,"t8": t8,"t9": t9,
            "v1": v1,"v2": v2,"v3": v3,"v4": v4,"v5": v5,"v6": v6,"v7": v7,"v8": v8,
            "g1": g1,"g2": g2,"g3": g3,"g4": g4,"g5": g5,"g6": g6,"g7": g7,"g8": g8,"g9": g9,
            "g10": g10,"u1": u1,"p1": p1,"p2": p2,"p3": p3,"p4": p4
        }]);
        var res;
        //alert(persons);
        $.ajax({
        dataType : "json",    
        url: "./savePermissions.htm",
        headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
        data: persons,
        type: "POST",
            success: function(htmlH){                 
                  res = parseInt(htmlH);
                  						
		}, error: function (jqXHR, textStatus, errorThrown) {
                    $("#errorDupItem").empty();
                    $(".display_msg_success_Ma").hide();
                    $(".display_msg_error_Ma").show();
                    $("#errorDupItem").append("Your action unsuccessfull. "+textStatus);
                }, beforeSend: function () {
                   $fo();
                 
                }, complete: function (data) {

                    loadAllAjax(ICNO);
                    setTimeout($.loadingBlockHide, 1000);
                    $("#successDupItem").empty();
                    $("#errorDupItem").empty();
                    if (res === 1) {
                        $(".display_msg_error_Ma").hide();
                        $(".display_msg_success_Ma").show();                                              
                        $("#successDupItem").append("Successfully permissions applied for new employee.");
                    } else if (res === 3) {
                        $(".display_msg_error_Ma").hide();
                        $(".display_msg_success_Ma").show();                        
                        $("#successDupItem").append("Successfully updated permissions for employee.");
                    } else if (res === 0) {
                        $(".display_msg_success_Ma").hide();
                        $(".display_msg_error_Ma").show();
                        $("#errorDupItem").append("Your action unsuccessfull.");
                    }
                    $(window).scrollTop(0);
                }
	});
    }
    });
   
    function en_dis_master(g) {
        //alert("in fun " + g);
        for (var m = 1; m <= 17; m++) {
            if (g === 1) {
                document.getElementById("m" + m).disabled = false;
            } else {
                document.getElementById("m" + m).disabled = true;
            }
        }
    } 
    function en_dis_account(g8) {        
        for (var d = 1; d <= 12; d++) {
            if (g8 === 1) {
                document.getElementById("d" + d).disabled = false;
            } else {
                document.getElementById("d" + d).disabled = true;
            }
        }
    }
     function en_dis_receipt(g7) {        
        for (var c = 1; c <= 12; c++) {
            if (g7 === 1) {
                document.getElementById("c" + c).disabled = false;
            } else {
                document.getElementById("c" + c).disabled = true;
            }
        }
    }
    function en_dis_indent(g2) {        
        for (var e = 1; e <= 14; e++) {
            if (g2 === 1) {
                document.getElementById("e" + e).disabled = false;
            } else {
                document.getElementById("e" + e).disabled = true;
            }
        }
    }
    function en_dis_vendor(g3) {        
        for (var v = 1; v <= 8; v++) {
            if (g3 === 1) {
                document.getElementById("v" + v).disabled = false;
            } else {
                document.getElementById("v" + v).disabled = true;
            }
        }
    }
    function en_dis_tender(g5) {        
        for (var t = 1; t <= 9; t++) {
            if (g5 === 1) {
                document.getElementById("t" + t).disabled = false;
            } else {
                document.getElementById("t" + t).disabled = true;
            }
        }
    }
    function en_dis_purchase(g6) {        
        for (var b = 1; b <= 2; b++) {
            if (g6 === 1) {
                document.getElementById("b" + b).disabled = false;
            } else {
                document.getElementById("b" + b).disabled = true;
            }
        }
    }
    function en_dis_upload(g9) {        
        for (var u = 1; u <= 1; u++) {
            if (g9 === 1) {
                document.getElementById("u" + u).disabled = false;
            } else {
                document.getElementById("u" + u).disabled = true;
            }
        }
    }
    function en_dis_despatch(g10) {        
        for (var p = 1; p <= 1; p++) {
            if (g10 === 1) {
                document.getElementById("p" + p).disabled = false;
            } else {
                document.getElementById("p" + p).disabled = true;
            }
        }
    }
    
});