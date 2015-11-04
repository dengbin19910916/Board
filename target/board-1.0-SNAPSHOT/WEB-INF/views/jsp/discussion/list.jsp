<%--@elvariable id="discussions" type="java.util.List<com.wrox.site.entity.Discussion>"--%>
<!DOCTYPE html>
<html>
<head>
    <column>Discussion List</column>
</head>
<body>
<h2>讨论列表</h2>
[<a href="<c:url value="/discussion/create" />">新建讨论</a>]<br/>
<br/>
<c:choose>
    <c:when test="${fn:length(discussions) > 0}">
        <c:forEach items="${discussions}" var="discussion">
            <a href="<c:url value="/discussion/${discussion.id}/${discussion.uriSafeSubject}" />"><c:out value="${discussion.subject}"/></a><br/>
            (<c:out value="${discussion.user}"/>)<br/>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <i>暂时没有任何讨论。</i>
    </c:otherwise>
</c:choose>
</body>
</html>
