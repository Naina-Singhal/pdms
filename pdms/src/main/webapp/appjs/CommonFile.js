/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    //**************************************************************************
    //***START: HIDE SHOW ERROR MESSAGES
    //**************************************************************************

    $(".display_msg_success_Ma p").remove();
    $(".display_msg_error_Ma p").remove();
    $(".display_msg_success_Ma").append("<p class='arrowMsg'></p>");
    $(".display_msg_error_Ma").append("<p class='arrowMsg'></p>");
    $(".arrowMsg").click(function () {
        close_message();
    });
    
    function close_message(){
        $(".display_msg_error_Ma").slideUp(1000);
        $(".display_msg_success_Ma").slideUp(1000);
    }
    function show_success_mes(){
        $("#successDupItem").empty();
        $(".display_msg_error_Ma").hide();
        $(".display_msg_success_Ma").slideDown(1500);
    }
    function show_error_mes(){
        $("#errorDupItem").empty();
        $(".display_msg_error_Ma").slideDown(1500);
        $(".display_msg_success_Ma").hide();
    }
    
    //**************************************************************************
    //***END: HIDE SHOW ERROR MESSAGES
    //**************************************************************************
     
     function addZeroes(num) {
        var value = Number(num);
        var res = num.split(".");
        if (num.indexOf('.') === -1) {
            value = value.toFixed(2);
            num = value.toString();
        } else if (res[1].length < 3) {
            value = value.toFixed(2);
            num = value.toString();
        }
        return num;
    }

