package com.ogriffers.chap01.section02;

/**
 * 인터페이스 - 역할을 정의하는 계약
 * 인터페이스란?
 * 객체가 수행해야 할 역할 또는 행위를 정의하는 순수한 계약이다.
 * - 무엇을 할 수 있는지를 정의하지만, 어떻게 하는지는 정의하지 않는다. (구현체가 책임을 가짐)
 *   즉, 코드를 작성하지 않고 메서드만 작성한다.
 * implements 키워드를 통해 클래스는 여러 개의 인터페이스, 즉 여러 개의 역할을 동시에 수행할 수 있다.
 *
 * 인터페이스 vs 추상 클래스
 * - 추상 클래스  is-a 관계 "A는 B의 한 종류이다." dog is a Animal -> 종족의 개념
 * - 인터페이스 : can-do 관계 "A는 B를 할 수 있다." bird can do Flyable -> 역할 개념
 *
 * 인터페이스는 다음과 같은 특징을 갖는다.
 * 1. 메서드는 자동으로 public과 abstract로 간주된다. (생략 가능)
 * 2. 필드는 자동으로 public static final로 간주되어 상수로 취급한다.
 * 3. 다중 상속을 지원하며 여러 인터페이스를 구현할 수 있다.
 * 4. 서로 다른 클래스 간의 공통된 기능을 정의하여 코드의 재사용성을 높인다.
 */
public interface SoundMaker {
    public final int DECIBEL = 2; // static 영역에 올라가서 바로 참조 가능
    // 상수의 특징 : 대문자로 쓴다. 값을 안 주면 안 된다.

    public abstract void makeSound();
}
