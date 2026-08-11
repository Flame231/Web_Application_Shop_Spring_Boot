package org.example.webApplicationShopSpringBoot.model;

import lombok.Getter;

@Getter
public enum ItemStatus {
    ACTIVE("Доступен"),
    DELETED("Удалён");
    private final String description;

    ItemStatus(String description) {
        this.description = description;
    }
}
