package ua.masaltsev.autoservice2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.FavorMapper;
import ua.masaltsev.autoservice2.dto.request.FavorRequestDto;
import ua.masaltsev.autoservice2.dto.response.FavorResponseDto;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
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

    @PutMapping("/{id}")
    public FavorResponseDto updateFavor(@RequestBody FavorRequestDto requestDto,
                                        @PathVariable Long id) {
        Favor favor = favorMapper.mapToModel(requestDto);
        favor.setId(id);
        return favorMapper.mapToDto(favorService.save(favor));
    }

    @PutMapping
    public FavorResponseDto updateFavorStatus(@RequestParam String status,
                                              @RequestParam Long id) {
        Favor favor = favorService.getById(id);
        favor.setStatus(FavorStatus.valueOf(status.toUpperCase()));
        return favorMapper.mapToDto(favorService.save(favor));
    }
}
