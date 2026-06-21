package org.example.webApplicationShopSpringBoot.dto.dto;

public interface ConverterDTONew<T, V, N> {

    T toEntity(V v);

    N toDTO(T t);
}
