package goorm.coutfit.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.coutfit.point.domain.ChargeHistory;

public interface ChargeHistoryRepository extends JpaRepository<ChargeHistory, Long> {
    
}
