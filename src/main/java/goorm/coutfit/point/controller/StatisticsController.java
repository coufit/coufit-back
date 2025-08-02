package goorm.coutfit.point.controller;

import goorm.coutfit.point.controller.response.SpendingPatternResponse;
import goorm.coutfit.point.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/spending-pattern")
    public List<SpendingPatternResponse> getSpendingPattern() {
        return statisticsService.getSpendingPatterns();
    }
}
