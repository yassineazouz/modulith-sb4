package org.formation.shop.inventory.internal;

import org.formation.shop.catalog.CoffeeCreated;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {
    private final StockRepository repository;

    public InventoryListener(StockRepository repository) {
        this.repository = repository;
    }

    @ApplicationModuleListener
    public void on(CoffeeCreated event) {
        repository.save(new StockEntity(event.coffeeId(), 0));
    }
}
