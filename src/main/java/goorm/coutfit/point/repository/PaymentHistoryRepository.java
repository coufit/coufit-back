package goorm.coutfit.point.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import goorm.coutfit.point.controller.response.PointSummaryResponse;
import goorm.coutfit.point.domain.PaymentHistory;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    @Query("""
        SELECT new goorm.coutfit.point.controller.response.PointSummaryResponse(
            COALESCE(SUM(p.amount), 0),
            COUNT(p),
            COUNT(DISTINCT p.store.id),
            0
        )
        FROM PaymentHistory p 
        WHERE p.user.id = :userId 
        AND p.paidAt BETWEEN :start AND :end
    """)
    PointSummaryResponse getMonthlySummary(Long userId, LocalDateTime start, LocalDateTime end);

}
