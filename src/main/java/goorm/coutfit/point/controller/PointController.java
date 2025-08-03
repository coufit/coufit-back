package goorm.coutfit.point.controller;

import java.util.List;

import goorm.coutfit.auth.CurrentUserUtil;
import goorm.coutfit.point.controller.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.point.controller.request.PointChargeRequest;
import goorm.coutfit.point.service.PointService;
import goorm.coutfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PointController {
    private final PointService pointService;
    private final CurrentUserUtil currentUserUtil;
    
    @GetMapping("/balance")
    public BaseResponse<PointBalanceResponse> balance(){
        User currentUser = currentUserUtil.getCurrentUser();
        PointBalanceResponse response = pointService.getCurrentBalance(currentUser);
        return BaseResponse.success("현재 잔액 조회 성공", response);
    }

    @GetMapping("/summary")
    public BaseResponse<PointSummaryResponse> summary(){
        User currentUser = currentUserUtil.getCurrentUser();
        PointSummaryResponse response = pointService.getMonthlySummary(currentUser);
        return BaseResponse.success("소비 요약 정보 조회 성공", response);
    }

    @GetMapping("/spend/history")
    public BaseResponse<List<PointSpendingHistoryResponse>> allSpendHistory() {
        User currentUser = currentUserUtil.getCurrentUser();
        List<PointSpendingHistoryResponse> response = pointService.getPaymentHistory(currentUser);
        return BaseResponse.success("소비 내역 전체 조회 성공", response);
    }

    @GetMapping("/spend/history/paged")
    public BaseResponse<Page<PointSpendingHistoryResponse>> spendHistoryWithPaging(Pageable pageable) {
        User currentUser = currentUserUtil.getCurrentUser();
        Page<PointSpendingHistoryResponse> response = pointService.getPaymentHistoryWithPaging(currentUser, pageable);
        return BaseResponse.success("소비 내역 페이지 조회 성공", response);
    }


    @PostMapping("/charge")
    public BaseResponse<PointChargeResponse> chargePoints(@RequestBody PointChargeRequest request) {
        User currentUser = currentUserUtil.getCurrentUser();
        PointChargeResponse response = pointService.chargePoints(currentUser, request.getAmount(), request.getPaymentMethod());
        return BaseResponse.success("포인트 충전 성공", response);
    }

    @GetMapping("/charge/history")
    public BaseResponse<List<PointChargeHistoryResponse>> chargeHistory() {
        User currentUser = currentUserUtil.getCurrentUser();
        List<PointChargeHistoryResponse> response = pointService.getChargeHistory(currentUser);
        return BaseResponse.success("포인트 충전 내역 조회 성공", response);
    }

    /**
     * TODO: 로그인 연동 전 임시 유저 반환 (나중에 제거 예정)
     */
    private User getDummyUser() {
        return User.builder()
                .id(1L)
                .build();
    }
}
