package goorm.coutfit.point.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.coutfit.common.response.BaseResponse;
import goorm.coutfit.point.controller.response.BalanceResponse;
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
    public BaseResponse<BalanceResponse> balance(){
        
        // TODO: 추후 유저 연동 필요
        User currentUser = getDummyUser();
        
        BalanceResponse response = pointService.getCurrentBalance(currentUser);

        return BaseResponse.success("현재 잔액 조회 완료", response);
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
