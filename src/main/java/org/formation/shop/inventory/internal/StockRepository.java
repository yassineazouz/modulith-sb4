package org.formation.shop.inventory.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;




public interface StockRepository extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findByCoffeeId(Long coffeeId);

}
