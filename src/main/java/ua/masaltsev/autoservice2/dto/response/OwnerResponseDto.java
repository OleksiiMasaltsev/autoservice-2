package ua.masaltsev.autoservice2.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class OwnerResponseDto {
    private Long id;
    private String name;
    private List<Long> carIds;
    private List<Long> orderingIds;
}
