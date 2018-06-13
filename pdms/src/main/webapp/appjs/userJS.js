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
      $("#usr").addClass("active");
      
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
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}
        ]
    })
            .columnFilter({
                sPlaceHolder: "head:after",
                aoColumns: [
                    {type: "text"},
                    {type: "text"},
                    {type: "text"},
                    {type: "text"},
                    {type: "text"},
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


    $("#firstName").val("");
    $("#middleName").val("");
    $("#lastName").val("");
    $("#icNumber").val("");
    $("#sectionID").val("0");
    $("#designationID").val("0");
    $("#employeeEmail").val("");
    $("#employeeTypeID").val("0");
    $('input:radio[id="gender"]').filter('[value="Male"]').attr('checked', false);
    $('input:radio[id="gender"]').filter('[value="Female"]').attr('checked', false);


    $("#cancelUser").click(function () {
        $("#firstName").val("");
        $("#middleName").val("");
        $("#lastName").val("");
        $("#icNumber").val("");
        if ($("#sectionID"))
        {
            $("#sectionID").val("0");
        }

        $("#designationID").val("0");
        $("#employeeEmail").val("");
        $("#employeeTypeID").val("0");

        $('input:radio[id="gender"]').filter('[value="Male"]').attr('checked', false);
        $('input:radio[id="gender"]').filter('[value="Female"]').attr('checked', false);

        $("#updatediv").css("display", "none");
        $("#addUser").css("display", "block");
    });


    $("#addUser").click(function () {
        if (fnValidateSaveUser())
        {
            document.addUserForm.action = "saveuser.htm";
            document.addUserForm.submit();
        }
    });

    $("#updateUser").click(function () {
        if (fnValidateSaveUser())
        {
            document.addUserForm.action = "upduser.htm";
            document.addUserForm.submit();
        }
    });

});

function fnEditUser(itid)
{
    $.ajax({
        url: 'editemp.htm?eid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if (jsonresult.encFieldValue.length > 0)
            {
                var icNo = jsonresult.employeeProfileDTO.icNumber;
                var fName = jsonresult.employeeProfileDTO.firstName;
                var mName = jsonresult.employeeProfileDTO.middleName;
                var lName = jsonresult.employeeProfileDTO.lastName;
                var email = jsonresult.employeeProfileDTO.employeeEmail;
                var gender = jsonresult.employeeProfileDTO.gender;
                var empTypeID = jsonresult.employeeProfileDTO.employeeTypeDTO.employeeTypeID;
                var secID = jsonresult.sectionDTO.sectionID;
                var desigID = jsonresult.designationDTO.designationID;
                var encUserID = jsonresult.encFieldValue;


                $("#firstName").val(fName);
                $("#middleName").val(mName);
                $("#lastName").val(lName);
                $("#icNumber").val(icNo);
                if (eval(secID) > 0)
                {
                    $("#sectionID").val(secID);
                }
                $("#designationID").val(desigID);
                $("#employeeEmail").val(email);
                $("#employeeTypeID").val(empTypeID);
                $("#encFieldValue").val(encUserID);

                if (gender == 'Male')
                {
                    //$("#gender")[0].checked = true;
                    $('input:radio[id="gender"]').filter('[value="Male"]').prop('checked', 'checked');
                } else if (gender == 'Female')
                {
                    //$("#gender")[1].checked = true;
                    $('input:radio[id="gender"]').filter('[value="Female"]').prop('checked', 'checked');
                }


                $("#updatediv").css("display", "block");
                $("#addUser").css("display", "none");
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveUser()
{
    var specialChars = "<>!#$%^&*()+[]{}?:;|'\"\\,/~`="
    var check = function (string) {
        for (i = 0; i < specialChars.length; i++) {
            if (string.indexOf(specialChars[i]) > -1) {
                //alert(specialChars[i]);
                return false;
            }
        }
        return true;
    }
    
    $("#errorfirstName").html("");
    $("#errormiddleName").html("");
    $("#errorlastName").html("");
    $("#erroricNumber").html("");
    if ($("#errorsectionID"))
    {
        $("#errorsectionID").html("");
    }
    $("#errordesignationID").html("");
    $("#erroremployeeEmail").html("");
    $("#errorgender").html("");
    $("#erroremployeeTypeID").html("");


    var fname = $("#firstName").val();
    var mname = $("#middleName").val();
    var lname = $("#lastName").val();
    var icno = $("#icNumber").val();
    if ($("#sectionID"))
    {
        var secid = $("#sectionID option:selected").val();
    }
    var desid = $("#designationID").val();
    var empemail = $("#employeeEmail").val();
    var emptype = $("#employeeTypeID").val();

    if ($('[id="gender"]:checked').length > 0) {
        $("#errorgender").html("");
        flagValidTotal.push(true);
    } else {
        $("#errorgender").html("Please select Gender");
        flagValidTotal.push(false);
    }

    if (($.trim(emptype) == '0'))
    {
        flagValidTotal.push(false);
        $("#erroremployeeTypeID").html("Please slect Employee Type");
    } else {
        flagValidTotal.push(true);
        $("#erroremployeeTypeID").html("");
    }
    
    var atpos = empemail.indexOf("@");
    var atposLastIndx = empemail.lastIndexOf("@");
    var dotposLastIndx = empemail.lastIndexOf(".");
    var dotpos = empemail.indexOf(".");
    
    if (($.trim(empemail) == ''))
    {
        flagValidTotal.push(false);
        $("#erroremployeeEmail").html("Please enter Email ID");
    } 
    else if (jQuery.trim(empemail).length >= 150) {
        $("#erroremployeeEmail").html("Email ID exceeds the limit of 200 characters");
        flagValidTotal.push(false);
    }
    else if (atpos < 1 || dotposLastIndx < atpos + 2 || dotposLastIndx + 2 >= empemail.length) {
        $("#erroremployeeEmail").html("Email ID can contain only alphanumeric and the following special characters -  _  . @");
        flagValidTotal.push(false);
    }
    else if (check(jQuery.trim(empemail)) === false) {
        $("#erroremployeeEmail").html("Please enter a valid Email ID");
        flagValidTotal.push(false);
    }
    else if ((atpos !== atposLastIndx)) {
        $("#erroremployeeEmail").html("Please enter a valid Email ID");
        flagValidTotal.push(false);
    }
    else {
        flagValidTotal.push(true);
        $("#erroremployeeEmail").html("");
    }

    if (($.trim(desid) == '0'))
    {
        flagValidTotal.push(false);
        $("#errordesignationID").html("Please select Designation");
    } else {
        flagValidTotal.push(true);
        $("#errordesignationID").html("");
    }

    if ($("#sectionID"))
    {
        if (($.trim(secid) == '0'))
        {
            flagValidTotal.push(false);
            $("#errorsectionID").html("Please select Section");
        } else {
            flagValidTotal.push(true);
            $("#errorsectionID").html("");
        }
    }

    if (($.trim(icno) == ''))
    {
        flagValidTotal.push(false);
        $("#erroricNumber").html("Please enter IC Number");
    } else {
        flagValidTotal.push(true);
        $("#erroricNumber").html("");
    }

    if (($.trim(fname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorfirstName").html("Please enter First Name");
    } else {
        flagValidTotal.push(true);
        $("#errorfirstName").html("");
    }
    if (($.trim(lname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorlastName").html("Please enter Last Name");
    } else {
        flagValidTotal.push(true);
        $("#errorlastName").html("");
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
