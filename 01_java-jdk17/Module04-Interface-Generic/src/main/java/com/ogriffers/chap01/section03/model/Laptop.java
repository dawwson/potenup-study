package com.ogriffers.chap01.section03.model;

/**
 * 노트북 : 휴대용 + 충전
 */
public class Laptop extends PortableDevice implements Rechargeable {
    public Laptop(String model) {
        super(model);
    }

    @Override
    public void recharge() {
        System.out.println(super.getModel() + " 디바이스 충전 중 ");
    }
}
