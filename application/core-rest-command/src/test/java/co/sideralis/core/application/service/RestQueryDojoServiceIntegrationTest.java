package co.sideralis.core.application.service;

import co.com.sofka.infraestructure.bus.EventBus;
import co.sideralis.core.application.Application;
import co.sideralis.core.dojo.events.CreatedDojo;
import co.sideralis.core.dojo.values.CoachId;
import co.sideralis.core.dojo.values.DataInfo;
import co.sideralis.core.dojo.values.Location;
import co.sideralis.core.dojo.values.OpeningHours;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class RestQueryDojoServiceIntegrationTest {

    @Autowired
    private EventBus eventBus;

    @BeforeAll
    static void cleanAll() {
        new MongoClient().getDatabase("queries").drop();
    }

    @Test
    public void findClan_noExist() {
        RestQueryDojoService restQueryClanService = new RestQueryDojoService();
        var result = restQueryClanService.existDojoName("cas");
        Assertions.assertFalse(result);
    }

    @Test
    public void findDojo() throws InterruptedException {
        var createdDojo = new CreatedDojo(
                CoachId.of("ffff-xxxx"),
                new DataInfo("my dojo", "the best dojo place"),
                new Location("https://google.com", "Medellin", "description",
                        new OpeningHours(10, 12, OpeningHours.Frequency.BIWEEKLY)
                )
        );
        eventBus.publish(createdDojo);
        Thread.sleep(1000);
        RestQueryDojoService restQueryDojoService = new RestQueryDojoService();
        var result = restQueryDojoService.existDojoName("My Dojo");
        Assertions.assertTrue(result);
    }

}
