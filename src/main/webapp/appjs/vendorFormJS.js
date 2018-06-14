/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    
    
    //***********************************************
//Start : Initialize basic datatable
//***********************************************
    if ($('#basic-usage'))
    {
        $('#basic-usage').dataTable({
            "aoColumns": [
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
                        null
                    ]
                });
    }
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
    
    $("#registrationType").change(function (){
        if($(this).val() === 'Un Registered')
        {
            $("#expDate").hide();
        }
        else
        {
            $("#expDate").show();
        }
    });
    
    $("#saveVendor").click(function(){
       if(fnValidateVendorForm()) 
       {
           document.addVendorForm.action="savevendor.htm";
           document.addVendorForm.submit();
       }
    });
    
    $("#updateVendor").click(function(){
       if(fnValidateVendorForm()) 
       {
           document.addVendorForm.action="updvendor.htm";
           document.addVendorForm.submit();
       }
    });
    
    
    $("#fetchItemDtls").click(function(){
        var catID = $("#categoryID").val();
        $("#errorcategoryID").html("");
        if($.trim(catID) === '0')
        {
            $("#errorcategoryID").html("Please Select Category");
        }
        else
        {
            $("#errorcategoryID").html("");
            
            document.vendorItemsForm.action="vendorselcatitems.htm";
            document.vendorItemsForm.submit();
        }
    });
    
    $("#addVendorItem").click(function(){
        
        if ($('[id="itemID"]:checked').length > 0) {
            $("#errorItemID").html("");
            document.vendorItemsForm.action="savevendoritems.htm";
            document.vendorItemsForm.submit();
        } else {
            $("#errorItemID").html("Please select Item(s) to add");
        }
    });
    
    
    $("#cancelView").click(function(){
       document.vIndentForm.action="vendorli.htm";
       document.vIndentForm.submit();
    });
    
});

function fnDeleteVIEntry(selVI,vid)
{
    var conf = window.confirm("Are you sure to Un Map the selected item?");
    if(conf)
    {
        document.vendorItemsForm.action="delvendoritem.htm?vidi="+selVI+"&vdi="+vid;
        document.vendorItemsForm.submit();
    }
}
function fnEnableVIEntry(selVI,vid)
{
    var conf = window.confirm("Are you sure to Map the selected item?");
    if(conf)
    {
        document.vendorItemsForm.action="mapvendoritem.htm?vidi="+selVI+"&vdi="+vid;
        document.vendorItemsForm.submit();
    }
}


var vChk=0;
function fnCheckVendorName(vName)
{
    vChk=0;
    $.ajax({
        url: 'chkvendor.htm?vendorName=' + vName,
        type: "GET",
        dataType: 'json',
        contentType : "application/json",
        mimeType: 'application/json',
        async: false,
        success: function (retval) {
            if(retval > 0)
            {
                vChk = retval;
            }
        }
    });
}

function fnValidateVendorForm()
{
    var flagValidTotal = new Array();
    var returnstring = false;
    
    $("#errorvendorCode").html("");
    $("#errorvendorName").html("");
    $("#errorContactPerson").html("");
    $("#errorvendorAddress").html("");
    $("#errorvendorCity").html("");
    $("#errorvendorPin").html("");
    $("#errorvendorEmail").html("");
    $("#errorvendorPhone").html("");
    $("#errorvendorFax").html("");
    $("#errorvendorPan").html("");
    $("#errorvendorRating").html("");
    $("#errorsource").html("");
    $("#errorcomments").html("");
    $("#errorregistrationType").html("");
    $("#errorexpiraryDate").html("");
    
    var vCode=$("#vendorCode").val();
    var vName=$("#vendorName").val();
    var conPerson=$("#contactPerson").val();
    var vAdd=$("#vendorAddress").val();
    var vCity=$("#vendorCity").val();
    var vPin=$("#vendorPin").val();
    var vEmail=$("#vendorEmail").val();
    var vPhone=$("#vendorPhone").val();
    var vFax=$("#vendorFax").val();
    var vPan=$("#vendorPan").val();
    var vRate=$("#vendorRating").val();
    var vSrc=$("#source").val();
    var vComments=$("#comments").val();
    var vRegType=$("#registrationType").val();
    var vExpiry=$("#expiraryDate").val();
   
    if ($.trim(vCode) === '')
    {
        $("#errorvendorCode").html("Please enter Vendor Code");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorCode").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vName) === '')
    {
        $("#errorvendorName").html("Please enter Vendor Name");
        flagValidTotal.push(false);
    } 
    else
    {
        $("#errorvendorName").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(conPerson) === '')
    {
        $("#errorContactPerson").html("Please enter Contact Person");
        flagValidTotal.push(false);
    } 
    else
    {
        $("#errorContactPerson").html("");
        flagValidTotal.push(true);
    }
    
    
    
    if ($.trim(vAdd) === '')
    {
        $("#errorvendorAddress").html("Please enter Vendor Address");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorAddress").html("");
        flagValidTotal.push(true);
    }
        
    if ($.trim(vCity) === '')
    {
        $("#errorvendorCity").html("Please enter Vendor City");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorCity").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vPin) === '')
    {
        $("#errorvendorPin").html("Please enter Vendor Pin Code");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorPin").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vEmail) === '')
    {
        $("#errorvendorEmail").html("Please enter Vendor Email");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorEmail").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vPhone) === '')
    {
        $("#errorvendorPhone").html("Please enter Vendor Phone");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorPhone").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vFax) === '')
    {
        $("#errorvendorFax").html("Please enter Vendor Fax");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorFax").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vPan) === '')
    {
        $("#errorvendorPan").html("Please enter Vendor Pan No#");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorPan").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vRate) === '')
    {
        $("#errorvendorRating").html("Please enter Vendor Rating");
        flagValidTotal.push(false);
    } else
    {
        $("#errorvendorRating").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vSrc) === '0')
    {
        $("#errorsource").html("Please enter Source");
        flagValidTotal.push(false);
    } else
    {
        $("#errorsource").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vRegType) === '0')
    {
        $("#errorregistrationType").html("Please select Registration Type");
        flagValidTotal.push(false);
    } else
    {
        $("#errorregistrationType").html("");
        flagValidTotal.push(true);
    }
    
    if ($.trim(vRegType) === 'Registered')
    {
        if ($.trim(vExpiry) === '')
        {
            $("#errorexpiraryDate").html("Please select Expiriry Date");
            flagValidTotal.push(false);
        } else
        {
            $("#errorexpiraryDate").html("");
            flagValidTotal.push(true);
        }
    }
    
    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] === false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;
    
}

