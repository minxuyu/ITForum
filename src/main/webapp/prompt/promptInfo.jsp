<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/wangEditor.css">
    <link rel="stylesheet" type="text/css" href="css/base.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <style type="text/css">
        .main div {
            margin: 200px auto;
            width: 600px;
            font-size: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="/header.jsp" flush="true"/>

<!-- 中间主体板块 -->
<div class="main w clearfix">
    <div>
        ${info}
    </div>
</div><!-- 主体结束 -->

<jsp:include page="/footer.jsp" flush="true"/>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
</body>
</html>













