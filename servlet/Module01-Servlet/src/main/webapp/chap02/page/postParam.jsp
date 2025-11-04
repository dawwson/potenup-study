<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>POST 파라미터 확인</title>
</head>
<body>
<h1>POST 요청 파라미터 결과</h1>
<p><strong>요청 메서드:</strong> <%= request.getAttribute("method") %></p>
<p><strong>사용자 이름:</strong> <%= request.getAttribute("name") %></p>
<p><strong>비밀번호:</strong> <%= request.getAttribute("pass") %></p>
</body>
</html>