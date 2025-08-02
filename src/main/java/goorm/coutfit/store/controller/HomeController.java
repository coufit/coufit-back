package goorm.coutfit.store.controller;

import goorm.coutfit.store.dto.HomeDashboardResponse;
import goorm.coutfit.store.dto.PopularStoreResponse;
import goorm.coutfit.store.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/dashboard")
    public ResponseEntity<HomeDashboardResponse> getDashboard() {
        return ResponseEntity.ok(homeService.getDashboard());
    }

    @GetMapping("/popular-stores")
    public ResponseEntity<List<PopularStoreResponse>> getPopularStores(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return ResponseEntity.ok(homeService.getPopularStore(lat, lon));
    }
}
