/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Control flags for list selection and sort sequence
// Sequence is on option value (first 2 chars - can be stripped off in form processing)
// It is assumed that the select list is in sort sequence initially
var singleSelect = true;  // Allows an item to be selected once only
var sortSelect = true;  // Only effective if above flag set to true
var sortPick = true;  // Will order the picklist in sort sequence

// Initialise - invoked on load
function initIt() {
    var selectList = document.getElementById("SelectList");
    var selectOptions = selectList.options;
    var selectIndex = selectList.selectedIndex;
    var pickList = document.getElementById("empList");
    var pickOptions = pickList.options;
    pickOptions[0] = null;  // Remove initial entry from picklist (was only used to set default width)
    if (!(selectIndex > -1)) {
        selectOptions[0].selected = true;  // Set first selected on load
        selectOptions[0].defaultSelected = true;  // In case of reset/reload
    }
    selectList.focus();  // Set focus on the selectlist
}

// Adds a selected item into the picklist
function addIt() {
    var selectList = document.getElementById("SelectList");
    var selectIndex = selectList.selectedIndex;
    var selectOptions = selectList.options;
    var pickList = document.getElementById("empList");
    var pickOptions = pickList.options;
    var pickOLength = pickOptions.length;
    var selectOLength = selectOptions.length;
    if (selectIndex == -1 && selectOLength != 0) {
        alert("Select Username to add.");
        return false;
    }
    if (selectOLength == 0) {
        alert("Select box is Empty");
        return false;
    }
    
    // An item must be selected
    //while (selectIndex > -1) {
    if (selectIndex > -1) {
        pickOptions[pickOLength] = new Option(selectList[selectIndex].text);
        pickOptions[pickOLength].value = selectList[selectIndex].value;
        // If single selection, remove the item from the select list
        if (singleSelect) {
            selectOptions[selectIndex] = null;
        }

        if (sortPick) {
            var tempText;
            var tempValue;
            tempText = pickOptions[pickOLength].text;
            tempValue = pickOptions[pickOLength].value;
            pickOptions[pickOLength].text = pickOptions[pickOLength].text;
            pickOptions[pickOLength].value = pickOptions[pickOLength].value;
        }
        selectIndex = selectList.selectedIndex;
        pickOLength = pickOptions.length;
    }
    //selectOptions[0].selected = true;
}

// Deletes an item from the picklist
function delIt() {
    var selectList = document.getElementById("SelectList");
    var selectOptions = selectList.options;
    var selectOLength = selectOptions.length;
    var pickList = document.getElementById("empList");
    var pickIndex = pickList.selectedIndex;
    var pickOptions = pickList.options;
    var pickOLength = pickOptions.length;
    if (pickIndex == -1 && pickOLength != 0) {
        alert("Select at least one field");
        return false;
    }
    if (pickOLength == 0) {
        alert("Select box is Empty");
        return false;
    }
    if (pickIndex > -1) {
        selectOptions[selectOLength] = new Option(pickList[pickIndex].text);
        selectOptions[selectOLength].value = pickList[pickIndex].value;
        // If single selection, replace the item in the pick list
        if (singleSelect) {
            pickOptions[pickIndex] = null;
        }
        if (sortSelect) {
            selectOptions[selectOLength].text = selectOptions[selectOLength].text;
            selectOptions[selectOLength].value = selectOptions[selectOLength].value;
        }
        pickIndex = pickList.selectedIndex;
        selectOLength = selectOptions.length;
    }
    if (pickOLength > 1) {
        pickOptions[0].selectedIndex = true;
    }

}

// Selection - invoked on submit
function selIt() {
    var pickList = document.getElementById("empList");
    var pickOptions = pickList.options;
    var pickOLength = pickOptions.length;

    var selList = document.getElementById("SelectList");
    var selOptions = selList.options;
    var selOLength = selOptions.length;


    if (pickOLength < 1) {
        //alert("No Selections in the Picklist\nPlease Select using the [->] button");
        return false;
    }
    for (var i = 0; i < pickOLength; i++) {
        pickOptions[i].selected = true;
    }

    for (var i = 0; i < selOLength; i++) {
        selOptions[i].selected = true;
    }

    return true;
}

//-->

function levelTwo() {
    var selectList = document.getElementById("SelectList");
    var selectIndex = selectList.selectedIndex;
    var selectOptions = selectList.options;
    var pickList = document.getElementById("empList1");
    var pickOptions = pickList.options;
    var pickOLength = pickOptions.length;
    // An item must be selected
    while (selectIndex > -1) {
        pickOptions[pickOLength] = new Option(selectList[selectIndex].text);
        pickOptions[pickOLength].value = selectList[selectIndex].value;
        // If single selection, remove the item from the select list
        if (singleSelect) {
            selectOptions[selectIndex] = null;
        }
        if (sortPick) {
            var tempText;
            var tempValue;
            // Sort the pick list
            while (pickOLength > 0 && pickOptions[pickOLength].value < pickOptions[pickOLength - 1].value) {
                tempText = pickOptions[pickOLength - 1].text;
                tempValue = pickOptions[pickOLength - 1].value;
                pickOptions[pickOLength - 1].text = pickOptions[pickOLength].text;
                pickOptions[pickOLength - 1].value = pickOptions[pickOLength].value;
                pickOptions[pickOLength].text = tempText;
                pickOptions[pickOLength].value = tempValue;
                pickOLength = pickOLength - 1;
            }
        }
        selectIndex = selectList.selectedIndex;
        pickOLength = pickOptions.length;
    }
    selectOptions[0].selected = true;
}