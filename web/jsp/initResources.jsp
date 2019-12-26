<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>初始化</title>
    <!--导入jquery-->
    <script src="js/jquery-3.3.1.js"></script>
    <script>
        // 表单校验
        function checkResourceName() {
            //
            var resName = $("#resName").val();
            // 定义正则
            var reg_resName = /^\w+$/;
            // 检验是否正确，返回boolean类型
            var flag = reg_resName.test(resName);
            if (flag){
                $("#resName").css("border","");
            } else {
                // 不正确边框边红
                $("#resName").css("border","1px solid red");
            }
            return flag;
        }
        // 当表单提交时调用所有的校验方法
        $(function () {
            $("#rescourcesForm").submit(function () {
                return checkResourceName();
            })
        });
    </script>
</head>
<body>
    <h3>初始化资源</h3>
    <div style="color: blue">
        资源数：${resourceSize}<br>
    </div>
    <form id="rescourcesForm" action="/init?cmd=resource" method="post">
        请输入资源名：<input id="resName" type="text" name="resourceName"/><br>
        请输入资源数量：<input type="text" name="resourceCount"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
