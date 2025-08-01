package goorm.coutfit.point.controller.response;

import lombok.Getter;

@Getter
public class PointSummaryResponse {
    private Long monthlyUsageAmount;
    private Long monthlyUsageCount;
    private Long usedStoresCount;
    private Integer monthlySavedAmount;

    public PointSummaryResponse(Long monthlyUsageAmount, Long monthlyUsageCount, Long usedStoresCount, Integer monthlySavedAmount) {
        // TODO: 이번달 사용 내역의 10% 절약 했다고 가정
        int savedAmount = (int) (monthlyUsageAmount * 0.1);
        
        this.monthlyUsageAmount = monthlyUsageAmount;
        this.monthlyUsageCount = monthlyUsageCount;
        this.usedStoresCount = usedStoresCount;
        this.monthlySavedAmount = savedAmount;
    }
}
