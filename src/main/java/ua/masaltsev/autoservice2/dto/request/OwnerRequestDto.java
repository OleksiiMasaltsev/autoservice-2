package ua.masaltsev.autoservice2.dto.request;

import java.util.Set;
import lombok.Data;

@Data
public class OwnerRequestDto {
    private String name;
    private Set<Long> carIds;
    private Set<Long> orderingIds;
}
