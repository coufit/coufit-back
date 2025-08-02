package goorm.coutfit.store.dto;

import goorm.coutfit.store.domain.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreMarkerResponse {
    private Long storeId;
    private String name;
    private Double latitude;
    private Double longitude;

   public static StoreMarkerResponse from(Store store) {
       return StoreMarkerResponse.builder()
               .storeId(store.getId())
               .name(store.getName())
               .latitude(store.getLatitude())
               .longitude(store.getLongitude())
               .build();
   }
}
