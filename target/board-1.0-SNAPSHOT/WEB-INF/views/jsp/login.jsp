<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="com.wrox.site.AuthenticationController.Form"--%>
<template:logOut htmlTitle="Log In" bodyTitle="登 录">
    你必须登录此站点。<br /><br />
    <c:if test="${loginFailed}">
        <b>用户名或密码错误，请重新登录。</b><br/><br/>
    </c:if>
    <form:form method="post" modelAttribute="loginForm">
        <label for="username">用户名：</label><br/>
        <form:input path="username"/><br/><br/>
        <label for="password">密&emsp;码：</label><br/>
        <form:password path="password"/><br/><br/>
        <input type="submit" class="btn" value="登 录"/>
    </form:form>
</template:logOut>
