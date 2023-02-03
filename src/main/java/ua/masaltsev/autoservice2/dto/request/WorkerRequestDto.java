package ua.masaltsev.autoservice2.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class WorkerRequestDto {
    private String name;
    private List<Long> orderingIds;
}
