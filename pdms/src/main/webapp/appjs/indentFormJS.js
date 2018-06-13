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
      
      $("#indlk").addClass("active");
      $("#indmenu").css("display","block");
      $("#nwind").addClass("active");
      
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
    
    $("#redirectIndentForm").click(function(){
       $.blockUI({message: "<b>Please wait while application prepares date for Indent Creation...</b>"});
       document.addIndentForm.action = "indentformredirect.htm";
       document.addIndentForm.submit(); 
    });
    
    $("#categoryID").change(function(){
        $.blockUI({message: "<b>Please wait while application loads items for selected category...</b>"});
        document.addIndentForm.action = "getselcatitems.htm";
        document.addIndentForm.submit();
    });
    
    $("#saveIndent").click(function () {
        if (fnValidateIndentForm())
        {
            document.addIndentForm.action = "saveindent.htm";
            document.addIndentForm.submit();
        }
    });
    $("#updateIndent").click(function () {
        if (fnValidateIndentForm())
        {
            document.addIndentForm.action = "updateindent.htm";
            document.addIndentForm.submit();
        }
    });
    //
    $("#cancelIndent").click(function () {
        document.addIndentForm.action = "appuserindli.htm";
        document.addIndentForm.submit();
    });
    $("#cancelView").click(function () {
        document.addIndentForm.action = "indentli.htm";
        document.addIndentForm.submit();
    });
    //


    $("#updateIndent").click(function () {
        if (fnValidateIndentForm())
        {
            document.addIndentForm.action = "updateindent.htm";
            document.addIndentForm.submit();
        }
    });
    //
    $("#cancelEdit").click(function () {
        document.addIndentForm.action = "appuserindli.htm";
        document.addIndentForm.submit();
    });
    $("#appCancelIndent").click(function () {
        document.addIndentForm.action = "indentli.htm";
        document.addIndentForm.submit();
    });
    $("#signCancelIndent").click(function () {
        document.addIndentForm.action = "indentli.htm";
        document.addIndentForm.submit();
    });
    
    
    $("#appApproveIndent").click(function(){
        if (fnValidateApproveIndentForm())
        {
            var btVal = $(this).attr("butval");
            document.addIndentForm.action = "approveindent.htm?apdi=" + btVal;
            document.addIndentForm.submit();
        }
    });
    
    $("#signApproveIndent").click(function(){
        if (fnValidateSignIndentForm())
        {
            var btVal = $(this).attr("butval");
            document.addIndentForm.action = "updsignindent.htm?apdi=" + btVal;
            document.addIndentForm.submit();
        }
    });
    
    $("#appRevertIndent").click(function(){
        if (fnValidateRevertIndentForm())
        {
            var btVal = $(this).attr("butval");
            document.addIndentForm.action = "approveindent.htm?apdi=" + btVal;
            document.addIndentForm.submit();
        }
    });
    
    $("#appRejectIndent").click(function(){
        if (fnValidateRejectIndentForm())
        {
            var btVal = $(this).attr("butval");
            document.addIndentForm.action = "approveindent.htm?apdi=" + btVal;
            document.addIndentForm.submit();
        }
    });
    
    
    //***********************************************
    /*CODE ADDED FOR FILE UPLOAD PAGE*/
    //***********************************************
    
    $("#indentFile").change(function(){
        $("#uploadFile").val("");
        var fileName = document.getElementById("indentFile").value;//files[0].fileName;
        var fileEle = document.getElementById("indentFile").files[0].name;
        var updFileName = fileName.replace("C:\\fakepath\\", "");
        //$("#uploadFile").val($(this).val());
        $(this).attr("value", fileEle);
        $("#uploadFile").val(updFileName);
    });
    
    $("#startUpload").click(function(){
        
        var specialChars = "<>!#$%^&*()+[]{}?:;|'\"\\,/~`=";
        var check = function (string) {
            for (i = 0; i < specialChars.length; i++) {
                if (string.indexOf(specialChars[i]) > -1) {
                    //alert(specialChars[i]);
                    return false;
                }
            }
            return true;
        }
        
        var pattern = /^[a-zA-Z0-9_\-.\s]*$/;
        
        $("#errorindentFile").html("");
        //var upFile = $("#indentFile").val();
        var upFile = document.getElementById("indentFile").files[0].name;
        var hidUpFile = $("#uploadFile").val();
        if(($.trim(upFile) == '') && ($.trim(hidUpFile) == ''))
        {
            $("#errorindentFile").html("Please select file to Upload");
            return false;
        }
        else
        {
            var fileExtension = upFile.substring(upFile.lastIndexOf(".") + 1, upFile.length);
            var filename = upFile.substring(0, upFile.lastIndexOf("."));
            
            if(filename.length > 50)
            {
                $("#errorindentFile").html("Filename must not be greater than 50 Characters.");
                return false;
            }
            else if (fileExtension === "jpeg" || fileExtension === "JPEG" 
                    || fileExtension === "png" || fileExtension === "PNG" 
                    || fileExtension === "PDF" || fileExtension === "pdf" 
                    || fileExtension === "doc" || fileExtension === "docx"
                    || fileExtension === "xlsx" || fileExtension === "xls"
                    || fileExtension === "JPG"  || fileExtension === "jpg"
                    || fileExtension === "bmp" || fileExtension === "BMP"
                    || fileExtension === "DOC" || fileExtension === "DOCX") {
                
                if (pattern.test(filename))
                {
                    $.blockUI({message: "<b>Please wait while file is getting uploaded...</b>"});
                    document.indFileUpldForm.action="saveupdfile.htm";
                    document.indFileUpldForm.submit();
                }
                else
                {
                    $("#errorindentFile").html("Please upload a file with valid name. \n\
                                    File name must not contain special characters.");
                    return false;
                }
                
            }
            else
            {
                $("#errorindentFile").html("Supported File formats for Upload (JPG, GIF, PNG, PDF, DOC, DOCX, XLS, XLSX)");
                return false;
            }
        }
        
    });
    //$("#deleteUpFile").click(function(){
    $('button[id^="deleteUpFile"]').click(function(){
       
        var w = window.confirm("Are you sure to delete the selected file");
        if(w)
        {
            var encIndId = $(this).attr('indid');
            var encFileId = $(this).attr('upid');
            //alert("encIndId>>>"+encIndId);
            //alert("encFileId>>>"+encFileId);
            
            $.blockUI({message: "<b>Please wait while application is processing your request...</b>"});
            document.indFileUpldForm.action="delupdfile.htm?eid="+encIndId+"&efd="+encFileId;
            document.indFileUpldForm.submit();
        }
        
        
    });
    
    
    $('a[id^="indFileDnd"]').click(function(){
        var encIndId = $(this).attr('indid');
        var encFileId = $(this).attr('upid');
        window.open("dndupdfile.htm?eid="+encIndId+"&efd="+encFileId);
    });
    
    
    //appuserindli.htm
    $("#skipUpload").click(function()
    {
        var w = window.confirm("Are you sure to continue the process without uploading the documents/files?")
        if(w)
        {
            //document.indFileUpldForm.action="appuserindli.htm";
            document.indFileUpldForm.action="indsubmitredirect.htm";
            document.indFileUpldForm.submit();
        }
    });
    
    $("#saveFiles").click(function()
    {
        //document.indFileUpldForm.action="appuserindli.htm";
        document.indFileUpldForm.action="indsubmitredirect.htm";
        document.indFileUpldForm.submit();
    });
    
    //***********************************************
    /*CODE ADDED FOR FILE UPLOAD PAGE*/
    //***********************************************
    
    //**************************************************************
    /*CODE ADDED FOR CHANGE IN FILE UPLOAD DURING INDENT APPROVAL*/
    //**************************************************************
    
    $("#apprIndStartUpload").click(function(){
        
        var specialChars = "<>!#$%^&*()+[]{}?:;|'\"\\,/~`=";
        var check = function (string) {
            for (i = 0; i < specialChars.length; i++) {
                if (string.indexOf(specialChars[i]) > -1) {
                    //alert(specialChars[i]);
                    return false;
                }
            }
            return true;
        }
        
        var pattern = /^[a-zA-Z0-9_\-.\s]*$/;
        
        $("#errorindentFile").html("");
        var upFile = $("#indentFile").val();
        var hidUpFile = $("#uploadFile").val();
        
        if(($.trim(upFile) == '') && ($.trim(hidUpFile) == ''))
        {
            $("#errorindentFile").html("Please select file to Upload");
            return false;
        }
        else
        {
            var fileExtension = upFile.substring(upFile.lastIndexOf(".") + 1, upFile.length);
            var filename = upFile.substring(0, upFile.lastIndexOf("."));
            
            if(filename.length > 50)
            {
                $("#errorindentFile").html("Filename must not be greater than 50 Characters.");
                return false;
            }
            else if (fileExtension === "jpeg" || fileExtension === "JPEG" 
                    || fileExtension === "png" || fileExtension === "PNG" 
                    || fileExtension === "PDF" || fileExtension === "pdf" 
                    || fileExtension === "doc" || fileExtension === "docx"
                    || fileExtension === "xlsx" || fileExtension === "xls"
                    || fileExtension === "JPG"  || fileExtension === "jpg"
                    || fileExtension === "bmp" || fileExtension === "BMP"
                    || fileExtension === "DOC" || fileExtension === "DOCX") {
                
                if (pattern.test(filename))
                {
                    $.blockUI({message: "<b>Please wait while file is getting uploaded...</b>"});
                    document.addIndentForm.action="apprindsaveupdfile.htm";
                    document.addIndentForm.submit();
                }
                else
                {
                    $("#errorindentFile").html("Please upload a file with valid name. \n\
                                    File name must not contain special characters.");
                    return false;
                }
                
            }
            else
            {
                $("#errorindentFile").html("Supported File formats for Upload (JPG, GIF, PNG, PDF, DOC, DOCX, XLS, XLSX)");
                return false;
            }
        }
        
    });
   
    $('button[id^="apprIndDeleteUpFile"]').click(function(){
       
        var w = window.confirm("Are you sure to delete the selected file");
        if(w)
        {
            var encIndId = $(this).attr('indid');
            var encFileId = $(this).attr('upid');
            $.blockUI({message: "<b>Please wait while application is processing your request...</b>"});
            document.addIndentForm.action="apprinddelupdfile.htm?eid="+encIndId+"&efd="+encFileId;
            document.addIndentForm.submit();
        }
        
        
    });
    
    $("#submitAppUpload").click(function(){
        window.opener.location.reload();
        window.close();
    });
    
    //**************************************************************
    /*CODE ADDED FOR CHANGE IN FILE UPLOAD DURING INDENT APPROVAL*/
    //**************************************************************
    
    
    
    
});



function fnValidateIndentForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    $("#errorindentNumber").html("");
    //$("#errorrecevidedDate").html("");
    $("#errorestimatedCost").html("");
    $("#errorindentDate").html("");
    $("#errordescriptionDetails").html("");
    $("#errorapproveAuthorityId").html("");
    $("#errormodeOfPurchaseId").html("");
    $("#erroritemId").html("");
    $("#errorheadOfAccountId").html("");
    $("#errorplaceOfDeliveryId").html("");
    $("#errormta").html("");
    $("#errorbriefDescription").html("");
    $("#errorotherInformation").html("");
    $("#errorindentType").html("");
    $("#erroritemCategoryId").html("");
    $("#errorsectionId").html("");

    var indentNumber = $("#indentNumber").val();
    //var recevidedDate = $("#recevidedDate").val();
    var estimatedCost = $("#estimatedCost").val();
    var indentDate = $("#indentDate").val();
    var descriptionDetails = $("#descriptionDetails").val();
    var approveAuthorityId = $("#approveAuthorityId").val();
    var itemId = $("#itemId").val();
    var headOfAccountId = $("#headOfAccountId").val();
    var mta = $("#mta").val();
    var briefDescription = $("#briefDescription").val();
    var otherInformation = $("#otherInformation").val();
    var placeOfDeliveryId = $("#placeOfDeliveryId").val();
    var categoryID = $("#categoryID").val();
   
    
    

    var nameregx = /^[)-_(\+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
    var deciamlregx = /^\d+(\.\d{1,2})?$/;

    if ($('[id="selItem"]:checked').length > 0) {
        $("#errorItemID").html("");
        flagValidTotal.push(true);
    } else {
        $("#errorItemID").html("Please select Item(s) to add.");
        flagValidTotal.push(false);
    }
        
    if($("#sectionId"))    
    {
        var sectionID = $("#sectionId").val();
         
        if ($.trim(sectionID) === '0')
        {
            $("#errorsectionId").html("Please select Section");
            flagValidTotal.push(false);
        } else
        {
            $("#errorsectionId").html("");
            flagValidTotal.push(true);
        }
    }
    if ($.trim(categoryID) === '0')
    {
        $("#erroritemCategoryId").html("Please select Item Category");
        flagValidTotal.push(false);
    } else
    {
        $("#erroritemCategoryId").html("");
        flagValidTotal.push(true);
    }
    if ($.trim(indentNumber) === '')
    {
        $("#errorindentNumber").html("Please enter Indent Number");
        flagValidTotal.push(false);
    } else
    {
        if(nameregx.test(indentNumber))
        {
            $("#errorindentNumber").html("");
            flagValidTotal.push(true);
        }
        else
        {
            $("#errorindentNumber").html("Please enter valid Indent Number accepted special characters '_/()-' ");
            flagValidTotal.push(false);
        }
    }
    
    if ($.trim(placeOfDeliveryId) === '0')
    {
        $("#errorplaceOfDeliveryId").html("Please select Place Of Delivery");
        flagValidTotal.push(false);
    } else
    {
        $("#errorplaceOfDeliveryId").html("");
        flagValidTotal.push(true);
    }

/*
    if ($.trim(recevidedDate) === '')
    {
        $("#errorrecevidedDate").html("Please select Received Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errorrecevidedDate").html("");
        flagValidTotal.push(true);
    }
*/
    if ($.trim(estimatedCost) === '')
    {
        $("#errorestimatedCost").html("Please enter Estimated Cost");
        flagValidTotal.push(false);
    } else
    {
        if(deciamlregx.test($.trim(estimatedCost)))
        {
            $("#errorestimatedCost").html("");
            flagValidTotal.push(true);
        }
        else
        {
            $("#errorestimatedCost").html("Please enter valid Estimated Cost (accept only 2 decimals).");
            flagValidTotal.push(false);
        }
    }

    if ($.trim(indentDate) === '')
    {
        $("#errorindentDate").html("Please select Indent Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errorindentDate").html("");
        flagValidTotal.push(true);
    }
/*
    if($.trim(indentDate).length>0 && $.trim(recevidedDate).length > 0)
    {
        if(Date.parse($.trim(recevidedDate)) > Date.parse($.trim(indentDate)) )
        {
            $("#errorrecevidedDate").html("Received Date must be less than Indent Date.");
            flagValidTotal.push(false);
        }
        else
        {
            $("#errorrecevidedDate").html("");
            flagValidTotal.push(true);
        }
    }
*/
    if ($.trim(descriptionDetails) === '')
    {
        $("#errordescriptionDetails").html("Please enter Delivery Schedule Details");
        flagValidTotal.push(false);
    } else
    {
        $("#errordescriptionDetails").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(approveAuthorityId) === '0')
    {
        $("#errorapproveAuthorityId").html("Please select Approval Authority");
        flagValidTotal.push(false);
    } else
    {
        $("#errorapproveAuthorityId").html("");
        flagValidTotal.push(true);
    }

   
    if ($.trim(itemId) === '0')
    {
        $("#erroritemId").html("Please select Item");
        flagValidTotal.push(false);
    } else
    {
        $("#erroritemId").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(headOfAccountId) === '0')
    {
        $("#errorheadOfAccountId").html("Please select Head Of Account");
        flagValidTotal.push(false);
    } else
    {
        $("#errorheadOfAccountId").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(mta) === '')
    {
        $("#errormta").html("Please enter MTA");
        flagValidTotal.push(false);
    } else
    {
        $("#errormta").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(briefDescription) === '')
    {
        $("#errorbriefDescription").html("Please enter Brief Description");
        flagValidTotal.push(false);
    } else
    {
        $("#errorbriefDescription").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(otherInformation) === '')
    {
        $("#errorotherInformation").html("Please enter Other Information");
        flagValidTotal.push(false);
    } else
    {
        $("#errorotherInformation").html("");
        flagValidTotal.push(true);
    }

//  
//    if ($('[id="indentType"]:checked').length >0) {
//        $("#errorindentType").html("");
//        flagValidTotal.push(true);
//    } else {
//        $("#errorindentType").html("Please select Indent Type");
//        flagValidTotal.push(false);
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



function fnValidateApproveIndentForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    //$("#errorrecevidedDate").html("");
    $("#errorestimatedCost").html("");
    $("#errorindentDate").html("");
    $("#errordescriptionDetails").html("");
    $("#erroritemId").html("");
    $("#errorheadOfAccountId").html("");
    $("#errorplaceOfDeliveryId").html("");
    $("#errormta").html("");
    $("#errorbriefDescription").html("");
    $("#errorotherInformation").html("");
    $("#errorsigningAuthorityId").html("");
    $("#errorindentType").html("");

    //var recevidedDate = $("#recevidedDate").val();
    var estimatedCost = $("#estimatedCost").val();
    var indentDate = $("#indentDate").val();
    var descriptionDetails = $("#descriptionDetails").val();
    var headOfAccountId = $("#headOfAccountId").val();
    var mta = $("#mta").val();
    var briefDescription = $("#briefDescription").val();
    var otherInformation = $("#otherInformation").val();
    var signingAuthorityId = $("#signingAuthorityId").val();
    var placeOfDeliveryId = $("#placeOfDeliveryId").val();

    if ($('[id="selItem"]:checked').length > 0) {
        $("#erroritemId").html("");
        flagValidTotal.push(true);
    } else {
        $("#erroritemId").html("Please select Item(s) to add.");
        flagValidTotal.push(false);
    }

    if ($.trim(placeOfDeliveryId) === '0')
    {
        $("#errorplaceOfDeliveryId").html("Please select Place Of Delivery");
        flagValidTotal.push(false);
    } else
    {
        $("#errorplaceOfDeliveryId").html("");
        flagValidTotal.push(true);
    }
    
    /*
    if ($.trim(recevidedDate) === '')
    {
        $("#errorrecevidedDate").html("Please select Received Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errorrecevidedDate").html("");
        flagValidTotal.push(true);
    }
    */
   
    if ($.trim(estimatedCost) === '')
    {
        $("#errorestimatedCost").html("Please enter Estimated Cost");
        flagValidTotal.push(false);
    } else
    {
        $("#errorestimatedCost").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(indentDate) === '')
    {
        $("#errorindentDate").html("Please select Indent Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errorindentDate").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(descriptionDetails) === '')
    {
        $("#errordescriptionDetails").html("Please enter Delivery Schedule Details");
        flagValidTotal.push(false);
    } else
    {
        $("#errordescriptionDetails").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(headOfAccountId) === '0')
    {
        $("#errorheadOfAccountId").html("Please select Head Of Account");
        flagValidTotal.push(false);
    } else
    {
        $("#errorheadOfAccountId").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(mta) === '')
    {
        $("#errormta").html("Please enter MTA");
        flagValidTotal.push(false);
    } else
    {
        $("#errormta").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(briefDescription) === '')
    {
        $("#errorbriefDescription").html("Please enter Brief Description");
        flagValidTotal.push(false);
    } else
    {
        $("#errorbriefDescription").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(otherInformation) === '')
    {
        $("#errorotherInformation").html("Please enter Other Information");
        flagValidTotal.push(false);
    } else
    {
        $("#errorotherInformation").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(signingAuthorityId) === '0')
    {
        $("#errorsigningAuthorityId").html("Please select Signing Authority");
        flagValidTotal.push(false);
    } else
    {
        $("#errorsigningAuthorityId").html("");
        flagValidTotal.push(true);
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


function fnValidateRejectIndentForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    $("#errorrejcomments").html("");
    
    var comments = $("#rejcomments").val();
    
    if ($.trim(comments) === '')
    {
        $("#errorrejcomments").html("Please enter Comments.");
        flagValidTotal.push(false);
    } else
    {
        $("#errorrejcomments").html("");
        flagValidTotal.push(true);
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


function fnValidateRevertIndentForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    $("#errorcomments").html("");
    
    var comments = $("#comments").val();
    
    if ($.trim(comments) === '')
    {
        $("#errorcomments").html("Please enter Comments.");
        flagValidTotal.push(false);
    } else
    {
        $("#errorcomments").html("");
        flagValidTotal.push(true);
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



function fnValidateSignIndentForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    $("#errormodeOfPurchaseId").html("");
    $("#errorgroupID").html("");
    $("#errorrecevidedDate").html("");
    
    var mop = $("#modeOfPurchaseId").val();
    var grpId = $("#groupID").val();
    var recevidedDate = $("#recevidedDate").val();
    
    if ($.trim(recevidedDate) === '')
    {
        $("#errorrecevidedDate").html("Please select Received Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errorrecevidedDate").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(grpId) === '0')
    {
        $("#errorgroupID").html("Please select Group.");
        flagValidTotal.push(false);
    } else
    {
        $("#errorgroupID").html("");
        flagValidTotal.push(true);
    }
    if ($.trim(mop) === '0')
    {
        $("#errormodeOfPurchaseId").html("Please select Mode Of Purchase.");
        flagValidTotal.push(false);
    } else
    {
        $("#errormodeOfPurchaseId").html("");
        flagValidTotal.push(true);
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