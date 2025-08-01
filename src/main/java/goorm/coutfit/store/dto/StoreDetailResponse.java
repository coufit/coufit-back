package goorm.coutfit.store.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDetailResponse {
    private Long storeId;
    private String storeName;
    private String categoryName;

    private DiscountResponse discount;

    private String address;
//    private Integer distance;
    private String openTime;
    private String closeTime;
    private String phoneNumber;
    private Boolean isOpen;
    private String imageUrl; // 대표 이미지 한 개만
}
