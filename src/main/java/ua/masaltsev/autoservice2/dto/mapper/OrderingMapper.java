package ua.masaltsev.autoservice2.dto.mapper;

import ua.masaltsev.autoservice2.dto.request.OrderingRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.service.CarService;
import ua.masaltsev.autoservice2.service.FavorService;
import ua.masaltsev.autoservice2.service.ProductService;
import java.util.stream.Collectors;

public class OrderingMapper implements RequestDtoMapper<OrderingRequestDto, Ordering>,
        ResponseDtoMapper<OrderingResponseDto, Ordering> {
    private final CarService carService;
    private final FavorService favorService;
    private final ProductService productService;

    public OrderingMapper(CarService carService, FavorService favorService,
                          ProductService productService) {
        this.carService = carService;
        this.favorService = favorService;
        this.productService = productService;
    }

    @Override
    public Ordering mapToModel(OrderingRequestDto dto) {
        Ordering ordering = new Ordering();
        ordering.setPrice(dto.getPrice());
        ordering.setDescription(dto.getDescription());
        ordering.setReceivingTime(dto.getReceivingTime());
        ordering.setCompletionTime(dto.getCompletionTime());
        ordering.setCar(carService.getById(dto.getCarId()));
        ordering.setStatus(OrderingStatus.valueOf(dto.getStatus().toUpperCase()));

        ordering.setFavors(dto.getFavorIds().stream()
                .map(favorService::getById)
                .collect(Collectors.toList()));

        ordering.setProducts(dto.getProductIds().stream()
                .map(productService::getById)
                .collect(Collectors.toList()));

        return ordering;
    }

    @Override
    public OrderingResponseDto mapToDto(Ordering ordering) {
        OrderingResponseDto dto = new OrderingResponseDto();
        dto.setId(ordering.getId());
        dto.setPrice(ordering.getPrice());
        dto.setDescription(ordering.getDescription());
        dto.setReceivingTime(ordering.getReceivingTime());
        dto.setCompletionTime(ordering.getCompletionTime());
        dto.setCarId(ordering.getCar().getId());
        dto.setStatus(ordering.getStatus().toString());

        dto.setFavorIds(ordering.getFavors().stream()
                .map(Favor::getId)
                .collect(Collectors.toList()));

        dto.setProductIds(ordering.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList()));

        return dto;
    }
}
