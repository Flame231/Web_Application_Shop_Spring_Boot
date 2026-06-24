package org.example.webApplicationShopSpringBoot.dto.ConverterDTO.ConverterDTONew.toDTO;


import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BagDTOConverter implements Converter<Bag, BagDTOResponse> {
    @Lazy
    private ConversionService conversionService;

    @Override
    public BagDTOResponse convert(Bag bag) {
        return BagDTOResponse.builder().userId(bag.getUser().getId())
                .productDTO(conversionService.convert(bag.getProduct(), ProductDTO.class))
                .count(bag.getCount()).build();
    }
}
