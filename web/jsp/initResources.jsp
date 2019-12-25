<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>初始化</title>
</head>
<body>
    <h3>初始化资源</h3>
    <form action="/init?cmd=resource" method="post">
        请输入资源名：<input type="text" name="resourceName"/><br>
        请输入资源数量：<input type="text" name="resourceCount"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
