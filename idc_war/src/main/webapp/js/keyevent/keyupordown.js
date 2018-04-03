var KEY = {SHIFT: 16, CTRL: 17, ALT: 18, DOWN: 40, RIGHT: 39, UP: 38, LEFT:  37};
var selectIndexs = {firstSelectRowIndex: 0, lastSelectRowIndex: 0};
var inputFlags = {isShiftDown: false, isCtrlDown: false, isAltDown: false};

function fun(index, row) {
    if (index != selectIndexs.firstSelectRowIndex && !inputFlags.isShiftDown) {
        selectIndexs.firstSelectRowIndex = index;
    }
    if (typeof(tableId) != "undefined" && tableId != null && tableId != '') {
        //安下SHIFT键
        var rows = $('#' + tableId).datagrid('getRows');
        if (inputFlags.isShiftDown) {
            $('#' + tableId).datagrid('clearSelections');
            selectIndexs.lastSelectRowIndex = index;
            var tempIndex = 0;
            if (selectIndexs.firstSelectRowIndex > selectIndexs.lastSelectRowIndex) {
                tempIndex = selectIndexs.firstSelectRowIndex;
                selectIndexs.firstSelectRowIndex = selectIndexs.lastSelectRowIndex;
                selectIndexs.lastSelectRowIndex = tempIndex;
            }
            for (var i = selectIndexs.firstSelectRowIndex; i <= selectIndexs.lastSelectRowIndex; i++) {
                if (tableId != null && tableId == 'rackunitTable') {
                    if (rows[i].STATUS != '20') {
                        $('#' + tableId).datagrid('unselectRow', i);
                    } else {
                        $('#' + tableId).datagrid('selectRow', i);
                    }
                } else {
                    $('#' + tableId).datagrid('selectRow', i);
                }
            }
        }else if(tableId=="rackunitTable"){
            if (rows[index].STATUS == '20') {
                if (rows[index].datagrid('getSelected')) {
                    $('#' + tableId).datagrid('unselectRow', index);
                } else {
                    $('#' + tableId).datagrid('selectRow', index);
                }
            } else {
                $('#' + tableId).datagrid('unselectRow', index);
            }
        }else{
            if(inputFlags.isCtrlDown){
                if (rows[index].datagrid('getSelected')) {
                    $('#' + tableId).datagrid('unselectRow', index);
                } else {
                    $('#' + tableId).datagrid('selectRow', index);
                }
            }
        }
    }
}

//响应键盘按下事件
$(document).keydown(function (event) {
    var e = event || window.event;
    var code = e.keyCode | e.which | e.charCode;
    if (typeof(tableId) != "undefined" && tableId != null && tableId != '') {
        switch (code) {
            case KEY.SHIFT:
                inputFlags.isShiftDown = true;
                $('#' + tableId).datagrid('options').singleSelect = false;
                break;
            case KEY.CTRL:
                inputFlags.isCtrlDown = true;
                $('#' + tableId).datagrid('options').singleSelect = false;
                break;
            default:
        }
    }
});

$(document).keyup(function (event) { //响应键盘按键放开的事件
    var e = event || window.event;
    var code = e.keyCode | e.which | e.charCode;
    if (typeof(tableId) != "undefined" && tableId != null && tableId != '') {
        switch (code) {
            case KEY.SHIFT:
                inputFlags.isShiftDown = false;
                selectIndexs.firstSelectRowIndex = 0;
                $('#' + tableId).datagrid('options').singleSelect = true;
                break;
            case KEY.CTRL:
                inputFlags.isCtrlDown = false;
                $('#' + tableId).datagrid('options').singleSelect = true;
                break;
            default:
        }
    }
});