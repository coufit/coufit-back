package goorm.coutfit.store.dto;

import java.time.LocalTime;

public interface StoreWithDistance {
    Long getId();
    String getName();
    String getCategoryName();
    LocalTime getOpenTime();
    LocalTime getCloseTime();
    String getAddress();
    Double getLatitude();
    Double getLongitude();
    Double getDistance();
    String getPhoneNumber();
    String getImageUrl();
}