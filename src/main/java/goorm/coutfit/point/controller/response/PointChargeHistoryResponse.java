package goorm.coutfit.point.controller.response;

import goorm.coutfit.point.domain.ChargeHistory;
import goorm.coutfit.point.domain.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointChargeHistoryResponse {
    private Integer amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime chargedAt;

    public static PointChargeHistoryResponse from(ChargeHistory chargeHistory) {
        return PointChargeHistoryResponse.builder()
                .amount(chargeHistory.getAmount())
                .paymentMethod(chargeHistory.getPaymentMethod())
                .chargedAt(chargeHistory.getChargedAt())
                .build();
    }
}
