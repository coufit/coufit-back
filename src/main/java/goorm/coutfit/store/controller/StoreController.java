package goorm.coutfit.store.controller;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.store.dto.StoreDetailResponse;
import goorm.coutfit.store.dto.StoreResponse;
import goorm.coutfit.store.dto.StoreSearchResponse;
import goorm.coutfit.store.dto.request.StoreSearchRequest;
import goorm.coutfit.store.service.StoreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreDetailResponse> getStoreDetail(@PathVariable Long storeId) {
        StoreDetailResponse response = storeService.getStoreDetail(storeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stores/nearby")
    public BaseResponse<List<StoreResponse>> nearby(
            @RequestParam("latitude")Double latitude, 
            @RequestParam("longitude") Double longitude, 
            @RequestParam(value = "radius", defaultValue = "150") Integer radius
    ) {
        List<StoreResponse> response = storeService.getNearbyStores(latitude, longitude, radius);
        return BaseResponse.success("내 주변 가맹점 조회 성공", response);
    }

    @GetMapping("/stores/search")
    public BaseResponse<StoreSearchResponse> search(StoreSearchRequest request) {
        StoreSearchResponse response = storeService.searchStores(request);
        return BaseResponse.success("가맹점 검색 결과 조회 성공", response);
    }
}
