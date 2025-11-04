package org.ohgiraffers.module01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {
    @GetMapping("/register")
    public String registerOrder(Model model){
        model.addAttribute("order", "GET 방식의 주문 등록용 핸들러 메서드 호출");

        return "mappingResult";
    }

    @RequestMapping(value = {"modify", "delete"}, method = RequestMethod.POST)
    public String modifyAndDelete(Model model) {
        model.addAttribute("message", "POST 방식의 주문 수정 및 삭제용 핸들러 메서드 호출");

        return "mappingResult";
    }

    @GetMapping("/detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") String orderNo) {
        model.addAttribute("message", orderNo + "번 주문 상세 조회용 핸들러 메서드 호출");

        return "mappingResult";
    }
}
