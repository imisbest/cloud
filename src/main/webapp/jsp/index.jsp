<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
    <title>APlus云盘-登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords"
          content="Eminent Login Form Widget Responsive, Login form web template,Flat Pricing tables,Flat Drop downs  Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design"/>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <!-- font files -->
    <!--<link href='//fonts.googleapis.com/css?family=Raleway:400,100,200,300,500,600,700,800,900' rel='stylesheet' type='text/css'>-->
    <!--<link href='//fonts.googleapis.com/css?family=Poiret+One' rel='stylesheet' type='text/css'>-->
    <!-- /font files -->
    <!-- css files -->
    <link href="${path}/static/css/style.css" rel='stylesheet' type='text/css' media="all"/>
    <!-- /css files -->
</head>
<body>
<script type="text/javascript">
    var sysMsg = "${sysMsg}"
    if (sysMsg != "")
        alert(sysMsg);
</script>
<h1>云盘</h1>
<div class="form-w3ls">
    <ul class="tab-group cl-effect-4">
        <li class="tab active"><a href="#signin-agile">登录</a></li>
        <li class="tab"><a href="#signup-agile">注册</a></li>
    </ul>
    <div class="tab-content">
        <div id="signin-agile">
            <form action="${path}/user/login" method="post">
                <p class="header">用户名</p>
                <input type="text" name="username" value="1" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '';}">
                <p class="header">密码</p>
                <input type="password" name="password" value="1" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '';}">
                <p>${sysMsg}</p>
                <input type="submit" class="sign-in" value="登录">
            </form>
        </div>
        <div id="signup-agile">
            <form action="${path}/user/regist" method="post">

                <p class="header">用户名(登录名)</p>
                <input id="username" type="text" name="username" value="" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '';}">

                <p class="header">邮箱</p>
                <input id="email" type="text" name="email" value="" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '';}">

                <p class="header">密码</p>
                <input id="password" type="password" name="password" value="" onfocus="this.value = '';"
                       onblur="if (this.value === '') {this.value = '';}">

                <p class="header">确认密码</p>
                <input id="rePassword" type="password" name="rePassword" value="" onfocus="this.value = '';"
                       onblur="if (this.value == '') {this.value = '';}">

                <input type="submit" class="register" value="注册"/>
            </form>
        </div>
    </div>
</div>
<p class="copyright">© 2017-2018 APlus. All Rights Reserved | Design by APlus</p>
<script src='${path}/static/js/jquery.min.js'></script>
<script src="${path}/static/js/index.js"></script>
<script type="text/javascript">
</script>
</body>
</html>