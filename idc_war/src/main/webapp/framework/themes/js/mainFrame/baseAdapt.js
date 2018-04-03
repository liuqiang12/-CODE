var leftOverHeight = 0;
var rightOverHeight = 0;
var bs_leftHeight_tmp = 0;
var bs_rightHeight_tmp = 0;
var c;
$(function() {
    /*
     outerHeight:包括padding，border
     innerHeight:padding，但是不包括border
     */
    leftOverHeight = $("#mainHbox").outerHeight() ;//获取菜单以外的高度
    rightOverHeight = $("#mainHbox").outerHeight()  + $("#rbox_topcenter").outerHeight()  ;//获取center以外的高度

    var c = null;
    autoReset();
    window.onresize = function() {
        //如果窗口变化时，重新规划高度
        if (c) {
            clearTimeout(c)
        }
        c = setTimeout("autoReset()", 100)
    };

    var b = document.getElementsByTagName("html")[0];
    b.style.overflow = "hidden";

    $.fn.toggle = function( fn, fn2 ) {
        var args = arguments,guid = fn.guid || $.guid++,i=0,
            toggle = function( event ) {
                var lastToggle = ( $._data( this, "lastToggle" + fn.guid ) || 0 ) % i;
                $._data( this, "lastToggle" + fn.guid, lastToggle + 1 );
                event.preventDefault();
                return args[ lastToggle ].apply( this, arguments ) || false;
            };
        toggle.guid = guid;
        while ( i < args.length ) {
            args[ i++ ].guid = guid;
        }
        return this.click( toggle );
    };
    $("#bs_center").toggle(function() {
        $("#hideCon").hide(200);
        $(this).parent().removeClass("main_shutiao");
        $(this).parent().addClass("main_shutiao_slide");
        $(this).removeClass("bs_leftArr");
        $(this).addClass("bs_rightArr");
        $(this).attr("title", "展开面板");
        $(".rbox_left_navcontent").css("marginLeft",15);
        //enableTooltips();
        //hideTooltip();
        return false
    }, function() {
        $("#hideCon").show();
        $(this).parent().removeClass("main_shutiao_slide");
        $(this).parent().addClass("main_shutiao");
        $(this).removeClass("bs_rightArr");
        $(this).addClass("bs_leftArr");
        $(this).attr("title", "收缩面板");
        $(".rbox_left_navcontent").css("marginLeft",0);
        //enableTooltips();
        //hideTooltip();
        return false
    });
    //enableTooltips();
});
/* 鼠标滑过出现气泡提示框 */
function enableTooltips(id) {

    var links, i, h;
    if (!document.getElementById || !document.getElementsByTagName)
        return;
    AddCss();
    h = document.createElement("span");
    h.id = "btc";
    h.setAttribute("id", "btc");
    h.style.position = "absolute";
    h.style.zIndex = 9999;
    document.getElementsByTagName("body")[0].appendChild(h);
    $("a[title],span[title],input[title],textarea[title],img[title],div[title]").each(function() {
        if ($(this).attr("defaultTip") != "false") {
            Prepare($(this)[0])
        }
    });
}
function _getStrLength(c) {
    var b;
    var a;
    for (b = 0, a = 0; b < c.length; b++) {
        if (c.charCodeAt(b) < 128) {
            a++
        } else {
            a = a + 2
        }
    }
    return a
}
function Prepare(el) {
    var tooltip, t, b, s, l;
    t = el.getAttribute("title");
    if (t == null || t.length == 0) {
        t = "No Reslut";
        //		return;                                  注释掉这一行则不显示
    }
    el.removeAttribute("title");
    tooltip = CreateEl("span", "tooltip");
    s = CreateEl("span", "top");
    s.appendChild(document.createTextNode(t));
    tooltip.appendChild(s);
    b = CreateEl("b", "bottom");
    //l = el.getAttribute("href");
    //if (l.length > 30)
    //	l = l.substr(0, 27) + "...";
    //b.appendChild(document.createTextNode(l));
    tooltip.appendChild(b);
    setOpacity(tooltip);
    el.tooltip = tooltip;
    el.onmouseover = showTooltip;
    el.onmouseout = hideTooltip;
    el.onmousemove = Locate;
}
function showTooltip(e) {
    document.getElementById("btc").appendChild(this.tooltip);
    Locate(e);
}
function hideTooltip(e) {
    var d = document.getElementById("btc");
    if (d.childNodes.length > 0)
        d.removeChild(d.firstChild);
}
function setOpacity(el) {
    el.style.filter = "alpha(opacity:95)";
    el.style.KHTMLOpacity = "0.95";
    el.style.MozOpacity = "0.95";
    el.style.opacity = "0.95";
}
function CreateEl(t, c) {
    var x = document.createElement(t);
    x.className = c;
    x.style.display = "block";
    return (x);
}
function AddCss() {}
function Locate(e) {
    var posx = 0, posy = 0;
    if (e == null)
        e = window.event;
    if (e.pageX || e.pageY) {
        posx = e.pageX;
        posy = e.pageY;
    } else if (e.clientX || e.clientY) {
        if (document.documentElement.scrollTop) {
            posx = e.clientX + document.documentElement.scrollLeft;
            posy = e.clientY + document.documentElement.scrollTop;
        } else {
            posx = e.clientX + document.body.scrollLeft;
            posy = e.clientY + document.body.scrollTop;
        }
    }
    document.getElementById("btc").style.top = (posy + 10) + "px";
    document.getElementById("btc").style.left = (posx - 20) + "px";
}

