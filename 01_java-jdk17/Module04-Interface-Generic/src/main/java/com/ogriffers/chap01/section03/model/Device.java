package com.ogriffers.chap01.section03.model;

public abstract class Device {
    private String name;
    private String model;

    // 생성자를 만들어놓으면 컴파일러가 기본 생성자를 자동으로 추가하지 않음
    public Device(String model) {
        this.model = model;
    }

    public abstract void powerOn();
    public abstract void powerOff();

    // private 필드에 접근하기 위해 만든 getter / setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
