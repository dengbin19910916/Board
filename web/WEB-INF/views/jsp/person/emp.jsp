<%--
  Created by IntelliJ IDEA.
  User: dengb
  Date: 2015/12/9
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<c:url value="/post/addUser"/>" method="post">
    <c:forEach items="${users}" var="user">
        User:${user.name}<br/>
        Create time:<fmt:formatDate value="${user.createTime}"/><br/>
        Is girl:
        <c:choose>
            <c:when test="${user.girl}">Yes</c:when>
            <c:when test="${!user.girl}">No</c:when>
            <c:otherwise>N/A</c:otherwise>
        </c:choose>
        <br/>
        Checkboxs:
        <c:forEach items="${user.cbx}" var="item">
            ${item},
        </c:forEach>
        <br/>
        Age:${user.age}<br/>
        E-mail:${user.email}<br/>
        <hr/>

        <table style="width:100%;border:1px solid #ccc;">
            <thead>
            <tr style="text-align:left; background-color:#eee;">
                <th>Company name</th>
                <th>User</th>
                <th>Create time</th>
            </tr>
            </thead>
            <tbody>
                <%--<c:forEach items="${user.customers}" var="item">
                    <tr>
                        <td>${item.company}</td>
                        <td>${item.user.name}</td>
                        <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                </c:forEach>--%>
            </tbody>
        </table>
        <hr/>
    </c:forEach>

    <label for="name">User name:</label><input type="text" name="name" id="name" /><br/>
    Is girl:
    <input type="radio" name="girl" id="isGirl" value="true" checked="checked" /><label for="isGirl">Yes</label>
    <input type="radio" name="girl" id="noGirl" value="false" /><label for="noGirl">No</label><br/>
    Checkboxs:
    <input type="checkbox" name="cbx" id="cbx1" value="1" /><label for="cbx1">1</label>
    <input type="checkbox" name="cbx" id="cbx2" value="2" /><label for="cbx2">2</label>
    <input type="checkbox" name="cbx" id="cbx3" value="3" /><label for="cbx3">3</label>
    <br/>
    <label for="age">Age:</label><input type="text" name="age" id="age" /><br/>
    <label for="email">E-mail:</label><input type="text" name="email" id="email" /><br/>
    <label for="createTime"> Create time:</label><input type="text" name="createTime" id="createTime" /><br/>
    <label for="customers[0].company">Company:</label><input type="text" name="customers[0].company" id="customers[0].company" /><br/>
    <input type="submit" value="add" />
    <%--<sf:errors path="*"/>--%>
</form>
</body>
</html>
