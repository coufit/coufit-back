package goorm.coutfit.store.service;

import goorm.coutfit.store.domain.Store;
import goorm.coutfit.store.domain.StoreDiscount;
import goorm.coutfit.store.dto.DiscountResponse;
import goorm.coutfit.store.dto.StoreDetailResponse;
import goorm.coutfit.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public StoreDetailResponse getStoreDetail(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가맹점이 존재하지 않습니다."));

        StoreDiscount discount = store.getStoreDiscount();
        DiscountResponse discountResponse = null;
        if (discount != null) {
            discountResponse = new DiscountResponse(
                    discount.getDiscountRate(),
                    discount.getTitle(),
                    discount.getDescription(),
                    discount.getStartTime(),
                    discount.getEndTime()
            );
        }

        LocalTime now = LocalTime.now();
        boolean isOpen = isStoreOpen(now, store.getOpenTime(), store.getCloseTime());

        String imageUrl = store.getStoreImage().getImageUrl().isEmpty() ? null : store.getStoreImage().getImageUrl();

        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getRegionCategory().getName(),
                discountResponse,
                store.getAddress(),
//                store.getDistance(),  Todo : 현재위치로부터 가맹점 거리 계산 로직
                store.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                store.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                store.getPhoneNumber(),
                isOpen,
                imageUrl
        );
    }

    public boolean isStoreOpen(LocalTime now, LocalTime openTime, LocalTime closeTime) {
        // 자정을 넘기는 영업시간일 경우 처리 (ex: 22:00 ~ 02:00)
        if (closeTime.isBefore(openTime)) {
            return !now.isBefore(openTime) || !now.isAfter(closeTime);
        }
        // 일반적인 경우
        return !now.isBefore(openTime) && !now.isAfter(closeTime);
    }
}
