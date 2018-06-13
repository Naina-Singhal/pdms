/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
      $("#navigation li a").removeClass("active");
      
      $("#master").addClass("active");
      $("#masterul").css("display","block");
      $("#mop").addClass("active");
      
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
    
    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
    $('#basic-usage').dataTable({
        "aoColumns": [
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            //{"<input class='form-control input-sm' type='text'/>"},
            {type: "text"},
            {type: "text"},
            null
        ]
    });
    
    
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
    $("#modeOfPurchase").val("");
    $("#description").val("");
    $("#encFieldValue").val("");
    
    $("#cancelMop").click(function () {
        $("#modeOfPurchase").val("");
        $("#description").val("");
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addMop").css("display", "block");
    });
    
    $("#addMop").click(function () {
        if(fnValidateSaveMOP())
        {
            document.addMopForm.action = "savemop.htm";
            document.addMopForm.submit();
        }
    });
    
    $("#updateMop").click(function () {
        if(fnValidateSaveMOP())
        {
            document.addMopForm.action = "updatemop.htm";
            document.addMopForm.submit();
        }
    });
    
    
});

function fnEditMop(itid)
{
    $.ajax({
        url: 'editmop.htm?mpid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var mopName = jsonresult.modeOfPurchase;
                var description = jsonresult.description;
                var encMopId = jsonresult.encFieldValue;
                

                $("#modeOfPurchase").val(mopName);
                $("#description").val(description);
                $("#encFieldValue").val(encMopId);
                
                $("#updatediv").css("display", "block");
                $("#addMop").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveMOP()
{
    $("#errormodeOfPurchase").html("");
    
    var nameregx = /^[_()-\][+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
    
    var mopname = $("#modeOfPurchase").val();
    var desc = $("#description").val();
    
    if (($.trim(mopname) === ''))
    {
        flagValidTotal.push(false);
        $("#errormodeOfPurchase").html("Please enter Mode Of Purchase");
    } else {
        if(coderegx.test($.trim(mopname)))
        {
            flagValidTotal.push(true);
            $("#errormodeOfPurchase").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errormodeOfPurchase").html("Invalid Mode of Purchase. It must be alphanumeric.");
        }
    }
    
    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;
}

