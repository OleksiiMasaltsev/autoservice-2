package ua.masaltsev.autoservice2.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class WorkerResponseDto {
    private Long id;
    private String name;
    private List<Long> orderingIds;
}
