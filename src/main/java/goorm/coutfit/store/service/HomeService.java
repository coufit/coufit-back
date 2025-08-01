package goorm.coutfit.store.service;

import goorm.coutfit.point.repository.PaymentHistoryRepository;
import goorm.coutfit.store.dto.HomeDashboardResponse;
import goorm.coutfit.store.repository.StoreRepository;
import goorm.coutfit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    public HomeDashboardResponse getDashboard() {
        long totalStoreCount = storeRepository.count();
        long totalUserCount = userRepository.count();
        long totalPaymentCount = paymentHistoryRepository.count();

        return new HomeDashboardResponse(totalStoreCount, totalUserCount, totalPaymentCount);
    }

}
