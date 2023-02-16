package ua.masaltsev.autoservice2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.OrderingMapper;
import ua.masaltsev.autoservice2.dto.mapper.ProductMapper;
import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.service.OrderingService;
import ua.masaltsev.autoservice2.service.ProductService;

@RestController
@RequestMapping("/orderings")
public class OrderingController {
    private final OrderingService orderingService;
    private final ProductService productService;
    private final OrderingMapper orderingMapper;
    private final ProductMapper productMapper;

    public OrderingController(OrderingService orderingService,
                              ProductService productService,
                              OrderingMapper orderingMapper,
                              ProductMapper productMapper) {
        this.orderingService = orderingService;
        this.productService = productService;
        this.orderingMapper = orderingMapper;
        this.productMapper = productMapper;
    }

    @PostMapping
    public OrderingResponseDto save(@RequestBody OrderingRequestDto requestDto) {
        return orderingMapper.mapToDto(
                orderingService.save(orderingMapper.mapToModel(requestDto)));
    }

    @PostMapping("/product")
    public OrderingResponseDto addProduct(@RequestParam Long productId,
                                          @RequestParam Long orderingId) {
        Product product = productService.getById(productId);
        Ordering ordering = orderingService.getById(orderingId);
        ordering.getProducts().add(product);
        return orderingMapper.mapToDto(orderingService.save(ordering));
    }

    @PutMapping("/{id}")
    public OrderingResponseDto update(@RequestBody OrderingRequestDto requestDto,
                                      @PathVariable Long id) {
        Ordering ordering = orderingMapper.mapToModel(requestDto);
        ordering.setId(id);
        return orderingMapper.mapToDto(orderingService.save(ordering));
    }

    @PutMapping
    public OrderingResponseDto updateOrderingStatus(@RequestParam String status,
                                                    @RequestParam Long id) {
        Ordering ordering = orderingService.getById(id);
        ordering.setStatus(OrderingStatus.valueOf(status.toUpperCase()));
        return orderingMapper.mapToDto(orderingService.save(ordering));
    }

    @GetMapping("/{id}")
    public OrderingResponseDto calculatePrice(@PathVariable Long id) {
        return orderingMapper.mapToDto(orderingService.calculatePrice(id));
    }
}
