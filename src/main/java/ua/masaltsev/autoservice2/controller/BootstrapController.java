package ua.masaltsev.autoservice2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.autoservice2.model.Car;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.model.Ordering;
import ua.masaltsev.autoservice2.model.Owner;
import ua.masaltsev.autoservice2.model.Product;
import ua.masaltsev.autoservice2.model.Worker;
import ua.masaltsev.autoservice2.model.status.FavorStatus;
import ua.masaltsev.autoservice2.model.status.OrderingStatus;
import ua.masaltsev.autoservice2.service.CarService;
import ua.masaltsev.autoservice2.service.FavorService;
import ua.masaltsev.autoservice2.service.OrderingService;
import ua.masaltsev.autoservice2.service.OwnerService;
import ua.masaltsev.autoservice2.service.ProductService;
import ua.masaltsev.autoservice2.service.WorkerService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
public class BootstrapController {
    private final ProductService productService;
    private final WorkerService workerService;
    private final OwnerService ownerService;
    private final CarService carService;
    private final FavorService favorService;
    private final OrderingService orderingService;

    public BootstrapController(ProductService productService,
                               WorkerService workerService,
                               OwnerService ownerService,
                               CarService carService,
                               FavorService favorService,
                               OrderingService orderingService) {
        this.productService = productService;
        this.workerService = workerService;
        this.ownerService = ownerService;
        this.carService = carService;
        this.favorService = favorService;
        this.orderingService = orderingService;
    }

    @GetMapping("/init")
    public String loadData() {
        //products
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

        //owners and cars
        Owner owner1 = new Owner();
        owner1.setName("Stepan");
        owner1.setCars(null);
        owner1.setOrderings(null);

        Owner owner2 = new Owner();
        owner2.setName("Dmytro");
        owner2.setCars(null);
        owner2.setOrderings(null);

        ownerService.save(owner1);
        ownerService.save(owner2);

        Car car1 = new Car();

        car1.setBrand("Skoda");
        car1.setModel("Octavia");
        car1.setYear((short) 2016);
        car1.setPlate("1233");
        car1.setOwner(null);

        Car car2 = new Car();
        car2.setBrand("Dacia");
        car2.setModel("Stepway");
        car2.setYear((short) 2019);
        car2.setPlate("9927");
        car2.setOwner(null);

//        carService.save(car1);
//        carService.save(car2);

        //orderings
        Ordering ordering1 = new Ordering();
        ordering1.setDescription("change oil and filter");
        ordering1.setPrice(BigDecimal.valueOf(32.00));
        LocalDateTime now1 = LocalDateTime.now();
        ordering1.setReceivingTime(now1);
        ordering1.setCompletionTime(now1.plusHours(3));
        ordering1.setStatus(OrderingStatus.RECEIVED);
        ordering1.setCar(car1);
        ordering1.setFavors(null);
        ordering1.setProducts(null);

        Ordering ordering2 = new Ordering();
        ordering2.setDescription("repair brakes");
        ordering2.setPrice(BigDecimal.valueOf(28.45));
        LocalDateTime now2 = LocalDateTime.now();
        ordering2.setReceivingTime(now2);
        ordering2.setCompletionTime(now2.plusHours(2));
        ordering2.setStatus(OrderingStatus.RECEIVED);
        ordering2.setCar(car2);
        ordering2.setFavors(null);
        ordering2.setProducts(null);

        orderingService.save(ordering1);
        orderingService.save(ordering2);

        //worker
        Worker worker1 = new Worker();
        worker1.setName("Kostiantyn");
        worker1.setOrderings(null);

        Worker worker2 = new Worker();
        worker2.setName("Petro");
        worker2.setOrderings(null);

        workerService.save(worker1);
        workerService.save(worker2);

        //favors
        Favor favor1 = new Favor();
        favor1.setPrice(BigDecimal.valueOf(4.56));
        favor1.setDescription("oil change");
        favor1.setWorker(null);
        favor1.setOrdering(null);
        favor1.setStatus(FavorStatus.UNPAID);

        Favor favor2 = new Favor();
        favor2.setPrice(BigDecimal.valueOf(15.66));
        favor2.setDescription("brakes change");
        favor2.setWorker(null);
        favor2.setOrdering(null);
        favor2.setStatus(FavorStatus.UNPAID);

        favorService.save(favor1);
        favorService.save(favor2);

        return "Done!" + linkOne();
    }

    public String linkOne() {
        Car car1 = carService.getById(1L);
        Owner owner1 = ownerService.getById(1L);
        car1.setOwner(owner1);

        Car car2 = carService.getById(2L);
        Owner owner2 = ownerService.getById(2L);
        car2.setOwner(owner2);

        Favor favor1 = favorService.getById(1L);
        Ordering ordering1 = orderingService.getById(1L);
        Worker worker1 = workerService.getById(1L);
        Product product1 = productService.getById(1L);
        Product product2 = productService.getById(2L);
        ordering1.setProducts(Set.of(product1, product2));
        favor1.setOrdering(ordering1);
        favor1.setWorker(worker1);
        owner1.setOrderings(Set.of(ordering1));
        worker1.setOrderings(Set.of(ordering1));

        Favor favor2 = favorService.getById(2L);
        Ordering ordering2 = orderingService.getById(2L);
        Worker worker2 = workerService.getById(2L);
        Product product3 = productService.getById(3L);
        ordering2.setProducts(Set.of(product3));
        favor2.setOrdering(ordering2);
        favor2.setWorker(worker2);
        owner2.setOrderings(Set.of(ordering2));
        worker2.setOrderings(Set.of(ordering2));

        carService.save(car1);
        carService.save(car2);
        favorService.save(favor1);
        favorService.save(favor2);
        ownerService.save(owner1);
        ownerService.save(owner2);
        workerService.save(worker1);
        workerService.save(worker2);
        orderingService.save(ordering1);
        orderingService.save(ordering2);

        return "Linked!";
    }
}
