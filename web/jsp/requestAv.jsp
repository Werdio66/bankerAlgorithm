<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<html>
<head>
    <title>请求资源</title>
</head>
<body>
    <h3>请求资源</h3>
    <form action="/init?cmd=request" method="post">
        请输入请求资源的名称：<input type="text" name="reqName"><br>
        请输入请求的资源：
        <c:forEach var="num" begin="1" step="1" end="${resourceSize}">
            <input type="text" name="reqCount${num}">
        </c:forEach><br>
        <input type="submit" value="发出请求">
    </form>
</body>
</html>
