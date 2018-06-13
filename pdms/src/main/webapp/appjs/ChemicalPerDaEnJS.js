/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    //***********************************************
    //Find user permission for access this page
    //***********************************************
    $.ajax({
        url: "getUserPermForPage.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({page_ID: "d7"}),
            success: function(htmlH){				
		var result = $.parseJSON(htmlH);                
                var act = parseInt(result);    
		if(act === 0){                    
                    window.open("./login","_self");
                }else{                   
                }								
        }, complete: function (data) {
           
        }, beforeSend: function () {
           
        }
	});  
    
    $("#navigation li a").removeClass("active");
      $("#accountul").css("display","block");
      $("#chepervoucupd ").addClass("active");
      
      
    //hide the error, success messages when load the page
    $(".display_msg_error").hide();
    $(".display_msg_success").hide();
    
      });