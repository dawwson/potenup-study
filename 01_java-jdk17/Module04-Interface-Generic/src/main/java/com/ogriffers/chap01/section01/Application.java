package com.ogriffers.chap01.section01;

/**
 * 상속의 한계 : 역할을 유연하게 부여할 수 없을까?
 *
 * 문제 상황
 * - module03에서 animal이라는 추상 클래스로 소리를 내는 책임을 구현하였다.
 * - 그런데, 동물 세계에서 새로운 역할 나는 능력(flyalble)을 추가해야 한다면 어떨까?
 * - bird는 날 수 있지만 dog나 fish는 날 수 없다.
 * - 만약 'animal' 클래스에 fly()라는 추상 메서드를 추가하면 모든 동물이 날아야 하는 이상한 설계가 시작된다. (is-a 관계의 핵심)
 * - 그렇다고 flyingAnimal이라는 클래스를 새로 만들면, 헤엄치는(swimmable) 능력까지 추가될 경우
 *   flyingAndSwimmingAnimal 같은 끔찍한 클래스 계층 구조가 만들어진다.
 *
 * 상속의 근본적인 한계
 * - 자바는 단일 상속만 지원하므로, 클래스는 오직 하나의 부모만 가질 수 있다.
 * - 즉, "A는 B이다(is-a)" 과녜는 단 하나만 설정 가능하며, 날 수 있는 동시에 헤엄칠 수 잇는 것과 같은 다중 역할을 표현하기 어렵다.
 *r
 * 해결 아이디어
 * 클래스의 소속(is-a)과 별개로 객체가 수행할 수 있는 역할이나 기능을 자유롭게 붙였다 뗄 수 있는 방법은 없을까?
 * -> 이 아이디어가 바로 인터페이스이다.
 */
public class Application {
    public static void main(String[] args) {
        Animal[] animals = {new Cat(), new Dog(), new Snake()};

        for (Animal animal : animals) {
            animal.makeSound();
        }
    }
}
