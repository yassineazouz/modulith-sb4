package org.formation.shop.catalog;

import lombok.RequiredArgsConstructor;
import org.formation.shop.catalog.internal.CoffeeEntity;
import org.formation.shop.catalog.internal.CoffeeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Catalog {
    private final CoffeeRepository repository;
    private final ApplicationEventPublisher events;

    public record CoffeeView( Long id,String name,int price) {}

    @Transactional
    public Long createCoffee(String name, int price) {
        CoffeeEntity saved = repository.save(new CoffeeEntity(name, price));
        events.publishEvent(new CoffeeCreated(saved.getId(), saved.getName()));
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public List<CoffeeView> findAll() {
        return repository.findAll().stream()
        .map(coffee -> new CoffeeView(
            coffee.getId() ,coffee.getName(),coffee.getPrice()
        )).toList();

    }

}
