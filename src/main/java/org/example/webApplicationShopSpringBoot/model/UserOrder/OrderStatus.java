package org.example.webApplicationShopSpringBoot.model.UserOrder;

import lombok.Getter;

@Getter
public enum OrderStatus {


    CREATED("Оформлен"),
    READY("Готов к выдаче"),
    REFUSED("Отказ"),
    CLOSED("Закрыт");
    private final String description;
    OrderStatus(String description) {
        this.description = description;
    }
}
