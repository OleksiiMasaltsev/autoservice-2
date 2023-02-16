package ua.masaltsev.autoservice2.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.OrderingMapper;
import ua.masaltsev.autoservice2.dto.mapper.OwnerMapper;
import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;
import ua.masaltsev.autoservice2.dto.response.OrderingResponseDto;
import ua.masaltsev.autoservice2.dto.response.OwnerResponseDto;
import ua.masaltsev.autoservice2.model.Owner;
import ua.masaltsev.autoservice2.service.OwnerService;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;
    private final OrderingMapper orderingMapper;

    public OwnerController(OwnerService ownerService,
                           OwnerMapper ownerMapper,
                           OrderingMapper orderingMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
        this.orderingMapper = orderingMapper;
    }

    @PostMapping
    public OwnerResponseDto save(@RequestBody OwnerRequestDto requestDto) {
        return ownerMapper.mapToDto(ownerService.save(ownerMapper.mapToModel(requestDto)));
    }

    @PutMapping("/{id}")
    public OwnerResponseDto update(@RequestBody OwnerRequestDto requestDto,
                                   @PathVariable Long id) {
        Owner owner = ownerMapper.mapToModel(requestDto);
        owner.setId(id);
        return ownerMapper.mapToDto(ownerService.save(owner));
    }

    @GetMapping("/orderings/{id}")
    public List<OrderingResponseDto> getOrderings(@PathVariable Long id) {
        return ownerService.getById(id).getOrderings().stream()
                .map(orderingMapper::mapToDto)
                .toList();
    }
}
