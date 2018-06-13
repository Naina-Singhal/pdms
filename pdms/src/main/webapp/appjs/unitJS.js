/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
     $("#navigation li a").removeClass("active");
      
      $("#master").addClass("active");
      $("#masterul").css("display","block");
      $("#unit").addClass("active");
    
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
    $("#unitName").val("");
    $("#unitCode").val("");
    $("#unitDescription").val("");
    $("#encFieldValue").val("");
    
    $("#cancelUnit").click(function () {
        $("#unitName").val("");
        $("#unitCode").val("");
        $("#unitDescription").val("");
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addUnit").css("display", "block");
    });
    
    $("#addUnit").click(function () {
        if(fnValidateSaveUnit())
        {
            document.addCatForm.action = "saveunit.htm";
            document.addCatForm.submit();
        }
    });
    
    $("#updateUnit").click(function () {
        if(fnValidateSaveUnit())
        {
            document.addCatForm.action = "updunit.htm";
            document.addCatForm.submit();
        }
    });
    
    
});

function fnEditUnit(itid)
{
    $.ajax({
        url: 'editunit.htm?uid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var uName = jsonresult.unitName;
                var uCode = jsonresult.unitCode;
                var description = jsonresult.unitDescription;
                var encUnitId = jsonresult.encFieldValue;

                $("#unitName").val(uName);
                $("#unitCode").val(uCode);
                $("#unitDescription").val(description);
                $("#encFieldValue").val(encUnitId);
                
                $("#updatediv").css("display", "block");
                $("#addUnit").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveUnit()
{
    $("#errorunitName").html("");
    $("#errorunitCode").html("");
   
    var uname = $("#unitName").val();
    var ucode = $("#unitCode").val();
    
    if (($.trim(uname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorunitName").html("Please enter Unit Name");
    } else {
        flagValidTotal.push(true);
        $("#errorunitName").html("");
    }
    if (($.trim(ucode) == ''))
    {
        flagValidTotal.push(false);
        $("#errorunitCode").html("Please enter Unit Code");
    } else {
        flagValidTotal.push(true);
        $("#errorunitCode").html("");
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

