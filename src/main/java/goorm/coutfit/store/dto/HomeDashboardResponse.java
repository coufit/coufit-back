package goorm.coutfit.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeDashboardResponse {
    private long totalStoreCount;
    private long totalUserCount;
    private long totalPaymentCount;
}
