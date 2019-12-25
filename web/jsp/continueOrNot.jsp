<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>是否继续输入资源</title>
</head>
<body>
    <h3>是否继续输入资源</h3>
    资源数：${resourceSize}<br>
    <a href="jsp/initResources.jsp">继续添加资源</a><br>
    <a href="jsp/initProcess.jsp?resourceSize=${resourceSize}">去添加进程</a><br>
</body>
</html>
