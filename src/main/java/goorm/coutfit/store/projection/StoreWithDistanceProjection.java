package goorm.coutfit.store.projection;

import java.time.LocalTime;

public interface StoreWithDistanceProjection {
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