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
      $("#grp").addClass("active");
      
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
    $("#groupName").val("");
    $("#groupCode").val("");
    $("#description").val("");
    $("#encFieldValue").val("");
    
    $("#cancelGroup").click(function () {
        $("#groupName").val("");
        $("#groupCode").val("");
        $("#description").val("");
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addGroup").css("display", "block");
    });
    
    $("#addGroup").click(function () {
        if(fnValidateSaveGroup())
        {
            document.addGroupForm.action = "savegroup.htm";
            document.addGroupForm.submit();
        }
    });
    
    $("#updateGroup").click(function () {
        if(fnValidateSaveGroup())
        {
            document.addGroupForm.action = "updategroup.htm";
            document.addGroupForm.submit();
        }
    });
    
    
});

function fnEditGroup(itid)
{
    $.ajax({
        url: 'editgroup.htm?cgid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var grpName = jsonresult.groupName;
                var grpCode = jsonresult.groupCode;
                var description = jsonresult.description;
                var encDesId = jsonresult.encFieldValue;
                

                $("#groupName").val(grpName);
                $("#groupCode").val(grpCode);
                $("#description").val(description);
                $("#encFieldValue").val(encDesId);
                
                $("#updatediv").css("display", "block");
                $("#addGroup").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveGroup()
{
    $("#errorgroupName").html("");
    $("#errorgroupCode").html("");
   
    var nameregx = /^[_()-\][+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
    
    var grpname = $("#groupName").val();
    var grpcode = $("#groupCode").val();
    
    if (($.trim(grpname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorgroupName").html("Please enter Group Name");
    } else {
        if(nameregx.test($.trim(grpname)))
        {
            flagValidTotal.push(true);
            $("#errorgroupName").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorgroupName").html("Invalid Group Name accepted special characters  '_()[]-'");
        }
    }
    if (($.trim(grpcode) == ''))
    {
        flagValidTotal.push(false);
        $("#errorgroupCode").html("Please enter Group Code");
    } else {
        if(coderegx.test($.trim(grpcode)))
        {
            flagValidTotal.push(true);
            $("#errorgroupCode").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorgroupCode").html("Invalid Group Code. Group Code must be alphanumeric.");
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
