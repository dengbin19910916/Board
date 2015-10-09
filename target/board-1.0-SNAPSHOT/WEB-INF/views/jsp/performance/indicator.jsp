<%--
  Created by IntelliJ IDEA.
  User: Dengbin
  Date: 2015/9/24
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<template:basic htmlTitle="指标" bodyTitle="指标">
    <jsp:attribute name="extraHeadContent">
        <link rel="stylesheet" href="<c:url value="/resources/stylesheet/main.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.common.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.default.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.office365.min.css"/>">
        <%--<link rel="stylesheet" href="<c:url value="/resources/styles/kendo.material.min.css"/>">--%>

        <script src="<c:url value="/resources/js/jquery-2.1.4.js"/>"></script>
        <script src="<c:url value="/resources/js/jszip.min.js"/>"></script>
        <script src="<c:url value="/resources/js/kendo.all.min.js"/>"></script>
        <script src="<c:url value="/resources/js/cultures/kendo.culture.zh-CN.min.js"/>"></script>
        <script src="<c:url value="/resources/js/messages/kendo.messages.zh-CN.min.js"/>"></script>
        <script type="text/javascript">
            kendo.culture("zh-CN");
        </script>
    </jsp:attribute>
    <jsp:body>
        <div id="grid"></div>
        <script>
            $("#grid").kendoGrid({
//                toolbar: ["excel"],
                excel: {
                    fileName: "Kendo UI Grid Export.xlsx",
//                    proxyURL: "",
//                    proxyURL: "//demos.telerik.com/kendo-ui/service/export",
                    filterable: true
                },
                dataSource: {
                    type: "odata",
                    transport: {
                        read: "//demos.telerik.com/kendo-ui/service/Northwind.svc/Products"
                    },
                    schema: {
                        model: {
                            fields: {
                                ProductName: {type: "string"},
                                UnitPrice: {type: "number"},
                                UnitsOnOrder: {type: "number"},
                                UnitsInStock: {type: "number"}
                            }
                        }
                    },
                    pageSize: 15,
                    group: {
                        field: "UnitsInStock", aggregates: [
                            {field: "ProductName", aggregate: "count"},
                            {field: "UnitPrice", aggregate: "sum"},
                            {field: "UnitsOnOrder", aggregate: "average"},
                            {field: "UnitsInStock", aggregate: "count"}
                        ]
                    },
                    aggregate: [
                        {field: "ProductName", aggregate: "count"},
                        {field: "UnitPrice", aggregate: "sum"},
                        {field: "UnitsOnOrder", aggregate: "average"},
                        {field: "UnitsInStock", aggregate: "min"},
                        {field: "UnitsInStock", aggregate: "max"}
                    ]
                },
                sortable: true,
                pageable: true,
                groupable: true,
                filterable: true,
                columnMenu: true,
                reorderable: true,
                resizable: true,
                columns: [
                    {
                        width: 300,
                        locked: true,
                        field: "ProductName",
                        title: "Product Name",
                        aggregates: ["count"],
                        footerTemplate: "Total Count: #=count#",
                        groupFooterTemplate: "Count: #=count#"
                    },
                    {width: 300, field: "UnitPrice", title: "Unit Price", aggregates: ["sum"]},
                    {
                        width: 300,
                        field: "UnitsOnOrder",
                        title: "Units On Order",
                        aggregates: ["average"],
                        footerTemplate: "Average: #=average#",
                        groupFooterTemplate: "Average: #=average#"
                    },
                    {
                        width: 300,
                        field: "UnitsInStock",
                        title: "Units In Stock",
                        aggregates: ["min", "max", "count"],
                        footerTemplate: "Min: #= min # Max: #= max #",
                        groupHeaderTemplate: "Units In Stock: #= value # (Count: #= count#)"
                    }
                ]
            });
        </script>
    </jsp:body>
</template:basic>