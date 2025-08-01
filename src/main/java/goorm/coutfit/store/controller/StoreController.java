package goorm.coutfit.store.controller;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.store.dto.StoreDetailResponse;
import goorm.coutfit.store.repository.StoreRepository;
import goorm.coutfit.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
