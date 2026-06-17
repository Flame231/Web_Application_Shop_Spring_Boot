package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;

public interface ConverterDTO<T, V> {

    T toEntity(V v);

    V toDTO(T t);
}
