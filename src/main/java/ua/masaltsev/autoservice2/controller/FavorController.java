package ua.masaltsev.autoservice2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.FavorMapper;
import ua.masaltsev.autoservice2.dto.request.FavorRequestDto;
import ua.masaltsev.autoservice2.dto.response.FavorResponseDto;
import ua.masaltsev.autoservice2.service.FavorService;

@RestController
@RequestMapping("/favors")
public class FavorController {
    private final FavorService favorService;
    private final FavorMapper favorMapper;

    public FavorController(FavorService favorService, FavorMapper favorMapper) {
        this.favorService = favorService;
        this.favorMapper = favorMapper;
    }

    @PostMapping
    public FavorResponseDto save(@RequestBody FavorRequestDto requestDto) {
        return favorMapper.mapToDto(favorService.save(favorMapper.mapToModel(requestDto)));
    }


}
