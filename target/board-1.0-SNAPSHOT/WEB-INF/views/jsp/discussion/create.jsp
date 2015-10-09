<!DOCTYPE html>
<html>
<head>
    <title>Create Discussion</title>
</head>
<body>
<h2>创建讨论</h2>
[<a href="<c:url value="/discussion/list" />">返回讨论列表</a>]<br/>
<br/>
<form:form method="post" modelAttribute="discussionForm">
    <label for="user">用户（邮箱）：</label><br/>
    <form:input path="user" type="email"/><br/>
    <br/>
    <label for="subject">主题：</label><br/>
    <form:input path="subject"/><br/>
    <br/>
    <label for="message">消息：</label><br/>
    <form:textarea path="message" cols="40" rows="5"/><br/>
    <br/>
    <input type="submit" value="提&nbsp;交"/>
</form:form>
</body>
</html>
