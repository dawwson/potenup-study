package org.ohgiraffers.module01servlet.chap02;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/params/*") // *: 와일드카드
public class ParamServlet extends HttpServlet {
    /*
     * [쿼리스트링 파라미터]
     * - http 요청의 url 뒤에 붙는 "?key=value&key2=value2" 형태의 데이터를 의미한다.
     * - 주로 GET 방식에서 사용되며, 클라이언트가 서버로 데이터를 전달할 때 url에 포함된다.
     * - 예 : "http://example.com?number=123&name=홍길동"에서 number=123과 name=홍길동 이 쿼리스트링과 파라미터이다.
     * - 특징 :
     *   - 데이터가 url에 노출되므로 보안에 취약할 수 있다.
     *   - 길이에 제한이 존재(브라우저나 서버에 따라 다름, 보통 2000자 내외)
     *   - httpServletRequest.getParameter("key") 메서드를 통해 쉽게 추출이 가능하다.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // setContentType: 응답 콘텐츠 타입과 문자 인코딩 설정
        // 클라이언트가 응답을 해석하는 방식을 지정한다.
        // mime 타입 (예: text/html)과 charset을 함께 정의
        resp.setContentType("text/html; charset=UTF-8");

        /*
         * 1. 쿼리스트링에서 두 개의 파라미터 가져오기
         * getParameter: 요청에서 특정 파라미터 값을 가져옴
         * - get 요청에서는 쿼리스트링(?name=John&age=25)에서 값을 추출
         * - 파라미터가 없으면 null 반환
         */
        String name = req.getParameter("name");
        String age = req.getParameter("age");

        if (name == null) name = "기본 이름";
        if (age == null) age = "기본 나이";

        // 2. 경로 값 가져오기 (path value)
        String pathInfo = req.getPathInfo(); // param 뒤의 경로 (ex: param/{1})
        String pathValue = (pathInfo != null && pathInfo.length() > 1) ? pathInfo.substring(1) : "기본 경로";

        req.setAttribute("method", "GET");
        req.setAttribute("name", name);
        req.setAttribute("age", age);
        req.setAttribute("pathInfo", pathValue);

        /*
         * 결과 페이지로 포워딩
         * getRequestDispatcher: 지정된 경로로 요청을 전달할 RequestDispatcher 객체를 반환
         * - 경로(예: /chap02/page/getParam.jsp)에 해당하는 리소스(jsp, 서블릿 등)제어를 넘기기 위한 준비
         */
        RequestDispatcher rd = req.getRequestDispatcher("/chap02/page/getParam.jsp");

        /*
         * RequestDispatcher: 요청을 다른 리소스로 전달하거나 포함시키는 객체
         * - forward 메서드를 제공하며, 서버 내부에서 처리되며, 클라이언트는 URL 변경을 인지하지 않음
         *   - forword: 현재 요청과 응답을 다른 리소스로 전달함
         */
        rd.forward(req, resp);
    }

    /*
     * [POST 방식: body에 파라미터 저장]
     * - HTTP 요청의 본문에 데이터를 포함하여 서버로 전송하는 방식이다.
     * - 주로 POST 메서드에서 사용되며, 쿼리스트링과 달리 URL에 데이터가 노출되지 않는다.
     * - 예: HTML 폼에서 <form method="post">를 통해 "key=value&key2=value2" 형태로 body에 데이터가 전송된다.
     * - 특징
     *   - 데이터가 요청 본문에 포함되므로 보안성이 쿼리스트링보다 높음
     *   - 길이 제한이 없어 대용량 데이터 전송에 적합
     *   - "httpServletRequest.getParameter("key")로 동일하게 추출 가능하며,
     *     필요시 "getInputStream()"으로 원시 데이터를 읽을 수 있음
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        if (name == null) name = "기본 사용자";
        if (pass == null) pass = "기본 기빌번호";

        req.setAttribute("method", "POST");
        req.setAttribute("name", name);
        req.setAttribute("pass", pass);
        RequestDispatcher rd = req.getRequestDispatcher("/chap02/page/postParam.jsp");
        rd.forward(req, resp);
    }
}
