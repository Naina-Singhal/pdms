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
      
      $("#vlnk").addClass("active");
      $("#vmenu").css("display","block");
      $("#vlist").addClass("active");
      
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
                    null
                ]
            });
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************

    $("#addVendor").click(function () {
        document.vendorLi.action = "addvendor.htm"
        document.vendorLi.submit();
    });
});


function fnEditVendor(vdi)
{
    document.vendorLi.action = "editvendor.htm?vdi="+vdi;
    document.vendorLi.submit();
}

function fnViewVendor(vdi)
{
    document.vendorLi.action = "viewvendor.htm?vdi="+vdi;
    document.vendorLi.submit();
}