package goorm.coutfit.point.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import goorm.coutfit.point.controller.response.PointSummaryResponse;
import goorm.coutfit.point.domain.PaymentHistory;
import goorm.coutfit.user.domain.User;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    @Query("""
        SELECT new goorm.coutfit.point.controller.response.PointSummaryResponse(
            COALESCE(SUM(CASE WHEN p.paidAt BETWEEN :start AND :end THEN p.amount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN p.paidAt BETWEEN :start AND :end THEN 1 ELSE 0 END), 0),      
            COUNT(DISTINCT CASE WHEN p.paidAt BETWEEN :start AND :end THEN p.store.id ELSE NULL END), 
            COALESCE(SUM(p.amount), 0)                                                       
        )
        FROM PaymentHistory p
        WHERE p.user.id = :userId
    """)
    PointSummaryResponse getMonthlySummary(Long userId, LocalDateTime start, LocalDateTime end);

    List<PaymentHistory> findByUserOrderByPaidAtDesc(User user);
    
    Page<PaymentHistory> findByUserOrderByPaidAtDesc(User user, Pageable pageable);


    @Query(value = """
        SELECT store_id
        FROM payment_history
        GROUP BY store_id
        ORDER BY COUNT(*) DESC
        LIMIT 4
    """, nativeQuery = true)
    List<Long> findTop4PopularStoreIds();

    @Query("SELECT ph From PaymentHistory ph JOIN FETCH ph.store WHERE ph.user.id =:userId")
    List<PaymentHistory> findByUserIdWithStore(Long userId);
}
