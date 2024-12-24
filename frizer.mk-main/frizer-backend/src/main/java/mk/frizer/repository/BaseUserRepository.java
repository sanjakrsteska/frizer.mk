package mk.frizer.repository;

import mk.frizer.domain.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByEmail(String username);
    Optional<BaseUser> findByEmailAndPassword(String username, String password);
    List<BaseUser> findByEmailContaining(String email);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
