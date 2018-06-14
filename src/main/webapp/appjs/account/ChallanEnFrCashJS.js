/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openChallanRe() {

    window.open("./challanEnFrCashRe.htm", "_parent");
}
$(document).ready(function () {

    challan_incre();

    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#challaenfrcs ").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    
    //******************************************************************
    //START: CHALLAN YEAR TEMPLATE
    //******************************************************************            
    $("#challanYear").dblclick(function () {
        $(".modal-body").empty();        
        var text_id;
        $("#cstModal").modal("show");

        var no = 1;
        var year = 2000;
        var yearTo;
        show_year(year);

        function show_year(yearStart) {
            for (var i = 1; i <= 50; i++) {
                yearTo = yearStart++;
                $(".modal-body").append("<a class='ineer-model' id='" + (no++) + "'>" + (yearTo) + "-" + (yearTo + 1) + "</a>");

                if (i % 5 === 0) {
                    $(".modal-body").append("<br/><br/>");
                }
            }
        }

        var div = $(".ineer-model");
        div.click(function ()
        {
            text_id = this.id;
            var tex = $("#" + text_id).text();
            $("#challanYear").text('');
            document.getElementById("challanYear").value = tex;
            $('#cstModal').modal('toggle');
            var WhereToMove = $("#challanYear").position().top;
            $("html,body").animate({scrollTop: WhereToMove}, 1000);
        });
    });
    //******************************************************************
    //END: CHALLAN YEAR TEMPLATE
    //******************************************************************              
    
    //******************************************************************
    //Getting Challan NUMBER for increment by one.
    //******************************************************************
    function challan_incre() {
        $.ajax({
            url: "getChalCashNoIncr.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (cha) {                 
                $("#challanNo").val(parseInt(cha));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });
    }
    
    //******************************************************************
    //START:  SUBHEAD FIELDS WORK
    //****************************************************************** 
     var initialText = $('.editable').val();
    $('.editOption').val(initialText);

    $('#subHead').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editable').text();
        $('.editOption').val('');
        if (selected == "editable") {
            $('.editOption').show();

            $('.editOption').keyup(function () {
                var editText = $('.editOption').val();
                $('.editable').val(editText);
                $('.editable').html(editText);
            });

        } else {
            $('.editOption').hide();
        }
    });
    
    //******************************************************************
    //END: SUBHEAD FIELDS WORK
    //****************************************************************** 
    
     //******************************************************************
    //START: HEAD OF ACCOUNT, SHORT CODE, HEAD DISPLY
    //****************************************************************** 
    $.ajax({
        url: "getHoaNamesDetails.htm",
        type: "POST",
        cache: false,
        success: function (hoade) {
            //alert(hoade);
            var hoaDe = $.parseJSON(hoade);
            $("#headOfAc").empty();
            $("#headOfAc").append("<option value='null'>select</option>");
            $.each(hoaDe, function (k, v) {

                $("#headOfAc").append("<option value=" + v.accountType + ">(" + v.accountCode + ")" + v.accountType + "</option>");
                $("#headOfAc").selectpicker("refresh");
            });

            $("#headOfAc").on('change', function () {
                $("#errorShortCo").empty();
                var hoaVar = this.value;                
                if (hoaVar === 'null' || hoaVar === '') {
                    $("#scode option").remove();
                    $("#errorShortCo").append("Select Head Of Account. ");
                    $("#detailedHead").val('');
                } else {
                    $.ajax({
                        url: "getHoaSsortByAcName.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({acName: hoaVar}),
                        success: function (hoashort) {
                            //alert(hoashort);
                            $("#detailedHead").val(hoaVar);
                            var shortCod = $.parseJSON(hoashort);
                            if ($.isEmptyObject(shortCod)) {
                                $("#errorShortCo").append("This number has no data. ");
                                $("#scode option").remove();
                                $("#detailedHead").val('');
                            } else {
                                $("#scode").empty();
                                $("#scode").append("<option value='null'>select</option>");
                                $.each(shortCod, function (k, w) {
                                    $("#scode").append("<option value=" + w.shortCode + ">" + w.shortCode + "</option>");
                                    $("#scode").selectpicker("refresh");
                                });
                            }
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert(textStatus + " :" + errorThrown);
                        }
                    });
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error :" + jqXHR.status + ", " + errorThrown);
        }, complete: function (data) {

        }
    });
    //******************************************************************
    //END: HEAD OF ACCOUNT, SHORT CODE DISPLY
    //****************************************************************** 
    
    
    $("#form_record").hide();
    $("#form_page").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save challan entry for cash  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#challanNo, #challanDate,#month,#challanYear, #challanAmt,' +
                '#headOfAc, #scode, #detailedHead, #subHead, #desOfHead,' +
                ' #cqeDdR, #pprNo, #lrNo, #bankLRC,' +
                '#details, #total');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
            var jsonObj = JSON.stringify([obj]);
            //alert(jsonObj);
        $.ajax({
            dataType : "json",
            url : "saveChallanEntry.htm",
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
                    $("#Challan_Form")[0].reset();
                    $('.editOption').hide();                    
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
    //END Save challan entry for cash details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display challan entry for cash record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getChallanEnFrCashRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(chaallan){            
            var result = $.parseJSON(chaallan);
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
                           "<td>"+v.challanNo+"</td><td>"+v.challanDate+"</td>"+  
                           "<td>"+v.challanYear+"</td><td>"+v.challanAmt+"</td>"+
                           "<td>"+v.desOfHead+"</td><td>"+v.total+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='generatePdf' style='margin-right: 5px;' value=" + v.challanId + ">PDF </button>"+
                           "<button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.challanId + ">Edit </button></td> ";
                                            
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
                        {title: "Challan NO", width: '10%'},
                        {title: "Challan Date", width: '13%'},
                        {title: "Challan Year", width: '13%'},
                        {title: "Challan Amount", width: '13%'},
                        {title: "Description Of Head", width: '16%'},
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
            
        }
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#Challan_Form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getChallanEnFrCashReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({challanEn_id: this.value}),
                        success: function (challencas) {                              
                            var result = $.parseJSON(challencas);
                            $.each(result, function (k, v) {                                
                                $("#challanId").val(v.challanId);
                                $("#challanNo").val(v.challanNo);
                                $("#challanDate").val(v.challanDate);  
                                $("#month").val(v.month); 
                                $("#challanYear").val(v.challanYear); 
                                $("#challanAmt").val(v.challanAmt); 
                                $("#headOfAc").val(v.headOfAc); 
                                $("#scode").val(v.scode); 
                                
                                $("#detailedHead").val(v.detailedHead); 
                                $("#subHead").val(v.subHead); 
                                $("#desOfHead").val(v.desOfHead); 
                                $("#details").val(v.details); 
                                $("#total").val(v.total);
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#scode").selectpicker("refresh");
                            $("#headOfAc").selectpicker("refresh");
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                }); //End edit button
   
                $("#basic-usage").on('click', '#generatePdf', function (){
                    //alert(this.value);
                    window.open('challanCashPdfDown.htm?eindi='+this.value);
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
        $("#Challan_Form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#challanId, #challanNo, #challanDate,#month,#challanYear, #challanAmt,' +
                '#headOfAc, #scode, #detailedHead, #subHead, #desOfHead,' +
                ' #cqeDdR, #pprNo, #lrNo, #bankLRC,' +
                '#details, #total');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateChallanEnFrDe.htm",
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
                    $("#Challan_Form")[0].reset();
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
        $("#Challan_Form")[0].reset();
        close_message();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
        challan_incre();
        $("#scode").selectpicker("refresh");
        $("#headOfAc").selectpicker("refresh");
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
