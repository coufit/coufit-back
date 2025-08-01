package goorm.coutfit.store.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountResponse {
    private int discountRate;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
