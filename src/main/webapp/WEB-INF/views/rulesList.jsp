<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: summer
  Date: 2019/3/29
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>rules</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="./static/js/jquery.min.js"></script>
    <script src="./static/js/bootstrap.min.js"></script>
</head>
<body>
<div style="height: 43px; background-color: #1e94f3">
    title
</div>
<div>
    <div class="row">
        <div class="col-md-2">
            <jsp:include page="navigation.jsp"/>
        </div>
        <div class="col-md-10">
            <c:forEach items="${rules}" var="keyword" varStatus="id">
                <a href="edit.do?fileName=${keyword}" class="list-group-item">${keyword}</a>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
