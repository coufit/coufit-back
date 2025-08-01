package goorm.coutfit.point.service;

import goorm.coutfit.point.controller.response.BalanceResponse;
import goorm.coutfit.point.repository.PointRepository;
import goorm.coutfit.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PointService {
    
    private final PointRepository pointRepository;
    
    @Transactional(readOnly = true)
    public BalanceResponse getCurrentBalance(User user) {
        return pointRepository.findByUserId(user.getId())
                                .map(BalanceResponse::from)
                                .orElse(BalanceResponse.empty());
    }
}
