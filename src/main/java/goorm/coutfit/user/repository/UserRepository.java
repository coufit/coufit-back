package goorm.coutfit.user.repository;

import goorm.coutfit.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
