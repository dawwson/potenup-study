package com.ogriffers.chap01.section02;

// extends가 implements 보다 앞으로 와야 한다.
// 상속은 한개만 되지만, 구현은 여러개 가능하기 때문에 열거될 가능성이 있기 때문이다.
public class Cat extends Animal implements SoundMaker {
    @Override
    public void makeSound() {
        System.out.println("야옹! " + SoundMaker.DECIBEL + " 크기로");
    }
}
