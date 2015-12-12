/**
 * 员工信息处理。
 *
 * @author dengb
 */

function loadInfo(id) {
    $(document).ready(function() {
        //noinspection JSUnresolvedFunction
        $("#gender").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "value",
            dataSource: {
                transport: {
                    read: {
                        dataType: "json",
                        url: "http://localhost:8080/board/hr/init/gender"
                    }
                }
            }
        });

        //noinspection JSUnresolvedFunction
        $("#birthday").kendoDatePicker({
            start: "year",
            depth: "month",
            format: "yyyy-MM-dd",
            change: calculateAge
        });

        //noinspection JSUnresolvedFunction
        $("#grid").kendoGrid({
            dataSource: {
                transport: {
                    read: {
                        dataType: "json",
                        url: "http://localhost:8080/board/hr/init/grid"
                    }
                },
                pageSize: 10
            },
            pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            columns: [{
                field: "productID",
                title: "Product ID",
                width: 200
            }, {
                field: "productName",
                title: "Product Name"
            }, {
                field: "unitPrice",
                title: "Unit Price"
            }]
        });
    });

    function print(array) {
        var s = "";
        for (var i = 0; i < array.length; i++) {
            s += (array[i].text + " - " + array[i].value + "\n");
        }
        alert(s);
    }

    $(document).ready(function() {
        // TODO 初始化基础数据。
        $.getJSON("http://localhost:8080/board/hr/load", null, function(data) {
            setValue("name", data["name"]);
            setValue("gender", data["gender"], "kendoDropDownList");
            setValue("birthday", new Date(data["birthday"]).format("yyyy-MM-dd"), "kendoDatePicker");
            calculateAge();
        });

        $("#get").click(function() {
            alert($("#birthday").data("kendoDatePicker").value());
        });
    });
}

/**
 * 设置组件的数据源。
 *
 * @param id 组件ID。
 * @param widget 组件名称/组件对象。
 * @param data 组件需要被设置的数据源。
 */
function setDataSource(id, widget, data) {
    $("#" + id).data(widget).setDataSource(new kendo.data.DataSource({data: data}));
}

/**
 * 设置控件的值。
 *
 * @param id 控件ID。
 * @param value 控件需要被设置的值。
 * @param widget 当控件为kendoUI组件时，传入组件名称/组件对象。
 */
function setValue(id, value, widget) {
    if (widget) {
        getWidget(id, widget).value(value + "");
    }
    $("#" + id).val(value);
}

/**
 * 返回控件的值。
 *
 * @param id 控件ID。
 * @param widget 当控件为kendo组件时，传入组件名称/组件对象。
 * @returns {*|jQuery} 控件值。
 */
function getValue(id, widget) {
    if (widget) {
        getWidget(id, widget).value();
    }
    return $("#" + id).val();
}

/**
 * 返回组件对象。
 *
 * @param id 组件ID。
 * @param widgetName 组件名称/组件对象。
 * @returns {*|jQuery} 组件对象。
 */
function getWidget(id, widgetName) {
    return $("#" + id).data(widgetName);
}

function calculateAge() {
    var now = new Date();
    var curYear = now.getFullYear();
    var curMonth = now.getMonth();
    var curDate = now.getDate();

    var birthday = getWidget("birthday", "kendoDatePicker").value();
    var birYear = birthday.getFullYear();
    var birMonth = birthday.getMonth();

    var birDate = birthday.getDate();

    var age = curYear - birYear;
    if (birMonth = curMonth) {
        if (birDate > curDate) {
            age -= 1;
        }
    }
    if (birMonth > curMonth) {
        age -= 1;
    }
    age = age < 0 ? 0 : age;
    setValue("age", age);
}

$(document).ready(function() {
    /*$("#edit").click(function() {
        var info = {name: getValue("name"), gender: getValue("gender", "kendoDropDownList"), birthday: getValue("birthday", "kendoDatePicker")};
        $.post("http://localhost:8080/board/hr/update", info, function(data) {
            if (data) {
                alert("更新成功");
            }
        });
    });*/
});