<%--@elvariable id="discussion" type="com.wrox.site.entity.Discussion"--%>
<%--@elvariable id="replies" type="java.util.List<com.wrox.site.entity.Reply>"--%>
<!DOCTYPE html>
<html>
<head>
    <title>Discussion: <c:out value="${discussion.subject}"/></title>
</head>
<body>
<h2>讨论: <c:out value="${discussion.subject}"/></h2>
[<a href="<c:url value="/discussion/list" />">返回讨论列表</a>]<br/>
<i>用户: <c:out value="${discussion.user}"/> / 创建时间: <c:out value="${discussion.created}"/></i>
<br/>
<c:out value="${discussion.message}"/><br/>
<br/>
<c:choose>
    <c:when test="${fn:length(replies) > 0}">
        <c:forEach items="${replies}" var="reply" varStatus="i">
            <b>回复 #${i.count}</b><i> - <c:out value="${reply.user}"/> -
            <c:out value="${reply.created}"/></i><br/>
            <c:out value="${reply.message}"/><br/>
            <br/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <i>暂时没有任何回复。</i><br/>
        <br/>
    </c:otherwise>
</c:choose>
<b>回复帖子</b><br/>
<br/>
<c:set var="action"><c:url value="/discussion/${discussion.id}/reply"/></c:set>
<form:form method="post" action="${action}" modelAttribute="replyForm">
    <form:label path="user">用户（邮箱）：</form:label><br/>
    <form:input path="user" type="email"/><br/>
    <br/>
    <form:label path="message">消息：</form:label><br/>
    <form:textarea path="message" cols="40" rows="5"/><br/>
    <br/>
    <input type="submit" value="提&nbsp;交"/>
</form:form>
</body>
</html>
