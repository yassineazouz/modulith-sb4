package org.formation.shop;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:coffee-flow")
class CoffeeFlowTests {
    @LocalServerPort int port;
    @Autowired JdbcTemplate jdbc;

    @Test
    void postCreatesCoffeeStockAndCompletedPublication() {
        Long id = RestClient.create("http://localhost:" + port).post()
                .uri("/coffees?name=Espresso&price=10")
                .retrieve().body(Long.class);

        assertThat(id).isNotNull();
        assertThat(jdbc.queryForObject("select name from coffee_entity where id = ?", String.class, id))
                .isEqualTo("Espresso");
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            assertThat(jdbc.queryForObject("select quantity from stock_entity where coffee_id = ?", Integer.class, id))
                    .isZero();
            assertThat(jdbc.queryForObject(
                    "select count(*) from event_publication where completion_date is not null", Long.class))
                    .isEqualTo(1L);
        });
    }
}
