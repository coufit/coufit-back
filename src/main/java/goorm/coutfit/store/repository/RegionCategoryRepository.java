package goorm.coutfit.store.repository;

import goorm.coutfit.store.domain.RegionCategory;
import goorm.coutfit.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionCategoryRepository extends JpaRepository<RegionCategory, Long> {

}
