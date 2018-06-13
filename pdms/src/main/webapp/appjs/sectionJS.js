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
      $("#sec").addClass("active");
      
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
    $("#sectionName").val("");
    $("#sectionCode").val("");
    $("#description").val("");
    $("#encFieldValue").val("");
    
    $("#cancelSection").click(function () {
        $("#sectionName").val("");
        $("#sectionCode").val("");
        $("#description").val("");
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addSection").css("display", "block");
    });
    
    $("#addSection").click(function () {
        if(fnValidateSaveSection())
        {
            document.addSectionForm.action = "savesection.htm";
            document.addSectionForm.submit();
        }
    });
    
    $("#updateSection").click(function () {
        if(fnValidateSaveSection())
        {
            document.addSectionForm.action = "updatesection.htm";
            document.addSectionForm.submit();
        }
    });
    
    
});

function fnEditSection(itid)
{
    $.ajax({
        url: 'editsection.htm?seid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var secName = jsonresult.sectionName;
                var secCode = jsonresult.sectionCode;
                var description = jsonresult.description;
                var encCatId = jsonresult.encFieldValue;

                $("#sectionName").val(secName);
                $("#sectionCode").val(secCode);
                $("#description").val(description);
                $("#encFieldValue").val(encCatId);
                
                $("#updatediv").css("display", "block");
                $("#addSection").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveSection()
{
    $("#errorsectionName").html("");
    $("#errorsectionCode").html("");
   
    var nameregx = /^[_()-\]&[+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
   
    var secname = $("#sectionName").val();
    var seccode = $("#sectionCode").val();
    
    if (($.trim(secname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorsectionName").html("Please enter Section Name");
    } else {
        if(nameregx.test($.trim(secname)))
        {
            flagValidTotal.push(true);
            $("#errorsectionName").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorsectionName").html("Invalid Section Name accepted special characters  '_&()[]-'");
        }
    }
    if (($.trim(seccode) == ''))
    {
        flagValidTotal.push(false);
        $("#errorsectionCode").html("Please enter Section Code");
    } else {
        if(coderegx.test($.trim(seccode)))
        {
            flagValidTotal.push(true);
            $("#errorsectionCode").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorsectionCode").html("Invalid Section Code. Section Code must be alphanumeric.");
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

