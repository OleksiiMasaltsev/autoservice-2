package ua.masaltsev.autoservice2.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class OwnerRequestDto {
    private String name;
    private List<Long> carIds;
    private List<Long> orderingIds;
}
