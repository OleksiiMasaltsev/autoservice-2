package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.OrderingMapper;
import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.service.OrderingService;
import ua.masaltsev.autoservice2.service.ProductService;

@RestController
@RequestMapping("/orderings")
@Tag(name = "Ordering controller")
public class OrderingController {
    private final OrderingService orderingService;
    private final ProductService productService;
    private final OrderingMapper orderingMapper;

    public OrderingController(OrderingService orderingService,
                              ProductService productService,
                              OrderingMapper orderingMapper) {
        this.orderingService = orderingService;
        this.productService = productService;
        this.orderingMapper = orderingMapper;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "save new ordering")
    public OrderingResponseDto save(@RequestBody OrderingRequestDto requestDto) {
        return orderingMapper.mapToDto(
                orderingService.save(orderingMapper.mapToModel(requestDto)));
    }

    @PostMapping("/product")
    @Operation(summary = "add product to an ordering")
    public OrderingResponseDto addProduct(@RequestParam Long productId,
                                          @RequestParam Long orderingId) {
        Product product = productService.getById(productId);
        Ordering ordering = orderingService.getById(orderingId);
        ordering.getProducts().add(product);
        return orderingMapper.mapToDto(orderingService.save(ordering));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update an ordering")
    public OrderingResponseDto update(@RequestBody OrderingRequestDto requestDto,
                                      @PathVariable Long id) {
        Ordering ordering = orderingMapper.mapToModel(requestDto);
        ordering.setId(id);
        return orderingMapper.mapToDto(orderingService.save(ordering));
    }

    @PutMapping("/status")
    @Operation(summary = "update status of an ordering")
    public OrderingResponseDto updateOrderingStatus(@RequestParam String status,
                                                    @RequestParam Long id) {
        return orderingMapper.mapToDto(orderingService.updateStatus(status, id));
    }

    @GetMapping("/{id}/price")
    @Operation(summary = "calculate and get price of an ordering")
    public BigDecimal calculatePrice(@PathVariable Long id) {
        return orderingService.calculatePrice(id);
    }
}
