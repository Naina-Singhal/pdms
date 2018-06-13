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
      $("#egm").addClass("active");
      
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


    $("#groupID").val("0");
    $('#empList').html('');
    $("#encFieldValue").val("");

    $("#cancelEmpGrpMap").click(function () {
        $("#groupID").val("0");
        $("#groupID").prop("disabled",false);
        $('#empList').html('');
        $("#encFieldValue").val("");

        $("#updatediv").css("display", "none");
        $("#addEmpGrpMap").css("display", "block");
    });


    $("#addEmpGrpMap").click(function () {
        if (fnValidateSaveEmpGrpMapping())
        {
            document.empMapForm.action = "saveempgrpmap.htm";
            document.empMapForm.submit();
        }
    });
    
    $("#updateEmpGrpMap").click(function () {
        if (fnValidateSaveEmpGrpMapping())
        {
            $("#groupID").prop("disabled",false);
            document.empMapForm.action = "updempgrpmap.htm";
            document.empMapForm.submit();
        }
    });
    
    


});

function fnEditEmpGroup(itid)
{
    $.ajax({
        url: 'editempgrp.htm?gpid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            //alert(jsonresult);
            var grpId = 0;
            for(var i in jsonresult)
            {
                grpId = jsonresult[i].groupID;
                var firstName = jsonresult[i].firstName;
                var lastName = jsonresult[i].lastName;
                var empId = jsonresult[i].employeeID;
                
                $("#empList").append(new Option(firstName+" "+lastName, empId));
            }
            
            $("#groupID").val(grpId);
            $("#groupID").prop("disabled",true);
            
            $("#updatediv").css("display", "block");
            $("#addEmpGrpMap").css("display", "none");
        }
    });
}

function fnValidateSaveEmpGrpMapping()
{

    var flagValidTotal = new Array();
    var returnstring = false;

    var grpId = $("#groupID").val();
    var selVal = selIt();

    $("#errorgroupID").html("");
    $("#errorempList").html("");
    
    if (($.trim(grpId) ==='0'))
    {
        flagValidTotal.push(false);
        $("#errorgroupID").html("Please select Group Name");
    } else {
        flagValidTotal.push(true);
        $("#errorgroupID").html("");
    }
    
    if(selVal)
    {
        flagValidTotal.push(true);
        $("#errorempList").html("");
    }
    else
    {
        flagValidTotal.push(false);
        $("#errorempList").html("Please select Employees to Map");
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