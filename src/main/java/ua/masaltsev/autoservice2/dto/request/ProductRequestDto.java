package ua.masaltsev.autoservice2.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String name;
    private BigDecimal price;
}
