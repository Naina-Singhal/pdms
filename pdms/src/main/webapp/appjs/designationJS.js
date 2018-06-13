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
      $("#desig").addClass("active");
      
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
    $("#designationName").val("");
    $("#designationCode").val("");
    $("#description").val("");
    $("#encFieldValue").val("");
    
    $("#cancelDesig").click(function () {
        $("#designationName").val("");
        $("#designationCode").val("");
        $("#description").val("");
        $("#encFieldValue").val("");
        $("#isSigningAuthority").removeAttr("checked");
        $("#isApprovingAuthority").removeAttr("checked");
        $("#updatediv").css("display", "none");
        $("#addDesig").css("display", "block");
    });
    
    $("#addDesig").click(function () {
        if(fnValidateSaveDesig())
        {
            document.addDesigForm.action = "savedesig.htm";
            document.addDesigForm.submit();
        }
    });
    
    $("#updateDesig").click(function () {
        if(fnValidateSaveDesig())
        {
            document.addDesigForm.action = "updatedesig.htm";
            document.addDesigForm.submit();
        }
    });
    
    
});

function fnEditDesig(itid)
{
    $.ajax({
        url: 'editdesig.htm?dsid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var desName = jsonresult.designationName;
                var desCode = jsonresult.designationCode;
                var description = jsonresult.description;
                var encDesId = jsonresult.encFieldValue;
                var signAuth = jsonresult.isSigningAuthority;
                var apprAuth = jsonresult.isApprovingAuthority;
                

                $("#designationName").val(desName);
                $("#designationCode").val(desCode);
                $("#description").val(description);
                $("#encFieldValue").val(encDesId);
                
                if(eval(signAuth) >0)
                {
                    //$("#isSigningAuthority").attr("checked","checked");
                    $("#isSigningAuthority").prop("checked",true);
                }
                if(eval(apprAuth) >0)
                {
                    //$("#isApprovingAuthority").attr("checked","checked");
                    $("#isApprovingAuthority").prop("checked",true);
                }
                
                $("#updatediv").css("display", "block");
                $("#addDesig").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveDesig()
{
    var nameregx = /^[_()-\][+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;

    $("#errordesignationName").html("");
    $("#errordesignationCode").html("");
   
    var desname = $("#designationName").val();
    var descode = $("#designationCode").val();
    
    if (($.trim(desname) == ''))
    {
        flagValidTotal.push(false);
        $("#errordesignationName").html("Please enter Designation Name");
    } else {
        if(nameregx.test($.trim(desname)))
        {
            flagValidTotal.push(true);
            $("#errordesignationName").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errordesignationName").html("Invalid Designation Name accepted special characters  '_()[]-'");
        }
    }
    if (($.trim(descode) == ''))
    {
        flagValidTotal.push(false);
        $("#errordesignationCode").html("Please enter Designation Code");
    } else {
        if(coderegx.test($.trim(descode)))
        {
            flagValidTotal.push(true);
            $("#errordesignationCode").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errordesignationCode").html("Invalid Designation Code. Designation Code must be alphanumeric.");
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
