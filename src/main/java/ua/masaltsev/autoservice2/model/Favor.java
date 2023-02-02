package ua.masaltsev.autoservice2.model;

import java.math.BigDecimal;
import ua.masaltsev.autoservice2.model.status.FavorStatus;

public class Favor {
    private Ordering ordering;
    private Worker worker;
    private BigDecimal price;
    private FavorStatus status;
}
