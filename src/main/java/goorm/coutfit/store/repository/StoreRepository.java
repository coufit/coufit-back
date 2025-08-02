package goorm.coutfit.store.repository;

import goorm.coutfit.store.domain.Store;
import goorm.coutfit.store.dto.StoreWithDistance;
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
           CASE
               WHEN :latitude IS NOT NULL AND :longitude IS NOT NULL
               THEN (6371000 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) *
                                   cos(radians(s.longitude) - radians(:longitude)) +
                                   sin(radians(:latitude)) * sin(radians(s.latitude))))
               ELSE NULL
           END AS distance
    FROM store s
    WHERE (
          :keyword IS NULL
          OR LENGTH(:keyword) < 2
          OR MATCH(s.name, s.address, s.category_name) AGAINST (:keyword IN BOOLEAN MODE)
    )
      AND (:region IS NULL OR s.address LIKE CONCAT(:region, '%'))
      AND (:area IS NULL OR s.address LIKE CONCAT('%', :area, '%'))
      AND (:category IS NULL OR s.category_name = :category)
      AND (:isOpenNow IS NULL OR
           (:isOpenNow = true AND s.open_time IS NOT NULL AND s.close_time IS NOT NULL AND
            CASE
                WHEN s.close_time < s.open_time
                THEN (CURRENT_TIME BETWEEN s.open_time AND '23:59:59' OR CURRENT_TIME BETWEEN '00:00:00' AND s.close_time)
                ELSE CURRENT_TIME BETWEEN s.open_time AND s.close_time
            END) OR
           (:isOpenNow = false AND (s.open_time IS NULL OR s.close_time IS NULL OR 
            CASE 
                WHEN s.close_time < s.open_time 
                THEN NOT (CURRENT_TIME BETWEEN s.open_time AND '23:59:59' OR CURRENT_TIME BETWEEN '00:00:00' AND s.close_time)
                ELSE NOT (CURRENT_TIME BETWEEN s.open_time AND s.close_time) 
            END)))
    ORDER BY 
        CASE 
            WHEN :sort = 'popularity' 
            THEN (SELECT COUNT(*) FROM payment_history ph WHERE ph.store_id = s.id) 
        END DESC,
        CASE 
            WHEN :sort = 'distance' THEN distance 
        END ASC,
        s.name ASC
    """, nativeQuery = true)
    List<StoreWithDistance> searchStores(
            @Param("keyword") String keyword,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("region") String region,
            @Param("area") String area,
            @Param("category") String category,
            @Param("isOpenNow") Boolean isOpenNow,
            @Param("sort") String sort
    );

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
    List<StoreWithDistance> findNearbyStoresWithDistance(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Integer radius
    );
}
