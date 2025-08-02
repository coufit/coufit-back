package goorm.coutfit.store.dto;

import goorm.coutfit.store.domain.RegionCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RegionCategoryResponse {
    private Long id;
    private String name;
    private Long parentId;
    private List<Long> childrenIdList;

    public static RegionCategoryResponse from( RegionCategory category) {
        return RegionCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .childrenIdList(
                        category.getChildren().stream()
                                .map(RegionCategory::getId)
                                .toList()
                )
                .build();
    }
}
