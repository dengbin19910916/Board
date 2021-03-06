<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>
<%@ include file="/WEB-INF/views/base.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Support :: <c:out value="${fn:trim(htmlTitle)}"/></title>
    <%--<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css"/>--%>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/main.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.common.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.office365.min.css"/>">

    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/user/js/prototype.js"/>"></script>
    <script src="<c:url value="/resources/js/moment.min.js" />"></script>
    <script src="<c:url value="/resources/js/jszip.min.js"/>"></script>
    <script src="<c:url value="/resources/js/kendo.all.min.js"/>"></script>
    <script src="<c:url value="/resources/js/cultures/kendo.culture.zh-CN.min.js"/>"></script>
    <script src="<c:url value="/resources/js/messages/kendo.messages.zh-CN.min.js"/>"></script>
    <script type="text/javascript">
        kendo.culture("zh-CN");
    </script>
    <%--<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>--%>
    <script type="text/javascript" lang="javascript">
        var postInvisibleForm = function (url, fields) {
            var form = $('<form id="mapForm" method="post"></form>')
                    .attr({action: url, style: 'display: none;'});
            for (var key in fields) {
                if (fields.hasOwnProperty(key))
                    form.append($('<input type="hidden">').attr({
                        name: key, value: fields[key]
                    }));
            }
            $('body').append(form);
            form.submit();
        };
        var newChat = function () {
            postInvisibleForm('<c:url value="/chat/new" />', {});
        };
    </script>
    <jsp:invoke fragment="headContent"/>
</head>
<body>
<h1>高级编程测试</h1>
<table border="0" id="bodyTable">
    <tbody>
    <tr>
        <td class="sidebarCell">
            <jsp:invoke fragment="navigationContent"/>
        </td>
        <td class="contentCell">
            <h2><c:out value="${fn:trim(bodyTitle)}"/></h2>
            <jsp:doBody/>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
