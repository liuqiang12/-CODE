/**
 * Created by mylove on 2017/9/14.
 */

/**
 * js时间对象的格式化;
 * eg:format="yyyy-MM-dd hh:mm:ss";
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,  //month
        "d+": this.getDate(),     //day
        "h+": this.getHours(),    //hour
        "m+": this.getMinutes(),  //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    }
    var week = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(w+)/.test(format)) {
        format = format.replace(RegExp.$1, week[this.getDay()]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/**
 *js中更改日期
 * y年， m月， d日， h小时， n分钟，s秒
 */
Date.prototype.add = function (part, value,isNew) {
    value *= 1;
    if (isNaN(value)) {
        value = 0;
    }
    var self = this;
    if(typeof (isNew)!='undefined'&&isNew==true){
        self = new Date();
        self.setTime(this.getTime())
    }
    switch (part) {
        case "y":
            self.setFullYear(this.getFullYear() + value);
            break;
        case "m":
            self.setMonth(this.getMonth() + value);
            break;
        case "d":
            self.setDate(this.getDate() + value);
            break;
        case "h":
            self.setHours(this.getHours() + value);
            break;
        case "n":
            self.setMinutes(this.getMinutes() + value);
            break;
        case "s":
            self.setSeconds(this.getSeconds() + value);
            break;
        default:
    }
    return self;
}
