package ua.masaltsev.autoservice2.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class OwnerRequestDto {
    private String name;
    private List<Long> carIds;
    private List<Long> orderingIds;
}
