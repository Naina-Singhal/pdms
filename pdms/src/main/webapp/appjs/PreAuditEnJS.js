/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
     
    
    $("#navigation li a").removeClass("active");
    $("#accountul").css("display","block");
    $("#preaudreen ").addClass("active");
      
      
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    generateRows();
    //******************************************************************
    //START: GENERATE PRE AUDIT FILES
    //******************************************************************   
    function generateRows() {
        $.ajax({
            url: "getDispatchEnDe.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (details) {
                var result = $.parseJSON(details);
                var slno = 1;
                var ckslno = 1;
                var f = 1;
                var ft = 1;
                var cud = 1;
                var pur = 1;
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!

                var yyyy = today.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }
                var today = mm + '/' + dd + '/' + yyyy;

                var final1;
                $.each(result, function (k, v) {
                    if (v.purpose === 'Pre Audit') {
                        final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chck' value='" + (ckslno++) + "' /></td>" +
                                "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                                "<td><input type='text' name='' class='painput fil" + (f++) + "' value='" + v.fileNumber + "' readonly/></td>" +
                                "<td><select name='fileType' class='fileType" + (ft++) + "' id='table_sele'>" +
                                "<option value='null'>select</option>" +
                                "<option value='lt'>LT</option>" +
                                "<option value='pt'>PT</option>" +
                                "</select></td>" +
                                "<td><input type='text' name='curDate' id='curDate' class='painput curDa" + (cud++) + "' value='" + today + "' /></td>" +
                                "<td><select name='purpose' class='purpose" + (pur++) + "' id='table_sele2'>" +
                                "<option value='null'>select</option>" +
                                "<option value='pp' selected>po preaudit</option>" +
                                "<option value='pa'>po amendment</option>" +
                                "<option value='ap'>amdt preaudit</option>" +
                                "</select></td>" +
                                "</tr>";
                    }
                });
                $("#append_files").html(final1);


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + " :" + errorThrown);

            }, complete: function (data) {

            }
        });
    }
    //******************************************************************
    //END: GENERATE PRE AUDIT FILES
    //****************************************************************** 
    
    //******************************************************************
    //START: SAVE PRE AUDIT FILES
    //******************************************************************
    
     $("#savePreAudit").click(function (){
         
          var val = [];
          var myData = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();          
            var obj = { 
                    fileNo: $(".fil"+val[i]).val(),
                    fileType: $(".fileType"+val[i]).val(),
                    receivedOn: $(".curDa"+val[i]).val(),
                    purpose: $(".purpose"+val[i]).val()
            };
            myData.push(obj);
          
        });
                        
            $.ajax({
            dataType: "json",
            url: "savePreAuditFr.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(myData),
            type: 'POST',
            success: function (data) {
                 var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);  
                    generateRows();
                }else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR);
            }, beforeSend: function () {
                return confirm("Are you sure you want to submit ?");
            }
        });
       });
       
       
    //******************************************************************
    //END: SAVE PRE AUDIT FILES
    //******************************************************************
    
    
      });
