package com.ogriffers.chap01.section02;

public class Dog extends Animal implements SoundMaker{
    @Override
    public void makeSound() {
        System.out.println("멍멍! " + SoundMaker.DECIBEL + " 크기");
    }
}
