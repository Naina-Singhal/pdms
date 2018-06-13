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
    $("#ptlnk").addClass("active");
    $("#ptmenu").css("display", "block");
    $("#ptilist").addClass("active");

    //***********************************************
    //End : Initialize basic datatable
    //***********************************************

    $("#sourceIndent").click(function () {
        alert("save tender");
        var lsCnt = $(this).attr("listCount");
        if (fnValidateTenderForm())
        {
            if (fnValidateTenderItemForm(lsCnt))
            {
                document.iTenderForm.action="savepubtender.htm";
                document.iTenderForm.submit();
            }
        }
    });
    
    $("#tenderTypeID").change(function(){
        var selTendType = $("#tenderTypeID option:selected").text();
        if($.trim(selTendType) === 'Public')
        {
            $("#lastDateSale").show();
        }
        else
        {
            $("#lastDateSale").hide();
        }
    });

});

function fnValidateTenderForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    var nameregx = /^[)-_(\+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;
    var deciamlregx = /^\d+(\.\d{1,2})?$/;

    $("#errorfileNo").html("");
    $("#errortenderCost").html("");
    $("#errorsetsNo").html("");
    $("#errorsaleLastDate").html("");
    $("#errordueDate").html("");
    $("#erroropeningDate").html("");
    $("#errorewd").html("");

    var fNo = $("#fileNo").val();
    var tCost = $("#tenderCost").val();
    var stNo = $("#setsNo").val();
    var sLastDate = $("#saleLastDate").val();
    var dDate = $("#dueDate").val();
    var opDate = $("#openingDate").val();
    var ewd = $("#ewd").val();

    if ($.trim(ewd) === '')
    {
        $("#errorewd").html("Please select EWD");
        flagValidTotal.push(false);
    } else
    {
        $("#errorewd").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(opDate) === '')
    {
        $("#erroropeningDate").html("Please select Opening Date");
        flagValidTotal.push(false);
    } else
    {
        $("#erroropeningDate").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(dDate) === '')
    {
        $("#errordueDate").html("Please select Due Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errordueDate").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(sLastDate) === '')
    {
        $("#errorsaleLastDate").html("Please select Sales Last Date");
        flagValidTotal.push(false);
    } else
    {
        $("#errorsaleLastDate").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(stNo) === '')
    {
        $("#errorsetsNo").html("Please enter No# Of Sets.");
        flagValidTotal.push(false);
    } else
    {
        $("#errorsetsNo").html("");
        flagValidTotal.push(true);
    }

    if ($.trim(tCost) === '')
    {
        $("#errortenderCost").html("Please enter Tender Cost.");
        flagValidTotal.push(false);
    } else
    {
        if (deciamlregx.test($.trim(tCost)))
        {
            $("#errortenderCost").html("");
            flagValidTotal.push(true);
        } else
        {
            $("#errortenderCost").html("Please enter valid Tender Cost.");
            flagValidTotal.push(false);
        }
    }

    if ($.trim(fNo) === '')
    {
        $("#errorfileNo").html("Please enter File No");
        flagValidTotal.push(false);
    } else
    {
        if (nameregx.test($.trim(fNo)))
        {
            $("#errorfileNo").html("");
            flagValidTotal.push(true);
        } else
        {
            $("#errorfileNo").html("Please enter valid File No#, accepted special characters '_/()-' ");
            flagValidTotal.push(false);
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
    //alert("returnstring:"+returnstring);
    return returnstring;

}


function fnValidateTenderItemForm(lsCnt)
{
    $("#errortendItems").html("");
    //alert("IN fnValidateTenderItemForm");
    for (var i = 0; i < lsCnt; i++)
    {
        var bDesc = $("#breifDesc" + i).val();
        var iQty = $("#itemQty" + i ).val();
        var iUnit = $("#itemUnits" + i).val();
        // && ($.trim(iQty) === '') && ($.trim(iUnit) === '')
        //alert(bDesc);
        if (($.trim(bDesc) === ''))
        {
            $("#errortendItems").html("Please provide values for all the Items");
            return false;
        }
    }
    return true;

}