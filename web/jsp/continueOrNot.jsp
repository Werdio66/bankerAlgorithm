<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>是否继续输入资源</title>
</head>
<body>
    <h3>是否继续输入资源</h3>
    <div style="color: blue">
        资源数：${resourceSize}<br>
    </div>
    <a href="jsp/initResources.jsp">1.继续添加资源</a><br>
    <a href="jsp/initProcess.jsp?resourceSize=${resourceSize}">2.去添加进程</a><br>
</body>
</html>
