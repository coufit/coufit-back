package goorm.coutfit.store.repository;

import goorm.coutfit.store.dto.StoreWithDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class StoreSearchRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<StoreWithDistance> searchStores(
            String keyword,
            Double latitude,
            Double longitude,
            String region,
            String area,
            String category,
            Boolean isOpenNow,
            String sort
    ) {
        StringBuilder sql = new StringBuilder("""
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
            LEFT JOIN store_image si ON s.id = si.store_id
            LEFT JOIN region_category rc ON s.region_category_id = rc.id
            LEFT JOIN (
                SELECT store_id, COUNT(*) AS payment_count
                FROM payment_history
                GROUP BY store_id
            ) ph ON ph.store_id = s.id
            WHERE 1=1
        """);

        Map<String, Object> params = new HashMap<>();
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        // 키워드 검색
        if (keyword != null && !keyword.isBlank()) {
            sql.append("""
                AND MATCH(s.name, s.category_name, s.address)
                AGAINST (:keyword IN BOOLEAN MODE)
            """);
            params.put("keyword", keyword + "*"); // 예: '카페*'
        }

        // 지역 필터
        if (region != null && !region.isBlank()) {
            sql.append(" AND rc.region = :region ");
            params.put("region", region);
        }

        if (area != null && !area.isBlank()) {
            sql.append(" AND rc.area = :area ");
            params.put("area", area);
        }

        // 카테고리 필터
        if (category != null && !category.isBlank()) {
            sql.append(" AND s.category_name = :category ");
            params.put("category", category);
        }

        // 현재 영업 중 필터
        if (Boolean.TRUE.equals(isOpenNow)) {
            sql.append("""
                AND (
                    (s.open_time < s.close_time AND CURTIME() BETWEEN s.open_time AND s.close_time)
                    OR
                    (s.open_time > s.close_time AND (CURTIME() >= s.open_time OR CURTIME() <= s.close_time))
                )
            """);
        }

        if (sort == null) {
            sort = "distance";  // 기본 정렬값
        }

        // 정렬
        switch (sort) {
            case "popularity" -> sql.append(" ORDER BY payment_count DESC ");
            case "distance" -> sql.append(" ORDER BY distance ASC ");
            default -> sql.append(" ORDER BY distance ASC ");
        }

        return jdbcTemplate.query(sql.toString(), params, rowMapper);
    }

    private final RowMapper<StoreWithDistance> rowMapper = (rs, rowNum) ->
            StoreWithDistance.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .categoryName(rs.getString("category_name"))
                    .openTime(rs.getObject("open_time", LocalTime.class))
                    .closeTime(rs.getObject("close_time", LocalTime.class))
                    .address(rs.getString("address"))
                    .latitude(rs.getDouble("latitude"))
                    .longitude(rs.getDouble("longitude"))
                    .distance(rs.getDouble("distance"))
                    .phoneNumber(rs.getString("phone_number"))
                    .imageUrl(rs.getString("image_url"))
                    .build();
}
