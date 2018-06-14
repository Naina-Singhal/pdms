/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    $.ajax({
        url: "getFileNumbers.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (htmlH) {
            alert(htmlH);
            var result = $.parseJSON(htmlH);
            $("#indentNumber").empty();
            $("#indentNumber").append("<option value='null'>select</option>");
            $.each(result, function (k, v) {
                alert(v.fileNoFrMapp);			
                $("#indentNumber").append("<option value='" + v.fileNoFrMapp +"'>'" + v.fileNoFrMapp + "'</option>");
            });
            $('#indentNumber').selectpicker('refresh');
        }, complete: function (data) {
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error occured at generate file numbers");
        }
    });

});
