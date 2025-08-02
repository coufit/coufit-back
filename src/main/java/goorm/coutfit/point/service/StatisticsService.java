package goorm.coutfit.point.service;

import goorm.coutfit.auth.CurrentUserUtil;
import goorm.coutfit.point.controller.response.SpendingPatternResponse;
import goorm.coutfit.point.domain.PaymentHistory;
import goorm.coutfit.point.repository.PaymentHistoryRepository;
import goorm.coutfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final CurrentUserUtil currentUserUtil;

    public List<SpendingPatternResponse> getSpendingPatterns() {
        User user = currentUserUtil.getCurrentUser();
        List<PaymentHistory> histories = paymentHistoryRepository.findByUserIdWithStore(user.getId());

        Map<String, Integer> spendingByCategory = new HashMap<>();
        int totalAmount = 0;

        for (PaymentHistory history : histories) {
            String category = history.getStore().getCategoryName();
            int amount = history.getAmount();

            spendingByCategory.put(category, spendingByCategory.getOrDefault(category, 0) + amount);
            totalAmount += amount;
        }

        int finalTotalAmount = totalAmount;

        return spendingByCategory.entrySet().stream()
                .map(entry -> new SpendingPatternResponse(
                        entry.getKey(),
                        Math.round((entry.getValue() * 100f) / finalTotalAmount)))
                .sorted((a, b) -> b.getPercentage() - a.getPercentage()) // 내림차순 정렬
                .collect(Collectors.toList());
    }
}
