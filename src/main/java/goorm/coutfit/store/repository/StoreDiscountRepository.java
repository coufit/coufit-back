package goorm.coutfit.store.repository;

import goorm.coutfit.store.domain.StoreDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreDiscountRepository extends JpaRepository<StoreDiscount, Long> {
    Optional<StoreDiscount> findFirstByStoreIdOrderByCreatedAtDesc(Long storeId);
}
