package co.sideralis.core.application.service;

import co.com.sofka.infraestructure.bus.EventBus;
import co.sideralis.core.application.Application;
import co.sideralis.core.clan.events.CreatedClan;
import co.sideralis.core.values.Color;
import co.sideralis.core.values.Name;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class RestQueryClanServiceIntegrationTest {

    @Autowired
    private EventBus eventBus;

    @BeforeAll
    static void cleanAll() {
        new MongoClient().getDatabase("queries").drop();
    }

    @Test
    public void findClan_noExist() {
        RestQueryClanService restQueryClanService = new RestQueryClanService();
        var result = restQueryClanService.existClanName("cas");
        Assertions.assertFalse(result);
    }

    @Test
    public void findClan() throws InterruptedException {
        var createdClan = new CreatedClan(
                new Name("Dream Clan"),
                new Color(Color.Type.BLUE)
        );
        eventBus.publish(createdClan);
        Thread.sleep(1000);
        RestQueryClanService restQueryClanService = new RestQueryClanService();
        var result = restQueryClanService.existClanName("Dream Clan");
        Assertions.assertTrue(result);
    }

}
