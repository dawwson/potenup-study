package org.ohgiraffers.handler;

public class MenuDTO {
    private String name;
    private int price;
    private int categoryName;
    private String orderableStatus;

    public MenuDTO() {}

    public MenuDTO(String name, int price, int categoryName, String orderableStatus) {
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.orderableStatus = orderableStatus;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCategoryName() {
        return categoryName;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategoryName(int categoryName) {
        this.categoryName = categoryName;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", categoryName=" + categoryName +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
