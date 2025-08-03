package goorm.coutfit.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.coutfit.point.domain.ChargeHistory;

import java.util.List;

public interface ChargeHistoryRepository extends JpaRepository<ChargeHistory, Long> {
    List<ChargeHistory> findAllByUserIdOrderByChargedAtDesc(Long userId);
}
