package ua.masaltsev.autoservice2.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
