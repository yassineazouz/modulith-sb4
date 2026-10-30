package org.formation.shop.inventory;

import org.formation.shop.catalog.CoffeeCreated;
import org.formation.shop.inventory.internal.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
class InventoryModuleTests {
    @Autowired StockRepository repository;

    @Test
    void createsStockWhenCoffeeIsCreated(Scenario scenario) {
        scenario.publish(new CoffeeCreated(42L, "Espresso"))
                .andWaitForStateChange(repository::findAll, stocks -> !stocks.isEmpty())
                .andVerify(stocks -> {
                    assertThat(stocks).hasSize(1);
                    assertThat(stocks.getFirst().getCoffeeId()).isEqualTo(42L);
                    assertThat(stocks.getFirst().getQuantity()).isZero();
                });
    }
}
