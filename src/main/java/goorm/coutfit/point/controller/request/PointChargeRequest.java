package goorm.coutfit.point.controller.request;

import goorm.coutfit.point.domain.PaymentMethod;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointChargeRequest {
    private Integer amount;
    private PaymentMethod paymentMethod;
}