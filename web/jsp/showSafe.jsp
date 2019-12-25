<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>安全序列</title>
</head>
<body>
    <h3>安全序列</h3>
    <table border="1">
        <tr>
            <a href="jsp/success.jsp">查看菜单</a>
        </tr>

        <tr>
            <td>进程名</td>
            <td>Work</td>
            <td>Need</td>
            <td>Allocation</td>
            <td>WorkAndAllocation</td>
            <td>Finish</td>
        </tr>
        <tr>
            <td></td>
            <c:forEach begin="2" step="1" end="5">
                <td>
                    <c:forEach items="${resourceName}" var="str">
                        ${str}
                    </c:forEach>
                </td>
            </c:forEach>

        </tr>
        <c:forEach var="num" begin="0" step="6" end="${(processNum + 1) * 6}">
            <tr>
                <c:forEach begin="${num}" step="1" end="${num + 5}" items="${safeSerial}" var="str">
                    <td>${str}</td>
                </c:forEach>
            </tr>
        </c:forEach>
        <div style="color: red">
            ${string}
        </div>
    </table>
</body>
</html>
