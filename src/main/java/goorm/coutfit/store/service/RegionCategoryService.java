package goorm.coutfit.store.service;

import goorm.coutfit.store.domain.RegionCategory;
import goorm.coutfit.store.dto.RegionCategoryResponse;
import goorm.coutfit.store.repository.RegionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionCategoryService {
    private final RegionCategoryRepository regionCategoryRepository;

    public List<RegionCategoryResponse> getAllRegionCategories() {
        List<RegionCategory> categories = regionCategoryRepository.findAll();

        return categories.stream()
                .map(RegionCategoryResponse::from)
                .toList();
    }
}
