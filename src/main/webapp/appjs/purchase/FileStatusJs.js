/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#navigation li a").removeClass("active");
    $("#purcmenu").css("display", "block");
    $("#statuscservsn").addClass("active");
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    //**************************************************************************
    //START: DATA FETCHING FROM INDENT_FORM TABLE BY FILE NO
    //**************************************************************************
    $("#fileNumber").keyup(function () {
        $("#errorFileNumber").empty();
        var fiNo = parseInt(this.value);        
        if (fiNo <= 0 || isNaN(fiNo) === true) {
            $("#errorFileNumber").append("Please enter File Number. ");
            //makeEmptyFunction();
        } else {
            generatePOEntryDe(fiNo);
            $.ajax({
                url: "getIndentFormIdByFiNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({filNumber: fiNo}),
                success: function (response) {                    
                    var inFoId = $.parseJSON(response);
                    if ($.isEmptyObject(inFoId)) {
                        $("#errorFileNumber").append("This number has no data. ");
                        //makeEmptyFunction();  
                        $("#group").val();
                    } else {
                         $.each(inFoId, function (k, v) {                            
                            $("#group").val(v.groupDTO.groupName);
                            $.ajax({
                                url: "getIndentFormDeByIndId.htm",
                                type: "POST",
                                cache: false,
                                async: false,
                                data: ({IndentFormId: v.indentFormID}),
                                success: function (indentFprm) {
                                    //alert(indentFprm);
                                    var indentRes = $.parseJSON(indentFprm);
                                    if ($.isEmptyObject(indentRes)) {
                                        $("#errorFileNumber").append("This number has no data. ");
                                    }else{
                                        $.each(indentRes, function (k, w) {                                            
                                            $("#indentNumber").val(w.indentNumber);
                                            $("#sectionCode").val(w.sectionObj.sectionCode);
                                            $("#indentDate").val(w.indentDate);
                                            $("#modeOfPurchase").val(w.mopObj.modeOfPurchase);
                                            $("#receivedDate").val(w.recevidedDate);
                                            $("#estimatedCost").val(w.estimatedCost);
                                            $("#description").val(w.briefDescription);                                            
                                            $("#dgmGroup").append("<option value='"+w.approvingAuthorityObj.designationCode+"' selected>"
                                                    +w.approvingAuthorityObj.designationName+"</option>");                                        
                                            $("#itemCode").append("<option value='"+w.categoryObj.categoryCode+"' selected>"
                                                    +w.categoryObj.categoryName+"</option>");
                                            $("#indenter").append("<option value='"+w.employeeProfileId+"' selected>"
                                                    +w.empProfileDTo.firstName+" - "+w.empProfileDTo.lastName+"</option>");
                                        });
                                    }
                                }, error: function (jqXHR, textStatus, errorThrown) {
                                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                                }
                            });
                        });
                        


                    }
                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }, beforeSend: function () {

                }, complete: function (data) {
                    //$('.selectpicker').selectpicker('refresh');
                }
            });
        }
    });
    //*************************************************************************
    //END: DATA FETCHING FROM INDENT_FORM TABLE BY FILE NO
    //**************************************************************************
    
    //*************************************************************************
    //START: DATA FETCHING FROM PO_ENTRY BY FILE NUMBER
    //**************************************************************************
     function generatePOEntryDe(fiNou){  
            $("#stic_Po_data").empty(); 
            $.ajax({
                url: "getPoEntryDeByFileNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({filNumber: fiNou}),
                success: function (response) {
                //alert(response);
                var res = $.parseJSON(response);
                if ($.isEmptyObject(res)) {
                    $("#stic_Po_data").append("This number has no data. ");
                    //makeEmptyFunction();                    
                } else {
                    var slNo = 1;
                    $("#stic_Po_data").empty();                    
                    $.each(res, function (k, m) {
                        $("#stic_Po_data").append("<tr>" +
                                "<td>" + (slNo++) + "</td><td>" + m.poNumber + "</td>" +
                                "<td>" + m.poDate + "</td><td>" + m.preparedDate + "</td>" +
                                "<td>" + m.venderName + "</td>"+
                                "<td>" + m.vendorObj.vendorName + ", Address: "+m.vendorObj.vendorAddress+",<br/> City: "+m.vendorObj.vendorCity+
                                ", Phone: "+m.vendorObj.vendorPhone+", Contact Person: "+m.vendorObj.contactPerson+"</td>" +
                                "<td>" + m.poValue + "</td><td>" + m.deliveryPeriod + "</td></tr>");
                    });


                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }, beforeSend: function () {

                }, complete: function (data) {
                   
                }
            });
        }
    //*************************************************************************
    //END: DATA FETCHING FROM PO_ENTRY BY FILE NUMBER
    //**************************************************************************

}); //End document

