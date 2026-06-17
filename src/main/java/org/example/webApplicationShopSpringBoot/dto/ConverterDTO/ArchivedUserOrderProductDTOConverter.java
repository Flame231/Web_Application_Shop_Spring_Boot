package org.example.webApplicationShopSpringBoot.dto.ConverterDTO;


import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ArchivedUserOrderProductDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;

public class ArchivedUserOrderProductDTOConverter implements ConverterDTO<ArchivedUserOrderProduct, ArchivedUserOrderProductDTO> {
    private ConverterDTO<ArchivedUserOrder, ArchivedUserOrderDTO> converterDTO = new ArchivedUserOrderDTOConverter();

    @Override
    public ArchivedUserOrderProduct toEntity(ArchivedUserOrderProductDTO archivedUserOrderProductDTO) {
        return null;
    }

    @Override
    public ArchivedUserOrderProductDTO toDTO(ArchivedUserOrderProduct archivedUserOrderProduct) {
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
