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
        LocalTime openTime = storeWithDistance.openTime().toLocalTime();
        LocalTime closeTime = storeWithDistance.closeTime().toLocalTime();
        return StoreResponse.builder()
                .storeId(storeWithDistance.id())
                .name(storeWithDistance.name())
                .categoryName(storeWithDistance.categoryName())
                .openTime(openTime)
                .closeTime(closeTime)
                .isOpenNow(isStoreOpenNow(openTime, closeTime))
                .address(storeWithDistance.address())
                .distance(storeWithDistance.distance() != null ? storeWithDistance.distance().intValue() : 0) // 현재 위치 값 없으면 0으로 반환
                .latitude(storeWithDistance.latitude())
                .longitude(storeWithDistance.longitude())
                .phoneNumber(storeWithDistance.phoneNumber())
                .imageUrl(storeWithDistance.imageUrl())
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
