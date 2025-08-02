package goorm.coutfit.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PopularStoreResponse {
    private String storeName;
    private String category;
    private Integer distance;
    private Integer discount;
    private String imageUrl;
}
