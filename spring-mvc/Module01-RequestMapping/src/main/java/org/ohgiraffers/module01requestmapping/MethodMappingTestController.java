package org.ohgiraffers.module01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * dispatcherServlet은 웹 요청을 받는 즉시 @Controller가 달린 컨트롤러 클래스에 처리를 위임한다.
 * 그 과정은 컨트롤러 클래스의 핸들러 메서드에 선언된 다양한 @RequestMapping 설정 내용을 따른다.
 */
@Controller
public class MethodMappingTestController {
    @RequestMapping("/menu/register")
    public String registerMenu(Model model) {
        /*
         * model 객체 addAttribute 메서드를 이용해
         * Key, value를 추가하면 추후 view에서 사용할 수 있다.
         */
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출함.");

        /*
         * 반환하고자 하는 view의 경로를 포함한 이름을 작성한다.
         * resources/templates/ 폴더 하위의 경로를 작성한다.
         */
        return "mappingResult";  // view 이름
    }

    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String modifyMenu(Model model) {
        model.addAttribute("message", "기존 메뉴 수정용 핸들러 메소드(GET) 호출함.");
        return "mappingResult";
    }

    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model) {
        model.addAttribute("message", "기존 메뉴 삭제용 핸들러 메서드(GET) 호출함.");
        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {
        model.addAttribute("message", "기존 메뉴 삭제용 핸들러 메서드(POST) 호출함.");
        return "mappingResult";
    }

    /*
     * [요청 메서드 전용 어노테이션]
     * post : @PostMapping (등록)
     * get : @GetMapping (조회)
     * put : @PutMapping (수정)
     * delete : @DeleteMapping (삭제)
     * patch : @PatchMapping (일부 수정)
     */
}
