package com.ogriffers.chap02.section03;
/*
 * =====================================
 *  🏆 [기초] 제네릭스 와일드카드 - extends와 super 쉽게 이해하기
 * =====================================
 *
 * 💡 목적:
 *  - 초급: extends와 super 기본 역할 이해.
 *  - 중급: 음식 상자 예제로 와일드카드 응용.
 *  - 고급: 간단한 PECS 원칙 소개.
 *
 * 💡 와일드카드란?
 *  - 제네릭스에서 "어떤 타입"을 유연하게 다루는 방법이에요.
 *  - 문법:
 *    - <? extends 타입>: 그 타입과 그 아래만 허용 (꺼내기 쉬움).
 *    - <? super 타입>: 그 타입과 그 위만 허용 (넣기 쉬움).
 *  - 초급 이해: "상자에 물건 넣거나 꺼낼 때 크기 조정하는 스티커야!"
 *
 * 💡 예제 설명:
 *  - 음식 상자(FoodBox)에 과일(Fruit), 사과(Apple), 포도(Grape)를 넣고 꺼내보자.
 *  - extends: 과일 상자에서 사과 꺼내기.
 *  - super: 사과를 과일 상자에 넣기.
 *
 * 💡 설계 과정 (초급→고급):
 *  - 1) 기본 상자 (초급):
 *    - FoodBox<Apple>은 사과만 담음 → 다른 과일 다루려면 유연성 부족.
 *  - 2) extends로 꺼내기 (중급):
 *    - <? extends Fruit>: 과일과 그 하위(사과, 포도) 꺼내기 가능.
 *  - 3) super로 넣기 (고급):
 *    - <? super Apple>: 사과를 과일 상자에 넣기 가능.
 */

import com.ogriffers.chap02.section03.model.Apple;
import com.ogriffers.chap02.section03.model.FoodBox;
import com.ogriffers.chap02.section03.model.Fruit;
import com.ogriffers.chap02.section03.model.Grape;

public class Application {
    public static void main(String[] args) {
        System.out.println("=== 기본 상자 ===");
        FoodBox<Apple> appleBox = new FoodBox<>();
        appleBox.put(new Apple("Red Apple"));
        System.out.println("꺼낸 물건: " + appleBox.get()); // Red Apple
        // appleBox.put(new Grape("Green Grape")); // 오류: Apple만 가능

        System.out.println("\n=== extends 와일드카드 - 꺼내기 ===");
        FoodBox<Apple> appleOnlyBox = new FoodBox<>();
        appleOnlyBox.put(new Apple("Green Apple"));

        FoodBox<Grape> grapeOnlyBox = new FoodBox<>();
        grapeOnlyBox.put(new Grape("Purple Grape"));

        showFruit(appleOnlyBox); // Green Apple
        showFruit(grapeOnlyBox); // Purple Grape


        System.out.println("\n=== super 와일드카드 - 넣기 ===");
        FoodBox<Fruit> fruitBox = new FoodBox<>();
        putApple(fruitBox, new Apple("Yellow Apple"));
        System.out.println("상자에 넣은 후: " + fruitBox.get()); // Yellow Apple

        // putApple에 포도를 넣으려고 한다면?
        // 포도는 Apple의 상위타입이 아니기 때문에 오류 발생!
        // putApple(fruitBox, new Grape("Blue Grape"));
    }

    // extends: Fruit와 그 하위 타입 꺼내기 허용
    private static void showFruit(FoodBox<? extends Fruit> box) {
        Fruit fruit = box.get(); // 꺼내기 가능
        System.out.println("꺼낸 과일: " + fruit);

        // DeepDive
        // box.put(new Apple("New Apple")); 오류 발생
        /*
         * 이유: box는 FoodBox<? extends Fruit>로 정의되어 있어,
         * 어떤 특정 서브타입(FoodBox<Apple>, FoodBox<Banana> 등)인지 컴파일러가 알 수 없다.
         * showFruit을 호출하는 것을 보면 "포도 박스", "사과 박스"가 매개변수로 전달됨.
         *
         * 따라서, box에 Apple을 넣는 것은 타입 불일치를 초래할 수 있으므로,
         * 컴파일러는 이를 안전하지 않다고 판단하여 오류를 발생시킨다.
         * 꺼내는 것은 안전하지만(모든 서브타입은 Fruit으로 처리 가능), 넣는 것은 허용되지 않는다.
         * */
    }

    // super: Apple과 그 상위 타입 넣기 허용
    private static void putApple(FoodBox<? super Apple> box, Apple apple) {
        box.put(apple); // 넣기 가능

        // DeepDive
        // Apple fruit = box.get(); // 오류: 꺼내면 타입 보장 불가
        /*
         * 꺼낼 수 없는 이유:
         * - box는 FoodBox<? super Apple>로 정의되어 있어, Apple의 상위 타입을 허용한다.
         * - 따라서 box에 들어 있는 객체는 Apple이 아닐 수도 있으며,
         *   예를 들어, Fruit 또는 Object 타입일 수 있다.
         * - get() 메서드 호출 시 반환되는 타입이 보장되지 않기 때문에
         *   이를 Apple로 직접 캐스팅하는 것이 안전하지 않는다.
         * - 타입 안전성을 보장하기 위해 컴파일러는 오류를 발생시킨다.
         *
         * 즉, Box에 타입 제한이 Apple의 상위 타입이기 때문에
         * Apple만 들어있다는 보장이 불가능함.
         */
    }

    /*
     * 💡 [심화] PECS 원칙 (Producer Extends, Consumer Super)
     * 제네릭 와일드카드를 언제 사용해야 하는지에 대한 핵심 원칙입니다.
     *
     * 1. Producer Extends (생산자/공급자는 extends)
     * - 컬렉션에서 데이터를 '꺼내기만(읽기만)' 하는 경우 `<? extends T>`를 사용합니다.
     * - 왜? `List<? extends Fruit>`에는 `Apple`이 들어있을 수도, `Grape`가 들어있을 수도 있습니다.
     *   하지만 어떤 과일이든 `Fruit` 타입으로 안전하게 꺼내서 사용할 수 있다. (`Apple`도 `Fruit`이고, `Grape`도 `Fruit`이므로)
     * - "읽기 전용 컬렉션을 만들 때 사용한다" 라고 이해하면 쉽습니다.
     *
     * 2. Consumer Super (소비자/사용자는 super)
     * - 컬렉션에 데이터를 '넣기만(쓰기만)' 하는 경우 `<? super T>`를 사용합니다.
     * - 왜? `List<? super Apple>`에는 `Apple`을 안전하게 넣을 수 있습니다.
     *   이 리스트가 `List<Apple>`, `List<Fruit>`, `List<Object>` 중 어떤 것이든 `Apple`은 항상 해당 타입으로 취급될 수 있기 때문입니다.
     * - "쓰기 전용 컬렉션을 만들 때 사용한다" 라고 이해하면 쉽습니다.
     *
     * 🚀 실무 관점: "꺼낼 땐 extends, 넣을 땐 super"
     * 이 원칙은 메서드의 파라미터 타입을 더 유연하게 만들어 API의 재사용성을 극대화합니다.
     * 예를 들어, `Collections.copy(List<? super T> dest, List<? extends T> src)` 메서드는
     * `src`에서는 T타입을 안전하게 꺼내고(`extends`), `dest`에는 T타입을 안전하게 넣기(`super`) 위해 PECS 원칙을 완벽하게 따르고 있습니다.
     */
}