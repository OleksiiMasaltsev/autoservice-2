package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.dto.mapper.FavorMapper;
import ua.masaltsev.autoservice2.dto.request.FavorRequestDto;
import ua.masaltsev.autoservice2.dto.response.FavorResponseDto;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.service.FavorService;

@Slf4j
@RestController
@RequestMapping("/favors")
@Tag(name = "Favor controller")
public class FavorController {
    private final FavorService favorService;
    private final FavorMapper favorMapper;

    public FavorController(FavorService favorService, FavorMapper favorMapper) {
        this.favorService = favorService;
        this.favorMapper = favorMapper;
    }

    @Operation(summary = "save new favor")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FavorResponseDto save(@RequestBody FavorRequestDto requestDto) {
        log.info("saving new favor: {}", requestDto);
        return favorMapper.mapToDto(favorService.save(favorMapper.mapToModel(requestDto)));
    }

    @Operation(summary = "retrieving a favor by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public FavorResponseDto getById(@PathVariable Long id) {
        log.info("retrieving a favor by id: {}", id);
        return favorMapper.mapToDto(favorService.getById(id));
    }

    @Operation(summary = "update a favor")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public FavorResponseDto update(@RequestBody FavorRequestDto requestDto, @PathVariable Long id) {
        log.info("updating a favor: {} with id: {}", requestDto, id);
        Favor favor = favorMapper.mapToModel(requestDto);
        favor.setId(id);
        return favorMapper.mapToDto(favorService.save(favor));
    }

    @Operation(summary = "delete a favor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("deleting a favor with id: {}", id);
        favorService.deleteById(id);
    }

    @Operation(summary = "update status of a favor")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/status")
    public FavorResponseDto updateFavorStatus(@RequestParam String status, @RequestParam Long id) {
        log.info("updating status of favor with id: {} to: {}", id, status);
        Favor favor = favorService.getById(id);
        favor.setStatus(FavorStatus.valueOf(status.toUpperCase()));
        return favorMapper.mapToDto(favorService.save(favor));
    }
}
