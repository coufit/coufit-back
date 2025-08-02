package goorm.coutfit.store.controller;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.store.dto.StoreDetailResponse;
import goorm.coutfit.store.dto.StoreMarkerResponse;
import goorm.coutfit.store.service.StoreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseResponse<List<StoreMarkerResponse>> nearby(
            @RequestParam("latitude")Double latitude, 
            @RequestParam("longitude") Double longitude, 
            @RequestParam(value = "radius", defaultValue = "150") Integer radius
    ) {
        List<StoreMarkerResponse> response = storeService.getNearbyStores(latitude, longitude, radius);
        return BaseResponse.success("내 주변 가맹정 조회 성공", response);     
    }
}
