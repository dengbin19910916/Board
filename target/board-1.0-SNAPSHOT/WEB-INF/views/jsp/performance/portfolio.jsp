<%--
  Created by IntelliJ IDEA.
  User: Dengbin
  Date: 2015/9/24
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <column>业务量</column>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/main.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.common.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.office365.min.css"/>">
    <%--<link rel="stylesheet" href="<c:url value="/resources/styles/kendo.default.min.css"/>">--%>
    <link rel="stylesheet" href="<c:url value="${theme}"/>">

    <script src="<c:url value="/resources/js/jquery-2.1.4.js"/>"></script>
    <script src="<c:url value="/resources/js/kendo.all.min.js"/>"></script>
    <script src="<c:url value="/resources/js/cultures/kendo.culture.zh-CN.min.js"/>"></script>
    <script src="<c:url value="/resources/js/messages/kendo.messages.zh-CN.min.js"/>"></script>
    <script type="text/javascript">
        kendo.culture("zh-CN");
    </script>
</head>
<body>
<div id="grid"></div>
<script>
    $(document).ready(function () {
        $("#grid").kendoGrid({
            dataSource: {
                type: "odata",
                transport: {
                    read: "//demos.telerik.com/kendo-ui/service/Northwind.svc/Customers"
                },
                pageSize: 20
            },
            height: 550,
            groupable: true,
            sortable: true,
            pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            columns: [{
                template: "<div class='customer-photo'" +
                "style='background-image: url(http://localhost:8080/board/resources/stylesheet/content/web/Customers/#:credit.CustomerID#.jpg);'></div>" +
                "<div class='customer-name'>#: ContactName #</div>",
                field: "ContactName",
                column: "Contact Name",
                width: 240
            }, {
                field: "ContactTitle",
                column: "Contact ExcelColumn"
            }, {
                field: "CompanyName",
                column: "Company Name"
            }, {
                field: "Country",
                width: 150
            }]
        });
    });
</script>

<style type="text/css">
    .customer-photo {
        display: inline-block;
        width: 32px;
        height: 32px;
        border-radius: 50%;
        background-size: 32px 35px;
        background-position: center center;
        vertical-align: middle;
        line-height: 32px;
        box-shadow: inset 0 0 1px #999, inset 0 0 10px rgba(0,0,0,.2);
        margin-left: 5px;
    }

    .customer-name {
        display: inline-block;
        vertical-align: middle;
        line-height: 32px;
        padding-left: 3px;
    }
</style>
</body>
</html>
