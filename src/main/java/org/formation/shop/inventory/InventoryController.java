package org.formation.shop.inventory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import org.formation.shop.inventory.internal.StockEntity;
import org.formation.shop.inventory.internal.StockRepository;


@RestController 
@RequestMapping("/stocks")
public class InventoryController {

    private final StockRepository stockRepository;

    InventoryController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/{coffeeId}")
    public int getCoffeeById(@PathVariable Long coffeeId) {

        Optional<StockEntity> stock = stockRepository.findByCoffeeId(coffeeId) ; 
        
        return stock.map(s -> s.getQuantity())
                            .orElse(0);
    }
    
}   