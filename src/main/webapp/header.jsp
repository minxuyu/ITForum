<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="j" uri="/jodd" %>


<div class="header clearfix">
    <div class="w">
        <h1 class="logo"><a href="toIndex.do">IT</a></h1>
        <ul class="left-nav">
            <li class="current-nav"><a href="toIndex.do">首页</a></li>
            <li><a href="listTopic.do">话题</a></li>
            <li><a href="listImage.do">文章</a></li>
            <li><a href="toMessage.do">消息</a></li>
        </ul>

        <ul class="right-nav">
            <j:ifelse test="${sessionScope.uid != null}">
                <j:then>
                    <li class="login2 relative">
                        <a href="toMyProfile.do" id="profile"><img src="${sessionScope.headUrl}"></a>
                        <ul id="down-menu">
                            <li><a href="toMyProfile.do">个人主页</a></li>
                            <li><a href="logout.do">退出登录</a></li>
                        </ul>
                    </li>
                </j:then>
                <j:else>
                    <li class="login">
                        <a href="toLogin.do">登录</a>
                        <a href="toLogin.do">/</a>
                        <a href="toLogin.do#register">注册</a>
                    </li>
                </j:else>
            </j:ifelse>
            <li>
                <a href="#"><span class="glyphicon glyphicon-search"></span></a>
            </li>
            <li><input type="text"></li>
        </ul>
    </div>
</div>
