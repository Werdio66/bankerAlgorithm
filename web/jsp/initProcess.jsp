<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<html>
<head>
    <title>初始化进程</title>

</head>
<body>
<h3>初始化进程</h3>
<form action="/init?cmd=process" method="post">
    请输入进程名：<input type="text" name="processName"/><br>
    请输入最大资源数量：
    <c:forEach var="num" begin="1" step="1" end="${resourceSize}">
        <input id="maxName" type="text" name="maxCount${num}" size="5">
</c:forEach><br>
    请输入已分配资源数量：<c:forEach var="num" begin="1" step="1" end="${resourceSize}">
    <input id="allocationName" type="text" name="allocationCount${num}" size="5">
</c:forEach><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
