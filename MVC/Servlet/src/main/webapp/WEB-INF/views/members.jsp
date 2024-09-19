<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <c:forEach var="memberList" items="${memberList}">
        <tr>
            <td>${memberList.id}</td>
            <td>${memberList.username}</td>
            <td>${memberList.age}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>