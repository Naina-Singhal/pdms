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
      $("#hoa").addClass("active");
      
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
            {"bSortable": true},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "text"},
            {type: "text"},
            null
        ]
    });
    
    
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
    $("#accountType").val("");
    $("#description").val("");
    $("#accountCode").val("");
    $("#encFieldValue").val("");
    
    $("#cancelHoa").click(function () {
        $("#accountType").val("");
        $("#accountCode").val("");
        $("#description").val("");
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addHoa").css("display", "block");
    });
    
    $("#addHoa").click(function () {
        if(fnValidateSaveHOA())
        {
            document.addHoaForm.action = "savehoa.htm";
            document.addHoaForm.submit();
        }
    });
    
    $("#updateHoa").click(function () {
        if(fnValidateSaveHOA())
        {
            document.addHoaForm.action = "updatehoa.htm";
            document.addHoaForm.submit();
        }
    });
    
    
});

function fnEditHoa(itid)
{
    $.ajax({
        url: 'edithoa.htm?hid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var actName = jsonresult.accountType;
                var actCode = jsonresult.accountCode;
                var description = jsonresult.description;
                var encHoaId = jsonresult.encFieldValue;
                
                $("#accountType").val(actName);
                $("#accountCode").val(actCode);
                $("#description").val(description);
                $("#encFieldValue").val(encHoaId);
                
                $("#updatediv").css("display", "block");
                $("#addHoa").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveHOA()
{
    $("#erroraccountType").html("");
    $("#erroraccountCode").html("");
    $("#errordescription").html("");
    
    var nameregx = /^[)-_(\+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
    
    var actname = $("#accountType").val();
    var actcode = $("#accountCode").val();
    var desc = $("#description").val();
    
    if (($.trim(actname) == ''))
    {
        flagValidTotal.push(false);
        $("#erroraccountType").html("Please enter Account Name");
    } else {
        if(nameregx.test($.trim(actname)))
        {
            flagValidTotal.push(true);
            $("#erroraccountType").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#erroraccountType").html("Invalid Account Name. Account Name must be alphanumeric.");
        }
    }
    if (($.trim(actcode) == ''))
    {
        flagValidTotal.push(false);
        $("#erroraccountCode").html("Please enter Account Code");
    } else {
        if(coderegx.test($.trim(actcode)))
        {
            flagValidTotal.push(true);
            $("#erroraccountCode").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#erroraccountCode").html("Invalid Account Code. Account Code must be alphanumeric.");
        }
    }
    if (($.trim(desc) == ''))
    {
        flagValidTotal.push(false);
        $("#errordescription").html("Please enter Account Description");
    }
    else
    {
        if (($.trim(desc).length>100))
        {
            flagValidTotal.push(false);
            $("#errordescription").html("Account Description must be less than 100 Chracters.");
        }
        else if(nameregx.test($.trim(desc)))
        {
            flagValidTotal.push(true);
            $("#errordescription").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errordescription").html("Invalid Account Description accepted special characters  '_()[]-'.");
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
