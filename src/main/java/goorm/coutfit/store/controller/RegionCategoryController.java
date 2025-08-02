package goorm.coutfit.store.controller;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.store.dto.RegionCategoryResponse;
import goorm.coutfit.store.service.RegionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionCategoryController {
    private final RegionCategoryService regionCategoryService;

    @GetMapping("/categories")
    public BaseResponse<List<RegionCategoryResponse>> categories() {
        List<RegionCategoryResponse> response = regionCategoryService.getAllRegionCategories();
        return BaseResponse.success("지역 카테고리 조회 성공", response);
    }
}
