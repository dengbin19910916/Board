/**
 * Javascript原型方法。
 *
 * Created by dengb on 2015/12/9.
 */

/**
 * 当且仅当此数组包含指定的值时，返回 true。
 *
 * @param value 要搜索的值
 * @returns {boolean} 如果此数组包含 value，则返回 true，否则返回 false
 */
Array.prototype.contains = function(value) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] === value) {
            return true;
        }
    }
    return false;
};

/**
 * 当且仅当此日期为代表闰年日期时返回true。
 *
 * @returns {boolean} 如果此日期为闰年日期时返回 true，否则返回 false
 */
Date.prototype.isLeapYear = function () {
    return (this.getFullYear() & 3 == 0 && this.getFullYear() % 100) != 0 || (this.getFullYear() % 400) == 0;
};

/**
 * 返回此日期在一年中的位置。
 *
 * @returns {number} 日期在一年中的索引位置，从1开始。
 */
Date.prototype.getDayOfYear = function () {
    return this.firstDayOfYear(this.isLeapYear()) + this.getDate() - 1;
};

Date.prototype.firstDayOfYear = function () {
    var month = this.getMonth() + 1;
    var leap = this.isLeapYear() ? 1 : 0;
    switch (month) {
        case 1:
            return 1;
        case 2:
            return 32;
        case 3:
            return 60 + leap;
        case 4:
            return 91 + leap;
        case 5:
            return 121 + leap;
        case 6:
            return 152 + leap;
        case 7:
            return 182 + leap;
        case 8:
            return 213 + leap;
        case 9:
            return 244 + leap;
        case 10:
            return 274 + leap;
        case 11:
            return 305 + leap;
        default:
            return 335 + leap;
    }
};

/**
 * 日期对象的format()函数，输出格式化之后的日期字符串。
 *
 * @param pattern 日期格式。
 * @returns {*} 格式化日期字符串。
 */
Date.prototype.format = function (pattern, local) {
    if (/(y+)/.test(pattern)) {
        var length = RegExp.$1.length == 3 || RegExp.$1.length == 4 ? 4 : RegExp.$1.length;
    }
    var imp = ["M+", "d+", "H+", "m+", "s+"];
    var patterns = {
        "G": "AD",  // Era标识符，AD
        "y+": (this.getFullYear() + "").substring(4 - length),   // 年，1996; 96
        "M+": this.getMonth() + 1,  // 年中的月份，July; Jul; 07
        "w+": Math.ceil(this.getDayOfYear() / 7),   // 年中的周数，27
        "W+": Math.ceil(this.getDate() / 7),        // 月份中的周数，2
        "D+": this.getDayOfYear(),  // 年中的天数，189
        "d+": this.getDate(),       // 月份中的天数，10
        "F+": Math.ceil(this.getDate() / 7),        // 月份中的星期，2
        "E+": this.getDay(),                        // 星期中的天数，Tuesday; Tue
        "a+": this.getHours() > 11 ? "PM" : "AM",   // AM/PM标记，PM; 上午
        "H+": this.getHours(),                      // 一天中的小时数（0-23），0
        "k+": this.getHours() + 1,                  // 一天中的小时数（1-24）, 24
        "K+": null,                     // AM/PM中的小时数（0-11），0
        "h+": this.getHours(),          // AM/PM中的小时数（1-12），12
        "m+": this.getMinutes(),        // 小时中的分钟数，30
        "s+": this.getSeconds(),        // 分钟的秒数，55
        "S+": this.getMilliseconds(),   // 毫秒数，978
        "z+": null,                     // 时区，Pacific Standard Time; PST; GMT-08:00
        "Z+": null                      // 时区， -0800
    };
    /*if (/(y+)/.test(pattern)) {
        pattern = pattern.replace(RegExp.$1, (this.getFullYear() + "")
            .substring(4 - (RegExp.$1.length == 3 || RegExp.$1.length == 4 ? 4 : RegExp.$1.length)));
    }*/
    for (var p in patterns) {
        if (new RegExp("(" + p + ")").test(pattern)) {
            if (patterns.hasOwnProperty(p)) {
                if (imp.contains(p)) {
                    pattern = pattern.replace(RegExp.$1, RegExp.$1.length == 1 ? patterns[p] : ("00" + patterns[p]).substring((patterns[p] + "").length));
                } else {
                    pattern = pattern.replace(RegExp.$1, patterns[p]);
                }
            }
        }
    }
    return pattern;
};


/*
 G  Era 标志符  Text  AD
 y  年  Year  1996; 96
 M  年中的月份  Month  July; Jul; 07
 w  年中的周数  Number  27
 W  月份中的周数  Number  2
 D  年中的天数  Number  189
 d  月份中的天数  Number  10
 F  月份中的星期  Number  2
 E  星期中的天数  Text  Tuesday; Tue
 a  Am/pm 标记  Text  PM
 H  一天中的小时数（0-23）  Number  0
 k  一天中的小时数（1-24）  Number  24
 K  am/pm 中的小时数（0-11）  Number  0
 h  am/pm 中的小时数（1-12）  Number  12
 m  小时中的分钟数  Number  30
 s  分钟中的秒数  Number  55
 S  毫秒数  Number  978
 z  时区  General time zone  Pacific Standard Time; PST; GMT-08:00
 Z  时区  RFC 822 time zone  -0800
 */