package goorm.coutfit.store.repository;

import goorm.coutfit.store.domain.Store;
import goorm.coutfit.store.projection.StoreWithDistanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = """
    SELECT
        s.id,
        s.name,
        s.category_name,
        s.open_time,
        s.close_time,
        s.address,
        s.latitude,
        s.longitude,
        (
            6371000 * acos(
                cos(radians(:latitude)) * cos(radians(s.latitude)) *
                cos(radians(s.longitude) - radians(:longitude)) +
                sin(radians(:latitude)) * sin(radians(s.latitude))
            )
        ) AS distance,
        s.phone_number,
        si.image_url
    FROM store s
    LEFT JOIN store_image si ON si.store_id = s.id
    WHERE (
        6371000 * acos(
            cos(radians(:latitude)) * cos(radians(s.latitude)) *
            cos(radians(s.longitude) - radians(:longitude)) +
            sin(radians(:latitude)) * sin(radians(s.latitude))
        )
    ) <= :radius
    ORDER BY distance ASC
    """, nativeQuery = true)
    List<StoreWithDistanceProjection> findNearbyStoresWithDistance(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Integer radius
    );
}
