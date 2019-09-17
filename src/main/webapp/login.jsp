<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page isELIgnored="false"%>--%>
<%@ taglib prefix="j" uri="/jodd" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/wangEditor.css">
    <link rel="stylesheet" type="text/css" href="css/base.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <style>
        .logo {
            text-align: center;
            margin-top: 100px;
        }
        .logo ul li {list-style: none; position: absolute; width: 390px; height: 390px; left: 50%;  margin-left: -200px;
            margin-top: -100px; }
        @-webkit-keyframes rotationp {
            0%{
                -webkit-transform: rotate(0deg);
            }
            50%{
                -webkit-transform: rotate(30deg);
            }
            0%{
                -webkit-transform: rotate(0deg);
            }
        }
        @-webkit-keyframes rotation {
            from {
                -webkit-transform: rotate(0deg);
            }
            to {
                -webkit-transform: rotate(360deg);
            }
        }
        .Rotationa {
            -webkit-transform: rotate(0deg);
            animation: rotation 64s linear infinite;
            -moz-animation: rotation 64s linear infinite;
            -webkit-animation: rotation 64s linear infinite;
            -o-animation: rotation 64s linear infinite;
        }
        .Rotationb{
            -webkit-transform: rotate(0deg);
            animation: rotationp 4s linear infinite;
            -moz-animation: rotationp 4s linear infinite;
            -webkit-animation: rotationp 4s linear infinite;
            -o-animation: rotationp 4s linear infinite;
        }
        .Rotationc {
            -webkit-transform: rotate(0deg);
            animation: rotation 16s linear infinite;
            -moz-animation: rotation 16s linear infinite;
            -webkit-animation: rotation 16s linear infinite;
            -o-animation: rotation 16s linear infinite;
        }
        .Rotationd{
            -webkit-transform: rotate(0deg);
            animation: rotationp 8s linear infinite;
            -moz-animation: rotationp 8s linear infinite;
            -webkit-animation: rotationp 8s linear infinite;
            -o-animation: rotationp 8s linear infinite;
        }
        .img {
            top:52px;
            position:relative;
            height:180px;
            width:180px;
            border-radius:90px;
        }

        .imga {
            height:199px;
            width:203px;
            position: relative;
            top:50px;
        }
        .imgb {
            height:213px;
            width:106px;
            position: relative;
            top:40px;
        }
        .imgc {
            height:253px;
            width:254px;
            position: relative;
            top:19px;
        }
        .imgd {
            height:142px;
            width:286px;
            position: relative;
            top:72px;
        }
    </style>
</head>
<body>

<jsp:include page="head.jsp"/>
<!-- 中间主体板块 -->
<div class="main w clearfix">

    <div class="buttons clearfix">
        <a href="#" id="login-button" class="selected"><span class="glyphicon glyphicon-user"></span>&nbsp;登录</a>
        <a href="#" id="register-button" class="unselected"><span class="glyphicon glyphicon-pencil"></span>&nbsp;注册</a>
    </div>

    <div class="contents">
        <div class="logo">
            <ul>
                <li><img class="img" src="image/portrait.jpeg"></li>
                <li><img class="Rotationa imga" src="image/portrait_outline_1.png"></li>
                <li><img class="Rotationb imgb" src="image/portrait_outline_2.png"></li>
                <li><img class="Rotationc imgc" src="image/portrait_outline_3.png"></li>
                <li><img class="Rotationd imgd" src="image/portrait_outline_4.png"></li>
            </ul>
        </div>
        <div id="login-area">
            <form action="login.do" method="post">
                <div class="error-message">${error}</div>
                <div class="email">
                    邮箱&nbsp;
                    <input id="login-email" type="text" name="FormEmail" value="${email}" required>
                </div>
                <div class="password">
                    密码&nbsp;
                    <input type="password" name="password" required>
                </div>
                <button id="login-submit">立即登录</button>
                <button type="button" id="forget-password">忘记密码</button>
            </form>
        </div>
        <div id="register-area">
            <form action="register.do" method="post">
                <div id="error-message" class="error-message">${error}</div>
                <div class="email">
                    邮箱&nbsp;
                    <input type="text" name="FormEmail" value="${email}" id="email" required>
                </div>
                <div class="password">
                    密码&nbsp;
                    <input type="password" name="password" id="password" required>
                </div>
                <div class="password relative clearfix">
                    <span style="position: absolute;left: -30px;">重复密码&nbsp;</span>
                    <input type="password" name="repassword" id="repassword" required
                           style="position: absolute;left: 40px;">
                </div>
                <div class="relative">
                    <button id="register-submit">立即注册</button>
                </div>
            </form>
        </div>
    </div>
</div><!-- 主体结束 -->
<jsp:include page="footer.jsp"/>

<j:if test="${status == 'yes'}">
    <script type="text/javascript">
        window.location.href='/toMyProfile.do';
    </script>
</j:if>

<j:if test="${status == 'toIndex.do'}">
    <script type="text/javascript">
        window.location.href='/toIndex.do';
    </script>
</j:if>

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript">
    $(function () {
        var loginButton = $("#login-button");
        var registerButton = $("#register-button");
        var lArea = $("#login-area");
        var rArea = $("#register-area");
        rArea.hide();

        loginButton.click(function () {
            loginButton.addClass("selected");
            loginButton.removeClass("unselected");
            registerButton.addClass("unselected");
            registerButton.removeClass("selected");
            lArea.show();
            rArea.hide();
        });

        registerButton.click(function () {
            registerButton.addClass("selected");
            registerButton.removeClass("unselected");
            loginButton.addClass("unselected");
            loginButton.removeClass("selected");
            lArea.hide();
            rArea.show();
        });

        if (location.href.indexOf("#register") >= 0) {
            registerButton.click();
        } else {
            loginButton.click();
        }

        //根据是否是注册跳回来，切换到注册页面
        var hideInfo = "${register}";
        if (hideInfo != null && hideInfo != "") {
            registerButton.click();
        }


        //输入校验
        //校验邮箱
        $("#email").blur(function () {
            var value = $(this).val();
            if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
                $("#error-message").text("邮箱格式错误啦~");
            } else {
                $("#error-message").text("");
            }
        });

        //忘记密码
        $("#forget-password").click(function () {
            //alert($("#login-email").val());
            $.ajax({
                type: "GET",
                url: "forgetPassword.do",
                data: {email: $("#login-email").val()},
                success: function (response, status, xhr) {
                    location.href = "afterForgetPassword.do";
                }
            });
        });
    });


</script>
</body>
</html>

