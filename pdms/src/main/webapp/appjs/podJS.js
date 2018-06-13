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
      $("#pod").addClass("active");
      
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
    $("#placeOfDelivery").val("");
    $("#description").val("");
    $("#encFieldValue").val("");
    
    $("#cancelPod").click(function () {
        $("#placeOfDelivery").val("");
        $("#description").val("");
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addPod").css("display", "block");
    });
    
    $("#addPod").click(function () {
        if(fnValidateSavePOD())
        {
            document.addPodForm.action = "savepod.htm";
            document.addPodForm.submit();
        }
    });
    
    $("#updatePod").click(function () {
        if(fnValidateSavePOD())
        {
            document.addPodForm.action = "updatepod.htm";
            document.addPodForm.submit();
        }
    });
    
    
});

function fnEditPod(itid)
{
    $.ajax({
        url: 'editpod.htm?pid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var podName = jsonresult.placeOfDelivery;
                var description = jsonresult.description;
                var encPodId = jsonresult.encFieldValue;
                

                $("#placeOfDelivery").val(podName);
                $("#description").val(description);
                $("#encFieldValue").val(encPodId);
                
                $("#updatediv").css("display", "block");
                $("#addPod").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSavePOD()
{
    $("#errorplaceOfDelivery").html("");
    
    var nameregx = /^[_()-\][+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
    
    var podname = $("#placeOfDelivery").val();
    var desc = $("#description").val();
    
    if (($.trim(podname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorplaceOfDelivery").html("Please enter Place Of Delivery");
    } else {
        if(coderegx.test($.trim(podname)))
        {
            flagValidTotal.push(true);
            $("#errorplaceOfDelivery").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorplaceOfDelivery").html("Invalid Place Of Delivery. It must be alphanumeric.");
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

