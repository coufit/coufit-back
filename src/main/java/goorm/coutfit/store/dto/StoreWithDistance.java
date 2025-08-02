package goorm.coutfit.store.dto;

import goorm.coutfit.store.projection.StoreWithDistanceProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@Builder
public class StoreWithDistance {
    private Long id;
    private String name;
    private String categoryName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private String phoneNumber;
    private String imageUrl;

    public static StoreWithDistance from(StoreWithDistanceProjection p) {
        return StoreWithDistance.builder()
                .id(p.getId())
                .name(p.getName())
                .categoryName(p.getCategoryName())
                .openTime(p.getOpenTime())
                .closeTime(p.getCloseTime())
                .address(p.getAddress())
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .distance(p.getDistance())
                .phoneNumber(p.getPhoneNumber())
                .imageUrl(p.getImageUrl())
                .build();
    }
}