package ua.masaltsev.autoservice2.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String name;
    private BigDecimal price;
}
