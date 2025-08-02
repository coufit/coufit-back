package goorm.coutfit.store.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreSearchResponse {
    private int totalStoresCount;
    private List<StoreResponse> storeList;

    public static StoreSearchResponse from(List<StoreResponse> storeList) {
        return StoreSearchResponse.builder()
                .totalStoresCount(storeList.size())
                .storeList(storeList)
                .build();
    }
}
