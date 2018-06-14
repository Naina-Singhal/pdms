/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function () {



    $("#navigation li a").removeClass("active");
    $("#uplameexul").css("display", "block");
    $("#itemexac").addClass("active");


    //hide the error, success messages when load the page
    $(".display_msg_error").hide();
    $(".display_msg_success").hide();

    //**************************************************************************
    //****START: UPLOAD EXCEL XLS (2003) FPRMAT FILE ***************************
    //**************************************************************************
    var size = 0;
    $("#uploadF").change(function ()
    {
        $("#show_file_msg").empty();
        $("#show_file_msg_gr").empty();
        var iSize = ($("#uploadF")[0].files[0].size / 1024);
        var exte = $("#uploadF").val().split('.').pop().toLowerCase();
        //alert(exte);
        var iSizeGb = (Math.round(((iSize / 1024) / 1024) * 100) / 100);
        var iSizeMb = (Math.round((iSize / 1024) * 100) / 100);
        var iSizeKb = (Math.round(iSize * 100) / 100);
        //alert(iSizeGb+"--"+iSizeMb+"--"+iSizeKb);
        size = iSizeMb;
        if (size > 5.0) {
            $("#show_file_msg_gr").append("File size: " + size + "</br>").css({"color": "red"});
        } else {
            $("#show_file_msg_gr").append("File size: " + size + "</br>").css({"color": "green"});
        }
    });



    $("#uploadLoading").click(function () {
        //alert(size);
        $("#show_file_msg").empty();
        $("#show_file_msg_sc").empty();
        var exte = $("#uploadF").val().split('.').pop().toLowerCase();
        if (exte === null || exte === "") {
            $("#show_file_msg").append("Please select file").css({"color": "red"});
            return  false;
        } else if (exte === "xls") {
            if (size > 5.0) {
                $("#show_file_msg").append("File size should be less than 5Mb.").css({"color": "red"});
                return false;
            } else {
                //$("#show_file_msg").append("File size: "+size).css({"color":"green"});
                if (confirm('Are you sure you want to submit ?')) {

                    event.preventDefault();
                    // Get form
                    var form = $('#form_excel')[0];
                    // Create an FormData object
                    var data = new FormData(form);
                    $.ajax({
                        type: "POST",
                        enctype: 'multipart/form-data',
                        url: "uploadaaa.htm",
                        data: data,
                        processData: false,
                        contentType: false,
                        cache: false,
                        timeout: 600000,
                        success: function (data) {
                            $("#show_file_msg_gr").empty();
                            var result = parseInt(data);
                            if (result === 1) {
                                $("#show_file_msg_sc").append("Your excel file has been successfully uploaded.").css({"color": "green"});
                            } else {
                                $("#show_file_msg_sc").append("A problem has been occurred while upload your excel file.").css({"color": "red"});
                            }
                        },
                        error: function (e) {

                        }, beforeSend: function () {
                            $.loadingBlockShow({
                                imgPath: 'appjs/loader/img/default.svg',
                                text: ' Loading ...',
                                style: {
                                    position: 'fixed',
                                    width: '100%',
                                    height: '100%',
                                    background: 'rgba(0, 0, 0, .8)',
                                    left: 0,
                                    top: 0,
                                    zIndex: 10000
                                }
                            });
                        }, complete: function (data) {
                            size = 0;
                            setTimeout($.loadingBlockHide, 1000);
                            $("#form_excel")[0].reset();
                        }
                    });


                    return  true;
                } else {
                    return false;
                }

            }


        } else {
            $("#show_file_msg").append("Error:  Accept only xls format.").css({"color": "red"});
            return  false;
        }

    });
    //**************************************************************************
    //****END: UPLOAD EXCEL XLS (2003) FPRMAT FILE *****************************
    //**************************************************************************

    //**************************************************************************
    //****START: UPLOAD EXCEL XLSX (2007) FPRMAT FILE **************************
    //**************************************************************************
    var sizeXlsx = 0;
    $("#uploadXlsx").change(function ()
    {
        $("#show_file_msg1").empty();
        $("#show_file_msg_gr1").empty();
        var iSize1 = ($("#uploadXlsx")[0].files[0].size / 1024);
        var exte1 = $("#uploadXlsx").val().split('.').pop().toLowerCase();
       // alert(exte1+"--"+iSize1);
        var iSizeGb1 = (Math.round(((iSize1 / 1024) / 1024) * 100) / 100);
        var iSizeMb1 = (Math.round((iSize1 / 1024) * 100) / 100);
        var iSizeKb1 = (Math.round(iSize1 * 100) / 100);
        //alert(iSizeGb+"--"+iSizeMb+"--"+iSizeKb);
        sizeXlsx = iSizeMb1;
        if (sizeXlsx > 5.0) {
            $("#show_file_msg_gr1").append("File size: " + sizeXlsx + "</br>").css({"color": "red"});
        } else {
            $("#show_file_msg_gr1").append("File size: " + sizeXlsx + "</br>").css({"color": "green"});
        }
    });
    
    $("#upload_save_xlsx").click(function () {
        //alert(size);
        $("#show_file_msg1").empty();
        $("#show_file_msg_sc1").empty();
        var exte = $("#uploadXlsx").val().split('.').pop().toLowerCase();
        //alert(exte);
        if (exte === null || exte === "") {
            $("#show_file_msg1").append("Please select file").css({"color": "red"});
            return  false;
        } else if (exte === "xlsx") {
            if (sizeXlsx > 5.0) {
                $("#show_file_msg1").append("File size should be less than 5Mb.").css({"color": "red"});
                return false;
            } else {
                //$("#show_file_msg").append("File size: "+size).css({"color":"green"});
                if (confirm('Are you sure you want to submit ?')) {

                    event.preventDefault();
                    // Get form
                    var form1 = $('#form_xlsx')[0];
                    // Create an FormData object
                    var data1 = new FormData(form1);
                    $.ajax({
                        type: "POST",
                        enctype: 'multipart/form-data',
                        url: "uploadXlsx.htm",
                        data: data1,
                        processData: false,
                        contentType: false,
                        cache: false,
                        timeout: 600000,
                        success: function (data) {
                            $("#show_file_msg_gr1").empty();
                            var result = parseInt(data);
                            if (result === 1) {
                                $("#show_file_msg_sc1").append("Your excel file has been successfully uploaded.").css({"color": "green"});
                            } else {
                                $("#show_file_msg_sc1").append("A problem has been occurred while upload your excel file.").css({"color": "red"});
                            }
                        },
                        error: function (e) {

                        }, beforeSend: function () {
                            $.loadingBlockShow({
                                imgPath: 'appjs/loader/img/default.svg',
                                text: ' Loading ...',
                                style: {
                                    position: 'fixed',
                                    width: '100%',
                                    height: '100%',
                                    background: 'rgba(0, 0, 0, .8)',
                                    left: 0,
                                    top: 0,
                                    zIndex: 10000
                                }
                            });
                        }, complete: function (data) {
                            sizeXlsx = 0;
                            setTimeout($.loadingBlockHide, 1000);
                            $("#form_xlsx")[0].reset();
                        }
                    });


                    return  true;
                } else {
                    return false;
                }

            }


        } else {
            $("#show_file_msg1").append("Error:  Accept only xlsx(2007) format.").css({"color": "red"});
            return  false;
        }

    });
    //**************************************************************************
    //****END: UPLOAD EXCEL XLSX (2007) FPRMAT FILE ****************************
    //**************************************************************************
});
