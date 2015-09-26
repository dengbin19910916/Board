<%--@elvariable id="ticketForm" type="com.wrox.site.TicketController.Form"--%>
<template:basic htmlTitle="Create a Ticket" bodyTitle="创建一个票据">
    <form:form method="post" enctype="multipart/form-data" modelAttribute="ticketForm">
        <label for="subject">主题：</label><br/>
        <form:input path="subject"/><br/><br/>
        <label for="body">内容：</label><br/>
        <form:textarea path="body" rows="5" cols="30"/><br/><br/>
        <b>附件：</b><br/>
        <input type="file" name="attachments" multiple="multiple"/><br/><br/>
        <input type="submit" class="btn" value="创 建"/>
    </form:form>
</template:basic>
