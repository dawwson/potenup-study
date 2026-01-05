package com.ogriffers.chap01.section03.model;

public class PortableDevice extends Device {
    public PortableDevice(String model) {
        super(model);
    }

    @Override
    public void powerOn() {
        System.out.println("Powering on 휴대용 기기 " + super.getModel());
    }

    @Override
    public void powerOff() {
        System.out.println("Powering off 휴대용 기기 " + super.getModel());
    }
}
