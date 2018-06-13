/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    //$("#categoryID").searchable();
    $("#itemID").customselect();
    $("#navigation li a").removeClass("active");

    $("#vlnk").addClass("active");
    $("#vmenu").css("display", "block");
    $("#srcvupd").addClass("active");
    
    $("#fetchVDtls").click(function (){
    
        $("#erroritemID").html("");
        var itmId = $("#itemID").val();
        if($.trim(itmId) === '0')
        {
            $("#erroritemID").html("Please select Item");
            return false;
        }
        
        document.srcUpdForm.action="srcupdatefetvends.htm";
        document.srcUpdForm.submit();
        
    });
    
    
    $("#updateSrcIndent").click(function(){
       var conf = window.confirm("Are you sure to record the selected vendors?"); 
       if(conf)
       {
           document.updSourceForm.action="updvendorsrc.htm";
           document.updSourceForm.submit();
       }
    });
});
