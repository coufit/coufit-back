package goorm.coutfit.store.service;

import goorm.coutfit.auth.CurrentUserUtil;
import goorm.coutfit.store.domain.Store;
import goorm.coutfit.store.domain.StoreDiscount;
import goorm.coutfit.store.dto.*;
import goorm.coutfit.store.dto.request.StoreSearchRequest;
import goorm.coutfit.store.projection.StoreWithDistanceProjection;
import goorm.coutfit.store.repository.StoreDiscountRepository;
import goorm.coutfit.store.repository.StoreRepository;
import goorm.coutfit.store.repository.StoreSearchRepository;
import goorm.coutfit.user.domain.User;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreDiscountRepository storeDiscountRepository;
    private final StoreSearchRepository storeSearchRepository;
    private final CurrentUserUtil currentUserUtil;
    private final RestTemplate restTemplate;

    @Transactional(readOnly = true)
    public StoreDetailResponse getStoreDetail(Long storeId) {
        User currentUser = currentUserUtil.getCurrentUser();

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가맹점이 존재하지 않습니다."));

        StoreDiscount discount = storeDiscountRepository.findFirstByStoreIdOrderByCreatedAtDesc(storeId)
                .orElse(null);

        DiscountResponse discountResponse = null;
        if (discount != null) {
            discountResponse = new DiscountResponse(
                    discount.getDiscountRate(),
                    discount.getTitle(),
                    discount.getDescription(),
                    discount.getStartTime(),
                    discount.getEndTime()
            );
        }

        LocalTime now = LocalTime.now();
        boolean isOpen = isStoreOpen(now, store.getOpenTime(), store.getCloseTime());

        String imageUrl = store.getStoreImage().getImageUrl().isEmpty() ? null : store.getStoreImage().getImageUrl();

        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getRegionCategory().getName(),
                discountResponse,
                store.getAddress(),
//                store.getDistance(),  Todo : 현재위치로부터 가맹점 거리 계산 로직
                store.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                store.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                store.getPhoneNumber(),
                isOpen,
                imageUrl
        );
    }
    @Transactional(readOnly = true)
    public List<StoreResponse> getNearbyStores(Double latitude, Double longitude, Integer radius) {
        List<StoreWithDistanceProjection> results = storeRepository.findNearbyStoresWithDistance(latitude, longitude, radius);
        return results == null ? Collections.emptyList() :
                results.stream()
                        .map(StoreResponse::from)
                        .toList();
    }

    @Transactional(readOnly = true)
    public StoreSearchResponse searchStores(StoreSearchRequest request) {
        List<StoreWithDistance> results = storeSearchRepository.searchStores(
                request.getKeyword(),
                request.getLatitude(),
                request.getLongitude(),
                request.getRegion(),
                request.getArea(),
                request.getCategory(),
                request.getIsOpenNow(),
                request.getSort()
        );
        log.info(String.valueOf(results==null));
        List<StoreResponse> storeList =
                results == null ? Collections.emptyList() :
                        results.stream()
                                .map(StoreResponse::from)
                                .toList();

        return StoreSearchResponse.from(storeList);
    }

    public boolean isStoreOpen(LocalTime now, LocalTime openTime, LocalTime closeTime) {
        // 자정을 넘기는 영업시간일 경우 처리 (ex: 22:00 ~ 02:00)
        if (closeTime.isBefore(openTime)) {
            return !now.isBefore(openTime) || !now.isAfter(closeTime);
        }
        // 일반적인 경우
        return !now.isBefore(openTime) && !now.isAfter(closeTime);
    }

    public List<StoreResponse> recommendStores(Long userId, Double latitude, Double longitude) {
        // FastAPI 요청
        String url = "http://172.16.24.58:8000/recommend/top-stores";
        Map<String, Object> requestBody = Map.of(
                "user_id", userId,
                "user_lat", latitude,
                "user_lon", longitude
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                List.class
        );

        // 추천된 store_id 리스트
        List<Long> recommendedIds = response.getBody();
        if (recommendedIds == null || recommendedIds.isEmpty()) {
            return List.of();
        }

        // DB에서 매핑 후 순서 보존
        List<StoreWithDistanceProjection> stores = storeRepository.findByIdIn(recommendedIds, latitude, longitude);

        return stores == null ? Collections.emptyList() :
                stores.stream()
                        .map(StoreResponse::from)
                        .toList();

//        Map<Long, StoreWithDistanceProjection> storeMap = stores.stream()
//                .collect(Collectors.toMap(StoreWithDistanceProjection::getId, Function.identity()));
//
//        return recommendedIds.stream()
//                .map(storeMap::get)
//                .filter(Objects::nonNull)
//                .map(StoreResponse::from)
//                .collect(Collectors.toList());
    }
}