function setAutoHeightWithChrome(obj){
    var myObject=obj;
    if(document.getElementById){
        if(myObject && !window.opera){
            //非opera浏览器
            if(myObject.contentDocument && myObject.contentDocument.body.scrollHeight){
                //内容加载完毕后:设置高度
                //firefox 、 chrom浏览器
                if($.browser.webkit){
                    //google浏览器、Safari浏览器
                    myObject.height = myObject.contentDocument.body.scrollHeight;
                }else if($.browser.mozilla){
                    //firefox浏览器
                    myObject.height = myObject.contentDocument.body.scrollHeight ;
                }
            }else if(myObject.Document && myObject.Document.body.scrollHeight){

                if($.browser.msie){
                    //IE浏览器   360浏览器
                    myObject.height = myObject.Document.body.scrollHeight;
                }
            }
        }
    }
}
function autoReset() {
    try {
        document.getElementById("frmleft").contentWindow.scrollContent()
    } catch (f) {}
    try {
        document.getElementById("frmright").contentWindow.scrollContent()
    } catch (f) {}
    //获取整个屏幕的高度
    /*
     clientHeight:指可看到内容的区域，滚动条不算在内，一般是最后一个工具条以下到状态栏以上的这个区域IE6，IE7，IE8以及最新的的FF, Chrome 元素上都是offsetHeight = clientHeight + 滚动条 + 边框
     */
    var documentClientHeight = document.documentElement.clientHeight;
    //菜单区域
    try {
        var bs_leftHeight = documentClientHeight - leftOverHeight - parseInt($("#lbox").css("paddingTop")) - parseInt($("#lbox").css("paddingBottom"));
        if(!bs_leftHeight_tmp){
            bs_leftHeight_tmp = bs_leftHeight;
        }
        $("#bs_left").height(bs_leftHeight+15);
    } catch (f) {};
    //内容区域
    try {
        var bs_rightHeight = documentClientHeight - rightOverHeight - parseInt($("#rbox").css("paddingTop")) - parseInt($("#rbox").css("paddingBottom"));
        if(!bs_rightHeight_tmp){
            bs_rightHeight_tmp = bs_rightHeight;
        }
        $("#bs_right").height(bs_rightHeight+9);

    } catch (f) {};

}
