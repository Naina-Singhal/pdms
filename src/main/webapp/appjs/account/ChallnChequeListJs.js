/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#accountul").css("display", "block");
    $("#challachec").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    $("#challanShow").hide();
    $("#chequeShow").hide();
    var chooseOption = '';
    $("#chooseOption").on('change', function () {
        //alert(this.value);
        chooseOption = this.value;
        $("#fromDate").val('');
        $("#toDate").val('');
        close_message();
        if (chooseOption === 'challan') {
            $("#chequeList").empty();
            $("#challanShow").show();
            $("#chequeShow").hide();
        } else if (chooseOption === 'cheque') {
            $("#stic_items").empty();
            $("#challanShow").hide();
            $("#chequeShow").show();
        } else {
            $("#challanShow").hide();
            $("#chequeShow").hide();
            $("#stic_items").empty();
            $("#chequeList").empty();
            $("#challanCheque")[0].reset();
        }




    }); //End on change function

    $("#getRelatedData").on('click', function () {
        $("#fromDateErr").empty();
        $("#toDateErr").empty();        
        var fromDate = $("#fromDate").val();
        var toDate = $("#toDate").val();
        if ($("#fromDate").val() === "") {
            $("#challanShow").hide();
            $("#chequeShow").hide();
            $("#fromDateErr").append("Please select from date.");
        } else if ($("#toDate").val() === "") {
            $("#challanShow").hide();
            $("#chequeShow").hide();
            $("#toDateErr").append("Please select to date.");
        } else {

            if (chooseOption === 'challan') {

                $("#stic_items").empty();
                $.ajax({
                    url: "getChallanNoDetails.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    data: ({FromDate: fromDate, ToDate: toDate}),
                    success: function (details) {
                        //alert(details);
                        var result = $.parseJSON(details);
                        if ($.isEmptyObject(result)) {
                            $("#challanShow").hide();
                            $("#fromDateErr").append("There is no data available.");
                            $("#stic_items").empty();
                            $("#stic_items").append("This number has no data. ").css({"color": "#0000009c"});
                        } else {
                            $("#challanShow").show();
                            var slno = 1;
                            var ckslno = 1;

                            var code = 1;
                            var group = 1;
                            var store = 1;
                            var item = 1;
                            var itemH = 1;
                            var final1;
                            $.each(result, function (k, v) {
                                //alert("item : "+w.itemName);
                                final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckChallan' value='" + (ckslno++) + "' /></td>" +
                                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                                        "<td><input type='text' name='' class='painput challanNum" + (code++) + "' value='" + v.challanNo + "' /></td>" +
                                        "<td><input type='text' name='' class='painput challanDate" + (group++) + "' value='" + v.challanDate + "' /></td>" +
                                        "<td><input type='text' name='' class='painput challanAmt" + (store++) + "' value='" + v.challanAmt + "' /></td>" +
                                        "<td><input type='date' name='' class='painput chaRecevDate" + (itemH++) + "' value='' /></td>" +
                                        "</tr>";
                            });
                            $("#stic_items").html(final1).css({"color": "#0000009c"});

                        }
                    }, error: function (jqXHR, textStatus, errorThrown) {
                        alert(textStatus + " :" + errorThrown);

                    }, complete: function (data) {

                    }, beforeSend: function () {
                        //return confirm("Are you sure you want to submit ?");

                    }
                });
            } else if (chooseOption === 'cheque') {

                $("#chequeList").empty();
                $.ajax({
                    url: "getChequeNoDeList.htm",
                    type: "POST",
                    cache: false,
                    async: false,
                    data: ({FromDate: fromDate, ToDate: toDate}),
                    success: function (details) {
                        //alert(details);
                        var result = $.parseJSON(details);
                        if ($.isEmptyObject(result)) {
                            $("#chequeShow").hide();
                            $("#fromDateErr").append("There is no data available.");
                            $("#chequeList").empty();
                            $("#chequeList").append("This number has no data. ").css({"color": "#0000009c"});
                        } else {
                            $("#chequeShow").show();
                            var slno = 1;
                            var ckslno = 1;

                            var code = 1;
                            var group = 1;
                            var store = 1;
                            var item = 1;
                            var itemH = 1;
                            var final1;
                            $.each(result, function (k, v) {
                                //alert("item : "+w.itemName);
                                final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckCheque' value='" + (ckslno++) + "' /></td>" +
                                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                                        "<td><input type='text' name='' class='painput chequeNo" + (code++) + "' value='" + v.chequeNo + "' /></td>" +
                                        "<td><input type='text' name='' class='painput chequeDate" + (group++) + "' value='" + v.chequeDate + "' /></td>" +
                                        "<td><input type='text' name='' class='painput chequeAmount" + (store++) + "' value='" + v.chequeAmount + "' /></td>" +
                                        "<td><input type='date' name='' class='painput chequeRecevDate" + (itemH++) + "' value='' /></td>" +
                                        "</tr>";
                            });
                            $("#chequeList").html(final1).css({"color": "#0000009c"});

                        }
                    }, error: function (jqXHR, textStatus, errorThrown) {
                        alert(textStatus + " :" + errorThrown);

                    }, complete: function (data) {

                    }, beforeSend: function () {
                        //return confirm("Are you sure you want to submit ?");

                    }
                });
            } else {
                alert('Please select option');
            }




        }

    }); // End click function
    
    
    $("#saveChallanList").on('click', function(){
        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        
        var val = [];
        var myDataObj = [];
        $('#chckChallan:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                            
                    challanNum: $(".challanNum"+val[i]).val(),                    
                    challanDate: $(".challanDate"+val[i]).val(),
                    challanAmt: $(".challanAmt"+val[i]).val(),
                    chaRecevDate: $(".chaRecevDate"+val[i]).val()                    
            };
            myDataObj.push(obj);
            
        });
       
        var big = [];
         
        big = JSON.stringify(myDataObj);            
        
        //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveChallanOnlyList.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data(Challan List) has been Successfully saved.");
                    $(window).scrollTop(0);                    
                    $("#fromDate").val('');
                    $("#toDate").val('');        
                    $("#stic_items").empty();
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
        
        
    });
    
  
    $("#saveChequeList").on('click', function(){
               
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        
        var val = [];
        var myDataObj = [];
        $('#chckCheque:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                            
                    chequeNo: $(".chequeNo"+val[i]).val(),                    
                    chequeDate: $(".chequeDate"+val[i]).val(),
                    chequeAmount: $(".chequeAmount"+val[i]).val(),
                    chequeRecevDate: $(".chequeRecevDate"+val[i]).val()                    
            };
            myDataObj.push(obj);
            
        });
       
        var big = [];
         
        big = JSON.stringify(myDataObj);            
        
        //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveChequeOnlyList.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data(Cheque List) has been Successfully saved.");
                    $(window).scrollTop(0);                    
                    $("#fromDate").val('');
                    $("#toDate").val('');        
                    $("#chequeList").empty();
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
        
        
    });
    



}); //End document