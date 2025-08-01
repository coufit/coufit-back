package goorm.coutfit.point.controller.response;

import java.time.LocalDateTime;

import goorm.coutfit.point.domain.ChargeHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointChargeResponse {
    private Integer amount;
    private Integer pointBalance;
    private LocalDateTime chargedAt;

    public static PointChargeResponse from(ChargeHistory chargeHistory, Integer pointBalance) {
        return PointChargeResponse.builder()
                .amount(chargeHistory.getAmount())
                .pointBalance(pointBalance)
                .chargedAt(chargeHistory.getChargedAt())
                .build();
    }
}
