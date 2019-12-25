<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单</title>
</head>
<body>
<h3>菜单</h3>
<div style="color: red">
    ${msg}
</div>
    <a href="initProcess.jsp">1.继续添加进程</a><br>
    <a href="/init?cmd=show">2.查看资源分配情况</a><br>
    <a href="requestAv.jsp">3.请求资源</a><br>
    <a href="/init?cmd=showSafe">4.查看安全序列</a><br>
    <a href="/init?cmd=remove">5.删除所有进程，添加新资源</a>
</body>
</html>
