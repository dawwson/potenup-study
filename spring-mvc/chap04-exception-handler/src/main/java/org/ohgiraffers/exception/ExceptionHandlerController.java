package org.ohgiraffers.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {
    @GetMapping("controller-null")
    public String nullPointerException() {
        String str = null;
        System.out.println(str.charAt(0));

        return "/";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerException(NullPointerException e) {
        System.out.println("🚨 NullPointerException 발생!");
        return "error/nullPointer";
    }

    @GetMapping("controller-user")
    public String userExceptionTest() {
        boolean check = true;

        if (check) {
            throw new MemberRegistException("사용할 수 없는 권한입니다.");
        }

        return "/";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException e) {
        System.out.println("controller 레벨의 exception 처리");
        model.addAttribute("exception", e.getMessage());
        return "error/memberRegist";
    }
}
