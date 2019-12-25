<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<html>
<head>
    <title>资源分配情况</title>
</head>
<body>
<h3>资源分配情况</h3>
    <table border="1">
        <tr>

            <a href="jsp/success.jsp">查看菜单</a>
        </tr>

        <tr>
            <td>进程名</td>
            <td>Max</td>
            <td>Allocation</td>
            <td>Need</td>
            <td>Available</td>
        </tr>
        <tr>
            <td></td>
            <c:forEach begin="2" step="1" end="4">
                <td>
                    <c:forEach items="${resourceName}" var="str">
                        ${str}
                    </c:forEach>
                </td>
            </c:forEach>
            <td>${available}</td>
        </tr>
        <c:forEach var="num" begin="0" step="4" end="${(processNum + 1) * 4}">
            <tr>
                <c:forEach begin="${num}" step="1" end="${num + 3}" items="${processes}" var="str">
                    <td>${str}</td>
                </c:forEach>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
