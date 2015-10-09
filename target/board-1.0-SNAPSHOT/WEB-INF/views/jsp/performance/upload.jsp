<%--
  Created by IntelliJ IDEA.
  User: Dengbin
  Date: 2015/9/29
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Test</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/main.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.common.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/kendo.office365.min.css"/>">

    <script src="<c:url value="/resources/js/jquery-2.1.4.js"/>"></script>
    <script src="<c:url value="/resources/js/kendo.all.min.js"/>"></script>
    <script src="<c:url value="/resources/js/cultures/kendo.culture.zh-CN.min.js"/>"></script>
    <script src="<c:url value="/resources/js/messages/kendo.messages.zh-CN.min.js"/>"></script>
    <script type="text/javascript">
        kendo.culture("zh-CN");
    </script>
</head>
<body>
<form method="post" action="<c:url value="/clerk/upload"/>">
    <div class="demo-section k-header">
        <input name="files" id="files" type="file"/>

        <p style="padding-top: 1em; text-align: right">
            <input type="submit" value="Submit" class="k-button k-primary"/>
        </p>
    </div>
</form>
<script>
    $(document).ready(function() {
        $("#files").kendoUpload();
    });
</script>
</body>
</html>
