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
      $("#cat").addClass("active");
      
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
    $("#categoryName").val("");
    $("#categoryCode").val("");
    $("#description").val("");
    $("#encFieldValue").val("");
    $("#exCategoryName").val("");
    $("#exCategoryCode").val("");
    $("#cancelCategory").click(function () {
        $("#categoryName").val("");
        $("#categoryCode").val("");
        $("#description").val("");
        $("#encFieldValue").val("");
        $("#exCategoryName").val("");
        $("#exCategoryCode").val("");

        $("#updatediv").css("display", "none");
        $("#addCategory").css("display", "block");
    });
    
    $("#addCategory").click(function () {
        if(fnValidateSaveCategory())
        {
            document.addCatForm.action = "savecategory.htm";
            document.addCatForm.submit();
        }
    });
    
    $("#updateCategory").click(function () {
        if(fnValidateSaveCategory())
        {
            document.addCatForm.action = "updatecategory.htm";
            document.addCatForm.submit();
        }
    });
    
    
});

var vChk=0;
function fnCheckDuplicateCategory(cName,cCode)
{
    vChk=0;
    $.ajax({
        url: 'chkcategory.htm',
        type: "GET",
        dataType: 'json',
        contentType : "application/json",
        mimeType: 'application/json',
        data: { cname: cName, ccode: cCode },
        async: false,
        success: function (retval) {
            if(retval > 0)
            {
                vChk = retval;
            }
        }
    });
}

function fnEditCategory(itid)
{
    $.ajax({
        url: 'editcategory.htm?ctid=' + itid,
        global: false,
        type: "GET",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
        , async: false
        , success: function (jsonresult) {
            if(jsonresult.encFieldValue.length>0)
            {
                var catName = jsonresult.categoryName;
                var catCode = jsonresult.categoryCode;
                var description = jsonresult.description;
                var encCatId = jsonresult.encFieldValue;

                $("#exCategoryName").val(catName);
                $("#categoryName").val(catName);
                $("#categoryCode").val(catCode);
                $("#exCategoryCode").val(catCode);
                $("#description").val(description);
                $("#encFieldValue").val(encCatId);
                
                $("#updatediv").css("display", "block");
                $("#addCategory").css("display", "none");
            }
        }
    });
}


var flagValidTotal = new Array();
var returnstring = false;
//alphanumeric along with '_()[]-' Special Characters with spaces
//var catnameregx = /^[_()-\][+\w\s]*$/;
var catnameregx = /^[)-_(\+\w\s]*$/;
var catcoderegx = /^[+\w\s]*$/;
function fnValidateSaveCategory()
{
    
    $("#errorcategoryName").html("");
    $("#errorcategoryCode").html("");
   
    var catname = $("#categoryName").val();
    var catcode = $("#categoryCode").val();
    var eCName = $("#exCategoryName").val();
    var eCCode = $("#exCategoryCode").val();
    
    if (($.trim(catname) == ''))
    {
        flagValidTotal.push(false);
        $("#errorcategoryName").html("Please enter Category Name");
    } else {
        if(catnameregx.test($.trim(catname)))
        {
            flagValidTotal.push(true);
            $("#errorcategoryName").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorcategoryName").html("Invalid Category Name accepted special characters  '_()[]-'");
        }
    }
    if (($.trim(catcode) == ''))
    {
        flagValidTotal.push(false);
        $("#errorcategoryCode").html("Please enter Category Code");
    } else {
        if(catcoderegx.test($.trim(catcode)))
        {
            flagValidTotal.push(true);
            $("#errorcategoryCode").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#errorcategoryCode").html("Invalid Category Code. Category Code must be alphanumeric.");
        }
    }
   
//    if(($.trim(catname).length>0) &&($.trim(catcode).length > 0))
//    {
//        //if (($.trim(eCName) !== $.trim(catname)) || ($.trim(eCCode) !== $.trim()))
//        //alert(eCCode);
//        if (($.trim(eCCode) !== $.trim(catcode)))
//        {
//            fnCheckDuplicateCategory(catname, catcode);
//
//            if (vChk > 0)
//            {
//                $("#errorDupCategory").html("Category details already exists");
//                flagValidTotal.push(false);
//            } else
//            {
//                $("#errorDupCategory").html("");
//                flagValidTotal.push(true);
//            }
//        }
//    }
   
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
