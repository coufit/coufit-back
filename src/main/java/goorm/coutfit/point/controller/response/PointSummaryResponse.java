package goorm.coutfit.point.controller.response;

import lombok.Getter;

@Getter
public class PointSummaryResponse {
    private Long monthlyUsageAmount;
    private Long monthlyUsageCount;
    private Long usedStoresCount;
    private Long totalUsageAmount;

    public PointSummaryResponse(Long monthlyUsageAmount, Long monthlyUsageCount, Long usedStoresCount, Long totalUsageAmount) {
        this.monthlyUsageAmount = monthlyUsageAmount;
        this.monthlyUsageCount = monthlyUsageCount;
        this.usedStoresCount = usedStoresCount;
        this.totalUsageAmount = totalUsageAmount;
    }
}
