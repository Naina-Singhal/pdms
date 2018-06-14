/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    
    $("#navigation li a").removeClass("active");
    $("#receiptul").css("display", "block");
    $("#rcivAuthori").addClass("active");
    hideshow();
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();

    function hideshow() {        
        $("#first_authorisation").show();
        $("#second_release").hide();
        $("#three_control").hide();
    }
    
    var authorUserid = $("#authorUserid").val();
    appendRequiNoReFrAuthorise(authorUserid);
    appendtAuthorsAuthor(authorUserid);
    appendPoNumber();
    generateIndenter();
    appendSection();
    $("#rcivSelect").on('change', function(){
          
          var rciv_auth = $(this).val();
          $("#auth_label").empty();
          if(rciv_auth === "select"){              
              $("#auth_label").append("<strong>Error! &nbsp</strong> Please select form page.");
              $("#first_authorisation").hide();
              $("#second_release").hide();
              $("#three_control").hide();
              
              $("#form_recordR").hide();
              $("#form_record").hide();
              $("#form_recordC").hide();
              
              $(".display_msg_error_Ma").hide();
              $(".display_msg_success_Ma").hide();
              
          }else if(rciv_auth === "RCIV_Authorisation"){
              //alert("your selecting"+rciv_auth);
              $("#first_authorisation").show();
              $("#second_release").hide();
              $("#three_control").hide();
              
              $("#form_recordR").hide();
              $("#form_recordC").hide();
              
              $(".display_msg_error_Ma").hide();
              $(".display_msg_success_Ma").hide();
              appendPoNumber();
              appendRequiNoReFrAuthorise();
              generateIndenter();
              appendSection();
          }else if(rciv_auth === "RCIV_Release"){
              //alert("your selecting"+rciv_auth);
              $("#first_authorisation").hide();
              $("#second_release").show();
              $("#three_control").hide();
              
              $("#form_record").hide();
              $("#form_recordC").hide();
              
              $(".display_msg_error_Ma").hide();
              $(".display_msg_success_Ma").hide();
              appendRequiNoReFrRelease();  
              appendtAuthorsR(authorUserid);
              appendPoNumberR();
              generateIndenterR();
              appendSectionR();
          }else if(rciv_auth === "RCIV_Control"){
              //alert("your selecting"+rciv_auth);
              $("#first_authorisation").hide();
              $("#second_release").hide();
              $("#three_control").show();
              
              $("#form_record").hide();
              $("#form_recordR").hide();
              
              $(".display_msg_error_Ma").hide();
              $(".display_msg_success_Ma").hide();
              ControlNumIncre();
              appendRequiNos();
              appendtAuthorsControl(authorUserid);
              appendPoNumberC();
              generateIndenterC();
              appendSectionC();
          }
      });
      
    
    //**************************************************************************
    //================================Authorisation=============================
    //**************************************************************************
    $("#form_record").hide();
    $("#first_authorisation").show();
    
    //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mit() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poNumber, #authoriStatus,#storeRequisition,#plant, #date,' +
                '#issueType, #issueTo, #disposal, #issueFromOSecNa, #requisitionNo,' +
                ' #requisitionDate, #controlDate, #indentorName,' +
                ' #section, #jobAllocation, #deliveryAt, #itemCovered, #AuthorisedYesNo,' +
                '#authorisedBy, #authorisedByDate, #remarks');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
        
        var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_pono = $("#poNumber").val();
            var cur_requiNo = $("#requisitionNo").val();
            //alert(cur_dbno);
            var itemObj = saveItemsAutho(cur_pono, cur_requiNo); 
            big = JSON.stringify({ authoDTO : parObj, authoItemsDTO : itemObj});            
        
            //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveRcivAuthorisition.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#authorisation_form")[0].reset();
                    $("#authori_form_sec")[0].reset(); 
                        $("#form_record").show();
                        $("#first_authorisation").hide();
                        show_record();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                }else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
                    $(window).scrollTop(0);
            },beforeSend:function(){
                return confirm("Are you sure you want to save ?");
            }
        });
        
        }
    //**************************************************************************    
    //END Save DD Number  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display DD Number record
    //**************************************************************************
    
    function show_record(){        
        
       $.ajax({
        url: "getRcivAuthorizationRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(Auth){            
            var result = $.parseJSON(Auth);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.jobAllocation+"</td><td>"+v.plant+"</td>"+  
                           "<td>"+v.date+"</td><td>"+v.requisitionNo+"</td>"+
                           "<td>"+v.section+"</td><td>"+v.deliveryAt+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" + v.rcivAuthoriID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(7)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "Job Allocation", width: '13%'},
                        {title: "Plant", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "Requisition NO", width: '13%'},
                        {title: "Section", width: '13%'},
                        {title: "Delivery At", width: '13%'},
                        {title: "Buttons", width: '12%'},
                    ],
                    'aoColumnDefs': [{
                            'bSortable': false,
                            'aTargets': [-1] /* 1st one, start by the right */
                    }]
            });
 
            // Apply the search
            $("#basic-usage thead input").on( 'keyup change', function () {
            table
                .column( $(this).parent().index()+':visible' )
                .search( this.value )
                .draw();
            });
            
            
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#authorisation_form")[0].reset();
                    $("#authori_form_sec")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getRcivAuthorisationReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({Authori_id: this.value}),
                        success: function (Authori) {                              
                            var result = $.parseJSON(Authori);
                            $.each(result, function (k, v) {                                
                                $("#rcivAuthoriID").val(v.rcivAuthoriID); 
                                $("#poNumber").val(v.poNumber); 
                                $('#poNumber').selectpicker('refresh');
                                $("#authoriStatus").val(v.authoriStatus);  
                                $("#storeRequisition").val(v.storeRequisition); 
                                $("#plant").val(v.plant); 
                                $("#date").val(v.date); 
                                $("#issueType").val(v.issueType); 
                                $("#issueTo").val(v.issueTo); 
                                
                                $("#disposal").val(v.disposal);
                                $("#issueFromOSecNa").val(v.issueFromOSecNa);
                                $("#requisitionNo").val(v.requisitionNo);  
                                $("#requisitionDate").val(v.requisitionDate);                                 
                                $("#controlDate").val(v.controlDate);                                 
                                $("#indentorName").val(v.indentorName);
                                $('#indentorName').selectpicker('refresh');
                                $('#requisitionNo').selectpicker('refresh');
                                $("#section").val(v.section);
                                $('#section').selectpicker('refresh');
                                $("#jobAllocation").val(v.jobAllocation);
                                $("#deliveryAt").val(v.deliveryAt);  
                                $("#itemCovered").val(v.itemCovered); 
                                $("#AuthorisedYesNo").val(v.AuthorisedYesNo); 
                                appendtAuthorsAuthor(authorUserid);     
                                $("#authorisedBy").append("<option value='" + v.authorisedBy + "' selected>" + v.authorisedBy + "</option>");
                                $("#authorisedByDate").val(v.authorisedByDate);
                                $("#remarks").val(v.remarks);
                                document.getElementById('remarks').readOnly = false;  
                                document.getElementById('deliveryAt').readOnly = false;
                                document.getElementById('itemCovered').readOnly = false;                                
                                document.getElementById('issueType').readOnly = false;
                                document.getElementById('disposal').readOnly = false;
                                document.getElementById('issueFromOSecNa').readOnly = false;
                                document.getElementById('requisitionDate').readOnly = false;                            
                                document.getElementById('jobAllocation').readOnly = false;
                            });
                            
                            $("#form_record").hide();
                            $("#first_authorisation").show();
                            $("#updateDiv").css("display", "block");
                            $("#saveDdNumber").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_itemsA").empty();
                            $("#tableA_form").hide();
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
   

        },error: function (jqXHR, textStatus, errorThrown) {
             alert("Error :" + jqXHR.status + ", " + errorThrown);   
        }
	}); 
     }
     
    //**************************************************************************    
    //END: DD Number  record 
    //**************************************************************************
    
    
    $('#saveDdNumber').on('click', function() {        
        sub_mit();
    });
    
    $('#updateDdNumber').on('click', function() {        
        update_dispatch();
    });
    //**************************************************************************    
    //START: DD Number  UPDATE 
    //**************************************************************************
    
    $("#cancelDdNumber").click(function () {
        close_message();
            $("#form_record").show();
            $("#first_authorisation").hide();
        $("#authorisation_form")[0].reset();
        $("#authori_form_sec")[0].reset();
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block");
    });
    
    
    function update_dispatch() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $items = $('#rcivAuthoriID, #poNumber, #authoriStatus,#storeRequisition,#plant, #date,' +
                '#issueType, #issueTo, #disposal, #issueFromOSecNa, #requisitionNo,' +
                ' #requisitionDate, #controlDate, #indentorName,' +
                ' #section, #jobAllocation, #deliveryAt, #itemCovered, #AuthorisedYesNo,' +
                '#authorisedBy, #authorisedByDate, #remarks');

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateRcivAuthorisation.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) {                
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#authorisation_form")[0].reset();
                    $("#authori_form_sec")[0].reset();
                        $("#form_record").show();
                        $("#first_authorisation").hide();
                        show_record();
                    $("#updateDiv").css("display", "none");
                    $("#saveDdNumber").css("display", "block");                    
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();                
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update Data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to update ?");
                //$("#basic-usage").remove();
            }, complete: function (data) {
                show_record();
                //$('#basic-usage').load('ajax.html#basic-usage');
            }
        });


    }   // end function
    //**************************************************************************
    //END: DD Number UPDATE
    //**************************************************************************
    $("#showRecord").on('click', function (){       
        $("#form_record").show();
        $("#first_authorisation").hide();
        show_record();
        close_message();
        $("#stic_itemsA").empty();
        $("#tableA_form").hide();
    });
    $("#showForm").on('click', function (){        
        $("#form_record").hide();
        $("#first_authorisation").show();
        close_message();
        $("#authorisation_form")[0].reset();
        $("#authori_form_sec")[0].reset();                        
        $("#updateDiv").css("display", "none");
        $("#saveDdNumber").css("display", "block"); 
        appendtAuthorsAuthor(authorUserid);
        appendPoNumber();
        appendSection();
        $("#stic_itemsA").empty();
        $("#tableA_form").show();
    });
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************

    var table = $('#basic-usage').DataTable();

        $('#basic-usage tbody')
        .on('mouseenter', 'td', function () {
            var colIdx = table.cell(this).index().column;

            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
        });
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 
    
    //******************************************************************
    //START: FETCHING MATERIAL REQUISITION IN RCIV RELEASE
    //******************************************************************
    
    function appendPoNumber() {
        $.ajax({
            url: "getCsrvPrepaRecord.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (htmlH) {
                //alert(htmlH);
                var result = $.parseJSON(htmlH);
                $("#poNumber").empty();
                $("#poNumber").append("<option value='null'>select</option>");
                $.each(result, function (k, v8) {
                    $("#poNumber").append("<option value='" + v8.poNumber + "'>" + v8.poNumber + "</option>");
                    $('#poNumber').selectpicker('refresh');
                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for poNumber:" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });

    }
    
        
    $("#poNumber").on('change', function () {
        $("#errorPoNumber").empty();
        var po_NoR = this.value
        if (po_NoR <= 0 || isNaN(po_NoR) === true) {
            $("#errorPoNumber").append("Please select PO number.");
        } else {
            generateRcivAuthoriTable(po_NoR);
            $.ajax({
                url: "getCsrvDeByPoNomber.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoNo_no: po_NoR}),
                success: function (poonoo) {
                    //alert(poonoo);
                    var resu = $.parseJSON(poonoo);
                    if ($.isEmptyObject(resu)) {
                        $("#errorPoNumber").append("This number has no data.");
                    } else {
                        $.each(resu, function (k, w) {                            
                            $("#remarks").val(w.remarks);                            
                            $("#indentorName").val(w.indentor);
                            $('#indentorName').selectpicker('refresh');
                            $("#section").val(w.section);    
                            $('#section').selectpicker('refresh');
                            $("#deliveryAt").val(w.deliveryAt);
                            $("#itemCovered").val(w.itemsCovered);                            
                            document.getElementById('remarks').readOnly = true;
                            document.getElementById('deliveryAt').readOnly = true;
                            document.getElementById('itemCovered').readOnly = true;                            
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    
    
    function appendRequiNoReFrAuthorise() {
        $("#errorRequiNoAut").empty();
        $.ajax({
            url: "getMaterialRequiRecord.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (author) {  
                //alert(author);
                var resultt = $.parseJSON(author);
                if ($.isEmptyObject(resultt)) {
                    $("#errorRequiNoAut").append("This field has no data.");
                } else {
                    $("#requisitionNo").empty();
                    $("#requisitionNo").append("<option value='null'>select</option>");
                    $.each(resultt, function (k, x) {
                        $("#requisitionNo").append("<option value=" + x.reqNumber + ">" + x.reqNumber + "</option>");
                        $('#requisitionNo').selectpicker('refresh');
                    });                    
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    
    
    
    $("#requisitionNo").on('change', function () {
        $("#errorRequiNoAut").empty();
        var requi_NoR = this.value
        if (requi_NoR <= 0 || isNaN(requi_NoR) === true) {
            $("#errorRequiNoAut").append("Please enter valid number.");
        } else {
            $.ajax({
                url: "getMaterialRquiReByRequiNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({Requisition_no: requi_NoR}),
                success: function (releaseR) {
                    //alert(controlC);
                    var resu = $.parseJSON(releaseR);
                    if ($.isEmptyObject(resu)) {
                        $("#errorRequiNoAut").append("This number has no data.");
                    } else {
                        $.each(resu, function (k, w) {
                            $("#issueType").val(w.issueType);
                            $("#disposal").val(w.disposalIssue);
                            $("#issueFromOSecNa").val(w.fromSection);
                            $("#requisitionDate").val(w.date);                            
                            $("#jobAllocation").val(w.jobAllocation);                            
                            document.getElementById('issueType').readOnly = false;
                            document.getElementById('disposal').readOnly = false;
                            document.getElementById('issueFromOSecNa').readOnly = false;
                            document.getElementById('requisitionDate').readOnly = true;                            
                            document.getElementById('jobAllocation').readOnly = false;                            
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    //******************************************************************
    //END:  FETCHING MATERIAL REQUISITION IN RCIV RELEASE
    //******************************************************************

    //******************************************************************
    //START:  APPEND INDENTER 
    //******************************************************************
    function generateIndenter() {
        
        $.ajax({
            url: "getUserProfileDa.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (data) {
                //alert(data);
                $("#indentorName").empty();
                var result = $.parseJSON(data);
                $("#indentorName").append("<option value='null'>select</option>");
                $.each(result, function (k, w) {                    
                  $("#indentorName").append("<option value='" + w.employeeProfileID + "'>("+ w.icNumber + ")" + w.firstName + "  " + w.lastName + "</option>");
                  $("#indentorName").selectpicker("refresh");  
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error while getting employee profile for indenter. "+textStatus);
            }

        });

    }
    
    //******************************************************************
    //END:  APPEND INDENTER 
    //******************************************************************
    
    //***********************************************
    //START: Getting section from section_master table
    //***********************************************
    function appendSection() {
        $.ajax({
            url: "getSection.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({uid: "dummy"}),
            success: function (htmlH) {
                var result = $.parseJSON(htmlH);
                $("#section").empty();
                $("#section").append("<option value='null'>select</option>");
                $.each(result, function (k, v) {
                    //alert(v.sectionCode);			
                    $("#section").append("<option value='" + v.sectionCode +
                            "'>(" + v.sectionCode + ")" + v.sectionName + "</option>");
                });

            }, complete: function (data) {
            }
        });
    }
    //***********************************************
    //END: Getting section from section_master table
    //***********************************************
    
    function appendtAuthorsAuthor(authUserid) {
        $.ajax({
            url: "getEmpDeByEmpId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({employee_id: authUserid}),
            success: function (rele) {
                var resul = $.parseJSON(rele);
                if ($.isEmptyObject(resul)) {
                    alert("There is no employee data");
                } else {
                    $("#authorisedBy").empty();
                    $("#authorisedBy").append("<option value='null'>select</option>");
                    $.each(resul, function (k, w) {
                        $("#authorisedBy").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")"
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#authorisedBy").append("<option class='editableA' value='other'>other</option> ");

                    });
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }

    var initialText = $('.editableA').val();
    $('.editOptionA').val(initialText);
    $('#authorisedBy').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editableA').text();
        $('.editOptionA').val('');
        if (selected == "editableA") {
            $('.editOptionA').show();
            $('.editOptionA').keyup(function () {
                var editText = $('.editOptionA').val();
                $('.editableA').val(editText);
                $('.editableA').html(editText);
            });
        } else {
            $('.editOptionA').hide();
        }
    });
    
    //******************************************************************
    //START:  GENERATE TABLE FOR RCIV AUTHORIZATION ITEMS
    //******************************************************************
    
    function generateRcivAuthoriTable(po_number) {        
        $("#stic_itemsA").empty();
        $.ajax({
            url: "getCsrvPreparItemsByPoNo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({poNumber_Id: po_number}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if($.isEmptyObject(result)){
                    $("#stic_itemsA").empty();
                    $("#stic_itemsA").append("This number has no data. ").css({"color":"#0000009c"});
                }else{
                var slno = 1;
                var ckslno = 1;
                
                var code = 1;
                var group = 1;               
                var store = 1;
                var item = 1;
                var qtyOrd = 1;
                var unitQty = 1;
                var lump = 1;
                var pf = 1;                
                var ace = 1;
                var rege = 1;
                var qtyOrdH = 1;
                var itemH = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+v.itemObj.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItemsA' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput store" + (code++) + "' value='"+v.storeCode+"' /></td>" +
                        "<td><input type='text' name='' class='painput groupCode" + (group++) + "' value='"+v.groupCode+"' /></td>" +
                        "<td><input type='text' name='' class='painput storeCardNo" + (store++) + "' value='' /></td>" +
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='item" + (item++) + "' value='"+v.item+"' /></td>" +
                        "<td><input type='text' name='' class='painput itemDes" + (unitQty++) + "' value='"+v.itemObj.description+"' /></td>"+
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unit" + (qtyOrd++) + "' value='"+v.unit+"' /></td>"+                        
                        "<td><input type='text' name='' class='painput requiredQty" + (lump++) + "' value='' /></td>" +
                        
                        "</tr>";
                });
                $("#stic_itemsA").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for authorization items table  ;"+textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveItemsAutho(PoNumber, requisitnNo){
          
          var val = [];
          var myDataObj = [];
        $('#chckItemsA:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    
                    poNumBer: PoNumber,  
                    requisitionNo: requisitnNo,
                    store: $(".store"+val[i]).val(),                    
                    groupCode: $(".groupCode"+val[i]).val(),
                    storeCardNo: $(".storeCardNo"+val[i]).val(),
                    item: $(".item"+val[i]).val(),
                    itemDes: $(".itemDes"+val[i]).val(),
                    unit: $(".unit"+val[i]).val(),
                    requiredQty: $(".requiredQty"+val[i]).val()                                       
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }

   
    //******************************************************************
    //END: GENERATE TABLE FOR RCIV AUTHORIZATION ITEMS
    //******************************************************************
    
    
    //******************************************************************
    //START: Item Covered TEMPLATE FOR RCIV AUTHORISATION
    //******************************************************************            
                $("#itemCovered").dblclick(function(){
                    $(".modal-bodyItem").empty();                    
                    var text_id;
                    $("#cstModalItem").modal("show");                    
                    $.ajax({
                        url: "getBreifDescriFrIndentForm.htm",
                        type: "POST",
                        cache: false,                                             
                        success: function(breif){  
                            //alert(breif);
                            var result = $.parseJSON(breif);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-bodyItem").append("<div class='ineer-model' id='"+v.indentFormID+"' >"
                                +" "+v.briefDescription+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#itemCovered").text('');                                   
                                    document.getElementById("itemCovered").value = tex;
                                    $('#cstModalItem').modal('toggle');
                                    var WhereToMove = $("#itemCovered").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#AuthorisedYesNo").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: Item Covered TEMPLATE FOR RCIV AUTHORISATION 
    //******************************************************************   
    
    
    
    //**************************************************************************    
    //@@@@@@@@@@@@@@@@@@@@@@@ Release @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //**************************************************************************    
    $("#form_recordR").hide();
    //$("#second_release").show();
    
   //$("#showForm").trigger("click");
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mitR() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $items = $('#poNumberR, #storeRequisitionR,#plantR,#dateR, #issueTypeR,' +
                '#issueToR, #disposalR, #issueFromOthSecNaR, #requisitionNumberR, #requisitionNoDateR,' +
                '#indentorNameR, #sectionR,' +
                '#jobAllocationR, #deliveryAtR, #itemCoveredR, #authorisedByR,' +
                '#authorisedByDateR, #authorisedRemarksR, #releasedYesNoR, #releasedByR,' +
                '#releasedByDateR, #releasedRemarksR');
                   
        var obj = {};
            $items.each(function() {
                obj[this.id] = $(this).val();
        });
            
        var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_ponoR = $("#poNumberR").val();
            var cur_requiR = $("#requisitionNumberR").val();
            //alert(cur_ponoR);
            var itemObj = saveItemsRelease(cur_ponoR, cur_requiR);
            big = JSON.stringify({ releaseDTO : parObj, releaseItemsDTO : itemObj});            
        
            //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveRcivReleaseData.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#release_form_sec")[0].reset();
                    $("#release_form")[0].reset();
                        $("#form_recordR").show();
                        $("#second_release").hide();
                        show_recordR();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                }else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
                    $(window).scrollTop(0);
            },beforeSend:function(){
                return confirm("Are you sure you want to save ?");
            }
        });
        
        }
    //**************************************************************************    
    //END Save DD Number  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display DD Number record
    //**************************************************************************
    
    function show_recordR(){        
        
       $.ajax({
        url: "getRcivReleaseRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(release){            
            var result = $.parseJSON(release);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usageR' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slnoR = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slnoR++) +"</td>"+
                           "<td>"+v.issueToR+"</td><td>"+v.plantR+"</td>"+  
                           "<td>"+v.dateR+"</td><td>"+v.disposalR+"</td>"+
                           "<td>"+v.requisitionNumberR+"</td><td>"+v.sectionR+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButButR' value=" + v.rcivReleaseID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_tableR").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usageR thead  #filterrow th').not(":eq(7)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usageR').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "Issue To", width: '13%'},
                        {title: "Plant", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "Disposal", width: '13%'},
                        {title: "Requisition No", width: '13%'},
                        {title: "Section", width: '13%'},
                        {title: "Buttons", width: '12%'},
                    ],
                    'aoColumnDefs': [{
                            'bSortable': false,
                            'aTargets': [-1] /* 1st one, start by the right */
                    }]
            });
 
            // Apply the search
            $("#basic-usageR thead input").on( 'keyup change', function () {
            table
                .column( $(this).parent().index()+':visible' )
                .search( this.value )
                .draw();
            });
            
            
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usageR').on('click', '#ButButR', function () {                    
                    $("#release_form")[0].reset();
                    $("#release_form_sec")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getRcivReleaseReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({ReleaseR_id: this.value}),
                        success: function (releas) {                              
                            var result = $.parseJSON(releas);
                            $.each(result, function (k, v) {                                
                                $("#rcivReleaseID").val(v.rcivReleaseID);  
                                $("#poNumberR").val(v.poNumberR);
                                $('#poNumberR').selectpicker('refresh');
                                $("#storeRequisitionR").val(v.storeRequisitionR);  
                                $("#plantR").val(v.plantR); 
                                $("#dateR").val(v.dateR); 
                                $("#issueTypeR").val(v.issueTypeR); 
                                $("#issueToR").val(v.issueToR); 
                                $("#disposalR").val(v.disposalR); 
                                $("#issueFromOthSecNaR").val(v.issueFromOthSecNaR);
                                $("#requisitionNumberR").val(v.requisitionNumberR);
                                $('#requisitionNumberR').selectpicker('refresh');
                                $("#requisitionNoDateR").val(v.requisitionNoDateR);                                 
                                $("#indentorNameR").val(v.indentorNameR); 
                                $('#indentorNameR').selectpicker('refresh');
                                $("#sectionR").val(v.sectionR); 
                                $('#sectionR').selectpicker('refresh');
                                $("#jobAllocationR").val(v.jobAllocationR); 
                                $("#deliveryAtR").val(v.deliveryAtR); 
                                $("#itemCoveredR").val(v.itemCoveredR);
                                appendtAuthorsR(authorUserid);                                                                
                                $("#authorisedByR").append("<option value='" + v.authorisedByR + "' selected>" + v.authorisedByR + "</option>"); 
                                $("#authorisedByDateR").val(v.authorisedByDateR);
                                $("#authorisedRemarksR").val(v.authorisedRemarksR);  
                                $("#releasedYesNoR").val(v.releasedYesNoR); 
                                //$("#releasedByR").val(v.releasedByR);                                
                                $("#releasedByR").append("<option value='" + v.releasedByR + "' selected>" + v.releasedByR + "</option>"); 
                                $("#releasedByDateR").val(v.releasedByDateR); 
                                $("#releasedRemarksR").val(v.releasedRemarksR);                                
                            });
                            
                            $("#form_recordR").hide();
                            $("#second_release").show();
                            $("#updateDivR").css("display", "block");
                            $("#saveDdNumberR").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_itemsR").empty();
                            $("#tableR_form").hide();
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
   

        },error: function (jqXHR, textStatus, errorThrown) {
             alert("Error :" + jqXHR.status + ", " + errorThrown);   
        }
	}); 
     }
     
    //**************************************************************************    
    //END: DD Number  record 
    //**************************************************************************
    
    
    $('#saveDdNumberR').on('click', function() {        
        sub_mitR();
    });
    
    $('#updateDdNumberR').on('click', function() {        
        update_dispatchR();
    });
    //**************************************************************************    
    //START: DD Number  UPDATE 
    //**************************************************************************
    
    $("#cancelDdNumberR").click(function () {
        close_message();
            $("#form_recordR").show();
            $("#second_release").hide();
        $("#release_form")[0].reset();
        $("#release_form_sec")[0].reset();
        $("#updateDivR").css("display", "none");
        $("#saveDdNumberR").css("display", "block");
    });
    
    
    function update_dispatchR() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $itemsR = $('#rcivReleaseID, #poNumberR, #storeRequisitionR,#plantR,#dateR, #issueTypeR,' +
                '#issueToR, #disposalR, #issueFromOthSecNaR, #requisitionNumberR, #requisitionNoDateR,' +
                '#indentorNameR, #sectionR,' +
                '#jobAllocationR, #deliveryAtR, #itemCoveredR, #authorisedByR,' +
                '#authorisedByDateR, #authorisedRemarksR, #releasedYesNoR, #releasedByR,' +
                '#releasedByDateR, #releasedRemarksR');

        var obj = {};
        $itemsR.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateRcivReleaseDe.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) {                
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#release_form")[0].reset();
                    $("#release_form_sec")[0].reset();
                        $("#form_recordR").show();
                        $("#second_release").hide();
                        show_recordR();
                    $("#updateDivR").css("display", "none");
                    $("#saveDdNumberR").css("display", "block");                    
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();                
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update Data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to update ?");
                //$("#basic-usage").remove();
            }, complete: function (data) {
                show_recordR();
                //$('#basic-usage').load('ajax.html#basic-usage');
            }
        });


    }   // end function
    //**************************************************************************
    //END: DD Number UPDATE
    //**************************************************************************
    $("#showRecordR").on('click', function (){       
        $("#form_recordR").show();
        $("#second_release").hide();
        show_recordR();
        close_message();
        $("#stic_itemsR").empty();
    });
    $("#showFormR").on('click', function (){        
        $("#form_recordR").hide();
        $("#second_release").show();
        close_message();
        $("#release_form")[0].reset();
        $("#release_form_sec")[0].reset();                      
        $("#updateDivR").css("display", "none");
        $("#saveDdNumberR").css("display", "block");
        appendRequiNoReFrRelease();
        appendtAuthorsR(authorUserid);
        appendPoNumberR();
        $("#stic_itemsR").empty();
        $("#tableR_form").show();
    });
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************

    var table = $('#basic-usageR').DataTable();

        $('#basic-usageR tbody')
        .on('mouseenter', 'td', function () {
            var colIdx = table.cell(this).index().column;

            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
        });
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 
    
    
    //******************************************************************
    //START:  APPEND INDENTER FOR RELEASE
    //******************************************************************
    function generateIndenterR() {
        
        $.ajax({
            url: "getUserProfileDa.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (data) {
                //alert(data);
                $("#indentorNameR").empty();
                var result = $.parseJSON(data);
                $("#indentorNameR").append("<option value='null'>select</option>");
                $.each(result, function (k, w) {                    
                  $("#indentorNameR").append("<option value='" + w.employeeProfileID + "'>("+ w.icNumber + ")" + w.firstName + "  " + w.lastName + "</option>");
                  $("#indentorNameR").selectpicker("refresh");  
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error while getting employee profile for indenter. "+textStatus);
            }

        });

    }
    
    //******************************************************************
    //END:  APPEND INDENTER 
    //******************************************************************
    
    //***********************************************
    //START: Getting section from section_master table
    //***********************************************
    function appendSectionR() {
        $.ajax({
            url: "getSection.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({uid: "dummy"}),
            success: function (htmlH) {
                var result = $.parseJSON(htmlH);
                $("#sectionR").empty();
                $("#sectionR").append("<option value='null'>select</option>");
                $.each(result, function (k, v) {
                    //alert(v.sectionCode);			
                    $("#sectionR").append("<option value='" + v.sectionCode +
                            "'>(" + v.sectionCode + ")" + v.sectionName + "</option>");
                });

            }, complete: function (data) {
            }
        });
    }
    //***********************************************
    //END: Getting section from section_master table
    //***********************************************
    
    //******************************************************************
    //START: FETCHING MATERIAL REQUISITION IN RCIV RELEASE AND PO NO
    //******************************************************************
    function appendPoNumberR() {
        $.ajax({
            url: "getCsrvPrepaRecord.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (htmlH) {
                //alert(htmlH);
                var result = $.parseJSON(htmlH);
                $("#poNumberR").empty();
                $("#poNumberR").append("<option value='null'>select</option>");
                $.each(result, function (k, v8) {
                    $("#poNumberR").append("<option value='" + v8.poNumber + "'>" + v8.poNumber + "</option>");
                    $('#poNumberR').selectpicker('refresh');
                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for poNumber release:" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });

    }
    
        
    $("#poNumberR").on('change', function () {
        $("#errorPoNumberR").empty();
        var po_NoRR = this.value
        if (po_NoRR <= 0 || isNaN(po_NoRR) === true) {
            $("#errorPoNumberR").append("Please select PO number.");
        } else {
            generateRcivReleaseTable(po_NoRR);
            $.ajax({
                url: "getCsrvDeByPoNomber.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoNo_no: po_NoRR}),
                success: function (poonoo) {
                    //alert(poonoo);
                    var res = $.parseJSON(poonoo);
                    if ($.isEmptyObject(res)) {
                        $("#errorPoNumberR").append("This number has no data.");
                    } else {
                        $.each(res, function (k, w) {                            
                            $("#releasedRemarksR").val(w.remarks);                            
                            $("#indentorNameR").val(w.indentor);
                            $('#indentorNameR').selectpicker('refresh');
                            $("#sectionR").val(w.section);    
                            $('#sectionR').selectpicker('refresh');
                            $("#deliveryAtR").val(w.deliveryAt);
                            $("#itemCoveredR").val(w.itemsCovered);                            
                            document.getElementById('releasedRemarksR').readOnly = true;
                            document.getElementById('deliveryAtR').readOnly = true;
                            document.getElementById('itemCoveredR').readOnly = true;                            
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error at PO number:" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    
    
    function appendRequiNoReFrRelease() {
        $("#errorRequiNoR").empty();
        $.ajax({
            url: "getMaterialRequiRecord.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (rele) {                
                var resul = $.parseJSON(rele);
                if ($.isEmptyObject(resul)) {
                    $("#errorRequiNoR").append("This field has no data.");
                } else {
                    $("#requisitionNumberR").empty();
                    $("#requisitionNumberR").append("<option value='null'>select</option>");
                    $.each(resul, function (k, v) {
                        $("#requisitionNumberR").append("<option value=" + v.reqNumber + ">" + v.reqNumber + "</option>");
                        $('#requisitionNumberR').selectpicker('refresh');
                    });                    
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    
    
    
    $("#requisitionNumberR").on('change', function () {
        $("#errorRequiNoR").empty();
        var requi_NoR = this.value
        if (requi_NoR <= 0 || isNaN(requi_NoR) === true) {
            $("#errorRequiNoR").append("Please enter valid number.");
        } else {
            $.ajax({
                url: "getMaterialRquiReByRequiNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({Requisition_no: requi_NoR}),
                success: function (releaseR) {
                    //alert(controlC);
                    var resu = $.parseJSON(releaseR);
                    if ($.isEmptyObject(resu)) {
                        $("#errorRequiNoR").append("This number has no data.");
                    } else {
                        $.each(resu, function (k, w) {
                            $("#issueTypeR").val(w.issueType);
                            $("#disposalR").val(w.disposalIssue);
                            $("#issueFromOthSecNaR").val(w.fromSection);
                            $("#requisitionNoDateR").val(w.date);
                            $("#jobAllocationR").val(w.jobAllocation);                            
                            document.getElementById('issueTypeR').readOnly = false;
                            document.getElementById('disposalR').readOnly = false;
                            document.getElementById('issueFromOthSecNaR').readOnly = false;
                            document.getElementById('requisitionNoDateR').readOnly = true;                            
                            document.getElementById('jobAllocationR').readOnly = false;                               
                            
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    //******************************************************************
    //END:  FETCHING MATERIAL REQUISITION IN RCIV RELEASE
    //******************************************************************
    
    function appendtAuthorsR(authUserid){    
    $.ajax({
            url: "getEmpDeByEmpId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({employee_id: authUserid}),
            success: function (rele) {                 
                var resul = $.parseJSON(rele);
                if ($.isEmptyObject(resul)) {
                    alert("There is no employee data");
                } else {
                    $("#authorisedByR").empty(); 
                    $("#authorisedByR").append("<option value='null'>select</option>");
                    $("#releasedByR").empty(); 
                    $("#releasedByR").append("<option value='null'>select</option>");
                    $.each(resul, function (k, w) {                        
                        $("#authorisedByR").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")" 
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#authorisedByR").append("<option class='editable' value='other'>other</option> ");
                        
                        $("#releasedByR").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")" 
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#releasedByR").append("<option class='editableRR' value='other'>other</option> ");
                    });                    
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
        }
    
    var initialText = $('.editable').val();
    $('.editOption').val(initialText);    
    $('#authorisedByR').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editable').text();
        $('.editOption').val('');
        if (selected == "editable") {
            $('.editOption').show();
            $('.editOption').keyup(function () {
                var editText = $('.editOption').val();
                $('.editable').val(editText);
                $('.editable').html(editText);
            });
        } else {
            $('.editOption').hide();
        }
    });
    
    var initialText = $('.editableRR').val();
    $('.editOptionRR').val(initialText);    
    $('#releasedByR').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editableRR').text();
        $('.editOptionRR').val('');
        if (selected == "editableRR") {
            $('.editOptionRR').show();
            $('.editOptionRR').keyup(function () {
                var editText = $('.editOptionRR').val();
                $('.editableRR').val(editText);
                $('.editableRR').html(editText);
            });
        } else {
            $('.editOptionRR').hide();
        }
    });


    //******************************************************************
    //START:  GENERATE TABLE FOR RCIV RELEASE ITEMS
    //******************************************************************
    
    function generateRcivReleaseTable(po_numberR) {        
        $("#stic_itemsR").empty();
        $.ajax({
            url: "getRcivReleaseItemsReByPo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({poNumber_IdR: po_numberR}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if($.isEmptyObject(result)){
                    $("#stic_itemsR").empty();
                    $("#stic_itemsR").append("This number has no data. ").css({"color":"#0000009c"});
                }else{
                var slno = 1;
                var ckslno = 1;
                
                var code = 1;
                var group = 1;               
                var store = 1;
                var item = 1;
                var qtyOrd = 1;
                var unitQty = 1;
                var lump = 1;
                var pf = 1;                
                var ace = 1;
                var rege = 1;
                var qtyOrdH = 1;
                var itemH = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+v.itemObj.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItemsR' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput storeR" + (code++) + "' value='"+v.store+"' /></td>" +
                        "<td><input type='text' name='' class='painput groupCodeR" + (group++) + "' value='"+v.groupCode+"' /></td>" +
                        "<td><input type='text' name='' class='painput storeCardNoR" + (store++) + "' value='"+v.storeCardNo+"' /></td>" +
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='itemR" + (item++) + "' value='"+v.item+"' /></td>" +
                        "<td><input type='text' name='' class='painput itemDesR" + (unitQty++) + "' value='"+v.itemObj.description+"' /></td>"+
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unitR" + (qtyOrd++) + "' value='"+v.unit+"' /></td>"+                        
                        "<td><input type='text' name='' class='painput requiredQtyR" + (lump++) + "' value='"+v.requiredQty+"' /></td>" +
                        
                        "</tr>";
                });
                $("#stic_itemsR").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for release items table  ;"+textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveItemsRelease(PoNumberR, requisitnNoR){
          
          var val = [];
          var myDataObj = [];
        $('#chckItemsR:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    
                    poNumBerR: PoNumberR,  
                    requisitionNoR: requisitnNoR,
                    storeR: $(".storeR"+val[i]).val(),                    
                    groupCodeR: $(".groupCodeR"+val[i]).val(),
                    storeCardNoR: $(".storeCardNoR"+val[i]).val(),
                    itemR: $(".itemR"+val[i]).val(),
                    itemDesR: $(".itemDesR"+val[i]).val(),
                    unitR: $(".unitR"+val[i]).val(),
                    requiredQtyR: $(".requiredQtyR"+val[i]).val()                                       
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }

   
    //******************************************************************
    //END: GENERATE TABLE FOR RCIV RELEASE ITEMS
    //******************************************************************
    
    //******************************************************************
    //START: Item Covered TEMPLATE FOR RCIV RELEASE
    //******************************************************************            
                $("#itemCoveredR").dblclick(function(){
                    $(".modal-bodyItemR").empty();                    
                    var text_id;
                    $("#cstModalItemR").modal("show");                    
                    $.ajax({
                        url: "getBreifDescriFrIndentForm.htm",
                        type: "POST",
                        cache: false,                                             
                        success: function(breif){  
                            //alert(breif);
                            var result = $.parseJSON(breif);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-bodyItemR").append("<div class='ineer-model' id='"+v.indentFormID+"' >"
                                +" "+v.briefDescription+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#itemCoveredR").text('');                                   
                                    document.getElementById("itemCoveredR").value = tex;
                                    $('#cstModalItemR').modal('toggle');
                                    var WhereToMove = $("#itemCoveredR").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#authorisedByR").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: Item Covered TEMPLATE FOR RCIV RELEASE
    //******************************************************************   
    
    
    //**************************************************************************
    //@@@@@@@@@@@@@@@@@@@@@@@@@   control   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //**************************************************************************
    $("#form_recordC").hide();
    //$("#three_control").show();
    
      
    //**************************************************************************
    //Start Save DD Number  in data base
    //**************************************************************************
    
    function sub_mitC() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        var $itemsC = $('#poNumberC, #authorisedStatusC,#storeRequisitionC,#plantC, #dateC,' +
                '#issueTypeC, #issueToC, #disposalC, #issueFrOtherSecNaC, #requisitionNumberC,' +
                ' #requisitionDateC, #controlNumberC, #controlDateC, #indentorNameC,' +
                ' #sectionC, #jobAllocationC, #deliveryAtC, #itemCoveredC, #authorisedByNoC,' +
                ' #authorisedByDateC, #authorisedRemarksC, #releasedByNoC, ' +
                '#releasedByDateC, #releasedRemarksC');
                   
        var obj = {};
            $itemsC.each(function() {
                obj[this.id] = $(this).val();
        });
            
          var jsonOb = JSON.stringify([obj]);        
            var parObj = JSON.parse(jsonOb); 
        
            var big = [];
            var cur_conNo = $("#controlNumberC").val();
            var cur_requiC = $("#requisitionNumberC").val();
            //alert(cur_poNoC+"--"+cur_requiC);
            var itemObj = saveItemsControl(cur_conNo, cur_requiC);
            big = JSON.stringify({ controlDTO : parObj, controlItemsDTO : itemObj});            
        
            //alert(big);
        $.ajax({
            dataType : "json",
            url : "saveRcivControlData.htm",
            contentType: 'application/json',
            mimeType: 'application/json',
            data : big,
            type : 'POST',
            success : function(data) {                 
                var data = parseInt(data);
                if(data === 1){                    
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully saved.");
                    $(window).scrollTop(0);
                    $("#control_form")[0].reset();
                    $("#control_form_sec")[0].reset();    
                        $("#form_recordC").show();
                        $("#three_control").hide();
                        show_recordC();                    
                } else if (data === -1) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else if (data === -5) {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> Problem may occured in tables or form which is filled by data.");
                    $(window).scrollTop(0);
                }else{                    
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong> There is a problem while saving data.");
                    $(window).scrollTop(0);
                }                
            },
            error : function(jqXHR,textStatus,errorThrown ){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while saving data.");
                    $(window).scrollTop(0);
            },beforeSend:function(){
                return confirm("Are you sure you want to save ?");
            }
        });
        
        }
    //**************************************************************************    
    //END Save DD Number  details in data base
    //**************************************************************************
    
    //**************************************************************************    
    //START: display DD Number record
    //**************************************************************************
    
    function show_recordC(){        
        
       $.ajax({
        url: "getRcivControlRecord.htm",
        type: "POST",
	cache: false,
	async: false,        
        success: function(cont){            
            var result = $.parseJSON(cont);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usageC' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.controlNumberC+"</td><td>"+v.plantC+"</td>"+  
                           "<td>"+v.dateC+"</td><td>"+v.disposalC+"</td>"+
                           "<td>"+v.requisitionNumberC+"</td><td>"+v.deliveryAtC+"</td>"+
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButButC' value=" + v.rcicControlID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_tableC").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usageC thead  #filterrow th').not(":eq(7)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usageC').DataTable({
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "Control NO", width: '13%'},
                        {title: "Plant", width: '13%'},
                        {title: "Date", width: '13%'},
                        {title: "Disposal", width: '13%'},
                        {title: "Requisition No", width: '13%'},
                        {title: "Delivery At", width: '13%'},
                        {title: "Buttons", width: '12%'},
                    ],
                    'aoColumnDefs': [{
                            'bSortable': false,
                            'aTargets': [-1] /* 1st one, start by the right */
                    }]
            });
 
            // Apply the search
            $("#basic-usageC thead input").on( 'keyup change', function () {
            table
                .column( $(this).parent().index()+':visible' )
                .search( this.value )
                .draw();
            });
            
            
        },complete: function (data) {
                //This is for append data values to form when click on edit button
                $('#basic-usageC').on('click', '#ButButC', function () {                    
                    $("#control_form")[0].reset();
                    $("#control_form_sec")[0].reset();
                    close_message();
                    $.ajax({
                        url: "getRcivControlReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({Control_id: this.value}),
                        success: function (htmlH) {                              
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                                
                                $("#rcicControlID").val(v.rcicControlID);   
                                $("#poNumberC").val(v.poNumberC);  
                                $('#poNumberC').selectpicker('refresh');
                                $("#authorisedStatusC").val(v.authorisedStatusC);  
                                $("#storeRequisitionC").val(v.storeRequisitionC); 
                                $("#plantC").val(v.plantC); 
                                $("#dateC").val(v.dateC); 
                                $("#issueTypeC").val(v.issueTypeC); 
                                $("#issueToC").val(v.issueToC); 
                                $("#disposalC").val(v.disposalC); 
                                $("#issueFrOtherSecNaC").val(v.issueFrOtherSecNaC); 
                                
                                $("#requisitionNumberC").val(v.requisitionNumberC);
                                $('#requisitionNumberC').selectpicker('refresh');
                                $("#requisitionDateC").val(v.requisitionDateC);                                
                                $("#controlNumberC").val(v.controlNumberC);  
                                $("#controlDateC").val(v.controlDateC);                                 
                                $("#indentorNameC").val(v.indentorNameC); 
                                $('#indentorNameC').selectpicker('refresh');
                                $("#sectionC").val(v.sectionC); 
                                $('#sectionC').selectpicker('refresh');
                                $("#jobAllocationC").val(v.jobAllocationC); 
                                $("#deliveryAtC").val(v.deliveryAtC); 
                                $("#itemCoveredC").val(v.itemCoveredC); 
                                appendtAuthorsControl(authorUserid);
                                $("#authorisedByNoC").append("<option value='" + v.authorisedByNoC + "' selected>" + v.authorisedByNoC + "</option>"); 
                                                             
                                $("#authorisedByDateC").val(v.authorisedByDateC);  
                                $("#authorisedRemarksC").val(v.authorisedRemarksC);                                 
                                $("#releasedByNoC").append("<option value='" + v.releasedByNoC + "' selected>" + v.releasedByNoC + "</option>"); 
                                $("#releasedByDateC").val(v.releasedByDateC); 
                                $("#releasedRemarksC").val(v.releasedRemarksC);                                
                            });
                            
                            $("#form_recordC").hide();
                            $("#three_control").show();
                            $("#updateDivC").css("display", "block");
                            $("#saveDdNumberC").css("display", "none");
                            $(window).scrollTop(0);
                            $("#stic_itemsC").empty();
                            $("#tableC_form").hide();
                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
   

        },error: function (jqXHR, textStatus, errorThrown) {
                 alert("Error :" + jqXHR.status + ", " + errorThrown);
        }
	}); 
     }
     
    //**************************************************************************    
    //END: DD Number  record 
    //**************************************************************************
    
    
    $('#saveDdNumberC').on('click', function() {        
        sub_mitC();
    });
    
    $('#updateDdNumberC').on('click', function() {        
        update_dispatchC();
    });
    //**************************************************************************    
    //START: DD Number  UPDATE 
    //**************************************************************************
    
    $("#cancelDdNumberC").click(function () {
        close_message();
            $("#form_recordC").show();
            $("#three_control").hide();
        $("#control_form")[0].reset();
        $("#control_form_sec")[0].reset();
        $("#updateDivC").css("display", "none");
        $("#saveDdNumberC").css("display", "block");
    });
    
    
    function update_dispatchC() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        close_message();
        var $itemsCU = $('#rcicControlID, #poNumberC, #authorisedStatusC,#storeRequisitionC,#plantC, #dateC,' +
                '#issueTypeC, #issueToC, #disposalC, #issueFrOtherSecNaC, #requisitionNumberC,' +
                ' #requisitionDateC, #controlNumberC, #controlDateC, #indentorNameC,' +
                ' #sectionC, #jobAllocationC, #deliveryAtC, #itemCoveredC, #authorisedByNoC,' +
                ' #authorisedByDateC, #authorisedRemarksC, #releasedByNoC,' +
                '#releasedByDateC, #releasedRemarksC');

        var obj = {};
        $itemsCU.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateRcivControlRe.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) {                
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#control_form")[0].reset();
                    $("#control_form_sec")[0].reset();
                        $("#form_recordC").show();
                        $("#three_control").hide();
                        show_record();
                    $("#updateDivC").css("display", "none");
                    $("#saveDdNumberC").css("display", "block");                    
                }else if(data === -1){
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Sorry! &nbsp</strong> That code or name already exist, Please try with different name or code.");
                    $(window).scrollTop(0);
                } else {
                    show_error_mes();
                    $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update data.");
                    $(window).scrollTop(0);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                show_error_mes();                
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while update Data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to update ?");
                //$("#basic-usage").remove();
            }, complete: function (data) {
                show_recordC();
                //$('#basic-usage').load('ajax.html#basic-usage');
            }
        });


    }   // end function
    //**************************************************************************
    //END: DD Number UPDATE
    //**************************************************************************
    $("#showRecordC").on('click', function (){       
        $("#form_recordC").show();
        $("#three_control").hide();
        show_recordC();
        close_message();
        $("#stic_itemsC").empty();
        
    });
    $("#showFormC").on('click', function (){        
        $("#form_recordC").hide();
        $("#three_control").show();
        close_message();        
        $("#control_form")[0].reset();
        $("#control_form_sec")[0].reset();
        ControlNumIncre();
        appendRequiNos();
        appendtAuthorsControl(authorUserid);
        $("#updateDivC").css("display", "none");
        $("#saveDdNumberC").css("display", "block");
        appendPoNumberC();
        $("#stic_itemsC").empty();
        $("#tableC_form").show();
    });
    //**************************************************************************
    //***START; Highlighting rows and columns OF DATATABLE**********************
    //**************************************************************************

    var table = $('#basic-usageC').DataTable();

        $('#basic-usageC tbody')
        .on('mouseenter', 'td', function () {
            var colIdx = table.cell(this).index().column;

            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
        });
    //**************************************************************************
    //***END; Highlighting rows and columns OF DATATABLE************************
    //************************************************************************** 

    //******************************************************************
    //START: DB NUMBER INCREAMENT
    //******************************************************************
    function ControlNumIncre() {
        $("#controlNumberC").val('');
        $.ajax({
            url: "generateControlNoIncrea.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (control) {                
                document.getElementById('controlNumberC').readOnly = true;
                $("#controlNumberC").val(parseInt(control));
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    //******************************************************************
    //END:  DB NUMBER INCREAMENT
    //******************************************************************
    
    
    //******************************************************************
    //START:  APPEND INDENTER FOR RELEASE
    //******************************************************************
    function generateIndenterC() {
        
        $.ajax({
            url: "getUserProfileDa.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (data) {
                //alert(data);
                $("#indentorNameC").empty();
                var result = $.parseJSON(data);
                $("#indentorNameC").append("<option value='null'>select</option>");
                $.each(result, function (k, w) {                    
                  $("#indentorNameC").append("<option value='" + w.employeeProfileID + "'>("+ w.icNumber + ")" + w.firstName + "  " + w.lastName + "</option>");
                  $("#indentorNameC").selectpicker("refresh");  
                });
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error while getting employee profile for indenter. "+textStatus);
            }

        });

    }
    
    //******************************************************************
    //END:  APPEND INDENTER 
    //******************************************************************
    
    //***********************************************
    //START: Getting section from section_master table
    //***********************************************
    function appendSectionC() {
        $.ajax({
            url: "getSection.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({uid: "dummy"}),
            success: function (htmlH) {
                var result = $.parseJSON(htmlH);
                $("#sectionC").empty();
                $("#sectionC").append("<option value='null'>select</option>");
                $.each(result, function (k, v) {
                    //alert(v.sectionCode);			
                    $("#sectionC").append("<option value='" + v.sectionCode +
                            "'>(" + v.sectionCode + ")" + v.sectionName + "</option>");
                });

            }, complete: function (data) {
            }
        });
    }
    //***********************************************
    //END: Getting section from section_master table
    //***********************************************
    
    //******************************************************************
    //START: FETCHING MATERIAL REQUISITION IN RCIV CONTROL
    //******************************************************************
        function appendPoNumberC() {
        $.ajax({
            url: "getCsrvPrepaRecord.htm",
            type: "POST",
            cache: false,
            async: false,            
            success: function (htmlH) {
                //alert(htmlH);
                var result = $.parseJSON(htmlH);
                $("#poNumberC").empty();
                $("#poNumberC").append("<option value='null'>select</option>");
                $.each(result, function (k, v8) {
                    $("#poNumberC").append("<option value='" + v8.poNumber + "'>" + v8.poNumber + "</option>");
                    $('#poNumberC').selectpicker('refresh');
                });

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for poNumber control:" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {
            }
        });

    }
    
        
    $("#poNumberC").on('change', function () {
        $("#errorPoNumberC").empty();
        var po_NoC = this.value
        if (po_NoC <= 0 || isNaN(po_NoC) === true) {
            $("#errorPoNumberC").append("Please select PO number.");
        } else {
            generateRcivControlTable(po_NoC);
            $.ajax({
                url: "getCsrvDeByPoNomber.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({PoNo_no: po_NoC}),
                success: function (poonooo) {
                    //alert(poonoo);
                    var rest = $.parseJSON(poonooo);
                    if ($.isEmptyObject(rest)) {
                        $("#errorPoNumberC").append("This number has no data.");
                    } else {
                        $.each(rest, function (k, w) {                            
                            $("#authorisedRemarksC").val(w.remarks);                            
                            $("#indentorNameC").val(w.indentor);
                            $('#indentorNameC').selectpicker('refresh');
                            $("#sectionC").val(w.section);    
                            $('#sectionC').selectpicker('refresh');
                            $("#deliveryAtC").val(w.deliveryAt);
                            $("#itemCoveredC").val(w.itemsCovered);                            
                            document.getElementById('authorisedRemarksC').readOnly = true;
                            document.getElementById('deliveryAtC').readOnly = true;
                            document.getElementById('itemCoveredC').readOnly = true;                            
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error at PO number:" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    
    
    function appendRequiNos() {
        $("#errorRequiNo").empty();        
        $.ajax({
            url: "getMaterialRequiRecord.htm",
            type: "POST",
            cache: false,
            async: false,
            success: function (control) {                
                var result = $.parseJSON(control);
                if ($.isEmptyObject(result)) {
                    $("#errorRequiNo").append("This field has no data.");
                } else {
                    $("#requisitionNumberC").empty();
                    $("#requisitionNumberC").append("<option value='null'>select</option>");
                    $.each(result, function (k, v) {
                        $("#requisitionNumberC").append("<option value=" + v.reqNumber + ">" + v.reqNumber + "</option>");
                        $('#requisitionNumberC').selectpicker('refresh');
                    });                    
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
    }
    
    
    
    $("#requisitionNumberC").on('change', function () {
        $("#errorRequiNo").empty();
        var requi_No = this.value
        if (requi_No <= 0 || isNaN(requi_No) === true) {
            $("#errorRequiNo").append("Please enter valid number.");
        } else {
            $.ajax({
                url: "getMaterialRquiReByRequiNo.htm",
                type: "POST",
                cache: false,
                async: false,
                data: ({Requisition_no: requi_No}),
                success: function (controlC) {
                    //alert(controlC);
                    var result = $.parseJSON(controlC);
                    if ($.isEmptyObject(result)) {
                        $("#errorRequiNo").append("This number has no data.");
                    } else {
                        $.each(result, function (k, w) {
                            $("#issueTypeC").val(w.issueType);
                            $("#disposalC").val(w.disposalIssue);
                            $("#issueFrOtherSecNaC").val(w.fromSection);
                            $("#requisitionDateC").val(w.date);                            
                            $("#jobAllocationC").val(w.jobAllocation);                            
                            document.getElementById('issueTypeC').readOnly = false;
                            document.getElementById('disposalC').readOnly = false;
                            document.getElementById('issueFrOtherSecNaC').readOnly = false;
                            document.getElementById('requisitionDateC').readOnly = true;                            
                            document.getElementById('jobAllocationC').readOnly = false;                                                     
                        });
                    }


                }, error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error :" + jqXHR.status + ", " + errorThrown);
                }
            });
        }
    });
    //******************************************************************
    //END:  FETCHING MATERIAL REQUISITION IN RCIV CONTROL
    //******************************************************************
    
    
    function appendtAuthorsControl(authUserid){    
    $.ajax({
            url: "getEmpDeByEmpId.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({employee_id: authUserid}),
            success: function (rele) {                 
                var resul = $.parseJSON(rele);
                if ($.isEmptyObject(resul)) {
                    alert("There is no employee data");
                } else {
                    $("#authorisedByNoC").empty(); 
                    $("#authorisedByNoC").append("<option value='null'>select</option>");
                    $("#releasedByNoC").empty(); 
                    $("#releasedByNoC").append("<option value='null'>select</option>");
                    $.each(resul, function (k, w) {                        
                        $("#authorisedByNoC").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")" 
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#authorisedByNoC").append("<option class='editableC' value='other'>other</option> ");
                        
                        $("#releasedByNoC").append("<option value='" + w.icNumber + "' selected>(" + w.icNumber + ")" 
                                + w.firstName + "  " + w.lastName + "</option>");
                        $("#releasedByNoC").append("<option class='editableCC' value='other'>other</option> ");
                    });                    
                }


            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }
        });
        }
    
        var initialText = $('.editableC').val();
    $('.editOptionC').val(initialText);    
    $('#authorisedByNoC').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editableC').text();
        $('.editOptionC').val('');
        if (selected == "editableC") {
            $('.editOptionC').show();
            $('.editOptionC').keyup(function () {
                var editText = $('.editOptionC').val();
                $('.editableC').val(editText);
                $('.editableC').html(editText);
            });
        } else {
            $('.editOptionC').hide();
        }
    });
    
    var initialText = $('.editableCC').val();
    $('.editOptionCC').val(initialText);    
    $('#releasedByNoC').change(function () {
        var selected = $('option:selected', this).attr('class');
        var optionText = $('.editableCC').text();
        $('.editOptionCC').val('');
        if (selected == "editableCC") {
            $('.editOptionCC').show();
            $('.editOptionCC').keyup(function () {
                var editText = $('.editOptionCC').val();
                $('.editableCC').val(editText);
                $('.editableCC').html(editText);
            });
        } else {
            $('.editOptionCC').hide();
        }
    });
    
    
    
    //******************************************************************
    //START:  GENERATE TABLE FOR RCIV CONTROL
    //******************************************************************
    

    function generateRcivControlTable(po_numberC) {        
        $("#stic_itemsC").empty();
        $.ajax({
            url: "getRcivControlItemsReByPo.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({poNumber_IdC: po_numberC}),
            success: function (details) {
                //alert(details);
                var result = $.parseJSON(details);
                if($.isEmptyObject(result)){
                    $("#stic_itemsC").empty();
                    $("#stic_itemsC").append("This number has no data. ").css({"color":"#0000009c"});
                }else{
                var slno = 1;
                var ckslno = 1;
                
                var code = 1;
                var group = 1;               
                var store = 1;
                var item = 1;
                var qtyOrd = 1;
                var unitQty = 1;
                var lump = 1;
                var pf = 1;                
                var ace = 1;
                var rege = 1;
                var qtyOrdH = 1;
                var itemH = 1;
                var final1;
                $.each(result, function (k, v) { 
                    //alert("item : "+v.itemObj.itemName);
                    final1 = final1 + "<tr><td><input type='checkbox' name='selector[]' class='' id='chckItemsC' value='" + (ckslno++) + "' /></td>" +
                        "<td><input type='text' name='' class='painput' value='" + (slno++) + "' readonly/></td>" +
                        "<td><input type='text' name='' class='painput storeC" + (code++) + "' value='"+v.storeR+"' /></td>" +
                        "<td><input type='text' name='' class='painput groupCodeC" + (group++) + "' value='"+v.groupCodeR+"' /></td>" +
                        "<td><input type='text' name='' class='painput storeCardNoC" + (store++) + "' value='"+v.storeCardNoR+"' /></td>" +
                        "<td><textarea name='' class='painput itemH" + (itemH++) + "'>"+v.itemObj.itemName+"</textarea>"+
                            "<input type='hidden' name='' class='itemC" + (item++) + "' value='"+v.itemR+"' /></td>" +
                        "<td><input type='text' name='' class='painput itemDesC" + (unitQty++) + "' value='"+v.itemObj.description+"' /></td>"+
                        "<td><input type='text' name='' class='painput unitH" + (qtyOrdH++) + "' value='"+v.unitObj.unitName+"' />"+
                            "<input type='hidden' name='' class='unitC" + (qtyOrd++) + "' value='"+v.unitR+"' /></td>"+                        
                        "<td><input type='text' name='' class='painput requiredQtyC" + (lump++) + "' value='"+v.requiredQtyR+"' /></td>" +
                        
                        "</tr>";
                });
                $("#stic_itemsC").html(final1).css({"color":"#0000009c"});

            }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error for CONTROL items table  ;"+textStatus + " :" + errorThrown);

            }, complete: function (data) {
               
            }, beforeSend: function () {
                //return confirm("Are you sure you want to submit ?");

            }
        });
    }   //end function

    function saveItemsControl(ConNumberC, requisitnNoC){
          
          var val = [];
          var myDataObj = [];
        $('#chckItemsC:checked').each(function(i){
          val[i] = $(this).val();           
            var obj = { 
                    
                    conNumberC: ConNumberC,  
                    requisitionNoC: requisitnNoC,
                    storeC: $(".storeC"+val[i]).val(),                    
                    groupCodeC: $(".groupCodeC"+val[i]).val(),
                    storeCardNoC: $(".storeCardNoC"+val[i]).val(),
                    itemC: $(".itemC"+val[i]).val(),
                    itemDesC: $(".itemDesC"+val[i]).val(),
                    unitC: $(".unitC"+val[i]).val(),
                    requiredQtyC: $(".requiredQtyC"+val[i]).val()                                       
            };
            myDataObj.push(obj);
            
        });
            
            return  myDataObj;
    }


   
    //******************************************************************
    //END: GENERATE TABLE FOR RCIV CONTROL
    //******************************************************************
    
    
    //******************************************************************
    //START: Item Covered TEMPLATE FOR CONTROL
    //******************************************************************            
                $("#itemCoveredC").dblclick(function(){
                    $(".modal-bodyItemC").empty();                    
                    var text_id;
                    $("#cstModalItemC").modal("show");                    
                    $.ajax({
                        url: "getBreifDescriFrIndentForm.htm",
                        type: "POST",
                        cache: false,                                             
                        success: function(breif){  
                            //alert(breif);
                            var result = $.parseJSON(breif);			
                            $.each(result, function(k, v) {                            			
                                $(".modal-bodyItemC").append("<div class='ineer-model' id='"+v.indentFormID+"' >"
                                +" "+v.briefDescription+"</div></br>");
                            });	
                            
                             var div = $(".ineer-model");
                                div.click(function()
                                {
                                    text_id = this.id;                                    
                                    var tex = $("#"+text_id).text();                                   
                                    $("#itemCoveredC").text('');                                   
                                    document.getElementById("itemCoveredC").value = tex;
                                    $('#cstModalItemC').modal('toggle');
                                    var WhereToMove = $("#itemCoveredC").position().top;
                                    $("html,body").animate({scrollTop: WhereToMove }, 1000);
                                    $("#authorisedByNoC").focus();
                                });										
                        },complete: function (data) {
                        }
                    });
                });
    //******************************************************************
    //END: Item Covered TEMPLATE FOR CONTROL
    //******************************************************************   
    
    
    
    }); //End document


