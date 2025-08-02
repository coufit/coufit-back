package goorm.coutfit.point.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpendingPatternResponse {
    private String categoryName;
    private int percentage;
}
