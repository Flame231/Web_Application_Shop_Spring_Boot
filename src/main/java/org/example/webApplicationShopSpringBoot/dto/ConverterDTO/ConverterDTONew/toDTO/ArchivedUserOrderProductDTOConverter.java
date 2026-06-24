package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArchivedUserOrderProductDTOConverter implements Converter<ArchivedUserOrderProduct, ArchivedUserOrderProductDTO> {

    @Override
    public ArchivedUserOrderProductDTO convert(ArchivedUserOrderProduct archivedUserOrderProduct) {
        return ArchivedUserOrderProductDTO
                .builder()
                .archivedUserOrder(archivedUserOrderProduct.getArchivedUserOrder())
                .productId(archivedUserOrderProduct.getProductId())
                .productName(archivedUserOrderProduct.getProductName())
                .productCount(archivedUserOrderProduct.getProductCount())
                .finalProductCount(archivedUserOrderProduct.getFinalProductCount())
                .price(archivedUserOrderProduct.getPrice())
                .createDateTime(archivedUserOrderProduct.getCreateDateTime())
                .build();
    }
}
