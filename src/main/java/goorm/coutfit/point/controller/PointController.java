package goorm.coutfit.point.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.point.controller.request.PointChargeRequest;
import goorm.coutfit.point.controller.response.PointBalanceResponse;
import goorm.coutfit.point.controller.response.PointChargeResponse;
import goorm.coutfit.point.controller.response.PointSpendingHistoryResponse;
import goorm.coutfit.point.controller.response.PointSummaryResponse;
import goorm.coutfit.point.service.PointService;
import goorm.coutfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    
    @GetMapping("/balance")
    public BaseResponse<PointBalanceResponse> balance(){
        // TODO: 추후 유저 연동 필요
        User currentUser = getDummyUser();
        
        PointBalanceResponse response = pointService.getCurrentBalance(currentUser);
        return BaseResponse.success("현재 잔액 조회 성공", response);
    }

    @GetMapping("/summary")
    public BaseResponse<PointSummaryResponse> summary(){
        // TODO: 추후 유저 연동 필요
        User currentUser = getDummyUser();

        PointSummaryResponse response = pointService.getMonthlySummary(currentUser);
        return BaseResponse.success("소비 요약 정보 조회 성공", response);
    }

    @GetMapping("/spend/history")
    public BaseResponse<List<PointSpendingHistoryResponse>> allSpendHistory() {
        User currentUser = getDummyUser(); // TODO: 나중에 실제 유저로 변경
        List<PointSpendingHistoryResponse> response = pointService.getPaymentHistory(currentUser);
        return BaseResponse.success("소비 내역 전체 조회 성공", response);
    }

    @GetMapping("/spend/history/paged")
    public BaseResponse<Page<PointSpendingHistoryResponse>> spendHistoryWithPaging(Pageable pageable) {
        User currentUser = getDummyUser(); // TODO: 나중에 실제 유저로 변경
        Page<PointSpendingHistoryResponse> response = pointService.getPaymentHistoryWithPaging(currentUser, pageable);
        return BaseResponse.success("소비 내역 페이지 조회 성공", response);
    }


    @PostMapping("/charge")
    public BaseResponse<PointChargeResponse> chargePoints(@RequestBody PointChargeRequest request) {
        User currentUser = getDummyUser(); // TODO: 나중에 실제 유저로 변경
        PointChargeResponse response = pointService.chargePoints(currentUser, request.getAmount(), request.getPaymentMethod());
        return BaseResponse.success("포인트 충전 성공", response);
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
