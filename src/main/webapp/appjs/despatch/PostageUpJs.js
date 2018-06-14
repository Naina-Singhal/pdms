/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    

    $("#navigation li a").removeClass("active");
    $("#despatchUl").css("display", "block");
    $("#postagupdgr").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    $("#form_record").hide();
    $("#form_page").show();
    generatePostageTable();
    
    
    var today = new Date();
    var date = (today.getMonth()+1)+'-'+today.getDate()+'-'+today.getFullYear();    
    $("#toDayDate").val(date);
    
    function generatePostageTable() {
        $("#stic_items").empty();
        $.ajax({
            url: "getPostEntryRecord.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (postEntry) {
                //alert(postEntry);
                var final;
                var slno = 1;
                var ckslno = 1;
                var group = 1;
                var store = 1;
                var qtyOrd = 1;
                var itemH = 1;
                var qtyOrdH = 1;
                var stor = 1;
                var sto = 1;
                var qtyOr = 1;
                var result = $.parseJSON(postEntry);
                $.each(result, function (k, v) {
                    //alert(v.dispatchNo);
                    $.ajax({
                        url: "getPostVenReByDisNo.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({dispatch_no: v.dispatchNo}),
                        success: function (dbincre) {
                            //alert(dbincre);
                            var result = $.parseJSON(dbincre);
                            $.each(result, function (k, z) {

                                if (v.dispatchNo === z.dispatchno) {
                                    //alert("----" + z.dispatchno);
                                    final = final + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItems' value='" + (ckslno++) + "' /></td>" +
                                            "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                                            "<td><input type='text' name='' class='painput dispatchno" + (itemH++) + "' value='" + z.dispatchno + "' /></td>" +
                                            "<td><input type='text' name='' class='painput nature" + (group++) + "' value='" + v.nature + "' /></td>" +
                                            "<td><textarea type='text' name='' class='painput cardNo" + (store++) + "'>" + z.vendorObj.vendorName + "</textarea></td>" +
                                            "<td><textarea type='text' name='' class='painput unitH" + (qtyOrdH++) + "'>" + z.vendorObj.vendorCity + "</textarea>" +
                                            "<input type='hidden' name='' class='vendorid" + (qtyOrd++) + "' value='" + z.vendorid + "' /></td>" +
                                            "<td><input type='text' name='' class='painput todss" + (stor++) + "' value='(" + v.todsObj.code + ")" + v.todsObj.name + "' />" +
                                            "<input type='hidden' name='' class='tods" + (qtyOr++) + "' value='" + v.todsObj.code + "' /></td>" +
                                            "<td><input type='text' name='' class='painput amount" + (sto++) + "' value='" + z.amount + "' /></td>" +
                                            "</tr>";
                                }
                            });

                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error occured at Dispatch Number:" + jqXHR.status + ", " + errorThrown);
                        }
                    });
                });
                $("#stic_items").html(final).css({"color": "#0000009c"});
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at Dispatch Number:" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    
    $("#closeButton").click(function () {
        
        $("#errorCloseButton").empty();
        var dateC = $("#toDayDate").val();
        if(dateC === ''){
            $("#errorCloseButton").append("Please select closing date");
        }else{
            
        var $items = $('#toDayDate, #status');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObjC = JSON.stringify([obj]);           
        $.ajax({
            dataType: "json",
            url: "saveCloseButton.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: jsonObjC,
            type: 'POST',
            success: function (html) {
                var data = parseInt(html);                
                    close_message();
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong>Successfully File saving's closed for today.");
                    $(window).scrollTop(0);                                       
                }else if(data === 0){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Already done 'file saving closed'.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("Error occured at close button:" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    });
    
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#toDayDate, #totalAmt, #otBalance, #ctBalance, #postage');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var val = [];
        var myDataObj = [];
        $('#chckItems:checked').each(function (i) {
            val[i] = $(this).val();
            var obj = {
                dispatchno: $(".dispatchno" + val[i]).val(),
                nature: $(".nature" + val[i]).val(),
                vendorid: $(".vendorid" + val[i]).val(),
                tods: $(".tods" + val[i]).val(),
                amount: $(".amount" + val[i]).val()                
            };
            myDataObj.push(obj);

        });
            
        var jsonOb = JSON.stringify([obj]);        
        var parObj = JSON.parse(jsonOb);
        var inwardJson = [];        
        
        inwardJson = JSON.stringify({ postageDTO : parObj, postageItemsDTO : myDataObj});            
        
        $.ajax({
            dataType : "json",
            url : "savePostageData.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : inwardJson,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                //alert(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#second_form")[0].reset();
                        $("#form_record").show();
                        $("#form_page").hide();
                        show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                }else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                }else if (data === -7) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Already files closed,  please try next day .");
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
        url: "getPostageUpRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(PostageUp){            
            var result = $.parseJSON(PostageUp);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.toDayDate+"</td><td>"+v.totalAmt+"</td>"+  
                           "<td>"+v.otBalance+"</td><td>"+v.ctBalance+"</td>"+
                           "<td>"+v.postage+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.postageId + ">Edit </button></td> ";
                                            
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
                        {title: "Today Date", width: '16%'},
                        {title: "Total", width: '16%'},
                        {title: "OT Balance", width: '16%'},
                        {title: "CT Balance", width: '16%'},
                        {title: "Postage", width: '13%'},                        
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
                    $("#second_form")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getPostageReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({postage_id: this.value}),
                        success: function (html) {                              
                            var result = $.parseJSON(html);
                            $.each(result, function (k, v) {                                
                                $("#postageId").val(v.postageId);
                                //$("#toDayDate").val(v.toDayDate);
                                $("#totalAmt").val(v.totalAmt);  
                                $("#otBalance").val(v.otBalance); 
                                $("#ctBalance").val(v.ctBalance); 
                                $("#postage").val(v.postage);                                
                            });
                            
                            $("#form_record").hide();
                            $("#form_page").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#table_one").hide();
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
        $("#second_form")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#postageId, #toDayDate, #totalAmt, #otBalance, #ctBalance, #postage');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updatePostageRe.htm",
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
                    $("#second_form")[0].reset();
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
        close_message();
        //generatePostageTable();
        $("#second_form")[0].reset();
        $("#table_one").show();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");  
        
        $('input[type=checkbox]').each(function ()
        {
            this.checked = false;
        }); 
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

