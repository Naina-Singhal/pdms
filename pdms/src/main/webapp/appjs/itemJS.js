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
      
      $("#master").addClass("active");
      $("#masterul").css("display","block");
      $("#item").addClass("active");
      
        $("#updatediv").css("display", "none");
        $("#addItem").css("display", "block");
        
    //hide the error, success messages when load the page
    $(".display_msg_error_Ma").hide();
    $(".display_msg_success_Ma").hide();
    
    //**************************************************************************
    //***START: APPEND CATEGORY
    //**************************************************************************
    $.ajax({
        url: "getAllCategoryRe.htm",
        type: "POST",
        cache: false,
        async: true,        
        success: function (htmlH) {            
            var result = $.parseJSON(htmlH);
            $("#categoryID").empty();
            $("#categoryID").append("<option value='null'>select</option>");
            $.each(result, function (k, v) {
                //alert(v.sectionCode);			
                $("#categoryID").append("<option value='" + v.categoryID +
                        "'>(" + v.categoryCode + ")" + v.categoryName + "</option>");
            });
            $('#categoryID').selectpicker('refresh');
        }, complete: function (data) {
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error occured at generate category code");
        }
    });
    //**************************************************************************
    //***END: APPEND CATEGORY
    //**************************************************************************
    
    //**************************************************************************
    //***START: APPEND UNIT
    //**************************************************************************
     $.ajax({
        url: "getAllunitRe.htm",
        type: "POST",
        cache: false,
        async: false,        
        success: function (units) {
            //alert(units);
            var result = $.parseJSON(units);
            $("#unitID").empty();
            $("#unitID").append("<option value='null'>select</option>");
            $.each(result, function (k, v) {
                //alert(v.sectionCode);			
                $("#unitID").append("<option value='" + v.unitID +
                        "'>(" + v.unitCode + ")" + v.unitName + "</option>");
            });
            $('#unitID').selectpicker('refresh');
        }, complete: function (data) {
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("Error occured at generate unitID");
        }
    });
    
    //**************************************************************************
    //***END: APPEND UNIT
    //**************************************************************************
        function ggggg(){
    
    $.ajax({
        url: "getItemMasterRe.htm",
        type: "POST",
	cache: false,
	async: false,
        data: ({uid: "dummy"}),
        success: function(IBC){  
            //alert(IBC);
            var result = $.parseJSON(IBC);
            var final  = "<div class='table-responsive'><table class='table table-custom row-border hover order-column' id='basic-usage' style='width:100% !important'>"+ 
                         "<thead>"+ 
                         "<tr><th></th><th></th><th></th><th></th><th></th><th></th> <th>Buttons</th></tr></thead>"+
                         "<thead><tr id='filterrow'><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>"+                                            
                         "<tbody id='table_body'>";
            var slno = 1;    
            $.each(result, function(k, v) {
            final = final +"<tr class='gradeX'>"+
                           "<td>"+ (slno++) +"</td>"+
                           "<td>"+v.itemCode+"</td><td>"+v.itemName+"</td>"+
                           "<td>"+v.description+"</td>"+
                           "<td>"+v.categoryDTO.categoryName+"</td><td>"+v.unitDTO.unitName+"</td>"+                           
                           "<td><button type='button' class='btn btn-info btn-rounded btn-sm' "+
                           "id='ButBut' value=" +v.itemID + ">Edit </button></td> ";
                                            
                    });
            final = final + "</tbody></table></div>";                 
            $("#show_table").html(final);
            
            // Setup - add a text input to each footer cell
            $('#basic-usage thead  #filterrow th').not(":eq(6)").each( function () {
                var title = $(this).text();
                $(this).html( '<input type="text"  />' );
            } );
 
            // DataTable
            var table = $('#basic-usage').DataTable({
                
                autoWidth: false,
                    "columns": [
                        {title: "SL No", width: '8%'},
                        {title: "Item Code", width: '16%'},
                        {title: "Item Name", width: '16%'},
                        {title: "Description", width: '16%'},
                        {title: "Category Name", width: '14%'},
                        {title: "Unit name", width: '14%'},
                        {title: "Buttons", width: '16%'}, 
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
            
                  //This is for append data values to form when click on edit button
                $('#basic-usage').on('click', '#ButBut', function () {                    
                    $("#item_form")[0].reset();                   
                    close_message();
                    $.ajax({
                        url: "itemMasterReById.htm",
                        type: "POST",
                        cache: false,
                        async: false,
                        data: ({itemid_id: this.value}),
                        success: function (htmlH) {                            
                            var result = $.parseJSON(htmlH);
                            $.each(result, function (k, v) {                            
                            $("#encFieldValue").val(v.encFieldValue);
                            $("#itemName").val(v.itemName);
                            $("#itemCode").val(v.itemCode);                             
                            $("#categoryID").val(v.categoryDTO.categoryID);
                            $("#categoryID").selectpicker("refresh"); 
                            $("#currentStock").val(v.currentStock);
                            $("#unitID").val(v.unitDTO.unitID);
                            $("#unitID").selectpicker("refresh"); 
                            $("#description").val(v.description);
                            //$("#ibcBank").selectpicker("refresh"); 
                        });

                            $("#updatediv").css("display", "block");
                            $("#addItem").css("display", "none");
                            $(window).scrollTop(0);

                        }, error: function (jqXHR, textStatus, errorThrown) {
                            alert("Error :" + jqXHR.status + ", " + errorThrown);
                        }, complete: function (data) {

                        }
                    });

                });
            
        },complete: function (data) {
                   
   

        }
	}); 
}
    
    //***********************************************
    //Start : Initialize basic datatable
    //***********************************************
   
    $("#itemName").val("");
    $("#itemCode").val("");
    $("#categoryID").val("0");
    $("#description").val("");
    $("#currentStock").val("");
    $("#encFieldValue").val("");
    $("#unitID").val("0");
    
    //***********************************************
    //End : Initialize basic datatable
    //***********************************************
   
    $("#addItem").click(function () {
        if(fnValidateSaveItem())
        {
            document.addItemForm.action = "saveitem.htm";
            document.addItemForm.submit();
        }
    });
    
    $("#updateItem").click(function () {
        if(fnValidateSaveItem())
        {
            document.addItemForm.action = "updateitem.htm";
            document.addItemForm.submit();
        }
    });
   
    $("#erroritemName").html("");
    $("#erroritemCode").html("");
    $("#errorcategoryID").html("");
    $("#errorDupItem").html("");

    $("#cancelItem").click(function(){
        $("#itemName").val("");
        $("#eItemCode").val("");
        $("#itemCode").val("");         
        $("#categoryID").val("null");
        $("#unitID").val("null");
        $("#description").val("");
        $("#encFieldValue").val("");
        $("#currentStock").val("");
        $("#currentStock").val("0");
        $("#errorDupItem").html("");
        $("#updatediv").css("display", "none");
        $("#addItem").css("display", "block");
        close_message();     
    });
    
    $('#updateItemData').on('click', function() {        
        update_ItemData();
    });
    
    function update_ItemData() {        
        $("#errorDupItem").empty();
        $("#successDupItem").empty();
        if(fnValidateSaveItem())
        {
        close_message();
        var $items = $('#encFieldValue, #itemName, #itemCode, #categoryID, #currentStock, #unitID,#description');
               

        var obj = {};
        $items.each(function () {
            obj[this.id] = $(this).val();
        });

        var jsonObj = JSON.stringify([obj]);
        //alert(jsonObj);
        $.ajax({
            dataType: "json",
            url: "updateItemRecord.htm",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify([obj]),
            type: 'POST',
            async: false,
            success: function (data) { 
                //alert(data);
                var data = parseInt(data);
                if (data === 1) {
                    show_success_mes();
                    $("#successDupItem").append("<strong>Success! &nbsp</strong> Your data has been Successfully updated.");
                    $(window).scrollTop(0);
                    $("#item_form")[0].reset();
                    $("#categoryID").selectpicker("refresh");                            
                    $("#unitID").selectpicker("refresh"); 
                    $("#updatediv").css("display", "none");
                    $("#addItem").css("display", "block");                    
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
                $("#errorDupItem").append("<strong>Error! &nbsp</strong>There is a problem while updating Data.");
                $(window).scrollTop(0);
            }, beforeSend: function () {
                return confirm("Are you sure you want to update ?");
                //$("#basic-usage").remove();
            }, complete: function (data) {                
                //$('#basic-usage').load('ajax.html#basic-usage');
            }
        });
    }

    }   // end function 
    

    
   
                                        function bbbb(){
    
    	var table = $("#example").dataTable( {
        "processing": true,
        "serverSide": true,
        "sort": "position",
        //bStateSave variable you can use to save state on client cookies: set value "true" 
        "bStateSave": false,
        //Default: Page display length
        "iDisplayLength": 10,
        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
        "iDisplayStart": 0,
        "fnDrawCallback": function () {
            //Get page numer on client. Please note: number start from 0 So
            //for the first page you will see 0 second page 1 third page 2...
            //Un-comment below alert to see page number
        	//alert("Current page number: "+this.fnPagingInfo().iPage);    
        },         
        "sAjaxSource": "springPaginationDataTables.htm",
        "bSort":false,
        "bFilter": true,
        "order": [[0,"asc"],[1,"desc"]],
        "aoColumns": [
            { "mData": "itemCode" },
            { "mData": "itemName" },
            { "mData": "description" },
            { "mData": "categoryDTO.categoryName" },
            { "mData": "unitDTO.unitName" },
            { "mData": "itemID" }
             
        ]
    } );
}
    
   
 
            // DataTable
            var table1 = $('#basic-usage').DataTable({
                "processing": true,
                "serverSide": true,
                "sort": "position",
                //bStateSave variable you can use to save state on client cookies: set value "true" 
                "bStateSave": false,
                //Default: Page display length
                "iDisplayLength": 10,
                //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
                "iDisplayStart": 0,
                "lengthMenu": [10, 25, 50, 100],
                "fnDrawCallback": function () {
                    //Get page numer on client. Please note: number start from 0 So
                    //for the first page you will see 0 second page 1 third page 2...
                    //Un-comment below alert to see page number
        	//alert("Current page number: "+this.fnPagingInfo().iPage);    
                },         
                "sAjaxSource": "springPaginationDataTables.htm",
                "bSort":true,
                "bFilter": true,
                "order": [[0,"asc"],[1,"desc"]],
                autoWidth: false,
                    "columns": [
                        { "mData": "itemID" },
                        { "mData": "itemCode" },
                        { "mData": "itemName" },
                        { "mData": "description" },
                        { "mData": "categoryDTO.categoryName" },
                        { "mData": "unitDTO.unitName" },
                        {
                          "mData": null,
                            "bSortable": false,
                            "mRender": function (o) { return '<button type="button" class="btn btn-info btn-rounded btn-sm" id="editButton" value="' +o.itemID + '">Edit </button>'; }
                        }
                    ]
                     
                   
            });

    $('#basic-usage tbody').on('click', 'button', function () {

        var itemId = this.value;
        alert(itemId);
        $("#item_form")[0].reset();
        close_message();
        $.ajax({
            url: "itemMasterReById.htm",
            type: "POST",
            cache: false,
            async: false,
            data: ({itemid_id: itemId}),
            success: function (htmlH) {
                var result = $.parseJSON(htmlH);
                $.each(result, function (k, v) {
                    $("#encFieldValue").val(v.encFieldValue);
                    $("#itemName").val(v.itemName);
                    $("#itemCode").val(v.itemCode);
                    $("#categoryID").val(v.categoryDTO.categoryID);
                    $("#categoryID").selectpicker("refresh");
                    $("#currentStock").val(v.currentStock);
                    $("#unitID").val(v.unitDTO.unitID);
                    $("#unitID").selectpicker("refresh");
                    $("#description").val(v.description);
                    //$("#ibcBank").selectpicker("refresh"); 
                });

                $("#updatediv").css("display", "block");
                $("#addItem").css("display", "none");
                $(window).scrollTop(0);

            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("Error :" + jqXHR.status + ", " + errorThrown);
            }, complete: function (data) {

            }
        });
    });
 
            // Apply the search
            
    

}); //End document

    //Plug-in to fetch page data 
jQuery.fn.dataTableExt.oApi.fnPagingInfo = function (oSettings)
{alert("---");
    return {
        "iStart": oSettings._iDisplayStart,
        "iEnd": oSettings.fnDisplayEnd(),
        "iLength": oSettings._iDisplayLength,
        "iTotal": oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage": oSettings._iDisplayLength === -1 ?
                0 : Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
        "iTotalPages": oSettings._iDisplayLength === -1 ?
                0 : Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
    };
};








var vChk=0;
function fnCheckDuplicateItem(iCode)
{
    vChk=0;
    $.ajax({
        url: 'chkdupitem.htm',
        type: "GET",
        dataType: 'json',
        contentType : "application/json",
        mimeType: 'application/json',
        data: { eitd: iCode },
        async: false,
        success: function (retval) {
            if(retval > 0)
            {
                vChk = retval;
            }
        }
    });
}

var flagValidTotal = new Array();
var returnstring = false;
function fnValidateSaveItem()
{
    //var nameregx = /^[_()-\][+\w\s]*$/;
    var nameregx = /^[)-_(\+\w\s]*$/;
    var coderegx = /^[+\w\s]*$/;

    $("#erroritemName").html("");
    $("#erroritemCode").html("");
    $("#errorcategoryID").html("");
    $("#errorDupItem").html("");
    $("#errorunitID").html("");
    
    var itemname = $("#itemName").val();
    var itemcode = $("#itemCode").val();
    var category = $("#categoryID").val();
    var stock = $("#currentStock").val();
    var unit = $("#unitID").val();
    
    var eItemCode = $("#eItemCode").val();
    
    if (($.trim(unit) == '0'))
    {
        flagValidTotal.push(false);
        $("#errorunitID").html("Please select Item Unit");
    } else {
        flagValidTotal.push(true);
        $("#errorunitID").html("");
    }
    if (($.trim(stock) == ''))
    {
        flagValidTotal.push(false);
        $("#errorcurrentStock").html("Please enter Available Stock");
    } else {
        flagValidTotal.push(true);
        $("#errorcurrentStock").html("");
    }
    if (($.trim(itemname) == ''))
    {
        flagValidTotal.push(false);
        $("#erroritemName").html("Please enter Item Name");
    } else {
        if(nameregx.test($.trim(itemname)))
        {
            flagValidTotal.push(true);
            $("#erroritemName").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#erroritemName").html("Invalid Item Name accepted special characters  '_()[]-'");
        }
    }
    if (($.trim(itemcode) == ''))
    {
        flagValidTotal.push(false);
        $("#erroritemCode").html("Please enter Item Code");
    } else {
        if(coderegx.test($.trim(itemcode)))
        {
            flagValidTotal.push(true);
            $("#erroritemCode").html("");
        }
        else
        {
            flagValidTotal.push(false);
            $("#erroritemCode").html("Invalid Item Code. Item Code must be alphanumeric.");
        }
    }
    if (($.trim(category) == '0'))
    {
        flagValidTotal.push(false);
        $("#errorcategoryID").html("Please select Item Category");
    } else {
        flagValidTotal.push(true);
        $("#errorcategoryID").html("");
    }

//    if($.trim(itemcode).length > 0)
//    {
//        if($.trim(itemcode) !== $.trim(eItemCode))
//        {
//            fnCheckDuplicateItem(itemcode);
//            if (vChk > 0)
//            {
//                $("#errorDupItem").html("Item details with entered code already exists");
//                flagValidTotal.push(false);
//            } else
//            {
//                $("#errorDupItem").html("");
//                flagValidTotal.push(true);
//            }
//        }
//    }

    for (var i = 0; i < flagValidTotal.length; i++) {
        if (flagValidTotal[i] == false) {
            returnstring = flagValidTotal[i];
            flagValidTotal = new Array();
            break;
        } else {
            returnstring = true;
        }
    }
    return returnstring;
}
