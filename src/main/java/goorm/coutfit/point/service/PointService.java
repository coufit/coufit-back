package goorm.coutfit.point.service;

import goorm.coutfit.point.controller.response.PointBalanceResponse;
import goorm.coutfit.point.controller.response.PointChargeResponse;
import goorm.coutfit.point.controller.response.PointSpendingHistoryResponse;
import goorm.coutfit.point.controller.response.PointSummaryResponse;
import goorm.coutfit.point.domain.ChargeHistory;
import goorm.coutfit.point.domain.PaymentHistory;
import goorm.coutfit.point.domain.PaymentMethod;
import goorm.coutfit.point.domain.Point;
import goorm.coutfit.point.repository.ChargeHistoryRepository;
import goorm.coutfit.point.repository.PaymentHistoryRepository;
import goorm.coutfit.point.repository.PointRepository;
import goorm.coutfit.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PointService {
    
    private final PointRepository pointRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final ChargeHistoryRepository chargeHistoryRepository;
    
    @Transactional(readOnly = true)
    public PointBalanceResponse getCurrentBalance(User user) {
        return pointRepository.findByUserId(user.getId())
                                .map(PointBalanceResponse::from)
                                .orElse(PointBalanceResponse.empty());
    }

    @Transactional(readOnly = true)
    public PointSummaryResponse getMonthlySummary(User user){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        
        return paymentHistoryRepository.getMonthlySummary(user.getId(), startOfMonth, now);
    }

    @Transactional(readOnly = true)
    public List<PointSpendingHistoryResponse> getPaymentHistory(User user) {
        List<PaymentHistory> paymentHistories = paymentHistoryRepository.findByUserOrderByPaidAtDesc(user);
        
        return paymentHistories.stream()
                .map(PointSpendingHistoryResponse::from)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Page<PointSpendingHistoryResponse> getPaymentHistoryWithPaging(User user, Pageable pageable) {
        Page<PaymentHistory> paymentHistories = paymentHistoryRepository.findByUserOrderByPaidAtDesc(user, pageable);
        
        return paymentHistories.map(PointSpendingHistoryResponse::from);
    }


    @Transactional
    public PointChargeResponse chargePoints(User user, Integer amount, PaymentMethod paymentMethod) {
        Point point = pointRepository.findByUserId(user.getId())
            .orElseGet(() -> pointRepository.save(Point.create(user)));

        point.charge(amount);

        ChargeHistory chargeHistory = ChargeHistory.create(user, amount, paymentMethod);
        chargeHistoryRepository.save(ChargeHistory.create(user, amount, paymentMethod));

        return PointChargeResponse.from(chargeHistory, point.getPointBalance());
    }
}
