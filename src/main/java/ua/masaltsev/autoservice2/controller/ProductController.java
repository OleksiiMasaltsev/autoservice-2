package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.ProductMapper;
import ua.masaltsev.autoservice2.dto.request.ProductRequestDto;
import ua.masaltsev.autoservice2.dto.response.ProductResponseDto;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.service.ProductService;

@RestController
@RequestMapping("/products")
@Tag(name = "Product controller")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @Operation(summary = "save new product")
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(productService.save(productMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a product")
    public ProductResponseDto update(@RequestBody ProductRequestDto requestDto,
                                     @PathVariable Long id) {
        Product product = productMapper.mapToModel(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }
}
