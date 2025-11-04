package org.ohgiraffers.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {
    @GetMapping("/other-null")
    public String otherNullPointerException() {
        String str = null;
        System.out.println(str.charAt(0));
        return "/";
    }

    @GetMapping("other-controller-array")
    public String otherArrayExceptionTest() {
        double[] array = new double[3];
        System.out.println(array[1]);
        return "/";
    }
}
