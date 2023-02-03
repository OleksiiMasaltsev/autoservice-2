package ua.masaltsev.autoservice2.dto.mapper;

import org.springframework.stereotype.Component;
import ua.masaltsev.autoservice2.dto.request.ProductRequestDto;
import ua.masaltsev.autoservice2.dto.response.ProductResponseDto;
import ua.masaltsev.autoservice2.model.Product;

@Component
public class ProductMapper implements RequestDtoMapper<ProductRequestDto, Product>,
        ResponseDtoMapper<ProductResponseDto, Product> {
    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
