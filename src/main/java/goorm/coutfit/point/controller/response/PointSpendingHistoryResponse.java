package goorm.coutfit.point.controller.response;

import java.time.LocalDateTime;

import goorm.coutfit.point.domain.PaymentHistory;
import goorm.coutfit.store.domain.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointSpendingHistoryResponse {
    private String storeName;
    private String category;
    private LocalDateTime paidAt;
    private Integer amount;
    private String imageUrl;

    public static PointSpendingHistoryResponse from(PaymentHistory paymentHistory) {
        Store store = paymentHistory.getStore();
        String imageUrl = (store.getStoreImage() != null)
                ? store.getStoreImage().getImageUrl()
                : null;

        return PointSpendingHistoryResponse.builder()
                .storeName(store.getName())
                .category(store.getCategoryName())
                .paidAt(paymentHistory.getPaidAt())
                .amount(paymentHistory.getAmount())
                .imageUrl(imageUrl)
                .build();
    }
}
