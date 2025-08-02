package goorm.coutfit.store.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreSearchRequest {
    private String keyword;
    private Double latitude;
    private Double longitude;
    private String region;
    private String area;
    private String category;
    private String sort;
    private Boolean isOpenNow;
}
