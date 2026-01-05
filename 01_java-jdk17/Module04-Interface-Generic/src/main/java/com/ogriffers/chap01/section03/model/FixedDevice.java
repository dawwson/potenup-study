package com.ogriffers.chap01.section03.model;

public class FixedDevice extends Device {
    public FixedDevice(String model) {
        super(model);
    }

    // 추상 클래스를 상속받으면 추상 메서드를 구현해야 할 책임이 있다.
    @Override
    public void powerOn() {
        System.out.println("Powering on 고정형" + super.getModel());
    }

    @Override
    public void powerOff() {
        System.out.println("Powering off 고정형" + super.getModel());
    }
}
