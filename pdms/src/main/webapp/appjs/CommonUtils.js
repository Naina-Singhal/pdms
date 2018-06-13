/*
 *Java Script file for some of the Common validations that are needed for Configuration
 *
 *1. limiter - Textarea and  Textbox Content restriction.
 *2. checkEmail - Email Validation with Regular Expression
 *3. alphaOnly - onkey press accept only alphabets
 *4. numbersonly - accepts numbers only
 *5. checkURL - Check Valid URL or not
 *6. checkCapital - checking for capital letters only
 *7. checkIsMobileNumber - Checking is mobile number or not (INDIAN STYLE)
 *8. checkIsPhoneNumber - Checking is phone number valid or not (INDIAN STYLE)
 *9. isSpecialCharacter - Special Characters are not allowed
 **/
//
//
//1. Text Area Restriction with Counter

function limiter(textLimit, maxLength, elementName, spanId) {
    var count = maxLength;   //Example: var count = "175";
    var tex = textLimit.value;
    var len = tex.length;
    if (len > count) {
        tex = tex.substring(0, count);
        textLimit.value = tex;
        $("#" + spanId).html("<font color='red'>Max length of " + elementName + "  is reached</font>");
        return false;
    }
    $("#" + spanId).html("");
    //document.view.limit.value = count-len;
}

function storeSelection(field) {
    if (document.all) {
        field.selected = true;
        field.selectedLength =
                field.createTextRange ?
                document.view.createRange().text.length : 1;
    }
}
//Ends here
//Usage -->
//<onkeyup="limiter(this,100)" onpaste="javascript: return false;" onselect="storeSelection(this)" >
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Email Validation

function checkEmail(emailField)
{

    var tomatch = /(\w+([\.-]?\w+)*@\w+([\.-]?\w+)*\.((com|net|org|edu|in|gov|info|co)))/i
    if (!tomatch.test(emailField.value))
    {
        window.alert("Please Enter Valid Email Id");
        emailField.value = "";
        emailField.focus();
        return true;
    } else {
        return false;
    }
}
//Usage -->  
//<onchange="checkEmail(this)">
////////////////////////////////////////////////////////////////////////////////////////////////////////////

//3.onkey press accept only alphabets 

function alphaOnly(eventRef)
{
    var keyStroke = (eventRef.which) ? eventRef.which : (window.event) ? window.event.keyCode : -1;
    var returnValue = false;
    //alert(keyStroke);
    if (((keyStroke >= 65) && (keyStroke <= 90)) ||
            ((keyStroke >= 97) && (keyStroke <= 122)) || (keyStroke == 8) || (keyStroke == -1))
        returnValue = true;

    if (navigator.appName.indexOf('Microsoft') != -1)
        window.event.returnValue = returnValue;

    return returnValue;
}

//Usage--> 
//<onkeypress="JavaScript:return alphaOnly(event);" onpaste="JavaScript:return false;"    >
////////////////////////////////////////////////////////////////////////////////////////////////////////////

//4.accept numbers only (onkeypress)

function numbersonly(myfield, e, dec)
{
    var key;
    var keychar;

    if (window.event)
        key = window.event.keyCode;
    else if (e)
        key = e.which;
    else
        return true;
    keychar = String.fromCharCode(key);

    // control keys
    if ((key == null) || (key == 0) || (key == 8) ||
            (key == 9) || (key == 13) || (key == 27))
        return true;

    // numbers
    else if ((("0123456789").indexOf(keychar) > -1))
        return true;

    // decimal point jump
    else if (dec && (keychar == "."))
    {
        myfield.form.elements[dec].focus();
        return false;
    } else
        return false;
}


// Usage -->
// <onKeyPress="return numbersonly(this, event) " onpaste="javascript:return false;" >
////////////////////////////////////////////////////////////////////////////////////////////////////////////

//5. URL validation in the form 

function checkURL(emailField)
{
    var tomatch = /^(http|ftp\www):\/\/((?:[a-zA-Z0-9_-]+\.?)+):?(\d*)/
    if (!tomatch.test(emailField.value))
    {
        window.alert("Please enter Valid URL");
        emailField.value = "";
        emailField.focus();
        return true;
    } else {
        return false;
    }
}

// Usage-->
// <onchange="checkURL(this)">
////////////////////////////////////////////////////////////////////////////////////////////////////////////


//6.checking for capital letters only

function checkCapital(textfld)
{
    var check = /^[A-Z]{1,15}$/
    if (!(check.test(textfld.value)))
    {
        window.alert("please enter only Capitals");
        textfld.value = "";
        textfld.focus;
        return false;
    } else
        return true;
}

// Usage --> 
// <onchange="checkCapital(this)">
////////////////////////////////////////////////////////////////////////////////////////////////////////////

