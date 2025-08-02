package goorm.coutfit.store.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class StoreRecommendationRequest {
    private Long userId;
    private Double latitude;
    private Double longitude;
}