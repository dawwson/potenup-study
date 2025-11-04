package org.ohgiraffers.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * 1. implements HandlerInterceptor 상속
 *   - spring mvc에서 컨트롤러의 실행 전/후를 가로챌 수 있게 하는 인터셉터 인터페이스이다.
 *
 * 2. 생성자 주입
 *   - @Autowired 필드 주입 대신 생성자를 통해 bean을 주입받을 수 있다.
 *   - final 키워드를 사용하여 불변성을 보장받는다.
 */
@Component
public class StopWatchInterceptor implements HandlerInterceptor {

    private final MenuService menuService;

    @Autowired
    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    /*
     * preHandler (전처리)
     * - 컨트롤러 메서드가 실행되기 전에 호출된다.
     * - 요청 처리 시작 시간(start time)을 측정하여 request 객체에 저장한다.
     * - return true : 요청을 계속 진행하여 컨트롤러를 호출한다.
     * - return false : 요청을 중단하고 컨트롤러를 호출하지 않는다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("✅ preHandle 호출함...");

        long startTime = System.currentTimeMillis();

        request.setAttribute("startTime", startTime);

        // true이면 컨트롤러를 이어서 호출한다. false이면 핸들러 메소드를 호출하지 않는다.
        return true;
    }

    /*
     * postHandler (후처리)
     * - 컨트롤러 메서드가 실행된 직후, view가 렌더링되기 전에 호출된다.
     * - preHandle에서 저장한 startTime을 request 객체에서 다시 꺼낸다.
     * - 종료 시간(end time)을 측정하여, 총 소요시간(*interval)을 계산한다.
     * - modelAndView.addObject() : 계산된 소요 시간을 model에 추가하여,
     *   view 페이지에서 이 값을 표시할 수 있게 된다.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("✅ postHandle 호출함...");

        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");

        long endTime = System.currentTimeMillis();
        long interval = endTime - startTime;

        modelAndView.addObject("interval", interval);
    }

    /*
     * afterCompletion (뷰 렌더링 후 처리)
     * - view 렌더링까지 모두 완료된 후, 응답이 나가기 직전에 호출된다.
     * - try-catch-finally 블록처럼 예외 발생 여부와 상관없이 항상 호출된다.
     * - 리소스 정리(clean-up)이나 최종 로그, 주입받은 서비스의 후처리 작업을 수행하기에 적합하다.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("✅ afterCompletion 호출함...");
        menuService.method();
    }
}
