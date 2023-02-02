package ua.masaltsev.autoservice2.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class WorkerRequestDto {
    private String name;
    private List<Long> orderingIds;
}
