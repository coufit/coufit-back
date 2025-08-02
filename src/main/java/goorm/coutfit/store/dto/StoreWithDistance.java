package goorm.coutfit.store.dto;

import java.sql.Time;

public record StoreWithDistance(
        Long id,
        String name,
        String categoryName,
        Time openTime,
        Time closeTime,
        String address,
        Double latitude,
        Double longitude,
        Double distance,
        String phoneNumber,
        String imageUrl
) {}