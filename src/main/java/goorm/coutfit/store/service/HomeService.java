package goorm.coutfit.store.service;

import goorm.coutfit.auth.CurrentUserUtil;
import goorm.coutfit.point.repository.PaymentHistoryRepository;
import goorm.coutfit.store.domain.Store;
import goorm.coutfit.store.domain.StoreDiscount;
import goorm.coutfit.store.dto.HomeDashboardResponse;
import goorm.coutfit.store.dto.PopularStoreResponse;
import goorm.coutfit.store.repository.StoreDiscountRepository;
import goorm.coutfit.store.repository.StoreRepository;
import goorm.coutfit.user.domain.User;
import goorm.coutfit.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final StoreDiscountRepository storeDiscountRepository;
    private final CurrentUserUtil currentUserUtil;

    public HomeDashboardResponse getDashboard() {
        long totalStoreCount = storeRepository.count();
        long totalUserCount = userRepository.count();
        long totalPaymentCount = paymentHistoryRepository.count();

        return new HomeDashboardResponse(totalStoreCount, totalUserCount, totalPaymentCount);
    }

    public List<PopularStoreResponse> getPopularStore(double userLat, double userLon) {
        User user = currentUserUtil.getCurrentUser();
        List<Long> topStoreIds = paymentHistoryRepository.findTop4PopularStoreIds();
        List<PopularStoreResponse> result = new ArrayList<>();

        for (Long storeId : topStoreIds) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("Store not found: " + storeId));

            Optional<StoreDiscount> optionalDiscount = storeDiscountRepository.findFirstByStoreIdOrderByCreatedAtDesc(store.getId());
            int discountRate = optionalDiscount.map(StoreDiscount::getDiscountRate).orElse(0);

            String imageUrl = store.getStoreImage() != null ? store.getStoreImage().getImageUrl() : null;

            int distance = calculateDistance(userLat, userLon, store.getLatitude(), store.getLongitude());

            result.add(new PopularStoreResponse(
                    store.getName(),
                    store.getCategoryName(),
                    distance,
                    discountRate,
                    imageUrl
            ));
        }

        return result;
    }

    private int calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (int) (R * c);
    }

}
