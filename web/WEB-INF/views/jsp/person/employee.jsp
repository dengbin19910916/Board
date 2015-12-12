<template:basic htmlTitle="Employee Information" bodyTitle="员工信息">
    <jsp:attribute name="extraHeadContent">
        <link rel="stylesheet" href="<c:url value="/resources/stylesheet/main.css" />">
        <script type="text/javascript" src="<c:url value="/resources/user/js/employee.js"/>"></script>
    </jsp:attribute>
    <jsp:body>
        <script type="text/javascript">
            window.onload = loadInfo(${id});
        </script>
        <form action="<c:url value="/hr/update"/>" method="post">
            <table>
                <tbody>
                <tr>
                    <td align="right" width="80px;"><label for="name">姓名</label></td>
                    <td align="left"><input id="name" name="name" class="k-textbox" style="width: 130px;"></td>
                    <td align="right" width="80px;"><label for="gender">性别</label></td>
                    <td align="left"><input id="gender" name="gender" style="width: 130px;"></td>
                    <td align="right" width="80px;"><label for="age">年龄</label></td>
                    <td align="left"><input id="age" name="age" class="k-textbox" style="width: 130px;" readonly="readonly"></td>
                    <td align="right" width="80px;"><label for="birthday">出生日期</label></td>
                    <td align="left"><input id="birthday" name="birthday" style="width: 130px;"></td>
                </tr>
                <tr>
                    <td colspan="8">
                        <div id="grid"></div>
                    </td>
                </tr>
                </tbody>
            </table>
            <br/>
            <button id="edit" class="k-button" style="width: 100px;">编辑</button>
            <button id="get" class="k-button" style="width: 100px;">GetBirthday</button>
            <input type="submit" class="k-button" style="width: 100px;" value="提交">
        </form>
    </jsp:body>
</template:basic>
