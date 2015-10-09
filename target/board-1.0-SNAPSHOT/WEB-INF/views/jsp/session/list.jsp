<%--@elvariable id="timestamp" type="long"--%>
<%--@elvariable id="numberOfSessions" type="int"--%>
<%--@elvariable id="sessionList" type="java.util.List<javax.servlet.http.HttpSession>"--%>
<template:basic htmlTitle="Active Sessions" bodyTitle="活动会话列表">
    系统中共有 ${numberOfSessions} 个活动的会话。<br /><br />
    <c:forEach items="${sessionList}" var="s">
        <c:if test="${s.id == pageContext.session.id}">
            <span style="color: red">
        </c:if>
        <c:out value="${s.id} - ${s.getAttribute('com.wrox.user.principal')}"/>
        <c:if test="${s.id == pageContext.session.id}">&nbsp;（你）</c:if>
        &nbsp;- 最后活动在
        ${wrox:timeIntervalToString(timestamp - s.lastAccessedTime)} 以前。<br />
        <c:if test="${s.id == pageContext.session.id}">
            </span>
        </c:if>
    </c:forEach>
</template:basic>
