/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    
    $("#updPassword").click(function () {
        if(fnValidateUpdPassword())
        {
            document.changePassForm.action = "updpswd.htm";
            document.changePassForm.submit();
        }
    });
    
    $("#updProfile").click(function () {
        if(fnValidateUpdProfile())
        {
            document.userProfileForm.action = "updprofile.htm";
            document.userProfileForm.submit();
        }
    });
    
});

function fnValidateUpdPassword()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    var cpass=$("#currentPassword").val();
    var npass=$("#newPassword").val();
    var cnpass = $("#confPassword").val();
    
    $("#errorcurrentPassword").html("");
    $("#errornewPassword").html("");
    $("#errorconfPassword").html("");
    
    if($.trim(cpass)==='')
    {
        flagValidTotal.push(false);
        $("#errorcurrentPassword").html("Please enter current password");
    }
    else
    {
        flagValidTotal.push(true);
        $("#errorcurrentPassword").html("");
    }
    if($.trim(npass)==='')
    {
        flagValidTotal.push(false);
        $("#errornewPassword").html("Please enter New password");
    }
    else
    {
        flagValidTotal.push(true);
        $("#errornewPassword").html("");
    }
    if($.trim(cnpass)==='')
    {
        flagValidTotal.push(false);
        $("#errorconfPassword").html("Please enter Confirm password");
    }
    else
    {
        if($.trim(npass)!=$.trim(cnpass))
        {
            flagValidTotal.push(false);
            $("#errorconfPassword").html("New Password and Confirm password must be same.");
        }
        else
        {
            flagValidTotal.push(true);
            $("#errorconfPassword").html("");
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


function fnValidateUpdProfile()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    $("#errorfirstName").html("");
    $("#errorlastName").html("");
    $("#erroremployeeEmail").html("");
    $("#errorphone").html("");
    
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#employeeEmail").val();
    var phone = $("#phone").val();
    
    if($.trim(phone)==='')
    {
        flagValidTotal.push(false);
        $("#errorphone").html("Please enter Phone No");
    }
    else
    {
        flagValidTotal.push(true);
        $("#errorphone").html("");
    }
    
    if($.trim(email)==='')
    {
        flagValidTotal.push(false);
        $("#erroremployeeEmail").html("Please enter Email");
    }
    else
    {
        flagValidTotal.push(true);
        $("#erroremployeeEmail").html("");
    }
    
    if($.trim(lastName)==='')
    {
        flagValidTotal.push(false);
        $("#errorlastName").html("Please enter Last Name");
    }
    else
    {
        flagValidTotal.push(true);
        $("#errorlastName").html("");
    }
    
    if($.trim(firstName)==='')
    {
        flagValidTotal.push(false);
        $("#errorfirstName").html("Please enter First Name");
    }
    else
    {
        flagValidTotal.push(true);
        $("#errorfirstName").html("");
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