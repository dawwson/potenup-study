package org.ohgiraffers.module01servlet.chap03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exception")
public class ExceptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            String numberStr = req.getParameter("number");

            if (numberStr == null) {
                throw new IllegalArgumentException("숫자를 입력해주세요.");
            }

            int number = Integer.parseInt(numberStr);

            out.println("<html> <body>");
            out.println("<h1>입력한 숫자 : " + numberStr + "</h1>");
            out.println("</body> </html>");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<html> <body>");
            out.println("<p> 입력값 : " + req.getAttribute("number") + "</p>");
            out.print("<a href='chap03/index/html'> 다시 시도</a>");
            out.println("</body> </html>");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<html> <body>");
            out.println("<h1> 오류 " + e. getMessage() + "</h1>");
            out.println("<a href='/chap03/index.html'> 다시 시도</a>");
            out.println("</body> </html>");

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "500번 에러 발생");
        }
    }
}