//7. Check MOBILE NO for validation

function checkIsMobileNumber(mobileField)
{
    var s = mobileField.value;
    reMobileNumber = new RegExp(/^9+[0-9]\d{8}/);
    if (!(reMobileNumber.test(s)))
    {
        alert("Please enter valid Mobile No");
        mobileField.value = "";
        return true;
    } else
    {
        return false;
    }
}

// Usage-->
// <onchange="checkIsMobileNumber(this)" onKeyPress="return numbersonly(this, event) " onpaste="javascript:return false;">
////////////////////////////////////////////////////////////////////////////////////////////////////////////

//8. Check PHONE NO for validation

function checkIsPhoneNumber(phoneField)
{
    var s = phoneField.value;
    rePhoneNumber = new RegExp(/^[0-9]+[0-9]\d{8}/);

    if (!(rePhoneNumber.test(s)))
    {
        alert("Please Enter valid Phone No");
        phoneField.value = "";
        return true;
    } else
    {
        return false;
    }
}

// Usage-->
// <onchange="checkIsPhoneNumber(this)" onKeyPress="return numbersonly(this, event) " onpaste="javascript:return false;">
////////////////////////////////////////////////////////////////////////////////////////////////////////////


//9.Special characters not allowed

function isSpecialCharacter(fieldVal)
{
    var s = fieldVal.value;
    //alert("string value is"+s);
    var specialChar = '/!\"=\\;+~%^*?:.[]{}()@#$%^&*,';
    for (var i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);
        if (c == "'")
        {
            var msg = "";
            msg = ("No Special Characters Allowed (/!\"=\\;+~%^*?:.[]{}()@#$%^&*,') ");
            fieldVal.value = '';
            fieldVal.focus();
            return msg;

        }


        for (var j = 0; j <= specialChar.length; j++)
        {
            var p = specialChar.charAt(j);
            if (p == c)
            {
                var msg = "";
                msg = ("No Special Characters Allowed (/!\"=\\;+~%^*?:.[]{}()@#$%^&*,')");
                fieldVal.value = '';
                fieldVal.focus();
                return msg;
            }
        }

    }
}
//Special chraracters
function validateRegExpr(data, elementName, dataType) {
    var regExp = "";
    var validChars = "";
    if (dataType == "AN") {
        regExp = /^[0-9a-zA-Z]+$/;
        validChars = "A-Z, a-z, 0-9";
    } else {
        regExp = /^[0-9a-zA-Z\s\-;,\.\":]+$/;
        validChars = "Please limit the special characters to : - ; , .<br>";
    }

    var msg = "";
    if (!regExp.test(data)) {
        //msg = "Please restrict "+elementName+" entry to the following characters. <br>"+validChars;
        msg = validChars;
    }
    return msg;
}

// Usage-->
// <onchange="isSpecialCharacter(this)"> 
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////
function captureEnterKey(id, evt)
{
    var keyCode = evt.keyCode ? evt.keyCode : evt.charCode ? evt.charCode : evt.which;
    //alert("keyCode>>"+keyCode);
    if (keyCode == 13) {
        if (evt.preventDefault) {
            var data = document.getElementById(id).value;
            //data=data+"<br/>";
            //data=data+"\n";
            document.getElementById(id).value = data;
        }
    }
    return true;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

function phoneNumbersonly(myfield, e, dec)
{
    var key;
    var keychar;

    if (window.event)
        key = window.event.keyCode;
    else if (e)
        key = e.which;
    else
        return true;
    keychar = String.fromCharCode(key);

    // control keys
    if ((key == null) || (key == 0) || (key == 8) ||
            (key == 9) || (key == 13) || (key == 27))
        return true;

    // numbers
    else if ((("0123456789-()").indexOf(keychar) > -1))
        return true;

    // decimal point jump
    else if (dec && (keychar == "."))
    {
        myfield.form.elements[dec].focus();
        return false;
    } else
        return false;
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*onkeypress="return validateFloatKeyPress(this,event);*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////
function validateFloatKeyPress(el, evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    var number = el.value.split('.');
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    //just one dot (thanks ddlab)
    if (number.length > 1 && charCode == 46) {
        return false;
    }
    //get the carat position
    var caratPos = getSelectionStart(el);
    var dotPos = el.value.indexOf(".");
    if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
        return false;
    }
    return true;
}

//thanks: http://javascript.nwbox.com/cursor_position/
function getSelectionStart(o) {
    if (o.createTextRange) {
        var r = document.selection.createRange().duplicate()
        r.moveEnd('character', o.value.length)
        if (r.text == '')
            return o.value.length
        return o.value.lastIndexOf(r.text)
    } else
        return o.selectionStart
}

