package org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class EditProductDTO {

    private ProductDTO productDTO;

    private List<ProductCategoryDTO> productCategoryDTOList;

    private List<SellerDTO> sellerDTOList;

}
