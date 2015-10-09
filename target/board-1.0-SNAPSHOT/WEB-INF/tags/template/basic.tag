<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>
<%@ include file="/WEB-INF/views/base.jspf" %>
<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent"/>
    </jsp:attribute>
    <jsp:attribute name="navigationContent">
        <a href="<c:url value="/ticket/list" />">票据列表</a><br/>
        <a href="<c:url value="/ticket/create" />">创建票据</a><br/>
        <a href="javascript:void 0;" onclick="newChat();">请求支持</a><br/>
        <a href="<c:url value="/chat/list" />">查看请求</a><br/>
        <a href="<c:url value="/session/list" />">会话列表</a><br/>
        <a href="<c:url value="/clerk/portfolio"/>">业务量</a><br/>
        <a href="<c:url value="/clerk/indicator"/>">指标</a><br/>
        <a href="<c:url value="/logout" />">注销</a><br/>
        <jsp:invoke fragment="extraNavigationContent"/>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</template:main>
