package goorm.coutfit.store.repository;

import goorm.coutfit.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value = """
    SELECT s.*
    FROM store s
    WHERE (
        6371000 * acos(
            cos(radians(:latitude)) * cos(radians(s.latitude)) *
            cos(radians(s.longitude) - radians(:longitude)) +
            sin(radians(:latitude)) * sin(radians(s.latitude))
        )
    ) <= :radius
    ORDER BY (
        6371000 * acos(
            cos(radians(:latitude)) * cos(radians(s.latitude)) *
            cos(radians(s.longitude) - radians(:longitude)) +
            sin(radians(:latitude)) * sin(radians(s.latitude))
        )
    ) ASC
    """, nativeQuery = true)
    List<Store> findNearbyStores(
        @Param("latitude") Double latitude,  
        @Param("longitude") Double longitude, 
        @Param("radius") Integer radius
    );
}
