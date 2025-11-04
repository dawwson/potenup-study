package org.ohgiraffers.chap03viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/*")
public class ResolverController {
    @GetMapping("String")
    public String stringReturning(Model model) {
        model.addAttribute("message", "String 반환 타입 테스트");

        /*
         * 문자열로 뷰 이름을 반환한다는 것은 반환 후 ThymeleafViewResolver에게 resources/templates/를 prefix로
         * .html을 suffix로 붙여서 최종 뷰 경로를 설정하라는 의미가 된다.
         */
        return "result";
    }

    @GetMapping("string-redirect")
    public String stringRedirect() {
        // 접두사로 redirect를 사용하게 되면 forward가 아닌 redirect 방식으로 동작하게 된다.
        return "redirect:/main";
    }

    @GetMapping("string-redirect-attr")
    public String stringRedirectAttr(RedirectAttributes rttr) {
        rttr.addFlashAttribute("flashMessage1", "리다이렉트 시 속성 전송 테스트");
        return "redirect:/";
    }

    @GetMapping("modelandview")
    public ModelAndView modelAndView(ModelAndView mv) {
        mv.addObject("forwardMessage", "ModelAndView 반환 타입 테스트");
        mv.setViewName("result");
        return mv;
    }

    @GetMapping("modelandview-redirect")
    public ModelAndView modelAndViewRedirect(ModelAndView mv) {
        mv.setViewName("redirect:/main");
        return mv;
    }

    @GetMapping("modelandview-redirect-attr")
    public ModelAndView modelAndViewRedirect(ModelAndView mv, RedirectAttributes rttr) {
        rttr.addFlashAttribute("flashMessage2", "ModelAndView 리다이렉트 속성 전송 테스트");
        mv.setViewName("redirect:/");
        return mv;
    }
}
