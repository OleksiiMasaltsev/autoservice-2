package ua.masaltsev.autoservice2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.Collections;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.service.ProductService;
import ua.masaltsev.autoservice2.service.WorkerService;

@RestController
@Tag(name = "Bootstrap controller", description = "load initial entities to the DB")
public class BootstrapController {
    private final ProductService productService;
    private final WorkerService workerService;

    public BootstrapController(ProductService productService,
                               WorkerService workerService) {
        this.productService = productService;
        this.workerService = workerService;
    }

    @Operation(summary = "load and link entities")
    @GetMapping("/init")
    public String loadData() {
        Product product1 = new Product();
        product1.setName("motor oil");
        product1.setPrice(BigDecimal.valueOf(23.00));

        Product product2 = new Product();
        product2.setName("air filter");
        product2.setPrice(BigDecimal.valueOf(11.15));

        Product product3 = new Product();
        product3.setName("battery");
        product3.setPrice(BigDecimal.valueOf(74.45));

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);

        Worker worker1 = new Worker();
        worker1.setName("Kostiantyn");
        worker1.setOrderings(Collections.emptySet());

        Worker worker2 = new Worker();
        worker2.setName("Petro");
        worker2.setOrderings(Collections.emptySet());

        workerService.save(worker1);
        workerService.save(worker2);

        return "Created!";
    }
}
