<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<!-- 상대 경로 사용, [현재 URL이 속한 계층 경로 + /save -->
<%--WEB-INF안에 있는 파일들은 무조건 컨트롤러를 거쳐야 화면을 쏴줌--%>
<form action="save" method="post">
    username: <input type="text" name="username" />
    age :     <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
<a href="/index.html">메인</a>
</body>
</html>
