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
      $("#vstk").addClass("active");
      
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
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "select"},
            {type: "select"},
            {type: "text"},
            null
        ]
    });
    
    $("#fetchDtls").click(function(){
        //alert("In fetchDtls");
        $("#errorcategoryID").html("");
        var catID = $("#categoryID").val();
        //alert("catID>>>"+catID);
        if($.trim(catID) === '0')
        {
            $("#errorcategoryID").html("Please select Category to Proceed");
            return false;
        }
        
        document.addItemForm.action="fetchitembycat.htm";
        document.addItemForm.submit();
    });
    
    $("#raiseIndent").click(function(){
        document.addItemForm.action="stkindentform.htm";
        document.addItemForm.submit();
    });

});