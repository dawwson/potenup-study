package com.ogriffers.chap01.section03.model;

public class SmartPhone extends PortableDevice implements Rechargeable {
    public SmartPhone(String model) {
        super(model);
    }

    @Override
    public void recharge() {
        System.out.println(super.getModel() + " 충전 중");
    }
}
