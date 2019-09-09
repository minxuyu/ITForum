<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="j" uri="/jodd" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/base.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="main w clearfix">
    <div class="main-left">
        <div class="share">
            <div class="share-left"><span></span>&nbsp;分享与提问</div>
            <div class="share-right">
                <a href="toPublish.do"><span class="glyphicon glyphicon-pencil"></span>&nbsp;我要发布</a>
            </div>
        </div>
        <div class="post">
            <div class="post-wrap">
                <div class="post-choice">
                    <a href="#" class="post-choice-current">最近</a>
                    <a href="#">最热</a>
                    <a href="#" class="post-choice-last">精华</a>
                </div>

                <ul class="post-list">
                    <j:iter items="${pageBean.list}" var="post">
                        <li class="clearfix">
                            <div class="post-image">
                                <a href="toProfile.do?uid=${post.user.uid}"><img src="${post.user.headUrl}"></a>
                            </div>
                            <div class="post-content">
                                <div class="post-title"><a href="toPost.do?pid=${post.pid}">${post.title}</a></div>
                                <div class="post-other">
                                    <div class="post-other-left">
                                        <span class="post-username"><a
                                                href="toProfile.do?uid=${post.user.uid}">${post.user.username}</a></span>
                                        <span>&nbsp;发表</span>
                                        <span class="post-time">&nbsp;${post.publishTime}</span>
                                        <span>&nbsp;最后回复&nbsp;</span>
                                        <span class="post-reply-time">${post.replyTime}</span>
                                    </div>
                                    <div class="post-other-right">
                                        <span class="post-reply-count">回复 ${post.replyCount}</span>&nbsp;
                                        <span class="post-like-count">赞 ${post.likeCount}</span>&nbsp;
                                        <span class="post-scan-count">浏览 ${post.scanCount}</span>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </j:iter>

                </ul>

                <%--分页导航--%>
                <nav class="col-md-10 col-md-offset-2">
                    <ul class="pagination pagination-sm">
                        <%--首页--%>
                        <li><a href="listPostByTime.do?curPage=1">首页</a></li>
                        <%--上一页--%>

                        <j:ifelse test="${pageBean.curPage!=1 }">
                            <j:then>
                                <li><a href="listPostByTime.do?curPage=${pageBean.curPage-1 }"><span>&laquo;</span></a>
                                </li>
                            </j:then>
                            <j:else>
                                <li><span>&laquo;</span></li>
                            </j:else>
                        </j:ifelse>

                        <%--中间部分--%>

                        <%--if--%>

                        <j:ifelse test="${pageBean.allPage<=10 }">

                            <j:then>
                                <j:for start="1" end="${ pageBean.allPage}" status="i">
                                    <li class="curPage"><a href="listPostByTime.do?curPage=${i.value }">${i.value }</a>

                                    </li>
                                </j:for>
                            </j:then>

                            <j:else>
                                <j:ifelse test="${pageBean.curPage<=5 }">
                                    <j:then>
                                        <j:for start="1" end="10" status="i">
                                            <li class="curPage"><a
                                                    href="listPostByTime.do?curPage=${i.value }">${i.value }</a></li>
                                        </j:for>
                                    </j:then>

                                    <j:else>
                                        <j:ifelse test="${pageBean.allPage-pageBean.curPage<5 }">
                                            <j:then>
                                                <j:for start="${pageBean.allPage-9 }" end="${ pageBean.allPage}"
                                                       status="i">
                                                    <li class="curPage"><a
                                                            href="listPostByTime.do?curPage=${i.value }">${i.value }</a>
                                                    </li>
                                                </j:for>
                                            </j:then>

                                            <j:else>
                                                <j:for start="${pageBean.curPage-4 }" end="${ pageBean.curPage+5}"
                                                       status="i">
                                                    <li class="curPage"><a
                                                            href="listPostByTime.do?curPage=${i.value }">${i.value }</a>
                                                    </li>
                                                </j:for>
                                            </j:else>
                                        </j:ifelse>
                                    </j:else>
                                </j:ifelse>
                            </j:else>
                        </j:ifelse>

                        <%--下一页--%>

                        <j:ifelse test="${pageBean.curPage!=pageBean.allPage }">
                            <j:then>
                                <li><a href="listPostByTime.do?curPage=${pageBean.curPage+1 }"><span>&raquo;</span></a>
                                </li>
                            </j:then>
                            <j:else>
                                <li><span>&raquo;</span></li>
                            </j:else>
                        </j:ifelse>

                        <%--尾页--%>

                        <li><a href="listPostByTime.do?curPage=${pageBean.allPage}">尾页</a></li>
                    </ul>
                </nav>

            </div>
        </div>
    </div>
    <div class="main-right">

        <div class="hot-user">
            <div class="clearfix">
                <div class="hot-user-title"><span></span>&nbsp;近期活跃用户</div>
            </div>
            <ul class="hot-user-list">
                <j:iter items="${hotUserList}" var="user">
                    <li class="clearfix">
                        <a href="toProfile.do?uid=${user.uid}" class="hot-user-image"><img src="${user.headUrl}"></a>
                        <a href="toProfile.do?uid=${user.uid}" class="hot-user-name">${user.username}</a>
                    </li>
                </j:iter>
            </ul>
        </div>

        <div class="hot-user">
            <div class="clearfix">
                <div class="hot-user-title"><span></span>&nbsp;近期加入用户</div>
            </div>
            <ul class="hot-user-list">
                <j:iter items="${userList}" var="user">
                    <li class="clearfix">
                        <a href="toProfile.do?uid=${user.uid}" class="hot-user-image"><img src="${user.headUrl}"></a>
                        <a href="toProfile.do?uid=${user.uid}" class="hot-user-name">${user.username}</a>
                    </li>
                </j:iter>
            </ul>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"/>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript">
    $(function () {
        var curPage = ${pageBean.curPage};
        $(".pageNum").each(function () {
            if ($(this).text() == curPage) {
                $(this).addClass("active");
            }
        });
    });

</script>
</body>
</html>
