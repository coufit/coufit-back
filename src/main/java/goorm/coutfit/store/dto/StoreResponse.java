package goorm.coutfit.store.dto;

import goorm.coutfit.store.domain.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class StoreResponse {
    private Long storeId;
    private String name;
    private String categoryName;
    private Boolean isOpenNow;
    private String address;
    private Integer distance;
    private Double latitude;
    private Double longitude;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String phoneNumber;
    private String imageUrl;

    public static StoreResponse from(StoreWithDistance storeWithDistance) {
        return StoreResponse.builder()
                .storeId(storeWithDistance.getId())
                .name(storeWithDistance.getName())
                .categoryName(storeWithDistance.getCategoryName())
                .openTime(storeWithDistance.getOpenTime())
                .closeTime(storeWithDistance.getCloseTime())
                .isOpenNow(isStoreOpenNow(storeWithDistance.getOpenTime(), storeWithDistance.getCloseTime()))
                .address(storeWithDistance.getAddress())
                .distance(storeWithDistance.getDistance() != null ? storeWithDistance.getDistance().intValue() : 0) // 현재 위치 값 없으면 0으로 반환
                .latitude(storeWithDistance.getLatitude())
                .longitude(storeWithDistance.getLongitude())
                .phoneNumber(storeWithDistance.getPhoneNumber())
                .imageUrl(storeWithDistance.getImageUrl())
                .build();
    }

    private static Boolean isStoreOpenNow(LocalTime openTime, LocalTime closeTime) {
        if (openTime == null || closeTime == null) {
            return null;
        }
        LocalTime now = LocalTime.now();

        if (closeTime.isBefore(openTime)) {
            return !now.isBefore(openTime) || !now.isAfter(closeTime);
        } else {
            return !now.isBefore(openTime) && !now.isAfter(closeTime);
        }
    }
}
