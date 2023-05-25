package ua.masaltsev.autoservice2.owner;

import ua.masaltsev.autoservice2.dto.request.OwnerRequestDto;

import java.util.Collections;

public class OwnerRequestHolder {
    public static OwnerRequestDto getRequest() {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto();
        ownerRequestDto.setName("Oleksii");
        ownerRequestDto.setCarIds(Collections.emptyList());
        ownerRequestDto.setOrderingIds(Collections.emptyList());
        return ownerRequestDto;
    }
}
