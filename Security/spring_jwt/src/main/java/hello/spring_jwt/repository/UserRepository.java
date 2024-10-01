package hello.spring_jwt.repository;

import hello.spring_jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
