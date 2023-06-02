package ua.masaltsev.autoservice2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import ua.masaltsev.autoservice2.car.CarScenario;
import ua.masaltsev.autoservice2.initializer.TestcontainersInitializer;
import ua.masaltsev.autoservice2.ordering.OrderingScenario;
import ua.masaltsev.autoservice2.owner.OwnerScenario;
import ua.masaltsev.autoservice2.worker.WorkerScenario;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
class Autoservice2ApplicationIT {
    private static final String LOCALHOST = "http://localhost:%d";
    private static TestRestTemplate template;
    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        template = new TestRestTemplate();
    }

    @Test
    void ownerCrud_ok() {
        OwnerScenario.ownerCrud(template, getRootUrl());
    }

    @Test
    void getOrderingsByOwnerId_ok() {
        OwnerScenario.getOrderings(template, getRootUrl());
    }

    @Test
    void carCrud_ok() {
        CarScenario.carCrud(template, getRootUrl());
    }

    @Test
    void orderingCrud_ok() {
        OrderingScenario.orderingCrud(template, getRootUrl());
    }

    @Test
    void calculateOrderingPrice_returnsCorrectValue() {
        OrderingScenario.calculatePrice(template, getRootUrl());
    }

    @Test
    void calculateWorkersSalary_returnsCorrectValue() {
        WorkerScenario.calculateWorkersSalary(template, getRootUrl());
    }

    private String getRootUrl() {
        return String.format(LOCALHOST, port);
    }
}
