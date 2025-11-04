<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
<%@ %>는 jsp 지시자(directive)로, 페이지 설정을 정의한다.
page 지시자는 이 jsp 페이지의 속성을 지정하는데 사용된다.
contentType="text/html;charset=UTF-8" 이 페이지가 html 형식으로 출력되며, 문자 인코딩을 utf-8로 설정한다.
language="java"이 jsp 페이지에서 사용할 스크립트 언어가 java임을 나타낸다.
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
"<%= %>"
jsp 표현식(expression)으로, java 코드의 결과값을 html에 출력한다.
request는 jsp 내장 객체(implicit object)로, 클라이언트의 요청 정보를 담고 있다.
getAttribute("method"): request 객체에 저장된 "method"라는 이름의 속성 값을 가져온다.
이 값은 서블릿에서 setAttribute()로 설정된 데이터이다.
--%>
    <p>
        <strong>요청 메서드 : </strong>
        <%= request.getAttribute("method")%>
    </p>
    <p>
        <strong>쿼리 스트링 - 이름 : </strong>
        <%= request.getAttribute("name")%>
    </p>
    <p>
        <strong>쿼리 스트링 - 나이 : </strong>
        <%= request.getAttribute("age")%>
    </p>
    <p>
        <strong>경로 값 : </strong>
        <%= request.getAttribute("pathInfo")%>
    </p>
</body>
</html>
