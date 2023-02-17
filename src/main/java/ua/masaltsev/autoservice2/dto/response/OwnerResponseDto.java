package ua.masaltsev.autoservice2.dto.response;

import java.util.Set;
import lombok.Data;

@Data
public class OwnerResponseDto {
    private Long id;
    private String name;
    private Set<Long> carIds;
    private Set<Long> orderingIds;
}
