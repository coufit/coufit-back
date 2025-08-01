package goorm.coutfit.point.controller.response;

import goorm.coutfit.point.domain.Point;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointBalanceResponse {
    private Integer pointBalance;
    private Integer expireAmount;
    private Integer expireInDays;

    public static PointBalanceResponse from(Point point) {
        int pointBalance = point.getPointBalance();
        int expireInDays = point.getExpireInDays();
        int expireAmount = pointBalance;

        return PointBalanceResponse.builder()
            .pointBalance(pointBalance)
            .expireAmount(expireAmount)
            .expireInDays(expireInDays)
            .build();
    }

    public static PointBalanceResponse empty() {
        return PointBalanceResponse.builder()
            .pointBalance(0)
            .expireAmount(0)
            .expireInDays(0)
            .build();
    }
}
